package com.hawk.game.module.rookieMilestone;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hawk.config.HawkConfigManager;
import org.hawk.log.HawkLog;
import org.hawk.net.protocol.HawkProtocol;
import org.hawk.os.HawkException;
import org.hawk.os.HawkTime;

import com.hawk.game.config.MilestoneCfg;
import com.hawk.game.config.MilestoneTaskCfg;
import com.hawk.game.entity.StatusDataEntity;
import com.hawk.game.item.AwardItems;
import com.hawk.game.item.ItemInfo;
import com.hawk.game.item.mission.MissionCfgItem;
import com.hawk.game.item.mission.MissionEntityItem;
import com.hawk.game.module.rookieMilestone.entity.RookieMilestoneEntity;
import com.hawk.game.msg.SuperSoldierTriggeTaskMsg;
import com.hawk.game.player.Player;
import com.hawk.game.protocol.HP;
import com.hawk.game.protocol.Reward;
import com.hawk.game.protocol.RooKieMilestone;
import com.hawk.game.protocol.Status;
import com.hawk.game.protocol.SuperSoldier;
import com.hawk.game.service.mssion.MissionContext;
import com.hawk.game.service.mssion.MissionManager;
import com.hawk.game.service.mssion.MissionType;
import com.hawk.game.service.mssion.type.IMission;
import com.hawk.game.util.GsConst;
import com.hawk.game.util.LogUtil;
import com.hawk.log.Action;
import com.hawk.log.LogConst;

/**
 * @author SJS
 * @description 新人里程碑
 * @date 2025/9/25
 */
public class RookieMilestoneService {

    private static RookieMilestoneService instance;

    public static RookieMilestoneService getInstance() {
        if (instance == null) {
            instance = new RookieMilestoneService();
        }
        return instance;
    }

    /**
     * 初始化
     */
    public boolean init() {
        return true;
    }

