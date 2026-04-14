package com.hawk.game.player.hero.rise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hawk.config.HawkConfigManager;
import org.hawk.tuple.HawkTuple2;
import org.hawk.tuple.HawkTuple3;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.hawk.game.config.HeroRiseFragmentsCfg;
import com.hawk.game.config.HeroRiseSkillCfg;
import com.hawk.game.player.hero.PlayerHero;
import com.hawk.game.player.hero.SerializJsonStrAble;
import com.hawk.game.protocol.Const.EffType;
import com.hawk.game.protocol.Hero.PBHeroEffect;
import com.hawk.game.protocol.Hero.PBHeroRise;

public class HeroRise implements SerializJsonStrAble {
	private final PlayerHero parent;
	private int riseSkillLevel;
	private Map<Integer, HeroRiseNode> nodeMap = new HashMap<>();
	private HeroRiseSkillCfg skillCfg;
	/** 做用号 */
	private ImmutableMap<EffType, Integer> nodeEffValMap = ImmutableMap.of();
	private int power;
//	private boolean effected; // 是否激活
	
	private Set<Integer> effectedSet = new HashSet<>();

	private HeroRise(PlayerHero school) {
		this.parent = school;
	}

	public int getPower() {
		return power;
	}

	public List<PBHeroEffect> effectVal() {
		List<PBHeroEffect> result = new ArrayList<>();
		for (HeroRiseNode node : nodeMap.values()) {
			for (Entry<EffType, Integer> eff : node.getEffValMap().entrySet()) {
				result.add(PBHeroEffect.newBuilder().setEffectId(eff.getKey().getNumber()).setValue(eff.getValue()).build());
			}
		}

		return result;
	}

	public void update() {
		int battlePoint = 0;
		int level = 0;
		Map<EffType, Integer> effmap = new HashMap<>();
		for (HeroRiseNode node : nodeMap.values()) {
			if (node.isMax()) {
				level += 1;
			}
			node.loadEffVal();
			battlePoint += node.getPower();
			for (Entry<EffType, Integer> ent : node.getEffValMap().entrySet()) {
				effmap.merge(ent.getKey(), ent.getValue(), (v1, v2) -> v1 + v2);
			}
		}
		nodeEffValMap = ImmutableMap.copyOf(effmap);
		riseSkillLevel = level;
		skillCfg = HawkConfigManager.getInstance().getCombineConfig(HeroRiseSkillCfg.class, getParent().getCfgId(), riseSkillLevel);
		if (skillCfg != null) {
			battlePoint += skillCfg.getPower();
		}
		this.power = battlePoint;
	}

	public int getNodeEffval(EffType effType) {
		return nodeEffValMap.getOrDefault(effType, 0);
	}

	public int getSkillEffval(EffType effType, int lmLevel) {
		if (skillCfg == null) {
			return 0;
		}
		HawkTuple2<Integer, Integer> effval = skillCfg.getEffval(effType);
		return effval.first * lmLevel + effval.second;
	}

	public Set<RisePageType> getType() {
		if (skillCfg == null) {
			return Collections.emptySet();
		}
		return skillCfg.getPageTypes();
	}

	public static HeroRise create(PlayerHero playerHero) {
		HeroRise rise = new HeroRise(playerHero);
		rise.mergeFrom(playerHero.getHeroEntity().getRiseSerialized());
		return rise;
	}

	public PBHeroRise toPBObj() {
		PBHeroRise.Builder builder = PBHeroRise.newBuilder();
		
		builder.addAllEffectTypes(effectedSet);
		for (HeroRiseNode node : nodeMap.values()) {
			builder.addNodes(node.toPBobj());
		}
		HeroRiseSkillCfg skillCfg = getSkillCfg();
		if (skillCfg != null) {
			builder.setRiseSkillId(skillCfg.getId());
		}
		return builder.build();
	}

	public int getSkillCfgId() {
		if (skillCfg != null) {
			return skillCfg.getId();
		}
		return 0;
	}

	public HeroRiseSkillCfg getSkillCfg() {
		return skillCfg;
	}

	public PlayerHero getParent() {
		return parent;
	}

	@Override
	public String serializ() {
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		for (HeroRiseNode node : nodeMap.values()) {
			arr.add(node.serializ());
		}
		JSONArray efrr = new JSONArray();
		for(int pt : effectedSet){
			efrr.add(pt);
		}
		
		obj.put("nodes", arr);
		obj.put("efrr", efrr);
		obj.put("skillId", getSkillCfgId());
		return obj.toJSONString();
	}

	@Override
	public void mergeFrom(String serialiedStr) {
		if (StringUtils.isEmpty(serialiedStr)) {
			return;
		}
		JSONObject obj = JSONObject.parseObject(serialiedStr);

		
		JSONArray arr = obj.getJSONArray("nodes");
		nodeMap.clear();
		if (arr != null) {
			for (Object str : arr) {
				HeroRiseNode node = new HeroRiseNode(this);
				node.mergeFrom(str.toString());
				nodeMap.put(node.getCfgId(), node);
			}
		}
		
		JSONArray efrr = obj.getJSONArray("efrr");
		effectedSet.clear();
		if (efrr != null) {
			for (Object str : efrr) {
				effectedSet.add(NumberUtils.toInt(str.toString()));
			}
		}
		
		
		update();
	}

	public void nodeUnlock(int nodeId) {
		HeroRiseNode node = getNodeById(nodeId);
		if (node == null) {
			node = new HeroRiseNode(this);
		}
		if (node.isUnlock()) {
			throw new RuntimeException("Rise nodeUnlock fail allready unlocked! ");
		}
		node.setCfgId(nodeId);
		node.setUnlock(true);
		nodeMap.put(nodeId, node);
	}

	public HeroRiseNode getNodeById(int nodeId) {
		return nodeMap.get(nodeId);
	}

	public boolean isEffected(int type) {
		return effectedSet.contains(type);
	}

	

	public HawkTuple3<Integer, Integer, Integer> getNodeAttr(int type) {
		int atkVal = 0, hpVal = 0, shdVal = 0;
		for (HeroRiseNode node : nodeMap.values()) {
			for(HeroRiseFragment chip : node.getChips()) {
				HeroRiseFragmentsCfg cfg = chip.getCfg();
				atkVal += cfg.getAtkAttr(type);
				hpVal += cfg.getHpAttr(type);
				shdVal += cfg.getShdAttr(type);
			}
		}
		return new HawkTuple3<Integer, Integer, Integer>(atkVal, hpVal, shdVal);
	}

	public int getRiseSkillLevel() {
		return riseSkillLevel;
	}

	public void setRiseSkillLevel(int riseSkillLevel) {
		this.riseSkillLevel = riseSkillLevel;
	}

	public Map<Integer, HeroRiseNode> getNodeMap() {
		return nodeMap;
	}

	public void setNodeMap(Map<Integer, HeroRiseNode> nodeMap) {
		this.nodeMap = nodeMap;
	}

	public ImmutableMap<EffType, Integer> getNodeEffValMap() {
		return nodeEffValMap;
	}

	public void setNodeEffValMap(ImmutableMap<EffType, Integer> nodeEffValMap) {
		this.nodeEffValMap = nodeEffValMap;
	}

	public Set<Integer> getEffectedSet() {
		return effectedSet;
	}

	public void setEffectedSet(Set<Integer> effectedSet) {
		this.effectedSet = effectedSet;
	}

	public void setSkillCfg(HeroRiseSkillCfg skillCfg) {
		this.skillCfg = skillCfg;
	}

	public void setPower(int power) {
		this.power = power;
	}
	
}
