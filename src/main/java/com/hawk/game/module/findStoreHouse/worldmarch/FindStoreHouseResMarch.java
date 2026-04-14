package com.hawk.game.module.findStoreHouse.worldmarch;

import com.hawk.activity.ActivityManager;
import com.hawk.activity.event.impl.FindStoreHouseOccupyEvent;
import com.hawk.activity.event.impl.VitCostEvent;
import com.hawk.activity.type.impl.findStoreHouse.cfg.FindStoreHouseActivityKVCfg;
import com.hawk.activity.type.impl.findStoreHouse.cfg.FindStoreHouseBuildCfg;
import com.hawk.game.battle.BattleOutcome;
import com.hawk.game.battle.BattleService;
import com.hawk.game.battle.battleIncome.impl.PvpBattleIncome;
import com.hawk.game.battle.effect.BattleConst;
import com.hawk.game.config.WorldMarchConstProperty;
import com.hawk.game.global.GlobalData;
import com.hawk.game.invoker.MarchVitReturnBackMsgInvoker;
import com.hawk.game.item.AwardItems;
import com.hawk.game.march.ArmyInfo;
import com.hawk.game.module.findStoreHouse.FindStoreHouseService;
import com.hawk.game.msg.PlayerVitCostMsg;
import com.hawk.game.player.Player;
import com.hawk.game.protocol.Const;
import com.hawk.game.protocol.GuildWar;
import com.hawk.game.protocol.MailConst;
import com.hawk.game.protocol.World;
import com.hawk.game.protocol.World.WorldMarchType;
import com.hawk.game.service.GuildService;
import com.hawk.game.service.mail.FightMailService;
import com.hawk.game.service.mail.MailParames;
import com.hawk.game.service.mail.SystemMailService;
import com.hawk.game.util.GameUtil;
import com.hawk.game.util.WorldUtil;
import com.hawk.game.world.WorldMarch;
import com.hawk.game.world.WorldMarchService;
import com.hawk.game.world.WorldPoint;
import com.hawk.game.world.march.IWorldMarch;
import com.hawk.game.world.march.PlayerMarch;
import com.hawk.game.world.march.submarch.BasedMarch;
import com.hawk.game.world.march.submarch.IPassiveAlarmTriggerMarch;
import com.hawk.game.world.march.submarch.IReportPushMarch;
import com.hawk.game.world.service.WorldPointService;
import com.hawk.gamelib.GameConst;
import com.hawk.log.Action;
import org.hawk.app.HawkApp;
import org.hawk.config.HawkConfigManager;
import org.hawk.os.HawkException;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 秘藏寻踪
 *
 * @author zhy
 */
public class FindStoreHouseResMarch extends PlayerMarch implements BasedMarch, IReportPushMarch, IPassiveAlarmTriggerMarch {

    /**
     * 日志
     */
    public static Logger logger = LoggerFactory.getLogger("Server");

    public FindStoreHouseResMarch(WorldMarch marchEntity) {
        super(marchEntity);
    }

    /**
     * 获取行军类型
     */
    @Override
    public WorldMarchType getMarchType() {
        return WorldMarchType.TREASURE_386_RES;
    }

    @Override
    public void onMarchStart() {
        this.pushAttackReport();
        this.pullAttackReport();
    }

    @Override
    protected double getPartMarchTime(double distance, double speed, boolean isSlowDownPart) {
        FindStoreHouseActivityKVCfg cfg = HawkConfigManager.getInstance().getKVInstance(FindStoreHouseActivityKVCfg.class);
        if (cfg != null) {
            double factor = 1;
            if (isSlowDownPart) {
                factor = WorldMarchConstProperty.getInstance().getWorldMarchCoreRangeTime();
            }
            // 行军距离修正参数
            double param1 = WorldMarchConstProperty.getInstance().getDistanceAdjustParam();
            // 部队行军类型行军时间调整参数
            double param2 = cfg.getFindPylonBuildSpeed();
            return Math.pow((distance), param1) * param2 * factor / speed;
        }
        return super.getPartMarchTime(distance, speed, isSlowDownPart);
    }


