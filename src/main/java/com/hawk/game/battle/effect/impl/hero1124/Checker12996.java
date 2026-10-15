package com.hawk.game.battle.effect.impl.hero1124;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;
import com.hawk.game.protocol.Const.SoldierType;

/**
 * 【万分比】【12996】雪拉：低空飞行：自身直升飞机总是能够找到敌方雷达死角，让对手难以捕获。
 * 被选作攻击目标的几率降低，所受伤害降低 {6}
 */
@BattleTupleType(tuple = Type.REDUCE_HURT_PCT)
@EffectChecker(effType = EffType.HERO_12996)
public class Checker12996 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (parames.type != SoldierType.PLANE_SOLDIER_4) {
			return CheckerKVResult.DefaultVal;
		}
		return new CheckerKVResult(parames.unity.getEffVal(effType()), 0);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}
}
