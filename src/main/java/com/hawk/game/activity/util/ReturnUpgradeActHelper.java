package com.hawk.game.activity.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.hawk.config.HawkConfigManager;
import org.hawk.config.iterator.ConfigIterator;
import org.hawk.log.HawkLog;
import org.hawk.os.HawkException;

import com.hawk.game.config.BuildAreaCfg;
import com.hawk.game.config.BuildLimitCfg;
import com.hawk.game.config.BuildingCfg;
import com.hawk.game.config.PlayerLevelExpCfg;
import com.hawk.game.config.TechnologyCfg;
import com.hawk.game.entity.BuildingBaseEntity;
import com.hawk.game.entity.TechnologyEntity;
import com.hawk.game.global.GlobalData;
import com.hawk.game.item.AwardItems;
import com.hawk.game.log.BehaviorLogger;
import com.hawk.game.module.agency.PlayerAgencyModule;
import com.hawk.game.msg.SuperSoldierTriggeTaskMsg;
import com.hawk.game.player.Player;
import com.hawk.game.player.PlayerData;
import com.hawk.game.protocol.Activity;
import com.hawk.game.protocol.Building;
import com.hawk.game.protocol.Const;
import com.hawk.game.protocol.HP;
import com.hawk.game.protocol.SuperSoldier;
import com.hawk.game.protocol.Const.BuildingType;
import com.hawk.game.service.BuildingService;
import com.hawk.game.service.mssion.MissionManager;
import com.hawk.game.service.mssion.event.EventAreaProgress;
import com.hawk.game.service.mssion.event.EventUnlockGround;
import com.hawk.game.util.GsConst;
import com.hawk.log.Action;
import com.hawk.log.Source;
import com.hawk.log.LogConst.PowerChangeReason;

public class ReturnUpgradeActHelper {
	
	public static int returnBuildingLvUp(String playerId, int level){
		try {
			BuildingCfg toCfg = HawkConfigManager.getInstance().getConfigByKey(BuildingCfg.class, level);
			if(toCfg == null){
				return -3;
			}
			if(getConstructionFactoryCfgId(playerId) >= level){
				return -5;
			}
			Player player = GlobalData.getInstance().makesurePlayer(playerId);
			if (player == null) {
				return -1;
			}
			if (player.getData().getQueueEntitiesByType(Const.QueueType.BUILDING_QUEUE_VALUE).size() > 0) {
				return -4;
			}
			unlockArea(player);
			for (int buildType : getBuildTypeList()) {
				// 这个时只在前端显示的假建筑，后端屏蔽不处理
				if (buildType == 2213) {
					continue;
				}
				//todo 先这么写看看效果，后面再和策划对
				if(buildType == 2233){
					continue;
				}
				if(buildType == 2234){
					continue;
				}
				if(buildType == 2235){
					continue;
				}
				if(buildType == 2236){
					continue;
				}
				if(buildType == 2237){
					continue;
				}
				BuildingBaseEntity buildingEntity = getBuildingBaseEntity(player, buildType);
				if (buildingEntity == null && !BuildAreaCfg.isShareBlockBuildType(buildType)) {
					BuildingCfg buildingCfg = HawkConfigManager.getInstance().getConfigByKey(BuildingCfg.class, (buildType * 100) + 1);
					buildingEntity = player.getData().createBuildingEntity(buildingCfg, "1", false);
					BuildingService.getInstance().createBuildingFinish(player, buildingEntity, Building.BuildingUpdateOperation.BUILDING_UPDATE_IMMIDIATELY, HP.code.BUILDING_CREATE_PUSH_VALUE);
					if (buildType == BuildingType.RADAR_VALUE) {
						try {
							PlayerAgencyModule module = player.getModule(GsConst.ModuleType.AGENCY_MODULE);
							module.initData();
						} catch (Exception e) {
						}
					}
				}
				buildUpgrade(player, buildingEntity, toCfg.getLevel(), toCfg.getProgress());
			}
			for (BuildingBaseEntity entity : player.getData().getBuildingEntities()) {
				BuildingCfg buildingCfg = HawkConfigManager.getInstance().getConfigByKey(BuildingCfg.class, entity.getBuildingCfgId());
				if (buildingCfg == null) {
					continue;
				}
				buildUpgrade(player, entity, toCfg.getLevel(), toCfg.getProgress());
			}
			player.refreshPowerElectric(PowerChangeReason.BUILD_LVUP);
			return 1;
		} catch (Exception e) {
			HawkException.catchException(e);
			return -2;
		}
	}

	/**
	 * 升级建筑
	 *
	 * @param player
	 * @param buildingEntity
	 */
	private static void buildUpgrade(Player player, BuildingBaseEntity buildingEntity,int honorLevel, int progress) {
		buildUpgradeLevel(player, buildingEntity, honorLevel);
		buildUpgradeProgress(player, buildingEntity, honorLevel, progress);
	}

