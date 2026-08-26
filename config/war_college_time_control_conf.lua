local war_college_time_control_conf = {
-- 退出副本保护时长/分
['warCollegeQuitSheld'] ={
     key = 'warCollegeQuitSheld',
     value = 1
},
-- 退副本CD
['warCollegeQuitCd'] ={
     key = 'warCollegeQuitCd',
     value = '1,2,3,5,10,15'
},
-- 老带新奖励每日次数上限
['dailyTeacherReward'] ={
     key = 'dailyTeacherReward',
     value = 3
},
-- 开服第X天，固定开放，多个日期_分隔
['serverDaysOpen'] ={
     key = 'serverDaysOpen',
     value = '1_2'
},
-- 开服第X天起（包含），走days字段的排期。
['normalDays'] ={
     key = 'normalDays',
     value = 3
}
}
return war_college_time_control_conf
