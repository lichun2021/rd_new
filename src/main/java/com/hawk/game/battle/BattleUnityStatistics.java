package com.hawk.game.battle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.hawk.collection.ConcurrentHashTable;
import org.hawk.os.HawkException;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import com.google.common.util.concurrent.AtomicLongMap;
import com.hawk.game.config.ConstProperty;
import com.hawk.game.march.ArmyInfo;
import com.hawk.game.protocol.Const.EffType;
import com.hawk.game.protocol.Const.SoldierType;

public class BattleUnityStatistics {
	/** 所有出战兵数量, 集结是整支部队 */
	private double totalCount;
	private AtomicLongMap<String> playerArmyCountMap;
	private AtomicLongMap<SoldierType> armyCountMap;
	/** 战报里最后显示有几排，就是几排兵*/
	private AtomicLongMap<SoldierType> armyPaiCountMap = AtomicLongMap.create();
	private Table<String, SoldierType, Integer> playerSoldierCount;
	
	/** 出征数量, 无影子兵 无防御武器 光陵塔*/
	private double totalCountMarch;
	private AtomicLongMap<String> playerArmyCountMapMarch;
	private AtomicLongMap<SoldierType> armyCountMapMarch;
	private Table<String, SoldierType, Integer> playerSoldierCountMarch;

	private Multimap<String, Integer> playerHeromap;
	/**玩家,兵种类型, 兵种 (free 倒序)*/
	private Table<String, SoldierType, List<BattleUnity>> playerSoldierTable;
	/**全体成员士兵类型 不包括城武*/
	private Set<SoldierType> soldierTypeAll;

	/** 已方所有参战玩家 */
	private List<ArmyInfo> armyList;
	/** 已方所有参战玩家 */
	private List<BattleUnity> unityList;
	private BattleUnity leaderUnity;

	private BattleUnityPlantStatistics plantUnityStatistics;
	/** 近战数量最多*/
	private BattleUnity maxJinZhan;
	private BattleUnity maxJinZhanMarch;
	/** 远程数量最多*/
	private BattleUnity maxYuanCheng;
	private BattleUnity maxYuanChengMarch;
	/** 参谋点*/
	private int staffOfficePoint;
	
	
	public SoldierType getPlayerRisePageType(String playerId) {
		try {
			final double per = 0.5;
			if ((playerSoldierCountMarch.get(playerId, SoldierType.TANK_SOLDIER_1) + playerSoldierCountMarch.get(playerId, SoldierType.TANK_SOLDIER_2))* 1.0
					/ playerArmyCountMapMarch.get(playerId) >= per) {
				boolean bfalse = playerSoldierCountMarch.get(playerId, SoldierType.TANK_SOLDIER_1) >= playerSoldierCountMarch.get(playerId, SoldierType.TANK_SOLDIER_2);
				return bfalse ? SoldierType.TANK_SOLDIER_1: SoldierType.TANK_SOLDIER_2;
			}
			if ((playerSoldierCountMarch.get(playerId, SoldierType.PLANE_SOLDIER_3) + playerSoldierCountMarch.get(playerId, SoldierType.PLANE_SOLDIER_4))* 1.0
					/ playerArmyCountMapMarch.get(playerId) >= per) {
				boolean bfalse = playerSoldierCountMarch.get(playerId, SoldierType.PLANE_SOLDIER_3) >= playerSoldierCountMarch.get(playerId, SoldierType.PLANE_SOLDIER_4);
				return bfalse ? SoldierType.PLANE_SOLDIER_3: SoldierType.PLANE_SOLDIER_4;
			}
			if ((playerSoldierCountMarch.get(playerId, SoldierType.FOOT_SOLDIER_5) + playerSoldierCountMarch.get(playerId, SoldierType.FOOT_SOLDIER_6))* 1.0
					/ playerArmyCountMapMarch.get(playerId) >= per) {
				boolean bfalse = playerSoldierCountMarch.get(playerId, SoldierType.FOOT_SOLDIER_5) >= playerSoldierCountMarch.get(playerId, SoldierType.FOOT_SOLDIER_6);
				return bfalse ? SoldierType.FOOT_SOLDIER_5: SoldierType.FOOT_SOLDIER_6;
			}
			if ((playerSoldierCountMarch.get(playerId, SoldierType.CANNON_SOLDIER_7) + playerSoldierCountMarch.get(playerId, SoldierType.CANNON_SOLDIER_8))* 1.0
					/ playerArmyCountMapMarch.get(playerId) >= per) {
				boolean bfalse = playerSoldierCountMarch.get(playerId, SoldierType.CANNON_SOLDIER_7) >= playerSoldierCountMarch.get(playerId, SoldierType.CANNON_SOLDIER_8);
				return bfalse ? SoldierType.CANNON_SOLDIER_7: SoldierType.CANNON_SOLDIER_8;
			}
		} catch (Exception e) {
			HawkException.catchException(e);
		}
		return null;
	}
	
	
	protected BattleUnityStatistics() {
	}

