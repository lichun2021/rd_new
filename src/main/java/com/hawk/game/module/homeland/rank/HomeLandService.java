package com.hawk.game.module.homeland.rank;

import com.google.protobuf.InvalidProtocolBufferException;
import com.hawk.activity.redis.ActivityGlobalRedis;
import com.hawk.game.crossproxy.CrossProxy;
import com.hawk.game.global.GlobalData;
import com.hawk.game.global.RedisKey;
import com.hawk.game.item.AwardItems;
import com.hawk.game.module.homeland.cfg.HomeLandIssueCfg;
import com.hawk.game.module.homeland.entity.HomeLandComponent;
import com.hawk.game.module.homeland.entity.HomeLandNpc;
import com.hawk.game.module.homeland.entity.PlayerHomeLandEntity;
import com.hawk.game.player.Player;
import com.hawk.game.protocol.HP;
import com.hawk.game.protocol.HomeLand;
import com.hawk.game.protocol.Status;
import com.hawk.game.protocol.SysProtocol;
import com.hawk.game.util.LogUtil;
import com.hawk.log.Action;
import com.hawk.log.LogConst;
import com.hawk.serialize.string.SerializeHelper;
import com.hawk.util.TimeUtil;
import org.hawk.annotation.ProtocolHandler;
import org.hawk.app.HawkAppObj;
import org.hawk.config.HawkConfigManager;
import org.hawk.log.HawkLog;
import org.hawk.net.protocol.HawkProtocol;
import org.hawk.os.HawkException;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkTime;
import org.hawk.redis.HawkRedisSession;
import org.hawk.task.HawkTaskManager;
import org.hawk.xid.HawkXID;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HomeLandService extends HawkAppObj {
    public static HomeLandService instance;
    //本服排行榜
    HomeLandLocalRankProvider localRank = new HomeLandLocalRankProvider();
    //跨服排行榜
    HomeLandCrossRankProvider crossRank = new HomeLandCrossRankProvider();
    //联盟排行榜
    HomeLandGuildRankProvider guildRank = new HomeLandGuildRankProvider();

    private long lastRankRefreshTime;

    public HomeLandService(HawkXID xid) {
        super(xid);
        instance = this;
    }

    @Override
    public boolean onTick() {
        long curTime = HawkTime.getMillisecond();
        long beginningOfInterval = TimeUtil.getHourTime(curTime);
        if (lastRankRefreshTime < beginningOfInterval) {
            localRank.onTick(curTime);
            crossRank.onTick(curTime);
            lastRankRefreshTime = beginningOfInterval;
        }
        guildRank.onTick(curTime);
        return super.onTick();
    }

    public static HomeLandService getInstance() {
        return instance;
    }

    public boolean init() {
        localRank.init();
        crossRank.init();
        guildRank.init();
        return true;
    }

    protected String getNpcRedDotRedisKey() {
        String dayTime = HawkTime.formatNowTime("yyyyMMdd");
        return RedisKey.HOME_LAND_NPC_RED + dayTime;
    }

    public void updatePlayerInfo(HomeLandPlayerRankInfo info) {
        HawkRedisSession redisSession = ActivityGlobalRedis.getInstance().getRedisSession();
        redisSession.hSet(RedisKey.HOME_LAND_RANK_PLAYER, info.getPlayerId(), SerializeHelper.toJson(info));
    }


    public HomeLandRankImpl getRankByType(HomeLandRankType rankType, HomeLandRankServerType serverType, String guildId) {
        HomeLandRankImpl rank = null;
        switch (serverType) {
            case LOCAL:
                rank = localRank.getRankByType(rankType);
                break;
            case CROSS:
                rank = crossRank.getRankByType(rankType);
                break;
            case GUILD:
                rank = guildRank.getRankByType(rankType, guildId);
                break;
            default:
                break;
        }
        return rank;
    }

    public void updateRank(HomeLandRankType rankType, HomeLandRank param, HomeLandPlayerRankInfo playerInfo) {
        localRank.updateRank(rankType, param);
        crossRank.updateRank(rankType, param);
        guildRank.updateRank(rankType, param);
        updatePlayerInfo(playerInfo);
    }

    private void onHomeBuildingInfoRsp(HomeLand.PBHomeLandServiceReq req) {
        HomeLand.HomeLandBuildingInfoReq.Builder homeInfoReq = HomeLand.HomeLandBuildingInfoReq.newBuilder();
        try {
            homeInfoReq.mergeFrom(req.getReq());
        } catch (InvalidProtocolBufferException e) {
            HawkException.catchException(e);
        }
        Player targetPlayer = GlobalData.getInstance().makesurePlayer(homeInfoReq.getTargetPlayerId());
        String mainServerId = GlobalData.getInstance().getMainServerId(homeInfoReq.getServerId());
        boolean liked = homeInfoReq.getLiked();
        if (targetPlayer == null) {
            sendRpcCall(HawkProtocol.valueOf(HP.sys.ERROR_CODE, SysProtocol.HPErrorCode.newBuilder().setHpCode(req.getProtoclType())
                    .setErrCode(Status.HOMELANDError.HOME_LAND_PLAYER_INVALID_VALUE)
                    .setErrFlag(0)), mainServerId, homeInfoReq.getPlayerId());
            return;
        }
        if (!targetPlayer.checkHomeLandFuncUnlock()) {
            sendRpcCall(HawkProtocol.valueOf(HP.sys.ERROR_CODE, SysProtocol.HPErrorCode.newBuilder().setHpCode(req.getProtoclType())
                    .setErrCode(Status.HOMELANDError.HOME_LAND_VISIT_UNLOCK_VALUE)
                    .setErrFlag(0)), mainServerId, homeInfoReq.getPlayerId());
            return;
        }
        PlayerHomeLandEntity entity = targetPlayer.getData().getHomeLandEntity();
        boolean isView = homeInfoReq.getPlayerId().equals(homeInfoReq.getTargetPlayerId());
        entity.getComponent().refreshNpc();
        HawkProtocol respProtocol = HawkProtocol.valueOf(HP.code2.HOME_BUILDING_INFO_S_VALUE, entity.getComponent().toPB(isView, liked, homeInfoReq.getActiveNpcList(), homeInfoReq.getActiveNpcTimes())
                .setPlayerId(targetPlayer.getId())
                .setServerId(targetPlayer.getMainServerId())
                .setGuildTag(targetPlayer.getGuildTag())
                .setPlayerName(targetPlayer.getName())
                .setIsLogin(false));
        sendRpcCall(respProtocol, mainServerId, homeInfoReq.getPlayerId());
    }

    /**
     * 点赞
     * @param req
     */
    private void onHomeBuildingThemeLikeRsp(HomeLand.PBHomeLandServiceReq req) {
        HomeLand.HomeLandThemeLikeReq.Builder likeInfoReq = HomeLand.HomeLandThemeLikeReq.newBuilder();
        try {
            likeInfoReq.mergeFrom(req.getReq());
        } catch (InvalidProtocolBufferException e) {
            HawkException.catchException(e);
        }
        String mainServerId = GlobalData.getInstance().getMainServerId(likeInfoReq.getServerId());
        Player targetPlayer = GlobalData.getInstance().makesurePlayer(likeInfoReq.getTargetPlayerId());
        if (targetPlayer == null) {
            sendRpcCall(HawkProtocol.valueOf(HP.sys.ERROR_CODE, SysProtocol.HPErrorCode.newBuilder().setHpCode(req.getProtoclType())
                    .setErrCode(Status.HOMELANDError.HOME_LAND_PLAYER_INVALID_VALUE)
                    .setErrFlag(0)), mainServerId, likeInfoReq.getPlayerId());
            return;
        }
        if (!targetPlayer.checkHomeLandFuncUnlock()) {
            sendRpcCall(HawkProtocol.valueOf(HP.sys.ERROR_CODE, SysProtocol.HPErrorCode.newBuilder().setHpCode(req.getProtoclType())
                    .setErrCode(Status.HOMELANDError.HOME_LAND_VISIT_UNLOCK_VALUE)
                    .setErrFlag(0)), mainServerId, likeInfoReq.getPlayerId());
            return;
        }
        PlayerHomeLandEntity targetEntity = targetPlayer.getData().getHomeLandEntity();
        targetEntity.getComponent().notifyLikeChange(likeInfoReq.getLiked());
        HawkProtocol crossProtocol = HawkProtocol.valueOf(HP.code2.HOME_BUILDING_LIKE_PUSH_S_VALUE, targetEntity.getComponent().buildLikePush(likeInfoReq.getLiked()));
        sendRpcCall(crossProtocol, mainServerId, likeInfoReq.getPlayerId());
        logHomeLandLike(targetPlayer, likeInfoReq.getPlayerId(), targetEntity.getLikes(), likeInfoReq.getLiked());
    }

    /**
     * npc互动B->A
     * @param req
     */
    private void onHomeInterActiveRpc(HomeLand.PBHomeLandServiceReq req) {
        HomeLand.HomeLandActiveNpcRpc.Builder activeNpcRpc = HomeLand.HomeLandActiveNpcRpc.newBuilder();
        try {
            activeNpcRpc.mergeFrom(req.getReq());
        } catch (InvalidProtocolBufferException e) {
            HawkException.catchException(e);
        }
        Player player = GlobalData.getInstance().makesurePlayer(activeNpcRpc.getFromPlayerId());
        if (player == null) {
            return;
        }
        PlayerHomeLandEntity entity = player.getData().getHomeLandEntity();
        HomeLandComponent component = entity.getComponent();
        HomeLandIssueCfg issueCfg = HawkConfigManager.getInstance().getConfigByKey(HomeLandIssueCfg.class, activeNpcRpc.getCfgId());
        if (issueCfg == null) {
            player.sendError(req.getProtoclType(), Status.SysError.CONFIG_ERROR, 0);
            return;
        }
        AwardItems awardItems = AwardItems.valueOf();
        awardItems.addAward(issueCfg.getReward());
        awardItems.rewardTakeAffectAndPush(player, Action.HOME_LAND_ACTIVE_NPC_AWARD, true);
        Optional<HomeLand.HomeLandNpcPB> optNpc = activeNpcRpc.getNpcListList().stream().filter(v -> v.getUuid().equals(activeNpcRpc.getUuid())).findAny();
        if (!optNpc.isPresent()) {
            player.sendError(req.getProtoclType(), Status.HOMELANDError.HOME_LAND_NPC_NOT_FIND, 0);
            return;
        }
        component.getNpcComp().addActiveNpc(activeNpcRpc.getPlayerId(), optNpc.get());
        entity.notifyUpdate();
        HomeLand.HomeLandNpcInfoPB.Builder builder = HomeLand.HomeLandNpcInfoPB.newBuilder();
        builder.addAllNpcList(activeNpcRpc.getNpcListList());
        builder.setInterActiveTimes(component.getNpcComp().getActiveTimes());
        HawkProtocol activeResponse = HawkProtocol.valueOf(HP.code2.HOME_BUILDING_INTERACTIVE_NPC_S_VALUE, builder);
        player.sendProtocol(activeResponse);
        player.responseSuccess(req.getProtoclType());
    }

    /**
     * npc互动
     * @param req
     */
    private void onHomeInterActiveRsp(HomeLand.PBHomeLandServiceReq req) {
        HomeLand.HomeLandInterActiveNpcReq.Builder activeNpcReq = HomeLand.HomeLandInterActiveNpcReq.newBuilder();
        try {
            activeNpcReq.mergeFrom(req.getReq());
        } catch (InvalidProtocolBufferException e) {
            HawkException.catchException(e);
        }
        String mainServerId = GlobalData.getInstance().getMainServerId(activeNpcReq.getServerId());
        Player targetPlayer = GlobalData.getInstance().makesurePlayer(activeNpcReq.getTargetPlayerId());
        if (targetPlayer == null) {
            sendRpcCall(HawkProtocol.valueOf(HP.sys.ERROR_CODE, SysProtocol.HPErrorCode.newBuilder().setHpCode(req.getProtoclType())
                    .setErrCode(Status.HOMELANDError.HOME_LAND_PLAYER_INVALID_VALUE)
                    .setErrFlag(0)), mainServerId, activeNpcReq.getPlayerId());
            return;
        }
        if (!targetPlayer.checkHomeLandFuncUnlock()) {
            sendRpcCall(HawkProtocol.valueOf(HP.sys.ERROR_CODE, SysProtocol.HPErrorCode.newBuilder().setHpCode(req.getProtoclType())
                    .setErrCode(Status.HOMELANDError.HOME_LAND_VISIT_UNLOCK_VALUE)
                    .setErrFlag(0)), mainServerId, activeNpcReq.getPlayerId());
            return;
        }
        PlayerHomeLandEntity targetEntity = targetPlayer.getData().getHomeLandEntity();
        int hpCode = targetEntity.getComponent().activeNpc(activeNpcReq.getUuid());
        HawkLog.logPrintln("activeNpc uuid:{} from:{},to:{},npc:{}", activeNpcReq.getUuid(), activeNpcReq.getPlayerId(), activeNpcReq.getTargetPlayerId());
        if (hpCode != 0) {
            sendRpcCall(HawkProtocol.valueOf(HP.sys.ERROR_CODE, SysProtocol.HPErrorCode.newBuilder().setHpCode(req.getProtoclType())
                    .setErrCode(hpCode)
                    .setErrFlag(0)), mainServerId, activeNpcReq.getPlayerId());
            return;
        }
        //发送原服发奖
        HomeLand.HomeLandActiveNpcRpc.Builder rpcBuilder = HomeLand.HomeLandActiveNpcRpc.newBuilder();
        HomeLandNpc npc = targetEntity.getComponent().getNpc(activeNpcReq.getUuid());
        rpcBuilder.setFromPlayerId(activeNpcReq.getPlayerId());
        if (npc != null) {
            rpcBuilder.setCfgId(npc.getCfgId());
            rpcBuilder.setUuid(npc.getUuid());
            rpcBuilder.setPlayerId(targetEntity.getPlayerId());
        }
        for (HomeLandNpc value : targetEntity.getComponent().getNpcComp().getNpcList().values()) {
            HomeLand.HomeLandNpcPB.Builder npcBuilder = value.toBuilder();
            if (activeNpcReq.getActiveNpcList().contains(value.getUuid()) || value.getUuid().equals(activeNpcReq.getUuid())) {
                npcBuilder.setInteracted(true);
            }
            rpcBuilder.addNpcList(npcBuilder);
        }
        HomeLand.PBHomeLandServiceReq.Builder csReq = HomeLand.PBHomeLandServiceReq.newBuilder();
        csReq.setProtoclType(HP.code2.HOME_BUILDING_INTERACTIVE_NPC_REQ_VALUE);
        csReq.setReq(rpcBuilder.build().toByteString());
        HawkProtocol sendProto = HawkProtocol.valueOf(HP.code2.HOME_BUILDING_SERVICE_REQ_VALUE, csReq);
        sendRpcCall(sendProto, mainServerId, null);
    }


    /**
     * 发送跨服协议，本服投递到玩家|service执行
     *
     * @param protocol
     * @param serverId
     * @param targetPlayer
     */
    public boolean sendRpcCall(HawkProtocol protocol, String serverId, String targetPlayer) {
        boolean hasTarget = !HawkOSOperator.isEmptyString(targetPlayer);
        if (GlobalData.getInstance().isLocalServer(serverId)) {
            if (!hasTarget) {
                return HawkTaskManager.getInstance().postProtocol(this.getXid(), protocol);
            }
            Player player = GlobalData.getInstance().makesurePlayer(targetPlayer);
            if (player == null) {
                return false;
            }
            return player.sendProtocol(protocol);
        } else {
            int protoType = hasTarget ? CrossProxy.ProtoType.PROTOCOL : CrossProxy.ProtoType.NOTIFY;
            return CrossProxy.getInstance().sendProtocol(protocol, serverId, null, targetPlayer, protoType);
        }
    }

    /**
     * B->接收家园
     *
     * @param protocol
     * @return
     */
    @ProtocolHandler(code = HP.code2.HOME_BUILDING_SERVICE_REQ_VALUE)
    private boolean onHomeInfo(HawkProtocol protocol) {
        HomeLand.PBHomeLandServiceReq req = protocol.parseProtocol(HomeLand.PBHomeLandServiceReq.getDefaultInstance());
        final int protoType = req.getProtoclType();
        switch (protoType) {
            case HP.code2.HOME_BUILDING_INFO_C_VALUE:
                onHomeBuildingInfoRsp(req);
                break;
            case HP.code2.HOME_BUILDING_THEME_LIKE_C_VALUE:
                onHomeBuildingThemeLikeRsp(req);
                break;
            case HP.code2.HOME_BUILDING_INTERACTIVE_NPC_C_VALUE:
                onHomeInterActiveRsp(req);
                break;
            case HP.code2.HOME_BUILDING_INTERACTIVE_NPC_REQ_VALUE:
                onHomeInterActiveRpc(req);
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * @param player
     */
    public void logHomeLandLike(Player player, String targetPlayerId, int likes, boolean liked) {
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("targetPlayerId", targetPlayerId);
            param.put("likes", likes);
            param.put("liked", liked ? 1 : 0);
            LogUtil.logActivityCommon(player, LogConst.LogInfoType.home_land_like, param);
        } catch (Exception e) {
            HawkException.catchException(e);
        }
    }
}
