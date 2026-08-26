--[[
	世界行军模型配置
	modelType    : armyType,详见RAMarchFrameActionConfig定义
	modelLevel   : 1,2,3等级,四类造兵建筑(1~10,11~20,21~30)所对应显示的模型等级,对应关系详见build_march_conf
	modelCount   : 一个Square(方阵)容纳的模型数量,一般一个行军实体包含四个Square
	modelCCBName : 模型CCB
	part 		 : 模型分拆(例如等级1飞机:part1机体,part2螺旋桨,part3螺旋桨)
	offset       : 模型距原点的偏移
	squareWidth  : Square(方阵)宽度,各个方向纹理不同,需要定制
]]
local march_model_conf = {
[1] = {
	modelName = "Soldier1",
	modelType = 1,
	modelLevel = 1,
	modelCount = 5,
	plistName = 'WorldBattleUnit_Whole.color.plist',
	picName = 'WorldBattleUnit_Whole.color.png',
	picMaskName = 'WorldBattleUnit_Whole.mask.png',	
	modelEffect = {1065,1066},
	part = {
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,1},
				[2] = {2,3,4,5,6,7,8,1,2},
				[3] = {3,4,5,6,7,8,1,2,3},
				[4] = {4,5,6,7,8,1,2,3,4},
				[5] = {5,6,7,8,1,2,3,4,5},
				[6] = {6,7,8,1,2,3,4,5,6},
				[7] = {7,8,1,2,3,4,5,6,7},
				[8] = {8,1,2,3,4,5,6,7,8},
			},
			delay = 1/10,
			disorder = true,
		},
	},
	zOrder = 1,
},

[2] = {
	modelName = "Tank1",
	modelType = 2,
	modelLevel = 1,
	modelCount = 3,
	plistName = 'WorldBattleUnit_Whole.color.plist',
	picName = 'WorldBattleUnit_Whole.color.png',	
	picMaskName = 'WorldBattleUnit_Whole.mask.png',	
	modelEffect = {1061,1062},
	part = {
		[1] = {
			frames = {[1] = {1}},
			delay = 1/12,
			disorder = false,		
		},
		[2] = {
			frames = {[1] = {1}},
			delay = 1/12,
			disorder = false,
		},
		[3] = {
			frames = {[1] = {1}},
			delay = 1/12,
			disorder = false,
		},
	},
	zOrder = 1,
},

[3] = {
	modelName = "AirForce1",
	modelType = 3,
	modelLevel = 1,
	modelCount = 2,
	plistName = 'WorldBattleUnit_Whole.color.plist',
	picName = 'WorldBattleUnit_Whole.color.png',	
	picMaskName = 'WorldBattleUnit_Whole.mask.png',	
	modelEffect = {1063,1064},	
	part = {
		[1] = {
			frames = {[1] = {1}},
			delay = 1/6,
			disorder = false,
		},
		[2] = {
			frames = {[1] = {1,2,3,1,2,3,1}},
			delay = 1/15,
			disorder = false,
		},
		[3] = {
			frames = {[1] = {1,2,3,1,2,3,1}},
			delay = 1/15,
			disorder = false,
		},
		[4] = {
			frames = {[1] = {1,2,3,1,2,3,1}},
			delay = 1/15,
			disorder = false,
		},
	},
	zOrder = 10,
},

[4] = {
	modelName = "RemoteFire2",
	modelType = 4,
	modelLevel = 1,
	modelCount = 2,
	plistName = 'WorldBattleUnit_Whole.color.plist',
	picName = 'WorldBattleUnit_Whole.color.png',	
	picMaskName = 'WorldBattleUnit_Whole.mask.png',	
	modelEffect = {1067,1068},	
	part = {
		[1] = {
			frames = {[1] = {1}},
			delay = 1/6,
			disorder = false,
		},
		[2] = {
			frames = {[1] = {1}},
			delay = 1/6,
			disorder = false,
		},
	},
	zOrder = 1,
},

[51] = {
	modelName = "Scout",
	modelType = 5,
	modelLevel = 1,
	modelCount = 1,
	modelCCBName = "Ani_World_March_PointScout.ccbi",
	plistName = 'WorldMarch_Harvester_Scout_Truck.color.plist',
	picName = 'WorldMarch_Harvester_Scout_Truck.color.png',	
	picMaskName = 'WorldMarch_Harvester_Scout_Truck.mask.png',	
	adjustDis = {
		[0]   = 0, 
		[1]   = 0, 
		[2]   = 0, 
		[3]   = 0, 
		[4]   = 0, 
		[5]   = 0, 
		[6]   = 0, 
		[7]   = 0, 
		[8]   = 0, 
		[9]   = 0, 
		[10]  = 0, 
		[11]  = 0, 
		[12]  = 0, 
		[13]  = 0, 
		[14]  = 0, 
		[15]  = 0, 
	},
},
[71] = {
	modelName = "Harvester",
	modelType = 7,
	modelLevel = 1,
	modelCount = 4,
	modelCCBName = "Ani_World_March_PointHarvester.ccbi",
	plistName = 'WorldMarch_Harvester_Scout_Truck.color.plist',
	picName = 'WorldMarch_Harvester_Scout_Truck.color.png',	
	picMaskName = 'WorldMarch_Harvester_Scout_Truck.mask.png',
	adjustDis = {
		[0]   = 120, 
		[1]   = 50, 
		[2]   = 50, 
		[3]   = 50, 
		[4]   = 50, 
		[5]   = 50, 
		[6]   = 50, 
		[7]   = 50, 
		[8]   = 50, 
		[9]   = 50, 
		[10]  = 50, 
		[11]  = 50, 
		[12]  = 50, 
		[13]  = 50, 
		[14]  = 50, 
		[15]  = 50, 
	},
},

[81] = {
	modelName = "YurisRevenge1",
	modelType = 8,
	modelLevel = 1,		--level1-2,用于幽灵归来
	modelCount = 1,
	modelCCBName = "Ani_World_March_PointYurisRevenge1.ccbi",
	plistName = 'WorldMarch_YurisRevenge1.plist',
	adjustDis = {
		[0]   = 0, 
		[1]   = 0, 
		[2]   = 0, 
		[3]   = 0, 
		[4]   = 0, 
		[5]   = 0, 
		[6]   = 0, 
		[7]   = 0, 
		[8]   = 0, 
		[9]   = 0, 
		[10]  = 0, 
		[11]  = 0, 
		[12]  = 0, 
		[13]  = 0, 
		[14]  = 0, 
		[15]  = 0, 
	},
},
[82] = {
	modelName = "YurisRevenge2",
	modelType = 8,
	modelLevel = 2,
	modelCount = 1,
	modelCCBName = "Ani_World_March_PointYurisRevenge2.ccbi",
	plistName = 'WorldMarch_YurisRevenge2.plist',
	adjustDis = {
		[0]   = 0, 
		[1]   = 0, 
		[2]   = 0, 
		[3]   = 0, 
		[4]   = 0, 
		[5]   = 0, 
		[6]   = 0, 
		[7]   = 0, 
		[8]   = 0, 
		[9]   = 0, 
		[10]  = 0, 
		[11]  = 0, 
		[12]  = 0, 
		[13]  = 0, 
		[14]  = 0, 
		[15]  = 0, 
	},
},
[810] = {
	modelName = "YurisRevenge2",
	modelType = 8,
	modelLevel = 10,		--level10之后,用于星甲召唤，对应配置到space_machine_enemy的modelLevel字段
	modelCount = 1,
	modelCCBName = "Ani_World_March_PointSpaceMechine1.ccbi",
	plistName = 'WorldMarch_YurisRevenge2.plist',
	adjustDis = {
		[0]   = 0, 
		[1]   = 0, 
		[2]   = 0, 
		[3]   = 0, 
		[4]   = 0, 
		[5]   = 0, 
		[6]   = 0, 
		[7]   = 0, 
		[8]   = 0, 
		[9]   = 0, 
		[10]  = 0, 
		[11]  = 0, 
		[12]  = 0, 
		[13]  = 0, 
		[14]  = 0, 
		[15]  = 0, 
	},
},

[811] = {
	modelName = "YurisRevenge2",
	modelType = 8,
	modelLevel = 11,
	modelCount = 1,
	modelCCBName = "Ani_World_March_PointSpaceMechine2.ccbi",
	plistName = 'WorldMarch_YurisRevenge2.plist',
	adjustDis = {
		[0]   = 0, 
		[1]   = 0, 
		[2]   = 0, 
		[3]   = 0, 
		[4]   = 0, 
		[5]   = 0, 
		[6]   = 0, 
		[7]   = 0, 
		[8]   = 0, 
		[9]   = 0, 
		[10]  = 0, 
		[11]  = 0, 
		[12]  = 0, 
		[13]  = 0, 
		[14]  = 0, 
		[15]  = 0, 
	},
},
[812] = {
	modelName = "YurisRevenge2",
	modelType = 8,
	modelLevel = 12,
	modelCount = 1,
	modelCCBName = "Ani_World_March_PointSpaceMechine3.ccbi",
	plistName = 'WorldMarch_YurisRevenge2.plist',
	adjustDis = {
		[0]   = 0, 
		[1]   = 0, 
		[2]   = 0, 
		[3]   = 0, 
		[4]   = 0, 
		[5]   = 0, 
		[6]   = 0, 
		[7]   = 0, 
		[8]   = 0, 
		[9]   = 0, 
		[10]  = 0, 
		[11]  = 0, 
		[12]  = 0, 
		[13]  = 0, 
		[14]  = 0, 
		[15]  = 0, 
	},
},
[91] = {
	modelName = "Truck",
	modelType = 9,
	modelLevel = 1,
	modelCount = 1,
	modelCCBName = "Ani_World_March_PointTruck.ccbi",
	plistName = 'WorldMarch_Harvester_Scout_Truck.color.plist',
	picName = 'WorldMarch_Harvester_Scout_Truck.color.png',	
	picMaskName = 'WorldMarch_Harvester_Scout_Truck.mask.png',
	adjustDis = {
		[0]   = 0, 
		[1]   = 0, 
		[2]   = 0, 
		[3]   = 0, 
		[4]   = 0, 
		[5]   = 0, 
		[6]   = 0, 
		[7]   = 0, 
		[8]   = 0, 
		[9]   = 0, 
		[10]  = 0, 
		[11]  = 0, 
		[12]  = 0, 
		[13]  = 0, 
		[14]  = 0, 
		[15]  = 0, 
	},
},

[101] = {
	modelName = "Hero1",
	modelType = 10,
	modelLevel = 1,
	modelCount = 1,
	modelCCBName = "RAMarchModelHero.ccbi",
	plistName = 'WorldMarch_Hero.plist',
	picName = 'WorldMarch_Hero.png',
	picMaskName = 'WorldMarch_Hero.png',
	adjustDis = {
		[0]   = 0, 
		[1]   = 0, 
		[2]   = 0, 
		[3]   = 0, 
		[4]   = 0, 
		[5]   = 0, 
		[6]   = 0, 
		[7]   = 0, 
		[8]   = 0, 
		[9]   = 0, 
		[10]  = 0, 
		[11]  = 0, 
		[12]  = 0, 
		[13]  = 0, 
		[14]  = 0, 
		[15]  = 0, 
	},
},

[111] = {
	modelName = "YurisAttack1",
	modelType = 11,
	modelLevel = 1,
	modelCount = 1,
	modelCCBName = "Ani_GuideV6_March_Point.ccbi",
	adjustDis = {
		[0]   = 0, 
		[1]   = 0, 
		[2]   = 0, 
		[3]   = 0, 
		[4]   = 0, 
		[5]   = 0, 
		[6]   = 0, 
		[7]   = 0, 
		[8]   = 0, 
		[9]   = 0, 
		[10]  = 0, 
		[11]  = 0, 
		[12]  = 0, 
		[13]  = 0, 
		[14]  = 0, 
		[15]  = 0, 
	},
},

