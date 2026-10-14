package com.hawk.game.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;

import com.hawk.serialize.string.SerializeHelper;

/**
 * 超武技能配置
 * @author lating
 *
 */
@HawkConfigManager.XmlResource(file = "xml/manhattan_sw_skill.xml")
public class ManhattanSWSkillCfg extends HawkConfigBase {

	/**
	 * 序列id
	 */
	@Id
	protected final int id;

	/**
	 * 对应超武
	 */
	protected final int swId;
	
	/**
	 * 技能等级
	 */
	protected final int level;
	/**
	 * 解锁品阶
	 */
	protected final int unlockStage;
	/** 欧米伽总技能，解锁后改用 unlockOmegaStage 判定。 */
	protected final int unlockOmega;
	protected final int unlockOmegaStage;

	/**
	 * 技能属性
	 */
	protected final String attr;
	/**
	 * 属性map
	 */
	private Map<Integer, Integer> attrMap = new HashMap<>();
	
	/**
	 * 每一阶对应的配置<swId*1000+stage, config>
	 */
	private static Map<Long, ManhattanSWSkillCfg> stageCfgMap = new HashMap<>();
	private static Map<Integer, List<ManhattanSWSkillCfg>> swSkillMap = new HashMap<>();
	
	
	public ManhattanSWSkillCfg() {
		id = 0;
		swId = 0;
		level = 0;
		unlockStage = 0;
		unlockOmega = 0;
		unlockOmegaStage = 0;
		attr = "";
	}
	
	public int getId() {
		return id;
	}

	public int getSwId() {
		return swId;
	}


	public int getLevel() {
		return level;
	}
	
	public int getUnlockStage() {
		return unlockStage;
	}

	public int getUnlockOmega() {
		return unlockOmega;
	}

	public int getUnlockOmegaStage() {
		return unlockOmegaStage;
	}

	public String getAttr() {
		return attr;
	}

	@Override
	protected boolean assemble() {
		attrMap = SerializeHelper.stringToMap(attr, Integer.class, Integer.class, "_", ",");
		long key = getMapKey(swId, unlockStage);
		stageCfgMap.put(key, this);
		List<ManhattanSWSkillCfg> skills = swSkillMap.computeIfAbsent(swId, value -> new ArrayList<>());
		skills.removeIf(e -> e.getId() == this.id);
		skills.add(this);
		skills.sort(Comparator.comparingInt(ManhattanSWSkillCfg::getLevel));
		return true;
	}
	
	public Map<Integer, Integer> getAttrMap() {
		return attrMap;
	} 
	
	private static long getMapKey(int swId, int stage) {
		return swId * 1000L + stage;
	}
	
	public static ManhattanSWSkillCfg getConfig(int swId, int stage) {
		return getConfig(swId, stage, false);
	}

	public static ManhattanSWSkillCfg getConfig(int swId, int stage, boolean omegaActive) {
		if (omegaActive) {
			ManhattanSWSkillCfg result = null;
			for (ManhattanSWSkillCfg cfg : swSkillMap.getOrDefault(swId, Collections.emptyList())) {
				if (stage >= cfg.getUnlockOmegaStage()) {
					result = cfg;
				}
			}
			return result;
		}
		while (stage > 0) {
			long key = getMapKey(swId, stage);
			ManhattanSWSkillCfg cfg = stageCfgMap.get(key);
			if (cfg != null) {
				return cfg;
			}
			stage--;
		}
		
		return null;
	}
	
}
