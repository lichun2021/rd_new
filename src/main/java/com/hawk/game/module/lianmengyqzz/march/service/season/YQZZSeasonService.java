package com.hawk.game.module.lianmengyqzz.march.service.season;

public class YQZZSeasonService {
	
	
//	private YQZZSeasonStateData seasonStateData;
//	private Map<String, YQZZSeasonGiftRecordData> giftRecordDataMap = new HashMap<>();
//	private List<YQZZSeasonGiftRecordData> giftRecordDataList = new ArrayList<>();



	
//	public boolean onTick() {
//		long curTime = HawkTime.getMillisecond();
//		if(curTime - this.lastTime < 2000){
//			return true;
//		}
//		this.lastTime = curTime;
//		onSeasonTick();
//		return true;
//	}
//	
//	
//	public void onSeasonTick(){
//		try {
//			YQZZSeasonStateData calData = calSeasonStateData();
//			YQZZSeasonStateData curData = getDataManger().getSeasonStateData();
//			if(curData.getSeason() != calData.getSeason() || curData.getState() != calData.getState()){
//				curData.next(calData);
//			}
//		}catch (Exception e){
//			HawkException.catchException(e);
//		}
//	}
//
//	public void onSeasonStart(){
//		String serverId = GsConfig.getInstance().getServerId();
//		//服务器
//		ServerInfo serverInfo = RedisProxy.getInstance().getServerInfo(String.valueOf(serverId));
//		// 司令
//		President president = PresidentFightService.getInstance().getPresidentCity().getPresident();
//		int season = getDataManger().getSeasonStateData().getSeason();
//		YQZZSeasonServer seasonServer = new YQZZSeasonServer();
//		seasonServer.setSeason(season);
//		seasonServer.setServerName(serverInfo.getName());
//		if(president != null &&
//				HawkOSOperator.isEmptyString(president.getPlayerId())){
//			seasonServer.setLeaderName(president.getPlayerName());
//		}else {
//			seasonServer.setLeaderName("");
//		}
//		seasonServer.saveRedis();
//		NationSpaceFlight flight = (NationSpaceFlight)NationService.getInstance().getNationBuildingByType(National.NationbuildingType.NATION_SPACE_FLIGHT);
//		if(flight != null && flight.getLevel() >= 1){
//			long currTime = HawkTime.getMillisecond();
//			long experiTime = currTime + HawkTime.DAY_MILLI_SECONDS * 7;
//			SystemMailService.getInstance().addGlobalMail(MailParames.newBuilder()
//					.setMailId(MailConst.MailId.YQZZ_LEAGUE_START)
//					.build(), currTime, currTime + experiTime);
//		}
//
//	}
//
//	public void onSeasonReward(){
//
//	}
//
//	public void onSeasonEnd(){
//
//	}
//	
//	public YQZZSeasonStateData calSeasonStateData(){
//		YQZZSeasonStateData seasonStateData = new YQZZSeasonStateData();
//		long now = HawkTime.getMillisecond();
//		int season = -1;
//		ConfigIterator<YQZZTimeCfg> iterator = HawkConfigManager.getInstance().getConfigIterator(YQZZTimeCfg.class);
//		for(YQZZTimeCfg cfg : iterator){
//			if(cfg.getSeason() < 0){
//				continue;
//			}
//			if(cfg.getSeasonStartTimeValue() != 0 && now >= cfg.getSeasonStartTimeValue()){
//				season = cfg.getSeason();
//			}
//		}
//		if(season == -1){
//			return seasonStateData;
//		}
//		seasonStateData.setSeason(season);
//		iterator = HawkConfigManager.getInstance().getConfigIterator(YQZZTimeCfg.class);
//		for(YQZZTimeCfg cfg : iterator){
//			if(cfg.getSeason() != season){
//				continue;
//			}
//			if(cfg.getSeasonEndTimeValue() != 0 && now <= cfg.getSeasonEndTimeValue()){
//				seasonStateData.setState(YQZZConst.YQZZSeasonState.OPEN);
//			}
//		}
//		return seasonStateData;
//	}
//	
//	public void syncYQZZLeagueWarInfo(Player player){
//		player.sendProtocol(HawkProtocol.valueOf(HP.code2.YQZZ_LEAGUE_WAR_INFO_RESP, genYQZZLeagueWarInfo()));
//	}
//	
//	private YQZZWar.PBYQZZLeagueWarInfoResp.Builder genYQZZLeagueWarInfo(){
//		YQZZWar.PBYQZZLeagueWarInfoResp.Builder resp = YQZZWar.PBYQZZLeagueWarInfoResp.newBuilder();
//		resp.setType(YQZZWar.PBYQZZWarType.YQZZ_NOT_SEASON);
//		int termId = YQZZMatchService.getInstance().getDataManger().getStateData().getTermId();
//		if(termId == 0){
//			resp.setTermId(1);
//			resp.setType(YQZZWar.PBYQZZWarType.YQZZ_NOT_SEASON);
//			resp.setSeason(0);
//			resp.setTurn(0);
//			resp.setState(YQZZWar.PBYQZZLeagueWarState.YQZZ_LEAGUE_START_SHOW);
//			resp.setStartTime(Long.MAX_VALUE);
//			resp.setEndTime(Long.MAX_VALUE);
//			return resp;
//		}
//		resp.setTermId(termId);
//		YQZZTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(YQZZTimeCfg.class, termId);
//		if(timeCfg == null
//				|| timeCfg.getSeason() <= 0
//				|| timeCfg.getType() == YQZZWar.PBYQZZWarType.YQZZ_NOT_SEASON_VALUE){
//			YQZZSeasonStateData curData = getDataManger().getSeasonStateData();
//			if(curData.getState() == YQZZConst.YQZZSeasonState.OPEN){
//				ConfigIterator<YQZZTimeCfg> iterator = HawkConfigManager.getInstance().getConfigIterator(YQZZTimeCfg.class);
//				YQZZTimeCfg nextCfg = null;
//				int nextSeason = curData.getSeason();
//				for(YQZZTimeCfg yqzzTimeCfg : iterator){
//					if(yqzzTimeCfg.getSeason() == nextSeason
//							&& yqzzTimeCfg.getType() == YQZZWar.PBYQZZWarType.YQZZ_GROUP_VALUE
//							&& yqzzTimeCfg.getTurn() == 1){
//						nextCfg = yqzzTimeCfg;
//						break;
//					}
//				}
//				resp.setType(YQZZWar.PBYQZZWarType.YQZZ_GROUP);
//				resp.setSeason(curData.getSeason());
//				resp.setTurn(1);
//				resp.setState(YQZZWar.PBYQZZLeagueWarState.YQZZ_LEAGUE_START_SHOW);
//				resp.setStartTime(nextCfg.getSeasonStartTimeValue());
//				resp.setEndTime(nextCfg.getEndShowTimeValue());
//			}else {
//				resp.setTermId(1);
//				resp.setType(YQZZWar.PBYQZZWarType.YQZZ_NOT_SEASON);
//				resp.setSeason(0);
//				resp.setTurn(0);
//				resp.setState(YQZZWar.PBYQZZLeagueWarState.YQZZ_LEAGUE_START_SHOW);
//				resp.setStartTime(Long.MAX_VALUE);
//				resp.setEndTime(Long.MAX_VALUE);
//			}
//			return resp;
//		}
//		resp.setType(YQZZWar.PBYQZZWarType.valueOf(timeCfg.getType()));
//		resp.setSeason(timeCfg.getSeason());
//		resp.setTurn(timeCfg.getTurn());
//		resp.setState(getClientLeagueState());
//		resp.setStartTime(timeCfg.getShowTimeValue());
//		resp.setEndTime(timeCfg.getEndShowTimeValue());
//		if(timeCfg.getType() == YQZZWar.PBYQZZWarType.YQZZ_KICKOUT_VALUE){
//			YQZZSeasonServer seasonServer = YQZZSeasonServer.loadByServerId(timeCfg.getSeason(), GsConfig.getInstance().getServerId());
//			resp.setKickout(!seasonServer.isAdvance());
//			resp.setGroupRank(seasonServer.getGroupRank());
//			return resp;
//		}else {
//			resp.setGroupRank(YQZZSeasonServer.getGroupSelfRank(timeCfg.getSeason(), GsConfig.getInstance().getServerId()));
//			return resp;
//		}
//	}
//
//	public YQZZWar.PBYQZZLeagueWarState getClientLeagueState(){
//		YQZZActivityState state = YQZZMatchService.getInstance().getDataManger().getStateData().getState();
//		switch (state) {
//			case START_SHOW: return YQZZWar.PBYQZZLeagueWarState.YQZZ_LEAGUE_START_SHOW;
//			case MATCH: return YQZZWar.PBYQZZLeagueWarState.YQZZ_LEAGUE_MATCH;
//			case BATTLE: return YQZZWar.PBYQZZLeagueWarState.YQZZ_LEAGUE_BATTLE;
//			case REWARD: return  YQZZWar.PBYQZZLeagueWarState.YQZZ_LEAGUE_END_SHOW;
//			case END_SHOW: return YQZZWar.PBYQZZLeagueWarState.YQZZ_LEAGUE_END_SHOW;
//			case HIDDEN: return YQZZWar.PBYQZZLeagueWarState.YQZZ_LEAGUE_HIDDEN;
//			default: return YQZZWar.PBYQZZLeagueWarState.YQZZ_LEAGUE_HIDDEN;
//		}
//	}
//	
//	
//	@ProtocolHandler(code = HP.code2.YQZZ_LEAGUE_WAR_INFO_REQ_VALUE)
//	private void onLeagueWarInfo(HawkProtocol protocol) {
//		YQZZMatchService.getInstance().syncYQZZLeagueWarInfo(player);
//	}
//
//	@ProtocolHandler(code = HP.code2.YQZZ_LEAGUE_WAR_DETIAL_INFO_REQ_VALUE)
//	private void onLeagueWarDetialInfo(HawkProtocol protocol) {
//		int termId = YQZZMatchService.getInstance().getDataManger().getStateData().getTermId();
//		YQZZTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(YQZZTimeCfg.class, termId);
//		int season = timeCfg.getSeason();
//		if(season <= 0){
//			YQZZWar.PBYQZZLeagueWarDetialInfoReq req = protocol.parseProtocol(YQZZWar.PBYQZZLeagueWarDetialInfoReq.getDefaultInstance());
//			YQZZWar.PBYQZZWarType type = req.getType();
//			YQZZWar.PBYQZZLeagueWarDetialInfoResp.Builder resp = YQZZWar.PBYQZZLeagueWarDetialInfoResp.newBuilder();
//			resp.setType(type);
//			player.sendProtocol(HawkProtocol.valueOf(HP.code2.YQZZ_LEAGUE_WAR_DETIAL_INFO_RESP, resp));
//			return;
//		}
//		YQZZActivityState state = YQZZMatchService.getInstance().getDataManger().getStateData().getState();
//		YQZZWar.PBYQZZLeagueWarDetialInfoReq req = protocol.parseProtocol(YQZZWar.PBYQZZLeagueWarDetialInfoReq.getDefaultInstance());
//		YQZZWar.PBYQZZWarType type = req.getType();
//		YQZZWar.PBYQZZLeagueWarDetialInfoResp.Builder resp = YQZZWar.PBYQZZLeagueWarDetialInfoResp.newBuilder();
//		resp.setType(type);
//		ConfigIterator<YQZZTimeCfg> iterator = HawkConfigManager.getInstance().getConfigIterator(YQZZTimeCfg.class);
//		for (YQZZTimeCfg cfg : iterator){
//			int turn = cfg.getTurn();
//			if(cfg.getSeason() != season
//					|| cfg.getType() != type.getNumber()
//					|| turn > timeCfg.getTurn()){
//				continue;
//			}
//			Map<String, YQZZMatchRoomData> dataMap = YQZZMatchRoomData.loadAllData(cfg.getTermId());
//			if(dataMap.isEmpty()){
//				continue;
//			}
//			boolean isEnd = true;
//			if(turn == timeCfg.getTurn()
//					&& (state == YQZZActivityState.MATCH || state == YQZZActivityState.BATTLE || state == YQZZActivityState.REWARD)){
//				isEnd = false;
//			}
//			int index = 1;
//			for(YQZZMatchRoomData roomData : dataMap.values()){
//				if(cfg.getType() == YQZZWar.PBYQZZWarType.YQZZ_KICKOUT_VALUE && !roomData.isAdvance()){
//					continue;
//				}
//				YQZZWar.PBYQZZLeagueWarGroupInfo.Builder group = genGroupInfo(roomData, season, cfg.getTermId(), isEnd);
//				group.setTurn(turn);
//				group.setIsEnd(isEnd);
//				group.setGroup(index);
//				index++;
//				resp.addGroupInfos(group);
//			}
//		}
//		player.sendProtocol(HawkProtocol.valueOf(HP.code2.YQZZ_LEAGUE_WAR_DETIAL_INFO_RESP, resp));
//	}
//
//	public YQZZWar.PBYQZZLeagueWarGroupInfo.Builder genGroupInfo(YQZZMatchRoomData room, int season, int termId, boolean isEnd){
//		YQZZWar.PBYQZZLeagueWarGroupInfo.Builder group = YQZZWar.PBYQZZLeagueWarGroupInfo.newBuilder();
//		if(isEnd){
//			List<YQZZRecordData> recordDataList = new ArrayList<>();
//			for(String serverId : room.getServers()){
//				YQZZRecordData recordData = YQZZRecordData.loadData(serverId, termId);
//				if(recordData != null){
//					recordDataList.add(recordData);
//				}
//			}
//			Collections.sort(recordDataList,new Comparator<YQZZRecordData>(){
//				@Override
//				public int compare(YQZZRecordData o1, YQZZRecordData o2) {
//					if(o1.getRank() != o2.getRank()){
//						return o1.getRank() > o2.getRank() ? 1 : -1;
//					}
//					return 0;
//				}
//			});
//			for(YQZZRecordData recordData : recordDataList){
//				group.addServerInfos(genServerInfo(recordData, recordData.getServerId(), season));
//			}
//		}else {
//			for(String serverId : room.getServers()){
//				group.addServerInfos(genServerInfo(null, serverId, season));
//			}
//		}
//		return group;
//	}
//
//	private YQZZWar.PBYQZZLeagueWarServerInfo.Builder genServerInfo(YQZZRecordData recordData, String serverId, int season){
//		YQZZSeasonServer seasonServer = YQZZSeasonServer.loadByServerId(season, serverId);
//		YQZZWar.PBYQZZLeagueWarServerInfo.Builder serverInfo = YQZZWar.PBYQZZLeagueWarServerInfo.newBuilder();
//		serverInfo.setServerId(serverId);
//		serverInfo.setServerName(seasonServer.getServerName());
//		serverInfo.setLeaderName(seasonServer.getLeaderName());
//		serverInfo.setLastRank(0);
//		serverInfo.setSeason(season);
//		if(recordData != null){
//			serverInfo.setRank(recordData.getRank());
//			serverInfo.setWinPoint(recordData.getScore());
//			serverInfo.setScore(recordData.getSeasonScore());
//			serverInfo.setIsKickOut(!recordData.isAdvance());
//		}
//		return serverInfo;
//	}
//
//	@ProtocolHandler(code = HP.code2.YQZZ_LEAGUE_WAR_SELF_INFO_REQ_VALUE)
//	private void onLeagueWarSelfInfo(HawkProtocol protocol) {
//		int termId = YQZZMatchService.getInstance().getDataManger().getStateData().getTermId();
//		YQZZTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(YQZZTimeCfg.class, termId);
//		int season = timeCfg.getSeason();
//		if(season <= 0){
//			YQZZWar.PBYQZZLeagueWarSelfInfoResp.Builder resp = YQZZWar.PBYQZZLeagueWarSelfInfoResp.newBuilder();
//			player.sendProtocol(HawkProtocol.valueOf(HP.code2.YQZZ_LEAGUE_WAR_SELF_INFO_RESP, resp));
//			return;
//		}
//		YQZZActivityState state = YQZZMatchService.getInstance().getDataManger().getStateData().getState();
//		YQZZWar.PBYQZZLeagueWarSelfInfoResp.Builder resp = YQZZWar.PBYQZZLeagueWarSelfInfoResp.newBuilder();
//		ConfigIterator<YQZZTimeCfg> iterator = HawkConfigManager.getInstance().getConfigIterator(YQZZTimeCfg.class);
//		for (YQZZTimeCfg cfg : iterator) {
//			int turn = cfg.getTurn();
//			if (cfg.getSeason() != season
//					|| turn > timeCfg.getTurn()) {
//				continue;
//			}
//			Map<String, YQZZMatchRoomData> dataMap = YQZZMatchRoomData.loadAllData(cfg.getTermId());
//			if (dataMap.isEmpty()) {
//				continue;
//			}
//			if(turn == timeCfg.getTurn()
//					&& (state == YQZZActivityState.MATCH || state == YQZZActivityState.BATTLE|| state == YQZZActivityState.REWARD)){
//				continue;
//			}
//			boolean isEnd = true;
//			String serverId = player.getMainServerId();
//			for(YQZZMatchRoomData roomData : dataMap.values()){
//				if(!roomData.getServers().contains(serverId)){
//					continue;
//				}
//				YQZZWar.PBYQZZLeagueWarGroupInfo.Builder group = genGroupInfo(roomData, season, cfg.getTermId(), isEnd);
//				group.setTurn(turn);
//				group.setIsEnd(isEnd);
//				resp.addGroupInfos(group);
//			}
//		}
//		player.sendProtocol(HawkProtocol.valueOf(HP.code2.YQZZ_LEAGUE_WAR_SELF_INFO_RESP, resp));
//	}
//
//	@ProtocolHandler(code = HP.code2.YQZZ_LEAGUE_GROUP_WAR_SERVER_RANK_REQ_VALUE)
//	private void onLeagueGroupWarServerRank(HawkProtocol protocol) {
//		int termId = YQZZMatchService.getInstance().getDataManger().getStateData().getTermId();
//		YQZZTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(YQZZTimeCfg.class, termId);
//		int season = timeCfg.getSeason();
//		if(season <= 0){
//			YQZZWar.PBYQZZLeagueKickoutWarServerRankResp.Builder resp = YQZZWar.PBYQZZLeagueKickoutWarServerRankResp.newBuilder();
//			resp.setMyInfo(buildSlefServerInfo());
//			player.sendProtocol(HawkProtocol.valueOf(HP.code2.YQZZ_LEAGUE_GROUP_WAR_SERVER_RANK_RESP, resp));
//			return;
//		}
//		YQZZWar.PBYQZZLeagueKickoutWarServerRankResp.Builder resp = YQZZWar.PBYQZZLeagueKickoutWarServerRankResp.newBuilder();
//		Map<String, YQZZSeasonServer> serverMap = YQZZSeasonServer.loadAll(season);
//		YQZZWar.PBYQZZLeagueWarServerInfo.Builder selfServer = null;
//		List<HawkTuple2<String, Integer>> rankList = YQZZSeasonServer.getGroupRank(season);
//		for(HawkTuple2<String, Integer> tuple2 : rankList){
//			String serverId = tuple2.first;
//			int rank = tuple2.second;
//			YQZZSeasonServer seasonServer = serverMap.get(serverId);
//			if(seasonServer == null || seasonServer.getScore() == 0){
//				continue;
//			}
//			YQZZWar.PBYQZZLeagueWarServerInfo.Builder builder = genServerInfo(seasonServer);
//			builder.setRank(rank);
//			resp.addRankInfos(builder);
//			if(GsConfig.getInstance().getServerId().equals(serverId)){
//				selfServer = builder;
//			}
//		}
//		if(selfServer!=null){
//			resp.setMyInfo(selfServer);
//		}else {
//			resp.setMyInfo(buildSlefServerInfo());
//		}
//		player.sendProtocol(HawkProtocol.valueOf(HP.code2.YQZZ_LEAGUE_GROUP_WAR_SERVER_RANK_RESP, resp));
//	}
//
//	private YQZZWar.PBYQZZLeagueWarServerInfo.Builder genServerInfo(YQZZSeasonServer seasonServer){
//		YQZZWar.PBYQZZLeagueWarServerInfo.Builder serverInfo = YQZZWar.PBYQZZLeagueWarServerInfo.newBuilder();
//		serverInfo.setServerId(seasonServer.getServerId());
//		serverInfo.setServerName(seasonServer.getServerName());
//		serverInfo.setScore(seasonServer.getScore());
//		serverInfo.setWinPoint(seasonServer.getTotalPoint());
//		serverInfo.setLastRank(0);
//		serverInfo.setLeaderName(seasonServer.getLeaderName());
//		serverInfo.setSeason(seasonServer.getSeason());
//		return serverInfo;
//
//	}
//
//	@ProtocolHandler(code = HP.code2.YQZZ_LEAGUE_KICKOUT_WAR_SERVER_RANK_REQ_VALUE)
//	private void onLeagueKickoutWarServerRank(HawkProtocol protocol) {
//		int termId = YQZZMatchService.getInstance().getDataManger().getStateData().getTermId();
//		YQZZTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(YQZZTimeCfg.class, termId);
//		int season = timeCfg.getSeason();
//		if(season <= 0){
//			YQZZWar.PBYQZZLeagueKickoutWarServerRankResp.Builder resp = YQZZWar.PBYQZZLeagueKickoutWarServerRankResp.newBuilder();
//			resp.setMyInfo(buildSlefServerInfo());
//			player.sendProtocol(HawkProtocol.valueOf(HP.code2.YQZZ_LEAGUE_KICKOUT_WAR_SERVER_RANK_RESP, resp));
//			return;
//		}
//		Map<String, YQZZSeasonServer> serverMap = YQZZSeasonServer.loadAll(season);
//		List<HawkTuple2<String, Integer>> rankList = YQZZSeasonServer.getFinalRank(season);
//		int min = 24;
//		if(rankList.size() > 0){
//			min = rankList.get(0).second - 1;
//		}
//		YQZZWar.PBYQZZLeagueKickoutWarServerRankResp.Builder resp = YQZZWar.PBYQZZLeagueKickoutWarServerRankResp.newBuilder();
//		for(int i = 1;i <= min; i++){
//			YQZZWar.PBYQZZLeagueWarServerInfo.Builder builder = YQZZWar.PBYQZZLeagueWarServerInfo.newBuilder();
//			builder.setServerId("-1");
//			builder.setServerName("虚位以待");
//			builder.setRank(i);
//			builder.setSeason(season);
//			resp.addRankInfos(builder);
//		}
//		YQZZWar.PBYQZZLeagueWarServerInfo.Builder selfServer = null;
//		for(HawkTuple2<String, Integer> tuple2 : rankList){
//			String serverId = tuple2.first;
//			int rank = tuple2.second;
//			YQZZSeasonServer seasonServer = serverMap.get(serverId);
//			if(seasonServer == null || seasonServer.getScore() == 0){
//				continue;
//			}
//			YQZZWar.PBYQZZLeagueWarServerInfo.Builder builder = YQZZWar.PBYQZZLeagueWarServerInfo.newBuilder();
//			builder.setServerId(serverId);
//			builder.setServerName(seasonServer.getServerName());
//			builder.setRank(rank);
//			builder.setIsEnd(true);
//			builder.setSeason(season);
//			resp.addRankInfos(builder);
//			if(GsConfig.getInstance().getServerId().equals(serverId)){
//				selfServer = builder;
//			}
//		}
//		if(selfServer != null){
//			resp.setMyInfo(selfServer);
//		}else {
//			resp.setMyInfo(buildSlefServerInfo());
//		}
//		player.sendProtocol(HawkProtocol.valueOf(HP.code2.YQZZ_LEAGUE_KICKOUT_WAR_SERVER_RANK_RESP, resp));
//	}
//
//	@ProtocolHandler(code = HP.code2.YQZZ_LEAGUE_WAR_GUILD_RANK_REQ_VALUE)
//	private void onLeagueWarGuildRank(HawkProtocol protocol) {
//		int termId = YQZZMatchService.getInstance().getDataManger().getStateData().getTermId();
//		YQZZTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(YQZZTimeCfg.class, termId);
//		int season = timeCfg.getSeason();
//		if(season <= 0 || timeCfg.getType() != YQZZWar.PBYQZZWarType.YQZZ_KICKOUT_VALUE){
//			YQZZWar.PBYQZZLeagueWarGuildRankResp.Builder resp = YQZZWar.PBYQZZLeagueWarGuildRankResp.newBuilder();
//			resp.setMyInfo(buildSelfGuildInfo(-1, 0));
//			player.sendProtocol(HawkProtocol.valueOf(HP.code2.YQZZ_LEAGUE_WAR_GUILD_RANK_RESP, resp));
//			return;
//		}
//		YQZZWar.PBYQZZLeagueWarGuildRankResp.Builder resp = YQZZWar.PBYQZZLeagueWarGuildRankResp.newBuilder();
//		resp.addAllRankInfos(YQZZSeasonGuild.getRankPBList(season));
//		if(player.hasGuild()){
//			HawkTuple2<Integer, Long> tuple2 = YQZZSeasonGuild.getSelfRank(season, player.getGuildId());
//			resp.setMyInfo(buildSelfGuildInfo(tuple2.first, tuple2.second));
//		}else{
//			resp.setMyInfo(buildSelfGuildInfo(-1, 0));
//		}
//		player.sendProtocol(HawkProtocol.valueOf(HP.code2.YQZZ_LEAGUE_WAR_GUILD_RANK_RESP, resp));
//
//	}
//
//	@ProtocolHandler(code = HP.code2.YQZZ_LEAGUA_WAR_GET_SCORE_INFO_REQ_VALUE)
//	private void onLeagueWarGetScoreInfo(HawkProtocol protocol) {
//		int termId = YQZZMatchService.getInstance().getDataManger().getStateData().getTermId();
//		YQZZTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(YQZZTimeCfg.class, termId);
//		int season = timeCfg.getSeason();
//		if(season <= 0){
//			YQZZWar.YLWGetScoreInfoResp.Builder resp = YQZZWar.YLWGetScoreInfoResp.newBuilder();
//			resp.setNationalScore(buildScoreInfo(0, new ArrayList<>()));
//			resp.setGuildScore(buildScoreInfo(0, new ArrayList<>()));
//			resp.setSelfScore(buildScoreInfo(0, new ArrayList<>()));
//			player.sendProtocol(HawkProtocol.valueOf(HP.code2.YQZZ_LEAGUA_WAR_GET_SCORE_INFO_RESP, resp));
//			return;
//		}
//		YQZZWar.YLWGetScoreInfoResp.Builder resp = YQZZWar.YLWGetScoreInfoResp.newBuilder();
//		YQZZSeasonServer server = YQZZSeasonServer.loadByServerId(season, player.getMainServerId());
//		if(server != null){
//			resp.setNationalScore(buildScoreInfo(server.getTotalPoint(), server.getReward()));
//		}else {
//			resp.setNationalScore(buildScoreInfo(0, new ArrayList<>()));
//		}
//		YQZZSeasonGuild guild = YQZZSeasonGuild.loadByGuildId(season, player.getGuildId());
//		YQZZSeasonPlayer seasonPlayer = YQZZSeasonPlayer.loadByPlayerId(season, player.getId());
//		if(guild != null && seasonPlayer != null){
//			if(seasonPlayer != null){
//				resp.setGuildScore(buildScoreInfo(guild.getTotalPoint(), seasonPlayer.getGuildReward()));
//			}
//		}else {
//			if(seasonPlayer != null){
//				resp.setGuildScore(buildScoreInfo(0, seasonPlayer.getGuildReward()));
//			}else {
//				resp.setGuildScore(buildScoreInfo(0, new ArrayList<>()));
//			}
//
//		}
//
//		if(seasonPlayer != null){
//			resp.setSelfScore(buildScoreInfo(seasonPlayer.getTotalPoint(), seasonPlayer.getReward()));
//		}else {
//			resp.setSelfScore(buildScoreInfo(0, new ArrayList<>()));
//		}
//		player.sendProtocol(HawkProtocol.valueOf(HP.code2.YQZZ_LEAGUA_WAR_GET_SCORE_INFO_RESP, resp));
//	}
//
//
//
//
//	@ProtocolHandler(code = HP.code2.YQZZ_LEAGUE_GIFT_INFO_REQ_VALUE)
//	private void onLeagueGiftInfo(HawkProtocol protocol) {
//		int termId = YQZZMatchService.getInstance().getDataManger().getStateData().getTermId();
//		YQZZTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(YQZZTimeCfg.class, termId);
//		int season = timeCfg.getSeason();
//		if(season <= 0
//				|| timeCfg.getType() != YQZZWar.PBYQZZWarType.YQZZ_KICKOUT_VALUE){
//			YQZZWar.YQZZGiftInfoResp.Builder resp = YQZZWar.YQZZGiftInfoResp.newBuilder();
//			resp.setGiftId(-1);
//			resp.setSendCount(0);
//			resp.setRank(-1);
//			resp.setIsEnd(false);
//			resp.setCanSend(false);
//			player.sendProtocol(HawkProtocol.valueOf(HP.code2.YQZZ_LEAGUE_GIFT_INFO_RESP, resp));
//			return;
//		}
//		HawkTuple2<Integer, Integer> turnTuple2 = getSeasonMaxTurn(season);
//		if(timeCfg.getTurn() != turnTuple2.second){
//			YQZZWar.YQZZGiftInfoResp.Builder resp = YQZZWar.YQZZGiftInfoResp.newBuilder();
//			resp.setGiftId(-1);
//			resp.setSendCount(0);
//			resp.setRank(-1);
//			resp.setIsEnd(false);
//			resp.setCanSend(false);
//			player.sendProtocol(HawkProtocol.valueOf(HP.code2.YQZZ_LEAGUE_GIFT_INFO_RESP, resp));
//			return;
//		}
//		YQZZActivityState state = YQZZMatchService.getInstance().getDataManger().getStateData().getState();
//		if(state != YQZZActivityState.END_SHOW && state != YQZZActivityState.HIDDEN){
//			YQZZWar.YQZZGiftInfoResp.Builder resp = YQZZWar.YQZZGiftInfoResp.newBuilder();
//			resp.setGiftId(-1);
//			resp.setSendCount(0);
//			resp.setRank(-1);
//			resp.setIsEnd(false);
//			resp.setCanSend(false);
//			player.sendProtocol(HawkProtocol.valueOf(HP.code2.YQZZ_LEAGUE_GIFT_INFO_RESP, resp));
//			return;
//		}
//		long now = HawkTime.getMillisecond();
//		if(state == YQZZActivityState.HIDDEN && now > timeCfg.getSeasonEndTimeValue()){
//			YQZZWar.YQZZGiftInfoResp.Builder resp = YQZZWar.YQZZGiftInfoResp.newBuilder();
//			resp.setGiftId(-1);
//			resp.setSendCount(0);
//			resp.setRank(-1);
//			resp.setIsEnd(false);
//			resp.setCanSend(false);
//			player.sendProtocol(HawkProtocol.valueOf(HP.code2.YQZZ_LEAGUE_GIFT_INFO_RESP, resp));
//			return;
//		}
//		YQZZSeasonServer seasonServer = YQZZSeasonServer.loadByServerId(season, GsConfig.getInstance().getServerId());
//		if(seasonServer == null){
//			YQZZWar.YQZZGiftInfoResp.Builder resp = YQZZWar.YQZZGiftInfoResp.newBuilder();
//			resp.setGiftId(-1);
//			resp.setSendCount(0);
//			resp.setRank(-1);
//			resp.setIsEnd(false);
//			resp.setCanSend(false);
//			player.sendProtocol(HawkProtocol.valueOf(HP.code2.YQZZ_LEAGUE_GIFT_INFO_RESP, resp));
//			return;
//		}
//		int rank = seasonServer.getKickoutRank() > 0? seasonServer.getKickoutRank() : seasonServer.getGroupRank();
//		YQZZSeasonServerRankAwardCfg cfg = getSendAwardCfg(rank);
//		if(cfg == null){
//			YQZZWar.YQZZGiftInfoResp.Builder resp = YQZZWar.YQZZGiftInfoResp.newBuilder();
//			resp.setGiftId(-1);
//			resp.setSendCount(0);
//			resp.setRank(-1);
//			resp.setIsEnd(false);
//			resp.setCanSend(false);
//			player.sendProtocol(HawkProtocol.valueOf(HP.code2.YQZZ_LEAGUE_GIFT_INFO_RESP, resp));
//			return;
//		}
//		YQZZWar.YQZZGiftInfoResp.Builder resp = YQZZWar.YQZZGiftInfoResp.newBuilder();
//		resp.setGiftId(cfg.getId());
//		resp.setSendCount(YQZZMatchService.getInstance().getDataManger().getGiftRecordDataList().size());
//		resp.setRank(rank);
//		resp.setIsEnd(true);
//		resp.setCanSend(seasonServer.getSenderId().equals(player.getId()));
//		player.sendProtocol(HawkProtocol.valueOf(HP.code2.YQZZ_LEAGUE_GIFT_INFO_RESP, resp));
//		return;
//
//	}
//
//	private HawkTuple2<Integer, Integer> getSeasonMaxTurn(int season){
//		int groupMaxTurn = -1;
//		int kickoutMaxTurn = -1;
//		ConfigIterator<YQZZTimeCfg> iterator = HawkConfigManager.getInstance().getConfigIterator(YQZZTimeCfg.class);
//		for(YQZZTimeCfg cfg : iterator){
//			if(cfg.getSeason() != season){
//				continue;
//			}
//			if(cfg.getType() == YQZZWar.PBYQZZWarType.YQZZ_GROUP_VALUE && cfg.getTurn() > groupMaxTurn){
//				groupMaxTurn = cfg.getTurn();
//			}
//			if(cfg.getType() == YQZZWar.PBYQZZWarType.YQZZ_KICKOUT_VALUE && cfg.getTurn() > kickoutMaxTurn){
//				kickoutMaxTurn = cfg.getTurn();
//			}
//		}
//		HawkTuple2<Integer, Integer> tuple2 = new HawkTuple2<>(groupMaxTurn, kickoutMaxTurn);
//		return tuple2;
//	}
//
//	private YQZZSeasonServerRankAwardCfg getSendAwardCfg(int rank){
//		ConfigIterator<YQZZSeasonServerRankAwardCfg> iterator = HawkConfigManager.getInstance().getConfigIterator(YQZZSeasonServerRankAwardCfg.class);
//		for (YQZZSeasonServerRankAwardCfg cfg : iterator){
//			if(rank >= cfg.getMin() && rank <= cfg.getMax()){
//				return cfg;
//			}
//		}
//		return null;
//	}
//
//	@ProtocolHandler(code = HP.code2.YQZZ_LEAGUE_GIFT_SEND_INFO_REQ_VALUE)
//	private void onLeagueGiftSend(HawkProtocol protocol) {
//		int termId = YQZZMatchService.getInstance().getDataManger().getStateData().getTermId();
//		YQZZTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(YQZZTimeCfg.class, termId);
//		int season = timeCfg.getSeason();
//		if(season <= 0){
//			return;
//		}
//		YQZZSeasonServer seasonServer = YQZZSeasonServer.loadByServerId(season, GsConfig.getInstance().getServerId());
//		if(seasonServer == null){
//			return;
//		}
//		if(!seasonServer.getSenderId().equals(player.getId())){
//			return;
//		}
//		int rank = seasonServer.getKickoutRank() > 0? seasonServer.getKickoutRank() : seasonServer.getGroupRank();
//		YQZZSeasonServerRankAwardCfg cfg = getSendAwardCfg(rank);
//		if(cfg == null){
//			return;
//		}
//		YQZZWar.YQZZGiftSendReq req = protocol.parseProtocol(YQZZWar.YQZZGiftSendReq.getDefaultInstance());
//		if(req.getSendInfoList().size() + YQZZMatchService.getInstance().getDataManger().getGiftRecordDataList().size() > cfg.getSendNum()){
//			sendError(protocol.getType(), Status.YQZZError.YQZZ_GIFT_SERVER_LIMIT_VALUE);
//			return;
//		}
//		for(YQZZWar.YQZZGiftSendInfo info : req.getSendInfoList()) {
//			if (YQZZMatchService.getInstance().getDataManger().getGiftRecordDataMap().containsKey(info.getTargetPlayerId())) {
//				sendError(protocol.getType(), Status.YQZZError.YQZZ_GIFT_PLAYER_LIMIT_VALUE);
//				return;
//			}
//		}
//		for(YQZZWar.YQZZGiftSendInfo info : req.getSendInfoList()){
//			if(YQZZMatchService.getInstance().getDataManger().getGiftRecordDataMap().containsKey(info.getTargetPlayerId())){
//				continue;
//			}
//			Player targetPlayer = GlobalData.getInstance().makesurePlayer(info.getTargetPlayerId());
//			if(targetPlayer == null){
//				continue;
//			}
//			YQZZSeasonGiftRecordData record = new YQZZSeasonGiftRecordData();
//			Player shot = GlobalData.getInstance().makesurePlayer(info.getTargetPlayerId());
//			record.setSeason(season);
//			record.setServerId(player.getMainServerId());
//			record.setSendTime(HawkTime.getMillisecond());
//			record.setGiftId(info.getGiftId());
//			record.setSendPlayerName(player.getName());
//			record.setSendPlayerTag(player.getGuildTag());
//			record.setPlayerName(shot.getName());
//			record.setPlayerId(info.getTargetPlayerId());
//			record.setGuildTag(shot.getGuildTag());
//			YQZZMatchService.getInstance().getDataManger().addSeasonGiftRecord(record);
//			SystemMailService.getInstance().sendMail(MailParames.newBuilder()
//					.setPlayerId(info.getTargetPlayerId())
//					.setMailId(MailConst.MailId.YQZZ_LEAGUE_SERVER_LEADER_REWARD)
//					.setRewards(ItemInfo.valueListOf(cfg.getSendAward()))
//					.setAwardStatus(Const.MailRewardStatus.NOT_GET)
//					.addContents(player.getName())
//					.build());
//			HawkLog.logPrintln("yqzz send gift player reward, playerId:{}, rank: {}, sendPlayer: {}, cfgId: {}",
//					info.getTargetPlayerId(), rank, player.getId(), cfg.getId());
//		}
//		onLeagueGiftInfo(null);
//		onLeagueGiftRecord(null);
//	}
//
//	@ProtocolHandler(code = HP.code2.YQZZ_LEAGUE_GIFT_RECORD_REQ_VALUE)
//	private void onLeagueGiftRecord(HawkProtocol protocol) {
//		YQZZWar.YQZZGiftRecordResp.Builder resp = YQZZWar.YQZZGiftRecordResp.newBuilder();
//		for(YQZZSeasonGiftRecordData record : YQZZMatchService.getInstance().getDataManger().getGiftRecordDataList()){
//			resp.addRecord(record.toPB());
//		}
//		player.sendProtocol(HawkProtocol.valueOf(HP.code2.YQZZ_LEAGUE_GIFT_RECORD_RESP, resp));
//	}
//
//	@ProtocolHandler(code = HP.code2.YQZZ_SEARCH_REQ_VALUE)
//	private void onSearch(HawkProtocol protocol) {
//		YQZZWar.YQZZSearchReq req = protocol.parseProtocol(YQZZWar.YQZZSearchReq.getDefaultInstance());
//		if (!req.hasName()) {
//			player.sendError(protocol.getType(), Status.SysError.PARAMS_INVALID, 0);
//			return;
//		}
//		// 禁言玩家推送禁言提示
//		if (player.getEntity().getSilentTime() > HawkTime.getMillisecond()) {
//			IDIPBanInfo banInfo = RedisProxy.getInstance().getIDIPBanInfo(player.getId(), GsConst.IDIPBanType.BAN_SEND_MSG);
//			if (banInfo != null) {
//				YQZZWar.YQZZSearchResp.Builder response = YQZZWar.YQZZSearchResp.newBuilder();
//				response.setMsg(banInfo.getBanMsg());
//				player.sendProtocol(HawkProtocol.valueOf(HP.code2.YQZZ_SEARCH_RESP, response));
//				return;
//			}
//		}
//		GameTssService.getInstance().wordUicChatFilter(player, req.getName(),
//				com.hawk.game.protocol.Player.MsgCategory.YQZZ_SEARCH_MEMBER.getNumber(), GameMsgCategory.YQZZ_SEARCH_MEMBER,
//				String.valueOf(req.getType()), null, protocol.getType());
//	}
//
//	public YQZZWar.YLWScoreInfo.Builder buildScoreInfo(long score, List<Integer> reward){
//		YQZZWar.YLWScoreInfo.Builder builder = YQZZWar.YLWScoreInfo.newBuilder();
//		builder.setScore(score);
//		builder.addAllRewardedId(reward);
//		return builder;
//	}
//
//	public YQZZWar.PBYQZZLeagueWarServerInfo.Builder buildSlefServerInfo(){
//		String serverId = GsConfig.getInstance().getServerId();
//		//服务器
//		ServerInfo serverInfo = RedisProxy.getInstance().getServerInfo(String.valueOf(serverId));
//		// 司令
//		President president = PresidentFightService.getInstance().getPresidentCity().getPresident();
//		YQZZWar.PBYQZZLeagueWarServerInfo.Builder builder = YQZZWar.PBYQZZLeagueWarServerInfo.newBuilder();
//		builder.setServerId(serverId);
//		builder.setServerName(serverInfo.getName());
//		builder.setScore(0);
//		builder.setRank(-1);
//		builder.setWinPoint(0);
//		builder.setLastRank(0);
//		if(president != null &&
//				HawkOSOperator.isEmptyString(president.getPlayerId())){
//			builder.setLeaderName(president.getPlayerName());
//		}
//		return builder;
//	}
//
//	public YQZZWar.PBYQZZLeagueWarGuildInfo.Builder buildSelfGuildInfo(int rank, long score){
//		YQZZWar.PBYQZZLeagueWarGuildInfo.Builder guildInfo = YQZZWar.PBYQZZLeagueWarGuildInfo.newBuilder();
//		guildInfo.setServerId(GsConfig.getInstance().getServerId());
//		guildInfo.setRank(rank);
//		guildInfo.setScore(score);
//		if(player.hasGuild()){
//			guildInfo.setGuildName(player.getGuildName());
//			guildInfo.setGuildLeader(player.getGuildLeaderName());
//			guildInfo.setGuildFlag(player.getGuildFlag());
//			guildInfo.setGuildTag(player.getGuildTag());
//		}else {
//			guildInfo.setGuildTag("");
//			guildInfo.setGuildName("");
//			guildInfo.setGuildLeader("");
//		}
//		return guildInfo;
//	}
//	
//	
//	private void doSeasonMatch(){
//		int termId = this.getDataManager().getStateData().getTermId();
//		YQZZTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(YQZZTimeCfg.class, termId);
//		int type = timeCfg.getType();
//		YQZZWar.PBYQZZWarType warType = YQZZWar.PBYQZZWarType.valueOf(type);
//		switch (warType){
//			case YQZZ_NOT_SEASON:{
//			}
//			break;
//			case YQZZ_GROUP:{
//				doSeasonGroupMatch();
//			}
//			break;
//			case YQZZ_KICKOUT:{
//				doSeasonKickoutMatch();
//			}
//			break;
//			default:{
//			}
//		}
//	}
//
//
//	private void doSeasonGroupMatch(){
//		int termId = this.getDataManager().getStateData().getTermId();
//		String matchServer = GsConfig.getInstance().getServerId();
//		long curTime = HawkTime.getMillisecond();
//		Map<String, YQZZJoinServer> serverMap = this.getDataManager().loadAllYQZZJoinServerData();
//		List<YQZZJoinServer> serverList = new ArrayList<>();
//		serverList.addAll(serverMap.values());
//		Map<String,YQZZMatchRoomData> rooms = new HashMap<>();
//		rooms.putAll(creatSeasonRoom(serverList, YQZZWar.PBYQZZWarType.YQZZ_GROUP));
//		//保存房间数据
//		YQZZMatchRoomData.saveAll(termId, rooms);
//		//保存匹配数据
//		YQZZMatchData data = new YQZZMatchData(termId, matchServer, curTime);
//		data.saveRedis();
//	}
//
//	private void doSeasonKickoutMatch(){
//		String matchServer = GsConfig.getInstance().getServerId();
//		long curTime = HawkTime.getMillisecond();
//		int termId = this.getDataManager().getStateData().getTermId();
//		YQZZTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(YQZZTimeCfg.class, termId);
//		int season = timeCfg.getSeason();
//		Map<String, YQZZSeasonServer> serverMap = YQZZSeasonServer.loadAll(season);
//		Map<String, YQZZJoinServer> joinServerMap = this.getDataManager().loadAllYQZZJoinServerData();
//		List<YQZZJoinServer> advanceServerList = new ArrayList<>();
//		List<YQZZJoinServer> kickoutServerList = new ArrayList<>();
//		for(YQZZJoinServer joinServer : joinServerMap.values()){
//			YQZZSeasonServer seasonServer = serverMap.get(joinServer.getServerId());
//			if(seasonServer != null && seasonServer.isAdvance()){
//				advanceServerList.add(joinServer);
//			}else {
//				kickoutServerList.add(joinServer);
//			}
//		}
//		Map<String,YQZZMatchRoomData> rooms = new HashMap<>();
//		rooms.putAll(creatSeasonRoom(kickoutServerList, YQZZWar.PBYQZZWarType.YQZZ_NOT_SEASON));
//		rooms.putAll(creatSeasonRoom(advanceServerList, YQZZWar.PBYQZZWarType.YQZZ_KICKOUT));
//		//保存房间数据
//		YQZZMatchRoomData.saveAll(termId, rooms);
//		//保存匹配数据
//		YQZZMatchData data = new YQZZMatchData(termId, matchServer, curTime);
//		data.saveRedis();
//	}
//
//	private Map<String,YQZZMatchRoomData> creatSeasonRoom(List<YQZZJoinServer> serverList, YQZZWar.PBYQZZWarType warType){
//		int termId = this.getDataManager().getStateData().getTermId();
//		YQZZTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(YQZZTimeCfg.class, termId);
//		Map<String,YQZZMatchRoomData> rooms = new HashMap<String,YQZZMatchRoomData>();
//		int serverCount = serverList.size();
//		int perCount = timeCfg.getMatchNeedCount();
//		int poolSize = timeCfg.getMatchListCount();
//		int p1 = serverCount / perCount;
//		int p2 = serverCount % perCount;
//		int roomCount = p1;
//		int lessIndex = serverCount + 1;
//		if(p2 > 0){
//			//最后一个房间元素不够，最后一个房间的元素个数是  perCount -1
//			roomCount += 1;
//			int p3 = perCount -1;
//			//共有lessCount个房间少一个元素
//			int lessCount = p3 - p2 + 1;
//			//从lessIndex 开始每个房间少一个元素
//			lessIndex = roomCount - lessCount + 1;
//		}
//		List<YQZZJoinServer> firstServerList = new ArrayList<>();
//		switch (warType){
//			case YQZZ_NOT_SEASON:{
//				Collections.sort(serverList,new Comparator<YQZZJoinServer>() {
//					@Override
//					public int compare(YQZZJoinServer o1, YQZZJoinServer o2) {
//						if(o1.getPower() != o2.getPower()){
//							return o1.getPower() > o2.getPower()?-1 :1;
//						}
//						return 0;
//					}
//				});
//			}
//			break;
//			case YQZZ_GROUP:{
//				Collections.sort(serverList,new Comparator<YQZZJoinServer>() {
//					@Override
//					public int compare(YQZZJoinServer o1, YQZZJoinServer o2) {
//						if(o1.getPower() != o2.getPower()){
//							return o1.getPower() > o2.getPower()?-1 :1;
//						}
//						return 0;
//					}
//				});
//				for(int i = 0; i < roomCount; i++ ){
//					if(serverList.isEmpty()){
//						break;
//					}
//					YQZZJoinServer server = serverList.remove(0);
//					firstServerList.add(server);
//				}
//				Collections.shuffle(serverList);
//			}
//			break;
//			case YQZZ_KICKOUT:{
//				if(timeCfg.getTurn() == 1){
//					HawkRedisSession redisSession = ActivityGlobalRedis.getInstance().getRedisSession();
//					Map<String,String> groupRankMap = redisSession.hGetAll(YQZZSeasonServer.getGroupRankKey(timeCfg.getSeason()));
//					Collections.sort(serverList,new Comparator<YQZZJoinServer>() {
//						@Override
//						public int compare(YQZZJoinServer o1, YQZZJoinServer o2) {
//							int rank1 = Integer.parseInt(groupRankMap.getOrDefault(o1.getServerId(), "100"));
//							int rank2 = Integer.parseInt(groupRankMap.getOrDefault(o2.getServerId(), "100"));
//							if(rank1 != rank2){
//								return rank1 < rank2 ? -1 : 1;
//							}
//							return 0;
//						}
//					});
//				}else {
//					Collections.sort(serverList,new Comparator<YQZZJoinServer>() {
//						@Override
//						public int compare(YQZZJoinServer o1, YQZZJoinServer o2) {
//							YQZZRecordData data1= YQZZRecordData.loadData(o1.getServerId(), termId - 1);
//							YQZZRecordData data2= YQZZRecordData.loadData(o2.getServerId(), termId - 1);
//							int rank1 = 10;
//							int rank2 = 10;
//							long seasonScore1 = 0;
//							long seasonScore2 = 0;
//							long score1 = 0;
//							long score2 = 0;
//							if(data1 != null){
//								rank1 = data1.getRank();
//								seasonScore1 = data1.getSeasonScore();
//								score1 = data1.getSeasonScore();
//							}
//							if(data2 != null){
//								rank2 = data2.getRank();
//								seasonScore2 = data2.getSeasonScore();
//								score2 = data2.getSeasonScore();
//							}
//							if(rank1 != rank2){
//								return rank1 < rank2 ? -1 : 1;
//							}
//							if(seasonScore1 != seasonScore2){
//								return seasonScore1 > seasonScore2 ? -1 : 1;
//							}
//							if(score1 != score2){
//								return score1 > score2 ? -1 : 1;
//							}
//							return 0;
//						}
//					});
//				}
//				for(int i = 0; i < roomCount; i++ ){
//					if(serverList.isEmpty()){
//						break;
//					}
//					YQZZJoinServer server = serverList.remove(0);
//					firstServerList.add(server);
//				}
//				Collections.shuffle(serverList);
//			}
//			break;
//		}
//		for(int i = 1;i<=roomCount;i++) {
//			int getCount = perCount;
//			if (i >= lessIndex) {
//				getCount = perCount - 1;
//			}
//			//没有了 ,结束循环
//			if (serverList.size() <= 0) {
//				break;
//			}
//			//开始拿去
//			List<YQZZJoinServer> roomServers = new ArrayList<>();
//
//			switch (warType) {
//				case YQZZ_NOT_SEASON: {
//					roomServers = this.getMatchRoomServers(serverList, poolSize, getCount);
//				}
//				break;
//				case YQZZ_GROUP:{
//					roomServers = this.getMatchRoomServers(firstServerList, serverList, getCount);
//				}
//				break;
//				case YQZZ_KICKOUT:{
//					roomServers = this.getMatchRoomServers(firstServerList, serverList, getCount);
//				}
//				break;
//			}
//			if (roomServers.isEmpty()) {
//				continue;
//			}
//			//排序
//			Collections.sort(roomServers, new Comparator<YQZZJoinServer>() {
//				@Override
//				public int compare(YQZZJoinServer o1, YQZZJoinServer o2) {
//					if (o1.getPower() != o2.getPower()) {
//						return o1.getPower() > o2.getPower() ? -1 : 1;
//					}
//					return 0;
//				}
//			});
//			String roomServerId = this.getRoomServer(roomServers);
//			YQZZMatchRoomData room = new YQZZMatchRoomData();
//			room.setTermId(termId);
//			room.setRoomId(HawkUUIDGenerator.genUUID());
//			room.setRoomServerId(roomServerId);
//			Set<String> joinServerSet = new HashSet<>();
//			for (YQZZJoinServer roomServer : roomServers) {
//				room.addServer(roomServer.getServerId());
//				joinServerSet.add(roomServer.getServerId());
//			}
//			if(warType == YQZZWar.PBYQZZWarType.YQZZ_KICKOUT){
//				room.setAdvance(true);
//			}
//			rooms.put(room.getRoomId(), room);
//			try {
//				String joinServerStr = SerializeHelper.collectionToString(joinServerSet, SerializeHelper.ATTRIBUTE_SPLIT);
//				for (YQZZJoinServer roomServer : roomServers) {
//					LogUtil.logYQZZMatch(termId, roomServer.getServerId(), roomServer.getPower(), joinServerStr, roomServerId);
//				}
//			} catch (Exception e) {
//				HawkException.catchException(e);
//			}
//		}
//		return rooms;
//	}
//
//	
//	
//	public void logBattleStartPower(){
//		try {
//			int termId = this.getDataManager().getStateData().getTermId();
//			long power = getMatchPower(termId);
//			String serverId = GsConfig.getInstance().getServerId();
//			LogUtil.logYQZZBattleStartPower(termId, serverId, power);
//		}catch (Exception e){
//			HawkException.catchException(e);
//		}
//	}
//
//	public long getMatchPower(int termId){
//		try {
//			YQZZWarConstCfg cfg = HawkConfigManager.getInstance().getKVInstance(YQZZWarConstCfg.class);
//			//获取列表
//			int count = cfg.getMoonMatchNumLimit() -1;
//			count = Math.max(count, 0);
//			Set<Tuple> rankList = MatchStrengthRank.getInstance().getStrengthList(count);
//			//列表为空则不走写入逻辑.
//			if (rankList == null || rankList.size() <= 0) {
//				return 0;
//			}
//			double memberPower = 0;
//			int rank = 0;
//			for(Tuple info : rankList){
//				rank ++;
//				String playerId = info.getElement();
//				long power = (long) info.getScore();
//				double powerWeight = this.getPowerWeight(rank);
//				double addPower =  (power * powerWeight);
//				memberPower += addPower;
//				//日志
//				//LogUtil.logCrossActivityPlayerStrength(termId, playerId, rank, power, powerWeight, addPower);
//				HawkLog.logPrintln("YQZZActivityService battle power,termId:{},playerId:{},rank:{},power:{},powerWeight:{},memberPower:{},",
//						termId,playerId,rank,power,powerWeight,addPower);
//			}
//
//			double teamParam = this.getTeamMatchParam(termId);
//			long matchPower = (long) (teamParam * memberPower);
//			HawkLog.logPrintln("YQZZActivityService battle power,termId:{},matchPower:{}",termId, matchPower);
//			return matchPower;
//		} catch (Exception e) {
//			HawkException.catchException(e);
//		}
//		return 0;
//	}
//
//	/**
//	 * 队伍磨合参数
//	 * @param teamId
//	 * @return
//	 */
//	private double getTeamMatchParam(int termId){
//		YQZZWarConstCfg cfg = HawkConfigManager.getInstance().getKVInstance(YQZZWarConstCfg.class);
//		int count = cfg.getMoonMatchTimesLimit() -1;
//		count = Math.max(count, 0);
//		List<Integer> terms = new ArrayList<>();
//		for(int i=1;i<=count;i++){
//			int termTemp = termId -i;
//			if(termTemp >= 1){
//				terms.add(termTemp);
//			}
//		}
//
//		String serverId = GsConfig.getInstance().getServerId();
//		Map<Integer, YQZZRecordData> logList = YQZZRecordData.loadAll(serverId, terms);
//		double param = 0;
//		for(YQZZRecordData record : logList.values()){
//			//int historyTerm = record.getTermId();
//			int rank = record.getRank();
//			double rankParam = cfg.getMoonMatchBattleResultValue(rank);
//			param += rankParam;
//			//日志
//			//LogUtil.logCrossActivityTeamParam(termId, historyTerm, rank, rankParam);
//			HawkLog.logPrintln("YQZZActivityService battle power, getTeamMatchParam,termId:{},rank:{},param:{}",
//					record.getTermId(),rank,rankParam);
//		}
//		param = Math.min(param, cfg.getMoonMatchCofMaxValue());
//		param = Math.max(param, cfg.getMoonMatchCofMinValue());
//		HawkLog.logPrintln("YQZZActivityService battle power, getTeamMatchParam, result,termId:{},param:{}",termId, param);
//		return param + 1;
//	}
//
//	/**
//	 * 战力排名权重
//	 * @param rank
//	 * @return
//	 */
//	private double getPowerWeight(int rank){
//		List<TeamStrengthWeightCfg> cfgList = AssembleDataManager.getInstance().getTeamStrengthWeightCfgList(40);
//		for(TeamStrengthWeightCfg cfg : cfgList){
//			if(cfg.getRankUpper()<= rank && rank <= cfg.getRankLower()){
//				return cfg.getWeightValue();
//			}
//		}
//		return 0;
//	}
//
//	
//	
//
//	private void checkServerPointReward(YQZZSeasonServer seasonServer){
//		ConfigIterator<YQZZSeasonServerAwardCfg> iterator = HawkConfigManager.getInstance().getConfigIterator(YQZZSeasonServerAwardCfg.class);
//		for(YQZZSeasonServerAwardCfg cfg : iterator) {
//			if (cfg.getScore() > seasonServer.getTotalPoint()) {
//				continue;
//			}
//			if (seasonServer.getReward().contains(cfg.getId())) {
//				continue;
//			}
//			seasonServer.getReward().add(cfg.getId());
//			seasonServer.saveRedis();
//			HawkLog.logPrintln("yqzz send point server reward, playerId:{}, score: {}, cfgId: {}",seasonServer.getServerId(),seasonServer.getTotalPoint(),cfg.getId());
//			long currTime = HawkTime.getMillisecond();
//			long experiTime = currTime + HawkTime.DAY_MILLI_SECONDS * 7;
//			SystemMailService.getInstance().addGlobalMail(MailParames.newBuilder()
//					.setMailId(MailId.YQZZ_LEAGUE_SERVER_POINT_REWARD)
//					.addContents(seasonServer.getTotalPoint())
//					.setRewards(cfg.getRewardItems())
//					.setAwardStatus(MailRewardStatus.NOT_GET)
//					.build(), currTime, currTime + experiTime);
//		}
//	}
//	
//	
//	private void sendGuildAward(){
//		int termId = this.getDataManager().getStateData().getTermId();
//		YQZZTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(YQZZTimeCfg.class, termId);
//		YQZZBattleData battleData = this.getDataManager().getBattleData();
//		if(battleData == null){
//			return;
//		}
//		YQZZSeasonServer seasonServer = YQZZSeasonServer.loadByServerId(timeCfg.getSeason(), GsConfig.getInstance().getServerId());
//		Map<String,YQZZGuildGameData> guilds = battleData.getGuildDatas();
//		//联盟奖励邮件
//		for(YQZZGuildGameData data : guilds.values()){
//			String guildId = data.getGuildId();
//			if(GuildService.getInstance().getGuildInfoObject(guildId) == null){
//				continue;
//			}
//			int guildRank = data.getRank();
//			long score = data.getScore();
//			YQZZGuildRankAwardCfg guildAwardCfg = this.getYQZZGuildRankAwardCfg(guildRank);
//			if(guildAwardCfg == null){
//				continue;
//			}
//			AwardItems award = AwardItems.valueOf();
//			award.addItemInfos(guildAwardCfg.getRewardList());
//			MailParames.Builder paramesBuilder = MailParames.newBuilder()
//					.setMailId(MailId.YQZZ_ACTIVITY_GUILD_RANK_REWARD)
//					.addContents(score, guildRank)
//					.setRewards(award.getAwardItems())
//					.setAwardStatus(MailRewardStatus.NOT_GET);
//			GuildMailService.getInstance().sendGuildMail(guildId, paramesBuilder);
//			HawkLog.logPrintln("yqzz send rank guild reward, guildId:{}, rank: {}, score: {}, cfgId: {}",
//					guildId, guildRank, score, guildAwardCfg.getId());
//			LogUtil.logYQZZGuildRankReward(termId, guildId, data.getGuildName(), score, guildRank, guildAwardCfg.getId());
//			try {
//				if(timeCfg.getSeason() > 0){
//					YQZZSeasonGuild seasonGuild = YQZZSeasonGuild.loadByGuildId(timeCfg.getSeason(), guildId);
//					if(seasonGuild ==null){
//						GuildInfoObject guild = GuildService.getInstance().getGuildInfoObject(guildId);
//						seasonGuild = new YQZZSeasonGuild();
//						seasonGuild.setGuildId(guildId);
//						seasonGuild.setServerId(guild.getServerId());
//						seasonGuild.setGuildName(guild.getName());
//						seasonGuild.setGuildTag(guild.getTag());
//						seasonGuild.setGuildFlag(guild.getFlagId());
//						seasonGuild.setSeason(timeCfg.getSeason());
//					}
//					if(timeCfg.getType() == YQZZWar.PBYQZZWarType.YQZZ_KICKOUT_VALUE && seasonServer !=null && seasonServer.isAdvance()){
//						seasonGuild.setKickoutPoint(seasonGuild.getKickoutPoint() + score);
//					}
//					seasonGuild.setTotalPoint(seasonGuild.getTotalPoint() + score);
//					checkGuildPointReward(seasonGuild);
//					seasonGuild.saveRedis();
//				}
//			}catch (Exception e){
//				HawkException.catchException(e);
//			}
//		}
//		Optional<SeasonActivity> opActivity = ActivityManager.getInstance().getGameActivityByType(ActivityType.SEASON_ACTIVITY.intValue());
//		if (!opActivity.isPresent()) {
//			return;
//		}
//		SeasonActivity activity = opActivity.get();
//		for(YQZZGuildGameData data : guilds.values()) {
//			String guildId = data.getGuildId();
//			if (GuildService.getInstance().getGuildInfoObject(guildId) == null) {
//				continue;
//			}
//			int guildRank = data.getRank();
//			activity.addGuildGradeExpFromMatchRank(Activity.SeasonMatchType.S_YQZZ, guildId, guildRank);
//		}
//	}
//
//	private void checkGuildPointReward(YQZZSeasonGuild seasonGuild){
//		Collection<String> idList = GuildService.getInstance().getGuildMembers(seasonGuild.getGuildId());
//		for (String playerId : idList) {
//			HawkLog.logPrintln("yqzz send point guild reward, guildId:{}, score: {}",playerId,seasonGuild.getTotalPoint());
//			YQZZSeasonPlayer seasonPlayer = YQZZSeasonPlayer.loadByPlayerId(seasonGuild.getSeason(), playerId);
//			if(seasonPlayer==null) {
//				seasonPlayer = new YQZZSeasonPlayer();
//				seasonPlayer.setPlayerId(playerId);
//				seasonPlayer.setSeason(seasonGuild.getSeason());
//				seasonPlayer.setServerId(GsConfig.getInstance().getServerId());
//			}
//			seasonPlayer.saveRedis();
//			ConfigIterator<YQZZSeasonGuildAwardCfg> iterator = HawkConfigManager.getInstance().getConfigIterator(YQZZSeasonGuildAwardCfg.class);
//			for(YQZZSeasonGuildAwardCfg cfg : iterator){
//				if(cfg.getScore() > seasonGuild.getTotalPoint() || cfg.getRewardItems().isEmpty()){
//					continue;
//				}
//				if(seasonPlayer.getGuildReward().contains(cfg.getId())){
//					continue;
//				}
//				seasonPlayer.getGuildReward().add(cfg.getId());
//				seasonPlayer.saveRedis();
//				HawkLog.logPrintln("yqzz send point guild reward, guildId:{}, score: {}, cfgId: {}",playerId,seasonGuild.getTotalPoint(),cfg.getId());
//				SystemMailService.getInstance().sendMail(MailParames.newBuilder()
//						.setPlayerId(playerId)
//						.setMailId(MailId.YQZZ_LEAGUE_GUILD_POINT_REWARD)
//						.addContents(seasonGuild.getTotalPoint())
//						.setRewards(cfg.getRewardItems())
//						.setAwardStatus(MailRewardStatus.NOT_GET)
//						.build());
//			}
//			seasonPlayer.saveRedis();
//		}
//	}
//
//
//	private void checkPlayerPointReward(YQZZSeasonPlayer seasonPlayer){
//		ConfigIterator<YQZZSeasonPersonAwardCfg> iterator = HawkConfigManager.getInstance().getConfigIterator(YQZZSeasonPersonAwardCfg.class);
//		for(YQZZSeasonPersonAwardCfg cfg : iterator){
//			if(cfg.getScore() > seasonPlayer.getTotalPoint()){
//				continue;
//			}
//			if(seasonPlayer.getReward().contains(cfg.getId())){
//				continue;
//			}
//			seasonPlayer.getReward().add(cfg.getId());
//			seasonPlayer.saveRedis();
//			HawkLog.logPrintln("yqzz send point player reward, playerId:{}, score: {}, cfgId: {}",seasonPlayer.getPlayerId(),seasonPlayer.getTotalPoint(),cfg.getId());
//			SystemMailService.getInstance().sendMail(MailParames.newBuilder()
//					.setPlayerId(seasonPlayer.getPlayerId())
//					.setMailId(MailId.YQZZ_LEAGUE_PLAYER_POINT_REWARD)
//					.addContents(seasonPlayer.getTotalPoint())
//					.setRewards(cfg.getRewardItems())
//					.setAwardStatus(MailRewardStatus.NOT_GET)
//					.build());
//		}
//	}
//	
	
	
	
	
	
	
//	if(timeCfg.getSeason() > 0){
//		ActivityManager.getInstance().postEvent(new YQZZScoreEvent(playerId, score,HawkTime.getMillisecond(),true));
//		try {
//			YQZZSeasonPlayer seasonPlayer = YQZZSeasonPlayer.loadByPlayerId(timeCfg.getSeason(), playerId);
//			if(seasonPlayer == null){
//				seasonPlayer = new YQZZSeasonPlayer();
//				seasonPlayer.setPlayerId(playerId);
//				seasonPlayer.setServerId(GsConfig.getInstance().getServerId());
//				seasonPlayer.setSeason(timeCfg.getSeason());
//			}
//			seasonPlayer.setTotalPoint(seasonPlayer.getTotalPoint() + score);
//			checkPlayerPointReward(seasonPlayer);
//			seasonPlayer.saveRedis();
//		}catch (Exception e){
//			HawkException.catchException(e);
//		}
//	}
//	
//	
//	
//	private void gmAddScore(){
//		long now = HawkTime.getMillisecond();
//		//15分钟后开始发奖，这样保证玩家都回来了
//		int termId = this.getDataManager().getStateData().getTermId();
//		YQZZTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(YQZZTimeCfg.class, termId);
//		if(timeCfg.getSeason() > 0){
//			Map<String, Integer> pointMap = new HashMap<>();
//			// 读文件
//			List<String> infos = new ArrayList<>();
//			try {
//				HawkOSOperator.readTextFileLines("tmp/yqzz_gm_server_info.txt", infos);
//				if(infos.size() > 0){
//					for (String info : infos) {
//						String serverId = info.split(",")[0];
//						int point = Integer.parseInt(info.split(",")[3]);
//						pointMap.put(serverId, point);
//					}
//				}
//			} catch (Exception e) {
//				HawkException.catchException(e);
//			}
//			int season = timeCfg.getSeason();
//			Map<String, YQZZSeasonServer> serverMap = YQZZSeasonServer.loadAll(season);
//			Map<String, YQZZMatchRoomData> dataMap = YQZZMatchRoomData.loadAllData(termId);
//			YQZZWarConstCfg constCfg = HawkConfigManager.getInstance().getKVInstance(YQZZWarConstCfg.class);
//			for(YQZZMatchRoomData roomData : dataMap.values()){
//				int i = 0;
//				for(String serverId : roomData.getServers()){
//					i++;
//					YQZZSeasonServer server = serverMap.get(serverId);
//					if(server == null){
//						continue;
//					}
//					int point = pointMap.getOrDefault(serverId, 0);
//					server.setLastRank(i);
//					long scoreAdd = (long)(point * constCfg.getGroupWinpointAdd() / 10000) + constCfg.getGroupRankAdd(i);
//					if(timeCfg.getType() == YQZZWar.PBYQZZWarType.YQZZ_GROUP_VALUE){
//						server.addScore(scoreAdd);
//					}
//					server.setTotalPoint(server.getTotalPoint() + point);
//					server.saveRedis();
//					YQZZRecordData recordData = new YQZZRecordData();
//					recordData.setTermId(termId);
//					recordData.setServerId(serverId);
//					recordData.setRoomId(roomData.getRoomId());
//					recordData.setRank(i);
//					recordData.setScore(point);
//					if(timeCfg.getType() == YQZZWar.PBYQZZWarType.YQZZ_GROUP_VALUE){
//						recordData.setSeasonScore(scoreAdd);
//					}
//					recordData.setSendAward(0);
//					recordData.setTime(now);
//					recordData.saveRedis();
//				}
//			}
//		}
//	}
//
//	
//	
//	private void seasonKickout(){
//		int termId = this.getDataManager().getStateData().getTermId();
//		YQZZTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(YQZZTimeCfg.class, termId);
//		if(timeCfg.getSeason() <= 0){
//			return;
//		}
//		int season = timeCfg.getSeason();
//		int type = timeCfg.getType();
//		Map<String, YQZZSeasonServer> serverMap = YQZZSeasonServer.loadAll(season);
//		Map<String, YQZZMatchRoomData> dataMap = YQZZMatchRoomData.loadAllData(termId);
//		HawkTuple2<Integer, Integer> turnTuple2 = getSeasonMaxTurn(season);
//		YQZZWar.PBYQZZWarType warType = YQZZWar.PBYQZZWarType.valueOf(type);
//		switch (warType){
//			case YQZZ_NOT_SEASON:{
//			}
//			break;
//			case YQZZ_GROUP:{
//				List<YQZZSeasonServer> serverList = new ArrayList<>();
//				serverList.addAll(serverMap.values());
//				Collections.sort(serverList,new Comparator<YQZZSeasonServer>(){
//					@Override
//					public int compare(YQZZSeasonServer o1, YQZZSeasonServer o2) {
//						if(o1.getScore() != o2.getScore()){
//							return o1.getScore() > o2.getScore() ? -1 : 1;
//						}
//						if(o1.getTotalPoint() != o2.getTotalPoint()){
//							return o1.getTotalPoint() > o2.getTotalPoint() ? -1 : 1;
//						}
//						if(o1.getPower() != o2.getPower()){
//							return o1.getPower() > o2.getPower() ? -1 : 1;
//						}
//						return 0;
//					}
//				});
//				int rank = 1;
//				for(YQZZSeasonServer server : serverList){
//					YQZZSeasonServer.updateGroupRank(season, server.getServerId(), rank);
//					rank++;
//				}
//				if(timeCfg.getTurn() >= turnTuple2.first){
//					YQZZWarConstCfg constConfig = HawkConfigManager.getInstance().getKVInstance(YQZZWarConstCfg.class);
//					if(GsConfig.getInstance().isDebug() && constConfig.isGm()){
//						// 读文件
//						List<String> infos = new ArrayList<>();
//						try {
//							HawkOSOperator.readTextFileLines("tmp/yqzz_gm_server_info.txt", infos);
//						} catch (Exception e) {
//							HawkException.catchException(e);
//						}
//						if(infos.size() > 0){
//							Map<String,Integer> rankMap = new HashMap<>();
//							for (String info : infos) {
//								String serverId = info.split(",")[0];
//								int debugRank = Integer.parseInt(info.split(",")[2]);
//								rankMap.put(serverId, debugRank);
//							}
//							int needCount = 6 * turnTuple2.second;
//							for(YQZZSeasonServer server : serverList){
//								int debugRank = rankMap.getOrDefault(server.getServerId(), 0);
//								if(debugRank != 0 && debugRank <= needCount){
//									server.setAdvance(true);
//									server.saveRedis();
//								}
//								if(debugRank != 0){
//									server.setGroupRank(debugRank);
//									server.saveRedis();
//									if(debugRank > needCount){
//										YQZZSeasonServer.updateFinalRank(season, server.getServerId(), debugRank);
//									}
//								}
//								YQZZSeasonServer.updateGroupRank(season, server.getServerId(), debugRank);
//							}
//						}
//					}else {
//						int needCount = 6 * turnTuple2.second;
//						needCount = Math.min(needCount, serverList.size());
//						for(int i = 0; i < needCount; i++){
//							YQZZSeasonServer server = serverList.get(i);
//							server.setAdvance(true);
//							server.setGroupRank(i+1);
//							server.saveRedis();
//						}
//						int finalRank = needCount + 1;
//						for(int i = needCount; i < serverList.size(); i++){
//							YQZZSeasonServer server = serverList.get(i);
//							server.setGroupRank(finalRank);
//							server.saveRedis();
//							YQZZSeasonServer.updateFinalRank(season, server.getServerId(), finalRank);
//							finalRank++;
//						}
//					}
//				}
//			}
//			break;
//			case YQZZ_KICKOUT:{
//				if(timeCfg.getTurn() < turnTuple2.second){
//					int needCount = 6 * (turnTuple2.second - timeCfg.getTurn());
//					int group = turnTuple2.second - timeCfg.getTurn() + 1;
//					int remainder = needCount % group;
//					int advanceRank = needCount / group;
//					List<YQZZSeasonServer> serverList = new ArrayList<>();
//					serverList.addAll(serverMap.values());
//					List<YQZZSeasonServer> advanceServerList = new ArrayList<>();
//					List<YQZZSeasonServer> kickoutServerList = new ArrayList<>();
//					for(YQZZSeasonServer server : serverList){
//						if(!server.isAdvance()){
//							continue;
//						}
//						if(server.getLastRank() > advanceRank){
//							kickoutServerList.add(server);
//						}else {
//							advanceServerList.add(server);
//						}
//					}
//					Collections.sort(kickoutServerList,new Comparator<YQZZSeasonServer>(){
//						@Override
//						public int compare(YQZZSeasonServer o1, YQZZSeasonServer o2) {
//							if(o1.getLastRank() != o2.getLastRank()){
//								return o1.getLastRank() < o2.getLastRank() ? -1 : 1;
//							}
//							YQZZRecordData data1= YQZZRecordData.loadData(o1.getServerId(), termId);
//							YQZZRecordData data2= YQZZRecordData.loadData(o2.getServerId(), termId);
//							long score1 = 0;
//							long score2 = 0;
//							if(data1 != null){
//								score1 = data1.getScore();
//							}
//							if(data2 != null){
//								score2 = data2.getScore();
//							}
//							if(score1 != score2){
//								return score1 > score2 ? -1 : 1;
//							}
//							if(o1.getPower() != o2.getPower()){
//								return o1.getPower() > o2.getPower() ? -1 : 1;
//							}
//							return 0;
//						}
//					});
//					int i = 0;
//					int rank = needCount + 1;
//					for(YQZZSeasonServer server : kickoutServerList){
//						if(i >= remainder){
//							server.setAdvance(false);
//							server.setKickoutRank(rank);
//							server.saveRedis();
//							YQZZSeasonServer.updateFinalRank(season, server.getServerId(), rank);
//							rank++;
//						}else {
//							YQZZRecordData data = YQZZRecordData.loadData(server.getServerId(), termId);
//							if(data != null){
//								data.setAdvance(true);
//								data.saveRedis();
//							}
//						}
//						i++;
//					}
//					for(YQZZSeasonServer server : advanceServerList){
//						YQZZRecordData data = YQZZRecordData.loadData(server.getServerId(), termId);
//						if(data != null){
//							data.setAdvance(true);
//							data.saveRedis();
//						}
//					}
//				}else if(timeCfg.getTurn() == turnTuple2.second){
//					List<YQZZSeasonServer> serverList = new ArrayList<>();
//					serverList.addAll(serverMap.values());
//					for(YQZZSeasonServer server : serverList){
//						if(!server.isAdvance()){
//							continue;
//						}
//						server.setAdvance(false);
//						server.setKickoutRank(server.getLastRank());
//						server.saveRedis();
//						YQZZSeasonServer.updateFinalRank(season, server.getServerId(), server.getLastRank());
//					}
//					YQZZSeasonTitle.delAll();
//				}
//			}
//			break;
//			default:{
//			}
//		}
//		String matchServer = GsConfig.getInstance().getServerId();
//		YQZZKickoutData data = new YQZZKickoutData(termId, matchServer, HawkTime.getMillisecond());
//		data.saveRedis();
//	}
//
//	private HawkTuple2<Integer, Integer> getSeasonMaxTurn(int season){
//		int groupMaxTurn = -1;
//		int kickoutMaxTurn = -1;
//		ConfigIterator<YQZZTimeCfg> iterator = HawkConfigManager.getInstance().getConfigIterator(YQZZTimeCfg.class);
//		for(YQZZTimeCfg cfg : iterator){
//			if(cfg.getSeason() != season){
//				continue;
//			}
//			if(cfg.getType() == YQZZWar.PBYQZZWarType.YQZZ_GROUP_VALUE && cfg.getTurn() > groupMaxTurn){
//				groupMaxTurn = cfg.getTurn();
//			}
//			if(cfg.getType() == YQZZWar.PBYQZZWarType.YQZZ_KICKOUT_VALUE && cfg.getTurn() > kickoutMaxTurn){
//				kickoutMaxTurn = cfg.getTurn();
//			}
//		}
//		HawkTuple2<Integer, Integer> tuple2 = new HawkTuple2<>(groupMaxTurn, kickoutMaxTurn);
//		return tuple2;
//	}
//
//
//	private void sendSeasonMail(){
//		try {
//			int termId = this.getDataManager().getStateData().getTermId();
//			YQZZTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(YQZZTimeCfg.class, termId);
//			if(timeCfg.getSeason() <= 0){
//				return;
//			}
//			int season = timeCfg.getSeason();
//			int type = timeCfg.getType();
//			HawkTuple2<Integer, Integer> turnTuple2 = getSeasonMaxTurn(season);
//			YQZZWar.PBYQZZWarType warType = YQZZWar.PBYQZZWarType.valueOf(type);
//			switch (warType) {
//				case YQZZ_NOT_SEASON: {
//				}
//				break;
//				case YQZZ_GROUP: {
//					if(timeCfg.getTurn() == turnTuple2.first){
//						String spKey = "YQZZ:SEASON_GROUP_END:" + season + ":" + GsConfig.getInstance().getServerId();
//						String val = ActivityGlobalRedis.getInstance().getRedisSession().getString(spKey);
//						if (StringUtils.isNotEmpty(val)){
//							return;
//						}
//						ActivityGlobalRedis.getInstance().getRedisSession().setString(spKey, spKey);
//						YQZZSeasonServer server = YQZZSeasonServer.loadByServerId(season, GsConfig.getInstance().getServerId());
//						if(server != null && server.getScore() != 0){
//							if(server.isAdvance()){
//								long currTime = HawkTime.getMillisecond();
//								long experiTime = currTime + HawkTime.DAY_MILLI_SECONDS * 7;
//								SystemMailService.getInstance().addGlobalMail(MailParames.newBuilder()
//										.setMailId(MailConst.MailId.YQZZ_LEAGUE_GROUP_ADVANCE)
//										.addContents(server.getScore(), server.getGroupRank())
//										.build(), currTime, currTime + experiTime);
//							}else {
//								long currTime = HawkTime.getMillisecond();
//								long experiTime = currTime + HawkTime.DAY_MILLI_SECONDS * 7;
//								SystemMailService.getInstance().addGlobalMail(MailParames.newBuilder()
//										.setMailId(MailConst.MailId.YQZZ_LEAGUE_GROUP_KICKOUT)
//										.addContents(server.getScore(), server.getGroupRank())
//										.build(), currTime, currTime + experiTime);
//							}
//						}
//					}
//				}
//				break;
//				case YQZZ_KICKOUT:{
//					if(timeCfg.getTurn() == turnTuple2.second){
//						String spKey = "YQZZ:SEASON_KICKOUT_END:" + season + ":" + GsConfig.getInstance().getServerId();
//						String val = ActivityGlobalRedis.getInstance().getRedisSession().getString(spKey);
//						if (StringUtils.isNotEmpty(val)){
//							return;
//						}
//						ActivityGlobalRedis.getInstance().getRedisSession().setString(spKey, spKey);
//						YQZZSeasonServer [] topServer = new YQZZSeasonServer[3];
//						Map<String, YQZZSeasonServer> serverMap = YQZZSeasonServer.loadAll(season);
//						for(YQZZSeasonServer seasonServer : serverMap.values()){
//
//							if(seasonServer.getKickoutRank() <=0 || seasonServer.getKickoutRank()>topServer.length){
//								continue;
//							}
//							topServer[seasonServer.getKickoutRank() - 1] = seasonServer;
//						}
//						List<Object> serverNames = new ArrayList<>();
//						for(YQZZSeasonServer seasonServer : topServer){
//							if(seasonServer == null){
//								continue;
//							}
//							ServerInfo serverInfo = RedisProxy.getInstance().getServerInfo(seasonServer.getServerId());
//							serverNames.add(seasonServer.getServerId());
//							serverNames.add(serverInfo== null ? "" : serverInfo.getName());
//						}
//						long currTime = HawkTime.getMillisecond();
//						long experiTime = currTime + HawkTime.DAY_MILLI_SECONDS * 7;
//						MailParames.Builder mailParames = MailParames.newBuilder();
//						mailParames.setMailId(MailConst.MailId.YQZZ_LEAGUE_TOP_SHOW);
//						for(YQZZSeasonServer seasonServer : topServer){
//							if(seasonServer == null){
//								continue;
//							}
//							mailParames.addContents(seasonServer.getServerId());
//						}
//						SystemMailService.getInstance().addGlobalMail(mailParames.build(), currTime, currTime + experiTime);
//					}
//				}
//			}
//		}catch (Exception e){
//			HawkException.catchException(e);
//		}
//	}
//
//	private void sendSeasonAward(){
//		int termId = this.getDataManager().getStateData().getTermId();
//		YQZZTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(YQZZTimeCfg.class, termId);
//		int season = timeCfg.getSeason();
//		int type = timeCfg.getType();
//		int turn = timeCfg.getTurn();
//		if(season <= 0){
//			return;
//		}
//		if(type != YQZZWar.PBYQZZWarType.YQZZ_KICKOUT_VALUE){
//			return;
//		}
//		HawkTuple2<Integer, Integer> turnTuple2 = getSeasonMaxTurn(season);
//		if(turn != turnTuple2.second){
//			return;
//		}
//		String spKey = "YQZZ:SEASON_AWARD:" + season + ":" + GsConfig.getInstance().getServerId();
//		String val = ActivityGlobalRedis.getInstance().getRedisSession().getString(spKey);
//		if (StringUtils.isNotEmpty(val)){
//			return;
//		}
//		ActivityGlobalRedis.getInstance().getRedisSession().setString(spKey, spKey);
//		confirmSeasonGiftSender(season);
//		sendSeasonServerAward(season);
//		//sendSeasonGuildAward(season);
//		addSeasonGuildGradeExp(season);
//
//	}
//	
//	private void addSeasonGuildGradeExp(int season){
//		YQZZSeasonServer seasonServer = YQZZSeasonServer.loadByServerId(season, GsConfig.getInstance().getServerId());
//		if(seasonServer == null){
//			return;
//		}
//		int rank = 0;
//		if(seasonServer.getKickoutRank() > 0){
//			rank = seasonServer.getKickoutRank();
//		}else{
//			rank = seasonServer.getGroupRank();
//		}
//		if(rank <= 0){
//			return;
//		}
//		Set<String> guildIds = new HashSet<>();
//		ConfigIterator<YQZZTimeCfg> iterator = HawkConfigManager.getInstance().getConfigIterator(YQZZTimeCfg.class);
//		for (YQZZTimeCfg cfg : iterator){
//			if(cfg.getSeason() != season){
//				continue;
//			}
//			if(cfg.getType() != YQZZWar.PBYQZZWarType.YQZZ_KICKOUT_VALUE){
//				continue;
//			}
//			String serverId = GsConfig.getInstance().getServerId();
//			List<String> serverList = GlobalData.getInstance().getMergeServerList(serverId);
//			if(serverList == null || serverList.isEmpty()){
//				serverList = new ArrayList<>();
//				serverList.add(serverId);
//			}
//			Map<String,YQZZJoinServer> map = YQZZJoinServer.loadAll(cfg.getTermId(), serverList);
//			for(YQZZJoinServer joinServer : map.values()){
//				if(joinServer == null){
//					continue;
//				}
//				guildIds.addAll(joinServer.getJoinGuilds());
//			}
//		}
//		for(String guildId : guildIds){
//			if(GuildService.getInstance().getGuildInfoObject(guildId) == null){
//				continue;
//			}
//			try {
//				Optional<SeasonActivity> opActivity = ActivityManager.getInstance().getGameActivityByType(ActivityType.SEASON_ACTIVITY.intValue());
//				if (opActivity.isPresent()) {
//					SeasonActivity activity = opActivity.get();
//					activity.addGuildGradeExpFromMatchRank(Activity.SeasonMatchType.S_YQZZ, guildId, rank);
//				}
//			}catch (Exception e){
//				HawkException.catchException(e);
//			}
//		}
//	}
//
//	private void sendSeasonServerAward(int season){
//		YQZZSeasonServer seasonServer = YQZZSeasonServer.loadByServerId(season, GsConfig.getInstance().getServerId());
//		if(seasonServer == null){
//			return;
//		}
//		if(seasonServer.isSeasonAward()){
//			return;
//		}
//		int rank = 0;
//		if(seasonServer.getKickoutRank() > 0){
//			rank = seasonServer.getKickoutRank();
//		}else{
//			rank = seasonServer.getGroupRank();
//		}
//		if(rank <= 0){
//			return;
//		}
//		YQZZSeasonServerRankAwardCfg cfg = getSeasonServerRankAwardCfg(rank);
//		if(cfg == null){
//			return;
//		}
//		long currTime = HawkTime.getMillisecond();
//		long experiTime = currTime + HawkTime.DAY_MILLI_SECONDS * 7;
//		String serverId = seasonServer.getServerId();
//		SystemMailService.getInstance().addGlobalMail(MailParames.newBuilder()
//				.setMailId(MailConst.MailId.YQZZ_LEAGUE_SERVER_RANK)
//				.addContents(rank, seasonServer.getLeaderName())
//				.setRewards(ItemInfo.valueListOf(cfg.getAward()))
//				.setAwardStatus(Const.MailRewardStatus.NOT_GET)
//				.build(), currTime, currTime + experiTime);
//		HawkLog.logPrintln("yqzz send season rank country reward, serverId: {}, rank: {}, cfgId: {}",
//				seasonServer, rank, cfg.getId());
//		seasonServer.setSeasonAward(true);
//		seasonServer.saveRedis();
//		YQZZSeasonTitle title = new YQZZSeasonTitle();
//		title.setServerId(GsConfig.getInstance().getServerId());
//		title.setRank(rank);
//		title.setNationHonor(cfg.getNationHonor());
//		title.saveRedis();
//	}
//
//	private void sendSeasonGuildAward(int season){
//		List<YQZZSeasonGuild> guilds = YQZZSeasonGuild.getRankList(season);
//		int rank = 0;
//		for(YQZZSeasonGuild guild : guilds){
//			rank++;
//			String guildId = guild.getGuildId();
//			if(GuildService.getInstance().getGuildInfoObject(guildId) == null){
//				continue;
//			}
//			YQZZSeasonGuildRankAwardCfg cfg = getSeasonGuildRankAwardCfg(rank);
//			if(cfg != null && !guild.isSeasonAward()){
//				AwardItems award = AwardItems.valueOf();
//				award.addItemInfos(ItemInfo.valueListOf(cfg.getAward()));
//				MailParames.Builder paramesBuilder = MailParames.newBuilder()
//						.setMailId(MailConst.MailId.YQZZ_LEAGUE_GUILD_RANK)
//						.addContents(guild.getKickoutPoint(), rank)
//						.setRewards(award.getAwardItems())
//						.setAwardStatus(Const.MailRewardStatus.NOT_GET);
//				GuildMailService.getInstance().sendGuildMail(guildId, paramesBuilder);
//				HawkLog.logPrintln("yqzz send season rank guild reward, guildId:{}, rank: {}, cfgId: {}",
//						guildId, rank, cfg.getId());
//				guild.setSeasonAward(true);
//				guild.saveRedis();
//			}
//		}
//	}
//
//
//
//	private YQZZSeasonServerRankAwardCfg getSeasonServerRankAwardCfg(int rank){
//		ConfigIterator<YQZZSeasonServerRankAwardCfg> iterator = HawkConfigManager.getInstance().getConfigIterator(YQZZSeasonServerRankAwardCfg.class);
//		for (YQZZSeasonServerRankAwardCfg cfg : iterator){
//			if(rank >= cfg.getMin() && rank <= cfg.getMax()){
//				return cfg;
//			}
//		}
//		return null;
//	}
//
//	private YQZZSeasonGuildRankAwardCfg getSeasonGuildRankAwardCfg(int rank){
//		ConfigIterator<YQZZSeasonGuildRankAwardCfg> iterator = HawkConfigManager.getInstance().getConfigIterator(YQZZSeasonGuildRankAwardCfg.class);
//		for (YQZZSeasonGuildRankAwardCfg cfg : iterator){
//			if(rank >= cfg.getMin() && rank <= cfg.getMax()){
//				return cfg;
//			}
//		}
//		return null;
//	}
//
//	private void confirmSeasonGiftSender(int season){
//		YQZZSeasonServer seasonServer = YQZZSeasonServer.loadByServerId(season, GsConfig.getInstance().getServerId());
//		if(seasonServer == null){
//			return;
//		}
//		fillSendGiftPlayerInfo(seasonServer);
//		seasonServer.saveRedis();
//	}
//
//	private String getSendGiftPlayerId(){
//		// 司令
//		President president = PresidentFightService.getInstance().getPresidentCity().getPresident();
//		if(president == null || HawkOSOperator.isEmptyString(president.getPlayerId())){
//			List<Rank.RankInfo> rankList = RankService.getInstance().getRankCache(Rank.RankType.ALLIANCE_FIGHT_KEY);
//			if(rankList.isEmpty()){
//				return "";
//			}
//			Rank.RankInfo rankInfo = rankList.get(0);
//			String guildId = rankInfo.getId();
//			GuildInfoObject obj = GuildService.getInstance().getGuildInfoObject(guildId);
//			if(obj == null){
//				return "";
//			}
//			return obj.getLeaderId() == null ? "" : obj.getLeaderId();
//		}
//		return president.getPlayerId();
//	}
//
//	private void fillSendGiftPlayerInfo(YQZZSeasonServer server){
//		// 司令
//		String leaderId = "";
//		String leaderName = "";
//		President president = PresidentFightService.getInstance().getPresidentCity().getPresident();
//		if(president == null || HawkOSOperator.isEmptyString(president.getPlayerId())){
//			List<Rank.RankInfo> rankList = RankService.getInstance().getRankCache(Rank.RankType.ALLIANCE_FIGHT_KEY);
//			if(!rankList.isEmpty()){
//				Rank.RankInfo rankInfo = rankList.get(0);
//				String guildId = rankInfo.getId();
//				GuildInfoObject obj = GuildService.getInstance().getGuildInfoObject(guildId);
//				if(obj != null){
//					leaderId = obj.getLeaderId() == null ? "" : obj.getLeaderId();
//					leaderName = obj.getLeaderName() == null ? "" : obj.getLeaderName();
//				}
//			}
//		}else {
//			leaderId = president.getPlayerId() == null ? "" : president.getPlayerId();
//			leaderName = president.getPlayerName() == null ? "" : president.getPlayerName();
//		}
//		server.setSenderId(leaderId);
//		server.setLeaderName(leaderName);
//	}
}
