package com.hawk.game.battle.effect.impl.hero1125;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;

/**
 * - 【万分比】【13008】墨明战技【金属狂潮】持续期间，受到伤害减少 +XX.XX%
 *   - 于战报中展示，不合并至精简战报中
 *   - 属于英雄战技专属作用号，作用值与持续时间由战技熟练度提供（见 Skill1125）
 *   - 战技未处于持续期间时作用值为 0，取值器无需再判定窗口
 *   - 与其他伤害减少作用号累乘，即
 *     实际伤害 = 基础伤害 *（1 - 其他作用号）*（1 - 【本作用值】）
 */
@BattleTupleType(tuple = Type.REDUCE_HURT_PCT)
@EffectChecker(effType = EffType.HERO_12731)
public class Checker13008 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		return new CheckerKVResult(parames.unity.getEffVal(effType()), 0);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}
}
