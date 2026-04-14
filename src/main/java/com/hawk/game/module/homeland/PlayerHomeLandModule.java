package com.hawk.game.module.homeland;

import com.hawk.activity.ActivityManager;
import com.hawk.activity.event.impl.HomeLandBuildingAchieveEvent;
import com.hawk.activity.event.impl.HomeLandBuildingLevelUpEvent;
import com.hawk.activity.event.impl.HomeLandBuildingShareEvent;
import com.hawk.activity.helper.PlayerAcrossDayLoginMsg;
import com.hawk.game.config.ItemCfg;
import com.hawk.game.crossproxy.CrossService;
import com.hawk.game.global.GlobalData;
import com.hawk.game.item.AwardItems;
import com.hawk.game.item.ConsumeItems;
import com.hawk.game.item.ItemInfo;
import com.hawk.game.module.homeland.cfg.*;
import com.hawk.game.module.homeland.entity.*;
import com.hawk.game.module.homeland.map.HomeLandMap;
import com.hawk.game.module.homeland.map.HomeLandMapComponent;
import com.hawk.game.module.homeland.rank.*;
import com.hawk.game.msg.GuildJoinMsg;
import com.hawk.game.msg.GuildQuitMsg;
import com.hawk.game.player.Player;
import com.hawk.game.player.PlayerModule;
import com.hawk.game.protocol.*;
import com.hawk.game.protocol.HomeLand.*;
import com.hawk.game.service.chat.ChatService;
import com.hawk.game.util.GsConst;
import com.hawk.game.util.LogUtil;
import com.hawk.log.Action;
import com.hawk.log.LogConst;
import com.hawk.serialize.string.SerializeHelper;
import org.hawk.annotation.MessageHandler;
import org.hawk.annotation.ProtocolHandler;
import org.hawk.config.HawkConfigManager;
import org.hawk.config.iterator.ConfigIterator;
import org.hawk.log.HawkLog;
import org.hawk.net.protocol.HawkProtocol;
import org.hawk.os.HawkException;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * 家园模块
 *
 * @author zhy
 */
public class PlayerHomeLandModule extends PlayerModule {
    static final Logger logger = LoggerFactory.getLogger("Server");
    private final HomeLandMapComponent mapComponent = new HomeLandMapComponent();
    private final Map<HomeLandBuildingOperation, BiConsumer<HomeLandBuildingUpdateReq, Integer>> operationHandlers = new HashMap<>();
    /**
     * 功能是否已解锁
     */
    private boolean funcUnlocked = false;

    /**
     * 构造函数
     *
     * @param player
     */
    public PlayerHomeLandModule(Player player) {
        super(player);
        operationHandlers.put(HomeLandBuildingOperation.BUILDING_UPGRADE, this::upgradeMapBuild);
        operationHandlers.put(HomeLandBuildingOperation.BUILDING_PLACE, this::placeBuild);
        operationHandlers.put(HomeLandBuildingOperation.BUILDING_RECYCLE, this::recycleBuild);
        operationHandlers.put(HomeLandBuildingOperation.BUILDING_MOVE, this::moveBuild);
        operationHandlers.put(HomeLandBuildingOperation.BUILDING_UPGRADE_WARHOUSE, this::upgradeWareBuild);
        operationHandlers.put(HomeLandBuildingOperation.BUILDING_DISASSEMBLY, this::disassemblyBuild);
    }

    /**
     * 玩家登陆处理(数据同步)
     */
    @Override
    protected boolean onPlayerLogin() {
        if (player.isCsPlayer()) {
            return true;
        }
        checkFuncUnlock();
        if (funcUnlocked) {
            PlayerHomeLandEntity playerHomeLandEntity = player.getData().getHomeLandEntity();
            initHomeLand(playerHomeLandEntity);
            resetData();
            playerHomeLandEntity.getComponent().updateHomeLandRank();
            playerHomeLandEntity.getComponent().pushBuildCollect();
        }
        return true;
    }

    /**
     * 检测功能是否已解锁
     */
    private void checkFuncUnlock() {
        if (player.checkHomeLandFuncUnlock()) {
            funcUnlocked = true;
        }
    }

    /**
     * 玩家跨天消息事件
     *
     * @param msg
     * @return
     */
    @MessageHandler
    public boolean onCrossDayLogin(PlayerAcrossDayLoginMsg msg) {
        if (funcUnlocked) {
            resetData();
        }
        return true;
    }

    @Override
    public boolean onTick() {
        if (!funcUnlocked) {
            return true;
        }
        HomeLandComponent component = player.getData().getHomeLandEntity().getComponent();
        component.onTick();
        return super.onTick();
    }

    //重置点赞
    private void resetData() {
        HomeLandComponent component = player.getData().getHomeLandEntity().getComponent();
        component.dailyReset();
    }

    private void initHomeLandView(PlayerHomeLandEntity entity) {
        for (HomeLandBuilding build : entity.getComponent().getMapBuildComp().getBuildingMap().values()) {
            mapComponent.addViewPoint(entity.getThemeId(), build);
        }
    }

    /**
     * 建筑放置,移动
     *
     * @param protocol
     * @return
     */
    @ProtocolHandler(code = HP.code2.HOME_BUILDING_OPERATION_C_VALUE)
    public boolean onBuildingUpdate(HawkProtocol protocol) {
        if (!funcUnlocked) {
            sendError(protocol.getType(), Status.HOMELANDError.HOME_LAND_UNLOCK);
            return false;
        }
        HomeLandBuildingUpdateReq req = protocol.parseProtocol(HomeLandBuildingUpdateReq.getDefaultInstance());
        BiConsumer<HomeLandBuildingUpdateReq, Integer> handler = operationHandlers.get(req.getOperation());
        if (handler == null) {
            return false;
        }
        handler.accept(req, protocol.getType());
        return true;
    }

    /**
     * 回收
     */
    public void recycleBuild(HomeLandBuildingUpdateReq req, int protocolType) {
        String uuid = req.getUuid();
        PlayerHomeLandEntity entity = player.getData().getHomeLandEntity();
        HomeLandComponent component = entity.getComponent();
        if (!component.containsBuild(uuid)) {
            sendError(protocolType, Status.HOMELANDError.HOME_LAND_NOT_FIND);
            return;
        }
        HomeLandBuilding building = component.getHomeBuildById(uuid);
        //主建筑不能回收
        if (building.isMainBuild()) {
            sendError(protocolType, Status.HOMELANDError.HOME_LAND_MAIN_BUILD_NOT_CYC);
            return;
        }
        HomeLandMap map = mapComponent.getMap(entity.getThemeId());
        if (map == null) {
            sendError(protocolType, Status.SysError.CONFIG_ERROR);
            return;
        }
        map.removeViewPoint(building);
        component.addWareHouse(building.getConfigId(), building.getBuildType());
        collectRecruit(building);
        component.removeMapBuild(uuid);
        logger.info("HomeLand player:{},recycleBuild:{},cfgId:{},pox:{},poy:{}", player.getId(), uuid, building.getConfigId(), building.getX(), building.getY());
        sendProtocol(HawkProtocol.valueOf(HP.code2.HOME_BUILDING_UPDATE_S_VALUE, buildPushBuildingUpdate(req.getOperation(), building)));
        component.notifyChanged();
        player.responseSuccess(protocolType);
    }

