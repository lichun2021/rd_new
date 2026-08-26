local xhjz_battle_conf = {
-- 返回时的行军加速
['playerMarchSpeedUp'] ={
     key = 'playerMarchSpeedUp',
     value = 30
},
-- 个人燃油上限
['fuelMax'] ={
     key = 'fuelMax',
     value = 2000
},
-- 移动1个岛屿消耗的燃油数(多个岛屿就是乘以这个值）
['fuelUseBase'] ={
     key = 'fuelUseBase',
     value = 10
},
-- 2个岛屿之间移动需要的时间
['marchBaseTime'] ={
     key = 'marchBaseTime',
     value = 5
},
-- 出征需要携带的燃油（不带这些油无法出征）
['fuelMarchNeed'] ={
     key = 'fuelMarchNeed',
     value = 300
},
-- 准备时间
['prepairTime'] ={
     key = 'prepairTime',
     value = 180
},
}
return xhjz_battle_conf
