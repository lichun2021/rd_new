package com.hawk.game.module.homeland.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hawk.game.player.hero.SerializJsonStrAble;
import com.hawk.game.protocol.HomeLand;
import com.hawk.serialize.string.SerializeHelper;

import java.util.*;

public class HomeLandNpcActive implements SerializJsonStrAble {
    private String playerId;
    private final Map<String, Long> activeNpcMap = new HashMap<>();

    public HomeLandNpcActive() {
    }

    public static HomeLandNpcActive valueOf(String playerId) {
        HomeLandNpcActive npc = new HomeLandNpcActive();
        npc.playerId = playerId;
        return npc;
    }

    /**
     * 序列化
     */
    @Override
    public String serializ() {
        JSONObject obj = new JSONObject();
        obj.put("playerId", this.getPlayerId());
        obj.put("npcList", this.getActiveNpcMap());
        return obj.toString();
    }

    @Override
    public void mergeFrom(String serialiedStr) {
        HomeLandNpcActive result = JSON.parseObject(serialiedStr, HomeLandNpcActive.class);
        this.setPlayerId(result.getPlayerId());
        this.getActiveNpcMap().putAll(result.getActiveNpcMap());
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public Map<String, Long> getActiveNpcMap() {
        return activeNpcMap;
    }

    /**
     * 当前时间之前的npc是否互动
     * @param npcNum
     * @return
     */
    public boolean isActive(int npcNum) {
        return activeNpcMap.size() >= npcNum;
    }

    public void addNpc(HomeLand.HomeLandNpcPB npc) {
        activeNpcMap.put(npc.getUuid(), npc.getCreateTime());
    }
}
