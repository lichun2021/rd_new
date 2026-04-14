package com.hawk.game.service.xqhxWar.season.data;

import com.alibaba.fastjson.JSON;
import org.hawk.os.HawkOSOperator;

import java.util.HashSet;
import java.util.Set;

public class XQHXSeasonPlayerData {
    public String playerId;
    public long teamScore;
    public Set<Integer> teamRewarded;

    public XQHXSeasonPlayerData() {

    }

    public XQHXSeasonPlayerData(String playerId) {
        this.playerId = playerId;
        this.teamRewarded = new HashSet<>();
    }

    public String serialize() {
        return JSON.toJSONString(this);
    }

    public static XQHXSeasonPlayerData unSerialize(String json) {
        if (HawkOSOperator.isEmptyString(json)) {
            return null;
        }
        return JSON.parseObject(json, XQHXSeasonPlayerData.class);
    }
}
