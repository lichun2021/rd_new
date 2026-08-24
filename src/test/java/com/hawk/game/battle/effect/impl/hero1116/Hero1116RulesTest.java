package com.hawk.game.battle.effect.impl.hero1116;

public final class Hero1116RulesTest {
	private Hero1116RulesTest() {
	}

	public static void main(String[] args) {
		assertEquals(25, Hero1116Rules.adjustAffectedCount(100, 2500, 10000),
				"12724 applies the default soldier coefficient");
		assertEquals(100, Hero1116Rules.adjustAffectedCount(100, 2500, 40000),
				"12724 applies the configured sniper coefficient");
		assertEquals(0, Hero1116Rules.adjustAffectedCount(100, 0, 40000),
				"12724 has no effect for a zero effect value");
	}

	private static void assertEquals(int expected, int actual, String message) {
		if (expected != actual) {
			throw new AssertionError(message + ": expected " + expected + ", got " + actual);
		}
	}
}
