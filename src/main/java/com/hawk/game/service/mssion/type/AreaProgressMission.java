package com.hawk.game.service.mssion.type;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hawk.game.item.mission.MissionCfgItem;
import com.hawk.game.item.mission.MissionEntityItem;
import com.hawk.game.player.Player;
import com.hawk.game.player.PlayerData;
import com.hawk.game.service.mssion.Mission;
import com.hawk.game.service.mssion.MissionEvent;
import com.hawk.game.service.mssion.MissionType;
import com.hawk.game.service.mssion.event.EventAreaProgress;

/**
 * @author SJS
 * @description 解锁地块进度
 * @date 2025/9/22
 */
@Mission(missionType = MissionType.UNLOCK_AREA_PROGRESS)
public class AreaProgressMission implements IMission {

    @Override
    public void initMission(PlayerData playerData, MissionEntityItem entityItem, MissionCfgItem cfg) {
        List<Integer> conditions = cfg.getIds();
        if (conditions.isEmpty()) {
            return;
        }
        // 已解锁
        Set<Integer> unlockedAreas = playerData.getPlayerBaseEntity().getUnlockedAreaSet();
        for (Integer unlockedArea : unlockedAreas) {
            if (conditions.contains(unlockedArea)) {
                entityItem.setValue(cfg.getValue());
            }
        }
        // 未解锁
        Map<Integer, Integer> unlockAreaProgressMap = playerData.getPlayerBaseEntity().getUnlockAreaProgressMap();
        for (Map.Entry<Integer, Integer> entry : unlockAreaProgressMap.entrySet()) {
            Integer areaId = entry.getKey();
            Integer progress = entry.getValue();
            if (conditions.contains(areaId)) {
                entityItem.setValue(progress);
            }
        }

        checkMissionFinish(entityItem, cfg);
    }

    @Override
    public <T extends MissionEvent> void refreshGeneralMission(Player player, T missionEvent) {

    }

    @Override
    public <T extends MissionEvent> void refreshMission(PlayerData playerData, T missionEvent, MissionEntityItem entityItem, MissionCfgItem cfg) {
        EventAreaProgress event = (EventAreaProgress) missionEvent;
        int areaId = event.getAreaId();
        int progress = event.getProgress();
        List<Integer> conditions = cfg.getIds();
        if (conditions.isEmpty()) {
            return;
        }
        if (!conditions.contains(areaId)) {
            return;
        }

        entityItem.setValue(progress);
        checkMissionFinish(entityItem, cfg);
    }
}