	public boolean isMass(){
		return getPlayerArmyCountMap().size() > 1;
	}
	
	public boolean isNotMass(){
		return !isMass();
	}
	
	public static BattleUnityStatistics create(List<BattleUnity> unitList) {
		List<String> playerIds = unitList.stream().map(BattleUnity::getPlayerId).distinct().collect(Collectors.toList());
		BattleUnityStatistics result = fill(new BattleUnityStatistics(), playerIds, unitList);
		List<BattleUnity> plantUList = new ArrayList<>();
		for (BattleUnity unity : unitList) {
			if (unity.getArmyInfo().isPlant()) {
				plantUList.add(unity);
			}
		}
		result.plantUnityStatistics = fill(new BattleUnityPlantStatistics(), playerIds, plantUList);
		return result;
	}

	private static <T extends BattleUnityStatistics> T fill(T result,List<String> playerIds, List<BattleUnity> unitList) {
		List<ArmyInfo> armyList = unitList.stream().map(BattleUnity::getArmyInfo).collect(Collectors.toList());
		/** 所有出战兵数量, 集结是整支部队 */
		double totalCount = 0;
		double totalCountMarch = 0;
		Set<SoldierType> soldierTypeAll = new HashSet<>();
		AtomicLongMap<String> playerArmyCountMap = AtomicLongMap.create();
		AtomicLongMap<SoldierType> armyCountMap = AtomicLongMap.create();
		Table<String, SoldierType, Integer> playerSoldierCount = ConcurrentHashTable.create();
		
		AtomicLongMap<String> playerArmyCountMapMarch = AtomicLongMap.create();
		AtomicLongMap<SoldierType> armyCountMapMarch = AtomicLongMap.create();
		Table<String, SoldierType, Integer> playerSoldierCountMarch = ConcurrentHashTable.create();
		
		for(SoldierType stype : SoldierType .values()){
			armyCountMap.put(stype, 0);
			armyCountMapMarch.put(stype, 0);
		}
		
		for(String playerId: playerIds){
			playerArmyCountMap.put(playerId, 0);
			playerArmyCountMapMarch.put(playerId, 0);
			for(SoldierType stype : SoldierType .values()){
				playerSoldierCount.put(playerId, stype, 0);
				playerSoldierCountMarch.put(playerId, stype, 0);
			}
		}
		
		Map<String,Integer> staffOfficePointMap = new HashMap<>();
		
		Table<String, SoldierType, List<BattleUnity>> playerSoldierTable = ConcurrentHashTable.create();
		Multimap<String, Integer> playerHeromap = HashMultimap.create();
		for (BattleUnity unit : unitList) {
			ArmyInfo ar = unit.getArmyInfo();
			totalCount += ar.getFreeCnt();
			if (unit.getSolider().isJinZhan() || unit.getSolider().isYuanCheng()) {
				totalCountMarch += ar.getFreeCnt() - ar.getShadowCnt();
				playerArmyCountMapMarch.getAndAdd(ar.getPlayerId(), ar.getFreeCnt() - ar.getShadowCnt());
			}
			playerArmyCountMap.getAndAdd(ar.getPlayerId(), ar.getFreeCnt());
			armyCountMap.getAndAdd(ar.getType(), ar.getFreeCnt());
			armyCountMapMarch.getAndAdd(ar.getType(), ar.getFreeCnt() - ar.getShadowCnt());
			if (ar.getType().getNumber() <= 8) {
				result.getArmyPaiCountMap().incrementAndGet(ar.getType());
				soldierTypeAll.add(ar.getType());
			}
			if (playerSoldierCount.contains(ar.getPlayerId(), ar.getType())) {
				Integer count = playerSoldierCount.get(ar.getPlayerId(), ar.getType());
				playerSoldierCount.put(ar.getPlayerId(), ar.getType(), count + ar.getFreeCnt());
			} else {
				playerSoldierCount.put(ar.getPlayerId(), ar.getType(), ar.getFreeCnt());
			}
			if (playerSoldierCountMarch.contains(ar.getPlayerId(), ar.getType())) {
				Integer count = playerSoldierCountMarch.get(ar.getPlayerId(), ar.getType());
				playerSoldierCountMarch.put(ar.getPlayerId(), ar.getType(), count + ar.getFreeCnt() - ar.getShadowCnt());
			} else {
				playerSoldierCountMarch.put(ar.getPlayerId(), ar.getType(), ar.getFreeCnt() - ar.getShadowCnt());
			}
			/////////////////////////////////////////
			if (!playerSoldierTable.contains(ar.getPlayerId(), ar.getType())) {
				playerSoldierTable.put(ar.getPlayerId(), ar.getType(), new ArrayList<>());
			}
			playerSoldierTable.get(ar.getPlayerId(), ar.getType()).add(unit);
			
			if(!staffOfficePointMap.containsKey(ar.getPlayerId())){
				staffOfficePointMap.put(ar.getPlayerId(), unit.getPlayer().getStaffOffic().getStaffVal());
			}
			if(!playerHeromap.containsKey(unit.getPlayerId())){
				playerHeromap.putAll(unit.getPlayerId(), unit.getEffectParams().getHeroIds());
			}
			//////////////////////////////////
		}
		int stpoint = staffOfficePointMap.values().stream().sorted(Comparator.comparingInt(Integer::intValue).reversed()).mapToInt(Integer::intValue).limit(ConstProperty.getInstance().getStaffOffice4Max()).sum();
		result.setStaffOfficePoint(stpoint);
		
		playerSoldierTable.values().forEach(list -> list.sort(Comparator.comparingInt(BattleUnity::getFreeCnt).thenComparingInt(BattleUnity::getSoldierLevel).reversed().thenComparing(BattleUnity::getBuildingWeight)));

		try {
			List<BattleUnity> maxnumList = playerSoldierTable.values().stream().filter(v-> !v.isEmpty())
					.map(v -> v.get(0))
					.sorted(Comparator.comparingInt(BattleUnity::getSoldierLevel).reversed().thenComparing(BattleUnity::getBuildingWeight))
					.collect(Collectors.toList());
			for (BattleUnity unit : maxnumList) {
				if (unit.getSolider().isJinZhan()) {
					if (result.getMaxJinZhan() == null) {
						result.setMaxJinZhan(unit);
						result.setMaxJinZhanMarch(unit);
					}
					if (unit.getFreeCnt() > result.getMaxJinZhan().getFreeCnt()) {
						result.setMaxJinZhan(unit);
					}
					if (unit.getMarchCnt() > result.getMaxJinZhan().getMarchCnt()) {
						result.setMaxJinZhanMarch(unit);
					}
				}
				if (unit.getSolider().isYuanCheng()) {
					if (result.getMaxYuanCheng() == null) {
						result.setMaxYuanCheng(unit);
						result.setMaxYuanChengMarch(unit);
					}
					if (unit.getFreeCnt() > result.getMaxYuanCheng().getFreeCnt()) {
						result.setMaxYuanCheng(unit);
					}
					if (unit.getMarchCnt() > result.getMaxYuanCheng().getMarchCnt()) {
						result.setMaxYuanChengMarch(unit);
					}
				}
			}
//			if (result.getMaxJinZhan() != null) {
//				result.getMaxJinZhan().getSolider().addDebugLog("本方近战最多 {}", result.getMaxJinZhan().getSolider().getUUID());
//			}
//			if (result.getMaxYuanCheng() != null) {
//				result.getMaxYuanCheng().getSolider().addDebugLog("本方远程最多 {}", result.getMaxYuanCheng().getSolider().getUUID());
//			}

		} catch (Exception e) {
			HawkException.catchException(e);
		}
		result.setTotalCount(totalCount);
		result.setTotalCountMarch(totalCountMarch);
		result.setPlayerArmyCountMap(playerArmyCountMap);
		result.setPlayerArmyCountMapMarch(playerArmyCountMapMarch);
		result.setPlayerSoldierCount(playerSoldierCount);
		result.setPlayerSoldierCountMarch(playerSoldierCountMarch);
		result.setArmyList(armyList);
		result.setUnityList(unitList);
		result.setSoldierTypeAll(soldierTypeAll);
		if (!unitList.isEmpty()) {
			result.setLeaderUnity(unitList.get(0));
		}
		result.setPlayerSoldierTable(playerSoldierTable);
		result.setArmyCountMap(armyCountMap);
		result.setArmyCountMapMarch(armyCountMapMarch);
		result.setPlayerHeromap(playerHeromap);
		return result;
	}
	
