package com.hawk.game.module.ann7party384.data;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.hawk.log.HawkLog;
import org.hawk.os.HawkException;
import org.hawk.os.HawkTime;
import org.hawk.uuid.HawkUUIDGenerator;

import com.hawk.activity.type.impl.ann7party384.cfg.Ann7Party384KVCfg;
import com.hawk.activity.type.impl.ann7party384.entity.Ann7Party384Entity;
import com.hawk.game.global.GlobalData;
import com.hawk.game.module.ann7party384.Ann7Party384Service;
import com.hawk.game.module.ann7party384.Ann7PartyConst;
import com.hawk.game.module.ann7party384.Ann7PartyConst.RoomJoinType;
import com.hawk.game.player.Player;
import com.hawk.game.protocol.Act384Ann7Party.JoinRoomType;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomBriefInfo;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomInfo;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomMemberInfo;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomRecordPB;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomSelectInfo;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoundRewardPB;
import com.hawk.game.protocol.Act384Ann7Party.RecordRoundRewardPB;
import com.hawk.game.protocol.Act384Ann7Party.RoomShareInfo;
import com.hawk.game.protocol.Act384Ann7Party.StartPartyCSPB;

public class Party384Room {
	/**
	 * 房间id
	 */
	private String roomId;
	/**
	 * 房间创建时间
	 */
	private long createTime;
	
	/**
	 * 是否自动开启（1是0否）
	 */
	private int autoStart;
	/**
	 * 加入房间是否需要申请（1是0否）
	 */
	private int joinApply;

	/**
	 * 抽奖轮次
	 */
	private int rewardRound;
	/**
	 * 当前轮次的开始时间
	 */
	private long roundBeginTime;
	
	private long autoRecMemberBaseTime;
	private long autoStartPartyBaseTime;
	
	/**
	 * 满员时间
	 */
	private long memberFullTime;
	
	/**
	 * 房主信息
	 */
	private Party384RoomMember roomMaster;
	
	/**
	 * 成员ID
	 */
	private Map<String, Integer> roomMemberMap = new ConcurrentHashMap<>();
	
	/**
	 * 房间分享信息
	 */
	private Map<String, Long> shareTimeMap = new ConcurrentHashMap<>();
	
	/**
	 * 聚会奖励相关: 开启聚会时随机出来的4轮12个奖励、每轮随机分配给每个玩家的奖励、玩家在每一轮选择的奖励序号、每一轮展示期展示的奖励序号
	 */
	private Map<Integer, PartyRoundRewardInfo> roundRewardInfoMap = new ConcurrentHashMap<>();
	private PartyRoundRewardInfo defaultObj = new PartyRoundRewardInfo();
	
	/**
	 * 发出邀请的人和时间
	 */
	private Map<String, Long> invitePlayerTimeMap = new ConcurrentHashMap<>();
	/**
	 * 申请加入的人
	 */
	private Deque<String> joinApplyPlayerQueue = new ConcurrentLinkedDeque<>();
	
	/**
	 * 用于同步客户端
	 */
	private PartyRoomInfo syncPbObj;
	
	public static Party384Room create(int autoStart, int joinApply) {
		Party384Room room = new Party384Room();
		room.roomId = HawkUUIDGenerator.genUUID();
		room.createTime = HawkTime.getMillisecond();
		room.autoStart = autoStart;
		room.joinApply = joinApply;
		if (joinApply == 0) {
			room.autoRecMemberBaseTime = room.createTime;
		}
		room.initRound();
		return room;
	}
	
