package com.hawk.game.battle.effect.impl.hero2002;

import com.hawk.game.battle.BattleUnity;
import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;

/**
 * - 【万分比】【12911】战技持续期间，自身数量最多的部队受到攻击时，伤害减少 +XX.XX%
 *   - 于战报中展示，不合并至精简战报中
 *   - 在战斗开始前判定，满足条件后本次战斗全程生效
 *   - 自身出征数量最多的部队：真实出征部队数量，战斗开始前判定
 *     （参谋军威、所罗门、吉迪恩 这类战斗中改变部队数量的机制对此无影响）
 *   - 不限兵种，取玩家所有兵种中出征数量最多的那一支
 *     （作用号【12841】是同一语义但只作用于轰炸机）
 *   - 该作用号为伤害减少效果，与其他作用号累乘计算，即
 *     实际伤害 = 基础伤害 *（1 - 其他作用号）*（1 - 【本作用值】）
 *   - 作用值取英雄战技熟练度，见 Skill2002
 */
@BattleTupleType(tuple = Type.REDUCE_HURT_PCT)
@EffectChecker(effType = EffType.HERO_12911)
public class Checker12911 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		String playerId = parames.unity.getPlayerId();
		BattleUnity maxArmy = parames.getPlayeMaxMarchArmy(playerId);
		if (maxArmy == null || parames.unity != maxArmy) {
			return CheckerKVResult.DefaultVal;
		}

		return new CheckerKVResult(parames.unity.getEffVal(effType()), 0);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}
}
