package com.hawk.game.dress.data;

import java.util.StringJoiner;
import com.hawk.game.global.GlobalData;
import com.hawk.game.protocol.Dress.LuxurySkillCastData;

/**
 * 天奢技能全服数据
 * 
 * @author lating
 */
public class ServerLuxuryInfo {
	/**
	 * 技能id
	 */
	private int skillId;
	/**
	 * 装扮id
	 */
	private int dressId;
	/**
	 * 开始生效时间
	 */
	private long startTime;
	/**
	 * 生效结束时间
	 */
	private long endTime;
	/**
	 * 开启天奢技能的人
	 */
	private String playerId;
	

	public ServerLuxuryInfo() {
	}
	
	public ServerLuxuryInfo(int skillId, int dressId, long startTime, long endTime, String playerId) {
		this.skillId = skillId;
		this.dressId = dressId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.playerId = playerId;
	}

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	
	public int getDressId() {
		return dressId;
	}

	public void setDressId(int dressId) {
		this.dressId = dressId;
	}
	
	public String serialize() {
		StringJoiner sj = new StringJoiner("_");
		sj.add(String.valueOf(skillId));
		sj.add(String.valueOf(dressId));
		sj.add(String.valueOf(startTime));
		sj.add(String.valueOf(endTime));
		sj.add(playerId);
		return sj.toString();
	}
	
	public static ServerLuxuryInfo parseObj(String str) {
		String[] info = str.split("_");
		if (info.length < 5) {
			return null;
		}
		int skillId = Integer.parseInt(info[0]), dressId = Integer.parseInt(info[1]);
		long startTime = Long.parseLong(info[2]), endTime = Long.parseLong(info[3]);
		String playerId = info[4];
		return new ServerLuxuryInfo(skillId, dressId, startTime, endTime, playerId);
	}
	
	public LuxurySkillCastData.Builder toBuilder() {
		LuxurySkillCastData.Builder builder = LuxurySkillCastData.newBuilder();
		builder.setPlayerId(playerId);
		builder.setPlayerName(GlobalData.getInstance().getPlayerNameById(playerId));
		builder.setStartTime(startTime);
		builder.setEndTime(endTime);
		return builder;
	}

}
