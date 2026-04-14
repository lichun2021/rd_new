package com.hawk.game.config;

import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;
import org.hawk.log.HawkLog;
import org.hawk.os.HawkTime;

@HawkConfigManager.XmlResource(file = "xml/xqhx_league_time.xml")
public class XQHXSeasonTimeCfg extends HawkConfigBase {
    /** 活动期数*/
    @Id
    private final int termId;
    /** 赛季开始时间*/
    private final String seasonStartTime;
    /** 入围赛结束时间*/
    private final String qualifierEndTime;
    /** 排名赛结束时间*/
    private final String rankingEndTime;
    /** 赛季结束时间*/
    private final String seasonEndTime;

    private final int qualifierTermId;
    private final int rankingTermId;
    private final int endTermId;

    /** 赛季开始时间戳*/
    private long seasonStartTimeValue;
    /** 入围赛结束时间戳*/
    private long qualifierEndTimeValue;
    /** 排名赛结束时间戳*/
    private long rankingEndTimeValue;
    /** 赛季结束时间戳*/
    private long seasonEndTimeValue;

    public XQHXSeasonTimeCfg(){
        termId = 0;
        seasonStartTime = "";
        qualifierEndTime = "";
        rankingEndTime = "";
        seasonEndTime = "";
        qualifierTermId = 0;
        rankingTermId = 0;
        endTermId = 0;
    }

    @Override
    protected boolean assemble() {
        seasonStartTimeValue = HawkTime.parseTime(seasonStartTime);
        qualifierEndTimeValue = HawkTime.parseTime(qualifierEndTime);
        rankingEndTimeValue = HawkTime.parseTime(rankingEndTime);
        seasonEndTimeValue = HawkTime.parseTime(seasonEndTime);
        return true;
    }

    @Override
    protected boolean checkValid() {
        if (termId < 0 || seasonStartTimeValue <= 0 || qualifierEndTimeValue <= 0 || rankingEndTimeValue <= 0 || seasonEndTimeValue <= 0){
            HawkLog.errPrintln("XQHXSeasonTimeCfg checkValid time error termId:{} seasonStartTime:{} qualifierEndTime:{} rankingEndTime:{} seasonEndTime:{}", termId, seasonStartTime, qualifierEndTime, rankingEndTime, seasonEndTime);
            return false;
        }
        // 赛季开始时间戳 < 入围赛结束时间戳 < 排名赛结束时间戳 <赛季结束时间戳
        if (seasonStartTimeValue >= qualifierEndTimeValue || qualifierEndTimeValue >= rankingEndTimeValue || rankingEndTimeValue >= seasonEndTimeValue){
            HawkLog.errPrintln("XQHXSeasonTimeCfg checkValid time error termId:{} seasonStartTime:{} qualifierEndTime:{} rankingEndTime:{} seasonEndTime:{}", termId, seasonStartTime, qualifierEndTime, rankingEndTime, seasonEndTime);
            return false;
        }
        return true;
    }

    public int getTermId() {
        return termId;
    }

    public int getQualifierTermId() {
        return qualifierTermId;
    }

    public int getRankingTermId() {
        return rankingTermId;
    }

    public int getEndTermId() {
        return endTermId;
    }

    public long getSeasonStartTimeValue() {
        return seasonStartTimeValue;
    }

    public long getQualifierEndTimeValue() {
        return qualifierEndTimeValue;
    }

    public long getRankingEndTimeValue() {
        return rankingEndTimeValue;
    }

    public long getSeasonEndTimeValue() {
        return seasonEndTimeValue;
    }
}
