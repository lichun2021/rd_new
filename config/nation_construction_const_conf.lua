local nation_construction_const_conf = {
-- 任务刷新数量
['refreshTimes'] ={
     key = 'refreshTimes',
     value = 3
},
-- 任务刷新消耗
['refreshCost'] ={
     key = 'refreshCost',
     value = '10000_1001_50'
},
-- 每日完成任务次数上限
['timesLimit'] ={
     key = 'timesLimit',
     value = 5
},
-- 任务刷新次数上限
['refreshTimesLimit'] ={
     key = 'refreshTimesLimit',
     value = 99999
},
-- 任务需求兵数
['taskSoldiers'] ={
     key = 'taskSoldiers',
     value = 20000
},
-- 任务需求兵数描述
['taskSoldiersDes'] ={
     key = 'taskSoldiersDes',
     value = 'taskSoldiersDes_01'
},
-- 任务行军时间（秒）
['marchTime'] ={
     key = 'marchTime',
     value = 60
},
-- 建筑与个人建设道具对应关系
['buildItemContact'] ={
     key = 'buildItemContact',
     value = '1_500101,2_500102,3_500103,4_500104,5_500105,6_500106,7_500107,8_500108'
},
-- 重建值上限
['rebuildingLimit'] ={
     key = 'rebuildingLimit',
     value = 200000
},
-- 捐献奖励
['rebuildingAward'] ={
     key = 'rebuildingAward',
     value = '30000_1300017_5'
},
-- 每日捐献次数上限
['rebuildingCountLimit'] ={
     key = 'rebuildingCountLimit',
     value = 20
},
-- 捐献消耗资源量
['consumeRes'] ={
     key = 'consumeRes',
     value = '10000_1007_100000;10000_1008_100000;10000_1010_16000;10000_1009_4000'
},
-- 单次捐献转化重建值
['rebuildVal'] ={
     key = 'rebuildVal',
     value = 10
},
-- 资助获得建设值
['supportBuilding'] ={
     key = 'supportBuilding',
     value = 200
},
-- 资助获得建设值展示
['supportBuildingShow'] ={
     key = 'supportBuildingShow',
     value = '30000_500110_200'
},
-- 每日资助次数上限
['supportLimit'] ={
     key = 'supportLimit',
     value = 1
},
-- 单次资助消耗
['supportCost'] ={
     key = 'supportCost',
     value = '10000_1000_1000'
},
-- 单次资助转换国家金条
['supportGold'] ={
     key = 'supportGold',
     value = 500
},
-- 资助奖励
['supportAward'] ={
     key = 'supportAward',
     value = '30000_500402_1000'
},
-- 捐献消耗KVK道具
['warehouseConsumeRes'] ={
     key = 'warehouseConsumeRes',
     value = '30000_500301_1;30000_500302_1;30000_500303_1;30000_500304_1'
},
-- 单次捐献上限
['warehouseConsumeLimit'] ={
     key = 'warehouseConsumeLimit',
     value = 2000000000
},
-- 单次捐献个人奖励
['warehouseAward'] ={
     key = 'warehouseAward',
     value = '30000_500402_5,30000_500403_1'
},
-- 商店栏位文本（前端展示）
['militaryShopName'] ={
     key = 'militaryShopName',
     value = '1|@militaryType_01;2|@militaryType_02;3|@militaryType_03;4|@militaryType_04;5|@militaryType_05;6|@militaryType_06;7|@militaryType_07;8|@militaryType_08'
},
-- 商店兑换所需道具（前端展示）
['exchangeItemShow'] ={
     key = 'exchangeItemShow',
     value = '30000_500402_1'
},
-- 可删除死兵等级
['deleteSoldierLevel'] ={
     key = 'deleteSoldierLevel',
     value = '1_14'
},
-- 可删除死兵等级描述
['deleteSoldierDes'] ={
     key = 'deleteSoldierDes',
     value = 'deleteSoldierDes_01'
},
-- 泰能兵死兵恢复特殊处理
['specialSoldierType'] ={
     key = 'specialSoldierType',
     value = '13_14'
},
-- 泰能兵死兵恢复特殊处理时间
['specialSoldierTime'] ={
     key = 'specialSoldierTime',
     value = 445
},
-- 死兵存储提醒比例-万分比
['soldierTipsProp'] ={
     key = 'soldierTipsProp',
     value = 5000
},
-- 任务刷新时间（秒）
['missionRefreshTime'] ={
     key = 'missionRefreshTime',
     value = 3600
},
-- 任务列表上限
['missionTaskLimit'] ={
     key = 'missionTaskLimit',
     value = 15
},
-- 每日可购买任务数
['missionDayBuyTimes'] ={
     key = 'missionDayBuyTimes',
     value = 1
},
-- 购买任务消耗
['missionPrice'] ={
     key = 'missionPrice',
     value = '10000_1001_50'
},
-- 放弃任务时间间隔（秒）
['missionGiveupCd'] ={
     key = 'missionGiveupCd',
     value = 3600
},
-- 全服每日科技值上限
['missionWeekLimit'] ={
     key = 'missionWeekLimit',
     value = 10000
},
-- 科技单人助力时间(秒)
['assistTechTime'] ={
     key = 'assistTechTime',
     value = 60
},
-- 科技助力奖励
['assistTechAward'] ={
     key = 'assistTechAward',
     value = '30000_500401_1'
},
-- 科技助力每日上限时间（秒）
['assistTechLimit'] ={
     key = 'assistTechLimit',
     value = 6000
},
-- 科技放弃时间CD（秒）
['techGiveUpCD'] ={
     key = 'techGiveUpCD',
     value = 86400
},
-- 科技取消升级返回资源比例-万分比
['cancelTechReturn'] ={
     key = 'cancelTechReturn',
     value = 5000
},
-- 主动技能显示
['techSkillShow'] ={
     key = 'techSkillShow',
     value = '107,111,116,207,214,220'
},
-- 飞船单人助力时间(秒)
['assistTime'] ={
     key = 'assistTime',
     value = 60
},
-- 飞船助力奖励
['assistAward'] ={
     key = 'assistAward',
     value = '30000_500401_1'
},
-- 飞船助力每日上限时间（秒）
['assistLimit'] ={
     key = 'assistLimit',
     value = 6000
},
-- 改造放弃时间CD（秒）
['modelGiveUpCD'] ={
     key = 'modelGiveUpCD',
     value = 86400
},
-- 飞船预览界面模型缩放
['modelScaling'] ={
     key = 'modelScaling',
     value = 0.8
},
-- 飞船取消升级返回资源比例-万分比
['cancelReturn'] ={
     key = 'cancelReturn',
     value = 5000
},
-- 进入国家2阶段条件参数
['statusCondPara'] ={
     key = 'statusCondPara',
     value = '3,1,60'
}
}
return nation_construction_const_conf