	private void initRound() {
		Ann7Party384KVCfg cfg = Ann7Party384KVCfg.getInstance();
		for (int round = 1; round <= cfg.getRewardRoundLimit(); round++) {
			this.roundRewardInfoMap.put(round, PartyRoundRewardInfo.create(round));
		}
	}
	
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String id) {
		this.roomId = id;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	public int getAutoStart() {
		return autoStart;
	}
	
	public boolean isAutoStart() {
		return autoStart > 0;
	}

	public void setAutoStart(int autoStart) {
		this.autoStart = autoStart;
		updateSyncPbObj();
	}

	public int getJoinApply() {
		return joinApply;
	}

	public void setJoinApply(int joinApply) {
		this.joinApply = joinApply;
		updateSyncPbObj();
	}
	
	public boolean joinNeedApply() {
		return joinApply > 0;
	}
	
	public void setSelectInfo(int autoStart, int joinApply, boolean local, Set<String> addMembers) {
		if (this.joinApply > 0 && joinApply == 0) {
			joinApplyUpdate(local, addMembers);
			this.autoRecMemberBaseTime = HawkTime.getMillisecond();
		}
		if (this.autoStart <= 0 && autoStart > 0 && this.autoStartPartyBaseTime > 0) {
			this.autoStartPartyBaseTime = HawkTime.getMillisecond();
		}
		
		this.joinApply = joinApply;
		this.autoStart = autoStart;
		updateSyncPbObj();
	}
	
	private void joinApplyUpdate(boolean local, Set<String> addMembers) {
		int count = joinApplyPlayerQueue.size();
		int fullMember = Ann7Party384KVCfg.getInstance().getPartyRoomMaxValue();
		while (roomMemberMap.size() < fullMember && !joinApplyPlayerQueue.isEmpty()) {
			String playerId = joinApplyPlayerQueue.removeFirst();
			Ann7Party384Service.getInstance().removeApplyJoinRoom(playerId, roomId);
			if (local) {
				int result = this.addMember(playerId, true, local, RoomJoinType.JOIN_DIRECT);
				if(result < 0) {
					break;
				}
				addMembers.add(playerId); //房主所在服，把添加成功的成员记录下来，供其它服操作
			} else if (addMembers.contains(playerId)) {
				//不是房主所在服，需要根据房主所在服的执行结果来操作
				this.addMember(playerId, true, local, RoomJoinType.JOIN_DIRECT);
			}
		}
		if (count > 0) {
			clearJoinApplys(false);
			Ann7Party384Service.getInstance().syncRoomJoinApplyPlayer(this, 0, "");
		}
	}
	
	
	public long getAutoRecMemberBaseTime() {
		return autoRecMemberBaseTime;
	}

	public void setAutoRecMemberBaseTime(long autoRecMemberBaseTime) {
		this.autoRecMemberBaseTime = autoRecMemberBaseTime;
	}

	public long getAutoStartPartyBaseTime() {
		return autoStartPartyBaseTime;
	}

	public void setAutoStartPartyBaseTime(long autoStartPartyBaseTime) {
		this.autoStartPartyBaseTime = autoStartPartyBaseTime;
	}
	
	public int getRewardRound() {
		return rewardRound;
	}

	public void incRewardRound() {
		if (this.rewardRound < 0) {
			this.rewardRound = 0;
		}
		if (rewardRound > 0) {
			setRoundRewardShow(false);
		}
		this.rewardRound++;
		this.roundBeginTime = HawkTime.getMillisecond();
		updateSyncPbObj();
	}

	public long getRoundBeginTime() {
		return roundBeginTime;
	}
	
	public long getMemberFullTime() {
		return memberFullTime;
	}

	public void setMemberFullTime(long memberFullTime) {
		this.memberFullTime = memberFullTime;
		this.autoStartPartyBaseTime = memberFullTime;
	}
	
	public Party384RoomMember getRoomMaster() {
		return roomMaster;
	}

	public void setRoomMaster(Party384RoomMember roomMaster) {
		this.roomMaster = roomMaster;
	}
	
	public String getMasterPlayerId() {
		return roomMaster.getPlayerId();
	}
	
	public String getMasterServerId() {
		return roomMaster.getServerId();
	}
	
	public String masterInfo() {
		return roomMaster.getServerId() + "-" + roomMaster.getPlayerId();
	}
	
	public boolean isRobotRoom() {
		return roomMaster.isRobot();
	}
	
	public Set<String> getRoomMemberSet() {
		return roomMemberMap.keySet();
	}
	
	public int memberCount() {
		return roomMemberMap.size();
	}
	
	public boolean isRoomMember(String playerId) {
		return roomMemberMap.containsKey(playerId);
	}
	
	public Map<Integer, PartyRoundRewardInfo> getRoundRewardInfoMap() {
		return roundRewardInfoMap;
	}
	
	public PartyRoundRewardInfo getRoundRewardInfo(int round) {
		if (round <= 0) {
			return defaultObj;
		}
		if (roundRewardInfoMap.isEmpty()) {
			this.initRound();
		}
		return roundRewardInfoMap.get(round);
	}
	
	public void addRoundRewardInfo(int round, PartyRoundRewardInfo info) {
		roundRewardInfoMap.put(round, info);
	}
	
	public void initRoundRewards(int round, List<String> rewards) {
		PartyRoundRewardInfo info = roundRewardInfoMap.get(round);
		if (info == null) {
			info = PartyRoundRewardInfo.create(round);
			roundRewardInfoMap.put(round, info);
		}
		info.getPartyStartRewardList().addAll(rewards);
	}
	
	public void addMasterReward(int round, List<String> rewards) {
		getRoundRewardInfo(round).getMasterRewardList().addAll(rewards);
	}
	
	public void addMemberReward(int round, String playerId, String reward) {
		getRoundRewardInfo(round).getMemberRewardMap().put(playerId, reward);
	}
	
	public List<String> getMasterRewardAll() {
		List<String> rewardList = new ArrayList<>();
		for (PartyRoundRewardInfo info : roundRewardInfoMap.values()) {
			String select = info.getMemberRewardMap().get(this.getMasterPlayerId());
			rewardList.add(select);
			rewardList.addAll(info.getMasterRewardList());
		}
		return rewardList;
	}
	
	public Map<String, String> getMemberRewardMap() {
		return getMemberRewardMap(rewardRound);
	}
	
	public Map<String, String> getMemberRewardMap(int round) {
		return getRoundRewardInfo(round).getMemberRewardMap();
	}
	
	public List<String> getMasterRewardList(int round) {
		return getRoundRewardInfo(round).getMasterRewardList();
	}
	
	public boolean isRoundRewardShow() {
		return getRoundRewardInfo(rewardRound).isRoundRewardShow();
	}

	public void setRoundRewardShow(boolean roundRewardShow) {
		PartyRoundRewardInfo info = getRoundRewardInfo(rewardRound);
		info.setRoundRewardShow(roundRewardShow);
		if (roundRewardShow) {
			this.updateSyncPbObj();
		}
	}
	
	public Map<Integer, PlayerRoundReward> getRoundRewardShowMap() {
		PartyRoundRewardInfo info = getRoundRewardInfo(rewardRound);
		return info.getRoundRewardShowMap();
	}
	
	public StartPartyCSPB buildPartyReward() {
		StartPartyCSPB.Builder builder = StartPartyCSPB.newBuilder();
		roundRewardInfoMap.values().forEach(e -> builder.addRoundReward(e.toBuilder()));
		return builder.build();
	}
	
	/**
	 * 是否满员了
	 * @return
	 */
	public boolean isFullMember() {
		return roomMemberMap.size() >= Ann7Party384KVCfg.getInstance().getPartyRoomMaxValue();
	}
	
	public Map<String, Integer> getRewardIndexMap() {
		PartyRoundRewardInfo info = getRoundRewardInfo(rewardRound);
		return info.getRewardIndexMap();
	}
	
	public boolean isSelected(String playerId) {
		return getRewardIndexMap().containsKey(playerId);
	}
	
	public boolean isRewardMember(String playerId) {
		PartyRoundRewardInfo info = getRoundRewardInfo(rewardRound);
		return info.getMemberRewardMap().containsKey(playerId);
	}
	
	public void updateRewardIndex(String playerId, int index) {
		PartyRoundRewardInfo info = getRoundRewardInfo(rewardRound);
		boolean succ = info.selectRewardIndex(playerId, index);
		if (succ) {
			updateSyncPbObj();
		}
	}
	
	public void clearRoundReward() {
		getRewardIndexMap().clear();
	}
	
	public int roundRewardCount() {
		return getRewardIndexMap().size();
	}
	
	public long getShareTime(String target) {
		return shareTimeMap.getOrDefault(target, 0L);
	}

	public void setShareTime(String target, long shareTime) {
		shareTimeMap.put(target, shareTime);
		updateSyncPbObj();
	}
	
	public Map<String, Long> getInvitePlayerTimeMap() {
		return invitePlayerTimeMap;
	}
	
	public boolean isInvitedPlayer(String playerId) {
		return invitePlayerTimeMap.containsKey(playerId);
	}
	
	public int getInviteCount() {
		return invitePlayerTimeMap.size();
	}
	
	public void removeInvitePlayer(String playerId) {
		invitePlayerTimeMap.remove(playerId);
	}
	
	public void clearInvitePlayers() {
		for (String playerId : invitePlayerTimeMap.keySet()) {
			Ann7Party384Service.getInstance().removeInviteMeRoom(playerId, roomId);
		}
		invitePlayerTimeMap.clear();
	}

	public Deque<String> getJoinApplyPlayerQueue() {
		return joinApplyPlayerQueue;
	}
	
	public void addJoinApply(String playerId) {
		joinApplyPlayerQueue.addLast(playerId);
	}
	
	public void clearJoinApplys(boolean startParty) {
		for (String playerId : joinApplyPlayerQueue) {
			Ann7Party384Service.getInstance().removeApplyJoinRoom(playerId, roomId, startParty);
		}
		joinApplyPlayerQueue.clear();
	}
	
	public boolean isJoinApplyPlayer(String playerId) {
		return joinApplyPlayerQueue.contains(playerId);
	}
	
	public int getJoinApplyPlayerCount() {
		return joinApplyPlayerQueue.size();
	}
	
	public void removeJoinApply(String playerId) {
		joinApplyPlayerQueue.remove(playerId);
	}
	
	/**
	 * 添加房客成员
	 * @param playerId
	 * @param update
	 */
	public int addMember(String playerId, boolean update, boolean local, int joinType) {
		if(roomMemberMap.containsKey(playerId)) {
			return 0;
		}
		if (local && !Ann7Party384Service.getInstance().addMemberSync(this.getRoomId())) {
			HawkLog.logPrintln("ann7Party addMember failed, full member, playerId: {}, roomId: {}, joinType: {}", playerId, this.getRoomId(), joinType);
			return -1; //房间成员已满
		}
		
		Ann7Party384Service.getInstance().intoRoom(this, playerId);
		int max = Ann7Party384KVCfg.getInstance().getPartyRoomMaxValue();
		for (int i = 1; i <= max; i++) {
			if (!roomMemberMap.containsValue(i)) {
				roomMemberMap.put(playerId, i);
				break;
			}
		}
		
		HawkLog.logPrintln("ann7Party addMember succ, playerId: {}, roomId: {}, joinType: {}, index: {}", playerId, this.getRoomId(), joinType, roomMemberMap.get(playerId));
		if (this.memberCount() >= Ann7Party384KVCfg.getInstance().getPartyRoomMaxValue()) {
			this.setMemberFullTime(HawkTime.getMillisecond());
		}
		Ann7Party384Service.getInstance().removePlayerInfo(playerId);
		if (Ann7Party384Service.getInstance().isLocalPlayer(playerId)) {
			Player player = GlobalData.getInstance().makesurePlayer(playerId);
			Ann7Party384Service.getInstance().addOrUpdatePlayerInfo(player);
		}
		if (update) {
			updateSyncPbObj();
		}
		Ann7Party384Service.getInstance().logJoinRoom(this, playerId, joinType);
		return 0;
	}
	
	/**
	 * 删除房客成员
	 * @param playerId
	 * @param local
	 */
	public void removeMember(String playerId, boolean local) {
		roomMemberMap.remove(playerId);
		setMemberFullTime(0);
		Ann7Party384Service.getInstance().leaveRoom(playerId);
		if (local) {
			Ann7Party384Service.getInstance().removeMemberSync(this.getRoomId());
		}
		updateSyncPbObj();
	}
	
	/**
	 * 系统自动接受排队成员进入房间
	 * @param playerId
	 * @param local
	 */
	public boolean recievePlayer(String playerId, boolean local) {
		//从申请列表中移除
		if (joinApplyPlayerQueue.contains(playerId)) {
			Ann7Party384Service.getInstance().removeApplyJoinRoom(playerId, this.getRoomId());
			removeJoinApply(playerId);
			Ann7Party384Service.getInstance().syncRoomJoinApplyPlayer(this, 0, "");
		} 
		//从邀请列表中移除
		if (invitePlayerTimeMap.containsKey(playerId)) {
			this.removeInvitePlayer(playerId);
			Ann7Party384Service.getInstance().removeInviteMeRoom(playerId, this.getRoomId());
		}
		return this.addMember(playerId, false, local, RoomJoinType.REC_BY_ROBOT) == 0;
	}

	/**
	 * (房主)邀请成员
	 * @param playerId
	 * @param local
	 */
	public void invitePlayer(String playerId, boolean local) {
		invitePlayerTimeMap.put(playerId, HawkTime.getMillisecond());
		Ann7Party384Entity entity = Ann7Party384Service.getInstance().getDBEntity(playerId);
		if (entity != null) {
			entity.addInviteMeRoom(roomId);
			Player player = GlobalData.getInstance().makesurePlayer(playerId);
			if (player != null) {
				Ann7Party384Service.getInstance().addOrUpdatePlayerInfo(player);
			}
		}
	}
	
	/**
	 * 玩家处理房主的邀请
	 * @param playerId
	 * @param agree
	 * @param local
	 */
	public int dealInvite(String playerId, int agree, boolean local) {
		this.removeInvitePlayer(playerId);
		Ann7Party384Service.getInstance().removeInviteMeRoom(playerId, this.getRoomId());
		if (agree > 0) {
			return this.addMember(playerId, true, local, RoomJoinType.DEAL_INVITE); //同意了，加入到成员列表中
		}
		return 0;
	}
	
	/**
	 * 玩家申请进入房间
	 * @param playerId
	 * @param local
	 * @return
	 */
	public int applyJoin(String playerId, boolean local) {
		int result = 0;
		if (!this.joinNeedApply()) {
			result = this.addMember(playerId, true, local, RoomJoinType.JOIN_DIRECT);
		} else {
			this.addJoinApply(playerId);
		}
		if (result < 0) {
			return result;
		}
		
		//往活动entity中添加信息
		if (local) {
			Player player = GlobalData.getInstance().makesurePlayer(playerId);
			if (player != null) {
				Ann7Party384Service.getInstance().addOrUpdatePlayerInfo(player);
			}
			if (!this.joinNeedApply()) {
				result = Ann7PartyConst.JOIN_ROOM_SUCC;
			} else {
				Ann7Party384Entity entity = Ann7Party384Service.getInstance().getDBEntity(playerId);
				if (entity != null) {
					entity.addApplyJoinRoom(roomId);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 房主处理加入申请
	 * @param playerId
	 * @param agree
	 * @param local
	 */
	public int dealJoinApply(String playerId, int agree, boolean local) {
		Ann7Party384Service.getInstance().removeApplyJoinRoom(playerId, this.getRoomId());
		removeJoinApply(playerId);
		//加入到房间正式成员中
		if (agree > 0) {
			return this.addMember(playerId, true, local, RoomJoinType.JOIN_APPLY_AGREE);
		}
		return 0;
	}
	
	/**
	 * 聚会开启
	 * @param local
	 */
	public void startParty(boolean local) {
		this.incRewardRound();
		this.clearInvitePlayers();
		this.clearJoinApplys(true);
	}
	
	/**
	 * 检测抽奖轮次中未选取奖励的玩家
	 * @return
	 */
	public Map<String, Integer> checkAbsentPlayerIndex() {
		PartyRoundRewardInfo info = getRoundRewardInfo(rewardRound);
		return info.checkAbsentPlayerIndex(this.getMasterPlayerId());
	}
	
	
	public PartyRoomBriefInfo.Builder toBriefInfoBuilder() {
		PartyRoomBriefInfo.Builder roomBuilder = PartyRoomBriefInfo.newBuilder();
		roomBuilder.setRoomdId(this.getRoomId());
		roomBuilder.setCreateTime(this.getCreateTime());
		roomBuilder.setRoomMaster(this.getRoomMaster().toBuilder());
		roomBuilder.setMemberCount(this.memberCount());
		roomBuilder.setType(joinApply > 0 ? JoinRoomType.NEED_APPLY : JoinRoomType.FREEDOM);
		return roomBuilder;
	}
	
	public void mergeFrom(PartyRoomInfo obj) {
		this.roomId = obj.getRoomdId();
		this.createTime = obj.getCreateTime();
		this.autoStart = obj.getSelectInfo().getAutoStart();
		this.joinApply = obj.getSelectInfo().getJoinApply();
		this.rewardRound = obj.getRewardRound();
		this.roundBeginTime = obj.getRoundBeginTime();
		
		this.roomMaster = new Party384RoomMember();
		this.roomMaster.mergeFrom(obj.getRoomMaster());
		this.initRound();
	}
	
	public PartyRoomInfo toRedisPBObj() {
		syncPbObj = null;
		PartyRoomInfo.Builder builder = PartyRoomInfo.newBuilder();
		builder.setRoomdId(roomId);
		builder.setCreateTime(createTime);
		builder.setRoomMaster(roomMaster.toBuilder());
		
		PartyRoomSelectInfo.Builder selectInfo = PartyRoomSelectInfo.newBuilder();
		selectInfo.setAutoStart(autoStart);
		selectInfo.setJoinApply(joinApply);
		builder.setSelectInfo(selectInfo);
		
		builder.setRewardRound(rewardRound);
		builder.setRoundBeginTime(roundBeginTime);
		return builder.build();
	}
	
	public PartyRoomInfo getSyncPbObj() {
		if (syncPbObj == null) {
			updateSyncPbObj();
		}
		return syncPbObj;
	}
	
	public PartyRoomRecordPB toRecordObj() {
		PartyRoomRecordPB.Builder buider = PartyRoomRecordPB.newBuilder();
		buider.setRoomId(roomId);
		buider.setEndTime(HawkTime.getMillisecond());
		buider.setMasterId(getMasterPlayerId());
		buider.setServerId(getMasterServerId());
		buider.addRoomMember(roomMaster.toBuilder());
		for (String playerId : this.getRoomMemberSet()) {
			Party384RoomMember member = Ann7Party384Service.getInstance().getPlayerInfo(playerId);
			buider.addRoomMember(member.toBuilder());
		}
		Ann7Party384KVCfg cfg = Ann7Party384KVCfg.getInstance();
		for (int round = 1; round <= cfg.getRewardRoundLimit(); round++) {
			Map<Integer, PlayerRoundReward> roundRewardMap = this.getRoundRewardInfo(round).getRoundRewardShowMap();
			RecordRoundRewardPB.Builder roundRewardBuilder = RecordRoundRewardPB.newBuilder();
			roundRewardBuilder.setRound(round);
			roundRewardMap.values().forEach(e -> roundRewardBuilder.addReward(e.toBuilder()));
			buider.addRoundReward(roundRewardBuilder);
		}
		return buider.build();
	}
	
	public void updateSyncPbObj() {
		try {
			PartyRoomInfo.Builder syncBuilder = PartyRoomInfo.newBuilder();
			syncBuilder.setRoomdId(roomId);
			syncBuilder.setCreateTime(createTime);
			syncBuilder.setRoomMaster(roomMaster.toBuilder());
			
			for (Entry<String, Long> entry : shareTimeMap.entrySet()) {
				RoomShareInfo.Builder shareBuilder = RoomShareInfo.newBuilder();
				shareBuilder.setShareTime(entry.getValue());
				if (entry.getKey().equals(Ann7PartyConst.SHARE_SERVER)) {
					shareBuilder.setShareType(0);
				} else {
					shareBuilder.setShareType(1);
					shareBuilder.setGuildId(entry.getKey());
				}
				syncBuilder.addShareTime(shareBuilder);
			}
			
			for (Entry<String, Integer> entry : roomMemberMap.entrySet()) {
				Party384RoomMember member = Ann7Party384Service.getInstance().getPlayerInfo(entry.getKey());
				if (member != null) {
					PartyRoomMemberInfo.Builder memBuilder = member.toBuilder();
					memBuilder.setPosIndex(entry.getValue());
					syncBuilder.addRoomMember(memBuilder);
				} else {
					HawkLog.errPrintln("party room updateSyncPbObj getPlayerInfo failed: {}", entry.getKey());
				}
			}
			
			if (rewardRound > 0) {
				syncBuilder.setRoundShow(this.isRoundRewardShow() ? 1 : 0);
				for (Entry<Integer, PlayerRoundReward> entry : getRoundRewardShowMap().entrySet()) {
					PartyRoundRewardPB.Builder showBuilder = entry.getValue().toBuilder();
					syncBuilder.addReward(showBuilder);
				}
			}
			
			PartyRoomSelectInfo.Builder selectInfo1 = PartyRoomSelectInfo.newBuilder();
			selectInfo1.setAutoStart(autoStart);
			selectInfo1.setJoinApply(joinApply);
			syncBuilder.setSelectInfo(selectInfo1);
			syncBuilder.setRewardRound(rewardRound);
			syncBuilder.setRoundBeginTime(roundBeginTime);
			if (roundBeginTime <= 0 && autoStart > 0 && autoStartPartyBaseTime > 0) {
				syncBuilder.setAutoStartTime(autoStartPartyBaseTime + Ann7Party384KVCfg.getInstance().getAutoPartyTime()); 
			}
			if (roundBeginTime <= 0 && joinApply <= 0) {
				syncBuilder.setRecQueuePlayerTime(autoRecMemberBaseTime + Ann7Party384KVCfg.getInstance().getReceivePlayerTime());
			} 
			
			syncPbObj = syncBuilder.build();
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}

	/**
	 * 解散房间
	 * 1.邀请关系解除
	 * 2.申请关系解除 
	 */
	public void dissolve(boolean local) {
		for (String playerId : joinApplyPlayerQueue) {
			Ann7Party384Service.getInstance().removeApplyJoinRoom(playerId, roomId);
		}
		joinApplyPlayerQueue.clear();
		for (String playerId : invitePlayerTimeMap.keySet()) {
			Ann7Party384Service.getInstance().removeInviteMeRoom(playerId, roomId);
		}
		invitePlayerTimeMap.clear();
	}
	
	
	public void tick(long now) {
		Ann7Party384Service service = Ann7Party384Service.getInstance();
		Ann7Party384KVCfg cfg = Ann7Party384KVCfg.getInstance();
		int memberCountMax = cfg.getPartyRoomMaxValue();
//		String masterId = this.getMasterPlayerId();
		//房主跨服出去了、或迁服了
//		if (!this.isRobotRoom() && CrossService.getInstance().isEmigrationPlayer(masterId)) {
//			HawkLog.logPrintln("ann7 party room tick dissove1, roomId: {}, masterInfo: {}", this.getRoomId(), this.masterInfo());
//			service.roomDissove(this, false, true, 3);
//			service.dissoveRoomReissue(this);
//			return;
//		}
		
		//解散房间
		if (roundBeginTime <= 0 && now - createTime >= cfg.getRoomTips2Times()) {
			HawkLog.logPrintln("ann7 party room tick dissove2, roomId: {}, masterInfo: {}", this.getRoomId(), this.masterInfo());
			service.roomDissove(this, false, true, 2);
			service.dissoveRoomReissue(this);
			return;
		}
		
		//自动接收玩家
		if (!joinNeedApply() && roundBeginTime <= 0 && memberCount() < memberCountMax && now - autoRecMemberBaseTime >= cfg.getReceivePlayerTime()) {
			service.recievePlayerAuto(this);
			return;
		}
		
		//自动进入抽奖轮次
		if (isAutoStart() && roundBeginTime <= 0 && autoStartPartyBaseTime > 0 && now - autoStartPartyBaseTime >= cfg.getAutoPartyTime()) {
			service.startParty(this, null, true);
			return;
		}
		
		//一轮抽奖结束进入展示期
		if (roundBeginTime > 0 && !isRoundRewardShow() && now - roundBeginTime >= cfg.getOpenRewardTime()) {
			service.intoRoundRewardShow(this, null, true);
			return;
		}
		
		//轮次替换
		if (roundBeginTime > 0 && rewardRound < cfg.getRewardRoundLimit() && now - roundBeginTime >= cfg.getRoundTime()) {
			service.switchPartyRewardRound(this, true);
			return;
		}
		
		//4轮抽奖都结束了，给房主结算奖励
		if (rewardRound == cfg.getRewardRoundLimit() && now - roundBeginTime >= cfg.getRoundTime()) {
			service.partyRewardEnd(this, true);
			return;
		}
	}

}
