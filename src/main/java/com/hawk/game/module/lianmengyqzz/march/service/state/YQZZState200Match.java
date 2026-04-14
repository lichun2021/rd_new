package com.hawk.game.module.lianmengyqzz.march.service.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.hawk.config.HawkConfigManager;
import org.hawk.log.HawkLog;
import org.hawk.os.HawkException;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkRand;
import org.hawk.os.HawkTime;
import org.hawk.uuid.HawkUUIDGenerator;

import com.hawk.game.GsConfig;
import com.hawk.game.entity.GuildInfoObject;
import com.hawk.game.module.lianmengyqzz.march.cfg.YQZZTimeCfg;
import com.hawk.game.module.lianmengyqzz.march.cfg.YQZZWarConstCfg;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZJoinGuild;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZJoinServer;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZMatchData;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZMatchLock;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZMatchRoomData;
import com.hawk.game.module.lianmengyqzz.march.service.YQZZConst.YQZZActivityJoinState;
import com.hawk.game.module.lianmengyqzz.march.service.YQZZConst.YQZZActivityState;
import com.hawk.game.module.lianmengyqzz.march.service.YQZZMatchService;
import com.hawk.game.protocol.MailConst.MailId;
import com.hawk.game.service.GuildService;
import com.hawk.game.service.mail.GuildMailService;
import com.hawk.game.service.mail.MailParames;
import com.hawk.game.util.LogUtil;
import com.hawk.serialize.string.SerializeHelper;

public class YQZZState200Match extends IYQZZServiceState {
	
	private long lastTickTime =0;
	
	public YQZZState200Match(YQZZMatchService parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		this.getDataManager().getStateData().setState(YQZZActivityState.MATCH);
		this.getDataManager().getStateData().saveRedis();
		HawkLog.logPrintln("YQZZState200Match-init:term:{}",this.getDataManager().getStateData().getTermId());
	}

	@Override
	public void tick() {
		//如果不参与战斗
		YQZZActivityJoinState joinState = this.getDataManager()
				.getStateData().getJoinGame();
		if(joinState == YQZZActivityJoinState.OUT){
			return;
		}
		//1分钟 检查一次匹配
		long curTime= HawkTime.getMillisecond();
		if(curTime > this.lastTickTime + HawkTime.MINUTE_MILLI_SECONDS){
			this.lastTickTime = curTime;
			//加载匹配数据
			YQZZMatchData matchData = this.getDataManager().loadYQZZMatchData();
			boolean matchFinish = matchData!= null && matchData.matchFinish();
			if(!matchFinish){
				YQZZMatchLock matchLock = this.getDataManager().createYQZZMatchLock(60 * 5);
				boolean achieve = matchLock.achieveMatchLockWithExpireTime();
				if(achieve){
					this.doNormalMatch();
				}
			}else{
				Map<String, YQZZMatchRoomData> roomDatas = this.getDataManager().getMatchRoomDatas();
				if(roomDatas == null || roomDatas.isEmpty()){
					//加载房间数据
					this.getDataManager().loadToCacheRoomData();
					//联盟参与数据
					this.getDataManager().loadToCacheYQZZJoinGuildDataForRoom();
					//发送邮件
					this.sendMatchMail();
				}
			}
		}
	}
	
	

	@Override
	public void gmOp() {
		for(int i=0;i<10;i++){
			YQZZMatchData matchData = this.getDataManager().loadYQZZMatchData();
			boolean matchFinish = matchData!= null && matchData.matchFinish();
			if(!matchFinish){
				YQZZMatchLock matchLock = this.getDataManager().createYQZZMatchLock(60 * 5);
				boolean achieve = matchLock.achieveMatchLockWithExpireTime();
				if(achieve){
					this.doNormalMatch();
				}
			}else{
				Map<String, YQZZMatchRoomData> roomDatas = this.getDataManager().getMatchRoomDatas();
				if(roomDatas == null || roomDatas.isEmpty()){
					//加载房间数据
					this.getDataManager().loadToCacheRoomData();
					//联盟参与数据
					this.getDataManager().loadToCacheYQZZJoinGuildDataForRoom();
					//发送邮件
					this.sendMatchMail();
				}
			}
		}
	}

	
	
	
	/**
	 * 发送邮件
	 */
	private void sendMatchMail(){
		Map<String, YQZZMatchRoomData> matchRooms = this.getDataManager().getMatchRoomDatas();
		if(matchRooms == null){
			return;
		}
		YQZZJoinServer serverData = this.getDataManager().getServerData();
		if(serverData.getOpenMailSend() > 0){
			return;
		}
		//记录发邮件
		long curTime = HawkTime.getMillisecond();
		serverData.setOpenMailSend(curTime);
		serverData.saveRedis();
		//开始发邮件
		Map<String, YQZZJoinGuild> JoinGuilds = this.getDataManager().getBattleGuilds();
		Map<String, String> guildMatchRooms = this.getDataManager().getGuildMatchRooms();
		for(Entry<String, String> gentry : guildMatchRooms.entrySet()){
			String gid = gentry.getKey();
			String rid = gentry.getValue();
			YQZZJoinGuild guild = JoinGuilds.get(gid);
			if(Objects.isNull(guild)){
				continue;
			}
			YQZZJoinGuild curGuild = JoinGuilds.get(gid);
			if(Objects.isNull(curGuild)){
				continue;
			}
			GuildInfoObject gobj = GuildService.getInstance().getGuildInfoObject(gid);
			if(Objects.isNull(gobj)){
				continue;
			}
			YQZZMatchRoomData roomData = matchRooms.get(rid);
			if(Objects.isNull(roomData)){
				continue;
			}
			MailParames.Builder mailParames = MailParames.newBuilder();
			mailParames.setMailId(MailId.YQZZ_ACTIVITY_MATCH_RLT);
			GuildMailService.getInstance().sendGuildMail(gid, mailParames);
		}
	}
	
	

