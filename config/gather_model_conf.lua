local gather_model_conf = {
["Gather_Soldier1"] = {
	modelName = "Gather_Soldier1",
	modelScale = 0.6,
	modelOffsetX = 0,
	modelOffsetY = 0,
	modelEffect = {1065,1066},
	idle1 = {
		name = "Idle1",
		part = {
			[1] = {
				name = "Part1",
				frames = "1",--"1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_2_2_2_2_2_2_3_3_3_3_3_4_4_4_4_5_5_5_5_5_6_6_6_6_6_6_6_6_6_6_5_5_5_5_5_4_4_4_4_3_3_3_3_3_2_2_2_2_2_2_1",
				delay = 1/30,
			},
		}
	},
	idle2 = {
		name = "Idle2",
		part = {
			[1] = {
				name = "Part1",
				frames = "1_1_2_2_3_4_4_5_5_6_6_6_7_7_8_8_9_10_10_11_12_12_13_13_13_13_13_13_13_14_14_14_15_15_16_16_17_17_1_1_1",
				delay = 1/30,
			},
		}
	},
	action = {
		name = "Action",
		part = {
			[1] = {
				name = "Part1",
				frames = "1_2_3_4_5_6_7_8_9_9_9_10_10_11_12_12_12_12_12_12_12_12_12_12_13_13_14_15_16_16_16_16_16_16_16_16_16_16_16_16_16_16_16_16_16_16_16_17_17_18_18_19_19_1_1_1",
				delay = 1/30,
			},
		},
	},
},

["Gather_Soldier2"] = {
	modelName = "Gather_Soldier2",
	modelScale = 0.6,
	modelOffsetX = 0,
	modelOffsetY = 0,
	modelEffect = {1065,1066},
	idle1 = {
		name = "Idle1",
		part = {
			[1] = {
				name = "Part1",
				frames = "1",
				delay = 1/30,
			},
		}
	},
	idle2 = {
		name = "Idle2",
		part = {
			[1] = {
				name = "Part1",
				frames = "1_1_2_2_2_3_3_4_4_5_5_5_6_6_6_6_6_6_7_7_8_8_9_10_10_10_11_11_11_11_11_12_12_12_13_13_13_14_14_15_15_16_16_1_1",
				delay = 1/30,
			},
		}
	},
	action = {
		name = "Action",
		part = {
			[1] = {
				name = "Part1",
				frames = "1_1_1_2_2_3_3_4_4_5_5_6_6_6_7_7_7_8_8_9_10_10_10_11_11_11_11_11_11_12_12_13_13_13_14_14_14_14_14_14_14_14_14_14_14_14_14_15_15_15_7_7_7_6_6_5_5_4_4_4_2_2_2_1_1",
				delay = 1/30,
			},
		}
	},
},

["Gather_Tank1"] = {
	modelName = "Gather_Tank1",
	modelScale = 0.7,
	modelOffsetX = 15,
	modelOffsetY = -9,
	modelEffect = {1061},
	idle1 = {
		name = "Idle1",
		part = {
			[1] = {
				name = "Part1",
				frames = "1_1",
				delay = 1/30,
			},
			[2] = {
				name = "Part2",
				frames = "1_1",
				delay = 1/30,
			},
		}
	},
	action = {
		name = "Action",
		part = {
			[1] = {
				name = "Part1",
				frames = "1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1",
				delay = 1/30,
			},
			[2] = {
				name = "Part2",
				frames = "1_1_2_2_3_3_4_4_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_5_4_4_3_3_2_2_1_1",
				delay = 1/30,
			},
		}
	},
},

["Gather_Tank2"] = {
	modelName = "Gather_Tank2",
	modelScale = 0.7,
	modelOffsetX = 10,
	modelOffsetY = -10,
	modelEffect = {1062},
	idle1 = {
		name = "Idle1",
		part = {
			[1] = {
				name = "Part1",
				frames = "1_1",
				delay = 1/30,
			},
			[2] = {
				name = "Part2",
				frames = "1_1",
				delay = 1/30,
			},
		}
	},
	action = {
		name = "Action",
		part = {
			[1] = {
				name = "Part1",
				frames = "1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1",
				delay = 1/30,
			},
			[2] = {
				name = "Part2",
				frames = "4_4_5_5_6_6_6_7_7_7_7_7_7_7_7_7_7_7_7_7_7_7_7_7_7_6_6_6_5_5_4_4_3_3_2_2_2_1_1_1_1_1_1_1_1_1_1_1_1_1_1_2_2_2_3_3_3_4_4_4_4",
				delay = 1/30,
			},
		}
	},
},

["Gather_RemoteFire1"] = {
	modelName = "Gather_RemoteFire1",
	modelScale = 0.6,
	modelOffsetX = 20,
	modelOffsetY = -8,
	modelEffect = {1068},
	idle1 = {
		name = "Idle1",
		part = {
			[1] = {
				name = "Part1",
				frames = "1_1",
				delay = 1/30,
			},
			[2] = {
				name = "Part2",
				frames = "1_1",
				delay = 1/30,
			},
		}
	},
	action = {
		name = "Action",
		part = {
			[1] = {
				name = "Part1",
				frames = "1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1",
				delay = 1/30,
			},
			[2] = {
				name = "Part2",
				frames = "1_1_1_2_2_2_3_3_4_4_5_5_5_6_6_6_6_6_6_6_6_6_6_6_6_6_6_6_6_6_6_6_6_6_6_6_6_6_6_6_6_6_6_6_6_6_6_5_5_5_4_4_3_3_2_2_2_1_1_1_1",
				delay = 1/30,
			},
		}
	},
},

["Gather_RemoteFire2"] = {
	modelName = "Gather_RemoteFire2",
	modelScale = 0.7,
	modelOffsetX = 20,
	modelOffsetY = -9,
	modelEffect = {1067},
	idle1 = {
		name = "Idle1",
		part = {
			[1] = {
				name = "Part1",
				frames = "1_1",
				delay = 1/30,
			},
			[2] = {
				name = "Part2",
				frames = "1_1",
				delay = 1/30,
			},
		}
	},
	action = {
		name = "Action",
		part = {
			[1] = {
				name = "Part1",
				frames = "1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1",
				delay = 1/30,
			},
			[2] = {
				name = "Part2",
				frames = "1_2_1_2_1_2_1_2_1_2_1_2_1_2_1_2_1_2_1_2_1_2_1_2_1_2_1_2_1_2_1_2_1_2_1_2_1_2_1_2_1_2_1_2_1_2_1_2_1_2_1_2_1_2_1_2_1_2_1_2_2",
				delay = 1/30,
			},
		}
	},
},

["Gather_AirForce1"] = {
	modelName = "Gather_AirForce1",
	modelScale = 0.7,
	modelOffsetX = 15,
	modelOffsetY = 8,
	modelEffect = {1063},
	idle1 = {
		name = "Idle1",
		part = {
			[1] = {
				name = "Part1",
				frames = "1_1",
				delay = 1/30,
			},
			[2] = {
				name = "Part2",
				frames = "1_1",
				delay = 1/30,
			},
		}
	},
	action = {
		name = "Action",
		part = {
			[1] = {
				name = "Part1",
				frames = "1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1",
				delay = 1/30,
			},
			[2] = {
				name = "Part2",
				frames = "1_1_1_1_2_2_2_3_3_3_1_1_2_2_3_3_1_1_2_2_3_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_3_1_1_2_2_3_3_1_1_2_2_3_3_3_1_1_1_2_2_2_3_3_3_3_3",
				delay = 1/30,
			},
		}
	},
},

["Gather_AirForce3"] = {
	modelName = "Gather_AirForce3",
	modelScale = 0.8,
	modelOffsetX = 20,
	modelOffsetY = 40,
	modelEffect = {1064},
	idle1 = {
		name = "Idle1",
		part = {
			[1] = {
				name = "Part1",
				frames = "1_1_1_1",
				delay = 1/30,
			},
			[2] = {
				name = "Part2",
				frames = "1_2_3_3",
				delay = 1/30,
			},
			[3] = {
				name = "Part3",
				frames = "1_1_1_1",
				delay = 1/30,
			},
		}
	},
	action = {
		name = "Action",
		part = {
			[1] = {
				name = "Part1",
				frames = "1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1",
				delay = 1/30,
			},
			[2] = {
				name = "Part2",
				frames = "1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_1_2_3_3",
				delay = 1/30,
			},
			[3] = {
				name = "Part3",
				frames = "1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1",
				delay = 1/30,
			},
		}
	},
},

}

return gather_model_conf