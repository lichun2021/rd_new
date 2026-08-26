local present_building_conf = {
[1] ={
	id = 1,
	type = 1,
	spine = 'PresidentialPalace',
	spineNS = 'PresidentialPalace_Nation',
	name = '@Capital',
	icon = 'Alliance_BuildIcon_PresidentOffice.png',
	gridCnt = 3,
	count = 1,
	nameOffsetY = 192,
	buildingModelScale = 0.3,
	buildingModelScale1 = 0.4,
	effectCCB = 'RAWorldBuild_PresidentialPalace_Snowball.ccbi',
	effectCCBTimeLine = '20,Snow_1;120,Snow_2'
},
[2] ={
	id = 2,
	type = 2,
	ccb = 'RAKingWar_ElectricityTower.ccbi',
	ccbNS = 'RAKingWar_ElectricityTower_Nation.ccbi',
	name = '@PresidentTower',
	icon = 'Alliance_BuildIcon_PresidentTower.png',
	gridCnt = 2,
	count = 4,
	nameOffsetY = 128,
	buildingModelScale = 0.4,
	buildingModelScale1 = 0.5
	
}
}
return present_building_conf
