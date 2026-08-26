local laboratory_const_conf = {
-- 锁一个消耗
['lockOneCost'] ={
     key = 'lockOneCost',
     value = '30000_1490005_1'
},
-- # 锁二个消耗 不叠加
['lockTwoCost'] ={
     key = 'lockTwoCost',
     value = '30000_1490005_5'
},
-- # 锁三个消耗 不叠加
['lockThreeCost'] ={
     key = 'lockThreeCost',
     value = '30000_1490005_10'
},
-- 改造消耗
['remakeCost'] ={
     key = 'remakeCost',
     value = '30000_1490006_1'
},
-- 解锁等级
['blockUnlock'] ={
     key = 'blockUnlock',
     value = '100_125_150_180'
},
-- 最多页数
['maxPage'] ={
     key = 'maxPage',
     value = 16
},
-- 能量源id
['lockItemId'] ={
     key = 'lockItemId',
     value = '30000_1480001_1'
},
-- 金币消耗 递增
['remakeGoldCost'] ={
     key = 'remakeGoldCost',
     value = '10000_1001_0,10000_1001_30,10000_1001_40,10000_1001_50,10000_1001_60,10000_1001_70,10000_1001_80,10000_1001_90,10000_1001_100'
}
}
return laboratory_const_conf
