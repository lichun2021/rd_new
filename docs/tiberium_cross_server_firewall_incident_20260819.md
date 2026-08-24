# 泰伯利亚跨服无法进入问题说明

## 一句话结论

泰伯利亚玩家无法进入跨服活动的直接原因是：**跨服代理服务器的 Linux 防火墙没有放行 `8280` 端口**。

游戏服可以找到代理服务器，但无法与它建立连接；因此玩家的跨服请求无法发送出去，最终表现为“进入失败”或“请求超时”。

## 对外说明

跨服活动需要经过一个“中转站”，可以把它理解成快递分拣中心：

```text
玩家所在游戏服（192.168.10.218）
        -> 跨服代理中转站（192.168.10.20:8280）
        -> 目标跨服游戏服（例如 10005）
```

故障期间，中转站上的代理程序实际在运行，但防火墙把游戏服发来的 `8280` 端口连接挡住了。于是请求到不了中转站，也就到不了目标跨服服务器。

这不是客户端、安卓/iOS、泰伯利亚资格校验或房间匹配的问题。

## 已验证的证据

| 检查项 | 结果 | 说明 |
| --- | --- | --- |
| 泰伯利亚源服校验 | 通过，`errorCode:0` | 玩家满足进入条件，目标服已选为 `10005`。 |
| CrossProxy 状态 | 持续 `RECOVERING` | 代理连接一直没有完成可用心跳验证。 |
| 代理心跳 | `0` 次有效心跳 | 源服没有收到代理的有效心跳。 |
| 源服到代理 TCP | 初始失败 | `192.168.10.218 -> 192.168.10.20:8280` 连接失败。 |
| Linux 代理进程 | 正常监听 | `proxyserver` 正在监听 `*:8280`。 |
| Linux 防火墙 | 未放行 `8280/tcp` | `public` 区域只显示 `22/tcp` 和 `6379/tcp`。 |
| 放行后 TCP | 成功 | 源服再次测试得到 `TcpTestSucceeded : True`。 |

故障日志中的代理地址为：

```text
tcp://192.168.10.20:8280
```

源服日志中的 `activeAddr`、`desiredAddr` 和 `proxyAddr` 都指向该地址，说明这是当时系统实际使用的线上跨服代理地址。

## 根因

代理机 `192.168.10.20` 是 Linux 服务器，代理程序已在监听 `8280`：

```text
LISTEN ... *:8280 ... proxyserver
```

但防火墙规则中没有允许游戏服访问 `8280/tcp`，并且未匹配流量会被后续 `REJECT` 规则拒绝。

因此会出现下面这种现象：

- 可以 Ping 到代理机，说明机器 IP 可达；
- 不能连接代理机的 `8280` 端口，说明具体服务端口被拦截；
- CrossProxy 收不到心跳，持续处于恢复状态；
- 泰伯利亚的跨服进入请求在源服被快速失败处理，不会到达目标服。

## 已执行修复

在 Linux 代理机的 `public` 防火墙区域中，仅放行源游戏服 `192.168.10.218` 到 `8280/tcp`：

```bash
sudo firewall-cmd --zone=public --add-rich-rule='rule family="ipv4" source address="192.168.10.218/32" port port="8280" protocol="tcp" accept'
sudo firewall-cmd --permanent --zone=public --add-rich-rule='rule family="ipv4" source address="192.168.10.218/32" port port="8280" protocol="tcp" accept'
sudo firewall-cmd --reload
```

放行后，源游戏服验证结果为：

```text
ComputerName     : 192.168.10.20
RemotePort       : 8280
SourceAddress    : 192.168.10.218
TcpTestSucceeded : True
```

这表示游戏服已经可以连接跨服代理端口。

## 后续验收

TCP 打通代表“道路已经通了”，但仍应确认跨服服务已经正常收发请求。

1. 等待约一分钟，让 CrossProxy 自动重连。
2. 在源游戏服的当前 `Server.log` 中确认：

   ```text
   state: HEALTHY
   csproxy heartbeat validated
   ```

3. 使用一个测试账号实际进入泰伯利亚跨服。
4. 确认不再出现：

   ```text
   tiberium prepare enter timeout
   csproxy send blocked, state: RECOVERING
   ```

当前排查时，`2026-08-19` 的 `D:\game\logs\Server.log` 还是空文件，尚未取得“防火墙放行后 CrossProxy 已进入 `HEALTHY`”的最新日志证据。因此，网络层已经修复并验证，业务进入成功仍需按上述步骤进行一次最终验收。

## 防止再次发生

1. 新建或迁移跨服代理机时，将所需游戏服来源 IP 到 `8280/tcp` 的防火墙放行纳入部署清单。
2. 不要直接把 `8280` 对全网开放；应只允许确认过的游戏服 IP 或受控网段访问。
3. 跨服发布后执行一次端口检查：

   ```powershell
   Test-NetConnection 192.168.10.20 -Port 8280
   ```

4. 使用 `Check-CrossProxyRuntime.ps1` 检查 TCP 连通、心跳和 CrossProxy 状态，避免只看客户端现象。
5. 代理机发现 `cs_proxy_router.pid` 与实际监听进程 PID 不一致。该问题不会直接阻断当前 TCP 连接，但建议后续修正代理进程的启动/守护脚本，避免运维脚本误判进程是否存活。

## 代码结论

本次直接故障由部署网络策略造成，不需要为了这个问题修改泰伯利亚业务代码或 Java 跨服状态机。

现有 CrossProxy 的 `RECOVERING` 和快速失败日志在本次排查中起到了保护和定位作用：它避免请求长期堆积，并明确暴露了代理连接不可用。网络恢复后，如最新日志仍无法进入 `HEALTHY`，再依据新日志继续排查代理协议、身份校验或心跳处理。
