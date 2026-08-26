local cross_const_conf = {
-- 跨服操作冷却时间
['crossCd'] ={
     key = 'crossCd',
     value = 300
},
-- 相对于凌晨的前后多长时间不可操作进入跨服，退出跨服.
['unoperatorTime'] ={
     key = 'unoperatorTime',
     value = 900
},
-- 远征要塞icon-东部
['fortress_iconA'] ={
     key = 'fortress_iconA',
     value = 'Alliance_BuildIcon_Fort_01.png'
},
-- 远征要塞icon-南部
['fortress_iconB'] ={
     key = 'fortress_iconB',
     value = 'Alliance_BuildIcon_Fort_02.png'
},
-- 远征要塞icon-西部
['fortress_iconC'] ={
     key = 'fortress_iconC',
     value = 'Alliance_BuildIcon_Fort_03.png'
},
-- 远征要塞icon-北部
['fortress_iconD'] ={
     key = 'fortress_iconD',
     value = 'Alliance_BuildIcon_Fort_04.png'
},
-- 远征合金
['crossTechItem'] ={
     key = 'crossTechItem',
     value = '30000_15800003_1'
},
-- 投票服务器排行数量限制
['voteServerRankLimit'] ={
     key = 'voteServerRankLimit',
     value = 1000
},
-- 单次充能消耗
['chargeCost'] ={
     key = 'chargeCost',
     value = '30000_15800001_1'
},
-- 单次充能值
['chargeValue'] ={
     key = 'chargeValue',
     value = 1
},
-- 充能上限(有区分先到到这个值，就开始投票)
['chargeMax'] ={
     key = 'chargeMax',
     value = 20000
},
-- 胜利区服总统票数
['presidentVoteCount'] ={
     key = 'presidentVoteCount',
     value = 100
},
-- 交税时间间隔(s)
['taxPeroid'] ={
     key = 'taxPeroid',
     value = 7200
},
-- 税率(万分比)
['taxRate'] ={
     key = 'taxRate',
     value = 1000
},
-- 税收记录条数限制
['taxRecordLimit'] ={
     key = 'taxRecordLimit',
     value = 50
},
-- 税收奖励接收次数限制
['taxReceiveTimesLimit'] ={
     key = 'taxReceiveTimesLimit',
     value = 10
},
-- 税收奖励接收数量限制
['taxReceiveCountLimit'] ={
     key = 'taxReceiveCountLimit',
     value = 5000000
},
-- 医院恢复一次伤兵所需道具（恢复一个死兵的消耗）
['hospitalRecoverCost'] ={
     key = 'hospitalRecoverCost',
     value = '30000_15800002_1'
},
-- 王战战斗时间(s)
['presidentWarFareTime'] ={
     key = 'presidentWarFareTime',
     value = 7200
},
-- 本服税收比例（万分比）
['taxRateOwnServer'] ={
     key = 'taxRateOwnServer',
     value = 1000
},
-- 远征要塞开始时间(秒)
['fightFortressStartTime'] ={
     key = 'fightFortressStartTime',
     value = 86400
},
-- 奖励预览展示的奖励内容
['rewardShow'] ={
     key = 'rewardShow',
     value = '30000_15800003_500,30000_1000005_30,30000_1300037_70,30000_21065001_70,30000_802005_600'
},
-- 出战联盟数量
['fightGuildCount'] ={
     key = 'fightGuildCount',
     value = 20
},
-- 拍脸图弹出的结束时间
['CrossServerShowTime'] ={
     key = 'CrossServerShowTime',
     value = 1660406400
},
-- 国家集结、盟主申请CD，单位：秒
['crossRallyApplicationCd'] ={
     key = 'crossRallyApplicationCd',
     value = 1800
},
-- 国家集结、司令邀请CD，单位：秒
['crossRallyInviteCd'] ={
     key = 'crossRallyInviteCd',
     value = 1800
},
-- 自由军修改电塔归属CD，单位：秒
['changeBelongCd'] ={
     key = 'changeBelongCd',
     value = 300
},
-- 跨服盟总胜利后，国家军成员奖励
['crossRallyWinReward'] ={
     key = 'crossRallyWinReward',
     value = '30000_1800001_28,30000_1300017_288,30000_800002_288,30000_810001_18,30000_850006_8,30000_850036_8,30000_850016_8,30000_850026_8'
},
-- 跨服盟总争夺进度条总长度
['crossProgressTotal'] ={
     key = 'crossProgressTotal',
     value = 36000000
},
-- 跨服盟总争夺征服方初始长度
['crossAttackInit'] ={
     key = 'crossAttackInit',
     value = 18000000
},
-- 跨服盟总争夺征服方推条速度，单位秒
['crossAttackSpeed'] ={
     key = 'crossAttackSpeed',
     value = 10000
},
-- 跨服盟总争夺防守方推条速度，单位秒
['crossDefenseSpeed'] ={
     key = 'crossDefenseSpeed',
     value = 10000
},
-- 赛季航海之星道具ID
['seasonStarItem'] ={
     key = 'seasonStarItem',
     value = 21063075
},
-- 能量塔排行榜最多展示多少
['fortressRankMax'] ={
     key = 'fortressRankMax',
     value = 100
},
}
return cross_const_conf