	private static void buildUpgradeLevel(Player player, BuildingBaseEntity buildingEntity,int honorLevel) {
		// 建筑满级
		BuildingCfg oldBuildCfg = HawkConfigManager.getInstance().getConfigByKey(BuildingCfg.class, buildingEntity.getBuildingCfgId());
		BuildingCfg buildingCfg = HawkConfigManager.getInstance().getConfigByKey(BuildingCfg.class, oldBuildCfg.getPostStage());
		while (buildingCfg != null && buildingCfg.getLevel() < honorLevel && buildingCfg.getBuildType() == buildingEntity.getType()) {
			BuildingService.getInstance().buildingUpgrade(player, buildingEntity, Building.BuildingUpdateOperation.BUILDING_UPDATE_IMMIDIATELY);
			oldBuildCfg = HawkConfigManager.getInstance().getConfigByKey(BuildingCfg.class, buildingEntity.getBuildingCfgId());
			buildingCfg = HawkConfigManager.getInstance().getConfigByKey(BuildingCfg.class, oldBuildCfg.getPostStage());
		}
	}

	private static void buildUpgradeProgress(Player player, BuildingBaseEntity buildingEntity,int honorLevel, int progress) {
		// 建筑满级
		BuildingCfg oldBuildCfg = HawkConfigManager.getInstance().getConfigByKey(BuildingCfg.class, buildingEntity.getBuildingCfgId());
		BuildingCfg buildingCfg = HawkConfigManager.getInstance().getConfigByKey(BuildingCfg.class, oldBuildCfg.getPostStage());
		while (buildingCfg != null && buildingCfg.getLevel() <= honorLevel && buildingCfg.getProgress() <= progress && buildingCfg.getBuildType() == buildingEntity.getType()) {
			BuildingService.getInstance().buildingUpgrade(player, buildingEntity, Building.BuildingUpdateOperation.BUILDING_UPDATE_IMMIDIATELY);
			oldBuildCfg = HawkConfigManager.getInstance().getConfigByKey(BuildingCfg.class, buildingEntity.getBuildingCfgId());
			buildingCfg = HawkConfigManager.getInstance().getConfigByKey(BuildingCfg.class, oldBuildCfg.getPostStage());
		}
	}

	/**
	 * 根据建筑cfgId获取建筑实体
	 * @param id
	 */
	public static BuildingBaseEntity getBuildingBaseEntity(Player player, int buildingType) {
		Optional<BuildingBaseEntity> op = player.getData().getBuildingEntities().stream()
				.filter(e -> e.getStatus() != Const.BuildingStatus.BUILDING_CREATING_VALUE)
				.filter(e -> e.getType() == buildingType)
				.findAny();
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}


	/**
	 * 获取需要升级至满级的建筑列表
	 * @return
	 */
	private static List<Integer> getBuildTypeList() {
		List<Integer> retList = new ArrayList<>();

		ConfigIterator<BuildingCfg> buildCfgIterator = HawkConfigManager.getInstance().getConfigIterator(BuildingCfg.class);
		while (buildCfgIterator.hasNext()) {
			BuildingCfg buildCfg = buildCfgIterator.next();
			if (buildCfg.getLevel() > 1) {
				continue;
			}
			BuildLimitCfg cfg = HawkConfigManager.getInstance().getConfigByKey(BuildLimitCfg.class, buildCfg.getLimitType());
			if (cfg == null || cfg.getLimit(30) > 1) {
				continue;
			}
			retList.add(buildCfg.getBuildType());
		}
		return retList;
	}

