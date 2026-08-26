local world_march_const_conf = {
-- 默认行军队列数量
['worldMarchBaseNum'] ={
     key = 'worldMarchBaseNum',
     value = 1
},
-- 世界行军最大距离
['worldMarchMaxDistance'] ={
     key = 'worldMarchMaxDistance',
     value = 1340
},
-- 世界行军穿过核心区域行军时间倍数
['worldMarchCoreRangeTime'] ={
     key = 'worldMarchCoreRangeTime',
     value = 10
},
-- 单英雄行军速度（格/秒，用千分比计数）
['heroMarchSpeed'] ={
     key = 'heroMarchSpeed',
     value = 200
},
-- 行军距离减去固定值
['distanceSubtractionParam'] ={
     key = 'distanceSubtractionParam',
     value = 0.82
},
-- 行军距离修正参数
['distanceAdjustParam'] ={
     key = 'distanceAdjustParam',
     value = 0.82
},
-- 部队行军类型行军时间调整参数
['armyTypeAdjustParam'] ={
     key = 'armyTypeAdjustParam',
     value = 120
},
-- 野怪行军类型行军时间调整
['monsterTypeAdjustParam'] ={
     key = 'monsterTypeAdjustParam',
     value = 120
},
-- 星海乱军首领行军时间（数字越大行军时间越久）
['monsterBossAdjustParam'] ={
     key = 'monsterBossAdjustParam',
     value = 24
},
-- 幽灵基地行军时间（数字越大行军时间越久）
['foggyAdjustParam'] ={
     key = 'foggyAdjustParam',
     value = 24
},
-- 新版野怪行军时间调整参数
['newMonsterAdjustParam'] ={
     key = 'newMonsterAdjustParam',
     value = 120
},
-- 侦察行军时间调整参数
['reconnoitreTypeAdjustParam'] ={
     key = 'reconnoitreTypeAdjustParam',
     value = 3
},
-- 资源援助行军时间调整参数
['resAidTypeAdjustParam'] ={
     key = 'resAidTypeAdjustParam',
     value = 20
},
-- 领取世界宝箱行军时间调整参数
['boxTypeAdjustParam'] ={
     key = 'boxTypeAdjustParam',
     value = 3
},
-- 联盟仓库存取资源行军时间调整参数
['allianceStoreAdjustParam'] ={
     key = 'allianceStoreAdjustParam',
     value = 20
},
-- 侦查行军速度
['investigationMarchSpeed'] ={
     key = 'investigationMarchSpeed',
     value = 1
},
-- 资源援助行军速度
['resourceAssistMarchSpeed'] ={
     key = 'resourceAssistMarchSpeed',
     value = 1
},
-- 领取世界宝箱行军速度
['boxMarchSpeed'] ={
     key = 'boxMarchSpeed',
     value = 1
},
-- 联盟仓库存取资源行军速度
['allianceStoreMarchSpeed'] ={
     key = 'allianceStoreMarchSpeed',
     value = 1
},
-- 抓将遣返行军速度
['generalBackMarchSpeed'] ={
     key = 'generalBackMarchSpeed',
     value = 200
},
-- 需要在世界行军模型显示特效的作用号
['marchShowEff'] ={
     key = 'marchShowEff',
     value = '1061_1062_1063_1064_1065_1066_1067_1068'
},
-- 采集资源1007速度（x点/秒）（用前除以1000000）--黄金
['collectRes1007speed'] ={
     key = 'collectRes1007speed',
     value = 150000000
},
-- 采集资源1008速度（x点/秒）（用前除以1000000）--石油
['collectRes1008speed'] ={
     key = 'collectRes1008speed',
     value = 150000000
},
-- 采集资源1009速度（x点/秒）（用前除以1000000）--铀矿
['collectRes1009speed'] ={
     key = 'collectRes1009speed',
     value = 6250000
},
-- 采集资源1010速度（x点/秒）（用前除以1000000）--合金
['collectRes1010speed'] ={
     key = 'collectRes1010speed',
     value = 25000000
},
-- 采集资源1107速度（x点/秒）（用前除以1000000）--黄金
['collectRes1107speed'] ={
     key = 'collectRes1107speed',
     value = 150000000
},
-- 采集资源1108速度（x点/秒）（用前除以1000000）--石油
['collectRes1108speed'] ={
     key = 'collectRes1108speed',
     value = 150000000
},
-- 采集资源1109速度（x点/秒）（用前除以1000000）--铀矿
['collectRes1109speed'] ={
     key = 'collectRes1109speed',
     value = 6250000
},
-- 采集资源1110速度（x点/秒）（用前除以1000000）--合金
['collectRes1110speed'] ={
     key = 'collectRes1110speed',
     value = 25000000
},
-- 资源1007（黄金）对应负重（1点=x负重）
['res1007Weight'] ={
     key = 'res1007Weight',
     value = 1
},
-- 资源1008（石油）对应负重（1点=x负重）
['res1008Weight'] ={
     key = 'res1008Weight',
     value = 1
},
-- 资源1009（铀矿）对应负重（1点=x负重）
['res1009Weight'] ={
     key = 'res1009Weight',
     value = 24
},
-- 资源1010（合金）对应负重（1点=x负重）
['res1010Weight'] ={
     key = 'res1010Weight',
     value = 6
},
-- 资源1107（黄金）对应负重（1点=x负重）
['res1107Weight'] ={
     key = 'res1107Weight',
     value = 1
},
-- 资源1108（石油）对应负重（1点=x负重）
['res1108Weight'] ={
     key = 'res1108Weight',
     value = 1
},
-- 资源1109（铀矿）对应负重（1点=x负重）
['res1109Weight'] ={
     key = 'res1109Weight',
     value = 24
},
-- 资源1110（合金）对应负重（1点=x负重）
['res1110Weight'] ={
     key = 'res1110Weight',
     value = 6
},
-- 攻打野怪不伤兵比例（死兵按比例转化为伤兵，其余自动复活）（用前除以1000）
['atkEnemyHurtRatio'] ={
     key = 'atkEnemyHurtRatio',
     value = 0
},
-- 连续攻打野怪可选择次数
['atkEnemyContinuityNums'] ={
     key = 'atkEnemyContinuityNums',
     value = '1,3,5,10'
},
-- 侦查兵力浮动范围（用前除1000）
['scoutSoldierRandomRange'] ={
     key = 'scoutSoldierRandomRange',
     value = 100
},
-- 侦查防御武器浮动范围
['scoutDefWeaponRandomRange'] ={
     key = 'scoutDefWeaponRandomRange',
     value = 1
},
-- 侦查相关作用号
['scoutEffectID'] ={
     key = 'scoutEffectID',
     value = '100_199'
},
-- 行军报告相关作用号
['attackReportEffectID'] ={
     key = 'attackReportEffectID',
     value = '100_149'
},
-- 侦查花费基础值(消耗黄金资源)
['investigationMarchCost'] ={
     key = 'investigationMarchCost',
     value = '10000_1007_100'
},
-- 集结时间数据（秒）
['worldGatherTime'] ={
     key = 'worldGatherTime',
     value = '300_600_1800_3600'
},
-- 旗帜集结时间（秒）
['warFlagGatherTime'] ={
     key = 'warFlagGatherTime',
     value = '60_180_300_600'
},
-- 集结参加者上限基础值
['assemblyQueueNum'] ={
     key = 'assemblyQueueNum',
     value = 4
},
-- 临时队列开启上限
['tempAssemblyQueueUpper'] ={
     key = 'tempAssemblyQueueUpper',
     value = 5
},
-- 临时队列开启消耗钻石
['tempAssemblyQueueCost'] ={
     key = 'tempAssemblyQueueCost',
     value = '100_200_500_1000_1000'
},
-- 集结精英怪时间
['monsterMass'] ={
     key = 'monsterMass',
     value = '60_180_300_600'
},
-- 被击飞天数计数
['daysOfDefeated'] ={
     key = 'daysOfDefeated',
     value = 1
},
-- 被击飞次数计数
['numsOfDefeated'] ={
     key = 'numsOfDefeated',
     value = 3
},
-- 采集资源1001速度（x点/秒）（用前除以1000000）--金币
['collectRes1001speed'] ={
     key = 'collectRes1001speed',
     value = 278
},
-- 资源1001（金币）对应负重（1点=x负重）
['res1001Weight'] ={
     key = 'res1001Weight',
     value = 1000
},
-- 单人攻击战区争夺指挥部出征队列最小战力值要求
['superBarrackSingleMarchPowerLimit'] ={
     key = 'superBarrackSingleMarchPowerLimit',
     value = 400000
},
-- 机甲集结时间
['gundamMassTime'] ={
     key = 'gundamMassTime',
     value = '60_180_300_600'
},
-- 高达行军类型行军时间调整
['bossTypeAdjustParam'] ={
     key = 'bossTypeAdjustParam',
     value = 120
},
-- 机甲攻击次数限制
['gundamAtkLimit'] ={
     key = 'gundamAtkLimit',
     value = 20
},
-- 集结攻击机甲伤害上限
['massGundamOnceKillLimit'] ={
     key = 'massGundamOnceKillLimit',
     value = 288888
},
-- 单人攻击机甲伤害上限
['gundamOnceKillLimit'] ={
     key = 'gundamOnceKillLimit',
     value = 288888
},
-- 军事演习集结时间配置
['MilitaryExerciseMarchTime'] ={
     key = 'MilitaryExerciseMarchTime',
     value = '60_120_180_300'
},
-- 机甲集结时间
['nianMassTime'] ={
     key = 'nianMassTime',
     value = '60_180_300_600'
},
-- 高达行军类型行军时间调整
['nianTypeAdjustParam'] ={
     key = 'nianTypeAdjustParam',
     value = 120
},
-- 年兽攻击次数限制
['nianAtkLimit'] ={
     key = 'nianAtkLimit',
     value = 20
},
-- 单次攻击年兽伤害上限（万分比）
['nianOnceKillLimit'] ={
     key = 'nianOnceKillLimit',
     value = 100
},
-- 集结攻击年兽伤害上限（万分比）
['massNianOnceKillLimit'] ={
     key = 'massNianOnceKillLimit',
     value = 300
},
-- 超级矿9级矿等级
['specialResLevel'] ={
     key = 'specialResLevel',
     value = 9
},
-- 超级矿9级采集速度作用号
['specialResBuffVal'] ={
     key = 'specialResBuffVal',
     value = 10000
},
-- 集结分享CD时间
['inviteMassCD'] ={
     key = 'inviteMassCD',
     value = 20
},
-- 圣诞攻击次数限制
['christmasAtkLimit'] ={
     key = 'christmasAtkLimit',
     value = 20
},
-- 单次攻击boss伤害上限（万分比）
['christmasDeadlinessAtkLimit'] ={
     key = 'christmasDeadlinessAtkLimit',
     value = 100
},
-- 集结攻击boss伤害上限（万分比）
['christmasMassDeadlinessAtkLimit'] ={
     key = 'christmasMassDeadlinessAtkLimit',
     value = 300
},
-- 圣诞宝箱的拾取限制（每日重置）
['christmasBoxReceiveLimit'] ={
     key = 'christmasBoxReceiveLimit',
     value = 10
},
-- 圣诞集结时间 单位(秒)
['christmasMassTime'] ={
     key = 'christmasMassTime',
     value = '60_180_300_600'
},
-- 单次攻击Boss伤害保底值（万分比）
['christmasDeadlinessAtkMin'] ={
     key = 'christmasDeadlinessAtkMin',
     value = 0
},
-- 集结攻击boss伤害保底值（万分比）
['christmasMassDeadlinessAtkMin'] ={
     key = 'christmasMassDeadlinessAtkMin',
     value = 0
},
-- 圣诞行军速度
['christmasTypeAdjustParam'] ={
     key = 'christmasTypeAdjustParam',
     value = 120
},
-- 间谍行军速度
['espionageAdjustParam'] ={
     key = 'espionageAdjustParam',
     value = 3
},
-- 间谍行军消耗
['espionageCost'] ={
     key = 'espionageCost',
     value = '30000_16412002_1'
},
-- 雪球炸弹消耗道具
['snowballAtkCost'] ={
     key = 'snowballAtkCost',
     value = '30000_16412001_1'
},
-- 攻击雪球行军索引（对应march_model_conf）
['snowballAtkMarch'] ={
     key = 'snowballAtkMarch',
     value = 137
},
-- 攻击雪球行军速度
['snowballAdjustParam'] ={
     key = 'snowballAdjustParam',
     value = 5
},
-- 星甲集结时间
['spmechaMassTime'] ={
     key = 'spmechaMassTime',
     value = '60_120_180_240'
},
-- 星海激战集结行军外型
['xhjzMarchRally'] ={
     key = 'xhjzMarchRally',
     value = 999
},
-- 星海激战个人行军外型
['xhjzMarchSolo'] ={
     key = 'xhjzMarchSolo',
     value = 998
}
}
return world_march_const_conf
