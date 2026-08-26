local college_const_conf = {
-- 教官大本等级限制
['coachCityLvlLimit'] ={
     key = 'coachCityLvlLimit',
     value = 10
},
-- 教官等级限制
['coachLevelLimit'] ={
     key = 'coachLevelLimit',
     value = 0
},
-- 创建学院消耗
['createCost'] ={
     key = 'createCost',
     value = '10000_1001_100'
},
-- 学院学员人数限制
['collegeMemberMaxCnt'] ={
     key = 'collegeMemberMaxCnt',
     value = 9
},
-- 在线时长领取数量限制
['onlineRewardLimit'] ={
     key = 'onlineRewardLimit',
     value = 9
},
-- 教官列表显示数量
['coachListSize'] ={
     key = 'coachListSize',
     value = 10
},
-- 申请列表显示数量限制
['applyListSize'] ={
     key = 'applyListSize',
     value = 30
},
-- 学院作用号
['memberEffect'] ={
     key = 'memberEffect',
     value = '320_100'
},
-- 提醒上线冷却
['remindTimeLimitCD'] ={
     key = 'remindTimeLimitCD',
     value = 1800
},
-- 离线多长时间之后可以提醒上线
['remindTime'] ={
     key = 'remindTime',
     value = 2000
},
-- 加入申请有效期
['applyEffectTime'] ={
     key = 'applyEffectTime',
     value = 86400
},
-- 功能引导开启条件，大本等级
['openLevel'] ={
     key = 'openLevel',
     value = 10
},
-- 学员主动退出学院扣除积分百分比
['collegeDeductRatio'] ={
     key = 'collegeDeductRatio',
     value = 5000
},
-- 学院可存储体力上限
['maxStrength'] ={
     key = 'maxStrength',
     value = 2000
},
-- 每日可分配体力上限
['dayMaxStrength'] ={
     key = 'dayMaxStrength',
     value = 50
},
-- 重新命名消耗
['renameExpend'] ={
     key = 'renameExpend',
     value = '10000_1001_0,10000_1001_100,10000_1001_100'
},
-- 换教官的时间：天
['changeCoach'] ={
     key = 'changeCoach',
     value = 7
},
-- 线上已有学院乱码名称
['randomName'] ={
     key = 'randomName',
     value = '学院'
},
-- 体力补充比例:万分比
['strengthBack'] ={
     key = 'strengthBack',
     value = 500
},
-- 最大名字长度
['nameLenLimitMax'] ={
     key = 'nameLenLimitMax',
     value = 8
},
-- 最小名字长度
['nameLenLimitMin'] ={
     key = 'nameLenLimitMin',
     value = 3
},
-- 默认名字
['@defaultCollegeName'] ={
     key = '@defaultCollegeName',
     value = '学院毕业生'
},
-- 跟随学院名字更改
['changeCollegeName'] ={
     key = 'changeCollegeName',
     value = '{0}·学院'
},
-- 学院邀请函发送冷却：秒
['letterSendCD'] ={
     key = 'letterSendCD',
     value = 1800
},
-- 邀请函生效时间：秒
['letterContinuedCD'] ={
     key = 'letterContinuedCD',
     value = 1800
},
-- 再次加入冷却时间：秒
['letterAgainJoinCD'] ={
     key = 'letterAgainJoinCD',
     value = 1800
},
-- 单次改变的体力数量
['strengthChange'] ={
     key = 'strengthChange',
     value = 5
}
}
return college_const_conf
