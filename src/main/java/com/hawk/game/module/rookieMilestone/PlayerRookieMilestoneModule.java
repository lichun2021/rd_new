package com.hawk.game.module.rookieMilestone;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hawk.annotation.MessageHandler;
import org.hawk.annotation.ProtocolHandler;
import org.hawk.config.HawkConfigManager;
import org.hawk.log.HawkLog;
import org.hawk.net.protocol.HawkProtocol;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkTime;

import com.hawk.game.config.BuildAreaCfg;
import com.hawk.game.config.MilestoneTaskCfg;
import com.hawk.game.entity.CustomDataEntity;
import com.hawk.game.item.mission.MissionEntityItem;
import com.hawk.game.module.rookieMilestone.entity.RookieMilestoneEntity;
import com.hawk.game.msg.MissionMsg;
import com.hawk.game.msg.SuperSoldierTriggeTaskMsg;
import com.hawk.game.player.Player;
import com.hawk.game.player.PlayerModule;
import com.hawk.game.protocol.HP;
import com.hawk.game.protocol.RooKieMilestone;
import com.hawk.game.protocol.SuperSoldier;
import com.hawk.game.service.mssion.MissionContext;
import com.hawk.game.service.mssion.MissionEvent;
import com.hawk.game.service.mssion.MissionManager;
import com.hawk.game.service.mssion.MissionType;
import com.hawk.game.service.mssion.event.EventAreaProgress;
import com.hawk.game.service.mssion.type.IMission;
import com.hawk.game.util.GsConst;

/**
 * @author SJS
 * @description 新手里程碑
 * @date 2025/9/25
 */
public class PlayerRookieMilestoneModule extends PlayerModule {
    /**
     * 构造函数
     *
     * @param player
     */
    public PlayerRookieMilestoneModule(Player player) {
        super(player);
    }


    @Override
    protected boolean onPlayerLogin() {
        // 初始化里程碑任务
        RookieMilestoneEntity entity = player.getData().getRookieMilestoneEntity();
        if (entity.getCurrentChapterId() == 0) {
            RookieMilestoneService.getInstance().initMilestoneMission(player);
            HawkLog.logPrintln("checkMissionCfgUpdate, init story mission, playerId:{}", player.getId());
        }

        fixMilestoneTask();

        // 检测配置更新
        RookieMilestoneService.getInstance().checkMilestoneMissionCfgUpdate(player);

        RookieMilestoneService.getInstance().syncMilestoneMissionInfo(player);
        return true;
    }