	/**指定兵种中出征数量最多的*/
	public BattleUnity getPlayerMaxFreeArmy(String playerId, SoldierType type) {
		if (getPlayerSoldierTable().contains(playerId, type)) {
			return getPlayerSoldierTable().get(playerId, type).get(0);
		}
		return null;
	}
	/**出征数量最多的*/
	public BattleUnity getPlayeMaxMarchArmy(String playerId) {
		BattleUnity max = null;
		for (SoldierType type : SoldierType.values()) {
			if (!BattleSoldier.isSoldier(type)) {
				continue;
			}
			BattleUnity ts = getPlayerMaxFreeArmy(playerId, type);
			if (ts == null) {
				continue;
			}
			if (max == null) {
				max = ts;
				continue;
			}
			if (ts.getMarchCnt() > max.getMarchCnt()) {
				max = ts;
				continue;
			}
			if (ts.getSoldierLevel() > max.getSoldierLevel()) {
				max = ts;
				continue;
			}
		}
		return max;
	}
	
	Map<String,BattleUnity> p505Cache = new HashMap<>();
	/**
	 * 成规模兵种的意义 （大于50%自身且大于5%集结）
	 * @return 如有满足条件的类型, 返回该类型中出征最多的
	 */
	public BattleUnity getPlayer505Army(String playerId) {
		if(p505Cache.containsKey(playerId)){
			return p505Cache.get(playerId);
		}
		
		BattleUnity result = null;
		for (SoldierType type : SoldierType.values()) {
			if (!BattleSoldier.isSoldier(type)) {
				continue;
			}
			double selfcount = playerSoldierCountMarch.get(playerId, type);
			if (selfcount / playerArmyCountMapMarch.get(playerId) > 0.5 && selfcount / totalCountMarch > 0.05) {
				result = getPlayerMaxFreeArmy(playerId, type);
				break;
			}

		}
		p505Cache.put(playerId, result);
		if(result!=null){
			result.getSolider().addDebugLog("成规模兵种 {}", result.getSolider().getUUID());
		}
		return result;
	}

