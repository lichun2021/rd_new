local simulate_war_const_conf = {
-- 总开关
['systemClose'] ={
     key = 'systemClose',
     value = 'FALSE'
},
-- 开服时长不足以参加本次活动
['serverDelay'] ={
     key = 'serverDelay',
     value = 604800
},
-- 参战最低大本等级限制
['cityLvlLimit'] ={
     key = 'cityLvlLimit',
     value = 15
},
-- 每条路的限制获取人数
['memberLimit'] ={
     key = 'memberLimit',
     value = 100
},
-- 成功参与玩法联盟最少的出战队伍
['minTeamNum'] ={
     key = 'minTeamNum',
     value = 45
},
-- 攻防模拟战最大助威次数
['maxEncourageTimes'] ={
     key = 'maxEncourageTimes',
     value = 80
}
}
return simulate_war_const_conf