[121] = {
	modelName = "Mech1",
	modelIndex = 121,
	modelType = 12,
	modelLevel = 1,
	modelCount = 1,
	modelMechaId = 1002,
	modelNode = 1,
	plistName = 'World_Mech1.plist',
	picName = 'World_Mech1.png',
	part = {
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
	},
	zOrder = 1,
	adjustPos = {
		[0]   = {x = 0, y = -40}, 
		[1]   = {x =10, y = -10}, 
		[2]   = {x = 35, y = 0}, 
		[3]   = {x = 20, y = 30}, 
		[4]   = {x = 40, y = 40}, 
		[5]   = {x = 20, y = 40}, 
		[6]   = {x = 0, y = 50}, 
		[7]   = {x = 0, y = 50}, 
		[8]   = {x = 0, y = 40}, 
		[9]   = {x = 0, y = 60}, 
		[10]  = {x = -10, y = 40}, 
		[11]  = {x = -20, y = 40}, 
		[12]  = {x = -40, y = 50}, 
		[13]  = {x = -20, y = 30}, 
		[14]  = {x = -40, y = -10}, 
		[15]  = {x = -20, y = -10},  
	},
	sizeScale = 1.5,
},
[122] = {
	modelName = "Mech2",
	modelIndex = 122,
	modelType = 12,
	modelLevel = 2,
	modelCount = 1,
	modelMechaId = 1003,
	modelNode = 1,
	plistName = 'World_Mech2.plist',
	picName = 'World_Mech2.png',	
	part = {
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,1},
			},
			delay = 1/10,
		},
	},
	zOrder = 1,
	adjustPos = {
		[0]   = {x = 0, y = -40}, 
		[1]   = {x =10, y = -10}, 
		[2]   = {x = 35, y = 0}, 
		[3]   = {x = 20, y = 30}, 
		[4]   = {x = 40, y = 40}, 
		[5]   = {x = 20, y = 40}, 
		[6]   = {x = 0, y = 50}, 
		[7]   = {x = 0, y = 50}, 
		[8]   = {x = 0, y = 40}, 
		[9]   = {x = 0, y = 60}, 
		[10]  = {x = -10, y = 40}, 
		[11]  = {x = -20, y = 40}, 
		[12]  = {x = -40, y = 50}, 
		[13]  = {x = -20, y = 30}, 
		[14]  = {x = -40, y = -10}, 
		[15]  = {x = -20, y = -10}, 
	},
	sizeScale = 1.5,
},
[123] = {
	modelName = "Mech3",
	modelIndex = 123,
	modelType = 12,
	modelLevel = 3,
	modelCount = 1,
	modelMechaId = 1004,
	modelNode = 2,
	plistName = 'World_Mech3.plist',
	picName = 'World_Mech3.png',	
	part = {
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
			},
			delay = 1/7,
		},
	},
	zOrder = 1,
	adjustPos = {
		[0]   = {x = 0, y = -40}, 
		[1]   = {x =10, y = -10}, 
		[2]   = {x = 35, y = 0}, 
		[3]   = {x = 20, y = 30}, 
		[4]   = {x = 40, y = 40}, 
		[5]   = {x = 20, y = 40}, 
		[6]   = {x = 0, y = 50}, 
		[7]   = {x = 0, y = 50}, 
		[8]   = {x = 0, y = 40}, 
		[9]   = {x = 0, y = 60}, 
		[10]  = {x = -10, y = 40}, 
		[11]  = {x = -20, y = 40}, 
		[12]  = {x = -40, y = 50}, 
		[13]  = {x = -20, y = 30}, 
		[14]  = {x = -40, y = -10}, 
		[15]  = {x = -20, y = -10}, 
	},
	sizeScale = 1.4,
},
[124] = {
	modelName = "Mech4",
	modelIndex = 124,
	modelType = 12,
	modelLevel = 4,
	modelCount = 1,
	modelMechaId = 1005,
	modelNode = 3,
	plistName = 'World_Mech4.plist',
	picName = 'World_Mech4.png',	
	part = {
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
	},
	zOrder = 1,
	adjustPos = {
		[0]   = {x = 0, y = -40}, 
		[1]   = {x =10, y = -10}, 
		[2]   = {x = 35, y = 0}, 
		[3]   = {x = 20, y = 30}, 
		[4]   = {x = 40, y = 40}, 
		[5]   = {x = 20, y = 40}, 
		[6]   = {x = 0, y = 50}, 
		[7]   = {x = 0, y = 50}, 
		[8]   = {x = 0, y = 40}, 
		[9]   = {x = 0, y = 60}, 
		[10]  = {x = -10, y = 40}, 
		[11]  = {x = -20, y = 40}, 
		[12]  = {x = -40, y = 50}, 
		[13]  = {x = -20, y = 30}, 
		[14]  = {x = -40, y = -10}, 
		[15]  = {x = -20, y = -10}, 
	},
	sizeScale = 1.4,
},
[125] = {
	modelName = "Mech5",
	modelIndex = 125,
	modelType = 12,
	modelLevel = 5,
	modelCount = 1,
	modelMechaId = 1006,
	modelNode = 4,
	plistName = 'World_Mech5.plist',
	picName = 'World_Mech5.png',	
	part = {
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
	},
	zOrder = 1,
	adjustPos = {
		[0]   = {x = 0, y = -40}, 
		[1]   = {x =10, y = -10}, 
		[2]   = {x = 35, y = 0}, 
		[3]   = {x = 20, y = 30}, 
		[4]   = {x = 40, y = 40}, 
		[5]   = {x = 20, y = 40}, 
		[6]   = {x = 0, y = 50}, 
		[7]   = {x = 0, y = 50}, 
		[8]   = {x = 0, y = 40}, 
		[9]   = {x = 0, y = 60}, 
		[10]  = {x = -10, y = 40}, 
		[11]  = {x = -20, y = 40}, 
		[12]  = {x = -40, y = 50}, 
		[13]  = {x = -20, y = 30}, 
		[14]  = {x = -40, y = -10}, 
		[15]  = {x = -20, y = -10}, 
	},
	sizeScale = 1.45,
},
[126] = {
	modelName = "Mech6",
	modelIndex = 126,
	modelType = 12,
	modelLevel = 5,
	modelCount = 1,
	modelMechaId = 1007,
	modelNode = 4,
	plistName = 'World_Mech6.plist',
	picName = 'World_Mech6.png',	
	part = {
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
	},
	zOrder = 1,
	adjustPos = {
		[0]   = {x = 0, y = -40}, 
		[1]   = {x =10, y = -10}, 
		[2]   = {x = 35, y = 0}, 
		[3]   = {x = 20, y = 30}, 
		[4]   = {x = 40, y = 40}, 
		[5]   = {x = 20, y = 40}, 
		[6]   = {x = 0, y = 50}, 
		[7]   = {x = 0, y = 50}, 
		[8]   = {x = 0, y = 40}, 
		[9]   = {x = 0, y = 60}, 
		[10]  = {x = -10, y = 40}, 
		[11]  = {x = -20, y = 40}, 
		[12]  = {x = -40, y = 50}, 
		[13]  = {x = -20, y = 30}, 
		[14]  = {x = -40, y = -10}, 
		[15]  = {x = -20, y = -10}, 
	},
	sizeScale = 1.45,
},
[127] = {
	modelName = "Mech8",
	modelIndex = 127,
	modelType = 12,
	modelLevel = 5,
	modelCount = 1,
	modelMechaId = 1008,
	modelNode = 4,
	plistName = 'World_Mech8.plist',
	picName = 'World_Mech8.png',	
	part = {
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
	},
	zOrder = 1,
	adjustPos = {
		[0]   = {x = 0, y = -40}, 
		[1]   = {x =10, y = -10}, 
		[2]   = {x = 35, y = 0}, 
		[3]   = {x = 20, y = 30}, 
		[4]   = {x = 40, y = 40}, 
		[5]   = {x = 20, y = 40}, 
		[6]   = {x = 0, y = 50}, 
		[7]   = {x = 0, y = 50}, 
		[8]   = {x = 0, y = 40}, 
		[9]   = {x = 0, y = 60}, 
		[10]  = {x = -10, y = 40}, 
		[11]  = {x = -20, y = 40}, 
		[12]  = {x = -40, y = 50}, 
		[13]  = {x = -20, y = 30}, 
		[14]  = {x = -40, y = -10}, 
		[15]  = {x = -20, y = -10}, 
	},
	sizeScale = 1.45,
},
[128] = {
	modelName = "Mech7",
	modelIndex = 128,
	modelType = 12,
	modelLevel = 5,
	modelCount = 1,
	modelMechaId = 1009,
	modelNode = 4,
	plistName = 'World_Mech7.plist',
	picName = 'World_Mech7.png',	
	part = {
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
	},
	zOrder = 1,
	adjustPos = {
		[0]   = {x = 0, y = -40}, 
		[1]   = {x =10, y = -10}, 
		[2]   = {x = 35, y = 0}, 
		[3]   = {x = 20, y = 30}, 
		[4]   = {x = 40, y = 40}, 
		[5]   = {x = 20, y = 40}, 
		[6]   = {x = 0, y = 50}, 
		[7]   = {x = 0, y = 50}, 
		[8]   = {x = 0, y = 40}, 
		[9]   = {x = 0, y = 60}, 
		[10]  = {x = -10, y = 40}, 
		[11]  = {x = -20, y = 40}, 
		[12]  = {x = -40, y = 50}, 
		[13]  = {x = -20, y = 30}, 
		[14]  = {x = -40, y = -10}, 
		[15]  = {x = -20, y = -10}, 
	},
	sizeScale = 1.45,
},
[129] = {
	modelName = "Mech9",
	modelIndex = 129,
	modelType = 12,
	modelLevel = 5,
	modelCount = 1,
	modelMechaId = 1010,
	modelNode = 4,
	plistName = 'World_Mech9.plist',
	picName = 'World_Mech9.png',	
	part = {
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
	},
	zOrder = 1,
	adjustPos = {
		[0]   = {x = 0, y = -40}, 
		[1]   = {x =10, y = -10}, 
		[2]   = {x = 35, y = 0}, 
		[3]   = {x = 20, y = 30}, 
		[4]   = {x = 40, y = 40}, 
		[5]   = {x = 20, y = 40}, 
		[6]   = {x = 0, y = 50}, 
		[7]   = {x = 0, y = 50}, 
		[8]   = {x = 0, y = 40}, 
		[9]   = {x = 0, y = 60}, 
		[10]  = {x = -10, y = 40}, 
		[11]  = {x = -20, y = 40}, 
		[12]  = {x = -40, y = 50}, 
		[13]  = {x = -20, y = 30}, 
		[14]  = {x = -40, y = -10}, 
		[15]  = {x = -20, y = -10}, 
	},
	sizeScale = 1.45,
},
[130] = {
	modelName = "Mech10",
	modelIndex = 130,
	modelType = 12,
	modelLevel = 5,
	modelCount = 1,
	modelMechaId = 1011,
	modelNode = 4,
	plistName = 'World_Mech10.plist',
	picName = 'World_Mech10.png',	
	part = {
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
	},
	zOrder = 1,
	adjustPos = {
		[0]   = {x = 0, y = -40}, 
		[1]   = {x =10, y = -10}, 
		[2]   = {x = 35, y = 0}, 
		[3]   = {x = 20, y = 30}, 
		[4]   = {x = 40, y = 40}, 
		[5]   = {x = 20, y = 40}, 
		[6]   = {x = 0, y = 50}, 
		[7]   = {x = 0, y = 50}, 
		[8]   = {x = 0, y = 40}, 
		[9]   = {x = 0, y = 60}, 
		[10]  = {x = -10, y = 40}, 
		[11]  = {x = -20, y = 40}, 
		[12]  = {x = -40, y = 50}, 
		[13]  = {x = -20, y = 30}, 
		[14]  = {x = -40, y = -10}, 
		[15]  = {x = -20, y = -10}, 
	},
	sizeScale = 1.45,
},
[131] = {
	modelName = "Motorbike",
	modelIndex = 131,
	modelType = 13,
	modelLevel = 1,
	modelCount = 12,
	partCount = 3,
	dressModelType = 2,
	length = 360,
	checkBtnScale = 400,
	modelConf = "march_dress_motor_conf",
	modelCCBName= "Ani_World_March_Point_Motor.ccbi",
	plistName = 'World_Motorbike.plist',
	picName = 'World_Motorbike.png',
	part = {
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,1},
				[2] = {2,3,4,5,6,7,8,9,1,2},
				[3] = {3,4,5,6,7,8,9,1,2,3},
				[4] = {4,5,6,7,8,9,1,2,3,4},
				[5] = {5,6,7,8,9,1,2,3,4,5},
				[6] = {6,7,8,9,1,2,3,4,5,6},
				[7] = {7,8,9,1,2,3,4,5,6,7},
				[8] = {8,9,1,2,3,4,5,6,7,8},
				[9] = {9,1,2,3,4,5,6,7,8,9},
			},
			delay = 1/15,
			disorder = true,
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,1},
				[2] = {2,3,4,5,6,7,8,9,1,2},
				[3] = {3,4,5,6,7,8,9,1,2,3},
				[4] = {4,5,6,7,8,9,1,2,3,4},
				[5] = {5,6,7,8,9,1,2,3,4,5},
				[6] = {6,7,8,9,1,2,3,4,5,6},
				[7] = {7,8,9,1,2,3,4,5,6,7},
				[8] = {8,9,1,2,3,4,5,6,7,8},
				[9] = {9,1,2,3,4,5,6,7,8,9},
			},
			delay = 1/15,
			disorder = true,
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,1,2,3,4,5,6,7,8,9},
				[10] = {10,11,1,2,3,4,5,6,7,8,9,10},
				[11] = {11,1,2,3,4,5,6,7,8,9,10,11},
			},
			delay = 1/15,
			disorder = true,
			isEffect = true,
		},
	},
	holeEffect = {
		[0]  = {[1] = 1,[2] = 1,[3] = 1,[4] = 1,[5] = 1,[6] = 1,[7] = 1,[8] = 0,[9] = 1,[10] = 0,[11] = 0,[12] = 0},
		[1]  = {[1] = 1,[2] = 1,[3] = 1,[4] = 1,[5] = 1,[6] = 1,[7] = 1,[8] = 0,[9] = 1,[10] = 0,[11] = 0,[12] = 0},
		[2]  = {[1] = 1,[2] = 0,[3] = 1,[4] = 1,[5] = 1,[6] = 1,[7] = 1,[8] = 1,[9] = 0,[10] = 1,[11] = 0,[12] = 0},
		[3]  = {[1] = 1,[2] = 0,[3] = 1,[4] = 1,[5] = 1,[6] = 1,[7] = 1,[8] = 1,[9] = 0,[10] = 1,[11] = 0,[12] = 0},
		[4]  = {[1] = 1,[2] = 0,[3] = 1,[4] = 1,[5] = 1,[6] = 1,[7] = 1,[8] = 1,[9] = 0,[10] = 1,[11] = 0,[12] = 0},
		[5]  = {[1] = 1,[2] = 0,[3] = 0,[4] = 1,[5] = 0,[6] = 1,[7] = 1,[8] = 1,[9] = 1,[10] = 1,[11] = 1,[12] = 0},
		[6]  = {[1] = 1,[2] = 0,[3] = 0,[4] = 1,[5] = 0,[6] = 1,[7] = 1,[8] = 1,[9] = 1,[10] = 1,[11] = 1,[12] = 0},
		[7]  = {[1] = 1,[2] = 0,[3] = 0,[4] = 1,[5] = 0,[6] = 1,[7] = 1,[8] = 1,[9] = 1,[10] = 1,[11] = 1,[12] = 0},
		[8]  = {[1] = 1,[2] = 0,[3] = 0,[4] = 1,[5] = 0,[6] = 1,[7] = 1,[8] = 1,[9] = 1,[10] = 1,[11] = 1,[12] = 0},
		[9]  = {[1] = 1,[2] = 0,[3] = 0,[4] = 1,[5] = 0,[6] = 1,[7] = 1,[8] = 1,[9] = 1,[10] = 1,[11] = 1,[12] = 0},
		[10] = {[1] = 1,[2] = 0,[3] = 0,[4] = 1,[5] = 0,[6] = 1,[7] = 1,[8] = 1,[9] = 1,[10] = 1,[11] = 1,[12] = 0},
		[11] = {[1] = 1,[2] = 0,[3] = 0,[4] = 1,[5] = 0,[6] = 1,[7] = 1,[8] = 1,[9] = 1,[10] = 1,[11] = 1,[12] = 0},
		[12] = {[1] = 1,[2] = 0,[3] = 1,[4] = 1,[5] = 1,[6] = 1,[7] = 1,[8] = 1,[9] = 0,[10] = 1,[11] = 0,[12] = 0},
		[13] = {[1] = 1,[2] = 0,[3] = 1,[4] = 1,[5] = 1,[6] = 1,[7] = 1,[8] = 1,[9] = 0,[10] = 1,[11] = 0,[12] = 0},
		[14] = {[1] = 1,[2] = 0,[3] = 1,[4] = 1,[5] = 1,[6] = 1,[7] = 1,[8] = 1,[9] = 0,[10] = 1,[11] = 0,[12] = 0},
		[15] = {[1] = 1,[2] = 1,[3] = 1,[4] = 1,[5] = 1,[6] = 1,[7] = 1,[8] = 0,[9] = 1,[10] = 0,[11] = 0,[12] = 0},
	},
	zOrder = 1,
	sizeScale = 1,
},
[132] = {
	modelName = "Starship",
	modelIndex = 132,
	modelType = 13,
	modelLevel = 1,
	modelCount = 8,
	partCount = 4,
	dressModelType = 3,
	length = 480,
	checkBtnScale = 500,
	modelConf = "march_dress_ship_conf",
	modelCCBName = "Ani_World_March_Point_Starship.ccbi",
	plistName = 'World_Starship.plist',
	picName = 'World_Starship.png',
	part = {
		[1] = {
			frames = {
				[1] = {1},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {1,1,1,1,1,4,4,4,4,4,4,4,1,1,1,1}
		},
		[2] = {
			frames = {
				[1] = {1,2,1},
				[2] = {2,1,2},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {4,3,3,3,3,2,2,2,3,2,2,2,3,3,3,3}
		},
		[3] = {
			frames = {
				[1] = {1,2,1},
				[2] = {2,1,2},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {3,2,2,4,2,1,1,1,2,1,1,1,2,4,2,2}
		},
		[4] = {
			frames = {
				[1] = {1,2,1},
				[2] = {2,1,2},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {2,4,4,2,4,3,3,3,1,3,3,3,4,2,4,4}
		},
	},
	zOrder = 1,
	sizeScale = 1,
},
[133] = {
	modelName = "Warsoldier",
	modelIndex = 133,
	modelType = 13,
	modelLevel = 1,
	modelCount = 7,
	partCount = 3,
	dressModelType = 4,
	length = 480,
	checkBtnScale = 500,
	modelConf = "march_dress_star_conf",
	modelCCBName = "Ani_World_March_Point_StarWars.ccbi",
	plistName = 'World_Starwars.plist',
	picName = 'World_Starwars.png',
	part = {
		[1] = {
			frames = {
				[1] = {1,2,1},
				[2] = {2,1,2},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {1,1,1,1,1,2,2,2,2,2,2,2,1,1,1,1,1}
		},
		[2] = {
			frames = {
				[1] = {1,2,1},
				[2] = {2,1,2},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {2,2,2,2,2,3,3,3,3,3,3,3,2,2,2,2,2}
		},
		[3] = {
			frames = {
				[1] = {1,2,1},
				[2] = {2,1,2},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {3,3,3,3,3,1,1,1,1,1,1,1,3,3,3,3,3}
		},
	},
	zOrder = 1,
	sizeScale = 1,
},
[134] = {
	modelName = "Rabbit",
	modelIndex = 134,
	modelType = 13,
	modelLevel = 1,
	modelCount = 3,
	partCount = 2,
	dressModelType = 5,
	length = 480,
	checkBtnScale = 500,
	modelConf = "march_dress_Rabbit_conf",
	modelCCBName = "Ani_World_March_Point_RabbitMoon.ccbi",
	plistName = 'World_RabbitMoon.plist',
	picName = 'World_RabbitMoon.png',
    part = {
        [1] = {
            frames = {
                [1] = {1,2,3,4,5,6,7,8,1},
                [2] = {2,3,4,5,6,7,8,1,2},
                [3] = {3,4,5,6,7,8,1,2,3},
                [4] = {4,5,6,7,8,1,2,3,4},
                [5] = {5,6,7,8,1,2,3,4,5},
                [6] = {6,7,8,1,2,3,4,5,6},
                [7] = {7,8,1,2,3,4,5,6,7},
                [8] = {8,1,2,3,4,5,6,7,8},
            },
            delay = 1/15,
            disorder = true,
            partZorder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        },
        [2] = {
            frames = {
                [1] = {1,2,3,4,5,6,7,8,1},
                [2] = {2,3,4,5,6,7,8,1,2},
                [3] = {3,4,5,6,7,8,1,2,3},
                [4] = {4,5,6,7,8,1,2,3,4},
                [5] = {5,6,7,8,1,2,3,4,5},
                [6] = {6,7,8,1,2,3,4,5,6},
                [7] = {7,8,1,2,3,4,5,6,7},
                [8] = {8,1,2,3,4,5,6,7,8},
            },
            delay = 1/15,
            disorder = true,
            partZorder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2}
		},
	},
	zOrder = 1,
	sizeScale = 1.25,
},
[135] = {
        modelName = "Porsche", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
        modelIndex = 135,  --顺序往后加，策划填
        modelType = 13,
        modelLevel = 1,
        modelCount = 6, --model数量 动效填
        partCount = 3, --part数量 动效填
        dressModelType = 6, --world_dress_model.conf 里面对应的modelType, 策划填 
        length = 480, --整个Entity的长度，用来计算行军hud的位置的 程序填
        checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
        modelConf = "march_dress_Anniversary_conf", --装扮生成的配置文件名，不要重名，策划填
        modelCCBName = "Ani_World_March_Point_Porsche.ccbi", --行军装扮名字，动效填
        plistName = "World_Porsche.plist",--行军装扮图集名字，动效填
        picName = "World_Porsche.png",--行军装扮图集名字，动效填
        zOrder = 1,
        sizeScale = 1,
        part = {  --动效填
            [1] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,1},
                    [2] = {2,3,4,5,6,7,8,9,10,1,2},
					[3] = {3,4,5,6,7,8,9,10,1,2,3},
					[4] = {4,5,6,7,8,9,10,1,2,3,4},
					[5] = {5,6,7,8,9,10,1,2,3,4,5},
					[6] = {6,7,8,9,10,1,2,3,4,5,6},
                    [7] = {7,8,9,10,1,2,3,4,5,6,7},
					[8] = {8,9,10,1,2,3,4,5,6,7,8},
					[9] = {9,10,1,2,3,4,5,6,7,8,9},
					[10] = {10,1,2,3,4,5,6,7,8,9,10},
                },
                delay = 1/10,
                disorder = true,
                partZorder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            },
            [2] = {
                frames = {
                    [1] = {1,2,3,4,1},
                    [2] = {2,3,4,1,2},
					[3] = {3,4,1,2,3},
					[4] = {4,1,2,3,4},
                },
                delay = 1/30,
                disorder = true,
                partZorder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            },
            [3] = {
                frames = {
                    [1] = {1,2,3,4,5,1},
                    [2] = {2,3,4,5,1,2},
					[3] = {3,4,5,1,2,3},
					[4] = {4,5,1,2,3,4},
					[5] = {5,1,2,3,4,5},
                },
                delay = 1/10,
                disorder = true,
                partZorder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
            },
        },
    },
[136] = {
        modelName = "Sledge", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
        modelIndex = 136,  --顺序往后加，策划填
        modelType = 13,
        modelLevel = 1,
        modelCount = 13, --model数量 动效填
        partCount = 4, --part数量 动效填
        dressModelType = 7, --world_dress_model.conf 里面对应的modelType, 策划填 
        length = 480, --整个Entity的长度，用来计算行军hud的位置的 程序填
        checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
        modelConf = "march_dress_IceCastle_conf", --装扮生成的配置文件名，不要重名，策划填
        modelCCBName = "Ani_World_March_Point_Sledge.ccbi", --行军装扮名字，动效填
        plistName = "World_Sledge.plist",--行军装扮图集名字，动效填
        picName = "World_Sledge.png",--行军装扮图集名字，动效填
        zOrder = 1,
        sizeScale = 1,
        part = {  --动效填
            [1] = {
                frames = {
                    [1] = {1,2,1},
                    [2] = {2,1,2},
                },
                delay = 1/15,
                disorder = true,
                partZorder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            },
            [2] = {
                frames = {
                    [1] = {1,1},
                },
                delay = 1/1,
                disorder = true,
                partZorder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            },
            [3] = {
                frames = {
                    [1] = {1,2,3,4,1},
                    [2] = {2,3,4,1,2},
					[3] = {3,4,1,2,3},
					[4] = {4,1,2,3,4},
                },
                delay = 1/10,
                disorder = true,
                partZorder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
            },
            [4] = {
                frames = {
                    [1] = {1,2,3,4,5,6,1},
                    [2] = {2,3,4,5,6,1,2},
					[3] = {3,4,5,6,1,2,3},
					[4] = {4,5,6,1,2,3,4},
					[5] = {5,6,1,2,3,4,5},
					[6] = {6,1,2,3,4,5,6},
                },
                delay = 1/10,
                disorder = true,
                partZorder = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
            },
        },
    },   
[137] = {
        modelName = "Oilcar", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
        modelIndex = 137,  --顺序往后加，策划填
        modelType = 13,
        modelLevel = 1,
        modelCount = 1, --model数量 动效填
        partCount = 1, --part数量 动效填
        dressModelType = 999, --world_dress_model.conf 里面对应的modelType, 策划填 
        length = 480, --整个Entity的长度，用来计算行军hud的位置的 程序填
        checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
        modelConf = "march_dress_snowgun_conf", --装扮生成的配置文件名，不要重名，策划填
        modelCCBName = "Ani_World_March_Point_SnowGun.ccbi", --行军装扮名字，动效填
        plistName = "WorldMarch_SnowGun.plist",--行军装扮图集名字，动效填
        picName = "WorldMarch_SnowGun.png",--行军装扮图集名字，动效填
        zOrder = 1,
        sizeScale = 1,
        part = {  --动效填
            [1] = {
                frames = {
                    [1] = {1},
                },
                delay = 1/1,
                disorder = true,
                partZorder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            },
        },
    },
[141] = {
	modelName = "SnowballFront",
	modelType = 14,
	modelLevel = 1,
	modelCount = 1,
	modelCCBName = "Ani_World_March_PointSnowball1.ccbi",
	plistName = 'WorldMarch_SnowGun.plist',
	picName = 'WorldMarch_SnowGun.png',	
	--picMaskName = 'WorldMarch_Harvester_Scout_Truck.mask.png',	
	Scale = 1,
	adjustDis = {
		[0]   = 0, 
		[1]   = 0, 
		[2]   = 0, 
		[3]   = 0, 
		[4]   = 0, 
		[5]   = 0, 
		[6]   = 0, 
		[7]   = 0, 
		[8]   = 0, 
		[9]   = 0, 
		[10]  = 0, 
		[11]  = 0, 
		[12]  = 0, 
		[13]  = 0, 
		[14]  = 0, 
		[15]  = 0, 
	},
},
[142] = {
	modelName = "SnowballEnd",
	modelType = 14,
	modelLevel = 1,
	modelCount = 1,
	modelCCBName = "Ani_World_March_PointSnowball2.ccbi",
	plistName = 'WorldMarch_SnowGun.plist',
	picName = 'WorldMarch_SnowGun.png',	
	--picMaskName = 'WorldMarch_Harvester_Scout_Truck.mask.png',
	Scale = 1,
	adjustDis = {
		[0]   = 0, 
		[1]   = 0, 
		[2]   = 0, 
		[3]   = 0, 
		[4]   = 0, 
		[5]   = 0, 
		[6]   = 0, 
		[7]   = 0, 
		[8]   = 0, 
		[9]   = 0, 
		[10]  = 0, 
		[11]  = 0, 
		[12]  = 0, 
		[13]  = 0, 
		[14]  = 0, 
		[15]  = 0, 
	},
},
[143] = {
	modelName = "Mech11",
	modelIndex = 143,
	modelType = 12,
	modelLevel = 5,
	modelCount = 1,
	modelMechaId = 1012,
	modelNode = 4,
	plistName = 'World_Mech11.plist',
	picName = 'World_Mech11.png',	
	part = {
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
	},
	zOrder = 1,
	adjustPos = {
		[0]   = {x = 0, y = -40}, 
		[1]   = {x =10, y = -10}, 
		[2]   = {x = 35, y = 0}, 
		[3]   = {x = 20, y = 30}, 
		[4]   = {x = 40, y = 40}, 
		[5]   = {x = 20, y = 40}, 
		[6]   = {x = 0, y = 50}, 
		[7]   = {x = 0, y = 50}, 
		[8]   = {x = 0, y = 40}, 
		[9]   = {x = 0, y = 60}, 
		[10]  = {x = -10, y = 40}, 
		[11]  = {x = -20, y = 40}, 
		[12]  = {x = -40, y = 50}, 
		[13]  = {x = -20, y = 30}, 
		[14]  = {x = -40, y = -10}, 
		[15]  = {x = -20, y = -10}, 
	},
	sizeScale = 1.45,
	},
[151] = {
	modelName = "Spy",
	modelType = 15,
	modelLevel = 1,
	modelCount = 1,
	modelCCBName = "Ani_World_March_PointSpy.ccbi",
	plistName = 'WorldMarch_Spy.plist',
	picName = 'WorldMarch_Spy.png',	
	--picMaskName = 'WorldMarch_Harvester_Scout_Truck.mask.png',	
	adjustDis = {
		[0]   = 0, 
		[1]   = 0, 
		[2]   = 0, 
		[3]   = 0, 
		[4]   = 0, 
		[5]   = 0, 
		[6]   = 0, 
		[7]   = 0, 
		[8]   = 0, 
		[9]   = 0, 
		[10]  = 0, 
		[11]  = 0, 
		[12]  = 0, 
		[13]  = 0, 
		[14]  = 0, 
		[15]  = 0, 
	},
},	
[152] = {
        modelName = "Police",
        modelIndex = 152,
        modelType = 13,
        modelLevel = 1,
        modelCount = 15,
        partCount = 4,
        dressModelType = 8,
        length = 480,
        checkBtnScale = 500,
        modelConf = "march_dress_Peacekeeping_conf",
        modelCCBName = "Ani_World_March_Point_Police.ccbi",
        plistName = "World_Police.plist",
        picName = "World_Police.png",
        zOrder = 1,
        sizeScale = 1,
        part = {  --动效填
            [1] = {
                frames = {
                    [1] = {1,2,3,1},
                    [2] = {2,3,1,2},
					[3] = {3,1,2,3},
                },
                delay = 1/15,
                disorder = true,
                partZorder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            },
            [2] = {
                frames = {
                    [1] = {1},
                },
                delay = 1/1,
                disorder = true,
                partZorder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            },
            [3] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,1}
                },
                delay = 1/15,
                disorder = true,
                partZorder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
				
            },
			[4] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,1},
                    [2] = {2,3,4,5,6,7,8,9,10,1,2},
					[3] = {3,4,5,6,7,8,9,10,1,2,3},
					[4] = {4,5,6,7,8,9,10,1,2,3,4},
					[5] = {5,6,7,8,9,10,1,2,3,4,5},
					[6] = {6,7,8,9,10,1,2,3,4,5,6},
					[7] = {7,8,9,10,1,2,3,4,5,6,7},
					[8] = {8,9,10,1,2,3,4,5,6,7,8},
					[9] = {9,10,1,2,3,4,5,6,7,8,9},
					[10] = {10,1,2,3,4,5,6,7,8,9,10},
                },
                delay = 1/15,
                disorder = true,
                partZorder = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
			},
        },
    },
