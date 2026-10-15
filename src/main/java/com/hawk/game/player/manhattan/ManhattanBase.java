package com.hawk.game.player.manhattan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import com.hawk.game.config.ManhattanBaseLevelCfg;
import com.hawk.game.config.ManhattanBaseStageCfg;
import com.hawk.game.config.ManhattanOmegaLevelCfg;
import com.hawk.game.config.ManhattanOmegaSkillCfg;
import com.hawk.game.entity.ManhattanEntity;
import com.hawk.game.protocol.Manhattan.ManhattanPosInfo;
import com.hawk.game.protocol.Manhattan.PBManhattanInfo;
import com.hawk.game.protocol.World.PresetMarchManhattan;
import com.hawk.log.LogConst.PowerChangeReason;
import com.hawk.serialize.string.SerializeHelper;

/**
 * 超武底座
 * @author lating
 */
public class ManhattanBase extends PlayerManhattan {
	private static final Map<String, ManhattanBase> BASE_BY_PLAYER = new ConcurrentHashMap<>();
	private final Map<Integer, Integer> omegaPosLevelMap = new ConcurrentHashMap<>();

	public static ManhattanBase getBaseByPlayerId(String playerId) {
		return playerId == null ? null : BASE_BY_PLAYER.get(playerId);
	}

	@Override
	public void init(ManhattanEntity entity) {
		omegaPosLevelMap.putAll(SerializeHelper.stringToMap(
				entity.getOmegaPosLevel(), Integer.class, Integer.class));
		if (entity.getPlayerId() != null) {
			BASE_BY_PLAYER.put(entity.getPlayerId(), this);
		}
		super.init(entity);
	}
	
	public static ManhattanBase create(ManhattanEntity entity) {
		ManhattanBase manhattan = new ManhattanBase(); 
		manhattan.init(entity);
		entity.recordManhattanObj(manhattan);
		return manhattan;
	}
	
	/**
	 * 等级提升
	 * @param posId
	 * @return
	 */
	public void levelUpgrade(int posId) {
		int oldLevel = this.getPosLevel(posId);
		int newLevel = oldLevel + 1;
		ManhattanBaseLevelCfg config = this.getPosLevelCfg(posId, newLevel);
		if (config == null) {
			throw new RuntimeException("wrong invoke");
		}
		updatePosLevel(posId, newLevel);
	}

	/** 提升一个欧米伽模块等级。调用前须完成配置、前置与消耗检查。 */
	public void omegaLevelUpgrade(int posId) {
		int newLevel = getOmegaPosLevel(posId) + 1;
		if (ManhattanOmegaLevelCfg.getConfig(posId, newLevel) == null) {
			throw new IllegalStateException("invalid omega level upgrade");
		}
		omegaPosLevelMap.put(posId, newLevel);
		getEntity().notifyUpdate();
	}

	public int getOmegaPosLevel(int posId) {
		return omegaPosLevelMap.getOrDefault(posId, 0);
	}

	public String serializeOmegaPosLevel() {
		return SerializeHelper.mapToString(omegaPosLevelMap);
	}

	public boolean isOmegaUnlocked() {
		ManhattanOmegaLevelCfg config = ManhattanOmegaLevelCfg.getConfig(1, 0);
		return config != null && getStage() >= config.getUnlockBaseStage();
	}

	/** 三个模块都达到150级后激活总技能901。 */
	public boolean isOmegaTotalSkillActive() {
		for (int posId : ManhattanOmegaLevelCfg.getPosIds()) {
			if (getOmegaPosLevel(posId) < 150) {
				return false;
			}
		}
		return ManhattanOmegaLevelCfg.getPosIds().size() == 3;
	}
	
	/**
	 * 品阶提升
	 * @return
	 */
	public void stageUpgrade() {
		int newStage = this.getStage() + 1;
		ManhattanBaseStageCfg config = ManhattanBaseStageCfg.getConfigByStage(newStage);
		if (config == null) {
			throw new RuntimeException("wrong invoke");
		}
		updateStage(newStage);
	}
	
	public int getType() {
		return 0;
	}
	
	public int getPower() {
		int power = 0;
		ManhattanBaseStageCfg stageCfg = this.getStageCfg();
		if (stageCfg != null) {
			power += stageCfg.getPower();
		}
		for(Entry<Integer, Integer> entry : getPosLevelMap().entrySet()) {
			ManhattanBaseLevelCfg cfg = this.getPosLevelCfg(entry.getKey(), entry.getValue());
			if (cfg != null) {
				power += cfg.getPower();
			}
		}
		for (Entry<Integer, Integer> entry : omegaPosLevelMap.entrySet()) {
			int posId = entry.getKey();
			int omegaLevel = entry.getValue();
			ManhattanOmegaLevelCfg cfg = ManhattanOmegaLevelCfg.getConfig(posId, omegaLevel);
			if (cfg != null) {
				power += cfg.getPower();
			}
			ManhattanOmegaSkillCfg unlockedSkill = null;
			for (ManhattanOmegaSkillCfg skillCfg : ManhattanOmegaSkillCfg.getSkills(posId)) {
				if (omegaLevel >= skillCfg.getUnlockLevel()
						&& (unlockedSkill == null || skillCfg.getLevel() > unlockedSkill.getLevel())) {
					unlockedSkill = skillCfg;
				}
			}
			if (unlockedSkill != null) {
				power += unlockedSkill.getPower();
			}
		}
		return power;
	}
	
