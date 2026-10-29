package com.hawk.game.player.hero;

import java.util.Arrays;
import java.util.List;

public final class LegacyHeroSkillMigrationTest {
    private LegacyHeroSkillMigrationTest() {
    }

    public static void main(String[] args) {
        clearsRetiredAlanSlotsFromLexa();
        installsSheilaStaffOfficerSlotsOverRetiredLexaLayout();
        leavesAlanAndCurrentLayoutsUntouched();
    }

    private static void clearsRetiredAlanSlotsFromLexa() {
        int[] unlockLevels = {20, 60, 100, 120};
        for (int index = 1; index <= 4; index++) {
            String original = "[1," + index + ",true," + (112210 + index) + ",[37]]";
            List<?> source = Arrays.asList(1, index, true, 112210 + index, "[37]");
            List<?> migrated = LegacyHeroSkillMigration.migrate(
                    1122, false, index, unlockLevels[index - 1], 0, null, false,
                    source, original);

            assertNotSame(source, migrated, "Lexa slot should migrate");
            assertEquals(Arrays.asList(unlockLevels[index - 1], index, false, null, null, original),
                    migrated, "Lexa migrated slot");
        }
    }

    private static void installsSheilaStaffOfficerSlotsOverRetiredLexaLayout() {
        int[] retiredUnlockLevels = {20, 60, 100, 120};
        for (int index = 1; index <= 4; index++) {
            String original = "[" + retiredUnlockLevels[index - 1] + "," + index
                    + ",false,null,null]";
            List<?> source = Arrays.asList(
                    retiredUnlockLevels[index - 1], index, false, null, null);
            List<?> migrated = LegacyHeroSkillMigration.migrate(
                    1123, true, index, 1, 112310 + index, "[0]", false,
                    source, original);

            assertNotSame(source, migrated, "Sheila slot should migrate");
            assertEquals(Arrays.asList(1, index, true, 112310 + index, "[0]", original),
                    migrated, "Sheila migrated slot");
        }
    }

    private static void leavesAlanAndCurrentLayoutsUntouched() {
        List<?> alan = Arrays.asList(1, 1, true, 112111, "[12]");
        assertSame(alan, LegacyHeroSkillMigration.migrate(
                1121, true, 1, 1, 112111, "[0]", true, alan, alan.toString()),
                "Alan has no incompatible slot layout");

        List<?> currentLexa = Arrays.asList(20, 1, false, null, null);
        assertSame(currentLexa, LegacyHeroSkillMigration.migrate(
                1122, false, 1, 20, 0, null, false,
                currentLexa, currentLexa.toString()), "current Lexa slot");

        List<?> currentSheila = Arrays.asList(1, 1, true, 112311, "[9]");
        assertSame(currentSheila, LegacyHeroSkillMigration.migrate(
                1123, true, 1, 1, 112311, "[0]", true,
                currentSheila, currentSheila.toString()), "current Sheila slot");

        List<?> alreadyMigrated = Arrays.asList(20, 1, false, null, null, "backup");
        assertSame(alreadyMigrated, LegacyHeroSkillMigration.migrate(
                1122, false, 1, 20, 0, null, false,
                alreadyMigrated, alreadyMigrated.toString()), "migration is idempotent");
    }

    private static void assertSame(Object expected, Object actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + ": expected same instance");
        }
    }

    private static void assertNotSame(Object expected, Object actual, String message) {
        if (expected == actual) {
            throw new AssertionError(message + ": expected a new instance");
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (!expected.equals(actual)) {
            throw new AssertionError(message + ": expected=" + expected + ", actual=" + actual);
        }
    }
}
