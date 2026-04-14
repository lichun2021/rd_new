package com.hawk.game.activity.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hawk.config.HawkConfigManager;
import org.hawk.config.iterator.ConfigIterator;
import org.hawk.log.HawkLog;
import org.hawk.net.protocol.HawkProtocol;
import org.hawk.os.HawkException;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkTime;

import com.alibaba.fastjson.JSONObject;
import com.hawk.common.AccountRoleInfo;
import com.hawk.common.IDIPBanInfo;
import com.hawk.game.GsConfig;
import com.hawk.game.config.ImmgrationActivityCfg;
import com.hawk.game.crossproxy.CrossProxy;
import com.hawk.game.global.GlobalData;
import com.hawk.game.global.RedisProxy;
import com.hawk.game.module.PlayerImmgrationModule;
import com.hawk.game.module.ann7party384.Ann7Party384Service;
import com.hawk.game.player.Player;
import com.hawk.game.player.cache.PlayerDataSerializer;
import com.hawk.game.protocol.Activity;
import com.hawk.game.protocol.Const;
import com.hawk.game.protocol.HP;
import com.hawk.game.protocol.Immgration;
import com.hawk.game.protocol.Status;
import com.hawk.game.service.ImmgrationService;
import com.hawk.game.service.RelationService;
import com.hawk.game.tsssdk.GameMsgCategory;
import com.hawk.game.tsssdk.GameTssService;
import com.hawk.game.util.GsConst;
import com.hawk.game.world.WorldMarchService;

public class ChangeServerActHelper {

	public static Activity.ChangeServerActivityCondition.Builder getChangeServerActivityCondition(String playerId, Map<String, String> toServerIdMap, 
			String tarServerId, Activity.ChangeServerActivityConditionType type) {
		Activity.ChangeServerActivityCondition.Builder builder = Activity.ChangeServerActivityCondition.newBuilder();
		Player player = GlobalData.getInstance().makesurePlayer(playerId);
		if(player == null){
			builder.setIsDone(false);
			return builder;
		}
		builder.setType(type);
		switch (type){
			case CHANGE_SVR_GUILD:{
				if (player.hasGuild()) {
					builder.setIsDone(false);
				}else {
					builder.setIsDone(true);
				}
				return builder;
			}
			case CHANGE_SVR_COLLEGE:{
				if (player.hasCollege()) {
					builder.setIsDone(false);
				}else {
					builder.setIsDone(true);
				}
				return builder;
			}
			case CHANGE_SVR_MARCH:{
				if (WorldMarchService.getInstance().getPlayerMarchCount(player.getId()) > 0) {
					builder.setIsDone(false);
				}else {
					builder.setIsDone(true);
				}
				return builder;
			}
			case CHANGE_SVR_ANN7_PARTY_ROOM:{
				if (Ann7Party384Service.getInstance().playerInRoom(player.getId())) {
					builder.setIsDone(false);
				}else {
					builder.setIsDone(true);
				}
				return builder;
			}
			case CHANGE_SVR_BUILDING_QUEUE:{
				if (player.getData().getQueueEntitiesByType(Const.QueueType.BUILDING_QUEUE_VALUE).size() > 0) {
					builder.setIsDone(false);
				}else {
					builder.setIsDone(true);
				}
				return builder;
			}
			case CHANGE_SVR_SCIENCE_QUEUE:{
				if (player.getData().getQueueEntitiesByType(Const.QueueType.SCIENCE_QUEUE_VALUE).size() > 0) {
					builder.setIsDone(false);
				}else {
					builder.setIsDone(true);
				}
				return builder;
			}
			case CHANGE_SVR_SOILDER_QUEUE:{
				if (player.getData().getQueueEntitiesByType(Const.QueueType.SOILDER_QUEUE_VALUE).size() > 0) {
					builder.setIsDone(false);
				}else {
					builder.setIsDone(true);
				}
				return builder;
			}
			case CHANGE_SVR_CURE_QUEUE:{
				if (player.getData().getQueueEntitiesByType(Const.QueueType.CURE_QUEUE_VALUE).size() > 0) {
					builder.setIsDone(false);
				}else {
					builder.setIsDone(true);
				}
				return builder;
			}
			case CHANGE_SVR_EQUIP_RESEARCH:{
				if (player.getData().getQueueEntitiesByType(Const.QueueType.EQUIP_RESEARCH_QUEUE_VALUE).size() > 0) {
					builder.setIsDone(false);
				}else {
					builder.setIsDone(true);
				}
				return builder;
			}
			case CHANGE_SVR_GUARDER:{
				if (RelationService.getInstance().hasGuarder(player.getId())) {
					builder.setIsDone(false);
				}else {
					builder.setIsDone(true);
				}
				return builder;
			}
			case CHANGE_SVR_CROSS:{
				if (player.isCsPlayer()) {
					builder.setIsDone(false);
				}else {
					builder.setIsDone(true);
				}
				return builder;
			}
			case CHANGE_SVR_ACCOUNT:{
				Set<String> haveSet = new HashSet<>();
				Set<String> canSet = new HashSet<>();
				Set<String> result = new HashSet<>();
				Map<String, AccountRoleInfo> accountRoleInfos = GlobalData.getInstance().getPlayerAccountInfos(player.getOpenId());
				for (AccountRoleInfo accountRole : accountRoleInfos.values()) {
					haveSet.add(accountRole.getServerId());
				}
				for(String toServerId : toServerIdMap.keySet()){
					String toMainServerId = toServerIdMap.get(toServerId);
					if (toMainServerId.equals(tarServerId)) {
						canSet.add(toServerId);
					}
				}
				result.addAll(canSet);
				result.removeAll(haveSet);
				if(result.size()<=0){
					builder.setIsDone(false);
					return builder;
				}
				builder.setIsDone(true);
				return builder;
			}
			case CHANGE_SVR_CROSS_TECH_QUEUE:{
				if (player.getData().getQueueEntitiesByType(Const.QueueType.CROSS_TECH_QUEUE_VALUE).size() > 0) {
					builder.setIsDone(false);
				}else {
					builder.setIsDone(true);
				}
				return builder;
			}
			case CHANGE_SVR_PLANT_SCIENCE_QUEUE:{
				if (player.getData().getQueueEntitiesByType(Const.QueueType.PLANT_SCIENCE_QUEUE_VALUE).size() > 0) {
					builder.setIsDone(false);
				}else {
					builder.setIsDone(true);
				}
				return builder;
			}
			case CHANGE_SVR_PLANT_ADVANCE_QUEUE:{
				if (player.getData().getQueueEntitiesByType(Const.QueueType.PLANT_ADVANCE_QUEUE_VALUE).size() > 0) {
					builder.setIsDone(false);
				}else {
					builder.setIsDone(true);
				}
				return builder;
			}
			case CHANGE_SVR_CURE_PLANT_QUEUE:{
				if (player.getData().getQueueEntitiesByType(Const.QueueType.CURE_PLANT_QUEUE_VALUE).size() > 0) {
					builder.setIsDone(false);
				}else {
					builder.setIsDone(true);
				}
				return builder;
			}
			case CHANGE_SVR_NATIONAL_HOSPITAL_RECOVER:{
				if (player.getData().getQueueEntitiesByType(Const.QueueType.NATIONAL_HOSPITAL_RECOVER_VALUE).size() > 0) {
					builder.setIsDone(false);
				}else {
					builder.setIsDone(true);
				}
				return builder;
			}
			default:{
				builder.setIsDone(false);
				return builder;
			}
		}
	}

