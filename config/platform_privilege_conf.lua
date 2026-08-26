local platform_privilege_conf = {
[1] ={
	id = 1,
	platformId = 1,
	isShow = 0,
	key = 'Wechat',
	order = 2,
	btnIcon = 'V5Privilege_u_Wechat.png',
	isUrl = 0,
	fun = 'RAWeChatPrivilegePage'
},
[2] ={
	id = 2,
	platformId = 2,
	isShow = 0,
	key = 'QQ',
	order = 2,
	btnIcon = 'V5Privilege_u_QQ.png',
	isUrl = 1,
	fun = 'https://speed.gamecenter.qq.com/pushgame/v1/gift/game?_wv=18950115&_wwv=393&appid=1105906633&adtag=10007'
},
[3] ={
	id = 3,
	platformId = 2,
	isShow = 0,
	key = 'QQSvip',
	order = 2,
	btnIcon = 'V5Privilege_u_SuperQQ.png',
	isUrl = 1,
	fun = 'https://mq.vip.qq.com/m/game/vipembed'
},
[4] ={
	id = 4,
	platformId = 2,
	isShow = 1,
	key = 'QQEgame',
	order = 3,
	btnIcon = 'V5Privilege_u_Egame.png',
	isUrl = 1,
	fun = 'https://game.egame.qq.com/cgi-bin/pgg_game_handle_ticket_fcgi?gc_t=1&url_id=1140'
},
[5] ={
	id = 5,
	platformId = 0,
	isShow = 0,
	key = 'Community',
	order = 1,
	btnIcon = 'V5Privilege_u_WSQ.png',
	btnIcon2 = 'V5Privilege_u_WSQ.png',
	isUrl = 1,
	fun = 'https://hongjing.qq.com/ingame/all/index.shtml'
},
[6] ={
	id = 6,
	platformId = 0,
	isShow = 0,
	key = 'Friends',
	order = 4,
	btnIcon = 'V5Privilege_u_Friend.png',
	isUrl = 0,
	fun = 'RAFriendsInvitePage'
},
[7] ={
	id = 7,
	platformId = 1,
	isShow = 0,
	key = 'WechatYXQ',
	order = 2,
	btnIcon = 'V5Privilege_u_YXQ.png',
	isUrl = 1,
	fun = 'https://game.weixin.qq.com/cgi-bin/h5/static/circle/index.html?jsapi=1&appid=wxd383fb5d440a7267&auth_type=2&ssid=1'
},
[8] ={
	id = 8,
	platformId = 1,
	isShow = 1,
	key = 'WechatFriends',
	order = 5,
	btnIcon = 'V5Privilege_u_FriendsUpgrade.png',
	isUrl = 1,
	fun = 'https://w.url.cn/s/AU2XvuS'
},
[9] ={
	id = 9,
	platformId = 2,
	isShow = 1,
	key = 'QQTencentChannel',
	order = 6,
	btnIcon = 'V5Privilege_u_Channel.png',
	isUrl = 1,
	fun = 'https://pd.qq.com/g/599830003988231458?mode=2&utm_source=ingame&platid=%s'
},
[10] ={
	id = 10,
	platformId = 2,
	isShow = 1,
	key = 'WarBook',
	order = 7,
	btnIcon = 'V5Privilege_u_Menual.png',
	isUrl = 1,
	fun = 'https://act.supercore.qq.com/commercial/act/a0de41e28c4a44868baf8d0882c682bba/index.html?via=svip_21'
},
[11] ={
	id = 11,
	platformId = 1,
	isShow = 1,
	key = 'WarBook',
	order = 7,
	btnIcon = 'V5Privilege_u_Menual.png',
	isUrl = 1,
	fun = 'https://act.supercore.qq.com/commercial/act/a0de41e28c4a44868baf8d0882c682bba/index.html?via=svip_21'
}
}
return platform_privilege_conf