	/**
	 * 匹配
	 */
	public void doNormalMatch(){
		int termId = this.getDataManager().getStateData().getTermId();
		YQZZTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(YQZZTimeCfg.class, termId);
		
		String matchServer = GsConfig.getInstance().getServerId();
		long curTime = HawkTime.getMillisecond();
		Map<String, YQZZJoinGuild> joinGuildMap = this.getDataManager().loadAllYQZZJoinGuildData();
        //匹配房间列表
        List<YQZZMatchRoomData> rooms = new ArrayList<>();
		//分池
        Map<Integer, List<YQZZJoinGuild>> guildDayMap = new HashMap<>();
        for (YQZZJoinGuild guildData : joinGuildMap.values()){
        	int openDayW = this.getOpenDayW(guildData.getOpenDays());
            if(!guildDayMap.containsKey(openDayW)){
            	guildDayMap.put(openDayW, new ArrayList<>());
            }
            guildDayMap.get(openDayW).add(guildData);
        }
        
        List<YQZZJoinGuild> noMatchList = new ArrayList<>();
        List<Integer> dayList = new ArrayList<>(guildDayMap.keySet());
        dayList.sort((o1, o2) -> o2 - o1);
        //匹配
        for(int day : dayList){
        	List<YQZZJoinGuild> matchGuildList = new ArrayList<>();
        	matchGuildList.addAll(noMatchList);
        	matchGuildList.addAll(guildDayMap.get(day));
            noMatchList = new ArrayList<>();
            //当前池匹配
            this.doPoolMatch(termId, rooms, matchGuildList, timeCfg.getMatchNeedCount(), 
            		timeCfg.getMatchListCount(), noMatchList);
          
        }
        //处理最后剩下的联盟
        this.dealNoMatchGuilds(termId, rooms,  timeCfg.getMatchNeedCount(), noMatchList);
		//选择战场服
        this.chooseRoomServer(rooms, joinGuildMap);
		//保存房间数据
		YQZZMatchRoomData.saveAll(termId, rooms);
		//保存匹配数据
		YQZZMatchData data = new YQZZMatchData(termId, matchServer, curTime);
		data.saveRedis();
		HawkLog.logPrintln("YQZZState200Match-doNormalMatch-term:{}",termId);
	}
	
