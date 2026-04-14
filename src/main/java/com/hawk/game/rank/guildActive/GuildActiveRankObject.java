package com.hawk.game.rank.guildActive;

import com.hawk.game.entity.GuildInfoObject;
import com.hawk.game.global.LocalRedis;
import com.hawk.game.service.GuildService;
import org.hawk.os.HawkTime;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author SJS
 * @description 帮派活跃榜
 * @date 2025/9/4
 */
public class GuildActiveRankObject {

    /**
     * 排序好的榜单
     */
    List<GuildActiveRankInfo> activeRankInfo = new ArrayList<>();

    public void init() {
        loadCacheRank();
    }

    /**
     * 周期 更新缓存
     */
    public void loadCacheRank() {
        // 获取上一天的排行榜
        String dayStr = HawkTime.formatTime(HawkTime.getMillisecond() - TimeUnit.DAYS.toMillis(1) , "yyyyMMdd");
        Set<Tuple> activeValueRankList = LocalRedis.getInstance().getActiveValueRankList(getMaxRank(), dayStr);
        if (activeValueRankList == null) {
            return;
        }
        List<GuildActiveRankInfo> rankList = new ArrayList<>();
        int rank = 1;
        int rankCount = getMaxRank();
        for (Tuple tuple : activeValueRankList) {
            String guildId = tuple.getElement();
            GuildInfoObject guildInfo = GuildService.getInstance().getGuildInfoObject(guildId);
            if (guildInfo == null) {
                continue;
            }
            if (rank <= rankCount) {
                GuildActiveRankInfo rankInfo = buildRankInfo(tuple.getElement(), (long) tuple.getScore());
                rankList.add(rankInfo);
            }
            rank++;
        }
        this.activeRankInfo = rankList;
    }

    private GuildActiveRankInfo buildRankInfo(String rankKey, long score) {
        return new GuildActiveRankInfo(rankKey, score);
    }

    public List<GuildActiveRankInfo> getRankCache() {
        return activeRankInfo;
    }

    /**
     * 修改排行榜
     */
    public void updateRank(String guildId, long incrementScore) {
        String dayStr = HawkTime.formatTime(HawkTime.getMillisecond(), "yyyyMMdd");
        LocalRedis.getInstance().updateActiveValueRankScore(incrementScore, guildId, dayStr);
    }

    /**
     * 最多排多少人
     */
    public int getMaxRank() {
        return 500;
    }

    /**
     * @author SJS
     * @description 帮派活跃rankInfo
     * @date 2025/9/4
     */
    public static class GuildActiveRankInfo {
        private final String guildId;
        private final long score;

        public GuildActiveRankInfo(String guildId, long score) {
            this.guildId = guildId;
            this.score = score;
        }

        public String getGuildId() {
            return guildId;
        }

        public long getScore() {
            return score;
        }
    }
}
