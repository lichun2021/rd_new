# CrossProxy 热更新安全修复设计

## 目标

修复游戏服 `CrossProxy.activeZmq` 长期为空导致跨服请求无法发出的问题，同时补齐可定位主从选址状态的日志和失败监控。

## 约束

- 线上通过 `hotfixagent.jar` 调用 JVM `Instrumentation.redefineClasses()` 热更新。
- 立即生效的改动只能修改现有方法体；不新增或删除字段、方法、内部类，不修改方法签名。
- 保留现有 Redis 主从选址架构，从节点不能绕过 Redis 自行选择代理节点。
- 当前节点列表为空时不在运行中动态创建 ZMQ；记录明确错误，配置修正后重启。

## 修复方案

### 主节点地址发布

主节点在 `updateProxyState()` 选中代理节点时立即把地址写入 Redis，而不是等待代理心跳回复后才发布。这样从节点可以在下一轮心跳检查中选择同一代理地址，消除首次选址依赖心跳回复的启动死锁。

### 主节点存活判断

`ProxyHelper.isMasterAlive()` 只接受合理时间窗口内的心跳。Redis 心跳时间超过本机当前时间一个检测周期时判定无效，避免异常未来时间让旧 master 永久存活。

### 失败统计与状态日志

`sendProxyProtocol()` 在 `activeZmq == null` 时递增现有 `sendProtocolFailNum`，并输出目标、来源玩家、目标玩家、RPC ID。

`recordMonitor()` 每十秒追加：

- 当前 active 地址；
- Redis master；
- Redis proxy 地址；
- 本地节点数量；
- Redis 地址是否能匹配本地节点；
- 最近代理心跳年龄。

日志读取 Redis 失败时沿用现有异常捕获，不影响 CSProxy 主循环。

### 启动校验

`init()` 在正式服和 Debug 服都拒绝 `localNodeCount <= 0`，明确输出当前 `areaId` 和配置节点总数。该逻辑只在下次进程启动时执行。

## 验证

- 使用 `javap` 对比热更前后 `CrossProxy`、`ProxyHelper` 字段和方法签名，确保类结构不变。
- 编译修改后的类。
- 检查差异只涉及现有方法体。
- 热更后确认服务端输出 `hotfix success`。
- 十秒监控应显示完整选址状态；节点可用时 `activeAddr` 不再为空。
