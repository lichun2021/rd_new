local space_machine_const_conf = {
-- 子舱数量
['subcabinNum'] ={
     key = 'subcabinNum',
     value = 2
},
-- 玩家对各个玩法建筑行军最多队列数量
['maxMarchNum'] ={
     key = 'maxMarchNum',
     value = 1
},
-- 阶段1首波敌军出发时间
['cabinFirstWaveTime'] ={
     key = 'cabinFirstWaveTime',
     value = 0
},
-- 阶段1子舱首波敌军出发时间
['subcabinFirstWaveTime'] ={
     key = 'subcabinFirstWaveTime',
     value = '110'
},
-- 阶段2首波敌军出发时间
['strongholdFirstWaveTime'] ={
     key = 'strongholdFirstWaveTime',
     value = '20'
},
-- 阶段3Boss出发时间
['bossMarchTime'] ={
     key = 'bossMarchTime',
     value = 30
},
-- 玩家行军速度
['marchTime'] ={
     key = 'marchTime',
     value = '10'
},
-- 敌军行军速度
['enemyMarchTime'] ={
     key = 'enemyMarchTime',
     value = 30
},
-- 掉落宝箱同时采集人数
['boxGatherLimit'] ={
     key = 'boxGatherLimit',
     value = 1
},
-- 建筑血条变色百分比
['bloodColorChangePer'] ={
     key = 'bloodColorChangePer',
     value = '70_35'
},
-- 信息面板变为小面板间隔
['boardChangeCd'] ={
     key = 'boardChangeCd',
     value = 30
},
-- 据点奖励领取次数限制
['strongholdAwardLimit'] ={
     key = 'strongholdAwardLimit',
     value = 10
},
-- 据点奖励领取总次数限制
['strongholdAwardPersonLimit'] ={
     key = 'strongholdAwardPersonLimit',
     value = 50
},
-- 对舱体扣血比例
['damgeTransPara'] ={
     key = 'damgeTransPara',
     value = '10000_10000'
},
-- 联盟放置次数上限
['setLimitNum'] ={
     key = 'setLimitNum',
     value = 5
}
}
return space_machine_const_conf
