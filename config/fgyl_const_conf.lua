local fgyl_const_conf = {
-- 报名时间
['warTimeHour'] ={
     key = 'warTimeHour',
     value = '11:00_11:30_12:00_12:30_13:00_13:30_14:00_14:30_15:00_15:30_16:00_16:30_17:00_17:30_18:00_18:30_19:00_19:30_20:00_20:30_21:00_21:30_22:00'
},
-- 玩家主动退出副本,或弹出结算界面之后 多长时间强制回签原服 （时间短点比较好）
['forceMoveBackTime'] ={
     key = 'forceMoveBackTime',
     value = 10
},
-- 战斗开始前多少时间（秒）显示入口
['preCountingDown'] ={
     key = 'preCountingDown',
     value = 180
},
-- 地图大小
['mapSize'] ={
     key = 'mapSize',
     value = '150_300'
},
-- 界面展示的奖励内容
['rewardShow'] ={
     key = 'rewardShow',
     value = '30000_21065001_0,30000_21063005_0,30000_1782103_0,30000_800002_0,30000_1800001_0'
},
-- 比赛时长，单位：秒
['battleTime'] ={
     key = 'battleTime',
     value = 1800
},
-- 打怪次数上限
['monsterAttackMax'] ={
     key = 'monsterAttackMax',
     value = 50
},
-- 打怪次数上限
['monsterKillMax'] ={
     key = 'monsterKillMax',
     value = 50
},
-- 提前报名时间，单位秒
['applyTime'] ={
     key = 'applyTime',
     value = 1800
},
-- 可挑战难度需要<=联盟最高通关难度+X
['levelUp'] ={
     key = 'levelUp',
     value = 3
},
-- 一期活动的挑战次数上限
['challengeNum'] ={
     key = 'challengeNum',
     value = 2
},
-- 机甲通用破损spine
['damageSpine'] ={
     key = 'damageSpine',
     value = 'FGYLBuild_Damaged'
},
-- 拍脸图弹出截止时间
['popUpBannerTime'] ={
     key = 'popUpBannerTime',
     value = 1739635199
}
}
return fgyl_const_conf
