package com.hawk.game.module.homeland.entity;

import com.alibaba.fastjson.JSONObject;
import com.hawk.game.module.homeland.cfg.HomeLandBuildPointShopCfg;
import com.hawk.game.player.hero.SerializJsonStrAble;
import com.hawk.game.protocol.HomeLand;
import com.hawk.serialize.string.SerializeHelper;
import org.hawk.config.HawkConfigManager;
import org.hawk.os.HawkTime;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class HLResolveComp implements SerializJsonStrAble {

    private Map<Integer, Integer> exchangeItemMap = new ConcurrentHashMap<>();
    private Set<Integer> playerPoints = new HashSet<>();
    private long lastWeekTime;

    /**
     * 序列化
     */
    @Override
    public String serializ() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("exchange", SerializeHelper.mapToString(exchangeItemMap));
        String playerPoint = SerializeHelper.collectionToString(this.playerPoints, SerializeHelper.BETWEEN_ITEMS);
        jsonObject.put("playerPoint", playerPoint);
        jsonObject.put("lastWeekTime", lastWeekTime);
        return jsonObject.toString();
    }

    @Override
    public void mergeFrom(String serialiedStr) {
        JSONObject obj = JSONObject.parseObject(serialiedStr);
        if (obj == null) {
            return;
        }
        if (obj.containsKey("exchange")) {
            String exchangeItems = obj.getString("exchange");
            this.exchangeItemMap = SerializeHelper.stringToMap(exchangeItems);
        }
        if (obj.containsKey("playerPoint")) {
            String playerPoint = obj.getString("playerPoint");
            playerPoints = SerializeHelper.stringToSet(Integer.class, playerPoint, SerializeHelper.BETWEEN_ITEMS);
        }
        if (obj.containsKey("lastWeekTime")) {
            lastWeekTime = obj.getLong("lastWeekTime");
        }
    }

    public Map<Integer, Integer> getExchangeItemMap() {
        return exchangeItemMap;
    }

    public Set<Integer> getPlayerPoints() {
        return playerPoints;
    }

    public void onTip(List<HomeLand.HomelandBuildGeneralExchangeTip> tips) {
        for (HomeLand.HomelandBuildGeneralExchangeTip tip : tips) {
            HomeLandBuildPointShopCfg cfg = HawkConfigManager.getInstance().getConfigByKey(HomeLandBuildPointShopCfg.class, tip.getId());
            if (cfg == null) {
                continue;
            }
            switch (tip.getTip()) {
                case 0: {
                    playerPoints.add(tip.getId());
                }
                break;
                case 1: {
                    playerPoints.remove(tip.getId());
                }
                break;
            }
        }
    }

    /**
     * 每周一刷新
     */
    public void refresh(long now) {
        if (now > lastWeekTime) {
            exchangeItemMap.clear();
            lastWeekTime = nextWeekTime();
        }
    }

    private final long WEEK_BASE = 1 * 60 * 60 * 24 * 7 * 1000;

    private long nextWeekTime() {
        //下周一0点时间
        return HawkTime.getFirstDayOfCurWeek().getTime() + WEEK_BASE;
    }
}
