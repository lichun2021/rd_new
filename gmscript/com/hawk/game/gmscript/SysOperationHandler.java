package com.hawk.game.gmscript;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import com.hawk.activity.helper.RewardHelper;
import com.hawk.game.cfgElement.ArmourEffObject;
import com.hawk.game.config.ArmourCfg;
import com.hawk.game.config.ArmourConstCfg;
import com.hawk.game.module.armour.cfg.ArmourImmortEchoCfg;
import com.hawk.game.entity.*;
import com.hawk.game.item.ConsumeItems;
import com.hawk.game.item.ItemInfo;
import com.hawk.game.module.armour.ArmourImmortComp;
import com.hawk.game.player.PlayerData;
import com.hawk.game.player.equip.CommanderObject;
import com.hawk.game.player.hero.PlayerHero;
import com.hawk.game.player.hero.rise.HeroRise;
import com.hawk.game.player.hero.rise.RiseSkillPage;
import com.hawk.game.player.hero.rise.RiseSkillSettings;
import com.hawk.game.protocol.*;
import com.hawk.game.util.GsConst;
import com.hawk.log.Action;
import com.hawk.log.LogConst;
import com.hawk.serialize.string.SerializeHelper;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.hawk.config.HawkConfigManager;
import org.hawk.config.iterator.ConfigIterator;
import org.hawk.db.HawkDBEntity;
import org.hawk.db.HawkDBManager;
import org.hawk.log.HawkLog;
import org.hawk.net.protocol.HawkProtocol;
import org.hawk.os.HawkException;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkTime;
import org.hawk.redis.HawkRedisSession;
import org.hawk.script.HawkScript;
import org.hawk.script.HawkScriptHttpInfo;
import org.hawk.task.HawkTaskManager;
import org.hawk.thread.HawkTask;
import org.hawk.thread.HawkThreadPool;

import com.hawk.activity.ActivityBase;
import com.hawk.activity.ActivityManager;
import com.hawk.activity.helper.PlayerPushHelper;
import com.hawk.activity.type.ActivityType;
import com.hawk.activity.type.impl.achieve.entity.AchieveItem;
import com.hawk.activity.type.impl.strongestGuild.StrongestGuildActivity;
import com.hawk.activity.type.impl.strongestGuild.entity.StrongestGuildEntity;
import com.hawk.activity.type.impl.strongestGuild.rank.StrongestGuildRank;
import com.hawk.game.GsConfig;
import com.hawk.game.cfgElement.ArmourStarExplores;
import com.hawk.game.crossactivity.season.CrossActivitySeasonService;
import com.hawk.game.crossactivity.season.CrossSeaonStateData;
import com.hawk.game.crossactivity.season.CrossSeasonServerData;
import com.hawk.game.crossproxy.CrossService;
import com.hawk.game.global.CSRedisProxyPool;
import com.hawk.game.global.GlobalData;
import com.hawk.game.global.RedisProxy;
import com.hawk.game.invoker.GuildDemiseLeaderInvoker;
import com.hawk.game.module.mechacore.entity.MechaCoreEntity;
import com.hawk.game.module.toucai.entity.MedalEntity;
import com.hawk.game.player.Player;
import com.hawk.game.player.cache.PlayerDataKey;
import com.hawk.game.protocol.Activity.StrongestGuildStateChange;
import com.hawk.game.service.GuildService;
import com.hawk.game.service.SysOpService;
import com.hawk.game.service.cyborgWar.CWTeamData;
import com.hawk.game.service.cyborgWar.CyborgWarRedis;
import com.hawk.game.service.cyborgWar.CyborgWarService;
import com.hawk.game.service.starwars.StarWarsOfficerService;
import com.hawk.game.service.xqhxWar.XQHXWarService;
import com.hawk.game.service.xqhxWar.season.XQHXSeasonManager;
import com.hawk.game.service.xqhxWar.season.data.XQHXSeasonData;
import com.hawk.gamelib.GameConst.MsgId;

/**
 * 用来动态调整
 * 
 * localhost:8080/script/sysop?op=09071500&playerId=
 *
 * @author hawk
 */
public class SysOperationHandler extends HawkScript {

	@Override
	public String action(Map<String, String> params, HawkScriptHttpInfo httpInfo) {
		String opType = params.get("op");
		if (HawkOSOperator.isEmptyString(opType)) {
			return HawkScript.failedResponse(HawkScript.SCRIPT_ERROR, "op type null");
		}

		/*
		 * 常驻脚本执行
		 * 常驻脚本opType用数字,和线上脚本区分开
		 */
		if (SysOpService.getInstance().sysop(params, opType)) {
			return HawkScript.successResponse("op service success");
		}

		if (opType.equals("202508111420")) {
			fixData();
			return HawkScript.successResponse("op success, opType:" + opType);
		}
		
		if (opType.equals("202508221010")) {
			xqhxWarRankUpdate();
			return HawkScript.successResponse("op success, opType:" + opType);
		}
		
		if (opType.equals("202508221212")) {
			cbwarGuildTeamCheck(params);
			return HawkScript.successResponse("op success, opType:" + opType);
		}
		
		
		if (opType.equals("202508221831")) {
			playerActivityDupAchieveDataFix(params);
			return HawkScript.successResponse("op success, opType:" + opType);
		}
		
		if (opType.equals("202508251010")) {
			starWarOfficeDataDel();
			return HawkScript.successResponse("op success, opType:" + opType);
		}
		
		if (opType.equals("202508252020")) {
			StarWarsOfficerService.getInstance().loadOrReloadOfficer();
			return HawkScript.successResponse("op success, opType:" + opType);
		}
		
		if (opType.equals("202509021510")) {
			fixCrossSeasonScore();
			return HawkScript.successResponse("op success, opType:" + opType);
		}
		
		if (opType.equals("202509152047")) {
			String msg = initCrossRedisProxyPool();
			return HawkScript.successResponse("op success, opType:" + opType + "   "+ msg);
		}
		
		if(opType.equals("202510211415")){
			starExploreMax(params);
			return HawkScript.successResponse("op success, opType:" + opType);
		}
		
		if(opType.equals("2025102517654321")){
			changeGuildLeader(params);
			return HawkScript.successResponse("op success, opType:" + opType);
		}
		
		if (opType.equals("202510281936")) {
			resetSuperGift(params);
			return HawkScript.successResponse("op success, opType:" + opType);
		}
		
		if(opType.equals("2025112701")){
			xiufuPlantTech();
			return HawkScript.successResponse("op success, opType:" + opType);
		}
		
		if(opType.equals("202512041622")){
			strongestGuildActivityFix(params);
			return HawkScript.successResponse("op success, opType:" + opType);
		}
		
		if(opType.equals("2025120101")){
			fixPlantTechEntity(params);
			return HawkScript.successResponse("op success, opType:" + opType);
		}

		if (opType.equals("2025121717")) {
			fixXQHXSeasonTeamData(params);
			// 刷排行榜
			return HawkScript.successResponse("op success, opType:" + opType);
		}
		if (opType.equals("2026012210")) {
            fixArmourImmortData();
			// 刷排行榜
			return HawkScript.successResponse("op success, opType:" + opType);
		}
		if (opType.equals("202601281840")) {
            fixArmourImmortExcessData(params);
			// 刷排行榜
			return HawkScript.successResponse("op success, opType:" + opType);
		}
		if (opType.equals("202603031020")) {
            fixArmourImmortExchange();
			// 刷排行榜
			return HawkScript.successResponse("op success, opType:" + opType);
		}


		return HawkScript.failedResponse(HawkScript.SCRIPT_ERROR, "opType not found");
	}
	
