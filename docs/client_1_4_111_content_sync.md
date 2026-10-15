# 1.4.111 客户端内容同步（新英雄 / 装扮 / 梦幻岛建筑）

日期：2026-10-13。数据来源：1.4.111 客户端热更包 `update/loose/lua/*_conf.lua`（xLua 字节码，用客户端工程自带的 `lua.exe` 解出）。

## 改了什么

### 战斗代码（本轮新增）
| 文件 | 作用 |
|---|---|
| `player/hero/skill/Skill1125.java` | 墨明战技【金属狂潮】112501~112505，熟练度作用号 13008(受伤减少)/13009(造成伤害增加)，时长受魂技 13015 延长 |
| `player/hero/skill/Skill2002.java` | 谭雅战技【王牌特工】200201~200205，熟练度作用号 12911(自身数量最多的部队受击减伤)，时长受魂技 12924 延长 |
| `battle/effect/impl/hero2002/Checker12911.java` | 12911 取值器，`REDUCE_HURT_PCT`，作用于"出征数量最多的部队"（不限兵种，等价于【12841】去掉轰炸机限制） |
| `battle/effect/impl/hero2002/Checker12902.java` | 12902 谭雅【战术指挥】出征兵种只有1种时全兵种攻防血，`{ATK,DEF,HP}` |
| `battle/effect/impl/hero2002/Checker12905.java` | 12905 谭雅【功勋授予】按战力加成，`{ATK,DEF,HP}`：配置值 × min(战力,200000)/1000000 |
| `battle/effect/impl/hero1125/Checker13008.java` | 13008 受伤减少，`REDUCE_HURT_PCT` |
| `battle/effect/impl/hero1125/Checker13009.java` | 13009 造成伤害增加，`HURT_PCT` |
| `battle/effect/impl/hero1125/Checker13010.java` | 13010 军魂受伤减少，`REDUCE_HURT_PCT`，绑定战技作用号 13008 判定生效窗口 |
| `battle/effect/impl/hero1125/Checker13011.java` | 13011 军魂造成伤害增加，`HURT_PCT`，同上 |

两个 Skill 类继承 `ISSSHeroSkill`，作用值公式与已有英雄战技一致（`英雄军事值 * 系数`），`effectTime()` 加上魂技的时长延长。Checker 靠 `@EffectChecker` + `@BattleTupleType` 注解在 `CheckerFactory.init()` 扫描时自动注册，不需要额外登记。

**谭雅的实现依据（对上一版文档的修正）**：1.4.111 客户端**有**谭雅的完整技能设计文本 `HeroSkillDesWithValue200201`~`200205`，其 `{0}`~`{8}` 占位符与 `hero_skill` 的 `effectList` **按顺序一一对应**：

| 占位符 | 作用号 | 机制 | 状态 |
|---|---|---|---|
| {0} | 1980 | 部队超能攻击（芯片属性） | 原有作用号 |
| {1} | 12901 | 猎杀指令：每第5回合追加一次伤害，并按自身兵种类型触发额外效果 | 未实现（需每5回合追加攻击的战斗钩子） |
| {2} | 12902 | 战术指挥：出征兵种只有1种时，全兵种攻防血 | **已实现** |
| {3}/{4} | 12903/12904 | 集结时造成伤害增加/受到伤害减少（每兵种最多2层，总上限16层） | 未实现（配置系数为0，层数取值无来源） |
| {5} | 12905 | 功勋授予：全兵种攻防血 × 自身战力/100万，战力生效上限20万 | **已实现** |
| {6}/{7} | 12906/12907 | 意志强化：战斗开始时全兵种攻防血/受伤减少，逐回合衰减，第10回合后消失 | 未实现（文案未给衰减系数，const 表也无字段） |
| {8} | 12911 | 英雄战技：数量最多的部队受击减伤 | **已实现** |

