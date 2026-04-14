package com.hawk.game.module.findStoreHouse.worldmarch;

import com.hawk.activity.helper.PlayerPushHelper;
import com.hawk.activity.type.impl.findStoreHouse.cfg.FindStoreHouseActivityKVCfg;
import com.hawk.activity.type.impl.findStoreHouse.cfg.FindStoreHouseBuildCfg;
import com.hawk.activity.type.impl.findStoreHouse.cfg.FindStoreHouseBuildPoolCfg;
import com.hawk.game.config.WorldMarchConstProperty;
import com.hawk.game.item.AwardItems;
import com.hawk.game.module.findStoreHouse.FindStoreHouseService;
import com.hawk.game.player.Player;
import com.hawk.game.protocol.Const;
import com.hawk.game.protocol.HP;
import com.hawk.game.protocol.MailConst;
import com.hawk.game.protocol.World.WorldMarchType;
import com.hawk.game.service.mail.MailParames;
import com.hawk.game.service.mail.SystemMailService;
import com.hawk.game.util.GameUtil;
import com.hawk.game.world.WorldMarch;
import com.hawk.game.world.WorldMarchService;
import com.hawk.game.world.march.PlayerMarch;
import com.hawk.game.world.march.submarch.BasedMarch;
import org.hawk.config.HawkConfigManager;
import org.hawk.net.protocol.HawkProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 秘藏寻踪
 *
 * @author zhy
 */
public class FindStoreHouseTreasureMarch extends PlayerMarch implements BasedMarch {

    /**
     * 日志
     */
    public static Logger logger = LoggerFactory.getLogger("Server");

    public FindStoreHouseTreasureMarch(WorldMarch marchEntity) {
        super(marchEntity);
//        getMarchEntity().setExtraSpyMarch(true);
    }

    /**
     * 获取行军类型
     */
    @Override
    public WorldMarchType getMarchType() {
        return WorldMarchType.TREASURE_386_SPY;
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
            double param2 = cfg.getFindAwardBuildSpeed();
            speed = 1.0d;
            return Math.pow((distance), param1) * param2 * factor / speed;
        }
        return super.getPartMarchTime(distance, speed, isSlowDownPart);
    }

    @Override
    public long getMarchNeedTime() {
        return super.getMarchNeedTime();
    }

    @Override
    public void moveCityProcess(long currentTime) {
        WorldMarchService.getInstance().accountMarchBeforeRemove(this);
        if (this.isReturnBackMarch()) {
            return;
        }
        if (!this.isMarchState()) {
            return;
        }
        onMarchGenTreasure();
    }

    public void onMarchGenTreasure() {
        // 行军
        WorldMarch march = getMarchEntity();
        // 行军返回
        WorldMarchService.getInstance().onPlayerNoneAction(this, march.getReachTime());
        // 目标宝库
        int treasurePoolId = Integer.parseInt(march.getTargetId());
        FindStoreHouseBuildPoolCfg poolCfg = HawkConfigManager.getInstance().getConfigByKey(FindStoreHouseBuildPoolCfg.class, treasurePoolId);
        if (poolCfg == null) {
            logger.info("onMarchGenTreasure FindStoreHouseBuildPoolCfg failed playerId:{},treasurePoolId:{}", this.getPlayerId(), treasurePoolId);
            return;
        }
        int buildId = poolCfg.getCreateTreasureBuildId();
        FindStoreHouseBuildCfg buildCfg = HawkConfigManager.getInstance().getConfigByKey(FindStoreHouseBuildCfg.class, buildId);
        if (buildCfg == null) {
            logger.info("onMarchGenTreasure FindStoreHouseBuildCfg failed playerId:{},treasurePoolId:{},buildId:{}", this.getPlayerId(), treasurePoolId, buildId);
            return;
        }
        AwardItems award = AwardItems.valueOf();
        award.addAward(buildCfg.getAward());
        SystemMailService.getInstance().sendMail(MailParames.newBuilder()
                .setMailId(MailConst.MailId.FSH_TREASURE_REWARD)
                .setPlayerId(this.getPlayerId())
                .setRewards(award.getAwardItems())
                .setAwardStatus(Const.MailRewardStatus.NOT_GET)
                .build());
        int randomProbability = GameUtil.randomProbability();
        if (poolCfg.getCreateTreasureBuildWeight() < randomProbability) {
            logger.info("onMarchGenTreasure randomProbability playerId:{},weight:{},probability:{}", this.getPlayerId(), poolCfg.getCreateTreasureBuildWeight(), randomProbability);
            return;
        }
        int createCount = poolCfg.getRandomOnce();
        if (createCount <= 0) {
            logger.info("onMarchGenTreasure createCount failed playerId:{},", this.getPlayerId());
            return;
        }
        FindStoreHouseService.getInstance().touchCreateResource(getOrigionId(), getPlayerId(), createCount, poolCfg.getRandomTreasuryBuild());
        PlayerPushHelper.getInstance().pushToPlayer(getPlayerId(), HawkProtocol.valueOf(HP.code2.FIND_STORE_HOUSE_RED_POINT_S));
    }

    @Override
    public void onMarchReach(Player player) {
        onMarchGenTreasure();
    }
}