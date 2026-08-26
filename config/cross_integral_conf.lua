local cross_integral_conf = {
[1] ={
	id = 1,
	name = '资源采集',
	scoreCof = '1007_0.001,1008_0.001,1010_0.006,1009_0.024,1001_24',
	crossScoreMag = 15000,
	gotoType = 31,
	gotoValue = '1_1_1007',
	gainScoreTips = '@CrossScoreTips1',
	gainScoreDes = 'CrossGainScoreDes1,CrossGainScoreDes11,CrossGainScoreDes12,CrossGainScoreDes13,CrossGainScoreDes14'
},
[3] ={
	id = 3,
	name = '击杀野怪',
	scoreCof = '20_40_60_80_100_120_140_160_180_200_220_240_260_280_300_320_340_360_380_400_420_440_460_480_500_520_540_560_580_600_620_640_660_680_700_720_740_760_780_800_820_840_860_880_900_920_940_960_980_1000',
	crossScoreMag = 12000,
	gotoType = 200,
	gotoValue = '2_0',
	gainScoreTips = '@CrossScoreTips2',
	gainScoreDes = 'CrossGainScoreDes2'
},
[4] ={
	id = 4,
	name = '击杀敌军',
	scoreCof = '15_30_60_120_200_320_500_750_1050_1450_1950_2500_3000_3000,1.5_3_6_12_20_32_50_75_105_145_195_250_300_300',
	crossScoreMag = 10000,
	gotoType = 1201,
	gainScoreTips = '@CrossScoreTips5',
	maxShowIndex = 13,
	gainPlantScoreDes = 'CrossGainScoreDes52,CrossGainScoreDes53',
	gainScoreDes = 'CrossGainScoreDes5,CrossGainScoreDes51'
},
[5] ={
	id = 5,
	name = '能量塔',
	scoreCof = 15000,
	crossScoreMag = 15000,
	gotoType = 31,
	gotoValue = 44,
	gainScoreTips = '@CrossScoreTips3',
	gainScoreDes = 'CrossGainScoreDes31'
}
}
return cross_integral_conf
