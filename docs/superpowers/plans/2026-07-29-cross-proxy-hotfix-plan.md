# CrossProxy Hotfix Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 在不改变 Java 类结构的前提下修复 CrossProxy 主从地址发布和异常 master 心跳问题，并补齐无连接失败监控。

**Architecture:** 保留 Redis 选主和统一代理地址机制。所有立即生效的逻辑仅修改 `CrossProxy` 与 `ProxyHelper` 的现有方法体，确保标准 JVM `redefineClasses()` 可接受热更新。

**Tech Stack:** Java 8、ZeroMQ、Redis、Gradle、JVM Instrumentation HotSwap

## Global Constraints

- 不新增或删除字段、方法、内部类。
- 不修改任何方法签名。
- 从节点不得绕过 Redis 独立选取代理节点。
- 不覆盖现有未提交文件。

---

### Task 1: 固定热更新结构基线

**Files:**
- Inspect: `src/main/java/com/hawk/game/crossproxy/CrossProxy.java`
- Inspect: `src/main/java/com/hawk/game/crossproxy/ProxyHelper.java`

**Interfaces:**
- Consumes: 当前编译产物或当前源码定义的字段、方法签名。
- Produces: 修改前的 `javap -p` 结构基线。

- [ ] **Step 1: 记录两个类的字段和方法签名**

运行现有 Gradle 编译或使用已有 class 输出，执行：

```powershell
javap -p -classpath build/classes/java/main com.hawk.game.crossproxy.CrossProxy
javap -p -classpath build/classes/java/main com.hawk.game.crossproxy.ProxyHelper
```

- [ ] **Step 2: 确认基线不包含计划新增结构**

预期：后续实现不添加字段、方法或内部类，只改变 Code 属性。

### Task 2: 修复 master 心跳时间判断

**Files:**
- Modify: `src/main/java/com/hawk/game/crossproxy/ProxyHelper.java:192`

**Interfaces:**
- Consumes: `isMasterAlive()` 现有 Redis 心跳字符串和 `MASTER_CHECK_PERIOD`。
- Produces: 仅合理过去时间或一个周期内轻微未来时间被视为存活。

- [ ] **Step 1: 构造行为检查**

检查源码确认当前逻辑会把任意未来时间判断为存活：

```powershell
rg -n -C 8 "boolean isMasterAlive" src/main/java/com/hawk/game/crossproxy/ProxyHelper.java
```

- [ ] **Step 2: 修改现有方法体**

计算 `timeDiff = now - heartbeatTime`，只在 `timeDiff >= -MASTER_CHECK_PERIOD && timeDiff <= MASTER_CHECK_PERIOD` 时返回 `true`；超出范围记录告警并返回 `false`。

- [ ] **Step 3: 编译检查**

```powershell
./gradlew compileJava
```

预期：`ProxyHelper.java` 编译成功。

### Task 3: 修复代理地址发布和无连接监控

**Files:**
- Modify: `src/main/java/com/hawk/game/crossproxy/CrossProxy.java:184`
- Modify: `src/main/java/com/hawk/game/crossproxy/CrossProxy.java:466`
- Modify: `src/main/java/com/hawk/game/crossproxy/CrossProxy.java:510`
- Modify: `src/main/java/com/hawk/game/crossproxy/CrossProxy.java:533`

**Interfaces:**
- Consumes: 现有 `zmqList`、`activeZmq`、`sendProtocolFailNum`、`ProxyHelper` 接口。
- Produces: master 立即发布地址、空连接失败计数、十秒状态日志、重启时空节点强校验。

- [ ] **Step 1: 强化启动节点校验**

将 `localNodeCount <= 0` 的检查从 Debug 限制改为所有环境生效，日志包含 `areaId` 和 `nodeCount`，随后抛出原有运行时异常。

- [ ] **Step 2: 统计无连接发送失败**

在 `sendProxyProtocol()` 的 `activeZmq == null` 分支递增现有 `sendProtocolFailNum`，日志增加 `from/source/target/rpcid/localNodeCount`。

- [ ] **Step 3: master 选中节点时立即发布地址**

在 `updateProxyState()` 给 `activeZmq` 赋值后调用现有 `ProxyHelper.registerProxyNode(activeZmq.getAddress())`，再发送心跳。

- [ ] **Step 4: 输出从节点地址异常**

在从节点分支区分 Redis 地址为空和地址无法匹配本地 `zmqList`，使用现有十秒监控统一输出状态，不新增节流字段。

- [ ] **Step 5: 扩充十秒监控**

在 `recordMonitor()` 使用局部变量读取 master、Redis address、active address、本地节点数量和匹配结果，并追加最近心跳年龄。

- [ ] **Step 6: 编译检查**

```powershell
./gradlew compileJava
```

预期：`CrossProxy.java` 编译成功。

### Task 4: 验证热更新兼容性

**Files:**
- Verify: `build/classes/java/main/com/hawk/game/crossproxy/CrossProxy.class`
- Verify: `build/classes/java/main/com/hawk/game/crossproxy/ProxyHelper.class`

**Interfaces:**
- Consumes: 修改前结构基线和修改后 class。
- Produces: 可被标准 JVM `redefineClasses()` 接受的两个 class。

- [ ] **Step 1: 对比字段和方法结构**

```powershell
javap -p -classpath build/classes/java/main com.hawk.game.crossproxy.CrossProxy
javap -p -classpath build/classes/java/main com.hawk.game.crossproxy.ProxyHelper
```

预期：字段、方法和内部类集合与修改前一致。

- [ ] **Step 2: 检查源码差异**

```powershell
git diff --check
git diff -- src/main/java/com/hawk/game/crossproxy/CrossProxy.java src/main/java/com/hawk/game/crossproxy/ProxyHelper.java
```

预期：只有计划中的方法体变化，无空白错误。

- [ ] **Step 3: 生成热更新文件并核验**

使用项目现有热更新脚本生成两个 class，线上执行后必须在服务端输出中确认：

```text
hotfix success: hotfix/.../CrossProxy.class
hotfix success: hotfix/.../ProxyHelper.class
```

- [ ] **Step 4: 观察运行状态**

十秒内确认监控包含 `activeAddr/masterServer/proxyAddr/localNodeCount/addressMatched/keepaliveAge`。若 `localNodeCount` 为 0，修正配置并安排重启；否则应在 3～10 秒内完成地址选择。
