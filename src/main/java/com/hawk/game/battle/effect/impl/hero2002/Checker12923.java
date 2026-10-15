package com.hawk.game.battle.effect.impl.hero2002;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;

/**
 * 【万分比】【12923】谭雅军魂【特工网络】：意志强化期间，自身部队受到伤害减少额外增加（随回合线性衰减，第10回合后消失）
 */
@BattleTupleType(tuple = Type.REDUCE_HURT_PCT)
@EffectChecker(effType = EffType.HERO_12923)
public class Checker12923 implements IChecker {
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
