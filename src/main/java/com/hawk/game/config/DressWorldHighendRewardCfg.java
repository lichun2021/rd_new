package com.hawk.game.config;

import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;

/**
 * 天奢装扮（成就）相关配置
 * 
 * @author lating
 *
 */
@HawkConfigManager.XmlResource(file = "xml/world_highend_dress_reward.xml")
public class DressWorldHighendRewardCfg extends HawkConfigBase {
	
	@Id
	protected final int id;
	/**
	 * 需要解锁天奢装扮的个数
	 */
	protected final int needValue;
	/**
	 * （成就）奖励
	 */
	protected final String rewards;
	
	public DressWorldHighendRewardCfg() {
		id = 0;
		needValue = 0;
		rewards = "";
	}

	public int getId() {
		return id;
	}

	public int getNeedValue() {
		return needValue;
	}

	public String getRewards() {
		return rewards;
	}

}