	public static void onChangeServerSearch(String playerId, int protoType, String name, int type) {
		Player player = GlobalData.getInstance().makesurePlayer(playerId);
		if(HawkOSOperator.isEmptyString(name)){
			player.sendError(protoType, Status.SysError.PARAMS_INVALID, 0);
			return;
		}
		// 禁言玩家推送禁言提示
		if (player.getEntity().getSilentTime() > HawkTime.getMillisecond()) {
			IDIPBanInfo banInfo = RedisProxy.getInstance().getIDIPBanInfo(player.getId(), GsConst.IDIPBanType.BAN_SEND_MSG);
			if (banInfo != null) {
				Activity.ChangeServerActivitySearchResp.Builder response = Activity.ChangeServerActivitySearchResp.newBuilder();
				response.setMsg(banInfo.getBanMsg());
				player.sendProtocol(HawkProtocol.valueOf(HP.code2.CHANGE_SVR_ACTIVITY_SEARCH_RESP, response));
				return;
			}
		}
		GameTssService.getInstance().wordUicChatFilter(player, name,
				com.hawk.game.protocol.Player.MsgCategory.CHANGE_SVR_SEARCH_MEMBER.getNumber(), GameMsgCategory.CHANGE_SVR_SEARCH_MEMBER,
				String.valueOf(type), null, protoType);
	}

