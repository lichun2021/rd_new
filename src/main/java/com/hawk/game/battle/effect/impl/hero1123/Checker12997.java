package com.hawk.game.battle.effect.impl.hero1123;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;
import com.hawk.game.protocol.Const.SoldierType;

/**
 * 【万分比】【12997】雪拉：蜂群链路：自身直升飞机每次攻击后，可得到友军随机轰炸机的火力支援，
 * 对敌人造成伤害（伤害率 {7}）。一回合内，该技能最多可触发 2 次
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.HERO_12997)
public class Checker12997 implements IChecker {
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
