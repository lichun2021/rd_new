package com.hawk.game.battle.effect.impl.hero2002;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;

/**
 * 【万分比】【12905】谭雅【功勋授予】：出征时，自身部队获得自身战力加成，全兵种攻击、防御、生命 +XX.XX%
 *
 * 依据 1.4.111 客户端文案 HeroSkillDesWithValue200201（占位符 {5} 即本作用号）：
 *   "功勋授予: 谭雅会为每一名不断突破自身极限的指挥官授予功勋，根据其成长的高度给予全军相应的战力加成，
 *    出征时，自身部队获得自身战力加，全兵种攻击、防御、生命 +{5} *自身战力/1,000,000（战力生效上限为200,000）"
 *
 * - 属于芯片属性（常驻），作用值来自 hero_skill 的 effectList（12905_0_0.5）
 * - 生效值 = 配置值 * min(自身战力, 200000) / 1000000
 *   - 战力上限 200,000 为文案明确写出的数值，客户端 const 表未提供字段，故在此以常量表示
 * - 全兵种生效，与目标兵种无关（tarTypeSensitive = false），战力按玩家取值后缓存
 */
@BattleTupleType(tuple = { Type.ATK, Type.DEF, Type.HP })
@EffectChecker(effType = EffType.HERO_12905)
public class Checker12905 implements IChecker {
	/** 战力生效上限，来源：客户端文案"战力生效上限为200,000" */
	private static final long STRENGTH_LIMIT = 200_000L;

	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (!isSoldier(parames.type)) {
			return CheckerKVResult.DefaultVal;
		}

		int effectVal = parames.unity.getEffVal(effType());
		if (effectVal <= 0) {
			return CheckerKVResult.DefaultVal;
		}

		long strength = Math.min(parames.unity.getPlayer().getStrength(), STRENGTH_LIMIT);
		return new CheckerKVResult((int) (effectVal * strength / 1_000_000L), 0);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}
}
