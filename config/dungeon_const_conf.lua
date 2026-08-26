local dungeon_const_conf = {
--跨服屏蔽部分消息
['skipWhenCross'] ={
     key = 'inDungeon',
     value = '3,5,6,7,9,11,13,15,17'
},
--显示保护罩10分钟邮件
['showShield10Mail'] ={
     key = 'showShield10Mail',
     value = '2,5,6'
},
--集结信息发送到战队聊天以及实时战况tips
['massInfoTeamAndWarTips'] ={
     key = 'massInfoTeamAndWarTips',
     value = '3,7,9,11,13,15,17'
},
--集结信息发送到军演聊天tips
['massInfoLMJYTips'] ={
     key = 'massInfoLMJYTips',
     value = '2'
},
--集结信息发送到战队频道tips
['massInfoTeamTips'] ={
     key = 'massInfoTeamTips',
     value = '5'
},
--屏蔽发私信
['forbitSendPlayerMail'] ={
     key = 'forbitSendPlayerMail',
     value = '3,5,6,7,9,11,13,15,17'
},
--屏蔽活动
['forbitActivity'] ={
     key = 'forbitActivity',
     value = '3,5,6,7,9,11,13,15,17'
},
--屏蔽黑科技图标特效
['forbitBlackTechEffect'] ={
     key = 'forbitBlackTechEffect',
     value = '3,5,6,7,9,11,13,15,17'
},
--屏蔽RST
['forbitRST'] ={
     key = 'forbitRST',
     value = '3,5,6,7,9,11,13,15,17'
},

--治疗伤兵不消耗资源
['hospitalNoCost'] ={
     key = 'hospitalNoCost',
     value = '2,3,7,9,13,17'
},

--治疗伤兵有加速
['cureSpeedUp'] ={
     key = 'cureSpeedUp',
     value = '3,7,9,11,17'
},
--行军有加速
['playerMarchSpeedUp'] ={
     key = 'playerMarchSpeedUp',
     value = '2,3,7,9,11,13,15,17'
},
--显示战场行军加速道具
['showDungeonMarchItem'] ={
     key = 'showDungeonMarchItem',
     value = '3,7,9,11,17'
},
--显示战场医疗加速道具
['showDungeonCureItem'] ={
     key = 'showDungeonCureItem',
     value = '3,7,9,11,17'
},
--屏蔽军事学院
['forbitMilitaryCollege'] ={
     key = 'forbitMilitaryCollege',
     value = '3,7,9,11,13,15,17'
},
--屏蔽皮肤BGM
['forbitSkinBGM'] ={
     key = 'forbitSkinBGM',
     value = '3,7,9,11,13,15,17'
},
--着火速率
['hasfireSpeedConfig'] ={
     key = 'hasfireSpeedConfig',
     value = '2,3,7,9,11,17'
},
--屏蔽军演房间请求
['forbitWarCollegeRoomListReq'] ={
     key = 'forbitWarCollegeRoomListReq',
     value = '3,5,6,7,9,11,13,15,17'
},
--有导弹建筑
['hasNuclear'] ={
     key = 'hasNuclear',
     value = '3,7'
},
--有战队频道
['haveDungeonTeam'] ={
     key = 'haveDungeonTeam',
     value = '3,5,7,9,11,13,15,17'
},
--显示收藏坐标标识
['showAddFavoritesIcon'] ={
     key = 'showAddFavoritesIcon',
     value = '3,5,7,9,11,13,15,17'
},
--显示皮肤界面
['showSkinPage'] ={
     key = 'showSkinPage',
     value = '3,7,11'
},
--屏蔽联盟帮助红点
['forbitAllianceHelpRedPoint'] ={
     key = 'forbitAllianceHelpRedPoint',
     value = '3,7,9,11,13,15,17'
},
--势力颜色
['hasMaskColor'] ={
     key = 'hasMaskColor',
     value = '3,7,9,11,13,17'
},
--收藏HUD直接分享坐标
['addFavHudToSend'] ={
     key = 'addFavHudToSend',
     value = '3,5,7,9,11,13,17'
},
--显示引导
['showNewbiePage'] ={
     key = 'showNewbiePage',
     value = '3,7,9,11,13,17'
},
--显示通用的玩法按钮
['showCommonNewbieBtn'] ={
     key = 'showCommonNewbieBtn',
     value = '3,7,9'
},
--显示特别的玩法按钮
['showSpecialNewbieBtn'] ={
     key = 'showSpecialNewbieBtn',
     value = '1'
},
--禁止move协议
['forbitMoveProtocol'] ={
     key = 'forbitMoveProtocol',
     value = '2,3,5,6,7,9,17'
},
--查找坐标功能
['searchPanel'] ={
     key = 'searchPanel',
     value = '3,5,6,7,11,13,15,17'
},
--是否可以传送
['canMigrate'] ={
     key = 'canMigrate',
     value = '3,5,7,9,11,15,17'
},
--是否忽略野怪最大等级
['ignoreMonsterLevel'] ={
     key = 'ignoreMonsterLevel',
     value = '3,7,11,15,17'
},
--有国家频道
['haveNationChannel'] ={
     key = 'haveNationChannel',
     value = '11'
},
--可以联盟标记
['canAllianceMark'] ={
     key = 'canAllianceMark',
     value = '11'
},
--可以显示援军
['canShowAssisant'] ={
     key = 'canShowAssisant',
     value = '11'
},
--分享按钮的箭头隐藏
['hideShareArrow'] ={
     key = 'hideShareArrow',
     value = '2'
},
--不使用伊娃
['noUseEva'] ={
     key = 'noUseEva',
     value = '11'
},
--集结编队战队标识使用途径
['formationUse'] ={
     key = 'formationUse',
     value = '3,5,11,17'
},
}
return dungeon_const_conf