    /**
     * 拆解建筑
     */
    public void disassemblyBuild(HomeLandBuildingUpdateReq req, int protocolType) {
        int buildCfgId = req.getBuildCfgId();
        PlayerHomeLandEntity entity = player.getData().getHomeLandEntity();
        HomeLandComponent component = entity.getComponent();
        if (req.getDisassembly() <= 0) {
            sendError(protocolType, Status.SysError.PARAMS_INVALID);
            return;
        }
        if (!component.containsWareHouse(buildCfgId)) {
            sendError(protocolType, Status.HOMELANDError.HOME_LAND_NOT_FIND);
            return;
        }
        if (!component.checkCanDisassembly(buildCfgId, req.getDisassembly())) {
            sendError(protocolType, Status.HOMELANDError.HOME_LAND_DISASSEMBLY_NOT_ENOUGH);
            return;
        }
        HomeLandBuildingCfg currCfg = HawkConfigManager.getInstance().getConfigByKey(HomeLandBuildingCfg.class, buildCfgId);
        if (currCfg.getReclaimItems().isEmpty()) {
            sendError(protocolType, Status.HOMELANDError.HOME_LAND_RECYCLE_INVALID);
            return;
        }
        List<ItemInfo> reclaim = currCfg.getReclaimItems();
        AwardItems awardItems = AwardItems.valueOf();
        for (int i = req.getDisassembly(); i > 0; i--) {
            awardItems.addItemInfos(reclaim);
        }
        awardItems.rewardTakeAffectAndPush(player, Action.HOME_LAND_RECYCLE, true);
        component.removeWareHouse(buildCfgId, req.getDisassembly());
        component.notifyWareHouseChanged();
        int left = component.containsWareHouse(buildCfgId) ? component.getWareHouseComp().getWareHouseMap().get(buildCfgId).getCount() : 0;
        logger.info("HomeLand player:{} DisassemblyBuild:{},disassembly:{},left:{}", player.getId(), buildCfgId, req.getDisassembly(), left);
        player.responseSuccess(protocolType);
    }

    /**
     * 升级背包中建筑
     */
    public void upgradeWareBuild(HomeLandBuildingUpdateReq req, int protocolType) {
        int buildCfgId = req.getBuildCfgId();
        PlayerHomeLandEntity entity = player.getData().getHomeLandEntity();
        HomeLandComponent component = entity.getComponent();
        if (!component.containsWareHouse(buildCfgId)) {
            sendError(protocolType, Status.HOMELANDError.HOME_LAND_NOT_FIND);
            return;
        }
//        HomeLandConstKVCfg cfg = HawkConfigManager.getInstance().getKVInstance(HomeLandConstKVCfg.class);
//        Optional<HomeLandBuilding> mainBuild = component.getMainBuild(cfg.getMainBuildType());
//        if (!mainBuild.isPresent()) {
//            sendError(protocolType, Status.HOMELANDError.HOME_LAND_MAIN_UNLOCK);
//            return;
//        }
        HomeLandBuildingCfg currCfg = HawkConfigManager.getInstance().getConfigByKey(HomeLandBuildingCfg.class, buildCfgId);
        if (currCfg == null) {
            sendError(protocolType, Status.SysError.CONFIG_ERROR);
            return;
        }
        HomeLandBuildingCfg nextLevelCfg = HawkConfigManager.getInstance().getConfigByKey(HomeLandBuildingCfg.class, currCfg.getPostStage());
        if (nextLevelCfg == null) {
            sendError(protocolType, Status.HOMELANDError.HOME_LAND_UPGRADE_MAX_LEVEL);
            return;
        }
        //达到繁荣度
        if (component.checkProsperity(nextLevelCfg.getBuildProsperityLimit())) {
            sendError(protocolType, Status.HOMELANDError.HOME_LAND_NOT_ENOUGH_PROSPERITY);
            return;
        }
        //下一等级的消耗
        List<ItemInfo> nextWareHouseCost = component.getBuildWareHouseCost(nextLevelCfg.getCostItem());
        if (!component.checkWareHouseCost(nextWareHouseCost, currCfg.getId())) {
            sendError(protocolType, Status.Error.ITEM_NOT_ENOUGH);
            return;
        }
        List<ItemInfo> nextCost = component.getItemCost(nextLevelCfg.getCostItem());
        if (!nextCost.isEmpty()) {
            ConsumeItems consume = ConsumeItems.valueOf();
            consume.addConsumeInfo(nextCost, false);
            if (!consume.checkConsume(player, protocolType)) {
                sendError(protocolType, Status.Error.ITEM_NOT_ENOUGH);
                return;
            }
            consume.consumeAndPush(player, Action.HOME_LAND_UPGRADE);
        }
        //消耗掉的
        component.costWareHouse(nextWareHouseCost);
        //删掉当前的
        component.removeWareHouse(currCfg.getId(), 1);
        //获得下一等级的
        component.addWareHouse(nextLevelCfg.getId(), nextLevelCfg.getBuildType());
        component.notifyWareHouseAndCollectChange(nextLevelCfg.getId());
        HomeLandBuildingTypeCfg typeCfg = HawkConfigManager.getInstance().getConfigByKey(HomeLandBuildingTypeCfg.class, currCfg.getBuildType());
        if (typeCfg != null) {
            logHomeLandUpgradeBuild(player, 2, currCfg.getId(), nextLevelCfg.getId(), typeCfg.getQuality());
        }
        player.responseSuccess(protocolType);
    }

    /**
     * 升级建筑
     */
    public void upgradeMapBuild(HomeLandBuildingUpdateReq req, int protocolType) {
        String uuid = req.getUuid();
        PlayerHomeLandEntity entity = player.getData().getHomeLandEntity();
        HomeLandComponent component = entity.getComponent();
        if (!component.containsBuild(uuid)) {
            sendError(protocolType, Status.HOMELANDError.HOME_LAND_NOT_FIND);
            return;
        }
//        HomeLandConstKVCfg cfg = HawkConfigManager.getInstance().getKVInstance(HomeLandConstKVCfg.class);
//        Optional<HomeLandBuilding> mainBuild = component.getMainBuild(cfg.getMainBuildType());
//        if (!mainBuild.isPresent()) {
//            sendError(protocolType, Status.HOMELANDError.HOME_LAND_MAIN_UNLOCK);
//            return;
//        }
        HomeLandBuilding building = component.getHomeBuildById(uuid);
        HomeLandBuildingCfg currCfg = HawkConfigManager.getInstance().getConfigByKey(HomeLandBuildingCfg.class, building.getConfigId());
        if (currCfg == null) {
            sendError(protocolType, Status.SysError.CONFIG_ERROR);
            return;
        }
        HomeLandBuildingCfg nextLevelCfg = HawkConfigManager.getInstance().getConfigByKey(HomeLandBuildingCfg.class, currCfg.getPostStage());
        if (nextLevelCfg == null) {
            sendError(protocolType, Status.HOMELANDError.HOME_LAND_UPGRADE_MAX_LEVEL);
            return;
        }
        //其他建筑不能高于主建筑
//        if (building.getBuildType() != cfg.getMainBuildType() && nextLevelCfg.getLevel() > mainBuild.get().getBuildCfg().getLevel()) {
//            logger.info("HomeLand upgradeMapBuild:{},mainBuildLevel:{},nextBuild:{}", uuid, mainBuild.get().getBuildCfg().getLevel(), nextLevelCfg.getId());
//            sendError(protocolType, Status.HOMELANDError.HOME_LAND_UPGRADE_LEVEL);
//            return;
//        }
        //达到繁荣度
        if (component.checkProsperity(nextLevelCfg.getBuildProsperityLimit())) {
            sendError(protocolType, Status.HOMELANDError.HOME_LAND_NOT_ENOUGH_PROSPERITY);
            return;
        }
        //下一等级的消耗
        List<ItemInfo> nextWareHouseCost = component.getBuildWareHouseCost(nextLevelCfg.getCostItem());
        if (!component.checkMapBuildCost(nextWareHouseCost)) {
            sendError(protocolType, Status.Error.ITEM_NOT_ENOUGH);
            return;
        }
        List<ItemInfo> nextCost = component.getItemCost(nextLevelCfg.getCostItem());
        if (!nextCost.isEmpty()) {
            ConsumeItems consume = ConsumeItems.valueOf();
            consume.addConsumeInfo(nextCost, false);
            if (!consume.checkConsume(player, protocolType)) {
                sendError(protocolType, Status.Error.ITEM_NOT_ENOUGH);
                return;
            }
            consume.consumeAndPush(player, Action.HOME_LAND_UPGRADE);
        }
        building.setConfigId(nextLevelCfg.getId());
        component.costWareHouse(nextWareHouseCost);
        component.addCollect(nextLevelCfg.getBuildType());
        component.notifyCollectChanged();
        sendProtocol(HawkProtocol.valueOf(HP.code2.HOME_BUILDING_UPDATE_S_VALUE, buildPushBuildingUpdate(req.getOperation(), building)));
        component.notifyChanged();
        HomeLandBuildingTypeCfg typeCfg = HawkConfigManager.getInstance().getConfigByKey(HomeLandBuildingTypeCfg.class, currCfg.getBuildType());
        if (typeCfg != null) {
            logHomeLandUpgradeBuild(player, 1, currCfg.getId(), nextLevelCfg.getId(), typeCfg.getQuality());
        }
        player.responseSuccess(protocolType);
        //活动事件
        HomeLandBuildingLevelUpEvent event = new HomeLandBuildingLevelUpEvent(player.getId());
        event.addBuildLevel(nextLevelCfg.getBuildType(), nextLevelCfg.getLevel());
        ActivityManager.getInstance().postEvent(event);
    }

