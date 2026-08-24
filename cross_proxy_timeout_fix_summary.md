# 跨服活动加入超时问题修复说明

## 背景

线上现象是跨服活动中玩家偶发加入失败，客户端表现为请求超时。该问题集中出现在玩家从源服进入跨服/战区服的链路中，入口请求已经发起，但源服迟迟等不到目标服或 CSProxy 的有效结果。

本次修改针对跨服 RPC 链路中两个会导致“静默等待直到超时”的点：

1. 源服到 CSProxy 的协议进入发送队列后，实际 ZMQ 发送失败没有被业务感知。
2. 目标服收到过期的 `INNER_ENTER_CROSS_REQ` 后直接 `return`，没有回 RPC 响应。

## 涉及文件

- `src/main/java/com/hawk/game/crossproxy/CrossProxy.java`
- `src/main/java/com/hawk/game/crossproxy/CrossService.java`

## 问题根因

### 1. 发送入队成功不等于实际发送成功

原链路中，`sendProxyProtocol()` 会先检查是否存在 active ZMQ 连接。如果存在，就把协议放入 `protoSendQueue` 并返回 `true`。

但真正的发送发生在 `flushProtoQueue()` 中。该方法原来调用：

- `csZmq.send(...)`
- `csZmq.sendProtocol(...)`

时没有检查返回值。

在 ZMQ 使用 `HZMQ_NOBLOCK` 的情况下，如果发送高水位、网络异常或代理节点异常导致实际发送失败，`send/sendProtocol` 会返回失败。但旧代码忽略返回值，导致业务层认为请求已经发出，后续只能等待 RPC 超时。

### 2. 目标服过期请求静默丢弃

`CrossService.prepareEnterCross()` 会校验 `InnerEnterCrossReq.curTime`。如果请求到达目标服时已经超过 `CrossProtocolValidTime`，旧逻辑只打印日志：

```java
inner enter cross timeout
```

然后直接 `return`。

这会导致源服 RPC 调用收不到 `INNER_ENTER_CROSS_RESP`，玩家侧仍然表现为请求超时，而不是快速失败。

## 修改内容

### 1. 增加 ZMQ 实际发送失败处理

位置：`CrossProxy.flushProtoQueue()`

修改后，所有实际发送都会检查返回值：

- 心跳发送检查 `csZmq.send(...)`
- 普通协议检查 header 和 body 两段发送
- 广播协议检查 header、body、idList 三段发送

任意一段发送失败时，会进入 `onSendProtocolFail(header)`。

处理逻辑：

- 记录失败次数 `sendProtocolFailNum`
- 打印失败协议上下文
- 如果协议带有 `rpcid`，立即触发 `onRpcTimeout(rpcid)`

这样 RPC 请求不会继续等待完整超时时间，能够尽快回到业务回调。

### 2. 增强跨服代理监控日志

位置：`CrossProxy.recordMonitor()`

10 秒监控日志新增：

- `sendProtocolFailNum`
- `sendQueueSize`

用于观察 CSProxy 发送失败和队列积压趋势。

### 3. 增强 RPC 超时日志

位置：`CrossProxy.onRpcTimeout()`

RPC 超时时新增上下文：

- `rpcid`
- `type`
- `from`
- `to`
- `source`
- `target`
- `costTime`
- `sendQueueSize`
- `rpcCacheSize`

同时在超时处理时移除 `stubTimeMap`，避免残留。

### 4. 目标服过期请求返回 RPC 响应

位置：`CrossService.prepareEnterCross()`

当 `INNER_ENTER_CROSS_REQ` 超过有效时间后，不再静默 `return`，而是：

```java
rpcCommonResp(proxyHeader, Status.SysError.EXCEPTION_VALUE);
```

同时日志增加：

- 源服 `from`
- `rpcid`
- `crossType`

这样源服能够收到明确失败响应，结束等待链路。

## 修复后的效果

本次修复后，以下场景不再静默等待到请求超时：

1. CSProxy 队列已入队，但实际 ZMQ 发送失败。
2. 目标服收到跨服进入请求时，协议已经超过有效时间。

新增日志也能帮助继续区分问题发生在哪一段：

- `csproxy flush send fail`：源服/代理发送阶段失败。
- `csproxy rpc timeout`：RPC 请求超过配置超时时间或发送失败后触发 timeout。
- `inner enter cross timeout`：目标服收到请求时已经过期。
- `crossProxy ten seconds ... sendProtocolFailNum/sendQueueSize`：观察发送失败数量和队列积压。

## 验证情况

已执行：

- `git diff --check`：通过。
- `CrossProxy.java` 单文件增量 `javac`：通过。
- 使用 `javap` 确认 `HawkZmq.send(...)` 和 `HawkZmq.sendProtocol(...)` 返回值为 `boolean`。

未执行完整 Gradle 编译，原因：

- 当前机器没有 `gradle` 命令。
- 手动编译两个文件时，当前 checkout 缺少完整外部依赖和匹配版本协议 jar，剩余错误为既有依赖/协议版本不匹配，例如 `ActiveStatCrossPB`、`GUILD_ACTIVE_STAT_CS_NOTIFY_VALUE`，不是本次修改引入。

## 风险和注意点

1. 本次修改没有改变跨服进入成功路径，只处理失败和超时路径。
2. `onSendProtocolFail()` 对带 `rpcid` 的请求会立即触发 timeout 回调，业务表现从“等待超时”变为“快速失败”。
3. 非 RPC 协议发送失败只记录日志和失败计数，不额外补偿。
4. `Status.SysError.EXCEPTION_VALUE` 是当前代码已有的通用异常码，后续如需更精细区分“目标服收到过期请求”，可以新增专用错误码。

## 建议观察日志

上线后重点观察以下日志：

```text
csproxy flush send fail
csproxy rpc timeout
inner enter cross timeout
crossProxy ten seconds sendProtocolNum
```

如果继续出现玩家加入跨服超时，可以通过 `rpcid/source/target/from/to` 串联源服、目标服和 CSProxy 日志，判断是发送失败、请求过期、队列积压，还是业务回调链路异常。