	public static String getRealChangeServerId(String playerId, String tarServerId, Map<String, String> toServerIdMap){
		Player player = GlobalData.getInstance().makesurePlayer(playerId);
		Set<String> haveSet = new HashSet<>();
		Set<String> canSet = new HashSet<>();
		Set<String> result = new HashSet<>();
		Map<String, AccountRoleInfo> accountRoleInfos = GlobalData.getInstance().getPlayerAccountInfos(player.getOpenId());
		for (AccountRoleInfo accountRole : accountRoleInfos.values()) {
			haveSet.add(accountRole.getServerId());
		}
		for(String toServerId : toServerIdMap.keySet()){
			String toMainServerId = toServerIdMap.get(toServerId);
			if (toMainServerId.equals(tarServerId)) {
				canSet.add(toServerId);
			}
		}
		result.addAll(canSet);
		result.removeAll(haveSet);
		if(result.size()<=0){
			return "";
		}
		List<String> canIds = new ArrayList<String>(result);
		return canIds.get(0);
	}

	public static boolean onChangeServer(String playerId, String tarServerId, Map<String, String> toServerIdMap) {
		// 拦截下
		if (tarServerId.equals(GsConfig.getInstance().getServerId())) {
			HawkLog.logPrintln("on change server error, can not change to own server, playerId:{}, tarServerId:{}", playerId, tarServerId);
			return false;
		}
		
		HawkLog.logPrintln("player immgration, onImmgration begin, playerId:{}, tarServerId:{}", playerId, tarServerId);
		Player player = GlobalData.getInstance().makesurePlayer(playerId);
		String serverId = getRealChangeServerId(playerId, tarServerId, toServerIdMap);
		if(HawkOSOperator.isEmptyString(serverId)){
			return false;
		}
		Map<String, AccountRoleInfo> accountRoleInfos = GlobalData.getInstance().getPlayerAccountInfos(player.getOpenId());
		for (AccountRoleInfo accountRole : accountRoleInfos.values()) {
			if (accountRole.getServerId().equals(serverId)){
				return false;
			}
		}
		HawkLog.logPrintln("player immgration, onImmgration begin, playerId:{}, serverId:{}", playerId, serverId);
		PlayerImmgrationModule module = player.getModule(GsConst.ModuleType.IMMGRATION);

//		// 迁服前检查
//		if (!module.checkBeforeImmgration(tarServerId)) {
//			return false;
//		}
		HawkLog.logPrintln("player immgration, onImmgration, flush to redis begin, playerId:{}", player.getId());

		try {
			int termId = ImmgrationService.getInstance().getImmgrationActivityTermId();
			JSONObject immgrationLog = new JSONObject();
			immgrationLog.put("playerId", player.getId());
			immgrationLog.put("fromServer", GsConfig.getInstance().getServerId());
			immgrationLog.put("tarServer", serverId);
			immgrationLog.put("time", HawkTime.formatNowTime());
			immgrationLog.put("puid", player.getPuid());
			RedisProxy.getInstance().updateImmgrationRecord(termId, player.getId(), immgrationLog.toJSONString());
			RedisProxy.getInstance().addPlayerImmgrationLog(immgrationLog);
		} catch (Exception e) {
			HawkException.catchException(e);
		}

		//把数据刷到redis里面
		try {
			boolean flushToRedis = PlayerDataSerializer.flushToRedis(player.getData().getDataCache(), null, false);
			if (!flushToRedis) {
				return false;
			}
			// 序列化活动数据
			ConfigIterator<ImmgrationActivityCfg> cfgIter = HawkConfigManager.getInstance().getConfigIterator(ImmgrationActivityCfg.class);
			while (cfgIter.hasNext()) {
				ImmgrationActivityCfg cfg = cfgIter.next();
				if (module.flushActivityToRedis(player.getId(), cfg.getActivityId())) {
					HawkLog.logPrintln("player immgration, onImmgration, flush activity to redis, playerId:{}, activityId:{}", player.getId(), cfg.getActivityId());
				}
			}
		} catch (Exception e) {
			HawkException.catchException(e);
			return false;
		}

		HawkLog.logPrintln("player immgration, onImmgration, flush to redis end, playerId:{}", player.getId());

		// 通知目标服
		Immgration.ImmgrationServerReq.Builder builder = Immgration.ImmgrationServerReq.newBuilder();
		builder.setPlayerId(playerId);
		builder.setTarServerId(serverId);
		HawkProtocol protocol = HawkProtocol.valueOf(HP.code2.IMMGRATION_SERVER_REQ_VALUE, builder);
		CrossProxy.getInstance().sendNotify(protocol, tarServerId, player.getId(), null);
		HawkLog.logPrintln("player immgration, onImmgration, send notify, playerId:{}, tarServerId:{}", player.getId(), tarServerId);
		return true;
	}
}
