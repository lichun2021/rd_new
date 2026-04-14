package com.hawk.game.module.ann7party384;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hawk.annotation.ProtocolHandler;
import org.hawk.app.HawkApp;
import org.hawk.app.HawkAppObj;
import org.hawk.log.HawkLog;
import org.hawk.net.protocol.HawkProtocol;
import org.hawk.os.HawkException;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkRand;
import org.hawk.os.HawkTime;
import org.hawk.redis.HawkRedisSession;
import org.hawk.task.HawkTaskManager;
import org.hawk.thread.HawkTask;
import org.hawk.util.HawkClassScaner;
import org.hawk.xid.HawkXID;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.hawk.activity.ActivityBase;
import com.hawk.activity.ActivityManager;
import com.hawk.activity.constant.ObjType;
import com.hawk.activity.tools.ListSplitter;
import com.hawk.activity.type.impl.ann7party384.cfg.Ann7Party384KVCfg;
import com.hawk.activity.type.impl.ann7party384.cfg.Ann7Party384RewardCfg;
import com.hawk.activity.type.impl.ann7party384.cfg.Ann7Party384RobotCfg;
import com.hawk.activity.type.impl.ann7party384.entity.Ann7Party384Entity;
import com.hawk.activity.type.impl.ann7party384.entity.RoundRewardObject;
import com.hawk.common.CommonConst.ServerType;
import com.hawk.common.ServerInfo;
import com.hawk.game.GsConfig;
import com.hawk.game.config.GameConstCfg;
import com.hawk.game.crossproxy.CrossProxy;
import com.hawk.game.crossproxy.CrossService;
import com.hawk.game.crossproxy.ProxyHeader;
import com.hawk.game.global.GlobalData;
import com.hawk.game.global.RedisProxy;
import com.hawk.game.item.ItemInfo;
import com.hawk.game.module.ann7party384.Ann7PartyConst.CrossServerOper;
import com.hawk.game.module.ann7party384.data.Party384Room;
import com.hawk.game.module.ann7party384.data.Party384RoomMember;
import com.hawk.game.module.ann7party384.data.PlayerRoundReward;
import com.hawk.game.player.Player;
import com.hawk.game.protocol.Act384Ann7Party.CreateRoomCSPB;
import com.hawk.game.protocol.Act384Ann7Party.CrossCommonPB;
import com.hawk.game.protocol.Act384Ann7Party.DealInviteReq;
import com.hawk.game.protocol.Act384Ann7Party.DealJoinApplyReq;
import com.hawk.game.protocol.Act384Ann7Party.InviteJoinReq;
import com.hawk.game.protocol.Act384Ann7Party.InviteSearchResp;
import com.hawk.game.protocol.Act384Ann7Party.JoinPartyType;
import com.hawk.game.protocol.Act384Ann7Party.JoinRoomComsumeBackType;
import com.hawk.game.protocol.Act384Ann7Party.JoinRoomType;
import com.hawk.game.protocol.Act384Ann7Party.KickoutMemberReq;
import com.hawk.game.protocol.Act384Ann7Party.LeaveRoomNotifyPB;
import com.hawk.game.protocol.Act384Ann7Party.LeaveRoomType;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomBriefInfo;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomInviteInfo;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomMemberInfo;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomRecordPB;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomSelectInfo;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomShareReq;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoundCSPB;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoundRewardPB;
import com.hawk.game.protocol.Act384Ann7Party.PartyServiceReq;
import com.hawk.game.protocol.Act384Ann7Party.PlayerPartyInfo;
import com.hawk.game.protocol.Act384Ann7Party.RoomInvitePlayerInfo;
import com.hawk.game.protocol.Act384Ann7Party.RoomJoinApplyInfo;
import com.hawk.game.protocol.Act384Ann7Party.RoomMasterRewardSyncPB;
import com.hawk.game.protocol.Act384Ann7Party.SelectRewardReq;
import com.hawk.game.protocol.Act384Ann7Party.SelectRewardResp;
import com.hawk.game.protocol.Act384Ann7Party.ShareRoomType;
import com.hawk.game.protocol.Act384Ann7Party.StartPartyCSPB;
import com.hawk.game.protocol.Const.MailRewardStatus;
import com.hawk.game.protocol.Const.NoticeCfgId;
import com.hawk.game.protocol.MailConst.MailId;
import com.hawk.game.service.chat.ChatParames;
import com.hawk.game.service.chat.ChatService;
import com.hawk.game.service.mail.MailParames;
import com.hawk.game.service.mail.SystemMailService;
import com.hawk.game.util.GameUtil;
import com.hawk.game.util.GsConst;
import com.hawk.game.util.LogUtil;
import com.hawk.log.LogConst.LogInfoType;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import com.hawk.game.protocol.Activity;
import com.hawk.game.protocol.Const;
import com.hawk.game.protocol.HP;
import com.hawk.game.protocol.Status;

/**
 * 7周年庆典--房间聚会
 */
public class Ann7Party384Service extends HawkAppObj {
	
	/**
	 * 检测机器人生成时间
	 */
	private long checkRobotTime;
	private long checkRobotGuestTime;
	/**
	 * 房间数据
	 */
	private Map<String, Party384Room> partyRoomData = new ConcurrentHashMap<>();
	private Deque<String> roomIdQueue = new ConcurrentLinkedDeque<>();
	/**
	 * 玩家跟房间的对应关系
	 */
	private Map<String, String> playerRoomMap = new ConcurrentHashMap<>();
	/**
	 * 玩家信息
	 */
	private Map<String, Party384RoomMember> playerInfoMap = new ConcurrentHashMap<>();
	/**
	 * 最新创建的x个房间
	 */
	private Deque<String> lastestRoomQueue = new ConcurrentLinkedDeque<>();
	
	/**
	 * 聚会历史记录信息
	 */
	private LoadingCache<String, PartyRoomRecordPB> partyRecordCache;
	
	/**
	 * 跨服接收处理器
	 */
	private Map<Integer, Ann7PartyCrossProcessor> crossProcessorMap = new HashMap<>();
	/**
	 * 房主奖励
	 */
	private Map<String, RoomMasterRewardSyncPB.Builder> roomMasterRewardMap = new ConcurrentHashMap<>();
	
	/**
	 * 机器人名字列表
	 */
	private List<String> robotRandomNameList = new ArrayList<>();
	
	private int initTermId;
	private int groupId = -1;
	List<String> groupServer = new ArrayList<>();
	private long lastCheckTime = 0;
	private static Ann7Party384Service instance;

	
	public Ann7Party384Service(HawkXID xid) {
		super(xid);
		instance = this;
	}

	public static Ann7Party384Service getInstance() {
		return instance;
	}

	public boolean init() {
		if (!scanCrossProcessor()) {
			return false;
		}
		
		partyRecordCache = CacheBuilder.newBuilder().recordStats().maximumSize(80000).initialCapacity(1000)
				.expireAfterWrite(GsConst.DAY_SECONDS, TimeUnit.SECONDS)
				.removalListener(new RemovalListener<String, PartyRoomRecordPB>() {
					@Override
					public void onRemoval(RemovalNotification<String, PartyRoomRecordPB> notification) {
						final PartyRoomRecordPB record = notification.getValue();
						if (record.getServerId().equals(thisServerId())) {
							getRedisSession().hDelBytes(roomRecordKey(), record.getRoomId().getBytes());
						}
					}
				}).build(new CacheLoader<String, PartyRoomRecordPB>() {
					@Override
					public PartyRoomRecordPB load(String roomId) {
						return null;
					}
				});
		checkActivityTerm();
		loadPartyRecord();
		return true;
	}

	private boolean scanCrossProcessor() {
		List<Class<?>> classList = HawkClassScaner.scanClassesFilter(Ann7PartyConst.CROSS_PROC_PACKAGE, Ann7PartyCrossProcessor.Declare.class);
		for (Class<?> classObj : classList) {
			Ann7PartyCrossProcessor.Declare declare = classObj.getAnnotation(Ann7PartyCrossProcessor.Declare.class);
			if (declare.proto() <= 0) {
				continue;
			}
			
			@SuppressWarnings("unchecked")
			Class<? extends Ann7PartyCrossProcessor> clazz = (Class<? extends Ann7PartyCrossProcessor>) classObj;
			try {
				Ann7PartyCrossProcessor processor = clazz.getConstructor().newInstance();
				if (processor != null) {
					if (crossProcessorMap.containsKey(declare.proto())) {
						throw new RuntimeException("ann7party declare processor duplicated: " + declare.proto());
					}
					crossProcessorMap.put(declare.proto(), processor);
				}
			} catch (Exception e) {
				HawkException.catchException(e);
				return false;
			}
		}
		
		return true;
	}
	
	public String getImmPlayerServer(String playerId) {
		String serverId = GlobalData.getInstance().getPlayerServerId(playerId);
		if (HawkOSOperator.isEmptyString(serverId)) {
			return null;
		}
		String mainServer = GlobalData.getInstance().getMainServerId(serverId);
		if (!groupServer.contains(mainServer)) {
			return null;
		}
		
		return mainServer;
	}
	