	/**
	 * 成规模兵种的意义 （大于50%自身且大于5%集结）
	 * @return 如有满足条件的类型, 返回该类型中出征最多的
	 */
	public int getMass505ArmyTypeCnt() {
		Set<SoldierType> set = new HashSet<>();
		for (String pid : playerArmyCountMapMarch.asMap().keySet()) {
			BattleUnity unity = getPlayer505Army(pid);
			if (unity != null) {
				set.add(unity.getSolider().getType());
			}
		}
		return set.size();
	}
	
	/**
	 * 成规模兵种的意义 （大于50%自身且大于5%集结）
	 * @return 如有满足条件的类型, 返回该类型中出征最多的
	 */
	public Map<String, BattleUnity> getMass505TypeArmy(SoldierType type) {
		Map<String, BattleUnity> result = new HashMap<>();
		for (String pid : playerArmyCountMapMarch.asMap().keySet()) {
			BattleUnity unity = getPlayer505Army(pid);
			if (unity != null && unity.getSolider().getType() == type) {
				result.put(pid, unity);
			}
		}
		return result;
	}
	
	public int getEffValSOType4(EffType effType){
		if(leaderUnity.getEffectParams().isStaffPointGreat()){
			return leaderUnity.getPlayer().getStaffOffic().getEffValSOType4(effType);
		}
		return 0;
	}

