local armour_const_conf = {
-- 套装组合
['suit_combination'] ={
     key = 'suit_combination',
     value = '2_4_6_7'
},
-- 装备页签初始值
['armour_page_initial'] ={
     key = 'armour_page_initial',
     value = 1
},
-- 最大页签数
['armour_page_limit'] ={
     key = 'armour_page_limit',
     value = 8
},
-- 解锁页签消耗
['suitUnlockCost'] ={
     key = 'suitUnlockCost',
     value = '10000_1001_1000;10000_1001_1000;10000_1001_1000;10000_1001_1000;10000_1001_1000;10000_1001_1000;10000_1001_1000;10000_1001_1000'
},
-- 额外属性数量
['extraAttrCount'] ={
     key = 'extraAttrCount',
     value = '1_1,2_2,3_3,4_4,6_4'
},
--  铠甲套装名字长度
['suitNameLength'] ={
     key = 'suitNameLength',
     value = '1_8'
},
-- 套装最大数量
['suitMaxCount'] ={
     key = 'suitMaxCount',
     value = 8
},
-- 累计抽卡次数可开启宝箱
['gachaTimesBox'] ={
     key = 'gachaTimesBox',
     value = 50
},
-- 装备穿戴等级
['cityLevelUnlock'] ={
     key = 'cityLevelUnlock',
     value = 8
},
-- 铠甲背包上限
['armourMaxCount'] ={
     key = 'armourMaxCount',
     value = 1000
},
-- 每日抽取上限
['gacha_limit'] ={
     key = 'gacha_limit',
     value = 9999
},
-- 装备随机充能免费次数
['freeCharge'] ={
     key = 'freeCharge',
     value = 1
},
-- 装备随机充能金币消耗
['chargeRefreshConsume'] ={
     key = 'chargeRefreshConsume',
     value = '10000_1001_10;10000_1001_20;10000_1001_30;10000_1001_40;10000_1001_60;10000_1001_80;10000_1001_100'
},
-- 装备充能普通消耗
['chargeConsumeCommon'] ={
     key = 'chargeConsumeCommon',
     value = '30000_802002_1'
},
-- 装备充能高级消耗
['chargeConsumeSpecial'] ={
     key = 'chargeConsumeSpecial',
     value = '30000_802003_1'
},
-- 升星品质限制
['starQualityLimit'] ={
     key = 'starQualityLimit',
     value = '3,4,6'
},
-- 升星等级限制
['starLevelLimit'] ={
     key = 'starLevelLimit',
     value = 30
},
-- 泰能灌注（星级）额外属性数量
['starExtraAttrCount'] ={
     key = 'starExtraAttrCount',
     value = '1_0,2_0,3_2,4_3'
},
-- 装备普通充能限制
['chargeCommonLimit'] ={
     key = 'chargeCommonLimit',
     value = 2000
},
-- 装备高级充能限制
['chargeSpecialLimit'] ={
     key = 'chargeSpecialLimit',
     value = 10000
},
-- 装备普通充能
['chargeCommonRate'] ={
     key = 'chargeCommonRate',
     value = '1000_1;1000_2;1000_3;1000_4;1000_5;1000_6;1000_7;1000_8;1000_9'
},
-- 装备高级充能
['chargeSpecialRate'] ={
     key = 'chargeSpecialRate',
     value = '1000_6;1000_7;1000_8;1000_9;1000_10;1000_11;1000_12;1000_13;1000_14'
},
-- 解锁红装前置装备品质需求
['quantumQualityLimit'] ={
     key = 'quantumQualityLimit',
     value = 4
},
-- 解锁红装前置装备等级需求
['quantumLevelLimit'] ={
     key = 'quantumLevelLimit',
     value = 45
},
-- 解锁红装后额外可强化等级次数
['quantumLevelLimitAdd'] ={
     key = 'quantumLevelLimitAdd',
     value = 15
},
-- 槽位X级，橙装变红
['quantumRedLevel'] ={
     key = 'quantumRedLevel',
     value = 60
},
-- 解锁红装后额外可泰晶强化等级次数
['starLimitAdd'] ={
     key = 'starLimitAdd',
     value = 10
},
-- 装备红色充能限制(比值）
['chargeRedLimit'] ={
     key = 'chargeRedLimit',
     value = 22500
},
-- 装备红色充能进度/次
['chargeRedRate'] ={
     key = 'chargeRedRate',
     value = '1000_1;1000_2;1000_3;1000_4;1000_5;1000_6;1000_7;1000_8;1000_9'
},
-- 装备红色充能消耗
['chargeConsumeRed'] ={
     key = 'chargeConsumeRed',
     value = '30000_802004_1'
},
-- 装备红色品质基础属性品质系数
['breakGrowUpRed'] ={
     key = 'breakGrowUpRed',
     value = 80000
},
-- 解锁不朽装前置装备品质需求
['immortQualityLimit'] ={
     key = 'immortQualityLimit',
     value = 5
},
-- 解锁不朽装前置装备等级需求
['immortLevelLimit'] ={
     key = 'immortLevelLimit',
     value = 60
},
-- 解锁不朽装后额外可强化等级次数
['immortLevelLimitAdd'] ={
     key = 'immortLevelLimitAdd',
     value = 15
},
-- 不朽等级达到X红装变不朽品阶
['immortRedLevel'] ={
     key = 'immortRedLevel',
     value = 100
},
-- 解锁不朽装后额外可泰晶强化等级次数
['immortstarLimitAdd'] ={
     key = 'immortstarLimitAdd',
     value = 10
},
-- 装备不朽充能限制
['chargeimmortLimit'] ={
     key = 'chargeimmortLimit',
     value = 45000
},
-- 装备不朽充能进度/次
['chargeimmortRate'] ={
     key = 'chargeimmortRate',
     value = '1000_5;1000_6;1000_7;1000_8;1000_9;1000_10;1000_11;1000_12;1000_13'
},
-- 装备不朽充能消耗
['chargeConsumeimmort'] ={
     key = 'chargeConsumeimmort',
     value = '30000_802006_1'
},
-- 装备不朽品质基础属性品质系数
['breakGrowUpimmort'] ={
     key = 'breakGrowUpimmort',
     value = 80000
},
-- 锁定不朽随机词条消耗道具
['lockrandomattr1'] ={
     key = 'lockrandomattr1',
     value = '30000_802007_10,30000_802007_30,30000_802007_70'
},
-- 每次洗练消耗的道具数量
['randomitem'] ={
     key = 'randomitem',
     value = '30000_802006_10'
},
-- 每次洗练增加的熟练度
['randomexp'] ={
     key = 'randomexp',
     value = 1
},
-- 洗练升级经验(每级清零）
['randomlevel'] ={
     key = 'randomlevel',
     value = '1_20,2_30,3_40,4_50,5_60,6_70,7_80,8_90,9_100'
},
-- 不朽装备等级与解锁词条数量关系
['randomattrnumber'] ={
     key = 'randomattrnumber',
     value = '20_1,40_2,60_3,80_4,100_5'
},
-- 共鸣套装触发件数配置
['echo_combination'] ={
     key = 'echo_combination',
     value = '2_4_7'
}
}
return armour_const_conf
