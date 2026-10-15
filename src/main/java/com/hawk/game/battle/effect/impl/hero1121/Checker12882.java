package com.hawk.game.battle.effect.impl.hero1121;

import java.util.HashSet;
import java.util.Set;

import com.hawk.game.battle.BattleUnity;
import com.hawk.game.battle.effect.BattleConst;
import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;

/**
 * 【万分比】【12882】莱因：任命在战备参谋部时，集结战斗开始时观测系统会锁定敌方数量最多的兵种，
 * 己方部队对该兵种造成伤害时攻击增加 【19.60% + {6} * 战备参谋部中任命莱因的友方成员数量】（友方莱因计数时至多取 7 个）
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.HERO_12882)
public class Checker12882 implements IChecker {
	private static final int BASE_VAL = 1960;
	private static final int MAX_PLAYERS = 7;

	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (!BattleConst.WarEff.MASS.check(parames.troopEffType) || !isSoldier(parames.type)) {
			return CheckerKVResult.DefaultVal;
		}
		int selfVal = parames.unity.getEffVal(effType());
		if (selfVal <= 0) {
			return CheckerKVResult.DefaultVal;
		}

		Set<String> players = new HashSet<>();
		for (BattleUnity unity : parames.unityList) {
			if (unity.getEffVal(effType()) > 0) {
				players.add(unity.getPlayerName());
			}
		}
		int count = Math.min(MAX_PLAYERS, players.size());
		if (count <= 0) {
			return CheckerKVResult.DefaultVal;
		}

		return new CheckerKVResult(BASE_VAL + selfVal * count, 0);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}
}