    public RookieMilestoneEntity initMilestoneMission(Player player) {
        try {
            RookieMilestoneEntity entity = player.getData().getRookieMilestoneEntity();
            if (entity.getCurrentChapterId() != 0) {
                return entity;
            }
            // 初始章节
            int initChapterId = 1;
            entity.setPlayerId(player.getId());
            entity.setCurrentChapterId(initChapterId);
            entity.setMissionItemList(generateChapterMission(player, initChapterId));
            // log
            LogUtil.logChapterMissionFlow(player, LogConst.TaskType.MILESTONE_MISSION, initChapterId, LogConst.ChapterMissionOperType.MISSION_REFRESH);
            return entity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成章节任务数据
     *
     * @param chapterId
     * @return
     */
    public List<MissionEntityItem> generateChapterMission(Player player, int chapterId) {
        List<MissionEntityItem> missions = new ArrayList<>();
        Map<Integer, MilestoneTaskCfg> chapterCfg = MilestoneTaskCfg.getMilestoneTaskCfg(chapterId);
        if (chapterCfg == null) {
            HawkLog.errPrintln("generateChapterMission chapterCfg is null, chapterId:{}", chapterId);
            return missions;
        }
        // 老玩家是否标识为完成 时间小于 2025-10-16 00:00:00 章节小于13
        boolean isAutoFinish = player.getCreateTime() <= 1760565600000L && chapterId <= 14;

        for (MilestoneTaskCfg cfg : chapterCfg.values()) {
            MissionCfgItem missionCfgItem = cfg.getMissionCfgItem();
            if (missionCfgItem == null) {
                HawkLog.errPrintln("generateChapterMission missionCfgItem is null, cfgId:{}", cfg.getId());
                continue;
            }
            MissionEntityItem missionEntityItem;
            if (isAutoFinish) {
                missionEntityItem = new MissionEntityItem(cfg.getId(), missionCfgItem.getValue(), GsConst.MissionState.STATE_FINISH);
            } else {
                missionEntityItem = new MissionEntityItem(cfg.getId(), 0, GsConst.MissionState.STATE_NOT_FINISH);
            }

            IMission iMission = MissionContext.getInstance().getMissions(missionCfgItem.getType());
            if (iMission == null) {
                HawkLog.errPrintln("generateChapterMission iMission is null, type:{}", missionCfgItem.getType());
                continue;
            }

            // 自动完成不需要初始化
            if (!isAutoFinish) {
                // 初始化任务数据
                iMission.initMission(player.getData(), missionEntityItem, missionCfgItem);
            }
            missions.add(missionEntityItem);
            // 记录里程碑任务日志
            logTaskFlow(player, cfg, missionEntityItem.getState());
            if (missionEntityItem.getState() == GsConst.MissionState.STATE_FINISH){
                // 完成里程碑任务增加机甲解锁计数
                MissionManager.getInstance().postSuperSoldierTaskMsg(player, new SuperSoldierTriggeTaskMsg(SuperSoldier.SupersoldierTaskType.COMPLETE_MILESTONE_MISSION, missionEntityItem.getCfgId()));
            }
        }
        return missions;
    }

    /**
     * 同步里程碑任务数据
     */
    public void syncMilestoneMissionInfo(Player player) {
        RookieMilestoneEntity entity = player.getData().getRookieMilestoneEntity();
        if (entity == null) {
            return;
        }
        RooKieMilestone.MilestoneMissionPage.Builder builder = entity.toBuilder();
        player.sendProtocol(HawkProtocol.valueOf(HP.code2.PUSH_ROOKIE_MILESTONE_INFO_S_VALUE, builder));
    }

    /**
     * 领取里程碑任务奖励
     */
    public boolean receiveMilestoneMissionReward(Player player, int missionId) {
        RookieMilestoneEntity entity = player.getData().getRookieMilestoneEntity();
        if (entity == null) {
            return false;
        }
        // 查找任务
        MissionEntityItem missionEntityItem = entity.getMilestoneMissionItem(missionId);
        if (missionEntityItem == null) {
            player.sendError(HP.code2.ROOKIE_MILESTONE_REWARD_S_VALUE, Status.SysError.PARAMS_INVALID_VALUE, 0);
            return false;
        }
        // 奖励不可领取
        if (!rewardCanReceive(entity, missionId)) {
            player.sendError(HP.code2.ROOKIE_MILESTONE_REWARD_S_VALUE, Status.Error.LEVEL_TAKE_AWARD_ALREADY_VALUE, 0);
            return false;
        }
        // 发奖
        receiveAndPushMissionReward(player, entity.getCurrentChapterId(), missionId);
        entity.changeMissionState(missionId, GsConst.MissionState.STATE_BONUS);
        MilestoneTaskCfg cfg = MilestoneTaskCfg.getMilestoneTaskCfg(entity.getCurrentChapterId(), missionId);
        // 领奖log
        logTaskFlow(player, cfg, GsConst.MissionState.STATE_BONUS);
        // 检测当前章节是否全领取
        chapterComplete(player, entity);
        // 同步msg
        syncMilestoneMissionInfo(player);
        syncMilestoneMissionAward(player, missionId, entity.getCurrentChapterId());
        return true;
    }

    /**
     * 发放章节奖励
     */
    private void distributeChapterReward(Player player, RookieMilestoneEntity entity) {
        MilestoneCfg milestoneCfg = HawkConfigManager.getInstance().getConfigByKey(MilestoneCfg.class, entity.getCurrentChapterId());
        if (milestoneCfg == null) {
            HawkLog.errPrintln("distributeChapterReward milestoneCfg is null, chapterId:{}", entity.getCurrentChapterId());
            return;
        }
        if (entity.getCompleteChapterSet().contains(entity.getCurrentChapterId())) {
            HawkLog.errPrintln("distributeChapterReward chapter already complete, chapterId:{}", entity.getCurrentChapterId());
            return;
        }
        // 完成当前章节
        entity.setCompleteChapter(entity.getCurrentChapterId());
        if (milestoneCfg.getBuff() >= 0) {
            // 解锁当前章节buff
            StatusDataEntity addStatusBuff = player.addStatusBuff(milestoneCfg.getBuff(), HawkTime.getMillisecond() + milestoneCfg.getBuffTime() * 1000L);
            if (addStatusBuff != null) {
                player.getPush().syncPlayerStatusInfo(false, addStatusBuff);
            }
        }
        if (!milestoneCfg.getRewardItem().isEmpty()) {
            // 发放章节奖励
            AwardItems awardItems = AwardItems.valueOf();
            awardItems.addItemInfos(milestoneCfg.getRewardItem()); // 任务奖励
            awardItems.rewardTakeAffectAndPush(player, Action.ROOKIE_MILESTONE_REWARD, true,
                    Reward.RewardOrginType.ROOKIE_MILESTONE_CHAPTER_REWARD); // 发奖
        }
    }

    /**
     * 刷新下一章
     */
    private void refreshNextChapter(Player player, RookieMilestoneEntity entity) {
        int currentChapterId = entity.getCurrentChapterId();
        // 切换下一章节
        int nextChapter = currentChapterId + 1;
        MilestoneCfg milestoneCfg = HawkConfigManager.getInstance().getConfigByKey(MilestoneCfg.class, nextChapter);
        if (milestoneCfg == null) {
            HawkLog.logPrintln("refreshNextChapter nextChapterCfg is null, nextChapter:{}", nextChapter);
            return;
        }
        Set<Integer> completeChapter = entity.getCompleteChapterSet();
        if (completeChapter.contains(nextChapter)) {
            HawkLog.errPrintln("refreshNextChapter chapter already complete, nextChapter:{}, completeChapter", nextChapter, completeChapter);
            return;
        }
        entity.setCurrentChapterId(nextChapter);
        entity.setMissionItemList(generateChapterMission(player, nextChapter));
        // log
        LogUtil.logChapterMissionFlow(player, LogConst.TaskType.MILESTONE_MISSION, nextChapter, LogConst.ChapterMissionOperType.MISSION_REFRESH);
    }

    /**
     * 检查是否领取完当前章所有任务奖励
     */
    private boolean checkAllChapterReward(Player player) {
        RookieMilestoneEntity entity = player.getData().getRookieMilestoneEntity();
        // 检查是否所有任务都完成
        for (MissionEntityItem mission : entity.getMissionItemList()) {
            if (mission.getState() != GsConst.MissionState.STATE_BONUS) {
                return false;
            }
        }
        return true;
    }

    /**
     * 里程碑任务奖励msg
     */
    private void syncMilestoneMissionAward(Player player, int missionId, int currentChapterId) {
        RooKieMilestone.MilestoneMissionRewardResp.Builder builder = RooKieMilestone.MilestoneMissionRewardResp.newBuilder();
        builder.setMissionId(missionId);
        player.sendProtocol(HawkProtocol.valueOf(HP.code2.ROOKIE_MILESTONE_REWARD_S, builder));
    }

    /**
     * 奖励是否可领取
     */
    private boolean rewardCanReceive(RookieMilestoneEntity entity, int missionCfgId) {
        MissionEntityItem mission = entity.getMilestoneMissionItem(missionCfgId);
        return mission != null && mission.getState() == GsConst.MissionState.STATE_FINISH;
    }

    /**
     * 发送任务奖励
     */
    public void receiveAndPushMissionReward(Player player, int chapterId, int missionId) {
        AwardItems awardItems = AwardItems.valueOf();
        awardItems.addItemInfos(getMissionReward(chapterId, missionId)); // 任务奖励
        awardItems.rewardTakeAffectAndPush(player, Action.ROOKIE_MILESTONE_REWARD, true,
                Reward.RewardOrginType.ROOKIE_MILESTONE_REWARD); // 发奖
    }


    /**
     * 获取任务奖励
     *
     * @param chapterId:章节id
     * @param missionId:任务id
     * @return
     */
    public List<ItemInfo> getMissionReward(int chapterId, int missionId) {
        MilestoneTaskCfg cfg = MilestoneTaskCfg.getMilestoneTaskCfg(chapterId, missionId);
        return cfg.getRewardItem();
    }


    /**
     * 检测任务配置更新
     */
    public void checkMilestoneMissionCfgUpdate(Player player) {
        RookieMilestoneEntity entity = player.getData().getRookieMilestoneEntity();
        if (entity.getCurrentChapterId() == 0) {
            initMilestoneMission(player);
            HawkLog.errPrintln("checkMilestoneMissionCfgUpdate, init story mission, playerId:{}", player.getId());
        }

        if (isTaskCfgUpdate(entity)) {
            HawkLog.logPrintln("refreshMilestoneMissionList ,playerId {} chapter:{}", player, entity.getCurrentChapterId());
            refreshMilestoneMissionList(player, entity);
        }
        // 新章节处理 todo

    }

    private void refreshMilestoneMissionList(Player player, RookieMilestoneEntity entity) {
        // 章节配置
        Map<Integer, MilestoneTaskCfg> milestoneTaskCfg = MilestoneTaskCfg.getMilestoneTaskCfg(entity.getCurrentChapterId());
        // 章节任务数据
        List<MissionEntityItem> missionEntityItems = entity.getMissionItemList();

        // 移除任务
        missionEntityItems.removeIf(next -> !milestoneTaskCfg.containsKey(next.getCfgId()));

        // 添加任务
        for (MilestoneTaskCfg cfg : milestoneTaskCfg.values()) {
            if (entity.getMilestoneMissionItem(cfg.getId()) != null && cfg.getChapter() == entity.getCurrentChapterId()) {
                continue;
            }

            MissionCfgItem missionCfgItem = cfg.getMissionCfgItem();
            MissionEntityItem missionEntityItem = new MissionEntityItem(cfg.getId(), 0, 0);

            // 初始化任务数据
            IMission iMission = MissionContext.getInstance().getMissions(missionCfgItem.getType());
            if (iMission == null) {
                HawkLog.errPrintln("refreshMilestoneMissionList, mission type not found, missionId:{}", cfg.getId());
                return;
            }
            iMission.initMission(player.getData(), missionEntityItem, missionCfgItem);

            missionEntityItems.add(missionEntityItem);
            logTaskFlow(player, cfg, missionEntityItem.getState());
        }
        entity.setMissionItemList(missionEntityItems);

        // 刷新章节完成状态
        chapterComplete(player, entity);
    }

    public void chapterComplete(Player player, RookieMilestoneEntity entity) {
        if (!checkAllChapterReward(player)) {
            return;
        }
        // 记录里程碑章节任务日志
        LogUtil.logChapterMissionFlow(player, LogConst.TaskType.MILESTONE_MISSION, entity.getCurrentChapterId(), LogConst.ChapterMissionOperType.COMPLETE_AWARD_TAKEN);
        // 发放章节奖励
        distributeChapterReward(player, entity);
        // 切换下一章节
        refreshNextChapter(player, entity);
    }

    /**
     * 检测任务配置是否需要更新
     * t:需要更新
     */
    private boolean isTaskCfgUpdate(RookieMilestoneEntity entity) {
        // 章节配置
        Map<Integer, MilestoneTaskCfg> milestoneTaskCfg = MilestoneTaskCfg.getMilestoneTaskCfg(entity.getCurrentChapterId());
        // 章节数据
        List<MissionEntityItem> missionItems = entity.getMissionItemList();

        // size不同，证明配置更新过
        if (missionItems.size() != milestoneTaskCfg.size()) {
            return true;
        }
        // 数据里存在配置没有的任务，证明配置更新过
        for (MissionEntityItem mission : missionItems) {
            if (milestoneTaskCfg.get(mission.getCfgId()) == null) {
                return true;
            }
        }

        return false;
    }

    /**
     * 记录里程碑任务流水日志
     */
    public void logTaskFlow(Player player, MilestoneTaskCfg cfg, int state) {
        try {
            int type = cfg.getTaskType();
            MissionType missionType = MissionType.valueOf(type);
            if (missionType != null) {
                type = missionType.logMissionTypeVal();
            }

            LogUtil.logTaskFlow(player, LogConst.TaskType.MILESTONE_MISSION, type, cfg.getId(), state, cfg.getChapter());
        } catch (Exception e) {
            HawkException.catchException(e);
        }
    }
}
