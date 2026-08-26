local const_conf = {
-- 新手初始化部队（id_数量,id_数量,id_数量）
['newSoldier'] ={
     key = 'newSoldier',
     value = '100101_10'
},
-- vip0级时的免费编队个数（现版本与vip无关，只读取此值）
['iniTroopTeamNum'] ={
     key = 'iniTroopTeamNum',
     value = 4
},
-- 【超级武器】底座升级的模块数量
['manhattanBaseModuleCount'] ={
     key = 'manhattanBaseModuleCount',
     value = 3
},
-- 【超级武器】功能解锁所需大本等级；绝对值
['manhattanUnlockLevel'] ={
     key = 'manhattanUnlockLevel',
     value = 30
},
-- 每日购买体力上限次数
['dailyBuyEnergyTimesLimit'] ={
     key = 'dailyBuyEnergyTimesLimit',
     value = 10
},
-- 解锁天赋路线2所需大本等级
['unlockTalentLine2NeedCityLevel'] ={
     key = 'unlockTalentLine2NeedCityLevel',
     value = 0
},
-- 解锁天赋路线3所需大本等级
['unlockTalentLine3NeedCityLevel'] ={
     key = 'unlockTalentLine3NeedCityLevel',
     value = 10
},
-- 解锁天赋路线4所需大本等级
['unlockTalentLine4NeedCityLevel'] ={
     key = 'unlockTalentLine4NeedCityLevel',
     value = 10
},
-- 解锁天赋路线5所需大本等级
['unlockTalentLine5NeedCityLevel'] ={
     key = 'unlockTalentLine5NeedCityLevel',
     value = 10
},
-- 解锁天赋路线6所需大本等级
['unlockTalentLine6NeedCityLevel'] ={
     key = 'unlockTalentLine6NeedCityLevel',
     value = 10
},
-- 解锁天赋路线7所需大本等级
['unlockTalentLine7NeedCityLevel'] ={
     key = 'unlockTalentLine7NeedCityLevel',
     value = 10
},
-- 解锁天赋路线8所需大本等级
['unlockTalentLine8NeedCityLevel'] ={
     key = 'unlockTalentLine8NeedCityLevel',
     value = 10
},
-- 批量招募英雄需求的道具数量阈值
['batchHeroThreshold'] ={
     key = 'batchHeroThreshold',
     value = 200
},
-- 批量招募英雄的次数上限
['batchHeroMax'] ={
     key = 'batchHeroMax',
     value = 100
},
-- 批量研究芯片需求的道具数量阈值
['batchChipThreshold'] ={
     key = 'batchChipThreshold',
     value = 200
},
-- 批量研究芯片的次数上限
['batchChipMax'] ={
     key = 'batchChipMax',
     value = 100
},
-- 批量打造装备需求的道具数量阈值
['batchEquipmentThreshold'] ={
     key = 'batchEquipmentThreshold',
     value = 200
},
-- 批量打造装备的次数上限
['batchEquipmentMax'] ={
     key = 'batchEquipmentMax',
     value = 100
},
-- 统帅之战-国家医院显示2个页签的开始时间
['showTwoStartTime'] ={
     key = 'showTwoStartTime',
     value = 1767830400
},
-- 统帅之战-国家医院显示2个页签的结束时间
['showTwoEndTime'] ={
     key = 'showTwoEndTime',
     value = 1770998399
},
-- 统帅之战预热拍脸图结束时间
['bannerShowEndTime'] ={
     key = 'bannerShowEndTime',
     value = 1769011199
},
-- 霸主赐福活动行军位置：即雕像的位置
['statueCoordinates'] ={
     key = 'statueCoordinates',
     value = '386_114'
},
-- 月球之巅可以使用的技能id(多个技能用英文,分隔)
['yqzzCanUseSkill'] ={
     key = 'yqzzCanUseSkill',
     value = 10102
},
-- 进入战场的随机时间最小值
['YQZZ_Min_Enter_Time'] ={
     key = 'YQZZ_Min_Enter_Time',
     value = 3
},
-- 进入战场的随机时间最大值
['YQZZ_Max_EnterTime'] ={
     key = 'YQZZ_Max_EnterTime',
     value = 30
},
-- 泰伯光环赛季拍脸图弹出开始时间
['TbbannerShowStartTime'] ={
     key = 'TbbannerShowStartTime',
     value = 1773849600
},
-- 泰伯光环赛季拍脸图弹出结束时间
['TbbannerShowEndTime'] ={
     key = 'TbbannerShowEndTime',
     value = 1774281599
},
-- 基地大于等于此等级，才可见主界面战备册按钮
['warBookCityLevel'] ={
     key = 'warBookCityLevel',
     value = 30
},
-- VIP大于等于此等级，才可见主界面战备册按钮
['warBookVipLevel'] ={
     key = 'warBookVipLevel',
     value = 9
},
-- 玩家每日参与185入侵Boss最大收益次数
['bossDailyLootTimeLimit185'] ={
     key = 'bossDailyLootTimeLimit185',
     value = '50;50'
},
-- 185入侵Boss的enemyID列表
['bossEnemyIdList185'] ={
     key = 'bossEnemyIdList185',
     value = '601001_601002_601003_602001_602002_602003;603001_603002_603003'
},
-- 184入侵Boss的enemyID列表
['bossEnemyIdList184'] ={
     key = 'bossEnemyIdList184',
     value = '600001_600002_600003'
},
-- 黑市商店每日系统刷新时间整点
['travelShopRefreshTime'] ={
     key = 'travelShopRefreshTime',
     value = '9_12_18_21'
},
-- 系统刷新后，重置的免费刷新次数
['travelShopFreeRefreshTimes'] ={
     key = 'travelShopFreeRefreshTimes',
     value = 1
},
-- 系统刷新后，重置的水晶刷新次数和消耗
['travelShopCrystalRefreshCost'] ={
     key = 'travelShopCrystalRefreshCost',
     value = '25_50_75_100_125'
},
-- 英雄星穹觉醒最大生效英雄数量
['heroRiseNum'] ={
     key = 'heroRiseNum',
     value = 7
},
-- 盟军医院死兵弹出道具id
['injuredSoldierGiftTipsItem'] ={
     key = 'injuredSoldierGiftTipsItem',
     value = 500203
},
-- 物资箱id列表
['resourceBoxIdList'] ={
     key = 'resourceBoxIdList',
     value = '840000_840001_840002_840003'
},
-- 迷雾要塞每日奖励次数
['foggyAttackMaxTimes'] ={
     key = 'foggyAttackMaxTimes',
     value = 3
},
-- 新手保护时间
['newProtectTime'] ={
     key = 'newProtectTime',
     value = 172800
},
-- 新手默认体力
['mewVitPoint'] ={
     key = 'mewVitPoint',
     value = 100
},
-- 造兵上限初始值
['newTrainQuantity'] ={
     key = 'newTrainQuantity',
     value = 10
},
-- 玩家名称最短最长（字符数）
['playerNameMinMax'] ={
     key = 'playerNameMinMax',
     value = '4_14'
},
-- 登录获得vip积分（未激活vip时获得积分_激活vip时倍数）
['loginVipPointAdd'] ={
     key = 'loginVipPointAdd',
     value = '0_2'
},
-- X的视野半径（坐标）
['viewRadiusX'] ={
     key = 'viewRadiusX',
     value = 5
},
-- Y的视野半径（坐标）
['viewRadiusY'] ={
     key = 'viewRadiusY',
     value = 13
},
-- 前端删除行军时，根据广播格子放大倍数（用前除以100）
['broadCastScaleForDel'] ={
     key = 'broadCastScaleForDel',
     value = 120
},
-- 城点最小间隔坐标(x,y)
['worldPlayerMinRange'] ={
     key = 'worldPlayerMinRange',
     value = '5_5'
},
-- 默认行军速度（行进坐标/秒）（标准为5秒走1坐标）（用前除1000）
['worldMarchBaseVelocity'] ={
     key = 'worldMarchBaseVelocity',
     value = 200
},
-- 目标为1x1格子的时候，前端提前行军距离减少值（用于散列动画）（单位像素）
['WorldMarchPreStopDistance_1'] ={
     key = 'WorldMarchPreStopDistance_1',
     value = 80
},
-- 目标为2x2格子的时候，前端提前行军距离减少值（用于散列动画）（单位像素）
['WorldMarchPreStopDistance_2'] ={
     key = 'WorldMarchPreStopDistance_2',
     value = 140
},
-- 世界上城点等级牌坐标
['worldCityLevelXy'] ={
     key = 'worldCityLevelXy',
     value = '-160_96'
},
-- 连续打野怪需满足vip等级
['atkEnemyContinuityVip'] ={
     key = 'atkEnemyContinuityVip',
     value = 4
},
-- 攻打野怪获得英雄经验图标
['atkEnemyHeroExpIcon'] ={
     key = 'atkEnemyHeroExpIcon',
     value = 'HeroExp.png'
},
-- 免费时间（秒）
['freeTime'] ={
     key = 'freeTime',
     value = 420
},
-- 使用道具加速时，决定推荐数量取整方式的时间阈值，单位：秒
['itemSpeedUpTimeThresholdValue'] ={
     key = 'itemSpeedUpTimeThresholdValue',
     value = 300
},
-- 玩家主城最大座标
['maxCoordinate'] ={
     key = 'maxCoordinate',
     value = '31_63'
},
-- 建筑物最大等级
['build_limit_max_level'] ={
     key = 'build_limit_max_level',
     value = 30
},
-- 首次试用建造队列（单位：小时）
['firstFreeBuildQueue'] ={
     key = 'firstFreeBuildQueue',
     value = 8
},
-- 购买建造队列
['buyBuildQueue'] ={
     key = 'buyBuildQueue',
     value = '10000_1001_50_172800'
},
-- 兵种头顶可领取奖励的气泡(气泡类型1;间隔时间;持续时间;资源类型_资源ID_资源数量;每日最大领取次数上限）
['rondaAwardBubble'] ={
     key = 'rondaAwardBubble',
     value = '1;600;180;10000_1008_1000,10000_1007_1000;20'
},
-- 建筑头顶气泡(气泡类型2;间隔时间;持续时间;资源类型_资源ID_资源数量;每日最大领取次数上限;引导半身像姓名;引导半身像资源;引导半身像头像资源;尤里兵死亡时间）
['buildAwardBubble'] ={
     key = 'buildAwardBubble',
     value = '2;600;180;10000_1008_1000,10000_1007_1000;10;@GuideName20000080;V3Guide_Single_TanYa;Hero_HUD_TanYa.png;9'
},
-- 建筑头顶谭雅气泡出现条件（t填写大本等级即可）
['buildAwardBubbleLimit'] ={
     key = 'buildAwardBubbleLimit',
     value = 31
},
-- 免费赠送的超武（超武引导默认定位）
['freeManhattan'] ={
     key = 'freeManhattan',
     value = 2001
},
-- 供电吃紧的门槛（电力占用/电力供应）（百分比）
['electric_cap1'] ={
     key = 'electric_cap1',
     value = 100
},
-- 供电吃紧时，队列的减速％
['electric_decrease1'] ={
     key = 'electric_decrease1',
     value = 20
},
-- 供电不足的门槛（电力占用/电力供应）（百分比）
['electric_cap2'] ={
     key = 'electric_cap2',
     value = 150
},
-- 供电不足时，队列的减速％
['electric_decrease2'] ={
     key = 'electric_decrease2',
     value = 50
},
-- 水晶秒时间通用算式：lower1_k1_b1,lower2_k2_b2……（分段线性公式）
['speedUpCost'] ={
     key = 'speedUpCost',
     value = '0_10000_1000000,300_10910_727000,3600_9723_4997201,7200_8565_13332000,28800_7987_29974400,43200_7524_49963200,86400_7147_82499200'
},
-- 城建时间价值水晶的权重
['buildingTimeWeight'] ={
     key = 'buildingTimeWeight',
     value = 1
},
-- 造兵时间价值水晶的权重
['trainSoldierTimeWeight'] ={
     key = 'trainSoldierTimeWeight',
     value = 1
},
-- 部队治疗时间价值水晶的权重
['cureSoldierTimeWeight'] ={
     key = 'cureSoldierTimeWeight',
     value = 1
},
-- 科技研究时间价值水晶的权重
['techResearchTimeWeight'] ={
     key = 'techResearchTimeWeight',
     value = 1
},
-- 英雄训练时间价值水晶的权重
['trainHeroTimeWeight'] ={
     key = 'trainHeroTimeWeight',
     value = 0.1
},
-- 英雄治疗时间价值水晶的权重
['cureHeroTimeWeight'] ={
     key = 'cureHeroTimeWeight',
     value = 1
},
-- 宝藏加速时间系数
['speedUpCoefficient'] ={
     key = 'speedUpCoefficient',
     value = 1
},
-- 装备打造、升品、拆解时间价值水晶的权重
['equipQueueTimeWeight'] ={
     key = 'equipQueueTimeWeight',
     value = 1
},
-- 装备研究时间价值水晶的权重
['equipResearchQueueTimeWeight'] ={
     key = 'equipResearchQueueTimeWeight',
     value = 1
},
-- 水晶秒资源通用算式：lower1_k1_b1,lower2_k2_b2……（分段线性公式）
['buyResCost'] ={
     key = 'buyResCost',
     value = '0_300_1000000,10000_300_1000000,50000_300_1000000,150000_300_1000000,500000_270_16000000,1500000_240_61000000,5000000_210_211000000'
},
-- 黄金资源价值水晶的权重
['goldResWeight'] ={
     key = 'goldResWeight',
     value = 1
},
-- 石油资源价值水晶的权重
['oilResWeight'] ={
     key = 'oilResWeight',
     value = 1
},
-- 合金资源价值水晶的权重
['soilResWeight'] ={
     key = 'soilResWeight',
     value = 6
},
-- 铀矿资源价值水晶的权重
['uraniumResWeight'] ={
     key = 'uraniumResWeight',
     value = 24
},
-- x的距离
['techUI_X'] ={
     key = 'techUI_X',
     value = 100
},
-- y的距离
['techUI_Y'] ={
     key = 'techUI_Y',
     value = 145
},
-- 首次加入联盟奖励
['firstJoinGuildReward'] ={
     key = 'firstJoinGuildReward',
     value = '10000_1001_200,30000_800134_1'
},
-- 搜索输入最短限制
['searchWordMinimum'] ={
     key = 'searchWordMinimum',
     value = 4
},
-- 战斗最大回合数（标准为50）
['battleRoundMax'] ={
     key = 'battleRoundMax',
     value = 50
},
-- 掠夺系数（用前除1000）
['grabWeightKey'] ={
     key = 'grabWeightKey',
     value = 1000
},
-- 士兵属性最大（血_攻_防）（用于士兵训练界面显示）
['soldierPropertyMax'] ={
     key = 'soldierPropertyMax',
     value = '72000_3500_300'
},
-- PVE星级评价，损失人口/出征人口上限
['pveStarEva'] ={
     key = 'pveStarEva',
     value = '60_40'
},
-- 造兵界面中兵种攻击进度的上限数值
['soldierAtkMaxForShow'] ={
     key = 'soldierAtkMaxForShow',
     value = 803
},
-- 造兵界面中兵种防御进度的上限数值
['soldierDefMaxForShow'] ={
     key = 'soldierDefMaxForShow',
     value = 564
},
-- 造兵界面中兵种生命进度的上限数值
['soldierHpMaxForShow'] ={
     key = 'soldierHpMaxForShow',
     value = 234
},
-- 选中建筑的灰暗程度
['buildingShadowValue'] ={
     key = 'buildingShadowValue',
     value = 160
},
-- 基地沦陷之后，损毁建筑物的修理所需时间（秒）
['repairTime'] ={
     key = 'repairTime',
     value = 15
},
-- 长按移动建筑规则内，显示计时器前所需时间
['moveBuildTimerBefore'] ={
     key = 'moveBuildTimerBefore',
     value = 25
},
-- 长按移动建筑规则内，计时器时间
['moveBuildTimer'] ={
     key = 'moveBuildTimer',
     value = 25
},
-- 复活俘虏所需钻石（前端用）
['rebornGold'] ={
     key = 'rebornGold',
     value = 500
},
-- 装备最大等级（前端用）
['EquipMaxLevel'] ={
     key = 'EquipMaxLevel',
     value = 50
},
-- 列兵区上限根据大本等级调整
['troopsDisplayLimit'] ={
     key = 'troopsDisplayLimit',
     value = '1_320000,2_320000,3_320000,4_320000,5_320000,6_320000,7_320000,8_320000,9_320000,10_320000,11_320000,12_320000,13_320000,14_320000,15_320000,16_320000,17_320000,18_320000,19_320000,20_320000,21_320000,22_320000,23_320000,24_320000,25_320000,26_320000,27_320000,28_320000,29_320000,30_320000,31_320000,32_320000,33_320000,34_320000,35_320000,36_320000,37_320000,38_320000,39_320000,40_320000,41_320000,42_320000,43_320000,44_320000,45_320000'
},
-- 兵种头顶聊天气泡(气泡类型3;步兵组或坦克组ID;间隔时间;持续时间;@字典1,@字典2）
['chitchatBubble'] ={
     key = 'chitchatBubble',
     value = '3;1;600;15;@cBubble1-1,@cBubble1-2,@cBubble1-3,@cBubble1-4,@cBubble1-5'
},
-- 坦克头顶聊天气泡(气泡类型3;步兵组或坦克组ID;间隔时间;持续时间;@字典1,@字典2）
['chitchatBubbleTank'] ={
     key = 'chitchatBubbleTank',
     value = '3;2;600;15;@cBubble2-1,@cBubble2-2,@cBubble2-3,@cBubble2-4,@cBubble2-5'
},
-- 免费时间倒计时(秒)
['FreeTimeCountDown'] ={
     key = 'FreeTimeCountDown',
     value = 600
},
-- 天赋升级亮线（不要修改，程序用）
['TalentRouteTurnOnLevel'] ={
     key = 'TalentRouteTurnOnLevel',
     value = 600
},
-- 新手期雷达视角俯冲时间
['GuideToRadarTime'] ={
     key = 'GuideToRadarTime',
     value = 0.7
},
-- 新手期第一次造兵数量
['GuideFirstTrainSoldierCount'] ={
     key = 'GuideFirstTrainSoldierCount',
     value = 5
},
-- 新手期普通造兵时间
['GuideFirstTrainSoldierTime'] ={
     key = 'GuideFirstTrainSoldierTime',
     value = 3
},
-- 雷达时间俯冲程度
['GuideToRadarScale'] ={
     key = 'GuideToRadarScale',
     value = 0.7
},
-- 新手背景fade时间
['GuideBGFadeTime'] ={
     key = 'GuideBGFadeTime',
     value = 1.5
},
-- 受新手影响，出征时，主基地的等级限制
['GuideMarchMainCityLevel'] ={
     key = 'GuideMarchMainCityLevel',
     value = 3
},
-- 受新手影响，主基地立即升级的等级限制
['GuideUpgradeImmMainCityLevel'] ={
     key = 'GuideUpgradeImmMainCityLevel',
     value = 3
},
-- 新手怪的战斗力
['GuideMonsterPower'] ={
     key = 'GuideMonsterPower',
     value = 480
},
-- 邮件上限
['mailLimit'] ={
     key = 'mailLimit',
     value = 50
},
-- 邮件生存时间
['mailEffectTime '] ={
     key = 'mailEffectTime ',
     value = 1296000
},
-- 任意一代机甲达到X阶，开启熔炼功能
['mechaSmeltingRequire'] ={
     key = 'mechaSmeltingRequire',
     value = 6
},
-- 防御建筑固定台座
['defenceBuilding'] ={
     key = 'defenceBuilding',
     value = '1_0_32,2_2_28,3_4_24,4_6_20,5_9_13,6_11_9,7_13_5,8_15_1,9_18_6,10_20_11,11_22_15,12_26_22,13_27_25,14_29_28,15_31_32,16_29_35,17_28_38,18_19_55,19_17_59,20_15_63,21_12_57,22_10_52,23_7_47,24_5_42,25_2_37'
},
-- 小助手随机条目数
['HelperTipsNum'] ={
     key = 'HelperTipsNum',
     value = 16
},
-- 排行榜显示最大条目数
['rankMaxCount'] ={
     key = 'rankMaxCount',
     value = 100
},
-- 去兵战力排行榜开关，1开，0关
['noSoldierRank'] ={
     key = 'noSoldierRank',
     value = 1
},
-- 若排名大于配置数，不再显示具体排名
['clearRankData'] ={
     key = 'clearRankData',
     value = 1000
},
-- 英雄训练可用分钟数
['heroTrainMinute'] ={
     key = 'heroTrainMinute',
     value = '60,240,480,4320'
},
-- 英雄血条显示颜色百分比
['heroHpColorPercent'] ={
     key = 'heroHpColorPercent',
     value = '80,50'
},
-- 英雄最大等级
['heroMaxLevel'] ={
     key = 'heroMaxLevel',
     value = 60
},
-- 英雄技能奖励界面最大停留时间（毫秒）
['heroSkillAwardUiTime'] ={
     key = 'heroSkillAwardUiTime',
     value = 5000
},
-- 耗电量
['electricConsume'] ={
     key = 'electricConsume',
     value = 'CommonSmallIcon_PowerConsumption.png'
},
-- 训练加速
['trainSpeed'] ={
     key = 'trainSpeed',
     value = 'CommonSmallIcon_Train_SpeedUp.png'
},
-- 总训练加速
['totalTrainSpeed'] ={
     key = 'totalTrainSpeed',
     value = 'CommonSmallIcon_Train_SpeedUp.png'
},
-- 受援助次数
['assistLimit'] ={
     key = 'assistLimit',
     value = 'CommonSmallIcon_Help.png'
},
-- 援助减少时间
['assistTime'] ={
     key = 'assistTime',
     value = 'CommonSmallIcon_Help.png'
},
-- 援军上限
['assistUnitLimit'] ={
     key = 'assistUnitLimit',
     value = 'CommonSmallIcon_Help.png'
},
-- 市场负重
['marketBurden'] ={
     key = 'marketBurden',
     value = 'CommonSmallIcon_MarketLoad.png'
},
-- 市场税率
['marketTax'] ={
     key = 'marketTax',
     value = 'CommonSmallIcon_Tax.png'
},
-- 集结上限
['buildupLimit'] ={
     key = 'buildupLimit',
     value = 'CommonSmallIcon_Gather_Max.png'
},
-- 训练上限
['trainQuantity'] ={
     key = 'trainQuantity',
     value = 'CommonSmallIcon_Train_Max.png'
},
-- 单次训练部队总上限（复制中心基值和值）
['totalTrainQuantity'] ={
     key = 'totalTrainQuantity',
     value = 'CommonSmallIcon_Train_Max.png'
},
-- 行军上限
['attackUnitLimit'] ={
     key = 'attackUnitLimit',
     value = 'CommonSmallIcon_March_Max.png'
},
-- 黄金保护
['resProtectA'] ={
     key = 'resProtectA',
     value = 'V3CommonUI_Icon_Gold_02.png'
},
-- 石油保护
['resProtectB'] ={
     key = 'resProtectB',
     value = 'V3CommonUI_Icon_Petroleum_02.png'
},
-- 合金保护
['resProtectD'] ={
     key = 'resProtectD',
     value = 'V3CommonUI_Icon_Steel_02.png'
},
-- 铀矿保护
['resProtectC'] ={
     key = 'resProtectC',
     value = 'V3CommonUI_Icon_Uranium_02.png'
},
-- 供电量
['electricGenerate'] ={
     key = 'electricGenerate',
     value = 'CommonSmallIcon_PowerConsumption.png'
},
-- 伤兵上限
['woundedLimit'] ={
     key = 'woundedLimit',
     value = 'CommonSmallIcon_HEAL_Max.png'
},
-- 总伤兵上限
['totalWoundedLimit'] ={
     key = 'totalWoundedLimit',
     value = 'CommonSmallIcon_HEAL_Max.png'
},
-- 黄金产量/小时
['resPerHourA'] ={
     key = 'resPerHourA',
     value = 'V3CommonUI_Icon_Gold_02.png'
},
-- 黄金总产量/小时
['totalResOutPutA'] ={
     key = 'totalResOutPutA',
     value = 'V3CommonUI_Icon_Gold_02.png'
},
-- 石油产量/小时
['resPerHourB'] ={
     key = 'resPerHourB',
     value = 'V3CommonUI_Icon_Petroleum_02.png'
},
-- 石油总产量/小时
['totalResOutPutB'] ={
     key = 'totalResOutPutB',
     value = 'V3CommonUI_Icon_Petroleum_02.png'
},
-- 合金产量/小时
['resPerHourD'] ={
     key = 'resPerHourD',
     value = 'V3CommonUI_Icon_Steel_02.png'
},
-- 合金总产量/小时
['totalResOutPutD'] ={
     key = 'totalResOutPutD',
     value = 'V3CommonUI_Icon_Steel_02.png'
},
-- 铀矿产量/小时
['resPerHourC'] ={
     key = 'resPerHourC',
     value = 'V3CommonUI_Icon_Uranium_02.png'
},
-- 铀矿总产量/小时
['totalResOutPutC'] ={
     key = 'totalResOutPutC',
     value = 'V3CommonUI_Icon_Uranium_02.png'
},
-- 黄金容量上限
['resLimitA'] ={
     key = 'resLimitA',
     value = 'V3CommonUI_Icon_Gold_02.png'
},
-- 石油容量上限
['resLimitB'] ={
     key = 'resLimitB',
     value = 'V3CommonUI_Icon_Petroleum_02.png'
},
-- 合金容量上限
['resLimitD'] ={
     key = 'resLimitD',
     value = 'V3CommonUI_Icon_Steel_02.png'
},
-- 铀矿容量上限
['resLimitC'] ={
     key = 'resLimitC',
     value = 'V3CommonUI_Icon_Uranium_02.png'
},
-- 黄金总容量上限
['totalResUpperLimitA'] ={
     key = 'totalResUpperLimitA',
     value = 'V3CommonUI_Icon_Gold_02.png'
},
-- 石油总容量上限
['totalResUpperLimitB'] ={
     key = 'totalResUpperLimitB',
     value = 'V3CommonUI_Icon_Petroleum_02.png'
},
-- 合金总容量上限
['totalResUpperLimitD'] ={
     key = 'totalResUpperLimitD',
     value = 'V3CommonUI_Icon_Steel_02.png'
},
-- 铀矿总容量上限
['totalResUpperLimitC'] ={
     key = 'totalResUpperLimitC',
     value = 'V3CommonUI_Icon_Uranium_02.png'
},
-- 攻击
['buildingAttack'] ={
     key = 'buildingAttack',
     value = 'CommonSmallIcon_Atk.png'
},
-- 防御
['buildingDefence'] ={
     key = 'buildingDefence',
     value = 'CommonSmallIcon_Def.png'
},
-- 生命上限
['defenceTotalHP'] ={
     key = 'defenceTotalHP',
     value = 'CommonSmallIcon_HP_Max.png'
},
-- 当前生命
['defenceCurrHP'] ={
     key = 'defenceCurrHP',
     value = 'CommonSmallIcon_HP.png'
},
-- 战役出征上限
['battlePopulation'] ={
     key = 'battlePopulation',
     value = 'CommonSmallIcon_Battle_Max.png'
},
-- 英雄治疗加速
['heroTreatmentSpeed'] ={
     key = 'heroTreatmentSpeed',
     value = 'CommonSmallIcon_Hero_Treatment_SpeedUp.png'
},
-- 英雄训练加速
['heroTrainSpeed'] ={
     key = 'heroTrainSpeed',
     value = 'CommonSmallIcon_HeroUnlock_SanBing.png'
},
-- 英雄训练经验/分钟
['expPerMinute'] ={
     key = 'expPerMinute',
     value = 'CommonSmallIcon_HeroUnlock_SanBing.png'
},
-- 英雄栏位
['heroSpace'] ={
     key = 'heroSpace',
     value = 'CommonSmallIcon_HeroUnlock_SanBing.png'
},
-- 英雄大厦_主界面红点_前{0}名可推送
['heroRedPointPushPowerRank'] ={
     key = 'heroRedPointPushPowerRank',
     value = 10
},
-- 英雄大厦_主界面红点_{0}品质可推送
['heroRedPointPushQuality'] ={
     key = 'heroRedPointPushQuality',
     value = 6
},
-- 新兵产量/小时
['resPerHourSoldier'] ={
     key = 'resPerHourSoldier',
     value = 'CommonSmallIcon_NewSoldier.png'
},
-- 新兵容量上限
['resLimitSoldier'] ={
     key = 'resLimitSoldier',
     value = 'CommonSmallIcon_NewSoldier_Max.png'
},
-- 行军队列增加
['effectID_202'] ={
     key = 'effectID_202',
     value = 'CommonSmallIcon_Battle_Max.png'
},
-- 城防值
['cityDefence'] ={
     key = 'cityDefence',
     value = 'cityDefence.png'
},
-- 攻击速度
['buildingAttackRound'] ={
     key = 'buildingAttackRound',
     value = 'buildingAttackRound.png'
},
-- 陷阱容量
['trapCapacity'] ={
     key = 'trapCapacity',
     value = 'trapCapacity.png'
},
-- PVE地图透视参数
['pveMapBgPers'] ={
     key = 'pveMapBgPers',
     value = 0.8
},
-- PVE地图背景透视参数
['pveMapParentPers'] ={
     key = 'pveMapParentPers',
     value = 0.8
},
-- PVE地图背景相对地图移动比例
['pveBackgroundMapMove'] ={
     key = 'pveBackgroundMapMove',
     value = 0.3
},
-- PVE地图移动范围参数
['pveMapMovePers'] ={
     key = 'pveMapMovePers',
     value = '1600_1300'
},
-- 战斗结束时候等待基地爆炸动画的时间（美术同学用）单位毫秒
['BattleFinishWait'] ={
     key = 'BattleFinishWait',
     value = 3
},
-- PVE中加速2倍按钮的结算关卡id
['2UpSpeedUnlockDungeon'] ={
     key = '2UpSpeedUnlockDungeon',
     value = 10106
},
-- PVE中加速3倍按钮的结算关卡id
['3UpSpeedUnlockDungeon'] ={
     key = '3UpSpeedUnlockDungeon',
     value = 10106
},
-- PVE精英副本次数最大购买次数
['pveTimesMaxBuyTimes'] ={
     key = 'pveTimesMaxBuyTimes',
     value = 5
},
-- 王城驻扎人口上限
['presidentOfficeDefencePopulation'] ={
     key = 'presidentOfficeDefencePopulation',
     value = 800000
},
-- 王城驻扎英雄上限
['presidentOfficeDefenceHeroNum'] ={
     key = 'presidentOfficeDefenceHeroNum',
     value = 3
},
-- 王城驻扎优先英雄血量百分比阈值
['presidentDefenceHeroHpPercent'] ={
     key = 'presidentDefenceHeroHpPercent',
     value = 30
},
-- 出征自动选择优先英雄血量百分比阈值
['PVPAutoHeroChooseHpPercent'] ={
     key = 'PVPAutoHeroChooseHpPercent',
     value = 30
},
-- 出征类型自动选择方式，配置格式：出征id_选择方式（1：等级优先，2：负重优先，3：速度优先，4：搭配优先）
['autoBattleTroopChoose'] ={
     key = 'autoBattleTroopChoose',
     value = '1_2|2_1|3_1|4_1|5_1|7_1|8_1|11_1|12_1|13_1|14_1|15_1|16_2|17_1|18_1|19_1|20_1|21_1|22_1|23_1|24_1|25_1|26_1|27_1|37_1|44_1|48_1|50_1|51_1|52_1|46_1|47_1|53_1|69_2'
},
-- 出征类型，搭配优先方式中，单兵种最大出征比例
['collocationPri'] ={
     key = 'collocationPri',
     value = 0.4
},
-- 购买体力每次获得体力值
['buyEnergyAdd'] ={
     key = 'buyEnergyAdd',
     value = 50
},
-- 购买体力每次消耗水晶数
['buyEnergyCost'] ={
     key = 'buyEnergyCost',
     value = '10000_1001_100'
},
-- 实际体力上限，卡住所有体力获取渠道
['actualVitLimit'] ={
     key = 'actualVitLimit',
     value = 999
},
-- 每日活跃任务的刷新时间
['dailyMissionTime'] ={
     key = 'dailyMissionTime',
     value = 5
},
-- 双货币固定兑换数量
['exchangeValue'] ={
     key = 'exchangeValue',
     value = 100
},
-- 商城礼包指定每日刷新时间，上午
['giftRefreshAM'] ={
     key = 'giftRefreshAM',
     value = 5
},
-- 商城礼包指定每日刷新时间，下午
['giftRefreshPM '] ={
     key = 'giftRefreshPM ',
     value = 17
},
-- 单次修复城墙增加城防值
['onceWallRepair'] ={
     key = 'onceWallRepair',
     value = 80
},
-- 修复城墙间隔时间（秒）
['wallRepairCd'] ={
     key = 'wallRepairCd',
     value = 1800
},
-- 着火状态下，普通土地上的燃烧速度（点/千秒）
['wallFireSpeed'] ={
     key = 'wallFireSpeed',
     value = 56
},
-- 着火状态下，黑土地上的燃烧速度（点/千秒）
['wallFireSpeedOnBlackLand'] ={
     key = 'wallFireSpeedOnBlackLand',
     value = 27778
},
-- 城墙着火时间上限值（秒）
['wallFireMaxTime'] ={
     key = 'wallFireMaxTime',
     value = 259200
},
-- 单次攻击城墙造成的燃烧时间（秒）
['onceAttackWallFireTime'] ={
     key = 'onceAttackWallFireTime',
     value = 1800
},
-- 灭火消耗资源（type_id_count）
['outFireCost'] ={
     key = 'outFireCost',
     value = '10000_1001_20'
},
-- 制造陷阱单次最大时间上限（秒）
['trapTrainMaxTime'] ={
     key = 'trapTrainMaxTime',
     value = 36000
},
-- 许愿池获取资源数值变化表达式，x1:基础值 x2:今日许愿次数
['wishingAddValueExpr'] ={
     key = 'wishingAddValueExpr',
     value = 'x1*(1+0.3*(x2-1)/100)'
},
-- 许愿池消耗资源数值变化表达式，x1:基础值 x2:今日许愿次数
['wishingCostValueExpr'] ={
     key = 'wishingCostValueExpr',
     value = 'x1+(x2-1)*1'
},
-- 许愿池消耗资源类型id
['wishingCostResType'] ={
     key = 'wishingCostResType',
     value = 1001
},
-- 许愿池暴击概率(千分比)，格式：倍数:概率;倍数:概率
['wishingCritRate'] ={
     key = 'wishingCritRate',
     value = '1:630;2:200;5:120;10:50'
},
-- 许愿池道具id
['wishingItemId'] ={
     key = 'wishingItemId',
     value = 1300008
},
-- 许愿池图片
['WishingResIcon'] ={
     key = 'WishingResIcon',
     value = '1107,Item_Res_Ore_03.png;1108,Item_Res_Oil_03.png;1110,Item_Res_Alloy_03.png;1109,Item_Res_Uranium_03.png'
},
-- 许愿池详情界面图标-金矿
['wishGoldore'] ={
     key = 'wishGoldore',
     value = 'Item_Res_Ore_03.png'
},
-- 许愿池详情界面图标-石油
['wishOil'] ={
     key = 'wishOil',
     value = 'Item_Res_Oil_03.png'
},
-- 许愿池详情界面图标-铀矿
['wishTombarthite'] ={
     key = 'wishTombarthite',
     value = 'Item_Res_Alloy_03.png'
},
-- 许愿池详情界面图标-钢铁
['wishSteel'] ={
     key = 'wishSteel',
     value = 'Item_Res_Uranium_03.png'
},
-- 许愿池详情界面图标-免费次数
['wishFreeCount'] ={
     key = 'wishFreeCount',
     value = 'Item_Voucher.png'
},
-- 天赋重置道具在商城（shop）中对应的id数据
['talentResetItemSaleId'] ={
     key = 'talentResetItemSaleId',
     value = 902002
},
-- 天赋切换道具在商城（shop）中对应的id数据
['talentExchangeItemSaleId'] ={
     key = 'talentExchangeItemSaleId',
     value = 902003
},
-- 解锁天赋路线2所需大本等级
['unlockTalentLine2NeedCityLevel'] ={
     key = 'unlockTalentLine2NeedCityLevel',
     value = 0
},
-- 解锁天赋路线3所需大本等级
['unlockTalentLine3NeedCityLevel'] ={
     key = 'unlockTalentLine3NeedCityLevel',
     value = 10
},
-- 谏言首次出现时间，单位：秒
['suggestionIntervalTime'] ={
     key = 'suggestionIntervalTime',
     value = 25
},
-- 谏言更新 时间，单位：秒
['suggestionKeepTime'] ={
     key = 'suggestionKeepTime',
     value = 25
},
-- 研究基础免费时间，单位：秒
['scienceFreeTime'] ={
     key = 'scienceFreeTime',
     value = 0
},
-- 研究时，距离免费时间XX秒的提示文案
['scienceFreeTipsTimeThreshold'] ={
     key = 'scienceFreeTipsTimeThreshold',
     value = 600
},
-- 好友每日收礼上限（个数）
['friendGift'] ={
     key = 'friendGift',
     value = 100
},
-- 亲密度区间与好友礼包映射(亲密度a,物品ID2;亲密度b,物品ID2)
['friendGiftIntimacy'] ={
     key = 'friendGiftIntimacy',
     value = '0,2500000;100,2500000;500,2500001'
},
-- 好友上限（个数）
['friendUpperLimit'] ={
     key = 'friendUpperLimit',
     value = 100
},
-- 申请列表容纳上限（个数）
['friendApplyLimit'] ={
     key = 'friendApplyLimit',
     value = 99
},
-- 推荐好友出现条件（当好友数量低于这个值时出现推荐好友）
['friendMinimumValue'] ={
     key = 'friendMinimumValue',
     value = 10
},
-- 每次推荐好友个数
['friendRecommendCount'] ={
     key = 'friendRecommendCount',
     value = 5
},
-- 亲密度上限
['friendIntimacyLimit'] ={
     key = 'friendIntimacyLimit',
     value = 21900
},
-- 赠送一次礼包互相增加多少亲密度
['friendIntimacy'] ={
     key = 'friendIntimacy',
     value = 10
},
-- 查找陌生人好友每次抓取上限
['friendFindLimit'] ={
     key = 'friendFindLimit',
     value = 20
},
-- 每日登陆奖励的推送时间点（按照小时数）
['loginPushTimePoint'] ={
     key = 'loginPushTimePoint',
     value = 10
},
-- 联盟传送离盟主的距离
['allianceTransfer'] ={
     key = 'allianceTransfer',
     value = 20
},
-- 消灭敌军的战力百分比大于固定值
['battleMailShowCondition1'] ={
     key = 'battleMailShowCondition1',
     value = 0.08
},
-- 攻击方损失的战斗力大于固定值
['battleMailShowCondition2'] ={
     key = 'battleMailShowCondition2',
     value = 50000
},
-- 双方交战的回合数达到固定值
['battleMailShowCondition3'] ={
     key = 'battleMailShowCondition3',
     value = 20
},
-- 我方战斗力/敌方战斗力，阈值1（千分比数值）
['BattleResultExpectValue1'] ={
     key = 'BattleResultExpectValue1',
     value = 400
},
-- 我方战斗力/敌方战斗力，阈值2（千分比数值）
['BattleResultExpectValue2'] ={
     key = 'BattleResultExpectValue2',
     value = 800
},
-- 我方战斗力/敌方战斗力，阈值3（千分比数值）
['BattleResultExpectValue3'] ={
     key = 'BattleResultExpectValue3',
     value = 2000
},
-- 我方战斗力/敌方战斗力，阈值4（千分比数值）
['BattleResultExpectValue4'] ={
     key = 'BattleResultExpectValue4',
     value = 3500
},
-- 同一封邮件分享到联盟聊天有间隔时间，单位是秒
['ShareTime'] ={
     key = 'ShareTime',
     value = 600
},
-- 联盟邀请函过期时间
['allianceInvitationOverTime'] ={
     key = 'allianceInvitationOverTime',
     value = 1209600
},
-- 英雄属性点重置消耗
['heroAttrResetCost'] ={
     key = 'heroAttrResetCost',
     value = '10000_1001_300'
},
-- 装备最高品质
['equipMaxQuality'] ={
     key = 'equipMaxQuality',
     value = 5
},
-- 指挥官装备栏位对应解锁等级
['equipSlotLimit'] ={
     key = 'equipSlotLimit',
     value = '1_1_1,2_2_1,3_3_1,4_4_1,5_5_1,6_6_1,7_7_1,8_7_1'
},
-- 兵种晋升系数（出售兵种打的折扣z）晋升消耗公式=xb-(Xa×z)
['promotionVariate'] ={
     key = 'promotionVariate',
     value = 0.5
},
-- 兵种晋升时间系数
['promotionTimeVariate'] ={
     key = 'promotionTimeVariate',
     value = 0.8
},
-- 消费提示出现的时间间隔；单位：秒
['consumeTipsTimeInterval'] ={
     key = 'consumeTipsTimeInterval',
     value = 86400
},
-- 一键加速提示出现的时间间隔；单位：秒
['oneClickSpeedUpTipsTimeInterval'] ={
     key = 'oneClickSpeedUpTipsTimeInterval',
     value = 180
},
-- 使用一键加速时，加速时间超出剩余时间一定阈值时，会触发弹框提示
['oneClickSpeedUpOverStepThreshold'] ={
     key = 'oneClickSpeedUpOverStepThreshold',
     value = 300
},
-- 充值钻石转换vip经验系数（百分比数据）
['diaExchangeVipExpCof'] ={
     key = 'diaExchangeVipExpCof',
     value = 100
},
-- vip0级时的免费编队个数（现版本与vip无关，只读取此值）
['iniTroopTeamNum'] ={
     key = 'iniTroopTeamNum',
     value = 9
},
-- 联盟帮助动画间隔时长（单位秒）
['helpAniInterval'] ={
     key = 'helpAniInterval',
     value = 0.5
},
-- 解锁战地任务功能需要完成剧情章节XX
['unlockCenterMissionNeedDramaChapter'] ={
     key = 'unlockCenterMissionNeedDramaChapter',
     value = 5
},
-- 手指提示时间要求
['untouchTime'] ={
     key = 'untouchTime',
     value = 10
},
-- 手指提示基地等级要求，小于该等级提示
['untouchLevel'] ={
     key = 'untouchLevel',
     value = 19
},
-- 每日手指引导出现次数上限
['untouchDayLimitTimes'] ={
     key = 'untouchDayLimitTimes',
     value = 5
},
-- 手指引导出现持续时间
['untouchLastTime'] ={
     key = 'untouchLastTime',
     value = 4
},
-- 常驻推送礼包界面达到XX秒时，自动给玩家进行页签切换
['pushGiftInterfaceAutoSwitchTime'] ={
     key = 'pushGiftInterfaceAutoSwitchTime',
     value = 60
},
-- 迷雾宝箱最大空位
['foggyBoxMaxSpace'] ={
     key = 'foggyBoxMaxSpace',
     value = 3
},
-- 迷雾要塞最高等级
['foggyFortressMaxLevel'] ={
     key = 'foggyFortressMaxLevel',
     value = 24
},
-- 进入防空洞时间
['shelterRecall'] ={
     key = 'shelterRecall',
     value = '3600_7200_14400_28800'
},
-- 防空洞即将返回提示时间，对应lang表格内文本@shelterTipsDes1
['shelterTips1'] ={
     key = 'shelterTips1',
     value = 180
},
-- 防空洞已返回提示时间，对应lang表格内同前缀文本@shelterTipsDes2
['shelterTips2'] ={
     key = 'shelterTips2',
     value = 0
},
--  城建解锁区域提示时间
['areaUnlockBubbleDuration'] ={
     key = 'areaUnlockBubbleDuration',
     value = 300
},
-- 城建解锁区域提示间隔
['areaUnlockBubbleShowTime'] ={
     key = 'areaUnlockBubbleShowTime',
     value = 20
},
-- 聊天功能屏蔽（大本小于或等于XX等级时，无法发送聊天信息）
['chatBlockByMainCityLevel'] ={
     key = 'chatBlockByMainCityLevel',
     value = 4
},
-- 世界聊天同样内容连续发送次数（大于3次不能发送，并弹出提示条）
['chatSameContentTimes'] ={
     key = 'chatSameContentTimes',
     value = 3
},
-- 连续发送同样内容时间限制
['chatSameContentCD'] ={
     key = 'chatSameContentCD',
     value = 60
},
-- 世界聊天连续发送间隔时间
['chatSendTimeCD'] ={
     key = 'chatSendTimeCD',
     value = 3
},
-- 首充礼包消失后，取代其位置的破冰礼包id
['breakIceHeroPackageId'] ={
     key = 'breakIceHeroPackageId',
     value = 0
},
-- 首充礼包消失后，取代其位置的破冰礼包主界面展示icon
['breakIceHeroPackageIcon'] ={
     key = 'breakIceHeroPackageIcon',
     value = 'Activity_Icon_31.png'
},
-- 超值礼包系统刷新时间间隔；单位：秒
['giftResetTimeInterval'] ={
     key = 'giftResetTimeInterval',
     value = 5400
},
-- 技能卡池const
['skillPoolList'] ={
     key = 'skillPoolList',
     value = '1313103009_1313103010_1313103011_1313103012_1313103013_1313103014_1313103015_1313103016_1313101013_1313101014_1313101015_1313101016_1313101020_1313101021_1313101022_1313101023_1313101024_1313101025_1313101026_1313101027_1313101031_1313101032_1313101033_1313101034_1313102004_1313102005_1313102006_1313102007_1313103027_1313103028_1313103029_1313103030_1313103031_1313103032_1313103033_1313103034_1313103035_1313103036_1313103037_1313101035_1313101036_1313101037_1313101038_1313101039_1313101040_1313101041_1313103038_1313103039_1313103040_1313101042_1313101043_1313101044_1313101045_1313101017_1313101018_1313101019_1313101028_1313102008_1313102009_1313102010_1313102011_1313101001_1313101002_1313101003_1313101004_1313101005_1313101006_1313101007_1313101008_1313103001_1313103002_1313103003_1313103004_1313103005_1313103006_1313103007_1313103008_1313101046_1313101047_1313101048_1313101049_1313101051_1313101052_1313101053_1313101054_1313103020_1313103021_1313103022_1313103023_1313103026_1313103017_1313102001_1313101012_1313102002_1313103018_1313101010_1313101011_1313102003_1313101029_1313101030_1313103024_1313103025_1313103019_1313101050_1313101009_1313101055_1313101056_1313101057_1313101058_1313101059_1313101060_1313103061_1313101063_1313103064_1313103065_1313103066_1313103067_1313103068_1313103069_1313101081_1313103073_1313103075_1313103074_1313103076_1313103077_1313103079_1313103078_1313103080_1313103105_1313103110_1313103115_1313103120_1313103125_1313103130_1313103135_1313103140_1313103145_1313103150_1313103155_1313103165_1313103170_1313103180_1313103185_1313103190_1313103195'
},
-- 低级英雄卡池const
['lowItemPoolList'] ={
     key = 'lowItemPoolList',
     value = '1001038_1001039_1001019_1001022_1001023_1001024_1001025_1001026_1001027_1001028_1001005_1001006_1001007_1001008_1001009_1001010_1001011_1001012_1001013_1001014_1001015_1001016_1001029_1001030_1001031_1001032_1001033_1001034_850034_850035_850004_850005_810000_810001_1510002_1510003_850054_850055_1011_1012_1013_1014_1015_1016_1029_1030_1031_1032_1033_1034'
},
-- 高级英雄卡池const
['highItemPoolList'] ={
     key = 'highItemPoolList',
     value = '1001018_1001035_1001036_1001037_1001042_1001004_1001017_1001002_1001003_1001020_1001021_1001038_1001039_1001005_1001006_1001019_1001022_1001023_1001024_1001025_1001026_1001027_1001028_1001007_1001008_1001009_1001010_1001011_1001012_1001013_1001014_1001015_1001016_1001029_1001030_1001031_1001032_1001033_1001034_1001051_1001077_1001044_1001057_1001001_1001043_1001067_1001073_1001050_1001041_1001053_1001048_1001064_1001046_1001059_1001049_1001070_1001045_1001052_1001063_1001047_1001068_1001076_1001072_1001079_1001069_1001075_1001071_1001085_1001082_1001087_1001080_1001084_1001091_1001097_1001055_1001061_1001066_1001074_1001056_1001054_1001058_1001062_1001065_1001060_850035_850036_850005_850006_810001_1510003_1510004_850055_850056_1018_1035_1036_1037_1042_1004_1017_1002_1003_1020_1021_1038_1039_1005_1006_1007_1008_1009_1010_1019_1022_1023_1024_1025_1026_1027_1028_1029_1030_1031_1032_1033_1034_1011_1012_1013_1014_1015_1016_1051_1077_1044_1057_1001_1043_1067_1073_1050_1041_1053_1048_1064_1046_1059_1049_1070_1045_1052_1063_1047_1068_1076_1072_1079_1069_1075_1071_1085_1082_1087_1080_1084_1091_1097_1055_1061_1066_1074_1056_1054_1058_1062_1065_1060'
},
-- 超级武器驻守奖励tick周期(ms)
['occupyAwardTickPeriod'] ={
     key = 'occupyAwardTickPeriod',
     value = 120000
},
-- 超时空转换器解锁
['AreaUnlockId'] ={
     key = 'AreaUnlockId',
     value = 1
},
-- 一次性合成最高弹窗次数
['skillComposeMaxTipsTimes'] ={
     key = 'skillComposeMaxTipsTimes',
     value = 3
},
-- 超级武器活动提前出现时间(s)
['superWeaponActivityShowTime'] ={
     key = 'superWeaponActivityShowTime',
     value = 86400
},
-- 音效最小间隔时间
['SoundIntervalTime'] ={
     key = 'SoundIntervalTime',
     value = 0
},
-- 引导箭头消失时间
['PromptDisappearance'] ={
     key = 'PromptDisappearance',
     value = 5
},
-- 个性签名长度限制
['signatureLengthLimit'] ={
     key = 'signatureLengthLimit',
     value = 20
},
-- 头像拉新时间
['avatarCacheDuration'] ={
     key = 'avatarCacheDuration',
     value = 3600
},
-- 非中文字符宽度
['en_char_width'] ={
     key = 'en_char_width',
     value = 1.4
},
-- 中文字符宽度
['cn_char_width'] ={
     key = 'cn_char_width',
     value = 2
},
-- 评论时间间隔
['ios_comment_interval_time'] ={
     key = 'ios_comment_interval_time',
     value = 600
},
-- 争夺详情界面提前出现时间（秒）
['PreShowTimeForSuperBarrack'] ={
     key = 'PreShowTimeForSuperBarrack',
     value = 7200
},
-- 头像类型分类标签的标签设置
['headIconTabTypeToKey'] ={
     key = 'headIconTabTypeToKey',
     value = '1_@headIconTab001,2_@headIconTab002'
},
-- 头像框类型分类标签的标签设置
['headBgTabTypeToKey'] ={
     key = 'headBgTabTypeToKey',
     value = '1_@headBgTab001,2_@headBgTab002'
},
-- 超时空急救站前端容量大于等于少显示泡泡和总览角标
['superTimeRescuePopRate'] ={
     key = 'superTimeRescuePopRate',
     value = 50
},
-- 免流量提示间隔时间
['freeFlowKey'] ={
     key = 'freeFlowKey',
     value = 300
},
-- 免流量在线时长
['freeFlowOnlineKey'] ={
     key = 'freeFlowOnlineKey',
     value = 180
},
-- 水车指引箭头持续时间
['weak_guide_common_last_time'] ={
     key = 'weak_guide_common_last_time',
     value = 5
},
-- QQ密友邀请时间
['qqFriendTime'] ={
     key = 'qqFriendTime',
     value = 259200
},
-- 资源田产量时间
['resTimeLimit'] ={
     key = 'resTimeLimit',
     value = 10
},
-- 出征部队战力英雄修正常量
['HeroTroopsConstPower'] ={
     key = 'HeroTroopsConstPower',
     value = 100
},
-- 腾讯信用积分查询网址
['creditScoreURL'] ={
     key = 'creditScoreURL',
     value = 'https://gamecredit.qq.com/static/games/index.htm'
},
-- CR英雄试练每日领奖次数限制
['crRewardLimit'] ={
     key = 'crRewardLimit',
     value = 3
},
-- 技能快捷栏增加提示文字
['talentRouteNameMinMax'] ={
     key = 'talentRouteNameMinMax',
     value = '4_8'
},
-- 指挥集结队伍名字长度
['rallySetTeamName'] ={
     key = 'rallySetTeamName',
     value = '2_8'
},
-- 机甲建筑未解锁时，默认展示的机甲模型
['superSoldierSpine'] ={
     key = 'superSoldierSpine',
     value = 100110
},
-- 人物对话在大于16:9分辨率时，缩放最大值
['spineRoleMaxZoonIn'] ={
     key = 'spineRoleMaxZoonIn',
     value = 1.15
},
-- 机甲页面，主城缩放
['superSoldierSpineScale'] ={
     key = 'superSoldierSpineScale',
     value = 1.9
},
-- 分享福袋积分要求
['questShareScore'] ={
     key = 'questShareScore',
     value = 150
},
-- 我要变强，兵种乘以系数的数值
['stronger_soldier_param'] ={
     key = 'stronger_soldier_param',
     value = 0.0001
},
-- 我要变强，单类型计算自身无畏图的最大分数
['stronger_method_max_score'] ={
     key = 'stronger_method_max_score',
     value = 1000
},
-- 跨服需要的大本等级
['crossCityLevel'] ={
     key = 'crossCityLevel',
     value = 10
},
-- 雷达额外侦查队列功能配置雷达等级
['extraSpyRadarLv'] ={
     key = 'extraSpyRadarLv',
     value = 35
},
-- 勋章手册开放建筑工厂等级
['MedalMenuOpen'] ={
     key = 'MedalMenuOpen',
     value = 26
},
-- 士兵详情界面引导开放等级
['MedalTrainSoldierOpen'] ={
     key = 'MedalTrainSoldierOpen',
     value = 26
},
-- 赏金猎人奖励说明界面展示两个（或以上）英雄碎片
['bountyHunterRewardHeroFragment'] ={
     key = 'bountyHunterRewardHeroFragment',
     value = '30000_1782201_1|30000_1000002_1'
},
-- 新兵救援等级上限
['recruitslevelLimit'] ={
     key = 'recruitslevelLimit',
     value = 6
},
-- 新兵救援单日领取上限
['dayclaimLimt'] ={
     key = 'dayclaimLimt',
     value = 30000
},
-- 新兵救援总领取上限
['totalclaimLimt'] ={
     key = 'totalclaimLimt',
     value = 300000
},
-- 功能开启时间限制
['rescueDuration'] ={
     key = 'rescueDuration',
     value = 2592000
},
-- 限时商店的每日触发次数
['timeLimitShopTriggerTimes'] ={
     key = 'timeLimitShopTriggerTimes',
     value = 3
},
-- 复仇商店刷新周期
['revengeShopRefresh'] ={
     key = 'revengeShopRefresh',
     value = 604800
},
-- 复仇商店存在时长
['revengeShopDuration'] ={
     key = 'revengeShopDuration',
     value = 86400
},
-- 复仇商店功能触发时长
['revengeShopTriggerTime'] ={
     key = 'revengeShopTriggerTime',
     value = 3600
},
-- 复仇商店功能触发死兵数量
['revengeShopTriggerNum'] ={
     key = 'revengeShopTriggerNum',
     value = 100000
},
-- 复仇商店的有效兵种最低等级
['revengeShopTroopsLevel'] ={
     key = 'revengeShopTroopsLevel',
     value = 7
},
-- 英雄试炼提前领奖花费金币系数
['heroTrialQueueTimeWeight'] ={
     key = 'heroTrialQueueTimeWeight',
     value = 1
},
-- 皮肤进阶概率提示文本颜色（0-49红色，50-79黄色，80-100绿色）
['heroSkinRate'] ={
     key = 'heroSkinRate',
     value = '50_79'
},
-- SSS英雄军魂功能，关联动态头像解锁品阶；绝对值
['heroSoulTroopImageUnlockStage'] ={
     key = 'heroSoulTroopImageUnlockStage',
     value = 2
},
-- SSS英雄军魂功能，关联行军头像动效解锁品阶；绝对值
['heroSoulMarchImageUnlockStage'] ={
     key = 'heroSoulMarchImageUnlockStage',
     value = 4
},
-- SSS英雄军魂功能，关联专属皮肤解锁品阶；绝对值
['heroSoulSkinUnlockStage'] ={
     key = 'heroSoulSkinUnlockStage',
     value = 6
},
-- 【军魂】功能是否开启重置功能开关；绝对值
['heroSoulResetOpen'] ={
     key = 'heroSoulResetOpen',
     value = 1
},
-- 【军魂】重置后，时间CD；绝对值/单位：秒
['heroSoulResetTimeLimit'] ={
     key = 'heroSoulResetTimeLimit',
     value = 604800
},
-- 野怪掉落限制awardid（仅掉落限制 用 | 隔开）
['blazeMedalAward'] ={
     key = 'blazeMedalAward',
     value = '2701046,2701047,2701048,2701049,2701050'
},
-- 掉落限制的物品id和数量
['blazeMedalLimit'] ={
     key = 'blazeMedalLimit',
     value = '1300037_30'
},
-- 聊天建群上限限制
['numberOfChats'] ={
     key = 'numberOfChats',
     value = 120
},
-- 二级密码审核时间
['secPasswordExamineTime'] ={
     key = 'secPasswordExamineTime',
     value = '259200'
},
-- 克瑞斯专属芯片作用号【1525】输出常量
['effect1525Power'] ={
     key = 'effect1525Power',
     value = '0,10000,13000,15000,15800,16600'
},
-- 编队改名字符长度限制
['formationNameMinMax'] ={
     key = 'formationNameMinMax',
     value = '2_8'
},
-- 雪球单次攻击城墙造成的燃烧时间（秒）
['snowballAtkFireTime'] ={
     key = 'snowballAtkFireTime',
     value = 180
},
-- 密友邀请前段拉起接口CD时间（单位秒）
['goodFriendInviteCD'] ={
     key = 'goodFriendInviteCD',
     value = 600
},
-- 装备技术研究中，基地光环解锁的条件（每2467个部件满级后解锁）
['equipResearchShowUnlock'] ={
     key = 'equipResearchShowUnlock',
     value = '2_4_6_7'
},
-- 建筑一键升级最大等级限制
['buildContinuousUpgradeLimit'] ={
     key = 'buildContinuousUpgradeLimit',
     value = 5
},
-- 建筑一键升级功能解锁条件
['buildContinuousUpgradeCondition'] ={
     key = 'buildContinuousUpgradeCondition',
     value = 201015
},
-- 科技一键研究最大等级限制
['techContinuousUpgradeLimit'] ={
     key = 'techContinuousUpgradeLimit',
     value = 20
},
-- 科技一键研究功能解锁条件
['techContinuousUpgradeCondition'] ={
     key = 'techContinuousUpgradeCondition',
     value = 201510
},
-- 【废弃，请移步itemChestFix表】
['cavilliPseudorandom'] ={
     key = 'cavilliPseudorandom',
     value = '840700_4_1000005_4400042'
},
-- 金币特权主界面按钮出现时长
['goldPrivilegeButtonTime'] ={
     key = 'goldPrivilegeButtonTime',
     value = 86400
},
-- 金币特权半价券道具ID
['goldPrivilegeDiscountItem'] ={
     key = 'goldPrivilegeDiscountItem',
     value = 21063001
},
-- 控制分享后的图片是否带有二维码，填写0则不带有，其他数据则带有二维码-用于平台上架
['shareQRCode'] ={
     key = 'shareQRCode',
     value = 0
},
-- 泰能研究所，三条生产线  初始读的表id
['plantTechnologyInitCfgId'] ={
     key = 'plantTechnologyInitCfgId',
     value = '10101_10201_10301_10401_10501_10601'
},
-- 10星堡-作战试验室特殊等级的特殊显示文本
['technologyTips'] ={
     key = 'technologyTips',
     value = '9_@TechnologyTips01,10_@TechnologyTips02,11_@TechnologyTips03,12_@TechnologyTips04,13_@TechnologyTips05,14_@TechnologyTips06,15_@TechnologyTips07,16_@TechnologyTips08,17_@TechnologyTips09,18_@TechnologyTips10,19_@TechnologyTips11,20_@TechnologyTips12'
},
-- 联盟成员界面，若成员离线天数大于此天数，则不显示具体值，显示为X+日前
['allianceMemberLeave'] ={
     key = 'allianceMemberLeave',
     value = 7
},
-- 登陆弹窗，1类常规拍脸图，单次最大弹出数量
['PopPictureNum'] ={
     key = 'PopPictureNum',
     value = 3
},
-- 每日必买，展示的英雄id
['dailyPreferenceHeroId'] ={
     key = 'dailyPreferenceHeroId',
     value = '1001_1047_1049_1051_1063_1054_1055_1058_1042_1061_1062_1065_1066_1068_1069_1071'
},
-- 战区使命阶段，对应到obelisk表的chapter
['obeliskChapter'] ={
     key = 'obeliskChapter',
     value = '101_102_103_104_105_106_201_202_203_204_301_302_303_304_401_402_403_404_501_502_503_504_601_602_603_604'
},
-- 联盟语音开关，0关闭，1开启
['openAllianceVoice'] ={
     key = 'openAllianceVoice',
     value = 0
},
-- 指定渠道分享后不显示二维码，填写渠道名，_分割，填写0则都显示
['hideQRCodePlatform'] ={
     key = 'hideQRCodePlatform',
     value = '10017385_10003392_10018084_10003898_10025553_10004231_10022592_10029304_10032223_10018351_10029778_10003401_10003901_10033159_10003402_10003412_10012938_10022591_10003405_10003807_10011917_10027723_10027724_10159208'
},
-- 指定渠道不显示平台能力，主界面上面屏蔽掉了qq 微信相关按钮    platform_privilege_conf这张表里的platformId > 0 的都屏蔽了。目前为oppo渠道。多个渠道用_分割
['hidePlatformCapability'] ={
     key = 'hidePlatformCapability',
     value = '10017385'
},
-- 机甲图纸道具id
['mechaRepairItem'] ={
     key = 'mechaRepairItem',
     value = '30000_15900001_1'
},
-- 机甲修复时显示的机甲
['machaFirstRepaired'] ={
     key = 'machaFirstRepaired',
     value = 1001
},
-- 机甲修复界面显示属性的等级
['machaRepairMaxEffect'] ={
     key = 'machaRepairMaxEffect',
     value = 60
},
-- 机甲图纸分析阶段大本等级限制
['machaAnalysisingLevel'] ={
     key = 'machaAnalysisingLevel',
     value = 4
},
-- 资源区战斗我方士兵生成间隔
['buildAreaAllyTroopRebornTime'] ={
     key = 'buildAreaAllyTroopRebornTime',
     value = 2
},
-- 资源区战斗我方士兵行军速度
['buildAreaAllyTroopSpeed'] ={
     key = 'buildAreaAllyTroopSpeed',
     value = 200
},
-- 资源区战斗我方士兵每次出兵数量
['buildAreaAllyTroopNum'] ={
     key = 'buildAreaAllyTroopNum',
     value = 2
},
-- 资源区战斗我方士兵攻击动画循环播放次数
['buildAreaAllyTroopAttackTime'] ={
     key = 'buildAreaAllyTroopAttackTime',
     value = 3
},
-- 空投宝箱解锁条件（前置建筑类型_前置建筑等级）
['airdropUnlock'] ={
     key = 'airdropUnlock',
     value = '2010_7'
},
-- 弱引导_点击剧情任务_停留内城/野外{0}秒触发（秒）
['plotTaskWeakGuideTime'] ={
     key = 'plotTaskWeakGuideTime',
     value = 15
},
-- 弱引导_点击剧情任务_开启条件(建筑等级_建筑id_等级)(同功能开启表)
['plotTaskWeakGuideOpen'] ={
     key = 'plotTaskWeakGuideOpen',
     value = '1_2010_4'
},
-- 弱引导_点击剧情任务_关闭条件(建筑等级_建筑id_等级)(同功能开启表)
['plotTaskWeakGuideClose'] ={
     key = 'plotTaskWeakGuideClose',
     value = '1_2010_10'
},
-- 野外_军情中心_入口_开启条件(建筑等级_建筑id_等级)(同功能开启表)
['fieldInformationOpen'] ={
     key = 'fieldInformationOpen',
     value = '1_2010_4'
},
-- 野外_军情中心_入口_关闭条件(建筑等级_建筑id_等级)(同功能开启表)
['fieldInformationClose'] ={
     key = 'fieldInformationClose',
     value = '1_2010_15'
},
-- 资源田防线展示动画过程时间
['defenseLineShowTime'] ={
     key = 'defenseLineShowTime',
     value = 3
},
-- 资源田防线展示动画镜头拉高数值
['defenseLineShowScaleParam'] ={
     key = 'defenseLineShowScaleParam',
     value = 1.66
},
-- 引导结束后是否弹出弹窗（隐私设置、拍脸图等）。关闭不弹出，打开弹出
['newbiePopup'] ={
     key = 'newbiePopup',
     value = '0'
},
-- 0304新手版本更新后老帐号需要标注为已完成的引导id
['newbieChapterIdSetDone'] ={
     key = 'newbieChapterIdSetDone',
     value = '20_30_60_80_90_110_130_140_180_190_220_240_250_260_310_330_340_350_380_390_400_420_430_440_460_479_510_560_570_600_670_790_800_810_820_830_845_880_890_1070_1080_1100_1110_1210_1220_1230_1240_1710_1720_1730_1740_1760_1770_1780_1790_1800'
},
-- 特惠商人购买获得好感度数量
['travelShopFriendly'] ={
     key = 'travelShopFriendly',
     value = '1001_15,1007_10,1008_10,1009_10,1010_10'
},
-- 黑市商店友好度特权卡持续时间(s)
['travelShopFriendlyCardTime'] ={
     key = 'travelShopFriendlyCardTime',
     value = 604800
},
-- 黑市商店友好度奖励领取消耗友好度(int值)
['travelShopFriendlyAwardCost'] ={
     key = 'travelShopFriendlyAwardCost',
     value = 1500
},
-- 黑市商店友好度奖励(三段式)
['travelShoFriendlyCommonAward'] ={
     key = 'travelShoFriendlyCommonAward',
     value = '30000_810001_2,30000_850006_5,30000_850036_5,30000_850016_5,30000_850026_5'
},
-- 特惠商人好感度特权卡购买后，每次购物获得好感度提升%（配置千分比）
['travelShopFriendlyUpRate'] ={
     key = 'travelShopFriendlyUpRate',
     value = 1000
},
-- 特惠商人直购礼包ID及购买赠送奖励
['travelShopFriendlyPayGift'] ={
     key = 'travelShopFriendlyPayGift',
     value = '1000201,1000202;10000_1005_300,30000_1000005_3,30000_802004_30,30000_1800000_200,30000_1800001_10'
},
-- 特惠商人普通礼盒随机奖励对应类型
['travelShopFriendlyAwardCommType'] ={
     key = 'travelShopFriendlyAwardCommType',
     value = 1
},
-- 特惠商人特权礼盒随机奖励对应类型
['travelShopFriendlyAwardPrivilegeType'] ={
     key = 'travelShopFriendlyAwardPrivilegeType',
     value = 2
},
-- 超时空道具
['spaceProp'] ={
     key = 'spaceProp',
     value = '30000_1140001_1,30000_1140002_1,30000_1140003_1'
},
-- 额外触发俯冲轰炸次数
['effect1645Maxinum'] ={
     key = 'effect1645Maxinum',
     value = 3
},
-- 额外触发俯冲轰炸伤害
['effect1645Power'] ={
     key = 'effect1645Power',
     value = 3500
},
-- 机甲精神赋能人数上限
['effect5001Cnt'] ={
     key = 'effect5001Cnt',
     value = 15
},
-- 活跃任务积分要求
['integral'] ={
     key = 'integral',
     value = 420
},
-- 活跃任务奖励会员经验数
['rewardExperience'] ={
     key = 'rewardExperience',
     value = 300
},
-- 每日登录基础经验及递增值
['dailyLogin'] ={
     key = 'dailyLogin',
     value = '100,50'
},
-- 每日登录任务最大值
['loginMaximum'] ={
     key = 'loginMaximum',
     value = 300
},
-- 自选资源宝箱选择奖励弹窗添加获得资源总量显示
['optionalResourceAmountShow'] ={
     key = 'optionalResourceAmountShow',
     value = '1780025_1780030_1780035_1780040_1780045_1780050'
},
-- 第二城建队列时间
['secondBuildDefaultOpenTime'] ={
     key = 'secondBuildDefaultOpenTime',
     value = '2001-11-10 00:00:00'
},
-- 第二城建队列礼包
['giftId'] ={
     key = 'giftId',
     value = 666551
},
-- 档案中心开放等级
['heroArchivesOpenLv'] ={
     key = 'heroArchivesOpenLv',
     value = 35
},
-- 档案中心礼包
['heroArchivesGiftGroupId'] ={
     key = 'heroArchivesGiftGroupId',
     value = '84001,84002,84003,84004'
},
-- 称号兑换（1是类型,然后是什么道具,可以换什么物品）类型目前只有1后续可加
['PropExchange'] ={
     key = 'PropExchange',
     value = '1,30000_9716000_1,30000_1400011_1000;1,30000_9716030_1,30000_1400011_100;1,30000_9716060_1,30000_1400011_200;1,30000_9702000_1,30000_1400011_1000;1,30000_9702030_1,30000_1400011_100;1,30000_9702060_1,30000_1400011_200;1,30000_9722000_1,30000_1400011_1000;1,30000_9722030_1,30000_1400011_100;1,30000_9722060_1,30000_1400011_200'
},
-- 点击坐标直接分享cd（秒）
['coordinateShareCD'] ={
     key = 'coordinateShareCD',
     value = 5
},
-- 悬赏令选择消耗上限
['RewardOrderLimit'] ={
     key = 'RewardOrderLimit',
     value = 100
},
-- 特级悬赏令判断值
['RewardOrderSuper'] ={
     key = 'RewardOrderSuper',
     value = 250
},
-- 段位对应表
['seasonGradeLevel'] ={
     key = 'seasonGradeLevel',
     value = '1,SeasonGradeLevel1;2,SeasonGradeLevel2;3,SeasonGradeLevel3;4,SeasonGradeLevel4;5,SeasonGradeLevel5;6,SeasonGradeLevel6;7,SeasonGradeLevel7'
},
-- 加机甲装置模块抽奖上限的道具组
['drawModuleUpperItems'] ={
     key = 'drawModuleUpperItems',
     value = '21070106,21070107,21070108,21070109,21070110,21070111,21070112,21070113,21070114,21070115,21070116,21070117,21070118,21070119,21070120,21070121,21070122,21070123'
},
-- 加机甲装置模块抽奖上限道具的道具来源道具
['drawModuleGetItem'] ={
     key = 'drawModuleGetItem',
     value = 21070111
},
-- 周卡兑换商店消耗显示
['MonthShopCostItems'] ={
     key = 'MonthShopCostItems',
     value = '30000_21070025_1'
},
-- 小翻倍卡显示判断值
['MonthShop21070009Value'] ={
     key = 'MonthShop21070009Value',
     value = 1000
},
-- 大翻倍卡显示判断值
['MonthShop21070010Value'] ={
     key = 'MonthShop21070010Value',
     value = 2500
},
-- 主界面右侧第二列挂点，折叠到该数量
['retainIconValue'] ={
     key = 'retainIconValue',
     value = 3
},
-- 主界面右侧第二列挂点，大于该数量开始折叠
['maxIconValue'] ={
     key = 'maxIconValue',
     value = 4
},
-- 指定道具消耗
['givenItemCostEvent'] ={
     key = 'givenItemCostEvent',
     value = 21070054
},
-- 一次性获得超过上限道具时，服务器拦截，前端提示
['maxAddItemNum'] ={
     key = 'maxAddItemNum',
     value = 250000
},
-- 联盟中通过成员集结进攻幽灵基地发放的联盟礼的个数上限
['massFoggyAllianceGiftLimit'] ={
     key = 'massFoggyAllianceGiftLimit',
     value = 360
},
-- 幽灵基地（迷雾要塞）队员参与集结胜利获得的奖励次数
['assembleRewardGetLimit'] ={
     key = 'assembleRewardGetLimit',
     value = 10
},
-- 幽灵基地（迷雾要塞）队长发动集结胜利获得的奖励次数
['startAssembleRewardGetLimit'] ={
     key = 'startAssembleRewardGetLimit',
     value = 3
},
}
return const_conf
