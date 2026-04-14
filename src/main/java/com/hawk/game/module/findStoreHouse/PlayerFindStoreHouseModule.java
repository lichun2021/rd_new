package com.hawk.game.module.findStoreHouse;

import com.hawk.activity.type.impl.findStoreHouse.FindStoreHouseActivity;
import com.hawk.activity.type.impl.findStoreHouse.cfg.FindStoreHouseActivityKVCfg;
import com.hawk.activity.type.impl.findStoreHouse.cfg.FindStoreHouseBuildPoolCfg;
import com.hawk.activity.type.impl.findStoreHouse.cfg.FindStoreHouseCreateCfg;
import com.hawk.game.config.WorldPylonCfg;
import com.hawk.game.item.ConsumeItems;
import com.hawk.game.march.ArmyInfo;
import com.hawk.game.module.PlayerMarchModule;
import com.hawk.game.module.findStoreHouse.worldmarch.FindStoreHousePoint;
import com.hawk.game.player.Player;
import com.hawk.game.player.PlayerModule;
import com.hawk.game.protocol.ActityFindStoreHouse.OccupyTreasureInfoRsp;
import com.hawk.game.protocol.Const;
import com.hawk.game.protocol.HP;
import com.hawk.game.protocol.Status;
import com.hawk.game.protocol.World;
import com.hawk.game.service.ArmyService;
import com.hawk.game.util.EffectParams;
import com.hawk.game.util.GameUtil;
import com.hawk.game.util.GsConst;
import com.hawk.game.world.WorldMarchService;
import com.hawk.game.world.WorldPoint;
import com.hawk.game.world.march.IWorldMarch;
import com.hawk.game.world.object.AreaObject;
import com.hawk.game.world.object.Point;
import com.hawk.game.world.service.WorldPlayerService;
import com.hawk.game.world.service.WorldPointService;
import com.hawk.log.Action;
import org.hawk.annotation.ProtocolHandler;
import org.hawk.config.HawkConfigManager;
import org.hawk.net.protocol.HawkProtocol;
import org.hawk.os.HawkTime;
import org.hawk.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 秘藏寻宝活动
 *
 * @author zhy
 */
public class PlayerFindStoreHouseModule extends PlayerModule {

    /**
     * 日志
     */
    public static Logger logger = LoggerFactory.getLogger("Server");


    public PlayerFindStoreHouseModule(Player player) {
        super(player);
    }

    @Override
    protected boolean onPlayerLogin() {
        if (player.isInDungeonMap()) {
            return true;
        }
        return true;
    }

    @Override
    public boolean onTick() {
        if (player.isInDungeonMap()) {
            return true;
        }
        return true;
    }

