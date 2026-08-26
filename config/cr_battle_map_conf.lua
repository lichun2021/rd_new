local cr_battle_map_conf = {
[9] ={
	id = 9,
	bg_ccb = 'RABattleTOH_Map_1.ccbi',
	fg_ccb = 'RABattleTOH_Map_1_Fore.ccbi',
	weather_ccb= 'RABattleMap_Grass_Weather.ccbi', 
	tmx = 'RABattleMap_Block_RTS_1_9.tmx',
	min_scale = 0.5,
	init_scale = 1, -- Ä¬ÈÏ0.9
	max_scale = 0.75,
	bounce_min_scale = 0.45,		-- 回弹
	bounce_max_scale = 0.75,
	camera_init_grid_pos = '73_91',	-- 初始化镜头的格子
	original_width = 1311,
	original_height = 990,
	prepareMusic = 10028,
	fightMusic = 10028,
}
}
return cr_battle_map_conf
