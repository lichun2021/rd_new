local lifetime_card_const_conf = {
-- 活动说明
['tips'] ={
     key = 'tips',
     value = '<font color="#adb0c0" face = "Microsoft YaHei" fontsize ="20">1.尊享卡分为黑金特权和铂金特权，两种特权。<br/>2.黑金特权为一次性解锁购买，在购买后会获得一次性的解锁奖励。并且激活每周和每月都可领取的宝箱奖励，每周宝箱和每月宝箱，会按照购买时间进行计算，未领取的宝箱奖励将在下一次刷新时补发。<br/>3.黑金特权中会激活四个增益效果加持：1：体力上限增加和体力恢复速度。2：每日活跃积分奖励收益增加。3：军情中心任务上限增加。<br/>4.铂金特权需要每月进行付费，需要先购买黑金特权后，才能购买铂金特权。<br/>5.铂金特权有以下几种增益效果加持：1：特权中心所有已激活特权奖励加成。2：幽灵基地每日集结发起（队长）奖励次数增加。3：联合军演积分增加（不包含首次通关奖励）和兑换次数增加。4：泰能研究所产量增加。5：机甲觉醒奖励增加。<br/>6.购买铂金特权时将会立即获得<font color="#f5d99d" face = "Font/HiraginoSansGB-W3.ttf" fontsize ="20">980金条+3日泰能研究所产量奖励+3日特权卡道具补给奖励</font>（该奖励内容为计算铂金特权卡增益效果后的值）。<br/>7.黑金特权和铂金特权在购买前如果已领取了含有增益加持的功能或活动奖励后，再购买后不会进行额外的加成奖励。<br/>8.特权类增益获得道具数量如遇小数点则会进行向上取整。其它内容则统一向下取整。</font>'
},
-- 大本解锁等级
['unlockCityLevel'] ={
     key = 'unlockCityLevel',
     value = 8
},
-- 免费体验时间（秒）
['freeTime'] ={
     key = 'freeTime',
     value = 172800
},
-- 老账号免费体验时间（秒）
['oldFreeTime'] ={
     key = 'oldFreeTime',
     value = 172800
},
-- 解锁奖励
['unlockAward'] ={
     key = 'unlockAward',
     value = '30000_1400011_100,30000_1000005_10,30000_1000003_100,30000_100050_50,30000_1780050_200'
},
-- 每周奖励
['weekAward'] ={
     key = 'weekAward',
     value = '30000_21065001_20,30000_21063005_200,30000_1782123_20,30000_1300027_300,30000_1300017_2000,30000_802005_100,30000_100050_30,30000_1780050_60'
},
-- 每月奖励
['monthAward'] ={
     key = 'monthAward',
     value = 510001
},
-- 终身特权（普通作用号）万分比
['commonEff'] ={
     key = 'commonEff',
     value = '518_50|517_2000,639_10000,640_2,647_200'
},
-- 进阶特权（高级作用号）万分比
['advanceEff'] ={
     key = 'advanceEff',
     value = '641_2000,646_2000,643_5000,644_5000|645_5000,642_2,222_5000'
},
-- 进阶卡持续时间（秒）
['advancedContinue'] ={
     key = 'advancedContinue',
     value = 2592000
},
-- 活动上线时间
['startTime'] ={
     key = 'startTime',
     value = '2023-11-23 8:00:00'
},
-- 终身特权（ios直购ID）
['commonIosPay'] ={
     key = 'commonIosPay',
     value = 3330001
},
-- 终身特权（安卓直购ID）
['commonAndroidPay'] ={
     key = 'commonAndroidPay',
     value = 3330003
},
-- 进阶特权（ios直购ID）
['iosPay'] ={
     key = 'iosPay',
     value = 3330002
},
-- 进阶特权（安卓直购ID）
['androidPay'] ={
     key = 'androidPay',
     value = 3330004
},
-- 到期前几天弹
['goFace'] ={
     key = 'goFace',
     value = 3
},
-- 到期后几天弹
['lateFace'] ={
     key = 'lateFace',
     value = 2
},
-- 
['commonNumber'] ={
     key = 'commonNumber',
     value = 3
},
-- 终身buffid标题1
['commonTitle1'] ={
     key = 'commonTitle1',
     value = '@lifeCardBf1'
},
-- 终身buffid
['commonEff1'] ={
     key = 'commonEff1',
     value = 'lifeCardbf1_lifeCardbf2'
},
-- buff跳转界面
['commonGotoType1'] ={
     key = 'commonGotoType1',
     value = ''
},
-- 终身buffid标题2
['commonTitle2'] ={
     key = 'commonTitle2',
     value = '@lifeCardBf2'
},
-- 终身buffid
['commonEff2'] ={
     key = 'commonEff2',
     value = 'lifeCardbf3'
},
-- buff跳转界面
['commonGotoType2'] ={
     key = 'commonGotoType2',
     value = ''
},
-- 终身buffid标题3
['commonTitle3'] ={
     key = 'commonTitle3',
     value = '@lifeCardBf3'
},
-- 终身buffid
['commonEff3'] ={
     key = 'commonEff3',
     value = 'lifeCardbf4'
},
-- buff跳转界面
['commonGotoType3'] ={
     key = 'commonGotoType3',
     value = ''
},
-- 终身buffid标题4
['commonTitle4'] ={
     key = 'commonTitle4',
     value = '@lifeCardBf4'
},
-- 终身buffid
['commonEff4'] ={
     key = 'commonEff4',
     value = 'lifeCardbf5_lifeCardbf6'
},
-- buff跳转界面
['commonGotoType4'] ={
     key = 'commonGotoType4',
     value = ''
},
-- 
['advanceNumber'] ={
     key = 'advanceNumber',
     value = '5'
},
-- 进阶buffid标题1
['advanceTitle1'] ={
     key = 'advanceTitle1',
     value = '@lifeCardBf5'
},
-- 进阶buffid
['advanceEff1'] ={
     key = 'advanceEff1',
     value = 'lifeCardbf7'
},
-- buff跳转界面
['advanceGotoType1'] ={
     key = 'advanceGotoType1',
     value = '1314_3'
},
-- 进阶buffid标题2
['advanceTitle2'] ={
     key = 'advanceTitle2',
     value = '@lifeCardBf9'
},
-- 进阶buffid
['advanceEff2'] ={
     key = 'advanceEff2',
     value = 'lifeCardbf11'
},
-- buff跳转界面
['advanceGotoType2'] ={
     key = 'advanceGotoType2',
     value = '2026'
},
-- 进阶buffid标题3
['advanceTitle3'] ={
     key = 'advanceTitle3',
     value = '@lifeCardBf7'
},
-- 进阶buffid
['advanceEff3'] ={
     key = 'advanceEff3',
     value = 'lifeCardbf12'
},
-- buff跳转界面
['advanceGotoType3'] ={
     key = 'advanceGotoType3',
     value = '1000_103'
},
-- 进阶buffid标题4
['advanceTitle4'] ={
     key = 'advanceTitle4',
     value = '@lifeCardBf8'
},
-- 进阶buffid
['advanceEff4'] ={
     key = 'advanceEff4',
     value = 'lifeCardbf9_lifeCardbf10'
},
-- buff跳转界面
['advanceGotoType4'] ={
     key = 'advanceGotoType4',
     value = '1321'
},
-- 进阶buffid标题5
['advanceTitle5'] ={
     key = 'advanceTitle5',
     value = '@lifeCardBf6'
},
-- 进阶buffid
['advanceEff5'] ={
     key = 'advanceEff5',
     value = 'lifeCardbf8'
},
-- buff跳转界面
['advanceGotoType5'] ={
     key = 'advanceGotoType5',
     value = '1310'
}
}
return lifetime_card_const_conf
