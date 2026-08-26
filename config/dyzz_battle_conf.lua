local dyzz_battle_conf = {
-- 准备时间
['prepairTime'] ={
     key = 'prepairTime',
     value = 60
},
-- 采集持续时间
['collectTime'] ={
     key = 'collectTime',
     value = 180
},
-- 战斗持续
['battleTime'] ={
     key = 'battleTime',
     value = 420
},
-- 燃烧加速
['fireSpeed'] ={
     key = 'fireSpeed',
     value = 45
},
-- 出生点配置A
['bornPointA'] ={
     key = 'bornPointA',
     value = '10_121,12_123,14_125,16_127,18_129'
},
-- 出生点配置B
['bornPointB'] ={
     key = 'bornPointB',
     value = '59_30,57_28,55_26,53_24,51_22'
},
-- 行军加速
['playerMarchSpeedUp'] ={
     key = 'playerMarchSpeedUp',
     value = 4
},
-- 治疗加速倍数
['cureSpeedUp'] ={
     key = 'cureSpeedUp',
     value = 4
},
-- 玩家城防耐久值
['citydefense'] ={
     key = 'citydefense',
     value = 20000
},
-- 战场消息发送间隔
['noticeCd'] ={
     key = 'noticeCd',
     value = 5
},
-- 战场消息最长停留时间
['dyzzNoticeMaxTime'] ={
     key = 'dyzzNoticeMaxTime',
     value = 3
},
-- 战场消息最小停留时间
['dyzzNoticeMinTime'] ={
     key = 'dyzzNoticeMinTime',
     value = 1
},
-- 自选奖励检测是否为头像框
['dyzzIsPortrait'] ={
     key = 'dyzzIsPortrait',
     value = '16400011,16500011,16600011'
},
-- 专属加速道具-退副本时清空
['speedupItem'] ={
     key = 'speedupItem',
     value = 820005
},
-- 每次进入战场可领取的道具次数
['speedupItemFree'] ={
     key = 'speedupItemFree',
     value = 40
},
-- 购买加速消耗金条数量
['speedupItemCost'] ={
     key = 'speedupItemCost',
     value = '10000_1001_3'
},
-- 单场战斗可购买加速次数
['speedupItemCnt'] ={
     key = 'speedupItemCnt',
     value = 200
}
}
return dyzz_battle_conf
