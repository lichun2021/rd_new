local homeland_const_conf = {
-- 解锁等级
['baseLimit'] ={
     key = 'baseLimit',
     value = 36
},
-- 地图大小
['mapRange'] ={
     key = 'mapRange',
     value = '30,30'
},
-- 通用货币ID
['currencyId'] ={
     key = 'currencyId',
     value = '30000_6000001_1'
},
-- 主建筑type
['mainBuildtype'] ={
     key = 'mainBuildtype',
     value = 1001
},
-- 【废弃】抽卡奖池awardId
['awardId'] ={
     key = 'awardId',
     value = 6000001
},
-- 【废弃】单抽消耗
['cost1'] ={
     key = 'cost1',
     value = '30000_6000001_100'
},
-- 【废弃】十抽消耗
['cost2'] ={
     key = 'cost2',
     value = '30000_6000001_1000'
},
-- 【废弃】每日免费次数
['shopFreeTimes'] ={
     key = 'shopFreeTimes',
     value = 5
},
-- 【废弃】免费抽取cd（秒）
['shopFreeCd'] ={
     key = 'shopFreeCd',
     value = 7200
},
-- 每日最大抽取次数
['shopMaxTimes'] ={
     key = 'shopMaxTimes',
     value = 99999
},
-- 【废弃】抽奖，第N次花费资源数量=step*N
['gacha_cost_step'] ={
     key = 'gacha_cost_step',
     value = 50
},
-- 【废弃】抽奖，每次花费资源数量不会超过max
['gacha_cost_max'] ={
     key = 'gacha_cost_max',
     value = 10000
},
-- 随机事件每天领奖次数
['issueNum'] ={
     key = 'issueNum',
     value = 2
},
-- 回收积分图标
['recoveryItem'] ={
     key = 'recoveryItem',
     value = '30000_6000002_1'
},
-- 够多少抽出批量
['batchNum'] ={
     key = 'batchNum',
     value = 100
}
}
return homeland_const_conf
