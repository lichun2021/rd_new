local president_const_conf = {
-- 初始和平时间(s)
['initPeaceTime'] ={
     key = 'initPeaceTime',
     value = 1209600
},
-- 初始开始的星期数，只能配1-7
['initday'] ={
     key = 'initday',
     value = 4
},
-- 初始开始时间点（下午两点）
['initTime'] ={
     key = 'initTime',
     value = '20'
},
-- 占领首都到成为国王过度时间(s)
['occupationTime'] ={
     key = 'occupationTime',
     value = '1800'
},
-- 战争时期周期(s)
['warfareTime'] ={
     key = 'warfareTime',
     value = 7200
},
-- 国王战事件数量上限
['maxEventCount'] ={
     key = 'maxEventCount',
     value = 20
},
-- 最大历届国王数
['maxHistoryCount'] ={
     key = 'maxHistoryCount',
     value = 200
},
-- 国王战开启前邮件时间
['mailBeforeTime'] ={
     key = 'mailBeforeTime',
     value = '1800_600_300'
},
-- 国王战结束前邮件时间
['mailAfterTime'] ={
     key = 'mailAfterTime',
     value = '1800_600_120'
},
-- tick周期(ms)
['tickPeriod'] ={
     key = 'tickPeriod',
     value = 30000
},
-- 箭塔伤害（万分比）
['towerAtk'] ={
     key = 'towerAtk',
     value = 3
},
-- 资源比例改变cd
['changeResCd'] ={
     key = 'changeResCd',
     value = 86400
},
-- 资源比例改变系数
['changeResCoe'] ={
     key = 'changeResCoe',
     value = 3
},
-- 名字最长长度
['nameLengthLimit'] ={
     key = 'nameLengthLimit',
     value = 16
},
-- 王国名字和旗帜修改的次数
['countryModifyTimes'] ={
     key = 'countryModifyTimes',
     value = 1
},
-- 国王禁言时间
['broadCastBanTime'] ={
     key = 'broadCastBanTime',
     value = 28800
},
-- 国王征税比例（万分比）
['taxPercent'] ={
     key = 'taxPercent',
     value = 1000
},
-- 搜索玩家推送个数
['searchMaxCount'] ={
     key = 'searchMaxCount',
     value = 20
},
-- 总统任命时间
['appointTime'] ={
     key = 'appointTime',
     value = 1800
},
-- 司令赐福所用消费
['globalBuff'] ={
     key = 'globalBuff',
     value = '3605,10000_1001_200;3606,10000_1001_300;3607,10000_1001_200'
},
-- 司令宣言所用消费
['manifestoCost'] ={
     key = 'manifestoCost',
     value = '10000_1001_100'
}
}
return president_const_conf
