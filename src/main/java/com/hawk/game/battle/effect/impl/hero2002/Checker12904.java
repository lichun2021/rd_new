package com.hawk.game.battle.effect.impl.hero2002;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.hawk.game.battle.BattleSoldier;
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
 * 【万分比】【12904】谭雅【战术指挥】：出征时，若自身出征兵种类型只有 1 种时，
 * 集结时，自身兵种给予己方部队所有单位受到伤害减少 +{4}（集结中每个兵种可提供最多2层，总叠加上限16层）
 */
@BattleTupleType(tuple = Type.REDUCE_HURT_PCT)
@EffectChecker(effType = EffType.HERO_12904)
public class Checker12904 implements IChecker {
	private static final int MAX_PER_SOLDIER_TYPE = 2;
	private static final int MAX_TOTAL_LAYERS = 16;

	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (!BattleConst.WarEff.MASS.check(parames.troopEffType) || !isSoldier(parames.type)) {
			return CheckerKVResult.DefaultVal;
		}

		Integer effPer;
		Object object = parames.getLeaderExtryParam(getSimpleName());
		if (Objects.nonNull(object)) {
			effPer = (Integer) object;
		} else {
			effPer = calcMassAuraVal(parames);
			parames.putLeaderExtryParam(getSimpleName(), effPer);
		}
		return new CheckerKVResult(effPer, 0);
	}

	private int calcMassAuraVal(CheckerParames parames) {
		Map<String, BattleUnity> playerUnityMap = new HashMap<>();
		for (BattleUnity unity : parames.unityList) {
			if (!playerUnityMap.containsKey(unity.getPlayerId()) && unity.getEffVal(effType()) > 0) {
				playerUnityMap.put(unity.getPlayerId(), unity);
			}
		}

		Map<SoldierType, List<Integer>> byType = new EnumMap<>(SoldierType.class);
		for (Map.Entry<String, BattleUnity> entry : playerUnityMap.entrySet()) {
			String playerId = entry.getKey();
			SoldierType singleType = getSingleMarchSoldierType(parames, playerId);
			if (singleType != null) {
				byType.computeIfAbsent(singleType, k -> new ArrayList<>()).add(entry.getValue().getEffVal(effType()));
			}
		}

		List<Integer> selected = new ArrayList<>();
		for (List<Integer> vals : byType.values()) {
			vals.sort(Comparator.reverseOrder());
			for (int i = 0; i < Math.min(MAX_PER_SOLDIER_TYPE, vals.size()); i++) {
				selected.add(vals.get(i));
			}
		}

		return selected.stream()
				.sorted(Comparator.reverseOrder())
				.limit(MAX_TOTAL_LAYERS)
				.mapToInt(Integer::intValue)
				.sum();
	}

	private SoldierType getSingleMarchSoldierType(CheckerParames parames, String playerId) {
		SoldierType found = null;
		for (SoldierType type : SoldierType.values()) {
			if (!BattleSoldier.isSoldier(type)) {
				continue;
			}
			Integer count = parames.unitStatic.getPlayerSoldierCountMarch().get(playerId, type);
			if (count != null && count > 0) {
				if (found != null) {
					return null;
				}
				found = type;
			}
		}
		return found;
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}
}
