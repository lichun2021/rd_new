local agency_const_conf = {
-- 首个事件的到达时间（秒）
['firstEventTime'] ={
     key = 'firstEventTime',
     value = 3
},
-- 每个任务增加的经验值
['eventExp'] ={
     key = 'eventExp',
     value = 1
},
-- 刷新CD
['refreshCd'] ={
     key = 'refreshCd',
     value = 28800
},
-- 事件的消失时间
['eventDisappearTime'] ={
     key = 'eventDisappearTime',
     value = 57600
},
-- 事件的体力消耗
['strengthConsume'] ={
     key = 'strengthConsume',
     value = 5
},
-- 开启实力验证的情报中心等级
['startSpecialLv'] ={
     key = 'startSpecialLv',
     value = 6
},
-- 事件刷新最大范围
['maxRange'] ={
     key = 'maxRange',
     value = '5_140'
},
-- 前X级自动升级，无需手动点击
['autoLevelUpLimit'] ={
     key = 'autoLevelUpLimit',
     value = 4
},
-- 情报中心开放需要大本等级
['agencyUnlockLevel'] ={
     key = 'agencyUnlockLevel',
     value = 3
},
-- 金色情报每天可刷新最大数量
['specialEventDailyRefreshLimit'] ={
     key = 'specialEventDailyRefreshLimit',
     value = 10
}
}
return agency_const_conf
