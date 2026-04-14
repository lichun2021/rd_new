package com.hawk.game.config;

import java.util.*;

import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;
import org.hawk.os.HawkOSOperator;

import com.hawk.game.item.ItemInfo;
import com.hawk.serialize.string.SerializeHelper;

/**
 * 铠甲常量表
 * @author golden
 *
 */
@HawkConfigManager.KVResource(file = "xml/armour_const.xml")
public class ArmourConstCfg extends HawkConfigBase {

	/**
	 * 额外属性数量
	 */
	protected final String extraAttrCount;

	/**
	 * 铠甲套装名字长度
	 */
	protected final String suitNameLength;

	/**
	 * 套装最大数量
	 */
	protected final int suitMaxCount;

	/**
	 * 铠甲套装解锁消耗
	 */
	protected final String suitUnlockCost;

	/**
	 * 装备页签初始值
	 */
	protected final int armour_page_initial;

	/**
	 * 套装组合
	 */
	protected final String suit_combination;

	/**
	 * 套装战力
	 */
	protected final String suitArmourCombat;

	/**
	 * 抽卡多少次可以领宝箱
	 */
	protected final int gachaTimesBox;

	/**
	 * xx大本可以穿戴铠甲
	 */
	protected final int cityLevelUnlock;

	/**
	 * 铠甲背包上限
	 */
	protected final int armourMaxCount;

	/**
	 * 每日抽取次数上限
	 */
	protected final int gacha_limit;

	/**
	 * 装备每日免费充能次数
	 */
	protected final int freeCharge;

	/**
	 * 装备充能属性刷新消耗
	 */
	protected final String chargeRefreshConsume;

	/**
	 * 装备普通充能消耗
	 */
	protected final String chargeConsumeCommon;

	/**
	 * 装备特殊充能消耗
	 */
	protected final String chargeConsumeSpecial;

	/**
	 * 装备红色充能消耗
	 */
	protected final String chargeConsumeRed;

	/**
	 * 装备升星品质限制
	 */
	protected final String starQualityLimit;

	/**
	 * 装备升星等级限制
	 */
	protected final int starLevelLimit;

	/**
	 * 装备普通充能限制
	 */
	protected final int chargeCommonLimit;

	/**
	 * 装备高级充能限制
	 */
	protected final int chargeSpecialLimit;

	/**
	 * 装备红色充能限制
	 */
	protected final int chargeRedLimit;

	/**
	 * 装备普通充能
	 */
	protected final String chargeCommonRate;

	/**
	 * 装备高级充能
	 */
	protected final String chargeSpecialRate;

	/**
	 * 装备红色充能
	 */
	protected final String chargeRedRate;

	/**
	 * 星级属性分解比率
	 */
	protected final int startAttrResolveRate;

	/**
	 * 装备星级属性突破等级限制
	 */
	protected final int armourStarAttrBreakQuality;

	/**
	 * 量子(槽位)品质限制
	 */
	protected final String quantumQualityLimit;

	/**
	 * 量子(槽位)等级限制
	 */
	protected final int quantumLevelLimit;

	/**
	 * 红装强化等级增加
	 */
	protected final int quantumLevelLimitAdd;
	/**
	 * 量子槽位到多少级变成红装
	 */
	protected final int quantumRedLevel;

	/**
	 * 红装泰晶等级增加
	 */
	protected final int starLimitAdd;

	/**
	 * 红装基础属性成长值
	 */
	protected final int breakGrowUpRed;

	protected final int immortQualityLimit;
	protected final int immortLevelLimit;
	protected final int immortLevelLimitAdd;
	protected final int immortRedLevel;
	protected final int immortstarLimitAdd;
	protected final int chargeimmortLimit;
	protected final String chargeimmortRate;
	protected final String chargeConsumeimmort;
	protected final String randomitem;
	protected final int breakGrowUpimmort;
	protected final String lockrandomattr1;
	protected final int randomexp;
	protected final String randomlevel;
	protected final String echopool;
	protected final int echofloor;
	protected final String echo_combination;
	public List<Integer> echoPoolList;
	/**
	 * 额外属性数量
	 */
	public Map<Integer, Integer> extraAttrCountMap;

	/**
	 * 铠甲套装解锁消耗
	 */
	public Map<Integer, List<ItemInfo>> suitUnlockCostMap;

	/**
	 * 升星品质限制
	 */
	public List<Integer> starQualityLimitList;

	/**
	 * 属性套装触发件数
	 */
	public List<Integer> suitCombination;
	/**
	 * 属性套装触发件数
	 */
	public List<Integer> suitEchoCombination;

	/**
	 * 装备普通充能
	 */
	public Map<Integer, Integer> chargeCommonRateMap;