    /**
     * 移动建筑
     */
    public void moveBuild(HomeLandBuildingUpdateReq req, int protocolType) {
        String uuid = req.getUuid();
        int posX = req.getPosX();
        int posY = req.getPosY();
        PlayerHomeLandEntity entity = player.getData().getHomeLandEntity();
        HomeLandComponent component = entity.getComponent();
        if (!component.containsBuild(uuid)) {
            sendError(protocolType, Status.HOMELANDError.HOME_LAND_NOT_FIND);
            return;
        }
        HomeLandBuilding build = component.getHomeBuildById(uuid);
        // 检查是否能被占用
        HomeLandMap map = mapComponent.getMap(entity.getThemeId());
        if (map == null) {
            sendError(protocolType, Status.SysError.CONFIG_ERROR);
            return;
        }
        map.removeViewPoint(build);
        if (!map.canMove(posX, posY, build)) {
            logger.info("HomeLand moveBuild:{},pox:{},poy:{}", uuid, posX, posY);
            sendError(protocolType, Status.HOMELANDError.HOME_LAND_INVALID_POS);
            return;
        }
        build.setX(posX);
        build.setY(posY);
        map.addViewPoint(build);
        entity.notifyUpdate();
        sendProtocol(HawkProtocol.valueOf(HP.code2.HOME_BUILDING_UPDATE_S_VALUE, buildPushBuildingUpdate(req.getOperation(), build)));
        player.responseSuccess(protocolType);
    }

    /**
     * 放置建筑
     */
    public void placeBuild(HomeLandBuildingUpdateReq req, int protocolType) {
        int buildCfgId = req.getBuildCfgId();
        int posX = req.getPosX();
        int posY = req.getPosY();
        PlayerHomeLandEntity entity = player.getData().getHomeLandEntity();
        HomeLandComponent component = entity.getComponent();
        if (!component.containsWareHouse(buildCfgId)) {
            sendError(protocolType, Status.HOMELANDError.HOME_LAND_NOT_FIND);
            return;
        }
        //相同类型建筑判断最大数量
        //落下
        HomeLandBuildingCfg buildingCfg = HawkConfigManager.getInstance().getConfigByKey(HomeLandBuildingCfg.class, buildCfgId);
        if (buildingCfg == null) {
            sendError(protocolType, Status.SysError.CONFIG_ERROR);
            return;
        }
        HomeLandBuildingTypeCfg buildingTypeCfg = HawkConfigManager.getInstance().getConfigByKey(HomeLandBuildingTypeCfg.class, buildingCfg.getBuildType());
        if (buildingTypeCfg == null) {
            sendError(protocolType, Status.SysError.CONFIG_ERROR);
            return;
        }
        if (!component.checkMapBuildSetMax(buildingTypeCfg.getBuildType(), buildingTypeCfg.getMaxSetNumber())) {
            sendError(protocolType, Status.HOMELANDError.HOME_LAND_MAP_BUILD_MAX);
            return;
        }
        HomeLandBuilding buildToMap = HomeLandBuilding.valueOf(buildCfgId);
        // 检查是否能被占用
        HomeLandMap map = mapComponent.getMap(entity.getThemeId());
        if (map == null) {
            sendError(protocolType, Status.SysError.CONFIG_ERROR);
            return;
        }
        if (!map.canMove(posX, posY, buildToMap)) {
            logger.info("HomeLand placeBuild:{},pox:{},poy:{}", buildCfgId, posX, posY);
            sendError(protocolType, Status.HOMELANDError.HOME_LAND_INVALID_POS);
            return;
        }
        buildToMap.setX(posX);
        buildToMap.setY(posY);
        component.addMapBuild(buildToMap.getUid(), buildToMap);
        map.addViewPoint(buildToMap);
        component.removeWareHouse(buildCfgId);
        sendProtocol(HawkProtocol.valueOf(HP.code2.HOME_BUILDING_UPDATE_S_VALUE, buildPushBuildingUpdate(req.getOperation(), buildToMap)));
        component.notifyChanged();
        player.responseSuccess(protocolType);
        //活动事件
        HomeLandBuildingLevelUpEvent event = new HomeLandBuildingLevelUpEvent(player.getId());
        event.addBuildLevel(buildingCfg.getBuildType(), buildingCfg.getLevel());
        ActivityManager.getInstance().postEvent(event);
    }

    private HomeLandBuildingUpdatePush.Builder buildPushBuildingUpdate(HomeLandBuildingOperation operation, HomeLandBuilding... building) {
        HomeLandBuildingUpdatePush.Builder builder = HomeLandBuildingUpdatePush.newBuilder();
        builder.setOperation(operation);
        for (HomeLandBuilding homelandBuilding : building) {
            builder.addBuilding(homelandBuilding.toPB());
        }
        return builder;
    }

