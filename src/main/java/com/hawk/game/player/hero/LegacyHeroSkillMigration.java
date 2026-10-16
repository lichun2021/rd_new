package com.hawk.game.player.hero;

import java.util.ArrayList;
import java.util.List;

/** Compatibility for 1123's retired, automatically installed staff-officer slots. */
public final class LegacyHeroSkillMigration {
    private LegacyHeroSkillMigration() {
    }

    /**
     * Returns the original list unless this is the exact retired slot layout.
     * The sixth element retains the complete old slot, including skill experience.
     * It is storage-only and must survive subsequent SkillSlot serialization.
     */
    public static List<?> migrate(int heroId, boolean staffOfficer, int targetIndex,
            int targetUnlockLevel, int targetSkillId, boolean oldConfigExists,
            List<?> slot, String original) {
        if (heroId != 1123 || staffOfficer || oldConfigExists || targetSkillId != 0
                || targetUnlockLevel <= 0 || slot.size() != 5
                || !Boolean.TRUE.equals(slot.get(2))) {
            return slot;
        }
        if (!(slot.get(1) instanceof Number) || !(slot.get(3) instanceof Number)) {
            return slot;
        }
        int index = ((Number) slot.get(1)).intValue();
        int skillId = ((Number) slot.get(3)).intValue();
        if (index != targetIndex || index < 1 || index > 4 || skillId != 112310 + index) {
            return slot;
        }
        List<Object> migrated = new ArrayList<Object>(6);
        migrated.add(targetUnlockLevel);
        migrated.add(index);
        migrated.add(false);
        migrated.add(null);
        migrated.add(null);
        migrated.add(original);
        return migrated;
    }
}
