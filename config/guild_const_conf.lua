local guild_const_conf = {
-- 联盟商店购买记录上限
['allianceStorePurchaseConfigLimit'] ={
     key = 'allianceStorePurchaseConfigLimit',
     value = 100
},
-- 堡垒名称最短最长（字符数）
['manorNameMinMax'] ={
     key = 'manorNameMinMax',
     value = '4_12'
},
-- 联盟名称最短最长（字符数）
['allianceNameMinMax'] ={
     key = 'allianceNameMinMax',
     value = '3_14'
},
-- 联盟缩写长度
['guildTagLength'] ={
     key = 'guildTagLength',
     value = 3
},
-- 联盟阶级名字限制
['allianceMemberLevelNameLenMax'] ={
     key = 'allianceMemberLevelNameLenMax',
     value = '4_12'
},
-- 联盟公开招募花费
['publicRecruitCost'] ={
     key = 'publicRecruitCost',
     value = '10000_1001_300'
},
-- 花费金币创建联盟等级
['createGuildCostGoldLevel'] ={
     key = 'createGuildCostGoldLevel',
     value = 999
},
-- 创建联盟花费金币
['createGuildCostGold'] ={
     key = 'createGuildCostGold',
     value = 300
},
-- 创建默认人数上限
['guildMemberNormalMaxNum'] ={
     key = 'guildMemberNormalMaxNum',
     value = 200
},
-- 改变联盟名称
['changeGuildNameGold'] ={
     key = 'changeGuildNameGold',
     value = 100
},
-- 改变联盟旗帜
['changeGuildFlagGold'] ={
     key = 'changeGuildFlagGold',
     value = 100
},
-- 改变联盟简称
['changeGuildTagGold'] ={
     key = 'changeGuildTagGold',
     value = 200
},
-- 联盟宣言长度
['guildDeclarationLength'] ={
     key = 'guildDeclarationLength',
     value = 200
},
-- 联盟留言字符限制
['allianceLeaveMsgLenMax'] ={
     key = 'allianceLeaveMsgLenMax',
     value = 200
},
-- 加入联盟时间间隔
['allianceJoinCooldownTime'] ={
     key = 'allianceJoinCooldownTime',
     value = 0
},
-- 推荐联盟成员人数百分比
['recommendAllianceMemberPercent'] ={
     key = 'recommendAllianceMemberPercent',
     value = 40
},
-- 联盟留言保存条数
['allianceLeaveMsgSave'] ={
     key = 'allianceLeaveMsgSave',
     value = 50
},
-- 联盟日志保存条数
['allianceDiarySave'] ={
     key = 'allianceDiarySave',
     value = 100
},
-- 宝藏自动刷新时间
['refreshTime'] ={
     key = 'refreshTime',
     value = 3600
},
-- 宝藏挖掘次数上限
['excavateNumber'] ={
     key = 'excavateNumber',
     value = 8
},
-- 宝藏挖掘恢复时间
['excavateTime'] ={
     key = 'excavateTime',
     value = 7200
},
-- 宝藏帮助次数上限
['helpNumber'] ={
     key = 'helpNumber',
     value = 10
},
-- 宝藏帮助恢复时间
['helpTime'] ={
     key = 'helpTime',
     value = 7200
},
-- 宝藏付费挖掘消耗
['excavateCost'] ={
     key = 'excavateCost',
     value = '10000_1001_10'
},
-- 首次固定刷新宝藏
['firstStorehouse'] ={
     key = 'firstStorehouse',
     value = '112_121_111'
},
-- 宝藏限制时间
['storehouseLimitTime'] ={
     key = 'storehouseLimitTime',
     value = 0
},
-- 免费次数恢复时间
['freeOfchargeTime'] ={
     key = 'freeOfchargeTime',
     value = 7200
},
-- 资源捐献暴击权重
['resourceCrit'] ={
     key = 'resourceCrit',
     value = '1_7324,2_1046,3_657,5_487,10_268,15_218'
},
-- 水晶捐献暴击权重
['crystalCrit'] ={
     key = 'crystalCrit',
     value = '5_6000,10_2000,15_1000,20_600,25_400'
},
-- 资源捐献次数上限
['resourceDonateNumber'] ={
     key = 'resourceDonateNumber',
     value = 20
},
-- 资源捐献恢复时间
['resourceDonateTime'] ={
     key = 'resourceDonateTime',
     value = 1800
},
-- 参数C0
['donateParameter0'] ={
     key = 'donateParameter0',
     value = 2
},
-- 参数C1
['donateParameter1'] ={
     key = 'donateParameter1',
     value = 2
},
-- 参数C2
['donateParameter2'] ={
     key = 'donateParameter2',
     value = 0
},
-- 捐献重置时间
['donateRefreshTime'] ={
     key = 'donateRefreshTime',
     value = 0
},
-- 重置次数消耗
['donateResetCost'] ={
     key = 'donateResetCost',
     value = '10000_1001_50'
},
-- vip0级可重置次数
['donateResetLimit'] ={
     key = 'donateResetLimit',
     value = 99
},
-- 推荐上限
['recommendUpLimit'] ={
     key = 'recommendUpLimit',
     value = 2
},
-- 领地范围
['manorRadius'] ={
     key = 'manorRadius',
     value = 12
},
-- 箭塔范围
['towerRadius'] ={
     key = 'towerRadius',
     value = 5
},
-- 建造速度参数1
['buildSpeedparameter1'] ={
     key = 'buildSpeedparameter1',
     value = 0.32
},
-- 建造速度参数2
['buildSpeedparameter2'] ={
     key = 'buildSpeedparameter2',
     value = 0.000004
},
-- 摧毁速度参数1
['destroySpeedparameter'] ={
     key = 'destroySpeedparameter',
     value = 0.5
},
-- 联盟仓库存储、取出行军速度
['warehouseMarchSpeed'] ={
     key = 'warehouseMarchSpeed',
     value = 10
},
-- 联盟名字长度区间
['allianceBastionNameMinMax'] ={
     key = 'allianceBastionNameMinMax',
     value = '4_12'
},
-- 联盟领地建筑缺省速度
['defaultBuildSpeed'] ={
     key = 'defaultBuildSpeed',
     value = 5
},
-- 铲地每日次数
['clearMaxNum'] ={
     key = 'clearMaxNum',
     value = 20
},
-- 铲地消耗
['clearResCost'] ={
     key = 'clearResCost',
     value = '10000_1001_100'
},
-- 联盟邀请函每天收到的上限
['allianceInvitationNum'] ={
     key = 'allianceInvitationNum',
     value = 5
},
-- 收到邀请函后，多少秒内不会再收取到
['allianceInvitationCD'] ={
     key = 'allianceInvitationCD',
     value = 3600
},
-- 收到邀请函的实际，登录后的x秒后
['allianceInvitationTime'] ={
     key = 'allianceInvitationTime',
     value = '60_300'
},
-- 大本等级高于配置，才会收到邀请函
['allianceInvitationCityLevel'] ={
     key = 'allianceInvitationCityLevel',
     value = 6
},
-- 联盟礼物上限
['allianceGiftUpLimit'] ={
     key = 'allianceGiftUpLimit',
     value = 300
},
-- 礼物消失时间（单位秒）
['allianceGiftDisappearTime'] ={
     key = 'allianceGiftDisappearTime',
     value = 86400
},
-- 联盟标记x轴偏移
['allianceSignOffsetX'] ={
     key = 'allianceSignOffsetX',
     value = 1
},
-- 联盟标记y轴偏移
['allianceSignOffsetY'] ={
     key = 'allianceSignOffsetY',
     value = 0
},
-- # 盟主取代功能时间(4阶以上可取代)
['leaderReplaceTime1'] ={
     key = 'leaderReplaceTime1',
     value = 259200
},
-- # 盟主取代功能时间(所有成员可取代)
['leaderReplaceTime2'] ={
     key = 'leaderReplaceTime2',
     value = 345600
},
-- # 取代盟主所需费用
['leaderReplaceCost'] ={
     key = 'leaderReplaceCost',
     value = '10000_1001_100'
},
-- 限制每日可领取悬赏金的上限
['beatbackMaxDailyBouns'] ={
     key = 'beatbackMaxDailyBouns',
     value = 400
},
-- 单个悬赏金额上限
['beatbackMaxBouns'] ={
     key = 'beatbackMaxBouns',
     value = 200
},
-- 插旗活动-小旗辐射半径
['warFlagRadius'] ={
     key = 'warFlagRadius',
     value = 5
},
-- 插旗活动-母旗辐射半径
['warBigFlagRadius'] ={
     key = 'warBigFlagRadius',
     value = 13
}
}
return guild_const_conf