    /**
     * 查看自己或他人的家园
     *
     * @param protocol
     * @return
     */
    @ProtocolHandler(code = HP.code2.HOME_BUILDING_INFO_C_VALUE)
    public boolean onHomeInfo(HawkProtocol protocol) {
        HomeLandBuildingInfoReq req = protocol.parseProtocol(HomeLandBuildingInfoReq.getDefaultInstance());
        if (HawkOSOperator.isEmptyString(req.getTargetPlayerId()) || HawkOSOperator.isEmptyString(req.getTargetServerId())) {
            sendError(protocol.getType(), Status.SysError.PARAMS_INVALID);
            return false;
        }
        if (HawkOSOperator.isEmptyString(req.getPlayerId()) || HawkOSOperator.isEmptyString(req.getServerId())) {
            sendError(protocol.getType(), Status.SysError.PARAMS_INVALID);
            return false;
        }
        String mainServerId = GlobalData.getInstance().getMainServerId(req.getServerId());
        String targetMainServerId = GlobalData.getInstance().getMainServerId(req.getTargetServerId());
        if (!CrossService.getInstance().isServerOpen(targetMainServerId, false)) {
            sendError(protocol.getType(), Status.HOMELANDError.HOME_LAND_SERVER_NOT_OPEN);
            return false;
        }
        boolean liked;
        Player sourcePlayer = GlobalData.getInstance().makesurePlayer(req.getPlayerId());
        if (sourcePlayer == null) {
            sendError(protocol.getType(), Status.HOMELANDError.HOME_LAND_PLAYER_INVALID_VALUE);
            return false;
        }
        PlayerHomeLandEntity entity = sourcePlayer.getData().getHomeLandEntity();
        if (!sourcePlayer.checkHomeLandFuncUnlock()) {
            sendError(protocol.getType(), Status.HOMELANDError.HOME_LAND_UNLOCK);
            return false;
        }
        checkFuncUnlock();
        initHomeLand(entity);
        liked = entity.getComponent().getLikeComp().getDailyLikeList().containsKey(req.getTargetPlayerId());
        List<String> activeList = entity.getComponent().getNpcComp().getActiveNpcList(req.getTargetPlayerId());
        HomeLandBuildingInfoReq.Builder crossReq = HomeLandBuildingInfoReq.newBuilder();
        crossReq.mergeFrom(req);
        crossReq.setPlayerId(req.getPlayerId());
        crossReq.setServerId(mainServerId);
        crossReq.setTargetServerId(targetMainServerId);
        crossReq.setLiked(liked);
        crossReq.addAllActiveNpc(activeList);
        crossReq.setActiveNpcTimes(entity.getComponent().getNpcComp().getActiveTimes());
        HomeLand.PBHomeLandServiceReq.Builder csReq = HomeLand.PBHomeLandServiceReq.newBuilder();
        csReq.setProtoclType(HP.code2.HOME_BUILDING_INFO_C_VALUE);
        csReq.setReq(crossReq.build().toByteString());
        HawkProtocol sendProto = HawkProtocol.valueOf(HP.code2.HOME_BUILDING_SERVICE_REQ_VALUE, csReq);
        HomeLandService.getInstance().sendRpcCall(sendProto, crossReq.getTargetServerId(), null);
        player.responseSuccess(protocol.getType());
        return true;
    }

    private void initHomeLand(PlayerHomeLandEntity entity) {
        mapComponent.init();
        entity.getComponent().initHomeLandWareHouse();
        initHomeLandView(entity);
    }

    /**
     * 商店抽奖
     *
     * @param protocol
     * @return
     */
    @ProtocolHandler(code = HP.code2.HOME_BUILDING_EXCHANGE_DRAW_C_VALUE)
    public boolean onExchangeDraw(HawkProtocol protocol) {
        if (!funcUnlocked) {
            sendError(protocol.getType(), Status.HOMELANDError.HOME_LAND_UNLOCK);
            return false;
        }
        HomeLandExchangeDrawReq req = protocol.parseProtocol(HomeLandExchangeDrawReq.getDefaultInstance());
        PlayerHomeLandEntity entity = player.getData().getHomeLandEntity();
        HomeLandComponent component = entity.getComponent();
        HomeLandConstKVCfg cfg = HawkConfigManager.getInstance().getKVInstance(HomeLandConstKVCfg.class);
        if (component.getShopComp().getDailyDrawTimes() >= cfg.getShopMaxTimes()) {
            sendError(protocol.getType(), Status.HOMELANDError.HOME_LAND_DAILY_LIMIT);
            return false;
        }
        int drawTimes = component.getShopComp().getDrawTimes();
        HomeLandGachaCfg gachaCfg = component.getShopComp().findPool(drawTimes);
        if (gachaCfg == null) {
            sendError(protocol.getType(), Status.SysError.PARAMS_INVALID);
            return false;
        }
        if (!gachaCfg.isTop() && req.getDrawType() > 1) {
            sendError(protocol.getType(), Status.SysError.PARAMS_INVALID);
            return false;
        }
        ConsumeItems consume = ConsumeItems.valueOf();
        List<ItemInfo> costInfo = ItemInfo.valueListOf(gachaCfg.getCost(), req.getDrawType());
        consume.addConsumeInfo(costInfo, false);
        if (!consume.checkConsume(player, protocol.getType())) {
            sendError(protocol.getType(), Status.Error.ITEM_NOT_ENOUGH);
            return false;
        }
        consume.consumeAndPush(player, Action.HOME_LAND_SHOP_DRAW);
        AwardItems awardItems = AwardItems.valueOf();
        for (int i = 0; i < req.getDrawType(); i++) {
            int awardId = component.getShopComp().gacha(drawTimes + 1, gachaCfg);
            if (awardId <= 0) {
                sendError(protocol.getType(), Status.SysError.PARAMS_INVALID);
                return false;
            }
            awardItems.addAward(awardId);
        }
        List<HomeLandWareHousePB> awardWareHouse = new ArrayList<>();
        for (ItemInfo awardItem : awardItems.getAwardItems()) {
            ItemCfg itemCfg = HawkConfigManager.getInstance().getConfigByKey(ItemCfg.class, awardItem.getItemId());
            if (itemCfg == null) {
                continue;
            }
            HomeLandBuildingCfg itemBuildCfg = HawkConfigManager.getInstance().getConfigByKey(HomeLandBuildingCfg.class, itemCfg.getBuildId());
            if (itemBuildCfg == null) {
                continue;
            }
            int itemCount = Math.toIntExact(awardItem.getCount());
            for (int count = itemCount; count > 0; count--) {
                HomeLandWareHousePB.Builder wareHouseBuilder = HomeLandWareHousePB.newBuilder();
                wareHouseBuilder.setBuildCfgId(itemBuildCfg.getId());
                wareHouseBuilder.setItemCount(1);
                awardWareHouse.add(wareHouseBuilder.build());
            }
        }
        if (!awardItems.getAwardItems().isEmpty()) {
            awardItems.rewardTakeAffectAndPush(player, Action.HOME_LAND_GET_BUILD);
        }
        component.getShopComp().setDrawTimes(component.getShopComp().getDrawTimes() + req.getDrawType());
        component.getShopComp().setDailyDrawTimes(component.getShopComp().getDailyDrawTimes() + req.getDrawType());
        component.notifyResolveChange(awardWareHouse);
        logger.info("HomeLand player:{}, drawType:{},gachaId:{},shopInfo:{}", player.getId(), req.getDrawType(), gachaCfg.getId(), component.getShopComp().toString());
        player.responseSuccess(protocol.getType());
        return true;
    }

    public void collectRecruit(HomeLandBuilding building) {
        AwardItems awardItems = AwardItems.valueOf();
        HomeLandConstKVCfg cfg = HawkConfigManager.getInstance().getKVInstance(HomeLandConstKVCfg.class);
        long now = HawkTime.getMillisecond();
        if (building.getBuildCfg().getResPerHour() <= 0) {
            return;
        }
        long collectInterval = now - building.getLastHarvestTime();
        // 收取时间间隔小于1s时不让收取
        if (collectInterval < 1000) {
            return;
        }
        long res = resStore(building.getBuildCfg(), collectInterval);
        awardItems.addItem(Const.ItemType.TOOL_VALUE, cfg.getCurrencyItem().getItemId(), res);
        building.setLastHarvestTime(now);
        awardItems.rewardTakeAffectAndPush(player, Action.HOME_LAND_COLLECT_RES);
    }