> 上一版文档称"客户端只有短标签、拿不到触发条件"是**错的**。当时检索文案的正则带了 `(?!\d)`，把 `HeroSkillDesWithValue200201` 这类 6 位 key 全排除了；谭雅的文本一直都在。**墨明 1125 则确实没有**：无 `HeroSkillDesWithValue1125`，只有 `HeroSkillName1125`（金属狂潮）、`HeroRiseSkillName1125`（域·破晓）和短标签。
>
> 另注：谭雅的 `HeroSkillGrow200201` 与 `HeroProficiencyGrow200201` 内容是别的英雄的（文本里出现 尤利娅/屏障重构/维萝莎，系数 0.3~0.33333 与 1120 一致），**不可作为取值依据**，以 `hero_skill` 的配置为准。

**枚举命名陷阱（重要）**：`13008/13009/13010/13011/13015` 这些**数值**在服务器 `EffType` 里的**常量名是历史名**——`13008` 叫 `HERO_12731`、`13009` 叫 `HERO_12741`、`13010/13011` 叫 `HERO_12742/12743`、`13015` 叫 `HERO_12771`（当初为了避开 `EFF127xx` 冲突改的名，注释写在 `Const.proto` 里）。所以 Checker 注解必须写旧常量名，写 `EffType.HERO_13008` 会编译不过。类名仍按数值命名（先例：`Checker100` 对应 `WAR_TROOP_ATK_PER`）。以后加作用号同理：**先查数值对应的常量名**。

### 协议（Const.proto → protoc → gradle → gameprotocol.jar）
按项目方式重建，没有手工改 jar：改 `Code_Proto/1.4.109/Const.proto` → `protoc.exe --java_out` 重新生成 `Protobuf/Java/src/main/java` → `gradle -b Protobuf/Java/build.gradle clean jar` → 复制 `Protobuf/Java/build/libs/gameprotocol.jar`。

校验：135 个 proto 全部重新生成一遍，产物与源码树**逐字节一致**（135/135），说明 proto 源与部署 jar 同源；gradle 产物与 2026-09-17 部署版对比，仅 42 个 `Const*` 条目不同（19 个新枚举 + 编译元数据），其余 14865 个条目一致。`jar` 已同步到 `GameServer/lib`、`GameActivity/lib`、`GameRobot/lib`、`RaolRobot/lib`、`Code_Proto/GameServer/lib`。

`Const.proto` 的 `EffType` 新增 19 个值：

- 谭雅 2002：`HERO_12901`~`HERO_12907`、`HERO_12911`（战技熟练度）、`HERO_12921`~`HERO_12924`（魂技）
- 墨明 1125：`HERO_13022`（13005~13014 在旧枚举里已存在，名字是 HERO_12728/12729/12730/12731/12741/12742/12743/12746/12771）
- 梦幻岛建筑：`EFF_10101`、`EFF_10106`、`EFF_10114`、`EFF_10115`、`EFF_10116`、`EFF_10118`

不补这些值，`hero_soul_skill.xml` 加载时会报 `null key in entry`，服务器起不来（已用旧 jar 复现）。

### 配置（GameServer/xml，22 个文件；回收商店整表重写，另替换 4 行，其余只追加）
| 内容 | 表 |
|---|---|
| 英雄 1125 墨明、2002 谭雅 | hero / hero_skill / hero_star_level / hero_collect / hero_soul_stage / hero_soul_skill / hero_soul_level / hero_rise_node / hero_rise_skill(1125) / battle2025ValueHero / item(碎片+英雄卡) / effectid |
| 21 个新装扮 + 方舟之门数值更新 | world_dress_model / world_dress / world_dress_group(套装 79~87) / item(装扮券) |
| 太昊·羲皇天奢 + 形象 | world_highend_dress 1002 / skill 90003、90004 / player_show 701021、701022 |
| 梦幻岛建筑 1035~1054 | homeland_build_type / homeland_build / item(建筑道具) / homeland_recovery_shop 23~34 / award 6001003 |

替换的行：`world_dress_model` 534（方舟之门属性）；`item` 6001001/6001002（改为建筑道具）、6001003（改为宝箱，开出 60010031）。

