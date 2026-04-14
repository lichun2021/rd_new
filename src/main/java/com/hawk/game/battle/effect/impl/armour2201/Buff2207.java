package com.hawk.game.battle.effect.impl.armour2201;

public class Buff2207 {
	public int selectCnt;
	public int startRound;
	public int endRound;
	public int value;
	
	public boolean isActive(int round) {
		return round >= startRound && round <= endRound;
	}
}
