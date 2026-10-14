package com.hawk.game.battle.effect.impl.manhattan;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;

/** 基因进化：战斗中每经过5回合，己方全兵种造成伤害增加，可叠加（回合判定在 BattleSoldier.addHurtValPct 中执行）。 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.EFF_2223)
public class Checker2223 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		int value = isSoldier(parames.type) ? parames.unity.getEffVal(effType()) : 0;
		return new CheckerKVResult(value, 0);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}
}
