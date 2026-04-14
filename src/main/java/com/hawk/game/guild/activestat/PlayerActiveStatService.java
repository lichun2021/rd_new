package com.hawk.game.guild.activestat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hawk.log.HawkLog;
import org.hawk.net.protocol.HawkProtocol;
import org.hawk.os.HawkException;
import org.hawk.os.HawkOSOperator;

import com.hawk.game.crossproxy.CrossProxy;
import com.hawk.game.crossproxy.CrossService;
import com.hawk.game.global.RedisKey;
import com.hawk.game.global.RedisProxy;
import com.hawk.game.player.Player;
import com.hawk.game.protocol.GuildManager.ActiveStatCrossPB;
import com.hawk.game.protocol.GuildManager.ActiveStatInfoPB;
import com.hawk.game.protocol.GuildManager.ActiveStatType;
import com.hawk.game.protocol.GuildManager.AuthId;
import com.hawk.game.protocol.GuildManager.MemberActiveStatPB;
import com.hawk.game.protocol.HP;
import com.hawk.game.service.GuildService;
import redis.clients.jedis.Jedis;

/**
 * 玩家（联盟成员）活跃统计服务类
 * 
 * @author lating
 *
 */
public class PlayerActiveStatService {
	/**
	 * 玩家活跃统计对象
	 */
	private Map<String, PlayerActiveStat> playerActiveStatMap = new ConcurrentHashMap<>();
	/**
	 * 单例
	 */
	private static PlayerActiveStatService instance = new PlayerActiveStatService();
	
	private PlayerActiveStatService() {
	}
	
	public static PlayerActiveStatService getInstance() {
		return instance;
	}
	
	/**
	 * 获取玩家活跃统计对象
	 * @param playerId
	 * @return
	 */
	private PlayerActiveStat getPlayerActiveStat(String playerId) {
		PlayerActiveStat statInfo = playerActiveStatMap.get(playerId);
		if (statInfo == null) {
			statInfo = PlayerActiveStat.create(playerId);
			statInfo.init(null);
			playerActiveStatMap.putIfAbsent(playerId, statInfo);
			statInfo = playerActiveStatMap.get(playerId);
		}
		return statInfo;
	}
	
	/**
	 * tick保存玩家活跃统计信息（主要是为了防止类似军需处连点操作而触发短时间内频繁保存数据）
	 * @param playerId
	 */
	public void savePlayerActiveStatInfo(String playerId) {
		if (CrossService.getInstance().isImmigrationPlayer(playerId)) {
			return;
		}
		PlayerActiveStat statInfo = getPlayerActiveStat(playerId);
		statInfo.saveActiveStatInfo();
	}
	
	/**
	 * 更新玩家活跃统计信息
	 * @param playerId
	 * @param type     活跃统计类型
	 * @param termId   活跃统计任务对象对应的期数（没有期数概念的默认传0）
	 * @param score    活跃统计任务对象中所得积分（除了 type=ActiveStatType.TYPE_PLAYER_DAILY 时是每次增量添加，其他都是传本期总分而非增量值）
	 */
	public void updateActiveStatInfo(String playerId, ActiveStatType type, int termId, long score) {
		//回传到源服执行
		if (CrossService.getInstance().isImmigrationPlayer(playerId)) {
			crossServerNotify(playerId, type, termId, score);
			return;
		}
		
		try {
			if (type != ActiveStatType.TYPE_PLAYER_DAILY && termId <= 0) {
				throw new RuntimeException("termId param error -> " + termId);
			}
			PlayerActiveStat statInfo = getPlayerActiveStat(playerId);
			statInfo.updateStatInfo(type, termId, score);
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	private void crossServerNotify(String playerId, ActiveStatType type, int termId, long score) {
		HawkLog.logPrintln("updateActiveStatInfo crossServerNotify, playerId: {}, type: {}, termId: {}, score: {}", playerId, type, termId, score);
		String serverId = CrossService.getInstance().getImmigrationPlayerServerId(playerId);
		ActiveStatCrossPB.Builder builder = ActiveStatCrossPB.newBuilder();
		builder.setPlayerId(playerId);
		builder.setStatType(type);
		builder.setTermId(termId);
		builder.setScore(score);
		HawkProtocol protocol = HawkProtocol.valueOf(HP.code2.GUILD_ACTIVE_STAT_CS_NOTIFY_VALUE, builder);
		CrossProxy.getInstance().sendNotify(protocol, serverId, playerId, null);
	}
	
	/**
	 * 构建联盟成员活跃统计信息数据
	 * @param player
	 */
	public void syncGuildActiveStatInfo(Player player) {
		if (!player.hasGuild()) {
			return;
		}
		
		//只有盟主和5阶玩家有权限看数据
		if (!GuildService.getInstance().checkGuildAuthority(player.getId(), AuthId.ACTIVE_STAT_AUTH)) {
			return;
		}
		
		List<String> activeStatEmptyList = new ArrayList<>();
		ActiveStatInfoPB.Builder syncBuilder = ActiveStatInfoPB.newBuilder();
		for (String playerId : GuildService.getInstance().getGuildMembers(player.getGuildId())) {
			try {
				PlayerActiveStat statInfo = playerActiveStatMap.get(playerId);
				if (statInfo == null) {
					activeStatEmptyList.add(playerId);
					continue;
				}
				MemberActiveStatPB builder = statInfo.buildActiveStatInfo();
				syncBuilder.addMemberStatInfo(builder);
			} catch (Exception e) {
				HawkException.catchException(e);
			}
		}
		
		if (!activeStatEmptyList.isEmpty()) {
			activeStatInitBatch(activeStatEmptyList, syncBuilder);
		}
		player.sendProtocol(HawkProtocol.valueOf(HP.code2.GUILD_ACTIVE_STAT_INFO_S_VALUE, syncBuilder));
	}
	
	/**
	 * 活跃统计信息批量初始化
	 * @param activeStatEmptyList
	 * @param syncBuilder
	 */
	private void activeStatInitBatch(List<String> activeStatEmptyList, ActiveStatInfoPB.Builder syncBuilder) {
		String[] keys = new String[activeStatEmptyList.size()];
		for (int i = 0; i < activeStatEmptyList.size(); i++) {
			keys[i] = RedisKey.PLAYER_ACTIVE_STAT_INFO + activeStatEmptyList.get(i);
		}
		
		List<String> statInfoList = new ArrayList<>();
		try (Jedis jedis = RedisProxy.getInstance().getRedisSession().getJedis()) {
			statInfoList = jedis.mget(keys);
		} catch (Exception e) {
			HawkException.catchException(e);
		}
		
		for (int i = 0; i < statInfoList.size(); i++) {
			String playerId = activeStatEmptyList.get(i), statInfo = statInfoList.get(i);
			try {
				PlayerActiveStat statInfoObj = PlayerActiveStat.create(playerId);
				if (!HawkOSOperator.isEmptyString(statInfo)) {
					statInfoObj.init(statInfo);
				}
				playerActiveStatMap.putIfAbsent(playerId, statInfoObj);
				statInfoObj = playerActiveStatMap.get(playerId);
				MemberActiveStatPB builder = statInfoObj.buildActiveStatInfo();
				syncBuilder.addMemberStatInfo(builder);
			} catch (Exception e) {
				HawkException.catchException(e);
			}
		}
	}
	
}
