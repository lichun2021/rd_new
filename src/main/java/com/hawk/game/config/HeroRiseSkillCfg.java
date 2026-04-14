package com.hawk.game.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;
import org.hawk.os.HawkOSOperator;
import org.hawk.tuple.HawkTuple2;
import org.hawk.tuple.HawkTuples;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.hawk.game.player.hero.rise.RisePageType;
import com.hawk.game.protocol.Const.EffType;

@HawkConfigManager.XmlResource(file = "xml/hero_rise_skill.xml")
@HawkConfigBase.CombineId(fields = { "heroId", "level" })
public class HeroRiseSkillCfg extends HawkConfigBase {
	// <data id="10781" heroId="1078" level="1" techEffect="1926_12800|1934_25100|1942_10400" />
	protected final int id;// ="14"
	protected final int heroId;// ="14"
	protected final int level;// ="14"
	protected final int type;
	protected final int leftRight;
	protected final String techEffect;//
	protected final int power;
	private Map<EffType, HawkTuple2<Integer, Integer>> effectList;
	private Set<RisePageType> pageTypes;
	public HeroRiseSkillCfg() {
		this.id = 0;
		this.heroId = 0;
		this.level = 0;
		techEffect = "";
		type = 0;
		leftRight = 0;
		power = 0;
	}

	@Override
	protected boolean assemble() {
		effectList = new HashMap<>();
		if (!HawkOSOperator.isEmptyString(techEffect)) {
			List<String> array = Splitter.on("|").omitEmptyStrings().splitToList(techEffect);
			for (String val : array) {
				String[] info = val.split("_");
				effectList.put(EffType.valueOf(Integer.parseInt(info[0])), HawkTuples.tuple(Integer.parseInt(info[1]), Integer.parseInt(info[2])));
			}
		}
		effectList = ImmutableMap.copyOf(effectList);
		pageTypes = new HashSet<>();
		if (type == 0) {
			pageTypes.add(RisePageType.One);
			pageTypes.add(RisePageType.Two);
			pageTypes.add(RisePageType.Three);
			pageTypes.add(RisePageType.Four);
		} else {
			pageTypes.add(RisePageType.valueOf(type));
		}
		pageTypes = ImmutableSet.copyOf(pageTypes);
		return true;
	}

	public HawkTuple2<Integer, Integer> getEffval(EffType eff) {
		return effectList.getOrDefault(eff, HawkTuples.tuple(0,0));
	}
	
	public Map<EffType, HawkTuple2<Integer, Integer>> getEffectList() {
		return effectList;
	}

	public void setEffectList(Map<EffType, HawkTuple2<Integer, Integer>> effectList) {
		this.effectList = effectList;
	}

	public int getId() {
		return id;
	}

	public int getHeroId() {
		return heroId;
	}

	public int getLevel() {
		return level;
	}

	public String getTechEffect() {
		return techEffect;
	}

//	public int getType() {
//		return type;
//	}

	public int getLeftRight() {
		return leftRight;
	}

	public int getPower() {
		return power;
	}

	public Set<RisePageType> getPageTypes() {
		return pageTypes;
	}

}
