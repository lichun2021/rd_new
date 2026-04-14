package com.hawk.game.module.findStoreHouse.worldmarch;

import com.google.protobuf.ByteString;
import com.hawk.activity.type.impl.findStoreHouse.cfg.FindStoreHouseBuildCfg;
import com.hawk.game.protocol.ActityFindStoreHouse;
import com.hawk.game.protocol.ActityFindStoreHouse.OccupyTreasureInfoPB;
import com.hawk.game.protocol.World;
import com.hawk.game.protocol.WorldPoint.PBSerializeData;
import com.hawk.game.protocol.WorldPoint.PointData;
import com.hawk.game.service.GuildService;
import com.hawk.game.world.WorldMarch;
import com.hawk.game.world.WorldMarchService;
import com.hawk.game.world.WorldPoint;
import com.hawk.game.world.march.IWorldMarch;
import com.hawk.game.world.service.WorldPointService;
import org.hawk.config.HawkConfigManager;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkTime;
import org.hawk.serializer.HawkSerializer;

public class FindStoreHousePoint extends WorldPoint {
    private int buildId;

    public FindStoreHousePoint() {
    }

    public FindStoreHousePoint(int x, int y, int areaId, int zoneId, int pointType) {
        super(x, y, areaId, zoneId, pointType);
    }

    public void onTick() {

    }

    @Override
    public World.WorldPointPB.Builder toBuilder(World.WorldPointPB.Builder builder, String viewerId) {
        super.toBuilder(builder, viewerId);
        builder.setStrongpointId(this.getBuildId());
        if (!HawkOSOperator.isEmptyString(this.getMarchId())) {
            WorldMarch march = WorldMarchService.getInstance().getWorldMarch(this.getMarchId());
            // 并发情况下可能导致march为null
            if (march != null) {
                builder.setLastActiveTime(march.getResEndTime() - HawkTime.getMillisecond());
            }
        } else {
            FindStoreHouseBuildCfg cfg = HawkConfigManager.getInstance().getConfigByKey(FindStoreHouseBuildCfg.class, this.getResourceId());
            if (cfg != null) {
                builder.setLastActiveTime(300);
            }
        }
        if (!HawkOSOperator.isEmptyString(viewerId) && viewerId.equals(this.getPlayerId())) {
            builder.setHasMarchStop(true);
        }
        return builder;
    }

    @Override
    public World.WorldPointDetailPB.Builder toDetailBuilder(String viewerId) {
        if (!HawkOSOperator.isEmptyString(getPlayerId())
                && !HawkOSOperator.isEmptyString(getMarchId())) {
            IWorldMarch march = WorldMarchService.getInstance().getMarch(getMarchId());
            if (march == null) {
                WorldPointService.getInstance().removeWorldPoint(getId());
            }
        }
        World.WorldPointDetailPB.Builder builder = super.toDetailBuilder(viewerId);
        builder.setStrongpointId(this.getBuildId());
        if (!HawkOSOperator.isEmptyString(this.getMarchId())) {
            WorldMarch march = WorldMarchService.getInstance().getWorldMarch(this.getMarchId());
            // 并发情况下可能导致march为null
            if (march != null) {
                builder.setLastActiveTime(march.getResEndTime() - HawkTime.getMillisecond());
            }
        } else {
            FindStoreHouseBuildCfg cfg = HawkConfigManager.getInstance().getConfigByKey(FindStoreHouseBuildCfg.class, this.getResourceId());
            if (cfg != null) {
                builder.setLastActiveTime(300);
            }
        }
        if (!HawkOSOperator.isEmptyString(viewerId) && viewerId.equals(this.getPlayerId())) {
            builder.setHasMarchStop(true);
        }
        return builder;
    }

    @Override
    public PointData.Builder buildPointData() {
        PointData.Builder builder = super.buildPointData();
        PBSerializeData.Builder extraBuilder = PBSerializeData.newBuilder();
        extraBuilder.setParam1(serialize(buildId));
        builder.setExtryData(extraBuilder.build());
        return builder;
    }

    @Override
    public void mergeFromPointData(PointData.Builder builder) {
        super.mergeFromPointData(builder);
        this.mergeFrom(builder.getExtryData());
    }

    public void mergeFrom(PBSerializeData data) {
        this.buildId = deserialize(data.getParam1(), int.class);
    }

    public int getBuildId() {
        return buildId;
    }

    public void setBuildId(int buildId) {
        this.buildId = buildId;
    }

    private <T> ByteString serialize(T value) {
        return ByteString.copyFrom(HawkSerializer.serialize(value));
    }

    private <T> T deserialize(ByteString bytes, Class<T> type) {
        return HawkSerializer.deserialize(bytes.toByteArray(), type);
    }

    public OccupyTreasureInfoPB.Builder toTreasureBuilder(long starTime) {
        String playerId = getPlayerId();
        OccupyTreasureInfoPB.Builder infoBuilder = OccupyTreasureInfoPB.newBuilder();
        infoBuilder.setTime(getLifeStartTime());
        infoBuilder.setPointId(getId());
        infoBuilder.setResourceId(getBuildId());
        infoBuilder.setOwnerId(getOwnerId());
        if (!HawkOSOperator.isEmptyString(playerId)) {
            infoBuilder.setOccupyId(playerId);
            infoBuilder.setOccupyName(getPlayerName());
            infoBuilder.setOccupyStart(starTime);
            String guildId = GuildService.getInstance().getPlayerGuildId(playerId);
            if (GuildService.getInstance().isGuildExist(guildId)) {
                infoBuilder.setGuildId(guildId);
            }
        }
        infoBuilder.setStatus(HawkOSOperator.isEmptyString(playerId) ? ActityFindStoreHouse.OccupyState.IDLE : ActityFindStoreHouse.OccupyState.OCCUPIED);
        return infoBuilder;
    }
}
