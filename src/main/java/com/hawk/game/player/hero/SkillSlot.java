package com.hawk.game.player.hero;

import java.util.Arrays;
import java.util.Objects;

import com.alibaba.fastjson.JSONArray;
import com.hawk.game.config.HeroSkillCfg;
import com.hawk.game.player.hero.skill.HeroSkillFactory;
import com.hawk.game.player.hero.skill.IHeroSkill;
import com.hawk.game.protocol.Hero.PBSkillSlot;

/**
 * 
 * @author lwt
 * @date 2017年10月23日
 */
public class SkillSlot implements SerializJsonStrAble {
	private PlayerHero parent;
	private int level; // 解锁等级
	private int index;
	private IHeroSkill skill;
	// Storage-only backup of a retired fixed skill; never sent as an active skill.
	private String legacySkillSerialized;

	public SkillSlot(PlayerHero parent) {
		this.parent = parent;
	}

	/**
	 * 技能已解锁
	 * 
	 * @return
	 */
	public final boolean isUnLock() {
		return parent.getLevel() >= level;
	}

	public double power() {
		if (Objects.isNull(this.skill)) {
			return 0;
		}
		if (!isUnLock()) {
			return 0;
		}

		HeroSkillCfg skillCfg = this.getSkill().getCfg();
		return skillCfg.getPowerCoe().first + skillCfg.getPowerCoe().second * this.getSkill().getLevel();
	}

	@Override
	public String serializ() {
		Object[] arr = new Object[legacySkillSerialized == null ? 5 : 6];
		arr[0] = level;
		arr[1] = index;

		if (Objects.isNull(skill)) {
			arr[2] = false;
		} else {
			arr[2] = true;
			arr[3] = skill.skillID();
			arr[4] = skill.serializ();
		}

		if (legacySkillSerialized != null) {
			arr[5] = legacySkillSerialized;
		}
		JSONArray array = new JSONArray(Arrays.asList(arr));
		return array.toJSONString();
	}

	@Override
	public void mergeFrom(String serialiedStr) {
		JSONArray array = JSONArray.parseArray(serialiedStr);
		level = array.getIntValue(0);
		index = array.getIntValue(1);
		legacySkillSerialized = array.size() > 5 ? array.getString(5) : null;
		skill = null;
		if (array.getBooleanValue(2)) {
			skill = HeroSkillFactory.getInstance().createEmptySkill(array.getIntValue(3));
			skill.setParent(this);
			skill.mergeFrom(array.getString(4));
		}
	}

	public PBSkillSlot toPBObj() {
		PBSkillSlot.Builder builder = PBSkillSlot.newBuilder();
		builder.setIndex(index).setUnlockLevel(level);
		if (Objects.nonNull(skill)) {
			builder.setSkill(skill.toPBobj());
		}
		return builder.build();
	}

	public PlayerHero getParent() {
		return parent;
	}

	public void setParent(PlayerHero parent) {
		this.parent = parent;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public IHeroSkill getSkill() {
		return skill;
	}

	public void setSkill(IHeroSkill skill) {
		this.skill = skill;
		if (Objects.nonNull(skill)) {
			skill.setParent(this);
		}
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
