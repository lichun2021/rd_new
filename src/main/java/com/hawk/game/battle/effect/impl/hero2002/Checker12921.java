package com.hawk.game.battle.effect.impl.hero2002;

import com.hawk.game.battle.BattleUnity;
import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;

/**
 * 【万分比】【12921】谭雅军魂【定点爆破】：触发猎杀指令时，造成的伤害额外增加
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.HERO_12921)
public class Checker12921 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		String playerId = parames.unity.getPlayerId();
		BattleUnity maxArmy = parames.getPlayeMaxMarchArmy(playerId);
		if (maxArmy == null || parames.unity != maxArmy) {
			return CheckerKVResult.DefaultVal;
		}
		return new CheckerKVResult(parames.unity.getEffVal(effType()), 0);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}
}
