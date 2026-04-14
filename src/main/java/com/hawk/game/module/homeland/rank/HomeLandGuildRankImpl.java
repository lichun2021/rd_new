package com.hawk.game.module.homeland.rank;

import com.hawk.activity.redis.ActivityGlobalRedis;
import com.hawk.activity.redis.ActivityLocalRedis;
import com.hawk.game.global.GlobalData;
import com.hawk.game.global.RedisKey;
import com.hawk.game.module.homeland.cfg.HomeLandConstKVCfg;
import com.hawk.game.module.homeland.cfg.HomeLandRankCfg;
import com.hawk.game.module.homeland.entity.HomeLandComponent;
import com.hawk.game.module.homeland.entity.PlayerHomeLandEntity;
import com.hawk.game.player.Player;
import com.hawk.game.protocol.HomeLand;
import com.hawk.game.service.GuildService;
import com.hawk.serialize.string.SerializeHelper;
import org.hawk.config.HawkConfigManager;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkTime;
import org.hawk.redis.HawkRedisSession;
import redis.clients.jedis.Tuple;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.hawk.game.global.RedisKey.HOME_LAND_RANK;

/**
 * 家园榜
 *
 * @author zhy
 */
public class HomeLandGuildRankImpl implements HomeLandRankImpl {
    protected final List<HomeLand.HomeLandRankMsg.Builder> showRankList = new ArrayList<>();
    protected HomeLandRankCfg cfg;
    private final String guildId;
    private long lastRankRefreshTime;
    private long lastNpcTickTime;
    private final long rankMaxTime = 9999999999L;
    public HomeLandGuildRankImpl(HomeLandRankCfg cfg, String guildId) {
        this.cfg = cfg;
        this.guildId = guildId;
    }

    protected String getRedisKey() {
        return HOME_LAND_RANK + HomeLandRankType.getValue(this.cfg.getType()).name() + ":" +
                HomeLandRankServerType.getValue(this.cfg.getRange()).name() + ":" + guildId;
    }

    @Override
    public int getRefreshInterval() {
        return this.cfg.getDelay();
    }

    public void loadRank() {
        doRankSort();
    }

    protected int getRankSize() {
        return cfg.getMaxNum();
    }

