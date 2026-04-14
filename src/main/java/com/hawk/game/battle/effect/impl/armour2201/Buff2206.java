package com.hawk.game.battle.effect.impl.armour2201;

public class Buff2206 {
	public int effect2206ContinueRound;
	public int eff2206Val;
	public int startRound;
	public int endRound;

	public boolean isActive(int round) {
		return round >= startRound && round <= endRound;
	}
}
