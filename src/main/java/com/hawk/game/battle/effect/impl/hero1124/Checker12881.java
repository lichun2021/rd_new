package com.hawk.game.battle.effect.impl.hero1124;

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
 * 【万分比】【12881】莱因：任命在战备参谋部时，集结战斗开始前，若集结全队出征坦克数量不低于集结部队总数 10%，
 * 己方全体近战部队攻击、防御、生命增加 【6.50% + {5}】 （多个莱因同时存在时，上述效果可叠加，至多 7 层）
 */
@BattleTupleType(tuple = { Type.ATK, Type.DEF, Type.HP })
@EffectChecker(effType = EffType.HERO_12881)
public class Checker12881 implements IChecker {
	private static final int BASE_VAL = 650;
	private static final int MAX_LAYERS = 7;

	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (!BattleConst.WarEff.MASS.check(parames.troopEffType) || !isJinzhan(parames.type)) {
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
		double tankCnt = parames.unitStatic.getArmyCountMapMarch().get(SoldierType.TANK_SOLDIER_1)
				+ parames.unitStatic.getArmyCountMapMarch().get(SoldierType.TANK_SOLDIER_2);
		if (tankCnt / total < 0.10D) {
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
