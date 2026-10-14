package com.hawk.game.battle.effect.impl.hero2002;

import com.hawk.game.battle.BattleSoldier;
import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;
import com.hawk.game.protocol.Const.SoldierType;

/**
 * 【万分比】【12902】谭雅【战术指挥】：出征时，若自身出征兵种类型只有 1 种，自身所有单位攻击、防御、生命 +XX.XX%
 *
 * 依据 1.4.111 客户端文案 HeroSkillDesWithValue200201（占位符 {2} 即本作用号）：
 *   "战术指挥: 出征时，若自身出征兵种类型只有 1 种时，获得效果:
 *    自身所有单位攻击、防御、生命 +{2};
 *    集结时，自身兵种给予己方部队所有单位造成伤害增加 +{3}，受到伤害减少 +{4}（集结中每个兵种可提供最多2层，总叠加上限16层）"
 *
 * - 属于芯片属性（常驻），不是战技窗口效果：作用值来自 hero_skill 的 effectList（12902_0_0.5），
 *   与 12911（战技熟练度）走的是两条不同的取值路径
 * - 判定条件为"自身出征兵种类型只有 1 种"，按出征(编队)兵种构成统计，不区分兵种等级
 * - 自身所有单位：对己方全部兵种生效，与目标兵种无关（tarTypeSensitive = false）
 */
@BattleTupleType(tuple = { Type.ATK, Type.DEF, Type.HP })
@EffectChecker(effType = EffType.HERO_12902)
public class Checker12902 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		String playerId = parames.unity.getPlayerId();
		if (!isSoldier(parames.type) || !isSingleSoldierTypeMarch(parames, playerId)) {
			return CheckerKVResult.DefaultVal;
		}

		return new CheckerKVResult(parames.unity.getEffVal(effType()), 0);
	}

	/** 自身出征兵种类型是否只有 1 种（只统计真实出征部队，忽略等级差异） */
	private boolean isSingleSoldierTypeMarch(CheckerParames parames, String playerId) {
		int types = 0;
		for (SoldierType type : SoldierType.values()) {
			if (!BattleSoldier.isSoldier(type)) {
				continue;
			}
			Integer count = parames.unitStatic.getPlayerSoldierCountMarch().get(playerId, type);
			if (count != null && count > 0) {
				types++;
				if (types > 1) {
					return false;
				}
			}
		}
		return types == 1;
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}
}
