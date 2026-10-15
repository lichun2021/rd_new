package com.hawk.game.battle.effect.impl.hero1125;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;
import com.hawk.game.protocol.Const.SoldierType;

/**
 * 【万分比】【13005】墨明【金属狂潮·作战计划】：主战坦克伤害增加
 */
@BattleTupleType(tuple = Type.HURT_PCT)
@EffectChecker(effType = EffType.HERO_12728)
public class Checker13005 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (parames.type != SoldierType.TANK_SOLDIER_2) {
			return CheckerKVResult.DefaultVal;
		}
		return new CheckerKVResult(parames.unity.getEffVal(effType()), 0);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}
}
