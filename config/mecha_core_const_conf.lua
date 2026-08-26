local mecha_core_const_conf = {
-- 开放vip等级
['vipLimit'] ={
     key = 'vipLimit',
     value = 0
},
-- 开放基地等级
['baseLimit'] ={
     key = 'baseLimit',
     value = 35
},
-- 开服时间
['serverDelay'] ={
     key = 'serverDelay',
     value = 1
},
-- 机甲核心开放
['superSoldierCoreOpen'] ={
     key = 'superSoldierCoreOpen',
     value = 1
},
-- 模块抽奖开放时间戳
['superSoldierDrawOpen'] ={
     key = 'superSoldierDrawOpen',
     value = 1750003200
},
-- 抽奖，装配槽界面开放时间戳
['moduleOpenTime'] ={
     key = 'moduleOpenTime',
     value = 1744819200
},
-- 科技装配开放科技品阶
['moduleLoadRankLimit'] ={
     key = 'moduleLoadRankLimit',
     value = 10
},
-- 抽奖功能开放科技品阶
['drawRankLimit'] ={
     key = 'drawRankLimit',
     value = 10
},
-- 免费抽奖次数
['freeDrawTimes'] ={
     key = 'freeDrawTimes',
     value = 5
},
-- 免费抽奖cd（分钟）
['freeDrawCD'] ={
     key = 'freeDrawCD',
     value = 5
},
-- 每日抽奖次数上限
['drawTimesLimit'] ={
     key = 'drawTimesLimit',
     value = 999
},
-- 抽奖赠送道具
['extReward'] ={
     key = 'extReward',
     value = '30000_840172_1'
},
-- 提升核心等级道具
['coreTechnologyLevelUpItem'] ={
     key = 'coreTechnologyLevelUpItem',
     value = '30000_21070056_1'
},
-- 突破核心阶位的道具
['coreTechnologyRankUpItem'] ={
     key = 'coreTechnologyRankUpItem',
     value = '30000_21070056_1'
},
-- 升级核心槽道具
['moduleSlotUpItem'] ={
     key = 'moduleSlotUpItem',
     value = '30000_21070057_1'
},
-- 传承消耗道具
['inheritConsumeItem'] ={
     key = 'inheritConsumeItem',
     value = '30000_21070058_1'
},
-- 抽取模块的道具
['moduleDrawItem'] ={
     key = 'moduleDrawItem',
     value = '30000_21070056_1'
},
-- 批量获取模块次数上限
['batchDrawMax'] ={
     key = 'batchDrawMax',
     value = 100
},
-- 自动分解配置
['breakDownQuality'] ={
     key = 'breakDownQuality',
     value = '3,4,5'
},
-- 模块背包中的存储上限
['moduleMaxCount'] ={
     key = 'moduleMaxCount',
     value = 1000
},
-- 槽位等级上限
['maxSlotLevel'] ={
     key = 'maxSlotLevel',
     value = 100
}
}
return mecha_core_const_conf