	/**
	 * 解锁地块
	 * @param player
	 */
	private static void unlockArea(Player player) {
		try {
			Set<Integer> unlockedAreas = player.getData().getPlayerBaseEntity().getUnlockedAreaSet();
			ConfigIterator<BuildAreaCfg> iterator = HawkConfigManager.getInstance().getConfigIterator(BuildAreaCfg.class);

			List<Integer> areaList = new ArrayList<Integer>();
			while (iterator.hasNext()) {
				BuildAreaCfg areaCfg = iterator.next();
				int areaId = areaCfg.getId();
				if (unlockedAreas.contains(areaId)) {
					continue;
				}

				areaList.add(areaId);
			}

			areaList.stream().forEach(e -> {
				player.unlockArea(e);
				MissionManager.getInstance().postMsg(player, new EventUnlockGround(e));
				BuildAreaCfg cfg = HawkConfigManager.getInstance().getConfigByKey(BuildAreaCfg.class, e);
				if (cfg != null) {
					MissionManager.getInstance().postMsg(player, new EventAreaProgress(e, cfg.getBattleNpcList().size()));
				}
				MissionManager.getInstance().postSuperSoldierTaskMsg(player, new SuperSoldierTriggeTaskMsg(SuperSoldier.SupersoldierTaskType.UNLOCK_AREA_TASK, 1));
				// 解锁地块任务
				BehaviorLogger.log4Service(player, Source.USER_OPERATION, Action.BUIDING_AREA_UNLOCK, BehaviorLogger.Params.valueOf("buildAreaId", e));
			});

			player.getPush().synUnlockedArea();
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}

	public static int returnTechUp(String playerId, int level){
		try {
			BuildingCfg toCfg = HawkConfigManager.getInstance().getConfigByKey(BuildingCfg.class, level);
			if(toCfg == null){
				return -3;
			}
			Player player = GlobalData.getInstance().makesurePlayer(playerId);
			if (player == null) {
				return -1;
			}
			if (player.getData().getQueueEntitiesByType(Const.QueueType.SCIENCE_QUEUE_VALUE).size() > 0) {
				return -4;
			}
			for(TechnologyCfg cfg : TechnologyCfg.getTechMap(toCfg.getLevel()).values()){
				techLevelUp(player, cfg);
			}
			return 1;
		} catch (Exception e) {
			HawkException.catchException(e);
		}

		return -2;
	}
	/**
	 * 科技升级
	 * @param techId
	 * @return
	 */
	private static boolean techLevelUp(Player player, TechnologyCfg cfg) {
		int techId = cfg.getTechId();
		TechnologyEntity entity = player.getData().getTechEntityByTechId(techId);
		if (entity == null) {
			entity = player.getData().createTechnologyEntity(cfg);
		}

		player.getData().getPlayerEffect().addEffectTech(player, entity);
		entity.setLevel(cfg.getLevel());
		entity.setResearching(false);
		player.getPush().syncTechnologyLevelUpFinish(entity.getCfgId());
		player.refreshPowerElectric(PowerChangeReason.TECH_LVUP);

		// 如果科技解锁技能,则推送科技技能信息
		if (cfg.getTechSkill() > 0) {
			player.getPush().syncTechSkillInfo();
		}

		return true;
	}

	public static int returnRoleUp(String playerId, int level) {
		Player player = GlobalData.getInstance().makesurePlayer(playerId);
		if (player == null) {
			return -1;
		}
		if(player.getLevel() >= level){
			return -4;
		}
		while (player.getLevel() < level){
			PlayerLevelExpCfg cfg = HawkConfigManager.getInstance().getConfigByKey(PlayerLevelExpCfg.class, player.getLevel() + 1);
			int exp = cfg.getExp() - player.getExp() + 1;
			HawkLog.logPrintln("returnRoleUp, cfg:{}, player:{}", cfg.getExp(), player.getExp());
			if(exp <= 0){
				return -2;
			}
			AwardItems awardItems = AwardItems.valueOf("10000_1004_"+exp);
			awardItems.rewardTakeAffectAndPush(player, Action.GM_AWARD);
		}
		return 0;
	}

	public static boolean returnUpgradeCheck(String playerId, Activity.ReturnUpgradeType type) {
		Player player = GlobalData.getInstance().makesurePlayer(playerId);
		if (player == null) {
			return false;
		}
		switch (type){
			case REUP_BASE:{
				if (player.getData().getQueueEntitiesByType(Const.QueueType.BUILDING_QUEUE_VALUE).size() > 0) {
					return false;
				}
			}
			break;
			case REUP_TECH:{
				if (player.getData().getQueueEntitiesByType(Const.QueueType.SCIENCE_QUEUE_VALUE).size() > 0) {
					return false;
				}
			}
			break;
		default:
			break;
		}
		return true;
	}

	public static Map<Integer, Integer> getTechTypeMap(String playerId) {
		Player player = GlobalData.getInstance().makesurePlayer(playerId);
		if (player == null) {
			return new ConcurrentHashMap<>();
		}
		Map<Integer, Integer> typeMap = new ConcurrentHashMap<>();
		for(TechnologyEntity entity : player.getData().getTechnologyEntities()){
			TechnologyCfg cfg = HawkConfigManager.getInstance().getConfigByKey(TechnologyCfg.class, entity.getCfgId());
			if(cfg == null){
				continue;
			}
			int cur = typeMap.getOrDefault(cfg.getTechType(), 0);
			cur += cfg.getLevel();
			typeMap.put(cfg.getTechType(), cur);
		}
		return typeMap;
	}

	public static Map<Integer, Integer> getTechLevelTypeMap(int level) {
		BuildingCfg toCfg = HawkConfigManager.getInstance().getConfigByKey(BuildingCfg.class, level);
		if(toCfg == null){
			return new ConcurrentHashMap<>();
		}
		return TechnologyCfg.getTypeMap(toCfg.getLevel());
	}

	public static Map<Integer, Integer> getTechTypeMaxMap() {
		return TechnologyCfg.getTypeMaxMap();
	}

	public static long getTechLevelPower(int level) {
		BuildingCfg toCfg = HawkConfigManager.getInstance().getConfigByKey(BuildingCfg.class, level);
		if(toCfg == null){
			return 0L;
		}
		return TechnologyCfg.getPower(toCfg.getLevel());
	}

	public static int getConstructionFactoryCfgId(String playerId) {
		PlayerData playerData = GlobalData.getInstance().getPlayerData(playerId, true);
		if (playerData == null) {
			return 0;
		}
		return playerData.getConstructionFactory().getBuildingCfgId();
	}
}
