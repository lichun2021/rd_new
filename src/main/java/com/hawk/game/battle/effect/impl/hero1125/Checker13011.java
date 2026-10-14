package com.hawk.game.battle.effect.impl.hero1125;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;

/**
 * - 【万分比】【13011】墨明军魂：战技持续期间，造成伤害增加 +XX.XX%
 *   - 于战报中隐藏，不合并至精简战报中
 *   - 作用值来自魂技 hero_soul_skill.xml 的 attr 字段（13011_600 ~ 13011_2000）
 *   - 生效窗口绑定墨明战技【金属狂潮】：作用号【13008】为 0 时战技不在持续期间，本作用号不生效
 *   - 客户端只有短标签"墨明军魂，造成伤害增加"，未写明生效窗口，处理依据同 Checker13010
 *   - 与其他伤害加成累乘，即
 *     实际伤害 = 基础伤害 *（1 + 其他伤害加成）*（1 + 本作用值/10000）
 */
@BattleTupleType(tuple = Type.HURT_PCT)
@EffectChecker(effType = EffType.HERO_12743)
public class Checker13011 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (parames.unity.getEffVal(EffType.HERO_12731) == 0) {
			return CheckerKVResult.DefaultVal;
		}

		return new CheckerKVResult(parames.unity.getEffVal(effType()), 0);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}
}