	/**
	 * 装备高级充能
	 */
	public Map<Integer, Integer> chargeSpecialRateMap;

	/**
	 * 装备红色充能
	 */
	public Map<Integer, Integer> chargeRedRateMap;
	/**
	 * 装备不朽充能
	 */
	public Map<Integer, Integer> chargeImmortRateMap;

	/**
	 * 充能属性刷新消耗
	 */
	public List<String> chargeRefreshConsumeList;

	/**
	 * 量子(槽位)品质限制
	 */
	public List<Integer> quantumQualityLimitList;

	List<ItemInfo> lockRandomAttr1Item;
	List<ItemInfo> chargeConsumeImmortItem;
	List<ItemInfo> randomConsumeItem;

	private static ArmourConstCfg instance = null;

	public static ArmourConstCfg getInstance() {
		return instance;
	}

	public ArmourConstCfg() {
		instance = this;
		extraAttrCount = "";
		suitNameLength = "";
		suitUnlockCost = "";
		suitMaxCount = 8;
		armour_page_initial = 1;
		suit_combination = "";
		suitArmourCombat = "";
		gachaTimesBox = 50;
		cityLevelUnlock = 0;
		armourMaxCount = 300;
		gacha_limit = 2000;
		freeCharge = 0;
		chargeRefreshConsume = "";
		chargeConsumeCommon = "";
		chargeConsumeSpecial = "";
		chargeConsumeRed = "";
		starQualityLimit = "";
		starLevelLimit = 0;
		chargeCommonLimit = 5000;
		chargeSpecialLimit = 9000;
		chargeRedLimit = 20000;
		chargeCommonRate = "25_1;25_5;25_10;25_100";
		chargeSpecialRate = "25_1;25_5;25_10;25_100";
		chargeRedRate = "25_1;25_5;25_10;25_100";
		startAttrResolveRate = 0;
		armourStarAttrBreakQuality = 4;
		quantumQualityLimit = "";
		quantumLevelLimit = 0;
		quantumLevelLimitAdd = 0;
		quantumRedLevel = 0;
		starLimitAdd = 0;
		breakGrowUpRed = 80000;
		immortQualityLimit = 0;
		immortLevelLimit = 0;
		immortLevelLimitAdd = 0;
		immortRedLevel = 0;
		immortstarLimitAdd = 0;
		chargeimmortLimit = 0;
		chargeimmortRate = "";
		chargeConsumeimmort = "";
		randomitem = "";
		breakGrowUpimmort = 0;
		lockrandomattr1 = "";
		randomexp = 0;
		randomlevel = "";
		echofloor = 0;
		echopool = "";
		echo_combination = "";
	}

	public String getExtraAttrCount() {
		return extraAttrCount;
	}

	public int getSuitNameLength() {
		return Integer.parseInt(suitNameLength.split("_")[1]);
	}

	public int getSuitMaxCount() {
		return suitMaxCount;
	}

	public int getGachaTimesBox() {
		return gachaTimesBox;
	}

	public String getSuitArmourCombat() {
		return suitArmourCombat;
	}

	public int getCityLevelUnlock() {
		return cityLevelUnlock;
	}

	public int getArmourMaxCount() {
		return armourMaxCount;
	}

	public int getGachaLimit() {
		return gacha_limit;
	}

	public List<Integer> getSuitCombination() {
		return suitCombination;
	}
	/**
	 * 获取铠甲件数触发第几套属性
	 */
	public int getSuitCombination(int armourConut) {
		for (int i = suitCombination.size() - 1; i >= 0; i--) {
			if (armourConut >= suitCombination.get(i)) {
				return i + 1;
			}
		}
		return 0;
	}
	/**
	 * 获取铠甲件数触发第几套属性
	 */
	public int getSuitEchoCombination(int armourCount) {
		for (int i = suitEchoCombination.size() - 1; i >= 0; i--) {
			if (armourCount >= suitEchoCombination.get(i)) {
				return i + 1;
			}
		}
		return 0;
	}

	/**
	 * 额外属性数量
	 */
	public Integer getExtrAttrCount(int quality) {
		if (!extraAttrCountMap.containsKey(quality)) {
			return 0;
		}
		return extraAttrCountMap.get(quality);
	}

	public List<ItemInfo> getSuitUnlockCostMap(int quality) {
		return suitUnlockCostMap.get(quality);
	}

	public int getFreeCharge() {
		return freeCharge;
	}

	public List<ItemInfo> getChargeConsumeCommon(int multi) {
		return ItemInfo.valueListOf(chargeConsumeCommon,multi);
	}

	public List<ItemInfo> getChargeConsumeSpecial(int multi) {
		return ItemInfo.valueListOf(chargeConsumeSpecial,multi);
	}

