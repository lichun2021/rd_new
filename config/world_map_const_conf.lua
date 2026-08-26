local world_map_const_conf = {
-- 世界地图最大坐标(x_y)
['worldMaxXy'] ={
     key = 'worldMaxXy',
     value = '760_1520'
},
-- 泰伯利亚最大坐标(x_y)
['TBLYMaxXy'] ={
     key = 'TBLYMaxXy',
     value = '152_300'
},
-- 赛博之战最大坐标(x_y)
['CyborgMaxXy'] ={
     key = 'CyborgMaxXy',
     value = '92_180'
},
-- 世界地图中心点坐标(x_y)
['worldCentreXy'] ={
     key = 'worldCentreXy',
     value = '380_760'
},
-- 世界禁区范围（中心坐标为起点，按坐标配半径，x半径_y半径）
['worldBanXy'] ={
     key = 'worldBanXy',
     value = '11_11'
},
-- 世界核心区坐标（中心坐标为起点，按坐标配半径，x半径_y半径）
['worldCoreXy'] ={
     key = 'worldCoreXy',
     value = '51_51'
},
-- 5V5地图大小
['DYZZMaxXy'] ={
     key = 'DYZZMaxXy',
     value = '82_160'
},
-- 
['broadCastWid'] ={
     key = 'broadCastWid',
     value = 10
},
-- 
['broadCastLen'] ={
     key = 'broadCastLen',
     value = 20
},
-- 资源带宽度（从里到外，6-1，每个资源带配左上和右下坐标）
['worldResLevelRange'] ={
     key = 'worldResLevelRange',
     value = '315_630,445_890;252_504,508_1016;189_378,571_1142;126_252,634_1268;63_126,697_1394;0_0,760_1520'
},
-- 资源刷新区块边长，坐标
['worldResRefreshRange'] ={
     key = 'worldResRefreshRange',
     value = '200_100'
},
-- 世界资源刷新比例权重[资源参数_权重,资源参数_权重,资源参数_权重]
['worldResRefreshRatio'] ={
     key = 'worldResRefreshRatio',
     value = '1007_2,1008_2,1009_1,1010_1'
},
-- 刷新随机宝箱总数(野怪数/区域内格子总数)（用前除以1000）
['worldBoxRefreshMax'] ={
     key = 'worldBoxRefreshMax',
     value = 2
},
-- 刷新据点总数(据点数/区域内格子总数)（用前除以1000）
['worldStrongpointRefreshMax'] ={
     key = 'worldStrongpointRefreshMax',
     value = 15
},
-- 刷新野怪总数(野怪数/区域内格子总数)（用前除以1000）
['worldEnemyRefreshMax'] ={
     key = 'worldEnemyRefreshMax',
     value = 40
},
-- 刷新资源点总数[黄金、石油、合金、铀矿](资源数/区域内格子总数)（用前除以1000）
['worldResRefreshMax'] ={
     key = 'worldResRefreshMax',
     value = 80
},
-- 刷新尤里叛军总数(野怪数/区域内格子总数)（用前除以1000）
['worldYuriSoldierRefreshMax'] ={
     key = 'worldYuriSoldierRefreshMax',
     value = 20
},
-- 刷新精英野怪总数(野怪数/区域内格子总数)（用前除以1000）
['worldSuperEnemyRefreshMax'] ={
     key = 'worldSuperEnemyRefreshMax',
     value = 0
},
-- 新版野怪多次攻击的次数
['newMonsterAttackNumber'] ={
     key = 'newMonsterAttackNumber',
     value = 5
},
-- 新手阶段等级（城市等级）（出生、清除道具、跨服，无盟被集结）
['stepCityLevel1'] ={
     key = 'stepCityLevel1',
     value = 8
},
-- 玩家城市等级≥多少，可以生产和采集高级资源，可采集铀矿等级_可采集合金等级
['stepCityLevel2'] ={
     key = 'stepCityLevel2',
     value = '15_10'
},
-- 影响服务器进程的玩家数量（世界资源点等级提升，世界开刷铀矿）
['affectPlayersNum'] ={
     key = 'affectPlayersNum',
     value = 50
},
-- 进攻活动野怪部队损耗率（千分比）
['actWildMonsterLossRate'] ={
     key = 'actWildMonsterLossRate',
     value = 80
},
-- 一个地图块上尤里的最大数量
['yuriRefreshMax'] ={
     key = 'yuriRefreshMax',
     value = 10
},
-- 一个地图块上暴怒的尤里最大数量
['angryYuriRefreshMax'] ={
     key = 'angryYuriRefreshMax',
     value = 3
},
-- 尤里刷新时间间隔（单位：秒）
['yuriRefreshCd'] ={
     key = 'yuriRefreshCd',
     value = 600
},
-- 暴怒的尤里刷新时间间隔（单位：秒）
['angryYuriRefreshCd'] ={
     key = 'angryYuriRefreshCd',
     value = 7200
},
-- 击杀尤里积分
['yuriKillPoints'] ={
     key = 'yuriKillPoints',
     value = 10
},
-- 击杀暴怒的尤里积分
['angryYuriKillPoints'] ={
     key = 'angryYuriKillPoints',
     value = 100
},
-- 领取随机宝箱时间间隔（单位：秒）
['randomBoxGetCd'] ={
     key = 'randomBoxGetCd',
     value = 3600
},
-- 领取随机宝箱的行军速度（行进坐标/秒）（标准为5秒走1坐标）（用前除1000）
['randomBoxMarchSpeed'] ={
     key = 'randomBoxMarchSpeed',
     value = 50
},
-- 刷新尤里总数(野怪数/区域内格子总数)（用前除以1000）
['worldNormalYuriRefreshMax'] ={
     key = 'worldNormalYuriRefreshMax',
     value = 5
},
-- 刷新暴怒的尤里总数(野怪数/区域内格子总数)（用前除以1000）
['worldAngryYuriRefreshMax'] ={
     key = 'worldAngryYuriRefreshMax',
     value = 2
},
-- 迷雾要塞总数(野怪数/区域内格子总数)（用前除以1000）
['worldfoggyFortressRefreshMax'] ={
     key = 'worldfoggyFortressRefreshMax',
     value = 15
},
-- 随机刷新城点的规则不可进入范围（中心坐标为起点，按坐标配半径，x半径_y半径）
['worldCityBanRange'] ={
     key = 'worldCityBanRange',
     value = '69_69'
},
-- 清除城点条件（city＜6|city≥6）（天）
['worldCityCleanTime'] ={
     key = 'worldCityCleanTime',
     value = '3_15'
},
-- 开服新手期(秒)
['newPlayerTime'] ={
     key = 'newPlayerTime',
     value = 432000
},
-- 收藏坐标最大数量
['favoriteMax'] ={
     key = 'favoriteMax',
     value = 100
},
-- 玩家基地被攻破效果持续时间
['mcvBrokenTime'] ={
     key = 'mcvBrokenTime',
     value = 300
},
-- 
['territoryDisplayLowLimit'] ={
     key = 'territoryDisplayLowLimit',
     value = 5
},
-- 搜索次数大于配置则重置
['searchNumberMax'] ={
     key = 'searchNumberMax',
     value = 10
},
-- 哪些基地外显特效需要播放（如飘加号，飘护盾）
['baseShow'] ={
     key = 'baseShow',
     value = '100,103,437,200,325'
},
-- 探索时间，单位s
['exploreTime'] ={
     key = 'exploreTime',
     value = '900_3600_14400_28800'
},
-- 帝陵自刷新时间，单位s，在时间范围内随机一个只
['disappearTime'] ={
     key = 'disappearTime',
     value = '57600_86400'
},
-- 单次tick奖励权重（经验_资源_道具）
['yuriRevengeWeight'] ={
     key = 'yuriRevengeWeight',
     value = '46_46_8'
},
-- 探索获取经验系数
['expCoefficient'] ={
     key = 'expCoefficient',
     value = 5
},
-- 探索获取资源系数
['resourceCoefficient'] ={
     key = 'resourceCoefficient',
     value = 5
},
-- 探索获取奖励id
['yuriRevengeAward'] ={
     key = 'yuriRevengeAward',
     value = 2500100
},
-- 探索奖励tick时间，单位：秒
['yuriSearchPeriod'] ={
     key = 'yuriSearchPeriod',
     value = 180
},
-- 玩家战胜尤里比例
['yuriSucceedRate'] ={
     key = 'yuriSucceedRate',
     value = 5000
},
-- 尤里失败n次结束活动
['yuriFailTimes'] ={
     key = 'yuriFailTimes',
     value = 2
},
-- hud被遮挡的移动时间，单位s
['worldHudMoveTime'] ={
     key = 'worldHudMoveTime',
     value = 0.35
},
-- 城外hud——上方截取长度
['worldHudWindowTopBorder'] ={
     key = 'worldHudWindowTopBorder',
     value = 146
},
-- 城外hud——下方截取长度
['worldHudWindowBottomBorder'] ={
     key = 'worldHudWindowBottomBorder',
     value = 150
},
-- 城外hud——中心上方长度
['worldHudTopHeight'] ={
     key = 'worldHudTopHeight',
     value = 64
},
-- 城外hud——中心宽度
['worldHudWidth'] ={
     key = 'worldHudWidth',
     value = 230
},
-- 城外hud——中心下方长度
['worldHudBottomHeight'] ={
     key = 'worldHudBottomHeight',
     value = 254
},
-- 保护罩道具id，应用于战争狂热状态的前段检测
['protectCoverId'] ={
     key = 'protectCoverId',
     value = '30000_830000_1,30000_830001_1,30000_830002_1,30000_830003_1,30000_830004_1,30000_830005_1,30000_830006_1'
},
-- 保护罩effectId，应用于战争狂热状态的前段检测
['protectCoverEffectId'] ={
     key = 'protectCoverEffectId',
     value = 440
},
-- 侦察红色标记
['WorldPointSpyTimeMax'] ={
     key = 'WorldPointSpyTimeMax',
     value = 1800
},
-- 侦察黄色标记
['WorldPointSpyTimeMid'] ={
     key = 'WorldPointSpyTimeMid',
     value = 1200
},
-- 侦察绿色标记
['WorldPointSpyTimeMin'] ={
     key = 'WorldPointSpyTimeMin',
     value = 600
},
-- 联盟建筑成员坑位
['allianceBuildingMember'] ={
     key = 'allianceBuildingMember',
     value = 30
},
-- 联盟指针消失距离
['allianceManorPointDistance'] ={
     key = 'allianceManorPointDistance',
     value = 25
},
-- 联盟指针消失基地等级
['allianceManorPointCityLevel'] ={
     key = 'allianceManorPointCityLevel',
     value = 18
},
-- 圣诞最大BOSS刷新限制
['christmasRefreshLimit'] ={
     key = 'christmasRefreshLimit',
     value = 20
},
-- 圣诞刷新时间
['christmasRefreshTime'] ={
     key = 'christmasRefreshTime',
     value = 19
},
-- 圣诞生成坐标
['christmasRefreshPos'] ={
     key = 'christmasRefreshPos',
     value = '252,504;504,504;252,1008;504,1008;378,252;126,756;630,756;378,1260'
},
-- 圣诞刷新区域半径(以坐标为中心，半径内区域随机生成点)
['christmasRefreshAreaRadius'] ={
     key = 'christmasRefreshAreaRadius',
     value = 25
},
-- 雪球炸弹攻击距离限制
['snowballAtkDistance'] ={
     key = 'snowballAtkDistance',
     value = 30
},
-- 雪球炸弹攻击CD限制
['snowballAtkCd'] ={
     key = 'snowballAtkCd',
     value = 5
},
-- 世界霸主地图
['StarWarMaxXy'] ={
     key = 'StarWarMaxXy',
     value = '360_720'
},
-- 删除圣诞世界boss的时间（秒）
['christmasRemoveTime'] ={
     key = 'christmasRemoveTime',
     value = 3600
},
-- 月球之战地图大小
['YQZZMaxXy'] ={
     key = 'YQZZMaxXy',
     value = '605_1207'
},
-- 星海激战地图大小
['XHJZMaxXy'] ={
     key = 'XHJZMaxXy',
     value = '217_411'
},
-- GVE地图大小
['FGYLMaxXy'] ={
     key = 'FGYLMaxXy',
     value = '150_300'
},
-- 先驱回向地图大小
['XQHXMaxXy'] ={
     key = 'XQHXMaxXy',
     value = '150_300'
},
-- 时空屏障小于这个配置的值（分钟）传递正常的数值，大于这个值则前端无法得到剩余护盾的实际数值
['pointProtectNotify '] ={
     key = 'pointProtectNotify ',
     value = 19
}
,
['XQHXMaxXy'] ={
     key = 'XQHXMaxXy',
     value = '217_411'
},
}
return world_map_const_conf
