package com.hawk.game.config;

import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;

@HawkConfigManager.XmlResource(file = "xml/hero_rise_node.xml")
public class HeroRiseNodeCfg extends HawkConfigBase {
	// <data nodeId="10784" heroId="1078" unlock="30000_21063005_40" front="10783" />
	@Id
	protected final int nodeId;// ="14"
	protected final int heroId;// ="14"
	protected final int front;// ="14"
	protected final String unlock;//
	protected final int maxFragment;

	public HeroRiseNodeCfg() {
		this.nodeId = 0;
		this.heroId = 0;
		this.front = 0;
		unlock = "";
		maxFragment = 0;
	}

	public int getNodeId() {
		return nodeId;
	}

	public int getHeroId() {
		return heroId;
	}

	public int getFront() {
		return front;
	}

	public String getUnlock() {
		return unlock;
	}

	public int getMaxFragment() {
		return maxFragment;
	}

}
