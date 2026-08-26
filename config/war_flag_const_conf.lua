local war_flag_const_conf = {
-- 战旗范围半径
['flagRadius'] ={
     key = 'flagRadius',
     value = 5
},
-- tick周期(ms),服务器控制
['tickPeriod'] ={
     key = 'tickPeriod',
     value = 1000
},
-- 最大建筑值
['maxBuildLife'] ={
     key = 'maxBuildLife',
     value = 18000
},
-- 产出资源周期(s)
['productResourcePeriod'] ={
     key = 'productResourcePeriod',
     value = 900
},
-- 战地旗帜产出
['flagResource'] ={
     key = 'flagResource',
     value = '10000_1007_15000,10000_1008_15000,10000_1010_2400,10000_1009_600'
},
-- 玩家旗帜资源上限
['flagResourceLimit'] ={
     key = 'flagResourceLimit',
     value = '10000_1007_100000000,10000_1008_100000000,10000_1009_16000000,10000_1010_5000000'
},
-- 联盟旗帜数量(战力_人数_数量)
['flagCount'] ={
     key = 'flagCount',
     value = '0_0_10,50000000_10_20,100000000_15_30,150000000_20_40,200000000_25_50,300000000_30_60,400000000_40_70,500000000_50_80'
},
-- 旗帜复制资源点比例（万分比）
['pointProRate'] ={
     key = 'pointProRate',
     value = 10000
},
-- 旗帜建造完成的占领值
['flagOccupy'] ={
     key = 'flagOccupy',
     value = 18000
},
-- 占领值时比建造时加速倍数
['flagOccupyDouble'] ={
     key = 'flagOccupyDouble',
     value = 2
},
-- 母旗建造值
['bigFlagOccupy'] ={
     key = 'bigFlagOccupy',
     value = 50000
},
-- 小旗达到指定数量可解锁母旗
['unlockBigFlag'] ={
     key = 'unlockBigFlag',
     value = '20_40_60_77'
},
-- 母旗拥有产出宝箱的格子数
['bigFlagCells'] ={
     key = 'bigFlagCells',
     value = 30
},
-- 母旗宝箱格子tick奖励时间（秒）
['bigFlagCellsTickTime'] ={
     key = 'bigFlagCellsTickTime',
     value = 3600
},
-- 母旗在黑土地特殊奖励内容
['bigFlagSpecialReward'] ={
     key = 'bigFlagSpecialReward',
     value = '30000_21070002_1'
},
-- 母旗结算时间节点（每日整点）
['bigFlagAccountTimeList'] ={
     key = 'bigFlagAccountTimeList',
     value = '11_23'
},
}
return war_flag_const_conf
