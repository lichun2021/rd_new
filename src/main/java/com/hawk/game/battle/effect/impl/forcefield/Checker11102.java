package com.hawk.game.battle.effect.impl.forcefield;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;
import com.hawk.game.protocol.Const.SoldierType;

/**
 * 【11102~11103&11117】采矿
- 【万分比】【11102~11103】每回合一次，回合开始时自身出征数量最多的采矿车对自身身后最近2（4）【11102】个非采矿前排单位（可叠加2（effect11102Maxinum）次），攻击+50/100/150/200/300%【11103】。满级时数值+队伍现有护盾百分比*50%【11117】->针对己方兵种留个内置系数effect11102SoldierAdjust
  - 战报相关
  - 于战报中隐藏
  - 不合并至精简战报中
  - 在战斗开始前判定，满足条件后本次战斗全程生效
  - 注：自身护盾存在时才生效
- 每回合一次，回合开始时自身出征数量最多的采矿车
  - 每回合触发一次
  - 回合开始时触发（1阶段）
  - 仅自身出征数量最多的采矿车触发（兵种类型 = 8）
    - 战斗开始前判定，战斗中不改变
    - 真实出征部队数量，在战斗开始前判定，即参谋军威技能、所罗门、吉迪恩 这种战斗中改变部队数量的机制对此无影响
- 对自身身后最近2（4）【11102】个非采矿前排单位（可叠加2（effect11102Maxinum）次）
  - 所谓自身身后最近的 X 个己方单位
    - 战斗单位排序在原目标后面且最近的 X 个
      - 注：若敌方其他战斗单位不足，则有多少取多少（但不能选择原目标作为目标）
  - 最近的单位为战斗开始前判定，实际战斗中不改变
  - 选择单位数量读取作用号【11102】
    - 配置格式：绝对值
  - 叠加次数读取const表，字段effect11102Maxinum
    - 配置格式：绝对值
- 攻击+50/100/150/200/300%【11103】
  - 该作用号为常规外围属性加成效果，与其他作用号累加计算
    - 即 实际属性 = 基础属性*（1 + 各类攻击加成 +【本作用值】）
      - 配置格式：万分比
- 满级时数值+队伍现有护盾百分比*50%【11117】
  - 技能等级 = 5 时，追加此效果
    - 即 实际属性 = 基础属性*（1 + 各类攻击加成 +(【本作用值】+队伍现有护盾百分比*【11117】)*兵种修正系数）
      - 固定值读取读取const表，字段【11117】
        - 配置格式：万分比
- ->针对己方兵种留个内置系数effect11102SoldierAdjust
  - 实际针对敌方各兵种类型，单独配置系数；兵种修正系数 读取const表，字段effect11102SoldierAdjust
    - 配置格式：兵种类型id1_修正系数1，......兵种类型id8_修正系数8
      - 修正系数具体配置为万分比
  - 配置格式：万分比
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.EFF_11102)
public class Checker11102 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		int effPer = 0;
		int effNum = 0;

		if (parames.unity != parames.getPlayerMaxFreeArmy(parames.unity.getPlayerId(), SoldierType.CANNON_SOLDIER_8)) {
			return CheckerKVResult.DefaultVal;
		}
		effPer = parames.unity.getEffVal(effType());
		return new CheckerKVResult(effPer, effNum);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}
}
