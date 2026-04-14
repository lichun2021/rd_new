package com.hawk.game.dress.data;

import java.util.List;
import com.hawk.game.protocol.Dress.LuxurySkillPersonalData;
import com.hawk.serialize.string.DataArray;
import com.hawk.serialize.string.SplitEntity;

/**
 * 天奢技能个人数据
 * 
 * @author lating
 */
public class PlayerLuxuryInfo implements SplitEntity {
	/**
	 * 技能id
	 */
	private int skillId;
	/**
	 * 所属装扮id
	 */
	private int dressId;
	/**
	 * 使用技能时间
	 */
	private long castTime;
	/**
	 * 技能冷却开始时间
	 */
	private long coolStartTime;
	
	public PlayerLuxuryInfo() {
	}
	
	public PlayerLuxuryInfo(int skillId, int dressId, long castTime, long coolStartTime) {
		this.skillId = skillId;
		this.dressId = dressId;
		this.castTime = castTime;
		this.coolStartTime = coolStartTime;
	}
	
	@Override
	public SplitEntity newInstance() {
		return new PlayerLuxuryInfo();
	}

	@Override
	public void serializeData(List<Object> dataList) {
		dataList.add(skillId);
		dataList.add(dressId);
		dataList.add(castTime);
		dataList.add(coolStartTime);
	}

	@Override
	public void fullData(DataArray dataArray) {
		dataArray.setSize(4);
		skillId = dataArray.getInt();
		dressId = dataArray.getInt();
		castTime = dataArray.getLong();
		coolStartTime = dataArray.getLong();
	}

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}
	
	public int getDressId() {
		return dressId;
	}

	public long getCastTime() {
		return castTime;
	}

	public void setCastTime(long castTime) {
		this.castTime = castTime;
	}

	public long getCoolStartTime() {
		return coolStartTime;
	}

	public void setCoolStartTime(long coolStartTime) {
		this.coolStartTime = coolStartTime;
	}
	
	public LuxurySkillPersonalData.Builder toBuilder() {
		LuxurySkillPersonalData.Builder builder = LuxurySkillPersonalData.newBuilder();
		builder.setSkillId(skillId);
		builder.setCastTime(castTime);
		builder.setCoolStartTime(coolStartTime);
		return builder;
	}

}
