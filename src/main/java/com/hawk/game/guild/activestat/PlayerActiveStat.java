package com.hawk.game.guild.activestat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.hawk.app.HawkApp;
import org.hawk.config.HawkConfigManager;
import org.hawk.log.HawkLog;
import org.hawk.os.HawkException;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkTime;
import org.hawk.tuple.HawkTuple2;

import com.hawk.game.config.CrossTimeCfg;
import com.hawk.game.config.CyborgWarTimeCfg;
import com.hawk.game.config.TiberiumSeasonTimeCfg;
import com.hawk.game.config.TiberiumTimeCfg;
import com.hawk.game.config.XQHXWarTimeCfg;
import com.hawk.game.entity.GuildMemberObject;
import com.hawk.game.global.RedisKey;
import com.hawk.game.global.RedisProxy;
import com.hawk.game.protocol.Const.GuildAuthority;
import com.hawk.game.protocol.GuildManager.ActiveStatItemPB;
import com.hawk.game.protocol.GuildManager.ActiveStatType;
import com.hawk.game.protocol.GuildManager.MemberActiveStatPB;
import com.hawk.game.service.GuildService;
import com.hawk.game.service.tblyTeam.TBLYSeasonService;

/**
 * 玩家（联盟成员）活跃统计对象
 * 
 * @author lating
 *
 */
public class PlayerActiveStat {
	/**
	 * 最近一期
	 */
	public static final int TERM_LATEST = 0;
	/**
	 * 最近一期的上一期
	 */
	public static final int TERM_LAST = 1;

	/**
	 * 玩家id
	 */
	private String playerId = "";
	/**
	 * 活跃统计信息
	 */
	private Map<Integer, ActiveStatInfo> statInfoMap = new ConcurrentHashMap<>();
	/**
	 * 最后一次更新数据的时间
	 */
	private AtomicLong updateTime = new AtomicLong(0);
	/**
	 * 最后一次保存更新的时间
	 */
	private AtomicLong saveTime = new AtomicLong(0);
	
	
	public static PlayerActiveStat create(String playerId) {
		PlayerActiveStat info = new PlayerActiveStat();
		info.playerId = playerId;
		return info;
	}
	