    @Override
    public void insertIntoRank(HomeLandRank rank) {
        if (rank.getRankTime() <= 0) {
            Optional<HomeLand.HomeLandRankMsg.Builder> showRank = showRankList.stream().filter(v -> v.getPlayerId().equals(rank.getId())).findAny();
            showRank.ifPresent(builder -> rank.setRankTime(builder.getRankTime()));
        }
        long curSeconds = rank.getRankTime() > 0 ? rank.getRankTime() : HawkTime.getSeconds();
        String playerId = rank.getId();
        long param = rankMaxTime - curSeconds;
        String valStr = String.format("%d.%d", rank.getScore(), param);
        double val = Double.parseDouble(valStr);
        ActivityLocalRedis.getInstance().zadd(getRedisKey(), val, playerId);
    }
    private long parseRankTime(double score) {
        String integerPart = String.valueOf(score);
        String[] split = integerPart.split("\\.");
        String decimal = split.length >= 1 ? split[1] : String.valueOf(HawkTime.getSeconds());
        decimal = String.format("%-10s", decimal).replace(' ', '0');
        return rankMaxTime - Long.parseLong(decimal);
    }
    @Override
    public void doRankSort() {
        int rankSize = getRankSize();
        Set<Tuple> rankSet = ActivityLocalRedis.getInstance().zrevrange(getRedisKey(), 0, Math.max((rankSize - 1), 0));
        showRankList.clear();
        List<HomeLandRank> showRankTemp = new ArrayList<>();
        HawkRedisSession redisSession = ActivityGlobalRedis.getInstance().getRedisSession();
        List<String> playerIds = new ArrayList<>();
        for (Tuple rank : rankSet) {
            Collection<String> members = GuildService.getInstance().getGuildMembers(guildId);
            if (!members.contains(rank.getElement())) {
                ActivityLocalRedis.getInstance().zrem(getRedisKey(), rank.getElement());
                continue;
            }
            HomeLandRank homeLandRank = new HomeLandRank();
            homeLandRank.setId(rank.getElement());
            homeLandRank.setRankTime(parseRankTime(rank.getScore()));
            homeLandRank.setScore((long) rank.getScore());
            playerIds.add(rank.getElement());
            showRankTemp.add(homeLandRank);
        }
        // 玩家信息
        Map<String, HomeLandPlayerRankInfo> playerInfoMap = new ConcurrentHashMap<>();
        String[] playerIdArray = playerIds.toArray(new String[0]);
        if (playerIdArray.length > 0) {
            List<String> playerInfoList = redisSession.hmGet(RedisKey.HOME_LAND_RANK_PLAYER, playerIdArray);
            List<String> npcMap = redisSession.hmGet(HomeLandService.getInstance().getNpcRedDotRedisKey(), playerIdArray);
            for (int i = 0; i < playerIdArray.length; i++) {
                HomeLandPlayerRankInfo playerInfo = SerializeHelper.parseJsonStr(playerInfoList.get(i), HomeLandPlayerRankInfo.class);
                if (playerInfo != null) {
                    String npcNum = npcMap.get(i);
                    if (!HawkOSOperator.isEmptyString(npcNum)) {
                        playerInfo.setNpcNum(Integer.parseInt(npcNum));
                    }
                    playerInfoMap.put(playerInfo.getPlayerId(), playerInfo);
                }
            }
        }
        int index = 1;
        for (HomeLandRank homeLandRank : showRankTemp) {
            if (!playerInfoMap.containsKey(homeLandRank.getId())) {
                continue;
            }
            homeLandRank.setRank(index);
            HomeLandPlayerRankInfo rankInfo = playerInfoMap.get(homeLandRank.getId());
            showRankList.add(rankInfo.buildRankInfo(homeLandRank));
            index++;
        }
    }

    @Override
    public List<HomeLand.HomeLandRankMsg.Builder> getRankList() {
        return showRankList;
    }

    @Override
    public HomeLandRank getRank(String playerId) {
        HomeLandRank scoreRank = new HomeLandRank();
        for (HomeLand.HomeLandRankMsg.Builder rankMsg : showRankList) {
            if (rankMsg.getPlayerId().equals(playerId)) {
                scoreRank.setId(playerId);
                scoreRank.setRank(rankMsg.getRank());
                scoreRank.setScore(rankMsg.getScore());
                return scoreRank;
            }
        }
        return scoreRank;
    }

    @Override
    public long getLastRankRefreshTime() {
        return lastRankRefreshTime;
    }

    @Override
    public void setLastRankRefreshTime(long lastRankRefreshTime) {
        this.lastRankRefreshTime = lastRankRefreshTime;
    }

    @Override
    public void refreshNpc(long currentTime) {
        boolean needRefresh = false;
        HomeLandConstKVCfg cfg = HawkConfigManager.getInstance().getKVInstance(HomeLandConstKVCfg.class);
        List<Integer> refreshTimeArray = cfg.getNpcRefreshTime();
        for (int time : refreshTimeArray) {
            long refreshTime = HawkTime.getHourOfDayTime(currentTime, time);
            if (lastNpcTickTime < refreshTime && currentTime >= refreshTime) {
                needRefresh = true;
                break;
            }
        }
        if (!needRefresh) {
            return;
        }
        for (HomeLand.HomeLandRankMsg.Builder rankPlayer : showRankList) {
            Player player = GlobalData.getInstance().makesurePlayer(rankPlayer.getPlayerId());
            if (player == null) {
                continue;
            }
            PlayerHomeLandEntity entity = player.getData().getHomeLandEntity();
            HomeLandComponent component = entity.getComponent();
            component.refreshNpc();
            rankPlayer.setNpcNum(component.getNpcComp().getNpcList().size());
        }
        lastNpcTickTime = currentTime;
    }
}
