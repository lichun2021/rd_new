package com.hawk.game.battle.effect.impl.armour2201;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;

/**
 * 【2214】辅助
【万分比】战斗15（effect2214StarRound）回合开始，每5（effect2214AtkRound）回合增加自身防御和生命XX.XX%【2214】，每次效果叠加，持续至战斗结束
- 于战报中隐藏
- 不合并至精简战报中
- 在战斗开始前判定，满足条件后本次战斗全程生效
- 外围属性加法叠加
- 固定值：绝对值
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.EFF_2214)
public class Checker2214 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		

		int effPer = 0;
		int effNum = 0;
		if (isSoldier(parames.type)) {
			effPer = parames.unity.getEffVal(effType());
		}
		return new CheckerKVResult(effPer, effNum);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}

}