	public double getTotalCount() {
		return totalCount;
	}

	public AtomicLongMap<String> getPlayerArmyCountMap() {
		return playerArmyCountMap;
	}

	public Table<String, SoldierType, Integer> getPlayerSoldierCount() {
		return playerSoldierCount;
	}

	public List<ArmyInfo> getArmyList() {
		return armyList;
	}

	public List<BattleUnity> getUnityList() {
		return unityList;
	}

	public void setTotalCount(double totalCount) {
		this.totalCount = totalCount;
	}

	public void setPlayerArmyCountMap(AtomicLongMap<String> playerArmyCountMap) {
		this.playerArmyCountMap = playerArmyCountMap;
	}

	public void setPlayerSoldierCount(Table<String, SoldierType, Integer> playerSoldierCount) {
		this.playerSoldierCount = playerSoldierCount;
	}

	public void setArmyList(List<ArmyInfo> armyList) {
		this.armyList = armyList;
	}

	public void setUnityList(List<BattleUnity> unityList) {
		this.unityList = unityList;
	}

	public BattleUnity getLeaderUnity() {
		return leaderUnity;
	}

	public void setLeaderUnity(BattleUnity leaderUnity) {
		this.leaderUnity = leaderUnity;
	}

	public Table<String, SoldierType, List<BattleUnity>> getPlayerSoldierTable() {
		return playerSoldierTable;
	}

	public void setPlayerSoldierTable(Table<String, SoldierType, List<BattleUnity>> playerSoldierTable) {
		this.playerSoldierTable = playerSoldierTable;
	}

	public AtomicLongMap<SoldierType> getArmyCountMap() {
		return armyCountMap;
	}

	public void setArmyCountMap(AtomicLongMap<SoldierType> armyCountMap) {
		this.armyCountMap = armyCountMap;
	}

	public Set<SoldierType> getSoldierTypeAll() {
		return soldierTypeAll;
	}

	public void setSoldierTypeAll(Set<SoldierType> soldierTypeAll) {
		this.soldierTypeAll = soldierTypeAll;
	}

	public final BattleUnityPlantStatistics getPlantUnityStatistics() {
		return plantUnityStatistics;
	}

	public final void setPlantUnityStatistics(BattleUnityPlantStatistics plantUnityStatistics) {
		this.plantUnityStatistics = plantUnityStatistics;
	}

	public AtomicLongMap<String> getPlayerArmyCountMapMarch() {
		return playerArmyCountMapMarch;
	}

