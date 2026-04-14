package com.hawk.game.dress.luxury;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang.math.NumberUtils;
import org.hawk.app.HawkApp;
import org.hawk.config.HawkConfigManager;
import org.hawk.log.HawkLog;
import org.hawk.net.protocol.HawkProtocol;
import org.hawk.os.HawkException;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkTime;

import com.hawk.game.GsApp;
import com.hawk.game.config.BuffCfg;
import com.hawk.game.config.DressCfg;
import com.hawk.game.config.DressWorldHighendCfg;
import com.hawk.game.config.DressWorldHighendRewardCfg;
import com.hawk.game.config.SkillCfg;
import com.hawk.game.crossproxy.CrossService;
import com.hawk.game.dress.data.ServerLuxuryInfo;
import com.hawk.game.dress.data.PlayerLuxuryInfo;
import com.hawk.game.entity.CustomDataEntity;
import com.hawk.game.entity.DressEntity;
import com.hawk.game.entity.StatusDataEntity;
import com.hawk.game.global.GlobalData;
import com.hawk.game.global.LocalRedis;
import com.hawk.game.item.AwardItems;
import com.hawk.game.player.Player;
import com.hawk.game.protocol.Dress.LuxuryDressInfo;
import com.hawk.game.protocol.Dress.LuxuryDressItemUseNotify;
import com.hawk.game.protocol.Dress.LuxurySkillDataPB;
import com.hawk.game.protocol.Dress.LuxurySkillServerData;
import com.hawk.game.protocol.Dress.LuxuyProtectShowSetReq;
import com.hawk.game.protocol.Dress.ProtectShow2Type;
import com.hawk.game.world.service.WorldPlayerService;
import com.hawk.log.Action;
import com.hawk.serialize.string.SerializeHelper;
import com.hawk.game.protocol.Const;
import com.hawk.game.protocol.HP;
import com.hawk.game.protocol.Status;

/**
 * 天奢装扮服务类
 * 
 * @author lating
 */
public class LuxuryDressService {
	/**
	 * 已解锁天奢装扮数据
	 */
	public static final String LUXURY_DRESS_UNLOCK = "LuxuryDressUnlock";
	/**
	 * 已完成的天奢装扮解锁成就数据
	 */
	public static final String LUXURY_DRESS_ACHIEVE = "LuxuryDressAchieve";
	/**
	 * 保护罩倒计时对外显示数据
	 */
	public static final String LUXURY_PROTECT_SHOW = "LuxuryProtectShow";
	/**
	 * 游戏设置数据
	 */
	public static final String LUXURY_GAME_SET     = "option_LuxuryNotice";
	
	/**
	 * 本服全局技能数据
	 */
	private Map<Integer, List<ServerLuxuryInfo>> globalSkillMap = new ConcurrentHashMap<>();
	
	public static LuxuryDressService instance = new LuxuryDressService();
	
	private LuxuryDressService() {
	}
	
	public static LuxuryDressService getInstance() {
		return instance;
	}
	
