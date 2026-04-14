package com.hawk.game.script;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hawk.app.HawkApp;
import org.hawk.config.HawkConfigManager;
import org.hawk.config.iterator.ConfigIterator;
import org.hawk.log.HawkLog;
import org.hawk.net.protocol.HawkProtocol;
import org.hawk.net.session.HawkSession;
import org.hawk.os.HawkException;
import org.hawk.os.HawkRand;
import org.hawk.os.HawkTime;
import org.hawk.script.HawkScript;
import org.hawk.script.HawkScriptHttpInfo;
import org.hawk.xid.HawkXID;

import com.hawk.activity.ActivityManager;
import com.hawk.activity.event.impl.AddTavernScoreEvent;
import com.hawk.game.GsApp;
import com.hawk.game.GsConfig;
import com.hawk.game.config.TavernAchieveCfg;
import com.hawk.game.entity.GuildInfoObject;
import com.hawk.game.entity.TavernEntity;
import com.hawk.game.global.GlobalData;
import com.hawk.game.global.LocalRedis;
import com.hawk.game.global.RedisProxy;
import com.hawk.game.guild.GuildCreateObj;
import com.hawk.game.invoker.GuildCreateRpcInvoker;
import com.hawk.game.item.ConsumeItems;
import com.hawk.game.msg.PlayerAssembleMsg;
import com.hawk.game.player.Player;
import com.hawk.game.player.PlayerData;
import com.hawk.game.protocol.HP;
import com.hawk.game.protocol.Login;
import com.hawk.game.protocol.Status;
import com.hawk.game.protocol.Tavern;
import com.hawk.game.queryentity.AccountInfo;
import com.hawk.game.rank.RankService;
import com.hawk.game.rank.guildActive.GuildActiveRankObject;
import com.hawk.game.service.GuildService;
import com.hawk.game.util.GsConst;
import com.hawk.game.util.GuildUtil;
import com.hawk.game.util.LogUtil;
import com.hawk.gamelib.GameConst;

import redis.clients.jedis.Jedis;

/**
 *功能: @author SJS
 * @description 联盟推荐GM
 * localhost:8080/script/recommendGuild?type=[createGuild,addActive,guildRank,registerRobot,updateRank] num=[默认 50]
 * @date 2025/11/4
 */
public class RecommendGuildGMHandler extends HawkScript {
    @Override
    public String action(Map<String, String> param, HawkScriptHttpInfo hawkScriptHttpInfo) {

        String type = param.get("type");
        switch (type) {
            case "guildRank":
                return printRankInfo();
            case "addActive":
                addActive();
                break;
            case "createGuild":
                createGuild(param.get("num") == null ? 50 : Integer.parseInt(param.get("num")));
                break;
            case "registerRobot":
                registRobot(param.get("num") == null ? 50 : Integer.parseInt(param.get("num")));
                break;
            case "addPlayerActive":
                Player player = GlobalData.getInstance().makesurePlayer(param.get("playerId"));
                addActive(player);
                break;
            case "updateRank":
                RankService.getInstance().getGuildActiveRankObject().loadCacheRank();
                return printRankInfo();
            case "clearRank":
                try (Jedis jedis = RedisProxy.getInstance().getRedisSession().getJedis()) {
                    Set<String> keys = jedis.keys(LocalRedis.getInstance().getLocalIdentify() + ":guild_active_rank:*");
                    for (String key : keys) {
                        jedis.del(key);
                    }
                } catch (Exception e) {
                    HawkException.catchException(e);
                }
                RankService.getInstance().getGuildActiveRankObject().loadCacheRank();
                return printRankInfo();
            default:
                return "error";
        }
        return "ok";
    }

    public void createGuild(int count) {
        int createCount = 0;
        Set<String> playerIds = GlobalData.getInstance().getAllPlayerIds();
        for (String playerId : playerIds) {
            Player player = GlobalData.getInstance().makesurePlayer(playerId);
            if (player == null) {
                HawkLog.logPrintln("RecommendGuildGMHandler error, playerId:{}", playerId);
                continue;
            }
            if (player.hasGuild()) {
                continue;
            }
            if (!player.getPuid().contains("robotG_puId")) {
                continue;
            }
            if (createCount > count) {
                break;
            }
            String name = GlobalData.getInstance().randomPlayerName().replaceFirst("指挥官", "");
            String guildName = name + "-机" + name.substring(0, 3);
            String tag = guildName.substring(0, 3);

            GuildCreateObj obj = new GuildCreateObj(guildName, tag, 10000, ConsumeItems.valueOf());
            int maxTryTime = 10;
            for (int i = 0; i <= maxTryTime; i++) {
                // 随机简称
                tag = obj.randomTag();
                int checkResult = GuildUtil.checkGuildTag(tag);
                if (checkResult == Status.SysError.SUCCESS_OK_VALUE) {
                    player.rpcCall(GameConst.MsgId.GUILD_CREATE, GuildService.getInstance(), new GuildCreateRpcInvoker(player, obj));
                    break;
                }
            }
            createCount++;
        }
    }