	/**
	 * 处理剩余联盟
	 * @param termId
	 * @param rooms
	 * @param perCount
	 * @param nomatchs
	 */
	public void dealNoMatchGuilds(int termId, List<YQZZMatchRoomData> rooms,int perCount,List<YQZZJoinGuild> nomatchs){
		if(nomatchs.size() >= perCount){
			HawkLog.logPrintln("dealNoMatchGuilds-err-nomatchs oversize...");
			return;
		}
		if(nomatchs.isEmpty()){
			return;
		}
		YQZZMatchRoomData room = new YQZZMatchRoomData();
		room.setTermId(termId);
		room.setRoomId(HawkUUIDGenerator.genUUID());
		Set<String> guildServers = new HashSet<>();
		for(YQZZJoinGuild data : nomatchs){
			room.getGuilds().add(data.getGuildId());
			guildServers.add(data.getServerId());
		}
		
		//需要从X房间取过来1个联盟
		int addCount = perCount - 1 - nomatchs.size();
		int allRoomSize = rooms.size();
		//循环取
		for(int take = 0; take < addCount; take++){
			int takeIndex = allRoomSize -1 - take;
			if(takeIndex < 0){
				continue;
			}
			if(takeIndex >= allRoomSize){
				continue;
			}
			YQZZMatchRoomData takeRoom = rooms.get(takeIndex);
			String guildId = takeRoom.getGuilds().remove(0);
			room.addGuild(guildId);
		}
		rooms.add(room);
	}
	
	
	/**
	 * 匹配池里匹配房间
	 * @param termId
	 * @param rooms
	 * @param guildList
	 * @param perCount
	 * @param poolSize
	 * @param nomatchs
	 */
	public void doPoolMatch(int termId, List<YQZZMatchRoomData> rooms, List<YQZZJoinGuild> guildList,int perCount,int poolSize,List<YQZZJoinGuild> nomatchs){
		//排序
		Collections.sort(guildList,new Comparator<YQZZJoinGuild>() {
			@Override
			public int compare(YQZZJoinGuild o1, YQZZJoinGuild o2) {
				if(o1.getOpenDays() != o2.getOpenDays()){
					return o1.getOpenDays() > o2.getOpenDays()?-1 :1;
				}
				if(o1.getPower() != o2.getPower()){
					return o1.getPower() > o2.getPower()?-1 :1;
				}
				return o1.getGuildId().compareTo(o2.getGuildId());
			}
		});
		int guildCount = guildList.size();
		int roomCount = guildCount / perCount;
		//创建房间
		for(int creat = 1;creat<=roomCount;creat++){
			if(guildList.size() < perCount){
				break;
			}
			//创建一个房间，循环取出联盟
			YQZZMatchRoomData room = new YQZZMatchRoomData();
			room.setTermId(termId);
			room.setRoomId(HawkUUIDGenerator.genUUID());
			
			int maxIndex = Math.min(poolSize-1, guildList.size() -1);
			for(int choose =1; choose<=perCount; choose++){
				int ranIndex = HawkRand.randInt(0, maxIndex);
				YQZZJoinGuild data = guildList.remove(ranIndex);
				room.addGuild(data.getGuildId());
				maxIndex--;
			}
			rooms.add(room);
		}
		
		if(guildList.size() > 0){
			nomatchs.addAll(guildList);
		}
	}


	/**
	 * 选择战场服
	 * @param rooms
	 * @param guildMap
	 */
	public void chooseRoomServer(List<YQZZMatchRoomData> rooms,Map<String, YQZZJoinGuild> guildMap){
		//服务器开启战场数量
        Map<String, Integer> roomServerMap = new HashMap<>();
		for(YQZZMatchRoomData roomData : rooms){
			Set<String> servers = new HashSet<>();
			Map<String,String> serverGuild = new HashMap<>();
			for(String gid : roomData.getGuilds()){
				YQZZJoinGuild guild = guildMap.get(gid);
				if(Objects.nonNull(guild)){
					servers.add(guild.getServerId());
					serverGuild.put(guild.getServerId(), guild.getGuildId());
				}
			}
			//设置战场服
			String roomServerId = this.getMatchRoomServers(servers, roomServerMap);
			roomData.setRoomServerId(roomServerId);
			//把战场服的联盟 放在第一  方便OB
			String roomServerGuild = serverGuild.get(roomServerId);
			roomData.getGuilds().remove(roomServerGuild);
			roomData.getGuilds().add(0, roomServerGuild);
			//日志
			try {
				String joinGuildStr = SerializeHelper.mapToString(serverGuild);
				LogUtil.logYQZZMatch(roomData.getTermId(), roomData.getRoomId(), 0, joinGuildStr, roomServerGuild);
			}catch (Exception e){
				HawkException.catchException(e);
			}
		}
	}
	private String getMatchRoomServers(Set<String> servers,Map<String, Integer> roomServerMap){
		String targetServer = "";
		int targetCnt = 0;
		for(String serverId : servers){
			int curCnt = roomServerMap.getOrDefault(serverId, 0);
			if(HawkOSOperator.isEmptyString(targetServer)){
				targetServer = serverId;
				targetCnt = curCnt;
				continue;
			}
			if(curCnt < targetCnt){
				targetServer = serverId;
				targetCnt = curCnt;
			}
		}
		roomServerMap.put(targetServer, targetCnt +1);
		return targetServer;
	}


	
	
	/**
	 * 根据开服天数计算匹配池ID
	 * @param day
	 * @return
	 */
	public int getOpenDayW(int day){
		try {
			YQZZWarConstCfg cfg = HawkConfigManager.getInstance().getKVInstance(YQZZWarConstCfg.class);
			return cfg.serverMatchOpenDayWeight(day);
		} catch (Exception e) {
			HawkException.catchException(e);
		}
		return 0;
	}

}
