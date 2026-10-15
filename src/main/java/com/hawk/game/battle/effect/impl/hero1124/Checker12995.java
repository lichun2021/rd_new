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
 * 【万分比】【12995】雪拉：集结增益：集结战斗开始前，若集结队伍中的空军数量占比达到 10%，
 * 己方全体后排部队攻击、防御、生命提升 【6.50% + {5}】（多个雪拉同时存在时，上述效果可叠加，至多 7 层）
 */
@BattleTupleType(tuple = { Type.ATK, Type.DEF, Type.HP })
@EffectChecker(effType = EffType.HERO_12995)
public class Checker12995 implements IChecker {
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
		double planeCnt = parames.unitStatic.getArmyCountMapMarch().get(SoldierType.PLANE_SOLDIER_3)
				+ parames.unitStatic.getArmyCountMapMarch().get(SoldierType.PLANE_SOLDIER_4);
		if (planeCnt / total < 0.10D) {
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
