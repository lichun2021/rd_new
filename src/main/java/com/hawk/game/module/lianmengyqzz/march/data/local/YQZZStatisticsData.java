package com.hawk.game.module.lianmengyqzz.march.data.local;

import org.hawk.os.HawkOSOperator;

import com.alibaba.fastjson.JSONObject;
import com.hawk.game.global.LocalRedis;
import com.hawk.game.global.StatisManager;
import com.hawk.game.module.lianmengyqzz.march.data.IYQZZData;

/**
 * @author LENOVO
 *
 */
public class YQZZStatisticsData  implements IYQZZData{

	private static final String redisKey = "YQZZ_ACTIVITY_STATISTICS_DATA_20251211";
	
	private String guildId;
	
	private String serverId;
	
	private int maxRankTerm;
	
	private int maxRank;
	
	
	public boolean updateMaxRank(int rank,int termId){
		if(rank <= 0){
			return false;
		}
		if(this.maxRank > 0 && this.maxRank < rank){
			return false;
		}
		this.maxRank = rank;
		this.maxRankTerm = termId;
		return true;
	}
	
	public String getGuildId() {
		return guildId;
	}
	
	public void setGuildId(String guildId) {
		this.guildId = guildId;
	}
	
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	
	public String getServerId() {
		return serverId;
	}
	
	public int getMaxRankTerm() {
		return maxRankTerm;
	}
	
	public void setMaxRankTerm(int maxRankTerm) {
		this.maxRankTerm = maxRankTerm;
	}
	
	public void setMaxRank(int maxRank) {
		this.maxRank = maxRank;
	}
	
	public int getMaxRank() {
		return maxRank;
	}
	

	@Override
	public String serializ() {
		JSONObject obj = new JSONObject();
		obj.put("guildId", this.guildId);
		obj.put("serverId", this.serverId);
		obj.put("maxRankTerm", this.maxRankTerm);
		obj.put("maxRank", this.maxRank);
		return obj.toJSONString();
	}

	@Override
	public void mergeFrom(String serialiedStr) {
		if(HawkOSOperator.isEmptyString(serialiedStr)){
			this.guildId = null;
			this.serverId = null;
			this.maxRank = 0;
			this.maxRankTerm = 0;
			return;
		}
		JSONObject obj = JSONObject.parseObject(serialiedStr);
		this.guildId = obj.getString("guildId");
		this.serverId = obj.getString("serverId");
		this.maxRank = obj.getIntValue("maxRank");
		this.maxRankTerm = obj.getIntValue("maxRankTerm = obj");
	}

	
	
	
	@Override
	public void saveRedis() {
		String key = redisKey  + ":" + this.guildId;
		LocalRedis.getInstance().getRedisSession().setString(key, this.serializ());	
		StatisManager.getInstance().incRedisKey(redisKey);
	}
	
	
	public static YQZZStatisticsData loadData(String guildId){
		String key = redisKey  + ":" + guildId;
		String str = LocalRedis.getInstance().getRedisSession().getString(key);
		if(HawkOSOperator.isEmptyString(str)){
			return null;
		}
		YQZZStatisticsData data = new YQZZStatisticsData();
		data.mergeFrom(str);
		return data;
	}
	
}