[153] = {
        modelName = "Saibo", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
        modelIndex = 153,  --顺序往后加，策划填
        modelType = 13,
        modelLevel = 1,
        modelCount = 13, --model数量 动效填
        partCount = 2, --part数量 动效填
        dressModelType = 9, --world_dress_model.conf 里面对应的modelType, 策划填 
        length = 480, --整个Entity的长度，用来计算行军hud的位置的 程序填
        checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
        modelConf = "march_dress_saibo_conf", --装扮生成的配置文件名，不要重名，策划填
        modelCCBName = "Ani_World_March_Point_Saibo.ccbi", --行军装扮名字，动效填
        plistName = "World_Police.plist",--行军装扮图集名字，动效填
        picName = "World_Police.png",--行军装扮图集名字，动效填
        zOrder = 1,
        sizeScale = 1,
        part = {  --动效填
            [1] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,1},
                    [2] = {2,3,4,5,6,7,8,1,2},
					[3] = {3,4,5,6,7,8,1,2,3},
					[4] = {4,5,6,7,8,1,2,3,4},
					[5] = {5,6,7,8,1,2,3,4,5},
					[6] = {6,7,8,1,2,3,4,5,6},
					[7] = {7,8,1,2,3,4,5,6,7},
					[8] = {8,1,2,3,4,5,6,7,8},
                },
                delay = 1/8,
                disorder = true,
                partZorder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            },
            [2] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,1},
                    [2] = {2,3,4,5,6,7,8,9,1,2},
					[3] = {3,4,5,6,7,8,9,1,2,3},
					[4] = {4,5,6,7,8,9,1,2,3,4},
					[5] = {5,6,7,8,9,1,2,3,4,5},
					[6] = {6,7,8,9,1,2,3,4,5,6},
					[7] = {7,8,9,1,2,3,4,5,6,7},
					[8] = {8,9,1,2,3,4,5,6,7,8},
					[9] = {9,1,2,3,4,5,6,7,8,9},
                },
                delay = 1/8,
                disorder = true,
                partZorder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            },

        },
    },
	--移动堡垒配置
[154] = {
        modelName = "SUV", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
        modelIndex = 154,  --顺序往后加，策划填
        modelType = 13,
        modelLevel = 1,
        modelCount = 7, --model数量 动效填
        partCount = 4, --part数量 动效填
        dressModelType = 10, --world_dress_model.conf 里面对应的modelType, 策划填 
        length = 480, --整个Entity的长度，用来计算行军hud的位置的 程序填
        checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
        modelConf = "march_dress_suv_conf", --装扮生成的配置文件名，不要重名，策划填
        modelCCBName = "Ani_World_March_Point_SUV.ccbi", --行军装扮名字，动效填
        plistName = "World_SUV.plist",--行军装扮图集名字，动效填
        picName = "World_SUV.png",--行军装扮图集名字，动效填
        zOrder = 1,
        sizeScale = 1,
        part = {  --动效填
            [1] = {
                frames = {
                    [1] = {1,2,3,4,5,1},
                    [2] = {2,3,4,5,1,2},
					[3] = {3,4,5,1,2,3},
					[4] = {4,5,1,2,3,4},
					[5] = {5,1,2,3,4,5},
                },
                delay = 1/15,
                disorder = true,
                partZorder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            },
            [2] = {
                frames = {
                    [1] = {1},
                },
                delay = 1/1,
                disorder = true,
                partZorder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            },
            [3] = {
                frames = {
                    [1] = {1,2,3,1},
					[2] = {2,3,1,2},
					[3] = {3,1,2,3},
                },
                delay = 1/15,
                disorder = true,
                partZorder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
				
            },
			[4] = {
                frames = {
                    [1] = {1,2,1},
                    [2] = {2,1,2},
                },
                delay = 1/15,
                disorder = true,
                partZorder = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
			},
        },
    },
    ---辉煌之刃,两周年庆典行军
[155] = {
        modelName = "Twoship", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
        modelIndex = 155,  --顺序往后加，策划填
        modelType = 13,
        modelLevel = 1,
        modelCount = 6, --model数量 动效填
        partCount = 5, --part数量 动效填
        dressModelType = 11, --world_dress_model.conf 里面对应的modelType, 策划填 
        length = 480, --整个Entity的长度，用来计算行军hud的位置的 程序填
        checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
        modelConf = "march_dress_Twoship_conf", --装扮生成的配置文件名，不要重名，策划填
        modelCCBName = "Ani_World_March_Point_Twoship.ccbi", --行军装扮名字，动效填
        plistName = "World_Twoship.plist",--行军装扮图集名字，动效填
        picName = "World_Twoship.png",--行军装扮图集名字，动效填
        zOrder = 1,
        sizeScale = 1,
        part = {  --动效填
            [1] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1},
                },
                delay = 1/30,
                disorder = true,
                partZorder = {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
            },
            [2] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
                },
                delay = 1/30,
                disorder = true,
                partZorder = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
            },
            [3] = {
                frames = {
                    [1] = {1,1},
                },
                delay = 1/1,
                disorder = true,
                partZorder = {1,1,1,1,1,3,3,3,3,3,3,3,1,1,1,1,1},
				
            },
			[4] = {
                frames = {
                    [1] = {1,2,3,1},
                    [2] = {2,3,1,2},
					[3] = {3,1,2,3},
                },
                delay = 1/15,
                disorder = true,
                partZorder = {2,2,2,2,2,1,1,1,1,1,1,1,2,2,2,2,2},
			},
			[5] = {
                frames = {
                    [1] = {1,2,3,1},
                    [2] = {2,3,1,2},
					[3] = {3,1,2,3},
                },
                delay = 1/15,
                disorder = true,
                partZorder = {3,3,3,3,3,2,2,2,2,2,2,2,3,3,3,3,3},
			},
        },
    },
[156] = {
	modelName = "Mech12",
	modelIndex = 156,
	modelType = 12,
	modelLevel = 5,
	modelCount = 1,
	isSkin = 1,
	modelMechaId = 10001,
	modelNode = 4,
	plistName = 'World_Mech12.plist',
	picName = 'World_Mech12.png',	
	part = {
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				
				
				
				
				
				
			},
			delay = 1/10,
		},
	},
	zOrder = 1,
	adjustPos = {
		[0]   = {x = 0, y = -40}, 
		[1]   = {x =10, y = -10}, 
		[2]   = {x = 35, y = 0}, 
		[3]   = {x = 20, y = 30}, 
		[4]   = {x = 40, y = 40}, 
		[5]   = {x = 20, y = 40}, 
		[6]   = {x = 0, y = 50}, 
		[7]   = {x = 0, y = 50}, 
		[8]   = {x = 0, y = 40}, 
		[9]   = {x = 0, y = 60}, 
		[10]  = {x = -10, y = 40}, 
		[11]  = {x = -20, y = 40}, 
		[12]  = {x = -40, y = 50}, 
		[13]  = {x = -20, y = 30}, 
		[14]  = {x = -40, y = -10}, 
		[15]  = {x = -20, y = -10}, 
	},
	sizeScale = 1.45,
},
[157] = {
        modelName = "Fish", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
        modelIndex = 157,  --顺序往后加，策划填
        modelType = 13,
        modelLevel = 1,
        modelCount = 9, --model数量 动效填
        partCount = 4, --part数量 动效填
        dressModelType = 12, --world_dress_model.conf 里面对应的modelType, 策划填 
        length = 340, --整个Entity的长度，用来计算行军hud的位置的 程序填
        checkBtnScale = 400, --Entity上的ccMenu长度，用来接收点击事件 程序填
        modelConf = "march_dress_Fish_conf", --装扮生成的配置文件名，不要重名，策划填
        modelCCBName = "Ani_World_March_Point_Fish.ccbi", --行军装扮名字，动效填
        plistName = "World_Fish.plist",--行军装扮图集名字，动效填
        picName = "World_Fish.png",--行军装扮图集名字，动效填
        zOrder = 1,
        sizeScale = 1,
        part = {  --动效填
            [1] = {
                frames = {
                    [1] = {0,1,2,3,4,5,6,7,0},
                },
                delay = 1/8,
                disorder = true,
                partZorder = {7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7},
            },
            [2] = {
                frames = {
                    [1] = {0,1,2,3,4,5,6,7,0},
					[2] = {3,4,5,6,7,0,1,2,3},
					[3] = {7,0,1,2,3,4,5,6,7},
					[4] = {5,6,7,0,1,2,3,4,5},
					[5] = {2,3,4,5,6,7,0,1,2},
                },
                delay = 1/15,
                disorder = true,
                partZorder = {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
            },
            [3] = {
                frames = {
                    [1] = {0,1,2,3,4,5,6,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                },
                delay = 1/8,
                disorder = true,
                partZorder = {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
				
            },
			[4] = {
                frames = {
                    [1] = {0,1,2,3,4,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					[2] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					
					
                 },
                delay = 1/8,
                disorder = true,
                partZorder = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
			},
			
			
        },
    },
[158] = {
	modelName = "Mech13",
	modelIndex = 158,
	modelType = 12,
	modelLevel = 5,
	modelCount = 1,
	modelMechaId = 1013,
	modelNode = 4,
	plistName = 'World_Mech13.plist',
	picName = 'World_Mech13.png',	
	part = {
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
	},
	zOrder = 1,
	adjustPos = {
		[0]   = {x = 0, y = -40}, 
		[1]   = {x =10, y = -10}, 
		[2]   = {x = 35, y = 0}, 
		[3]   = {x = 20, y = 30}, 
		[4]   = {x = 40, y = 40}, 
		[5]   = {x = 20, y = 40}, 
		[6]   = {x = 0, y = 50}, 
		[7]   = {x = 0, y = 50}, 
		[8]   = {x = 0, y = 40}, 
		[9]   = {x = 0, y = 60}, 
		[10]  = {x = -10, y = 40}, 
		[11]  = {x = -20, y = 40}, 
		[12]  = {x = -40, y = 50}, 
		[13]  = {x = -20, y = 30}, 
		[14]  = {x = -40, y = -10}, 
		[15]  = {x = -20, y = -10}, 
	},
	sizeScale = 1.45,

},
[159] = {
        modelName = "StarGuard", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
        modelIndex = 159,  --顺序往后加，策划填
        modelType = 13,
        modelLevel = 1,
        modelCount = 6, --model数量 动效填
        partCount = 7, --part数量 动效填
        dressModelType = 13, --world_dress_model.conf 里面对应的modelType, 策划填 
        length = 490, --整个Entity的长度，用来计算行军hud的位置的 程序填
        checkBtnScale = 600, --Entity上的ccMenu长度，用来接收点击事件 程序填
        modelConf = "march_dress_StarGuard_conf", --装扮生成的配置文件名，不要重名，策划填
        modelCCBName = "Ani_World_March_Point_StarGuard.ccbi", --行军装扮名字，动效填
        plistName = "World_StarGuard.plist",--行军装扮图集名字，动效填
        picName = "World_StarGuard.png",--行军装扮图集名字，动效填
        zOrder = 1,
        sizeScale = 1,
        part = {  --动效填
            [1] = {
                frames = {
                    [1] = {1,1},
                },
                delay = 1/1,
                disorder = true,
                partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            },
            [2] = {
                frames = {
                    [1] = {0,1,2,3,4,5,6,7,0},
					[2] = {3,4,5,6,7,0,1,2,3},
					[3] = {7,0,1,2,3,4,5,6,7},
					[4] = {5,6,7,0,1,2,3,4,5},
					[5] = {2,3,4,5,6,7,0,1,2},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {6,6,6,6,2,2,2,2,2,2,2,2,2,6,6,6},
            },
            [3] = {
                frames = {
                    [1] = {0,1,2,3,4,5,6,7,0},
					[2] = {3,4,5,6,7,0,1,2,3},
					[3] = {7,0,1,2,3,4,5,6,7},
					[4] = {5,6,7,0,1,2,3,4,5},
					[5] = {2,3,4,5,6,7,0,1,2},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {5,5,5,5,3,3,3,3,3,3,3,3,3,5,5,5},
				
            },
			[4] = {
                frames = {
                    [1] = {0,1,2,3,4,5,6,7,0},
					[2] = {3,4,5,6,7,0,1,2,3},
					[3] = {7,0,1,2,3,4,5,6,7},
					[4] = {5,6,7,0,1,2,3,4,5},
					[5] = {2,3,4,5,6,7,0,1,2},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
			},
			[5] = {
                frames = {
                    [1] = {1,1},
                },
                delay = 1/1,
                disorder = true,
                partZOrder = {3,3,3,3,5,5,5,5,5,5,5,5,5,3,3,3},
			},
			[6] = {
                frames = {
                    [1] = {1,1},
                },
                delay = 1/1,
                disorder = true,
                partZOrder = {2,2,2,2,6,6,6,6,6,6,6,6,6,2,2,2},
            },
			[7] = {
                frames = {
                    [1] = {0,1,2,3,4,5,6,7,0},
					[2] = {3,4,5,6,7,0,1,2,3},
					[3] = {7,0,1,2,3,4,5,6,7},
					[4] = {5,6,7,0,1,2,3,4,5},
					[5] = {2,3,4,5,6,7,0,1,2},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7},
            },
			
        },
    },
[160] = {
        modelName = "Dragon", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
        modelIndex = 160,  --顺序往后加，策划填
        modelType = 13,
        modelLevel = 1,
        modelCount = 3, --model数量 动效填
        partCount = 3, --part数量 动效填
        dressModelType = 14, --world_dress_model.conf 里面对应的modelType, 策划填 
        length = 490, --整个Entity的长度，用来计算行军hud的位置的 程序填
        checkBtnScale = 600, --Entity上的ccMenu长度，用来接收点击事件 程序填
        modelConf = "march_dress_Dragon_conf", --装扮生成的配置文件名，不要重名，策划填
        modelCCBName = "Ani_World_March_Point_Dragon.ccbi", --行军装扮名字，动效填
        plistName = "World_Dragon.plist",--行军装扮图集名字，动效填
        picName = "World_Dragon.png",--行军装扮图集名字，动效填
        zOrder = 1,
        sizeScale = 1,
        part = {  --动效填
            [1] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,1},
				},
                delay = 1/8,
                disorder = true,
                partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            },
            [2] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,1}	
                },
                delay = 1/8,
                disorder = true,
                partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				
            },
			[3] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,1}	
                },
                delay = 1/8,
                disorder = true,
                partZOrder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,},
            },
			
        },
    },
[164] = {
        modelName = "Pumpkin", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
        modelIndex = 164,  --顺序往后加，策划填
        modelType = 13,
        modelLevel = 1,
        modelCount = 9, --model数量 动效填
        partCount = 1, --part数量 动效填
        dressModelType = 15, --world_dress_model.conf 里面对应的modelType, 策划填 
        length = 490, --整个Entity的长度，用来计算行军hud的位置的 程序填
        checkBtnScale = 600, --Entity上的ccMenu长度，用来接收点击事件 程序填
        modelConf = "march_dress_Pumpkin_conf", --装扮生成的配置文件名，不要重名，策划填
        modelCCBName = "Ani_World_March_Point_Pumpkin.ccbi", --行军装扮名字，动效填
        plistName = "World_Pumpkin.plist",--行军装扮图集名字，动效填
        picName = "World_Pumpkin.png",--行军装扮图集名字，动效填
        zOrder = 1,
        sizeScale = 1,
        part = {  --动效填
            [1] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,1},
                    [2] = {2,3,4,5,6,7,8,1,2},
					[3] = {3,4,5,6,7,8,1,2,3},
					[4] = {4,5,6,7,8,1,2,3,4},
					[5] = {5,6,7,8,1,2,3,4,5},
					[6] = {6,7,8,1,2,3,4,5,6},
					[7] = {7,8,1,2,3,4,5,6,7},
					[8] = {8,1,2,3,4,5,6,7,8},
				},
                delay = 1/8,
                disorder = true,
                partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
         
            },
        },
    },
