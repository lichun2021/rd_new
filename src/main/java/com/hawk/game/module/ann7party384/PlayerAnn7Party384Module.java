package com.hawk.game.module.ann7party384;

import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.hawk.annotation.MessageHandler;
import org.hawk.annotation.ProtocolHandler;
import org.hawk.config.HawkConfigManager;
import org.hawk.log.HawkLog;
import org.hawk.net.protocol.HawkProtocol;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkTime;
import org.hawk.util.HawkNumberConvert;

import com.hawk.activity.type.impl.ann7party384.cfg.Ann7Party384KVCfg;
import com.hawk.activity.type.impl.ann7party384.cfg.Ann7Party384ShopCfg;
import com.hawk.activity.type.impl.ann7party384.entity.Ann7Party384Entity;
import com.hawk.game.GsApp;
import com.hawk.game.crossproxy.CrossService;
import com.hawk.game.crossproxy.model.CsPlayer;
import com.hawk.game.global.GlobalData;
import com.hawk.game.global.RedisProxy;
import com.hawk.game.item.AwardItems;
import com.hawk.game.item.ConsumeItems;
import com.hawk.game.item.ItemInfo;
import com.hawk.game.module.ann7party384.data.Party384Room;
import com.hawk.game.msg.Ann7PartyGiftBuyMsg;
import com.hawk.game.player.Player;
import com.hawk.game.player.PlayerModule;
import com.hawk.game.protocol.Act384Ann7Party.ApplyJoinRoomReq;
import com.hawk.game.protocol.Act384Ann7Party.ApplyJoinRoomResp;
import com.hawk.game.protocol.Act384Ann7Party.CreatePartyRoomReq;
import com.hawk.game.protocol.Act384Ann7Party.DealInviteReq;
import com.hawk.game.protocol.Act384Ann7Party.DealJoinApplyReq;
import com.hawk.game.protocol.Act384Ann7Party.InviteJoinReq;
import com.hawk.game.protocol.Act384Ann7Party.InviteSearchReq;
import com.hawk.game.protocol.Act384Ann7Party.InviteSearchResp;
import com.hawk.game.protocol.Act384Ann7Party.JoinRoomComsumeBackType;
import com.hawk.game.protocol.Act384Ann7Party.KickoutMemberReq;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomBriefInfo;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomMemberInfo;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomRecordListPB;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomRecordPB;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomSelectSetReq;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomShareReq;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomState;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomStateReq;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomStateResp;
import com.hawk.game.protocol.Act384Ann7Party.PlayerExitQueueSync;
import com.hawk.game.protocol.Act384Ann7Party.PlayerQueueUpResp;
import com.hawk.game.protocol.Act384Ann7Party.RoomStatePB;
import com.hawk.game.protocol.Act384Ann7Party.SearchRoomReq;
import com.hawk.game.protocol.Act384Ann7Party.SearchRoomResp;
import com.hawk.game.protocol.Act384Ann7Party.SelectRewardReq;
import com.hawk.game.protocol.Act384Ann7Party.SelectRewardResp;
import com.hawk.game.protocol.Act384Ann7Party.ShareRoomType;
import com.hawk.game.protocol.Act384Ann7Party.ShopBuyReq;
import com.hawk.game.protocol.HP;
import com.hawk.game.protocol.Status;
import com.hawk.game.queryentity.AccountInfo;
import com.hawk.log.Action;

/**
 * 7周年房间聚会活动功能
 * 
 * @author lating
 *
 */
public class PlayerAnn7Party384Module extends PlayerModule {

	public PlayerAnn7Party384Module(Player player) {
		super(player);
	}

	@Override
	public boolean isListenProto(int proto) {
		if (player instanceof CsPlayer) {
			return false;
		}
		return super.isListenProto(proto);
	}
	
	@Override
	protected boolean onPlayerLogin() {
		if (player.ann7PartyExitQueueTime > GsApp.getInstance().getCurrentTime()) {
			syncPlayerExitQueueInfo();
		}
		return super.onPlayerLogin();
	}
	
	@ProtocolHandler(code = HP.code2.ANN7_PARTY_INFO_C_VALUE)
	private boolean onActivityInfoReq(HawkProtocol protocol) {
		if (!Ann7Party384Service.getInstance().isActivityOpening(player.getId())) {
			sendError(protocol.getType(), Status.Error.ACTIVITY_NOT_OPEN_VALUE);
			return false;
		}
		Ann7Party384Service.getInstance().syncActivityInfo(player);
		return true;
	}

	/**
	 * 创建房间（房主操作）
	 * @param protocol
	 * @return
	 */
	@ProtocolHandler(code = HP.code2.ANN7_PARTY_CREATE_ROOM_C_VALUE)
	private boolean onCreateRoomReq(HawkProtocol protocol) {
		if (!Ann7Party384Service.getInstance().isActivityOpening(player.getId())) {
			sendError(protocol.getType(), Status.Error.ACTIVITY_NOT_OPEN_VALUE);
			return false;
		}
		
		//距离活动结束不到x分钟，不能再创建房间
		if (Ann7Party384Service.getInstance().nearActivityEnd()) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_NEAR_ACT_END);
			HawkLog.errPrintln("anny party create room error: {}, playerId: {}", "near act end", player.getId());
			return false;
		}
		
		//不在可操作时间范围
		if (!Ann7Party384KVCfg.getInstance().checkTimeRange()) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_NOT_IN_TIME_RANGE);
			HawkLog.errPrintln("anny party create room error: {}, playerId: {}", "not in time range", player.getId());
			return false;
		}
				
		//判断条件：是否已在房间里面、是否还有未处理的聚会奖励、当日创建房间次数是否已满、是否有券
		CreatePartyRoomReq req = protocol.parseProtocol(CreatePartyRoomReq.getDefaultInstance());
		//已经在房间里面了
		if (Ann7Party384Service.getInstance().playerInRoom(player.getId())) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_IN_ROOM);
			HawkLog.errPrintln("anny party create room error: {}, playerId: {}", "in room", player.getId());
			return false;
		}
		
		Ann7Party384Entity entity = Ann7Party384Service.getInstance().getDBEntity(player.getId());
		//还有未处理的奖励
