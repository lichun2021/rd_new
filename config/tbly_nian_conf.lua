local tbly_nian_conf = {
-- 刷新时间点,游戏创建后,,/秒
['refreshTime'] ={
     key = 'refreshTime',
     value = '7200'
},
-- 刷新点 附近
['refreshPoint'] ={
     key = 'refreshPoint',
     value = '40_150,110_150'
},
-- 每次刷新数
['refreshCount'] ={
     key = 'refreshCount',
     value = 1
},
-- 每次击杀获得buff 按顺序1层
['buffList1'] ={
     key = 'buffList1',
     value = '102_5000,136_5000'
},
-- 每次击杀获得buff 按顺序2层
['buffList2'] ={
     key = 'buffList2',
     value = '100_5000,136_5000'
},
-- 每次击杀获得buff 按顺序3层
['buffList3'] ={
     key = 'buffList3',
     value = '100_5000,102_5000'
},
-- 每点血量折算联盟积分
['perHPGuildHonor'] ={
     key = 'perHPGuildHonor',
     value = 0
},
-- 每点血量折算个人积分
['perHPPlayerHonor'] ={
     key = 'perHPPlayerHonor',
     value = 0
},
-- 致命一击联盟积分
['onceKillGuildHonor'] ={
     key = 'onceKillGuildHonor',
     value = 400
},
-- 致命一击个人积分
['onceKillPlayerHonor'] ={
     key = 'onceKillPlayerHonor',
     value = 40
},
-- 击杀联盟积分
['killGuildHonor'] ={
     key = 'killGuildHonor',
     value = 1600
},
-- 击杀个人积分
['killPlayerHonor'] ={
     key = 'killPlayerHonor',
     value = 160
},
-- 伤害加成倍数(万分比)  
['hurtRate'] ={
     key = 'hurtRate',
     value = 10000
},
-- 集结扣血上限(万分比) 
['massKillLimit'] ={
     key = 'massKillLimit',
     value = 1200
},
-- 个人扣血上限(万分比) 
['signKillLimit'] ={
     key = 'signKillLimit',
     value = 400
},
}
return tbly_nian_conf