    /**
     * 386寻宝资源点行军
     */
    @ProtocolHandler(code = HP.code2.FIND_STORE_HOUSE_RES_MARCH_C_VALUE)
    private boolean onWorldTreasure386ResStart(HawkProtocol protocol) {
        FindStoreHouseActivity activity = FindStoreHouseService.getInstance().getActivityObject();
        if (activity == null) {
            sendError(protocol.getType(), Status.FindStoreHouseError.FIND_STORE_HOUSE_NOT_OPEN_RES_MARCH);
            return false;
        }
        Result<?> result = activity.onAttackMarch(player.getId());
        if (result.isFail()) {
            sendError(protocol.getType(), result.getStatus());
            return false;
        }
        World.WorldMarchReq req = protocol.parseProtocol(World.WorldMarchReq.getDefaultInstance());
        // 目标点
        WorldPoint point = WorldPointService.getInstance().getWorldPoint(req.getPosX(), req.getPosY());
        // 坐标点检查
        if (point == null || point.getPointType() != World.WorldPointType.TREASURE_386_VALUE) {
            sendError(protocol.getType(), Status.Error.WORLD_POINT_NOT_EXIST);
            return false;
        }
//        if(HawkOSOperator.isEmptyString(point.getPlayerId())){
//            // 向一个点只能出发一条同类型行军
//            Set<IWorldMarch> playerTypeMarchs = WorldMarchService.getInstance().getPlayerTypeMarchs(player.getId(), World.WorldMarchType.TREASURE_386_RES_VALUE);
//            for (IWorldMarch march : playerTypeMarchs) {
//                if (march.isMarchState() && march.getTerminalId() == point.getId()) {
//                    sendError(protocol.getType(), Status.Error.HAS_SAME_MARCH_THIS_POINT);
//                    return false;
//                }
//            }
//        }
        // 所需体力
        WorldPylonCfg cfg = HawkConfigManager.getInstance().getConfigByKey(WorldPylonCfg.class, point.getResourceId());
        if (cfg == null) {
            return false;
        }
        ConsumeItems consumeItems = ConsumeItems.valueOf(Const.PlayerAttr.VIT, cfg.getStrongpointCost());

        // 体力判断
        if (!consumeItems.checkConsume(player, protocol.getType())) {
            logger.error("gen strongpoint march failed, vit not enough, playerId:{}, vit:{}", player.getId(), player.getVit());
            return false;
        }

        // 带兵出征通用检查
        List<ArmyInfo> armyList = new ArrayList<>();
        PlayerMarchModule marchModule = player.getModule(GsConst.ModuleType.WORLD_MARCH_MODULE);
        if (!marchModule.checkMarchReq(req, protocol.getType(), armyList, point)) {
            logger.error("gen strongpoint march failed, check army req filed, playerId:{}, req:{}", player.getId(), req);
            return false;
        }

        // 扣兵
        if (!ArmyService.getInstance().checkArmyAndMarch(player, armyList, req.getHeroIdList(), req.getSuperSoldierId())) {
            logger.error("gen strongpoint march failed, deduct army failed, player:{}, req:{}", player.getId(), req);
            sendError(protocol.getType(), Status.Error.WORLD_MARCH_ARMY_COUNT);
            return false;
        }

        // 生成行军
        IWorldMarch startMarch = WorldMarchService.getInstance().startMarch(player, World.WorldMarchType.TREASURE_386_RES_VALUE, point.getId(), String.valueOf(point.getResourceId()), null, 0, new EffectParams(req, armyList));
        startMarch.getMarchEntity().setVitCost(cfg.getStrongpointCost());

        // 扣除体力
        consumeItems.consumeAndPush(player, Action.FIND_STORE_RES_VIT_COST);

        // 回复协议
        World.WorldMarchResp.Builder builder = World.WorldMarchResp.newBuilder();
        builder.setSuccess(true);
        protocol.response(HawkProtocol.valueOf(HP.code2.FIND_STORE_HOUSE_RES_MARCH_S, builder));

        return true;
    }