    @Override
    public long getMarchNeedTime() {
        return super.getMarchNeedTime();
    }


    @Override
    public void onMarchReach(Player player) {
        // 删除行军报告
        removeAttackReport();
        pullAttackReport();
        this.getMarchEntity().setEndTime(Long.MAX_VALUE);

        WorldPoint point = WorldPointService.getInstance().getWorldPoint(this.getMarchEntity().getTerminalId());

        if (point == null || point.getPointType() != World.WorldPointType.TREASURE_386_VALUE) {
            WorldMarchService.getInstance().onPlayerNoneAction(this, HawkTime.getMillisecond());
            // 返还体力
            player.dealMsg(GameConst.MsgId.RETURN_VIT, new MarchVitReturnBackMsgInvoker(player, this));

            SystemMailService.getInstance().sendMail(MailParames.newBuilder()
                    .setPlayerId(player.getId())
                    .setMailId(MailConst.MailId.FSH_DISAPPEAR)
                    .build());

            return;
        }

        if (HawkOSOperator.isEmptyString(point.getPlayerId())) {
            emptyStatusReach(point, player);
        } else {
            collectStatusReach(point, player);
        }

        try {
            ActivityManager.getInstance().postEvent(new VitCostEvent(this.getPlayerId(), getMarchEntity().getVitCost()));
            HawkApp.getInstance().postMsg(getPlayer(), PlayerVitCostMsg.valueOf(getMarchEntity().getPlayerId(), getMarchEntity().getVitCost()));
        } catch (Exception e) {
            HawkException.catchException(e);
        }

    }

