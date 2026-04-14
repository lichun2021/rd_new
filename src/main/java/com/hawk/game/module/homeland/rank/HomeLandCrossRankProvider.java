package com.hawk.game.module.homeland.rank;

import com.hawk.game.module.homeland.cfg.HomeLandRankCfg;
import org.hawk.config.HawkConfigManager;
import org.hawk.config.iterator.ConfigIterator;
import org.hawk.os.HawkException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 家园榜全服榜
 *
 * @author zhy
 */
public class HomeLandCrossRankProvider {
    private final Map<HomeLandRankType, HomeLandRankImpl> rankList = new ConcurrentHashMap<>();

    public void init() {
        try {
            ConfigIterator<HomeLandRankCfg> ranks = HawkConfigManager.getInstance().getConfigIterator(HomeLandRankCfg.class);
            for (HomeLandRankCfg rankCfg : ranks) {
                if (rankCfg.getRange() != HomeLandRankServerType.CROSS.getType()) {
                    continue;
                }
                rankList.computeIfAbsent(
                        HomeLandRankType.getValue(rankCfg.getType()),
                        k1 -> {
                            HomeLandCrossRankImpl crossRank = new HomeLandCrossRankImpl(rankCfg, "");
                            crossRank.loadRank();
                            return crossRank;
                        }
                );
            }
        } catch (Exception e) {
            HawkException.catchException(e);
        }
    }

    public HomeLandRankImpl getRankByType(HomeLandRankType type) {
        if (rankList.containsKey(type)) {
            return rankList.get(type);
        }
        return null;
    }

    public void onTick(long curTime) {
        for (HomeLandRankImpl rank : rankList.values()) {
            rank.doRankSort();
            rank.refreshNpc(curTime);
        }
    }

    public void updateRank(HomeLandRankType rankType, HomeLandRank param) {
        if (param.getScore() <= 0) {
            return;
        }
        try {
            HomeLandRankImpl rankImpl = getRankByType(rankType);
            if (rankImpl == null) {
                return;
            }
            rankImpl.insertIntoRank(param);
        } catch (Exception e) {
            HawkException.catchException(e);
        }
    }
}
