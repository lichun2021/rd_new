package com.hawk.game.module.lianmengyqzz.march.service.state;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.hawk.config.HawkConfigManager;
import org.hawk.log.HawkLog;
import org.hawk.os.HawkException;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkTime;

import com.hawk.game.GsConfig;
import com.hawk.game.global.GlobalData;
import com.hawk.game.item.ItemInfo;
import com.hawk.game.module.lianmengyqzz.battleroom.cfg.YQZZBattleCfg;
import com.hawk.game.module.lianmengyqzz.march.cfg.YQZZGuildRankAwardCfg;
import com.hawk.game.module.lianmengyqzz.march.cfg.YQZZPlayerRankAwardCfg;
import com.hawk.game.module.lianmengyqzz.march.cfg.YQZZTimeCfg;
import com.hawk.game.module.lianmengyqzz.march.cfg.YQZZWarConstCfg;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZBattleData;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZBattleData.YQZZGuildGameData;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZBattleData.YQZZPlayerGameData;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZGameData;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZGuildRewardData;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZJoinServer;
import com.hawk.game.module.lianmengyqzz.march.data.local.YQZZRecordData;
import com.hawk.game.module.lianmengyqzz.march.data.local.YQZZStatisticsData;
import com.hawk.game.module.lianmengyqzz.march.service.YQZZConst.YQZZActivityJoinState;
import com.hawk.game.module.lianmengyqzz.march.service.YQZZConst.YQZZActivityState;
import com.hawk.game.module.lianmengyqzz.march.service.YQZZMatchService;
import com.hawk.game.player.Player;
import com.hawk.game.protocol.Const.MailRewardStatus;
import com.hawk.game.protocol.Const.PlayerAttr;
import com.hawk.game.protocol.MailConst.MailId;
import com.hawk.game.service.mail.GuildMailService;
import com.hawk.game.service.mail.MailParames;
import com.hawk.game.service.mail.SystemMailService;
import com.hawk.game.util.LogUtil;
import com.hawk.log.Action;

public class YQZZState310Reward  extends IYQZZServiceState {

	private long lastTickTime = 0;
	
	public YQZZState310Reward(YQZZMatchService parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		this.getDataManager().getStateData().setState(YQZZActivityState.REWARD);
		this.getDataManager().getStateData().saveRedis();
		long curTime = HawkTime.getMillisecond();
		this.lastTickTime = curTime;
	}

	@Override
	public void tick() {
		//如果不参与战斗
		YQZZActivityJoinState joinState = this.getDataManager()
				.getStateData().getJoinGame();
		if(joinState == YQZZActivityJoinState.OUT){
			return;
		}
		YQZZTimeCfg timeCfg = this.getTimeCfg();
		long endShowTime = timeCfg.getEndShowTimeValue();
		long curTime = HawkTime.getMillisecond();
		if(curTime > this.lastTickTime + HawkTime.MINUTE_MILLI_SECONDS){
			this.lastTickTime = curTime;
			//15分钟后开始发奖，这样保证玩家都回来了
			int termId = this.getDataManager().getStateData().getTermId();
			YQZZJoinServer serverData = this.getDataManager().getServerData();
			if(Objects.isNull(serverData)){
				return;
			}
			//是否已经完成结算
			if(serverData.getAwardTime() > 0){
				return;
			}
			//重新加载玩家战场数据
			this.getDataManager().loadToCacheYQZZBattleData();
			if(curTime >= (endShowTime - HawkTime.MINUTE_MILLI_SECONDS * 10)){
				serverData.setAwardTime(curTime);
				serverData.saveRedis();
				Set<String> joinGuilds = this.getDataManager().getServerData().getJoinGuilds();
				for(String guildId : joinGuilds){
					String roomId = this.getDataManager().getGuildMatchRooms().get(guildId);
					if(HawkOSOperator.isEmptyString(roomId)){
						continue;
					}
					YQZZGameData gameData = this.getDataManager().loadYQZZGameData(termId, roomId);
					if(gameData.getFinishTime() <= 0){
						continue;
					}
					//添加记录
					this.addRecord(guildId,roomId);
					//发联盟奖
					this.sendGuildAward(guildId,roomId);
					//发个人奖
					this.sendPlayerAward(guildId,roomId);
				}
			}
		}
	}
	
	
	
	

