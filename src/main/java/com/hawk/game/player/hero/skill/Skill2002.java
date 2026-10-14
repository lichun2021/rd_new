package com.hawk.game.player.hero.skill;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.hawk.os.HawkException;

import com.hawk.game.protocol.Const.EffType;
import com.hawk.game.protocol.Hero.PBHeroEffect;
import com.hawk.game.util.EffectParams;

/**
 * 谭雅(2002) 专属战技【王牌特工】
 *
 * 客户端作用号说明（只有短标签，没有更长的触发条件文本）：
 *   12911 自身数量最多的部队受到攻击时，伤害减少
 *   12924 谭雅专属战技持续时间增加（魂技）
 *
 * 配置格式 proficiencyEffect = 12911_0.15_300
 *   作用号id_系数_持续时间(秒)，本作用值 = 英雄军事值 * 系数。
 *   全兵种取"出征数量最多的部队"，与作用号【12841】同一语义，
 *   区别是【12841】只作用于轰炸机(兵种3)，本作用号不限兵种。
 */
@HeroSkill(skillID = { 200201, 200202, 200203, 200204, 200205 })
public class Skill2002 extends ISSSHeroSkill {
	private int effect;
	private double effectCoefficient;
	private int durationSeconds;

	@Override
	public List<PBHeroEffect> effectVal() {
		try {
			// 12911_0.15_300
			String[] values = getCfg().getProficiencyEffect().split("_");
			effect = NumberUtils.toInt(values[0]);
			effectCoefficient = NumberUtils.toDouble(values[1]);
			durationSeconds = NumberUtils.toInt(values[2]);
		} catch (Exception exception) {
			HawkException.catchException(exception);
		}
		return super.effectVal();
	}

	@Override
	public int getShowProficiencyEffect() {
		return isEffecting() ? effect : 0;
	}

	@Override
	public int getProficiencyEffect(EffType effType, EffectParams effParams) {
		if (!isEffecting() || effType.getNumber() != effect) {
			return 0;
		}
		return (int) Math.ceil(effectCoefficient * getParent().getParent().attrVale(101));
	}

	@Override
	public int effectTime() {
		return durationSeconds * 1000 + getSoulEffVal(EffType.HERO_12924) * 1000;
	}
}
