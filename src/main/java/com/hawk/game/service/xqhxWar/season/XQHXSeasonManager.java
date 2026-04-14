package com.hawk.game.service.xqhxWar.season;

import com.hawk.activity.ActivityManager;
import com.hawk.activity.type.ActivityType;
import com.hawk.activity.type.impl.seasonActivity.SeasonActivity;
import com.hawk.game.GsApp;
import com.hawk.game.GsConfig;
import com.hawk.game.config.*;
import com.hawk.game.entity.GuildInfoObject;
import com.hawk.game.global.GlobalData;
import com.hawk.game.global.RedisProxy;
import com.hawk.game.guild.GuildCreateObj;
import com.hawk.game.invoker.GuildCreateRpcInvoker;
import com.hawk.game.item.ConsumeItems;
import com.hawk.game.msg.PlayerAssembleMsg;
import com.hawk.game.player.Player;
import com.hawk.game.player.PlayerData;
import com.hawk.game.protocol.*;
import com.hawk.game.queryentity.AccountInfo;
import com.hawk.game.service.GuildService;
import com.hawk.game.service.guildTeam.ipml.XQHXGuildTeamManager;
import com.hawk.game.service.guildTeam.model.GuildTeamData;
import com.hawk.game.service.mail.MailParames;
import com.hawk.game.service.mail.SystemMailService;
import com.hawk.game.service.xqhxWar.XQHXWarResidKey;
import com.hawk.game.service.xqhxWar.XQHXWarRoomData;
import com.hawk.game.service.xqhxWar.XQHXWarService;
import com.hawk.game.service.xqhxWar.season.data.XQHXSeasonBattleInfo;
import com.hawk.game.service.xqhxWar.season.data.XQHXSeasonData;
import com.hawk.game.service.xqhxWar.season.data.XQHXSeasonPlayerData;
import com.hawk.game.service.xqhxWar.season.state.XQHXSeasonStateData;
import com.hawk.game.service.xqhxWar.season.state.XQHXSeasonStateEnum;
import com.hawk.game.service.xqhxWar.state.XQHXWarStateEnum;
import com.hawk.game.util.GsConst;
import com.hawk.game.util.LogUtil;
import com.hawk.gamelib.GameConst;
import com.hawk.log.LogConst;
import com.hawk.serialize.string.SerializeHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.hawk.app.HawkApp;
import org.hawk.config.HawkConfigManager;
import org.hawk.config.iterator.ConfigIterator;
import org.hawk.log.HawkLog;
import org.hawk.net.protocol.HawkProtocol;
import org.hawk.net.session.HawkSession;
import org.hawk.os.HawkException;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkRand;
import org.hawk.os.HawkTime;
import org.hawk.task.HawkDelayTask;
import org.hawk.task.HawkTaskManager;
import org.hawk.thread.HawkTask;
import org.hawk.thread.HawkThreadPool;
import org.hawk.tuple.HawkTuple2;
import org.hawk.xid.HawkXID;
import redis.clients.jedis.Tuple;

import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 先驱回响赛季管理
 *
 * @author sjs
 */
public class XQHXSeasonManager {

    private XQHXSeasonStateData stateData;
    /**
     * 入选队伍 老区巅峰赛
     */
    private Map<String, XQHXSeasonData> oldMap = new HashMap<>();
    /**
     * 入选队伍 新秀赛
     */
    private Map<String, XQHXSeasonData> newMap = new HashMap<>();
    /**
     * rank缓存
     */
    private final Map<String, Integer> rankMap = new HashMap<>();
    /**
     * rank缓存 巅峰赛
     */
    private final Map<String, Integer> rankingRankMap = new HashMap<>();
    private static final XQHXSeasonManager instance = new XQHXSeasonManager();

    private XQHXSeasonManager() {
    }

    public static XQHXSeasonManager getInstance() {
        return instance;
    }

    // ----------------base --------------------------
    public void onTickPerOneSecond() {
        stateData.tick();
    }

    public void onTickPerTenMinute() {

    }

    public int getSeason() {
        return stateData == null ? 0 : stateData.getSeason();
    }

    public XQHXSeasonStateEnum getState() {
        return stateData == null ? XQHXSeasonStateEnum.CLOSE : stateData.getState();
    }

    public void syncAllPLayer() {
        for (Player player : GlobalData.getInstance().getOnlinePlayers()) {
            try {
                pageInfo(player, null);
            } catch (Exception e) {
                HawkException.catchException(e);
            }
        }
    }


    public boolean isInSeason(String teamId) {
        if (oldMap.containsKey(teamId)) {
            return true;
        }
        return newMap.containsKey(teamId);
    }

    public boolean getLock(String key, String state) {
        return RedisProxy.getInstance().getRedisSession().hSetNx(key, state, GsConfig.getInstance().getServerId()) > 0;
    }

    public void enterQualifier() {
        stateData.setSeason(calSeason());
        sendOpenMail();
    }

    public void enterRanking() {
        loadAllJoinDatas();
        sendQualifierMail();
    }

    public void enterEndShow() {
        loadAllJoinDatas();
        sendFinalMail();
    }

    public void enterClose() {
        rankMap.clear();
        rankingRankMap.clear();
        oldMap.clear();
        newMap.clear();
    }

    public void calQualifier() {
        boolean getLock = getLock(getLockKey(), XQHXSeasonStateEnum.RANKING.name());
        if (!getLock) {
            return;
        }
        pickJoin(false);
        pickJoin(true);
    }


    public void calFinal() {
        boolean getLock = getLock(getLockKey(), XQHXSeasonStateEnum.END_SHOW.name());
        if (!getLock) {
            return;
        }
        loadAllJoinDatas();
        calRank(false);
        calRank(true);
    }

    public void calRank(boolean isNew) {
        try {
            List<XQHXSeasonData> teamList = isNew ? new ArrayList<>(newMap.values()) : new ArrayList<>(oldMap.values());
            teamList.sort((o1, o2) -> {
                if (o1.group != o2.group) {
                    return o1.group < o2.group ? -1 : 1;
                }
                int totalWin1 = o1.rankingWinCnt - o1.rankingLoseCnt;
                int totalWin2 = o2.rankingWinCnt - o2.rankingLoseCnt;
                if (totalWin1 != totalWin2) {
                    return totalWin1 > totalWin2 ? -1 : 1;
                }
                if (o1.rankingScore != o2.rankingScore) {
                    return o1.rankingScore > o2.rankingScore ? -1 : 1;
                }
                if (o1.totalScore != o2.totalScore) {
                    return o1.totalScore > o2.totalScore ? -1 : 1;
                }
                return 0;
            });
            Map<String, String> dataStrMap = new HashMap<>();
            int i = 1;
            for (XQHXSeasonData data : teamList) {
                if (data == null) {
                    continue;
                }
                data.rank = i;
                dataStrMap.put(data.teamId, data.serialize());
                HawkLog.logPrintln("XQHXManagerBase calRank, matchType:{}, isNew:{}, teamId:{}， rank:{}", getMatchType(), isNew, data.teamId, i);
                i++;
            }
            if (!dataStrMap.isEmpty()) {
                updateDatas(dataStrMap);
            } else {
                HawkLog.warnPrintln("XQHXManagerBase calRank, matchType:{}, isNew:{}, no data", getMatchType(), isNew);
            }
        } catch (Exception e) {
            HawkException.catchException(e);
        }
    }

    public void pickJoin(boolean isNew) {
        try {
            HawkLog.logPrintln("XQHXManagerBase pickJoin start, matchType:{}, isNew:{}", getMatchType(), isNew);
            XQHXSeasonConst seasonConst = HawkConfigManager.getInstance().getKVInstance(XQHXSeasonConst.class);
            Set<Tuple> tuples = RedisProxy.getInstance().getRedisSession().zRevrangeWithScores(getRankKey(isNew), 0, seasonConst.getGroupBmax() - 1, 0);
            if (tuples == null || tuples.isEmpty()) {
                HawkLog.logPrintln("XQHXManagerBase pickJoin tuples is null, matchType:{}, isNew:{}", getMatchType(), isNew);
                return;
            }
            HawkLog.logPrintln("XQHXManagerBase pickJoin tuples, matchType:{}, isNew:{}, size:{}", getMatchType(), isNew, tuples.size());
            List<String> teamIds = new ArrayList<>();
            for (Tuple tuple : tuples) {
                teamIds.add(tuple.getElement());
            }
            HawkLog.logPrintln("XQHXManagerBase pickJoin add join, matchType:{}, isNew:{}", getMatchType(), isNew);
            RedisProxy.getInstance().getRedisSession().sAdd(getJoinKey(isNew), 0, teamIds.toArray(new String[0]));
            Map<String, String> dataStrMap = new HashMap<>();
            Map<String, XQHXSeasonData> dataMap = loadDatas(teamIds);
            HawkLog.logPrintln("XQHXManagerBase pickJoin pick start, matchType:{}, isNew:{}", getMatchType(), isNew);
            int i = 1;
            for (Tuple tuple : tuples) {
                try {
                    String teamId = tuple.getElement();
                    HawkLog.logPrintln("XQHXManagerBase pickJoin pick team start, matchType:{}, isNew:{}, teamId:{}", getMatchType(), isNew, teamId);
                    XQHXSeasonData data = dataMap.get(teamId);
                    if (data != null) {
                        if (i >= seasonConst.getGroupSmin() && i <= seasonConst.getGroupSmax()) {
                            data.group = PBCommonMatch.PBCMWGroupType.S_GROUP_VALUE;
                        }
                        if (i >= seasonConst.getGroupAmin() && i <= seasonConst.getGroupAmax()) {
                            data.group = PBCommonMatch.PBCMWGroupType.A_GROUP_VALUE;
                        }
                        if (i >= seasonConst.getGroupBmin() && i <= seasonConst.getGroupBmax()) {
                            data.group = PBCommonMatch.PBCMWGroupType.B_GROUP_VALUE;
                        }
                        data.qualifierRank = i;
                        HawkLog.logPrintln("XQHXManagerBase pickJoin pick team, matchType:{}, isNew:{}, teamId:{}, rank:{}, group:{}", getMatchType(), isNew, teamId, i, data.group);
                        dataStrMap.put(data.teamId, data.serialize());
                    }
                    HawkLog.logPrintln("XQHXManagerBase pickJoin pick team end, matchType:{}, isNew:{}, teamId:{}", getMatchType(), isNew, teamId);
                } catch (Exception e) {
                    HawkException.catchException(e);
                }
                i++;
            }
            updateDatas(dataStrMap);
            HawkLog.logPrintln("XQHXManagerBase pickJoin end, matchType:{}, isNew:{}", getMatchType(), isNew);
        } catch (Exception e) {
            HawkException.catchException(e);
        }
    }

    public void loadAllJoinDatas() {
        oldMap = loadJoinDatas(false);
        newMap = loadJoinDatas(true);

    }

    public Map<String, XQHXSeasonData> loadJoinDatas(boolean isNew) {
        Set<String> teamIds = RedisProxy.getInstance().getRedisSession().sMembers(getJoinKey(isNew));
        return loadDatas(teamIds);
    }