	private void initRobotRandomNames() {
		robotRandomNameList.clear();
		List<String> textList = new ArrayList<String>();
		try {
			HawkOSOperator.readTextFileLines("cfg/anny384/vrolename.txt", textList);
			for (String line : textList) {
				String name = GameUtil.getStrDecoded(line);
				if (name != null) {
					robotRandomNameList.add(name);
				}
			}
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	public String randRobotName() {
		return HawkRand.randomObject(robotRandomNameList);
	}
	
	/**
	 * 加载历史记录
	 */
	private void loadPartyRecord() {
		String key = roomRecordKey();
		List<byte[]> byteDataList = new ArrayList<>();
		ScanParams scanParams = new ScanParams().match("*").count(500);
	    String cursor = "0";
	    try (Jedis jedis = getRedisSession().getJedis()) {
	        do {
	        	ScanResult<Map.Entry<byte[], byte[]>> result = jedis.hscan(key.getBytes(), cursor.getBytes(), scanParams);
	            cursor = result.getStringCursor();
	            for(Entry<byte[], byte[]> entry : result.getResult()) {
	            	byteDataList.add(entry.getValue());
	            }
	        } while (!cursor.equals("0"));
	    } catch (Exception e) {
	        HawkException.catchException(e);
	    }
		
		long now = HawkTime.getMillisecond();
		for (byte[] recordByte : byteDataList) {
			PartyRoomRecordPB.Builder record = PartyRoomRecordPB.newBuilder();
			try {
				record.mergeFrom(recordByte);
				if (now - record.getEndTime() > HawkTime.DAY_MILLI_SECONDS && record.getServerId().equals(thisServerId())) {
					getRedisSession().hDelBytes(roomRecordKey(), record.getRoomId().getBytes());
					continue;
				}
				addRecord(record.build());
			} catch (InvalidProtocolBufferException e) {
				HawkException.catchException(e);
			}
		}
	}
	
	public int getInitTermId() {
		return initTermId;
	}

	public void setInitTermId(int initTermId) {
		this.initTermId = initTermId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public List<String> getGroupServer() {
		return groupServer;
	}

	public void setGroupServer(List<String> groupServer) {
		this.groupServer = groupServer;
	}

	public Map<String, String> getPlayerRoomMap() {
		return playerRoomMap;
	}
	
	public Party384Room getRoomById(String roomId) {
		return partyRoomData.get(roomId);
	}
	
	public Party384Room getPlayerRoom(String playerId) {
		String roomId = playerRoomMap.get(playerId);
		if (HawkOSOperator.isEmptyString(roomId)) {
			return null;
		}
		return partyRoomData.get(roomId);
	}
	
	public int getRoomCount() {
		return partyRoomData.size();
	}

	public boolean isRoomMaster(String playerId) {
		Party384Room room = getPlayerRoom(playerId);
		return room != null && room.getRoomMaster().getPlayerId().equals(playerId);
	}

	public void removeInviteMeRoom(String playerId, String roomId) {
		try {
			Ann7Party384Entity entity = getDBEntity(playerId);
			if (entity != null) {
				entity.removeInviteMeRoom(roomId);
			}
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	public void removeApplyJoinRoom(String playerId, String roomId) {
		removeApplyJoinRoom(playerId, roomId, false);
	}
	
	public void removeApplyJoinRoom(String playerId, String roomId, boolean startParty) {
		try {
			Ann7Party384Entity entity = getDBEntity(playerId);
			if (entity == null) {
				return;
			}
			entity.removeApplyJoinRoom(roomId);
			if (startParty) {
				sendJoinRoomConsumeBackMail(playerId, JoinRoomComsumeBackType.NOT_APPROVED_VALUE);
			}
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	
	@Override
	public boolean onTick() {
		long currentTime = HawkTime.getMillisecond();
		if (currentTime - lastCheckTime < 1000L) {
			return true;
		}
		lastCheckTime = currentTime;
		checkActivityTerm();
		//checkCrossPlayer();
		roomTick();
		genRobotRoomTick();
		genRobotGuestTick();
		
		long tickEnd = HawkTime.getMillisecond();
		long tickCost = tickEnd - lastCheckTime;
		if (tickCost > 200) {
			HawkLog.logPrintln("ann7party tick costtime: {}, room count: {}, serverId: {}, groupId: {}", tickCost, partyRoomData.size(), thisServerId(), groupId);
		}

		return super.onTick();
	}
	
	/**
	 * 轮次更新、奖励结算、房间解散
	 * 
	 * 1.创建房间x分钟后还未开始聚会，解散房间
	 * 2.玩家创建的不需要申请即可加入的房间，或是机器人房间，创建完x秒后，如果还没满6人，自动接收玩家
	 * 3.玩家自动开启，或是机器人房间，房客人数满6人后10s后自动进入轮次
	 * 4.轮次更新：有个奖励抽取时间 和 展示时间
	 * 5.4轮抽奖结束之后，给房主结算奖励 
	 */
	private void roomTick() {
		long now = lastCheckTime;
		for (String roomId : roomIdQueue) {
			Party384Room room = partyRoomData.get(roomId);
			if (room == null) {
				continue;
			}
			try {
				//只处理本服创建的房间
				if (GlobalData.getInstance().isLocalServer(room.getMasterServerId())) {
					room.tick(now);
				}else if(HawkTime.getMillisecond() - room.getCreateTime() > 60*60 * 1000){
					roomDissove(room, true, false, Integer.MAX_VALUE);
					HawkLog.logPrintln("ann7 party corss room time out r:{} p:{} n:{}", room.getRoomId(),room.getRoomMaster().getPlayerId(),  room.getRoomMaster().getPlayerName());
				}
			} catch (Exception e) {
				HawkException.catchException(e);
			}
		}
	}
	
	/**
	 * 跨服玩家检测
	 */
	protected void checkCrossPlayer() {
		Map<String, String> map = CrossService.getInstance().getEmigrationPlayers();
		for (String playerId : map.keySet()) {
			crossLeaveRoom(playerId);
		}
		
		try {
			if (groupId > 0 && initTermId > 0 && isActivityOpening(null)) {
				for (Player player : GlobalData.getInstance().getOnlinePlayers()) {
					if (!HawkOSOperator.isEmptyString(player.getDungeonMap())) {
						crossLeaveRoom(player.getId());
					}
				}
			}
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	/**
	 * 因跨服或进入副本而离开房间
	 * @param playerId
	 */
	private void crossLeaveRoom(String playerId) {
		try {
			Party384Room room = getPlayerRoom(playerId);
			if (room == null) {
				return;
			}
			if (room.getMasterPlayerId().equals(playerId)) {
				HawkLog.logPrintln("ann7 party check cross dissove room, roomId: {}, masterInfo: {}", room.getRoomId(), room.masterInfo());
				roomDissove(room, false, true, 3);
				dissoveRoomReissue(room);
			} else {
				HawkLog.logPrintln("ann7 party check cross kickout member, roomId: {}, masterInfo: {}, playerId: {}", room.getRoomId(), room.masterInfo(), playerId);
				KickoutMemberReq.Builder req = KickoutMemberReq.newBuilder();
				req.setPlayerId(playerId);
				kickoutMember(room, req.build(), true, 0);
			}
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	private void checkActivityTerm() {
		int termId = getTermId();
		if (groupId > -1 && initTermId == termId) {
			return;
		}
		if (termId == 0) {
			return;
		}

		String serverId = thisServerId();
		String lockKey = serverGroupLockKey();
		String result = getRedisSession().hGet(lockKey, serverId);
		if (StringUtils.isEmpty(result)) {
			List<String> serverList = getOpenServerList();
			int amount = Ann7Party384KVCfg.getInstance().getServerGroup();
			List<List<String>> list = ListSplitter.splitList(serverList, amount);
			for (int i = 0; i < list.size(); i++) {
				List<String> groupServerList = list.get(i);
				for (String serv : groupServerList) {
					getRedisSession().hSetNx(lockKey, serv, Integer.valueOf(i + 1).toString());
				}
			}
			getRedisSession().expire(lockKey, getRedisExpire());
		}

		groupId = NumberUtils.toInt(getRedisSession().hGet(lockKey, serverId));
		Map<String, String> map = getRedisSession().hGetAll(lockKey);
		List<String> groupServerList = new ArrayList<>();
		for (Entry<String, String> ent : map.entrySet()) {
			if (ent.getValue().equals(groupId + "")) {
				groupServerList.add(ent.getKey());
			}
		}
		
		List<String> testServers = GameConstCfg.getInstance().getAnn7ServerGroupList();
		if (testServers.size() > 1 && testServers.contains(serverId)) {
			groupId = Integer.parseInt(testServers.get(0));
			groupServerList = testServers;
		}
		
		groupServer = groupServerList;
		initTermId = getTermId();
		initRobotRandomNames();
		HawkLog.logPrintln("ann7party checkActivityTerm, serverId: {}, groupId: {}, groupServer: {}, termId: {}", serverId, groupId, groupServer, initTermId);
	}
	
	/**
	 * 获取符合开启条件的区服
	 */
	private List<String> getOpenServerList() {
		String serverId = thisServerId();
		List<String> testServers = GameConstCfg.getInstance().getAnn7ServerGroupList();
		if (testServers.size() > 1 && testServers.contains(serverId)) {
			return testServers;
		}
		List<ServerInfo> serverInfoList = GlobalData.getInstance().getServerList().stream()
				.filter(s -> GlobalData.getInstance().isMainServer(s.getId()))
				.filter(s -> s.getServerType() == ServerType.NORMAL)
				.collect(Collectors.toList());
		Collections.sort(serverInfoList, Comparator.comparing(ServerInfo::getOpenTime));
		int termId = getTermId();
		Collections.shuffle(serverInfoList, new Random(termId)); //乱序

		List<String> result = new ArrayList<>();
		long now = HawkTime.getMillisecond();
		long serverDelay = Ann7Party384KVCfg.getInstance().getServerDelay();
		for (ServerInfo sinfo : serverInfoList) {
			long timeLimit = HawkTime.parseTime(sinfo.getOpenTime()) + serverDelay;
			if (timeLimit < now) {
				result.add(sinfo.getId());
			}
		}

		return result;
	}
	
	/**
	 * 房主消耗返还邮件
	 * @param playerId
	 */
	private void sendConsumeBackMail(Party384Room room) {
		String playerId = room.getMasterPlayerId();
		String createRoomConsume = Ann7Party384KVCfg.getInstance().getOpenPartyRoomItem();
		SystemMailService.getInstance().sendMail(MailParames.newBuilder()
				.setPlayerId(playerId)
				.setMailId(MailId.ANN7_PARTY_MASTER_CONSUME_BACK)
				.addRewards(ItemInfo.valueListOf(createRoomConsume))
				.setAwardStatus(MailRewardStatus.NOT_GET) 
				.build());
		for (String memberPlayerId : room.getRoomMemberSet()) {
			sendJoinRoomConsumeBackMail(memberPlayerId, JoinRoomComsumeBackType.WAITING_TIMEOUT_VALUE);
		}
	}
	
	/**
	 * 房客消耗返还邮件
	 * @param playerId
	 */
	public void sendJoinRoomConsumeBackMail(String playerId, int reason) {
		//机器人不补发
		if (HawkOSOperator.isEmptyString(playerId) || playerId.startsWith(Ann7PartyConst.ROBOT_PREFIX)){
			return;
		}
		try {
			HawkLog.logPrintln("sendJoinRoomConsumeBackMail, playerId: {}, reason: {}", playerId, reason);
			String joinRoomConsume = Ann7Party384KVCfg.getInstance().getInPartyRoomItem();
			SystemMailService.getInstance().sendMail(MailParames.newBuilder()
					.setPlayerId(playerId)
					.setMailId(MailId.ANN7_PARTY_JOIN_CONSUME_BACK) //TODO 房客消耗返还邮件
					.addContents(reason)
					.addRewards(ItemInfo.valueListOf(joinRoomConsume))
					.setAwardStatus(MailRewardStatus.NOT_GET) 
					.build());
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	/**
	 * 停服处理
	 */
	public void onShutdown() {
		for (Party384Room room : partyRoomData.values()) {
			try {
				shutdownRemoveRoom(room);
				//本地成员清空聚会奖励
				for (String memberId : room.getRoomMemberSet()) {
					Ann7Party384Entity entity = getDBEntity(memberId);
					if (entity != null) {
						entity.decJoinRoomCount();
						HawkLog.logPrintln("ann7party server shutdown, memberId: {}", memberId);
					}
				}
			} catch (Exception e) {
				HawkException.catchException(e);
			}
		}
	}
	
	/**
	 * 移除房间
	 * @param room
	 */
	private void shutdownRemoveRoom(Party384Room room) {
		//非本服的不处理
		if (!GlobalData.getInstance().isLocalServer(room.getMasterServerId())) {
			return;
		}
		
		getRedisSession().hDelBytes(roomRedisKey(), room.getRoomId().getBytes());
		//非机器人房间，给房主返还消耗物品邮件
		if (!room.isRobotRoom()) {
			sendConsumeBackMail(room);
			Ann7Party384Entity entity = getDBEntity(room.getMasterPlayerId());
			entity.decCreateRoomCount();
			HawkLog.logPrintln("ann7party server shutdown, masterId: {}", room.getMasterPlayerId());
		}
	}
	
	/**
	 * 添加或更新房间数据
	 * @param room
	 * @param serviceType
	 */
	public void addOrUpdateRoom(Party384Room room) {
		getRedisSession().hSetBytes(roomRedisKey().getBytes(), room.getRoomId().getBytes(), room.toRedisPBObj().toByteArray(), getOneHour()*24);
		addRoom(room);
	}
	
	/**
	 * 添加房间
	 * @param room
	 */
	public void addRoom(Party384Room room) {
		String roomId = room.getRoomId();
		partyRoomData.put(roomId, room);
		if (!roomIdQueue.contains(roomId)) {
			roomIdQueue.addLast(roomId);
		}
		if (!lastestRoomQueue.contains(roomId) && !room.joinNeedApply()) {
			lastestRoomQueue.addFirst(roomId);
		}
		int roomCount = Ann7Party384KVCfg.getInstance().getShowRoomValueLimit();
		if (lastestRoomQueue.size() > roomCount + 10) {
			lastestRoomQueue.removeLast();
		}
	}
	
	/**
	 * 删除房间
	 * @param room
	 */
	public void removeRoom(Party384Room room) {
		for (String playerId : room.getRoomMemberSet()) {
			leaveRoom(playerId);
		}
		leaveRoom(room.getMasterPlayerId());
		partyRoomData.remove(room.getRoomId());
		roomIdQueue.remove(room.getRoomId());
		lastestRoomQueue.remove(room.getRoomId());
	}
	
	/**
	 * 进入房间
	 * @param room
	 * @param playerId
	 */
	public void intoRoom(Party384Room room, String playerId) {
		playerRoomMap.put(playerId, room.getRoomId());
	}
	
	/**
	 * 离开房间
	 * @param playerId
	 */
	public void leaveRoom(String playerId) {
		playerRoomMap.remove(playerId);
	}
	
	public void removePlayerInfo(String playerId) {
		playerInfoMap.remove(playerId);
	}
	
	/**
	 * 添加或更新玩家信息
	 * @param player
	 */
	public void addOrUpdatePlayerInfo(Player player) {
		if (player == null) {
			return;
		}
		Party384RoomMember playerInfo = playerInfoMap.get(player.getId());
		if (playerInfo != null) {
			return;
		}
		playerInfo = new Party384RoomMember();
		playerInfo.setPlayerId(player.getId());
		playerInfo.setPlayerName(player.getName());
		playerInfo.setVipLevel(player.getVipLevel());
		playerInfo.setIcon(player.getIcon());
		playerInfo.setPfIcon(player.getPfIcon());
		playerInfo.setServerId(thisServerId());
		playerInfo.setPower(player.getPower());
		String key = playerInfoKey();
		byte[] info = playerInfo.toBuilder().build().toByteArray();
		getRedisSession().hSetBytes(key.getBytes(), player.getId().getBytes(), info, getRedisExpire());
		playerInfoMap.put(player.getId(), playerInfo);
	}
	
	public void addOrUpdatePlayerInfo(Party384RoomMember robot) {
		if (robot == null) {
			return;
		}
		Party384RoomMember playerInfo = playerInfoMap.get(robot.getPlayerId());
		if (playerInfo != null) {
			return;
		}
		playerInfo = new Party384RoomMember();
		playerInfo.setPlayerId(robot.getPlayerId());
		playerInfo.setPlayerName(robot.getPlayerName());
		playerInfo.setVipLevel(robot.getVipLevel());
		playerInfo.setIcon(robot.getIcon());
		playerInfo.setPfIcon(robot.getPfIcon());
		playerInfo.setServerId(robot.getServerId());
		playerInfo.setPower(robot.getPower());
		playerInfo.setFrame(robot.getFrame());
		String key = playerInfoKey();
		byte[] info = playerInfo.toBuilder().build().toByteArray();
		getRedisSession().hSetBytes(key.getBytes(), robot.getPlayerId().getBytes(), info, getRedisExpire());
		playerInfoMap.put(robot.getPlayerId(), playerInfo);
	}
	
	/**
	 * 获取玩家信息
	 * @param playerId
	 * @return
	 */
	public Party384RoomMember getPlayerInfo(String playerId) {
		Party384RoomMember playerInfo = playerInfoMap.get(playerId);
		if (playerInfo != null) {
			return playerInfo;
		}
		
		String key = playerInfoKey();
		byte[] data = getRedisSession().hGetBytes(key, playerId);
		if (data != null) {
			PartyRoomMemberInfo.Builder builder = PartyRoomMemberInfo.newBuilder();
			try {
				builder.mergeFrom(data);
				Party384RoomMember member = new Party384RoomMember();
				member.mergeFrom(builder.build());
				playerInfoMap.put(playerId, member);
				playerInfo = member;
			} catch (InvalidProtocolBufferException e) {
				HawkException.catchException(e);
			}
			return playerInfo;
		}
		
		if (isLocalPlayer(playerId)) {
			Player player = GlobalData.getInstance().makesurePlayer(playerId);
			if (player != null) {
				addOrUpdatePlayerInfo(player);
				playerInfo = playerInfoMap.get(playerId);
			}
		}
		return playerInfo;
	}
	
	/**
	 * 创建房间
	 */
	public void createPartyRoom(Player player, PartyRoomSelectInfo selectInfo) {
		roomMasterRewardMap.remove(player.getId());
		Ann7Party384Entity entity = getDBEntity(player.getId());
		Party384Room room = Party384Room.create(selectInfo.getAutoStart(), selectInfo.getJoinApply());
		Party384RoomMember master = Party384RoomMember.createPlayerMember(player);
		room.setRoomMaster(master);
		
		addOrUpdateRoom(room);
		intoRoom(room, player.getId());
		CreateRoomCSPB.Builder builder = CreateRoomCSPB.newBuilder();
		builder.setPartyRoom(room.toRedisPBObj());
		builder.addAllApplyJoinRoom(entity.getApplyJoinRoomSet());
		builder.addAllInviteMeRoom(entity.getInviteMeRoomSet());
		notifyGroupServer(HP.code2.ANN7_PARTY_CREATE_ROOM_C_VALUE, room.getRoomId(), builder.build().toByteString());
		
		for(int i=1; i<=entity.getApplyJoinRoomSet().size(); i++) {
			this.sendJoinRoomConsumeBackMail(player.getId(), JoinRoomComsumeBackType.NOT_APPROVED_VALUE);
		}
		//创建成功后的处理：从排队里面移除、将申请移除、将别人向自己发出的邀请移除、增加当日创建房间次数
		entity.incCreateRoomCount();
		updateApplyAndInvite(player.getId(), entity.getApplyJoinRoomSet(), entity.getInviteMeRoomSet());
		entity.removeAllApplyJoinRoom(); 
		entity.removeAllInviteMeRoom();
		
		//排队数量
		int queueCnt = getQueueMemberTotal();
		 //排队的机器人数量
		String robotCountStr = getRedisSession().getString(queueRobotKey());
		int queueRobotCount = HawkOSOperator.isEmptyString(robotCountStr) ? 0 : Integer.parseInt(robotCountStr);
		//排队的真实玩家数
		int queuePlayerCount =queueCnt - queueRobotCount; 
		
		//创建房间打点
		logCreateRoom(room,queueCnt,queuePlayerCount,queueRobotCount);
	}
	
	/**
	 * 创建房间后，从申请过的房间，以及邀请过自己的房间中清除
	 * @param playerId
	 * @param applyJoinRooms
	 * @param inviteJoinRooms
	 */
	public void updateApplyAndInvite(String playerId, Collection<String> applyJoinRooms, Collection<String> inviteJoinRooms) {
		try {
			for (String roomId : applyJoinRooms) {
				Party384Room room = getRoomById(roomId);
				if (room != null && room.isJoinApplyPlayer(playerId)) {
					room.removeJoinApply(playerId);
					syncRoomJoinApplyPlayer(room, 0, "");
				}
			}
			for (String roomId : inviteJoinRooms) {
				Party384Room room = getRoomById(roomId);
				if (room != null && room.isInvitedPlayer(playerId)) {
					room.removeInvitePlayer(playerId);
					syncRoomInvitePlayer(room, 0, "");
				}
			}
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	/**
	 * 设置房间勾选状态
	 * @param room
	 * @param selectInfo
	 */
	protected void setRoom(Party384Room room, PartyRoomSelectInfo selectInfo) {
		Set<String> addMembers = new HashSet<>();
		setRoom(room, selectInfo, true, addMembers);
	}
	
	public void setRoom(Party384Room room, PartyRoomSelectInfo selectInfo, boolean local, Set<String> addMembers) {
		room.setSelectInfo(selectInfo.getAutoStart(), selectInfo.getJoinApply(), local, addMembers);
		if (room.joinNeedApply() && lastestRoomQueue.contains(room.getRoomId())) {
			lastestRoomQueue.remove(room.getRoomId());
		}
		
		if (local) {
			addOrUpdateRoom(room);
			StringJoiner sj = new StringJoiner(",");
			sj.add(String.valueOf(selectInfo.getAutoStart()));
			sj.add(String.valueOf(selectInfo.getJoinApply()));
			for(String playerId : addMembers) {
				sj.add(playerId);
			}
			CrossCommonPB.Builder crossReq = CrossCommonPB.newBuilder();
			crossReq.setValue(sj.toString());
			notifyGroupServer(HP.code2.ANN7_PARTY_ROOM_SET_C_VALUE, room.getRoomId(), crossReq.build().toByteString());
		} else if (!room.joinNeedApply()) {
			addRoom(room); //主要是加到 lastestRoomQueue 中
		}
		
		syncPlayer(room.getMasterPlayerId());
		for (String pid : room.getRoomMemberSet()) {
			syncPlayer(pid);
		}
		
		HawkLog.logPrintln("ann7party set room, serverId: {}, roomId: {}, master: {}, autoStart: {}, joinApply: {}", thisServerId(), room.getRoomId(), 
				room.masterInfo(), selectInfo.getAutoStart(), selectInfo.getJoinApply());
	}
	
	/**
	 * 分享房间
	 * @param player
	 * @param room
	 * @param req
	 * @param local
	 */
	public void shareRoom(Player player, Party384Room room, PartyRoomShareReq req, boolean local) {
		if (local && req.getType() == ShareRoomType.TO_GUILD) {
			shareToGuild(room, player);
			return;
		}
		boolean succ = shareToWorld(room, player);
		if (local && succ) {
			notifyGroupServer(HP.code2.ANN7_PARTY_SHARE_ROOM_C_VALUE, room.getRoomId(), req.toByteString());
		}
	}
	
	/**
	 * 分享到联盟
	 * @param room
	 * @param player
	 */
	private void shareToGuild(Party384Room room, Player player) {
		long timeNow = HawkTime.getMillisecond();
		long shareTime = room.getShareTime(player.getGuildId());
		if (timeNow - shareTime < Ann7Party384KVCfg.getInstance().getShareCD()) {
			syncActivityInfo(player);
			HawkLog.errPrintln("anny party share room error: {}, playerId: {}, roomId: {}", "guild cd in service", player.getId(), room.getRoomId());
			return;
		}
		room.setShareTime(player.getGuildId(), timeNow);
		ChatParames chatParams = ChatParames.newBuilder()
				.setChatType(Const.ChatType.CHAT_ALLIANCE)
				.setKey(NoticeCfgId.ANN7_PARTY_SHARE_GUILD)
				.setGuildId(player.getGuildId())
				.setPlayer(player)
				.addParms(room.getRoomMaster().getPlayerName())
				.addParms(room.getRoomId())
				.addParms(room.getMasterServerId())
				.addParms(room.memberCount())
				.build();
		ChatService.getInstance().addWorldBroadcastMsg(chatParams);
		
		HawkLog.logPrintln("anny party share room guild succ, playerId: {}, roomId: {}", player.getId(), room.getRoomId());
	}
	
	/**
	 * 分享到世界
	 * @param room
	 * @param player
	 * @return
	 */
	private boolean shareToWorld(Party384Room room, Player player) {
		String playerId = player == null ? "" : player.getId();
		String serverId = thisServerId();
		long timeNow = HawkTime.getMillisecond();
		long shareTime = room.getShareTime(Ann7PartyConst.SHARE_SERVER);
		if (timeNow - shareTime < Ann7Party384KVCfg.getInstance().getShareCD()) {
			syncActivityInfo(player);
			HawkLog.errPrintln("anny party share room error: {}, playerId: {}, roomId: {}", "world-in-"+serverId, playerId, room.getRoomId());
			return false;
		}
		
		room.setShareTime(Ann7PartyConst.SHARE_SERVER, timeNow);
		ChatParames.Builder chatParams = ChatParames.newBuilder()
				.setChatType(Const.ChatType.CHAT_WORLD)
				.setKey(NoticeCfgId.ANN7_PARTY_SHARE_WORLD)
				.addParms(room.getRoomMaster().getPlayerName())
				.addParms(room.getRoomId())
				.addParms(room.getMasterServerId())
				.addParms(room.memberCount());
		if (player != null) {
			chatParams.setPlayer(player);
		}
		ChatService.getInstance().addWorldBroadcastMsg(chatParams.build());
		HawkLog.logPrintln("anny party share room world succ, playerId: {}, serverId: {}, roomId: {}", playerId, serverId, room.getRoomId());
		return true;
	}
	
	/**
	 * 房主踢出现有成员
	 * @param room
	 * @param tarPlayerId
	 */
	public void kickoutMember(Party384Room room, KickoutMemberReq req, boolean local, int type) {
		String playerId = req.getPlayerId();
		room.removeMember(playerId, local);
		if (local) {
			notifyGroupServer(HP.code2.ANN7_PARTY_ROOM_KICKOUT_C_VALUE, room.getRoomId(), req.toByteString());
			logKickoutMember(room, playerId, type);
		}
		
		if(isLocalPlayer(playerId) && !isCrossServerOut(playerId)) {
			notifyLeaveRoom(playerId, room.getRoomId(), LeaveRoomType.BE_KICKOUT);
			syncPlayer(playerId);
			SystemMailService.getInstance().sendMail(MailParames.newBuilder()
					.setPlayerId(playerId)
					.setMailId(MailId.ANN7_PARTY_KICKOUT_MEMBER)
					.addContents(room.getRoomId())
					.build());
		}
		
		syncPlayer(room.getMasterPlayerId());
		for (String pid : room.getRoomMemberSet()) {
			syncPlayer(pid);
		}
	}
	
	private void notifyLeaveRoom(String playerId, String roomId, LeaveRoomType reason) {
		Player player = GlobalData.getInstance().getActivePlayer(playerId);
		if (player != null) {
			LeaveRoomNotifyPB.Builder builder = LeaveRoomNotifyPB.newBuilder();
			builder.setRoomId(roomId);
			builder.setType(reason);
			player.sendProtocol(HawkProtocol.valueOf(HP.code2.ANN7_PARTY_LEAVE_ROOM_NOTIFY_VALUE, builder));
		}
	}
	
	/**
	 * 房主邀请加入房间
	 * @param room
	 * @param tarPlayerId
	 * @param tarServerId
	 */
	public void inviteJoinRoom(Party384Room room, InviteJoinReq req, boolean local) {
		String playerId = req.getPlayerId();
		room.invitePlayer(playerId, local);
		if (local) {
			notifyGroupServer(HP.code2.ANN7_PARTY_INVITE_C_VALUE, room.getRoomId(), req.toByteString());
			syncRoomInvitePlayer(room, 1, playerId);
		}
		
		syncPlayer(playerId);
	}
	
	/**
	 * 处理邀请
	 * @param playerId
	 * @param room
	 * @param local
	 */
	public int dealInvite(String playerId, Party384Room room, DealInviteReq req, boolean local) {
		int result = room.dealInvite(playerId, req.getAgree(), local);
		if (result < 0) {
			syncRoomInvitePlayer(room, 0, ""); //不同意加入，给房主同步邀请人员信息
			return result;
		}
		if (local) {
			if (req.getAgree() <= 0) { //拒绝了
				String key = refuseInviteKey(room.getRoomId(), playerId);
				int expire = Ann7Party384KVCfg.getInstance().getFriendCD();
				getRedisSession().setString(key, String.valueOf(HawkTime.getMillisecond()), expire);
			}
			DealInviteReq.Builder crossReq = DealInviteReq.newBuilder();
			crossReq.setRoomId(playerId);
			crossReq.setAgree(req.getAgree());
			notifyGroupServer(HP.code2.ANN7_PARTY_DEAL_INVITE_C_VALUE, room.getRoomId(), crossReq.build().toByteString());
		}
		
		syncRoomInvitePlayer(room, 0, ""); //不同意加入，给房主同步邀请人员信息
		if (req.getAgree() > 0) {
			syncPlayer(room.getMasterPlayerId()); //同意加入，给房主同步房间信息
			for (String pid : room.getRoomMemberSet()) {
				syncPlayer(pid);
			}
		} else {
			syncPlayer(playerId);
		}
		return 0;
	}
	
	/**
	 * 玩家申请加入房间
	 * @param playerId
	 * @param room
	 * @param req
	 * @param local
	 */
	public int applyJoinRoom(String playerId, Party384Room room, boolean local) {
		int result = room.applyJoin(playerId, local);
		if (result < 0) {
			leaveQueue(playerId);
			return result;
		}
		if (local) {
			CrossCommonPB.Builder builder = CrossCommonPB.newBuilder();
			builder.setValue(playerId);
			notifyGroupServer(HP.code2.ANN7_PARTY_APPLY_JOIN_C_VALUE, room.getRoomId(), builder.build().toByteString());
		}
		
		//加入房间了，给房主和玩家本身同步房间信息
		if (!room.joinNeedApply()) {
			leaveQueue(playerId);
			syncPlayer(room.getMasterPlayerId());
			for (String pid : room.getRoomMemberSet()) {
				syncPlayer(pid);
			}
		} else {
			syncRoomJoinApplyPlayer(room, 1, playerId);
		}
		return result;
	}
	
	/**
	 * 房主处理加入申请
	 * @param room
	 * @param req
	 */
	public int dealJoinApply(Party384Room room, DealJoinApplyReq req, boolean local) {
		//1同意0拒绝
		String playerId = req.getPlayerId();
		int result = room.dealJoinApply(playerId, req.getAgree(), local);
		if (result < 0) {
			return result;
		}
		if (local) {
			syncRoomJoinApplyPlayer(room, 0, "");
			notifyGroupServer(HP.code2.ANN7_PARTY_DEAL_APPLY_C_VALUE, room.getRoomId(), req.toByteString());
			if (req.getAgree() > 0) {
				leaveQueue(playerId);
			}
		}
		
		syncPlayer(playerId);
		//同意了，给房主同步房间信息
		if (req.getAgree() > 0) {
			syncPlayer(room.getMasterPlayerId());
			for (String pid : room.getRoomMemberSet()) {
				syncPlayer(pid);
			}
		}
		return 0;
	}
	
	/**
	 * 聚会开启
	 * 1. 确定各个轮次房主、房客的奖励
	 * @param room
	 * @param local
	 */
	public void startParty(Party384Room room, StartPartyCSPB req, boolean local) {
		room.startParty(local);
		if (local) {
			randomReward(room);
			req = room.buildPartyReward();
			addOrUpdateRoom(room);
			notifyGroupServer(HP.code2.ANN7_PARTY_START_PARTY_C_VALUE, room.getRoomId(), req.toByteString());
			logStartParty(room);
		}
		
		Set<String> memeberSet = new HashSet<>();
		for (PartyRoundCSPB roundInfo : req.getRoundRewardList()) {
			int round = roundInfo.getRound();
			if (!local) {
				room.initRoundRewards(round, roundInfo.getInitRewardList());
				room.addMasterReward(round, roundInfo.getMasterRewardList());
				for (PartyRoundRewardPB memberReward : roundInfo.getMemberRewardList()) {
					room.addMemberReward(round, memberReward.getPlayerId(), memberReward.getReward());
				}
			}
			
			for (PartyRoundRewardPB memberReward : roundInfo.getMemberRewardList()) {
				memeberSet.add(memberReward.getPlayerId());
			}
		}
		
		for (String playerId : memeberSet) {
			Ann7Party384Entity entity = getDBEntity(playerId);
			if (entity == null) {
				continue;
			}
			boolean master = playerId.equals(room.getMasterPlayerId());
			HawkXID xid = HawkXID.valueOf(ObjType.PLAYER, playerId);
			int threadIdx = xid.getHashThread(HawkTaskManager.getInstance().getThreadNum());
			HawkTaskManager.getInstance().postTask(new HawkTask() {
				@Override
				public Object run() {
					for(int i=1; i<=entity.getApplyJoinRoomSet().size(); i++) {
						sendJoinRoomConsumeBackMail(playerId, JoinRoomComsumeBackType.NOT_APPROVED_VALUE);
					}
					entity.startParty(master);
					syncPlayer(playerId);
					return null;
				}
				
			}, threadIdx);
		}
		
		HawkLog.logPrintln("ann7party start party, serverId: {}, roomId: {}, master: {}, members: {}, local: {}", thisServerId(), room.getRoomId(), room.masterInfo(), room.memberCount(), local);
	}
	
	/**
	 * 开启聚会时，随机分配奖励
	 * @param room
	 * @return
	 */
	private void randomReward(Party384Room room) {
		Ann7Party384KVCfg cfg = Ann7Party384KVCfg.getInstance();
		for (int round = 1; round <= cfg.getRewardRoundLimit(); round++) {
			List<String> rewardList = Ann7Party384RewardCfg.randomRewards(round, cfg.getGoodRewardCount(round), cfg.getRewardBoxLimit());
			Collections.shuffle(rewardList);
			HawkLog.logPrintln("ann7party start party randomReward, serverId: {}, roomId: {}, master: {}, rewards: {}", thisServerId(), room.getRoomId(), room.masterInfo(), rewardList);
			room.initRoundRewards(round, rewardList);
			for (String playerId : room.getRoomMemberSet()) {
				String reward = rewardList.remove(0);
				room.getMemberRewardMap(round).put(playerId, reward);
			}
			
			//上面给房客安排完了，剩下的就给房主了（机器人房间直接跳过）
			if (room.isRobotRoom()) {
				continue;
			}
			
			String reward = rewardList.remove(0);
			room.getMemberRewardMap(round).put(room.getMasterPlayerId(), reward);
			int masterRewardCount = cfg.getHomeownerGetBoxLimit() - 1;
			if (rewardList.size() <= masterRewardCount){
				room.getMasterRewardList(round).addAll(rewardList);
			} else {
				room.getMasterRewardList(round).addAll(rewardList.subList(0, masterRewardCount));
			}
		}
	}
	
	/**
	 * 玩家抽取奖励
	 * @param playerId
	 * @param room
	 * @param req
	 * @param local
	 * @return
	 */
	public SelectRewardResp.Builder selectAward(String playerId, Party384Room room, SelectRewardReq req, boolean local) {
		if (room.isRoundRewardShow()) {
			HawkLog.logPrintln("ann7party select award index break, playerId: {}, roomId: {}, serverId: {}-{}", playerId, room.getRoomId(), thisServerId(), room.getMasterServerId());
			syncPlayer(playerId);
			return null;
		}
		
		int index = req.getRewardIndex();
		SelectRewardResp.Builder resp = SelectRewardResp.newBuilder();
		resp.setResult(0);
		String awardKey = awardLockKey(room.getRoomId(), room.getRewardRound());
		int result = 1;
		if (!local) {
			room.updateRewardIndex(playerId, index);
		} else {
			List<String> list = getRedisSession().hVals(awardKey);
			if(list != null && list.contains(playerId)) {
				result = 0;
				HawkLog.logPrintln("ann7party select award index repeated, playerId: {}, index: {}, groupId: {}, roomId: {}, round: {}", playerId, index, groupId, room.getRoomId(), room.getRewardRound());
			} else {
				result = (int)getRedisSession().hSetNx(awardKey, String.valueOf(index), playerId);
				if (result > 0) {
					getRedisSession().expire(awardKey, getOneHour() * 24);
					room.updateRewardIndex(playerId, index);
					String paramRoomId = room.getRoomId() + "," + playerId;
					notifyGroupServer(HP.code2.ANN7_PARTY_SELECT_AWARD_C_VALUE, paramRoomId, req.toByteString());
					HawkLog.logPrintln("ann7party select award index succ, playerId: {}, index: {}, groupId: {}, roomId: {}, round: {}", playerId, index, groupId, room.getRoomId(), room.getRewardRound());
				} else {
					HawkLog.logPrintln("ann7party select award index failed, playerId: {}, index: {}, groupId: {}, roomId: {}, round: {}", playerId, index, groupId, room.getRoomId(), room.getRewardRound());
				}
			}
		}
		
		Map<String, String> map = getRedisSession().hGetAll(awardKey);
		for (String val : map.keySet()) {
			resp.addRewardIndex(Integer.parseInt(val));
		}
		
		//抽奖失败了，只需给玩家一个人同步结果就行
		if (result <= 0) {
			resp.setResult(1);
		} else {
			for(String mplayerId : room.getMemberRewardMap().keySet()) {
				if (!mplayerId.equals(playerId)) {
					syncPlayer(mplayerId);
				}
			}
		}
		
		return resp;
	}

	public boolean addMemberSync(String roomId) {
		String incToolKey = "ann7PartyMemInc:" + roomId;
		long count = getRedisSession().increaseBy(incToolKey, 1, getOneHour());
		int max = Ann7Party384KVCfg.getInstance().getPartyRoomMaxValue();
		return count <= max;
	}
	
	public void removeMemberSync(String roomId) {
		String incToolKey = "ann7PartyMemInc:" + roomId;
		getRedisSession().increaseBy(incToolKey, -1, getOneHour());
	}
	
	//------------------- 排队信息 
	protected int getQueueMemberTotal() {
		String key = queueListKey();
		long total = getRedisSession().lLen(key);
		return (int) total;
	}
	
	protected int getQueueRank(String playerId) {
		String key = queueRankKey();
		Long rank = getRedisSession().zRank(key, playerId);
		return rank == null ? -1 : rank.intValue();
	}
	
	protected void addToQueue(String playerId) {
		int expire = getRedisExpire();
		String key = queueListKey();
		getRedisSession().rPush(key, expire, playerId);
		
		String incToolKey = "ann7PartyQueueInc:" + getTermId() + ":" + groupId;
		long count = getRedisSession().increaseBy(incToolKey, 1, expire);
		long nonaTime = 1760957999000L + count; //2025-10-20 18:59:59
		
		String rankKey = queueRankKey();
		getRedisSession().zAdd(rankKey, nonaTime, playerId, expire);
		if (playerId.startsWith(Ann7PartyConst.ROBOT_PREFIX)) {
			String robotKey = queueRobotKey();
			getRedisSession().increaseBy(robotKey, 1, expire);
		}
	}
	
	private String getFirstQueuePlayer() {
		String key = queueListKey();
		String playerId = RedisProxy.getInstance().lpop(key);
		if (!HawkOSOperator.isEmptyString(playerId)) {
			getRedisSession().zRem(queueRankKey(), 0, playerId);
			if (playerId.startsWith(Ann7PartyConst.ROBOT_PREFIX)) {
				String robotKey = queueRobotKey();
				getRedisSession().increaseBy(robotKey, -1, 0);
			}
		}
		return playerId;
	}
	
	protected void leaveQueue(String playerId) {
		long rank = getQueueRank(playerId);
		if (rank >= 0) {
			sendJoinRoomConsumeBackMail(playerId, JoinRoomComsumeBackType.LEAVE_QUEUE_VALUE);
		}
		String key = queueListKey();
		getRedisSession().lRem(key, 1, playerId);
		getRedisSession().zRem(queueRankKey(), 0, playerId);
		HawkLog.logPrintln("anny party kickout from queue, playerId: {}, serverId: {}", playerId, thisServerId());
	}
	
	/**
	 * 玩家迁服后从队列中移除
	 * @param playerId
	 */
	public void kickout(String playerId) {
		try {
			if (isActivityOpening(playerId)) {
				leaveQueue(playerId);
			}
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	//------------------- 排队信息 end
	
	
	private boolean buildPartyRewardInfo(String playerId, PlayerPartyInfo.Builder builder) {
		Ann7Party384Entity entity = this.getDBEntity(playerId);
		for (RoundRewardObject obj : entity.getPartyRewardMap().values()) {
			PartyRoundRewardPB.Builder rewardBuilder = PartyRoundRewardPB.newBuilder();
			rewardBuilder.setRound(obj.getRound());
			rewardBuilder.setRewardIndex(obj.getIndex());
			rewardBuilder.setReward(obj.getReward());
			builder.addReward(rewardBuilder);
		}
		
		return entity.getPartyRewardMap().isEmpty();
	}
	
	private void buildJoinRoomInfo(String playerId, PlayerPartyInfo.Builder builder) {
		List<String> rooms = new ArrayList<>();
		int roomCount = Ann7Party384KVCfg.getInstance().getShowRoomValueLimit();
		Ann7Party384Entity entity = getDBEntity(playerId);
		Set<String> roomIds = entity.getInviteMeRoomSet();
		for (String roomId : roomIds) {
			Party384Room room = this.getRoomById(roomId);
			if (room == null) {
				rooms.add(roomId);
				continue;
			}
			if (room.isFullMember() || room.getRoundBeginTime() > 0) {
				continue;
			}
			roomCount--;
			PartyRoomBriefInfo.Builder roomBuilder = room.toBriefInfoBuilder();
			roomBuilder.setType(JoinRoomType.INVITE);
			roomBuilder.setInviteTime(room.getInvitePlayerTimeMap().get(playerId));
			builder.addRoomInfo(roomBuilder);
		}
		
		if (!rooms.isEmpty()) {
			entity.getInviteMeRoomSet().removeAll(rooms);
			entity.notifyUpdate();
		}
		
		if (roomCount <= 0) {
			return;
		}
		
		rooms.clear();
		for (String roomId : lastestRoomQueue) {
			if (roomIds.contains(roomId)) {
				continue;
			} 
			Party384Room room = this.getRoomById(roomId);
			if (room == null) {
				rooms.add(roomId);
				continue;
			}
			if (room.joinNeedApply() || room.isFullMember() || room.getRoundBeginTime() > 0) {
				rooms.add(roomId);
				continue;
			}
			PartyRoomBriefInfo.Builder roomBuilder = room.toBriefInfoBuilder();
			roomBuilder.setType(JoinRoomType.FREEDOM);
			builder.addRoomInfo(roomBuilder);
			if(--roomCount == 0) {
				break;
			}
		}
		
		if (!rooms.isEmpty()) {
			lastestRoomQueue.removeAll(rooms);
		}
	}
	
	/**
	 * 判断是否是跨服出去（到别的服去）的玩家
	 * @param playerId
	 * @return
	 */
	public boolean isCrossServerOut(String playerId) {
		return CrossService.getInstance().isEmigrationPlayer(playerId);
	}
	
	/**
	 * 判断是否是跨服进来（从别的服进来）的玩家
	 * @param playerId
	 * @return
	 */
	public boolean isCrossServerIn(String playerId) {
		return CrossService.getInstance().isImmigrationPlayer(playerId);
	}
	
	/**
	 * 给指定玩家同步信息
	 * @param playerId
	 */
	public void syncPlayer(String playerId) {
		if (!isLocalPlayer(playerId) || isCrossServerOut(playerId)) {
			return;
		}
		Player player = GlobalData.getInstance().getActivePlayer(playerId);
		if (player != null) {
			syncActivityInfo(player);
		}
	}
	
	/**
	 * 给玩家同步活动信息
	 */
	public void syncActivityInfo(Player player) {
		if (player == null || !isLocalPlayer(player.getId()) || isCrossServerOut(player.getId())) {
			return;
		}
		PlayerPartyInfo.Builder builder = PlayerPartyInfo.newBuilder();
		builder.addAllGroupServer(this.getGroupServer());
		builder.setQueueSize(this.getQueueMemberTotal()); //总的排队人数
		
		Party384Room room = this.getPlayerRoom(player.getId());
		if (room == null) {
			builder.setType(JoinPartyType.NO_JOIN);
			boolean empty = buildPartyRewardInfo(player.getId(), builder);
			if (empty) {
				int queueIndex = this.getQueueRank(player.getId());
				builder.setQueueIndex(queueIndex); //自己排第几位
				this.buildJoinRoomInfo(player.getId(), builder);      //可选择加入的房间信息
			}
		} else if (room.getMasterPlayerId().equals(player.getId())) {
			builder.setType(JoinPartyType.ROOM_CREATOR);
			builder.setPartyRoom(room.getSyncPbObj());
		} else {
			builder.setType(JoinPartyType.ROOM_JOIN);
			builder.setPartyRoom(room.getSyncPbObj());
		}
		
		Ann7Party384Entity entity = getDBEntity(player.getId());
		if (entity != null) {
			entity.buildEntityInfo(builder);
		}
		
		RoomMasterRewardSyncPB.Builder masterReward = roomMasterRewardMap.get(player.getId());
		if (masterReward != null) {
			builder.setMasterReward(masterReward);
		}
		player.sendProtocol(HawkProtocol.valueOf(HP.code2.ANN7_PARTY_INFO_SYNC_VALUE, builder));
	}
	
	
	/**
	 * 解散房间
	 * @param room
	 */
	public void roomDissove(Party384Room room, boolean end, boolean local, int type) {
		try {
			if (end) {
				savePartyReward(room);
			}
			room.dissolve(local);
			removeRoom(room);
			if (local) {
				getRedisSession().hDelBytes(roomRedisKey(), room.getRoomId().getBytes());
				CrossCommonPB.Builder builder = CrossCommonPB.newBuilder();
				builder.setValue(end ? "1" : "0");
				notifyGroupServer(CrossServerOper.ROOM_DISSOLVE, room.getRoomId(), builder.build().toByteString());
				logRoomDiss(room, type);
			}
			
			HawkLog.logPrintln("ann7party room dissove, serverId: {}, roomId: {}, master: {}, local: {}", thisServerId(), room.getRoomId(), room.masterInfo(), local);
			syncPlayer(room.getMasterPlayerId());
			for (String playerId : room.getRoomMemberSet()) {
				if (end) {
					syncPlayer(playerId);
					continue;
				}
				Ann7Party384Entity entity = getDBEntity(playerId);
				if (entity != null && room.getRewardRound() > 0) {
					entity.decJoinRoomCount();
					HawkLog.logPrintln("ann7party roomDissove joinRoomCount recover, memberId: {}", playerId);
				}
				
				syncPlayer(playerId);
				notifyLeaveRoom(playerId, room.getRoomId(), LeaveRoomType.ROOM_DISSOVE);
				SystemMailService.getInstance().sendMail(MailParames.newBuilder()
						.setPlayerId(playerId)
						.setMailId(MailId.ANN7_PARTY_KICKOUT_MEMBER)
						.addContents(room.getRoomId())
						.build());
			}
		} catch (Exception e) {
			HawkException.catchException(e);
		}
		
		//存储奖励记录
		if (end) {
			savePartyRecord(room, local);
		}
	}
	
	private void savePartyReward(Party384Room room) {
		try {
			Ann7Party384KVCfg cfg = Ann7Party384KVCfg.getInstance();
			Map<String, List<PartyRoundRewardPB>> memeberRewardMap = new HashMap<>();
			for (int round = 1; round <= cfg.getRewardRoundLimit(); round++) {
				Map<Integer, PlayerRoundReward> roundRewardMap = room.getRoundRewardInfo(round).getRoundRewardShowMap();
				for (Entry<Integer, PlayerRoundReward> entry : roundRewardMap.entrySet()) {
					String playerId = entry.getValue().getPlayerId();
					if (!room.getRoomMemberSet().contains(playerId)) {
						continue;
					}
					PartyRoundRewardPB.Builder roundReward = entry.getValue().toBuilder();
					roundReward.setRound(round);
					List<PartyRoundRewardPB> rewardList = memeberRewardMap.get(playerId);
					if (rewardList == null) {
						rewardList = new ArrayList<>();
						memeberRewardMap.put(playerId, rewardList);
					}
					rewardList.add(roundReward.build());
				}
			}
			
			for (String playerId : room.getRoomMemberSet()) {
				try {
					Ann7Party384Entity entity = getDBEntity(playerId);
					if (entity != null) {
						entity.addHisJoinRoom(room.getRoomId());
						List<PartyRoundRewardPB> rewardList = memeberRewardMap.get(playerId);
						if (rewardList != null) {
							sendJoinRoomEndReward(playerId, room.getRoomId(), rewardList);
							entity.partyEnd(rewardList);
							HawkLog.logPrintln("ann7party savePartyReward succ, playerId: {}, groupId: {}, roomId: {}", playerId, groupId, room.getRoomId());
						} else {
							HawkLog.errPrintln("ann7party savePartyReward null error, playerId: {}, groupId: {}, roomId: {}", playerId, groupId, room.getRoomId());
						}
					}
				} catch (Exception e) {
					HawkException.catchException(e);
				}
			}
		} catch (Exception e){
			HawkException.catchException(e);
		}
	}
	
	/**
	 * 房间聚会结束后，给房客发奖励邮件
	 * @param playerId
	 * @param roomId
	 * @param rewardList
	 */
	private void sendJoinRoomEndReward(String playerId, String roomId, List<PartyRoundRewardPB> rewardList) {
		try {
			List<ItemInfo> rewardItemList = new ArrayList<>();
			for (PartyRoundRewardPB rewardInfo : rewardList) {
				rewardItemList.addAll(ItemInfo.valueListOf(rewardInfo.getReward()));
			}
			HawkLog.logPrintln("sendJoinRoomEndReward, playerId: {}, roomId: {}, rewardItemList: {}", playerId, roomId, rewardItemList);
			//房客聚会奖励邮件
			SystemMailService.getInstance().sendMail(MailParames.newBuilder()
					.setPlayerId(playerId)
					.setMailId(MailId.ANN7_PARTY_JOIN_ROOM_REWARD) //TODO 房客加入房间聚会奖励邮件
					.addRewards(rewardItemList)
					.addContents(roomId)
					.setAwardStatus(MailRewardStatus.NOT_GET) 
					.build());
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	/**
	 * 存储历史记录
	 * @param room
	 * @param local
	 */
	private void savePartyRecord(Party384Room room, boolean local) {
		try {
			PartyRoomRecordPB record = room.toRecordObj();
			if (local) {
				partyRecordCache.put(record.getRoomId(), record);
				getRedisSession().hSetBytes(roomRecordKey().getBytes(), record.getRoomId().getBytes(), record.toByteArray(), getOneHour()*24);
				return;
			}
			addRecord(record);
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	private void addRecord(PartyRoomRecordPB record) {
		boolean existLocalPlayer = false;
		for (PartyRoomMemberInfo member : record.getRoomMemberList()) {
			if (isLocalPlayer(member.getPlayerId())) {
				existLocalPlayer = true;
				break;
			}
		}
		if (existLocalPlayer) {
			partyRecordCache.put(record.getRoomId(), record);
		}
	}
	
	public PartyRoomRecordPB getRecord(String roomId) {
		return partyRecordCache.getIfPresent(roomId);
	}
	
	/**
	 * 解散房间补偿
	 * @param playerId
	 */
	public void dissoveRoomReissue(Party384Room room) {
		if (room.isRobotRoom()) {
			return;
		}
		//房主消耗返还邮件
		sendConsumeBackMail(room);
		Ann7Party384Entity entity = getDBEntity(room.getMasterPlayerId());
		if (entity != null) {
			entity.decCreateRoomCount();
		}
	}
	
	/**
	 * 自动接收玩家
	 * @param room
	 * @param local
	 */
	public void recievePlayerAuto(Party384Room room) {
		int memberCount = room.memberCount();
		int recieveCount = Ann7Party384KVCfg.getInstance().getPartyRoomMaxValue() - memberCount;
		StringJoiner sj = new StringJoiner(",");
		while (recieveCount > 0) {
			recieveCount--;
			String playerId = getFirstQueuePlayer();
			if (HawkOSOperator.isEmptyString(playerId)) {
				break;
			}
			if (getPlayerRoom(playerId) != null) {
				HawkLog.logPrintln("ann7party rec player auto break, roomId: {}, masterInfo: {}, playerId: {} has room", room.getRoomId(), room.masterInfo(), playerId);
				continue;
			}
//			if (CrossService.getInstance().isCrossPlayer(playerId)) {
//				HawkLog.logPrintln("ann7party rec player auto break, roomId: {}, masterInfo: {}, playerId: {}", room.getRoomId(), room.masterInfo(), playerId);
//				continue;
//			}
			if(room.recievePlayer(playerId, true)) {
				sj.add(playerId);
			} else {
				sendJoinRoomConsumeBackMail(playerId, JoinRoomComsumeBackType.NOT_APPROVED_VALUE);
			}
		}
		
		String players = sj.toString();
		if (HawkOSOperator.isEmptyString(players)) {
			return;
		}
		room.updateSyncPbObj();
		CrossCommonPB.Builder crossReq = CrossCommonPB.newBuilder();
		crossReq.setValue(players);
		notifyGroupServer(CrossServerOper.REC_QUEUE_PLAYER, room.getRoomId(), crossReq.build().toByteString());
		
		HawkLog.logPrintln("ann7party rec player auto, serverId: {}, roomId: {}, master: {}, players: {}", thisServerId(), room.getRoomId(), room.masterInfo(), players);
		
		syncPlayer(room.getMasterPlayerId());
		for (String playerId : room.getRoomMemberSet()) {
			syncPlayer(playerId);
		}
	}
	
	/**
	 * 进入展示期确定奖励顺序
	 * @param room
	 * @param local
	 */
	public void intoRoundRewardShow(Party384Room room, PartyRoundCSPB req, boolean local) {
		if (local) {
			Map<String, Integer> absentMap = room.checkAbsentPlayerIndex();
			PartyRoundCSPB.Builder builder = PartyRoundCSPB.newBuilder();
			for (Entry<String, Integer> entry : absentMap.entrySet()) {
				PartyRoundRewardPB.Builder reward = PartyRoundRewardPB.newBuilder();
				reward.setPlayerId(entry.getKey());
				reward.setRewardIndex(entry.getValue());
				builder.addMemberReward(reward);
			}
			Map<String, Integer> rewardIndexMap = room.getRewardIndexMap();
			for (Entry<Integer, PlayerRoundReward> entry : room.getRoundRewardShowMap().entrySet()) {
				if (rewardIndexMap.containsValue(entry.getKey())) {
					continue;
				}
				PartyRoundRewardPB.Builder reward = PartyRoundRewardPB.newBuilder();
				reward.setPlayerId(entry.getValue().getPlayerId());
				reward.setReward(entry.getValue().getReward());
				reward.setRewardIndex(entry.getKey());
				reward.setRound(room.getRewardRound());
				builder.addMemberReward(reward);
			}
			req = builder.build();
			notifyGroupServer(CrossServerOper.INTO_ROUND_SHOW, room.getRoomId(), req.toByteString());
		}
		
		for (PartyRoundRewardPB memberReward : req.getMemberRewardList()) {
			String playerId = memberReward.getPlayerId();
			int index = memberReward.getRewardIndex();
			if (memberReward.getRound() > 0) {
				if (!local) {
					PlayerRoundReward obj = PlayerRoundReward.create(playerId, memberReward.getReward(), index);
					room.getRoundRewardShowMap().put(index, obj);
				}
			} else {
				if (!local) {
					room.updateRewardIndex(playerId, index);
				}
			}
		}

		room.setRoundRewardShow(true);
		HawkLog.logPrintln("ann7party into round show, serverId: {}, roomId: {}, round: {}, masterInfo: {}, local: {}", thisServerId(), room.getRoomId(), room.getRewardRound(), room.masterInfo(), local);
		syncPlayer(room.getMasterPlayerId());
		for (String playerId : room.getRoomMemberSet()) {
			syncPlayer(playerId);
		}
	}
	
	/**
	 * 奖励轮次切换
	 * @param room
	 * @param local
	 */
	public void switchPartyRewardRound(Party384Room room, boolean local) {
		room.incRewardRound();
		if (local) {
			addOrUpdateRoom(room);
			notifyGroupServer(CrossServerOper.SWITCH_ROUND, room.getRoomId(), null);
		}
		
		HawkLog.logPrintln("ann7party switch round, serverId: {}, roomId: {}, master: {}, new round: {}, local: {}", thisServerId(), room.getRoomId(), room.masterInfo(), room.getRewardRound(), local);
		
		syncPlayer(room.getMasterPlayerId());
		for (String playerId : room.getRoomMemberSet()) {
			syncPlayer(playerId);
		}
	}
	
	
	/**
	 * 4轮抽奖都结束了，给房主结算奖励
	 * @param room
	 * @param local
	 */
	public void partyRewardEnd(Party384Room room, boolean local) {
		HawkLog.logPrintln("ann7party room party end, serverId: {}, roomId: {}, master: {}, local: {}", thisServerId(), room.getRoomId(), room.masterInfo(), local);
		//房主为真实玩家，结算奖励、解散房间
		if (!room.isRobotRoom()) {
			HawkXID xid = HawkXID.valueOf(ObjType.PLAYER, room.getMasterPlayerId());
			int threadIdx = xid.getHashThread(HawkTaskManager.getInstance().getThreadNum());
			HawkTaskManager.getInstance().postTask(new HawkTask() {
				@Override
				public Object run() {
					masterRewartSettle(room, local);
					return null;
				}
			}, threadIdx);
		}
		
		roomDissove(room, true, true, 1);
	}
	
	/**
	 * 给房主结算奖励
	 * @param room
	 * @param local
	 */
	private void masterRewartSettle(Party384Room room, boolean local) {
		try {
			RoomMasterRewardSyncPB.Builder masterRewardBuilder = RoomMasterRewardSyncPB.newBuilder();
			Ann7Party384Entity entity = getDBEntity(room.getMasterPlayerId());
			entity.addHisJoinRoom(room.getRoomId());
			int memberCount = room.memberCount();
			int remain = Ann7Party384KVCfg.getInstance().getFriendGiftLimit() - entity.getInviteAwardCount();
			memberCount = Math.min(memberCount, remain);
			if (memberCount > 0) {
				entity.incInviteAwardCount(memberCount);
				String reward = Ann7Party384KVCfg.getInstance().getHomeownerGetAward();
				List<ItemInfo> rewardItemList = ItemInfo.valueListOf(reward, memberCount);
				//房主邀请奖励邮件
				SystemMailService.getInstance().sendMail(MailParames.newBuilder()
						.setPlayerId(room.getMasterPlayerId())
						.setMailId(MailId.ANN7_PARTY_MASTER_INVITE_REWARD)
						.addRewards(rewardItemList)
						.addContents(room.getRoomId(), memberCount)
						.setAwardStatus(MailRewardStatus.NOT_GET)
						.build());
				rewardItemList.forEach(e -> masterRewardBuilder.addReward2(e.toRewardItem()));
			} else {
				HawkLog.logPrintln("ann7party party master invite reward full, roomId: {}, master: {}, count: {}", room.getRoomId(), room.masterInfo(), entity.getInviteAwardCount());
			}
			
			List<String> masterReward = room.getMasterRewardAll();
			int masterAwardRemain = Ann7Party384KVCfg.getInstance().getHomeownerDaliyGetLimit() - entity.getMasterAwardCount();
			masterAwardRemain = Math.min(masterAwardRemain, masterReward.size());
			final int masterAwardInc = masterAwardRemain;
			if (masterAwardRemain > 0) {
				entity.incMasterAwardCount(masterAwardRemain);
				StringJoiner sj = new StringJoiner(",");
				for (String reward : masterReward) {
					sj.add(reward);
					if(--masterAwardRemain == 0) {
						break;
					}
				}
				
				int createRoomRemainTimes = Ann7Party384KVCfg.getInstance().getOpenPartyRoomValueLimit() - entity.getCreateRoomCount();
				if (createRoomRemainTimes < 0) {
					createRoomRemainTimes = 0;
				}
				List<ItemInfo> rewardItemList = ItemInfo.valueListOf(sj.toString());
				//房主聚会奖励邮件
				SystemMailService.getInstance().sendMail(MailParames.newBuilder()
						.setPlayerId(room.getMasterPlayerId())
						.setMailId(MailId.ANN7_PARTY_MASTER_REWARD)
						.addRewards(rewardItemList)
						.addContents(room.getRoomId(), createRoomRemainTimes)
						.setAwardStatus(MailRewardStatus.NOT_GET) 
						.build());
				rewardItemList.forEach(e -> masterRewardBuilder.addReward1(e.toRewardItem()));
			} else {
				HawkLog.logPrintln("ann7party party master reward full, roomId: {}, master: {}, count: {}", room.getRoomId(), room.masterInfo(), entity.getMasterAwardCount());
			}
			
			masterRewardBuilder.setRoomId(room.getRoomId());
			roomMasterRewardMap.put(room.getMasterPlayerId(), masterRewardBuilder);
			Player player = GlobalData.getInstance().getActivePlayer(room.getMasterPlayerId());
			if (player != null) {
				player.sendProtocol(HawkProtocol.valueOf(HP.code2.ANN7_PARTY_MASTER_REWARD_SYNC, masterRewardBuilder));
			}
			
			HawkLog.logPrintln("ann7party party end, serverId: {}, roomId: {}, master: {}, local: {}, inviteInc: {}, masterAwardInc: {}", 
					thisServerId(), room.getRoomId(), room.masterInfo(), local, memberCount, masterAwardInc);
			
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	/**
	 * 生成机器人：检测机器人生成时间，每X分钟检测一次（要加锁，同一组内只让一个服去做这个事情）
	 * 1.大于x个排队游客，生成房主机器人
	 */
	private void genRobotRoomTick() {
		long now = lastCheckTime, createRobotTimeGap = Ann7Party384KVCfg.getInstance().getCreateRobotTime();
		if (now - checkRobotTime < createRobotTimeGap) {
			return;
		}
		checkRobotTime = now;
		if (getActivityEndTime() <= 0 || this.nearActivityEnd()) {
			return;
		}
		
		if (!Ann7Party384KVCfg.getInstance().checkTimeRange()) {
			return;
		}
		
		String key = robotLockKey();
		boolean succ = getRedisSession().setNx(key, thisServerId());
		if (!succ) {
			return;
		}
		
		String timeKey = key + ":time";
		try {
			String lastTime = getRedisSession().getString(timeKey);
			if (!HawkOSOperator.isEmptyString(lastTime) && now - Long.valueOf(lastTime) < createRobotTimeGap) {
				return;
			}
			getRedisSession().setString(timeKey, String.valueOf(now), getOneHour()*24);
			genRobotRoom();
		} catch (Exception e) {
			HawkException.catchException(e);
		} finally {
			getRedisSession().del(key);
		}
	}
	
	/**
	 * 生成机器人房间
	 */
	private void genRobotRoom() {
		int queueMemberTotal = getQueueMemberTotal();
		Ann7Party384RobotCfg robotCfg = Ann7Party384RobotCfg.getConfigByRoomCondVal(queueMemberTotal);
		if (robotCfg == null) {
			HawkLog.logPrintln("ann7party create robot room break, serverGroup: {}, serverId: {}, queueMemberTotal: {}", groupId, thisServerId(), queueMemberTotal);
			return;
		}
		
		//排队的机器人数量
		String robotCountStr = getRedisSession().getString(queueRobotKey());
		int queueRobotCount = HawkOSOperator.isEmptyString(robotCountStr) ? 0 : Integer.parseInt(robotCountStr);
		//排队的真实玩家数
		int queuePlayerCount =queueMemberTotal - queueRobotCount; 
		
		int newCount = HawkRand.randInt(robotCfg.getRobotValueMin(), robotCfg.getRobotValueMax());
		int robotCountNow = (int) partyRoomData.values().stream().filter(e -> e.isRobotRoom()).count();
		newCount = Math.min(newCount, robotCfg.getNotCreateRobotLimit() - robotCountNow);
		
		while (newCount > 0) {
			newCount--;
			try {
				Party384Room room = Party384Room.create(Ann7PartyConst.ROBOT_ROOM_AUTO_START, Ann7PartyConst.ROBOT_ROOM_JOIN_APPLY);
				Party384RoomMember master = Party384RoomMember.createRobot();
				this.addOrUpdatePlayerInfo(master);
				room.setRoomMaster(master);
				addOrUpdateRoom(room);
				intoRoom(room, master.getPlayerId());
				HawkLog.logPrintln("ann7party create robot room, serverGroup: {}, serverId: {}, master: {}, roomId: {}", groupId, master.getServerId(), master.getPlayerId(), room.getRoomId());
				CreateRoomCSPB.Builder builder = CreateRoomCSPB.newBuilder();
				builder.setPartyRoom(room.toRedisPBObj());
				notifyGroupServer(HP.code2.ANN7_PARTY_CREATE_ROOM_C_VALUE, room.getRoomId(), builder.build().toByteString());
				
				logCreateRoom(room,queueMemberTotal,queuePlayerCount,queueRobotCount);
			} catch (Exception e) {
				HawkException.catchException(e);
			}
		}
	}
	
	/**
	 * 生成普通机器人tick
	 */
	private void genRobotGuestTick() {
		long now = lastCheckTime, createRobotGuestTimeGap = Ann7Party384KVCfg.getInstance().getCreateRobotGuestTime();
		if (now - checkRobotGuestTime < createRobotGuestTimeGap) {
			return;
		}
		checkRobotGuestTime = now;
		if (getActivityEndTime() <= 0 || this.nearActivityEnd()) {
			return;
		}
		
		if (!Ann7Party384KVCfg.getInstance().checkTimeRange()) {
			return;
		}
		
		String key = robotLockKey() + ":guest";
		boolean succ = getRedisSession().setNx(key, thisServerId());
		if (!succ) {
			return;
		}
		
		String timeKey = key + ":time";
		try {
			String lastTime = getRedisSession().getString(timeKey);
			if (!HawkOSOperator.isEmptyString(lastTime) && now - Long.valueOf(lastTime) < createRobotGuestTimeGap) {
				return;
			}
			getRedisSession().setString(timeKey, String.valueOf(now), getOneHour()*24);
			genRobotGuest();
		} catch (Exception e) {
			HawkException.catchException(e);
		} finally {
			getRedisSession().del(key);
		}
	}
	
	private void genRobotGuest() {
		String robotCountStr = getRedisSession().getString(queueRobotKey()); //排队的机器人数量
		int queueRobotCount = HawkOSOperator.isEmptyString(robotCountStr) ? 0 : Integer.parseInt(robotCountStr);
		int count = getQueueMemberTotal() - queueRobotCount; //排队的真实玩家数
		Ann7Party384RobotCfg robotCfg = Ann7Party384RobotCfg.getConfigByGuestCondVal(count);
		if (robotCfg == null) {
			HawkLog.logPrintln("ann7party create robot break, serverGroup: {}, serverId: {}, queuePlayerCount: {}", groupId, thisServerId(), count);
			return;
		}
		
		int newCount = HawkRand.randInt(robotCfg.getRobotValueMin(), robotCfg.getRobotValueMax());
		newCount = Math.min(newCount, robotCfg.getNotCreateRobotLimit() - queueRobotCount);
		while (newCount > 0) {
			newCount--;
			try {
				Party384RoomMember robot = Party384RoomMember.createRobot();
				addToQueue(robot.getPlayerId());
				this.addOrUpdatePlayerInfo(robot);
				queueRobotCount += 1;
				HawkLog.logPrintln("ann7party create robot, serverGroup: {}, serverId: {}, guest: {}, queueRobotCount: {}", groupId, robot.getServerId(), robot.getPlayerId(), queueRobotCount);
			} catch (Exception e) {
				HawkException.catchException(e);
			}
		}
	}
	
	
	/**
	 * 判断玩家是否在房间内
	 * @param playerId
	 * @return
	 */
	public boolean playerInRoom(String playerId) {
		if (this.getPlayerRoom(playerId) == null) {
			kickout(playerId);
			return false;
		}
		return true;
	}

	public boolean isLocalPlayer(String playerId) {
		if (HawkOSOperator.isEmptyString(playerId)) {
			return false;
		}
		if (isCrossServerIn(playerId)) {
			return false;
		}
		return GlobalData.getInstance().getAccountInfoByPlayerId(playerId) != null;
	}
	
	public Ann7Party384Entity getDBEntity(String playerId) {
		if (!isLocalPlayer(playerId)) {
			return null;
		}
		Optional<ActivityBase> activity = ActivityManager.getInstance().getActivity(Activity.ActivityType.ANN7_PARTY_384_VALUE);
		return (Ann7Party384Entity) activity.get().getPlayerDataEntity(playerId).get();
	}
	
	public int getTermId() {
		Optional<ActivityBase> activity = ActivityManager.getInstance().getActivity(Activity.ActivityType.ANN7_PARTY_384_VALUE);
		if (activity.isPresent()) {
			return activity.get().getActivityTermId();
		}
		return 0;
	}
	
	public boolean isActivityOpening(String playerId) {
		Optional<ActivityBase> activity = ActivityManager.getInstance().getActivity(Activity.ActivityType.ANN7_PARTY_384_VALUE);
		return activity.isPresent() && activity.get().isOpening(playerId);
	}
	
	public long getActivityEndTime() {
		Optional<ActivityBase> activity = ActivityManager.getInstance().getActivity(Activity.ActivityType.ANN7_PARTY_384_VALUE);
		if (activity.isPresent()) {
			int termId = activity.get().getActivityTermId();
			return activity.get().getTimeControl().getEndTimeByTermId(termId);
		}
		
		return 0;
	}
	
	public boolean nearActivityEnd() {
		return getActivityEndTime() - HawkTime.getMillisecond() < Ann7Party384KVCfg.getInstance().getBuyItemLimit();
	}
	
	/**
	 * 给房主同步邀请的人
	 * @param room
	 * @param playerId
	 */
	public void syncRoomInvitePlayer(Party384Room room, int add, String playerId) {
		Player player = GlobalData.getInstance().getActivePlayer(room.getMasterPlayerId());
		if (player == null) {
			return;
		}
		RoomInvitePlayerInfo.Builder builder = RoomInvitePlayerInfo.newBuilder();
		if (add > 0) {
			builder.setAdd(1);
			Party384RoomMember member = getPlayerInfo(playerId);
			if (member != null) {
				PartyRoomInviteInfo.Builder inviteBuilder = PartyRoomInviteInfo.newBuilder();
				inviteBuilder.setInviteTime(room.getInvitePlayerTimeMap().get(playerId));
				inviteBuilder.setPlayerInfo(member.toBuilder());
				builder.addInvitePlayer(inviteBuilder);
			}
		} else {
			for (Entry<String, Long> entry : room.getInvitePlayerTimeMap().entrySet()) {
				String pid = entry.getKey();
				if (getPlayerRoom(pid) != null) {
					continue;
				}
				Party384RoomMember member = getPlayerInfo(pid);
				if (member != null) {
					PartyRoomInviteInfo.Builder inviteBuilder = PartyRoomInviteInfo.newBuilder();
					inviteBuilder.setInviteTime(entry.getValue());
					inviteBuilder.setPlayerInfo(member.toBuilder());
					builder.addInvitePlayer(inviteBuilder);
				}
			}
		}
		player.sendProtocol(HawkProtocol.valueOf(HP.code2.ANN7_PARTY_INVITE_PLAYER_INFO_S_VALUE, builder));
	}
	
	/**
	 * 给房主同步申请加入房间的人
	 * @param room
	 * @param add
	 * @param playerId
	 */
	public void syncRoomJoinApplyPlayer(Party384Room room, int add, String playerId) {
		Player player = GlobalData.getInstance().getActivePlayer(room.getMasterPlayerId());
		if (player == null) {
			return;
		}
		RoomJoinApplyInfo.Builder builder = RoomJoinApplyInfo.newBuilder();
		if (add > 0) {
			builder.setAdd(1);
			Party384RoomMember member = getPlayerInfo(playerId);
			if (member != null) {
				builder.addApplyPlayer(member.toBuilder());
			}
		} else {
			for (String pid : room.getJoinApplyPlayerQueue()) {
				if (getPlayerRoom(pid) != null) {
					continue;
				}
				Party384RoomMember member = getPlayerInfo(pid);
				if (member != null) {
					builder.addApplyPlayer(member.toBuilder());
				}
			}
		}
		player.sendProtocol(HawkProtocol.valueOf(HP.code2.ANN7_PARTY_APPLY_PLAYER_INFO_S_VALUE, builder));
	}
	
	/**
	 * 跨服搜索玩家
	 * @param serverId
	 * @param playerId
	 */
	public void searchPlayerCrossServer(String serverId, String playerId, String tarPlayerId) {
		PartyServiceReq.Builder csReq = PartyServiceReq.newBuilder();
		csReq.setReqType(CrossServerOper.SEARCH_ROLE_REQ);
		csReq.setRoomId(thisServerId() + ":" + playerId);
		PartyRoomMemberInfo.Builder builder = PartyRoomMemberInfo.newBuilder();
		builder.setPlayerId(tarPlayerId);
		csReq.setReqData(builder.build().toByteString());
		HawkProtocol hawkProtocol = HawkProtocol.valueOf(HP.code2.ANN7_PARTY_SERVICE_REQ_VALUE, csReq);
		CrossProxy.getInstance().sendNotify(hawkProtocol, serverId, null);
	}
	
	public void searchPlayerResp(String playerId, PartyRoomMemberInfo memberInfo) {
		Player player = GlobalData.getInstance().makesurePlayer(playerId);
		if (HawkOSOperator.isEmptyString(memberInfo.getPlayerId())) {
			int protocol = HP.code2.ANN7_PARTY_INVITE_SEARCH_C_VALUE;
			player.sendError(protocol, Status.Error.ANN7_PARTY_NOT_GROUP_PLAYER_VALUE, 0);
			HawkLog.errPrintln("anny party search player error: {}, playerId: {}", "not group player from cross resp", player.getId());
		} else {
			InviteSearchResp.Builder resp = InviteSearchResp.newBuilder();
			resp.setPlayerInfo(memberInfo);
			player.sendProtocol(HawkProtocol.valueOf(HP.code2.ANN7_PARTY_INVITE_SEARCH_S_VALUE, resp));
		}
	}
	
	/**
	 * 通知其它服务器
	 * @param serviceType
	 * @param roomId
	 * @param data
	 */
	private void notifyGroupServer(int serviceType, String roomId, ByteString data) {
		for (String toServerId : groupServer) { // 通知其它服A->B
			if (toServerId.equals(thisServerId())) {
				continue;
			}
			PartyServiceReq.Builder csReq = PartyServiceReq.newBuilder();
			csReq.setReqType(serviceType);
			csReq.setRoomId(roomId);
			if (data != null) {
				csReq.setReqData(data);
			}
			HawkProtocol hawkProtocol = HawkProtocol.valueOf(HP.code2.ANN7_PARTY_SERVICE_REQ_VALUE, csReq);
			CrossProxy.getInstance().sendNotify(hawkProtocol, toServerId, null);
		}
	}

	/**B 接收*/
	@ProtocolHandler(code = HP.code2.ANN7_PARTY_SERVICE_REQ_VALUE)
	public void onServerReq(HawkProtocol hawkProtocol) {
		PartyServiceReq req = hawkProtocol.parseProtocol(PartyServiceReq.getDefaultInstance());
		Ann7PartyCrossProcessor processor = crossProcessorMap.get(req.getReqType());
		if (processor != null) {
			ProxyHeader header = hawkProtocol.getUserData();
			HawkLog.logPrintln("ann7party accept req from server: {}, type: {}", header.getFrom(), req.getReqType());
			try {
				processor.process(req);
			} catch (Exception e) {
				HawkException.catchException(e);
			}
		} else {
			HawkLog.errPrintln("ann7party miss processor of type: {}", req.getReqType());
		}
	}
	
	public void onActivityEnd() {
		try {
			String key = queueListKey();
			List<String> queueMembers = getRedisSession().lRange(key, 0, -1, 0);
			for (String playerId : queueMembers) {
				if (isLocalPlayer(playerId)) {
					leaveQueue(playerId);
				}
			}
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	private String thisServerId() {
		return GsConfig.getInstance().getServerId();
	}
	
	private int getOneHour() {
		return 3600;
	}
	
	private int getRedisExpire() {
		long leaveTime = getActivityEndTime() - HawkApp.getInstance().getCurrentTime();
		if (leaveTime > 0) {
			return (int) (leaveTime / 1000) + 3600;
		}
		return 3600 * 24 * 15;
	}
	
	private HawkRedisSession getRedisSession() {
		return RedisProxy.getInstance().getRedisSession();
	}
	
	/** 服务器分组key */
	private String serverGroupLockKey() {
		return Ann7PartyConst.GROUP_LOCK_KEY + getTermId();
	}
	
	/** 房间信息key */
	private String roomRedisKey() {
		return Ann7PartyConst.PARTY_ROOM_KEY + getTermId() + ":" + groupId;
	}
	
	/** 房间聚会记录key */
	private String roomRecordKey() {
		return Ann7PartyConst.PARTY_RECORD_KEY + getTermId() + ":" + groupId;
	}
	
	/** 排队信息key */
	public String queueListKey() {
		return Ann7PartyConst.PARTY_QUEUE_KEY + getTermId() + ":" + groupId;
	}
	
	/** 排队信息key */
	public String queueRankKey() {
		return Ann7PartyConst.PARTY_QUEUE_RANK_KEY + getTermId() + ":" + groupId;
	}
	
	/** 排队机器人数量 */
	public String queueRobotKey() {
		return Ann7PartyConst.PARTY_QUEUE_ROBOT_KEY + getTermId() + ":" + groupId;
	}
	
	/** 抽奖key */
	public String awardLockKey(String roomId, int round) {
		return Ann7PartyConst.PARTY_AWARD_LOCK_KEY + roomId + ":" + round;
	}
	
	/** robot锁 + 时间 */
	public String robotLockKey() {
		return Ann7PartyConst.CHECK_ROBOT_KEY + getTermId() + ":" + groupId;
	}
	
	/** (跨服)玩家信息key */
	public String playerInfoKey() {
		return Ann7PartyConst.PLAYER_INFO_KEY + getTermId() + ":" + groupId;
	}
	
	/** 拒绝邀请时间key */
	public String refuseInviteKey(String roomId, String playerId) {
		return Ann7PartyConst.INVITE_REFUSE_KEY + ":" + roomId + ":" + playerId;
	}
	
	
	private void fillRobotLogParam(Map<String, Object> param, String playerId) {
		param.put("gameAppId", "");
        param.put("platId", 0);
        param.put("iZoneAreaId", 0);  
        param.put("puid", "");         
        param.put("playerId", playerId);               
        param.put("playerLevel", 0);
        param.put("vipLevel", 0);         
        param.put("cityLevel", 0); 
	}
	
	/**
	 * 开房间 
	 * @param room
	 */
	public void logCreateRoom(Party384Room room,int queueCnt,int queuePlayerCnt,int queueRobotCnt) {
		try {
			Map<String, Object> param = new HashMap<>();
	        param.put("roomId", room.getRoomId());               //房间id
	        param.put("robot", room.getRoomMaster().getRobot()); //是否是机器人房间：1是0否
	        param.put("autoStart", room.getAutoStart());         //是否自动开启聚会：1是0否
	        param.put("joinApply", room.getJoinApply());         //加入房间是否需要申请：1是0否
	        param.put("queueCnt", queueCnt);       
	        param.put("queuePlayerCnt", queuePlayerCnt);       
	        param.put("queueRobotCnt", queueRobotCnt);       
	        if (room.isRobotRoom()) {
	        	fillRobotLogParam(param, room.getMasterPlayerId());
	        	LogUtil.logActivityCommon(LogInfoType.ann7_party_open_room, param);
	        } else {
	        	Player player = GlobalData.getInstance().makesurePlayer(room.getMasterPlayerId());
	        	LogUtil.logActivityCommon(player, LogInfoType.ann7_party_open_room, param);
	        }
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	/**
	 * 加入房间
	 * @param room
	 */
	public void logJoinRoom(Party384Room room, String playerId, int joinType) {
		if (playerId.startsWith(Ann7PartyConst.ROBOT_PREFIX)) {
			Party384RoomMember member = getPlayerInfo(playerId);
			if (member == null || !member.getServerId().equals(thisServerId())) {
				return;
			}
		} else if (!isLocalPlayer(playerId)) {
			return;
		}
		
		try {
			Map<String, Object> param = new HashMap<>();
	        param.put("roomId", room.getRoomId());               //房间id
	        param.put("roomServer", room.getMasterServerId());   //房主所在服id
	        param.put("robot", room.getRoomMaster().getRobot()); //是否是机器人房间：1是0否
	        param.put("joinType", joinType);                     //加入房间的方式
	        param.put("memebrCount", room.memberCount());        //成员数量
	        if (playerId.startsWith(Ann7PartyConst.ROBOT_PREFIX)) {
	 	        fillRobotLogParam(param, playerId);
	        	LogUtil.logActivityCommon(LogInfoType.ann7_party_join_room, param);
	        } else {
	        	Player player = GlobalData.getInstance().makesurePlayer(playerId);
	        	LogUtil.logActivityCommon(player, LogInfoType.ann7_party_join_room, param);
	        }
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	/**
	 * 踢出房客成员
	 * @param roomId
	 * @param tarPlayerId
	 */
	public void logKickoutMember(Party384Room room, String tarPlayerId, int outType) {
		try {
			Map<String, Object> param = new HashMap<>();
	        param.put("roomId", room.getRoomId());   //房间id
	        param.put("tarPlayerId", tarPlayerId);   //被提出的房客成员角色id
	        param.put("memebrCount", room.memberCount()); //成员数量
	        param.put("outType", outType);            //退出房间情形：1被房主踢出，0因自身跨服或进入副本而退出
	        if (room.isRobotRoom()) {
	        	fillRobotLogParam(param, room.getMasterPlayerId());       
	        	LogUtil.logActivityCommon(LogInfoType.ann7_party_kickout, param);
	        } else {
	        	Player player = GlobalData.getInstance().makesurePlayer(room.getMasterPlayerId());
	        	LogUtil.logActivityCommon(player, LogInfoType.ann7_party_kickout, param);
	        }
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	/**
	 * 房间解散打点
	 * @param room
	 * @param dissType
	 */
	public void logRoomDiss(Party384Room room, int dissType) {
		try {
			Map<String, Object> param = new HashMap<>();
	        param.put("roomId", room.getRoomId());           //房间id
	        param.put("masterId", room.getMasterPlayerId()); //房主角色id
	        param.put("dissType", dissType);                 //解散类型：1聚会结束解散，2超时未开启聚会解散，3房主开服或进入副本解散
	        LogUtil.logActivityCommon(LogInfoType.ann7_party_room_diss, param);
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	/**
	 * 开启聚会打点
	 * @param room
	 */
	public void logStartParty(Party384Room room) {
		try {
			Ann7Party384KVCfg cfg = Ann7Party384KVCfg.getInstance();
			Map<Integer, String> rewardMap = new HashMap<>();
			for (int round = 1; round <= cfg.getRewardRoundLimit(); round++) {
				StringJoiner sj = new StringJoiner(";");
				for(Entry<String, String> entry : room.getMemberRewardMap(round).entrySet()) {
					sj.add(entry.getKey() + ":" + entry.getValue());
				}
				for (String reward : room.getMasterRewardList(round)) {
					sj.add(reward);
				}
				rewardMap.put(round, sj.toString());
			}
			
			Map<String, Object> param = new HashMap<>();
	        param.put("roomId", room.getRoomId());               //房间id
	        param.put("autoStart", room.getAutoStart());         //是否自动开启聚会：1是0否
	        param.put("robot", room.getRoomMaster().getRobot()); //是否是机器人房间：1是0否
	        param.put("memebrCount", room.memberCount());        //成员数量
	        param.put("round1Reward", rewardMap.get(1));   //第一轮奖励
	        param.put("round2Reward", rewardMap.get(2));   //第二轮奖励
	        param.put("round3Reward", rewardMap.size() > 2 ? rewardMap.get(3) : "");   //第三轮奖励
	        if (room.isRobotRoom()) {
	        	fillRobotLogParam(param, room.getMasterPlayerId());       
	        	LogUtil.logActivityCommon(LogInfoType.ann7_party_start, param);
	        } else {
	        	Player player = GlobalData.getInstance().makesurePlayer(room.getMasterPlayerId());
	        	LogUtil.logActivityCommon(player, LogInfoType.ann7_party_start, param);
	        }
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	/**
	 * 商店购买打点
	 * @param player
	 * @param shopId
	 * @param count
	 */
	public void logShopBuy(Player player, int shopId, int count) {
		try {
			Map<String, Object> param = new HashMap<>();
	        param.put("shopId", shopId);   //商品id
	        param.put("count", count);     //购买数量
	        LogUtil.logActivityCommon(player, LogInfoType.ann7_party_shop_buy, param);
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
}
