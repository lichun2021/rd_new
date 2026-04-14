package com.hawk.game.module.homeland.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hawk.game.player.hero.SerializJsonStrAble;
import com.hawk.game.protocol.HomeLand;
import com.hawk.game.util.GameUtil;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkTime;

public class HomeLandNpc implements SerializJsonStrAble {
    private String uuid;       //唯一id
    private int cfgId;       //配置ID，对应静态配置表
    private long createTime;      //更新时间
    private int pointId;      //位置y
    private int type;      //类型

    public HomeLandNpc(int cfgId) {
        this.cfgId = cfgId;
    }

    public HomeLandNpc() {
    }

    public static HomeLandNpc valueOf(int cfgId, int type, int pointId, long createTime) {
        HomeLandNpc npc = new HomeLandNpc();
        npc.uuid = HawkOSOperator.randomUUID();
        npc.createTime = createTime;
        npc.cfgId = cfgId;
        npc.type = type;
        npc.pointId = pointId;
        return npc;
    }

    /**
     * 序列化
     */
    @Override
    public String serializ() {
        JSONObject obj = new JSONObject();
        obj.put("uuid", this.getUuid());
        obj.put("cfgId", this.getCfgId());
        obj.put("createTime", this.getCreateTime());
        obj.put("pointId", this.getPointId());
        obj.put("type", this.getType());
        return obj.toString();
    }

    @Override
    public void mergeFrom(String serialiedStr) {
        HomeLandNpc result = JSON.parseObject(serialiedStr, HomeLandNpc.class);
        this.setUuid(result.getUuid());
        this.setCfgId(result.getCfgId());
        this.setCreateTime(result.getCreateTime());
        this.setPointId(result.getPointId());
        this.setType(result.getType());
    }

    public int getCfgId() {
        return cfgId;
    }

    public void setCfgId(int cfgId) {
        this.cfgId = cfgId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isExpire(long currentTime) {
        return HawkTime.isCrossDay(currentTime, getCreateTime(), 0);
    }

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public HomeLand.HomeLandNpcPB.Builder toBuilder() {
        int[] pos = GameUtil.splitXAndY(pointId);
        return HomeLand.HomeLandNpcPB.newBuilder()
                .setCfgId(cfgId)
                .setCreateTime(createTime)
                .setUuid(uuid)
                .setX(pos[0])
                .setY(pos[1]);
    }

    @Override
    public String toString() {
        return "HomeLandNpc{" +
                "uuid='" + uuid + '\'' +
                ", cfgId=" + cfgId +
                ", createTime=" + createTime +
                ", pointId=" + pointId +
                ", type=" + type +
                '}';
    }
}