	public List<ItemInfo> getChargeConsumeRed(int multi) {
		return ItemInfo.valueListOf(chargeConsumeRed,multi);
	}
	public List<ItemInfo> getChargeConsumeImmort(int multi) {
		return ItemInfo.valueListOf(chargeConsumeimmort,multi);
	}

	public List<String> getChargeRefreshConsumeList() {
		return chargeRefreshConsumeList;
	}

	public List<ItemInfo> getChargeRefreshConsume(int index) {
		if (index >= chargeRefreshConsumeList.size()) {
			index = chargeRefreshConsumeList.size() - 1;
		}
		return ItemInfo.valueListOf(chargeRefreshConsumeList.get(index));
	}

	/**
	 * 此品质是否能升星
	 * @param quality
	 * @return
	 */
	public boolean canQualityStar(int quality) {
		return starQualityLimitList.contains(quality);
	}

	/**
	 * 此等级是否能升星
	 * @param level
	 * @return
	 */
	public boolean canLevelStar(int level) {
		return level >= starLevelLimit;
	}


	/**
	 * 此品质是否能升级量子（槽位）
	 * @param quality
	 * @return
	 */
	public boolean canQualityQuantum(int quality) {
		return quantumQualityLimitList.contains(quality);
	}
	/**
	 * 此品质是否能升级不朽
	 * @param quality
	 * @return
	 */
	public boolean canQualityImmort(int quality) {
		return quality >= immortQualityLimit;
	}
	/**
	 * 此等级是否能升级量子（槽位）
	 * @param level
	 * @return
	 */
	public boolean canLevelQuantum(int level) {
		return level >= quantumLevelLimit;
	}
	/**
	 * 此等级是否能升级量子（槽位）
	 * @param level
	 * @return
	 */
	public boolean canLevelImmort(int level) {
		return level >= immortLevelLimit;
	}
	public int getQuantumLevelLimitAdd() {
		return quantumLevelLimitAdd;
	}

	public int getQuantumRedLevel() {
		return quantumRedLevel;
	}

	public int getStarLimitAdd(){
		return starLimitAdd;
	}

	public int getBreakGrowUpRed() {
		return breakGrowUpRed;
	}

	public int getChargeCommonLimit() {
		return chargeCommonLimit;
	}

	public int getChargeSpecialLimit() {
		return chargeSpecialLimit;
	}

	public int getChargeRedLimit() {
		return chargeRedLimit;
	}

	public Map<Integer, Integer> getChargeCommonRateMap() {
		return chargeCommonRateMap;
	}

	public Map<Integer, Integer> getChargeSpecialRateMap() {
		return chargeSpecialRateMap;
	}

	public Map<Integer, Integer> getChargeRedRateMap() {
		return chargeRedRateMap;
	}
	public Map<Integer, Integer> getChargeImmortRateMap() {
		return chargeImmortRateMap;
	}

	public int getStartAttrResolveRate() {
		return startAttrResolveRate;
	}

	public int getArmourStarAttrBreakQuality() {
		return armourStarAttrBreakQuality;
	}

