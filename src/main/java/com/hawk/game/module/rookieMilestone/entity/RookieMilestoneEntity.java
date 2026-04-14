package com.hawk.game.module.rookieMilestone.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hawk.annotation.IndexProp;
import org.hawk.db.HawkDBEntity;
import org.hawk.os.HawkOSOperator;

import com.hawk.game.item.mission.MissionEntityItem;
import com.hawk.game.protocol.RooKieMilestone;
import com.hawk.serialize.string.SerializeHelper;

/**
 * @author SJS
 * @description 新人里程碑实体
 * @date 2025/9/25
 */
@Entity
@Table(name = "rookie_milestone")
public class RookieMilestoneEntity extends HawkDBEntity {
    @Id
    @Column(name = "playerId", unique = true, nullable = false)
    @IndexProp(id = 1)
    private String playerId = "";

    // 当前章节id
    @Column(name = "currentChapterId", nullable = false)
    @IndexProp(id = 2)
    private int currentChapterId;

    // 任务列表 结构: type_id_count_state_cfgId
    @Column(name = "missions", nullable = false)
    @IndexProp(id = 3)
    private String missions = "";

    // 已完成的章节
    @Column(name = "completeChapters", nullable = false)
    @IndexProp(id = 4)
    private String completeChapters;

    @Column(name = "createTime", nullable = false)
    @IndexProp(id = 5)
    private long createTime = 0;

    @Column(name = "updateTime", nullable = false)
    @IndexProp(id = 6)
    private long updateTime;

    @Column(name = "invalid")
    @IndexProp(id = 7)
    private boolean invalid;

    @Transient
    private List<MissionEntityItem> missionItemList = new ArrayList<>();

    @Transient
    private Set<Integer> completeChapterSet = new HashSet<>();

    @Override
    public void beforeWrite() {
        this.missions = SerializeHelper.collectionToString(this.missionItemList, SerializeHelper.BETWEEN_ITEMS);
        this.completeChapters = SerializeHelper.collectionToString(this.completeChapterSet, SerializeHelper.ATTRIBUTE_SPLIT);
    }

    @Override
    public void afterRead() {
        this.missionItemList.clear();
        this.completeChapterSet.clear();
        this.completeChapterSet = SerializeHelper.stringToSet(Integer.class, this.completeChapters, SerializeHelper.ATTRIBUTE_SPLIT);

        this.missionItemList = new ArrayList<>();
        if (!HawkOSOperator.isEmptyString(missions)) {
            String[] missionArr = missions.split(SerializeHelper.BETWEEN_ITEMS);
            for (String missionStr : missionArr) {
                String[] mission = missionStr.split(SerializeHelper.ATTRIBUTE_SPLIT);
                MissionEntityItem missionItem = new MissionEntityItem(Integer.parseInt(mission[0]), Integer.parseInt(mission[1]), Integer.parseInt(mission[2]));
                this.missionItemList.add(missionItem);
            }
        }
    }

    /**
     * 设置任务状态
     */
    public void changeMissionState(int missionId, int state) {
        for (MissionEntityItem mission : getMissionItemList()) {
            if (mission.getCfgId() == missionId) {
                mission.setState(state);
            }
        }
        notifyUpdate();
    }

    /**
     * 转协议
     *
     * @return
     */
    public RooKieMilestone.MilestoneMissionPage.Builder toBuilder() {
        RooKieMilestone.MilestoneMissionPage.Builder builder = RooKieMilestone.MilestoneMissionPage.newBuilder();
        builder.setCurrentChapterId(currentChapterId);

        RooKieMilestone.MilestoneTaskData.Builder missionData = RooKieMilestone.MilestoneTaskData.newBuilder();
        for (MissionEntityItem missionItem : missionItemList) {
            missionData.setMissionId(missionItem.getCfgId());
            missionData.setState(missionItem.getState());
            missionData.setNum((int) Math.min(Integer.MAX_VALUE - 1, missionItem.getValue()));
            builder.addData(missionData);
        }
        return builder;
    }

    /**
     * 获取指定配置的任务item
     */
    public MissionEntityItem getMilestoneMissionItem(int cfgId) {
        for (MissionEntityItem missionItem : missionItemList) {
            if (missionItem.getCfgId() == cfgId) {
                return missionItem;
            }
        }
        return null;
    }


    /**
     * 设置指定配置的任务item
     */
    public void setMilestoneMissionItem(MissionEntityItem item) {
        for (MissionEntityItem missionItem : missionItemList) {
            if (missionItem.getCfgId() == item.getCfgId()) {
                missionItem = item;
                break;
            }
        }
    }

    @Override
    public String getPrimaryKey() {
        return playerId;
    }

    @Override
    public void setPrimaryKey(String primaryKey) {
        throw new UnsupportedOperationException("rookie_milestone entity primaryKey is playerId");
    }

    public String getOwnerKey() {
        return playerId;
    }

    @Override
    public long getCreateTime() {
        return createTime;
    }

    @Override
    protected void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Override
    public long getUpdateTime() {
        return updateTime;
    }

    @Override
    protected void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean isInvalid() {
        return invalid;
    }

    @Override
    protected void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }


    public List<MissionEntityItem> getMissionItemList() {
        return missionItemList;
    }

    public void setMissionItemList(List<MissionEntityItem> missionItemList) {
        this.missionItemList = missionItemList;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getCurrentChapterId() {
        return currentChapterId;
    }

    public void setCurrentChapterId(int currentChapterId) {
        this.currentChapterId = currentChapterId;
    }


    public Set<Integer> getCompleteChapterSet() {
        return completeChapterSet;
    }

    /**
     * 完成章节
     */
    public void setCompleteChapter(int nextChapter) {
        completeChapterSet.add(nextChapter);
    }
}
