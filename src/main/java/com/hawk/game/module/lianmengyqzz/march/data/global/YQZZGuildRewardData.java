package com.hawk.game.module.lianmengyqzz.march.data.global;

import java.util.HashMap;
import java.util.Map;

import org.hawk.os.HawkOSOperator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hawk.game.GsConfig;
import com.hawk.game.global.RedisProxy;
import com.hawk.game.global.StatisManager;
import com.hawk.game.module.lianmengyqzz.march.data.IYQZZData;
import com.hawk.game.module.lianmengyqzz.march.service.YQZZConst;

public class YQZZGuildRewardData  implements IYQZZData{

	private static final String redisKey = "YQZZ_ACTIVITY_GUILD_REWARD_DATA_20251211";
	
	private int termId;
	
	private String serverId;
	
	private String guildId;
	
	private int guildRank;

	private long guildRewardTime;
	
	private Map<String,Integer> memberRewards = new HashMap<>();
	
	
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
	

	public String getGuildId() {
		return guildId;
	}

	public void setGuildId(String guildId) {
		this.guildId = guildId;
	}

	
	public int getGuildRank() {
		return guildRank;
	}
	

	public void setGuildRank(int guildRank) {
		this.guildRank = guildRank;
	}
	
	public long getGuildRewardTime() {
		return guildRewardTime;
	}
	
	
	public void setGuildRewardTime(long guildRewardTime) {
		this.guildRewardTime = guildRewardTime;
	}
	
	
	public Map<String, Integer> getMemberRewards() {
		return memberRewards;
	}
	
	
	
	
	


	@Override
	public String serializ() {
		JSONObject obj = new JSONObject();
		obj.put("termId", this.termId);
		obj.put("serverId", this.serverId);
		obj.put("guildId", this.guildId);
		obj.put("guildRank", this.guildRank);
		obj.put("guildRewardTime", this.guildRewardTime);
		
		JSONArray arrMember = new JSONArray();
		if(this.memberRewards!= null && !this.memberRewards.isEmpty()){
			for(Map.Entry<String, Integer> entry : this.memberRewards.entrySet()){
				JSONObject entryObj = new JSONObject();
				entryObj.put("playerId", entry.getKey());
				entryObj.put("rank", entry.getValue());
				arrMember.add(entryObj.toJSONString());
			}
		}
		obj.put("memberRewards", arrMember.toJSONString());
		return obj.toString();
	}

	@Override
	public void mergeFrom(String serialiedStr) {
		JSONObject obj = JSONObject.parseObject(serialiedStr);
		this.termId = obj.getIntValue("termId");
		this.serverId = obj.getString("serverId");
		this.guildId = obj.getString("guildId");
		this.guildRank = obj.getIntValue("guildRank");
		this.guildRewardTime = obj.getLongValue("guildRewardTime");
				
		this.memberRewards = new HashMap<>();
		if(obj.containsKey("memberRewards")){
			String playerIdsStr = obj.getString("memberRewards");
			JSONArray arr = JSONArray.parseArray(playerIdsStr);
			for(int i=0;i<arr.size();i++){
				String str = arr.getString(i);
				JSONObject playerObj = JSONObject.parseObject(str);
				String playerId = playerObj.getString("playerId");
				int rank = playerObj.getIntValue("rank");
				this.memberRewards.put(playerId, rank);
			}
		}
	}

	public void saveRedis() {
		String key = redisKey  + ":" + this.termId;
		RedisProxy.getInstance().getRedisSession().hSet(key, this.guildId, this.serializ(),YQZZConst.REDIS_DATA_EXPIRE_TIME);	
		StatisManager.getInstance().incRedisKey(redisKey);
	}
	
	public static YQZZGuildRewardData loadData(int termId,String guildId){
		String key = redisKey  + ":" + termId;
		String str = RedisProxy.getInstance().getRedisSession().hGet(key, guildId, YQZZConst.REDIS_DATA_EXPIRE_TIME);
		YQZZGuildRewardData data = new YQZZGuildRewardData();
		if(!HawkOSOperator.isEmptyString(str)){
			data.mergeFrom(str);
			return data;
		}
		String serverId = GsConfig.getInstance().getServerId();
		data.setTermId(termId);
		data.setGuildId(guildId);
		data.setServerId(serverId);
		return data;
	}

	
}
