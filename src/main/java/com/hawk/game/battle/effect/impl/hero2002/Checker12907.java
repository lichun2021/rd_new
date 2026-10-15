package com.hawk.game.battle.effect.impl.hero2002;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;

/**
 * 【万分比】【12907】谭雅【意志强化】：该效果拥有荣耀谭雅即生效，无需出征或驻防。
 * 战斗开始时，自身部队获得意志强化使自身受到伤害减少 +{7}，该效果每回合衰减，第10回合结束后效果消失。
 */
@BattleTupleType(tuple = Type.REDUCE_HURT_PCT)
@EffectChecker(effType = EffType.HERO_12907)
public class Checker12907 implements IChecker {
	private static final int MAX_ROUND = 10;

	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (!isSoldier(parames.type)) {
			return CheckerKVResult.DefaultVal;
		}
		int val = Checker12906.resolveEffVal(parames, effType());
		if (val <= 0) {
			return CheckerKVResult.DefaultVal;
		}
		int round = Checker12906.resolveBattleRound(parames);
		if (round > MAX_ROUND) {
			return CheckerKVResult.DefaultVal;
		}
		int decayed = (int) (val * (MAX_ROUND + 1L - round) / MAX_ROUND);
		return new CheckerKVResult(decayed, 0);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}
}
