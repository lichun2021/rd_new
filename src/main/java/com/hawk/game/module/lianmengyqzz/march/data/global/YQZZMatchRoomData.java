package com.hawk.game.module.lianmengyqzz.march.data.global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hawk.os.HawkOSOperator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hawk.game.global.RedisProxy;
import com.hawk.game.global.StatisManager;
import com.hawk.game.module.lianmengyqzz.march.data.IYQZZData;
import com.hawk.game.module.lianmengyqzz.march.service.YQZZConst;

/**
 *  月球之战房间信息
 * @author che
 */
public class YQZZMatchRoomData implements IYQZZData{
	
	private static final String redisKey = "YQZZ_ACTIVITY_ROOM_DATA_20251211";

	private int termId;
	
	private String roomId;
	
	private String roomServerId;
	
	private List<String> guilds;

	private boolean isAdvance;
	
	

	
	public YQZZMatchRoomData() {
		this.guilds = new ArrayList<>();
	}
	
	
	public void addGuild(String guildId){
		if(this.guilds.contains(guildId)){
			return;
		}
		this.guilds.add(guildId);
	}
	
	
	
	
	
	public int getTermId() {
		return termId;
	}
	public void setTermId(int termId) {
		this.termId = termId;
	}
	
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
	public String getRoomServerId() {
		return roomServerId;
	}
	public void setRoomServerId(String roomServerId) {
		this.roomServerId = roomServerId;
	}
	
	public List<String> getGuilds() {
		return guilds;
	}
	
	public int getGuildCount(){
		return this.guilds.size();
	}

	public void setAdvance(boolean advance) {
		isAdvance = advance;
	}

	public boolean isAdvance() {
		return isAdvance;
	}

	@Override
	public String serializ() {
		JSONObject obj = new JSONObject();
		obj.put("termId", this.termId);
		obj.put("roomId", this.roomId);
		obj.put("roomServerId", this.roomServerId);
		JSONArray arr = new JSONArray();
		if(this.guilds!= null && !this.guilds.isEmpty()){
			for(String str : this.guilds){
				arr.add(str);
			}
		}
		obj.put("guilds", arr.toJSONString());
		obj.put("isAdvance",isAdvance);
		return obj.toJSONString();
	}

	@Override
	public void mergeFrom(String serialiedStr) {
		this.termId = 0;
		this.roomId =null;
		this.roomServerId = null;
		this.guilds = new ArrayList<>();
		if(HawkOSOperator.isEmptyString(serialiedStr)){
			return;
		}
		JSONObject obj = JSONObject.parseObject(serialiedStr);
		this.termId = obj.getIntValue("termId");
		this.roomId = obj.getString("roomId");
		this.roomServerId = obj.getString("roomServerId");
		String guildsStr = obj.getString("guilds");
		if(!HawkOSOperator.isEmptyString(guildsStr)){
			JSONArray arr = JSONArray.parseArray(guildsStr);
			for(int i=0;i< arr.size();i++){
				this.guilds.add(arr.getString(i));
			}
		}
		this.isAdvance = obj.getBooleanValue("isAdvance");
	}

	@Override
	public void saveRedis() {
		String key = redisKey  + ":" + termId;
		RedisProxy.getInstance().getRedisSession()
			.hSet(key, this.roomId,this.serializ(),YQZZConst.REDIS_DATA_EXPIRE_TIME);
		StatisManager.getInstance().incRedisKey(redisKey);
	}

	public static YQZZMatchRoomData  loadData(int termId,String roomId) {
		String key = redisKey  + ":" + termId;
		String dataStr = RedisProxy.getInstance().getRedisSession()
				.hGet(key, roomId,YQZZConst.REDIS_DATA_EXPIRE_TIME);
		StatisManager.getInstance().incRedisKey(redisKey);
		if(HawkOSOperator.isEmptyString(dataStr)){
			return null;
		}
		YQZZMatchRoomData data = new YQZZMatchRoomData();
		data.mergeFrom(dataStr);
		return data;
	}
	
	public static Map<String,YQZZMatchRoomData>  loadAllData(int termId) {
		String key = redisKey  + ":" + termId;
		Map<String,String> map = RedisProxy.getInstance().getRedisSession()
				.hGetAll(key,YQZZConst.REDIS_DATA_EXPIRE_TIME);
		StatisManager.getInstance().incRedisKey(redisKey);
		Map<String,YQZZMatchRoomData> rlt = new HashMap<>();
		if(map != null){
			for(Entry<String, String> entry : map.entrySet()){
				String valStr = entry.getValue();
				YQZZMatchRoomData data = new YQZZMatchRoomData();
				data.mergeFrom(valStr);
				rlt.put(data.getRoomId(), data);
			}
		}
		return rlt;
	}
	
	public static void saveAll(int termId, List<YQZZMatchRoomData> rooms){
		String key = redisKey  + ":" + termId;
		Map<String,String> dataStrMap = new HashMap<>();
		for(YQZZMatchRoomData room : rooms){
			dataStrMap.put(room.getRoomId(), room.serializ());
		}
		RedisProxy.getInstance().getRedisSession()
			.hmSet(key,dataStrMap,YQZZConst.REDIS_DATA_EXPIRE_TIME);
		
	}

}
