local moon_war_battle_conf = {
-- 准备时间
['prepairTime'] ={
     key = 'prepairTime',
     value = 0
},
-- 燃烧加速（其他副本是45，因为月球是真死兵，所以燃烧加快）
['fireSpeed'] ={
     key = 'fireSpeed',
     value = 135
},
-- 行军加速
['playerMarchSpeedUp'] ={
     key = 'playerMarchSpeedUp',
     value = 1
},
-- 治疗加速倍数
['cureSpeedUp'] ={
     key = 'cureSpeedUp',
     value = 1
},
-- 宣战令数量上限
['declareWarOrderMax'] ={
     key = 'declareWarOrderMax',
     value = 20
},
-- 多少秒恢复一个宣战令
['declareWarOrderSpeed'] ={
     key = 'declareWarOrderSpeed',
     value = 1800
},
-- 副本内医院容量比外面增加多少倍（普通医院、泰能医院通用）
['hospitalCapacity'] ={
     key = 'hospitalCapacity',
     value = 2
},
-- 升级盟军航天中心，有以下效果：1. 能减少宣战令的恢复时间，单位分钟。
['declareWarOrderSpeedAdd'] ={
     key = 'declareWarOrderSpeedAdd',
     value = '1,2,3,4,5,6,7,8,9,10'
},
-- 升级盟军航天中心，有以下效果：2. 能增加副本内医院容量（百分比，与moon_war_battle表的hospitalCapacity的增加值呈线性叠加关系）
['hospitalCapacityAdd'] ={
     key = 'hospitalCapacityAdd',
     value = '5,10,15,20,25,30,35,40,45,50'
},
-- 玩家一天内攻打幽灵基地获得军功次数由无上限调整为限X次
['foggyLimit'] ={
     key = 'foggyLimit',
     value = 3
},
-- 玩家一天内攻打幽灵基地车身获得军功次数由无上限调整为限X次
['foggyAssembleLimit'] ={
     key = 'foggyAssembleLimit',
     value = 10
},
-- 玩家一天内攻打幽灵基地车头获得军功次数由无上限调整为限X次
['foggyStartAssembleLimit'] ={
     key = 'foggyStartAssembleLimit',
     value = 3
},
}
return moon_war_battle_conf
