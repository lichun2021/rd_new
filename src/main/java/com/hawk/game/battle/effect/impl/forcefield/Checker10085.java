package com.hawk.game.battle.effect.impl.forcefield;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;

/**
【10084】
【万分比】【10084】对失去星穹护盾保护的敌人造成的伤害增加XX.XX%
- 造成伤害时，敌方没有星穹护盾或者护盾值为0时生效
- 该作用号为外围伤害加成效果，与其他伤害增加作用号累加计算（与12101/10082等伤害增加作用号加法运算）
  - 即 实际伤害 = 基础伤害*（1 + 各类伤害加成+【本作用值】）*（1 - 各类减免）
    - 配置格式为万分比

【10085】
【万分比】【10085】受到星穹护盾保护时，造成的伤害增加XX.XX%
- 造成伤害时，自身有星穹护盾时生效
- 该作用号为外围伤害加成效果，与其他伤害增加作用号累加计算（与12101/10082等伤害增加作用号加法运算）
  - 即 实际伤害 = 基础伤害*（1 + 各类伤害增加加成 +【本作用值】）
    - 配置格式为万分比

【10086】
【万分比】【10086】受到星穹护盾保护时，受到的伤害减少XX.XX%
- 受到伤害时，自身有星穹护盾时生效
- 该伤害减少效果与其他伤害减少效果累乘计算；
  - 即 实际伤害 = 基础伤害*（1 + 敌方各类伤害加成）*（1 - 己方某伤害减少）*（1 - 本作用值伤害减少）
    - 配置格式为万分比
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.EFF_10085)
public class Checker10085 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		int effPer = 0;
		int effNum = 0;
		if (isSoldier(parames.type)) {
			effPer = parames.unity.getEffVal(effType());
		}
		return new CheckerKVResult(effPer, effNum);
	}
}