    public Map<String, XQHXSeasonData> loadDatas(Collection<String> teamIds) {
        Map<String, XQHXSeasonData> dataMap = new HashMap<>();
        if (teamIds == null || teamIds.isEmpty()) {
            return dataMap;
        }
        List<String> list = RedisProxy.getInstance().getRedisSession().hmGet(getDataKey(), teamIds.toArray(new String[0]));
        for (String json : list) {
            XQHXSeasonData data = XQHXSeasonData.unSerialize(json);
            if (data == null) {
                continue;
            }
            dataMap.put(data.teamId, data);
        }
        return dataMap;
    }

    public void updateDatas(Map<String, String> dataStrMap) {
        RedisProxy.getInstance().getRedisSession().hmSet(getDataKey(), dataStrMap, 0);
    }

    public XQHXSeasonData loadData(String teamId) {
        String json = RedisProxy.getInstance().getRedisSession().hGet(getDataKey(), teamId);
        return XQHXSeasonData.unSerialize(json);
    }

    public void update(XQHXSeasonData data) {
        RedisProxy.getInstance().getRedisSession().hSet(getDataKey(), data.teamId, data.serialize());
        if (getState() == XQHXSeasonStateEnum.QUALIFIER) {
            RedisProxy.getInstance().getRedisSession().zAdd(getRankKey(data.isNew), data.score, data.teamId);
        }
    }

    public void removeRank(String teamId) {
        if (getState() == XQHXSeasonStateEnum.QUALIFIER) {
            RedisProxy.getInstance().getRedisSession().zRem(getRankKey(true), 0, teamId);
            RedisProxy.getInstance().getRedisSession().zRem(getRankKey(false), 0, teamId);
        }
    }

    public Map<String, XQHXSeasonPlayerData> loadPlayerDatas(Collection<String> playerIds) {
        Map<String, XQHXSeasonPlayerData> dataMap = new HashMap<>();
        List<String> list = RedisProxy.getInstance().getRedisSession().hmGet(getPlayerDataKey(), playerIds.toArray(new String[0]));
        for (String json : list) {
            XQHXSeasonPlayerData data = XQHXSeasonPlayerData.unSerialize(json);
            if (data == null) {
                continue;
            }
            dataMap.put(data.playerId, data);
        }
        return dataMap;
    }

    public void updatePlayerDatas(Map<String, String> dataStrMap) {
        RedisProxy.getInstance().getRedisSession().hmSet(getPlayerDataKey(), dataStrMap, 0);
    }

    public XQHXSeasonPlayerData loadPlayerData(String playerId) {
        String json = RedisProxy.getInstance().getRedisSession().hGet(getPlayerDataKey(), playerId);
        return XQHXSeasonPlayerData.unSerialize(json);
    }

    public String getStateKey() {
        return String.format(XQHXSeasonRedisKey.XQHX_STATE, getMatchType().name());
    }

    public String getLockKey() {
        return String.format(XQHXSeasonRedisKey.XQHX_LOCK, getMatchType().name(), getSeason());
    }

    public String getDataKey() {
        return String.format(XQHXSeasonRedisKey.XQHX_DATA, getMatchType().name(), getSeason());
    }

    public String getPlayerDataKey() {
        return String.format(XQHXSeasonRedisKey.XQHX_PLAYER_DATA, getMatchType().name(), getSeason());
    }

    public String getRankKey(boolean isNew) {
        if (isNew) {
            return String.format(XQHXSeasonRedisKey.XQHX_RANK_NEW, getMatchType().name(), getSeason());
        } else {
            return String.format(XQHXSeasonRedisKey.XQHX_RANK, getMatchType().name(), getSeason());
        }
    }

    public String getJoinKey(boolean isNew) {
        if (isNew) {
            return String.format(XQHXSeasonRedisKey.XQHX_JOIN_NEW, getMatchType().name(), getSeason());
        } else {
            return String.format(XQHXSeasonRedisKey.XQHX_JOIN, getMatchType().name(), getSeason());
        }
    }

    // ----------------base --------------------------


    public void init() {
        stateData = new XQHXSeasonStateData();
        stateData.load(getInstance());
        loadQualifierRank(true);
        loadQualifierRank(false);
        if (getState() == XQHXSeasonStateEnum.RANKING || getState() == XQHXSeasonStateEnum.END_SHOW) {
            loadAllJoinDatas();
            loadRankingRank(true);
            loadRankingRank(false);
        }
    }


    public PBCommonMatch.PBCMWMatchType getMatchType() {
        return PBCommonMatch.PBCMWMatchType.XQHX_SEASON;
    }


    public PBCommonMatch.PBCMWServerType getServerType() {
        XQHXSeasonConst seasonConst = HawkConfigManager.getInstance().getKVInstance(XQHXSeasonConst.class);
        if (seasonConst.getOnlyOldServerSet().contains(GsConfig.getInstance().getServerId())) {
            return PBCommonMatch.PBCMWServerType.OLD_SERVER;
        }
        PBCommonMatch.PBCMWServerType type = PBCommonMatch.PBCMWServerType.NEW_SERVER;
        String serverId = GsConfig.getInstance().getServerId();
        List<String> serverList = GlobalData.getInstance().getMergeServerList(serverId);
        if (CollectionUtils.isEmpty(serverList)) {
            return type;
        }
        if (serverList.size() > 8) {
            type = PBCommonMatch.PBCMWServerType.OLD_SERVER;
        }
        return type;
    }

    public XQHXSeasonTimeCfg getCfg() {
        return HawkConfigManager.getInstance().getConfigByKey(XQHXSeasonTimeCfg.class, getSeason());
    }

    public XQHXSeasonTimeCfg calCfg() {
        long now = HawkTime.getMillisecond();
        ConfigIterator<XQHXSeasonTimeCfg> iterator = HawkConfigManager.getInstance().getConfigIterator(XQHXSeasonTimeCfg.class);
        for (XQHXSeasonTimeCfg cfg : iterator) {
            if (cfg.getSeasonStartTimeValue() <= now && cfg.getSeasonEndTimeValue() > now) {
                return cfg;
            }
        }
        return null;
    }


    public int calSeason() {
        XQHXSeasonTimeCfg cfg = calCfg();
        return cfg == null ? -1 : cfg.getTermId();
    }


    public long getEndTime() {
        switch (getState()) {
            case CLOSE: {
                XQHXSeasonTimeCfg cfg = calCfg();
                if (cfg == null) {
                    return HawkTime.getMillisecond() + TimeUnit.DAYS.toMillis(30);
                }
                return cfg.getSeasonStartTimeValue();
            }
            case QUALIFIER: {
                XQHXSeasonTimeCfg cfg = getCfg();
                if (cfg == null) {
                    return HawkTime.getMillisecond() + TimeUnit.DAYS.toMillis(30);
                }
                return cfg.getQualifierEndTimeValue();
            }
            case RANKING: {
                XQHXSeasonTimeCfg cfg = getCfg();
                if (cfg == null) {
                    return HawkTime.getMillisecond() + TimeUnit.DAYS.toMillis(30);
                }
                return cfg.getRankingEndTimeValue();
            }
            case END_SHOW: {
                XQHXSeasonTimeCfg cfg = getCfg();
                if (cfg == null) {
                    return HawkTime.getMillisecond() + TimeUnit.DAYS.toMillis(30);
                }
                return cfg.getSeasonEndTimeValue();
            }
        }
        return Long.MAX_VALUE;
    }


