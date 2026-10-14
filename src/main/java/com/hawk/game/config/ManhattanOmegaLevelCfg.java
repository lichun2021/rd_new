package com.hawk.game.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.hawk.serialize.string.SerializeHelper;

/** 欧米伽模块等级配置。 */
@HawkConfigManager.XmlResource(file = "xml/manhattan_omega_level.xml")
public class ManhattanOmegaLevelCfg extends HawkConfigBase {
	@Id
	protected final int id;
	protected final int posId;
	protected final int level;
	protected final int unlockBaseStage;
	protected final int skilllevel;
	protected final String consumption;
	protected final int power;
	protected final String attr;

	private Map<Integer, Integer> attrMap = new HashMap<>();
	private static final Table<Integer, Integer, ManhattanOmegaLevelCfg> CONFIGS = HashBasedTable.create();
	private static final Set<Integer> POS_IDS = new HashSet<>();

	public ManhattanOmegaLevelCfg() {
		id = 0;
		posId = 0;
		level = 0;
		unlockBaseStage = 0;
		skilllevel = 0;
		consumption = "";
		power = 0;
		attr = "";
	}

	@Override
	protected boolean assemble() {
		attrMap = SerializeHelper.stringToMap(attr, Integer.class, Integer.class, "_", ",");
		CONFIGS.put(posId, level, this);
		POS_IDS.add(posId);
		return true;
	}

	public static ManhattanOmegaLevelCfg getConfig(int posId, int level) {
		return CONFIGS.get(posId, level);
	}

	public static Set<Integer> getPosIds() {
		return Collections.unmodifiableSet(POS_IDS);
	}

	public int getId() { return id; }
	public int getPosId() { return posId; }
	public int getLevel() { return level; }
	public int getUnlockBaseStage() { return unlockBaseStage; }
	public int getSkillLevel() { return skilllevel; }
	public String getConsumption() { return consumption; }
	public int getPower() { return power; }
	public Map<Integer, Integer> getAttrMap() { return attrMap; }
}
