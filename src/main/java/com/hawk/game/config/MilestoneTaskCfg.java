package com.hawk.game.config;

import java.util.List;
import java.util.Map;

import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;
import org.hawk.log.HawkLog;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.hawk.game.item.ItemInfo;
import com.hawk.game.item.mission.MissionCfgItem;
import com.hawk.game.service.mssion.MissionType;

/**
 * @author SJS
 * @description rookie milestone Task配置
 * @date 2025/9/25
 */
@HawkConfigManager.XmlResource(file = "xml/milestoneTask.xml")
public class MilestoneTaskCfg extends HawkConfigBase {
    @Id
    private final int id;
    private final int chapter;
    private final int taskType;
    private final String val1;
    private final int val2;
    private final String reward;


    // 里程碑任务  chapter-id-cfg
    private static Table<Integer, Integer, MilestoneTaskCfg> milestoneTaskCfgTable = HashBasedTable.create();

    public MilestoneTaskCfg() {
        this.id = 0;

        this.chapter = 0;

        this.taskType = 0;

        this.val1 = "";

        this.val2 = 0;

        this.reward = "";
    }

    public int getId() {
        return id;
    }

    public int getChapter() {
        return chapter;
    }

    public int getTaskType() {
        return taskType;
    }

    public String getVal1() {
        return val1;
    }

    public int getVal2() {
        return val2;
    }


    @Override
    protected boolean assemble() {
        milestoneTaskCfgTable.put(chapter, id, this);
        return super.assemble();
    }

    @Override
    protected boolean checkValid() {
        if (MissionType.valueOf(taskType) == null) {
            HawkLog.errPrintln("MilestoneTaskCfg check error, type:{}", taskType);
            return false;
        }
        return true;
    }
    public MissionCfgItem getMissionCfgItem() {
        return new MissionCfgItem(id, taskType, val1, val2);
    }

    public List<ItemInfo> getRewardItem() {
        return ItemInfo.valueListOf(reward);
    }

    /**
     * 里程碑任务配置
     */
    public static MilestoneTaskCfg getMilestoneTaskCfg(int chapterId, int missionId) {
        return milestoneTaskCfgTable.get(chapterId, missionId);
    }

    /**
     * 里程碑任务配置
     */
    public static Map<Integer, MilestoneTaskCfg> getMilestoneTaskCfg(int chapterId) {
        return milestoneTaskCfgTable.row(chapterId);
    }
}