    /**
     * 主建筑收取
     *
     * @param protocol
     * @return
     */
    @ProtocolHandler(code = HP.code2.HOME_BUILDING_COLLECT_RECRUITS_C_VALUE)
    public boolean onCollectRecruit(HawkProtocol protocol) {
        if (!funcUnlocked) {
            sendError(protocol.getType(), Status.HOMELANDError.HOME_LAND_UNLOCK);
            return false;
        }
        HomeLandCollectRecruitsReq req = protocol.parseProtocol(HomeLandCollectRecruitsReq.getDefaultInstance());
        if (req.getUuidList().isEmpty()) {
            sendError(protocol.getType(), Status.SysError.PARAMS_INVALID);
            return false;
        }
        PlayerHomeLandEntity entity = player.getData().getHomeLandEntity();
        HomeLandComponent component = entity.getComponent();
        long now = HawkTime.getMillisecond();
        //上一次的收取时间
        Map<HomeLandBuilding, Integer> buildingCollectIntervals = new HashMap<>();
        AwardItems awardItems = AwardItems.valueOf();
        HomeLandConstKVCfg cfg = HawkConfigManager.getInstance().getKVInstance(HomeLandConstKVCfg.class);
        for (String uuid : req.getUuidList()) {
            if (!component.containsBuild(uuid)) {
                continue;
            }
            HomeLandBuilding building = component.getHomeBuildById(uuid);
            if (building.getBuildCfg().getResPerHour() <= 0) {
                continue;
            }
            long collectInterval = now - building.getLastHarvestTime();
            // 收取时间间隔小于1s时不让收取
            if (collectInterval < 1000) {
                logger.error("collect resource interval too short, playerId: {}, buildCfgId: {}, uuid: {}, lastTime: {}",
                        player.getId(), building.getConfigId(), uuid, building.getLastHarvestTime());
                continue;
            }
            long res = resStore(building.getBuildCfg(), collectInterval);
            awardItems.addItem(Const.ItemType.TOOL_VALUE, cfg.getCurrencyItem().getItemId(), res);
            building.setLastHarvestTime(now);
            building.setLastHarvestRes(res);
            buildingCollectIntervals.put(building, (int) collectInterval / 1000);
            logger.info("collect resource, playerId: {}, buildCfgId: {}, uuid: {}, lastTime: {}, timeLong: {}, res:{}",
                    player.getId(), building.getConfigId(), uuid, building.getLastHarvestTime(), collectInterval, res);
            //抛活动事件
            ActivityManager.getInstance().postEvent(new HomeLandBuildingAchieveEvent(player.getId(), building.getBuildType()));
        }
        if (awardItems.hasAwardItem()) {
            awardItems.rewardTakeAffectAndPush(player, Action.HOME_LAND_COLLECT_RES);
        }
        if (!buildingCollectIntervals.isEmpty()) {
            component.getShopComp().setLastCollectRecruit(now);
            component.pushBuildCollect();
            entity.notifyUpdate();
        }
        sendProtocol(HawkProtocol.valueOf(HP.code2.HOME_BUILDING_UPDATE_S_VALUE, buildPushBuildingUpdate(HomeLandBuildingOperation.BUILDING_COLLECT, buildingCollectIntervals.keySet().toArray(new HomeLandBuilding[0]))));
        player.responseSuccess(protocol.getType());
        return true;
    }

    /**
     * 可收取资源建筑当前储量
     *
     * @param buildCfg
     * @param timeLong 产出资源的时长
     * @return
     */
    public long resStore(HomeLandBuildingCfg buildCfg, long timeLong) {
        double realOutputRate = buildCfg.getResPerHour();
        // 资源建筑最大储量
        double realOutputLimit = realOutputRate / buildCfg.getResPerHour() * buildCfg.getResLimit();
        // 产出量
        long product = (long) (timeLong * 1.0D / GsConst.HOUR_MILLI_SECONDS * realOutputRate);
        product = (long) Math.min(realOutputLimit, product);
        return product;
    }

    /**
     * 点赞
     *
     * @param protocol
     * @return
     */
    @ProtocolHandler(code = HP.code2.HOME_BUILDING_THEME_LIKE_C_VALUE)
    public boolean onHomeLike(HawkProtocol protocol) {
        HomeLandThemeLikeReq req = protocol.parseProtocol(HomeLandThemeLikeReq.getDefaultInstance());
        if (HawkOSOperator.isEmptyString(req.getTargetPlayerId()) || HawkOSOperator.isEmptyString(req.getTargetServerId())) {
            sendError(protocol.getType(), Status.SysError.PARAMS_INVALID);
            return false;
        }
        if (HawkOSOperator.isEmptyString(req.getPlayerId()) || HawkOSOperator.isEmptyString(req.getServerId())) {
            sendError(protocol.getType(), Status.SysError.PARAMS_INVALID);
            return false;
        }
        boolean isLiked;
        String mainServerId = GlobalData.getInstance().getMainServerId(req.getServerId());
        String targetMainServerId = GlobalData.getInstance().getMainServerId(req.getTargetServerId());
        //推送点赞成功
        Player sourcePlayer = GlobalData.getInstance().makesurePlayer(req.getPlayerId());
        if (sourcePlayer == null) {
            sendError(protocol.getType(), Status.HOMELANDError.HOME_LAND_PLAYER_INVALID_VALUE);
            return false;
        }
        if (!sourcePlayer.checkHomeLandFuncUnlock()) {
            sendError(protocol.getType(), Status.HOMELANDError.HOME_LAND_UNLOCK);
            return false;
        }
        PlayerHomeLandEntity sourceEntity = sourcePlayer.getData().getHomeLandEntity();
        HomeLandComponent component = sourceEntity.getComponent();
        HomeLandConstKVCfg cfg = HawkConfigManager.getInstance().getKVInstance(HomeLandConstKVCfg.class);
        if (!component.getLikeComp().getDailyLikeList().containsKey(req.getTargetPlayerId())) {
            if (component.getLikeComp().getDailyLikeList().size() >= cfg.getThumbsUpMaxTimes()) {
                sendError(protocol.getType(), Status.HOMELANDError.HOME_LAND_REPEAT_LIKE);
                return false;
            }
        }
        isLiked = component.CheckLikeAndSet(req.getTargetPlayerId());
        HomeLandThemeLikeReq.Builder crossReq = HomeLandThemeLikeReq.newBuilder();
        crossReq.mergeFrom(req);
        crossReq.setLiked(isLiked);
        crossReq.setServerId(mainServerId);
        crossReq.setTargetServerId(targetMainServerId);

        HomeLand.PBHomeLandServiceReq.Builder csReq = HomeLand.PBHomeLandServiceReq.newBuilder();
        csReq.setProtoclType(HP.code2.HOME_BUILDING_THEME_LIKE_C_VALUE);
        csReq.setReq(crossReq.build().toByteString());
        HawkProtocol sendProto = HawkProtocol.valueOf(HP.code2.HOME_BUILDING_SERVICE_REQ_VALUE, csReq);
        HomeLandService.getInstance().sendRpcCall(sendProto, crossReq.getTargetServerId(), null);
        player.responseSuccess(protocol.getType());
        return true;
    }

