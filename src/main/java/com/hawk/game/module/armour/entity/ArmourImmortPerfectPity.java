package com.hawk.game.module.armour.entity;

import com.alibaba.fastjson.JSONObject;
import com.hawk.game.module.armour.cfg.ArmourImmortRandomMaxCfg;
import com.hawk.game.player.hero.SerializJsonStrAble;
import org.hawk.os.HawkRand;

/**
 * 不朽重塑属性保底
 * @author zhy
 *
 */
public class ArmourImmortPerfectPity implements SerializJsonStrAble {
    private int pos;
    private int perfectCount;
    private int pityCounter;
    private int threshold;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public ArmourImmortPerfectPity() {
    }

    public ArmourImmortPerfectPity(int pos) {
        this.pos =pos;
    }

    public int getPerfectCount() {
        return perfectCount;
    }

    public void setPerfectCount(int perfectCount) {
        this.perfectCount = perfectCount;
    }

    public int getPityCounter() {
        return pityCounter;
    }

    public void setPityCounter(int pityCounter) {
        this.pityCounter = pityCounter;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public String serializ() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pos", this.pos);
        jsonObject.put("perfectCount", this.perfectCount);
        jsonObject.put("pityCounter", this.pityCounter);
        jsonObject.put("threshold", this.threshold);
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
        if (obj.containsKey("perfectCount")) {
            this.perfectCount = obj.getInteger("perfectCount");
        }
        if (obj.containsKey("pityCounter")) {
            this.pityCounter = obj.getInteger("pityCounter");
        }
        if (obj.containsKey("threshold")) {
            this.threshold = obj.getInteger("threshold");
        }
    }

    /**
     * 是否完美属性
     * @return
     */
    public boolean checkPerfect(boolean allPerfect) {
        if (allPerfect) {
            return false;
        }
        ArmourImmortRandomMaxCfg maxCfg = ArmourImmortRandomMaxCfg.getPerfect(this.perfectCount);
        if (this.threshold <= 0) {
            int min = maxCfg.getControlDown();
            int max = maxCfg.getControlUp();
            this.threshold = HawkRand.randInt(min, max);
        }
        pityCounter++;
        if (pityCounter >= this.threshold) {
            int min = maxCfg.getControlDown();
            int max = maxCfg.getControlUp();
            this.threshold = HawkRand.randInt(min, max);
            this.perfectCount++;
            pityCounter = 0;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "ArmourImmortPerfectPity{" +
                "pos=" + pos +
                ", perfectCount=" + perfectCount +
                ", pityCounter=" + pityCounter +
                ", threshold=" + threshold +
                '}';
    }
}
