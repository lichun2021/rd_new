package com.hawk.game.config;

import java.util.TreeMap;

import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;
import org.hawk.os.HawkException;

@HawkConfigManager.XmlResource(file = "xml/hero_rise_level.xml")
public class HeroRiseLevelCfg extends HawkConfigBase {
	@Id
	protected final int id;// ="14"
	protected final int level;// ="14"
	protected final int num;

	static TreeMap<Integer, Integer> levelmap = new TreeMap<>();

	public HeroRiseLevelCfg() {
		this.id = 0;
		this.level = 0;
		this.num = 0;
	}

	@Override
	protected boolean assemble() {

		levelmap.put(num, level);

		return true;
	}

	public static int num2Level(int num) {
		try {
			return levelmap.floorEntry(num).getValue();

		} catch (Exception e) {
			HawkException.catchException(e);
		}
		return 0;
	}

}