[165] = {
	modelName = "Mech14",
	modelIndex = 165,
	modelType = 12,
	modelLevel = 5,
	modelCount = 1,
	isSkin = 1,
	modelMechaId = 10002,
	modelNode = 4,
	plistName = 'World_Mech14.plist',
	picName = 'World_Mech14.png',	
	part = {
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
	},
	zOrder = 1,
	adjustPos = {
		[0]   = {x = 0, y = -40}, 
		[1]   = {x =10, y = -10}, 
		[2]   = {x = 35, y = 0}, 
		[3]   = {x = 20, y = 30}, 
		[4]   = {x = 40, y = 40}, 
		[5]   = {x = 20, y = 40}, 
		[6]   = {x = 0, y = 50}, 
		[7]   = {x = 0, y = 50}, 
		[8]   = {x = 0, y = 40}, 
		[9]   = {x = 0, y = 60}, 
		[10]  = {x = -10, y = 40}, 
		[11]  = {x = -20, y = 40}, 
		[12]  = {x = -40, y = 50}, 
		[13]  = {x = -20, y = 30}, 
		[14]  = {x = -40, y = -10}, 
		[15]  = {x = -20, y = -10}, 
	},
	sizeScale = 1.45,
},
[166] = {
	modelName = "Kirov", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
	modelIndex = 166,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 3, --model数量 动效填
	partCount = 5, --part数量 动效填
	dressModelType = 16, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_Kirov_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_Kirov.ccbi", --行军装扮名字，动效填
	plistName = "World_Kirov.plist",--行军装扮图集名字，动效填
	picName = "World_Kirov.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1,1},
				
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3},
				
			},
			delay = 1/30,
			disorder = true,
			partZOrder = {4,4,4,4,2,2,2,2,2,2,2,2,2,4,4,4},
		},
		[3] = {
			frames = {
				[1] = {0,0},
			
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[4] = {
			frames = {
				[1] = {1,1},
				
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {2,2,2,2,4,4,4,4,4,4,4,4,4,2,2,2},
		},
		[5] = {
			frames = {
				[1] = {1,1},
				
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
		},
	},
},
[167] = {
	modelName = "Mech15",
	modelIndex = 167,
	modelType = 12,
	modelLevel = 5,
	modelCount = 1,
	modelMechaId = 1014,
	modelNode = 4,
	plistName = 'World_Mech15.plist',
	picName = 'World_Mech15.png',	
	part = {
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
	},
	zOrder = 1,
	adjustPos = {
		[0]   = {x = 0, y = -40}, 
		[1]   = {x =10, y = -10}, 
		[2]   = {x = 35, y = 0}, 
		[3]   = {x = 20, y = 30}, 
		[4]   = {x = 40, y = 40}, 
		[5]   = {x = 20, y = 40}, 
		[6]   = {x = 0, y = 50}, 
		[7]   = {x = 0, y = 50}, 
		[8]   = {x = 0, y = 40}, 
		[9]   = {x = 0, y = 60}, 
		[10]  = {x = -10, y = 40}, 
		[11]  = {x = -20, y = 40}, 
		[12]  = {x = -40, y = 50}, 
		[13]  = {x = -20, y = 30}, 
		[14]  = {x = -40, y = -10}, 
		[15]  = {x = -20, y = -10}, 
	},
	sizeScale = 1.45,
},
[168] = {
	modelName = "Snowman", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
	modelIndex = 168,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 5, --model数量 动效填
	partCount = 5, --part数量 动效填
	dressModelType = 17, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_Snowman_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_Snowman.ccbi", --行军装扮名字，动效填
	plistName = "World_Snowman.plist",--行军装扮图集名字，动效填
	picName = "World_Snowman.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
		},
		[2] = {
			frames = {
				[1] = {1,2,1},
					
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,1},
			
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
		},
		[4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
	},
},
[169] = {
	modelName = "Mech16",
	modelIndex = 169,
	modelType = 12,
	modelLevel = 5,
	modelCount = 1,
	isSkin = 1,
	modelMechaId = 10003,
	modelNode = 4,
	plistName = 'World_Mech16.plist',
	picName = 'World_Mech16.png',	
	part = {
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
	},
	zOrder = 1,
	adjustPos = {
		[0]   = {x = 0, y = -40}, 
		[1]   = {x =10, y = -10}, 
		[2]   = {x = 35, y = 0}, 
		[3]   = {x = 20, y = 30}, 
		[4]   = {x = 40, y = 40}, 
		[5]   = {x = 20, y = 40}, 
		[6]   = {x = 0, y = 50}, 
		[7]   = {x = 0, y = 50}, 
		[8]   = {x = 0, y = 40}, 
		[9]   = {x = 0, y = 60}, 
		[10]  = {x = -10, y = 40}, 
		[11]  = {x = -20, y = 40}, 
		[12]  = {x = -40, y = 50}, 
		[13]  = {x = -20, y = 30}, 
		[14]  = {x = -40, y = -10}, 
		[15]  = {x = -20, y = -10}, 
	},
	sizeScale = 1.45,
},
[170] = {
	modelName = "ChunJie", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
	modelIndex = 170,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 6, --model数量 动效填
	partCount = 2, --part数量 动效填
	dressModelType = 18, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_ChunJie_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_ChunJie.ccbi", --行军装扮名字，动效填
	plistName = "World_ChunJie.plist",--行军装扮图集名字，动效填
	picName = "World_ChunJie.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,1},
				
			},
			delay = 1/8,
			disorder = true,
			partZOrder = {2,2,2,2,1,1,1,1,1,1,1,1,1,2,2,2},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,1},
					
			},
			delay = 1/8,
			disorder = true,
			partZOrder = {1,1,1,1,2,2,2,2,2,2,2,2,2,1,1,1},
		},
	},
},
[171] = {
	modelName = "Mech17",
	modelIndex = 171,
	modelType = 12,
	modelLevel = 5,
	modelCount = 1,
	modelMechaId = 1015,
	modelNode = 4,
	plistName = 'World_Mech17.plist',
	picName = 'World_Mech17.png',	
	part = {
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,1},
			},
			delay = 1/8,
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,1},
			},
			delay = 1/8,
		},

	},
	zOrder = 1,
	adjustPos = {
		[0]   = {x = 0, y = -40}, 
		[1]   = {x =10, y = -10}, 
		[2]   = {x = 35, y = 0}, 
		[3]   = {x = 20, y = 30}, 
		[4]   = {x = 40, y = 40}, 
		[5]   = {x = 20, y = 40}, 
		[6]   = {x = 0, y = 50}, 
		[7]   = {x = 0, y = 50}, 
		[8]   = {x = 0, y = 40}, 
		[9]   = {x = 0, y = 60}, 
		[10]  = {x = -10, y = 40}, 
		[11]  = {x = -20, y = 40}, 
		[12]  = {x = -40, y = 50}, 
		[13]  = {x = -20, y = 30}, 
		[14]  = {x = -40, y = -10}, 
		[15]  = {x = -20, y = -10}, 
	},
	sizeScale = 1.45,
},
[172] = {
	modelName = "Mech18",
	modelIndex = 172,
	modelType = 12,
	modelLevel = 5,
	modelCount = 1,
	isSkin = 1,
	modelMechaId = 10004,
	modelNode = 4,
	plistName = 'World_Mech18.plist',
	picName = 'World_Mech18.png',	
	part = {
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
			},
			delay = 1/10,
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
			},
			delay = 1/10,
		},
	},
	zOrder = 1,
	adjustPos = {
		[0]   = {x = 0, y = 40}, 
		[1]   = {x =-20, y = 30}, 
		[2]   = {x = 60, y = 50}, 
		[3]   = {x = -10, y = 50}, 
		[4]   = {x = 0, y = 80}, 
		[5]   = {x = -10, y = 90}, 
		[6]   = {x = -30, y = 50}, 
		[7]   = {x = -30, y = 50}, 
		[8]   = {x = 8, y = 40}, 
		[9]   = {x = 0, y = 60}, 
		[10]  = {x = -10, y = 40}, 
		[11]  = {x = -20, y = -10}, 
		[12]  = {x = 0, y = 80}, 
		[13]  = {x = -20, y = 100}, 
		[14]  = {x = -40, y = 40}, 
		[15]  = {x = 0, y = 60}, 
	},
	sizeScale = 1.45,
},
[173] = {
        modelName = "Megatron", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
        modelIndex = 173,  --顺序往后加，策划填
        modelType = 13,
        modelLevel = 1,
        modelCount = 8, --model数量 动效填
        partCount = 7, --part数量 动效填
        dressModelType = 19, --world_dress_model.conf 里面对应的modelType, 策划填 
        length = 490, --整个Entity的长度，用来计算行军hud的位置的 程序填
        checkBtnScale = 600, --Entity上的ccMenu长度，用来接收点击事件 程序填
        modelConf = "march_dress_Megatron_conf", --装扮生成的配置文件名，不要重名，策划填
        modelCCBName = "Ani_World_March_Point_Megatron.ccbi", --行军装扮名字，动效填
        plistName = "World_Megatron.plist",--行军装扮图集名字，动效填
        picName = "World_Megatron.png",--行军装扮图集名字，动效填
        zOrder = 1,
        sizeScale = 1,
        part = {  --动效填
            [1] = {
                frames = {
                    [1] = {1,1},
                },
                delay = 1/1,
                disorder = true,
                partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            },
            [2] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,1},
					[2] = {3,4,5,6,7,8,9,10,1,2,3},
					[3] = {7,8,9,10,1,2,3,4,5,6,7},
					[4] = {5,6,7,8,9,10,1,2,3,4,5},
					[5] = {2,3,4,5,6,7,8,9,10,1,2},
                },
                delay = 1/10,
                disorder = true,
                partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            },
            [3] = {
                frames = {
                    [1] = {0,1,2,3,4,5,6,7,0},
					[2] = {3,4,5,6,7,0,1,2,3},
					[3] = {7,0,1,2,3,4,5,6,7},
					[4] = {5,6,7,0,1,2,3,4,5},
					[5] = {2,3,4,5,6,7,0,1,2},
                },
                delay = 1/10,
                disorder = true,
                partZOrder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},		
            },
			[4] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,1},
					[2] = {3,4,5,6,7,8,9,10,1,2,3},
					[3] = {7,8,9,10,1,2,3,4,5,6,7},
					[4] = {5,6,7,8,9,10,1,2,3,4,5},
					[5] = {2,3,4,5,6,7,8,9,10,1,2},
                },
                delay = 1/10,
                disorder = true,
                partZOrder = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
			},
			[5] = {
                frames = {
                    [1] = {1,1},
                },
                delay = 1/1,
                disorder = true,
                partZOrder = {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
			},
			[6] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,1},
					[2] = {3,4,5,6,7,8,9,10,1,2,3},
					[3] = {7,8,9,10,1,2,3,4,5,6,7},
					[4] = {5,6,7,8,9,10,1,2,3,4,5},
					[5] = {2,3,4,5,6,7,8,9,10,1,2},
                },
                delay = 1/10,
                disorder = true,
                partZOrder = {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
            },
			[7] = {
                frames = {
                    [1] = {1,2,3,4,5,6,1},
					[2] = {3,4,5,6,1,2,3},
					[3] = {4,5,6,1,2,3,4},
					[4] = {5,6,1,2,3,4,5},
					[5] = {2,3,4,5,6,1,2},
                },
                delay = 1/6,
                disorder = true,
                partZOrder = {7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7},
            },
        },
},
[174] = {
        modelName = "Shark", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
        modelIndex = 174,  --顺序往后加，策划填
        modelType = 13,
        modelLevel = 1,
        modelCount = 9, --model数量 动效填
        partCount = 6, --part数量 动效填
        dressModelType = 20, --world_dress_model.conf 里面对应的modelType, 策划填 
        length = 490, --整个Entity的长度，用来计算行军hud的位置的 程序填
        checkBtnScale = 600, --Entity上的ccMenu长度，用来接收点击事件 程序填
        modelConf = "march_dress_Shark_conf", --装扮生成的配置文件名，不要重名，策划填
        modelCCBName = "Ani_World_March_Point_Shark.ccbi", --行军装扮名字，动效填
        plistName = "World_Shark.plist",--行军装扮图集名字，动效填
        picName = "World_Shark.png",--行军装扮图集名字，动效填
        zOrder = 1,
        sizeScale = 1,
        part = {  --动效填
            [1] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
                },
                delay = 1/1,
                disorder = true,
                partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            },
            [2] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,1},
					[2] = {3,4,5,6,7,8,9,10,1,2,3},
					[3] = {7,8,9,10,1,2,3,4,5,6,7},
					[4] = {5,6,7,8,9,10,1,2,3,4,5},
					[5] = {2,3,4,5,6,7,8,9,10,1,2},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
				},
            [3] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,1},
					[2] = {3,4,5,6,7,8,9,10,1,2,3},
					[3] = {7,8,9,10,1,2,3,4,5,6,7},
					[4] = {5,6,7,8,9,10,1,2,3,4,5},
					[5] = {2,3,4,5,6,7,8,9,10,1,2},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},		
            },
			[4] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1},
                },
                delay = 1/30,
                disorder = true,
                partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
			},
			[5] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
                },
                delay = 1/1,
                disorder = true,
                partZOrder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
			},
			[6] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
                },
                delay = 1/10,
                disorder = true,
                partZOrder = {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
            },
        },
},
[175] = {
        modelName = "SummerKirov", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
        modelIndex = 175,  --顺序往后加，策划填
        modelType = 13,
        modelLevel = 1,
        modelCount = 5, --model数量 动效填
        partCount = 5, --part数量 动效填
        dressModelType = 22, --world_dress_model.conf 里面对应的modelType, 策划填 
        length = 490, --整个Entity的长度，用来计算行军hud的位置的 程序填
        checkBtnScale = 600, --Entity上的ccMenu长度，用来接收点击事件 程序填
        modelConf = "march_dress_SummerKirov_conf", --装扮生成的配置文件名，不要重名，策划填
        modelCCBName = "Ani_World_March_Point_SummerKirov.ccbi", --行军装扮名字，动效填
        plistName = "World_SummerKirov.plist",--行军装扮图集名字，动效填
        picName = "World_SummerKirov.png",--行军装扮图集名字，动效填
        zOrder = 1,
        sizeScale = 1,
        part = {  --动效填
            [1] = {
                frames = {
                    [1] = {1,2,3,4,5,6,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            },
            [2] = {
                frames = {
                    [1] = {1,1},
                },
                delay = 1/1,
                disorder = true,
                partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
				},
            [3] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},		
            },
			[4] = {
                frames = {
                    [1] = {1,1},
                },
                delay = 1/1,
                disorder = true,
                partZOrder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},	
			},
			[5] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
            },
        },
},
[176] = {
        modelName = "IronShark", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
        modelIndex = 176,  --顺序往后加，策划填
        modelType = 13,
        modelLevel = 1,
        modelCount = 5, --model数量 动效填
        partCount = 9, --part数量 动效填
        dressModelType = 21, --world_dress_model.conf 里面对应的modelType, 策划填 
        length = 490, --整个Entity的长度，用来计算行军hud的位置的 程序填
        checkBtnScale = 600, --Entity上的ccMenu长度，用来接收点击事件 程序填
        modelConf = "march_dress_IronShark_conf", --装扮生成的配置文件名，不要重名，策划填
        modelCCBName = "Ani_World_March_Point_IronShark.ccbi", --行军装扮名字，动效填
        plistName = "World_IronShark.plist",--行军装扮图集名字，动效填
        picName = "World_IronShark.png",--行军装扮图集名字，动效填
        zOrder = 1,
        sizeScale = 1,
        part = {  --动效填
            [1] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {5,5,5,5,3,3,3,3,3,3,3,3,3,5,5,5},
            },
            [2] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
				},
            [3] = {
                frames = {
                    [1] = {1,2,3,4,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},		
            },
			[4] = {
                frames = {
                    [1] = {1,2,3,4,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},	
			},
			[5] = {
                frames = {
                    [1] = {1,1},
                },
                delay = 1/1,
                disorder = true,
                partZOrder = {3,3,3,3,5,5,5,5,5,5,5,5,5,3,3,3},
            },
			[6] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
            },
			[7] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7},
            },
			[8] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8},
            },
			[9] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9},
            },
        },
},
[177] = {
	modelName = "Mech19",
	modelIndex = 177,
	modelType = 12,
	modelLevel = 5,
	modelCount = 1,
	isSkin = 1,
	modelMechaId = 10005,
	modelNode = 4,
	plistName = 'World_Mech19.plist',
	picName = 'World_Mech19.png',	
	part = {
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,1},
			},
			delay = 1/10,
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,1},
			},
			delay = 1/10,
		},
	},
	zOrder = 1,
	adjustPos = {
		[0]   = {x = -40, y = -10}, 
		[1]   = {x =-30, y = 30}, 
		[2]   = {x = -10, y = 50}, 
		[3]   = {x = -10, y = 50}, 
		[4]   = {x = -50, y = 60}, 
		[5]   = {x = -10, y = 90}, 
		[6]   = {x = -30, y = 50}, 
		[7]   = {x = -40, y = 50}, 
		[8]   = {x = -40, y = 10}, 
		[9]   = {x = 40, y = 60}, 
		[10]  = {x = 50, y = 40}, 
		[11]  = {x = 0, y = 70}, 
		[12]  = {x = 40, y = 60}, 
		[13]  = {x = 40, y = 20}, 
		[14]  = {x = 0, y = 40}, 
		[15]  = {x = 40, y = 40}, 
	},
	sizeScale = 1.45,
},
[178] = {
	modelName = "Spacecraft", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
	modelIndex = 178,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 6, --model数量 动效填
	partCount = 3, --part数量 动效填hhh
	dressModelType = 23, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 450, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 600, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_Spacecraft_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_Spacecraft.ccbi", --行军装扮名字，动效填
	plistName = "World_Spacecraft.plist",--行军装扮图集名字，动效填
	picName = "World_Spacecraft.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1,1},
				
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,1},
					
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,1},
					
			},
			delay = 1/8,
			disorder = true,
			partZOrder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
	},
},
},
[179] = {
	modelName = "SeventhEvening",  --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
	modelIndex = 179,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 5,  --model数量 动效填
	partCount = 9,  --part数量 动效填
	dressModelType = 25,  --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 360,  --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 400,  --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_SeventhEvening_conf",  --装扮生成的配置文件名，不要重名，策划填
	modelCCBName= "Ani_World_March_Point_SeventhEvening.ccbi",--行军装扮名字，动效填
	plistName = 'World_SeventhEvening.plist',--行军装扮图集名字，动效填
	picName = 'World_SeventhEvening.png',--行军装扮图集名字，动效填
	zOrder = 1,
    sizeScale = 1,
        part = {  --动效填
            [1] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {4,4,4,4,4,3,3,3,6,3,3,3,4,1,4,4},
            },
            [2] = {
                frames = {
                    [1] = {1,1},
                },
                delay = 1/1,
                disorder = true,
                partZOrder = {1,1,1,1,1,4,4,4,3,4,4,4,1,2,1,1},
				},
            [3] = {
                frames = {
                    [1] = {1,2,3,4,5,6,1},
                },
                delay = 1/10,
                disorder = true,
                partZOrder = {2,2,2,2,2,5,5,5,4,5,5,5,2,3,2,2},		
            },
			[4] = {
                frames = {
                    [1] = {1,2,3,4,5,6,1},
                },
                delay = 1/10,
                disorder = true,
                partZOrder = {3,3,3,3,3,6,6,6,5,6,6,6,3,4,3,3},	
			},
			[5] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {5,5,5,5,5,7,7,7,7,7,7,7,5,5,5,5},
            },
			[6] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {6,6,6,6,6,8,8,8,8,8,8,8,6,6,6,6},
            },
			[7] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {7,7,7,7,7,2,2,2,2,2,2,2,7,7,7,7},
            },
			[8] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {8,8,8,8,8,1,1,1,1,1,1,1,8,8,8,8},
            },
			[9] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9},
            },
	},
	},
[180] = {
        modelName = "CyberOverlord", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
        modelIndex = 180,  --顺序往后加，策划填
        modelType = 13,
        modelLevel = 1,
        modelCount = 6, --model数量 动效填
        partCount = 5, --part数量 动效填
        dressModelType = 24, --world_dress_model.conf 里面对应的modelType, 策划填 
        length = 480, --整个Entity的长度，用来计算行军hud的位置的 程序填
        checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
        modelConf = "march_dress_CyberOverlord_conf", --装扮生成的配置文件名，不要重名，策划填
        modelCCBName = "Ani_World_March_Point_CyberOverlord.ccbi", --行军装扮名字，动效填
        plistName = "World_CyberOverlord.plist",--行军装扮图集名字，动效填
        picName = "World_CyberOverlord.png",--行军装扮图集名字，动效填
        zOrder = 1,
        sizeScale = 1,
        part = {  --动效填
            [1] = {
                frames = {
                    [1] = {1,1},
                },
                delay = 1/1,
                disorder = true,
                partZorder = {2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2},
            },
            [2] = {
                frames = {
                    [1] = {1,2,1},
					[2] = {2,1,2},
                },
                delay = 1/10,
                disorder = true,
                partZorder = {3,3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3},
            },
            [3] = {
                frames = {
                    [1] = {1,2,3,4,5,1},
                },
                delay = 1/30,
                disorder = true,
                partZorder = {4,4,3,3,3,3,3,3,3,3,3,3,3,3,3,3,4},
            },
			[4] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {5,5,4,4,4,4,4,4,4,4,4,4,4,4,4,5},	
			},
			[5] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {1,1,5,5,5,5,5,5,5,5,5,5,5,5,5,1},
            },
			
        },
    },
