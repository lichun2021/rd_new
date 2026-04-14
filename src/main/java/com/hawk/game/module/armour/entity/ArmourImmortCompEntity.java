package com.hawk.game.module.armour.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hawk.game.cfgElement.ArmourImmortEffObject;
import com.hawk.game.config.ArmourConstCfg;
import com.hawk.game.module.armour.cfg.ArmourImmortRandomAttrCfg;
import com.hawk.game.player.hero.SerializJsonStrAble;
import com.hawk.game.protocol.Armour.ArmourImmortInfo;
import org.hawk.config.HawkConfigManager;
import org.hawk.config.iterator.ConfigIterator;
import org.hawk.os.HawkWeightFactor;

import java.util.*;

/**
 * 不朽装备
 * @author zhy
 *
 */
public class ArmourImmortCompEntity implements SerializJsonStrAble {
    /**
     * 熔铸等级
     */
    private int infinityLevel = 0;
    /**
     * 不朽核心
     */
    private boolean immortCore;
    /**
     * 熔铸等级属性
     */
    private final LinkedHashMap<Integer, ArmourImmortEffObject> infinityEffMap = new LinkedHashMap<>();
    /**
     * 重塑词条记录
     */
    private final LinkedHashMap<Integer, ArmourImmortEffObject> pendingInfinityEffMap = new LinkedHashMap<>();
    /**
     * 共鸣Id
     */
    private int echoId;

    public int getInfinityLevel() {
        return infinityLevel;
    }

    public int getEchoId() {
        return echoId;
    }

    public void setInfinityLevel(int infinityLevel) {
        this.infinityLevel = infinityLevel;
    }

    public Map<Integer, ArmourImmortEffObject> getInfinityEffMap() {
        return infinityEffMap;
    }
    /**
     * 是否熔铸中
     * @return
     */
    public boolean isIntensify() {
        return infinityLevel > 0 && infinityLevel < ArmourConstCfg.getInstance().getImmortRedLevel();
    }

    @Override
    public void mergeFrom(String serialiedStr) {
        JSONObject obj = JSONObject.parseObject(serialiedStr);
        if (obj == null) {
            return;
        }
        if (obj.containsKey("infinityLevel")) {
            this.infinityLevel = obj.getInteger("infinityLevel");
        }
        if (obj.containsKey("immortCore")) {
            this.immortCore = obj.getBoolean("immortCore");
        }
        if (obj.containsKey("infinityAttr")) {
            JSONArray arr = obj.getJSONArray("infinityAttr");
            if (arr == null) {
                return;
            }
            arr.forEach(str -> {
                ArmourImmortEffObject effObject = new ArmourImmortEffObject();
                effObject.mergeFrom(str.toString());
                infinityEffMap.put(effObject.getAttrId(), effObject);
            });
        }
        if (obj.containsKey("historyAttr")) {
            JSONArray arr = obj.getJSONArray("historyAttr");
            if (arr == null) {
                return;
            }
            arr.forEach(str -> {
                ArmourImmortEffObject effObject = new ArmourImmortEffObject();
                effObject.mergeFrom(str.toString());
                pendingInfinityEffMap.put(effObject.getAttrId(), effObject);
            });
        }
        if (obj.containsKey("echoId")) {
            echoId = obj.getInteger("echoId");
        }
    }

    @Override
    public String serializ() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("infinityLevel", infinityLevel);
        jsonObject.put("immortCore", immortCore);
        JSONArray infinityAttr = new JSONArray();
        infinityEffMap.values().stream().map(ArmourImmortEffObject::serializ).forEach(infinityAttr::add);
        jsonObject.put("infinityAttr", infinityAttr);
        JSONArray historyAttr = new JSONArray();
        pendingInfinityEffMap.values().stream().map(ArmourImmortEffObject::serializ).forEach(historyAttr::add);
        jsonObject.put("historyAttr", historyAttr);
        jsonObject.put("echoId", echoId);
        return jsonObject.toJSONString();
    }

    public boolean isImmortCore() {
        return immortCore;
    }

    public void setImmortCore(boolean immortCore) {
        this.immortCore = immortCore;
    }

    public ArmourImmortInfo toBuilder() {
        ArmourImmortInfo.Builder builder = ArmourImmortInfo.newBuilder();
        builder.setImmortLevel(infinityLevel);
        builder.setIsImmortCore(immortCore);
        infinityEffMap.values().forEach(v -> builder.addImmortAttr(v.toBuilder()));
        pendingInfinityEffMap.values().forEach(v -> builder.addPendingAttr(v.toBuilder()));
        builder.setEchoId(echoId);
        return builder.build();
    }

    public void setEchoId(int echoId) {
        this.echoId = echoId;
    }

    public Map<Integer, ArmourImmortEffObject> getPendingInfinityEffMap() {
        return pendingInfinityEffMap;
    }

}
