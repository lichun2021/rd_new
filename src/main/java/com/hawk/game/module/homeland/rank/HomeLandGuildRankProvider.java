package com.hawk.game.module.homeland.rank;

import com.hawk.game.entity.GuildInfoObject;
import com.hawk.game.module.homeland.cfg.HomeLandRankCfg;
import com.hawk.game.service.GuildService;
import com.hawk.game.util.GsConst;
import com.hawk.util.TimeUtil;
import org.hawk.config.HawkConfigManager;
import org.hawk.config.iterator.ConfigIterator;
import org.hawk.os.HawkException;
import org.hawk.os.HawkOSOperator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 家园联盟榜
 *
 * @author zhy
 */
public class HomeLandGuildRankProvider {
    private final Map<HomeLandRankType, Map<String, HomeLandRankImpl>> rankList = new ConcurrentHashMap<>();

    public void init() {
        try {
            ConfigIterator<HomeLandRankCfg> ranks = HawkConfigManager.getInstance().getConfigIterator(HomeLandRankCfg.class);
            for (HomeLandRankCfg rankCfg : ranks) {
                if (rankCfg.getRange() != HomeLandRankServerType.GUILD.getType()) {
                    continue;
                }
                rankList.computeIfAbsent(
                        HomeLandRankType.getValue(rankCfg.getType()),
                        k -> new HashMap<>()
                );
            }
        } catch (Exception e) {
            HawkException.catchException(e);
        }
    }

    public void onTick(long curTime) {
        long beginningOfInterval = TimeUtil.getHourTime(curTime);
        for (Map<String, HomeLandRankImpl> rankMap : rankList.values()) {
            for (Map.Entry<String, HomeLandRankImpl> rankEntry : rankMap.entrySet()) {
                HomeLandRankImpl rank = rankEntry.getValue();
                String guildId = rankEntry.getKey();
                boolean exist = GuildService.getInstance().isGuildExist(guildId);
                if (!exist) {
                    continue;
                }
                GuildInfoObject guildInfo = GuildService.getInstance().getGuildInfoObject(guildId);
                if (guildInfo != null && guildInfo.getActiveType() != null && guildInfo.getActiveType().equals(GsConst.GuildActiveType.DEADB)) {
                    continue;
                }
                if (rank.getLastRankRefreshTime() >= beginningOfInterval) {
                    continue;
                }
                rank.doRankSort();
                rank.refreshNpc(curTime);
                rank.setLastRankRefreshTime(beginningOfInterval);
                return;
            }
        }
    }

    public HomeLandRankImpl getRankByType(HomeLandRankType type, String guildId) {
        if (rankList.containsKey(type)) {
            if (HawkOSOperator.isEmptyString(guildId)) {
                return null;
            }
            Map<String, HomeLandRankImpl> guildRankImplMap = rankList.get(type);
            guildRankImplMap.computeIfAbsent(guildId,
                    k -> {
                        HomeLandGuildRankImpl guildRank = new HomeLandGuildRankImpl(HomeLandRankCfg.getByType(type.getType(),
                                HomeLandRankServerType.GUILD.getType()), guildId);
                        guildRank.loadRank();
                        return guildRank;
                    }
            );
            return guildRankImplMap.get(guildId);
        }
        return null;
    }

    public void updateRank(HomeLandRankType rankType, HomeLandRank param) {
        if (param.getScore() <= 0) {
            return;
        }
        try {
            HomeLandRankImpl rankImpl = getRankByType(rankType, param.getGuildId());
            if (rankImpl == null) {
                return;
            }
            rankImpl.insertIntoRank(param);
        } catch (Exception e) {
            HawkException.catchException(e);
        }
    }
}
