package com.hawk.game.module.ann7party384.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.hawk.os.HawkRand;

import com.hawk.activity.type.impl.ann7party384.cfg.Ann7Party384KVCfg;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoundCSPB;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoundRewardPB;

/**
 * 聚会奖励相关: 开启聚会时随机出来的4轮12个奖励、每轮随机分配给每个玩家的奖励、玩家在每一轮选择的奖励序号、每一轮展示期展示的奖励序号
 */
public class PartyRoundRewardInfo {
	/**
	 * 轮次
	 */
	private int round;
	
	/**
	 * 当前轮次的12个奖励
	 */
	private List<String> partyStartRewardList = new ArrayList<>();
	
	/**
	 * 初始时给当前轮次下每个玩家随机的奖励
	 */
	private Map<String, String> memberRewardMap = new ConcurrentHashMap<>();
	
	/**
	 * 当前轮次玩家选择的奖励序号
	 */
	private Map<String, Integer> rewardIndexMap = new ConcurrentHashMap<>();
	
	/**
	 * 给房主的奖励（房主自选的那一个奖励除外）
	 */
	private List<String> masterRewardList = new ArrayList<>();
	
	/**
	 * 当前轮次是否进入展示期
	 */
	private boolean roundRewardShow = false;
	/**
	 * 每个位置对应的奖励信息
	 */
	private Map<Integer, PlayerRoundReward> roundRewardShowMap = new ConcurrentHashMap<>();
	
	public static PartyRoundRewardInfo create(int round) {
		PartyRoundRewardInfo info = new PartyRoundRewardInfo();
		info.round = round;
		return info;
	}
	
	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}
	
	public boolean isRoundRewardShow() {
		return roundRewardShow;
	}
	
	public void setRoundRewardShow(boolean roundRewardShow) {
		this.roundRewardShow = roundRewardShow;
	}
	
	public List<String> getPartyStartRewardList() {
		return partyStartRewardList;
	}
	
	public Map<String, Integer> getRewardIndexMap() {
		return rewardIndexMap;
	}
	
	public Map<Integer, PlayerRoundReward> getRoundRewardShowMap() {
		return roundRewardShowMap;
	}

	public Map<String, String> getMemberRewardMap() {
		return memberRewardMap;
	}

	public List<String> getMasterRewardList() {
		return masterRewardList;
	}

	public PartyRoundCSPB.Builder toBuilder() {
		PartyRoundCSPB.Builder builder = PartyRoundCSPB.newBuilder();
		builder.setRound(round);
		builder.addAllMasterReward(partyStartRewardList);
		builder.addAllMasterReward(masterRewardList);
		for (Entry<String,String> entry : memberRewardMap.entrySet()) {
			PartyRoundRewardPB.Builder reward = PartyRoundRewardPB.newBuilder();
			reward.setPlayerId(entry.getKey());
			reward.setReward(entry.getValue());
			builder.addMemberReward(reward);
		}
		return builder;
	}

	public boolean selectRewardIndex(String playerId, int index) {
		if (!memberRewardMap.containsKey(playerId)) {
			return false;
		}
		rewardIndexMap.put(playerId, index);
		String reward = memberRewardMap.get(playerId);
		PlayerRoundReward obj = PlayerRoundReward.create(playerId, reward, index);
		roundRewardShowMap.put(index, obj);
		return true;
	}

	public Map<String, Integer> checkAbsentPlayerIndex(String masterPlayerId) {
		Map<String, Integer> map = new HashMap<>();
		int max = Ann7Party384KVCfg.getInstance().getRewardBoxLimit();
		for (String playerId : memberRewardMap.keySet()) {
			if (rewardIndexMap.containsKey(playerId)) {
				continue;
			}
			List<Integer> emptyList = new ArrayList<>();
			for (int index = 1; index <= max; index++) {
				if(!rewardIndexMap.containsValue(index)) {
					emptyList.add(index);
				}
			}
			if (emptyList.isEmpty()) {
				break;
			}
			int index = HawkRand.randomObject(emptyList);
			rewardIndexMap.put(playerId, index);
			map.put(playerId, index);
			String reward = memberRewardMap.get(playerId);
			PlayerRoundReward obj = PlayerRoundReward.create(playerId, reward, index);
			roundRewardShowMap.put(index, obj);
		}
		
		for (String reward : masterRewardList) {
			List<Integer> emptyList = new ArrayList<>();
			for (int index = 1; index <= max; index++) {
				if(!roundRewardShowMap.containsKey(index)) {
					emptyList.add(index);
				}
			}
			if (emptyList.isEmpty()) {
				break;
			}
			int index = HawkRand.randomObject(emptyList);
			PlayerRoundReward obj = PlayerRoundReward.create(masterPlayerId, reward, index);
			roundRewardShowMap.put(index, obj);
		}
		
		if (roundRewardShowMap.size() < partyStartRewardList.size()) {
			List<String> rewards = roundRewardShowMap.values().stream().map(e -> e.getReward()).collect(Collectors.toList());
			partyStartRewardList.removeAll(rewards);
			for (String reward : partyStartRewardList) {
				for (int index = 1; index <= max; index++) {
					if(!roundRewardShowMap.containsKey(index)) {
						PlayerRoundReward obj = PlayerRoundReward.create("", reward, index);
						roundRewardShowMap.put(index, obj);
						break;
					}
				}
			}
		}
		
		return map;
	}
	
}