	/**
	 * 初始化
	 */
	public void init() {
		try {
			Map<Integer, List<ServerLuxuryInfo>> map = LocalRedis.getInstance().getAllLuxurySkillServer();
			if (map.isEmpty()) {
				return;
			}
			long now = HawkTime.getMillisecond();
			for (Entry<Integer, List<ServerLuxuryInfo>> entry : map.entrySet()) {
				int skillId = entry.getKey();
				List<ServerLuxuryInfo> skillList = entry.getValue();
				globalSkillMap.put(skillId, new CopyOnWriteArrayList<>(skillList));
				for (ServerLuxuryInfo obj : skillList) {
					if (obj.getEndTime() <= now) {
						globalSkillMap.get(skillId).remove(obj);
						LocalRedis.getInstance().removeLuxurySkillServer(obj);
						HawkLog.logPrintln("LuxurySkillService remove expired ServerLuxuryInfo, {}", obj.serialize());
					}
				}
			}
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	
	/**
	 * 获取作用号
	 * @param playerId
	 * @param effectId
	 * @return
	 */
	public int getEffect(String playerId, int effectId) {
		if (!GsApp.getInstance().isInitOK()) {
			return 0;
		}
		if (HawkOSOperator.isEmptyString(playerId)) {
			return 0;
		}
		if (CrossService.getInstance().isCrossPlayer(playerId)) {
			return 0;
		}
		
		int effVal = 0;
		for (Entry<Integer, List<ServerLuxuryInfo>> entry : globalSkillMap.entrySet()) {
			try {
				int skillId = entry.getKey();
				SkillCfg skillCfg = HawkConfigManager.getInstance().getConfigByKey(SkillCfg.class, skillId);
				if (skillCfg == null || skillCfg.isLuxuryProtectSvrSkill() || skillCfg.isLuxuryProtectSelfSkill()) {
					continue;
				}
				BuffCfg buffCfg = HawkConfigManager.getInstance().getConfigByKey(BuffCfg.class, skillCfg.getBuffId());
				if (buffCfg == null || buffCfg.getEffect() != effectId) {
					continue;
				}
				
				int limitCount = Integer.parseInt(skillCfg.getParam2());
				long now = HawkApp.getInstance().getCurrentTime();
				int count = 0;
				for (ServerLuxuryInfo info : entry.getValue()) {
					if (info.getEndTime() > now && count < limitCount) {
						count++;
						effVal += buffCfg.getValue();
					}
				}
			} catch (Exception e) {
				HawkException.catchException(e);
			}
		}
		return effVal;
	}
	
	/**
	 * 获取保护罩buff增益
	 * @param playerId
	 * @return
	 */
	public int getCityProctedGainBuff(String playerId) {
		if (HawkOSOperator.isEmptyString(playerId)) {
			return 0;
		}
		if (CrossService.getInstance().isCrossPlayer(playerId)) {
			return 0;
		}
		
		int addVal = 0;
		for (Entry<Integer, List<ServerLuxuryInfo>> entry : globalSkillMap.entrySet()) {
			try {
				int skillId = entry.getKey();
				SkillCfg skillCfg = HawkConfigManager.getInstance().getConfigByKey(SkillCfg.class, skillId);
				if (skillCfg == null || !skillCfg.isLuxuryProtectSvrSkill()) {
					continue;
				}
				int limitCount = NumberUtils.toInt(skillCfg.getParam2());
				int gainAdd = NumberUtils.toInt(skillCfg.getParam3());
				long now = HawkApp.getInstance().getCurrentTime();
				int count = 0;
				for (ServerLuxuryInfo info : entry.getValue()) {
					if (info.getEndTime() > now && count < limitCount) {
						count++;
						addVal += gainAdd;
					}
				}
			} catch (Exception e) {
				HawkException.catchException(e);
			}
		}
		return addVal;
	}
	
	
	/**
	 * 获取技能数据
	 * @param skillId
	 * @return
	 */
	public List<ServerLuxuryInfo> getSkillInfoById(int skillId) {
		return globalSkillMap.getOrDefault(skillId, Collections.emptyList());
	}
	
	/**
	 * 获取一个技能的开始生效时间
	 * @param skillId
	 * @param defaultTime
	 * @return
	 */
	public long getSkillEffectStartTime(int skillId, long defaultTime) {
		SkillCfg skillCfg = HawkConfigManager.getInstance().getConfigByKey(SkillCfg.class, skillId);
		int limitCount = NumberUtils.toInt(skillCfg.getParam2());
		List<ServerLuxuryInfo> skillList = getSkillInfoById(skillId);
		if (skillList.size() < limitCount) {
			return defaultTime;
		}
		
		long continueLong = skillCfg.getContinueTime() * 1000L;
		int lastIndex = skillList.size() - 1;
		long lastEndTime = skillList.get(lastIndex).getEndTime();
		int count = 0;
		long resultTime = 0L, now = HawkApp.getInstance().getCurrentTime();
		for (ServerLuxuryInfo obj : skillList) {
			//把已经过期的给排除掉
			if (obj.getEndTime() <= now) {
				continue;
			}
			count++;
			if (resultTime == 0 && obj.getEndTime() + continueLong > lastEndTime) {
				resultTime = obj.getEndTime();
			}
		}
		//还没有达到上限，立马就可以生效
		if (count < limitCount || resultTime == 0) {
			return defaultTime;
		}
		return resultTime;
	}
	
	/**
	 * 触发技能
	 * @param playerId
	 * @param skillId
	 * @params effectStartTime 技能开始生效的时间
	 */
	public int castSkill(String playerId, int skillId, long effectStartTime) {
		SkillCfg skillCfg = HawkConfigManager.getInstance().getConfigByKey(SkillCfg.class, skillId);
		DressWorldHighendCfg highendCfg = DressWorldHighendCfg.getCfgBySkillId(skillId);
		if (skillCfg == null || highendCfg == null) {
			return Status.SysError.CONFIG_ERROR_VALUE;
		}
		List<ServerLuxuryInfo> skillList = getSkillInfoById(skillId);
		long now = HawkTime.getMillisecond();
		List<ServerLuxuryInfo> removeList = new ArrayList<>();
		for (ServerLuxuryInfo obj : skillList) {
			if (obj.getEndTime() <= now) {
				removeList.add(obj);
				HawkLog.logPrintln("cast luxury skill remove expired ServerLuxuryInfo, playerId: {}, skillId: {}, info: {}", playerId, skillId, obj.serialize());
				continue;
			}
			if (obj.getPlayerId().equals(playerId)) {
				HawkLog.logPrintln("cast luxury skill break, playerId: {}, skillId: {}, endTime: {}", playerId, skillId, obj.getEndTime());
				return Status.Error.LUXURY_SKILL_WORKING_VALUE;
			}
		}
		
		if (!removeList.isEmpty()) {
			removeList.forEach(obj -> LocalRedis.getInstance().removeLuxurySkillServer(obj));
			skillList.removeAll(removeList);
		}
		
		boolean first = skillList.isEmpty();
		if (first) {
			globalSkillMap.putIfAbsent(skillId, new CopyOnWriteArrayList<>());
			skillList = getSkillInfoById(skillId);
		}
		
		long continueLong = skillCfg.getContinueTime() * 1000L;
		ServerLuxuryInfo obj = new ServerLuxuryInfo(skillId, highendCfg.getDressId(), effectStartTime, effectStartTime + continueLong, playerId);
		skillList.add(obj);
		LocalRedis.getInstance().addLuxurySkillServer(obj, first);
		
		HawkLog.logPrintln("cast luxury skill succ, playerId: {}, skillId: {}, effectStartTime: {}", playerId, skillId, effectStartTime);
		return 0;
	}
	
	/**
	 * 检查是否解锁了天奢装扮
	 * @param player
	 */
	public void checkLuxuryDressUnlock(Player player, DressCfg dressCfg) {
		DressWorldHighendCfg highendCfg = HawkConfigManager.getInstance().getConfigByKey(DressWorldHighendCfg.class, dressCfg.getHighendDressId());
		if (highendCfg == null) {
			return;
		}
		
		LuxuryDressItemUseNotify.Builder builder = LuxuryDressItemUseNotify.newBuilder();
		builder.setDressId(dressCfg.getDressId());
		player.sendProtocol(HawkProtocol.valueOf(HP.code2.LUXURY_DRESS_ITEM_USE_NOTIFY_VALUE, builder));
		
		try {
			CustomDataEntity dressUnlockData = getCustomData(player, LUXURY_DRESS_UNLOCK, "");
			List<Integer> collectDressList = SerializeHelper.stringToList(Integer.class, dressUnlockData.getArg());
			if (!collectDressList.contains(dressCfg.getDressId())) {
				collectDressList.add(dressCfg.getDressId());
				String collectDressStr = SerializeHelper.collectionToString(collectDressList, SerializeHelper.ELEMENT_DELIMITER);
				dressUnlockData.setArg(collectDressStr);
				dressUnlockData.setValue(dressUnlockData.getValue() + 1);
				syncCollectLuxuryDress(player); //给客户端同步
			}
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	/**
	 * 领取解锁天奢装扮个数的成就奖励
	 * @param player
	 * @param cfgId
	 * @return
	 */
	public int takeLuxuryDressAchieveReward(Player player, int cfgId) {
		DressWorldHighendRewardCfg rewardCfg = HawkConfigManager.getInstance().getConfigByKey(DressWorldHighendRewardCfg.class, cfgId);
		if (rewardCfg == null) {
			return Status.SysError.CONFIG_ERROR_VALUE;
		}

		CustomDataEntity dressAchieveData = getCustomData(player, LUXURY_DRESS_ACHIEVE, "");
		List<Integer> achieveList = SerializeHelper.stringToList(Integer.class, dressAchieveData.getArg());
		//已经领取过这一档奖励了
		if (achieveList.contains(cfgId)) {
			return Status.Error.ACTIVITY_REWARD_IS_TOOK_VALUE;
		}
		CustomDataEntity dressUnlockData = getCustomData(player, LUXURY_DRESS_UNLOCK, "");
		int collectDressCount = dressUnlockData.getValue();
		//天奢装扮解锁个数还不满足条件
		if (collectDressCount < rewardCfg.getNeedValue()) {
			return Status.Error.ACTIVITY_CAN_NOT_TAKE_REWARD_VALUE;
		}
		
		achieveList.add(cfgId);
		String completeAchieveStr = SerializeHelper.collectionToString(achieveList, SerializeHelper.ELEMENT_DELIMITER);
		dressAchieveData.setArg(completeAchieveStr);
		
		AwardItems awardItems = AwardItems.valueOf(rewardCfg.getRewards());
		awardItems.rewardTakeAffectAndPush(player, Action.LUXURY_DRESS_COLLECT_REWARD, true);
		
		syncCollectLuxuryDress(player);
		HawkLog.logPrintln("player takeLuxuryDressAchieveReward success, playerId: {}, cfgId: {}", player.getId(), cfgId);
		return 0;
	}
	
	/**
	 * 天奢技能产生的护盾延长时间（倒计时）对外展示设置
	 * @param player
	 * @param req
	 */
	public void luxuyProtectEndShowSet(Player player, LuxuyProtectShowSetReq req) {
		CustomDataEntity protectShowData = getCustomData(player, LUXURY_PROTECT_SHOW, getDefaultProtectShow());
		List<Integer> showTypeList = SerializeHelper.stringToList(Integer.class, protectShowData.getArg());
		showTypeList.clear();
		req.getShow2TypeList().forEach(e -> showTypeList.add(e.getNumber()));
		String showTypeStr = SerializeHelper.collectionToString(showTypeList, SerializeHelper.ELEMENT_DELIMITER);
		protectShowData.setArg(showTypeStr);
		syncCollectLuxuryDress(player);
		//
		StatusDataEntity statusEntity = player.getData().getStatusById(Const.EffType.CITY_SHIELD_VALUE);
		WorldPlayerService.getInstance().updateWorldPointProtected(player.getId(), statusEntity.getEndTime());
	}
	
	/**
	 * 同步天奢装扮相关信息 
	 * @param player
	 */
	public void syncCollectLuxuryDress(Player player) {
		LuxuryDressInfo.Builder builder = LuxuryDressInfo.newBuilder();
		CustomDataEntity dressUnlockData = player.getData().getCustomDataEntity(LUXURY_DRESS_UNLOCK);
		//还没有解锁任何天奢装扮，就不同步了
		if (dressUnlockData != null) {
			builder.setCollectedDressCount(dressUnlockData.getValue());
		}
		CustomDataEntity dressAchieveData = player.getData().getCustomDataEntity(LUXURY_DRESS_ACHIEVE);
		if (dressAchieveData != null) {
			List<Integer> achieveList = SerializeHelper.stringToList(Integer.class, dressAchieveData.getArg());
			if (!achieveList.isEmpty()) {
				builder.addAllRewardGotAchieve(achieveList);
			}
		}
		
		CustomDataEntity protectShowData = this.getCustomData(player, LUXURY_PROTECT_SHOW, getDefaultProtectShow());
		List<Integer> showList = SerializeHelper.stringToList(Integer.class, protectShowData.getArg());
		if (!showList.isEmpty()) {
			builder.addAllProtectShow2Type(showList);
		}
		player.sendProtocol(HawkProtocol.valueOf(HP.code2.LUXURY_DRESS_INFO_SYNC_VALUE, builder));
	}
	
	/**
	 * 同步技能数据
	 * @param playerId
	 */
	public void syncSkillInfo(Player player) {
		LuxurySkillDataPB.Builder builder = LuxurySkillDataPB.newBuilder();
		if (player.isCsPlayer()) {
			player.sendProtocol(HawkProtocol.valueOf(HP.code2.LUXURY_SKILL_INFO_SYNC_VALUE, builder));
			return;
		}
		
		try {
			long now = HawkApp.getInstance().getCurrentTime();
			DressEntity dressEntity = player.getData().getDressEntity();
			for (Entry<Integer, List<ServerLuxuryInfo>> entry : globalSkillMap.entrySet()) {
				int skillId = entry.getKey();
				PlayerLuxuryInfo skillInfo = dressEntity.getSkillInfo(skillId);
				if (skillInfo != null) {
					builder.addPersonalData(skillInfo.toBuilder());
				}
				LuxurySkillServerData.Builder data = LuxurySkillServerData.newBuilder();
				data.setSkillId(skillId);
				for (ServerLuxuryInfo info : entry.getValue()) {
					if (info.getEndTime() > now) {
						data.addCastData(info.toBuilder());
					}
				}
				builder.addServerData(data);
			}
			player.sendProtocol(HawkProtocol.valueOf(HP.code2.LUXURY_SKILL_INFO_SYNC_VALUE, builder));
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	/**
	 * 给在线玩家同步技能信息
	 * @param skillId
	 */
	public void syncSkill2OnlinePlayer(int skillId) {
		List<ServerLuxuryInfo> list = globalSkillMap.get(skillId);
		if (list == null) {
			return;
		}
		for (ServerLuxuryInfo info : list) {
			try {
				if (GlobalData.getInstance().isOnline(info.getPlayerId())) {
					syncSkillInfo(GlobalData.getInstance().makesurePlayer(info.getPlayerId()));
				}
			} catch (Exception e) {
				HawkException.catchException(e);
			}
		}
	}
	
	/**
	 * 获取默认的保护罩倒计时对外显示设置
	 * @return
	 */
	public String getDefaultProtectShow() {
		//守护是默认就有的
		return String.valueOf(ProtectShow2Type.GUARD_PLAYER_VALUE);
	}
	
	/**
	 * 获取自定义数据
	 * @param player
	 * @param customKey
	 * @return
	 */
	private CustomDataEntity getCustomData(Player player, String customKey, String defaultArgVal) {
		CustomDataEntity customData = player.getData().getCustomDataEntity(customKey);
		if(customData != null){
			return customData;
		}
		return player.getData().createCustomDataEntity(customKey, 0, defaultArgVal);
	}
}
