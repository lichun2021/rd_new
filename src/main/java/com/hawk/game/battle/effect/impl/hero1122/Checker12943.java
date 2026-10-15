package com.hawk.game.battle.effect.impl.hero1122;

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
 * 【万分比】【12943】艾伦：任命在战备参谋部时，集结战斗开始前，集结全队出征近战单位每满 20 万，
 * 自身近战部队受到攻击时伤害减少 + {7}（该效果可叠加，至多 20 层）
 */
@BattleTupleType(tuple = Type.REDUCE_HURT_PCT)
@EffectChecker(effType = EffType.HERO_12943)
public class Checker12943 implements IChecker {
	private static final int STEP_COUNT = 200_000;
	private static final int MAX_LAYERS = 20;

	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (!BattleConst.WarEff.MASS.check(parames.troopEffType) || !isJinzhan(parames.type)) {
			return CheckerKVResult.DefaultVal;
		}
		int effVal = parames.unity.getEffVal(effType());
		if (effVal <= 0) {
			return CheckerKVResult.DefaultVal;
		}

		long meleeCnt = parames.unitStatic.getArmyCountMapMarch().get(SoldierType.TANK_SOLDIER_1)
				+ parames.unitStatic.getArmyCountMapMarch().get(SoldierType.TANK_SOLDIER_2)
				+ parames.unitStatic.getArmyCountMapMarch().get(SoldierType.PLANE_SOLDIER_3)
				+ parames.unitStatic.getArmyCountMapMarch().get(SoldierType.CANNON_SOLDIER_8);
		int layers = (int) Math.min(MAX_LAYERS, meleeCnt / STEP_COUNT);
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
