package com.hawk.game.player.hero.rise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import org.hawk.config.HawkConfigManager;
import org.hawk.helper.HawkAssert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import com.hawk.game.config.HeroRiseFragmentsCfg;
import com.hawk.game.config.HeroRiseNodeCfg;
import com.hawk.game.module.plantsoldier.strengthen.soldierStrengthen.cfg.PlantSoldierStrengthenCfg;
import com.hawk.game.player.hero.SerializJsonStrAble;
import com.hawk.game.protocol.Const.EffType;
import com.hawk.game.protocol.Const.SoldierType;
import com.hawk.game.protocol.Hero.PBHeroRiseFragment;
import com.hawk.game.protocol.Hero.PBHeroRiseNode;

public class HeroRiseNode implements SerializJsonStrAble {
	private final HeroRise parent;

	private int cfgId;
	private boolean unlock;
	private List<HeroRiseFragment> chips = new CopyOnWriteArrayList<>();
	// ----------------------------------------------------------------------------------------//
	/** 做用号 */
	private ImmutableMap<EffType, Integer> effValMap = ImmutableMap.of();

	private int techPower;
	private boolean efvalLoad;

	public HeroRiseNode(HeroRise rise) {
		this.parent = rise;
	}

	/** 序列化 */
	@Override
	public String serializ() {
		JSONObject result = new JSONObject();
		result.put("cfgId", cfgId);
		result.put("unlock", unlock);
		JSONArray arr = new JSONArray();
		chips.stream().map(HeroRiseFragment::serializ).forEach(arr::add);

		result.put("chips", arr);
		return result.toJSONString();
	}

	@Override
	public void mergeFrom(String jsonstr) {

		JSONObject result = JSONObject.parseObject(jsonstr);
		this.cfgId = result.getIntValue("cfgId");
		this.unlock = result.getBooleanValue("unlock");
		List<HeroRiseFragment> list = new ArrayList<>();
		JSONArray arr = result.getJSONArray("chips");
		arr.forEach(str -> {
			HeroRiseFragment slot = new HeroRiseFragment(this);
			slot.mergeFrom(str.toString());
			list.add(slot);
		});

		this.chips = list;
	}

	public boolean isEfvalLoad() {
		return efvalLoad;
	}

	public void loadEffVal() {
		// 重新推送所有做用号
		Map<EffType, Integer> effmap = new HashMap<>();
		for (HeroRiseFragment chip : chips) {
			if(chip.getCfg() == null){
				continue;
			}
			for (Entry<EffType, Integer> ent : chip.getCfg().getEffectList().entrySet()) {
				effmap.merge(ent.getKey(), ent.getValue(), (v1, v2) -> v1 + v2);
			}
		}
		effValMap = ImmutableMap.copyOf(effmap);
		efvalLoad = true;
		this.techPower = power();
	}

	private int power() {
		int result = 0;
		for (HeroRiseFragment chip : chips) {
			result += chip.getCfg().getBattlePoint();
		}
		return result;
	}

	public PBHeroRiseNode toPBobj() {
		PBHeroRiseNode.Builder builder = PBHeroRiseNode.newBuilder();
		// builder.setUnlock(unlock);
		builder.setCfgId(cfgId);
		for (HeroRiseFragment chip : chips) {
			builder.addTechs(PBHeroRiseFragment.newBuilder().setCfgId(chip.getCfgId()));
		}
		return builder.build();
	}

	public HeroRiseNodeCfg getCfg() {
		HeroRiseNodeCfg config = HawkConfigManager.getInstance().getConfigByKey(HeroRiseNodeCfg.class, cfgId);
		HawkAssert.notNull(config, "HeroRiseNodeCfg null id:" + cfgId);
		return config;
	}

	public HeroRise getParent() {
		return parent;
	}

	public List<HeroRiseFragment> getChips() {
		return chips;
	}

	public int getCfgId() {
		return cfgId;
	}

	public void setCfgId(int cfgId) {
		this.cfgId = cfgId;
	}

	public int getPower() {
		return techPower;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("cfgId", getCfgId())
				.add("chipSerialized", serializ())
				.toString();
	}

	public boolean isUnlock() {
		return unlock;
	}

	public void setUnlock(boolean unlock) {
		this.unlock = unlock;
	}

	public HeroRiseFragment getTechByTechId(int techId) {
		return chips.stream().filter(tech -> tech.getTechId() == techId).findAny().orElse(null);
	}

	/**
	 * 检查科技研究条件
	 * @param upcfg
	 * @return
	 */
	public boolean checkCondition(HeroRiseFragmentsCfg upcfg) {

		HeroRiseFragment tech = getTechByTechId(upcfg.getTechId());
		// 科技已达到对应的等级
		if (Objects.nonNull(tech) && tech.getLevel() >= upcfg.getTechLevel()) {
			return false;
		}

		for (int condition : upcfg.getConditionTechList()) {
			HeroRiseFragmentsCfg cfg = HawkConfigManager.getInstance().getConfigByKey(HeroRiseFragmentsCfg.class, condition);
			if (cfg == null) {
				return false;
			}
			HeroRiseFragment entity = getTechByTechId(cfg.getTechId());
			boolean match = false;
			if (entity != null && entity.getLevel() >= cfg.getTechLevel()) {
				match = true;
			}
			if (!match) {
				return false;
			}
		}
		return true;
	}

	public void techUpGrade(HeroRiseFragmentsCfg techCfg) {
		HeroRiseFragment tech = getTechByTechId(techCfg.getTechId());
		if (Objects.isNull(tech)) {
			HeroRiseFragment slot = new HeroRiseFragment(this);
			slot.setCfgId(techCfg.getId());
			chips.add(slot);
		} else {
			tech.setCfgId(techCfg.getId());
		}

	}

	public boolean isMax() {
		int max = getCfg().getMaxFragment();
		for (HeroRiseFragment chip : chips) {
			if (chip.getCfgId() == max) {
				return true;
			}
		}
		return false;
	}

	public ImmutableMap<EffType, Integer> getEffValMap() {
		return effValMap;
	}

}
