package com.hawk.game.module.lianmengyqzz.march.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.hawk.log.HawkLog;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkTime;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.hawk.game.GsConfig;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZBattleData;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZGameData;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZJoinGuild;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZJoinPlayerData;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZJoinServer;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZMatchData;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZMatchLock;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZMatchRoomData;
import com.hawk.game.module.lianmengyqzz.march.data.local.YQZZActivityStateData;

public class YQZZDataManager {
	/** 活动状态数据(当前服)*/
	private YQZZActivityStateData stateData;
	/** 服务器数据(当前服)*/
	private YQZZJoinServer serverData;
	
	/** 房间匹配信息(全量)*/
	private Map<String,YQZZMatchRoomData> matchRoomDatas;
	/** 联盟战场映射(全量)*/
	private Map<String,String> guildMatchRooms;
	/**参与联盟信息(全量)*/
	private Map<String,YQZZJoinGuild> battleGuilds;
	
	/** 战场统计数据(战时数据)*/
	private Map<String,YQZZBattleData> battleDatas;
	private Map<String, Cache<String,Boolean>> joinExtarPlayers;
	
	/** 战绩记录(玩家请求查询操作缓存)*/
	private Map<String,YQZZBattleData> history;
	
	
	
	

	public void init(){
		//初始化活动状态数据
		this.loadToCacheYQZZActivityStateData();
		//初始话当当前服数据
		this.loadToCacheYQZZJoinServerData();
		//初始化匹配房间数据
		this.loadToCacheRoomData();
		//联盟参与数据
		this.loadToCacheYQZZJoinGuildDataForRoom();
		//初始化战场数据
		this.loadToCacheYQZZBattleData();
		//初始化缓存容器
		this.history = new ConcurrentHashMap<>();
		//受限玩家加入副本
		this.joinExtarPlayers = new ConcurrentHashMap<>();
	}
	
	
	/**
	 * 清除缓存
	 */
	public void clearData(){
		this.serverData = null;
		this.matchRoomDatas = null;
		this.guildMatchRooms = null;
		this.battleGuilds = null;
		this.battleDatas = null;
		this.joinExtarPlayers = new ConcurrentHashMap<>();
		HawkLog.logPrintln("YQZZDataManager-clearData");
	}
	
	
	
	/**
	 * 加载活动状态
	 * @return
	 */
	public YQZZActivityStateData loadToCacheYQZZActivityStateData(){
		String serverId = GsConfig.getInstance().getServerId();
		YQZZActivityStateData data = YQZZActivityStateData.loadData(serverId);
		if(data == null){
			data = new YQZZActivityStateData();
			data.setServerId(serverId);
			data.saveRedis();
		}
		this.stateData = data;
		return this.stateData;
	}

	
	
	/**
	 * 加载活动数据
	 * @return
	 */
	public YQZZJoinServer loadToCacheYQZZJoinServerData(){
		String serverId = GsConfig.getInstance().getServerId();
		YQZZJoinServer data = YQZZJoinServer.load(this.stateData.getTermId(), serverId);
		if(data == null){
			return null;
		}
		this.serverData = data;
		return this.serverData;
	}
	
	
	/**
	 * 初始化当前服活动数据
	 * @param termId
	 * @return
	 */
	public YQZZJoinServer initYQZZJoinServerData(int termId){
		String serverId = GsConfig.getInstance().getServerId();
		YQZZJoinServer serverData = new YQZZJoinServer();
		serverData.setTermId(termId);
		serverData.setServerId(serverId);
		serverData.saveRedis();
		this.serverData = serverData;
		return this.serverData;
	}
	
	
	/**
	 * 加载房间所以参数联盟
	 * @return
	 */
	public Map<String,YQZZJoinGuild> loadToCacheYQZZJoinGuildDataForRoom(){
		int termId = this.stateData.getTermId();
		if(this.matchRoomDatas!= null && !this.matchRoomDatas.isEmpty()){
			Map<String,YQZZJoinGuild> map = YQZZJoinGuild.loadAllData(termId);
			this.battleGuilds = map;
		}
		return this.battleGuilds;
	}
	
	
	/**
	 * 加载战场数据
	 * @return
	 */
	public Map<String,YQZZBattleData> loadToCacheYQZZBattleData(){
		if(this.serverData == null){
			return null;
		}
		if(this.matchRoomDatas == null){
			return null;
		}
		Set<String> roomIds = new HashSet<>();
		for(String guildId : this.serverData.getJoinGuilds()){
			String roomId = this.guildMatchRooms.get(guildId);
			if(HawkOSOperator.isEmptyString(roomId)){
				continue;
			}
			roomIds.add(roomId);
		}
		
		Map<String,YQZZBattleData> temp = new HashMap<>();
		for(String roomId : roomIds){
			YQZZBattleData data = YQZZBattleData.loadData(roomId);
			if(Objects.isNull(data)){
				continue;
			}
			temp.put(roomId, data);
		}
		this.battleDatas= temp;
		return this.battleDatas;
	}
	
	
	/**
	 * 加载战斗房间数据
	 * @return
	 */
	public Map<String,YQZZMatchRoomData> loadToCacheRoomData(){
		int termId = this.stateData.getTermId();
		Map<String,YQZZMatchRoomData> dataMap = YQZZMatchRoomData.loadAllData(termId);
		this.matchRoomDatas = dataMap;
		//联盟ID 战场映射
		Map<String,String> guildMatchRoomsTemp = new HashMap<>();
		for(YQZZMatchRoomData roomData: dataMap.values()){
			for(String gid : roomData.getGuilds()){
				guildMatchRoomsTemp.put(gid, roomData.getRoomId());
			}
		}
		this.guildMatchRooms = guildMatchRoomsTemp;
		return this.matchRoomDatas;
	}
	
	