	protected void init(String statInfo) {
		try {
			if (HawkOSOperator.isEmptyString(statInfo)) {
				statInfo = RedisProxy.getInstance().getRedisSession().getString(redisKey());
				if (HawkOSOperator.isEmptyString(statInfo)) {
					return;
				}
			}
			for(String str : statInfo.split(",")) {
				try {
					ActiveStatInfo info = ActiveStatInfo.parseObj(str);
					int key = getMapKey(info.getStatType(), info.getTerm());
					statInfoMap.put(key, info);
				} catch (Exception e) {
					HawkException.catchException(e);
				}
			}
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	public String getPlayerId() {
		return playerId;
	}
	
	public String serialize() {
		StringJoiner sj = new StringJoiner(",");
		statInfoMap.values().stream().forEach(e -> sj.add(e.serialize()));
		return sj.toString();
	}
	
	public GuildMemberObject getGuildMemberObj() {
		GuildMemberObject obj = GuildService.getInstance().getGuildMemberObject(playerId);
		return obj;
	}
	
	private long getJoinGuildTime() {
		GuildMemberObject obj = getGuildMemberObj();
		if (obj == null) {
			return 0;
		}
		if(obj.getAuthority() == GuildAuthority.L5_VALUE){
			return GuildService.getInstance().getGuildCreateTime(obj.getGuildId());
		}
		return obj.getJoinGuildTime();
	}
	
	private int getMapKey(int type, int term) {
		return type * 100 + term;
	}
	
	/**
	 * 获取所有活跃信息统计对象
	 * @return
	 */
	public Map<Integer, ActiveStatInfo> getStatInfoMap() {
		return statInfoMap;
	}
	
	/**
	 * 获取活跃信息统计对象
	 * @param type
	 * @param term
	 * @return
	 */
	public ActiveStatInfo getStatInfo(int type, int term) {
		int key = getMapKey(type, term);
		return statInfoMap.get(key);
	}
	
	/**
	 * 更新活跃统计信息
	 * @param type     活跃统计类型
	 * @param realTerm 活跃统计任务对象对应的期数（没有期数概念的默认传0）
	 * @param score    活跃统计任务对象中所得积分（除了个人每日活跃任务积分是每次增量添加，其他都是传本期总分而非增量值）
	 */
	public void updateStatInfo(ActiveStatType type, int realTerm, long score) {
		int term = TERM_LATEST;
		ActiveStatInfo info = getStatInfo(type.getNumber(), term);
		if (info == null) {
			createStatInfo(type, term, score, realTerm);
			saveActiveStatInfo(type);
			HawkLog.logPrintln("player activeStat update new object, playerId: {}, type: {}, realTerm: {}, score: {}", playerId, type, realTerm, score);
			return;
		}
		
		boolean crossTerm = false;
		if (type == ActiveStatType.TYPE_PLAYER_DAILY) {
			crossTerm = !HawkTime.isSameWeek(info.getCreateTime(), HawkTime.getMillisecond());
		} else {
			crossTerm = info.getRealTerm() != realTerm;
		}

		if (crossTerm) {
			saveHistory(type.getNumber(), term);
			createStatInfo(type, term, score, realTerm);
		} else if (type == ActiveStatType.TYPE_PLAYER_DAILY){
			info.addScore(score);
		} else {
			info.setScore(score);
		}
		
		saveActiveStatInfo(type);
		HawkLog.logPrintln("player activeStat update crossTerm: {}, playerId: {}, type: {}, realTerm: {}, score: {}, newScore: {}", crossTerm, playerId, type, realTerm, score, info.getScore());
	}
	
	/**
	 * 创建新的对象
	 * @param type
	 * @param term
	 * @param score
	 * @param realTerm
	 * @return
	 */
	private ActiveStatInfo createStatInfo(ActiveStatType type, int term, long score, int realTerm) {
		ActiveStatInfo info = ActiveStatInfo.valueOf(type.getNumber(), term, score);
		info.setRealTerm(realTerm);
		int key = getMapKey(type.getNumber(), term);
		statInfoMap.put(key, info);
		return info;
	}
	
	/**
	 * 将当前期的对象转换成历史对象
	 * @param type
	 * @param term
	 */
	private void saveHistory(int type, int term) {
		ActiveStatInfo info = getStatInfo(type, term);
		if (info == null) {
			return;
		}
		int historyTerm = term + 1;
		ActiveStatInfo historyInfo = getStatInfo(type, historyTerm);
		if (historyInfo != null) {
			statInfoMap.remove(historyInfo);
		}
		info.setTerm(historyTerm);
		int key = getMapKey(type, historyTerm);
		statInfoMap.put(key, info);
	}
	
	/**
	 * 数据落地保存
	 */
	private void saveActiveStatInfo(ActiveStatType type) {
		long time = HawkApp.getInstance().getCurrentTime();
		if (type == ActiveStatType.TYPE_PLAYER_DAILY && time - saveTime.get() < 5000L) {
			updateTime.set(time);
			return;
		}
		RedisProxy.getInstance().getRedisSession().setString(redisKey(), serialize());
		saveTime.set(time);
	}
	
	/**
	 * tick保存
	 */
	public void saveActiveStatInfo() {
		long time = HawkApp.getInstance().getCurrentTime();
		if (updateTime.get() > saveTime.get() && time - saveTime.get() > 5000L) {
			RedisProxy.getInstance().getRedisSession().setString(redisKey(), serialize());
			saveTime.set(time);
		}
	}
	
	private String redisKey() {
		return RedisKey.PLAYER_ACTIVE_STAT_INFO + playerId;
	}

	/**
	 * 构建信息（通过时间判断，就不需要考虑跨周重置了）
	 * @return
	 */
	public MemberActiveStatPB buildActiveStatInfo() {
		long timeNow = HawkTime.getMillisecond();
		long weekZero = HawkTime.getFirstDayOfWeek(timeNow).getTime();
		MemberActiveStatPB.Builder builder = MemberActiveStatPB.newBuilder();
		builder.setPlayerId(playerId);
		builder.setJoinGuildTime(getJoinGuildTime());
		for (ActiveStatInfo info : statInfoMap.values()) {
			long weekZero1 = HawkTime.getFirstDayOfWeek(info.getCreateTime()).getTime();
			if (weekZero1 == weekZero) {
				builder.addCurrWeekStat(info.toBuilder()); //本周
			} else if (weekZero - weekZero1 <= HawkTime.DAY_MILLI_SECONDS * 7) {
				builder.addLastWeekStat(info.toBuilder()); //上周
			}
		}
		
		buildStatTermInfo(builder, timeNow);
		return builder.build();
	}
	
	private void buildStatTermInfo(MemberActiveStatPB.Builder builder, long timeNow) {
		List<ActiveStatInfo> statInfoList = new ArrayList<>();
		for (ActiveStatType statType : ActiveStatType.values()) {
			if (statType == ActiveStatType.TYPE_PLAYER_DAILY) {
				continue;
			}
			if (statType == ActiveStatType.TYPE_TBLY_SEASON || statType == ActiveStatType.TYPE_TBLY) {
				ActiveStatInfo info1 = statInfoMap.get(getMapKey(statType.getNumber(), TERM_LATEST));
				if (info1 != null) {
					statInfoList.add(info1);
				}
				ActiveStatInfo info2 = statInfoMap.get(getMapKey(statType.getNumber(), TERM_LAST));
				if (info2 != null) {
					statInfoList.add(info2);
				}
				continue;
			}
			
			ActiveStatItemPB.Builder pbBuilder = ActiveStatItemPB.newBuilder();
			pbBuilder.setStatType(statType.getNumber());
			int key = getMapKey(statType.getNumber(), TERM_LATEST);
			ActiveStatInfo info = statInfoMap.get(key);
			if (info == null) {
				pbBuilder.setScore(0);
				pbBuilder.setLastTermScore(0);
				builder.addTermStat(pbBuilder);
				continue;
			}
			
			long nextTermEndTime = getTermEndTime(statType.getNumber(), info.getRealTerm() + 1);
			int score = info.getScore() >= Integer.MAX_VALUE ? Integer.MAX_VALUE-1 : (int)info.getScore();
			//下一期已经过了
			if (nextTermEndTime < timeNow) {
				pbBuilder.setScore(0);
				//看下下期，决定上上期的数据
				nextTermEndTime = getTermEndTime(statType.getNumber(), info.getRealTerm() + 2);
				pbBuilder.setLastTermScore(nextTermEndTime >= timeNow ? score : 0);
			} else {
				//下一期还没到，那上一期有数据
				pbBuilder.setScore(score);
				int lastTermKey = getMapKey(statType.getNumber(), TERM_LAST);
				ActiveStatInfo lastTermInfo = statInfoMap.get(lastTermKey);
				if (lastTermInfo != null && lastTermInfo.getRealTerm() + 1 == info.getRealTerm()) {
					score = lastTermInfo.getScore() >= Integer.MAX_VALUE ? Integer.MAX_VALUE-1 : (int)lastTermInfo.getScore();
					pbBuilder.setLastTermScore(score);
				} else {
					pbBuilder.setLastTermScore(0);
				}
			}
			builder.addTermStat(pbBuilder);
		}
		
		HawkTuple2<ActiveStatInfo, ActiveStatInfo> tuple = TBLYConfigInfo.selectStatInfo(statInfoList, timeNow);
		ActiveStatItemPB.Builder pbBuilder = ActiveStatItemPB.newBuilder();
		pbBuilder.setStatType(ActiveStatType.TYPE_TBLY_VALUE);
		if (tuple.first != null) {
			int score = tuple.first.getScore() >= Integer.MAX_VALUE ? Integer.MAX_VALUE-1 : (int)tuple.first.getScore();
			pbBuilder.setScore(score);
		} else {
			pbBuilder.setScore(0);
		}
		
		if (tuple.second != null) {
			int score = tuple.second.getScore() >= Integer.MAX_VALUE ? Integer.MAX_VALUE-1 : (int)tuple.second.getScore();
			pbBuilder.setLastTermScore(score);
		} else {
			pbBuilder.setLastTermScore(0);
		}
		builder.addTermStat(pbBuilder);
	}
	
	private long getTermEndTime(int type, int termId) {
		switch (type) {
			case ActiveStatType.TYPE_CYBOR_VALUE: {
				CyborgWarTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(CyborgWarTimeCfg.class, termId);
				if (timeCfg != null) {
					return timeCfg.getWarEndTimeValue();
				}
			}
			case ActiveStatType.TYPE_XQHX_VALUE: {
				XQHXWarTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(XQHXWarTimeCfg.class, termId);
				if (timeCfg != null) {
					return timeCfg.getEndTime();
				}
			}
			case ActiveStatType.TYPE_CROSS_VALUE: {
				CrossTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(CrossTimeCfg.class, termId);
				if (timeCfg != null) {
					return timeCfg.getEndTimeValue();
				}
			}
			case ActiveStatType.TYPE_TBLY_VALUE: {
				TiberiumTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(TiberiumTimeCfg.class, termId);
				if (timeCfg != null) {
					return timeCfg.getWarEndTimeValue();
				}
			}
			case ActiveStatType.TYPE_TBLY_SEASON_VALUE: {
				TiberiumSeasonTimeCfg timeCfg = TBLYSeasonService.getInstance().getTimeCfgBySeasonAndTermId(termId/1000, termId%1000);
				if (timeCfg != null) {
					return timeCfg.getWarEndTimeValue();
				}
			}
		}
		return 0;
	}
	
}
