local dyzz_out_tower_conf = {
-- 半径
['redis'] ={
     key = 'redis',
     value = 3
},
-- 控制时长
['controlCountDown'] ={
     key = 'controlCountDown',
     value = 60
},
-- 摧毁时长
['destroyCountDown'] ={
     key = 'destroyCountDown',
     value = 120
},
-- 加成
['collectBuffList'] ={
     key = 'collectBuffList',
     value = '9013_1500'
},
-- 坐标
['refreshPointA'] ={
     key = 'refreshPointA',
     value = '17_85,37_123'
},
-- 坐标
['refreshPointB'] ={
     key = 'refreshPointB',
     value = '33_35,53_67'
},
-- 攻击伤害
['atkVal'] ={
     key = 'atkVal',
     value = 0
},
-- 攻击间隔
['atkCd'] ={
     key = 'atkCd',
     value = 10000
},
}
return dyzz_out_tower_conf
