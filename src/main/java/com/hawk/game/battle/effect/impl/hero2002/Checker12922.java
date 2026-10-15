package com.hawk.game.battle.effect.impl.hero2002;

import com.hawk.game.battle.BattleSoldier;
import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;
import com.hawk.game.protocol.Const.SoldierType;

/**
 * 【万分比】【12922】谭雅军魂【特工小队】：触发战术指挥时，自身所有单位攻击、防御、生命额外增加
 */
@BattleTupleType(tuple = { Type.ATK, Type.DEF, Type.HP })
@EffectChecker(effType = EffType.HERO_12922)
public class Checker12922 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		String playerId = parames.unity.getPlayerId();
		if (!isSoldier(parames.type) || !isSingleSoldierTypeMarch(parames, playerId)) {
			return CheckerKVResult.DefaultVal;
		}
		return new CheckerKVResult(parames.unity.getEffVal(effType()), 0);
	}

	private boolean isSingleSoldierTypeMarch(CheckerParames parames, String playerId) {
		int types = 0;
		for (SoldierType type : SoldierType.values()) {
			if (!BattleSoldier.isSoldier(type)) {
				continue;
			}
			Integer count = parames.unitStatic.getPlayerSoldierCountMarch().get(playerId, type);
			if (count != null && count > 0) {
				types++;
				if (types > 1) {
					return false;
				}
			}
		}
		return types == 1;
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}
}
