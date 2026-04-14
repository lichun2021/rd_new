package com.hawk.game.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;
import org.hawk.os.HawkOSOperator;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.hawk.game.protocol.Const.EffType;
import com.hawk.serialize.string.SerializeHelper;

@HawkConfigManager.XmlResource(file = "xml/hero_rise_fragments.xml")
public class HeroRiseFragmentsCfg extends HawkConfigBase {
	// <data id="10781172" nodeId="10781" heroId="1078" techLevel="1" frontTech="10781162"
	// techCost="30000_21063005_1600,30000_21063003_800,30000_21063002_800" techEffect="1934_25100|1942_10400" battlePoint="48000" />
	@Id
	protected final int id;// ="14"
	protected final int nodeId;// ="14"
	protected final int techId;
	protected final int heroId;// ="14"
	protected final int techLevel;
	protected final String frontTech;//
	protected final int battlePoint;
	protected final String techCost;//
	protected final String techEffect;//
	
	/** 英雄星穹觉醒攻击加成 */
	protected final String atkAttr;
	/** 英雄星穹觉醒生命加成 */
	protected final String hpAttr;
	/** 英雄星穹觉醒星穹护盾值 */
	protected final String shdAttr;

	private Map<EffType, Integer> effectList;
	/**
	 * 前置解锁科技列表
	 */
	private List<Integer> conditionTechList;
	
	private Map<Integer, Integer> atkAttrMap = new HashMap<>();
	private Map<Integer, Integer> hpAttrMap = new HashMap<>();
	private Map<Integer, Integer> shdAttrMap = new HashMap<>();
	
	public HeroRiseFragmentsCfg() {
		this.id = 0;
		techId = 0;
		this.nodeId = 0;
		this.heroId = 0;
		this.techLevel = 0;
		frontTech = "";
		techCost = "";
		techEffect = "";
		battlePoint = 0;
		atkAttr = "";
		hpAttr = "";
		shdAttr = "";
	}

	@Override
	protected boolean assemble() {
		effectList = new HashMap<>();
		if (!HawkOSOperator.isEmptyString(techEffect)) {
			List<String> array = Splitter.on("|").omitEmptyStrings().splitToList(techEffect);
			for (String val : array) {
				String[] info = val.split("_");
				effectList.put(EffType.valueOf(Integer.parseInt(info[0])), Integer.parseInt(info[1]));
			}
		}
		effectList = ImmutableMap.copyOf(effectList);

		conditionTechList = new ArrayList<>();
		if (!HawkOSOperator.isEmptyString(frontTech)) {
			String[] array = frontTech.split(";");
			for (String val : array) {
				conditionTechList.add(Integer.parseInt(val));
			}
		}
		conditionTechList = ImmutableList.copyOf(conditionTechList);
		
		atkAttrMap = SerializeHelper.cfgStr2Map(atkAttr);
        hpAttrMap = SerializeHelper.cfgStr2Map(hpAttr);
        shdAttrMap = SerializeHelper.cfgStr2Map(shdAttr);
		return true;
	}
	
	public int getAtkAttr(int type) {
		return atkAttrMap.getOrDefault(type, 0);
	}

    public int getHpAttr(int type) {
    	return hpAttrMap.getOrDefault(type, 0);
    }
    
    public int getShdAttr(int type) {
    	return shdAttrMap.getOrDefault(type, 0);
    }

	public Map<EffType, Integer> getEffectList() {
		return effectList;
	}

	public void setEffectList(Map<EffType, Integer> effectList) {
		this.effectList = effectList;
	}

	public int getId() {
		return id;
	}

	public int getNodeId() {
		return nodeId;
	}

	public int getHeroId() {
		return heroId;
	}

	public int getTechLevel() {
		return techLevel;
	}

	public String getFrontTech() {
		return frontTech;
	}

	public String getTechCost() {
		return techCost;
	}

	public String getTechEffect() {
		return techEffect;
	}

	public int getTechId() {
		return techId;
	}

	public int getBattlePoint() {
		return battlePoint;
	}

	public List<Integer> getConditionTechList() {
		return conditionTechList;
	}

	public void setConditionTechList(List<Integer> conditionTechList) {
	}

}