	/**
	 * 王者联盟活动数据修复
	 */
	private void strongestGuildActivityFix(Map<String, String> params) {
		String playerId = params.get("playerId");
		if (!HawkOSOperator.isEmptyString(playerId)) {
			String buildPower = params.getOrDefault("buildPower", "0");
			String techPower = params.getOrDefault("techPower", "0");
			fixStrongestGuildActivity(playerId, Integer.parseInt(buildPower), Integer.parseInt(techPower));
			return;
		}
		
		long result = RedisProxy.getInstance().getRedisSession().hSetNx("fixStrongestGuild1204Svr", GsConfig.getInstance().getServerId(), String.valueOf(HawkTime.getMillisecond()));
		if (result <= 0) {
			HawkLog.logPrintln("sysop StrongestGuildEvent repeated");
			return;
		}
		
		RedisProxy.getInstance().getRedisSession().expire("fixStrongestGuild1204Svr", 3600);
		
		List<String> infos = new ArrayList<>();
		try {
			HawkOSOperator.readTextFileLines("tmp/20251204_strongest_guild.txt", infos);
		} catch (Exception e) {
			HawkException.catchException(e);
			return;
		}
		
		for (String info : infos) {
			try {
				String[] arr = info.split(",");
				if (!arr[3].equals(GsConfig.getInstance().getServerId())) {
					continue;
				}
				fixStrongestGuildActivity(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
			} catch (Exception e) {
				HawkException.catchException(e);
			}
		}
	}
	
	/**
	 * 王者联盟活动数据修复
	 */
	private void fixStrongestGuildActivity(String playerId, int buildPower, int techPower) {
		Player player = GlobalData.getInstance().makesurePlayer(playerId);
		if (player == null) {
			return;
		} 
		if (CrossService.getInstance().isCrossPlayer(playerId)) {
			HawkLog.logPrintln("sysop StrongestGuildEvent, cross player, playerId: {}", playerId);
			return;
		}
		if (buildPower > 0 && techPower > 0) {
			HawkLog.logPrintln("sysop StrongestGuildEvent, invalid data, playerId: {}, buildPower: {}, techPower: {}", playerId, buildPower, techPower);
			return;
		}
		long score = buildPower * 100L + techPower * 100L;
		if (score <= 0) {
			HawkLog.logPrintln("sysop StrongestGuildEvent, score zero, playerId: {}, buildPower: {}, techPower: {}", playerId, buildPower, techPower);
			return;
		}
		
		HawkThreadPool threadPool = HawkTaskManager.getInstance().getTaskExecutor();
        int threadIdx = Math.abs(player.getXid().hashCode() % threadPool.getThreadNum());
        HawkTaskManager.getInstance().postExtraTask(new HawkTask() {
				@Override
                public Object run() {
                	fixStrongestGuildActivity(playerId, score);
                	return null;
                }
         }, threadIdx);
	}
	
	/**
	 * 王者联盟活动数据修复
	 */
	private void fixStrongestGuildActivity(String playerId, long update) {
		Optional<StrongestGuildActivity> opActivity = ActivityManager.getInstance().getGameActivityByType(Activity.ActivityType.STRONGEST_GUILD_VALUE);
		if (!opActivity.isPresent()) {
			return;
		}
		StrongestGuildActivity activity = opActivity.get();
		Optional<StrongestGuildEntity> opEntity = activity.getPlayerDataEntity(playerId);
		if (!opEntity.isPresent()) {
			return;
		}
		
		StrongestGuildEntity entity = opEntity.get();
		entity.setScore(entity.getScore() + update);
		//计算玩家目标是否达成
		HawkLog.logPrintln("sysop StrongestGuildEvent, addScore:{}, playerId:{}, guildId:{}",update, playerId, activity.getDataGeter().getGuildId(playerId));
		entity.checkTarget();
		entity.notifyUpdate(); //积分变了这个必须要执行
		// 推送积分变化
		StrongestGuildStateChange.Builder builder = StrongestGuildStateChange.newBuilder();
		builder.setScore(entity.getScore());
		PlayerPushHelper.getInstance().pushToPlayer(playerId, HawkProtocol.valueOf(HP.code.STRONGEST_GUILD_SCORE_CHANGE_S_VALUE, builder));

		int stageId = activity.getCurStageId();
		HawkTaskManager.getInstance().postExtraTask(new HawkTask() {
			@Override
			public Object run() {
				try {
					Field personRankFiled = HawkOSOperator.getClassField(activity, "personRank");
					@SuppressWarnings("unchecked")
					List<StrongestGuildRank> personRank = (List<StrongestGuildRank>) personRankFiled.get(activity);
					Field guildRankFiled = HawkOSOperator.getClassField(activity, "guildRank");
					@SuppressWarnings("unchecked")
					List<StrongestGuildRank> guildRank = (List<StrongestGuildRank>) guildRankFiled.get(activity);
					
					int rankType = 1;
					for(StrongestGuildRank rank : personRank){
						rank.addScore(update, playerId); 
						activity.getDataGeter().strongestGuildScoreRecord(playerId, rankType, activity.getActivityTermId(), stageId, (long) rank.getScore(playerId));
						rankType --;
					}
					String guildId = activity.getDataGeter().getGuildId(playerId);
					if (!HawkOSOperator.isEmptyString(guildId)) {
						for (StrongestGuildRank rank : guildRank) {
							rank.addScore(update, guildId);
						}
					}
				} catch (Exception e) {
					HawkException.catchException(e);
				}
				return null;
			}
		});
	}
	
	
	/**
	 * 超值礼包数据重置
	 * @param params
	 */
	private void resetSuperGift(Map<String, String> params) {
		String playerId = params.get("playerId");
		String groupIds = params.get("groupId");
		if (HawkOSOperator.isEmptyString(playerId) || HawkOSOperator.isEmptyString(groupIds)) {
			HawkLog.logPrintln("sysop reset super gift failed, playerId: {}, groupIds: {}", playerId, groupIds);
			return;
		}
		Player player = GlobalData.getInstance().makesurePlayer(playerId);
		if (player == null) {
			HawkLog.logPrintln("sysop reset super gift failed, player null: {}", playerId);
			return;
		}
		
		String[] groupIdArr = groupIds.split(",");
		HawkThreadPool threadPool = HawkTaskManager.getInstance().getTaskExecutor();
		int threadIdx = Math.abs(player.getXid().hashCode() % threadPool.getThreadNum());
		HawkTaskManager.getInstance().postExtraTask(new HawkTask() {
				@Override
				public Object run() {
					PlayerGiftEntity giftEntity = player.getData().getPlayerGiftEntity();
					for (String groupId : groupIdArr) {
						giftEntity.getRootGroupIdRefRecordsMap().remove(Integer.valueOf(groupId));
					}
					giftEntity.notifyUpdate();
					HawkLog.logPrintln("sysop reset super gift end, playerId: {}, groupIds: {}", playerId, groupIds);
					return null;
				}
		 }, threadIdx);
	}
	
	/**
	 * 更换盟主
	 */
	public void changeGuildLeader(Map<String, String> params) {
		
		logger.info("start changeGuildLeader...");
		
		String beforePlayerId = params.get("playerId");
		Player beforePlayer = GlobalData.getInstance().makesurePlayer(beforePlayerId);
		if (beforePlayer == null) {
			logger.info("changeGuildLeader beforePlayer error");
			return;
		}
		
		String afterPlayerId = params.get("tarplayerId");
		Player afterPlayer = GlobalData.getInstance().makesurePlayer(afterPlayerId);
		if (afterPlayer == null) {
			logger.info("changeGuildLeader afterPlayer error");
			return;
		}
		
		// 检测两个玩家的联盟
		String beforePlayerGuildId = GuildService.getInstance().getPlayerGuildId(beforePlayerId);
		String afterPlayerGuildId = GuildService.getInstance().getPlayerGuildId(afterPlayerId);
		if (HawkOSOperator.isEmptyString(beforePlayerGuildId) || HawkOSOperator.isEmptyString(afterPlayerGuildId) || !beforePlayerGuildId.equals(afterPlayerGuildId)) {
			logger.info("changeGuildLeader guildId error, beforePlayerGuildId:{}, afterPlayerGuildId:{}", beforePlayerGuildId, afterPlayerGuildId);
			return;
		}
		
//		// 权限检测
//		if (!GuildService.getInstance().checkGuildAuthority(beforePlayerId, AuthId.ALLIANCE_LEADERSHIP_CHANGE)) {
//			logger.info("changeGuildLeader GuildService error");
//			return;
//		}
	
		// 转让盟主
		GuildService.getInstance().dealMsg(MsgId.GUILD_DEMISE_LEADER, new GuildDemiseLeaderInvoker(beforePlayer, afterPlayerId, HP.code.GUILDMANAGER_DEMISELEADER_C_VALUE));
	}
	
	
	private String initCrossRedisProxyPool() {
		boolean bfalse = CSRedisProxyPool.getInstance().init();
		for (int i = 0; i < 10; i++) {
			String playerId = GsConfig.getInstance().getServerId() + "-" + i;
			CSRedisProxyPool.getInstance().getRedisSession(playerId).hSet("CSRedisProxyPool", playerId, playerId, 24* 60*60);
		}
		return "CSRedisProxyPool init " + bfalse;
	}


	/**
	 * 设置航海赛季分数
	 * @param params
	 */
	protected void fixCrossSeasonScore(){
		int score = 1100;
		String serverId = GsConfig.getInstance().getServerId();
		CrossSeaonStateData state = CrossActivitySeasonService.getInstance().getCrossSeaonStateData();
		int season = state.getSeason();
		CrossSeasonServerData data = CrossSeasonServerData.loadData(season, serverId);
		if(Objects.isNull(data)){
			HawkLog.logPrintln("fixCrossSeasonScore-CrossSeasonServerData-null-{},serverId");
			return;
		}
		int bef = data.getScore();
		data.setScore(score);
		data.saveData();
		HawkLog.logPrintln("fixCrossSeasonScore-CrossSeasonServerData-over-{}-{}-{}",serverId,bef,data.getScore());
	}
	
	
	/**
	 * 清除一下大帝战数据
	 */
	protected void starWarOfficeDataDel() {
		try {
			//清除官职
			Method methodClearOfficer = HawkOSOperator.getClassMethod(StarWarsOfficerService.getInstance(), "clearOfficerInfo");
			methodClearOfficer.invoke(StarWarsOfficerService.getInstance());
			HawkLog.logPrintln("starWarOfficeDataDel-clearOfficerInfo suc");
		} catch (Exception e) {
			HawkLog.logPrintln("starWarOfficeDataDel-clearOfficerInfo err");
		}
		
		try {
			//清除礼包
			Method methodClearGift = HawkOSOperator.getClassMethod(StarWarsOfficerService.getInstance(), "clearGiftInfo");
			methodClearGift.invoke(StarWarsOfficerService.getInstance());
			HawkLog.logPrintln("starWarOfficeDataDel-methodClearGift suc");
		} catch (Exception e) {
			HawkLog.logPrintln("starWarOfficeDataDel-methodClearGift err");
		}
		HawkLog.logPrintln("starWarOfficeDataDel-over");
		
	}
	
	/**
	 * 修复活动中的重复成就
	 * @param params
	 */
	protected void playerActivityDupAchieveDataFix(Map<String, String> params) {
        String playerId = params.get("playerId");
        int activityId = Integer.parseInt(params.get("activityId"));
        String listName = params.get("listName");
        if (HawkOSOperator.isEmptyString(playerId)) {
            return;
        }
        Player player = GlobalData.getInstance().makesurePlayer(playerId);
        if (player == null) {
                return;
        }

        HawkThreadPool threadPool = HawkTaskManager.getInstance().getTaskExecutor();
        int threadIdx = Math.abs(player.getXid().hashCode() % threadPool.getThreadNum());
        HawkTaskManager.getInstance().postExtraTask(new HawkTask() {
                @SuppressWarnings("unchecked")
				@Override
                public Object run() {
                	Optional<ActivityBase> opActivity = ActivityManager.getInstance().getGameActivityByType(activityId);
                	if(!opActivity.isPresent()){
                		return null;
                	}
                	 
                	ActivityBase activity = opActivity.get();
 	                Optional<HawkDBEntity> entityOp = activity.getPlayerDataEntity(playerId);
 	                if(!entityOp.isPresent()){
 	                	 return null;
 	                }
 	                
 	                HawkDBEntity entity = entityOp.get();
 	                ActivityType atype = ActivityType.getType(activityId);
 	                if(Objects.isNull(atype)){
	                	 return null;
	                }
 	                Field[] fs = entity.getClass().getDeclaredFields();
 	                List<AchieveItem> alist = null;
 	                for(Field field : fs){
 	                	if(!field.getName().equals(listName)){
 	                		continue;
 	                	}
 	                	
 	                	field.setAccessible(true);
 	                	try {
							alist= (List<AchieveItem>) field.get(entity);
						} catch (Exception e) {
							e.printStackTrace();
						}
 	                	break;
 	                }
 	                if(Objects.isNull(alist)){
 	                	return null;
 	                }
 	                Map<Integer, AchieveItem> itemMap = new HashMap<>();
                    for (AchieveItem item : alist) {
                       AchieveItem oldItem = itemMap.get(item.getAchieveId());
                       if (oldItem == null || item.getState() > oldItem.getState()) {
                    	   itemMap.put(item.getAchieveId(), item);
                       }
                    }
                    alist.clear();
                    alist.addAll(itemMap.values());
                    entity.notifyUpdate();
                    return null;
                }
         }, threadIdx);
	}


	
	/**
	 * 刷新一下先驱的排行榜
	 */
	public void xqhxWarRankUpdate(){
		XQHXWarService.getInstance().doTeamRank(true);
	}
	
	/**
	 * 检查一下赛博小队问题
	 */
	public void cbwarGuildTeamCheck(Map<String, String> params){
		String guildId = params.get("guildId");
		if(HawkOSOperator.isEmptyString(guildId)){
			HawkLog.logPrintln("cbwarGuildTeamCheck guildId null");
			return;
		}
		GuildInfoObject guildObj = GuildService.getInstance().getGuildInfoObject(guildId);
		HawkLog.logPrintln("cbwarGuildTeamCheck guildObj null:{}",guildId);
		if(Objects.isNull(guildObj)){
			return;
		}
		List<String> teams = CyborgWarRedis.getInstance().getCWGuildTeams(guildId);
		
		List<String> tlist = new ArrayList<>();
		for(String tid : teams){
			//小队信息不存在了，直接删除
			CWTeamData tdata = CyborgWarRedis.getInstance().getCWTeamData(tid);
			if(Objects.isNull(tdata)){
				CyborgWarRedis.getInstance().removeCWGuildTeam(guildId, tid);
				continue;
			}
			tlist.add(tid);
		}
		CyborgWarService.guildTeams.put(guildId, tlist);
	}
	
	/**
	 * 工单号】：25080110363484188301
	【游戏名称】：红警OL
	【游戏账号】：478146112
	【OPENID/GOP】：FEF80FC9804EE3642D7A316393BA41AB
	【游戏大区】：QQ安卓-115区
	【问题描述】：玩家反馈由于迁服导致服务器崩溃而统一回档，回档后造成账号数据错误，机甲核心和星耀能量站等级被清零，还请@海燕 老师看一下
	 */
	private void fixData() {
		String playerId = "flc-12fv4s-4";
		Player player = GlobalData.getInstance().makesurePlayer(playerId);
		if (player == null) {
			HawkLog.logPrintln("fixData fail playerId : {} not found!!!!", playerId);
			return;
		}
		if (GlobalData.getInstance().isOnline(playerId)) {
			player.kickout(Status.Error.MIGRATE_FINISH_VALUE, true, "");
		}
		
		List<MechaCoreEntity> entities = HawkDBManager.getInstance().query("from MechaCoreEntity where playerId = ? and invalid = 0", playerId);
		if (entities.size() == 2) {
			for (MechaCoreEntity objData : entities) {
				if (objData.getId().equals("fij-3w6kd1-1")) {
					player.getData().getDataCache().update(PlayerDataKey.MechaCoreEntity, objData);
					HawkLog.logPrintln("fixData MechaCoreEntity playerId : {} OK!!!!", playerId);
				}
				if (objData.getId().equals("fij-418wdx-1")) {
					objData.delete();
				}
			}
		}

//		String[] keyArr = new String[] { "HeroArchivesEntity", "LifetimeCardEntity", "MedalEntity" };
//		immgration(playerId, keyArr);
		
		HeroArchivesEntity archivesEntity = player.getData().getHeroArchivesEntity();
		archivesEntity.setArchives("1058:4|1059:5|1065:4|1003:5|1004:2|1036:4|1037:5|1069:4|1042:5|1075:3|1077:3|1046:5|1018:5|1051:4|1021:5|1054:4");
		archivesEntity.afterRead();
		
		LifetimeCardEntity card = player.getData().getLifetimeCardEntity();
		card.setAdvancedEndTime(0);
		card.setWeekAwardTime(88);
		card.setCommonUnlockTime(1700756982939L);
		card.setFreeEndTime(1700874069753L);
		card.setMonthAwardTime(21);
		card.afterRead();
		
		MedalEntity medal = player.getData().getMedalEntity();
		medal.setDailyRefresh(0);
		medal.setRefreshCool(1753838504523L);
		medal.setRefreshStr("{\"rand\":[\"flg-132pw2-5\",\"flg-138a64-o\",\"flo-14nkt8-b\"],\"friend\":[\"flc-12fvlv-q\",\"flb-128ze4-i\",\"flo-14luvc-2\",\"fln-14jztu-q\",\"fli-13h8pu-5\",\"flc-12c5lz-w\",\"flo-14qur1-7\",\"flc-12foyf-6\",\"flc-12ep04-m\",\"flh-13az24-7\",\"flh-13f2k1-1a\",\"flo-14ra7v-c\",\"flo-14o4t3-5\",\"fli-13hxyy-k\"],\"enemy\":[\"flo-14r90o-e\",\"fli-13frf6-c\",\"fln-14iwn1-2\",\"flb-125s4d-i\",\"flh-13fb21-m\"]}");
		medal.setDailyReward(21);
		medal.setLastRefreshDay(212);
		medal.setCollectStr("[\"{\\\"rf\\\":10201,\\\"pf\\\":15900001,\\\"start\\\":1753884734172,\\\"index\\\":0,\\\"end\\\":1753899134172}\",\"{\\\"rf\\\":10201,\\\"pf\\\":15900001,\\\"start\\\":1753884734910,\\\"index\\\":1,\\\"end\\\":1753899134910}\",\"{\\\"rf\\\":10203,\\\"pf\\\":15900001,\\\"start\\\":1753884735821,\\\"index\\\":2,\\\"end\\\":1753899135821}\",\"{\\\"rf\\\":0,\\\"pf\\\":0,\\\"start\\\":0,\\\"index\\\":3,\\\"end\\\":0}\"]");
		medal.setExp(7200);
		medal.afterRead();
		HawkLog.logPrintln("fixData MechaCoreEntity playerId : {} OK!!!!", playerId);
	}
	
	private void starExploreMax(Map<String, String> params) {
		String playerId = "88q-2r0aib-i";
		if(params.containsKey("playerId")){
			playerId = params.get("playerId");
		}
		Player player = GlobalData.getInstance().makesurePlayer(playerId);
		if(player==null){
			HawkLog.logPrintln("fix1021 playerId is null  {}", playerId);
			return;
		}
		
		//星能探索
		CommanderEntity commander = player.getData().getCommanderEntity();
		String starExplore = "[{\"starId\":1,\"progress\":[{\"progressVal\":50,\"protressId\":1},{\"progressVal\":25,\"protressId\":2},{\"progressVal\":25,\"protressId\":3}]},{\"starId\":2,\"progress\":[{\"progressVal\":50,\"protressId\":1},{\"progressVal\":25,\"protressId\":2},{\"progressVal\":25,\"protressId\":3}]},{\"starId\":3,\"progress\":[{\"progressVal\":25,\"protressId\":1},{\"progressVal\":25,\"protressId\":2},{\"progressVal\":50,\"protressId\":3}]},{\"starId\":4,\"progress\":[{\"progressVal\":50,\"protressId\":1},{\"progressVal\":25,\"protressId\":2},{\"progressVal\":25,\"protressId\":3}]},{\"starId\":5,\"progress\":[{\"progressVal\":25,\"protressId\":1},{\"progressVal\":50,\"protressId\":2},{\"progressVal\":25,\"protressId\":3}]},{\"starId\":6,\"progress\":[{\"progressVal\":50,\"protressId\":1},{\"progressVal\":25,\"protressId\":2},{\"progressVal\":25,\"protressId\":3}]},{\"starId\":7,\"progress\":[{\"progressVal\":50,\"protressId\":1},{\"progressVal\":25,\"protressId\":2},{\"progressVal\":25,\"protressId\":3}]}]";
		String starExploreCollect = "[{\"fixAttr\":[{\"attrId\":11028,\"attrVal\":7000}],\"tmpAttr\":[],\"collectId\":1,\"upCount\":6000,\"randomAttr\":[{\"attrId\":11031,\"attrVal\":500}]},{\"fixAttr\":[{\"attrId\":11029,\"attrVal\":7000}],\"tmpAttr\":[],\"collectId\":2,\"upCount\":6000,\"randomAttr\":[{\"attrId\":11032,\"attrVal\":500}]},{\"fixAttr\":[{\"attrId\":11035,\"attrVal\":13000}],\"tmpAttr\":[{\"attrId\":11034,\"attrVal\":1000}],\"collectId\":3,\"upCount\":12000,\"randomAttr\":[{\"attrId\":11034,\"attrVal\":1000}]}]";
									
		ArmourStarExplores starExplores = ArmourStarExplores.unSerialize(commander, starExplore, starExploreCollect);
		commander.setStarExplores(starExplores);
		commander.beforeWrite();
		commander.notifyUpdate();
		player.getPush().syncArmourStarExploreInfo();
		HawkLog.logPrintln("fix1021 fix success  {}", playerId);
	}
	
	public void xiufuPlantTech(){
		String playerId = "7t5-ibp86-2d";
		Player player = GlobalData.getInstance().makesurePlayer(playerId);
		if(player==null){
			HawkLog.logPrintln("xiufuPlantTech playerId is null  {}", playerId);
			return;
		}
		List<PlantTechEntity> entitys = player.getData().getPlantTechEntities();
		List<PlantTechEntity> entitysRemove = new ArrayList<>();
		Map<Integer, PlantTechEntity> map = new HashMap<>();
		for(PlantTechEntity entity: entitys){
			if(map.containsKey(entity.getBuildType())){
				PlantTechEntity v1 = map.get(entity.getBuildType());
				if(v1.getCreateTime() > entity.getCreateTime()){
					entitysRemove.add(entity);
				}else{
					entitysRemove.add(v1);
				}
			}else{
				map.put(entity.getBuildType(), entity);
			}
		}
		
		entitys.retainAll(entitysRemove);
		HawkDBEntity.batchDelete(entitysRemove);
		player.kickout(Status.Error.MIGRATE_FINISH_VALUE, true, "");
		for(PlantTechEntity entity: entitysRemove){
			HawkLog.logPrintln("xiufuPlantTech fix success  {} del entity : {}", playerId, entity);
		}
	}
	
	public void fixPlantTechEntity(Map<String, String> params){
//		2025-11-29 13:56:24,735 INFO [http-nio-9001-exec-7] [HawkLog.java:45] - xiufuPlantTech fix success  7t5-ibp86-2d del entity : {"createTime":-1,"buildType":2010,"chipSerialized":"[\"{\\\"cfgId\\\":102010031}\",\"{\\\"cfgId\\\":102010032}\",\"{\\\"cfgId\\\":102010033}\",\"{\\\"cfgId\\\":102010034}\",\"{\\\"cfgId\\\":102010035}\"]","invalid":true,"updateTime":1690303639817,"id":"7sh-2p7p8z-7","cfgId":10201003,"class":"com.hawk.game.entity.PlantTechEntity","playerId":"7t5-ibp86-2d"}
//		2025-11-29 13:56:24,735 INFO [http-nio-9001-exec-7] [HawkLog.java:45] - xiufuPlantTech fix success  7t5-ibp86-2d del entity : {"createTime":-1,"buildType":2011,"chipSerialized":"[\"{\\\"cfgId\\\":102011001}\",\"{\\\"cfgId\\\":102011002}\",\"{\\\"cfgId\\\":102011003}\",\"{\\\"cfgId\\\":102011004}\",\"{\\\"cfgId\\\":102011005}\"]","invalid":true,"updateTime":1673282438476,"id":"7sh-2p7p8z-8","cfgId":10201100,"class":"com.hawk.game.entity.PlantTechEntity","playerId":"7t5-ibp86-2d"}
//		2025-11-29 13:56:24,735 INFO [http-nio-9001-exec-7] [HawkLog.java:45] - xiufuPlantTech fix success  7t5-ibp86-2d del entity : {"createTime":-1,"buildType":2013,"chipSerialized":"[\"{\\\"cfgId\\\":102013001}\",\"{\\\"cfgId\\\":102013002}\",\"{\\\"cfgId\\\":102013003}\",\"{\\\"cfgId\\\":102013004}\",\"{\\\"cfgId\\\":102013005}\"]","invalid":true,"updateTime":1673282374959,"id":"7sh-2p7p8z-6","cfgId":10201300,"class":"com.hawk.game.entity.PlantTechEntity","playerId":"7t5-ibp86-2d"}
//		2025-11-29 13:56:24,735 INFO [http-nio-9001-exec-7] [HawkLog.java:45] - xiufuPlantTech fix success  7t5-ibp86-2d del entity : {"createTime":-1,"buildType":2012,"chipSerialized":"[\"{\\\"cfgId\\\":102012031}\",\"{\\\"cfgId\\\":102012032}\",\"{\\\"cfgId\\\":102012033}\",\"{\\\"cfgId\\\":102012034}\",\"{\\\"cfgId\\\":102012035}\"]","invalid":true,"updateTime":1691272600266,"id":"7sh-2p7p8z-5","cfgId":10201203,"class":"com.hawk.game.entity.PlantTechEntity","playerId":"7t5-ibp86-2d"}
//		2025-11-29 13:56:24,735 INFO [http-nio-9001-exec-7] [HawkLog.java:45] - xiufuPlantTech fix success  7t5-ibp86-2d del entity : {"createTime":-1,"buildType":2014,"chipSerialized":"[\"{\\\"cfgId\\\":102014001}\",\"{\\\"cfgId\\\":102014002}\",\"{\\\"cfgId\\\":102014003}\",\"{\\\"cfgId\\\":102014004}\",\"{\\\"cfgId\\\":102014005}\"]","invalid":true,"updateTime":1673282387539,"id":"7sh-2p7p8z-4","cfgId":10201400,"class":"com.hawk.game.entity.PlantTechEntity","playerId":"7t5-ibp86-2d"}
		int cfgId2010 = 10201403;
		String str2010 = "[\"{\\\"cfgId\\\":102010031}\",\"{\\\"cfgId\\\":102010032}\",\"{\\\"cfgId\\\":102010033}\",\"{\\\"cfgId\\\":102010034}\",\"{\\\"cfgId\\\":102010035}\"]";
//		int cfgId2011 = 10201100;
		//		String str2011 = "[\"{\\\"cfgId\\\":102011021}\",\"{\\\"cfgId\\\":102011022}\",\"{\\\"cfgId\\\":102011023}\",\"{\\\"cfgId\\\":102011024}\",\"{\\\"cfgId\\\":102011025}\"]";
//		int cfgId2013 = 10201300;
		//		String str2013 = "[\"{\\\"cfgId\\\":102013001}\",\"{\\\"cfgId\\\":102013002}\",\"{\\\"cfgId\\\":102013003}\",\"{\\\"cfgId\\\":102013004}\",\"{\\\"cfgId\\\":102013005}\"]";
		int cfgId2012 = 10201203;
		String str2012 = "[\"{\\\"cfgId\\\":102012031}\",\"{\\\"cfgId\\\":102012032}\",\"{\\\"cfgId\\\":102012033}\",\"{\\\"cfgId\\\":102012034}\",\"{\\\"cfgId\\\":102012035}\"]";
//		int cfgId2014 = 10201400;
		//		String str2014 = "[\"{\\\"cfgId\\\":102014001}\",\"{\\\"cfgId\\\":102014002}\",\"{\\\"cfgId\\\":102014003}\",\"{\\\"cfgId\\\":102014004}\",\"{\\\"cfgId\\\":102014005}\"]";
		
		int cfgId2011 = 10201102;
		String str2011 = "[\"{\\\"cfgId\\\":102011021}\",\"{\\\"cfgId\\\":102011022}\",\"{\\\"cfgId\\\":102011023}\",\"{\\\"cfgId\\\":102011024}\",\"{\\\"cfgId\\\":102011025}\"]";
		int cfgId2013 = 10201301;
		String str2013 = "[\"{\\\"cfgId\\\":102013021}\",\"{\\\"cfgId\\\":102013012}\",\"{\\\"cfgId\\\":102013013}\",\"{\\\"cfgId\\\":102013014}\",\"{\\\"cfgId\\\":102013015}\"]";
		int cfgId2014 = 10201403;
		String str2014 = "[\"{\\\"cfgId\\\":102014031}\",\"{\\\"cfgId\\\":102014032}\",\"{\\\"cfgId\\\":102014033}\",\"{\\\"cfgId\\\":102014034}\",\"{\\\"cfgId\\\":102014035}\"]";
		
		
		String playerId = "7t5-ibp86-2d";
		if(params.containsKey("playerId")){
			playerId = params.get("playerId");
		}
		Player player = GlobalData.getInstance().makesurePlayer(playerId);
		if(player==null){
			HawkLog.logPrintln("xiufuPlantTech playerId is null  {}", playerId);
			return;
		}
		List<PlantTechEntity> entitys = player.getData().getPlantTechEntities();
		for(PlantTechEntity entity: entitys){
			if(entity.getBuildType() == 2010){
				entity.setChipSerialized(str2010);
				entity.setCfgId(cfgId2010);
				entity.afterRead();
				entity.getTechObj().notifyChange();
			}
			if(entity.getBuildType() == 2011){
				entity.setChipSerialized(str2011);
				entity.setCfgId(cfgId2011);
				entity.afterRead();
				entity.getTechObj().notifyChange();
			}
			if(entity.getBuildType() == 2013){
				entity.setChipSerialized(str2013);
				entity.setCfgId(cfgId2013);
				entity.afterRead();
				entity.getTechObj().notifyChange();
			}
			if(entity.getBuildType() == 2012){
				entity.setChipSerialized(str2012);
				entity.setCfgId(cfgId2012);
				entity.afterRead();
				entity.getTechObj().notifyChange();
			}
			if(entity.getBuildType() == 2014){
				entity.setChipSerialized(str2014);
				entity.setCfgId(cfgId2014);
				entity.afterRead();
				entity.getTechObj().notifyChange();
			}
		}
		player.kickout(Status.Error.MIGRATE_FINISH_VALUE, true, "");
		HawkLog.logPrintln("fixPlantTechEntity playerId is {}", playerId);
		
	}

	private static void fixXQHXSeasonTeamData(Map<String, String> params) {
		HawkLog.logPrintln("fixXQHXSeanTeamData start -> {}", GsConfig.getInstance().getServerId());
		String fixKey = params.get("key");
		if (HawkOSOperator.isEmptyString(fixKey)) {
			HawkLog.logPrintln("fixXQHXSeanTeamData key null -> {}", GsConfig.getInstance().getServerId());
			return;
		}
		String fixServer = params.get("fixServer");
		if (HawkOSOperator.isEmptyString(fixServer)) {
			HawkLog.logPrintln("fixXQHXSeanTeamData fixServer null -> {}", GsConfig.getInstance().getServerId());
			return;
		}
		if (!fixServer.equals(GsConfig.getInstance().getServerId())) {
			HawkLog.logPrintln("fixXQHXSeanTeamData fixServer not this -> {}", GsConfig.getInstance().getServerId());
			return;
		}
		Map<String, String> fixMsgMap = new HashMap<>();
		try {
			List<String> fixMsgList = new ArrayList<>();
			HawkOSOperator.readTextFileLines("tmp/xqhxFixMsg.txt", fixMsgList);
			for (String s : fixMsgList) {
				String[] split = s.split("\\|");
				if (split.length == 2) {
					fixMsgMap.put(split[0], split[1]);
				}
			}
		} catch (Exception e) {
			HawkException.catchException(e);
			return;
		}
		String redisKey = "fixXQHXSeasonTeamData-" + fixKey;
		HawkRedisSession redisSession = RedisProxy.getInstance().getRedisSession();
		boolean rlt = redisSession.setNx(redisKey, GsConfig.getInstance().getServerId());
		if (!rlt) {
			HawkLog.logPrintln("fixXQHXSeanTeamData fix already -> {}", GsConfig.getInstance().getServerId());
			return;
		}
		//修复老服数据
		for (Map.Entry<String, String> entry : fixMsgMap.entrySet()) {
			String teamId = entry.getKey();
			try {
				String json = RedisProxy.getInstance().getRedisSession().hGet(XQHXSeasonManager.getInstance().getDataKey(), teamId);
				XQHXSeasonData redisData = XQHXSeasonData.unSerialize(json);
				if (redisData == null) {
					HawkLog.logPrintln("fixXQHXSeanTeamData-old data is Null, teamId{}", teamId);
					continue;
				}
				int count = redisData.rankingLoseCnt + redisData.rankingWinCnt;
				if (count >= 1) {
					HawkLog.logPrintln("fixXQHXSeanTeamData-old count {} , teamId{} redisData {}", count, teamId, redisData.serialize());
				}
				HawkLog.logPrintln("fixXQHXSeanTeamData-teamData-begin,old, {}", redisData.serialize());
				if (entry.getValue().equals("win")) {
					HawkLog.logPrintln("fixXQHXSeanTeamData-teamData-win,old before, {}", redisData.serialize());
					redisData.rankingWinCnt += 1;
					redisData.rankingScore += 2000;
					HawkLog.logPrintln("fixXQHXSeanTeamData-teamData-win,old, {}", redisData.serialize());
				} else {
					HawkLog.logPrintln("fixXQHXSeanTeamData-teamData-lose,old before, {}", redisData.serialize());
					redisData.rankingLoseCnt += 1;
					redisData.rankingScore += 1000;
					HawkLog.logPrintln("fixXQHXSeanTeamData-teamData-lose,old, {}", redisData.serialize());
				}
				RedisProxy.getInstance().getRedisSession().hSet(XQHXSeasonManager.getInstance().getDataKey(), redisData.teamId, redisData.serialize());
				HawkLog.logPrintln("fixXQHXSeanTeamData-teamData-over,old, {}", redisData.serialize());
			} catch (Exception e) {
				HawkException.catchException(e);
				HawkLog.logPrintln("fixXQHXSeanTeamData-teamData-err,old, {}", teamId);
			}
		}
		try {
			// 刷排行榜
			XQHXSeasonManager.getInstance().loadAllJoinDatas();
			XQHXSeasonManager.getInstance().loadRankingRank(false);
		}catch (Exception e) {
			HawkException.catchException(e);
			HawkLog.logPrintln("fixXQHXSeanTeamData-loadRankError");
		}
	}
	private static void fixArmourImmortWithNoCore(int armourId){

	}
	/**
	 * 修复核心
	 * @param armourList
	 */
	private static void fixArmourImmortExchangeCore(PlayerData playerData,List<ArmourEntity> armourList){
		//找出没有锚点的不朽装备
		for (ArmourEntity armourNoCore : armourList) {
			if(armourNoCore.getImmort().isImmortCore()){
				continue;
			}
			if(!armourNoCore.getImmort().canImmortRedLevel()){
				continue;
			}
			ArmourCfg armourNoCoreCfg = HawkConfigManager.getInstance().getConfigByKey(ArmourCfg.class, armourNoCore.getArmourId());
			if (armourNoCoreCfg == null) {
				continue;
			}
			List<ArmourEntity> armourNoCoreList = new ArrayList<>();
			List<ArmourEntity> armourCoreList = new ArrayList<>();
			for (ArmourEntity armourCore : armourList) {
				ArmourCfg armourCoreCfg = HawkConfigManager.getInstance().getConfigByKey(ArmourCfg.class, armourCore.getArmourId());
				if (armourCoreCfg == null) {
					continue;
				}
				if(!armourCore.getImmort().isImmortCore()){
					continue;
				}
				if (armourCore.getArmourId() == armourNoCore.getArmourId()) {
					armourNoCoreList.add(armourCore);
				}
				if(armourCoreCfg.getPos() == armourNoCoreCfg.getPos()){
					List<ArmourEntity> coreWithNoArmourList = new ArrayList<>();
					for (ArmourEntity coreWithNoArmour : armourList) {
						if(coreWithNoArmour.getImmort().isImmortCore()){
							continue;
						}
						if(!coreWithNoArmour.getImmort().canImmortRedLevel()){
							continue;
						}
						if (coreWithNoArmour.getArmourId() == armourCore.getArmourId()) {
							coreWithNoArmourList.add(coreWithNoArmour);
						}
					}
					if (coreWithNoArmourList.isEmpty()) {
						armourCoreList.add(armourCore);
					}
				}
			}
			if (!armourNoCoreList.isEmpty()) {
				continue;
			}
			if(armourCoreList.isEmpty()){
				continue;
			}
			ArmourEntity armourCoreForFix = armourCoreList.get(0);
			//需要修复的锚点
			HawkLog.errPrintln("fixArmourImmortExchange-fixCoreForSure playerId:{},armour:{},core:{}",playerData.getPlayerId(),armourNoCore,armourCoreForFix);
			//修复数据
			int coreArmourId = armourCoreForFix.getArmourId();
			armourCoreForFix.setArmourId(armourNoCore.getArmourId());
			armourCoreForFix.getExtraAttrEff().clear();
			armourCoreForFix.getExtraAttrEff().addAll(armourNoCore.getExtraAttrEff());
			armourCoreForFix.getStarEff().clear();
			armourCoreForFix.getStarEff().addAll(armourNoCore.getStarEff());
			armourCoreForFix.getSkillEff().clear();
			armourCoreForFix.getSkillEff().addAll(armourNoCore.getSkillEff());
			armourCoreForFix.setLock(false);
			armourCoreForFix.notifyUpdate();
			HawkLog.errPrintln("fixArmourImmortExchange-fixCore playerId:{},coreArmourId:{},core:{},changeArmour:{}", playerData.getPlayerId(),
					coreArmourId, armourCoreForFix, armourNoCore);
		}
	}
	/**
	 * 修复共鸣
	 * @param armourList
	 */
	private static void fixArmourImmortExchangeEcho(PlayerData playerData,List<ArmourEntity> armourList){
		for (ArmourEntity armourEntity : armourList) {
			if(!armourEntity.getImmort().canImmortRedLevel()){
				continue;
			}
			if(armourEntity.getImmort().isImmortCore()){
				continue;
			}
			int armourId = armourEntity.getArmourId();
			ArmourCfg armourCfg = HawkConfigManager.getInstance().getConfigByKey(ArmourCfg.class, armourId);
			if (armourCfg == null) {
				continue;
			}
			int echoId = armourEntity.getImmort().getArmourImmortEntity().getEchoId();
			ArmourImmortEchoCfg echoCfg = HawkConfigManager.getInstance().getConfigByKey(ArmourImmortEchoCfg.class, echoId);
			if (echoCfg == null) {
				continue;
			}
			if (echoCfg.getType() == armourCfg.getArmourSuitId()) {
				continue;
			}
			ConfigIterator<ArmourImmortEchoCfg> echoCfgList = HawkConfigManager.getInstance().getConfigIterator(ArmourImmortEchoCfg.class);
			for (ArmourImmortEchoCfg armourImmortEchoCfg : echoCfgList) {
				if (armourImmortEchoCfg.getType() == armourCfg.getArmourSuitId() && armourImmortEchoCfg.getEchoType() == echoCfg.getEchoType()) {
					armourEntity.getImmort().getArmourImmortEntity().setEchoId(armourImmortEchoCfg.getId());
					HawkLog.errPrintln("fixArmourImmortExchange-fixEchoId playerId:{},armourId:{},oldEcho:{},changeEcho:{}", playerData.getPlayerId(),
							armourEntity.getId(), echoId, armourImmortEchoCfg.getId());
					armourEntity.notifyUpdate();
					break;
				}
			}
		}
	}
	private static void fixArmourImmortExchange() {
		try {
			Map<String, PlayerData> allPlayerData = GlobalData.getInstance().getAllPlayerData();
			for (PlayerData playerData : allPlayerData.values()) {
				List<ArmourEntity> armourList = playerData.getArmourEntityList();
				fixArmourImmortExchangeEcho(playerData,armourList);
				fixArmourImmortExchangeCore(playerData,armourList);
			}
		} catch (Exception e) {
			HawkException.catchException(e);
			HawkLog.errPrintln("fixArmourImmortExchange-fixArmourImmortExchangeError");
		}
	}
    private static void fixArmourImmortData() {
        try {
            Map<String, PlayerData> allPlayerData = GlobalData.getInstance().getAllPlayerData();
            for (PlayerData playerData : allPlayerData.values()) {
                List<ArmourEntity> armourList = playerData.getArmourEntityList();
                List<ArmourEntity> fixArmourList = new ArrayList<>();
                for (ArmourEntity armourEntity : armourList) {
                    if (armourEntity.getCreateTime() >= 1769039700000L && armourEntity.getCreateTime() <= 1769076000000L) {
                        fixArmourList.add(armourEntity);
                        HawkLog.logPrintln("fixArmourImmortData-playerId:{},armour:{}", playerData.getPlayerId(), armourEntity);
                    }
                }
				if (!fixArmourList.isEmpty()) {
					HawkDBEntity.batchDelete(fixArmourList);
					fixArmourList.stream().forEach(a -> a.setInvalid(false));
					HawkDBEntity.batchCreate(fixArmourList);
				}
            }
        } catch (Exception e) {
            HawkException.catchException(e);
            HawkLog.errPrintln("fixArmourImmortData-fixArmourError");
        }
    }
    private static void fixArmourImmortExcessData(Map<String, String> params) {
        try {
			String fixServer = params.get("fixServer");
			if (HawkOSOperator.isEmptyString(fixServer)) {
				HawkLog.logPrintln("fixArmourImmortExcessData fixServer null -> {}", GsConfig.getInstance().getServerId());
				return;
			}
			if (!fixServer.equals(GsConfig.getInstance().getServerId())) {
				HawkLog.logPrintln("fixArmourImmortExcessData fixServer not this -> {}", GsConfig.getInstance().getServerId());
				return;
			}
			List<String> fixPlayerArmourList = new ArrayList<>();
			try {
				HawkOSOperator.readTextFileLines("tmp/immortFixPlayer.txt", fixPlayerArmourList);
			} catch (Exception e) {
				HawkException.catchException(e);
				return;
			}
			for (String armourStr : fixPlayerArmourList) {
				String[] splitArmour = armourStr.split("\\|");
				if (splitArmour.length < 22) {
					continue;
				}
				String playerId = splitArmour[7];
				Player player = GlobalData.getInstance().makesurePlayer(playerId);
				if (player == null) {
					HawkLog.logPrintln("fixArmourImmortExcessData- playerId:{} is null", playerId);
					continue;
				}
				String armourUUid = splitArmour[splitArmour.length-1];
				ArmourEntity armourEntity = player.getData().getArmourEntity(armourUUid);
				if (armourEntity == null) {
					HawkLog.logPrintln("fixArmourImmortExcessData- player:{},armourUUid:{} is null",playerId, armourUUid);
					continue;
				}
				HawkLog.logPrintln("fixArmourImmortExcessData-original playerId:{} armourEntity:{}", playerId, armourEntity);
				String armourIdStr = splitArmour[11];
				if (!HawkOSOperator.isEmptyString(armourIdStr)) {
					int armourId = Integer.parseInt(armourIdStr);
					armourEntity.setArmourId(armourId);
				}
				String levelStr = splitArmour[12];
				if (!HawkOSOperator.isEmptyString(levelStr)) {
					int level = Integer.parseInt(levelStr);
					armourEntity.setLevel(level);
				}
				String qualityStr = splitArmour[13];
				if (!HawkOSOperator.isEmptyString(qualityStr)) {
					int quality = Integer.parseInt(qualityStr);
					armourEntity.setQuality(quality);
				}
				String starStr = splitArmour[14];
				if (!HawkOSOperator.isEmptyString(starStr)) {
					int star = Integer.parseInt(starStr);
					armourEntity.setStar(star);
				}
				String suitStr = splitArmour[16];
				if (!HawkOSOperator.isEmptyString(suitStr)) {
					Set<Integer> suitSet = SerializeHelper.stringToSet(Integer.class, suitStr, SerializeHelper.BETWEEN_ITEMS, null, new ConcurrentHashSet<>());
					armourEntity.getSuitSet().clear();
					for (Integer i : suitSet) {
						armourEntity.getSuitSet().add(i);
					}
				}
				String extraAttr = splitArmour[17];
				if (!HawkOSOperator.isEmptyString(extraAttr)) {
					armourEntity.setExtraAttr(extraAttr);
				}
				String starAttr = splitArmour[18];
				if (!HawkOSOperator.isEmptyString(starAttr)) {
					List<ArmourEffObject> starEff = SerializeHelper.stringToList(ArmourEffObject.class, starAttr, SerializeHelper.BETWEEN_ITEMS, SerializeHelper.ATTRIBUTE_SPLIT, new CopyOnWriteArrayList<>());
					armourEntity.getStarEff().clear();
					for (ArmourEffObject armourEffObject : starEff) {
						armourEntity.addStarEff(armourEffObject);
					}
				}
				String quantumStr = splitArmour[20];
				if (!HawkOSOperator.isEmptyString(quantumStr)) {
					int quantum = Integer.parseInt(quantumStr);
					armourEntity.setQuantum(quantum);
				}
				//不朽重置
				armourEntity.getImmort().getArmourImmortEntity().setEchoId(0);
				armourEntity.getImmort().getArmourImmortEntity().getInfinityEffMap().clear();
				armourEntity.getImmort().getArmourImmortEntity().setInfinityLevel(0);
				armourEntity.getImmort().getArmourImmortEntity().getPendingInfinityEffMap().clear();
				armourEntity.notifyUpdate();
				// 推单条装备信息
				player.getPush().syncArmourInfo(armourEntity);
				// 刷新作用号
				player.getEffect().resetEffectArmour(player);
				// 刷新战力
				player.refreshPowerElectric(LogConst.PowerChangeReason.ARMOUR_CHANGE);

				HawkLog.logPrintln("fixArmourImmortExcessData-fixArmour playerId:{},armour:{}", player.getId(), armourEntity);
				//重置洗炼等级
				CommanderEntity commanderEntity = player.getData().getCommanderEntity();
				CommanderObject commanderObject = commanderEntity.getCommanderObject();
				commanderObject.getImmortPerfectPityMap().clear();
				commanderObject.getImmortReshapeLevelMap().clear();
				commanderObject.getImmortEchoFloorsMap().clear();
				commanderObject.getImmortEchoPoolsMap().clear();
				HawkLog.logPrintln("fixArmourImmortExcessData-fixArmourRand playerId:{}", player.getId());
				//重置英雄星穹
				RiseSkillSettings riseSettings = commanderEntity.getRiseSettings();
				for (Const.SoldierType value : Const.SoldierType.values()) {
					RiseSkillPage skillPage = riseSettings.getSkillPage(value);
					skillPage.setLeftHero(0);
					skillPage.setRightHero(0);
				}
				HawkLog.logPrintln("fixArmourImmortExcessData-RiseSkillSettings playerId:{},risesettting:{}", player.getId(), riseSettings);
				commanderObject.notifyChange();
				player.sendProtocol(HawkProtocol.valueOf(HP.code2.HERO_RISE_SKILL_SYNC, commanderEntity.getRiseSettings().toPBObj().toBuilder()));
				//删除不朽核心
				List<ArmourEntity> armourEntityList = player.getData().getArmourEntityList();
				for (ArmourEntity entity : armourEntityList) {
					if(entity.getImmort().isImmortCore()){
						player.getData().removeArmourEntity(entity);
						entity.delete(true);
						HawkLog.logPrintln("fixArmourImmortExcessData-deleteArmourCore playerId:{},armour:{}", player.getId(), entity);
					}
				}
				player.getPush().syncAllArmourInfo();
				List<PlayerHero> allHero = player.getAllHero();
				for (PlayerHero hero : allHero) {
					hero.getHeroEntity().setRiseSerialized("");
					Field rise = HawkOSOperator.getClassField(hero, "rise");
					try {
						rise.set(hero, HeroRise.create(hero));
					} catch (Exception e) {
						HawkException.catchException(e);
					}
					hero.notifyChange();
					HawkLog.logPrintln("fixArmourImmortExcessData-hero playerId:{},herorise:{}", player.getId(), hero.getRise());
				}
				//回收不朽道具
				int itemId = 802006;
				int itemId2 = 21063077;
				int itemId3 = 21063076;
				int itemCount = player.getData().getItemNumByItemId(itemId);
				int itemCount2 = player.getData().getItemNumByItemId(itemId2);
				int itemCount3 = player.getData().getItemNumByItemId(itemId3);
				HawkLog.logPrintln("fixArmourImmortExcessData-resolveItem playerId:{},itemCount:{},itemCount2:{},itemCount3:{}", player.getId(), itemCount, itemCount2, itemCount3);
				ItemInfo itemInfo = new ItemInfo(30000, itemId, itemCount);
				ItemInfo itemInfo2 = new ItemInfo(30000, itemId2, itemCount2);
				ItemInfo itemInfo3 = new ItemInfo(30000, itemId3, itemCount3);
				ConsumeItems consumeItems = ConsumeItems.valueOf();
				consumeItems.addConsumeInfo(itemInfo, false);
				consumeItems.addConsumeInfo(itemInfo2, false);
				consumeItems.addConsumeInfo(itemInfo3, false);
				if (!consumeItems.checkConsume(player, 0)) {
					return;
				}
				consumeItems.consumeAndPush(player, Action.GM_EXPLOIT);
			}
        } catch (Exception e) {
            HawkException.catchException(e);
            HawkLog.errPrintln("fixArmourImmortExcessData-fixArmourExcessDataError");
        }
    }
}