[181] = {
	modelName = "Mech20",
	modelIndex = 181,
	modelType = 12,
	modelLevel = 5,
	modelCount = 1,
	isSkin = 1,
	modelMechaId = 10006,
	modelNode = 4,
	plistName = 'World_Mech20.plist',
	picName = 'World_Mech20.png',	
	part = {
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
			},
			delay = 1/10,
		},
	},
	zOrder = 1,
	adjustPos = {
		[0]   = {x = -30, y = -20}, 
		[1]   = {x = 0, y = -10}, 
		[2]   = {x = 15, y = 10}, 
		[3]   = {x = -40, y = 65}, 
		[4]   = {x = 40, y = 60}, 
		[5]   = {x = 20, y = 40}, 
		[6]   = {x = 0, y = 50}, 
		[7]   = {x = 0, y = 50}, 
		[8]   = {x = -30, y = 20}, 
		[9]   = {x = 25, y = 60}, 
		[10]  = {x = 20, y = 50}, 
		[11]  = {x = 20, y = 65}, 
		[12]  = {x = 0, y = 60}, 
		[13]  = {x = -20, y = 30}, 
		[14]  = {x = -40, y = -10}, 
		[15]  = {x = -20, y = -10}, 
    },
	sizeScale = 1.45,
},
[182] = {
        modelName = "GloryFist", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
        modelIndex = 182,  --顺序往后加，策划填
        modelType = 13,
        modelLevel = 1,
        modelCount = 1, --model数量 动效填
        partCount = 11, --part数量 动效填
        dressModelType = 26, --world_dress_model.conf 里面对应的modelType, 策划填 
        length = 480, --整个Entity的长度，用来计算行军hud的位置的 程序填
        checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
        modelConf = "march_dress_GloryFist_conf", --装扮生成的配置文件名，不要重名，策划填
        modelCCBName = "Ani_World_March_Point_GloryFist.ccbi", --行军装扮名字，动效填
        plistName = "World_GloryFist.plist",--行军装扮图集名字，动效填
        picName = "World_GloryFist.png",--行军装扮图集名字，动效填
        zOrder = 1,
        sizeScale = 1,
        part = {  --动效填
            [1] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
                },
                delay = 1/15,
                disorder = true,
                partZorder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            },
            [2] = {
                frames = {
                    [1] = {1,1},
                },
                delay = 1/1,
                disorder = true,
                partZorder = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
            },
            [3] = {
                frames = {
                    [1] = {1,1},
                },
                delay = 1/1,
                disorder = true,
                partZorder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
            },
			[4] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},	
			},
			[5] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
            },
			[6] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7},
            },
			[7] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8},
            },
			[8] = {
                frames = {
                    [1] = {1,1},
                },
                delay = 1/1,
                disorder = true,
                partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            },
			[9] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9},
            },	
			[10] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
            },
			[11] = {
                frames = {
                    [1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1},
                },
                delay = 1/15,
                disorder = true,
                partZOrder = {11,11,11,11,11,11,11,11,11,11,11,11,11,11,11},
            },
			
        },
    },
