local plant_soldier_const_conf = {
-- 泰能进化所-初始训练上限
['evolutionNumber'] ={
     key = 'evolutionNumber',
     value = 200
},
-- 泰能进化所 前置条件,必须创建前面的建筑
['evolutionFrontBuild'] ={
     key = 'evolutionFrontBuild',
     value = 223601
},
-- 剧情任务第一个ID
['storyFirstId'] ={
     key = 'storyFirstId',
     value = 10101
},
-- 章节剧情的任务ID
['questId'] ={
     key = 'questId',
     value = 1400000
},
-- 泰能进化所收兵气泡出现条件，大于
['evolutionPupNumber'] ={
     key = 'evolutionPupNumber',
     value = 0
},
-- 功能开关，1开，0关
['isOpen'] ={
     key = 'isOpen',
     value = 1
},
-- 取消进化，返还资源比例，万分比
['cancelEvolutionRes'] ={
     key = 'cancelEvolutionRes',
     value = 5000
},
-- 取消进化，返还勋章比例，万分比
['cancelEvolutionItem'] ={
     key = 'cancelEvolutionItem',
     value = 10000
}
}
return plant_soldier_const_conf
