package com.hawk.game.battle.effect.impl.hero1120;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;
/**
 * - 【万分比】【12833】
  - 战报相关
    - 于战报中隐藏
    - 不合并至精简战报中
  - 在战斗开始前判定，满足条件后本场战斗全程生效
  - 本技能为尤利娅作用号【12411】额外数值增加【241024】【SSS】【军事】【轰炸】【尤利娅】【Yulia】【1104】
    - 即 【12411】实际值=【12411】+【12833】
    - 注：需要自身和尤利娅一同出征才有加成
 * @author lwt
 * @date 2026年2月3日
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL2)
@EffectChecker(effType = EffType.HERO_12833)
public class Checker12833 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (parames.solider.getEffVal(EffType.HERO_12831) == 0) {
			return CheckerKVResult.DefaultVal;
		}

		return new CheckerKVResult(parames.unity.getEffVal(effType()), 0);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}

}