//		if (!entity.getPartyRewardMap().isEmpty()) {
//			sendError(protocol.getType(), Status.Error.ANN7_PARTY_REWARD_REMAIN);
//			HawkLog.errPrintln("anny party create room error: {}, playerId: {}", "reward not empty", player.getId());
//			return false;
//		}
		
		//当日创建房间数量已达上限
		if (entity.getCreateRoomCount() >= Ann7Party384KVCfg.getInstance().getOpenPartyRoomValueLimit()) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_CREATE_ROOM_LIMIT);
			HawkLog.errPrintln("anny party create room error: {}, playerId: {}", "daily limit", player.getId());
			return false;
		}
		
		int masterAwardRemain = Ann7Party384KVCfg.getInstance().getHomeownerDaliyGetLimit() - entity.getMasterAwardCount();
		if (masterAwardRemain <= 0) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_MASTER_AWARD_LIMIT);
			HawkLog.errPrintln("anny party create room error: {}, playerId: {}", "party award limit", player.getId());
			return false;
		}
		
		//本组服同时存在的房间数，已达上限
		if (Ann7Party384Service.getInstance().getRoomCount() >= Ann7Party384KVCfg.getInstance().getGroupRoomLimit()) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_ROOM_MAX);
			HawkLog.errPrintln("anny party create room error: {}, playerId: {}", "server group limit", player.getId());
			return false;
		}
		
		String createRoomConsume = Ann7Party384KVCfg.getInstance().getOpenPartyRoomItem();
		if (!HawkOSOperator.isEmptyString(createRoomConsume)) {
			ConsumeItems consume = ConsumeItems.valueOf();
			consume.addConsumeInfo(ItemInfo.valueListOf(createRoomConsume));
			if (!consume.checkConsume(player, protocol.getType())) {
				HawkLog.errPrintln("anny party create room error: {}, playerId: {}", "consume", player.getId());
				return false;
			}
			consume.consumeAndPush(player, Action.ANNY_PARTY_CREATE_ROOM);
		}
		
		Ann7Party384Service.getInstance().leaveQueue(player.getId());
		Ann7Party384Service.getInstance().createPartyRoom(player, req.getSetInfo());
		//同步数据
		Ann7Party384Service.getInstance().syncActivityInfo(player);
		player.responseSuccess(protocol.getType());
		Party384Room newRoom = Ann7Party384Service.getInstance().getPlayerRoom(player.getId());
		HawkLog.logPrintln("anny party create room succ, playerId: {}, roomId: {}, groupId: {}",  player.getId(), newRoom.getRoomId(), Ann7Party384Service.getInstance().getGroupId());
		return true;
	}
	
	/**
	 * 分享房间信息 （房主和成员都可以操作）
	 */
	@ProtocolHandler(code = HP.code2.ANN7_PARTY_SHARE_ROOM_C_VALUE)
	private boolean onShareRoomReq(HawkProtocol protocol) {
		if (!Ann7Party384Service.getInstance().isActivityOpening(player.getId())) {
			sendError(protocol.getType(), Status.Error.ACTIVITY_NOT_OPEN_VALUE);
			return false;
		}
		Party384Room room = Ann7Party384Service.getInstance().getPlayerRoom(player.getId());
		if (room == null) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_NOT_IN_ROOM);
			HawkLog.errPrintln("anny party share room error: {}, playerId: {}", "not in room", player.getId());
			return false;
		}
		long time = HawkTime.getMillisecond();
		PartyRoomShareReq req = protocol.parseProtocol(PartyRoomShareReq.getDefaultInstance());
		if (req.getType() == ShareRoomType.TO_GUILD && !player.hasGuild()) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_SHARE_NEED_GUILD);
			HawkLog.errPrintln("anny party share room error: {}, playerId: {}", "no guild", player.getId());
			return false;
		}
		if (req.getType() == ShareRoomType.TO_GUILD && time - room.getShareTime(player.getGuildId()) < Ann7Party384KVCfg.getInstance().getShareCD()) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_SHARE_CD_BREAK);
			Ann7Party384Service.getInstance().syncActivityInfo(player);
			HawkLog.errPrintln("anny party share room error: {}, playerId: {}", "guild cd", player.getId());
			return false;
		}
		
		if (req.getType() == ShareRoomType.TO_WORLD && time - room.getShareTime(Ann7PartyConst.SHARE_SERVER) < Ann7Party384KVCfg.getInstance().getShareCD()) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_SHARE_CD_BREAK);
			Ann7Party384Service.getInstance().syncActivityInfo(player);
			HawkLog.errPrintln("anny party share room error: {}, playerId: {}", "world cd", player.getId());
			return false;
		}
		
		Ann7Party384Service.getInstance().shareRoom(player, room, req, true);
		player.responseSuccess(protocol.getType());
		return true;
	}
	
	/**
	 * 设置房间选项（房主操作）
	 * @param protocol
	 * @return
	 */
	@ProtocolHandler(code = HP.code2.ANN7_PARTY_ROOM_SET_C_VALUE)
	private boolean onSetRoomReq(HawkProtocol protocol) {
		if (!Ann7Party384Service.getInstance().isActivityOpening(player.getId())) {
			sendError(protocol.getType(), Status.Error.ACTIVITY_NOT_OPEN_VALUE);
			return false;
		}
		Party384Room room = Ann7Party384Service.getInstance().getPlayerRoom(player.getId());
		//不是房主不能设置
		if (!Ann7Party384Service.getInstance().isRoomMaster(player.getId())) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_NEED_ROOM_MASTER);
			HawkLog.errPrintln("anny party set room error: {}, playerId: {}", room == null ? "room null" : "no master", player.getId());
			return false;
		}
		
		if (room.getRoundBeginTime() > 0) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_ROOM_STARTED);
			HawkLog.errPrintln("anny party set room error: {}, playerId: {}", "room party started", player.getId());
			return false;
		}
		
		PartyRoomSelectSetReq req = protocol.parseProtocol(PartyRoomSelectSetReq.getDefaultInstance());
		Ann7Party384Service.getInstance().setRoom(room, req.getSetInfo());
		player.responseSuccess(protocol.getType());
		HawkLog.logPrintln("anny party set room succ, playerId: {}, autoStart: {}, joinApply: {}", player.getId(), req.getSetInfo().getAutoStart(), req.getSetInfo().getJoinApply());
		return true;
	}
	
	/**
	 * 邀请加入房间（房主操作）
	 * @param protocol
	 * @return
	 */
	@ProtocolHandler(code = HP.code2.ANN7_PARTY_INVITE_C_VALUE)
	private boolean onInviteJoinRoomReq(HawkProtocol protocol) {
		if (!Ann7Party384Service.getInstance().isActivityOpening(player.getId())) {
			sendError(protocol.getType(), Status.Error.ACTIVITY_NOT_OPEN_VALUE);
			return false;
		}
		Party384Room room = Ann7Party384Service.getInstance().getPlayerRoom(player.getId());
		//不是房主不能邀请
		if (!Ann7Party384Service.getInstance().isRoomMaster(player.getId())) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_NEED_ROOM_MASTER);
			HawkLog.errPrintln("anny party invite join room error: {}, playerId: {}", room == null ? "room null" : "no master", player.getId());
			return false;
		}
		
		if (room.isFullMember() || room.getRoundBeginTime() > 0) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_ROOM_MEM_FULL);
			HawkLog.errPrintln("anny party invite join room error: {}, playerId: {}", room.isFullMember() ? "full member" : "started", player.getId());
			return false;
		}
		
		//超出上限
		if (room.getInviteCount() >= Ann7Party384KVCfg.getInstance().getRoomInviteLimit()) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_INVITE_COUNT_LIMIT);
			HawkLog.errPrintln("anny party invite join room error: {}, playerId: {}", "invite count limit", player.getId());
			return false;
		}
		
		InviteJoinReq req = protocol.parseProtocol(InviteJoinReq.getDefaultInstance());
		if (HawkOSOperator.isEmptyString(req.getPlayerId())) {
			sendError(protocol.getType(), Status.SysError.PARAMS_INVALID_VALUE);
			return false;
		}
		
		Party384Room tarPlayerRoom = Ann7Party384Service.getInstance().getPlayerRoom(req.getPlayerId());
		if (tarPlayerRoom != null) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_TARGET_IN_ROOM);
			HawkLog.errPrintln("anny party invite join room error: {}, playerId: {}", req.getPlayerId() + " in room", player.getId());
			return false;
		}
		
		//不能邀请自己
		if (player.getId().equals(req.getPlayerId())) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_INVITE_SELF_ERR);
			HawkLog.errPrintln("anny party invite join room error: {}, playerId: {}", "invite self", player.getId());
			return false;
		}
		
		//邀请对象已经是房客成员了
		if (room.isRoomMember(req.getPlayerId())) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_ROOM_MEMNER_ERR);
			Ann7Party384Service.getInstance().syncActivityInfo(player);
			HawkLog.errPrintln("anny party invite join room error: {}, playerId: {}", req.getPlayerId() + " in room", player.getId());
			return false;
		}
		
		//已经发出过邀请了
		if (room.isInvitedPlayer(req.getPlayerId())) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_INVITE_REPEAT);
			Ann7Party384Service.getInstance().syncRoomInvitePlayer(room, 0, "");
			HawkLog.errPrintln("anny party invite join room error: {}, playerId: {}", req.getPlayerId() + " invite history", player.getId());
			return false;
		}
		
		//对方处于跨服状态
		if (CrossService.getInstance().isCrossPlayer(req.getPlayerId())) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_PLAYER_CROSS_SERVER);
			HawkLog.errPrintln("anny party invite join room error: {}, playerId: {}", req.getPlayerId() + " cross server", player.getId());
			return false;
		}
		
		String key = Ann7Party384Service.getInstance().refuseInviteKey(room.getRoomId(), req.getPlayerId());
		String refuseTime = RedisProxy.getInstance().getRedisSession().getString(key);
		if (!HawkOSOperator.isEmptyString(refuseTime)) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_REFUSE_TIME_CD);
			HawkLog.errPrintln("anny party invite join room error: {}, playerId: {}", req.getPlayerId() + " refuse cd", player.getId());
			return false;
		}
		
		Ann7Party384Service.getInstance().inviteJoinRoom(room, req, true);
		player.responseSuccess(protocol.getType());
		HawkLog.logPrintln("anny party invite join room succ, playerId: {}, target: {}", player.getId(), req.getPlayerId());
		return true;
	}
	
	/**
	 * 踢出房客成员（房主操作）
	 * @param protocol
	 * @return
	 */
	@ProtocolHandler(code = HP.code2.ANN7_PARTY_ROOM_KICKOUT_C_VALUE)
	private boolean onKickoutMemberReq(HawkProtocol protocol) {
		if (!Ann7Party384Service.getInstance().isActivityOpening(player.getId())) {
			sendError(protocol.getType(), Status.Error.ACTIVITY_NOT_OPEN_VALUE);
			return false;
		}
		Party384Room room = Ann7Party384Service.getInstance().getPlayerRoom(player.getId());
		//不是房主不能踢
		if (!Ann7Party384Service.getInstance().isRoomMaster(player.getId())) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_NEED_ROOM_MASTER);
			HawkLog.errPrintln("anny party kickout member error: {}, playerId: {}", room == null ? "room null" : "no master", player.getId());
			return false;
		}
		//聚会抽奖已经开始了，不能踢
		if (room.getRoundBeginTime() > 0) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_ROOM_STARTED);
			HawkLog.errPrintln("anny party kickout member error: {}, playerId: {}", "party started", player.getId());
			return false;
		}
		
		//设置的是自动开启，满员了不能踢，否则可能引发数据一致性问题
		if (room.isAutoStart() && room.isFullMember()) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_KICKOUT_FULL_ERROR);
			HawkLog.errPrintln("anny party kickout member error: {}, playerId: {}", "full memebr", player.getId());
			return false;
		}
		
		KickoutMemberReq req = protocol.parseProtocol(KickoutMemberReq.getDefaultInstance());
		String tarPlayerId = req.getPlayerId();
		if (HawkOSOperator.isEmptyString(tarPlayerId)) {
			sendError(protocol.getType(), Status.SysError.PARAMS_INVALID_VALUE);
			return false;
		}
		//现有房客成员中不包含这个要踢的人
		if (!room.isRoomMember(tarPlayerId)) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_KICKOUT_NO_MEMBER);
			HawkLog.errPrintln("anny party kickout member error: {}, playerId: {}", tarPlayerId + " not member", player.getId());
			return false;
		}
		
		//给房客补发奖券道具
		Ann7Party384Service.getInstance().sendJoinRoomConsumeBackMail(tarPlayerId, JoinRoomComsumeBackType.BE_KICKTOU_VALUE);
		
		Ann7Party384Service.getInstance().kickoutMember(room, req, true, 1);
		//Ann7Party384Service.getInstance().syncActivityInfo(player);
		player.responseSuccess(protocol.getType());
		HawkLog.logPrintln("anny party kickout member succ, playerId: {}, target: {}", player.getId(), req.getPlayerId());
		return true;
	}
	
	/**
	 * 处理申请加入房间的请求（同意或拒绝，房主操作）
	 * @param protocol
	 * @return
	 */
	@ProtocolHandler(code = HP.code2.ANN7_PARTY_DEAL_APPLY_C_VALUE)
	private boolean onDealApplyReq(HawkProtocol protocol) {
		if (!Ann7Party384Service.getInstance().isActivityOpening(player.getId())) {
			sendError(protocol.getType(), Status.Error.ACTIVITY_NOT_OPEN_VALUE);
			return false;
		}
		Party384Room room = Ann7Party384Service.getInstance().getPlayerRoom(player.getId());
		if (!Ann7Party384Service.getInstance().isRoomMaster(player.getId())) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_NEED_ROOM_MASTER);
			HawkLog.errPrintln("anny party deal apply error: {}, playerId: {}", room == null ? "room null" : "no master", player.getId());
			return false;
		}
		
		DealJoinApplyReq req = protocol.parseProtocol(DealJoinApplyReq.getDefaultInstance());
		String tarPlayerId = req.getPlayerId();
		if (HawkOSOperator.isEmptyString(tarPlayerId)) {
			sendError(protocol.getType(), Status.SysError.PARAMS_INVALID_VALUE);
			return false;
		}
		
		Party384Room tarPlayerRoom = Ann7Party384Service.getInstance().getPlayerRoom(tarPlayerId);
		if (tarPlayerRoom != null && req.getAgree() > 0) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_TARGET_IN_ROOM);
			room.removeJoinApply(tarPlayerId);
			Ann7Party384Service.getInstance().syncRoomJoinApplyPlayer(room, 0, ""); //从申请列表中剔除
			HawkLog.errPrintln("anny party deal apply error: {}, playerId: {}", tarPlayerId + " in room", player.getId());
			return false;
		}
		
		if (!room.isJoinApplyPlayer(req.getPlayerId())) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_NO_JOIN_APPLY);
			Ann7Party384Service.getInstance().syncRoomJoinApplyPlayer(room, 0, "");
			HawkLog.errPrintln("anny party deal apply error: {}, playerId: {}", tarPlayerId + " not join apply", player.getId());
			return false;
		}
		
		//拒绝时要给人家补发奖券道具
		if (req.getAgree() <= 0) {
			Ann7Party384Service.getInstance().sendJoinRoomConsumeBackMail(tarPlayerId, JoinRoomComsumeBackType.NOT_APPROVED_VALUE);
		}
		Ann7Party384Service.getInstance().dealJoinApply(room, req, true);
		player.responseSuccess(protocol.getType());
		HawkLog.logPrintln("anny party deal apply succ, playerId: {}, target: {}, agree: {}", player.getId(), req.getPlayerId(), req.getAgree());
		return true;
	}
	
	/**
	 * 搜索玩家（准备发邀请）
	 * @param protocol
	 * @return
	 */
	@ProtocolHandler(code = HP.code2.ANN7_PARTY_INVITE_SEARCH_C_VALUE)
	private boolean onInviteSearchReq(HawkProtocol protocol) {
		if (!Ann7Party384Service.getInstance().isActivityOpening(player.getId())) {
			sendError(protocol.getType(), Status.Error.ACTIVITY_NOT_OPEN_VALUE);
			return false;
		}
		Party384Room room = Ann7Party384Service.getInstance().getPlayerRoom(player.getId());
		if (!Ann7Party384Service.getInstance().isRoomMaster(player.getId())) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_NEED_ROOM_MASTER);
			HawkLog.errPrintln("anny party search player error: {}, playerId: {}", room == null ? "room null" : "no master", player.getId());
			return false;
		}
		
		InviteSearchReq req = protocol.parseProtocol(InviteSearchReq.getDefaultInstance());
		if (HawkOSOperator.isEmptyString(req.getPlayerName())) {
			sendError(protocol.getType(), Status.SysError.PARAMS_INVALID_VALUE);
			return false;
		}
		
		String playerId = GlobalData.getInstance().getPlayerIdByName(req.getPlayerName());
		if (HawkOSOperator.isEmptyString(playerId)) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_SEACH_NOT_EXIST);
			HawkLog.errPrintln("anny party search player error: {}, playerId: {}", req.getPlayerName() + " not exist", player.getId());
			return false;
		}
		if (playerId.equals(player.getId())) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_SEACH_SELF);
			HawkLog.errPrintln("anny party search player error: {}, playerId: {}", "search self", player.getId());
			return false;
		}

		if (room.isRoomMember(playerId)) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_SEACH_MEMBER);
			HawkLog.errPrintln("anny party search player error: {}, playerId: {}", req.getPlayerName()+" is member", player.getId());
			return false;
		}
		
		InviteSearchResp.Builder resp = InviteSearchResp.newBuilder();
		AccountInfo accountInfo = GlobalData.getInstance().getAccountInfoByPlayerId(playerId);
		if (accountInfo != null) {
			Player tarPlayer = GlobalData.getInstance().makesurePlayer(playerId);
			PartyRoomMemberInfo.Builder builder = PartyRoomMemberInfo.newBuilder();
			builder.setPlayerId(playerId);
			builder.setPlayerName(tarPlayer.getName());
			builder.setVipLevel(tarPlayer.getVipLevel());
			builder.setIconId(tarPlayer.getIcon());
			builder.setPfIcon(tarPlayer.getPfIcon());
			builder.setServerId(tarPlayer.getMainServerId());
			builder.setPower(tarPlayer.getPower());
			resp.setPlayerInfo(builder);
			sendProtocol(HawkProtocol.valueOf(HP.code2.ANN7_PARTY_INVITE_SEARCH_S_VALUE, resp));
			return true;
		}

		String mainServerId = Ann7Party384Service.getInstance().getImmPlayerServer(playerId);
		if (HawkOSOperator.isEmptyString(mainServerId)) {
			int serverId = HawkNumberConvert.convertInt(playerId.split("-")[0]);
			mainServerId = GlobalData.getInstance().getMainServerId(String.valueOf(serverId));
			if (!Ann7Party384Service.getInstance().getGroupServer().contains(mainServerId)) {
				sendError(protocol.getType(), Status.Error.ANN7_PARTY_NOT_GROUP_PLAYER);
				HawkLog.errPrintln("anny party search player error: {}, playerId: {}", req.getPlayerName() + " not group player", player.getId());
				return false;
			}
		}
		
		Ann7Party384Service.getInstance().searchPlayerCrossServer(mainServerId, player.getId(), playerId);
		return true;
	}
	
	
	/**
	 * 搜索房间
	 * @param protocol
	 * @return
	 */
	@ProtocolHandler(code = HP.code2.ANN7_PARTY_SEARCH_ROOM_C_VALUE)
	private boolean onRoomSearchReq(HawkProtocol protocol) {
		if (!Ann7Party384Service.getInstance().isActivityOpening(player.getId())) {
			sendError(protocol.getType(), Status.Error.ACTIVITY_NOT_OPEN_VALUE);
			return false;
		}
		Party384Room room = Ann7Party384Service.getInstance().getPlayerRoom(player.getId());
		if (room != null) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_IN_ROOM);
			HawkLog.errPrintln("anny party search room error: {}, playerId: {}", "in room", player.getId());
			return false;
		}
		
		SearchRoomReq req = protocol.parseProtocol(SearchRoomReq.getDefaultInstance());
		Party384Room searchRoom = Ann7Party384Service.getInstance().getRoomById(req.getRoomId());
		if (searchRoom == null) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_ROOM_NOT_EXIST);
			HawkLog.errPrintln("anny party search room error: {}, playerId: {}", req.getRoomId() +" not exist", player.getId());
			return false;
		}
		
		PartyRoomBriefInfo.Builder roomBuilder = searchRoom.toBriefInfoBuilder();
		SearchRoomResp.Builder resp = SearchRoomResp.newBuilder();
		resp.setRoomInfo(roomBuilder);
		sendProtocol(HawkProtocol.valueOf(HP.code2.ANN7_PARTY_SEARCH_ROOM_S_VALUE, resp));
		return true;
	}
	
	/**
	 * 申请加入房间 
	 * @param protocol
	 * @return
	 */
	@ProtocolHandler(code = HP.code2.ANN7_PARTY_APPLY_JOIN_C_VALUE)
	private boolean onApplyJoinRoomReq(HawkProtocol protocol) {
		if (!Ann7Party384Service.getInstance().isActivityOpening(player.getId())) {
			sendError(protocol.getType(), Status.Error.ACTIVITY_NOT_OPEN_VALUE);
			return false;
		}
		if (Ann7Party384Service.getInstance().playerInRoom(player.getId())) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_IN_ROOM);
			HawkLog.errPrintln("anny party apply join room error: {}, playerId: {}", "in room", player.getId());
			return false;
		}
		
		ApplyJoinRoomReq req = protocol.parseProtocol(ApplyJoinRoomReq.getDefaultInstance());
		Ann7Party384Entity entity = Ann7Party384Service.getInstance().getDBEntity(player.getId());
		//还有未处理的奖励
