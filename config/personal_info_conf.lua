local personal_info_conf = {
[1] ={
	id = 1,
	icon = 'V3More_u_PersonalInfo_Check.png',
	cellTitle = '@personalInfoCheck',
	popTitle = '@personalInfoGo',
	callFunc = 'onPersionalInfo'
},
[2] ={
	id = 2,
	icon = 'V3More_u_PersonalInfo_Check.png',
	cellTitle = '@FriendInfoCheck',
	popTitle = '@personalInfoGo',
	callFunc = 'onFriendInfo'
},
[3] ={
	id = 3,
	icon = 'V3More_u_PersonalInfo_Call.png',
	cellTitle = '@OtherInfoCheck',
	popDesc = 'contactUsDes',
	popTitle = '@contactUsGo',
	callFunc = 'onContactUs'
}
}
return personal_info_conf
