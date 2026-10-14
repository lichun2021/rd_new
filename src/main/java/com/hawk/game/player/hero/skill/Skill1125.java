package com.hawk.game.player.hero.skill;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.hawk.os.HawkException;

import com.hawk.game.protocol.Const.EffType;
import com.hawk.game.protocol.Hero.PBHeroEffect;
import com.hawk.game.util.EffectParams;

/**
 * 墨明(1125) 专属战技【金属狂潮】
 *
 * 客户端作用号说明（只有短标签，没有更长的触发条件文本）：
 *   13008 墨明战技，受到伤害减少
 *   13009 墨明战技，造成伤害增加
 *   13015 墨明战技持续时间增加（魂技）
 *
 * 配置格式 proficiencyEffect = 13008_0.153_300|13009_0.153_300
 *   作用号id_系数_持续时间(秒)，同一战技带两个作用号，系数与持续时间相同。
 *   本作用值 = 英雄军事值 * 系数，与作用号【12141】等英雄战技的算式一致。
 */
@HeroSkill(skillID = { 112501, 112502, 112503, 112504, 112505 })
public class Skill1125 extends ISSSHeroSkill {
	private int reduceEffect;
	private double reduceCoefficient;
	private int hurtEffect;
	private double hurtCoefficient;
	private int durationSeconds;

	@Override
	public List<PBHeroEffect> effectVal() {
		try {
			// 13008_0.153_300|13009_0.153_300
			String[] effects = getCfg().getProficiencyEffect().split("\\|");
			String[] reduce = effects[0].split("_");
			reduceEffect = NumberUtils.toInt(reduce[0]);
			reduceCoefficient = NumberUtils.toDouble(reduce[1]);
			durationSeconds = NumberUtils.toInt(reduce[2]);

			String[] hurt = effects[1].split("_");
			hurtEffect = NumberUtils.toInt(hurt[0]);
			hurtCoefficient = NumberUtils.toDouble(hurt[1]);
		} catch (Exception exception) {
			HawkException.catchException(exception);
		}
		return super.effectVal();
	}

	@Override
	public int getShowProficiencyEffect() {
		return isEffecting() ? reduceEffect : 0;
	}

	@Override
	public int getProficiencyEffect(EffType effType, EffectParams effParams) {
		if (!isEffecting()) {
			return 0;
		}

		if (effType.getNumber() == reduceEffect) {
			return (int) Math.ceil(reduceCoefficient * getParent().getParent().attrVale(101));
		}
		if (effType.getNumber() == hurtEffect) {
			return (int) Math.ceil(hurtCoefficient * getParent().getParent().attrVale(101));
		}

		return 0;
	}

	@Override
	public int effectTime() {
		return durationSeconds * 1000 + getSoulEffVal(EffType.HERO_12771) * 1000;
	}
}
