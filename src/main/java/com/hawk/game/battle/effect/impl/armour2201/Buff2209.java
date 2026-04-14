package com.hawk.game.battle.effect.impl.armour2201;

public class Buff2209 {
	public int atkVal;
	public int dicVal;
	public int startRound;
	public int endRound;
	
	public boolean isActive(int round) {
		return round >= startRound && round <= endRound;
	}
}