	public void setPlayerArmyCountMapMarch(AtomicLongMap<String> playerArmyCountMapMarch) {
		this.playerArmyCountMapMarch = playerArmyCountMapMarch;
	}

	public AtomicLongMap<SoldierType> getArmyCountMapMarch() {
		return armyCountMapMarch;
	}

	public void setArmyCountMapMarch(AtomicLongMap<SoldierType> armyCountMapMarch) {
		this.armyCountMapMarch = armyCountMapMarch;
	}

	public Table<String, SoldierType, Integer> getPlayerSoldierCountMarch() {
		return playerSoldierCountMarch;
	}

	public void setPlayerSoldierCountMarch(Table<String, SoldierType, Integer> playerSoldierCountMarch) {
		this.playerSoldierCountMarch = playerSoldierCountMarch;
	}

	public double getTotalCountMarch() {
		return totalCountMarch;
	}

	public void setTotalCountMarch(double totalCountMarch) {
		this.totalCountMarch = totalCountMarch;
	}

	public BattleUnity getMaxJinZhan() {
		return maxJinZhan;
	}

	public void setMaxJinZhan(BattleUnity maxJinZhan) {
		this.maxJinZhan = maxJinZhan;
	}

	public BattleUnity getMaxJinZhanMarch() {
		return maxJinZhanMarch;
	}

	public void setMaxJinZhanMarch(BattleUnity maxJinZhanMarch) {
		this.maxJinZhanMarch = maxJinZhanMarch;
	}

	public BattleUnity getMaxYuanCheng() {
		return maxYuanCheng;
	}

	public void setMaxYuanCheng(BattleUnity maxYuanCheng) {
		this.maxYuanCheng = maxYuanCheng;
	}

	public BattleUnity getMaxYuanChengMarch() {
		return maxYuanChengMarch;
	}

	public void setMaxYuanChengMarch(BattleUnity maxYuanChengMarch) {
		this.maxYuanChengMarch = maxYuanChengMarch;
	}

	public int getStaffOfficePoint() {
		return staffOfficePoint;
	}

	public void setStaffOfficePoint(int staffOfficePoint) {
		this.staffOfficePoint = staffOfficePoint;
	}
	
	public BattleUnity getMaxSoldier() {
		if (maxJinZhan == null) {
			return maxYuanCheng;
		}
		if (maxYuanCheng == null) {
			return maxJinZhan;
		}

		if (maxJinZhan.getFreeCnt() > maxYuanCheng.getFreeCnt()) {
			return maxJinZhan;
		}
		return maxYuanCheng;
	}
	
	public BattleUnity getMaxSoldierMarch() {
		if (maxJinZhanMarch == null) {
			return maxYuanChengMarch;
		}
		if (maxYuanChengMarch == null) {
			return maxJinZhanMarch;
		}

		if (maxJinZhanMarch.getFreeCnt() > maxYuanChengMarch.getFreeCnt()) {
			return maxJinZhanMarch;
		}
		return maxYuanChengMarch;
	}

	/** 敌方上了5个轰炸 3个狙击 2个主战这个数就是5*/
	public int maxArmyPaiCount() {
		try {
			return armyPaiCountMap.asMap().values().stream().mapToInt(Long::intValue).max().orElse(1);
		} catch (Exception e) {
			HawkException.catchException(e);
		}
		return 1;
	}

	public Multimap<String, Integer> getPlayerHeromap() {
		return playerHeromap;
	}


	public void setPlayerHeromap(Multimap<String, Integer> playerHeromap) {
		this.playerHeromap = playerHeromap;
	}


	public AtomicLongMap<SoldierType> getArmyPaiCountMap() {
		return armyPaiCountMap;
	}


	public void setArmyPaiCountMap(AtomicLongMap<SoldierType> armyPaiCountMap) {
		this.armyPaiCountMap = armyPaiCountMap;
	}

}
