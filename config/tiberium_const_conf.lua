local tiberium_const_conf = {
-- 报名联盟排行条件
['signRankLimit'] ={
     key = 'signRankLimit',
     value = 20
},
-- 参战人数上限
['warMemberLimit'] ={
     key = 'warMemberLimit',
     value = 50
},
-- 报名时间
['warTimeHour'] ={
     key = 'warTimeHour',
     value = '13:00_20:00_21:00_22:00'
},
-- 战场开启时间(单位:秒)
['warOpenTime'] ={
     key = 'warOpenTime',
     value = 3600
},
-- 玩家主动退出副本,或弹出结算界面之后 多长时间强制回签原服 （时间短点比较好）
['forceMoveBackTime'] ={
     key = 'forceMoveBackTime',
     value = 10
},
-- 地图大小
['mapSize'] ={
     key = 'mapSize',
     value = '150_300'
},
-- 每个时间段最多报名联盟数量
['maxSignNum'] ={
     key = 'maxSignNum',
     value = 1000
},
-- 作战小队任务数量上限
['teamTargetLimit'] ={
     key = 'teamTargetLimit',
     value = 2
},
-- 个人策略目标上限
['teamMemberStrategyLimit'] ={
     key = 'teamMemberStrategyLimit',
     value = 2
},
-- 个人主力兵种数量上限
['mySoldierTypeLimit'] ={
     key = 'mySoldierTypeLimit',
     value = 2
},
-- 多少个联盟入选泰伯联赛正赛
['guildPickCnt'] ={
     key = 'guildPickCnt',
     value = 768
},
-- 比赛未开启阶段展示的奖励
['rewardShow'] ={
     key = 'rewardShow',
     value = '30000_802006_156,30000_1000005_24,30000_802005_1800,30000_802004_1500,30000_800002_5400'
},
-- 小队名字的长度
['teamNameNumLimit'] ={
     key = 'teamNameNumLimit',
     value = '1_8'
},
-- 创建小队消耗
['createTeamCost'] ={
     key = 'createTeamCost',
     value = '10000_1001_200'
},
-- 小队正式队员数量上限
['teamMemberLimit'] ={
     key = 'teamMemberLimit',
     value = 50
},
-- 小队预备队员数量上限
['teamPreparationLimit'] ={
     key = 'teamPreparationLimit',
     value = 10
},
-- 小队的数量
['teamNumLimit'] ={
     key = 'teamNumLimit',
     value = 2
},
}
return tiberium_const_conf