    public void registRobot(int num) {
        int startId = 1;
        int count = num;
        for (int i = startId; i < startId + count; i++) {
            try {
                String puid = "robotG_puId_" + (i + 1);
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
                    AccountInfo accountInfo = GlobalData.getInstance().getAccountInfo(puid+"#android", GsConfig.getInstance().getServerId());
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

    public String printRankInfo() {
        List<GuildActiveRankObject.GuildActiveRankInfo> rankInfos = RankService.getInstance().getGuildActiveRankObject().getRankCache();
        //页面信息
        StringBuilder info = new StringBuilder();
        info.append("<table border=\"1\">");
        try {
            int index = 1;
            for (GuildActiveRankObject.GuildActiveRankInfo rankInfo : rankInfos) {
                GuildInfoObject guildInfo = GuildService.getInstance().getGuildInfoObject(rankInfo.getGuildId());
                info.append("<tr>");
                info.append("<td>").append(index++).append("</td>");
                info.append("<td>联盟Tag: ").append(guildInfo.getTag()).append(" </td>");
                info.append("<td>联盟Name: ").append(guildInfo.getName()).append(" </td>");
                info.append("<td>联盟Id: ").append(rankInfo.getGuildId()).append(" </td>");
                info.append("<td>积分: ").append(rankInfo.getScore()).append(" </td>");
                info.append("</tr>");
            }
            info.append("</table>");
        } catch (Exception e) {
            HawkException.catchException(e);
        }
        return info.toString();
    }

    public void addActive() {
        Set<String> playerIds = GlobalData.getInstance().getAllPlayerIds();
        for (String playerId : playerIds) {
            AccountInfo accountInfo = GlobalData.getInstance().getAccountInfoByPlayerId(playerId);
            if (accountInfo != null) {
                accountInfo.setInBorn(false);
            }
            Player player = GlobalData.getInstance().makesurePlayer(playerId);
            if (player == null) {
                continue;
            }
            if (!player.getPuid().contains("robotG_puId_")) {
                continue;
            }

            addActive(player);
        }
    }

    private void addActive(Player player) {
        ConfigIterator<TavernAchieveCfg> cfgs = HawkConfigManager.getInstance().getConfigIterator(TavernAchieveCfg.class);
        TavernEntity tavernEntity = player.getData().getTavernEntity();
        int randInt = HawkRand.randInt(15);
        for (int i = 0; i <= randInt; i++) {
            TavernAchieveCfg config = cfgs.next();
            int addCount = HawkRand.randInt(8);

            int beforeScore = getTotalScore(tavernEntity);

            int count = tavernEntity.addFinishCount(config.getAchieveId(), addCount);

            if (logger.isDebugEnabled()) {
                logger.debug("[tavern] playerId:{} finish tavern achieve achieveId:{} count:{}", player.getId(), config.getAchieveId(), count);
            }

            // 增加积分
            int addScore = config.getScore() * addCount;
            int afterScore = getTotalScore(tavernEntity);
            ActivityManager.getInstance().postEvent(new AddTavernScoreEvent(player.getId(), addScore, afterScore));
            if (player.hasGuild()) {
                RankService.getInstance().updateActiveRankScore(player.getGuildId(), addScore);
            }

            // 同步次数信息
            Tavern.FinishCountPB.Builder countPB = Tavern.FinishCountPB.newBuilder();
            countPB.setAchieveId(config.getAchieveId());
            countPB.setFinishCount(count);
            player.sendProtocol(HawkProtocol.valueOf(HP.code.TAVERN_COUNT_SYNC_S_VALUE, countPB));

            LogUtil.logDailyActiveScoreChange(player, beforeScore, afterScore, addScore, config.getAchieveType().getValue());
        }
    }

    /**
     * 获取每日任务总积分
     *
     * @return
     */
    public int getTotalScore(TavernEntity tavernEntity) {
        Map<Integer, Integer> achieveFinishMap = tavernEntity.getAchieveFinishMap();
        int totalScore = 0;
        for (Map.Entry<Integer, Integer> entry : achieveFinishMap.entrySet()) {
            TavernAchieveCfg config = HawkConfigManager.getInstance().getConfigByKey(TavernAchieveCfg.class, entry.getKey());
            if (config == null) {
                continue;
            }

            totalScore += config.getScore() * entry.getValue();
        }

        return totalScore;
    }

}