`homeland_recovery_shop.xml` 整表按客户端重写（34 行）：1.4.111 客户端把原来的 1~22 号重新编号（价格和次数没变，只是 id 对应的建筑换了），又加了 23~34。客户端按 id 发兑换请求，服务器不跟着改就会换错东西。玩家已有的本周兑换次数是按 id 记的，周一会自动清零。

服务器专用列（客户端表没有）按现有行的规律补：
- 装扮：`atkAttrUse/hpAttrUse` 取 useAttribute 中 100/102 的值；城堡装扮 `atkAttrGain/hpAttrGain` 取 gainAttribute 中 1605 的值；`attrUseValue` 城堡 100、挂件 36
- 梦幻岛类型表：5 品质且带 11070 的 → shdAttr 200；4 品质首效果 100/102 → atkAttr/hpAttr 100；其余 0
- 套装：attrValue 36，atkAttr/hpAttr 取 effects 中 100 的值
- 英雄：maxMilitary / marchSelfAtkAttr / marchSelfHpAttr 同 1120（同为出征型、魂系统英雄）

## 已知限制（需要另外处理）

1. **两个英雄仍未实现的机制**（均已确认缺乏实现依据，不臆造）：
   - 谭雅：**12901 猎杀指令**（每第5回合追加一次攻击 + 按兵种类型的额外效果，需要战斗钩子，不是取值器能表达的）；**12903/12904 集结增减伤**（`effectList` 里系数为 0，层数取值与来源未给出）；**12906/12907 意志强化**（文案只写"每回合衰减、第10回合后消失"，未给衰减系数，`const.xml` 也无对应字段）；以及依赖它们的魂技 **12921~12923**。
   - 墨明：**13005 金属狂潮作战计划、13006 冲击伤害、13007 伤害降低、13014 坦克对决、13022 棱镜视觉受伤害系数**。客户端**没有** `HeroSkillDesWithValue1125`，只有 `HeroSkillName1125`（金属狂潮）和短标签，触发条件无从得知。
   - 需要的输入：策划的技能设计稿（谭雅那三项只需补"衰减系数/层数取值"，墨明需要完整设计），或官方服务端对应代码。
2. **梦幻岛 1036/1040/1043/1045/1048/1049/1050/1053 的新效果（10101 等）同样没有战斗实现**，只计入数值不生效；10084/10085 等老效果正常。
3. **墨明军魂的生效窗口是推断的。** 13010/13011 的短标签没写窗口，实现按"战技持续期间"处理（依据：1122 魂技 12993 短标签同样省略窗口、完整描述为"战技持续期间"）。若策划另有说明需同步调整，代码注释里标了依据。谭雅的 12902/12905 则无需推断——文案明确写了生效条件与公式。
4. **1121~1123 与新客户端的 ID 不一致。** 服务器 8 月按旧版客户端配的 1121~1123，在 1.4.111 客户端里整体往后挪了一位（服务器 1121 的技能 = 客户端 1122，服务器 1122 = 客户端 1123，服务器 1123 = 客户端 1124）。这次没有改动这三个英雄，需要单独决定怎么迁移。
5. 装扮券里客户端热更包没有的道具（月兔宫灯、爻定八方、称号、铭牌、签名、木德苍灵、琼华映庭、太昊羲皇、天穹铁骑），按服务器现有装扮券的统一格式补（itemType=141）。
6. 方舟之门 534 在客户端标记了 `isOpenByServer=1`，服务器 const.xml `honorSevenOpen=1` 已打开，不需要改。
7. 墨明 1125 有觉醒节点和觉醒技能，但客户端热更包里没有他的觉醒芯片（hero_rise_fragments），所以服务器也没配。节点解锁后无法升级芯片，下一个节点也解不开，不会报错，只是走不下去。拿到完整客户端表后补上即可。
8. 梦幻岛卡池：客户端第 4 档普通池引用了新奖池 14（award 2540064），服务器卡池表和奖励都没跟，保持旧卡池不变（不影响建筑获取，新建筑走回收商店）。

## 验证

