package com.hawk.game.cfgElement;

import com.alibaba.fastjson.JSONObject;
import com.hawk.game.player.hero.SerializJsonStrAble;
import com.hawk.game.protocol.Armour;
import com.hawk.game.protocol.Armour.ArmourAttr;
import com.hawk.game.protocol.Const;

/**
 * 不朽作用号
 * @author zhy
 *
 */
public class ArmourImmortEffObject implements SerializJsonStrAble {
    private int attrId;

    private int effectType;

    private int effectValue;

    private boolean lock;

    public ArmourImmortEffObject() {

    }

    public ArmourImmortEffObject(int attrId, int effectType, int effectValue) {
        this.attrId = attrId;
        this.effectType = effectType;
        this.effectValue = effectValue;
    }
    public ArmourImmortEffObject(int attrId, int effectType) {
        this.attrId = attrId;
        this.effectType = effectType;
    }

    public int getAttrId() {
        return attrId;
    }

    public void setAttrId(int attrId) {
        this.attrId = attrId;
    }

    public int getEffectType() {
        return effectType;
    }

    public void setEffectType(int effectType) {
        this.effectType = effectType;
    }

    public int getEffectValue() {
        return effectValue;
    }

    public void setEffectValue(int effectValue) {
        this.effectValue = effectValue;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public Const.EffType getType() {
        return Const.EffType.valueOf(effectType);
    }

    @Override
    public String serializ() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("attrId", attrId);
        jsonObject.put("effectType", effectType);
        jsonObject.put("effectValue", effectValue);
        jsonObject.put("lock", lock);
        return jsonObject.toJSONString();
    }

    @Override
    public void mergeFrom(String serialiedStr) {
        JSONObject obj = JSONObject.parseObject(serialiedStr);
        if (obj != null) {
            if (obj.containsKey("attrId")) {
                this.attrId = obj.getInteger("attrId");
            }
            if (obj.containsKey("effectType")) {
                this.effectType = obj.getInteger("effectType");
            }
            if (obj.containsKey("effectValue")) {
                this.effectValue = obj.getInteger("effectValue");
            }
            if (obj.containsKey("lock")) {
                this.lock = obj.getBooleanValue("lock");
            }
        }
    }

    public ArmourAttr toBuilder() {
        return ArmourAttr.newBuilder()
                .setLock(isLock())
                .setAttrId(getAttrId())
                .setAttrType(getEffectType())
                .setAttrValue(getEffectValue())
                .setType(Armour.ArmourAttrType.IMMORT_EXTR)
                .build();
    }

    @Override
    public String toString() {
        return "ArmourImmortEffObject{" +
                "attrId=" + attrId +
                ", effectType=" + effectType +
                ", effectValue=" + effectValue +
                ", lock=" + lock +
                '}';
    }
}
