package com.hawk.game.battle.effect.impl.hero1125;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;

/**
 * - 【万分比】【13010】墨明军魂：战技持续期间，受到伤害减少 +XX.XX%
 *   - 于战报中隐藏，不合并至精简战报中
 *   - 作用值来自魂技 hero_soul_skill.xml 的 attr 字段（13010_600 ~ 13010_2000）
 *   - 生效窗口绑定墨明战技【金属狂潮】：作用号【13008】为 0 时战技不在持续期间，本作用号不生效
 *     （与【1120】的魂技【12851/12852/12853】绑定战技作用号【12831】的写法一致）
 *   - 客户端只有短标签"墨明军魂，受到伤害减少"，未写明生效窗口；
 *     参照【1122】魂技【12993】的短标签同样省略窗口、完整描述为"战技持续期间"，
 *     此处按"战技持续期间生效"实现，若策划另有说明需同步调整。
 */
@BattleTupleType(tuple = Type.REDUCE_HURT_PCT)
@EffectChecker(effType = EffType.HERO_12742)
public class Checker13010 implements IChecker {
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