    /**
     * 采集状态行军到达
     */
    private void collectStatusReach(WorldPoint point, Player atkPlayer) {
        if (!HawkOSOperator.isEmptyString(atkPlayer.getId())
                && !HawkOSOperator.isEmptyString(point.getPlayerId())
                && GuildService.getInstance().isInTheSameGuild(atkPlayer.getId(), point.getPlayerId())) {
            logger.info("treasure386Reach march, collectStatusReach, guild changed, marchId:{}, guildId:{}", this.getMarchId(), atkPlayer.getId());

            WorldMarchService.getInstance().onPlayerNoneAction(this, HawkTime.getMillisecond());

            // 返还体力
            atkPlayer.dealMsg(GameConst.MsgId.RETURN_VIT, new MarchVitReturnBackMsgInvoker(atkPlayer, this));
            return;
        }

        if (point.getPlayerId().equals(atkPlayer.getId())) {
            WorldMarchService.getInstance().onPlayerNoneAction(this, HawkTime.getMillisecond());
            atkPlayer.dealMsg(GameConst.MsgId.RETURN_VIT, new MarchVitReturnBackMsgInvoker(atkPlayer, this));
            return;
        }

        /********************** 战斗数据组装及战斗 ***************************/
        // 进攻方玩家
        List<Player> atkPlayers = new ArrayList<>();
        atkPlayers.add(atkPlayer);

        // 防守方玩家
        List<Player> defPlayers = new ArrayList<>();
        Player defPlayer = GlobalData.getInstance().makesurePlayer(point.getPlayerId());
        defPlayers.add(defPlayer);

        // 进攻方行军
        List<IWorldMarch> atkMarchs = new ArrayList<>();
        atkMarchs.add(this);

        // 防守方行军
        List<IWorldMarch> defMarchs = new ArrayList<>();
        IWorldMarch defMarch = WorldMarchService.getInstance().getMarch(point.getMarchId());
        defMarchs.add(defMarch);

        // 战斗
        PvpBattleIncome battleIncome = BattleService.getInstance().initPVPBattleData(BattleConst.BattleType.ATTACK_TREASURE_386_RES, point.getId(), atkPlayers, defPlayers,
                atkMarchs, defMarchs, null);
        BattleOutcome battleOutcome = BattleService.getInstance().doBattle(battleIncome);
        // 战斗胜利
        final boolean isAtkWin = battleOutcome.isAtkWin();
        // 发送战斗邮件
        FightMailService.getInstance().sendFightMail(point.getPointType(), battleIncome, battleOutcome, null);
        BattleService.getInstance().dealWithPvpBattleEvent(battleIncome, battleOutcome, isMassMarch(), this.getMarchType());

        // 双方战后剩余部队
        Map<String, List<ArmyInfo>> atkArmyLeftMap = battleOutcome.getAftArmyMapAtk();
        Map<String, List<ArmyInfo>> defArmyLeftMap = battleOutcome.getAftArmyMapDef();
        List<ArmyInfo> atkArmyLeft = atkArmyLeftMap.get(atkPlayer.getId());
        List<ArmyInfo> defArmyLeft = defArmyLeftMap.get(defPlayer.getId());
        // 发送战斗结果，用于前端播放动画
        WorldMarchService.getInstance().sendBattleResultInfo(this, isAtkWin, atkArmyLeft, defArmyLeft, isAtkWin);

        // 处理任务、统计
        sendMsgUpdateDefPlayerAfterWar(defPlayer, battleOutcome, null);
        sendMsgUpdateAtkPlayerAfterWar(isAtkWin, atkArmyLeft, defPlayer.getCityLevel(), atkPlayer, battleOutcome);
        /********************** 战斗数据组装及战斗 ***************************/

        if (!isAtkWin) {
            logger.info("treasure386Reach march, collectStatusReach, attack failed, marchId:{}", this.getMarchId());
            WorldMarchService.getInstance().onMarchReturn(this, atkArmyLeft, point.getId());
            // 刷新防御方的兵力信息
            WorldMarchService.getInstance().resetMarchArmys(defMarch, defArmyLeft);
        } else {
            logger.info("treasure386Reach march, collectStatusReach, attack win, marchId:{}", this.getMarchId());

            // 防守行军奖励计算级返回
            defMarch.getMarchEntity().setResEndTime(0);
            WorldMarchService.getInstance().onMarchReturn(defMarch, defArmyLeft, 0);

            // 世界点处理
            point.initPlayerInfo(atkPlayer.getData());
            point.setMarchId(this.getMarchId());
            notifyPointUpdate(point);

            // 行军处理
            onMarchStop(World.WorldMarchStatus.MARCH_STATUS_MARCH_QUARTERED_VALUE, atkArmyLeft, point);
        }
        // 刷新战力
        refreshPowerAfterWar(atkPlayers, defPlayers);
    }

    /**
     * 据点空状态行军到达
     */
    private void emptyStatusReach(WorldPoint point, Player player) {
        logger.info("treasure386Reach march, emptyStatusReach, marchId:{}", this.getMarchId());

        // 世界点处理
        point.initPlayerInfo(player.getData());
        point.setMarchId(this.getMarchId());
        notifyPointUpdate(point);

        // 行军处理
        onMarchStop(World.WorldMarchStatus.MARCH_STATUS_MARCH_QUARTERED_VALUE, this.getMarchEntity().getArmys(), point);
    }

    private void notifyPointUpdate(WorldPoint point) {
        // 点行军数据刷新
        Collection<IWorldMarch> worldPointMarchs = WorldMarchService.getInstance().getWorldPointMarch(point.getX(), point.getY());
        for (IWorldMarch march : worldPointMarchs) {
            if (march.isReturnBackMarch() || march.getMarchId().equals(this.getMarchId())) {
                continue;
            }
            march.updateMarch();
            WorldMarchService.getInstance().addGuildMarch(march);
        }

        // 点刷新
        WorldPointService.getInstance().notifyPointUpdate(point.getX(), point.getY());
    }

