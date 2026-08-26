local xhjz_const_conf = {
-- 小队指挥官数量上限
['teamCommanderLimit'] ={
     key = 'teamCommanderLimit',
     value = 2
},
-- 小队正式队员数量上限
['teamMemberLimit'] ={
     key = 'teamMemberLimit',
     value = 28
},
-- 小队预备队员数量上限
['teamPreparationLimit'] ={
     key = 'teamPreparationLimit',
     value = 10
},
-- 小队的数量
['teamNumLimit'] ={
     key = 'teamNumLimit',
     value = 4
},
-- 报名时间
['warTimeHour'] ={
     key = 'warTimeHour',
     value = '13:00_20:00_21:00_22:00'
},
-- 玩家主动退出副本,或弹出结算界面之后 多长时间强制回签原服 （时间短点比较好）
['forceMoveBackTime'] ={
     key = 'forceMoveBackTime',
     value = 10
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
-- 地图大小
['mapSize'] ={
     key = 'mapSize',
     value = '217_411'
},
-- 界面展示的奖励内容
['rewardShow'] ={
     key = 'rewardShow',
     value = '30000_1000005_1,30000_1782103_10,30000_9990032_10,30000_802004_10,30000_1782106_10'
},
-- 拍脸图弹出截止时间
['popUpBannerTime'] ={
     key = 'popUpBannerTime',
     value = 1729612799
},
-- 报名战队排行条件
['signRankLimit'] ={
     key = 'signRankLimit',
     value = 100
},
-- 商店代币道具
['shopCost'] ={
     key = 'shopCost',
     value = '30000_21063068_1'
},
-- 燃油分配点加减号时候的最小单位
['fuelMin'] ={
     key = 'fuelMin',
     value = 100
},
}
return xhjz_const_conf
