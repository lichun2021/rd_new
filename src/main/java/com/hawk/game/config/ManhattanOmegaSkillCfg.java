package com.hawk.game.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;

import com.hawk.serialize.string.SerializeHelper;

/** 欧米伽模块技能配置。 */
@HawkConfigManager.XmlResource(file = "xml/manhattan_omega_skill.xml")
public class ManhattanOmegaSkillCfg extends HawkConfigBase {
	@Id
	protected final int id;
	protected final int posId;
	protected final int level;
	protected final int unlockLevel;
	protected final int power;
	protected final String attr;

	private Map<Integer, Integer> attrMap = new HashMap<>();
	private static final Map<Integer, List<ManhattanOmegaSkillCfg>> POS_SKILLS = new HashMap<>();

	public ManhattanOmegaSkillCfg() {
		id = 0;
		posId = 0;
		level = 0;
		unlockLevel = 0;
		power = 0;
		attr = "";
	}

	@Override
	protected boolean assemble() {
		attrMap = SerializeHelper.stringToMap(attr, Integer.class, Integer.class, "_", ",");
		List<ManhattanOmegaSkillCfg> skills = POS_SKILLS.computeIfAbsent(posId, key -> new ArrayList<>());
		skills.removeIf(e -> e.getId() == this.id);
		skills.add(this);
		return true;
	}

	public static List<ManhattanOmegaSkillCfg> getSkills(int posId) {
		return POS_SKILLS.containsKey(posId)
				? Collections.unmodifiableList(POS_SKILLS.get(posId)) : Collections.emptyList();
	}

	public int getId() { return id; }
	public int getPosId() { return posId; }
	public int getLevel() { return level; }
	public int getUnlockLevel() { return unlockLevel; }
	public int getPower() { return power; }
	public Map<Integer, Integer> getAttrMap() { return attrMap; }
}
