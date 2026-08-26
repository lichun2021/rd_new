local gm_conf = {
{
	typeStr = "addCrystal",
	name = "加金币",
},
{
	typeStr = "addDiamond",
	name = "加金条",
},
{
	typeStr = "addVip",
	name = "加贵族",
},
{
	typeStr = "addExp",
	name = "加经验",
},
{
    typeStr = 'setCanUseAddItemReq',
    name = "来源添加道具",
},
{
    typeStr = 'searchItemAndAddReq',
    name = "查找添加道具",
},
{
	typeStr = 'battleTestForTroop',
	name = "战斗测试(编队)",
},
{
	typeStr = "upgradeBuilding",
	name = "建筑满级"
},
{
	typeStr = "upgradeHonorTenBuilding",
	name = "荣耀10星"
},
{
	typeStr = "upgradeHonor15Building",
	name = "荣耀15星"
},
{
	typeStr = "upgradeHonor20Building",
	name = "荣耀20星(满级)"
},
{
    typeStr = 'addArmour',
    name = "加装备"
},
{
    typeStr = 'addArmourOneResearch',
    name = "装备一期研究"
},
{
    typeStr = 'addArmourTwoResearch',
    name = "装备二期研究"
},
{
	typeStr = "addSoldiers",
	name = "兵 来"
},
{
	typeStr = "clearSoldiers",
	name = "一键删兵"
},
{
	typeStr = 'personalProcessChapter',
	name = "里程碑"
},
{
	typeStr = 'heroSoulLevelMax',
	name = "英雄军魂满级"
},
{
	typeStr = 'heroRiseLevelMax',
	name = "星穹觉醒满级"
},
{
	typeStr = "unlockSuperSoldier",
	name = "机甲解锁"
},
{
	typeStr = "unlockAllPreSuperSoldier",
	name = "1代机甲"
},
{
	typeStr = "unlockAllSoldier",
	name = "2代机甲"
},
{
	typeStr = "superSoldierLevelMax",
	name = "3代机甲满级"
},
{
	typeStr = "AddMechaCoreModule",
	name = "一键加模块"
},
{
	typeStr = "MechaCoreTechMax",
	name = "机甲科技满级"
},
{
	typeStr = "MechaCoreSlotMax",
	name = "机核槽位满级"
},
{
	typeStr = "MechaCoreModuleAddMax",
	name = "机核顶级模块"
},
{
	typeStr = "completeChapter",
	name = "完成章节"
},
{
	typeStr = "breakProtect",
	name = "屏蔽罩子",
	type = 5,
},
{
	typeStr = "techLvlUp",
	name = "科技满级"
},
{
	typeStr = "plantTechLvlUp",
	name = "泰能科技满级"
},
{
	typeStr = "showModel",
	name = "查看模型"
},
{
    typeStr = 'setOnlyActivityShow',
    name = "显示单一活动"
},
{ 
    typeStr = "flagObjects",
    name = "标记已创建物件"
},
{ 
    typeStr = "printNewObjects",
    name = "打印新创建物件",
    type = 3,
},
{
    typeStr = "printPerform",
    name = "性能日志输出",
    type = 3,
},
{
	typeStr = "notSaveFuncNewbie",
	name = "功能引导保存",
},
{
	typeStr = "openLuaProfiler",
	name = "远程lua性能工具",
	type = 3,
},
{
	typeStr = "selectiveRepeat",
	name = "SR测试(F12)",
	type = 3,
},
{
	typeStr = "openMulitKingDom",
	name = "跳转王国"
},
-- {
-- 	typeStr = "openDungenous",
-- 	name = "开/关 副本"
-- },
{
	typeStr = "openDungenous1",
	name = "联盟军演1",
	type = 2,
},
{
	typeStr = "openHome",
	name = "家园系统",
	type = 2,
},
{
	typeStr = "openDungenous2",
	name = "泰伯地图1",
	type = 2,
},
{
	typeStr = "openDungenous17",
	name = "先驱回响-进入",
	type = 2,
},
{
	typeStr = "overDungenous17Over",
	name = "先驱回响-结束",
	type = 2,
},
{
	typeStr = "openDungenous17OB",
	name = "先驱回响-OB",
	type = 2,
},
{
	typeStr = "openDungenous3Battle",
	name = "泰伯利亚-进入",
	type = 2,
},
{
	typeStr = "overDungenous3OverBattle",
	name = "泰伯利亚-结束",
	type = 2,
},
-- {
-- 	typeStr = "openDungenous3",
-- 	name = "泰伯地图2"
-- },
-- {
-- 	typeStr = "openDungenous4",
-- 	name = "泰伯地图3"
-- },
{
	typeStr = "toggleTileBG",
	name = "显隐城外地块",
	type = 4,
},
{
	typeStr = "toggleBlockBG",
	name = "显隐tmx阻挡",
	type = 4,
},
{
	typeStr = 'toggleTerritoryEffect',
	name = '显隐领地特效',
	type = 4,
},
{
	typeStr = 'toggleMap',
	name = '隐MapLayer',
	type = 4,
},
{
	typeStr = 'toggleMapResver',
	name = '显MapLayer',
	type = 4,
},
{
	typeStr = 'toggleLowTrans',
	name = '显隐LowTrans',
	type = 4,
},
{
	typeStr = "showDisplayStats",
	name = "显隐录屏",
},
{
	typeStr = "showPanel",
	name = "统计分析",
	type = 3,
},
{
	typeStr = "convertPos",
	name = "坐标转换",
	type = 4,
},
{
	typeStr = "mapUpdateImmediate",
	name = "Tmx分帧/立即加载",
	type = 4,
},
-- {
-- 	typeStr = "openProfilerLog",
-- 	name = "打开/关闭性能上报",
-- },
{
	typeStr = "clearNewBie",
	name = "重建新号",
},
{
	typeStr = "makeMarchConf",
	name = "行军装扮配置",
},
{
	typeStr = "stopLog",
	name = "屏蔽Log",
	type = 5,
},
{
	typeStr = "noNet",
	name = "屏蔽MoveMsg",
	type = 5,
},
{
	typeStr = "noAll",
	name = "屏蔽all",
	type = 5,
},
{
	typeStr = "noCity",
	name = "屏蔽城市",
	type = 5,
},
{
	typeStr = "noMonster",
	name = "屏蔽野怪",
	type = 5,
},
{
	typeStr = "noRes",
	name = "屏蔽资源矿",
	type = 5,
},
{
	typeStr = "noGuild",
	name = "屏蔽工会建筑",
	type = 5,
},
{
	typeStr = "noYURI",
	name = "屏蔽尤里实验室",
	type = 5,
},
{
	typeStr = "noBox",
	name = "屏蔽随机宝箱",
	type = 5,
},
{
	typeStr = "noKing",
	name = "屏蔽王城",
	type = 5,
},
{
	typeStr = "noTower",
	name = "屏蔽箭塔",
	type = 5,
},
{
	typeStr = "noQuartered",
	name = "屏蔽驻扎点",
	type = 5,
},
{
	typeStr = "noStrongPoint",
	name = "屏蔽据点",
	type = 5,
},
{
	typeStr = "noForest",
	name = "屏蔽迷雾森林",
	type = 5,
},
{
	typeStr = "noGuard",
	name = "屏蔽保护罩",
	type = 5,
},
{
	typeStr = "noBuildName",
	name = "屏蔽建筑名牌",
	type = 5,
},
{
	typeStr = "stopBuildingExecute",
	name = "暂停建筑update",
},
{
	typeStr = "noTownBuilding",
	name = "城内建筑显隐",
},
{
	typeStr = "townBuildingAnimStop",
	name = "城内动画暂停",
},
{
	typeStr = "townBuildingAnimResume",
	name = "城内动画恢复",
},
{
	typeStr = "noTownPatrols",
	name = "城内巡逻显隐",
},
{
	typeStr = "openRTSPlot",
	name = "开启RTS关卡",
},
{
	typeStr = "addServerMarch",
	name = "添加服务器行军",
},
{
	typeStr = "testBuglyluaError",
	name = "测试bugly错误",
	type = 3,
},
{
	typeStr = "testBuglyCrash",
	name = "测试bugly崩溃",
	type = 3,
},
-- {
-- 	typeStr = "addServer100March",
-- 	name = "服务器百行军",
-- },
-- {
-- 	typeStr = "printBattleRefCount",
-- 	name = "行军战斗CCBRef",
-- },
{
	typeStr = "curMarchCount",
	name = "当前行军数量",
	type = 3,
},
{
	typeStr = "stopFrameChange",
	name = "屏蔽帧率变更",
	type = 5,
},
{
	typeStr = "stopPrespect",
	name = "屏蔽视角透视",
	type = 5,
},
{
	typeStr = "upLoadGameLog",
	name = "上传日志",
	type = 3,
},
{
	typeStr = "chooseRTS",
	name = "rts关卡选择",
},
{ 
    typeStr = "passRTS",
    name = "rts通关"
},
{
	typeStr = "joinRoom",
	name = "主播",
},
{ 
    typeStr = "openFontTestPage",
    name = "打开字体测试界面"
},
{ 
    typeStr = "showCurrCCBFileInfo",
    name = "输出CCBFile信息"
},
{ 
    typeStr = "switchTextureAsyncLoad",
    name = "纹理异步加载开关",
    type = 3,
},
{ 
    typeStr = "switchHeroDataShow",
    name = "英雄数据"
},
{ 
    typeStr = "openURL",
    name = "打开URL"
},
{ 
    typeStr = "unLockHeros",
    name = "解锁所有英雄"
},
{
    typeStr = 'openHeroBattle',
    name = "英雄试炼",
},
{
	typeStr = 'joinALVoiceRoom',
	name = "联盟语音"
},
{
    typeStr = 'addYQZZMainPage',
    name = "月球之战",
    type = 2,
},
{
    typeStr = 'showDungenousPanel',
    name = "副本性能数据",
    type = 2,
},
{
    typeStr = 'puidLogin',
    name = "openId登录",
    type = 3,
},
{
    typeStr = 'changeCityStyle',
    name = "切换主城皮肤"
},
{
    typeStr = 'cmndis',
    name = "通用奖励放出"
},
{
    typeStr = 'clearPackageItems',
    name = "清空背包"
},
{
	typeStr = "isSupportLuaProtobuf",
	name = "新协议解析",
	type = 3,
},
{
	typeStr = "armourStarAttrMax",
	name = "泰晶全满",
	type = 1,
},
{
	typeStr = "armourQuantumMax",
	name = "装备红装",
	type = 1,
},
{
	typeStr = "plantSoliderMilitaryMax",
	name = "尉官满阶",
	type = 1,
},
{
	typeStr = "plantSoliderColonelMax",
	name = "校官满阶",
	type = 1,
},
{
	typeStr = "openActivityNewStart",
	name = "破晓启程",
	type = 1,
},
{
	typeStr = "ALTecMax",
	name = "联盟科技满级",
	type = 1,
},
{
	typeStr = "SWBaseMax",
	name = "超武底座满级",
	type = 1,
},
{
	typeStr = "SWUpMax",
	name = "超武升级满级",
	type = 1,
},
{
	typeStr = "sendAct385AddScore",
	name = "385活动加积分",
	type = 1,
},
}
return gm_conf