    /**
     * npc交互
     *
     * @param protocol
     * @return
     */
    @ProtocolHandler(code = HP.code2.HOME_BUILDING_INTERACTIVE_NPC_C_VALUE)
    public boolean onHomeInterActiveNpc(HawkProtocol protocol) {
        HomeLandInterActiveNpcReq req = protocol.parseProtocol(HomeLandInterActiveNpcReq.getDefaultInstance());
        if (HawkOSOperator.isEmptyString(req.getTargetPlayerId()) || HawkOSOperator.isEmptyString(req.getTargetServerId())) {
            sendError(protocol.getType(), Status.SysError.PARAMS_INVALID);
            return false;
        }
        if (HawkOSOperator.isEmptyString(req.getPlayerId()) || HawkOSOperator.isEmptyString(req.getServerId())) {
            sendError(protocol.getType(), Status.SysError.PARAMS_INVALID);
            return false;
        }
        String mainServerId = GlobalData.getInstance().getMainServerId(req.getServerId());
        String targetMainServerId = GlobalData.getInstance().getMainServerId(req.getTargetServerId());
        Player sourcePlayer = GlobalData.getInstance().makesurePlayer(req.getPlayerId());
        if (sourcePlayer == null) {
            sendError(protocol.getType(), Status.HOMELANDError.HOME_LAND_PLAYER_INVALID_VALUE);
            return false;
        }
        if (!sourcePlayer.checkHomeLandFuncUnlock()) {
            sendError(protocol.getType(), Status.HOMELANDError.HOME_LAND_UNLOCK);
            return false;
        }
        PlayerHomeLandEntity sourceEntity = sourcePlayer.getData().getHomeLandEntity();
        HomeLandComponent component = sourceEntity.getComponent();
        HomeLandConstKVCfg cfg = HawkConfigManager.getInstance().getKVInstance(HomeLandConstKVCfg.class);
        if (component.getNpcComp().getActiveTimes() >= cfg.getIssueNum()) {
            sendError(protocol.getType(), Status.HOMELANDError.HOME_LAND_ACTIVE_NPC_LIMIT);
            return false;
        }
        if (!component.getNpcComp().isActive(req.getTargetPlayerId(), req.getUuid())) {
            sendError(protocol.getType(), Status.HOMELANDError.HOME_LAND_NPC_ALREADY_ACTIVE);
            return false;
        }
        HomeLandNpcActive active = component.getNpcComp().getActiveNpc().get(req.getTargetPlayerId());
        List<String> activeList = new ArrayList<>();
        if (active != null) {
            activeList.addAll(active.getActiveNpcMap().keySet());
        }
        HomeLandInterActiveNpcReq.Builder crossReq = HomeLandInterActiveNpcReq.newBuilder();
        crossReq.mergeFrom(req);
        crossReq.setServerId(mainServerId);
        crossReq.setTargetServerId(targetMainServerId);
        crossReq.addAllActiveNpc(activeList);
        HomeLand.PBHomeLandServiceReq.Builder csReq = HomeLand.PBHomeLandServiceReq.newBuilder();
        csReq.setProtoclType(HP.code2.HOME_BUILDING_INTERACTIVE_NPC_C_VALUE);
        csReq.setReq(crossReq.build().toByteString());
        HawkProtocol sendProto = HawkProtocol.valueOf(HP.code2.HOME_BUILDING_SERVICE_REQ_VALUE, csReq);
        HomeLandService.getInstance().sendRpcCall(sendProto, crossReq.getTargetServerId(), null);
        player.responseSuccess(protocol.getType());
        return true;
    }

    /**
     * 切换主题
     *
     * @param protocol
     * @return
     */
    @ProtocolHandler(code = HP.code2.HOME_BUILDING_CHANGE_THEME_C_VALUE)
    public boolean onChangeTheme(HawkProtocol protocol) {
        if (!funcUnlocked) {
            sendError(protocol.getType(), Status.HOMELANDError.HOME_LAND_UNLOCK);
            return false;
        }
        ChangeHomeLandThemeReq req = protocol.parseProtocol(ChangeHomeLandThemeReq.getDefaultInstance());
        int theme = req.getThemeId();
        //判断解锁条件
        PlayerHomeLandEntity entity = player.getData().getHomeLandEntity();
        HomeLandComponent component = entity.getComponent();
        if (!component.getLikeComp().getDailyLikeList().containsKey(theme)) {
            sendError(protocol.getType(), Status.HOMELANDError.HOME_LAND_THEME_UNLOCK);
            return false;
        }
        HomeLandMap map = mapComponent.getMap(theme);
        if (map == null) {
            sendError(protocol.getType(), Status.SysError.CONFIG_ERROR);
            return false;
        }
        long now = HawkTime.getMillisecond();
        List<HomeLandBuilding> unPlaceBuild = new ArrayList<>();
        for (HomeLandBuilding build : component.getMapBuildComp().getBuildingMap().values()) {
            if (!map.tryOccupied(build.getX(), build.getY(), build.getWidth(), build.getHeight())) {
                build.setLastHarvestTime(now);
                map.addViewPoint(build);
            } else {
                unPlaceBuild.add(build);
            }
        }
        for (HomeLandBuilding homelandBuilding : unPlaceBuild) {
            component.addWareHouse(homelandBuilding.getConfigId(), homelandBuilding.getBuildType());
            component.removeMapBuild(homelandBuilding.getUid());
        }
        entity.setThemeId(theme);
        component.notifyChanged();
        player.responseSuccess(protocol.getType());
        return true;
    }

    /**
     * 拜访
     *
     * @param protocol
     * @return
     */
    @ProtocolHandler(code = HP.code2.HOME_BUILDING_RANK_C_VALUE)
    public boolean onRankInfo(HawkProtocol protocol) {
        HomeLandRankReq req = protocol.parseProtocol(HomeLandRankReq.getDefaultInstance());
        HomeLandRankResp.Builder resp = HomeLandRankResp.newBuilder();
        resp.setRankType(req.getRankType());
        resp.setTab(req.getTab());
        HomeLandRankImpl rankImpl = HomeLandService.getInstance().getRankByType(HomeLandRankType.getValue(req.getTab()),
                HomeLandRankServerType.getValue(req.getRankType()), player.getGuildId());
        PlayerHomeLandEntity entity = player.getData().getHomeLandEntity();
        HomeLandComponent component = entity.getComponent();
        long score = component.getRankScore(HomeLandRankType.getValue(req.getTab()));
        if (rankImpl == null) {
            resp.setMyRank(buildRankInfo(HomeLandRank.valueOf(player.getId(), score), player));
            sendProtocol(HawkProtocol.valueOf(HP.code2.HOME_BUILDING_RANK_S_VALUE, resp));
            return false;
        }
        List<HomeLand.HomeLandRankMsg.Builder> rankList = rankImpl.getRankList();
        for (HomeLandRankMsg.Builder builder : rankList) {
            HomeLandRankMsg.Builder rankMsg = HomeLandRankMsg.newBuilder().mergeFrom(builder.build());
            HomeLandNpcActive active = component.getNpcComp().getActiveNpc().get(builder.getPlayerId());
            if ((active != null && active.isActive(builder.getNpcNum())) || builder.getNpcNum() <= 0) {
                rankMsg.setNpcRedDot(true);
            }
            resp.addRankItems(rankMsg.build());
        }
        HomeLandRank myRank = rankImpl.getRank(player.getId());
        if (myRank != null) {
            myRank.setScore(score);
            resp.setMyRank(buildRankInfo(myRank, player));
        }
        sendProtocol(HawkProtocol.valueOf(HP.code2.HOME_BUILDING_RANK_S_VALUE, resp));
        player.responseSuccess(protocol.getType());
        return true;
    }

    /**
     * 加入联盟
     *
     * @return
     */
    @MessageHandler
    private boolean onGuildJoinMsg(GuildJoinMsg msg) {
        if (!funcUnlocked) {
            return true;
        }
        PlayerHomeLandEntity entity = player.getData().getHomeLandEntity();
        HomeLandComponent component = entity.getComponent();
        component.updateHomeLandRank();
        return true;
    }

    /**
     * 退盟
     *
     * @param msg
     */
    @MessageHandler
    private void onQuitGuild(GuildQuitMsg msg) {
        if (!funcUnlocked) {
            return;
        }
        PlayerHomeLandEntity entity = player.getData().getHomeLandEntity();
        HomeLandComponent component = entity.getComponent();
        component.updateHomeLandRank();
    }

    public HomeLand.HomeLandRankMsg buildRankInfo(HomeLandRank homeLandRank, Player playerInfo) {
        HomeLand.HomeLandRankMsg.Builder rankInfo = HomeLand.HomeLandRankMsg.newBuilder();
        rankInfo.setPlayerId(playerInfo.getId());
        rankInfo.setPlayerName(playerInfo.getName());
        rankInfo.setIcon(playerInfo.getIcon());
        rankInfo.setPfIcon(playerInfo.getPfIcon());
        rankInfo.setGuildTag(playerInfo.getGuildTag());
        rankInfo.setRank(homeLandRank.getRank() <= 0 ? -1 : homeLandRank.getRank());
        rankInfo.setScore(homeLandRank.getScore());
        rankInfo.setServerId(playerInfo.getMainServerId());
        rankInfo.setGuildName(playerInfo.getGuildName());
        return rankInfo.build();
    }

