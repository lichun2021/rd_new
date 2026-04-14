package com.hawk.game.module.homeland.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hawk.game.global.RedisKey;
import com.hawk.game.global.RedisProxy;
import com.hawk.game.module.homeland.cfg.HomeLandConstKVCfg;
import com.hawk.game.module.homeland.cfg.HomeLandIssueCfg;
import com.hawk.game.module.homeland.map.HomeLandMapBlock;
import com.hawk.game.module.homeland.map.HomeLandMapService;
import com.hawk.game.player.hero.SerializJsonStrAble;
import com.hawk.game.protocol.HomeLand;
import com.hawk.game.util.GameUtil;
import org.hawk.config.HawkConfigManager;
import org.hawk.log.HawkLog;
import org.hawk.os.HawkTime;
import org.hawk.redis.HawkRedisSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class HLNpcComp implements SerializJsonStrAble {
    private final Map<String, HomeLandNpc> npcMap = new ConcurrentHashMap<>();
    private final Map<String, HomeLandNpcActive> activeNpc = new ConcurrentHashMap<>();
    private AtomicLong lastTickTime = new AtomicLong(0);
    private int activeTimes;

    /**
     * 序列化
     */
    @Override
    public String serializ() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activeTimes", activeTimes);
        jsonObject.put("lastTickTime", lastTickTime.get());
        JSONArray arr = new JSONArray();
        npcMap.values().stream().map(HomeLandNpc::serializ).forEach(arr::add);
        jsonObject.put("npcInfo", arr);
        JSONArray activeJson = new JSONArray();
        activeNpc.values().stream().map(HomeLandNpcActive::serializ).forEach(activeJson::add);
        jsonObject.put("activeNpc", activeJson);
        return jsonObject.toJSONString();
    }

    @Override
    public void mergeFrom(String serialiedStr) {
        JSONObject obj = JSONObject.parseObject(serialiedStr);
        if (obj != null) {
            if (obj.containsKey("npcInfo")) {
                JSONArray arr = obj.getJSONArray("npcInfo");
                if (arr == null) {
                    return;
                }
                arr.forEach(str -> {
                    HomeLandNpc npc = new HomeLandNpc();
                    npc.mergeFrom(str.toString());
                    npcMap.put(npc.getUuid(), npc);
                });
            }
            if (obj.containsKey("activeNpc")) {
                JSONArray arr = obj.getJSONArray("activeNpc");
                if (arr == null) {
                    return;
                }
                arr.forEach(str -> {
                    HomeLandNpcActive active = new HomeLandNpcActive();
                    active.mergeFrom(str.toString());
                    activeNpc.put(active.getPlayerId(), active);
                });
            }
            if (obj.containsKey("activeTimes")) {
                this.activeTimes = obj.getInteger("activeTimes");
            }
            if (obj.containsKey("lastTickTime")) {
                this.lastTickTime = new AtomicLong(obj.getLong("lastTickTime"));
            }
        }
    }

    public HomeLand.HomeLandNpcInfoPB.Builder buildNpcPb(List<String> activeList, int activeTimes) {
        HomeLand.HomeLandNpcInfoPB.Builder builder = HomeLand.HomeLandNpcInfoPB.newBuilder();
        for (HomeLandNpc value : npcMap.values()) {
            HomeLand.HomeLandNpcPB.Builder npcPb = value.toBuilder();
            if (activeList.contains(value.getUuid())) {
                npcPb.setInteracted(true);
            }
            builder.addNpcList(npcPb.build());
        }
        builder.setInterActiveTimes(activeTimes);
        return builder;
    }

    protected String getNpcRedDotRedisKey() {
        String dayTime = HawkTime.formatNowTime("yyyyMMdd");
        return RedisKey.HOME_LAND_NPC_RED + dayTime;
    }

    public void updatePlayerRedPoint(String playerId) {
        long curSeconds = HawkTime.getMillisecond();
        HawkRedisSession redisSession = RedisProxy.getInstance().getRedisSession();
        int second = (int) ((HawkTime.getNextAM0Date() - curSeconds) / 1000);
        redisSession.hIncrBy(getNpcRedDotRedisKey(), playerId, 1, second);
    }

    public Map<String, HomeLandNpc> getNpcList() {
        return npcMap;
    }

    public Map<String, HomeLandNpcActive> getActiveNpc() {
        return activeNpc;
    }

    /**
     * 刷npc,
     */
    public boolean refreshNpc(String playerId, int themeId, long currentTime) {
        List<Long> needRefresh = new ArrayList<>();
        HomeLandConstKVCfg cfg = HawkConfigManager.getInstance().getKVInstance(HomeLandConstKVCfg.class);

        List<Integer> refreshTimeArray = cfg.getNpcRefreshTime();
        for (int time : refreshTimeArray) {
            long refreshTime = HawkTime.getHourOfDayTime(currentTime, time);
            if (lastTickTime.get() < refreshTime && currentTime >= refreshTime) {
                needRefresh.add(refreshTime);
            }
        }
        if (needRefresh.isEmpty()) {
            return false;
        }
        if (npcMap.size() > refreshTimeArray.size()) {
            return false;
        }
        boolean needRefreshNpc = false;
        for (Long refreshTime : needRefresh) {
            long probability = GameUtil.randomProbability();
            if (cfg.getIssueRate() <= probability) {
                HawkLog.logPrintln("homeland probability failed playerId:{},refreshTime:{},probability:{}", playerId, refreshTime, probability);
                continue;
            }
            boolean timeNpc = npcMap.values().stream().anyMatch(v -> v.getCreateTime() == refreshTime);
            if (timeNpc) {
                HawkLog.logPrintln("homeland already have playerId:{},refreshTime:{}", playerId, refreshTime);
                continue;
            }
            int npcSize = HawkConfigManager.getInstance().getConfigSize(HomeLandIssueCfg.class);
            int randomValue = new Random().nextInt(npcSize);
            HomeLandIssueCfg issueCfg = HawkConfigManager.getInstance().getConfigByIndex(HomeLandIssueCfg.class, randomValue);
            if (issueCfg == null) {
                continue;
            }
            int pointId = 0;
            if (issueCfg.getIsMove() == 0) {
                //宝箱去重复
                HomeLandMapBlock mapBlock = HomeLandMapService.getInstance().getBlock(themeId);
                List<Integer> blockList = npcMap.values().stream().filter(v -> v.getType() == 0).map(HomeLandNpc::getPointId).collect(Collectors.toList());
                pointId = mapBlock.randStopPoint(blockList);
                if (pointId <= 0) {
                    HawkLog.logPrintln("homeland pointId failed playerId:{},cfgId:{},time:{},block:{}", playerId, issueCfg.getId(), refreshTime, blockList);
                    continue;
                }
            }
            HomeLandNpc npc = HomeLandNpc.valueOf(issueCfg.getId(), issueCfg.getIsMove(), pointId, refreshTime);
            npcMap.put(npc.getUuid(), npc);
            updatePlayerRedPoint(playerId);
            HawkLog.logPrintln("homeland refreshNpc playerId:{},cfgId:{},time:{},pointId:{},lastTickTime:{}", playerId, issueCfg.getId(), refreshTime, pointId, lastTickTime.get());
            needRefreshNpc = true;
        }
        lastTickTime.set(HawkTime.getMillisecond());
        return needRefreshNpc;
    }

    public void addActiveNpc(String playerId, HomeLand.HomeLandNpcPB npc) {
        HomeLandNpcActive active = this.activeNpc.computeIfAbsent(playerId, k -> HomeLandNpcActive.valueOf(playerId));
        active.addNpc(npc);
        this.activeTimes += 1;
    }

    public boolean isActive(String playerId, String uuid) {
        HomeLandNpcActive active = activeNpc.get(playerId);
        return active == null || !active.getActiveNpcMap().containsKey(uuid);
    }

    public int getActiveTimes() {
        return activeTimes;
    }

    public void setActiveTimes(int activeTimes) {
        this.activeTimes = activeTimes;
    }

    public List<String> getActiveNpcList(String playerId) {
        HomeLandNpcActive active = getActiveNpc().get(playerId);
        List<String> activeList = new ArrayList<>();
        if (active != null) {
            activeList.addAll(active.getActiveNpcMap().keySet());
        }
        return activeList;
    }
}
