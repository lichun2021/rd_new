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
 * 【万分比】【12901】谭雅【猎杀指令】：战斗中，每第 5 回合开始时，谭雅对敌方特定部队下达猎杀指令，
 * 使自身出征数量最多的部队对该目标进行 1 次追加攻击（伤害率 {1}，集结战斗中该伤害翻倍）
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.HERO_12901)
public class Checker12901 implements IChecker {
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