    public void onTeamDismiss(String teamId) {
        try {
            removeRank(teamId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addScore(String teamId, long score, boolean isWin) {
        if (getState() == XQHXSeasonStateEnum.CLOSE) {
            HawkLog.logPrintln("XQHXSeasonManager addScore season is close, teamId:{}, score:{}, isWin:{}", teamId, score, isWin);
            return;
        }
        HawkLog.logPrintln("XQHXSeasonManager addScore start, teamId:{}, score:{}, isWin:{}", teamId, score, isWin);
        XQHXSeasonConst seasonConst = HawkConfigManager.getInstance().getKVInstance(XQHXSeasonConst.class);
        XQHXSeasonData data = loadData(teamId);
        if (data == null) {
            HawkLog.errPrintln("XQHXSeasonManager addScore data is null, teamId:{}, score:{}, isWin:{}", teamId, score, isWin);
            return;
        }
        if (getState() == XQHXSeasonStateEnum.QUALIFIER) {
            data.score += calScore(seasonConst, score, isWin);
        } else {
            data.rankingScore += calScore(seasonConst, score, isWin);
        }
        data.totalScore += score;
        if (isWin) {
            if (getState() == XQHXSeasonStateEnum.QUALIFIER) {
                data.winCnt++;
            } else {
                data.rankingWinCnt++;
            }

        } else {
            if (getState() == XQHXSeasonStateEnum.QUALIFIER) {
                data.loseCnt++;
            } else {
                data.rankingLoseCnt++;
            }

        }
        update(data);
        addPlayerScore(teamId, data.totalScore);
        HawkLog.logPrintln("XQHXSeasonManager addScore data, data:{}", data.serialize());
        HawkLog.logPrintln("XQHXSeasonManager addScore end, teamId:{}, score:{}, isWin:{}", teamId, score, isWin);
    }

    public void addPlayerScore(String teamId, long score) {
        try {
            HawkLog.logPrintln("XQHXSeasonManager addPlayerScore start, teamId:{}, score:{}", teamId, score);
            Set<String> playerIds = XQHXGuildTeamManager.getInstance().getTeamPlayerIds(teamId);
            if (playerIds == null || playerIds.isEmpty()) {
                HawkLog.logPrintln("XQHXSeasonManager addPlayerScore playerIds is null, teamId:{}", teamId);
                return;
            }
            Map<String, XQHXSeasonPlayerData> playerDataMap = loadPlayerDatas(playerIds);
            HawkLog.logPrintln("XQHXSeasonManager addPlayerScore playerIds, teamId:{}, size:{}", teamId, playerDataMap.size());
            Map<String, String> playerDataStrMap = new HashMap<>();
            for (String playerId : playerIds) {
                XQHXSeasonPlayerData playerData = playerDataMap.getOrDefault(playerId, new XQHXSeasonPlayerData(playerId));
                playerData.teamScore = score;
                ConfigIterator<XQHXSeasonTargetRewardCfg> iterator = HawkConfigManager.getInstance().getConfigIterator(XQHXSeasonTargetRewardCfg.class);
                for (XQHXSeasonTargetRewardCfg cfg : iterator) {
                    if (cfg.getTarget() > score) {
                        HawkLog.logPrintln("XQHXSeasonManager addPlayerScore send player target is not, teamId:{}, playerId:{}", teamId, playerId);
                        continue;
                    }
                    if (playerData.teamRewarded.contains(cfg.getId())) {
                        HawkLog.logPrintln("XQHXSeasonManager addPlayerScore send player have send, teamId:{}, playerId:{}", teamId, playerId);
                        continue;
                    }
                    playerData.teamRewarded.add(cfg.getId());
                    HawkLog.logPrintln("XQHXSeasonManager addPlayerScore send player, teamId:{}, playerId:{}, cfgId:{}", teamId, playerId, cfg.getId());
                    SystemMailService.getInstance().sendMail(MailParames.newBuilder().setPlayerId(playerData.playerId).setMailId(MailConst.MailId.XQHX_SEASON_2024072509).addContents(cfg.getTarget()).setAwardStatus(Const.MailRewardStatus.NOT_GET).addRewards(cfg.getRewardItem()).build());
                }
                playerDataStrMap.put(playerData.playerId, playerData.serialize());
            }
            updatePlayerDatas(playerDataStrMap);
            HawkLog.logPrintln("XQHXSeasonManager addPlayerScore end, teamId:{}, score:{}", teamId, score);
        } catch (Exception e) {
            HawkException.catchException(e);
        }
    }

    public int calScore(XQHXSeasonConst seasonConst, long score, boolean isWin) {
        if (isWin) {
            return (int) (score / seasonConst.getWinPointPara()) + seasonConst.getWinPoint();
        } else {
            return (int) (score / seasonConst.getLosrPointPara()) + seasonConst.getLosrPoint();
        }
    }

    public void updateBattleInfo(GuildTeamData teamData1, GuildTeamData teamData2, XQHXWarRoomData roomData) {
        try {
            if (teamData1 == null || teamData2 == null || roomData == null || roomData.group == 0) {
                return;
            }
            XQHXSeasonBattleInfo battleInfo = new XQHXSeasonBattleInfo(teamData1, teamData2, roomData);
            XQHXSeasonStateEnum state = getState();
            String name = getMatchType().name();
            int season = getSeason();
            int termId = roomData.termId;
            long delayTime = randDelayTime();
            addDelayTask(new HawkDelayTask(delayTime, delayTime, 1) {

                public Object run() {
                    if (state == XQHXSeasonStateEnum.QUALIFIER) {
                        String kayBase = battleInfo.isNew ? XQHXSeasonRedisKey.XQHX_BATTLE_QUALIFIER_SELF_NEW : XQHXSeasonRedisKey.XQHX_BATTLE_QUALIFIER_SELF;
                        String key1 = String.format(kayBase, name, season, battleInfo.teamIdA);
                        String key2 = String.format(kayBase, name, season, battleInfo.teamIdB);
                        RedisProxy.getInstance().getRedisSession().hSet(key1, String.valueOf(battleInfo.termId), battleInfo.serialize());
                        RedisProxy.getInstance().getRedisSession().hSet(key2, String.valueOf(battleInfo.termId), battleInfo.serialize());
                    }
                    if (state == XQHXSeasonStateEnum.RANKING) {
                        String kayBase = battleInfo.isNew ? XQHXSeasonRedisKey.XQHX_BATTLE_RANKING_SELF_NEW : XQHXSeasonRedisKey.XQHX_BATTLE_RANKING_SELF;
                        String key1 = String.format(kayBase, name, season, battleInfo.teamIdA);
                        String key2 = String.format(kayBase, name, season, battleInfo.teamIdB);
                        RedisProxy.getInstance().getRedisSession().hSet(key1, String.valueOf(battleInfo.termId), battleInfo.serialize());
                        RedisProxy.getInstance().getRedisSession().hSet(key2, String.valueOf(battleInfo.termId), battleInfo.serialize());
                        String kayGroupBase = battleInfo.isNew ? XQHXSeasonRedisKey.XQHX_BATTLE_GROUP_NEW : XQHXSeasonRedisKey.XQHX_BATTLE_GROUP;
                        String key = String.format(kayGroupBase, name, season, battleInfo.group, termId);
                        RedisProxy.getInstance().getRedisSession().hSet(key, battleInfo.roomId, battleInfo.serialize());
                    }
                    return null;
                }
            });
            if (HawkOSOperator.isEmptyString(roomData.winnerId)) {
                Map<String, Object> param = new HashMap<>();
                param.put("teamA", roomData.campA);
                param.put("teamB", roomData.campB);
                param.put("serverType", roomData.isNew ? 1 : 2);
                param.put("groupType", roomData.group);
                LogUtil.logActivityCommon(LogConst.LogInfoType.xqhx_season_match, param);
            } else {
                Map<String, Object> param = new HashMap<>();
                param.put("teamA", roomData.campA);
                param.put("scoreA", roomData.scoreA);
                param.put("teamB", roomData.campB);
                param.put("scoreB", roomData.scoreB);
                param.put("winnerId", roomData.winnerId);
                param.put("serverType", roomData.isNew ? 1 : 2);
                param.put("groupType", roomData.group);
                LogUtil.logActivityCommon(LogConst.LogInfoType.xqhx_season_result, param);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static long randDelayTime() {
        long baseTime = 5000L;
        long randTime = HawkRand.randInt(30000);
        return baseTime + randTime;
    }

    private static void addDelayTask(HawkDelayTask task) {
        HawkThreadPool taskPool = HawkTaskManager.getInstance().getThreadPool("task");
        if (null != taskPool) {
            task.setTypeName("XQHXDelayTask");
            taskPool.addTask(task, 0, false);
        }
    }


    public void pageInfo(Player player, PBCommonMatch.PBCMWPageInfoReq req) {
        PBCommonMatch.PBCMWPageInfoResp.Builder resp = PBCommonMatch.PBCMWPageInfoResp.newBuilder();
        resp.setMatchType(req == null ? getMatchType() : req.getMatchType());
        resp.setServerType(req == null ? getServerType() : req.getServerType());
        resp.setPageInfo(genPage(player, req));
        resp.setSelfType(getServerType());
        player.sendProtocol(HawkProtocol.valueOf(HP.code2.CMW_PAGE_INFO_RESP, resp));
    }


    public void rankInfo(Player player, PBCommonMatch.PBCMWRankInfoReq req) {
        PBCommonMatch.PBCMWRankInfoResp.Builder resp = null;
        Map<String, PBCommonMatch.PBCMWTeamInfo.Builder> infoMap = null;
        if (req.getBattleType() == PBCommonMatch.PBCMWBattleType.QUALIFIER) {
            if (req.getServerType() == PBCommonMatch.PBCMWServerType.NEW_SERVER) {
                resp = newQualifierRank;
                infoMap = newQualifierInfoMap;
            } else {
                resp = oldQualifierRank;
                infoMap = oldQualifierInfoMap;
            }
        }

        if (req.getBattleType() == PBCommonMatch.PBCMWBattleType.RANKING) {
            int termId = XQHXWarService.getInstance().getTermId();
            XQHXSeasonTimeCfg cfg = getCfg();
            if (cfg != null) {
                if (termId < cfg.getRankingTermId() || termId > cfg.getEndTermId()) {
                    return;
                }
                if (termId == cfg.getRankingTermId() && XQHXWarService.getInstance().getState() != XQHXWarStateEnum.PEACE) {
                    return;
                }
            }
            if (req.getServerType() == PBCommonMatch.PBCMWServerType.NEW_SERVER) {
                resp = newRankingRank;
                infoMap = newRankingInfoMap;
            } else {
                resp = oldRankingRank;
                infoMap = oldRankingInfoMap;
            }
        }
        if (resp == null) {
            return;
        }
        resp.setMatchType(req.getMatchType());
        resp.setServerType(req.getServerType());
        resp.setBattleType(req.getBattleType());
        resp.setIsEnd(req.getIsEnd());
        String teamId = XQHXWarService.getInstance().getSelfTeamId(player);
        if (infoMap != null) {
            PBCommonMatch.PBCMWTeamInfo.Builder selfInfo = infoMap.get(teamId);
            if (selfInfo != null) {
                resp.setSelfInfo(selfInfo);
            }
        }
        player.sendProtocol(HawkProtocol.valueOf(HP.code2.CMW_RANK_INFO_RESP, resp));
    }


    public void battleInfo(Player player, PBCommonMatch.PBCMWBattleInfoReq req) {
        PBCommonMatch.PBCMWBattleInfoResp.Builder resp = PBCommonMatch.PBCMWBattleInfoResp.newBuilder();
        resp.setMatchType(req.getMatchType());
        resp.setServerType(req.getServerType());
        resp.setBattleType(req.getBattleType());
        resp.setGroupType(req.getGroupType());
        resp.setIsSelf(req.getIsSelf());
        resp.setTermId(req.getTermId());
        XQHXSeasonTimeCfg cfg = getCfg();
        String teamId = XQHXWarService.getInstance().getSelfTeamId(player);
        int curTermId = XQHXWarService.getInstance().getTermId();
        if (req.getBattleType() == PBCommonMatch.PBCMWBattleType.QUALIFIER) {
            String keyBase = req.getServerType() == PBCommonMatch.PBCMWServerType.NEW_SERVER ? XQHXSeasonRedisKey.XQHX_BATTLE_QUALIFIER_SELF_NEW : XQHXSeasonRedisKey.XQHX_BATTLE_QUALIFIER_SELF;
            Map<Integer, XQHXSeasonBattleInfo> battleInfoMap = loadQualifierBattleInfo(String.format(keyBase, getMatchType().name(), getSeason(), teamId));
            int endTerm = Math.min(curTermId + 1, cfg.getRankingTermId());
            for (int i = cfg.getQualifierTermId(); i < endTerm; i++) {
                XQHXWarTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(XQHXWarTimeCfg.class, i);
                long battleStartTIme = timeCfg == null ? HawkTime.getMillisecond() : timeCfg.getBattleTime();
                XQHXSeasonBattleInfo battleInfo = battleInfoMap.get(i);
                if (battleInfo == null) {
                    PBCommonMatch.PBCMWBattleInfo.Builder builder = PBCommonMatch.PBCMWBattleInfo.newBuilder();
                    builder.setTermId(i - cfg.getQualifierTermId() + 1);
                    builder.setBattleStartTime(battleStartTIme);
                    builder.setWinCount(0);
                    resp.addBattleInfos(builder);
                } else {
                    if (!HawkOSOperator.isEmptyString(battleInfo.winnerId)) {
                        PBCommonMatch.PBCMWBattleInfo.Builder builder = toBattleInfoPB(battleInfo, teamId);
                        builder.setTermId(i - cfg.getQualifierTermId() + 1);
                        resp.addBattleInfos(builder);
                    }
                }
            }
        } else {
            if (req.getIsSelf()) {
                String keyBase = req.getServerType() == PBCommonMatch.PBCMWServerType.NEW_SERVER ? XQHXSeasonRedisKey.XQHX_BATTLE_RANKING_SELF_NEW : XQHXSeasonRedisKey.XQHX_BATTLE_RANKING_SELF;
                Map<Integer, XQHXSeasonBattleInfo> battleInfoMap = loadQualifierBattleInfo(String.format(keyBase, getMatchType().name(), getSeason(), teamId));
                int endTerm = Math.min(curTermId, cfg.getEndTermId());
                for (int i = cfg.getRankingTermId(); i <= endTerm; i++) {
                    XQHXWarTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(XQHXWarTimeCfg.class, i);
                    long battleStartTIme = timeCfg == null ? HawkTime.getMillisecond() : timeCfg.getBattleTime();
                    XQHXSeasonBattleInfo battleInfo = battleInfoMap.get(i);
                    if (battleInfo == null) {
                        PBCommonMatch.PBCMWBattleInfo.Builder builder = PBCommonMatch.PBCMWBattleInfo.newBuilder();
                        builder.setTermId(i - cfg.getRankingTermId() + 1);
                        builder.setBattleStartTime(battleStartTIme);
                        builder.setWinCount(0);
                        resp.addBattleInfos(builder);
                    } else {
                        if (!HawkOSOperator.isEmptyString(battleInfo.winnerId)) {
                            PBCommonMatch.PBCMWBattleInfo.Builder builder = toBattleInfoPB(battleInfo, teamId);
                            builder.setTermId(i - cfg.getRankingTermId() + 1);
                            resp.addBattleInfos(builder);
                        }
                    }
                }
            } else {
                int realTerm = req.getTermId() + cfg.getRankingTermId() - 1;
                String keyBase = req.getServerType() == PBCommonMatch.PBCMWServerType.NEW_SERVER ? XQHXSeasonRedisKey.XQHX_BATTLE_GROUP_NEW : XQHXSeasonRedisKey.XQHX_BATTLE_GROUP;
                Map<String, XQHXSeasonBattleInfo> battleInfoMap = loadRankingBattleInfo(String.format(keyBase, getMatchType().name(), getSeason(), req.getGroupType().getNumber(), realTerm));
                for (XQHXSeasonBattleInfo battleInfo : battleInfoMap.values()) {
                    if (!HawkOSOperator.isEmptyString(battleInfo.winnerId)) {
                        resp.addBattleInfos(toBattleInfoPB(battleInfo, teamId));
                    }
                }
                XQHXWarTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(XQHXWarTimeCfg.class, realTerm);
                long battleStartTIme = timeCfg == null ? HawkTime.getMillisecond() : timeCfg.getBattleTime();
                resp.setBattleStartTime(battleStartTIme);
            }

        }
        player.sendProtocol(HawkProtocol.valueOf(HP.code2.CMW_BATTLE_INFO_RESP, resp));
    }

    public PBCommonMatch.PBCMWBattleInfo.Builder toBattleInfoPB(XQHXSeasonBattleInfo battleInfo, String teamId) {
        PBCommonMatch.PBCMWBattleInfo.Builder builder = PBCommonMatch.PBCMWBattleInfo.newBuilder();
        PBCommonMatch.PBCMWTeamInfo.Builder team1 = PBCommonMatch.PBCMWTeamInfo.newBuilder();
        PBCommonMatch.PBCMWTeamInfo.Builder team2 = PBCommonMatch.PBCMWTeamInfo.newBuilder();
        if (teamId.equals(battleInfo.teamIdA)) {
            team1.setId(battleInfo.teamIdA);
            team1.setName(battleInfo.teamNameA);
            team1.setGuildName(battleInfo.guildNameA);
            team1.setGuildTag(battleInfo.guildTagA);
            team1.setServerId(battleInfo.serverIdA);
            team1.setIsWin(battleInfo.winnerId.equals(battleInfo.teamIdA));
            team2.setId(battleInfo.teamIdB);
            team2.setName(battleInfo.teamNameB);
            team2.setGuildName(battleInfo.guildNameB);
            team2.setGuildTag(battleInfo.guildTagB);
            team2.setServerId(battleInfo.serverIdB);
            team2.setIsWin(battleInfo.winnerId.equals(battleInfo.teamIdB));
        } else {
            team1.setId(battleInfo.teamIdB);
            team1.setName(battleInfo.teamNameB);
            team1.setGuildName(battleInfo.guildNameB);
            team1.setGuildTag(battleInfo.guildTagB);
            team1.setServerId(battleInfo.serverIdB);
            team1.setIsWin(battleInfo.winnerId.equals(battleInfo.teamIdB));
            team2.setId(battleInfo.teamIdA);
            team2.setName(battleInfo.teamNameA);
            team2.setGuildName(battleInfo.guildNameA);
            team2.setGuildTag(battleInfo.guildTagA);
            team2.setServerId(battleInfo.serverIdA);
            team2.setIsWin(battleInfo.winnerId.equals(battleInfo.teamIdA));
        }
        builder.setTeam1(team1);
        builder.setTeam2(team2);
        builder.setTermId(battleInfo.termId);
        builder.setBattleStartTime(XQHXWarService.getInstance().getBattleStartTime(battleInfo.termId, battleInfo.timeIndex));
        builder.setWinCount(battleInfo.winCount);
        return builder;
    }


    public void timeInfo(Player player, PBCommonMatch.PBCMWBattleTimeReq req) {
        PBCommonMatch.PBCMWBattleTimeResp.Builder resp = PBCommonMatch.PBCMWBattleTimeResp.newBuilder();
        resp.setMatchType(req.getMatchType());
        resp.setServerType(req.getServerType());

        PBCommonMatch.PBCMWBattleTime.Builder time1 = PBCommonMatch.PBCMWBattleTime.newBuilder();
        time1.setType(PBCommonMatch.PBCMWBattleType.QUALIFIER);
        PBCommonMatch.PBCMWBattleTime.Builder time2 = PBCommonMatch.PBCMWBattleTime.newBuilder();
        time2.setType(PBCommonMatch.PBCMWBattleType.RANKING);
        XQHXSeasonTimeCfg cfg = getCfg();
        long now = HawkTime.getMillisecond();
        if (cfg != null) {
            time1.setStartTime(cfg.getSeasonStartTimeValue());
            time1.setEndTime(cfg.getQualifierEndTimeValue());
            time2.setStartTime(cfg.getQualifierEndTimeValue());
            time2.setEndTime(cfg.getRankingEndTimeValue());

            for (int i = cfg.getQualifierTermId(); i < cfg.getRankingTermId(); i++) {
                PBCommonMatch.PBCMWBattleTimeInfo.Builder info = PBCommonMatch.PBCMWBattleTimeInfo.newBuilder();
                info.setTermId(i - cfg.getQualifierTermId() + 1);
                XQHXWarTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(XQHXWarTimeCfg.class, i);
                if (timeCfg != null) {
                    info.setMatchEndTime(timeCfg.getMatchEndTime());
                    info.setBattleStartTime(timeCfg.getBattleTime());
                    info.setBattleEndTime(timeCfg.getSettleTime());
                } else {
                    info.setMatchEndTime(now);
                    info.setBattleStartTime(now);
                    info.setBattleEndTime(now);
                }
                time1.addInfos(info);
            }
            for (int i = cfg.getRankingTermId(); i <= cfg.getEndTermId(); i++) {
                PBCommonMatch.PBCMWBattleTimeInfo.Builder info = PBCommonMatch.PBCMWBattleTimeInfo.newBuilder();
                info.setTermId(i - cfg.getRankingTermId() + 1);
                XQHXWarTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(XQHXWarTimeCfg.class, i);
                if (timeCfg != null) {
                    info.setMatchEndTime(timeCfg.getMatchEndTime());
                    info.setBattleStartTime(timeCfg.getBattleTime());
                    info.setBattleEndTime(timeCfg.getSettleTime());
                } else {
                    info.setMatchEndTime(now);
                    info.setBattleStartTime(now);
                    info.setBattleEndTime(now);
                }
                time2.addInfos(info);
            }
        }
        resp.addTimeInfos(time1);
        resp.addTimeInfos(time2);
        player.sendProtocol(HawkProtocol.valueOf(HP.code2.CMW_BATTLE_TIME_RESP, resp));
    }


    public void targetInfo(Player player, PBCommonMatch.PBCMWBattleTargetReq req) {
        PBCommonMatch.PBCMWBattleTargetResp.Builder resp = PBCommonMatch.PBCMWBattleTargetResp.newBuilder();
        resp.setMatchType(getMatchType());
        resp.setServerType(getServerType());
        XQHXSeasonPlayerData playerData = loadPlayerData(player.getId());
        if (playerData == null) {
            player.sendProtocol(HawkProtocol.valueOf(HP.code2.CMW_BATTLE_TARGET_RESP, resp));
            return;
        }
        resp.setScore(playerData.teamScore);
        resp.addAllSendAwards(playerData.teamRewarded);
        player.sendProtocol(HawkProtocol.valueOf(HP.code2.CMW_BATTLE_TARGET_RESP, resp));
    }

    public PBCommonMatch.PBCMWPageInfo.Builder genPage(Player player, PBCommonMatch.PBCMWPageInfoReq req) {
        PBCommonMatch.PBCMWPageInfo.Builder pageInfo = PBCommonMatch.PBCMWPageInfo.newBuilder();
        pageInfo.setBigState(getClientBigState());
        fillClientState(pageInfo, player);
        fillClientSeasonTime(pageInfo);
        if (req == null || req.getServerType() == getServerType()) {
            fillClientTeam(pageInfo, player);
        }
        return pageInfo;
    }


    public PBCommonMatch.PBCMWBigState getClientBigState() {
        switch (getState()) {
            case CLOSE:
                return PBCommonMatch.PBCMWBigState.BIG_COLSE;
            case SIGNUP:
                return PBCommonMatch.PBCMWBigState.BIG_SIGNUP;
            case QUALIFIER:
                return PBCommonMatch.PBCMWBigState.BIG_QUALIFIER;
            case RANKING:
                return PBCommonMatch.PBCMWBigState.BIG_RANKING;
            case END_SHOW:
                return PBCommonMatch.PBCMWBigState.BIG_END_SHOW;
        }
        return PBCommonMatch.PBCMWBigState.BIG_COLSE;
    }

    public void fillClientState(PBCommonMatch.PBCMWPageInfo.Builder pageInfo, Player player) {
        XQHXWar.XQHXStateInfo.Builder builder = XQHXWarService.getInstance().getStateInfo(player);
        switch (builder.getState()) {
            case XQHX_PEACE: {
                pageInfo.setState(PBCommonMatch.PBCMWState.PEACE);
            }
            break;
            case XQHX_SIGNUP: {
                pageInfo.setState(PBCommonMatch.PBCMWState.SIGNUP);
            }
            break;
            case XQHX_MATCH_WAIT: {
                pageInfo.setState(PBCommonMatch.PBCMWState.MATCH_WAIT);
            }
            break;
            case XQHX_MATCH: {
                pageInfo.setState(PBCommonMatch.PBCMWState.MATCH);
            }
            break;
            case XQHX_PREPARE: {
                pageInfo.setState(PBCommonMatch.PBCMWState.PREPARE);
            }
            break;
            case XQHX_BATTLE: {
                pageInfo.setState(PBCommonMatch.PBCMWState.BATTLE);
            }
            break;
            case XQHX_FINISH: {
                pageInfo.setState(PBCommonMatch.PBCMWState.FINISH);
            }
            break;
        }
        pageInfo.setTermId(calSeasonTerm());
        pageInfo.setEndTime(builder.getEndTime());
    }

    public int calSeasonTerm() {
        XQHXSeasonTimeCfg cfg = getCfg();
        if (cfg == null) {
            return 1;
        } else {
            if (getState() == XQHXSeasonStateEnum.QUALIFIER) {
                return Math.max(1, XQHXWarService.getInstance().getTermId() - cfg.getQualifierTermId() + 1);
            } else if (getState() == XQHXSeasonStateEnum.RANKING) {
                return Math.max(1, XQHXWarService.getInstance().getTermId() - cfg.getRankingTermId() + 1);
            } else {
                return 1;
            }

        }
    }

    public void fillClientTeam(PBCommonMatch.PBCMWPageInfo.Builder pageInfo, Player player) {
        PBCommonMatch.PBCMWTeamInfo.Builder teamInfo = PBCommonMatch.PBCMWTeamInfo.newBuilder();
        String teamId = XQHXWarService.getInstance().getSelfTeamId(player);
        teamInfo.setId(teamId);
        if (getState() == XQHXSeasonStateEnum.QUALIFIER) {
            teamInfo.setRank(rankMap.getOrDefault(teamId, -1));
        } else {
            teamInfo.setRank(rankingRankMap.getOrDefault(teamId, -1));
        }
        XQHXSeasonData data = loadData(teamId);
        if (data != null) {
            if (getState() == XQHXSeasonStateEnum.QUALIFIER) {
                teamInfo.setWinCount(data.winCnt);
                teamInfo.setLoseCount(data.loseCnt);
                teamInfo.setScore(data.score);
                teamInfo.setGroupType(PBCommonMatch.PBCMWGroupType.QUALIFIER_GROUP);
            } else {
                teamInfo.setWinCount(data.rankingWinCnt);
                teamInfo.setLoseCount(data.rankingLoseCnt);
                teamInfo.setScore(data.rankingScore);
                teamInfo.setGroupType(PBCommonMatch.PBCMWGroupType.valueOf(data.group));
            }
        } else {
            if (getState() == XQHXSeasonStateEnum.QUALIFIER) {
                teamInfo.setGroupType(PBCommonMatch.PBCMWGroupType.QUALIFIER_GROUP);
            } else {
                teamInfo.setGroupType(PBCommonMatch.PBCMWGroupType.NOMAL_GROUP);
            }
        }
        pageInfo.setSelfTeam(teamInfo);
    }

    public void fillClientSeasonTime(PBCommonMatch.PBCMWPageInfo.Builder pageInfo) {
        XQHXSeasonTimeCfg cfg = getCfg();
        if (cfg == null) {
            long now = HawkTime.getMillisecond();
            pageInfo.setSeasonStartTime(now + TimeUnit.DAYS.toMillis(10));
            pageInfo.setSignUpEndTime(now + TimeUnit.DAYS.toMillis(10));
            pageInfo.setQualifierEndTime(now + TimeUnit.DAYS.toMillis(20));
            pageInfo.setRankingEndTime(now + TimeUnit.DAYS.toMillis(30));
            pageInfo.setSeasonEndTime(now + TimeUnit.DAYS.toMillis(40));
            pageInfo.setQualifierMax(100);
            pageInfo.setRankingEndMax(100);
        } else {
            pageInfo.setSeasonStartTime(cfg.getSeasonStartTimeValue());
            pageInfo.setSignUpEndTime(cfg.getSeasonStartTimeValue());
            pageInfo.setQualifierEndTime(cfg.getQualifierEndTimeValue() + 1000);
            pageInfo.setRankingEndTime(cfg.getRankingEndTimeValue());
            pageInfo.setSeasonEndTime(cfg.getSeasonEndTimeValue());
            pageInfo.setQualifierMax(cfg.getRankingTermId() - cfg.getQualifierTermId());
            pageInfo.setRankingEndMax(cfg.getEndTermId() - cfg.getRankingTermId() + 1);
        }
    }

    PBCommonMatch.PBCMWRankInfoResp.Builder newQualifierRank;
    PBCommonMatch.PBCMWRankInfoResp.Builder oldQualifierRank;
    Map<String, PBCommonMatch.PBCMWTeamInfo.Builder> newQualifierInfoMap;
    Map<String, PBCommonMatch.PBCMWTeamInfo.Builder> oldQualifierInfoMap;

    PBCommonMatch.PBCMWRankInfoResp.Builder newRankingRank;
    PBCommonMatch.PBCMWRankInfoResp.Builder oldRankingRank;
    Map<String, PBCommonMatch.PBCMWTeamInfo.Builder> newRankingInfoMap;
    Map<String, PBCommonMatch.PBCMWTeamInfo.Builder> oldRankingInfoMap;

    public void onSignup() {
        if (getState() == XQHXSeasonStateEnum.RANKING) {
            loadAllJoinDatas();
            XQHXSeasonConst seasonConst = HawkConfigManager.getInstance().getKVInstance(XQHXSeasonConst.class);
            for (String teamId : oldMap.keySet()) {
                XQHXWarService.getInstance().seasonSignup(teamId, seasonConst.getSeasonTimeIndex());
            }
            for (String teamId : newMap.keySet()) {
                XQHXWarService.getInstance().seasonSignup(teamId, seasonConst.getSeasonTimeIndex());
            }
        }
    }

    /**
     * 排位赛匹配
     */
    public void onQualifierMatch() {
        int termId = XQHXWarService.getInstance().getTermId();
        HawkLog.logPrintln("XQHXWarService onMatch start, termId:{}", termId);
        //抢锁
        String serverId = GsConfig.getInstance().getServerId();
        String matchKey = String.format(XQHXWarResidKey.XQHX_WAR_MATCH, termId);
        boolean getLock = RedisProxy.getInstance().getRedisSession().setNx(matchKey, serverId);
        if (!getLock) {
            HawkLog.errPrintln("XQHXWarService onMatch get lock fail, termId:{}", termId);
            return;
        }
        HawkLog.logPrintln("XQHXWarService onMatch real start, termId:{}", termId);
        XQHXConstCfg constCfg = HawkConfigManager.getInstance().getKVInstance(XQHXConstCfg.class);
        Map<String, Set<String>> serverIdToRoomIdMap = new HashMap<>();
        Map<String, String> roomStrMap = new HashMap<>();
        List<GuildTeamData> noMatchList = new ArrayList<>();
        for (int i = 1; i <= constCfg.getWarCount(); i++) {
            String signUpTimeKey = String.format(XQHXWarResidKey.XQHX_WAR_SIGNUP_TIME, termId, i);
            Set<String> teamIds = RedisProxy.getInstance().getRedisSession().sMembers(signUpTimeKey);
            if (teamIds == null || teamIds.isEmpty()) {
                continue;
            }
            List<GuildTeamData> guildTeamList = XQHXGuildTeamManager.getInstance().loadTeams(teamIds);
            List<GuildTeamData> newTeamList = new ArrayList<>();
            List<GuildTeamData> oldTeamList = new ArrayList<>();
            Map<String, XQHXSeasonData> seasonDataMap = loadDatas(teamIds);
            for (GuildTeamData teamData : guildTeamList) {
                if (teamData == null) {
                    continue;
                }
                // 把 teamData的 isNew 改为 从seasonDataMap 获取 isNew
                XQHXSeasonData seasonData = seasonDataMap.get(teamData.id);
                if (seasonData == null) {
                    HawkLog.errPrintln("XQHXSeasonManager onQualifierMatch seasonData is null, termId:{} teamId {} signUpTimeKey{}", termId, teamData.id, signUpTimeKey);
                    continue;
                }
                if (seasonData.isNew) {
                    newTeamList.add(teamData);
                } else {
                    oldTeamList.add(teamData);
                }
            }
            createRooms(termId, i, newTeamList, roomStrMap, serverIdToRoomIdMap, true, PBCommonMatch.PBCMWGroupType.QUALIFIER_GROUP_VALUE, 0);
            createRooms(termId, i, oldTeamList, roomStrMap, serverIdToRoomIdMap, false, PBCommonMatch.PBCMWGroupType.QUALIFIER_GROUP_VALUE, 0);
            if (!newTeamList.isEmpty()) {
                noMatchList.addAll(newTeamList);
            }
            if (!oldTeamList.isEmpty()) {
                noMatchList.addAll(oldTeamList);
            }
        }
        if (!roomStrMap.isEmpty()) {
            RedisProxy.getInstance().getRedisSession().hmSet(String.format(XQHXWarResidKey.XQHX_WAR_ROOM, termId), roomStrMap, 0);
        }
        for (String toServerId : serverIdToRoomIdMap.keySet()) {
            Set<String> roomIds = serverIdToRoomIdMap.get(toServerId);
            RedisProxy.getInstance().getRedisSession().sAdd(String.format(XQHXWarResidKey.XQHX_WAR_ROOM_SERVER, termId, toServerId), 0, roomIds.toArray(new String[roomIds.size()]));
        }
        List<String> noMatchIds = new ArrayList<>();
        for (GuildTeamData teamData : noMatchList) {
            noMatchIds.add(teamData.id);
        }
        if (!noMatchIds.isEmpty()) {
            RedisProxy.getInstance().getRedisSession().sAdd(String.format(XQHXWarResidKey.XQHX_WAR_NO_MATCH, termId), 0, noMatchIds.toArray(new String[0]));
        }
    }

    public void onRankingMatch() {
        int termId = XQHXWarService.getInstance().getTermId();
        HawkLog.logPrintln("XQHXWarService onMatch start, termId:{}", termId);
        //抢锁
        String serverId = GsConfig.getInstance().getServerId();
        String matchKey = String.format(XQHXWarResidKey.XQHX_WAR_MATCH, termId);
        boolean getLock = RedisProxy.getInstance().getRedisSession().setNx(matchKey, serverId);
        if (!getLock) {
            HawkLog.logPrintln("XQHXWarService onMatch get lock fail, termId:{}", termId);
            return;
        }
        HawkLog.logPrintln("XQHXWarService onMatch real start, termId:{}", termId);
        XQHXConstCfg constCfg = HawkConfigManager.getInstance().getKVInstance(XQHXConstCfg.class);
        Map<String, Set<String>> serverIdToRoomIdMap = new HashMap<>();
        Map<String, String> roomStrMap = new HashMap<>();
        List<GuildTeamData> noMatchList = new ArrayList<>();
        for (int i = 1; i <= constCfg.getWarCount(); i++) {
            String signUpTimeKey = String.format(XQHXWarResidKey.XQHX_WAR_SIGNUP_TIME, termId, i);
            Set<String> teamIds = RedisProxy.getInstance().getRedisSession().sMembers(signUpTimeKey);
            if (teamIds == null || teamIds.isEmpty()) {
                continue;
            }
            List<GuildTeamData> normalTeamList = new ArrayList<>();
            Map<Integer, List<GuildTeamData>> newSTeamListMap = new HashMap<>();
            Map<Integer, List<GuildTeamData>> oldSTeamListMap = new HashMap<>();
            Map<Integer, List<GuildTeamData>> newATeamListMap = new HashMap<>();
            Map<Integer, List<GuildTeamData>> oldATeamListMap = new HashMap<>();
            Map<Integer, List<GuildTeamData>> newBTeamListMap = new HashMap<>();
            Map<Integer, List<GuildTeamData>> oldBTeamListMap = new HashMap<>();
            List<GuildTeamData> guildTeamList = XQHXGuildTeamManager.getInstance().loadTeams(teamIds);
            for (GuildTeamData teamData : guildTeamList) {
                if (teamData == null) {
                    continue;
                }
                if (oldMap.containsKey(teamData.id)) {
                    XQHXSeasonData seasonData = oldMap.get(teamData.id);
                    if (seasonData.group == PBCommonMatch.PBCMWGroupType.S_GROUP_VALUE) {
                        putToCountMap(oldSTeamListMap, teamData, seasonData);
                    } else if (seasonData.group == PBCommonMatch.PBCMWGroupType.A_GROUP_VALUE) {
                        putToCountMap(oldATeamListMap, teamData, seasonData);
                    } else {
                        putToCountMap(oldBTeamListMap, teamData, seasonData);
                    }
                } else if (newMap.containsKey(teamData.id)) {
                    XQHXSeasonData seasonData = newMap.get(teamData.id);
                    if (seasonData.group == PBCommonMatch.PBCMWGroupType.S_GROUP_VALUE) {
                        putToCountMap(newSTeamListMap, teamData, seasonData);
                    } else if (seasonData.group == PBCommonMatch.PBCMWGroupType.A_GROUP_VALUE) {
                        putToCountMap(newATeamListMap, teamData, seasonData);
                    } else {
                        putToCountMap(newBTeamListMap, teamData, seasonData);
                    }
                } else {
                    normalTeamList.add(teamData);
                }

            }
            for (int count : oldSTeamListMap.keySet()) {
                List<GuildTeamData> teamList = oldSTeamListMap.get(count);
                HawkLog.logPrintln("XQHXSeasonManager onRankingMatch timeIndex:{} oldS_GROUP_VALUE count:{} timeSize{}", i, count, teamList.size());
                createRooms(termId, i, teamList, roomStrMap, serverIdToRoomIdMap, false, PBCommonMatch.PBCMWGroupType.S_GROUP_VALUE, count);
                noMatchList.addAll(teamList);
            }
            for (int count : newSTeamListMap.keySet()) {
                List<GuildTeamData> teamList = newSTeamListMap.get(count);
                HawkLog.logPrintln("XQHXSeasonManager onRankingMatch timeIndex:{} newS_GROUP_VALUE count:{} timeSize{}", i, count, teamList.size());
                createRooms(termId, i, teamList, roomStrMap, serverIdToRoomIdMap, true, PBCommonMatch.PBCMWGroupType.S_GROUP_VALUE, count);
                noMatchList.addAll(teamList);
            }
            for (int count : oldATeamListMap.keySet()) {
                List<GuildTeamData> teamList = oldATeamListMap.get(count);
                HawkLog.logPrintln("XQHXSeasonManager onRankingMatch timeIndex:{} oldA_GROUP_VALUE count:{}  timeSize{}", i, count, teamList.size());
                createRooms(termId, i, teamList, roomStrMap, serverIdToRoomIdMap, false, PBCommonMatch.PBCMWGroupType.A_GROUP_VALUE, count);
                noMatchList.addAll(teamList);
            }
            for (int count : newATeamListMap.keySet()) {
                List<GuildTeamData> teamList = newATeamListMap.get(count);
                HawkLog.logPrintln("XQHXSeasonManager onRankingMatch timeIndex:{} newA_GROUP_VALUE count:{} timeSize{}", i, count, teamList.size());
                createRooms(termId, i, teamList, roomStrMap, serverIdToRoomIdMap, true, PBCommonMatch.PBCMWGroupType.A_GROUP_VALUE, count);
                noMatchList.addAll(teamList);
            }
            for (int count : oldBTeamListMap.keySet()) {
                List<GuildTeamData> teamList = oldBTeamListMap.get(count);
                HawkLog.logPrintln("XQHXSeasonManager onRankingMatch timeIndex:{} oldB_GROUP_VALUE count:{} timeSize{}", i, count, teamList.size());
                createRooms(termId, i, teamList, roomStrMap, serverIdToRoomIdMap, false, PBCommonMatch.PBCMWGroupType.B_GROUP_VALUE, count);
                noMatchList.addAll(teamList);
            }
            for (int count : newBTeamListMap.keySet()) {
                List<GuildTeamData> teamList = newBTeamListMap.get(count);
                HawkLog.logPrintln("XQHXSeasonManager onRankingMatch timeIndex:{} newB_GROUP_VALUE count:{} timeSize{}", i, count, teamList.size());
                createRooms(termId, i, teamList, roomStrMap, serverIdToRoomIdMap, true, PBCommonMatch.PBCMWGroupType.B_GROUP_VALUE, count);
                noMatchList.addAll(teamList);
            }
            createRooms(termId, i, normalTeamList, roomStrMap, serverIdToRoomIdMap, false, 0, 0);
        }
        if (!roomStrMap.isEmpty()) {
            RedisProxy.getInstance().getRedisSession().hmSet(String.format(XQHXWarResidKey.XQHX_WAR_ROOM, termId), roomStrMap, 0);
        }
        for (String toServerId : serverIdToRoomIdMap.keySet()) {
            Set<String> roomIds = serverIdToRoomIdMap.get(toServerId);
            RedisProxy.getInstance().getRedisSession().sAdd(String.format(XQHXWarResidKey.XQHX_WAR_ROOM_SERVER, termId, toServerId), 0, roomIds.toArray(new String[roomIds.size()]));
        }
        List<String> noMatchIds = new ArrayList<>();
        for (GuildTeamData teamData : noMatchList) {
            noMatchIds.add(teamData.id);
        }
        if (!noMatchIds.isEmpty()) {
            HawkLog.logPrintln("XQHXWarService updateSignUpGuild noMatchTeam:" + noMatchIds);
        }
//        for(GuildTeamData teamData : noMatchList){
//            addScore(teamData.id, 0, true);
//        }
    }

    public void putToCountMap(Map<Integer, List<GuildTeamData>> countMap, GuildTeamData teamData, XQHXSeasonData seasonData) {
        int winCount = seasonData.rankingWinCnt;
        if (!countMap.containsKey(winCount)) {
            countMap.put(winCount, new ArrayList<>());
        }
        countMap.get(winCount).add(teamData);
    }

    public void createRooms(int termId, int i, List<GuildTeamData> teamList, Map<String, String> roomStrMap, Map<String, Set<String>> serverIdToRoomIdMap, boolean isNew, int group, int count) {
        Collection<String> collect = teamList.stream().map(v -> v.id).collect(Collectors.toCollection(ArrayList::new));
        Map<String, XQHXSeasonData> stringXQHXSeasonDataMap = loadDatas(collect);
        teamList.sort((o1, o2) -> {
            // 把team的 seasonScore 改为 XQHXSeasonData的score
            XQHXSeasonData seasonData1 = stringXQHXSeasonDataMap.get(o1.id);
            XQHXSeasonData seasonData2 = stringXQHXSeasonDataMap.get(o2.id);
            if (seasonData1 != null && seasonData2 != null) {
                if (seasonData1.score != seasonData2.score) {
                    return seasonData1.score > seasonData2.score ? -1 : 1;
                }
            }
            if (o1.matchPower != o2.matchPower) {
                return o1.matchPower > o2.matchPower ? -1 : 1;
            }
            return 0;
        });

        String str = RedisProxy.getInstance().getRedisSession().getString("xqhx_gm_match_20250626");
        Map<String, String> gmMatchMap = SerializeHelper.stringToMap(str, String.class, String.class, SerializeHelper.ATTRIBUTE_SPLIT, SerializeHelper.BETWEEN_ITEMS);

        while (teamList.size() > 1) {
            /************************GM匹配***************************************************/
            HawkTuple2<GuildTeamData, GuildTeamData> result = null;
            if (!gmMatchMap.isEmpty()) {
                for (Map.Entry<String, String> gmMatch : gmMatchMap.entrySet()) {
                    String t1 = gmMatch.getKey();
                    String t2 = gmMatch.getValue();
                    GuildTeamData team1 = null;
                    GuildTeamData team2 = null;
                    for (GuildTeamData teamData : teamList) {
                        if (teamData.id.equals(t1)) {
                            team1 = teamData;
                        }
                        if (teamData.id.equals(t2)) {
                            team2 = teamData;
                        }
                    }
                    if (team1 != null && team2 != null) {
                        result = new HawkTuple2<>(team1, team2);
                        HawkLog.logPrintln("XQHXSeasonManager-xqhx_gm_match_20250626,{}-{}", team1.id, team2.id);
                    }
                }
            }
            /************************GM匹配***************************************************/
            if (Objects.isNull(result)) {
                result = matchTeam(teamList);
            }
            if (result == null) {
                HawkLog.logPrintln("XQHXSeasonManager createRooms result isnull termId:{} timeIndex:{} teamList.size:{}", termId, i, teamList.size());
                break;
            }
            if (result.first != null) {
                teamList.remove(result.first);
            }
            if (result.second != null) {
                teamList.remove(result.second);
            }
            GuildTeamData teamData1 = result.first;
            GuildTeamData teamData2 = result.second;
            if (teamData1 != null && teamData2 != null) {
                teamData1.oppTeamId = teamData2.id;
                teamData2.oppTeamId = teamData1.id;
                String roomServerId = teamData1.serverId.compareTo(teamData2.serverId) < 0 ? teamData1.serverId : teamData2.serverId;
                XQHXWarRoomData roomData = new XQHXWarRoomData(termId, i, teamData1, teamData2, roomServerId);
                roomData.isNew = isNew;
                roomData.group = group;
                roomData.winCount = count;
                XQHXGuildTeamManager.getInstance().updateTeam(teamData1);
                XQHXGuildTeamManager.getInstance().updateTeam(teamData2);
                roomStrMap.put(roomData.id, roomData.serialize());
                XQHXWarService.getInstance().updateRoomIdToServer(serverIdToRoomIdMap, teamData1.serverId, roomData.id);
                XQHXWarService.getInstance().updateRoomIdToServer(serverIdToRoomIdMap, teamData2.serverId, roomData.id);
                XQHXWarService.getInstance().updateRoomIdToServer(serverIdToRoomIdMap, roomData.roomServerId, roomData.id);
                updateBattleInfo(teamData1, teamData2, roomData);
            } else {
                HawkLog.logPrintln("XQHXSeasonManager createRooms  termId:{} timeIndex:{} teamData1 or teamData2 is null teamListSize{} ", termId, i, teamList.size());
            }
        }
    }

    public HawkTuple2<GuildTeamData, GuildTeamData> matchTeam(List<GuildTeamData> teamList) {
        if (teamList == null || teamList.isEmpty()) {
            return null;
        }
        XQHXConstCfg constCfg = HawkConfigManager.getInstance().getKVInstance(XQHXConstCfg.class);
        int teamCount = constCfg.getMatchParam();
        teamCount = Math.min(teamCount, teamList.size());
        List<GuildTeamData> subList = teamList.subList(0, teamCount);
        GuildTeamData teamData1 = subList.get(0);
        List<GuildTeamData> tmpList = new ArrayList<>();
        for (int i = 1; i < subList.size(); i++) {
            GuildTeamData tmpData = subList.get(i);
            if (tmpData.guildId.equals(teamData1.guildId)) {
                continue;
            }
            tmpList.add(tmpData);
        }
        if (tmpList.isEmpty()) {
            return new HawkTuple2<>(teamData1, null);
        }
        Collections.shuffle(tmpList);
        GuildTeamData teamData2 = tmpList.get(0);
        return new HawkTuple2<>(teamData1, teamData2);
    }

    /**
     * 开始匹配
     */
    public void onMatch() {
        if (getState() == XQHXSeasonStateEnum.RANKING) {
            onRankingMatch();
        } else {
            onQualifierMatch();
        }
    }

    public void onEnd() {
        rankMap.clear();
        rankingRankMap.clear();
        if (getState() == XQHXSeasonStateEnum.QUALIFIER) {
            loadQualifierRank(false);
            loadQualifierRank(true);
        }
        if (getState() == XQHXSeasonStateEnum.RANKING) {
            loadAllJoinDatas();
            loadRankingRank(false);
            loadRankingRank(true);
        }
        XQHXSeasonTimeCfg cfg = getCfg();
        int termId = XQHXWarService.getInstance().getTermId();
        if (cfg != null && termId == (cfg.getRankingTermId() - 1)) {
            calQualifier();
        }
        if (cfg != null && termId == cfg.getEndTermId()) {
            calFinal();
        }
    }


    public void loadQualifierRank(boolean isNew) {
        Set<Tuple> tuples = RedisProxy.getInstance().getRedisSession().zRevrangeWithScores(getRankKey(isNew), 0, 999, 0);
        Set<String> teamIds = new HashSet<>();
        for (Tuple tuple : tuples) {
            teamIds.add(tuple.getElement());
        }
        Map<String, XQHXSeasonData> dataMap = loadDatas(teamIds);
        Map<String, GuildTeamData> teamDataMap = loadTeams(teamIds);
        Map<String, PBCommonMatch.PBCMWTeamInfo.Builder> infoMap = new HashMap<>();
        PBCommonMatch.PBCMWRankInfoResp.Builder resp = PBCommonMatch.PBCMWRankInfoResp.newBuilder();
        resp.setMatchType(PBCommonMatch.PBCMWMatchType.XQHX_SEASON);
        resp.setServerType(isNew ? PBCommonMatch.PBCMWServerType.NEW_SERVER : PBCommonMatch.PBCMWServerType.OLD_SERVER);
        resp.setBattleType(PBCommonMatch.PBCMWBattleType.QUALIFIER);
        int i = 1;
        for (Tuple tuple : tuples) {
            PBCommonMatch.PBCMWTeamInfo.Builder info = PBCommonMatch.PBCMWTeamInfo.newBuilder();
            String teamId = tuple.getElement();
            long score = Math.round(tuple.getScore());
            GuildTeamData teamData = teamDataMap.get(teamId);
            if (teamData == null) {
                continue;
            }
            XQHXSeasonData seasonData = dataMap.get(teamId);
            if (seasonData == null) {
                continue;
            }
            info.setRank(i);
            info.setId(teamId);
            info.setName(teamData.name);
            info.setGuildName(teamData.guildName);
            info.setGuildTag(teamData.guildTag);
            info.setServerId(teamData.serverId);
            info.setWinCount(seasonData.winCnt);
            info.setLoseCount(seasonData.loseCnt);
            info.setScore(score);
            info.setGroupType(PBCommonMatch.PBCMWGroupType.QUALIFIER_GROUP);
            resp.addRankInfos(info);
            infoMap.put(teamId, info);
            rankMap.put(teamId, i);
            i++;
        }
        if (isNew) {
            newQualifierRank = resp;
            newQualifierInfoMap = infoMap;
        } else {
            oldQualifierRank = resp;
            oldQualifierInfoMap = infoMap;
        }
    }

    public void loadRankingRank(boolean isNew) {
        Map<String, PBCommonMatch.PBCMWTeamInfo.Builder> infoMap = new HashMap<>();
        PBCommonMatch.PBCMWRankInfoResp.Builder resp = PBCommonMatch.PBCMWRankInfoResp.newBuilder();
        resp.setMatchType(PBCommonMatch.PBCMWMatchType.XQHX_SEASON);
        resp.setServerType(isNew ? PBCommonMatch.PBCMWServerType.NEW_SERVER : PBCommonMatch.PBCMWServerType.OLD_SERVER);
        resp.setBattleType(PBCommonMatch.PBCMWBattleType.RANKING);
        List<XQHXSeasonData> dataList = isNew ? new ArrayList<>(newMap.values()) : new ArrayList<>(oldMap.values());
        dataList.sort((o1, o2) -> {
            if (o1.group != o2.group) {
                return o1.group < o2.group ? -1 : 1;
            }
            int totalWin1 = o1.rankingWinCnt - o1.rankingLoseCnt;
            int totalWin2 = o2.rankingWinCnt - o2.rankingLoseCnt;
            if (totalWin1 != totalWin2) {
                return totalWin1 > totalWin2 ? -1 : 1;
            }
            if (o1.rankingScore != o2.rankingScore) {
                return o1.rankingScore > o2.rankingScore ? -1 : 1;
            }
            if (o1.totalScore != o2.totalScore) {
                return o1.totalScore > o2.totalScore ? -1 : 1;
            }
            return 0;
        });
        Set<String> teamIds = isNew ? newMap.keySet() : oldMap.keySet();
        Map<String, GuildTeamData> teamDataMap = loadTeams(teamIds);
        int i = 1;
        for (XQHXSeasonData data : dataList) {
            PBCommonMatch.PBCMWTeamInfo.Builder info = PBCommonMatch.PBCMWTeamInfo.newBuilder();
            String teamId = data.teamId;
            GuildTeamData teamData = teamDataMap.get(teamId);
            if (teamData == null) {
                continue;
            }
            info.setRank(i);
            info.setId(teamId);
            info.setName(teamData.name);
            info.setGuildName(teamData.guildName);
            info.setGuildTag(teamData.guildTag);
            info.setServerId(teamData.serverId);
            info.setWinCount(data.rankingWinCnt);
            info.setLoseCount(data.rankingLoseCnt);
            info.setScore(data.rankingScore);
            info.setGroupType(PBCommonMatch.PBCMWGroupType.valueOf(data.group));
            resp.addRankInfos(info);
            infoMap.put(teamId, info);
            if (data.rankingWinCnt > 0 || data.rankingLoseCnt > 0) {
                rankingRankMap.put(teamId, i);
            }
            i++;
        }
        if (isNew) {
            newRankingRank = resp;
            newRankingInfoMap = infoMap;
        } else {
            oldRankingRank = resp;
            oldRankingInfoMap = infoMap;
        }
    }


    public Map<String, GuildTeamData> loadTeams(Collection<String> teamIds) {
        Map<String, GuildTeamData> teamDataMap = new HashMap<>();
        if (teamIds == null || teamIds.isEmpty()) {
            return teamDataMap;
        }
        List<GuildTeamData> guildTeamList = XQHXGuildTeamManager.getInstance().loadTeams((Set<String>) teamIds);
        for (GuildTeamData teamData : guildTeamList) {
            if (teamData == null) {
                continue;
            }
            teamDataMap.put(teamData.id, teamData);
        }
        return teamDataMap;
    }

    public Map<Integer, XQHXSeasonBattleInfo> loadQualifierBattleInfo(String key) {
        Map<Integer, XQHXSeasonBattleInfo> infoMap = new HashMap<>();
        Map<String, String> map = RedisProxy.getInstance().getRedisSession().hGetAll(key);
        for (String json : map.values()) {
            XQHXSeasonBattleInfo battleInfo = XQHXSeasonBattleInfo.unSerialize(json);
            if (battleInfo == null) {
                continue;
            }
            infoMap.put(battleInfo.termId, battleInfo);
        }
        return infoMap;
    }

    public Map<String, XQHXSeasonBattleInfo> loadRankingBattleInfo(String key) {
        Map<String, XQHXSeasonBattleInfo> infoMap = new HashMap<>();
        Map<String, String> map = RedisProxy.getInstance().getRedisSession().hGetAll(key);
        for (String json : map.values()) {
            XQHXSeasonBattleInfo battleInfo = XQHXSeasonBattleInfo.unSerialize(json);
            if (battleInfo == null) {
                continue;
            }
            infoMap.put(battleInfo.roomId, battleInfo);
        }
        return infoMap;
    }


    public void sendOpenMail() {
        long now = HawkTime.getMillisecond();
        SystemMailService.getInstance().addGlobalMail(MailParames.newBuilder().setMailId(MailConst.MailId.XQHX_SEASON_2024072507).addContents(getServerType().getNumber()).build(), now, now + TimeUnit.DAYS.toMillis(7));
    }


    public void sendQualifierMail() {
        sendQualifierMail(oldMap);
        sendQualifierMail(newMap);
    }


    public void sendQualifierMail(Map<String, XQHXSeasonData> dataMap) {
        for (String teamId : dataMap.keySet()) {
            try {
                if (!XQHXWarService.getInstance().isLocalTeam(teamId)) {
                    continue;
                }
                XQHXSeasonData data = dataMap.get(teamId);
                int serverType = getServerType().getNumber();
                long score = data.score;
                int rank = data.qualifierRank;
                int groupType = data.group;

                Map<String, Object> param = new HashMap<>();
                param.put("teamId", teamId);
                param.put("score", score);
                param.put("rank", rank);
                param.put("serverType", serverType);
                param.put("groupType", data.group);
                LogUtil.logActivityCommon(LogConst.LogInfoType.xqhx_season_qualifier, param);

                Set<String> playerIds = XQHXGuildTeamManager.getInstance().getTeamPlayerIds(teamId);
                if (playerIds == null || playerIds.isEmpty()) {
                    continue;
                }
                HawkTaskManager.getInstance().postExtraTask(new HawkTask() {

                    public Object run() {
                        // 匹配成功邮件
                        for (String playerId : playerIds) {
                            SystemMailService.getInstance().sendMail(MailParames.newBuilder().setPlayerId(playerId).setMailId(MailConst.MailId.XQHX_SEASON_2024072508).addContents(serverType, score, rank, groupType).build());
                        }
                        return null;
                    }
                });
            } catch (Exception e) {
                HawkException.catchException(e);
            }
        }
    }


    public void sendFinalMail() {
        HawkLog.logPrintln("XQHXSeasonManager sendFinalMail start");
        sendFinalMail(oldMap);
        sendFinalMail(newMap);
        HawkLog.logPrintln("XQHXSeasonManager sendFinalMail end");
    }

    public void sendFinalMail(Map<String, XQHXSeasonData> dataMap) {
        try {
            HawkLog.logPrintln("XQHXSeasonManager sendFinalMail start, size:{}", dataMap.size());
            SeasonActivity activity = null;
            Optional<SeasonActivity> opActivity = ActivityManager.getInstance().getGameActivityByType(ActivityType.SEASON_ACTIVITY.intValue());
            if (opActivity.isPresent()) {
                activity = opActivity.get();
            }
            Map<String, Integer> guildIdToRank = new HashMap<>();
            for (String teamId : dataMap.keySet()) {
                try {
                    if (!XQHXWarService.getInstance().isLocalTeam(teamId)) {
                        HawkLog.logPrintln("XQHXSeasonManager sendFinalMail team is not local, teamId:{}", teamId);
                        continue;
                    }
                    HawkLog.logPrintln("XQHXSeasonManager sendFinalMail team start, teamId:{}", teamId);
                    XQHXSeasonData data = dataMap.get(teamId);
                    if (data == null) {
                        HawkLog.logPrintln("XQHXSeasonManager sendFinalMail team data is null, teamId:{}", teamId);
                        continue;
                    }
                    int serverType = getServerType().getNumber();
                    int groupType = data.group;
                    int winCount = data.rankingWinCnt;
                    int loseCount = data.rankingLoseCnt;
                    int rank = data.rank;
                    HawkLog.logPrintln("XQHXSeasonManager sendFinalMail team mail team, teamId:{}, serverType:{}, groupType:{},winCount:{},loseCount:{},rank:{}", teamId, serverType, groupType, winCount, loseCount, rank);
                    Map<String, Object> param = new HashMap<>();
                    param.put("teamId", teamId);
                    param.put("winCount", winCount);
                    param.put("loseCount", loseCount);
                    param.put("rank", rank);
                    param.put("serverType", serverType);
                    param.put("groupType", groupType);
                    LogUtil.logActivityCommon(LogConst.LogInfoType.xqhx_season_final, param);
                    GuildTeamData teamData = XQHXGuildTeamManager.getInstance().getTeamData(teamId);
                    if (teamData != null) {
                        int guildRank = guildIdToRank.getOrDefault(teamData.guildId, Integer.MAX_VALUE);
                        if (data.rank < guildRank) {
                            guildRank = data.rank;
                            guildIdToRank.put(teamData.guildId, guildRank);
                        }
                    }
                    XQHXSeasonRankRewardCfg rankRewrdCfg = XQHXSeasonRankRewardCfg.getRankCfg(serverType, rank);
                    if (rankRewrdCfg == null) {
                        HawkLog.logPrintln("XQHXSeasonManager sendFinalMail team rankRewrdCfg is null, teamId:{}, rank:{}", teamId, rank);
                        continue;
                    }
                    Set<String> playerIds = XQHXGuildTeamManager.getInstance().getTeamPlayerIds(teamId);
                    if (playerIds == null || playerIds.isEmpty()) {
                        HawkLog.logPrintln("XQHXSeasonManager sendFinalMail team playerIds is empty, teamId:{}", teamId);
                        continue;
                    }
                    HawkLog.logPrintln("XQHXSeasonManager sendFinalMail team playerIds, teamId:{}, size:{}", teamId, playerIds.size());
                    HawkTaskManager.getInstance().postExtraTask(new HawkTask() {

                        public Object run() {
                            // 匹配成功邮件
                            for (String playerId : playerIds) {
                                try {
                                    HawkLog.logPrintln("XQHXSeasonManager sendFinalMail team mail player, teamId:{}, playerId:{}, serverType:{}, groupType:{},winCount:{},loseCount:{},rank:{}", teamId, playerId, serverType, groupType, winCount, loseCount, rank);
                                    SystemMailService.getInstance().sendMail(MailParames.newBuilder().setPlayerId(playerId)
                                            .setMailId(MailConst.MailId.XQHX_SEASON_2024072510)
                                            .addSubTitles(rank)
                                            .addContents(serverType, groupType, winCount, loseCount, rank)
                                            .setAwardStatus(Const.MailRewardStatus.NOT_GET).addRewards(rankRewrdCfg.getRewardItem()).build());
                                } catch (Exception e) {
                                    HawkLog.logPrintln("XQHXSeasonManager sendFinalMail team mail player error, teamId:{}, playerId:{}", teamId, playerId);
                                    HawkException.catchException(e);
                                }
                            }
                            return null;
                        }
                    });
                    HawkLog.logPrintln("XQHXSeasonManager sendFinalMail team end, teamId:{}", teamId);
                } catch (Exception e) {
                    HawkLog.logPrintln("XQHXSeasonManager sendFinalMail team error, teamId:{}", teamId);
                    HawkException.catchException(e);
                }
            }
            if (activity != null) {
                for (String guildId : guildIdToRank.keySet()) {
                    try {
                        int guildRank = guildIdToRank.getOrDefault(guildId, Integer.MAX_VALUE);
                        activity.addGuildGradeExpFromMatchRank(Activity.SeasonMatchType.S_XQHX, guildId, guildRank);
                        HawkLog.logPrintln("XQHXSeasonManager sendFinalMail guild, guildId:{}, guildRank:{}", guildId, guildRank);
                    } catch (Exception e) {
                        HawkException.catchException(e);
                    }
                }
            }
            HawkLog.logPrintln("XQHXSeasonManager sendFinalMail end");
        } catch (Exception e) {
            HawkException.catchException(e);
        }
    }

    /**
     * gm入口
     *
     * @param map gm参数
     * @return 活动信息
     */

    public String gm(Map<String, String> map) {
        //只有测试环境可以使用
        if (!GsConfig.getInstance().isDebug()) {
            return "不是测试环境";
        }
        //要执行的gm指令
        String cmd = map.getOrDefault("cmd", "");
        HawkLog.logPrintln("XQHXSeasonManager gm: {} stateData {}", cmd, stateData.toString());
        switch (cmd) {
            case "info": {
                return printInfo();
            }
            case "next": {
                stateData.toNext();
                return printInfo();
            }
            case "setSeason": {
                int season = Integer.parseInt(map.get("season"));
                stateData.setSeason(season);
                return printInfo();
            }
            case "addSeason": {
                int add = Integer.parseInt(map.get("add"));
                if (add == 0) {
                    stateData.setSeason(0);
                } else {
                    stateData.setSeason(stateData.getSeason() + add);
                }
                return printInfo();
            }
            case "load": {
                if (getState() == XQHXSeasonStateEnum.QUALIFIER) {
                    loadQualifierRank(false);
                    loadQualifierRank(true);
                }
                if (getState() == XQHXSeasonStateEnum.RANKING) {
                    loadRankingRank(false);
                    loadRankingRank(true);
                }
                return printInfo();
            }
            case "allOne": {
                List<String> guildIds = GuildService.getInstance().getGuildIds();
                guildIds.forEach(id -> {
                    try {
                        GuildInfoObject guildInfoObject = GuildService.getInstance().getGuildInfoObject(id);
                        if (guildInfoObject != null) {
                            GuildTeamData teamData = XQHXGuildTeamManager.getInstance().makeSureTeamData(guildInfoObject, 1);
                            XQHXGuildTeamManager.getInstance().updateTeam(teamData);
                            Player leader = GlobalData.getInstance().makesurePlayer(guildInfoObject.getLeaderId());
                            GuildTeam.GuildBattleMemberListReq.Builder listReq = GuildTeam.GuildBattleMemberListReq.newBuilder();
                            listReq.setType(GuildTeam.GuildTeamType.XQHX_WAR);
                            XQHXGuildTeamManager.getInstance().memberList(leader, listReq.build());
                            for (String memberId : GuildService.getInstance().getGuildMembers(id)) {
                                Player player = GlobalData.getInstance().makesurePlayer(memberId);
                                if (player == null) {
                                    continue;
                                }
                                GuildTeam.GuildBattleMemberManagerReq.Builder req = GuildTeam.GuildBattleMemberManagerReq.newBuilder();
                                req.setAuth(GuildTeam.GuildTeamAuth.GT_STARTER);
                                req.setType(GuildTeam.GuildTeamType.XQHX_WAR);
                                req.setPlayerId(memberId);
                                req.setTeamId(id + ":1");
                                XQHXGuildTeamManager.getInstance().memberManager(player, req.build());
                            }
                        }
                    } catch (Exception e) {
                        HawkException.catchException(e);
                    }
                });
                return printInfo();
            }
            case "registRobot": {
                int count = 1000;
                if (map.containsKey("count")) {
                    count = Integer.parseInt(map.get("count"));
                }
                registRobot(count);
                return printInfo();
            }
            case "createGuild": {
                int count = 500;
                if (map.containsKey("count")) {
                    count = Integer.parseInt(map.get("count"));
                }
                createGuild(count);
                return printInfo();
            }
            case "sendFinalMail": {
                sendFinalMail();
                return printInfo();
            }
        }
        return "";
    }

    public String printInfo() {
        //页面信息
        String info = "";
        try {
            //获取本地InetAddress对象
            InetAddress localAddress = InetAddress.getLocalHost();
            //获取本地IP地址
            String ipAddress = localAddress.getHostAddress();
            //获得gm端口
            int port = GsConfig.getInstance().getGmPort();
            //组装页面信息
            info += "<a href=\"http://" + ipAddress + ":" + port + "/script/whcgm?opt=XQHXSEASONGM&type=XQHX_SEASON&cmd=info\">刷新</a>           ";
            info += "<a href=\"http://" + ipAddress + ":" + port + "/script/whcgm?opt=XQHXSEASONGM&type=XQHX_SEASON&cmd=next\">切阶段</a>          ";
            info += "<a href=\"http://" + ipAddress + ":" + port + "/script/whcgm?opt=XQHXSEASONGM&type=XQHX_SEASON&cmd=addSeason&add=1\">加赛季</a>          ";
            info += "<a href=\"http://" + ipAddress + ":" + port + "/script/whcgm?opt=XQHXSEASONGM&type=XQHX_SEASON&cmd=addSeason&add=0\">赛季归0</a>          ";
            info += "<a href=\"http://" + ipAddress + ":" + port + "/script/whcgm?opt=XQHXSEASONGM&type=XQHX_SEASON&cmd=allOne\">全员进1队</a>          ";
            info += "<a href=\"http://" + ipAddress + ":" + port + "/script/whcgm?opt=XQHXSEASONGM&type=XQHX_SEASON&cmd=sendFinalMail\">发最终奖励</a>          ";
            info += "<a href=\"http://" + ipAddress + ":" + port + "/script/whcgm?opt=XQHXSEASONGM&cmd=registRobot\">创建机器人</a>           ";
            info += "<a href=\"http://" + ipAddress + ":" + port + "/script/whcgm?opt=XQHXSEASONGM&cmd=createGuild\">创建联盟</a>           ";
            info += "<br><br>";
            info += stateData.toString() + "<br>";
        } catch (Exception e) {
            HawkException.catchException(e);
        }
        return info;
    }

    public void registRobot(int count) {
        int startId = 1;
        for (int i = startId; i < startId + count; i++) {
            try {
                String puid = "robot_puid_" + (i + 1);
                // 构造登录协议对象
                Login.HPLogin.Builder builder = Login.HPLogin.newBuilder();
                builder.setCountry("cn");
                builder.setChannel("guest");
                builder.setLang("zh-CN");
                builder.setPlatform("android");
                builder.setVersion("1.0.0.0");
                builder.setPfToken("da870ef7cf996eb6");
                builder.setPhoneInfo("{\"deviceMode\":\"win32\",\"mobileNetISP\":\"0\",\"mobileNetType\":\"0\"}\n");
                builder.setPuid(puid);
                builder.setServerId(GsConfig.getInstance().getServerId());
                builder.setDeviceId(puid);

                HawkSession session = new HawkSession(null);
                session.setAppObject(new Player(null));
                if (GsApp.getInstance().doLoginProcess(session, HawkProtocol.valueOf(HP.code.LOGIN_C_VALUE, builder), HawkTime.getMillisecond())) {
                    AccountInfo accountInfo = GlobalData.getInstance().getAccountInfo(puid, GsConfig.getInstance().getServerId());
                    if (accountInfo != null) {
                        // 加载数据
                        accountInfo.setInBorn(false);
                        HawkXID xid = HawkXID.valueOf(GsConst.ObjType.PLAYER, accountInfo.getPlayerId());
                        Player player = (Player) GsApp.getInstance().queryObject(xid).getImpl();
                        PlayerData playerData = GlobalData.getInstance().getPlayerData(accountInfo.getPlayerId(), true);
                        player.updateData(playerData);

                        // 投递消息
                        HawkApp.getInstance().postMsg(player, PlayerAssembleMsg.valueOf(builder.build(), session));
                    }
                }
            } catch (Exception e) {
                HawkException.catchException(e);
            }
        }
    }

    public void createGuild(int count) {
        int createCount = 0;
        Set<String> playerIds = GlobalData.getInstance().getAllPlayerIds();
        for (String playerId : playerIds) {
            Player player = GlobalData.getInstance().makesurePlayer(playerId);
            if (player == null) {
                HawkLog.logPrintln("XQHXGMCreatGuild error, playerId:{}", playerId);
                continue;
            }
            if (player.hasGuild()) {
                continue;
            }
            if (createCount > count) {
                break;
            }
            String guildName = GlobalData.getInstance().randomPlayerName().replaceFirst("指挥官", "");
            String tag = guildName.substring(0, 3);

            GuildCreateObj obj = new GuildCreateObj(guildName, tag, 10000, ConsumeItems.valueOf());
            obj.randomTag();
            player.rpcCall(GameConst.MsgId.GUILD_CREATE, GuildService.getInstance(), new GuildCreateRpcInvoker(player, obj));
            createCount++;
        }
    }
}