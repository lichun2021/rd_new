package com.hawk.game.battle.effect.impl.hero2002;

import java.util.Optional;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.player.hero.PlayerHero;
import com.hawk.game.protocol.Const.EffType;

/**
 * 【万分比】【12906】谭雅【意志强化】：该效果拥有荣耀谭雅即生效，无需出征或驻防。
 * 战斗开始时，自身部队获得意志强化使自身所有部队攻击、防御、生命 +{6}，该效果每回合衰减，第10回合结束后效果消失。
 */
@BattleTupleType(tuple = { Type.ATK, Type.DEF, Type.HP })
@EffectChecker(effType = EffType.HERO_12906)
public class Checker12906 implements IChecker {
	private static final int HERO_ID = 2002;
	private static final int MAX_ROUND = 10;

	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (!isSoldier(parames.type)) {
			return CheckerKVResult.DefaultVal;
		}
		int val = resolveEffVal(parames, effType());
		if (val <= 0) {
			return CheckerKVResult.DefaultVal;
		}
		int round = resolveBattleRound(parames);
		if (round > MAX_ROUND) {
			return CheckerKVResult.DefaultVal;
		}
		int decayed = (int) (val * (MAX_ROUND + 1L - round) / MAX_ROUND);
		return new CheckerKVResult(decayed, 0);
	}

	public static int resolveEffVal(CheckerParames parames, EffType effType) {
		int val = parames.unity.getEffVal(effType);
		if (val <= 0 && parames.unity.getPlayer() != null) {
			Optional<PlayerHero> heroOp = parames.unity.getPlayer().getHeroByCfgId(HERO_ID);
			if (heroOp.isPresent()) {
				val = heroOp.get().battleEffectMap().getOrDefault(effType, 0);
			}
		}
		return val;
	}

	public static int resolveBattleRound(CheckerParames parames) {
		if (parames.solider != null && parames.solider.getTroop() != null && parames.solider.getTroop().getBattle() != null) {
			int r = parames.solider.getBattleRound();
			return r > 0 ? r : 1;
		}
		return 1;
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}
}
