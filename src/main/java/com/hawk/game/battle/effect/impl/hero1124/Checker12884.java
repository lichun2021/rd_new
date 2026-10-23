package com.hawk.game.battle.effect.impl.hero1124;

import com.hawk.game.battle.effect.BattleConst;
import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;
import com.hawk.game.protocol.Const.SoldierType;

/**
 * 【万分比】【12884】莱因：任命在战备参谋部时，集结战斗开始前，集结全队出征防御坦克数量每达到 5 万，
 * 自身防御坦克单位受到攻击时，伤害减少 + {8}（该效果可叠加，至多 20 层）
 */
@BattleTupleType(tuple = Type.REDUCE_HURT_PCT)
@EffectChecker(effType = EffType.HERO_12884)
public class Checker12884 implements IChecker {
	private static final int STEP_COUNT = 50_000;
	private static final int MAX_LAYERS = 20;

	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (!BattleConst.WarEff.MASS.check(parames.troopEffType) || parames.type != SoldierType.TANK_SOLDIER_1) {
			return CheckerKVResult.DefaultVal;
		}
		int effVal = parames.unity.getEffVal(effType());
		if (effVal <= 0) {
			return CheckerKVResult.DefaultVal;
		}

		long tankCnt = parames.unitStatic.getArmyCountMapMarch().get(SoldierType.TANK_SOLDIER_1);
		int layers = (int) Math.min(MAX_LAYERS, tankCnt / STEP_COUNT);
		if (layers <= 0) {
			return CheckerKVResult.DefaultVal;
		}

		return new CheckerKVResult(effVal * layers, 0);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}
}
