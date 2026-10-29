package com.hawk.game.player.hero;

import java.util.ArrayList;
import java.util.List;

/** Repairs skill slots persisted while heroes 1122 and 1123 used temporary identities. */
public final class LegacyHeroSkillMigration {
    private static final int[] LEXA_UNLOCK_LEVELS = {20, 60, 100, 120};

    private LegacyHeroSkillMigration() {
    }

    /**
     * Returns the original list unless it matches one of the retired layouts exactly.
     * The sixth element retains the complete old slot as a storage-only backup.
     */
    public static List<?> migrate(int heroId, boolean staffOfficer, int targetIndex,
            int targetUnlockLevel, int targetSkillId, String targetSkillSerialized,
            boolean oldConfigExists, List<?> slot, String original) {
        if (slot.size() != 5 || targetIndex < 1 || targetIndex > 4
                || !(slot.get(0) instanceof Number) || !(slot.get(1) instanceof Number)) {
            return slot;
        }

        int sourceUnlockLevel = ((Number) slot.get(0)).intValue();
        int sourceIndex = ((Number) slot.get(1)).intValue();
        if (sourceIndex != targetIndex) {
            return slot;
        }

        if (heroId == 1122 && !staffOfficer && targetSkillId == 0
                && targetUnlockLevel == LEXA_UNLOCK_LEVELS[targetIndex - 1]
                && sourceUnlockLevel == 1 && !oldConfigExists
                && Boolean.TRUE.equals(slot.get(2)) && slot.get(3) instanceof Number
                && ((Number) slot.get(3)).intValue() == 112210 + targetIndex) {
            return migratedSlot(targetUnlockLevel, targetIndex, false, 0, null, original);
        }

        if (heroId == 1123 && staffOfficer && targetUnlockLevel == 1
                && targetSkillId == 112310 + targetIndex && targetSkillSerialized != null
                && sourceUnlockLevel == LEXA_UNLOCK_LEVELS[targetIndex - 1]) {
            return migratedSlot(targetUnlockLevel, targetIndex, true, targetSkillId,
                    targetSkillSerialized, original);
        }

        return slot;
    }

    private static List<Object> migratedSlot(int unlockLevel, int index, boolean active,
            int skillId, String skillSerialized, String original) {
        List<Object> migrated = new ArrayList<Object>(6);
        migrated.add(unlockLevel);
        migrated.add(index);
        migrated.add(active);
        migrated.add(active ? skillId : null);
        migrated.add(active ? skillSerialized : null);
        migrated.add(original);
        return migrated;
    }
}
