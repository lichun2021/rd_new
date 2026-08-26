local auto_mass_join_conf = {
-- 生效时间（秒）
['workTime'] ={
     key = 'workTime',
     value = 43200
},
-- 距离限制
['distanceLimit'] ={
     key = 'distanceLimit',
     value = 200
},
-- 集结时间限制
['massTimeLimit'] ={
     key = 'massTimeLimit',
     value = 60
},
-- 行军时间限制
['marchTimeLimit'] ={
     key = 'marchTimeLimit',
     value = 120
},
-- 随机英雄的品质
['heroQualityColor'] ={
     key = 'heroQualityColor',
     value = 4
},
-- 错误邮件的间隔
['missMailTime'] ={
     key = 'missMailTime',
     value = 28800
}
}
return auto_mass_join_conf
