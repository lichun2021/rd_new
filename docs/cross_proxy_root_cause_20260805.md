# 先驱回响跨服进入失败根因结论（2026-08-05）

## 结论

`E:\111` 中的“先驱回响 Android/iOS 都无法进入”尚未在运行环境中修复。故障不在客户端、先驱回响的进入条件或目标服业务处理，而在通用 `CSProxy/CrossProxy` 链路：源服 `10001` 到当前代理 `tcp://192.168.10.20:8280` 的 ZeroMQ 通道从至少 2026-08-01 00:00 起就没有建立有效收发，导致所有依赖该通道的跨服功能都可能失败。

先驱回响只是第一个明显暴露故障的业务入口：房间被分配到 `10003` 后，`10001` 无法把 `INNER_ENTER_CROSS_REQ` 交给代理，目标服自然不会执行 `prepareEnterCross`。

## 直接证据

### 1. 先驱回响业务校验通过，失败发生在发送阶段

`E:\111\2026-08-04\Server.log.2026-08-04`：

```text
20:00:04,092 xqhx condtion check ... errorCode:0, serverId:10003
20:00:04,128 csproxy flush send fail, type:1, from:10001, to:10003, ...
20:00:04,128 csproxy rpc timeout ... costTime:36ms
20:00:04,130 xqhx prepare enter timeout ...
```

`36ms` 不是目标服处理慢或常规 RPC 超时，而是 ZMQ 实际发送失败后立即触发的失败回调。

当天统计：

| 日志项 | 次数 |
| --- | ---: |
| `xqhx prepare enter timeout` | 44 |
| `csproxy rpc timeout` | 44 |
| `csproxy flush send fail, type:1` | 52 |
| 目标服 `prepare cross server code` | 0 |

### 2. 代理心跳全天失败

同一日志中，`type:99`（CSProxy 心跳）的发送失败从 `00:00:02` 持续到 `23:59:59`，共 `28516` 次；没有任何 `csproxy heartbeat:` 接收日志。

20:00 的监控值反复为：

```text
sendProtocolNum:0, sendProtocolFailNum:5~7,
receivedProtocolNum:0,
activeAddr:tcp://192.168.10.20:8280,
masterServer:10005,
proxyAddr:tcp://192.168.10.20:8280,
localNodeCount:2, addressMatched:true, keepaliveAge:-1ms
```

`activeAddr/addressMatched` 只说明本地选中了 Redis 中登记的地址，不表示 TCP/ZeroMQ 通道已连通；`receivedProtocolNum:0` 与 `keepaliveAge:-1ms` 才说明从未收到代理响应。

### 3. 发送缓存耗尽的时间点证明通道一开始就不可用

`E:\RDLog\newLog\2026-08-01\Server.log.2026-08-01` 在 00:00 至 06:08 期间，心跳的 `sendProtocolNum` 仍是 3 或 4，但 `receivedProtocolNum` 始终为 0、`keepaliveAge=-1ms`。首个 `csproxy flush send fail, type:99` 出现在 06:08:19。

这说明前 6 小时的 `send` 只是写入本地 ZeroMQ 缓冲，并没有送达代理；缓冲积满后，使用 `HZMQ_NOBLOCK` 的发送才返回失败。`CrossProxy.init()` 对每个节点设置了 2MB 缓冲，现象与“未建立可用连接后本地缓存逐步耗尽”一致。

## 代码根因

相关代码：

- `src/main/java/com/hawk/game/crossproxy/CrossProxy.java`
- `src/main/java/com/hawk/game/crossproxy/ProxyHelper.java`

`CrossProxy.updateProxyState()` 的从节点逻辑只根据 Redis `csproxy:<areaId>.address` 选择 `activeZmq`。即使持续收不到心跳，`masterCheckTime` 的切换逻辑也只在 `ProxyHelper.isMasterServer()` 为真时运行；10001 是从节点时不会探测第二个配置节点，也不会重建当前连接。

因此故障过程是：