    /**
     * 激活繁荣度属性,一键激活
     *
     * @param protocol
     * @return
     */
    @ProtocolHandler(code = HP.code2.HOME_BUILDING_PROSPERITY_ACTIVE_C_VALUE)
    public boolean onActiveProsperityAttr(HawkProtocol protocol) {
        if (!funcUnlocked) {
            sendError(protocol.getType(), Status.HOMELANDError.HOME_LAND_UNLOCK);
            return false;
        }
        PlayerHomeLandEntity entity = player.getData().getHomeLandEntity();
        HomeLandComponent component = entity.getComponent();
        ConfigIterator<HomeLandProsperityAttrCfg> cfgIter = HawkConfigManager.getInstance().getConfigIterator(HomeLandProsperityAttrCfg.class);
        if (cfgIter.isEmpty()) {
            sendError(protocol.getType(), Status.SysError.CONFIG_ERROR);
            return false;
        }
        Set<Integer> allAttr = new HashSet<>();
        AwardItems awardItems = AwardItems.valueOf();
        for (HomeLandProsperityAttrCfg attrCfg : cfgIter) {
            if (entity.getProsperity() < attrCfg.getNeedProsperity()) {
                continue;
            }
            if (component.getAttrComp().getActiveProsperityAttrSet().contains(attrCfg.getId())) {
                continue;
            }
            awardItems.addItemInfos(attrCfg.getRewardItem());
            allAttr.add(attrCfg.getId());
        }
        if (!allAttr.isEmpty()) {
            component.getAttrComp().getActiveProsperityAttrSet().addAll(allAttr);
            component.notifyProsperityAttrChange();
            logHomeLandActiveAttr(player, entity.getProsperity(), allAttr);
        }
        awardItems.rewardTakeAffectAndPush(player, Action.HOME_LAND_ACTIVE_ATTR, true);
        player.responseSuccess(protocol.getType());
        return false;
    }

    /**
     * 分享
     *
     * @param protocol
     * @return
     */
    @SuppressWarnings("deprecation")
    @ProtocolHandler(code = HP.code2.HOME_BUILDING_SHARE_C_VALUE)
    public boolean onHomeShare(HawkProtocol protocol) {
        if (!funcUnlocked) {
            sendError(protocol.getType(), Status.HOMELANDError.HOME_LAND_UNLOCK);
            return false;
        }
        HomeLandShareReq req = protocol.parseProtocol(HomeLandShareReq.getDefaultInstance());
        if (req.getShareTo() != 1 && req.getShareTo() != 2) {
            sendError(protocol.getType(), Status.SysError.PARAMS_INVALID);
            return false;
        }
        if (HawkOSOperator.isEmptyString(req.getPlayerName())) {
            sendError(protocol.getType(), Status.SysError.PARAMS_INVALID);
            return false;
        }
        PlayerHomeLandEntity entity = player.getData().getHomeLandEntity();
        long now = HawkTime.getMillisecond();
        HomeLandConstKVCfg cfg = HawkConfigManager.getInstance().getKVInstance(HomeLandConstKVCfg.class);
        if (now - entity.getShareTime() < cfg.getShareCdTime()) {
            sendError(protocol.getType(), Status.HOMELANDError.HOME_LAND_LIKE_CD_VALUE);
            return false;
        }
        Const.NoticeCfgId noticeId = req.getPlayerId().equals(player.getId()) ? Const.NoticeCfgId.HOME_LAND_SELF_SHARE : Const.NoticeCfgId.HOME_LAND_OTHER_SHARE;
        Const.ChatType chatType = req.getShareTo() == 1 ? Const.ChatType.CHAT_WORLD : Const.ChatType.CHAT_ALLIANCE;
        ChatService.getInstance().addWorldBroadcastMsg(chatType, noticeId, player, req.getPlayerId(), req.getServerId(), req.getPlayerName());
        entity.setShareTime(now);
        player.responseSuccess(protocol.getType());
        //抛事件
        ActivityManager.getInstance().postEvent(new HomeLandBuildingShareEvent(player.getId()));
        return true;
    }


    /**
     * 获取建筑等级
     *
     * @return
     */
    public Map<Integer, Integer> getHomeLandBuildLevel() {
        PlayerHomeLandEntity entity = player.getData().getHomeLandEntity();
        if (Objects.isNull(entity)) {
            return null;
        }
        HomeLandComponent component = entity.getComponent();
        if (Objects.isNull(component)) {
            return null;
        }
        HLMapBuildComp hmap = component.getMapBuildComp();
        if (Objects.isNull(hmap)) {
            return null;
        }
        Map<Integer, Integer> levelMap = new HashMap<>();
        Map<String, HomeLandBuilding> hbMap = hmap.getBuildingMap();
        for (HomeLandBuilding building : hbMap.values()) {
            HomeLandBuildingCfg cfg = building.getBuildCfg();
            if (Objects.isNull(cfg)) {
                continue;
            }
            int type = cfg.getBuildType();
            int level = cfg.getLevel();
            int inLevel = levelMap.getOrDefault(type, 0);
            if (level > inLevel) {
                levelMap.put(type, level);
            }
        }
        return levelMap;
    }

    public Map<Integer, HomeLandBuildingCfg> getHomeLandBuildMaxLevelCfg() {
        PlayerHomeLandEntity entity = player.getData().getHomeLandEntity();
        if (Objects.isNull(entity)) {
            return null;
        }
        HomeLandComponent component = entity.getComponent();
        if (Objects.isNull(component)) {
            return null;
        }
        HLMapBuildComp hmap = component.getMapBuildComp();
        if (Objects.isNull(hmap)) {
            return null;
        }

        Map<Integer, HomeLandBuildingCfg> maxLvBuildCfgMap = new HashMap<>();
        List<HomeLandBuildingCfg> buildingCfgList = hmap.getBuildingMap().values().stream().map(e -> e.getBuildCfg()).collect(Collectors.toList());
        component.getWareHouseComp().getWareHouseMap().keySet().forEach(cfgId -> {
            HomeLandBuildingCfg cfg = HawkConfigManager.getInstance().getConfigByKey(HomeLandBuildingCfg.class, cfgId);
            buildingCfgList.add(cfg);
        });

        for (HomeLandBuildingCfg cfg : buildingCfgList) {
            if (Objects.isNull(cfg)) {
                continue;
            }
            int type = cfg.getBuildType();
            int level = cfg.getLevel();
            int inLevel = maxLvBuildCfgMap.containsKey(type) ? maxLvBuildCfgMap.get(type).getLevel() : 0;
            if (level > inLevel) {
                maxLvBuildCfgMap.put(type, cfg);
            }
        }
        return maxLvBuildCfgMap;
    }

