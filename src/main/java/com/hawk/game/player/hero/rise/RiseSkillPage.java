package com.hawk.game.player.hero.rise;

import com.alibaba.fastjson.JSONObject;
import com.hawk.game.player.hero.SerializJsonStrAble;
import com.hawk.game.protocol.Const.SoldierType;
import com.hawk.game.protocol.Hero.PBHeroRisePage;

/**
 * 生效设置星穷技
 */
public class RiseSkillPage implements SerializJsonStrAble {
	private RisePageType type;
	private int leftHero;
	private int rightHero;
	private SoldierType soldierType;
	private int lmLevel;

	@Override
	public String serializ() {
		JSONObject obj = new JSONObject();
		obj.put("t", type.getIntVal());
		obj.put("l", leftHero);
		obj.put("r", rightHero);
		obj.put("s", soldierType.getNumber());
		obj.put("lm", lmLevel);
		return obj.toJSONString();
	}

	@Override
	public void mergeFrom(String serialiedStr) {
		JSONObject obj = JSONObject.parseObject(serialiedStr);
		this.type = RisePageType.valueOf(obj.getIntValue("t"));
		this.leftHero = obj.getIntValue("l");
		this.rightHero = obj.getIntValue("r");
		this.soldierType = SoldierType.valueOf(obj.getIntValue("s"));
		this.lmLevel = obj.getIntValue("lm");
	}

	public RisePageType getType() {
		return type;
	}

	public void setType(RisePageType type) {
		this.type = type;
	}

	public int getLeftHero() {
		return leftHero;
	}

	public void setLeftHero(int leftHero) {
		this.leftHero = leftHero;
	}

	public int getRightHero() {
		return rightHero;
	}

	public void setRightHero(int rightHero) {
		this.rightHero = rightHero;
	}

	public PBHeroRisePage toPBObj() {
		PBHeroRisePage.Builder builder = PBHeroRisePage.newBuilder();
		builder.setType(type.getIntVal());
		builder.setLeftHero(leftHero);
		builder.setRightHero(rightHero);
		builder.setSoldierType(soldierType.getNumber());
		builder.setLmLevel(lmLevel);
		return builder.build();
	}

	public SoldierType getSoldierType() {
		return soldierType;
	}

	public void setSoldierType(SoldierType soldierType) {
		this.soldierType = soldierType;
	}

	public int getLmLevel() {
		return lmLevel;
	}

	public void setLmLevel(int lmLevel) {
		this.lmLevel = lmLevel;
	}

}
