package com.hawk.game.service.xqhxWar.season.data;

import com.alibaba.fastjson.JSON;
import org.hawk.os.HawkOSOperator;
/**
 * 先驱回响赛季数据
 *
 * @author sjs
 */
public class XQHXSeasonData {
    public String teamId;
    public long score;
    public long rankingScore;
    public long totalScore;
    public int winCnt;
    public int loseCnt;
    public int rankingWinCnt;
    public int rankingLoseCnt;
    public int qualifierRank;
    public int rank;
    public int group;
    public boolean isNew;


    public String serialize() {
        return JSON.toJSONString(this);
    }

    public static XQHXSeasonData unSerialize(String json) {
        if (HawkOSOperator.isEmptyString(json)) {
            return null;
        }
        return JSON.parseObject(json, XQHXSeasonData.class);
    }
}