	public ManhattanBaseStageCfg getStageCfg() {
		return ManhattanBaseStageCfg.getConfigByStage(getStage());
	}
	
	public ManhattanBaseLevelCfg getPosLevelCfg(int posId, int level) {
		return ManhattanBaseLevelCfg.getConfig(posId, level);
	}
	
	/**
	 * 通知变化
	 */
	public void refreshPower() {
		getParent().refreshPowerElectric(PowerChangeReason.MANHATTAN_BASE);
	}
	
	protected Map<Integer, Integer> loadEffValMap(boolean loadSkill){
		return loadEffValMap();
	}
	
	public Map<Integer, Integer> loadEffValMap() {
		Map<Integer, Integer> map = new HashMap<>();
		ManhattanBaseStageCfg stageCfg = this.getStageCfg();
		if (stageCfg != null) {
			map.putAll(stageCfg.getAttrMap());
		}
		for(Entry<Integer, Integer> entry : getPosLevelMap().entrySet()) {
			ManhattanBaseLevelCfg cfg = this.getPosLevelCfg(entry.getKey(), entry.getValue());
			if (cfg == null) {
				continue;
			}
			for(Entry<Integer, Integer> attrEntry : cfg.getAttrMap().entrySet()) {
				map.merge(attrEntry.getKey(), attrEntry.getValue(), (v1, v2) -> v1 + v2);
			}
		}
		for (int posId : ManhattanOmegaLevelCfg.getPosIds()) {
			int omegaLevel = getOmegaPosLevel(posId);
			ManhattanOmegaLevelCfg levelCfg = ManhattanOmegaLevelCfg.getConfig(posId, omegaLevel);
			if (levelCfg != null) {
				mergeAttrs(map, levelCfg.getAttrMap());
			}
			ManhattanOmegaSkillCfg unlockedSkill = null;
			for (ManhattanOmegaSkillCfg skillCfg : ManhattanOmegaSkillCfg.getSkills(posId)) {
				if (omegaLevel >= skillCfg.getUnlockLevel()
						&& (unlockedSkill == null || skillCfg.getLevel() > unlockedSkill.getLevel())) {
					unlockedSkill = skillCfg;
				}
			}
			if (unlockedSkill != null) {
				mergeAttrs(map, unlockedSkill.getAttrMap());
			}
		}
		return map;
	}

	private void mergeAttrs(Map<Integer, Integer> target, Map<Integer, Integer> attrs) {
		for (Entry<Integer, Integer> entry : attrs.entrySet()) {
			target.merge(entry.getKey(), entry.getValue(), Integer::sum);
		}
	}

	@Override
	public boolean isBase() {
		return true;
	}
	
	/**
	 * 构造超武builder
	 * @param builder
	 */
	@Override
	public void buildManhattanInfo(PBManhattanInfo.Builder builder, PresetMarchManhattan presetMarchManhattan) {
		builder.setBaseStage(getStage());
		List<ManhattanPosInfo> list = new ArrayList<>();
		Set<Integer> posSet = new HashSet<>();
		for (Entry<Integer, Integer> entry : this.getPosLevelMap().entrySet()) {
			ManhattanPosInfo.Builder posInfo = ManhattanPosInfo.newBuilder();
			ManhattanBaseLevelCfg cfg = this.getPosLevelCfg(entry.getKey(), entry.getValue());
			posInfo.setCfgId(cfg == null ? 0 : cfg.getId());
			posInfo.setPosId(entry.getKey());
			posInfo.setLevel(entry.getValue());
			list.add(posInfo.build());
			posSet.add(entry.getKey());
		}

		for (int posId : ManhattanBaseLevelCfg.getPosSet()) {
			if (posSet.contains(posId)) {
				continue;
			}
			ManhattanPosInfo.Builder posInfo = ManhattanPosInfo.newBuilder();
			ManhattanBaseLevelCfg cfg = this.getPosLevelCfg(posId, 0);
			posInfo.setCfgId(cfg == null ? 0 : cfg.getId());
			posInfo.setPosId(posId);
			posInfo.setLevel(0);
			list.add(posInfo.build());
		}
		builder.addAllBasePosLevels(list);

		List<ManhattanPosInfo> omegaList = new ArrayList<>();
		for (int posId : ManhattanOmegaLevelCfg.getPosIds()) {
			int level = getOmegaPosLevel(posId);
			ManhattanOmegaLevelCfg cfg = ManhattanOmegaLevelCfg.getConfig(posId, level);
			ManhattanPosInfo.Builder posInfo = ManhattanPosInfo.newBuilder();
			posInfo.setCfgId(cfg == null ? 0 : cfg.getId());
			posInfo.setPosId(posId);
			posInfo.setLevel(level);
			omegaList.add(posInfo.build());
		}
		builder.addAllOmegaPosLevels(omegaList);
	}
}
