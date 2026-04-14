package com.hawk.game.battle.effect.impl.bxzb;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;

/**
 *   2101	英雄协同攻击	每个英雄，使自身攻击提升，最多生效20个英雄
2102	英雄协同防御	每个英雄，使自身防御提升，最多生效20个英雄
2103	英雄协同生命	每个英雄，使自身生命提升，最多生效20个英雄
2104	英雄协同攻防血	每个英雄，使自身攻防血提升，最多生效20个英雄
2105	己方全体攻防血	己方全体攻防血提升
2106	敌方全体攻防血降低	敌方全体攻防血降低
2107	己方单数回合攻防血	己方单数回合攻防血提升
2108	己方双数回合攻防血	己方双数回合攻防血提升
 */
@BattleTupleType(tuple = { Type.HP, Type.ATK, Type.DEF })
@EffectChecker(effType = EffType.EFF_2104)
public class Checker2104 implements IChecker {

	@Override
	public CheckerKVResult value(CheckerParames parames) {

		int effPer = 0;
		int effNum = 0;
		if (isSoldier(parames.type)) {
			effPer = parames.unity.getEffVal(effType()) * Math.min(20, parames.unitStatic.getPlayerHeromap().values().size());
		}

		return new CheckerKVResult(effPer, effNum);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}

}
