local cyborg_const_conf = {
-- 小队成员上限
['teamMemberLimit'] ={
     key = 'teamMemberLimit',
     value = 20
},
-- 小队的数量
['teamNumLimit'] ={
     key = 'teamNumLimit',
     value = 5
},
-- 报名时间
['warTimeHour'] ={
     key = 'warTimeHour',
     value = '13:00_20:00_21:10_22:00'
},
-- 玩家主动退出副本,或弹出结算界面之后 多长时间强制回签原服 （时间短点比较好）
['forceMoveBackTime'] ={
     key = 'forceMoveBackTime',
     value = 10
},
-- 地图大小
['mapSize'] ={
     key = 'mapSize',
     value = '90_180'
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
-- 战斗开始前多少时间（秒）显示入口
['preCountingDown'] ={
     key = 'preCountingDown',
     value = 180
},
-- 每期新增出战成员上限
['warNewMemberLimit'] ={
     key = 'warNewMemberLimit',
     value = 5
},
-- 赛博能量药剂分配的总数
['cyborgItemTotal'] ={
     key = 'cyborgItemTotal',
     value = 4000
},
}
return cyborg_const_conf
