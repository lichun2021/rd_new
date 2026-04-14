package com.hawk.game.module.findStoreHouse;

import com.hawk.activity.ActivityBase;
import com.hawk.activity.ActivityManager;
import com.hawk.activity.type.ActivityState;
import com.hawk.activity.type.impl.findStoreHouse.FindStoreHouseActivity;
import com.hawk.activity.type.impl.findStoreHouse.cfg.FindStoreHouseBuildCfg;
import com.hawk.game.config.ConstProperty;
import com.hawk.game.module.findStoreHouse.worldmarch.FindStoreHousePoint;
import com.hawk.game.protocol.Activity;
import com.hawk.game.protocol.World;
import com.hawk.game.util.GameUtil;
import com.hawk.game.util.GsConst;
import com.hawk.game.util.WorldUtil;
import com.hawk.game.world.WorldPoint;
import com.hawk.game.world.object.AreaObject;
import com.hawk.game.world.object.Point;
import com.hawk.game.world.proxy.WorldPointProxy;
import com.hawk.game.world.service.WorldPointService;
import org.hawk.app.HawkAppObj;
import org.hawk.config.HawkConfigManager;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkTime;
import org.hawk.os.HawkWeightFactor;
import org.hawk.xid.HawkXID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class FindStoreHouseService extends HawkAppObj {
    /**
     * 日志对象
     */
    public static Logger logger = LoggerFactory.getLogger("Server");
    /**
     * 单例对象
     */
    private static FindStoreHouseService instance = null;

    /**
     * 获取单例对象
     *
     * @return
     */
    public static FindStoreHouseService getInstance() {
        return instance;
    }


    /**
     * 上次tick时间
     */
    public long lastTickTime;

    public FindStoreHouseService(HawkXID xid) {
        super(xid);
        instance = this;
    }

    /**
     * 世界上资源列表
     */
    public Map<Integer, WorldPoint> resources;

    /**
     * 初始化
     */
    public boolean init() {
        resources = new ConcurrentHashMap<>();
        List<WorldPoint> points = WorldPointService.getInstance().getWorldPointsByType(World.WorldPointType.TREASURE_386);
        for (WorldPoint point : points) {
            resources.put(point.getId(), point);
        }
        lastTickTime = HawkTime.getMillisecond();
        return true;
    }

    @Override
    public boolean onTick() {
        if (HawkTime.getMillisecond() - lastTickTime < 10000) {
            return true;
        }

        lastTickTime = HawkTime.getMillisecond();
        // 移除过期资源
        List<WorldPoint> rmRes = new ArrayList<>();
        for (WorldPoint res : resources.values()) {
            FindStoreHousePoint findStoreHousePoint = (FindStoreHousePoint) res;
            FindStoreHouseBuildCfg cfg = HawkConfigManager.getInstance().getConfigByKey(FindStoreHouseBuildCfg.class, findStoreHousePoint.getBuildId());
            if (cfg == null) {
                continue;
            }
            if (HawkTime.getMillisecond() - res.getLifeStartTime() < cfg.getLive() * 1000L) {
                continue;
            }
            if (!HawkOSOperator.isEmptyString(res.getPlayerId())) {
                continue;
            }
            rmRes.add(res);
        }

        for (WorldPoint point : rmRes) {
            notifyResRemove(point.getId(), null, null);
            WorldPointService.getInstance().removeWorldPoint(point.getId(), false);
        }
        WorldPointProxy.getInstance().batchDelete(rmRes);
        return true;
    }

    /**
     * 获取玩家宝箱
     */
    public List<WorldPoint> getTreasureList(String playerId) {
        return resources.values().stream().filter(v -> v.getOwnerId().equals(playerId) || v.getPlayerId().equals(playerId)).collect(Collectors.toList());
    }
    /**
     * 获取玩家占领中的宝库数量
     */
    public long getOccupyTreasureCount(String playerId) {
        return resources.values().stream().filter(v ->v.getPlayerId().equals(playerId)).count();
    }

    /**
     * 触发世界生成资源
     */
    public void touchCreateResource(int terminalId, String ownerId, int createCount, HawkWeightFactor<Integer> treasureBuildId) {
        // 生成点列表
        List<WorldPoint> createPoints = new ArrayList<>();
        for (int i = 0; i < createCount; i++) {
            WorldPoint point = createResource(terminalId, ownerId, treasureBuildId.randomObj());
            if (point == null) {
                continue;
            }
            createPoints.add(point);
        }
        WorldPointProxy.getInstance().batchCreate(createPoints);
    }

    /**
     * 生成资源
     */
    private WorldPoint createResource(int terminalId, String ownerId, int treasureId) {
        FindStoreHouseBuildCfg buildCfg = HawkConfigManager.getInstance().getConfigByKey(FindStoreHouseBuildCfg.class, treasureId);
        if (buildCfg == null) {
            logger.info("buildCfg failed randomPool:{}", treasureId);
            return null;
        }
        String disCfgStr = ConstProperty.getInstance().getWorldEnemyDistance();
        String[] disCfg = disCfgStr.split("_");
        int minDis = Integer.parseInt(disCfg[0]);  //2距离范围
        int[] pos = GameUtil.splitXAndY(terminalId);
        int maxDis = 200;
        List<Point> points = WorldUtil.searchFreeNearPointPos(pos, minDis, maxDis);
        for (Point point : points) {
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
            // 创建世界点对象
            FindStoreHousePoint worldPoint = new FindStoreHousePoint(point.getX(), point.getY(), point.getAreaId(), point.getZoneId(), World.WorldPointType.TREASURE_386_VALUE);
            worldPoint.setResourceId(buildCfg.getThirdBuildId());
            worldPoint.setOwnerId(ownerId);
            worldPoint.setBuildId(buildCfg.getBuildId());
            worldPoint.setLifeStartTime(HawkTime.getMillisecond());
            WorldPointService.getInstance().addPoint(worldPoint);
            resources.put(worldPoint.getId(), worldPoint);
            logger.info("createTreasure386Res, x:{}, y:{}, areaId:{}, resId:{}", point.getX(), point.getY(), point.getAreaId(), treasureId);
            return worldPoint;
        }
        return null;
    }

    /**
     * 通知资源移除
     */
    public void notifyResRemove(int pointId, String playerId, String marchId) {
        WorldPointService.getInstance().removeWorldPoint(pointId);
        resources.remove(pointId);
        int[] pos = GameUtil.splitXAndY(pointId);
        logger.info("notify386ResRemove, posX:{}, posY:{}, playerId:{}, marchId:{}, currentCount:{}", pos[0], pos[1], playerId, marchId, resources.size());
    }

    /**
     * 活动是否开放
     */
    public boolean isActivityOpen() {
        return getActivityObject() != null;
    }

    /**
     * 获取活动对象
     */
    public FindStoreHouseActivity getActivityObject() {
        Optional<ActivityBase> activityOp = ActivityManager.getInstance().getGameActivityByType(Activity.ActivityType.FIND_STORE_HOUSE_VALUE);
        if (!activityOp.isPresent()) {
            return null;
        }

        ActivityBase activity = activityOp.get();
        if ((activity.isInvalid() || activity.getActivityEntity().getActivityState() != ActivityState.OPEN)) {
            return null;
        }

        return (FindStoreHouseActivity) activity;
    }
}