    @Override
    public void onMarchReturn() {
        // 删除行军报告
        removeAttackReport();
        // 处发其他行军重推报告
        this.pullAttackReport();
    }

    @Override
    public void pullAttackReport() {
        // 处理复仇
        // 不是同联盟且不是回程的行军，才会处理，
        for (IWorldMarch targetMarch : alarmPointEnemyMarches()) {
            if (targetMarch instanceof IReportPushMarch) {
                ((IReportPushMarch) targetMarch).pushAttackReport();
            }
        }
    }

    @Override
    public void pullAttackReport(String playerId) {
        // 不是同联盟且不是回程的行军，才会处理
        for (IWorldMarch targetMarch : alarmPointEnemyMarches()) {
            if (targetMarch instanceof IReportPushMarch) {
                ((IReportPushMarch) targetMarch).pushAttackReport(playerId);
            }
        }
    }

    @Override
    public void remove() {
        super.remove();
        // 删除行军报告
        removeAttackReport();
    }

    @Override
    public void doQuitGuild(String guildId) {
        // 移除自己行军的联盟战争显示
        WorldMarchService.getInstance().rmGuildMarch(this.getMarchId());

        if (!this.isReachAndStopMarch()) {
            return;
        }

        // 删除向驻扎点行军的目标方联盟战争显示
        int pos[] = GameUtil.splitFromAndTo(this.getMarchEntity().getTerminalId());
        Collection<IWorldMarch> worldPointMarchs = WorldMarchService.getInstance().getWorldPointMarch(pos[0], pos[1]);
        for (IWorldMarch worldPointMarch : worldPointMarchs) {
            // 更新行军信息(更新行军线颜色)
            worldPointMarch.updateMarch();
            // 删除联盟战争显示
            WorldMarchService.getInstance().rmGuildMarch(worldPointMarch.getMarchId(), guildId);
        }
    }

    @Override
    public Set<String> attackReportRecipients() {
        Set<String> result = alarmPointEnemyMarches().stream()
                .filter(march -> march.getMarchEntity().getMarchStatus() == World.WorldMarchStatus.MARCH_STATUS_MARCH_QUARTERED_VALUE)
                .map(IWorldMarch::getPlayerId)
                .filter(tid -> !Objects.equals(tid, this.getMarchEntity().getPlayerId()))
                .collect(Collectors.toSet());
        return result;
    }

    /**
     * 行军召回
     */
    @Override
    public void onMarchCallback(long callbackTime, WorldPoint worldPoint) {
        WorldMarch march = this.getMarchEntity();

        if (isReturnBackMarch()) {
            return;
        }

        if (this.isMarchState()) {
            WorldMarchService.getInstance().onMarchReturn(this, march.getArmys(), 0);
            return;
        }

        // 据点
        int pointId = this.getMarchEntity().getTerminalId();
        WorldPoint point = WorldPointService.getInstance().getWorldPoint(pointId);

        // 行军返回
        march.setResEndTime(0);
        WorldMarchService.getInstance().onMarchReturn(this, march.getArmys(), 0);

        point.setPlayerId("");
        point.setPlayerName("");
        point.setPlayerIcon(0);
        point.setMarchId("");

        // 点行军数据刷新
        Collection<IWorldMarch> worldPointMarchs = WorldMarchService.getInstance().getWorldPointMarch(point.getX(), point.getY());
        for (IWorldMarch pointMarch : worldPointMarchs) {
            if (pointMarch.isReturnBackMarch() || pointMarch.getMarchId().equals(this.getMarchId())) {
                continue;
            }
            // 更新行军信息(更新行军线颜色)
            pointMarch.updateMarch();
            // 删除联盟战争显示
            WorldMarchService.getInstance().rmGuildMarch(pointMarch.getMarchId());
        }
        // 通知场景点数据更新
        WorldPointService.getInstance().getWorldScene().update(point.getAoiObjId());
    }