```text
Redis 地址 -> 从节点选择 192.168.10.20:8280
            -> ZMQ 实际没有收到代理响应
            -> 本地发送缓冲先累积、后写满
            -> NOBLOCK 发送失败
            -> 跨服 RPC 快速失败
            -> XQHX 玩家无法进入
```

## 已有提交与实际效果

提交 `5692a68d（尝试修复跨服）` 已经合入 `main`，修改了 `CrossProxy.java` 和 `CrossService.java`：

1. 检查 NOBLOCK 发送返回值；带 `rpcid` 的请求发送失败时立即回调失败。
2. 目标服收到过期 `INNER_ENTER_CROSS_REQ` 时回复异常，而不是静默丢弃。

这两个改动修复的是“静默等待”问题，并提供了本次定位所需日志；它们没有恢复失效的代理连接。因此它让故障从“长时间请求超时”变为“快速失败”，但不能宣称已解决代理不可达。

当前工作区未提交的 `CrossProxy.java` / `ProxyHelper.java` 改动增加了代理地址、选主和时钟偏差日志，并让主节点更早发布代理地址。它们同样没有处理“从节点连续收不到心跳后切换节点或重建连接”，不足以解决本次日志中的故障。

## 最终修复方向

需要修复通用框架，而不是修改先驱回响：

1. 从节点连续一个 `MASTER_CHECK_PERIOD` 未收到代理心跳时，将当前 `activeZmq` 判为失效。
2. 对本地 `zmqList` 中的其余节点做探测/切换；切换后必须以收到代理心跳作为健康判定，而不能只看 `send()` 返回 true。
3. 全部节点均未恢复时，关闭并重建 DEALER socket，避免继续使用已耗尽发送缓存的 socket。
4. 运行环境同时排查 `192.168.10.20:8280`：CSProxy 进程是否监听、10001/10003 到该地址的网络连通性、CSProxy 是否保留两个服的 DEALER identity，以及 Redis 中 `csproxy:<areaId>` 的 `master/address/heartbeat` 是否与实际代理一致。

## 2026-08-05 已落地的框架修复

`rd_new` 已实现以下恢复逻辑：

1. 新增 `CrossProxyHealth.shouldRecover(...)`，统一判断“未收到心跳”或“心跳超过检查周期”的失联状态。
2. 从节点在 `MASTER_CHECK_PERIOD` 内持续收不到心跳时，重建当前 Redis 指定代理地址对应的 DEALER socket，清除已经写满的本地发送缓存；新的 socket 只有收到代理心跳后才被视为恢复。
3. 从节点因 Redis 地址切换到新 socket 时清空旧的心跳健康状态，避免把旧连接的心跳误用于新连接。
4. 修复主节点备用代理选择处被注释吞掉的 `activeZmq = zmqObj` 赋值，使“csproxy master select proxy”日志对应真实 socket 切换。

对应文件：

- `src/main/java/com/hawk/game/crossproxy/CrossProxy.java`
- `src/main/java/com/hawk/game/crossproxy/CrossProxyHealth.java`
- `src/test/java/com/hawk/game/crossproxy/CrossProxyHealthTest.java`

独立回归测试已验证失联与健康边界。完整 Gradle 编译目前无法在该 checkout 执行：工程缺少相邻模块及当前协议生成物，编译会在既有 `PBHeroRiseShow`、`RooKieMilestone`、活动实现等缺失依赖处失败；这些错误发生在本次文件之外。

## 上线验收标准

修复后，以真实跨服进入验证，而非只看客户端不报超时：

1. `crossProxy ten seconds` 的 `keepaliveAge` 为正常正值，且 `receivedProtocolNum` 持续大于 0。
2. `csproxy flush send fail, type:99` 不再每 3 秒出现。
3. XQHX 进入后目标服出现 `prepare cross server code`，源服不再出现相同 `rpcid` 的 `csproxy flush send fail`。
4. 在当前代理停机或网络隔离的演练中，从节点可以切到备用代理或在可控时间内返回明确失败，不会永久停在失效地址。
