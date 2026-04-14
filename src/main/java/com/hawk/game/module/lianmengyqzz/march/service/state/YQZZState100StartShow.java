package com.hawk.game.module.lianmengyqzz.march.service.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hawk.config.HawkConfigManager;
import org.hawk.log.HawkLog;
import org.hawk.os.HawkException;
import org.hawk.os.HawkTime;

import com.hawk.game.GsConfig;
import com.hawk.game.entity.GuildInfoObject;
import com.hawk.game.module.lianmengyqzz.march.cfg.YQZZTimeCfg;
import com.hawk.game.module.lianmengyqzz.march.cfg.YQZZWarConstCfg;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZJoinGuild;
import com.hawk.game.module.lianmengyqzz.march.data.local.YQZZActivityStateData;
import com.hawk.game.module.lianmengyqzz.march.service.YQZZConst.YQZZActivityJoinState;
import com.hawk.game.module.lianmengyqzz.march.service.YQZZConst.YQZZActivityState;
import com.hawk.game.nation.NationService;
import com.hawk.game.module.lianmengyqzz.march.service.YQZZMatchService;
import com.hawk.game.protocol.MailConst.MailId;
import com.hawk.game.protocol.National.NationbuildingType;
import com.hawk.game.protocol.Rank.RankInfo;
import com.hawk.game.protocol.Rank.RankType;
import com.hawk.game.rank.RankService;
import com.hawk.game.service.GuildService;
import com.hawk.game.service.mail.MailParames;
import com.hawk.game.service.mail.SystemMailService;
import com.hawk.game.util.GameUtil;
import com.hawk.game.util.LogUtil;


public class YQZZState100StartShow  extends IYQZZServiceState {
	
	
	public YQZZState100StartShow(YQZZMatchService parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		YQZZActivityStateData data = this.calcInfo();
		//清除一下数据
		this.getDataManager().clearData();
		//重置一下状态
		this.getDataManager().getStateData().setTermId(data.getTermId());
		this.getDataManager().getStateData().setState(YQZZActivityState.START_SHOW);
		//是否可以参加本次赛事
		boolean canJoin = this.getParent().canJoinActivity();
		YQZZActivityJoinState joinState = canJoin?YQZZActivityJoinState.JOIN:YQZZActivityJoinState.OUT;
		this.getDataManager().getStateData().setJoinGame(joinState);
		this.getDataManager().getStateData().saveRedis();
		//初始化当前服数据
		this.getDataManager().initYQZZJoinServerData(data.getTermId());
		HawkLog.logPrintln("YQZZState100StartShow-init: {}", data.getTermId());
	}
	
	
	@Override
	public void tick() {
		//如果不参与战斗
		YQZZActivityJoinState joinState = this.getDataManager()
				.getStateData().getJoinGame();
		if(joinState == YQZZActivityJoinState.OUT){
			return;
		}
		//如果参战，则写入当前服参战信息,距离匹配阶段10分钟前
		long curTime = HawkTime.getMillisecond();
		YQZZTimeCfg cfg = this.getTimeCfg();
		long matchTime = cfg.getMatchTimeValue();
		if(curTime >(matchTime - HawkTime.MINUTE_MILLI_SECONDS * 20)){
			this.saveYQZZJoinServer();
		}
	}

	@Override
	public void gmOp() {
		//如果不参与战斗
		YQZZActivityJoinState joinState = this.getDataManager()
				.getStateData().getJoinGame();
		if(joinState == YQZZActivityJoinState.OUT){
			return;
		}
		this.saveYQZZJoinServer();
	}


	/**
	 * 发放活动开始邮件
	 */
	public void sendStartEmail(){
		long currTime = HawkTime.getMillisecond();
		long experiTime = currTime + HawkTime.DAY_MILLI_SECONDS * 7;
		SystemMailService.getInstance().addGlobalMail(MailParames.newBuilder()
				.setMailId(MailId.YQZZ_ACTIVITY_START)
				.build(), currTime, currTime + experiTime);
	}
	
	
	/**
	 * 保存当前服参赛信息
	 */
	private void saveYQZZJoinServer(){
		if(this.getDataManager().getStateData()
				.getJoinGame() != YQZZActivityJoinState.JOIN){
			return;
		}
		if(this.getDataManager().getServerData().getSaveServerInfo() > 0){
			return;
		}
		List<RankInfo> rankList = RankService.getInstance().getRankCache(RankType.ALLIANCE_FIGHT_KEY);
		if(rankList.isEmpty()){
			HawkLog.logPrintln("YQZZState100StartShow-saveYQZZJoinServer-rankList-empty");
			return;
		}
		YQZZWarConstCfg cfg = HawkConfigManager.getInstance().getKVInstance(YQZZWarConstCfg.class);
		String serverId = GsConfig.getInstance().getServerId();
		int termId = this.getDataManager().getStateData().getTermId();
		int joinGuildCount = cfg.getJoinGuildCount();
		joinGuildCount = Math.min(rankList.size(), joinGuildCount);
		int openDays = GameUtil.getServerOpenDay();
		int nationLevel = NationService.getInstance().getBuildLevel(serverId, NationbuildingType.NATION_SPACE_FLIGHT_VALUE);
		//参赛联盟ID
		Map<String, YQZZJoinGuild> joinGuilds = new HashMap<>();
		for (int i = 0; i < joinGuildCount; i++) {
			RankInfo rankInfo = rankList.get(i);
			String guildId = rankInfo.getId();
			GuildInfoObject obj = GuildService.getInstance().getGuildInfoObject(guildId);
			if(obj != null){
				long power = GuildService.getInstance().getGuildStrength(guildId);
				YQZZJoinGuild joinGuild = new YQZZJoinGuild();
				joinGuild.setTermId(termId);
				joinGuild.setServerId(serverId);
				joinGuild.setNationLevel(nationLevel);
				joinGuild.setGuildId(guildId);
				joinGuild.setGuildName(obj.getName());
				joinGuild.setGuildTag(obj.getTag());
				joinGuild.setGuildFlag(obj.getFlagId());
				joinGuild.setLeaderId(obj.getLeaderId());
				joinGuild.setLeaderName(obj.getLeaderName());
				joinGuild.setGuildRank(rankInfo.getRank());
				joinGuild.setPower(power);
				joinGuild.setOpenDays(openDays);
				joinGuilds.put(joinGuild.getGuildId(), joinGuild);
				//日志
				LogUtil.logYQZZMatchPower(termId, serverId, guildId, obj.getName(),rankInfo.getRank(), rankInfo.getRankInfoValue());
				HawkLog.logPrintln("YQZZState100StartShow-joinGuild-info:term:{},serverId:{},guildId:{},guildName:{},rank:{},power:{}", 
						termId,serverId, guildId, obj.getName(),rankInfo.getRank(),rankInfo.getRankInfoValue());
			}
		}
		//记录数据
		YQZZJoinGuild.saveAllData(termId, joinGuilds);
		this.getDataManager().getServerData().recordJoinGuild(joinGuilds);
		this.getDataManager().getServerData().setSaveServerInfo(1);
		this.getDataManager().getServerData().saveRedis();
		HawkLog.logPrintln("YQZZState100StartShow-saveYQZZJoinServer: {}", termId);
	}

	



	
}
