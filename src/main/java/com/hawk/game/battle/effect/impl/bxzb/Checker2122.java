package com.hawk.game.battle.effect.impl.bxzb;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;
import com.hawk.game.protocol.Const.SoldierType;

/**
 *   EFF_2100 = 2100; //	英雄协同星穹护盾	每个英雄，使星穹护盾值提升
	EFF_2111 = 2111; //	英雄协同主战攻击	每个英雄，使我的主战坦克攻击力提升
	EFF_2112 = 2112; //	英雄协同主战防御	每个英雄，使我的主战坦克防御力提升
	EFF_2113 = 2113; //	英雄协同主战生命	每个英雄，使我的主战坦克生命值提升
	EFF_2114 = 2114; //	英雄协同主战攻防血	每个英雄，使我的主战坦克攻防血提升
	EFF_2121 = 2121; //	英雄协同防御攻击	每个英雄，使我的防御坦克攻击力提升
	EFF_2122 = 2122; //	英雄协同防御防御	每个英雄，使我的防御坦克防御力提升
	EFF_2123 = 2123; //	英雄协同防御生命	每个英雄，使我的防御坦克生命值提升
	EFF_2124 = 2124; //	英雄协同防御攻防血	每个英雄，使我的防御坦克攻防血提升
	EFF_2131 = 2131; //	英雄协同直升攻击	每个英雄，使我的直升机攻击力提升
	EFF_2132 = 2132; //	英雄协同直升防御	每个英雄，使我的直升机防御力提升
	EFF_2133 = 2133; //	英雄协同直升生命	每个英雄，使我的直升机生命值提升
	EFF_2134 = 2134; //	英雄协同直升攻防血	每个英雄，使我的直升机攻防血提升
	EFF_2141 = 2141; //	英雄协同轰炸攻击	每个英雄，使我的轰炸机攻击力提升
	EFF_2142 = 2142; //	英雄协同轰炸防御	每个英雄，使我的轰炸机防御力提升
	EFF_2143 = 2143; //	英雄协同轰炸生命	每个英雄，使我的轰炸机生命值提升
	EFF_2144 = 2144; //	英雄协同轰炸攻防血	每个英雄，使我的轰炸机攻防血提升
	EFF_2151 = 2151; //	英雄协同狙击攻击	每个英雄，使我的狙击步兵攻击力提升
	EFF_2152 = 2152; //	英雄协同狙击防御	每个英雄，使我的狙击步兵防御力提升
	EFF_2153 = 2153; //	英雄协同狙击生命	每个英雄，使我的狙击步兵生命值提升
	EFF_2154 = 2154; //	英雄协同狙击攻防血	每个英雄，使我的狙击步兵攻防血提升
	EFF_2161 = 2161; //	英雄协同突击攻击	每个英雄，使我的突击步兵攻击力提升
	EFF_2162 = 2162; //	英雄协同突击防御	每个英雄，使我的突击步兵防御力提升
	EFF_2163 = 2163; //	英雄协同突击生命	每个英雄，使我的突击步兵生命值提升
	EFF_2164 = 2164; //	英雄协同突击攻防血	每个英雄，使我的突击步兵攻防血提升
	EFF_2171 = 2171; //	英雄协同采矿攻击	每个英雄，使我的采矿车攻击力提升
	EFF_2172 = 2172; //	英雄协同采矿防御	每个英雄，使我的采矿车防御力提升
	EFF_2173 = 2173; //	英雄协同采矿生命	每个英雄，使我的采矿车生命值提升
	EFF_2174 = 2174; //	英雄协同采矿攻防血	每个英雄，使我的采矿车攻防血提升
	EFF_2181 = 2181; //	英雄协同攻城攻击	每个英雄，使我的工程车攻击力提升
	EFF_2182 = 2182; //	英雄协同攻城防御	每个英雄，使我的工程车防御力提升
	EFF_2183 = 2183; //	英雄协同攻城生命	每个英雄，使我的工程车生命值提升
	EFF_2184 = 2184; //	英雄协同攻城攻防血	每个英雄，使我的工程车攻防血提升
 */
@BattleTupleType(tuple = Type.DEF)
@EffectChecker(effType = EffType.EFF_2122)
public class Checker2122 implements IChecker {

	@Override
	public CheckerKVResult value(CheckerParames parames) {

		if (parames.type != SoldierType.TANK_SOLDIER_1) {
			return CheckerKVResult.DefaultVal;
		}

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