    /**
     * 386活动侦查行军
     *
     * @param protocol
     * @return
     */
    @ProtocolHandler(code = HP.code2.FIND_STORE_HOUSE_SEARCH_C_VALUE)
    private boolean onTreasure386SpyMarch(HawkProtocol protocol) {
        long time = HawkTime.getMillisecond();
        if (!FindStoreHouseActivityKVCfg.getInstance().isOpen(time)) {
            sendError(protocol.getType(), Status.FindStoreHouseError.FIND_STORE_HOUSE_SEARCH_TIME_LIMIT_VALUE);
            return false;
        }
        //根据主堡等级，vip等级
        FindStoreHouseCreateCfg createCfg = FindStoreHouseCreateCfg.getTreasurePool(player.getCityLevel(), player.getVipLevel());
        if (createCfg == null) {
            sendError(protocol.getType(), Status.SysError.CONFIG_ERROR);
            return false;
        }
        int[] pos = GameUtil.splitXAndY(WorldPlayerService.getInstance().getPlayerPos(player.getId()));
        int maxDis = createCfg.getCreateTreasurePointArea();
        List<Point> allPointList = WorldPointService.getInstance().getRhoAroundPointsFree(pos[0], pos[1], maxDis);
        List<IWorldMarch> currentMarch = WorldMarchService.getInstance().getPlayerMarch(player.getId(), World.WorldMarchType.TREASURE_386_SPY_VALUE);
        for (IWorldMarch march : currentMarch) {
            allPointList.removeIf(v -> v.getId() == march.getTerminalId());
        }
        Collections.shuffle(allPointList);
        Point worldPoint = null;
        for (Point point : allPointList) {
            AreaObject area = WorldPointService.getInstance().getArea(point.getAreaId());
            if (!point.canSeatByRadius(1)) {
                continue;
            }
            if (!WorldPointService.getInstance().tryOccupied(area, point, 1)) {
                continue;
            }
            if (WorldPointService.getInstance().getWorldPoint(point.getId()) != null) {
                continue;
            }

            if (WorldPointService.getInstance().isInCapitalArea(point.getId())) {
                continue;
            }
            worldPoint = point;
            break;
        }
        if (worldPoint == null) {
            sendError(protocol.getType(), Status.FindStoreHouseError.FIND_STORE_HOUSE_NO_FREE_POINT_VALUE);
            return false;
        }
        FindStoreHouseActivity activity = FindStoreHouseService.getInstance().getActivityObject();
        if (activity == null) {
            sendError(protocol.getType(), Status.Error.ACTIVITY_NOT_OPEN_VALUE);
            return false;
        }
        Result<?> result = activity.onSearch(player.getId());
        if (result.isFail()) {
            sendError(protocol.getType(), result.getStatus());
            return false;
        }
        // 目标点
        int terminalId = GameUtil.combineXAndY(worldPoint.getX(), worldPoint.getY());
        int treasurePoolId = createCfg.getRandomPool().randomObj();
        FindStoreHouseBuildPoolCfg buildPoolCfg = HawkConfigManager.getInstance().getConfigByKey(FindStoreHouseBuildPoolCfg.class, treasurePoolId);
        if (buildPoolCfg == null) {
            sendError(protocol.getType(), Status.SysError.CONFIG_ERROR);
            return false;
        }
        // 发起行军
        World.WorldTreasure386Resp.Builder builder = World.WorldTreasure386Resp.newBuilder();
        builder.setResouceId(buildPoolCfg.getCreateTreasureBuildId());
        builder.setTargetX(worldPoint.getX());
        builder.setTargetY(worldPoint.getY());
        player.sendProtocol(HawkProtocol.valueOf(HP.code2.FIND_STORE_HOUSE_SEARCH_S_VALUE, builder));
        //处理点
        WorldMarchService.getInstance().startMarch(player, World.WorldMarchType.TREASURE_386_SPY_VALUE, terminalId, String.valueOf(treasurePoolId), null, 0, 0, 0, 0, new EffectParams());
        player.responseSuccess(protocol.getType());
        return true;
    }

    /**
     * 活动期间生成宝藏列表
     *
     * @param protocol
     */
    @ProtocolHandler(code = HP.code2.FIND_STORE_HOUSE_TREASURE_LIST_C_VALUE)
    public void onTreasureList(HawkProtocol protocol) {
        FindStoreHouseActivity activity = FindStoreHouseService.getInstance().getActivityObject();
        if (activity == null) {
            sendError(protocol.getType(), Status.Error.ACTIVITY_NOT_OPEN_VALUE);
            return;
        }
        Result<?> result = activity.onOccupyRecord(player.getId());
        if (result.isFail()) {
            sendError(protocol.getType(), result.getStatus());
            return;
        }
        OccupyTreasureInfoRsp.Builder occupyInfo = (OccupyTreasureInfoRsp.Builder) result.getRetObj();
        List<WorldPoint> treasureList = FindStoreHouseService.getInstance().getTreasureList(player.getId());
        for (WorldPoint worldPoint : treasureList) {
            FindStoreHousePoint findStoreHousePoint = (FindStoreHousePoint) worldPoint;
            String marchId = findStoreHousePoint.getMarchId();
            IWorldMarch march = WorldMarchService.getInstance().getMarch(marchId);
            long startTime = findStoreHousePoint.getLifeStartTime();
            if (march != null) {
                startTime = march.getMarchEntity().getResStartTime();
            }
            occupyInfo.addTreasureInfo(findStoreHousePoint.toTreasureBuilder(startTime));
        }
        player.sendProtocol(HawkProtocol.valueOf(HP.code2.FIND_STORE_HOUSE_TREASURE_LIST_S_VALUE, occupyInfo));
        player.responseSuccess(protocol.getType());
    }
}