	@Override
	public void gmOp() {
		long curTime = HawkTime.getMillisecond();
		//15分钟后开始发奖，这样保证玩家都回来了
		int termId = this.getDataManager().getStateData().getTermId();
		YQZZJoinServer serverData = this.getDataManager().getServerData();
		if(Objects.isNull(serverData)){
			return;
		}
		//是否已经完成结算
		if(serverData .getAwardTime() > 0){
			return;
		}
		//重新加载玩家战场数据
		this.getDataManager().loadToCacheYQZZBattleData();
			serverData.setAwardTime(curTime);
			serverData.saveRedis();
			Set<String>  joinGuilds = this.getDataManager().getServerData().getJoinGuilds();
			for(String guildId : joinGuilds){
				String roomId = this.getDataManager().getGuildMatchRooms().get(guildId);
				if(HawkOSOperator.isEmptyString(roomId)){
					continue;
				}
				YQZZGameData gameData = this.getDataManager().loadYQZZGameData(termId, roomId);
				if(gameData.getFinishTime() <= 0){
					continue;
				}
				//添加记录
				this.addRecord(guildId,roomId);
				//发联盟奖
				this.sendGuildAward(guildId,roomId);
				//发个人奖
				this.sendPlayerAward(guildId,roomId);
			}
	}

	
	

	
	/**
	 * 添加战斗记录
	 * @param guildId
	 * @param roomId
	 */
	private void addRecord(String guildId,String roomId){
		long curTime = HawkTime.getMillisecond();
		Map<String, YQZZBattleData> battleDatas = this.getDataManager().getBattleDatas();
		if(Objects.isNull(battleDatas)){
			return;
		}
		if(!battleDatas.containsKey(roomId)){
			return;
		}
		YQZZBattleData battleData = battleDatas.get(roomId);
		Map<String, YQZZGuildGameData> guilds = battleData.getGuildDatas();
		int termId = this.getDataManager().getStateData().getTermId();
		String serverId = GsConfig.getInstance().getServerId();
		
		YQZZGuildGameData guildGameData = guilds.get(guildId);
		if(Objects.isNull(guildGameData)){
			return;
		}
		YQZZWarConstCfg constCfg = HawkConfigManager.getInstance().getKVInstance(YQZZWarConstCfg.class);
		long scoreAdd = (long)(guildGameData.getScore() * constCfg.getGroupWinpointAdd() / 10000) + constCfg.getGroupRankAdd(guildGameData.getRank());
		//记录一下
		YQZZRecordData recordData = new YQZZRecordData();
		recordData.setTermId(termId);
		recordData.setServerId(serverId);
		recordData.setGuildId(guildId);
		recordData.setRoomId(roomId);
		recordData.setRank(guildGameData.getRank());
		recordData.setScore(guildGameData.getScore());
		recordData.setSeasonScore(scoreAdd);
		recordData.setSendAward(0);
		recordData.setTime(curTime);
		recordData.saveRedis();
		//更新最好成绩  也更新到缓存
		YQZZStatisticsData statisticsData = YQZZStatisticsData.loadData(guildId);
		if(Objects.isNull(statisticsData)){
			statisticsData = new YQZZStatisticsData();
			statisticsData.setGuildId(guildId);
			statisticsData.setServerId(serverId);
		}
		statisticsData.updateMaxRank(guildGameData.getRank(),termId);
		statisticsData.saveRedis();
	}
	