	/**
	 * 创建匹配锁
	 * @param expireTime
	 * @return
	 */
	public YQZZMatchLock createYQZZMatchLock(int expireTime){
		int termId = this.stateData.getTermId();
		String serverId = GsConfig.getInstance().getServerId();
		YQZZMatchLock lock = new YQZZMatchLock(termId, serverId, expireTime);
		return lock;
	}

	
	
	/**
	 * 加载匹配数据
	 * @return
	 */
	public YQZZMatchData loadYQZZMatchData(){
		int termId = this.stateData.getTermId();
		YQZZMatchData data = YQZZMatchData.loadData(termId);
		return data;
	}
	
	
	
	/**
	 * 加载玩家数据
	 * @param termId
	 * @param playerId
	 * @return
	 */
	public YQZZJoinPlayerData loadYQZZJoinPlayerData(int termId,String playerId){
		YQZZJoinPlayerData data = YQZZJoinPlayerData.loadData(termId, playerId);
		return data;
	}
	
	/**
	 * 加载战场状态数据
	 * @param termId
	 * @param roomId
	 * @return
	 */
	public YQZZGameData loadYQZZGameData(int termId,String roomId){
		YQZZGameData data = YQZZGameData.loadData(termId, roomId);
		return data;
	}
	
	
	/**
	 * 更新战场活跃时间
	 * @param roomId
	 */
	public void updateYQZZGameDataActiveTime(String roomId){
		int termId = this.stateData.getTermId();
		YQZZGameData data = YQZZGameData.loadData(termId, roomId);
		if(data != null){
			data.setLastActiveTime(HawkTime.getMillisecond());
			data.saveRedis();
		}
	}
	
	
	/**
	 * 更新战场结束时间	
	 * @param termId
	 * @param roomId
	 */
	public void updateYQZZGameDataFinishTime(int termId,String roomId){
		YQZZGameData data = YQZZGameData.loadData(termId, roomId);
		if(data != null){
			data.setFinishTime(HawkTime.getMillisecond());
			data.saveRedis();
		}
	}
	
	
	/**
	 * 获取历史战场数据
	 * @param roomId
	 * @return
	 */
	public YQZZBattleData getHistoyrYQZZBattleData(String roomId){
		if(this.history.size() > 100){
			this.history = new HashMap<>();
		}
		YQZZBattleData data = this.history.get(roomId);
		if(data != null){
			return data;
		}
		YQZZBattleData battleData = YQZZBattleData.loadDataWithoutSecondMap(roomId);
		this.history.put(roomId, battleData);
		return battleData;
	}
	
	
	
	/**
	 * 进入副本记录
	 * @param guildId
	 * @param playerId
	 * @param limitSize
	 * @return
	 */
	public synchronized boolean addJoinExtraPlayer(String guildId,String playerId, int limitSize) {
		Cache<String,Boolean> cache = this.joinExtarPlayers.get(guildId);
		if(Objects.isNull(cache)){
			cache = CacheBuilder.newBuilder().expireAfterWrite(30L, TimeUnit.MINUTES).build();
			this.joinExtarPlayers.putIfAbsent(guildId, cache);
		}
		cache = this.joinExtarPlayers.get(guildId);
		if (cache.getIfPresent(playerId) != null) {
			return true;
		}
		long size = this.joinExtarPlayers.size();
		if (size >= limitSize) {
			return false;
		}
		cache.put(playerId, Boolean.TRUE);
		return true;
	}
	
	public void removeJoinExtraPlayer(String playerId){
//		this.joinExtarPlayers.remove(playerId); 进入的玩家在过期时间内,仍占有位置,可以随意进入, 不删除
	}

	
	
	/**
	 * 获取全部参与服数据
	 * @return
	 */
	public Map<String,YQZZJoinGuild> loadAllYQZZJoinGuildData(){
		int termId = this.stateData.getTermId();
		return YQZZJoinGuild.loadAllData(termId);
	}
	
	
	/**
	 * 获取的某联盟的战场数据
	 * @param guildId
	 * @return
	 */
	public YQZZBattleData getGuildYQZZBattleData(String guildId){
		if(Objects.isNull(this.guildMatchRooms)){
			return null;
		}
		if(Objects.isNull(this.battleDatas)){
			return null;
		}
		String roomId = this.guildMatchRooms.get(guildId);
		if(HawkOSOperator.isEmptyString(roomId)){
			return null;
		}
		return this.battleDatas.get(roomId);
	}
	
	
	/**
	 * 获取联盟对应战场数据
	 * @param guildId
	 * @return
	 */
	public YQZZMatchRoomData getGuildYQZZMatchRoomData(String guildId){
		if(HawkOSOperator.isEmptyString(guildId)){
			return null;
		}
		if(Objects.isNull(this.guildMatchRooms)){
			return null;
		}
		String roomId = this.guildMatchRooms.get(guildId);
		if(HawkOSOperator.isEmptyString(roomId)){
			return null;
		}
		return this.matchRoomDatas.get(roomId);
	}
	
	
	public YQZZActivityStateData getStateData() {
		return stateData;
	}
	
	public YQZZJoinServer getServerData() {
		return serverData;
	}
	
	public Map<String, YQZZMatchRoomData> getMatchRoomDatas() {
		return matchRoomDatas;
	}
	
	
	public Map<String, String> getGuildMatchRooms() {
		return guildMatchRooms;
	}
	
	
	public Map<String, YQZZJoinGuild> getBattleGuilds() {
		return battleGuilds;
	}
	
	public Map<String, YQZZBattleData> getBattleDatas() {
		return battleDatas;
	}
	
}
