package com.hawk.game.service.mssion.event;

import java.util.ArrayList;
import java.util.List;

import com.hawk.game.service.mssion.MissionEvent;
import com.hawk.game.service.mssion.MissionType;

/**
 * @author SJS
 * @description 地块解锁进度
 * @date 2025/9/22
 */
public class EventAreaProgress extends MissionEvent {

    private int areaId;
    private int Progress;

    public EventAreaProgress(int areaId,int Progress) {
        this.areaId = areaId;
        this.Progress = Progress;
    }

    public int getAreaId() {
        return areaId;
    }

    public int getProgress() {
        return Progress;
    }

    @Override
    public List<MissionType> touchMissions() {
        List<MissionType> touchMissionList = new ArrayList<>();
        touchMissionList.add(MissionType.UNLOCK_AREA_PROGRESS);
        return touchMissionList;
	}
    @Override
    public List<MissionType> touchGeneralMissions() {
        List<MissionType> touchMissionList = new ArrayList<>();
        touchMissionList.add(MissionType.UNLOCK_AREA_PROGRESS);
        return touchMissionList;
    }
}