	@Override
	protected boolean assemble() {
		extraAttrCountMap = SerializeHelper.stringToMap(extraAttrCount, Integer.class, Integer.class, SerializeHelper.ATTRIBUTE_SPLIT, SerializeHelper.BETWEEN_ITEMS);

		Map<Integer, List<ItemInfo>> suitUnlockCostMap = new HashMap<>();
		if (!HawkOSOperator.isEmptyString(suitUnlockCost)) {
			String[] cost = suitUnlockCost.split(";");
			for (int i = 1; i <= cost.length; i++) {
				List<ItemInfo> itemList = ItemInfo.valueListOf(cost[i - 1]);
				suitUnlockCostMap.put(i, itemList);
			}
		}
		this.suitUnlockCostMap = suitUnlockCostMap;

		List<Integer> suitCombination = new ArrayList<>();
		if (!HawkOSOperator.isEmptyString(suit_combination)) {
			String[] split = suit_combination.split("_");
			for (int i = 0; i < split.length; i++) {
				suitCombination.add(Integer.valueOf(split[i]));
			}
		}
		this.suitCombination = suitCombination;
		List<Integer> suitEchoCombination = new ArrayList<>();
		if (!HawkOSOperator.isEmptyString(echo_combination)) {
			String[] split = echo_combination.split("_");
			for (int i = 0; i < split.length; i++) {
				suitEchoCombination.add(Integer.valueOf(split[i]));
			}
		}
		this.suitEchoCombination = suitEchoCombination;

		List<Integer> starQualityLimitList = new ArrayList<>();
		if (!HawkOSOperator.isEmptyString(starQualityLimit)) {
			String[] split = starQualityLimit.split(",");
			for (int i = 0; i < split.length; i++) {
				starQualityLimitList.add(Integer.valueOf(split[i]));
			}
		}
		this.starQualityLimitList = starQualityLimitList;

		Map<Integer, Integer> chargeCommonRateMap = new HashMap<>();
		if (!HawkOSOperator.isEmptyString(chargeCommonRate)) {
			String[] split = chargeCommonRate.split(";");
			for (int i = 0; i < split.length; i++) {
				String[] split2 = split[i].split("_");
				chargeCommonRateMap.put(Integer.valueOf(split2[1]), Integer.valueOf(split2[0]));
			}
		}
		this.chargeCommonRateMap = chargeCommonRateMap;

		Map<Integer, Integer> chargeSpecialRateMap = new HashMap<>();
		if (!HawkOSOperator.isEmptyString(chargeSpecialRate)) {
			String[] split = chargeSpecialRate.split(";");
			for (int i = 0; i < split.length; i++) {
				String[] split2 = split[i].split("_");
				chargeSpecialRateMap.put(Integer.valueOf(split2[1]), Integer.valueOf(split2[0]));
			}
		}
		this.chargeSpecialRateMap = chargeSpecialRateMap;

		Map<Integer, Integer> chargeRedRateMap = new HashMap<>();
		if (!HawkOSOperator.isEmptyString(chargeRedRate)) {
			String[] split = chargeRedRate.split(";");
			for (int i = 0; i < split.length; i++) {
				String[] split2 = split[i].split("_");
				chargeRedRateMap.put(Integer.valueOf(split2[1]), Integer.valueOf(split2[0]));
			}
		}
		this.chargeRedRateMap = chargeRedRateMap;

		Map<Integer, Integer> chargeImmortRateMap = new HashMap<>();
		if (!HawkOSOperator.isEmptyString(chargeimmortRate)) {
			String[] split = chargeimmortRate.split(";");
			for (int i = 0; i < split.length; i++) {
				String[] split2 = split[i].split("_");
				chargeImmortRateMap.put(Integer.valueOf(split2[1]), Integer.valueOf(split2[0]));
			}
		}
		this.chargeImmortRateMap = chargeImmortRateMap;

		List<String> chargeRefreshConsumeList = new ArrayList<>();
		if (!HawkOSOperator.isEmptyString(chargeRefreshConsume)) {
			String[] split = chargeRefreshConsume.split(";");
			for (int i = 0; i < split.length; i++) {
				chargeRefreshConsumeList.add(split[i]);
			}
		}
		this.chargeRefreshConsumeList = chargeRefreshConsumeList;

		List<Integer> quantumQualityLimitList = new ArrayList<>();
		if (!HawkOSOperator.isEmptyString(quantumQualityLimit)) {
			String[] split = quantumQualityLimit.split(",");
			for (int i = 0; i < split.length; i++) {
				quantumQualityLimitList.add(Integer.valueOf(split[i]));
			}
		}
		this.quantumQualityLimitList = quantumQualityLimitList;

		lockRandomAttr1Item = ItemInfo.valueListOf(lockrandomattr1);
        chargeConsumeImmortItem = ItemInfo.valueListOf(chargeConsumeimmort);
		randomConsumeItem = ItemInfo.valueListOf(randomitem);

		echoPoolList = new ArrayList<>();
		if (!HawkOSOperator.isEmptyString(echopool)) {
			String[] items = echopool.split(",");
			for (String item : items) {
				echoPoolList.add(Integer.valueOf(item.trim()));
			}
		}

		return true;
	}

	public int getImmortQualityLimit() {
		return immortQualityLimit;
	}

	public int getImmortLevelLimitAdd() {
		return immortLevelLimitAdd;
	}

	public int getImmortLevelLimit() {
		return immortLevelLimit;
	}

	public int getImmortRedLevel() {
		return immortRedLevel;
	}

	public int getImmortStarLimitAdd() {
		return immortstarLimitAdd;
	}

	public int getChargeImmortLimit() {
		return chargeimmortLimit;
	}

	public String getChargeImmortRate() {
		return chargeimmortRate;
	}

	public List<ItemInfo> getChargeConsumeImmort() {
		return chargeConsumeImmortItem;
	}
	public List<ItemInfo> getRandomConsumeItem() {
		return randomConsumeItem;
	}

	public int getBreakGrowUpImmort() {
		return breakGrowUpimmort;
	}
	public int getRandomEXP() {
		return randomexp;
	}

	public String getRandomLevel() {
		return randomlevel;
	}

	public List<ItemInfo> getLockRandomAttr1Item() {
		return lockRandomAttr1Item;
	}


	public List<Integer> getEchoPoolList() {
		return echoPoolList;
	}

	public int getEchofloor() {
		return echofloor;
	}
}
