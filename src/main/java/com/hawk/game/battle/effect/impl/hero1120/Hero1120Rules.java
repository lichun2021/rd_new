package com.hawk.game.battle.effect.impl.hero1120;

/** Pure battle calculations for hero 1120 effects. */
public final class Hero1120Rules {
	private Hero1120Rules() {
	}

	/**
	 * Calculates the shared layer count for effects 12838 and 12839.
	 * Invalid configuration disables only the layered effect so battle can continue.
	 */
	public static int effect12838Layer(int battleRound, int intervalRound, int maximumLayer) {
		if (battleRound <= 0 || intervalRound <= 0 || maximumLayer <= 0) {
			return 0;
		}
		return Math.min(battleRound / intervalRound, maximumLayer);
	}
}