    @Override
    public void moveCityProcess(long currentTime) {

        WorldMarchService.getInstance().accountMarchBeforeRemove(this);
        if (this.isReturnBackMarch()) {
            return;
        }

        // 返还体力
        Player player = this.getPlayer();
        player.dealMsg(GameConst.MsgId.RETURN_VIT, new MarchVitReturnBackMsgInvoker(player, this));

        if (this.isMarchState()) {
            WorldMarchService.getInstance().onMarchReturn(this, getMarchEntity().getArmys(), 0);
            return;
        }

        WorldMarch march = this.getMarchEntity();


        // 据点
        int pointId = this.getMarchEntity().getTerminalId();
        WorldPoint point = WorldPointService.getInstance().getWorldPoint(pointId);

        // 行军返回
        march.setResEndTime(0);
        point.setPlayerId("");
        point.setPlayerName("");
        point.setPlayerIcon(0);
        point.setMarchId("");

        // 点行军数据刷新
        Collection<IWorldMarch> worldPointMarchs = WorldMarchService.getInstance().getWorldPointMarch(point.getX(), point.getY());
        for (IWorldMarch pointMarch : worldPointMarchs) {
            if (pointMarch.isReturnBackMarch() || pointMarch.getMarchId().equals(this.getMarchId())) {
                continue;
            }
            // 更新行军信息(更新行军线颜色)
            pointMarch.updateMarch();
            // 删除联盟战争显示
            WorldMarchService.getInstance().rmGuildMarch(pointMarch.getMarchId());
        }
        // 通知场景点数据更新
        WorldPointService.getInstance().getWorldScene().update(point.getAoiObjId());
    }

    @Override
    public void detailMarchStop(WorldPoint point) {
        long currentTime = HawkTime.getMillisecond();
        this.getMarchEntity().setResStartTime(currentTime);
        FindStoreHousePoint findStoreHousePoint = (FindStoreHousePoint) point;
        FindStoreHouseBuildCfg cfg = HawkConfigManager.getInstance().getConfigByKey(FindStoreHouseBuildCfg.class, findStoreHousePoint.getBuildId());
        long holdTime = cfg == null ? 300 : cfg.getHoldTime();
        this.getMarchEntity().setResEndTime(currentTime + holdTime * 1000L);
        logger.info("treasure386Res march, detailMarchStop, resStartTime:{}, resEndTime:{}", this.getMarchEntity().getResStartTime(), this.getMarchEntity().getResEndTime());
    }

    @Override
    public boolean isNeedCalcTickMarch() {
        return true;
    }

    @Override
    public boolean marchHeartBeats(long currTime) {
        if (this.getMarchEntity().getMarchStatus() != World.WorldMarchStatus.MARCH_STATUS_MARCH_QUARTERED_VALUE) {
            return false;
        }

        if (this.getMarchEntity().getResEndTime() <= 0) {
            return false;
        }
        if (this.getMarchEntity().getResEndTime() > HawkTime.getMillisecond()) {
            return false;
        }
        // 据点
        int pointId = this.getMarchEntity().getTerminalId();
        WorldPoint point = WorldPointService.getInstance().getWorldPoint(pointId);
        FindStoreHousePoint findStoreHousePoint = (FindStoreHousePoint) point;
        FindStoreHouseBuildCfg cfg;
        if (point == null) {
            cfg = HawkConfigManager.getInstance().getConfigByIndex(FindStoreHouseBuildCfg.class, 0);
        } else {
            cfg = HawkConfigManager.getInstance().getConfigByKey(FindStoreHouseBuildCfg.class, findStoreHousePoint.getBuildId());
        }
        if (cfg == null) {
            return false;
        }
        // 填充奖励
        AwardItems award = AwardItems.valueOf();
        award.addAward(cfg.getAward());
        this.getMarchEntity().setAwardItems(award);
        this.getMarchEntity().setResEndTime(HawkTime.getMillisecond());
        ActivityManager.getInstance().postEvent(new FindStoreHouseOccupyEvent(this.getPlayer().getId(), this.getTerminalId(), cfg.getBuildId(), this.getMarchEntity().getResStartTime()));
        WorldMarchService.getInstance().onMarchReturn(this, this.getMarchEntity().getArmys(), 0);
        FindStoreHouseService.getInstance().notifyResRemove(pointId, this.getPlayer().getId(), this.getMarchId());
        return true;
    }

