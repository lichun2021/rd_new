package com.hawk.game.config;

import org.apache.commons.lang.math.NumberUtils;
import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;

@HawkConfigManager.XmlResource(file = "xml/skill.xml")
public class SkillCfg extends HawkConfigBase {
	/**
	 * 天奢技能标识
	 */
	public static final int LUXURY_SKILL = 5;
	/**
	 * 天奢技能在自己开罩时享受延时效果
	 */
	public static final int LUXURY_PROTECT_SELF = 1;
	/**
	 * 天奢技能本服玩家开罩时享受延时效果
	 */
	public static final int LUXURY_PROTECT_SVR = 2;
	
	@Id
	protected final int id;// ="10104"
	protected final int type;// ="1"
	protected final int cd;// ="43200"
	protected final int mutexSkills;// ="10103"
	protected final int continueTime;// ="-1"
	protected final int uiType;// 1=指挥官技能；2=科技；3=英雄技能；4=龙；5=天奢技能
	protected final int heroLevel;// ="38" 英雄等级
	protected final int talentId;// ="10217"要求点出天赋
	protected final String effect;// "352_800" 英雄被动技能作用号
	protected final String param1;// ="5000000"（uiType=5时，param1=1给个人保护罩延时，param1=2给本服所有开罩的人延时）
	protected final String param2;// ="3"
	protected final String param3;// ="200000" />
	protected final int buffId;
	protected final int tblyUse;
	
	public SkillCfg() {
		id = 0;
		type = 0;
		cd = 0;
		mutexSkills = 0;
		continueTime = 0;
		uiType = 0;
		heroLevel = 0;
		talentId = 0;
		effect = "";
		param1 = "";
		param2 = "";
		param3 = "";
		buffId = 0;
		tblyUse = 0;
	}
	
	@Override
	protected boolean assemble() {
		if (uiType == LUXURY_SKILL && NumberUtils.toInt(param1) == SkillCfg.LUXURY_PROTECT_SELF && NumberUtils.toInt(param2) <= 0){
			return false;
		}
		if (uiType == LUXURY_SKILL && NumberUtils.toInt(param1) == SkillCfg.LUXURY_PROTECT_SVR){
			if (NumberUtils.toInt(param2) <= 0 || NumberUtils.toInt(param3) <= 0) {
				return false;
			}
		}
		return true;
	}

	public int getId() {
		return id;
	}

	public int getType() {
		return type;
	}

	public int getCd() {
		return cd;
	}

	public int getMutexSkills() {
		return mutexSkills;
	}

	public int getContinueTime() {
		return continueTime;
	}

	public int getUiType() {
		return uiType;
	}

	public String getEffect() {
		return effect;
	}

	public String getParam1() {
		return param1;
	}

	public String getParam2() {
		return param2;
	}

	public String getParam3() {
		return param3;
	}
	
	public boolean isLuxuryProtectSelfSkill() {
		return uiType == LUXURY_SKILL && NumberUtils.toInt(param1) == LUXURY_PROTECT_SELF;
	}
	
	public boolean isLuxuryProtectSvrSkill() {
		return uiType == LUXURY_SKILL && NumberUtils.toInt(param1) == LUXURY_PROTECT_SVR;
	}

	public int getHeroLevel() {
		return heroLevel;
	}

	public int getTalentId() {
		return talentId;
	}

	public int getBuffId() {
		return buffId;
	}

	public int getTblyUse() {
		return tblyUse;
	}
	
}