//		if (!entity.getPartyRewardMap().isEmpty()) {
//			sendError(protocol.getType(), Status.Error.ANN7_PARTY_REWARD_REMAIN);
//			HawkLog.errPrintln("anny party apply join room error: {}, playerId: {}", "reward not empty", player.getId());
//			return false;
//		}
		
		if (entity.getApplyJoinRoomSet().contains(req.getRoomId())) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_APPLY_JOIN_REPEAT);
			HawkLog.errPrintln("anny party apply join room error: {}, playerId: {}", "apply repeated entity", player.getId());
			return false;
		}
		if (entity.getApplyJoinRoomSet().size() >= Ann7Party384KVCfg.getInstance().getJoinApplyLimit()) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_APPLY_COUNT_LIMIT);
			HawkLog.errPrintln("anny party apply join room error: {}, playerId: {}", "apply count limit", player.getId());
			return false;
		}
		
		if (entity.getJoinRoomCount() >= Ann7Party384KVCfg.getInstance().getInPartyRoomValueLimit()) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_JOIN_ROOM_LIMIT);
			HawkLog.errPrintln("anny party apply join room error: {}, playerId: {}", "daily join limit", player.getId());
			return false;
		}
		
		if (entity.getRecAwardTimes() >= Ann7Party384KVCfg.getInstance().getPartyRoomGetLodgerAwardLimit()) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_REC_AWARD_LIMIT);
			HawkLog.errPrintln("anny party apply join room error: {}, playerId: {}", "rec award limit", player.getId());
			return false;
		}
		
		Party384Room room = Ann7Party384Service.getInstance().getRoomById(req.getRoomId());
		if (room == null) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_ROOM_NOT_EXIST);
			HawkLog.errPrintln("anny party apply join room error: {}, playerId: {}", req.getRoomId() +" not exist", player.getId());
			return false;
		}
		
		if (room.isFullMember()) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_ROOM_MEM_FULL);
			Ann7Party384Service.getInstance().syncActivityInfo(player);
			HawkLog.errPrintln("anny party apply join room error: {}, playerId: {}", room.getRoomId() +" full member", player.getId());
			return false;
		}
		
		if (room.getRoundBeginTime() > 0) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_ROOM_STARTED);
			Ann7Party384Service.getInstance().syncActivityInfo(player);
			HawkLog.errPrintln("anny party apply join room error: {}, playerId: {}", room.getRoomId() +" party started", player.getId());
			return false;
		}
		
		if (room.isJoinApplyPlayer(player.getId())) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_APPLY_JOIN_REPEAT);
			HawkLog.errPrintln("anny party apply join room error: {}, playerId: {}", req.getRoomId() + " apply repeated", player.getId());
			return false;
		}
		
		if (room.joinNeedApply() && room.getJoinApplyPlayerCount() >= Ann7Party384KVCfg.getInstance().getRoomJoinApplyLimitCount()) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_JOIN_APPLY_LIMIT);
			Ann7Party384Service.getInstance().syncActivityInfo(player); // 从可申请加入的列表中剔除
			HawkLog.errPrintln("anny party apply join room error: {}, playerId: {}", req.getRoomId() + " roomJoinApplyLimit", player.getId());
			return false;
		}
		
		ConsumeItems consume = ConsumeItems.valueOf();
		consume.addConsumeInfo(ItemInfo.valueListOf(Ann7Party384KVCfg.getInstance().getInPartyRoomItem()));
		if (!consume.checkConsume(player, protocol.getType())) {
			HawkLog.errPrintln("anny party apply join room error: {}, playerId: {}", "consume", player.getId());
			return false;
		}
		
		//申请加入房间
		int result = Ann7Party384Service.getInstance().applyJoinRoom(player.getId(), room, true);
		if (result < 0) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_ROOM_MEM_FULL);
			Ann7Party384Service.getInstance().syncActivityInfo(player);
			HawkLog.errPrintln("anny party apply join room error: {}, playerId: {}", room.getRoomId() + " full member-1", player.getId());
			return false;
		}
		
		consume.consumeAndPush(player, Action.ANNY_PARTY_APPLY_JOIN);
		ApplyJoinRoomResp.Builder resp = ApplyJoinRoomResp.newBuilder();
		resp.setResult(result);
		player.sendProtocol(HawkProtocol.valueOf(HP.code2.ANN7_PARTY_APPLY_JOIN_S, resp));
		player.responseSuccess(protocol.getType());
		HawkLog.logPrintln("anny party apply join room succ, playerId: {}, roomId: {}, needApply: {}", player.getId(), req.getRoomId(), room.joinNeedApply());
		return true;
	}
	
	/**
	 * 处理邀请（同意/拒绝邀请） 
	 * @param protocol
	 * @return
	 */
	@ProtocolHandler(code = HP.code2.ANN7_PARTY_DEAL_INVITE_C_VALUE)
	private boolean onDealInviteReq(HawkProtocol protocol) {
		if (!Ann7Party384Service.getInstance().isActivityOpening(player.getId())) {
			sendError(protocol.getType(), Status.Error.ACTIVITY_NOT_OPEN_VALUE);
			return false;
		}
		Party384Room selfRoom = Ann7Party384Service.getInstance().getPlayerRoom(player.getId());
		if (selfRoom != null) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_IN_ROOM);
			HawkLog.errPrintln("anny party deal invite error: {}, playerId: {}", "in room", player.getId());
			return false;
		}
		
		DealInviteReq req = protocol.parseProtocol(DealInviteReq.getDefaultInstance());
		Party384Room room = Ann7Party384Service.getInstance().getRoomById(req.getRoomId());
		if (room == null) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_ROOM_NOT_EXIST);
			Ann7Party384Service.getInstance().syncActivityInfo(player); // 要从邀请列表中剔除
			HawkLog.errPrintln("anny party deal invite error: {}, playerId: {}", req.getRoomId()+" room not exist", player.getId());
			return false;
		}
		
		if ((room.isFullMember() || room.getRoundBeginTime() > 0) && req.getAgree() > 0) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_ROOM_MEM_FULL);
			Ann7Party384Entity entity = Ann7Party384Service.getInstance().getDBEntity(player.getId());
			entity.removeInviteMeRoom(req.getRoomId());
			Ann7Party384Service.getInstance().syncActivityInfo(player);
			HawkLog.errPrintln("anny party deal invite error: {}, playerId: {}", req.getRoomId() + (room.isFullMember() ? " full member":" party started"), player.getId());
			return false;
		}
		
		ConsumeItems consume = ConsumeItems.valueOf();
		Ann7Party384Entity entity = Ann7Party384Service.getInstance().getDBEntity(player.getId());
		if (req.getAgree() > 0) {
			//还有未处理的奖励
//			if (!entity.getPartyRewardMap().isEmpty()) {
//				sendError(protocol.getType(), Status.Error.ANN7_PARTY_REWARD_REMAIN);
//				HawkLog.errPrintln("anny party deal invite error: {}, playerId: {}", "reward not empty", player.getId());
//				return false;
//			}
			if (entity.getJoinRoomCount() >= Ann7Party384KVCfg.getInstance().getInPartyRoomValueLimit()) {
				sendError(protocol.getType(), Status.Error.ANN7_PARTY_JOIN_ROOM_LIMIT);
				HawkLog.errPrintln("anny party deal invite error: {}, playerId: {}", "daily join limit", player.getId());
				return false;
			}
			if (entity.getRecAwardTimes() >= Ann7Party384KVCfg.getInstance().getPartyRoomGetLodgerAwardLimit()) {
				sendError(protocol.getType(), Status.Error.ANN7_PARTY_REC_AWARD_LIMIT);
				HawkLog.errPrintln("anny party deal invite error: {}, playerId: {}", "rec award limit", player.getId());
				return false;
			}
			
			//判断消耗
			consume.addConsumeInfo(ItemInfo.valueListOf(Ann7Party384KVCfg.getInstance().getInPartyRoomItem()));
			if (!consume.checkConsume(player, protocol.getType())) {
				HawkLog.errPrintln("anny party deal invite error: {}, playerId: {}", "consume", player.getId());
				return false;
			}
			Ann7Party384Service.getInstance().leaveQueue(player.getId());
		}
		
		int result = Ann7Party384Service.getInstance().dealInvite(player.getId(), room, req, true);
		if (result < 0) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_ROOM_MEM_FULL);
			Ann7Party384Service.getInstance().syncActivityInfo(player);
			HawkLog.errPrintln("anny party deal invite error: {}, playerId: {}", req.getRoomId() + " full member-1", player.getId()); 
			return false;
		}
		
		//直接消耗
		if (req.getAgree() > 0) {
			consume.consumeAndPush(player, Action.ANNY_PARTY_AGREE_INVITE);
		}
		
		player.responseSuccess(protocol.getType());
		HawkLog.logPrintln("anny party deal invite succ, playerId: {}, roomId: {}, agree: {}", player.getId(), req.getRoomId(), req.getAgree());
		return true;
	}
	
	/**
	 * 加入排队
	 * @param protocol
	 * @return
	 */
	@ProtocolHandler(code = HP.code2.ANN7_PARTY_QUEUE_UP_C_VALUE)
	private boolean onPlayerJoinQueueReq(HawkProtocol protocol) {
		if (!Ann7Party384Service.getInstance().isActivityOpening(player.getId())) {
			sendError(protocol.getType(), Status.Error.ACTIVITY_NOT_OPEN_VALUE);
			return false;
		}
		//刚退出排队队列，30s内不能再重新加入队列
		if (HawkTime.getMillisecond() - player.ann7PartyExitQueueTime < Ann7Party384KVCfg.getInstance().getQueueCd()) {
			syncPlayerExitQueueInfo();
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_LEAVE_QUEUE_NEAR_VALUE);
			return false;
		}
		
		String playerId = player.getId();
		Party384Room room = Ann7Party384Service.getInstance().getPlayerRoom(playerId);
		if (room != null) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_IN_ROOM);
			HawkLog.errPrintln("anny party into queue error: {}, playerId: {}", "in room", player.getId());
			return false;
		}
	
		Ann7Party384Entity entity = Ann7Party384Service.getInstance().getDBEntity(player.getId());
		if (entity.getJoinRoomCount() >= Ann7Party384KVCfg.getInstance().getInPartyRoomValueLimit()) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_JOIN_ROOM_LIMIT);
			HawkLog.errPrintln("anny party into queue error: {}, playerId: {}", "daily join limit", player.getId());
			return false;
		}
		
		if (entity.getRecAwardTimes() >= Ann7Party384KVCfg.getInstance().getPartyRoomGetLodgerAwardLimit()) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_REC_AWARD_LIMIT);
			HawkLog.errPrintln("anny party into queue error: {}, playerId: {}", "rec award limit", player.getId());
			return false;
		}
		
		int count = Ann7Party384Service.getInstance().getQueueMemberTotal();
		if (count > Ann7Party384KVCfg.getInstance().getQueuePlayerLimit()) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_QUEUE_PLAYER_LIMIT);
			HawkLog.errPrintln("anny party into queue error: {}, playerId: {}", "queue member total limit", player.getId());
			return false;
		}
		
		long rank = Ann7Party384Service.getInstance().getQueueRank(playerId);
		if (rank >= 0) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_IN_QUEUE);
			HawkLog.errPrintln("anny party into queue error: {}, playerId: {}", "in queue", player.getId());
			return false;
		}
		
		//判断消耗
		ConsumeItems consume = ConsumeItems.valueOf(); 
		consume.addConsumeInfo(ItemInfo.valueListOf(Ann7Party384KVCfg.getInstance().getInPartyRoomItem()));
		if (!consume.checkConsume(player, protocol.getType())) {
			HawkLog.errPrintln("anny party into queue error: {}, playerId: {}", "consume", player.getId());
			return false;
		}
		consume.consumeAndPush(player, Action.ANNY_PARTY_JOIN_QUEUE);
		
		Ann7Party384Service.getInstance().addToQueue(playerId);
		Ann7Party384Service.getInstance().addOrUpdatePlayerInfo(player);
		
		int total = Ann7Party384Service.getInstance().getQueueMemberTotal();
		int queueIndex = Ann7Party384Service.getInstance().getQueueRank(playerId);
		PlayerQueueUpResp.Builder resp = PlayerQueueUpResp.newBuilder();
		resp.setTotal(total);
		resp.setSelf(queueIndex);
		sendProtocol(HawkProtocol.valueOf(HP.code2.ANN7_PARTY_QUEUE_UP_S_VALUE, resp));
		player.responseSuccess(protocol.getType());
		HawkLog.logPrintln("anny party into queue succ, playerId: {}, total: {}, queueIndex: {}, groupId: {}", player.getId(), total, queueIndex, Ann7Party384Service.getInstance().getGroupId());
		return true;
	}
	
	/**
	 * 退出排队队列
	 * @param protocol
	 * @return
	 */
	@ProtocolHandler(code = HP.code2.ANN7_PARTY_EXIT_QUEUE_VALUE)
	private boolean onPlayerExitQueue(HawkProtocol protocol) {
		if (!Ann7Party384Service.getInstance().isActivityOpening(player.getId())) {
			sendError(protocol.getType(), Status.Error.ACTIVITY_NOT_OPEN_VALUE);
			return false;
		}
		long rank = Ann7Party384Service.getInstance().getQueueRank(player.getId());
		if (rank < 0) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_NOT_IN_QUEUE_VALUE);
			return false;
		}
		player.ann7PartyExitQueueTime = HawkTime.getMillisecond();
		Ann7Party384Service.getInstance().leaveQueue(player.getId());
		syncPlayerExitQueueInfo();
		Ann7Party384Service.getInstance().syncActivityInfo(player);
		HawkLog.logPrintln("anny party leave queue, playerId: {}", player.getId());
		return true;
	}
	
	/**
	 * 同步退出排队的时间
	 */
	private void syncPlayerExitQueueInfo() {
		PlayerExitQueueSync.Builder builder = PlayerExitQueueSync.newBuilder();
		builder.setExitTime(player.ann7PartyExitQueueTime);
		player.sendProtocol(HawkProtocol.valueOf(HP.code2.ANN7_PARTY_EXIT_SYNC_VALUE, builder));
	}
	
	/**
	 * 开启聚会（房主操作）
	 * @param protocol
	 * @return
	 */
	@ProtocolHandler(code = HP.code2.ANN7_PARTY_START_PARTY_C_VALUE)
	private boolean onStartPartyReq(HawkProtocol protocol) {
		if (!Ann7Party384Service.getInstance().isActivityOpening(player.getId())) {
			sendError(protocol.getType(), Status.Error.ACTIVITY_NOT_OPEN_VALUE);
			return false;
		}
		Party384Room room = Ann7Party384Service.getInstance().getPlayerRoom(player.getId());
		if (room == null) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_NOT_IN_ROOM);
			HawkLog.errPrintln("anny party start party error: {}, playerId: {}", "not in room", player.getId());
			return false;
		}
		
		if (!room.getMasterPlayerId().equals(player.getId())) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_NEED_ROOM_MASTER);
			HawkLog.errPrintln("anny party start party error: {}, playerId: {}", "not master", player.getId());
			return false;
		}
		
		if (room.getRoundBeginTime() > 0) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_ROOM_STARTED);
			HawkLog.errPrintln("anny party start party error: {}, playerId: {}", "started already", player.getId());
			return false;
		}
		
		if (room.memberCount() < Ann7Party384KVCfg.getInstance().getNeedLodgerValueLimit()) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_ROOM_MEMBER_ERR);
			HawkLog.errPrintln("anny party start party error: {}, playerId: {}", "member count low - " + room.memberCount(), player.getId());
			return false;
		}
		
		Ann7Party384Service.getInstance().startParty(room, null, true);
		//Ann7Party384Service.getInstance().syncActivityInfo(player);
		player.responseSuccess(protocol.getType());
		HawkLog.logPrintln("anny party start party succ, playerId: {}", player.getId());
		return true;
	}
	
	/**
	 * 抽奖
	 * @param protocol
	 * @return
	 */
	@ProtocolHandler(code = HP.code2.ANN7_PARTY_SELECT_AWARD_C_VALUE)
	private boolean onSelectAwardReq(HawkProtocol protocol) {
		if (!Ann7Party384Service.getInstance().isActivityOpening(player.getId())) {
			sendError(protocol.getType(), Status.Error.ACTIVITY_NOT_OPEN_VALUE);
			return false;
		}
		Party384Room room = Ann7Party384Service.getInstance().getPlayerRoom(player.getId());
		if (room == null || !room.isRewardMember(player.getId())) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_NOT_IN_ROOM);
			HawkLog.errPrintln("anny party select award error: {}, playerId: {}, room null: {}", "not in room", player.getId(), room == null);
			return false;
		}

		if (room.getRoundBeginTime() <= 0) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_ROOM_NOT_STARTED);
			HawkLog.errPrintln("anny party select award error: {}, playerId: {}", "not started", player.getId());
			return false;
		}
		if (room.isSelected(player.getId())) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_AWARD_REPEAT);
			HawkLog.errPrintln("anny party select award error: {}, playerId: {}", "repeated", player.getId());
			return false;
		}
		
		if (HawkTime.getMillisecond() - room.getRoundBeginTime() >= Ann7Party384KVCfg.getInstance().getOpenRewardTime()) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_ROUND_SHOW); // 已进入展示期
			HawkLog.errPrintln("anny party select award error: {}, playerId: {}", "into show", player.getId());
			return false;
		}
		
		SelectRewardReq req = protocol.parseProtocol(SelectRewardReq.getDefaultInstance());
		SelectRewardResp.Builder resp = Ann7Party384Service.getInstance().selectAward(player.getId(), room, req, true);
		if (resp != null) {
			Ann7Party384Service.getInstance().syncActivityInfo(player);
			player.sendProtocol(HawkProtocol.valueOf(HP.code2.ANN7_PARTY_SELECT_AWARD_S_VALUE, resp));
		}
		player.responseSuccess(protocol.getType());
		return true;
	}
	
	/**
	 * 处理聚会奖励 (此协议只清除奖励内容，不再有领取奖励或放弃奖励的选项，直接通过邮件发放奖励）
	 * @param protocol
	 * @return
	 */
	@ProtocolHandler(code = HP.code2.ANN7_PARTY_DEAL_REWARD_C_VALUE)
	protected boolean onDealPartyRewardReq(HawkProtocol protocol) {
		if (!Ann7Party384Service.getInstance().isActivityOpening(player.getId())) {
			//sendError(protocol.getType(), Status.Error.ACTIVITY_NOT_OPEN_VALUE);
			return false;
		}
		Ann7Party384Entity entity = Ann7Party384Service.getInstance().getDBEntity(player.getId());
		if (entity.getPartyRewardMap().isEmpty()) {
			//sendError(protocol.getType(), Status.Error.ANN7_PARTY_REWARD_EMPTY);
			HawkLog.errPrintln("anny party deal party award error: {}, playerId: {}", "award empty", player.getId());
			return false;
		}
		
//		DealPartyRewardReq req = protocol.parseProtocol(DealPartyRewardReq.getDefaultInstance());
//		if (req.getReceive() > 0 && entity.getMemberAwardCount() < Ann7Party384KVCfg.getInstance().getLodgerDaliyGetLimit()) {
//			ConsumeItems consume = ConsumeItems.valueOf();
//			consume.addConsumeInfo(ItemInfo.valueListOf(Ann7Party384KVCfg.getInstance().getInPartyRoomItem()));
//			if (!consume.checkConsume(player, protocol.getType())) {
//				HawkLog.errPrintln("anny party deal party award error: {}, playerId: {}", "consume", player.getId());
//				return false;
//			}
//			consume.consumeAndPush(player, Action.ANNY_PARTY_AWARD_REC);
//			AwardItems awardItems = AwardItems.valueOf();
//			for (RoundRewardObject obj : entity.getPartyRewardMap().values()) {
//				awardItems.addItemInfos(ItemInfo.valueListOf(obj.getReward()));
//			}
//			entity.incMemberAwardCount(entity.getPartyRewardMap().size());
//			entity.incRecAwardTimes();
//			awardItems.rewardTakeAffectAndPush(player, Action.ANNY_PARTY_AWARD_REC, true);
//		}
		
		entity.clearPartyReward();
		Ann7Party384Service.getInstance().syncActivityInfo(player);
		player.responseSuccess(protocol.getType());
		HawkLog.logPrintln("anny party deal party award succ, playerId: {}", player.getId());
		return true;
	}
	
	/**
	 * 商店购买
	 * @param protocol
	 * @return
	 */
	@ProtocolHandler(code = HP.code2.ANN7_PARTY_SHOP_BUY_C_VALUE)
	private boolean onShopBuyReq(HawkProtocol protocol) {
		if (!Ann7Party384Service.getInstance().isActivityOpening(player.getId())) {
			sendError(protocol.getType(), Status.Error.ACTIVITY_NOT_OPEN_VALUE);
			return false;
		}
		
		//非人民币购买，不需要加限制
//		if (Ann7Party384Service.getInstance().nearActivityEnd()) {
//			sendError(protocol.getType(), Status.Error.ANN7_PARTY_NEAR_ACT_END);
//			HawkLog.errPrintln("anny party shop buy error: {}, playerId: {}", "near act end", player.getId());
//			return false;
//		}
		
		ShopBuyReq req = protocol.parseProtocol(ShopBuyReq.getDefaultInstance());
		int shopId = req.getShopId();
		int buyCount = req.getBuyCount();
		Ann7Party384ShopCfg shopCfg = HawkConfigManager.getInstance().getConfigByKey(Ann7Party384ShopCfg.class, shopId);
		if (shopCfg == null) {
			sendError(protocol.getType(), Status.SysError.PARAMS_INVALID_VALUE);
			HawkLog.errPrintln("anny party shop buy error: {}, playerId: {}", "config error " + shopId, player.getId());
			return false;
		}
		if (shopCfg.getShopItemType() == Ann7PartyConst.SHOP_BUY_RMB) {
			sendError(protocol.getType(), Status.SysError.PARAMS_INVALID_VALUE);
			HawkLog.errPrintln("anny party shop buy error: {}, playerId: {}", "shopItemType error " + shopId, player.getId());
			return false;
		}
		Ann7Party384Entity entity = Ann7Party384Service.getInstance().getDBEntity(player.getId());
		if (entity.getShopBuyCount(shopId) >= shopCfg.getTimes()) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_SHOP_BUY_LIMIT);
			HawkLog.errPrintln("anny party shop buy error: {}, playerId: {}", "shopBuyCount limit " + shopId, player.getId());
			return false;
		}
		
		buyCount = Math.min(buyCount, shopCfg.getTimes() - entity.getShopBuyCount(shopId));
		boolean succ = shopBuy(entity, shopCfg, buyCount);
		if (succ) {
			player.responseSuccess(protocol.getType());
		}
		return true;
	}
	
	/**
	 * 直购消息
	 * @param msg
	 */
	@MessageHandler
	private void buyShopGift(Ann7PartyGiftBuyMsg msg) {
		Ann7Party384ShopCfg shopCfg = Ann7Party384ShopCfg.getCfgByGoodsId(msg.getPayGiftId());
		if (shopCfg == null) {
			HawkLog.errPrintln("anny party shop buy error: {}, playerId: {}", "msg config error " + msg.getPayGiftId(), player.getId());
			return;
		}
		Ann7Party384Entity entity = Ann7Party384Service.getInstance().getDBEntity(player.getId());
		shopBuy(entity, shopCfg, 1);
	}
	
	private boolean shopBuy(Ann7Party384Entity entity, Ann7Party384ShopCfg shopCfg, int buyCount) {
		if (shopCfg.getShopItemType() == Ann7PartyConst.SHOP_BUY_COIN) {
			ConsumeItems consume = ConsumeItems.valueOf();
			consume.addConsumeInfo(ItemInfo.valueListOf(shopCfg.getPayItem(), buyCount));
			if (!consume.checkConsume(player, HP.code2.ANN7_PARTY_SHOP_BUY_C_VALUE)) {
				HawkLog.errPrintln("anny party shop buy error: {}, playerId: {}", "consume", player.getId());
				return false;
			}
			consume.consumeAndPush(player, Action.ANNY_PARTY_SHOP_BUY);
		}
		
		entity.addShopBuyCount(shopCfg.getId(), buyCount);
		AwardItems awardItems = AwardItems.valueOf();
		awardItems.addItemInfos(ItemInfo.valueListOf(shopCfg.getGetItem(), buyCount));
		awardItems.rewardTakeAffectAndPush(player, Action.ANNY_PARTY_SHOP_BUY, true);
		
		Ann7Party384Service.getInstance().logShopBuy(player, shopCfg.getId(), buyCount);
		Ann7Party384Service.getInstance().syncActivityInfo(player);
		HawkLog.logPrintln("anny party shop buy succ, playerId: {}, shopId: {}, buyCount: {}", player.getId(), shopCfg.getId(), buyCount);
		return true;
	}
	
	/**
	 * 请求房主邀请的人员信息
	 * @param protocol
	 * @return
	 */
	@ProtocolHandler(code = HP.code2.ANN7_PARTY_INVITE_PLAYER_INFO_C_VALUE)
	private boolean onRoomInvitePlayerInfoReq(HawkProtocol protocol) {
		if (!Ann7Party384Service.getInstance().isActivityOpening(player.getId())) {
			sendError(protocol.getType(), Status.Error.ACTIVITY_NOT_OPEN_VALUE);
			return false;
		}
		if (!Ann7Party384Service.getInstance().isRoomMaster(player.getId())) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_NEED_ROOM_MASTER);
			return false;
		}
		
		Party384Room room = Ann7Party384Service.getInstance().getPlayerRoom(player.getId());
		Ann7Party384Service.getInstance().syncRoomInvitePlayer(room, 0, "");
		return true;
	}
	
	/**
	 * 请求申请加入房间的人员信息
	 * @param protocol
	 * @return
	 */
	@ProtocolHandler(code = HP.code2.ANN7_PARTY_APPLY_PLAYER_INFO_C_VALUE)
	private boolean onRoomJoinApplyPlayerInfoReq(HawkProtocol protocol) {
		if (!Ann7Party384Service.getInstance().isActivityOpening(player.getId())) {
			sendError(protocol.getType(), Status.Error.ACTIVITY_NOT_OPEN_VALUE);
			return false;
		}
		if (!Ann7Party384Service.getInstance().isRoomMaster(player.getId())) {
			sendError(protocol.getType(), Status.Error.ANN7_PARTY_NEED_ROOM_MASTER);
			return false;
		}
		
		Party384Room room = Ann7Party384Service.getInstance().getPlayerRoom(player.getId());
		Ann7Party384Service.getInstance().syncRoomJoinApplyPlayer(room, 0, "");
		return true;
	}
	
	/**
	 * 请求历史记录
	 * @param protocol
	 * @return
	 */
	@ProtocolHandler(code = HP.code2.ANN7_PARTY_RECORD_C_VALUE)
	private boolean onPartyRecordReq(HawkProtocol protocol) {
		if (!Ann7Party384Service.getInstance().isActivityOpening(player.getId())) {
			sendError(protocol.getType(), Status.Error.ACTIVITY_NOT_OPEN_VALUE);
			return false;
		}
		Ann7Party384Entity entity = Ann7Party384Service.getInstance().getDBEntity(player.getId());
		if (entity.getHisJoinRoomMap().isEmpty()) {
			player.sendProtocol(HawkProtocol.valueOf(HP.code2.ANN7_PARTY_RECORD_S_VALUE, PartyRoomRecordListPB.newBuilder()));
			return false;
		}
		
		long now = HawkTime.getMillisecond();
		if (now - entity.getFirstRoomEndTime() > HawkTime.DAY_MILLI_SECONDS) {
			long earlyTime = now;
			for (Entry<String, Long> entry : entity.getHisJoinRoomMap().entrySet()) {
				if (now - entry.getValue() > HawkTime.DAY_MILLI_SECONDS) {
					entity.getHisJoinRoomMap().remove(entry.getKey());
				} else if (entry.getValue() < earlyTime) {
					earlyTime = entry.getValue();
				}
			}
			if (earlyTime < now) {
				entity.setFirstRoomEndTime(earlyTime);
			}
		}
		
		PartyRoomRecordListPB.Builder builder = PartyRoomRecordListPB.newBuilder();
		for (String roomId : entity.getHisJoinRoomMap().keySet()) {
			PartyRoomRecordPB record = Ann7Party384Service.getInstance().getRecord(roomId);
			if (record != null) {
				builder.addRecord(record);
			}
		}
		
		player.sendProtocol(HawkProtocol.valueOf(HP.code2.ANN7_PARTY_RECORD_S_VALUE, builder));
		return true;
	}
	
	/**
	 * 请求房间状态数据
	 * @param protocol
	 * @return
	 */
	@ProtocolHandler(code = HP.code2.ANN7_PARTY_ROOM_STATE_C_VALUE)
	private boolean onPartyRoomStateReq(HawkProtocol protocol) {
//		if (!Ann7Party384Service.getInstance().isActivityOpening(player.getId())) {
//			sendError(protocol.getType(), Status.Error.ACTIVITY_NOT_OPEN_VALUE);
//			return false;
//		}
		
		PartyRoomStateReq req = protocol.parseProtocol(PartyRoomStateReq.getDefaultInstance());
		List<String> roomIds = req.getRoomIdList();
		if (roomIds.isEmpty()) {
			sendError(protocol.getType(), Status.SysError.PARAMS_INVALID_VALUE);
			return false;
		}
		
		Set<String> roomIdSet = new HashSet<>(roomIds);
		PartyRoomStateResp.Builder resp = PartyRoomStateResp.newBuilder();
		for (String roomId : roomIdSet) {
			Party384Room room = Ann7Party384Service.getInstance().getRoomById(roomId);
			RoomStatePB.Builder state = RoomStatePB.newBuilder();
			state.setRoomId(roomId);
			if (room == null) {
				state.setState(PartyRoomState.ROOM_EDN);
			} else {
				state.setState(room.getRoundBeginTime() > 0 ? PartyRoomState.ROOM_PARTY : PartyRoomState.ROOM_WAITING);
				state.setMemberCount(room.memberCount());
			}
			resp.addRoomState(state);
		}
		player.sendProtocol(HawkProtocol.valueOf(HP.code2.ANN7_PARTY_ROOM_STATE_S_VALUE, resp));
		return true;
	}
	
}