	/**
	 * 发放奖励
	 * @param guildId
	 * @param roomId
	 */
	private void sendGuildAward(String guildId,String roomId){
		int termId = this.getDataManager().getStateData().getTermId();
		Map<String, YQZZBattleData> battleDatas = this.getDataManager().getBattleDatas();
		if(battleDatas == null){
			return;
		}
		if(!battleDatas.containsKey(roomId)){
			return;
		}
		YQZZBattleData battleData = battleDatas.get(roomId);
		if(battleData == null){
			return;
		}
		Map<String,YQZZGuildGameData> guilds = battleData.getGuildDatas();
		String serverId = GsConfig.getInstance().getServerId();
		YQZZGuildGameData data = guilds.get(guildId);
		if(Objects.isNull(data)){
			return;
		}
		if(!data.getServerId().equals(serverId)){
			return;
		}
		long score = data.getScore();
		int rank = data.getRank();
		
		//联盟发奖记录
		YQZZGuildRewardData rewardData = YQZZGuildRewardData.loadData(termId, guildId);
		if(rewardData.getGuildRewardTime() > 0){
			return;
		}
		long curTime = HawkTime.getMillisecond();
		rewardData.setGuildRank(rank);
		rewardData.setGuildRewardTime(curTime);
		rewardData.saveRedis();
		//发全服奖励邮件
		YQZZGuildRankAwardCfg awardCfg = this.getYQZZGuildRankAwardCfg(data.getRank());
		if(awardCfg != null){
			GuildMailService.getInstance().sendGuildMail(guildId, MailParames.newBuilder()
					.setMailId(MailId.YQZZ_ACTIVITY_GUILD_RANK_REWARD)
					.addContents(score, rank)
					.setRewards(awardCfg.getRewardList())
					.setAwardStatus(MailRewardStatus.NOT_GET));
			HawkLog.logPrintln("yqzz-send-rank-guild-reward-{}, serverId: {}, rank: {}, score: {}, cfgId: {}",
					guildId, serverId, score, rank, awardCfg.getId());
			LogUtil.logYQZZGuildRankReward(termId, guildId, data.getGuildName(), score, rank, awardCfg.getId());
		}
	}
	
	
	private void sendPlayerAward(String guildId,String roomId){
		Map<String, YQZZBattleData> battleDatas = this.getDataManager().getBattleDatas();
		if(battleDatas == null){
			return;
		}
		if(!battleDatas.containsKey(roomId)){
			return;
		}
		YQZZBattleData battleData = battleDatas.get(roomId);
		if(battleData == null){
			return;
		}
		
		int termId = this.getDataManager().getStateData().getTermId();
		Map<String,YQZZPlayerGameData> players = battleData.getPlayerDatas();
		Map<String,YQZZGuildGameData> guilds = battleData.getGuildDatas();
		
		//联盟发奖记录
		YQZZGuildRewardData rewardData = YQZZGuildRewardData.loadData(termId, guildId);
		Map<String, Integer> memberRewardMap = rewardData.getMemberRewards();
		//玩家奖励邮件
		for(YQZZPlayerGameData data : players.values()){
			try {
				String playerServer = data.getServerId();
				String playerId = data.getPlayerId();
				long score = data.getScore();
				int rank = data.getRank();
				if(!data.getPlayerGuild().equals(guildId)){
					continue;
				}
				if(!GlobalData.getInstance().isLocalServer(playerServer)){
					continue;
				}
				
				Player player =GlobalData.getInstance().makesurePlayer(playerId);
				if(player == null){
					continue;
				}
				//发放排行奖励
				int guildRank = 0;
				YQZZGuildGameData guildGameData = guilds.get(data.getPlayerGuild());
				if(Objects.nonNull(guildGameData)){
					guildRank = guildGameData.getRank();
				}
				if(memberRewardMap.containsKey(playerId)){
					continue;
				}
				memberRewardMap.put(playerId, rank);
				double rate = HawkConfigManager.getInstance().getKVInstance(YQZZBattleCfg.class).playerRankRate(guildRank);
				YQZZPlayerRankAwardCfg cfg = this.getYQZZPlayerRankAwardCfg(rank);
				if(cfg != null && !cfg.getRewardList().isEmpty() && rate > 0){
					List<ItemInfo> rewardList = cfg.getRewardList();
					rewardList.forEach(item -> item.setCount((long) (item.getCount() * rate)));
					SystemMailService.getInstance().sendMail(MailParames.newBuilder()
							.setPlayerId(playerId)
							.setMailId(MailId.YQZZ_ACTIVITY_PLAYER_RANK_REWARD)
							.addContents(score, rank,guildRank,rate)
							.setRewards(rewardList)
							.setAwardStatus(MailRewardStatus.NOT_GET)
							.build());
					HawkLog.logPrintln("yqzz send rank player reward, playerId:{}, rank: {}, score: {}, cfgId: {}",
							playerId, rank, score, cfg.getId());
					LogUtil.logYQZZPlayerRankReward(player, termId, score, rank, cfg.getId());
				}
				//添加军功
				player.increaseNationMilitary((int)score, PlayerAttr.NATION_MILITARY_VALUE, Action.YQZZ_NATION_MILITARY, true);
			} catch (Exception e) {
				HawkException.catchException(e);
			}
		}
		//保存数据
		rewardData.saveRedis();
	}

	

	private YQZZGuildRankAwardCfg getYQZZGuildRankAwardCfg(int rank){
		List<YQZZGuildRankAwardCfg> list = HawkConfigManager.getInstance()
				.getConfigIterator(YQZZGuildRankAwardCfg.class).toList();
		for(YQZZGuildRankAwardCfg cfg : list){
			if(cfg.getRankUpper() <= rank &&
					rank <= cfg.getRankLower()){
				return cfg;
			}
		}
		return null;
	}
	
	private YQZZPlayerRankAwardCfg getYQZZPlayerRankAwardCfg(int rank){
		List<YQZZPlayerRankAwardCfg> list = HawkConfigManager.getInstance()
				.getConfigIterator(YQZZPlayerRankAwardCfg.class).toList();
		for(YQZZPlayerRankAwardCfg cfg : list){
			if(cfg.getRankUpper() <= rank &&
					rank <= cfg.getRankLower()){
				return cfg;
			}
		}
		return null;
	}
}
