package com.hawk.game.module.lianmengyqzz.march.data.global;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hawk.os.HawkOSOperator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hawk.game.global.RedisProxy;
import com.hawk.game.global.StatisManager;
import com.hawk.game.module.lianmengyqzz.march.data.IYQZZData;
import com.hawk.game.module.lianmengyqzz.march.service.YQZZConst;

public class YQZZJoinServer implements IYQZZData{
	
	private static final String redisKey = "YQZZ_ACTIVITY_JOIN_SERVER_20251211";
	
	/** 期数*/
	private int termId;
	/** 服务器ID*/
	private String serverId;
	
	private int saveServerInfo;
	
	private long openMailSend;
	
	private long awardTime;
	
	private Map<String,String> joinGuildsMap = new HashMap<>();
	
	// 当前进程使用内存
	private long totalMem;
	// 当前进程可使用的最大内存
	private long usedMem;
	private double cpuUsage;
	private int openDayW;
	
	
	
	public int getGuildCount(){
		return this.joinGuildsMap.size();
	}
	
	
	public int getTermId() {
		return termId;
	}
	
	public void setTermId(int termId) {
		this.termId = termId;
	}
	
	
	public String getServerId() {
		return serverId;
	}
	
	
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	
	
	
	
	
	public long getAwardTime() {
		return awardTime;
	}
	
	public void setAwardTime(long awardTime) {
		this.awardTime = awardTime;
	}
	
	
	public long getOpenMailSend() {
		return openMailSend;
	}
	public void setOpenMailSend(long openMailSend) {
		this.openMailSend = openMailSend;
	}
	
	public int getSaveServerInfo() {
		return saveServerInfo;
	}
	public void setSaveServerInfo(int saveServerInfo) {
		this.saveServerInfo = saveServerInfo;
	}
	
	
	
	
	public Set<String> getJoinGuilds() {
		Set<String> set = new HashSet<>();
		set.addAll(joinGuildsMap.keySet());
		return set;
	}
	
	
	public void recordJoinGuild(Map<String,YQZZJoinGuild> joins){
		this.joinGuildsMap = new HashMap<>();
		for(YQZZJoinGuild guild: joins.values()){
			StringBuilder valbuilder = new StringBuilder();
			valbuilder.append(guild.getGuildRank()).append("_")
				.append(guild.getPower()).toString();
			this.joinGuildsMap.put(guild.getGuildId(), valbuilder.toString());
		}
		this.saveRedis();
	}
	
	


	public long getTotalMem() {
		return totalMem;
	}

	public void setTotalMem(long totalMem) {
		this.totalMem = totalMem;
	}

	public long getUsedMem() {
		return usedMem;
	}


	public void setUsedMem(long usedMem) {
		this.usedMem = usedMem;
	}

	public double getCpuUsage() {
		return cpuUsage;
	}

	public void setCpuUsage(double cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

	public int getOpenDayW() {
		return openDayW;
	}

	public void setOpenDayW(int openDayW) {
		this.openDayW = openDayW;
	}

	@Override
	public String serializ() {
		JSONObject obj = new JSONObject();
		obj.put("termId", this.termId);
		obj.put("serverId", this.serverId);
		obj.put("saveServerInfo", this.saveServerInfo);
		obj.put("openMailSend", this.openMailSend);
		obj.put("awardTime", this.awardTime);

		JSONArray joinGuildArr = new JSONArray();
		if(this.joinGuildsMap!= null && !this.joinGuildsMap.isEmpty()){
			for(Map.Entry<String, String> entry : this.joinGuildsMap.entrySet()){
				JSONObject entryObj = new JSONObject();
				entryObj.put("guildId", entry.getKey());
				entryObj.put("info", entry.getValue());
				joinGuildArr.add(entryObj.toJSONString());
			}
		}
		obj.put("joinGuildsMap", joinGuildArr.toJSONString());
		
		obj.put("usedMem", usedMem);
		obj.put("totalMem", totalMem);
		obj.put("cpuUsage", cpuUsage);
		obj.put("openDayW", openDayW);
		return obj.toJSONString();
	}


	@Override
	public void mergeFrom(String serialiedStr) {
		JSONObject obj = JSONObject.parseObject(serialiedStr);
		if(obj.containsKey("termId")){
			this.termId = obj.getIntValue("termId");
		}
		if(obj.containsKey("serverId")){
			this.serverId = obj.getString("serverId");
		}
		if(obj.containsKey("saveServerInfo")){
			this.saveServerInfo = obj.getIntValue("saveServerInfo");
		}
		if(obj.containsKey("openMailSend")){
			this.openMailSend = obj.getLongValue("openMailSend");
		}
		if(obj.containsKey("awardTime")){
			this.awardTime = obj.getLongValue("awardTime");
		}
		
		this.joinGuildsMap = new HashMap<>();
		if(obj.containsKey("joinGuildsMap")){
			String joinGuildStr = obj.getString("joinGuildsMap");
			JSONArray arr = JSONArray.parseArray(joinGuildStr);
			for(int i=0;i<arr.size();i++){
				String str = arr.getString(i);
				JSONObject guildObj = JSONObject.parseObject(str);
				String guildId = guildObj.getString("guildId");
				String info = guildObj.getString("info");
				this.joinGuildsMap.put(guildId, info);
			}
		}
		
		this.usedMem = obj.getLongValue("usedMem");
		this.totalMem = obj.getLongValue("totalMem");
		this.cpuUsage = obj.getDoubleValue("cpuUsage");
		this.openDayW = obj.getIntValue("openDayW");
	}


	@Override
	public void saveRedis() {
		String key = redisKey  + ":" + this.termId;
		RedisProxy.getInstance().getRedisSession().hSet(key, this.serverId, this.serializ(),YQZZConst.REDIS_DATA_EXPIRE_TIME);	
		StatisManager.getInstance().incRedisKey(redisKey);
	}

	public static YQZZJoinServer load(int termId, String serverId){
		String key = redisKey  + ":" + termId;
		YQZZJoinServer rlt = null;
		String value  = RedisProxy.getInstance().getRedisSession().hGet(key, serverId, YQZZConst.REDIS_DATA_EXPIRE_TIME);
		if(!HawkOSOperator.isEmptyString(value)){
			rlt = new YQZZJoinServer();
			rlt.mergeFrom(value);
		}
		return rlt;
	}

	public static Map<String,YQZZJoinServer> loadAll(int termId){
		String key = redisKey  + ":" + termId;
		Map<String,YQZZJoinServer> rlt = new HashMap<>();
		Map<String,String> map = RedisProxy.getInstance().getRedisSession().hGetAll(key, YQZZConst.REDIS_DATA_EXPIRE_TIME);
		for(Map.Entry<String, String> entry : map.entrySet()){
			String value = entry.getValue();
			YQZZJoinServer join = new YQZZJoinServer();
			join.mergeFrom(value);
			rlt.put(join.getServerId(), join);
		}
		return rlt;
	}
	
	public static Map<String,YQZZJoinServer> loadAll(int termId,List<String> servers){
		String key = redisKey  + ":" + termId;
		Map<String,YQZZJoinServer> rlt = new HashMap<>();
		if(servers == null || servers.isEmpty()){
			return rlt;
		}
		List<String> list = RedisProxy.getInstance().getRedisSession()
				.hmGet(key, servers.toArray(new String[servers.size()]));
		for(String str : list){
			YQZZJoinServer join = new YQZZJoinServer();
			join.mergeFrom(str);
			rlt.put(join.getServerId(), join);
		}
		return rlt;
	}
	
}
