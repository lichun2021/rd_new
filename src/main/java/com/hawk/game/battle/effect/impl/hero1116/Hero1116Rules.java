package com.hawk.game.battle.effect.impl.hero1116;

/** Pure calculations shared by hero 1116 battle hooks and focused tests. */
public final class Hero1116Rules {
	private static final double EFFECT_SCALE = 0.0001d;

	private Hero1116Rules() {
	}

	public static int adjustAffectedCount(int freeCount, int effectValue, int soldierAdjust) {
		return (int) (freeCount * EFFECT_SCALE * effectValue * EFFECT_SCALE * soldierAdjust);
	}
}
