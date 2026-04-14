package com.hawk.game.battle.effect.impl.hero1120;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;
/**
 * - 【万分比】【12834】
  - 战报相关
    - 于战报中隐藏
    - 不合并至精简战报中
  - 在战斗开始前判定，满足条件后本场战斗全程生效
  - 本技能为尤利娅作用号【12413】额外数值增加【241024】【SSS】【军事】【轰炸】【尤利娅】【Yulia】【1104】
    - 即 【12413】实际值=【12413】+【12834】
    - 注：需要自身和尤利娅一同出征才有加成
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL2)
@EffectChecker(effType = EffType.HERO_12834)
public class Checker12834 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (parames.solider.getEffVal(EffType.HERO_12831) == 0) {
			return CheckerKVResult.DefaultVal;
		}

		return new CheckerKVResult(parames.unity.getEffVal(effType()), 0);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}

}
