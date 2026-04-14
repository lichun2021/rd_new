package com.hawk.game.battle.effect.impl.bxzb;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;

/**
 *     - 2301=时间破坏增伤，显示为整数。攻击时，通过时间湍流对目标造成的伤害提升。
  - 2302=空间破坏增伤，显示为整数。攻击时，通过空间裂隙对目标造成的伤害提升。
  - 2303=时间维护减伤，显示为整数。防止敌人利用时间湍流，使自身受到伤害减少。
  - 2304=空间维护减伤，显示为整数。防止敌人利用空间裂隙，使自身受到伤害减少。
 */
@BattleTupleType(tuple = { Type.SOLDIER_SKILL })
@EffectChecker(effType = EffType.EFF_2303)
public class Checker2303 implements IChecker {

	@Override
	public CheckerKVResult value(CheckerParames parames) {

		int effPer = 0;
		int effNum = 0;
		if (isSoldier(parames.type)) {
			effPer = parames.unity.getEffVal(effType());
		}

		return new CheckerKVResult(effPer, effNum);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}

}
