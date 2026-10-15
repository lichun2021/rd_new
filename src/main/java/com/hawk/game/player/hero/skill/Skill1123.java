package com.hawk.game.player.hero.skill;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hawk.os.HawkException;

import com.hawk.game.protocol.Const.EffType;
import com.hawk.game.protocol.Hero.PBHeroEffect;
import com.hawk.game.util.EffectParams;

/**
 * 莱克莎(1123) 专属战技【相位突进】
 */
@HeroSkill(skillID = { 112301, 112302, 112303, 112304, 112305 })
public class Skill1123 extends ISSSHeroSkill {
	private int effect;
	private double effectCoefficient;
	private int durationSeconds;

	@Override
	public List<PBHeroEffect> effectVal() {
		try {
			String proficiencyEffect = getCfg().getProficiencyEffect();
			if (StringUtils.isNotEmpty(proficiencyEffect)) {
				String[] values = proficiencyEffect.replace("|", "_").split("_");
				effect = NumberUtils.toInt(values[0]);
				effectCoefficient = NumberUtils.toDouble(values[1]);
				durationSeconds = NumberUtils.toInt(values[2]);
			}
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
		return durationSeconds * 1000 + getSoulEffVal(EffType.HERO_12994) * 1000;
	}
}