    @Override
    public void onWorldMarchReturn(Player player) {
        doAwardCalc(player);
    }

    @Override
    public boolean beforeImmediatelyRemoveMarchProcess(Player player) {
        doAwardCalc(player);
        return true;
    }

    /**
     * 获取被动方联盟战争界面信息
     */
    @Override
    public GuildWar.GuildWarTeamInfo.Builder getGuildWarPassivityInfo() {
        // 协议
        GuildWar.GuildWarTeamInfo.Builder builder = GuildWar.GuildWarTeamInfo.newBuilder();

        // 队长位置
        int terminalId = this.getMarchEntity().getTerminalId();
        int[] pos = GameUtil.splitXAndY(terminalId);

        builder.setPointType(World.WorldPointType.TREASURE_386);
        builder.setX(pos[0]);
        builder.setY(pos[1]);

        WorldPoint worldPoint = WorldPointService.getInstance().getWorldPoint(terminalId);

        if (worldPoint != null && !HawkOSOperator.isEmptyString(worldPoint.getMarchId()) && worldPoint.getPointType() == World.WorldPointType.TREASURE_386_VALUE) {
            // 队长
            String leaderId = worldPoint.getPlayerId();
            String marchId = worldPoint.getMarchId();
            Player leader = GlobalData.getInstance().makesurePlayer(leaderId);

            if (!HawkOSOperator.isEmptyString(leader.getGuildId())) {
                String guildTag = GuildService.getInstance().getGuildTag(leader.getGuildId());
                builder.setGuildTag(guildTag);
            }

            // 队长信息
            GuildWar.GuildWarSingleInfo.Builder leaderInfo = GuildWar.GuildWarSingleInfo.newBuilder();
            leaderInfo.setPlayerId(leader.getId());
            leaderInfo.setPlayerName(leader.getName());
            leaderInfo.setIconId(leader.getIcon());
            leaderInfo.setPfIcon(leader.getPfIcon());
            leaderInfo.setMarchStatus(World.WorldMarchStatus.MARCH_STATUS_MARCH_QUARTERED);

            IWorldMarch march = WorldMarchService.getInstance().getMarch(marchId);
            for (ArmyInfo army : march.getMarchEntity().getArmys()) {
                leaderInfo.addArmys(army.toArmySoldierPB(leader));
            }
            builder.setLeaderMarch(leaderInfo);
            builder.setReachArmyCount(WorldUtil.calcSoldierCnt(this.getMarchEntity().getArmys()));
        }
        return builder;
    }

    /**
     * 奖励结算
     *
     * @param player
     */
    public void doAwardCalc(Player player) {
        if (this.getMarchEntity().getResStartTime() <= 0 || this.getMarchEntity().getResEndTime() <= 0) {
            return;
        }

        if (WorldUtil.getFreeArmyCnt(this.getMarchEntity().getArmys()) <= 0) {
            return;
        }

        AwardItems awardItems = this.getMarchEntity().getAwardItems();
        boolean hasAwardItem = awardItems.hasAwardItem();
        MailConst.MailId mailId = MailConst.MailId.FSH_COLLECT_REWARD;
        // 发奖
        if (hasAwardItem) {
            SystemMailService.getInstance().sendMail(MailParames.newBuilder()
                    .setMailId(mailId)
                    .setPlayerId(this.getPlayerId())
                    .setRewards(awardItems.getAwardItems())
                    .setAwardStatus(Const.MailRewardStatus.NOT_GET)
                    .build());
        }
        this.getMarchEntity().setAwardItems(null);
    }
}