[183] = {
    modelName = "Mech21",
    modelIndex = 183,
    modelType = 12,
    modelLevel = 5,
    modelCount = 1,
    isSkin = 1,
    modelMechaId = 10007,
    modelNode = 4, 
    plistName = 'World_Mech21.plist', 
    picName = 'World_Mech21.png',
    part = { 
        [1] = {
            frames = {
                [1] = {1,2,3,4,5,6,1},
            },
            delay = 1/8,
        },
    },
    zOrder = 1,
    adjustPos = { 
        [0]   = {x = 0, y = -40}, 
        [1]   = {x =10, y = -10}, 
        [2]   = {x = 35, y = 0}, 
        [3]   = {x = 20, y = 30}, 
        [4]   = {x = 40, y = 40}, 
        [5]   = {x = 20, y = 40}, 
        [6]   = {x = 0, y = 50}, 
        [7]   = {x = 0, y = 50}, 
        [8]   = {x = 0, y = 40}, 
        [9]   = {x = 0, y = 60}, 
        [10]  = {x = -10, y = 40}, 
        [11]  = {x = -20, y = 40}, 
        [12]  = {x = -40, y = 50}, 
        [13]  = {x = -20, y = 30}, 
        [14]  = {x = -40, y = -10}, 
        [15]  = {x = -20, y = -10}, 
    },
    sizeScale = 1.45, 
},
[184] = {
	modelName = "GiftBox", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
	modelIndex = 184,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 7, --model数量 动效填
	partCount = 2, --part数量 动效填
	dressModelType = 27, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_GiftBox_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_GiftBox.ccbi", --行军装扮名字，动效填
	plistName = "World_GiftBox.plist",--行军装扮图集名字，动效填
	picName = "World_GiftBox.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {2,2,2,2,2,1,1,1,1,1,1,1,2,2,2,2},
		},
		[2] = {
			frames = {
				[1] = {1,1},
					
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,2,2,2,2,2,2,2,1,1,1,1},
		},
	},
},
[185] = {
	modelName = "RabbitTeam", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
	modelIndex = 185,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 5, --model数量 动效填
	partCount = 6, --part数量 动效填
	dressModelType = 28, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_RabbitTeam_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_RabbitTeam.ccbi", --行军装扮名字，动效填
	plistName = "World_RabbitTeam.plist",--行军装扮图集名字，动效填
	picName = "World_RabbitTeam.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1,1},
				
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
					
			},
			delay = 1/7,
			disorder = true,
			partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
					
			},
			delay = 1/7,
			disorder = true,
			partZOrder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
		},
		[4] = {
			frames = {
				[1] = {1,2,1},
					
			},
			delay = 1/30,
			disorder = true,
			partZOrder = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
		},
		[5] = {
			frames = {
				[1] = {1,2,3,4,5,1},
					
			},
			delay = 1/30,
			disorder = true,
			partZOrder = {6,6,6,6,6,5,5,5,5,5,5,5,6,6,6,6},
		},
		[6] = {
			frames = {
				[1] = {1,1},
					
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {5,5,5,5,5,6,6,6,6,6,6,6,5,5,5,5},
		},			
	},
},
[186] = {
	modelName = "SpaceThruster", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
	modelIndex = 186,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 5, --model数量 动效填
	partCount = 4, --part数量 动效填
	dressModelType = 29, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_SpaceThruster_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_SpaceThruster.ccbi", --行军装扮名字，动效填
	plistName = "World_SpaceThruster.plist",--行军装扮图集名字，动效填
	picName = "World_SpaceThruster.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1,1},
				
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
			
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {4,4,4,4,4,2,2,2,2,2,2,2,4,4,4,4},
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,1},
					
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
		},
		[4] = {
			frames = {
				[1] = {1,1},
					
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {2,2,2,2,2,4,4,4,4,4,4,4,2,2,2,2},
		},
	},
},
[187] = {
	modelName = "WarOrder", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
	modelIndex = 187,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 6, --model数量 动效填
	partCount = 6, --part数量 动效填
	dressModelType = 30, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_WarOrder_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_WarOrder.ccbi", --行军装扮名字，动效填
	plistName = "World_WarOrder.plist",--行军装扮图集名字，动效填
	picName = "World_WarOrder.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1,1},
				
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
			
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
		},
		[3] = {
			frames = {
				[1] = {1,1},
					
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
		},
		[4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
					
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
		},
		[5] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,1},
					
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
		},
		[6] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,1},
					
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
		},
	},
},
[188] = {
	modelName = "DemolitionTeam", --拼接图片名字用的，比如摩托车，March_DemolitionTeam_Part1_0_1.png
	modelIndex = 188,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 7, --model数量 动效填
	partCount = 5, --part数量 动效填
	dressModelType = 31, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_DemolitionTeam_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_DemolitionTeam.ccbi", --行军装扮名字，动效填
	plistName = "World_DemolitionTeam.plist",--行军装扮图集名字，动效填
	picName = "World_DemolitionTeam.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1,1},
				
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
			
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
		},
		[3] = {
			frames = {
				[1] = {1,1},
					
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
		},
		[4] = {
			frames = {
				[1] = {1,2,1},
					
			},
			delay = 1/30,
			disorder = true,
			partZOrder = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
		},
		[5] = {
			frames = {
				[1] = {1,1},
					
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
		},
	},
},
[189] = {
	modelName = "LionTeam", --拼接图片名字用的，比如摩托车，March_Warsolder10_Part1_0_1.png
	modelIndex = 189,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 3, --model数量 动效填
	partCount = 5, --part数量 动效填
	dressModelType = 32, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_LionTeam_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_LionTeam.ccbi", --行军装扮名字，动效填
	plistName = "World_LionTeam.plist",--行军装扮图集名字，动效填
	picName = "World_LionTeam.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,1},
				[2] = {2,3,4,5,6,1,2},
			
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,1},
				[2] = {2,3,4,5,6,1,2},

			},
			delay = 1/10,
			disorder = true,
			partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,1},

			},
			delay = 1/10,
			disorder = true,
			partZOrder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
		},
		[4] = {
			frames = {
				[1] = {1,2,3,4,1},
				
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
		},
		[5] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
		
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
		},
	},
},
[190] = {
	modelName = "Jellyfish", --拼接图片名字用的，比如摩托车，March_"Fish"_Part1_0_1.png
	modelIndex = 190,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 8, --model数量 动效填
	partCount = 3, --part数量 动效填
	dressModelType = 33, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_Jellyfish_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_Jellyfish.ccbi", --行军装扮名字，动效填
	plistName = "World_Jellyfish.plist",--行军装扮图集名字，动效填
	picName = "World_Jellyfish.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,16,17,18,19,20,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,16,17,18,19,20,1,2,3,4,5,6,7,8},
			
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,16,17,18,19,20,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,16,17,18,19,20,1,2,3,4,5,6,7,8},
			
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,16,17,18,19,20,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,16,17,18,19,20,1,2,3,4,5,6,7,8},
				
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
		},
	},
},
[191] = {
    modelName = "Mech22",
    modelIndex = 191,
    modelType = 12, -- 固定标识, 策划填.
    modelLevel = 5, -- 不用动.
    modelCount = 1, -- model数量.
    isSkin = 1, -- 是否是机甲皮肤.
    modelMechaId = 10008, -- 机甲id, 策划填.
    modelNode = 4, -- 加在哪一层 (1,2,3,4),默认为 4 .
    plistName = 'World_Mech22.plist', -- 行军装扮图集名字, 动效填.
    picName = 'World_Mech22.png', -- 行军装扮图集名字, 动效填.
    part = { -- 动效填
        [1] = {
            frames = { -- 参数{frames}, Array从1开始, 数组内的数字为序列帧编号.
                [1] = {1,2,3,4,5,6,7,8,9,10,1},
            },
            delay = 1/8, -- 每帧间隔时长.
        },
    },
    zOrder = 1,
    adjustPos = { -- 16个方向，机甲位置, 默认不改.
        [0]   = {x = 0, y = -40}, 
        [1]   = {x =10, y = -10}, 
        [2]   = {x = 35, y = 0}, 
        [3]   = {x = 20, y = 30}, 
        [4]   = {x = 40, y = 40}, 
        [5]   = {x = 20, y = 40}, 
        [6]   = {x = 0, y = 50}, 
        [7]   = {x = 0, y = 50}, 
        [8]   = {x = 0, y = 40}, 
        [9]   = {x = 0, y = 60}, 
        [10]  = {x = -10, y = 40}, 
        [11]  = {x = -20, y = 40}, 
        [12]  = {x = -40, y = 50}, 
        [13]  = {x = -20, y = 30}, 
        [14]  = {x = -40, y = -10}, 
        [15]  = {x = -20, y = -10}, 
    },
    sizeScale = 1.4, -- 整体缩放.
},
[192] = {
	modelName = "Qixi", --拼接图片名字用的，比如摩托车，March_"Fish"_Part1_0_1.png
	modelIndex = 192,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 7, --model数量 动效填
	partCount = 8, --part数量 动效填
	dressModelType = 34, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_Qixi_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_Qixi.ccbi", --行军装扮名字，动效填
	plistName = "World_Qixi.plist",--行军装扮图集名字，动效填
	picName = "World_Qixi.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {3,3,2,2,2,2,2,2,2,2,2,2,2,2,2,3},
		},
		[3] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {2,2,3,3,3,3,3,3,3,3,3,3,3,3,3,2},
		},
		[4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
		},
		[5] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5}
		},
		[6] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},	
		},
		[7] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7},	
		},
		[8] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8},	
		},
	},
},
[193] = {
	modelName = "JadeRabbit", --拼接图片名字用的，比如摩托车，March_"Fish"_Part1_0_1.png
	modelIndex = 193,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 5, --model数量 动效填
	partCount = 7, --part数量 动效填
	dressModelType = 35, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_JadeRabbit_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_JadeRabbit.ccbi", --行军装扮名字，动效填
	plistName = "World_JadeRabbit.plist",--行军装扮图集名字，动效填
	picName = "World_JadeRabbit.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {5,5,5,5,2,2,2,2,2,2,2,2,2,5,5,5},
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {6,6,6,6,3,3,3,3,3,3,3,3,3,6,6,6},
		},
		[4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},

			},
			delay = 1/8,
			disorder = true,
			partZOrder = {7,7,7,7,7,4,4,4,4,4,4,4,4,7,7,7},
		},
		[5] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {2,2,2,2,5,5,5,5,5,5,5,5,5,2,2,2}
		},
		[6] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {3,3,3,3,6,6,6,6,6,6,6,6,6,3,3,3},
		},
		[7] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {4,4,4,4,6,7,7,7,7,7,7,7,7,4,4,4},
		},
	},
},
[194] = {
	modelName = "FiveYear", --拼接图片名字用的，比如摩托车，March_"Fish"_Part1_0_1.png
	modelIndex = 194,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 9, --model数量 动效填
	partCount = 11, --part数量 动效填
	dressModelType = 36, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_FiveYear_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_FiveYear.ccbi", --行军装扮名字，动效填
	plistName = "World_FiveYear.plist",--行军装扮图集名字，动效填
	picName = "World_FiveYear.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			
		},
		[2] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
			
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},
				[7] = {7,8,9,10,1,2,3,4,5,6,7},
				[8] = {8,9,10,1,2,3,4,5,6,7,8},
				[9] = {9,10,1,2,3,4,5,6,7,8,9},
				[10] = {10,1,2,3,4,5,6,7,8,9,10},

			},
			delay = 1/30,
			disorder = true,
			partZOrder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
			
		},
		[4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},
				[7] = {7,8,9,10,1,2,3,4,5,6,7},
				[8] = {8,9,10,1,2,3,4,5,6,7,8},
				[9] = {9,10,1,2,3,4,5,6,7,8,9},
				[10] = {10,1,2,3,4,5,6,7,8,9,10},

			},
			delay = 1/10,
			disorder = true,
			partZOrder = {10,10,10,10,4,4,4,4,4,4,4,4,4,10,10,10},
			
		},
		[5] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {4,4,4,4,5,5,5,5,5,5,5,5,5,4,4,4}
			
		},
		[6] = {
			frames = {
				[1] = {1,2,3,4,5,1},

			},
			delay = 1/30,
			disorder = true,
			partZOrder = {5,5,5,5,6,6,6,6,6,6,6,6,6,5,5,5},
			
		},
		[7] = {
			frames = {
				[1] = {1,2,3,4,5,1},

			},
			delay = 1/30,
			disorder = true,
			partZOrder = {6,6,6,6,7,7,7,7,7,7,7,7,7,6,6,6},
			
		},
		[8] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},

			},
			delay = 1/8,
			disorder = true,
			partZOrder = {11,11,11,11,8,8,8,8,8,8,8,8,8,11,11,11},
			
		},
		[9] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,12,13,14,15,1,2,3,4,5,6,7,8,9},
				
			},
			delay = 1/30,
			disorder = true,
			partZOrder = {7,7,7,7,9,9,9,9,9,9,9,9,9,7,7,7},
			
		},
		[10] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},
				[7] = {7,8,9,10,1,2,3,4,5,6,7},
				[8] = {8,9,10,1,2,3,4,5,6,7,8},
				[9] = {9,10,1,2,3,4,5,6,7,8,9},
				[10] = {10,1,2,3,4,5,6,7,8,9,10},

			},
			delay = 1/30,
			disorder = true,
			partZOrder = {8,8,8,8,10,10,10,10,10,10,10,10,10,8,8,8},
		},
		[11] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},
				[7] = {7,8,9,10,1,2,3,4,5,6,7},
				[8] = {8,9,10,1,2,3,4,5,6,7,8},
				[9] = {9,10,1,2,3,4,5,6,7,8,9},
				[10] = {10,1,2,3,4,5,6,7,8,9,10},
				
			},
			delay = 1/30,
			disorder = true,
			partZOrder = {9,9,9,9,11,11,11,11,11,11,11,11,11,9,9,9},
		},
	},
},
[195] = {
    modelName = "Mech24",
    modelIndex = 195, --防坦：泰坦
    modelType = 12, -- 固定标识, 策划填.
    modelLevel = 5, -- 不用动.
    modelCount = 1, -- model数量.
    isEnabling = 1, -- 是否是机甲赋能.
    modelMechaId = 10013, -- 机甲id, 策划填.
    modelNode = 4, -- 加在哪一层 (1,2,3,4),默认为 4 .
    plistName = 'World_Mech24.plist', -- 行军装扮图集名字, 动效填.
    picName = 'World_Mech24.png', -- 行军装扮图集名字, 动效填.
    part = { -- 动效填
        [1] = {
            frames = { -- 参数{frames}, Array从1开始, 数组内的数字为序列帧编号.
                [1] = {1,2,3,4,5,6,7,8,1},
            },
            delay = 1/8, -- 每帧间隔时长.
        },
    },
    zOrder = 1,
    adjustPos = { -- 16个方向，机甲位置, 默认不改.
        [0]   = {x = 0, y = -40}, 
        [1]   = {x =10, y = -10}, 
        [2]   = {x = 35, y = 0}, 
        [3]   = {x = 20, y = 30}, 
        [4]   = {x = 40, y = 40}, 
        [5]   = {x = 20, y = 40}, 
        [6]   = {x = 0, y = 50}, 
        [7]   = {x = 0, y = 50}, 
        [8]   = {x = 0, y = 40}, 
        [9]   = {x = 0, y = 60}, 
        [10]  = {x = -10, y = 40}, 
        [11]  = {x = -20, y = 40}, 
        [12]  = {x = -40, y = 50}, 
        [13]  = {x = -20, y = 30}, 
        [14]  = {x = -40, y = -10}, 
        [15]  = {x = -20, y = -10}, 
    },
    sizeScale = 1, -- 整体缩放.
},
[196] = {
    modelName = "Mech23",
    modelIndex = 196, --主站：毁灭
    modelType = 12, -- 固定标识, 策划填.
    modelLevel = 5, -- 不用动.
    modelCount = 1, -- model数量.
    isEnabling = 1, -- 是否是机甲赋能.
    modelMechaId = 10011, -- 机甲id, 策划填.
    modelNode = 4, -- 加在哪一层 (1,2,3,4),默认为 4 .
    plistName = 'World_Mech23.plist', -- 行军装扮图集名字, 动效填.
    picName = 'World_Mech23.png', -- 行军装扮图集名字, 动效填.
    part = { -- 动效填
        [1] = {
            frames = { -- 参数{frames}, Array从1开始, 数组内的数字为序列帧编号.
                [1] = {1,2,3,4,5,6,7,8,1},
            },
            delay = 1/8, -- 每帧间隔时长.
        },
    },
    zOrder = 1,
    adjustPos = { -- 16个方向，机甲位置, 默认不改.
        [0]   = {x = 0, y = -40}, 
        [1]   = {x =10, y = -10}, 
        [2]   = {x = 35, y = 0}, 
        [3]   = {x = 20, y = 30}, 
        [4]   = {x = 40, y = 40}, 
        [5]   = {x = 20, y = 40}, 
        [6]   = {x = 0, y = 50}, 
        [7]   = {x = 0, y = 50}, 
        [8]   = {x = 0, y = 40}, 
        [9]   = {x = 0, y = 60}, 
        [10]  = {x = -10, y = 40}, 
        [11]  = {x = -20, y = 40}, 
        [12]  = {x = -40, y = 50}, 
        [13]  = {x = -20, y = 30}, 
        [14]  = {x = -40, y = -10}, 
        [15]  = {x = -20, y = -10}, 
    },
    sizeScale = 1, -- 整体缩放.
},
[197] = {
    modelName = "Mech28",
    modelIndex = 197, --轰炸：自由
    modelType = 12, -- 固定标识, 策划填.
    modelLevel = 5, -- 不用动.
    modelCount = 1, -- model数量.
    isEnabling = 1, -- 是否是机甲赋能.
    modelMechaId = 10014, -- 机甲id, 策划填.
    modelNode = 4, -- 加在哪一层 (1,2,3,4),默认为 4 .
    plistName = 'World_Mech28.plist', -- 行军装扮图集名字, 动效填.
    picName = 'World_Mech28.png', -- 行军装扮图集名字, 动效填.
    part = { -- 动效填
        [1] = {
            frames = { -- 参数{frames}, Array从1开始, 数组内的数字为序列帧编号.
                [1] = {1,2,3,4,5,6,7,8,1},
            },
            delay = 1/8, -- 每帧间隔时长.
        },
    },
    zOrder = 1,
    adjustPos = { -- 16个方向，机甲位置, 默认不改.
        [0]   = {x = 0, y = -40}, 
        [1]   = {x =10, y = -10}, 
        [2]   = {x = 35, y = 0}, 
        [3]   = {x = 20, y = 30}, 
        [4]   = {x = 40, y = 40}, 
        [5]   = {x = 20, y = 40}, 
        [6]   = {x = 0, y = 50}, 
        [7]   = {x = 0, y = 50}, 
        [8]   = {x = 0, y = 40}, 
        [9]   = {x = 0, y = 60}, 
        [10]  = {x = -10, y = 40}, 
        [11]  = {x = -20, y = 40}, 
        [12]  = {x = -40, y = 50}, 
        [13]  = {x = -20, y = 30}, 
        [14]  = {x = -40, y = -10}, 
        [15]  = {x = -20, y = -10}, 
    },
    sizeScale = 1.25, -- 整体缩放.
},
[198] = {
    modelName = "Mech27",
    modelIndex = 198, --直升机：夜鹰
    modelType = 12, -- 固定标识, 策划填.
    modelLevel = 5, -- 不用动.
    modelCount = 1, -- model数量.
    isEnabling = 1, -- 是否是机甲赋能.
    modelMechaId = 10016, -- 机甲id, 策划填.
    modelNode = 4, -- 加在哪一层 (1,2,3,4),默认为 4 .
    plistName = 'World_Mech27.plist', -- 行军装扮图集名字, 动效填.
    picName = 'World_Mech27.png', -- 行军装扮图集名字, 动效填.
    part = { -- 动效填
        [1] = {
            frames = { -- 参数{frames}, Array从1开始, 数组内的数字为序列帧编号.
                [1] = {1,2,3,4,5,6,7,8,1},
            },
            delay = 1/8, -- 每帧间隔时长.
        },
    },
    zOrder = 1,
    adjustPos = { -- 16个方向，机甲位置, 默认不改.
        [0]   = {x = 0, y = -40}, 
        [1]   = {x =10, y = -10}, 
        [2]   = {x = 35, y = 0}, 
        [3]   = {x = 20, y = 30}, 
        [4]   = {x = 40, y = 40}, 
        [5]   = {x = 20, y = 40}, 
        [6]   = {x = 0, y = 50}, 
        [7]   = {x = 0, y = 50}, 
        [8]   = {x = 0, y = 40}, 
        [9]   = {x = 0, y = 60}, 
        [10]  = {x = -10, y = 40}, 
        [11]  = {x = -20, y = 40}, 
        [12]  = {x = -40, y = 50}, 
        [13]  = {x = -20, y = 30}, 
        [14]  = {x = -40, y = -10}, 
        [15]  = {x = -20, y = -10}, 
    },
    sizeScale = 1.25, -- 整体缩放.
},
[199] = {
    modelName = "Mech25",
    modelIndex = 199, --突击：爆破
    modelType = 12, -- 固定标识, 策划填.
    modelLevel = 5, -- 不用动.
    modelCount = 1, -- model数量.
    isEnabling = 1, -- 是否是机甲赋能.
    modelMechaId = 10015, -- 机甲id, 策划填.
    modelNode = 4, -- 加在哪一层 (1,2,3,4),默认为 4 .
    plistName = 'World_Mech25.plist', -- 行军装扮图集名字, 动效填.
    picName = 'World_Mech25.png', -- 行军装扮图集名字, 动效填.
    part = { -- 动效填
        [1] = {
            frames = { -- 参数{frames}, Array从1开始, 数组内的数字为序列帧编号.
                [1] = {1,2,3,4,5,6,7,8,1},
            },
            delay = 1/8, -- 每帧间隔时长.
        },
    },
    zOrder = 1,
    adjustPos = { -- 16个方向，机甲位置, 默认不改.
        [0]   = {x = 0, y = -40}, 
        [1]   = {x =10, y = -10}, 
        [2]   = {x = 35, y = 0}, 
        [3]   = {x = 20, y = 30}, 
        [4]   = {x = 40, y = 40}, 
        [5]   = {x = 20, y = 40}, 
        [6]   = {x = 0, y = 50}, 
        [7]   = {x = 0, y = 50}, 
        [8]   = {x = 0, y = 40}, 
        [9]   = {x = 0, y = 60}, 
        [10]  = {x = -10, y = 40}, 
        [11]  = {x = -20, y = 40}, 
        [12]  = {x = -40, y = 50}, 
        [13]  = {x = -20, y = 30}, 
        [14]  = {x = -40, y = -10}, 
        [15]  = {x = -20, y = -10}, 
    },
    sizeScale = 1, -- 整体缩放.
},
[200] = {
    modelName = "Mech30",
    modelIndex = 200, --狙击：幽灵
    modelType = 12, -- 固定标识, 策划填.
    modelLevel = 5, -- 不用动.
    modelCount = 1, -- model数量.
    isEnabling = 1, -- 是否是机甲赋能.
    modelMechaId = 10012, -- 机甲id, 策划填.
    modelNode = 4, -- 加在哪一层 (1,2,3,4),默认为 4 .
    plistName = 'World_Mech30.plist', -- 行军装扮图集名字, 动效填.
    picName = 'World_Mech30.png', -- 行军装扮图集名字, 动效填.
    part = { -- 动效填
        [1] = {
            frames = { -- 参数{frames}, Array从1开始, 数组内的数字为序列帧编号.
                [1] = {1,2,3,4,5,6,7,8,1},
            },
            delay = 1/8, -- 每帧间隔时长.
        },
    },
    zOrder = 1,
    adjustPos = { -- 16个方向，机甲位置, 默认不改.
        [0]   = {x = 0, y = -40}, 
        [1]   = {x =10, y = -10}, 
        [2]   = {x = 35, y = 0}, 
        [3]   = {x = 20, y = 30}, 
        [4]   = {x = 40, y = 40}, 
        [5]   = {x = 20, y = 40}, 
        [6]   = {x = 0, y = 50}, 
        [7]   = {x = 0, y = 50}, 
        [8]   = {x = 0, y = 40}, 
        [9]   = {x = 0, y = 60}, 
        [10]  = {x = -10, y = 40}, 
        [11]  = {x = -20, y = 40}, 
        [12]  = {x = -40, y = 50}, 
        [13]  = {x = -20, y = 30}, 
        [14]  = {x = -40, y = -10}, 
        [15]  = {x = -20, y = -10}, 
    },
    sizeScale = 1, -- 整体缩放.
},
[201] = {
    modelName = "Mech26",
    modelIndex = 201, --攻城：救赎
    modelType = 12, -- 固定标识, 策划填.
    modelLevel = 5, -- 不用动.
    modelCount = 1, -- model数量.
    isEnabling = 1, -- 是否是机甲赋能.
    modelMechaId = 10018, -- 机甲id, 策划填.
    modelNode = 4, -- 加在哪一层 (1,2,3,4),默认为 4 .
    plistName = 'World_Mech26.plist', -- 行军装扮图集名字, 动效填.
    picName = 'World_Mech26.png', -- 行军装扮图集名字, 动效填.
    part = { -- 动效填
        [1] = {
            frames = { -- 参数{frames}, Array从1开始, 数组内的数字为序列帧编号.
                [1] = {1,2,3,4,5,6,7,8,1},
            },
            delay = 1/8, -- 每帧间隔时长.
        },
    },
    zOrder = 1,
    adjustPos = { -- 16个方向，机甲位置, 默认不改.
        [0]   = {x = 0, y = -40}, 
        [1]   = {x =10, y = -10}, 
        [2]   = {x = 35, y = 0}, 
        [3]   = {x = 20, y = 30}, 
        [4]   = {x = 40, y = 40}, 
        [5]   = {x = 20, y = 40}, 
        [6]   = {x = 0, y = 50}, 
        [7]   = {x = 0, y = 50}, 
        [8]   = {x = 0, y = 40}, 
        [9]   = {x = 0, y = 60}, 
        [10]  = {x = -10, y = 40}, 
        [11]  = {x = -20, y = 40}, 
        [12]  = {x = -40, y = 50}, 
        [13]  = {x = -20, y = 30}, 
        [14]  = {x = -40, y = -10}, 
        [15]  = {x = -20, y = -10}, 
    },
    sizeScale = 1, -- 整体缩放.
},
[202] = {
    modelName = "Mech29",
    modelIndex = 202, --采矿：阿尔法
    modelType = 12, -- 固定标识, 策划填.
    modelLevel = 5, -- 不用动.
    modelCount = 1, -- model数量.
    isEnabling = 1, -- 是否是机甲赋能.
    modelMechaId = 10017, -- 机甲id, 策划填.
    modelNode = 4, -- 加在哪一层 (1,2,3,4),默认为 4 .
    plistName = 'World_Mech29.plist', -- 行军装扮图集名字, 动效填.
    picName = 'World_Mech29.png', -- 行军装扮图集名字, 动效填.
    part = { -- 动效填
        [1] = {
            frames = { -- 参数{frames}, Array从1开始, 数组内的数字为序列帧编号.
                [1] = {1,2,3,4,5,6,7,8,1},
            },
            delay = 1/8, -- 每帧间隔时长.
        },
    },
    zOrder = 1,
    adjustPos = { -- 16个方向，机甲位置, 默认不改.
        [0]   = {x = 0, y = -40}, 
        [1]   = {x =10, y = -10}, 
        [2]   = {x = 35, y = 0}, 
        [3]   = {x = 20, y = 30}, 
        [4]   = {x = 40, y = 40}, 
        [5]   = {x = 20, y = 40}, 
        [6]   = {x = 0, y = 50}, 
        [7]   = {x = 0, y = 50}, 
        [8]   = {x = 0, y = 40}, 
        [9]   = {x = 0, y = 60}, 
        [10]  = {x = -10, y = 40}, 
        [11]  = {x = -20, y = 40}, 
        [12]  = {x = -40, y = 50}, 
        [13]  = {x = -20, y = 30}, 
        [14]  = {x = -40, y = -10}, 
        [15]  = {x = -20, y = -10}, 
    },
    sizeScale = 1, -- 整体缩放.
},
[203] = {
	modelName = "GanEnJie", --拼接图片名字用的，比如摩托车，March_"Fish"_Part1_0_1.png
	modelIndex = 203,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 6, --model数量 动效填
	partCount = 7, --part数量 动效填
	dressModelType = 37, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_GanEnJie_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_GanEnJie.ccbi", --行军装扮名字，动效填
	plistName = "World_GanEnJie.plist",--行军装扮图集名字，动效填
	picName = "World_GanEnJie.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {3,3,3,3,2,2,2,2,2,2,2,2,2,3,3,3},
			
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {4,4,4,4,3,3,3,3,3,3,3,3,3,4,4,4},
			
		},
		[4] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {2,2,2,2,4,4,4,4,4,4,4,4,4,2,2,2}
			
		},
		[5] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
			
		},
		[6] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
			
		},
		[7] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7},
			
		},
	},
},
[204] = {
	modelName = "Christmas5", --拼接图片名字用的，比如摩托车，March_"Fish"_Part1_0_1.png
	modelIndex = 204,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 6, --model数量 动效填
	partCount = 8, --part数量 动效填
	dressModelType = 38, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_Christmas5_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_Christmas5.ccbi", --行军装扮名字，动效填
	plistName = "World_Christmas5.plist",--行军装扮图集名字，动效填
	picName = "World_Christmas5.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},

			},
			delay = 1/10,
			disorder = true,
			partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},

			},
			delay = 1/10,
			disorder = true,
			partZOrder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
		},
		[4] = {
			frames = {
				[1] = {1,1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {6,6,6,6,4,4,4,4,4,4,4,4,4,6,6,6},
		},
		[5] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},

			},
			delay = 1/30,
			disorder = true,
			partZOrder = {7,7,7,7,5,5,5,5,5,5,5,5,5,7,7,7}
		},
		[6] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},

			},
			delay = 1/30,
			disorder = true,
			partZOrder = {8,8,8,8,6,6,6,6,6,6,6,6,6,8,8,8},
		},
		[7] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,1},
				[2] = {2,3,4,5,6,7,8,1,2},
				[3] = {3,4,5,6,7,8,1,2,3},
				[4] = {4,5,6,7,8,1,2,3,4},
				[5] = {5,6,7,8,1,2,3,4,5},
				[6] = {6,7,8,1,2,3,4,5,6},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {4,4,4,4,7,7,7,7,7,7,7,7,7,4,4,4},
		},
		[8] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,1},
				[2] = {2,3,4,5,6,7,8,1,2},
				[3] = {3,4,5,6,7,8,1,2,3},
				[4] = {4,5,6,7,8,1,2,3,4},
				[5] = {5,6,7,8,1,2,3,4,5},
				[6] = {6,7,8,1,2,3,4,5,6},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {5,5,5,5,8,8,8,8,8,8,8,8,8,5,5,5},
		},
	},
},
[205] = {
	modelName = "DragonLantern", --拼接图片名字用的，比如摩托车，March_"Fish"_Part1_0_1.png
	modelIndex = 205,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 9, --model数量 动效填
	partCount = 5, --part数量 动效填
	dressModelType = 39, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_DragonLantern_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_DragonLantern.ccbi", --行军装扮名字，动效填
	plistName = "World_DragonLantern.plist",--行军装扮图集名字，动效填
	picName = "World_DragonLantern.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,12,13,14,15,1,2,3,4,5,6,7,8,9},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {3,3,3,3,2,2,2,2,2,2,2,2,2,3,3,3},
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},
				[7] = {7,8,9,10,1,2,3,4,5,6,7},
				[8] = {8,9,10,1,2,3,4,5,6,7,8},
				[9] = {9,10,1,2,3,4,5,6,7,8,9},

			},
			delay = 1/10,
			disorder = true,
			partZOrder = {4,4,4,4,3,3,3,3,3,3,3,3,3,4,4,4},
		},
		[4] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {2,2,2,2,4,4,4,5,5,5,4,4,4,2,2,2},
		},
		[5] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},
				[7] = {7,8,9,10,1,2,3,4,5,6,7},
				[8] = {8,9,10,1,2,3,4,5,6,7,8},
				[9] = {9,10,1,2,3,4,5,6,7,8,9},

			},
			delay = 1/10,
			disorder = true,
			partZOrder = {5,5,5,5,5,5,5,4,4,4,5,5,5,5,5,5}
		},
	},
},
[206] = {
	modelName = "OrderKirov", --拼接图片名字用的，比如摩托车，March_"Fish"_Part1_0_1.png
	modelIndex = 206,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 15, --model数量 动效填
	partCount = 4, --part数量 动效填
	dressModelType = 40, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_OrderKirov_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_OrderKirov.ccbi", --行军装扮名字，动效填
	plistName = "World_OrderKirov.plist",--行军装扮图集名字，动效填
	picName = "World_OrderKirov.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9},
				[10] = {10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9,10},
				[11] = {11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9,10,11},
				[12] = {12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9,10,11,12},
				[13] = {13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9,10,11,12,13},
				[14] = {14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9,10,11,12,13,14},
				[15] = {15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},

			},
			delay = 1/30,
			disorder = true,
			partZOrder = {3,3,3,3,2,2,2,2,2,2,2,2,2,3,3,3},
		},
		[3] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {2,2,2,2,3,3,3,3,3,3,3,3,3,2,2,2},
		},
		[4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9},
				[10] = {10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9,10},
				[11] = {11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9,10,11},
				[12] = {12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9,10,11,12},
				[13] = {13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9,10,11,12,13},
				[14] = {14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9,10,11,12,13,14},
				[15] = {15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
		},
	},
},
  [207] = {
	modelName = "WorkersUnion", --拼接图片名字用的，比如摩托车，March_"Fish"_Part1_0_1.png
	modelIndex = 207,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 6, --model数量 动效填
	partCount = 8, --part数量 动效填
	dressModelType = 41, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_WorkersUnion_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_WorkersUnion.ccbi", --行军装扮名字，动效填
	plistName = "World_WorkersUnion.plist",--行军装扮图集名字，动效填
	picName = "World_WorkersUnion.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {3,3,3,3,2,2,2,2,2,2,2,2,2,3,3,3},
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {4,4,4,4,3,3,3,3,3,3,3,3,3,4,4,4},
		},
		[4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {5,5,5,5,4,4,4,4,4,4,4,4,4,5,5,5},
		},
		[5] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {6,6,6,6,5,5,5,5,5,5,5,5,5,6,6,6},
		},
		[6] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {2,2,2,2,6,6,6,6,6,6,6,6,6,2,2,2},
		},
		[7] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7},
		},
		[8] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8},
		},
	},
},
  [208] = {
	modelName = "Cancer", --拼接图片名字用的，比如摩托车，March_"Fish"_Part1_0_1.png
	modelIndex = 208,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 6, --model数量 动效填
	partCount = 7, --part数量 动效填
	dressModelType = 42, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_Cancer_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_Cancer.ccbi", --行军装扮名字，动效填
	plistName = "World_Cancer.plist",--行军装扮图集名字，动效填
	picName = "World_Cancer.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6},

			},
			delay = 1/30,
			disorder = true,
			partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {4,4,4,4,3,3,3,3,3,3,3,3,3,4,4,4},
		},
		[4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {5,5,5,5,4,4,4,4,4,4,4,4,4,5,5,5},
		},
		[5] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {6,6,6,6,5,5,5,5,5,5,5,5,5,6,6,6},
		},
		[6] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {7,7,7,7,6,6,6,6,6,6,6,6,6,7,7,7},
		},
		[7] = {
			frames = {
				[1] = {1,2,1},

			},
			delay = 1/30,
			disorder = true,
			partZOrder = {3,3,3,3,7,7,7,7,7,7,7,7,7,3,3,3},
		},
	},
},
[209] = {
    modelName = "Mech31",
    modelIndex = 209,
    modelType = 12, -- 固定标识, 策划填.
    modelLevel = 5, -- 不用动.
    modelCount = 1, -- model数量.
    isSkin = 1, -- 是否是机甲皮肤.
    modelMechaId = 10009, -- 机甲皮肤id, 策划填.
    modelNode = 4, -- 加在哪一层 (1,2,3,4),默认为 4 .
    plistName = 'World_Mech31.plist', -- 行军装扮图集名字, 动效填.
    picName = 'World_Mech31.png', -- 行军装扮图集名字, 动效填.
    part = { -- 动效填
        [1] = {
            frames = { -- 参数{frames}, Array从1开始, 数组内的数字为序列帧编号.
                [1] = {1,2,3,4,5,6,7,8,1},
            },
            delay = 1/8, -- 每帧间隔时长.
        },
    },
    zOrder = 1,
    adjustPos = { -- 16个方向，机甲位置, 默认不改.
        [0]   = {x = 0, y = -40}, 
        [1]   = {x =10, y = -10}, 
        [2]   = {x = 35, y = 0}, 
        [3]   = {x = 20, y = 30}, 
        [4]   = {x = 40, y = 40}, 
        [5]   = {x = 20, y = 40}, 
        [6]   = {x = 0, y = 50}, 
        [7]   = {x = 0, y = 50}, 
        [8]   = {x = 0, y = 40}, 
        [9]   = {x = 0, y = 60}, 
        [10]  = {x = -10, y = 40}, 
        [11]  = {x = -20, y = 40}, 
        [12]  = {x = -40, y = 50}, 
        [13]  = {x = -20, y = 30}, 
        [14]  = {x = -40, y = -10}, 
        [15]  = {x = -20, y = -10}, 
    },
    sizeScale = 1.13, -- 整体缩放.
},
[210] = {
    modelName = "Mech32",
    modelIndex = 210,
    modelType = 12, -- 固定标识, 策划填.
    modelLevel = 5, -- 不用动.
    modelCount = 1, -- model数量.
    isSkin = 1, -- 是否是机甲皮肤.
    modelMechaId = 10010, -- 机甲id, 策划填.
    modelNode = 4, -- 加在哪一层 (1,2,3,4),默认为 4 .
    plistName = 'World_Mech32.plist', -- 行军装扮图集名字, 动效填.
    picName = 'World_Mech32.png', -- 行军装扮图集名字, 动效填.
    part = { -- 动效填
        [1] = {
            frames = { -- 参数{frames}, Array从1开始, 数组内的数字为序列帧编号.
                [1] = {1,2,3,4,5,6,7,8,1},
            },
            delay = 1/8, -- 每帧间隔时长.
        },
    },
    zOrder = 1,
    adjustPos = { -- 16个方向，机甲位置, 默认不改.
        [0]   = {x = 0, y = -40}, 
        [1]   = {x =10, y = -10}, 
        [2]   = {x = 35, y = 0}, 
        [3]   = {x = 20, y = 30}, 
        [4]   = {x = 40, y = 40}, 
        [5]   = {x = 20, y = 40}, 
        [6]   = {x = 0, y = 50}, 
        [7]   = {x = 0, y = 50}, 
        [8]   = {x = 0, y = 40}, 
        [9]   = {x = 0, y = 60}, 
        [10]  = {x = -10, y = 40}, 
        [11]  = {x = -20, y = 40}, 
        [12]  = {x = -40, y = 50}, 
        [13]  = {x = -20, y = 30}, 
        [14]  = {x = -40, y = -10}, 
        [15]  = {x = -20, y = -10}, 
    },
    sizeScale = 1.54, -- 整体缩放.
},
[213] = {
    modelName = "Mech33",
    modelIndex = 213,
    modelType = 12, -- 固定标识, 策划填.
    modelLevel = 5, -- 不用动.
    modelCount = 1, -- model数量.
    isSkin = 1, -- 是否是机甲皮肤.
    modelMechaId = 20001, -- 机甲id, 策划填.
    modelNode = 4, -- 加在哪一层 (1,2,3,4),默认为 4 .
    plistName = 'World_Mech33.plist', -- 行军装扮图集名字, 动效填.
    picName = 'World_Mech33.png', -- 行军装扮图集名字, 动效填.
    part = { -- 动效填
        [1] = {
            frames = { -- 参数{frames}, Array从1开始, 数组内的数字为序列帧编号.
                [1] = {1,2,3,4,5,6,7,8,1},
            },
            delay = 1/8, -- 每帧间隔时长.
        },
    },
    zOrder = 1,
    adjustPos = { -- 16个方向，机甲位置, 默认不改.
        [0]   = {x = 0, y = -40}, 
        [1]   = {x =10, y = -10}, 
        [2]   = {x = 35, y = 0}, 
        [3]   = {x = 20, y = 30}, 
        [4]   = {x = 40, y = 40}, 
        [5]   = {x = 20, y = 40}, 
        [6]   = {x = 0, y = 50}, 
        [7]   = {x = 0, y = 50}, 
        [8]   = {x = 0, y = 40}, 
        [9]   = {x = 0, y = 60}, 
        [10]  = {x = -10, y = 40}, 
        [11]  = {x = -20, y = 40}, 
        [12]  = {x = -40, y = 50}, 
        [13]  = {x = -20, y = 30}, 
        [14]  = {x = -40, y = -10}, 
        [15]  = {x = -20, y = -10}, 
    },
    sizeScale = 1.25, -- 整体缩放.
},
[215] = {
    modelName = "Mech35",
    modelIndex = 215,
    modelType = 12, -- 固定标识, 策划填.
    modelLevel = 5, -- 不用动.
    modelCount = 1, -- model数量.
    isSkin = 1, -- 是否是机甲皮肤.
    modelMechaId = 20002, -- 机甲id, 策划填.
    modelNode = 4, -- 加在哪一层 (1,2,3,4),默认为 4 .
    plistName = 'World_Mech35.plist', -- 行军装扮图集名字, 动效填.
    picName = 'World_Mech35.png', -- 行军装扮图集名字, 动效填.
    part = { -- 动效填
        [1] = {
            frames = { -- 参数{frames}, Array从1开始, 数组内的数字为序列帧编号.
                [1] = {1,2,3,4,5,6,7,8,1},
            },
            delay = 1/8, -- 每帧间隔时长.
        },
    },
    zOrder = 1,
    adjustPos = { -- 16个方向，机甲位置, 默认不改.
        [0]   = {x = 0, y = -40}, 
        [1]   = {x =10, y = -10}, 
        [2]   = {x = 35, y = 0}, 
        [3]   = {x = 20, y = 30}, 
        [4]   = {x = 40, y = 40}, 
        [5]   = {x = 20, y = 40}, 
        [6]   = {x = 0, y = 50}, 
        [7]   = {x = 0, y = 50}, 
        [8]   = {x = 0, y = 40}, 
        [9]   = {x = 0, y = 60}, 
        [10]  = {x = -10, y = 40}, 
        [11]  = {x = -20, y = 40}, 
        [12]  = {x = -40, y = 50}, 
        [13]  = {x = -20, y = 30}, 
        [14]  = {x = -40, y = -10}, 
        [15]  = {x = -20, y = -10}, 
    },
    sizeScale = 1.42, -- 整体缩放.
},
[216] = {
    modelName = "Mech34",
    modelIndex = 216,
    modelType = 12, -- 固定标识, 策划填.
    modelLevel = 5, -- 不用动.
    modelCount = 1, -- model数量.
    isSkin = 1, -- 是否是机甲皮肤.
    modelMechaId = 20003, -- 机甲id, 策划填.
    modelNode = 4, -- 加在哪一层 (1,2,3,4),默认为 4 .
    plistName = 'World_Mech34.plist', -- 行军装扮图集名字, 动效填.
    picName = 'World_Mech34.png', -- 行军装扮图集名字, 动效填.
    part = { -- 动效填
        [1] = {
            frames = { -- 参数{frames}, Array从1开始, 数组内的数字为序列帧编号.
                [1] = {1,2,3,4,5,6,7,8,1},
            },
            delay = 1/8, -- 每帧间隔时长.
        },
    },
    zOrder = 1,
    adjustPos = { -- 16个方向，机甲位置, 默认不改.
        [0]   = {x = 0, y = -40}, 
        [1]   = {x =10, y = -10}, 
        [2]   = {x = 35, y = 0}, 
        [3]   = {x = 20, y = 30}, 
        [4]   = {x = 40, y = 40}, 
        [5]   = {x = 20, y = 40}, 
        [6]   = {x = 0, y = 50}, 
        [7]   = {x = 0, y = 50}, 
        [8]   = {x = 0, y = 40}, 
        [9]   = {x = 0, y = 60}, 
        [10]  = {x = -10, y = 40}, 
        [11]  = {x = -20, y = 40}, 
        [12]  = {x = -40, y = 50}, 
        [13]  = {x = -20, y = 30}, 
        [14]  = {x = -40, y = -10}, 
        [15]  = {x = -20, y = -10}, 
    },
    sizeScale = 1.2, -- 整体缩放.
},
[218] = {
    modelName = "Mech37",
    modelIndex = 218,
    modelType = 12, -- 固定标识, 策划填.
    modelLevel = 5, -- 不用动.
    modelCount = 1, -- model数量.
    isSkin = 1, -- 是否是机甲皮肤.
    modelMechaId = 20004, -- 机甲id, 策划填.
    modelNode = 4, -- 加在哪一层 (1,2,3,4),默认为 4 .
    plistName = 'World_Mech37.plist', -- 行军装扮图集名字, 动效填.
    picName = 'World_Mech37.png', -- 行军装扮图集名字, 动效填.
    part = { -- 动效填
        [1] = {
            frames = { -- 参数{frames}, Array从1开始, 数组内的数字为序列帧编号.
                [1] = {1,2,3,4,5,6,7,8,1},
            },
            delay = 1/8, -- 每帧间隔时长.
        },
    },
    zOrder = 1,
    adjustPos = { -- 16个方向，机甲位置, 默认不改.
        [0]   = {x = 0, y = -40}, 
        [1]   = {x =10, y = -10}, 
        [2]   = {x = 35, y = 0}, 
        [3]   = {x = 20, y = 30}, 
        [4]   = {x = 40, y = 40}, 
        [5]   = {x = 20, y = 40}, 
        [6]   = {x = 0, y = 50}, 
        [7]   = {x = 0, y = 50}, 
        [8]   = {x = 0, y = 40}, 
        [9]   = {x = 0, y = 60}, 
        [10]  = {x = -10, y = 40}, 
        [11]  = {x = -20, y = 40}, 
        [12]  = {x = -40, y = 50}, 
        [13]  = {x = -20, y = 30}, 
        [14]  = {x = -40, y = -10}, 
        [15]  = {x = -20, y = -10}, 
    },
    sizeScale = 1.6, -- 整体缩放.
},
[219] = {
    modelName = "Mech36",
    modelIndex = 219,
    modelType = 12, -- 固定标识, 策划填.
    modelLevel = 5, -- 不用动.
    modelCount = 1, -- model数量.
    isSkin = 1, -- 是否是机甲皮肤.
    modelMechaId = 20005, -- 机甲id, 策划填.
    modelNode = 4, -- 加在哪一层 (1,2,3,4),默认为 4 .
    plistName = 'World_Mech36.plist', -- 行军装扮图集名字, 动效填.
    picName = 'World_Mech36.png', -- 行军装扮图集名字, 动效填.
    part = { -- 动效填
        [1] = {
            frames = { -- 参数{frames}, Array从1开始, 数组内的数字为序列帧编号.
                [1] = {1,2,3,4,5,6,7,8,1},
            },
            delay = 1/8, -- 每帧间隔时长.
        },
    },
    zOrder = 1,
    adjustPos = { -- 16个方向，机甲位置, 默认不改.
        [0]   = {x = 0, y = -40}, 
        [1]   = {x =10, y = -10}, 
        [2]   = {x = 35, y = 0}, 
        [3]   = {x = 20, y = 30}, 
        [4]   = {x = 40, y = 40}, 
        [5]   = {x = 20, y = 40}, 
        [6]   = {x = 0, y = 50}, 
        [7]   = {x = 0, y = 50}, 
        [8]   = {x = 0, y = 40}, 
        [9]   = {x = 0, y = 60}, 
        [10]  = {x = -10, y = 40}, 
        [11]  = {x = -20, y = 40}, 
        [12]  = {x = -40, y = 50}, 
        [13]  = {x = -20, y = 30}, 
        [14]  = {x = -40, y = -10}, 
        [15]  = {x = -20, y = -10}, 
    },
    sizeScale = 1.25, -- 整体缩放.
},
[221] = {
    modelName = "Mech38",
    modelIndex = 221,
    modelType = 12, -- 固定标识, 策划填.
    modelLevel = 5, -- 不用动.
    modelCount = 1, -- model数量.
    isSkin = 1, -- 是否是机甲皮肤.
    modelMechaId = 20006, -- 机甲id, 策划填.
    modelNode = 4, -- 加在哪一层 (1,2,3,4),默认为 4 .
    plistName = 'World_Mech38.plist', -- 行军装扮图集名字, 动效填.
    picName = 'World_Mech38.png', -- 行军装扮图集名字, 动效填.
    part = { -- 动效填
        [1] = {
            frames = { -- 参数{frames}, Array从1开始, 数组内的数字为序列帧编号.
                [1] = {1,2,3,4,5,6,7,8,1},
            },
            delay = 1/8, -- 每帧间隔时长.
        },
    },
    zOrder = 1,
    adjustPos = { -- 16个方向，机甲位置, 默认不改.
        [0]   = {x = 0, y = -40}, 
        [1]   = {x =10, y = -10}, 
        [2]   = {x = 35, y = 0}, 
        [3]   = {x = 20, y = 30}, 
        [4]   = {x = 40, y = 40}, 
        [5]   = {x = 20, y = 40}, 
        [6]   = {x = 0, y = 50}, 
        [7]   = {x = 0, y = 50}, 
        [8]   = {x = 0, y = 40}, 
        [9]   = {x = 0, y = 60}, 
        [10]  = {x = -10, y = 40}, 
        [11]  = {x = -20, y = 40}, 
        [12]  = {x = -40, y = 50}, 
        [13]  = {x = -20, y = 30}, 
        [14]  = {x = -40, y = -10}, 
        [15]  = {x = -20, y = -10}, 
    },
    sizeScale = 1.3, -- 整体缩放.
},
[212] = {
	modelName = "Qixi2", --拼接图片名字用的，比如摩托车，March_"Qixi2"_Part1_0_1.png
	modelIndex = 212,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 6, --model数量 动效填
	partCount = 8, --part数量 动效填
	dressModelType = 43, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_Qixi2_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_Qixi2.ccbi", --行军装扮名字，动效填
	plistName = "World_Qixi2.plist",--行军装扮图集名字，动效填
	picName = "World_Qixi2.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},

			},
			delay = 1/10,
			disorder = true,
			partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
		},
		[3] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
		},
		[4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {8,8,8,8,8,4,4,4,4,4,4,4,8,8,8,8},
		},
		[5] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {4,4,4,4,4,5,5,5,5,5,5,5,4,4,4,4},
		},
		[6] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},

			},
			delay = 1/10,
			disorder = true,
			partZOrder = {5,5,5,5,5,6,6,6,6,6,6,6,5,5,5,5},
		},
		[7] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6},

			},
			delay = 1/30,
			disorder = true,
			partZOrder = {6,6,6,6,6,7,7,7,7,7,7,7,6,6,6,6},
		},
		[8] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {7,7,7,7,7,8,8,8,8,8,8,8,7,7,7,7},
		},
	},
},
[999] = {
	modelName = "LargeFleet", --拼接图片名字用的，比如摩托车，March_"LargeFleet"_Part1_0_1.png
	modelIndex = 999,  --顺序往后加，策划填
	modelType = 44,
	modelLevel = 1,
	modelCount = 8, --model数量 动效填
	partCount = 10, --part数量 动效填
	dressModelType = 17, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_LargeFleet_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_LargeFleet.ccbi", --行军装扮名字，动效填
	plistName = "World_LargeFleet.color.plist",--行军装扮图集名字，动效填
	picName = "World_LargeFleet.color.png",--行军装扮图集名字，动效填
	picMaskName = "World_LargeFleet.mask.png",
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},
				[7] = {7,8,9,10,1,2,3,4,5,6,7},
				[8] = {8,9,10,1,2,3,4,5,6,7,8},
				[9] = {9,10,1,2,3,4,5,6,7,8,9},
				[10] ={10,1,2,3,4,5,6,7,8,9,10},

			},
			delay = 1/10,
			disorder = true,
			partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},
				[7] = {7,8,9,10,1,2,3,4,5,6,7},
				[8] = {8,9,10,1,2,3,4,5,6,7,8},
				[9] = {9,10,1,2,3,4,5,6,7,8,9},
				[10] ={10,1,2,3,4,5,6,7,8,9,10},

			},
			delay = 1/10,
			disorder = true,
			partZOrder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
		},
		[4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,12,13,14,15,1,2,3,4,5,6,7,8,9},
				[10] = {10,11,12,13,14,15,1,2,3,4,5,6,7,8,9,10},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {8,8,8,8,8,4,4,4,4,4,4,4,8,8,8,8},
		},
		[5] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,12,13,14,15,1,2,3,4,5,6,7,8,9},
				[10] = {10,11,12,13,14,15,1,2,3,4,5,6,7,8,9,10},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {9,9,9,9,9,5,5,5,5,5,5,5,9,9,9,9},
		},
		[6] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,12,13,14,15,1,2,3,4,5,6,7,8,9},
				[10] = {10,11,12,13,14,15,1,2,3,4,5,6,7,8,9,10},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {10,10,10,10,10,6,6,6,6,6,6,6,10,10,10,10},
		},
		[7] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,12,13,14,15,1,2,3,4,5,6,7,8,9},
				[10] = {10,11,12,13,14,15,1,2,3,4,5,6,7,8,9,10},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {4,4,4,4,4,7,7,7,7,7,7,7,4,4,4,4},
		},		
		[8] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},
				[7] = {7,8,9,10,1,2,3,4,5,6,7},
				[8] = {8,9,10,1,2,3,4,5,6,7,8},
				[9] = {9,10,1,2,3,4,5,6,7,8,9},
				[10] ={10,1,2,3,4,5,6,7,8,9,10},

			},
			delay = 1/10,
			disorder = true,
			partZOrder = {5,5,5,5,5,8,8,8,8,8,8,8,5,5,5,5},
		},
		[9] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {6,6,6,6,6,9,9,9,9,9,9,9,6,6,6,6},
		},	
		[10] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},
				[7] = {7,8,9,10,1,2,3,4,5,6,7},
				[8] = {8,9,10,1,2,3,4,5,6,7,8},
				[9] = {9,10,1,2,3,4,5,6,7,8,9},
				[10] ={10,1,2,3,4,5,6,7,8,9,10},

			},
			delay = 1/10,
			disorder = true,
			partZOrder = {7,7,7,7,7,10,10,10,10,10,10,10,7,7,7,7},
		},		
	},
},
[998] = {
	modelName = "SmallFleet", --拼接图片名字用的，比如摩托车，March_"SmallFleet"_Part1_0_1.png
	modelIndex = 998,  --顺序往后加，策划填
	modelType = 45,
	modelLevel = 1,
	modelCount = 1, --model数量 动效填
	partCount = 5, --part数量 动效填
	dressModelType = 17, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_SmallFleet_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_SmallFleet.ccbi", --行军装扮名字，动效填
	plistName = "World_SmallFleet.color.plist",--行军装扮图集名字，动效填
	picName = "World_SmallFleet.color.png",--行军装扮图集名字，动效填
	picMaskName = "World_SmallFleet.mask.png",
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},

			},
			delay = 1/10,
			disorder = true,
			partZOrder = {5,5,5,5,5,2,2,2,2,2,2,2,5,5,5,5},
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},

			},
			delay = 1/10,
			disorder = true,
			partZOrder = {2,2,2,2,2,3,3,3,3,3,3,3,2,2,2,2},
		},
		[4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {3,3,3,3,3,4,4,4,4,4,4,4,3,3,3,3},
		},
		[5] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {4,4,4,4,4,5,5,5,5,5,5,5,4,4,4,4},
		},
	},
},
[214] = {
	modelName = "Team", --拼接图片名字用的，比如摩托车，March_"Team"_Part1_0_1.png
	modelIndex = 214,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 3, --model数量 动效填
	partCount = 8, --part数量 动效填
	dressModelType = 46, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_Team_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_Team.ccbi", --行军装扮名字，动效填
	plistName = "World_Team.plist",--行军装扮图集名字，动效填
	picName = "World_Team.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {5,5,5,5,5,3,3,3,3,3,3,3,5,5,5,5},
		},
		[4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {3,3,3,3,3,4,4,4,4,4,4,4,3,3,3,3},
		},		
		[5] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {4,4,4,4,4,5,5,5,5,5,5,5,4,4,4,4},
		},
		[6] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
		},
		[7] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7},
		},
		[8] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8},
		},
	},
},
[217] = {
	modelName = "Wildcruise", --拼接图片名字用的，比如摩托车，March_"Wildcruise"_Part1_0_1.png
	modelIndex = 217,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 3, --model数量 动效填
	partCount = 7, --part数量 动效填
	dressModelType = 47, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_Wildcruise_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_Wildcruise.ccbi", --行军装扮名字，动效填
	plistName = "World_Wildcruise.plist",--行军装扮图集名字，动效填
	picName = "World_Wildcruise.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1},

			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},

			},
			delay = 1/15,
			disorder = true,
			partZOrder = {5,5,5,5,2,2,2,2,2,2,2,2,2,5,5,5},
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {6,6,6,6,3,3,3,3,3,3,3,3,3,6,6,6},
		},		
		[4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {2,2,2,2,4,4,4,4,4,4,4,4,4,2,2,2},
		},
		[5] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {3,3,3,3,5,5,5,5,5,5,5,5,5,3,3,3},
		},
		[6] = {
			frames = {
				[1] = {1,2,1},
				[2] = {2,1,2},
			
			},
			delay = 1/30,
			disorder = true,
			partZOrder = {4,4,4,4,6,6,6,6,6,6,6,6,6,4,4,4},
		},
		[7] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7},
		},
	},
},
[220] = {
	modelName = "Viper", --拼接图片名字用的，比如摩托车，March_"Viper"_Part1_0_1.png
	modelIndex = 220,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 4, --model数量 动效填
	partCount = 10, --part数量 动效填
	dressModelType = 48, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_Viper_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_Viper.ccbi", --行军装扮名字，动效填
	plistName = "World_Viper.plist",--行军装扮图集名字，动效填
	picName = "World_Viper.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1},
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4},
			},
			delay = 1/30,
			disorder = true,
			partZOrder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
		},		
		[4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4},
			},
			delay = 1/30,
			disorder = true,
			partZOrder = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
		},
		[5] = {
			frames = {
				[1] = {1},
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
		},
		[6] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4},
			},
			delay = 1/30,
			disorder = true,
			partZOrder = {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
		},
		[7] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4},
			},
			delay = 1/30,
			disorder = true,
			partZOrder = {7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7},
		},
		[8] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8},
		},
		[9] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9},
		},
		[10] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10},
		},
	},
},
  [227] = {
	modelName = "Troops", --拼接图片名字用的，比如摩托车，March_"Troops"_Part1_0_1.png
	modelIndex = 227,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 13, --model数量 动效填
	partCount = 9, --part数量 动效填
	dressModelType = 49, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_Troops_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_Troops.ccbi", --行军装扮名字，动效填
	plistName = "World_Troops.plist",--行军装扮图集名字，动效填
	picName = "World_Troops.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1},
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,12,13,14,15,1,2,3,4,5,6,7,8,9},
				[10] = {10,11,12,13,14,15,1,2,3,4,5,6,7,8,9,10},
				[11] = {11,12,13,14,15,1,2,3,4,5,6,7,8,9,10,11},
				[12] = {12,13,14,15,1,2,3,4,5,6,7,8,9,10,11,12},
				[13] = {13,14,15,1,2,3,4,5,6,7,8,9,10,11,12,13},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {3,3,3,3,2,2,2,2,2,2,2,2,2,3,3,3},
		},	
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,12,13,14,15,1,2,3,4,5,6,7,8,9},
				[10] = {10,11,12,13,14,15,1,2,3,4,5,6,7,8,9,10},
				[11] = {11,12,13,14,15,1,2,3,4,5,6,7,8,9,10,11},
				[12] = {12,13,14,15,1,2,3,4,5,6,7,8,9,10,11,12},
				[13] = {13,14,15,1,2,3,4,5,6,7,8,9,10,11,12,13},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {4,4,4,4,3,3,3,3,3,3,3,3,3,4,4,4},
		},	
		[4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,12,13,14,15,1,2,3,4,5,6,7,8,9},
				[10] = {10,11,12,13,14,15,1,2,3,4,5,6,7,8,9,10},
				[11] = {11,12,13,14,15,1,2,3,4,5,6,7,8,9,10,11},
				[12] = {12,13,14,15,1,2,3,4,5,6,7,8,9,10,11,12},
				[13] = {13,14,15,1,2,3,4,5,6,7,8,9,10,11,12,13},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {5,5,5,5,4,4,4,4,4,4,4,4,4,5,5,5},
		},	
		[5] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,12,13,14,15,1,2,3,4,5,6,7,8,9},
				[10] = {10,11,12,13,14,15,1,2,3,4,5,6,7,8,9,10},
				[11] = {11,12,13,14,15,1,2,3,4,5,6,7,8,9,10,11},
				[12] = {12,13,14,15,1,2,3,4,5,6,7,8,9,10,11,12},
				[13] = {13,14,15,1,2,3,4,5,6,7,8,9,10,11,12,13},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {6,6,6,6,5,5,5,5,5,5,5,5,5,6,6,6},
		},			
		[6] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9},
				[10] = {10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9,10},
				[11] = {11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9,10,11},
				[12] = {12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9,10,11,12},
				[13] = {13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9,10,11,12,13},
			},
			delay = 1/30,
			disorder = true,
			partZOrder = {2,2,2,2,6,6,6,6,6,6,6,6,6,2,2,2},
		},
		[7] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},
				[7] = {7,8,9,10,1,2,3,4,5,6,7},
				[8] = {8,9,10,1,2,3,4,5,6,7,8},
				[9] = {9,10,1,2,3,4,5,6,7,8,9},
				[10] = {10,1,2,3,4,5,6,7,8,9,10},
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7},
		},	
		[8] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},
				[7] = {7,8,9,10,1,2,3,4,5,6,7},
				[8] = {8,9,10,1,2,3,4,5,6,7,8},
				[9] = {9,10,1,2,3,4,5,6,7,8,9},
				[10] = {10,1,2,3,4,5,6,7,8,9,10},
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8},
		},	
		[9] = {
			frames = {
				[1] = {1,2,3,4,5,6,1},
				[2] = {2,3,4,5,6,1,2},
				[3] = {3,4,5,6,1,2,3},
				[4] = {4,5,6,1,2,3,4},
				[5] = {5,6,1,2,3,4,5},
				[6] = {6,1,2,3,4,5,6},
			},
			delay = 1/6,
			disorder = true,
			partZOrder = {9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9},
		},			
	},
},
[222] = {
	modelName = "Spring", --拼接图片名字用的，比如摩托车，March_"Spring"_Part1_0_1.png
	modelIndex = 222,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 6, --model数量 动效填
	partCount = 7, --part数量 动效填
	dressModelType = 50, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_Spring_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_Spring.ccbi", --行军装扮名字，动效填
	plistName = "World_Spring.plist",--行军装扮图集名字，动效填
	picName = "World_Spring.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1},
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {4,4,4,4,3,3,3,3,3,3,3,3,3,4,4,4},
		},		
		[4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {5,5,5,5,4,4,4,4,4,4,4,4,4,5,5,5},
		},
		[5] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {6,6,6,6,5,5,5,5,5,5,5,5,5,6,6,6},
		},				
		[6] = {
			frames = {
				[1] = {1},
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {3,3,3,3,6,6,6,6,6,6,6,6,6,3,3,3},
		},
		[7] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7},
		},		
	},
},
[223] = {
	modelName = "Command", --拼接图片名字用的，比如摩托车，March_"Command"_Part1_0_1.png
	modelIndex = 223,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 6, --model数量 动效填
	partCount = 4, --part数量 动效填
	dressModelType = 51, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_Command_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_Command.ccbi", --行军装扮名字，动效填
	plistName = "World_Command.plist",--行军装扮图集名字，动效填
	picName = "World_Command.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1},
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1},
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {4,4,4,4,3,3,3,3,3,3,3,3,3,4,4,4},
		},		
		[4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6},
			},
			delay = 1/30,
			disorder = true,
			partZOrder = {3,3,3,3,4,4,4,4,4,4,4,4,4,3,3,3},
		},	
	},
},
[224] = {
	modelName = "Labour", --拼接图片名字用的，比如摩托车，March_"Labour"_Part1_0_1.png
	modelIndex = 224,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 7, --model数量 动效填
	partCount = 6, --part数量 动效填
	dressModelType = 52, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_Labour_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_Labour.ccbi", --行军装扮名字，动效填
	plistName = "World_Labour.plist",--行军装扮图集名字，动效填
	picName = "World_Labour.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1},
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {3,3,3,3,3,2,2,2,2,2,2,2,3,3,3,3},
		},	
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {4,4,4,4,4,3,3,3,3,3,3,3,4,4,4,4},
		},	
		[4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {5,5,5,5,5,3,3,3,3,3,3,3,5,5,5,5},
		},			
		[5] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7},
			},
			delay = 1/30,
			disorder = true,
			partZOrder = {2,2,2,2,2,5,5,5,5,5,5,5,2,2,2,2},
		},
		[6] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},
				[7] = {7,8,9,10,1,2,3,4,5,6,7},
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
		},					
	},
},
[225] = {
	modelName = "DragonBoat", --拼接图片名字用的，比如摩托车，March_"DragonBoat"_Part1_0_1.png
	modelIndex = 225,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 8, --model数量 动效填
	partCount = 8, --part数量 动效填
	dressModelType = 53, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_DragonBoat_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_DragonBoat.ccbi", --行军装扮名字，动效填
	plistName = "World_DragonBoat.plist",--行军装扮图集名字，动效填
	picName = "World_DragonBoat.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1},
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {3,3,3,3,2,2,2,2,2,2,2,2,2,3,3,3},
		},	
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {4,4,4,4,3,3,3,3,3,3,3,3,3,4,4,4},
		},			
		[4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7},
			},
			delay = 1/30,
			disorder = true,
			partZOrder = {2,2,2,2,4,4,4,4,4,4,4,4,4,2,2,2},
		},
		[5] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
		},
		[6] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
		},
		[7] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7},
		},
		[8] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8},
		},		
	},
},
[226] = {
	modelName = "Fortress", --拼接图片名字用的，比如摩托车，March_"Fortress"_Part1_0_1.png
	modelIndex = 226,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 7, --model数量 动效填
	partCount = 2, --part数量 动效填
	dressModelType = 54, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_Fortress_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_Fortress.ccbi", --行军装扮名字，动效填
	plistName = "World_Fortress.plist",--行军装扮图集名字，动效填
	picName = "World_Fortress.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},			
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7},
			},
			delay = 1/30,
			disorder = true,
			partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
		},
	},
},
[227] = {
	modelName = "Season", --拼接图片名字用的，比如摩托车，March_"Season"_Part1_0_1.png
	modelIndex = 227,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 7, --model数量 动效填
	partCount = 11, --part数量 动效填
	dressModelType = 55, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_Season_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_Season.ccbi", --行军装扮名字，动效填
	plistName = "World_Season.plist",--行军装扮图集名字，动效填
	picName = "World_Season.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},
				[7] = {7,8,9,10,1,2,3,4,5,6,7},
				[8] = {8,9,10,1,2,3,4,5,6,7,8},
				[9] = {9,10,1,2,3,4,5,6,7,8,9},
				[10] = {10,1,2,3,4,5,6,7,8,9,10},
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},	
		[2] = {
			frames = {
				[1] = {1},
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
		},
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,12,13,14,15,1,2,3,4,5,6,7,8,9},
				[10] = {10,11,12,13,14,15,1,2,3,4,5,6,7,8,9,10},
				[11] = {11,12,13,14,15,1,2,3,4,5,6,7,8,9,10,11},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
		},	
		[4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,12,13,14,15,1,2,3,4,5,6,7,8,9},
				[10] = {10,11,12,13,14,15,1,2,3,4,5,6,7,8,9,10},
				[11] = {11,12,13,14,15,1,2,3,4,5,6,7,8,9,10,11},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
		},			
		[5] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,12,13,14,15,1,2,3,4,5,6,7,8,9},
				[10] = {10,11,12,13,14,15,1,2,3,4,5,6,7,8,9,10},
				[11] = {11,12,13,14,15,1,2,3,4,5,6,7,8,9,10,11},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
		},	
		[6] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,12,13,14,15,1,2,3,4,5,6,7,8,9},
				[10] = {10,11,12,13,14,15,1,2,3,4,5,6,7,8,9,10},
				[11] = {11,12,13,14,15,1,2,3,4,5,6,7,8,9,10,11},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
		},	
		[7] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,12,13,14,15,1,2,3,4,5,6,7,8,9},
				[10] = {10,11,12,13,14,15,1,2,3,4,5,6,7,8,9,10},
				[11] = {11,12,13,14,15,1,2,3,4,5,6,7,8,9,10,11},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7},
		},	
		[8] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,12,13,14,15,1,2,3,4,5,6,7,8,9},
				[10] = {10,11,12,13,14,15,1,2,3,4,5,6,7,8,9,10},
				[11] = {11,12,13,14,15,1,2,3,4,5,6,7,8,9,10,11},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8},
		},	
		[9] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,12,13,14,15,1,2,3,4,5,6,7,8,9},
				[10] = {10,11,12,13,14,15,1,2,3,4,5,6,7,8,9,10},
				[11] = {11,12,13,14,15,1,2,3,4,5,6,7,8,9,10,11},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {10,10,10,10,9,9,9,9,9,9,9,9,9,10,10,10},
		},	
		[10] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,12,13,14,15,1,2,3,4,5,6,7,8,9},
				[10] = {10,11,12,13,14,15,1,2,3,4,5,6,7,8,9,10},
				[11] = {11,12,13,14,15,1,2,3,4,5,6,7,8,9,10,11},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {11,11,11,11,10,10,10,10,10,10,10,10,10,11,11,11},
		},	
		[11] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9},
				[10] = {10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9,10},
				[11] = {11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9,10,11},
			},
			delay = 1/30,
			disorder = true,
			partZOrder = {9,9,9,9,11,11,11,11,11,11,11,11,11,9,9,9},
		},	
	},
},
  [228] = {
	modelName = "Qixi3", --拼接图片名字用的，比如摩托车，March_"Qixi3"_Part1_0_1.png
	modelIndex = 228,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 4, --model数量 动效填
	partCount = 7, --part数量 动效填
	dressModelType = 56, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_Qixi3_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_Qixi3.ccbi", --行军装扮名字，动效填
	plistName = "World_Qixi3.plist",--行军装扮图集名字，动效填
	picName = "World_Qixi3.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1},
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
		},	
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
		},			
		[4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,1},
				[2] = {2,3,4,5,6,7,8,1,2},
				[3] = {3,4,5,6,7,8,1,2,3},
				[4] = {4,5,6,7,8,1,2,3,4},
			},
			delay = 1/8,
			disorder = true,
			partZOrder = {5,5,5,5,4,4,4,4,4,4,4,4,4,5,5,5},
		},	
		[5] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,1},
				[2] = {2,3,4,5,6,7,8,1,2},
				[3] = {3,4,5,6,7,8,1,2,3},
				[4] = {4,5,6,7,8,1,2,3,4},
			},
			delay = 1/8,
			disorder = true,
			partZOrder = {6,6,6,6,5,5,5,5,5,5,5,5,5,6,6,6},
		},	
		[6] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7},
				[8] = {8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8},
				[9] = {9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9},
				[10] = {10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9,10},
				[11] = {11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7,8,9,10,11},
			},
			delay = 1/30,
			disorder = true,
			partZOrder = {4,4,4,4,6,6,6,6,6,6,6,6,6,4,4,4},
		},	
		[7] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7},
		},	
	},
},
[229] = {
	modelName = "Anniversary7", --拼接图片名字用的，比如摩托车，March_"Anniversary7"_Part1_0_1.png
	modelIndex = 229,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 6, --model数量 动效填
	partCount = 7, --part数量 动效填
	dressModelType = 57, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_Anniversary7_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_Anniversary7.ccbi", --行军装扮名字，动效填
	plistName = "World_Anniversary7.plist",--行军装扮图集名字，动效填
	picName = "World_Anniversary7.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1},
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},
				[7] = {7,8,9,10,1,2,3,4,5,6,7},
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {3,3,3,3,2,2,2,2,2,2,2,2,2,3,3,3},
		},	
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},
				[7] = {7,8,9,10,1,2,3,4,5,6,7},
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {4,4,4,4,3,3,3,3,3,3,3,3,3,4,4,4},
		},			
		[4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,1,2,3,4,5,6,7},
			},
			delay = 1/30,
			disorder = true,
			partZOrder = {2,2,2,2,4,4,4,4,4,4,4,4,4,2,2,2},
		},	
		[5] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},
				[7] = {7,8,9,10,1,2,3,4,5,6,7},
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
		},	
		[6] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},
				[7] = {7,8,9,10,1,2,3,4,5,6,7},
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
		},	
		[7] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
				[7] = {7,8,9,10,11,12,13,14,15,1,2,3,4,5,6,7},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7},
		},	
	},
},
[230] = {
	modelName = "Gliding", --拼接图片名字用的，比如摩托车，March_"Gliding"_Part1_0_1.png
	modelIndex = 230,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 6, --model数量 动效填
	partCount = 5, --part数量 动效填
	dressModelType = 58, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_Gliding_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_Gliding.ccbi", --行军装扮名字，动效填
	plistName = "World_Gliding.plist",--行军装扮图集名字，动效填
	picName = "World_Gliding.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,1},
				[2] = {2,3,4,5,6,7,8,1,2},
				[3] = {3,4,5,6,7,8,1,2,3},
				[4] = {4,5,6,7,8,1,2,3,4},
				[5] = {5,6,7,8,1,2,3,4,5},
				[6] = {6,7,8,1,2,3,4,5,6},
			},
			delay = 1/8,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,1},
				[2] = {2,3,4,5,6,7,8,1,2},
				[3] = {3,4,5,6,7,8,1,2,3},
				[4] = {4,5,6,7,8,1,2,3,4},
				[5] = {5,6,7,8,1,2,3,4,5},
				[6] = {6,7,8,1,2,3,4,5,6},
			},
			delay = 1/8,
			disorder = true,
			partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
		},	
		[3] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},
				[7] = {7,8,9,10,1,2,3,4,5,6,7},
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
		},			
	    [4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},
				[7] = {7,8,9,10,1,2,3,4,5,6,7},
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
		},
		[5] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,1},
				[2] = {2,3,4,5,6,7,8,9,10,1,2},
				[3] = {3,4,5,6,7,8,9,10,1,2,3},
				[4] = {4,5,6,7,8,9,10,1,2,3,4},
				[5] = {5,6,7,8,9,10,1,2,3,4,5},
				[6] = {6,7,8,9,10,1,2,3,4,5,6},
				[7] = {7,8,9,10,1,2,3,4,5,6,7},
			},
			delay = 1/10,
			disorder = true,
			partZOrder = {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
		},		
	},
},
[231] = {
	modelName = "Nuwa", --拼接图片名字用的，比如摩托车，March_"Nuwa"_Part1_0_1.png
	modelIndex = 231,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 6, --model数量 动效填
	partCount = 6, --part数量 动效填
	dressModelType = 59, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_Nuwa_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_Nuwa.ccbi", --行军装扮名字，动效填
	plistName = "World_Nuwa.plist",--行军装扮图集名字，动效填
	picName = "World_Nuwa.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1,1},
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
		},	
		[3] = {
			frames = {
				[1] = {1,1},
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
		},			
	    [4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
		},
		[5] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
		},
		[6] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
		},		
	},
},
  [232] = {
	modelName = "Horse", --拼接图片名字用的，比如摩托车，March_"Horse"_Part1_0_1.png
	modelIndex = 232,  --顺序往后加，策划填
	modelType = 13,
	modelLevel = 1,
	modelCount = 6, --model数量 动效填
	partCount = 6, --part数量 动效填
	dressModelType = 60, --world_dress_model.conf 里面对应的modelType, 策划填 
	length = 400, --整个Entity的长度，用来计算行军hud的位置的 程序填
	checkBtnScale = 500, --Entity上的ccMenu长度，用来接收点击事件 程序填
	modelConf = "march_dress_Horse_conf", --装扮生成的配置文件名，不要重名，策划填
	modelCCBName = "Ani_World_March_Point_Horse.ccbi", --行军装扮名字，动效填
	plistName = "World_Horse.plist",--行军装扮图集名字，动效填
	picName = "World_Horse.png",--行军装扮图集名字，动效填
	zOrder = 1,
	sizeScale = 1,
	part = {  --动效填
		[1] = {
			frames = {
				[1] = {1,1},
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		},
		[2] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
		},	
		[3] = {
			frames = {
				[1] = {1,1},
			},
			delay = 1/1,
			disorder = true,
			partZOrder = {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
		},			
	    [4] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
		},
		[5] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
		},
		[6] = {
			frames = {
				[1] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,1},
				[2] = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,1,2},
				[3] = {3,4,5,6,7,8,9,10,11,12,13,14,15,1,2,3},
				[4] = {4,5,6,7,8,9,10,11,12,13,14,15,1,2,3,4},
				[5] = {5,6,7,8,9,10,11,12,13,14,15,1,2,3,4,5},
				[6] = {6,7,8,9,10,11,12,13,14,15,1,2,3,4,5,6},
			},
			delay = 1/15,
			disorder = true,
			partZOrder = {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
		},		
	},
},
[211] = {
	modelName = "EmptyMarch",
	modelType = 8,
	modelLevel = 13,
	modelCount = 1,
	modelCCBName = "Ani_World_March_Point_S.ccbi",
	-- plistName = 'WorldMarch_YurisRevenge2.plist',
	adjustDis = {
		[0]   = 0, 
		[1]   = 0, 
		[2]   = 0, 
		[3]   = 0, 
		[4]   = 0, 
		[5]   = 0, 
		[6]   = 0, 
		[7]   = 0, 
		[8]   = 0, 
		[9]   = 0, 
		[10]  = 0, 
		[11]  = 0, 
		[12]  = 0, 
		[13]  = 0, 
		[14]  = 0, 
		[15]  = 0, 
	},
},
[2973] = {
	modelName = "Harvester",
	modelLevel = 1,
	modelCount = 1,
	modelCCBName = "Ani_World_Act375_Vehicle_04.ccbi",
},
[2974] = {
	modelName = "Harvester",
	modelLevel = 1,
	modelCount = 1,
	modelCCBName = "Ani_World_Act375_Vehicle_03.ccbi",
},
[2975] = {
	modelName = "Harvester",
	modelLevel = 1,
	modelCount = 1,
	modelCCBName = "Ani_World_Act375_Vehicle_02.ccbi",
},
[2976] = {
	modelName = "Harvester",
	modelLevel = 1,
	modelCount = 1,
	modelCCBName = "Ani_World_Act375_Vehicle_01.ccbi",
},

[2981] = {
	modelName = "Harvester",
	modelLevel = 1,
	modelCount = 1,
	modelCCBName = "Ani_World_Act375_Vehicle_AL_02.ccbi",
},
[2991] = {
	modelName = "Harvester",
	modelLevel = 1,
	modelCount = 1,
	modelCCBName = "Ani_World_Act375_Vehicle_AL_01.ccbi",
},
}	
return march_model_conf