- `src/test/python/test_client_1_4_111_config.py`（新增）：英雄行数、装扮/套装/天奢/形象、梦幻岛建筑与道具引用闭环。
- `src/test/python/test_new_hero_skill_runtime.py`（新增）：两个 Skill 类的 skillID 与魂技时长作用号、7 个 Checker 的元组与枚举常量名、未实现作用号不得出现、谭雅 `effectList` 顺序与客户端文案 `{0}~{7}` 一致，并**真编译 + 真跑 `CheckerFactory.init()`/`HeroSkillFactory`** 确认注册成功（含旧英雄未被影响）。
- `src/test/python/test_hero_protocol_artifact.py`：补了 19 个新枚举的检查；对旧 jar 运行会失败，对新 jar 通过。
- 用服务器自己的配置类（HawkConfigStorage + 各 Cfg 的 assemble/checkValid）加载上述 26 张表，全部通过；旧 jar 下 HeroSoulSkillCfg 加载失败。
- 用 gradle 构建的 jar 编译 GameServer 全部 5947 个源文件通过。

## 回滚

改动前的 22 个 xml、Const.proto、Const.java、旧 gameprotocol.jar 备份在 `D:\projects\rd_new\client_diff_1.4.111\backup_20261013_2227\`。本轮新增的 Java 文件直接删掉即可回滚代码部分。

## 2026-10-14 增量合入：欧米伽系统 + 10 个自定义称号槽位

在不覆盖已有 1.4.111 新英雄/装扮/梦幻岛改动的前提下，从增量包 `rd_new_server_full_omega_custom_title_20261014` 合并了欧米伽系统与 10 个自定义称号槽位，并修正了增量包中的问题：

1. **协议（Code_Proto/1.4.109 → protoc → gradle → gameprotocol.jar）**：
   - `Const.proto`：叠加 `EFF_2221 = 2221`、`EFF_2222 = 2222`、`EFF_2223 = 2223`（完整保留原 19 个 1.4.111 新增枚举及 `HERO_12731` 等历史常量名）。
   - `HP.proto`：新增 `MANHATTAN_OMEGA_LEVEL_UP_C = 203009`、`MANHATTAN_EXCHANGE_C = 203010`。
   - `Status.proto`：新增 `MANHATTAN_OMEGA_NOT_EXIST = 32951`、`MANHATTAN_OMEGA_MAX_LEVEL = 32952`、`MANHATTAN_OMEGA_COND_LIMIT = 32953`。
   - `Manhattan.proto`：`ManhattanInfoSync` 新增 `omegaUnlocked = 3`、`PBManhattanInfo` 新增 `omegaPosLevels = 5`、新增 `ManhattanOmegaLevelUpReq`、`ManhattanSWUnlockReq` 新增 `count = 2`。
2. **欧米伽配置与修正**：
   - `xml/manhattan_omega_level.xml`：3 个模块 × 0~150 级共 453 行。
   - `xml/manhattan_omega_skill.xml`：修正 `901` 行 `unlockLevel=""` 导致的启动 `NumberFormatException`（改为 `unlockLevel="0"`）；按客户端文案修正配反的作用号（`101~105` 机械誓约 → `2222_*` 前20回合受伤减少，`201~205` 基因进化 → `2223_*` 每5回合造成伤害增加，`301~305` 灵能共振 → `2221_*` 前20回合造成伤害增加）。
   - `xml/manhattan_sw_skill.xml`：补充 `unlockOmega="901"`、`unlockOmegaStage` 列及 6 阶技能行（共 90 行）。
   - `xml/effectid.xml`：补充 `2221`、`2222`、`2223`。
   - `xml/item.xml`：补充欧米伽蓝图 `9990043` 及配套道具 `603401`、`603402`、`840395`。
3. **欧米伽战斗检查器与运行时逻辑**：
   - `Checker2221` / `Checker2222` / `Checker2223`：改为 `@BattleTupleType(tuple = Type.SOLDIER_SKILL)`，将回合数判断移入 `BattleSoldier.addHurtValPct`（`EFF_2221`、`EFF_2223`）与 `BattleSoldier.reduceHurtValPct`（`EFF_2222`），修复原增量包在建兵阶段调用 `parames.solider.getBattleRound()` 必抛 `NullPointerException` 的问题。
   - `ManhattanBase` / `ManhattanSW` / `ManhattanEntity` / `PlayerManhattanModule` / `ManhattanSWSkillCfg`：修正 `ManhattanBase.init` 先加载 `omegaPosLevelMap` 再调用 `super.init`，补充 `ManhattanSW.getUnlockedSkillCfg` 的 `getParent()` 判空、`PlayerManhattanModule.onManhattanOmegaLevelUp` 的建筑前置校验，以及 `ManhattanOmegaSkillCfg` / `ManhattanSWSkillCfg` 热更去重。
   - `sql/game_new.sql` 与 `sql/update/20261013_manhattan_omega.sql`：`manhattan` 表新增 `omegaPosLevel` 列。
4. **10 个自定义称号槽位（追加，不覆盖现有装扮）**：
   - `world_dress_model.xml`：追加 `dressId="1057"`~`"1066"`（`dressType="7"`，`modelType="344"`~`"353"`，`gainAttribute="201_20000,4101_20000"`）。
   - `world_dress.xml`：追加 `1057000`~`1066000`。
   - `item.xml`：追加 `13057000`~`13066000`。

## 2026-10-15 全量补齐：1121~1124 ID 对齐 + 墨明/谭雅/参谋英雄全部技能与作用号

1. **1121~1124 与客户端 1.4.111 完全对齐**：
   - `1121` = **莱因**（坦克参谋英雄，被动 `112101~112105`，作用号 `12881~12884`）。
   - `1122` = **艾伦**（近战/远程参谋英雄，被动 `112201~112205`，作用号 `12941~12944`）。
   - `1123` = **莱克莎**（直升机出征英雄，含军魂，被动 `112301~112305`，作用号 `12961~12970`、战技 `12981`、魂技 `12991~12994`）。
   - `1124` = **雪拉**（空军参谋英雄，被动 `112401~112405`，作用号 `12995~12997`）。
   - 同步更新 `hero.xml`、`hero_skill.xml`、`hero_star_level.xml`、`hero_collect.xml`、`hero_soul_stage.xml`、`hero_soul_skill.xml`、`hero_soul_level.xml`、`hero_rise_node.xml`、`hero_rise_skill.xml`、`hero_rise_fragments.xml`（含 `1121~1125` 全部 1000 行觉醒芯片）、`battle2025ValueHero.xml`、`item.xml`（新增 `1001124`、`1101124`）、`effectid.xml`（新增 `12881~12884`），并配套存量迁移脚本 `sql/update/20261015_hero_1121_1124_align.sql`。
2. **全部缺失技能与 Checker 补齐**：
   - **墨明 (`1125`)**：补齐 `Checker13005`（`HERO_12728`）、`Checker13006`（`HERO_12729` + `BattleSoldier_2.sss12131Atk` 冲击伤害）、`Checker13007`（`HERO_12730`）、`Checker13014`（`HERO_12746` + `BattleSoldier_2` 坦克对决触发）、`Checker13022`（`HERO_13022` + `Hero1116Param.skill12722Atk` 棱镜视觉减伤）。
   - **谭雅 (`2002`)**：补齐 `Checker12901` + `Checker12921`（猎杀指令 + 定点爆破，由 `BattleSoldier.hero2002HuntOrder` 每 5 回合触发追加攻击及兵种专属增益）、`Checker12903` + `Checker12904`（战术指挥集结增减伤）、`Checker12906` + `Checker12907` + `Checker12923`（意志强化 + 特工网络，拥有即生效且前 10 回合线性衰减）、`Checker12922`（特工小队单兵种出征攻防血额外加成）。
   - **参谋英雄 (`1121` 莱因 / `1122` 艾伦 / `1124` 雪拉)**：补齐 `Skill1121`~`Skill1124`、`Checker12881~12884`（含 `BattleSoldier.skillAtkExactly` 敌方最多兵种锁定加成）、`Checker12941~12944`、`Checker12995~12997`（含 `BattleSoldier_4.attackOver` 蜂群链路轰炸机支援攻击）。
