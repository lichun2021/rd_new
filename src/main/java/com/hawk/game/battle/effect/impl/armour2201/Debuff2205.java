package com.hawk.game.battle.effect.impl.armour2201;

public class Debuff2205 {
	public int debuffCnt;
	public int debuffVal;
	public int startRound;
	public int endRound;
	
	public boolean isActive(int round) {
		return round >= startRound && round <= endRound;
	}
}
