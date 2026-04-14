package com.hawk.game.module.armour.entity;

import com.alibaba.fastjson.JSONObject;
import com.hawk.game.config.ArmourConstCfg;
import com.hawk.game.player.hero.SerializJsonStrAble;
import com.hawk.game.protocol.Armour.ArmourImmortReshapeInfo;
/**
 * 不朽重塑升级表
 * @author zhy
 *
 */
public class ArmourImmortReshapeLevel implements SerializJsonStrAble {
    private int pos;
    private int exp;
    private int level;

    public ArmourImmortReshapeLevel() {
    }

    public ArmourImmortReshapeLevel(int pos) {
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void immortLevelUp(int level, int cost) {
        int afterExp = exp + ArmourConstCfg.getInstance().getRandomEXP();
        if (afterExp >= cost) {
            this.level = level;
            this.exp = 0;
        } else {
            this.exp = afterExp;
        }
    }

    @Override
    public String serializ() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("exp", this.exp);
        jsonObject.put("level", this.level);
        jsonObject.put("pos", this.pos);
        return jsonObject.toJSONString();
    }

    @Override
    public void mergeFrom(String serialiedStr) {
        JSONObject obj = JSONObject.parseObject(serialiedStr);
        if (obj == null) {
            return;
        }
        if (obj.containsKey("pos")) {
            this.pos = obj.getInteger("pos");
        }
        if (obj.containsKey("exp")) {
            this.exp = obj.getInteger("exp");
        }
        if (obj.containsKey("level")) {
            this.level = obj.getInteger("level");
        }
    }

    @Override
    public String toString() {
        return "ArmourImmortReshapeLevel{" +
                "pos=" + pos +
                ", exp=" + exp +
                ", level=" + level +
                '}';
    }
    public ArmourImmortReshapeInfo build(){
        ArmourImmortReshapeInfo.Builder reshapeInfo = ArmourImmortReshapeInfo.newBuilder();
        reshapeInfo.setPos(pos);
        reshapeInfo.setReShapeExp(exp);
        reshapeInfo.setReShapeLevel(level + 1);
        return reshapeInfo.build();
    }
}