    private void fixMilestoneTask() {
        RookieMilestoneEntity entity = player.getData().getRookieMilestoneEntity();
        try {
            // 老玩家是否标识为完成 未完成的标记为完成 时间小于 2025-10-16 06:00:00 章节小于13
            if (entity.getCurrentChapterId() > 0) {
                boolean isAutoFinish = player.getCreateTime() <= 1760565600000L && entity.getCurrentChapterId() <= 14;
                if (isAutoFinish) {
                    HawkLog.logPrintln("PlayerRookieMilestoneModule to old player, playerId: {}", player.getId());
                    // 章节任务数据
                    List<MissionEntityItem> missionEntityItems = entity.getMissionItemList();
                    boolean needUpdate = false;
                    for (MissionEntityItem missionEntityItem : missionEntityItems) {
                        if (missionEntityItem.getState() == GsConst.MissionState.STATE_NOT_FINISH) {
                            MilestoneTaskCfg cfg = HawkConfigManager.getInstance().getConfigByKey(MilestoneTaskCfg.class, missionEntityItem.getCfgId());
                            if (cfg == null) {
                                HawkLog.errPrintln("isAutoFinish  cfg is null, player{} chapter{} cfgId:{}", player.getId(), entity.getCurrentChapterId(), missionEntityItem.getCfgId());
                                continue;
                            }
                            missionEntityItem.setValue(cfg.getVal2());
                            missionEntityItem.setState(GsConst.MissionState.STATE_FINISH);
                            // 完成里程碑任务增加机甲解锁计数
                            MissionManager.getInstance().postSuperSoldierTaskMsg(player, new SuperSoldierTriggeTaskMsg(SuperSoldier.SupersoldierTaskType.COMPLETE_MILESTONE_MISSION, missionEntityItem.getCfgId()));
                            RookieMilestoneService.getInstance().logTaskFlow(player, cfg, missionEntityItem.getState());
                            needUpdate = true;
                        }
                    }
                    if (needUpdate) {
                        entity.setMissionItemList(missionEntityItems);
                    }
                }
            }
        } catch (Exception e) {
            HawkLog.errPrintln("PlayerRookieMilestoneModule onPlayerLogin error, playerId:{}", player.getId(), e);
        }

        try {
            String key = "rookieMilestoneTaskFixArea1104";
            CustomDataEntity customDataEntity = player.getData().getCustomDataEntity(key);
            if (customDataEntity == null) {
                //这里不调player.getData().createCustomDataEntity接口，是为了防止db容错的一个缺陷（数据落地失败但又触发了容错机制，导致内存跟db不一致）
                customDataEntity = new CustomDataEntity();
                customDataEntity.setPlayerId(player.getId());
                customDataEntity.setType(key);
                customDataEntity.setArg("");
                customDataEntity.setId(HawkOSOperator.randomUUID());
                customDataEntity.create(true);
                player.getData().getCustomDataEntities().add(customDataEntity);
            }
            // 已经处理过了，跳过
            if (customDataEntity.getValue() <= 0) {
                customDataEntity.setValue(HawkTime.getSeconds());
                HawkLog.logPrintln("rookieMilestoneTaskFixArea1104 login fix Area, playerId: {}", player.getId());
                Set<Integer> unlockedAreaSet = player.getPlayerBaseEntity().getUnlockedAreaSet();
                for (MissionEntityItem missionEntityItem : player.getData().getRookieMilestoneEntity().getMissionItemList()) {
                    if (missionEntityItem.getState() == GsConst.MissionState.STATE_NOT_FINISH) {
                        MilestoneTaskCfg cfg = HawkConfigManager.getInstance().getConfigByKey(MilestoneTaskCfg.class, missionEntityItem.getCfgId());
                        if (cfg == null) {
                            continue;
                        }
                        // 区块战斗任务类型
                        if (cfg.getTaskType() != 50) {
                            continue;
                        }
                        int areaId = Integer.parseInt(cfg.getVal1());
                        if (unlockedAreaSet.contains(areaId)) {
                            BuildAreaCfg config = HawkConfigManager.getInstance().getConfigByKey(BuildAreaCfg.class, areaId);
                            MissionManager.getInstance().postMsg(player, new EventAreaProgress(areaId, config.getBattleNpcList().size()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            HawkLog.errPrintln("PlayerRookieMilestoneModule onPlayerLogin rookieMilestoneTaskFixArea1104 error, playerId:{}", player.getId(), e);
        }


    }

    /**
     * 领取里程碑任务奖励
     */
    @ProtocolHandler(code = HP.code2.ROOKIE_MILESTONE_REWARD_C_VALUE)
    private boolean getMilestoneReward(HawkProtocol protocol) {
        RooKieMilestone.MilestoneMissionRewardReq req = protocol.parseProtocol(RooKieMilestone.MilestoneMissionRewardReq.getDefaultInstance());
        // 领取奖励
        return RookieMilestoneService.getInstance().receiveMilestoneMissionReward(player, req.getMissionId());
    }

    /**
     * 刷新里程碑任务
     */
    @MessageHandler
    private void onRefreshMilestoneMission(MissionMsg msg) {
        MissionEvent event = msg.getEvent();
        // 事件触发任务列表
        List<MissionType> touchMissions = event.touchMissions();
        if (touchMissions == null || touchMissions.isEmpty()) {
            return;
        }

        // 章节任务配置
        RookieMilestoneEntity entity = player.getData().getRookieMilestoneEntity();

        Map<Integer, MilestoneTaskCfg> cfgs = MilestoneTaskCfg.getMilestoneTaskCfg(entity.getCurrentChapterId());
        for (MilestoneTaskCfg cfg : cfgs.values()) {
            // 任务类型
            MissionType missionType = MissionType.valueOf(cfg.getTaskType());

            // 不触发此类型任务
            if (!touchMissions.contains(missionType)) {
                continue;
            }

            // 任务实体
            MissionEntityItem entityItem = entity.getMilestoneMissionItem(cfg.getId());
            if (entityItem == null) {
                continue;
            }

            if (entityItem.getState() != GsConst.MissionState.STATE_NOT_FINISH) {
                continue;
            }

            // 刷新任务
            IMission iMission = MissionContext.getInstance().getMissions(missionType);
            iMission.refreshMission(player.getData(), event, entityItem, cfg.getMissionCfgItem());

            // 设置任务状态(这里要设置一下，调用下entity的set方法，不然可能不会落地)
            entity.setMilestoneMissionItem(entityItem);

            if (entityItem.getState() == GsConst.MissionState.STATE_FINISH) {
                RookieMilestoneService.getInstance().logTaskFlow(player, cfg, entityItem.getState());
                // 完成里程碑任务增加机甲解锁计数
                MissionManager.getInstance().postSuperSoldierTaskMsg(player, new SuperSoldierTriggeTaskMsg(SuperSoldier.SupersoldierTaskType.COMPLETE_MILESTONE_MISSION, entityItem.getCfgId()));
            }

            // 同步
            RookieMilestoneService.getInstance().syncMilestoneMissionInfo(player);
        }
    }

}