    /**
     * @param player
     */
    public static void logHomeLandActiveAttr(Player player, long prosperity, Set<Integer> attr) {
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("prosperity", prosperity);
            param.put("attrs", SerializeHelper.collectionToString(attr, SerializeHelper.BETWEEN_ITEMS));
            LogUtil.logActivityCommon(player, LogConst.LogInfoType.home_land_active_attr, param);
        } catch (Exception e) {
            HawkException.catchException(e);
        }
    }
    /**
     * 回收打点
     * @param player
     * @param
     */
    public static void logHomeLandResolveBuild(Player player, int buildId, int itemCount) {
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("buildId", buildId);
            param.put("itemCount", itemCount);
            LogUtil.logActivityCommon(player, LogConst.LogInfoType.home_land_resolve, param);
        } catch (Exception e) {
            HawkException.catchException(e);
        }
    }
    /**
     * @param player
     * @param operType 1:升级地图上建筑，2:升级背包中建筑
     */
    public static void logHomeLandUpgradeBuild(Player player, int operType, int buildId, int nextBuildId, int quality) {
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("operType", operType);
            param.put("buildId", buildId);
            param.put("nextBuildId", nextBuildId);
            param.put("quality", quality);
            LogUtil.logActivityCommon(player, LogConst.LogInfoType.home_land_upgrade, param);
        } catch (Exception e) {
            HawkException.catchException(e);
        }
    }

    /**
     * 回收
     * 如果某type的建筑当前升到最高级的数量达到了最大生效数量，则余下的该type的建筑可以被回收
     * @param protocol
     * @return
     */
    @ProtocolHandler(code = HP.code2.HOME_BUILDING_RESOLVE_BUILD_REQ_VALUE)
    public boolean onHomeResolve(HawkProtocol protocol) {
        if (!funcUnlocked) {
            sendError(protocol.getType(), Status.HOMELANDError.HOME_LAND_UNLOCK);
            return false;
        }
        HomeLandBuildResolveReq req = protocol.parseProtocol(HomeLandBuildResolveReq.getDefaultInstance());
        if (req.getResolveItemsCount() <= 0) {
            sendError(protocol.getType(), Status.SysError.PARAMS_INVALID);
            return false;
        }
        PlayerHomeLandEntity homeLandEntity = player.getData().getHomeLandEntity();
        HomeLandComponent component = homeLandEntity.getComponent();
        AwardItems awardItems = AwardItems.valueOf();
        for (HomeLandWareHousePB wareHousePb : req.getResolveItemsList()) {
            int buildCfgId = wareHousePb.getBuildCfgId();
            int itemCount = wareHousePb.getItemCount();
            HomeLandWareHouse wareHouse = component.getWareHouseComp().getWareHouseMap().get(buildCfgId);
            if (wareHouse == null || wareHouse.getCount() <= 0) {
                HawkLog.errPrintln("onHomeResolve buildId invalid playerId:{}", player.getId(), buildCfgId);
                continue;
            }
            if (itemCount > wareHouse.getCount()) {
                HawkLog.errPrintln("onHomeResolve itemCount invalid playerId:{} itemCount:{}", player.getId(), itemCount);
                continue;
            }
            HomeLandBuildingCfg cfg = HawkConfigManager.getInstance().getConfigByKey(HomeLandBuildingCfg.class, buildCfgId);
            if (cfg == null) {
                continue;
            }
            HomeLandBuildingTypeCfg typeCfg = HawkConfigManager.getInstance().getConfigByKey(HomeLandBuildingTypeCfg.class, cfg.getBuildType());
            if (typeCfg == null) {
                continue;
            }
            int maxLevel = HomeLandBuildingCfg.getMaxLevel(cfg.getBuildType());
            int maxMapLevelNumber = component.getMapBuildComp().checkEffectBuild(cfg.getBuildType(), maxLevel);
            int effectWareHouse = component.getWareHouseComp().checkEffectWareHouse(cfg.getBuildType(), maxLevel);
            int canResolve = maxMapLevelNumber + effectWareHouse - typeCfg.getMaxNumber();
            if (canResolve < 0) {
                HawkLog.errPrintln("onHomeResolve can't resolve playerId:{} maxMapLevelNumber:{} effectWareHouse:{} maxNumber:{}", player.getId(), maxMapLevelNumber, effectWareHouse, typeCfg.getMaxNumber());
                continue;
            }
            if (cfg.getLevel() == maxLevel && itemCount > canResolve) {
                HawkLog.errPrintln("onHomeResolve maxLevel invalid playerId:{} itemCount:{} maxLevel:{} canResolve:{}", player.getId(), itemCount, maxLevel, canResolve);
                continue;
            }
            List<ItemInfo> resolveItems = ItemInfo.valueListOf(cfg.getBuildRecovery(), itemCount);
            awardItems.addItemInfos(resolveItems);
            component.removeWareHouse(buildCfgId, itemCount);
            logHomeLandResolveBuild(player, buildCfgId, itemCount);
            HawkLog.logPrintln("onHomeResolve playerId:{} resolve id:{},count:{}", player.getId(), buildCfgId, itemCount);
        }
        if (!awardItems.getAwardItems().isEmpty()) {
            //计算回收积分,可能超过25W
            awardItems.setCountCheck(false);
            awardItems.rewardTakeAffectAndPush(player, Action.HOME_LAND_EXCHANGE_AWARD, true);
            component.notifyWareHouseChanged();
        }
        player.responseSuccess(protocol.getType());
        return true;
    }

    /**
     * 兑换
     *
     * @param protocol
     * @return
     */
    @ProtocolHandler(code = HP.code2.HOME_BUILDING_EXCHANGE_BUILD_REQ_VALUE)
    public boolean onHomeExchange(HawkProtocol protocol) {
        if (!funcUnlocked) {
            sendError(protocol.getType(), Status.HOMELANDError.HOME_LAND_UNLOCK);
            return false;
        }
        HomeLandBuildExchangeReq req = protocol.parseProtocol(HomeLandBuildExchangeReq.getDefaultInstance());
        PlayerHomeLandEntity homeLandEntity = player.getData().getHomeLandEntity();
        HomeLandComponent component = homeLandEntity.getComponent();
        HomeLandBuildPointShopCfg cfg = HawkConfigManager.getInstance().getConfigByKey(HomeLandBuildPointShopCfg.class, req.getCfgId());
        if (cfg == null) {
            sendError(protocol.getType(), Status.SysError.CONFIG_ERROR);
            return false;
        }
        if (req.getCount() <= 0 || req.getCount() > cfg.getTimes()) {
            sendError(protocol.getType(), Status.SysError.PARAMS_INVALID);
            return false;
        }
        // 判断道具足够否
        int eCount = component.getResolveComp().getExchangeItemMap().getOrDefault(req.getCfgId(), 0);
        if (eCount + req.getCount() > cfg.getTimes()) {
            sendError(protocol.getType(), Status.SysError.PARAMS_INVALID);
            return false;
        }
        ConsumeItems consume = ConsumeItems.valueOf();
        List<ItemInfo> needItem = ItemInfo.valueListOf(cfg.getNeedItem(), req.getCount());
        consume.addConsumeInfo(needItem, false);
        if (!consume.checkConsume(player, protocol.getType())) {
            sendError(protocol.getType(), Status.Error.ITEM_NOT_ENOUGH);
            return false;
        }
        consume.consumeAndPush(player, Action.HOME_LAND_EXCHANGE_COST);
        AwardItems awardItems = AwardItems.valueOf();
        List<ItemInfo> gainItem = ItemInfo.valueListOf(cfg.getGainItem(), req.getCount());
        awardItems.addItemInfos(gainItem);
        awardItems.rewardTakeAffectAndPush(player, Action.HOME_LAND_EXCHANGE_AWARD, true);
        //增加兑换次数
        component.getResolveComp().getExchangeItemMap().put(req.getCfgId(), eCount + req.getCount());
        component.notifyResolveChange();
        player.responseSuccess(protocol.getType());
        return true;
    }

    /**
     * 兑换提醒
     *
     * @param protocol
     * @return
     */
    @ProtocolHandler(code = HP.code2.HOME_BUILDING_EXCHANGE_TIP_REQ_VALUE)
    public boolean onHomeExchangeTip(HawkProtocol protocol) {
        if (!funcUnlocked) {
            sendError(protocol.getType(), Status.HOMELANDError.HOME_LAND_UNLOCK);
            return false;
        }
        HomelandBuildExchangeTipReq req = protocol.parseProtocol(HomelandBuildExchangeTipReq.getDefaultInstance());
        PlayerHomeLandEntity homeLandEntity = player.getData().getHomeLandEntity();
        HomeLandComponent component = homeLandEntity.getComponent();
        component.getResolveComp().onTip(req.getTipsList());
        component.notifyResolveChange();
        player.responseSuccess(protocol.getType());
        return true;
    }
}
