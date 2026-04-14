package com.hawk.game.service.xqhxWar.season;

import com.hawk.game.player.Player;
import com.hawk.game.protocol.PBCommonMatch;
import org.hawk.app.HawkAppObj;
import org.hawk.os.HawkException;
import org.hawk.tickable.HawkPeriodTickable;
import org.hawk.xid.HawkXID;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 先驱回响 赛季
 */
public class XQHXSeasonService extends HawkAppObj {
    public static XQHXSeasonService instance = null;

    public static XQHXSeasonService getInstance() {
        return instance;
    }

    public XQHXSeasonService(HawkXID xid) {
        super(xid);
        instance = this;
    }

    public boolean init() {
        try {
            XQHXSeasonManager.getInstance().init();
        } catch (Exception e) {
            HawkException.catchException(e);
        }
        addTickable(new HawkPeriodTickable(1000) {
            @Override
            public void onPeriodTick() {
                onTickPerOneSecond();
            }
        });
        addTickable(new HawkPeriodTickable(TimeUnit.MINUTES.toMillis(10)) {
            @Override
            public void onPeriodTick() {
                onTickPerTenMinute();
            }
        });
        return true;
    }

    public void onTickPerOneSecond() {
        try {
            XQHXSeasonManager.getInstance().onTickPerOneSecond();
        } catch (Exception e) {
            HawkException.catchException(e);
        }
    }

    public void onTickPerTenMinute() {
        try {
            XQHXSeasonManager.getInstance().onTickPerTenMinute();
        } catch (Exception e) {
            HawkException.catchException(e);
        }
    }

    public void pageInfo(Player player, PBCommonMatch.PBCMWPageInfoReq req) {
        XQHXSeasonManager.getInstance().pageInfo(player, req);
    }

    public void rankInfo(Player player, PBCommonMatch.PBCMWRankInfoReq req) {
        XQHXSeasonManager.getInstance().rankInfo(player, req);
    }

    public void battleInfo(Player player, PBCommonMatch.PBCMWBattleInfoReq req) {
        XQHXSeasonManager.getInstance().battleInfo(player, req);
    }

    public void timeInfo(Player player, PBCommonMatch.PBCMWBattleTimeReq req) {
        XQHXSeasonManager.getInstance().timeInfo(player, req);
    }

    public void targetInfo(Player player, PBCommonMatch.PBCMWBattleTargetReq req) {
        XQHXSeasonManager.getInstance().targetInfo(player, req);
    }


    /**
     * gm入口
     *
     * @param map gm参数
     * @return 活动信息
     */
    public String gm(Map<String, String> map) {
        return XQHXSeasonManager.getInstance().gm(map);
    }
}
