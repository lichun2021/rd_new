package com.hawk.game.module.ann7party384.data;

import org.hawk.os.HawkOSOperator;

import com.hawk.game.protocol.Act384Ann7Party.PartyRoundRewardPB;

public class PlayerRoundReward {

	private String playerId;
	
	private String reward;
	
	private int rewardIndex;

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	public int getRewardIndex() {
		return rewardIndex;
	}

	public void setRewardIndex(int rewardIndex) {
		this.rewardIndex = rewardIndex;
	}
	
	public static PlayerRoundReward create(String playerId, String reward, int index) {
		PlayerRoundReward obj = new PlayerRoundReward();
		obj.playerId = playerId;
		obj.reward = reward;
		obj.rewardIndex = index;
		return obj;
	}
	
	public PartyRoundRewardPB.Builder toBuilder() {
		PartyRoundRewardPB.Builder builder = PartyRoundRewardPB.newBuilder();
		if (!HawkOSOperator.isEmptyString(playerId)) {
			builder.setPlayerId(playerId);
		}
		builder.setReward(reward);
		builder.setRewardIndex(rewardIndex);
		return builder;
	}
}
