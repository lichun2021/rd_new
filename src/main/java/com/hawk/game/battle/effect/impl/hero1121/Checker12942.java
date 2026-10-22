package com.hawk.game.battle.effect.impl.hero1121;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.hawk.game.battle.BattleUnity;
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
 * 【万分比】【12942】艾伦：任命在战备参谋部时，集结战斗开始前，若集结全队出征远程部队数量不低于集结部队总数 40%，
 * 己方全体远程部队攻击、防御、生命增加 【6.50% + {6}】 （多个艾伦同时存在时，上述效果可叠加，至多 7 层）
 */
@BattleTupleType(tuple = { Type.ATK, Type.DEF, Type.HP })
@EffectChecker(effType = EffType.HERO_12942)
public class Checker12942 implements IChecker {
	private static final int BASE_VAL = 650;
	private static final int MAX_LAYERS = 7;

	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (!BattleConst.WarEff.MASS.check(parames.troopEffType) || !isYuanCheng(parames.type)) {
			return CheckerKVResult.DefaultVal;
		}

		Integer effPer;
		Object object = parames.getLeaderExtryParam(getSimpleName());
		if (Objects.nonNull(object)) {
			effPer = (Integer) object;
		} else {
			effPer = selectPlayer(parames);
			parames.putLeaderExtryParam(getSimpleName(), effPer);
		}
		return new CheckerKVResult(effPer, 0);
	}

	private int selectPlayer(CheckerParames parames) {
		double total = parames.unitStatic.getTotalCountMarch();
		if (total <= 0) {
			return 0;
		}
		double rangedCnt = parames.unitStatic.getArmyCountMapMarch().get(SoldierType.PLANE_SOLDIER_4)
				+ parames.unitStatic.getArmyCountMapMarch().get(SoldierType.FOOT_SOLDIER_5)
				+ parames.unitStatic.getArmyCountMapMarch().get(SoldierType.FOOT_SOLDIER_6)
				+ parames.unitStatic.getArmyCountMapMarch().get(SoldierType.CANNON_SOLDIER_7);
		if (rangedCnt / total < 0.40D) {
			return 0;
		}

		Map<String, Integer> valMap = new HashMap<>();
		for (BattleUnity unity : parames.unityList) {
			if (valMap.containsKey(unity.getPlayerName())) {
				continue;
			}
			int effVal = unity.getEffVal(effType());
			if (effVal > 0) {
				valMap.put(unity.getPlayerName(), BASE_VAL + effVal);
			}
		}

		return valMap.entrySet().stream()
				.sorted((item1, item2) -> item2.getValue().compareTo(item1.getValue()))
				.filter(ent -> ent.getValue() > 0)
				.limit(MAX_LAYERS)
				.mapToInt(item -> item.getValue().intValue())
				.sum();
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}
}
