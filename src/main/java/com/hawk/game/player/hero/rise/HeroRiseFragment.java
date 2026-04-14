package com.hawk.game.player.hero.rise;

import org.hawk.config.HawkConfigManager;

import com.hawk.game.config.HeroRiseFragmentsCfg;
import com.hawk.game.player.hero.SerializJsonStrAble;

public class HeroRiseFragment implements SerializJsonStrAble {
	private final HeroRiseNode parent;
	private int cfgId;

	public HeroRiseFragment(HeroRiseNode plantTech) {
		this.parent = plantTech;
	}

	@Override
	public String serializ() {
		return cfgId + "";
	}

	@Override
	public void mergeFrom(String serialiedStr) {
		cfgId = Integer.valueOf(serialiedStr);
	}

	public HeroRiseFragmentsCfg getCfg() {
		return HawkConfigManager.getInstance().getConfigByKey(HeroRiseFragmentsCfg.class, cfgId);
	}

	public HeroRiseNode getParent() {
		return parent;
	}

	public int getCfgId() {
		return cfgId;
	}

	public void setCfgId(int cfgId) {
		this.cfgId = cfgId;
	}

	public int getTechId() {
		return getCfg().getTechId();
	}

	public int getLevel() {
		return getCfg().getTechLevel();
	}

}
