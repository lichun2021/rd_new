package com.hawk.game.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;
import org.hawk.log.HawkLog;
import com.hawk.serialize.string.SerializeHelper;

/**
 * 天奢装扮相关配置
 * 
 * @author lating
 *
 */
@HawkConfigManager.XmlResource(file = "xml/world_highend_dress.xml")
public class DressWorldHighendCfg extends HawkConfigBase {
	/**
	 * 天奢装扮id
	 */
	@Id
	protected final int id;
	/**
	 * 装扮表的id
	 */
	protected final int dressId;
	/**
	 * 装扮类型（1铭牌,2基地,3行军,4签名,5挂件,6护卫,7称号,8主题）
	 */
	protected final int dressType;
	/**
	 * 所属系列
	 */
	protected final int series;
	/**
	 * 天奢特权（被动技能，对个人生效）
	 */
	protected final String luxuryPassiveSkills;
	/**
	 * 天奢技能（主动技能，全服生效）
	 */
	protected final String luxuryActiveSkills;
	
	private Set<Integer> luxuryPassSkillSet;
	private Set<Integer> luxuryActSkillSet;
	private static Map<Integer, Integer> luxurySkillDressMap = new HashMap<>();
	
	public DressWorldHighendCfg() {
		id = 0;
		dressId = 0;
		dressType = 0;
		series = 0;
		luxuryPassiveSkills = "";
		luxuryActiveSkills = "";
	}

	@Override
	protected boolean assemble() {
		luxuryPassSkillSet = SerializeHelper.stringToSet(Integer.class, luxuryPassiveSkills, ",");
		luxuryActSkillSet = SerializeHelper.stringToSet(Integer.class, luxuryActiveSkills, ",");
		for (int skillId : luxuryPassSkillSet) {
			if (luxurySkillDressMap.containsKey(skillId)) {
				HawkLog.errPrintln("xml/world_highend_dress.xml contains repeated luxurySkill: {}", skillId);
				return false;
			}
			luxurySkillDressMap.put(skillId, id);
		}
		for (int skillId : luxuryActSkillSet) {
			if (luxurySkillDressMap.containsKey(skillId)) {
				HawkLog.errPrintln("xml/world_highend_dress.xml contains repeated luxurySkill: {}", skillId);
				return false;
			}
			luxurySkillDressMap.put(skillId, id);
		}
		return true;
	}
	
	@Override
	protected boolean checkValid() {
		for (int skillId : luxuryPassSkillSet) {
			SkillCfg skillCfg = HawkConfigManager.getInstance().getConfigByKey(SkillCfg.class, skillId);
			if (skillCfg == null) {
				return false;
			}
		}
		for (int skillId : luxuryActSkillSet) {
			SkillCfg skillCfg = HawkConfigManager.getInstance().getConfigByKey(SkillCfg.class, skillId);
			if (skillCfg == null) {
				return false;
			}
		}
		
		DressCfg dressCfg = HawkConfigManager.getInstance().getConfigByKey(DressCfg.class, dressId);
		if (dressCfg == null) {
			return false;
		}
		return super.checkValid();
	}
	
	public int getId() {
		return id;
	}

	public int getSeries() {
		return series;
	}
	
	public int getDressId() {
		return dressId;
	}

	public int getDressType() {
		return dressType;
	}
	
	public Set<Integer> getLuxuryActSkillSet() {
		return luxuryActSkillSet;
	}

	public Set<Integer> getLuxuryPassSkillSet() {
		return luxuryPassSkillSet;
	}
	
	public boolean isLuxuryActiveSkill(int skillId) {
		return luxuryActSkillSet.contains(skillId);
	}

	public static int getCfgIdBySkillId(int skillId) {
		return luxurySkillDressMap.getOrDefault(skillId, 0);
	}
	
	public static DressWorldHighendCfg getCfgBySkillId(int skillId) {
		int cfgId = getCfgIdBySkillId(skillId);
		return HawkConfigManager.getInstance().getConfigByKey(DressWorldHighendCfg.class, cfgId);
	}
	
}
