package com.hawk.game.crossproxy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.LockSupport;

import org.hawk.config.HawkConfigManager;
import org.hawk.log.HawkLog;
import org.hawk.net.protocol.HawkProtocol;
import org.hawk.net.session.HawkSession;
import org.hawk.os.HawkException;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkTime;
import org.hawk.profiler.HawkSysProfiler;
import org.hawk.profiler.HawkProfilerAnalyzer;
import org.hawk.task.HawkTaskManager;
import org.hawk.thread.HawkTask;
import org.hawk.thread.HawkThreadPool;
import org.hawk.tickable.HawkTickable;
import org.hawk.uuid.HawkUUIDGenerator;
import org.hawk.xid.HawkXID;
import org.hawk.zmq.HawkZmq;
import org.hawk.zmq.HawkZmqManager;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.hawk.game.GsApp;
import com.hawk.game.GsConfig;
import com.hawk.game.config.ProxyNodeCfg;
import com.hawk.game.global.GlobalData;
import com.hawk.game.player.Player;
import com.hawk.game.protocol.SysProtocol.PlayerIdList;
import com.hawk.game.util.GsConst;

public class CrossProxy extends HawkTickable {
	private static final long HEART_BEAT_PERIOD_NANOS = TimeUnit.MILLISECONDS.toNanos(ProxyHelper.HEART_BEAT_PERIOD);
	private static final long MASTER_CHECK_PERIOD_NANOS = TimeUnit.MILLISECONDS.toNanos(ProxyHelper.MASTER_CHECK_PERIOD);
	private static final long BLOCKED_SEND_LOG_PERIOD_NANOS = TimeUnit.SECONDS.toNanos(10L);

	private static final class PendingProtocol {
		private final HawkProtocol protocol;
		private final long generation;

		private PendingProtocol(HawkProtocol protocol, long generation) {
			this.protocol = protocol;
			this.generation = generation;
		}
	}

	/**
	 * 跨服协议类型
	 * 
	 * @author hawk
	 *
	 */
	public static class ProtoType {
		public static final int PROTOCOL  = 0;
		
		public static final int NOTIFY  = 1;
		
		public static final int RPC_REQ = 2;
		public static final int RPC_REP = 3;
		
		public static final int BROADCAST = 4;
		
		public static final int HEART_BEAT = 99;
	}
	
	private void onSendProtocolFail(ProxyHeader header) {
		sendProtocolFailNum ++;
		if (header == null) {
			HawkLog.warnPrintln("csproxy flush send fail, header is null, queueSize: {}", protoSendQueue.size());
			return;
		}

		HawkLog.warnPrintln("csproxy flush send fail, type: {}, from: {}, to: {}, source: {}, target: {}, rpcid: {}, queueSize: {}",
				header.getType(), header.getFrom(), header.getTo(), header.getSource(), header.getTarget(), header.getRpcid(), protoSendQueue.size());

		if (!HawkOSOperator.isEmptyString(header.getRpcid())) {
			onRpcTimeout(header.getRpcid());
		}
	}

	/**
	 * 跨服通信对象
	 */
	private volatile HawkZmq activeZmq;
	private volatile CrossProxyHealth.State proxyState = CrossProxyHealth.State.DISCONNECTED;
	private volatile long connectionGeneration;
	private volatile String desiredProxyAddress = "";
	/**
	 * 当前所有连接的通信对象
	 */
	private List<HawkZmq> zmqList;
	private List<String> proxyAddresses;
	/**
	 * 节点心跳时间
	 */
	private long heartbeatTime;
	/**
	 * 主服的节点检测时间
	 */
	private long masterCheckTime;
	/**
	 * 保持活跃的心跳回复时间
	 */
	private long keepaliveTime;
	private long nextRecoveryTime;
	private int recoveryAttempt;
	private long recoveryJitterMillis;
	/**
	 * 代理头信息缓存对象
	 */
	private byte[] headerBytes;
	/**
	 * 发送队列
	 */
	private Queue<PendingProtocol> protoSendQueue;
	/**
	 * rpc请求存根信息
	 */
	private LoadingCache<String, CsRpcStub> rpcStubCache;
	/**
	 * rpc时间
	 */
	private Map<String, Long> stubTimeMap;
	
	/**
	 * 单例对象
	 */
	private static CrossProxy instance = null;
	/**
	 * 上一次的记录时间
	 */
	private long lastRecordTime;
	/**
	 * 发送协议数据量
	 */
	private int sendProtocolNum;
	/**
	 * 发送协议消耗时间
	 */
	private long sendProtocolCostTime;
	private int sendProtocolFailNum;
	private final AtomicLong lastBlockedSendLogTime = new AtomicLong();
	private final AtomicInteger suppressedBlockedSendLogs = new AtomicInteger();
	/**
	 * 接受协议数量
	 */
	private int receivedProtocolNum;
	/**
	 * 接受协议消耗时间.
	 */
	private long receivedProtocolCostTime;
	/**
	 * 获取实体对象
	 * 
	 * @return
	 */
	public static CrossProxy getInstance() {
		if (instance == null) {
			synchronized (CrossProxy.class) {
				if (instance == null) {
					instance = new CrossProxy();
				}
			}			
		}
		return instance;
	}

	/**
	 * 默认构造
	 * 
	 * @param xid
	 */
	private CrossProxy() {
		headerBytes = new byte[8192];
		
		protoSendQueue = new LinkedBlockingQueue<PendingProtocol>();
		
		stubTimeMap = new ConcurrentHashMap<String, Long>();
		
		rpcStubCache = CacheBuilder.newBuilder().recordStats().maximumSize(16384).initialCapacity(32).expireAfterAccess(60, TimeUnit.SECONDS)
				.build(new CacheLoader<String, CsRpcStub>() {
					@Override
					public CsRpcStub load(String rpcId) {
						return null;
					}
				});
		
		instance = this;
	}
	
	/**
	 * 初始化跨服通信
	 * 
	 * @return
	 */
	public boolean init() {
		if (zmqList != null) {
			return false;
		}
		
		int nodeCount = HawkConfigManager.getInstance().getConfigSize(ProxyNodeCfg.class);
		if (nodeCount <= 0) {
			HawkLog.warnPrintln("csproxy node miss");
			return false;
		}
		
		// 检测主服务器
		ProxyHelper.contendMasterServer(false);
		
		// 初始化上下文
		HawkZmqManager.getInstance().init(HawkZmq.HZMQ_CONTEXT_THREAD);
		
		int localNodeCount = 0;
		zmqList = new ArrayList<HawkZmq>(1);
		proxyAddresses = new ArrayList<String>(nodeCount);
		Set<String> uniqueAddresses = new LinkedHashSet<String>();
		for (int i = 0; i < nodeCount; i++) {
			ProxyNodeCfg nodeCfg = HawkConfigManager.getInstance().getConfigByIndex(ProxyNodeCfg.class, i);
			if (nodeCfg == null || !nodeCfg.getAreaId().equals(GsConfig.getInstance().getAreaId())) {
				continue;
			}
			
			localNodeCount++;
			if (!uniqueAddresses.add(nodeCfg.getAddr())) {
				HawkLog.warnPrintln("csproxy duplicate proxy address ignored, areaId: {}, address: {}",
						GsConfig.getInstance().getAreaId(), nodeCfg.getAddr());
				continue;
			}
			proxyAddresses.add(nodeCfg.getAddr());
			HawkLog.logPrintln("csproxy configured node, address: {}", nodeCfg.getAddr());
		}
		
		if (localNodeCount <= 0) {
			HawkLog.errPrintln("csproxy node this server miss, areaId: {}, configNodeCount: {}",
					GsConfig.getInstance().getAreaId(), nodeCount);
			throw new RuntimeException("cfg/cs/proxyNode.xml配置有误");
		}
		if (proxyAddresses.isEmpty()) {
			throw new RuntimeException("cfg/cs/proxyNode.xml未配置有效代理地址");
		}
		
		long currentTick = System.nanoTime();
		heartbeatTime = currentTick - HEART_BEAT_PERIOD_NANOS;
		masterCheckTime = currentTick - MASTER_CHECK_PERIOD_NANOS;
		recoveryJitterMillis = Math.abs((long) GsConfig.getInstance().getServerId().hashCode()) % 2000L;
		
		// 开启一个事件线程
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				csEventLoop();
			}
		});
		thread.setName("CSProxy");
		thread.setDaemon(true);
		thread.start();
		
		// 注册更新机制
		GsApp.getInstance().addTickable(this);
		
		return true;
	}
	
	/**
	 * 初始化通信对象
	 */
	private HawkZmq getActiveZmq() {
		return activeZmq;
	}

	private HawkZmq createProxyZmq(String address) {
		HawkZmq zmqObj = null;
		try {
			zmqObj = HawkZmqManager.getInstance().createZmq(HawkZmq.ZmqType.DEALER);
			if (zmqObj == null) {
				throw new IllegalStateException("create DEALER socket returned null");
			}
			zmqObj.checkCacheBuffer(1024 * 1024 * 2);

			// CSProxy ROUTER 未开启 ROUTER_HANDOVER；恢复连接始终使用唯一 identity，避免旧路由残留拒绝新连接。
			String identify = String.format("%s@%s", GsConfig.getInstance().getServerId(), HawkUUIDGenerator.genUUID());
			if (!zmqObj.setIdentity(identify.getBytes("UTF-8"))) {
				throw new IllegalStateException("set DEALER identity failed");
			}

			if (GsConfig.getInstance().getZmqSndHwm() > 0) {
				zmqObj.getSocket().setSndHWM(GsConfig.getInstance().getZmqSndHwm());
			}
			if (GsConfig.getInstance().getZmqRcvHwm() > 0) {
				zmqObj.getSocket().setRcvHWM(GsConfig.getInstance().getZmqRcvHwm());
			}

			if (!zmqObj.connect(address)) {
				throw new IllegalStateException("connect DEALER socket failed");
			}
			return zmqObj;
		} catch (Exception e) {
			HawkLog.errPrintln("csproxy create proxy failed, address: {}, reason: {}", address, e.getMessage());
			HawkException.catchException(e);
			if (zmqObj != null) {
				HawkZmqManager.getInstance().closeZmq(zmqObj);
			}
			return null;
		}
	}

	private boolean reconnectProxy(String address, String reason) {
		if (HawkOSOperator.isEmptyString(address) || !proxyAddresses.contains(address)) {
			HawkLog.warnPrintln("csproxy recovery target invalid, reason: {}, address: {}, configuredAddresses: {}",
					reason, address, proxyAddresses);
			return false;
		}

		HawkZmq oldZmq = activeZmq;
		long oldGeneration = connectionGeneration;
		proxyState = CrossProxyHealth.State.DISCONNECTED;
		connectionGeneration = oldGeneration + 1L;
		desiredProxyAddress = address;
		keepaliveTime = 0L;
		drainPendingProtocols("connection recovery: " + reason);
		failInFlightRpcs("connection recovery: " + reason);
		activeZmq = null;
		zmqList.clear();
		if (oldZmq != null) {
			HawkZmqManager.getInstance().closeZmq(oldZmq);
		}

		HawkZmq newZmq = createProxyZmq(address);
		long currentTick = System.nanoTime();
		long retryDelay = CrossProxyHealth.retryDelayMillis(recoveryAttempt, recoveryJitterMillis);
		nextRecoveryTime = currentTick + TimeUnit.MILLISECONDS.toNanos(retryDelay);
		recoveryAttempt++;
		if (newZmq == null) {
			HawkLog.warnPrintln("csproxy recovery create failed, reason: {}, address: {}, generation: {}, retryDelay: {}ms, attempt: {}",
					reason, address, connectionGeneration, retryDelay, recoveryAttempt);
			return false;
		}

		zmqList.add(newZmq);
		activeZmq = newZmq;
		proxyState = CrossProxyHealth.State.RECOVERING;
		HawkLog.warnPrintln("csproxy recovery socket created, reason: {}, address: {}, generation: {}, retryDelay: {}ms, attempt: {}",
				reason, address, connectionGeneration, retryDelay, recoveryAttempt);
		return true;
	}

	private void markConnectionBroken(HawkZmq brokenZmq, String reason) {
		if (brokenZmq == null || brokenZmq != activeZmq) {
			return;
		}

		String address = brokenZmq.getAddress();
		CrossProxyHealth.State oldState = proxyState;
		proxyState = CrossProxyHealth.State.DISCONNECTED;
		connectionGeneration++;
		desiredProxyAddress = address;
		keepaliveTime = 0L;
		drainPendingProtocols("connection broken: " + reason);
		failInFlightRpcs("connection broken: " + reason);
		activeZmq = null;
		zmqList.clear();
		HawkZmqManager.getInstance().closeZmq(brokenZmq);
		// 健康连接首次失败立即允许重建；连续建连/验活失败由 reconnectProxy 的退避控制。
		if (oldState == CrossProxyHealth.State.HEALTHY) {
			recoveryAttempt = 0;
			nextRecoveryTime = System.nanoTime();
		}
		HawkLog.errPrintln("csproxy connection marked broken, reason: {}, address: {}, oldState: {}, generation: {}, retryAttempt: {}",
				reason, address, oldState, connectionGeneration, recoveryAttempt);
	}

	private void drainPendingProtocols(String reason) {
		int dropped = 0;
		int rpcFailed = 0;
		PendingProtocol pending;
		while ((pending = protoSendQueue.poll()) != null) {
			dropped++;
			ProxyHeader header = pending.protocol.getUserData();
			if (header != null && !HawkOSOperator.isEmptyString(header.getRpcid())) {
				rpcFailed++;
				onRpcTimeout(header.getRpcid());
			}
		}
		if (dropped > 0) {
			sendProtocolFailNum += dropped;
			HawkLog.warnPrintln("csproxy pending queue drained, reason: {}, dropped: {}, rpcFailed: {}, generation: {}, state: {}",
					reason, dropped, rpcFailed, connectionGeneration, proxyState);
		}
	}

	private void failInFlightRpcs(String reason) {
		int failed = 0;
		for (String rpcId : new ArrayList<String>(stubTimeMap.keySet())) {
			if (onRpcTimeout(rpcId)) {
				failed++;
			}
		}
		if (failed > 0) {
			HawkLog.warnPrintln("csproxy in-flight rpc failed on connection loss, reason: {}, failed: {}, remaining: {}, generation: {}",
					reason, failed, stubTimeMap.size(), connectionGeneration);
		}
	}

	private String selectMasterRecoveryAddress(String currentAddress) {
		if (proxyAddresses == null || proxyAddresses.isEmpty()) {
			return "";
		}
		if (HawkOSOperator.isEmptyString(currentAddress) || proxyAddresses.size() == 1) {
			return proxyAddresses.get(0);
		}
		int currentIndex = proxyAddresses.indexOf(currentAddress);
		return proxyAddresses.get((currentIndex + 1 + proxyAddresses.size()) % proxyAddresses.size());
	}

	private void logBlockedSend(ProxyHeader header, HawkZmq csZmq, CrossProxyHealth.State state, long generation) {
		long currentTick = System.nanoTime();
		long lastLogTick = lastBlockedSendLogTime.get();
		if (currentTick - lastLogTick < BLOCKED_SEND_LOG_PERIOD_NANOS
				|| !lastBlockedSendLogTime.compareAndSet(lastLogTick, currentTick)) {
			suppressedBlockedSendLogs.incrementAndGet();
			return;
		}
		int suppressed = suppressedBlockedSendLogs.getAndSet(0);
		HawkLog.warnPrintln("csproxy send blocked, state: {}, generation: {}, activeAddr: {}, type: {}, from: {}, to: {}, source: {}, target: {}, rpcid: {}, configuredNodeCount: {}, suppressedSinceLastLog: {}",
				state, generation, csZmq == null ? "" : csZmq.getAddress(),
				header.getType(), header.getFrom(), header.getTo(), header.getSource(), header.getTarget(), header.getRpcid(),
				proxyAddresses == null ? 0 : proxyAddresses.size(), suppressed);
	}

	/**
	 * 发送通知
	 * 
	 * @param protocol
	 * @param serverId
	 * @param targetPlayer
	 * @return
	 */
	public boolean sendNotify(HawkProtocol protocol, String serverId, String targetPlayer) {
		return  sendNotify(protocol, serverId, null, targetPlayer);
	}
	
	/**
	 * 发送通知
	 * 
	 * @param protocol
	 * @param targetSid
	 * @param targetXid
	 */
	public boolean sendNotify(HawkProtocol protocol, String serverId, String sourcePlayer, String targetPlayer) {
		return sendProtocol(protocol, serverId, sourcePlayer, targetPlayer, ProtoType.NOTIFY);
	}
	
	/**
	 * 发送协议
	 * 
	 * @param protocol
	 * @param serverId
	 * @param targetPlayer
	 * @return
	 */
	public boolean sendProtocol(HawkProtocol protocol, String serverId, String targetPlayer) {
		return sendProtocol(protocol, serverId, null, targetPlayer, ProtoType.PROTOCOL);
	}
	
	/**
	 * 发送协议
	 * 
	 * @param protocol
	 * @param serverId
	 * @param targetPlayer
	 * @param protoType
	 * @return
	 */
	public boolean sendProtocol(HawkProtocol protocol, String serverId, String sourcePlayer, String targetPlayer, int protoType) {
		ProxyHeader header = new ProxyHeader();
		header.setType(protoType);
		header.setFrom(GsConfig.getInstance().getServerId());
		header.setTo(serverId);
		header.setSource(sourcePlayer);
		header.setTarget(targetPlayer);
		
		return sendProxyProtocol(header, protocol);
	}
	
	/**
	 * 跨服协议广播
	 */
	public boolean broadcastProtocolV2(String serverId, Set<String> playerIds, HawkProtocol protocol) {
		serverId = GlobalData.getInstance().getMainServerId(serverId);
		return broadcastProtocol(serverId, playerIds, protocol);
	}
	
	/**
	 * 协议广播
	 * 没有做强制主服转换, 建议使用 {@link #broadcastProtocolV2()}
	 * @param serverId(这个必须是转换之后的所在物理服务器的id)
	 * @param playerIds
	 * @param protocol
	 * @return
	 */
	@Deprecated
	public boolean broadcastProtocol(String serverId, Set<String> playerIds, HawkProtocol protocol) {
		try {
			if (playerIds == null || playerIds.isEmpty()) {
				return true;
			}
			// serverId = GlobalData.getInstance().getMainServerId(serverId);
			
			// 自己给自己服进行广播
			if (serverId.equals(GsConfig.getInstance().getServerId())) {
				throw new RuntimeException("cannot broadcast to local server");
			}
			
			ProxyHeader header = new ProxyHeader();
			header.setType(ProtoType.BROADCAST);
			header.setFrom(GsConfig.getInstance().getServerId());
			header.setTo(serverId);
			header.getBroadcastIds().addAll(playerIds);
			
			return sendProxyProtocol(header, protocol);
		} catch (Exception e) {
			HawkException.catchException(e);
		}
		
		return false;
	}
	
	/**
	 * 发送心跳
	 * 
	 * @return
	 */
	private boolean sendHeartbeat() {
		// 判断连接状态
		HawkZmq csZmq = getActiveZmq();
		if (csZmq == null) {
			return false;
		}
		try {
			ProxyHeader header = new ProxyHeader();
			header.setType(ProtoType.HEART_BEAT);
			header.setFrom(GsConfig.getInstance().getServerId());
			header.setSource(new String(csZmq.getSocket().getIdentity(), "UTF-8"));
			header.setTimestamp(HawkTime.getMillisecond());
			boolean result = csZmq.send(header.pack().getBytes("UTF-8"), HawkZmq.HZMQ_NOBLOCK);
			if (!result) {
				markConnectionBroken(csZmq, "heartbeat send failed");
			}
			return result;
		} catch (Exception e) {
			HawkException.catchException(e);
			markConnectionBroken(csZmq, "heartbeat exception: " + e.getClass().getSimpleName());
		}
		
		return false;
		
	}
	
	/**
	 * 向指定服rpc请求
	 * 
	 * @param protocol
	 * @param callback
	 * @param serverId
	 * @param targetXid
	 */
	public boolean rpcRequest(HawkProtocol protocol, CsRpcCallback callback, String serverId, String sourcePlayer, String targetPlayer) {
		ProxyHeader header = new ProxyHeader();
		header.setType(ProtoType.NOTIFY);
		header.setFrom(GsConfig.getInstance().getServerId());
		header.setTo(serverId);
		header.setSource(sourcePlayer);
		header.setTarget(targetPlayer);
		header.setRpcid(HawkOSOperator.randomUUID());
		
		// 注册rpc
		int threadIdx = HawkTaskManager.getInstance().getTaskExecutor().getThreadIndex(HawkOSOperator.getThreadId());
		CsRpcStub stub = new CsRpcStub(header, threadIdx, callback);
		rpcStubCache.put(header.getRpcid(), stub);
		stubTimeMap.put(header.getRpcid(), HawkTime.getMillisecond());
		
		if (!sendProxyProtocol(header, protocol)) {
			// 原子认领本次失败；若恢复线程已经清队列并回调，这里不会重复回调。
			onRpcTimeout(header.getRpcid());
			return false;
		}
		return true;
	}
	
	/**
	 * 响应rpc请求
	 * 
	 * @param header
	 * @param protocol
	 */
	public boolean rpcResponse(ProxyHeader header, HawkProtocol protocol) {
		if (header == null) {
			return false;
		}
		
		ProxyHeader respHeader = new ProxyHeader();
		respHeader.setType(ProtoType.RPC_REP);
		respHeader.setFrom(GsConfig.getInstance().getServerId());
		respHeader.setTo(header.getFrom());
		respHeader.setSource(header.getTarget());
		respHeader.setRpcid(header.getRpcid());
		respHeader.setTarget(header.getSource());
		
		return sendProxyProtocol(respHeader, protocol);
	}
	
	/**
	 * 发送跨服代理协议
	 * 
	 * @param header
	 * @param protocol
	 * @return
	 */
	private boolean sendProxyProtocol(ProxyHeader header, HawkProtocol protocol) {
		try {
			CrossProxyHealth.State state = proxyState;
			long generation = connectionGeneration;
			HawkZmq csZmq = getActiveZmq();
			if (csZmq == null || !CrossProxyHealth.canSendBusiness(state)) {
				sendProtocolFailNum ++;
				logBlockedSend(header, csZmq, state, generation);
				return false;
			}
			
			// 添加到协议发送队列
			header.setTimestamp(HawkTime.getMillisecond());
			protocol.setUserData(header);
			PendingProtocol pending = new PendingProtocol(protocol, generation);
			protoSendQueue.add(pending);
			// 状态切换和业务线程入队可能并发；变化后撤销本次入队，防止恢复后重放旧请求。
			if (proxyState != CrossProxyHealth.State.HEALTHY || connectionGeneration != generation || activeZmq != csZmq) {
				protoSendQueue.remove(pending);
				return false;
			}
			return true;
		} catch (Exception e) {
			HawkException.catchException(e);
		}
		
		return false;
	}
	
	/**
	 * 事件处理主循环
	 */
	private void csEventLoop() {
		while (true) {
			try {
				// 监视器事件
				updateProxyState();
				
				// 发送队列中的协议数据
				flushProtoQueue();
				
				// 代理服务器事件
				updateProxyEvent();
				
				//输出性能数据.
				recordMonitor();
			} catch (Exception e) {
				HawkException.catchException(e);
			}
			if (getActiveZmq() == null) {
				LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(10L));
			}
		}
	}
	
	private void recordMonitor() {
		try {
			long currentTime = HawkTime.getMillisecond();
			if (currentTime - lastRecordTime >= 10000) {
				String masterServer = "";
				String proxyAddr = "";
				try {
					masterServer = ProxyHelper.getMasterServer();
					proxyAddr = ProxyHelper.getProxyAddress();
				} catch (Exception e) {
					HawkException.catchException(e);
				}

				HawkZmq currentZmq = getActiveZmq();
				String activeAddr = currentZmq == null ? "" : currentZmq.getAddress();
				int localNodeCount = proxyAddresses == null ? 0 : proxyAddresses.size();
				boolean addressMatched = false;
				if (!HawkOSOperator.isEmptyString(proxyAddr) && proxyAddresses != null) {
					addressMatched = proxyAddresses.contains(proxyAddr);
				}
				long keepaliveAge = keepaliveTime > 0 ? TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - keepaliveTime) : -1;

				//时间为毫秒
				HawkLog.logPrintln("crossProxy ten seconds sendProtocolNum: {}, sendProtocolCostTime: {}ms, sendProtocolFailNum: {}, sendQueueSize: {}, receivedProtocolNum: {}, receiveProtocolCostTime: {}ms, state: {}, generation: {}, activeAddr: {}, desiredAddr: {}, masterServer: {}, proxyAddr: {}, configuredNodeCount: {}, addressMatched: {}, keepaliveAge: {}ms, recoveryAttempt: {}",
						sendProtocolNum, sendProtocolCostTime / 1000000, sendProtocolFailNum, protoSendQueue.size(), receivedProtocolNum, receivedProtocolCostTime / 1000000,
						proxyState, connectionGeneration, activeAddr, desiredProxyAddress, masterServer, proxyAddr, localNodeCount, addressMatched, keepaliveAge, recoveryAttempt);
				
				sendProtocolNum = 0;
				sendProtocolFailNum = 0;
				sendProtocolCostTime = 0;
				receivedProtocolCostTime = 0;
				receivedProtocolNum = 0;
				lastRecordTime = currentTime;
			}
		} catch (Exception e) {
			HawkException.catchException(e);
		}		
		
	}

	/**
	 * 发送心跳
	 */
	private void updateProxyState() {
		long currentTick = System.nanoTime();
		if (currentTick - heartbeatTime >= HEART_BEAT_PERIOD_NANOS) {
			heartbeatTime = currentTick;
			ProxyHelper.contendMasterServer(true);
			boolean masterServer = ProxyHelper.isMasterServer();
			String targetAddress = masterServer ? desiredProxyAddress : ProxyHelper.getProxyAddress();
			if (masterServer && (HawkOSOperator.isEmptyString(targetAddress) || !proxyAddresses.contains(targetAddress))) {
				targetAddress = selectMasterRecoveryAddress("");
			}

			HawkZmq currentZmq = getActiveZmq();
			if (!masterServer && (HawkOSOperator.isEmptyString(targetAddress) || !proxyAddresses.contains(targetAddress))) {
				if (currentZmq != null) {
					markConnectionBroken(currentZmq, "follower redis target missing or not configured");
				}
				desiredProxyAddress = targetAddress;
				if (currentTick >= nextRecoveryTime) {
					long retryDelay = CrossProxyHealth.retryDelayMillis(recoveryAttempt, recoveryJitterMillis);
					nextRecoveryTime = currentTick + TimeUnit.MILLISECONDS.toNanos(retryDelay);
					recoveryAttempt++;
					HawkLog.warnPrintln("csproxy follower target rejected, redisAddress: {}, configuredAddresses: {}, generation: {}, retryDelay: {}ms, attempt: {}",
							targetAddress, proxyAddresses, connectionGeneration, retryDelay, recoveryAttempt);
				}
				return;
			}
			boolean desiredChanged = !targetAddress.equals(desiredProxyAddress);
			boolean targetChanged = currentZmq != null && !currentZmq.getAddress().equals(targetAddress);
			if (!HawkOSOperator.isEmptyString(targetAddress) && (targetChanged || desiredChanged
					|| (currentZmq == null && currentTick >= nextRecoveryTime))) {
				reconnectProxy(targetAddress, masterServer ? "master select target" : "follow redis target");
				currentZmq = getActiveZmq();
			}
			if (currentZmq != null) {
				sendHeartbeat();
			}
		}

		// RECOVERING/DISCONNECTED 的退避到期后立即重试，不受 10 秒健康检测采样边界影响。
		if (proxyState != CrossProxyHealth.State.HEALTHY && nextRecoveryTime > 0L && currentTick >= nextRecoveryTime) {
			boolean masterServer = ProxyHelper.isMasterServer();
			String targetAddress = masterServer
					? selectMasterRecoveryAddress(desiredProxyAddress)
					: ProxyHelper.getProxyAddress();
			if (!HawkOSOperator.isEmptyString(targetAddress)) {
				reconnectProxy(targetAddress, "recovery backoff elapsed, master=" + masterServer);
				if (getActiveZmq() != null) {
					sendHeartbeat();
				}
			} else {
				long retryDelay = CrossProxyHealth.retryDelayMillis(recoveryAttempt, recoveryJitterMillis);
				nextRecoveryTime = currentTick + TimeUnit.MILLISECONDS.toNanos(retryDelay);
				recoveryAttempt++;
				HawkLog.warnPrintln("csproxy recovery target unavailable, master: {}, state: {}, generation: {}, retryDelay: {}ms, attempt: {}",
						masterServer, proxyState, connectionGeneration, retryDelay, recoveryAttempt);
			}
		}

		if (currentTick - masterCheckTime >= MASTER_CHECK_PERIOD_NANOS) {
			masterCheckTime = currentTick;
			if (proxyState == CrossProxyHealth.State.HEALTHY
					&& CrossProxyHealth.shouldRecover(currentTick, keepaliveTime, MASTER_CHECK_PERIOD_NANOS)
					&& currentTick >= nextRecoveryTime) {
				HawkZmq currentZmq = getActiveZmq();
				String currentAddress = currentZmq == null ? desiredProxyAddress : currentZmq.getAddress();
				// 先关业务门禁，再访问 Redis；即使 Redis 超时/异常也不能继续向已失活 socket 发业务。
				if (currentZmq != null) {
					markConnectionBroken(currentZmq, "heartbeat timeout");
				} else {
					proxyState = CrossProxyHealth.State.DISCONNECTED;
					connectionGeneration++;
					nextRecoveryTime = currentTick;
				}
				boolean masterServer = ProxyHelper.isMasterServer();
				String targetAddress = masterServer
						? selectMasterRecoveryAddress(currentAddress)
						: ProxyHelper.getProxyAddress();
				if (!HawkOSOperator.isEmptyString(targetAddress)) {
					if (reconnectProxy(targetAddress, "heartbeat timeout, master=" + masterServer)) {
						sendHeartbeat();
					}
				} else {
					HawkLog.warnPrintln("csproxy recovery waiting for target, master: {}, state: {}, generation: {}, desiredAddr: {}",
							masterServer, proxyState, connectionGeneration, desiredProxyAddress);
				}
			}
		}
	}

	/**
	 * 响应的心跳通知
	 */
	private void onHeartbeat(HawkZmq csZmq, long observedGeneration) {
		if (csZmq == null || csZmq != getActiveZmq() || observedGeneration != connectionGeneration) {
			HawkLog.warnPrintln("csproxy stale heartbeat ignored, observedGeneration: {}, currentGeneration: {}, address: {}",
					observedGeneration, connectionGeneration, csZmq == null ? "" : csZmq.getAddress());
			return;
		}
		CrossProxyHealth.State oldState = proxyState;
		int connectionRole = ProxyHelper.PROXY_CONNECTION_REJECTED;
		String publishedAddress = "";
		connectionRole = ProxyHelper.validateProxyConnection(csZmq.getAddress());

		if (connectionRole == ProxyHelper.PROXY_CONNECTION_REJECTED) {
			if (HawkOSOperator.isEmptyString(publishedAddress)) {
				try {
					publishedAddress = ProxyHelper.getProxyAddress();
				} catch (Exception e) {
					HawkException.catchException(e);
				}
			}
			HawkLog.warnPrintln("csproxy heartbeat rejected by atomic role/address check, address: {}, redisAddress: {}, role: {}, generation: {}, state: {}",
					csZmq.getAddress(), publishedAddress, connectionRole, connectionGeneration, oldState);
			markConnectionBroken(csZmq, "heartbeat role/address check failed");
			if (!HawkOSOperator.isEmptyString(publishedAddress) && proxyAddresses.contains(publishedAddress)) {
				desiredProxyAddress = publishedAddress;
				nextRecoveryTime = System.nanoTime();
			}
			return;
		}

		keepaliveTime = System.nanoTime();
		proxyState = CrossProxyHealth.State.HEALTHY;
		recoveryAttempt = 0;
		nextRecoveryTime = 0L;
		
		String indentify = "";
		byte[] identityBytes = csZmq.getSocket().getIdentity();
		if (identityBytes != null) {
			indentify = new String(identityBytes, java.nio.charset.StandardCharsets.UTF_8);
		}
		HawkLog.logPrintln("csproxy heartbeat validated, address: {}, identify: {}, generation: {}, oldState: {}, master: {}",
				csZmq.getAddress(), indentify, connectionGeneration, oldState,
				connectionRole == ProxyHelper.PROXY_CONNECTION_MASTER);
	}
	
	/**
	 * 发送所有队列协议
	 */
	private void flushProtoQueue() {
		// 判断连接状态
		HawkZmq csZmq = getActiveZmq();
		long generation = connectionGeneration;
		if (csZmq == null || !CrossProxyHealth.canSendBusiness(proxyState)) {
			return;
		}
		long nanoStartTime = System.nanoTime();
		int oldSendProtocolNum = sendProtocolNum;
		while (protoSendQueue.size() > 0) {
			try {
				PendingProtocol pending = protoSendQueue.poll();
				if (pending == null) {
					continue;
				}
				HawkProtocol protocol = pending.protocol;
				
				ProxyHeader header = protocol.getUserData();
				if (pending.generation != generation || csZmq != activeZmq
						|| generation != connectionGeneration || !CrossProxyHealth.canSendBusiness(proxyState)) {
					onSendProtocolFail(header);
					continue;
				}
				boolean sendResult = true;
				
				if (header.getType() == ProtoType.BROADCAST) {
					// 构建广播对象列表协议
					if (header.getBroadcastIds().size() > 0) {
						PlayerIdList.Builder idList = PlayerIdList.newBuilder();
						idList.addAllPlayerId(header.getBroadcastIds());
						
						sendResult = csZmq.send(header.pack().getBytes(), HawkZmq.HZMQ_NOBLOCK | HawkZmq.HZMQ_SNDMORE);
						if (sendResult) {
							sendResult = csZmq.sendProtocol(protocol, HawkZmq.HZMQ_NOBLOCK | HawkZmq.HZMQ_SNDMORE);
						}
						if (sendResult) {
							sendResult = csZmq.sendProtocol(HawkProtocol.valueOf(0, idList), HawkZmq.HZMQ_NOBLOCK);
						}
					}
				} else {
					sendResult = csZmq.send(header.pack().getBytes(), HawkZmq.HZMQ_NOBLOCK | HawkZmq.HZMQ_SNDMORE);
					if (sendResult) {
						sendResult = csZmq.sendProtocol(protocol, HawkZmq.HZMQ_NOBLOCK);
					}
				}
				
				if (!sendResult) {
					onSendProtocolFail(header);
					// 任意 multipart 分帧失败后，旧 socket 的帧边界不再可信，禁止继续发送下一条消息。
					markConnectionBroken(csZmq, "multipart send failed, type=" + header.getType());
					return;
				}

				sendProtocolNum ++;
				
				// 统计信息
				HawkProfilerAnalyzer.getInstance().addSendProtocolInfo(protocol.getSize() + HawkProtocol.HEADER_SIZE);
				HawkSysProfiler.getInstance().incSendProtoTask(protocol.getType());
				
			} catch (Exception e) {
				HawkException.catchException(e);
				markConnectionBroken(csZmq, "flush exception: " + e.getClass().getSimpleName());
				return;
			}
		}
		
		if (oldSendProtocolNum != sendProtocolNum) {
			long nanoEndStartTime = System.nanoTime();
			long costtime = nanoEndStartTime - nanoStartTime;
			sendProtocolCostTime += costtime;
			//大于100ms记录下来
			if (costtime / 1000000 >= 100) {
				HawkLog.warnPrintln("csproxy tick flushProtoQueue costtime: {}, protoCount: {}", costtime / 1000000, sendProtocolNum - oldSendProtocolNum);
			}
		}		
	}
	
	/**
	 * 运行跨服事件
	 */
	private boolean updateProxyEvent() {
		try {
			// 判断连接状态
			HawkZmq csZmq = getActiveZmq();
			if (csZmq == null) {
				return false;
			}
			long observedGeneration = connectionGeneration;
			
			// 检测事件
			int event = csZmq.pollEvent(HawkZmq.HZMQ_EVENT_READ, 50);
			if (event <= 0) {
				return false;
			}
			
			long nanoStartTime = System.nanoTime();
			int oldReceiveProtocolNum = receivedProtocolNum;
			// 取出所有协议
			while (true) {
				// 接收协议头信息
				int headSize = csZmq.recv(headerBytes, HawkZmq.HZMQ_DONTWAIT);
				if (headSize <= 0) {
					break;
				}
				
				// 解析协议头
				ProxyHeader header = new ProxyHeader();
				header.unpack(new String(headerBytes, 0, headSize, "UTF-8"));
				
				// 心跳处理
				if (header.getType() == ProtoType.HEART_BEAT) {
					receivedProtocolNum++;
					onHeartbeat(csZmq, observedGeneration);
					if (csZmq != activeZmq || observedGeneration != connectionGeneration) {
						return true;
					}
					continue;
				}
				
				// 接收协议体
				if (!csZmq.hasReceiveMore()) {
					continue;
				}
				
				// 接收协议体
				HawkProtocol protocol = csZmq.recvProtocol(HawkZmq.HZMQ_DONTWAIT);
				
				// 广播模式下, 还有参数待接收
				HawkProtocol idsProto = null;
				if (csZmq.hasReceiveMore()) {
					idsProto = csZmq.recvProtocol(HawkZmq.HZMQ_DONTWAIT);
				}
				
				// 头标记信息
				if (!header.isValid()) {
					HawkLog.errPrintln("csproxy header error: {}", header.getOri());
					continue;
				}
				
				// 协议正确性
				if (protocol == null) {
					HawkLog.errPrintln("csproxy decode protocol fail header:{}", header.toString());					
					continue;
				}

				if (csZmq != activeZmq || observedGeneration != connectionGeneration
						|| !CrossProxyHealth.canSendBusiness(proxyState)) {
					HawkLog.warnPrintln("csproxy business response dropped before heartbeat validation, type: {}, rpcid: {}, observedGeneration: {}, currentGeneration: {}, state: {}",
							header.getType(), header.getRpcid(), observedGeneration, connectionGeneration, proxyState);
					continue;
				}			

				// 超时的协议处理
				long gap = HawkTime.getMillisecond() - header.getTimestamp();
				if (gap > 2000L) {
					HawkLog.errPrintln("csproxy header timeout: {}, protocol: {}, gap: {}", header.getOri(), protocol.getType(), gap);
					HawkProfilerAnalyzer.getInstance().addMsgHandleInfo("csproxy-header-timeout", gap);
					if (gap > ProxyHelper.PROTOCOL_EXPIRE) {
						// RPC 回包和带 rpcid 的 RPC 请求不丢弃，由发起方的 RPC 超时机制兜底
						// 丢弃 RPC 回包会导致发起方 stub 无法回调，玩家只能等 RPC 超时才收到失败结果
						if (header.getType() == ProtoType.RPC_REP || !HawkOSOperator.isEmptyString(header.getRpcid())) {
							HawkLog.warnPrintln("csproxy deliver expired rpc protocol, type: {}, from: {}, rpcid: {}, gap: {}", 
									header.getType(), header.getFrom(), header.getRpcid(), gap);
						} else {
							continue;
						}
					}
				}
				
				// 协议处理
				protocol.setUserData(header);
				
				if (header.getType() == ProtoType.BROADCAST) {
					onBroadcast(header, protocol, idsProto);
				} else {
					onProtocol(header, protocol);
				}
				
				receivedProtocolNum++;
			}
			
			if (oldReceiveProtocolNum != receivedProtocolNum) {
				long nanoEndTime = System.nanoTime();
				long costtime = nanoEndTime - nanoStartTime;
				receivedProtocolCostTime += costtime;
				//大于100ms记录下来
				if (costtime / 1000000 >= 100) {
					HawkLog.warnPrintln("csproxy tick recieve proto costtime: {}, protoCount: {}", costtime / 1000000, receivedProtocolNum - oldReceiveProtocolNum);
				}
			}			
			
			return true;
		} catch (Exception e) {
			HawkException.catchException(e);
		}
		return false;
	}
	
	/**
	 * 协议处理
	 * 
	 * @param header
	 * @param protocol
	 * @return
	 */
	private boolean onProtocol(ProxyHeader header, HawkProtocol protocol) {
		try {
			// 协议处理
			if (header.getType() == ProtoType.PROTOCOL) {
				transportProtocol(header, protocol);
				return true;
			}
			
			// 通知处理
			if (header.getType() == ProtoType.NOTIFY) {
				dispatchProtocol(header, protocol);
			}
			
			// rpc处理
				if (header.getType() == ProtoType.RPC_REP) {
					CsRpcStub stub = rpcStubCache.getIfPresent(header.getRpcid());
					if (stub != null && CrossProxyHealth.tryClaimRpcCompletion(stubTimeMap, header.getRpcid())) {
						rpcStubCache.invalidate(header.getRpcid());
					
					HawkTaskManager.getInstance().postTask(new HawkTask() {
						@Override
						public Object run() {
							stub.getCallback().invoke(protocol);
							return null;
						}
					}, stub.getThreadIdx());
					return true;
				}
			}
		} catch (Exception e) {
			HawkException.catchException(e);
		} 
		
		return false;
	}
	
	
	/**
	 * 广播协议
	 * 
	 * @param header
	 * @param protocol
	 * @param idsProto
	 */
	private void onBroadcast(ProxyHeader header, HawkProtocol protocol, HawkProtocol idsProto) {
		if (header == null || protocol == null || header.getType() != ProtoType.BROADCAST || idsProto == null) {
			return;
		}
		
		try {
			HawkTask broadcastTask = new HawkTask() {			
				@Override
				public Object run() {
					// 构建广播任务
					PlayerIdList idList = idsProto.parseProtocol(PlayerIdList.getDefaultInstance());
					if (idList == null || idList.getPlayerIdCount() <= 0) {
						return null;
					}
					
					int count = idList.getPlayerIdCount();
					for (int i = 0; i < count; i++) {
						try {
							Player player = GlobalData.getInstance().queryPlayer(idList.getPlayerId(i));							
							if (player != null) {
								HawkSession session = player.getSession();
								if (session != null) {
									session.sendProtocol(protocol);	
								}								
							}
						} catch (Exception e) {
							HawkException.catchException(e);
						}
					}
					
					return null;
				}
			};
			
			// 线程池中固定线程执行
			HawkThreadPool threadPool = HawkTaskManager.getInstance().getThreadPool("task");
			if (threadPool != null) {
				threadPool.addTask(broadcastTask, threadPool.getThreadNum() - 1, false);
			} else {
				HawkTaskManager.getInstance().postTask(broadcastTask,  0);		
			}
			
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	/**
	 * 协议转发
	 * 
	 * @param header
	 * @param protocol
	 */
	private void transportProtocol(ProxyHeader header, HawkProtocol protocol) {
		CrossPlayerProtocolHandler.getInstance().onTransportProtocol(header, protocol);
	}
	
	/**
	 * 投递协议
	 * 
	 * @param header
	 * @param protocol
	 */
	private void dispatchProtocol(ProxyHeader header, HawkProtocol protocol) {
		String targetId = header.getTarget() == null ? "" : header.getTarget();
		
		// 指定目标分发协议
		if (HawkOSOperator.isEmptyString(targetId)) {
			HawkTaskManager.getInstance().postProtocol(CrossService.getInstance().getXid(), protocol, 0);
			return;
		}
		
		int threadIndex = Math.abs(targetId.hashCode()) % HawkTaskManager.getInstance().getThreadNum();
		HawkTaskManager.getInstance().postTask(new HawkTask(){			
			@Override
			public Object run() {
				try {
					// 分发给管理器
					boolean result = CrossPlayerProtocolHandler.getInstance().onProtocol(header, protocol);
					if (result) {
						HawkTaskManager.getInstance().postProtocol(HawkXID.valueOf(GsConst.ObjType.PLAYER, header.getTarget()), protocol, 0);
					}
				} catch (Exception e) {
					HawkException.catchException(e);
				}
				return null;
			}
		},  threadIndex);
	}

	/**
	 * 帧更新检测rpc超时
	 * 
	 */
	@Override
	public void onTick() {
		try {
			int timeout = GsConfig.getInstance().getProxyRpcTimeout();
			
			long curTime = HawkTime.getMillisecond();			
			Iterator<Entry<String, Long>> it = stubTimeMap.entrySet().iterator();
			while(it.hasNext()) {
				Entry<String, Long> entry = it.next();
				if (timeout > 0 && curTime - entry.getValue() >= timeout) {
					onRpcTimeout(entry.getKey());
				}
			}
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}

	/**
	 * rpc 超时处理
	 * 
	 * @param rpcId
	 */
	private boolean onRpcTimeout(String rpcId) {
		// stubTimeMap 是完成权的原子闸门：响应、发送失败、清队列、定时超时只能有一个胜者。
		if (!CrossProxyHealth.tryClaimRpcCompletion(stubTimeMap, rpcId)) {
			return false;
		}
		CsRpcStub stub = rpcStubCache.getIfPresent(rpcId);
		if (stub != null) {
			rpcStubCache.invalidate(rpcId);

			ProxyHeader header = stub.getHeader();
			long costTime = HawkTime.getMillisecond() - stub.getStubTime();
			HawkLog.warnPrintln("csproxy rpc timeout, rpcid: {}, type: {}, from: {}, to: {}, source: {}, target: {}, costTime: {}ms, sendQueueSize: {}, rpcCacheSize: {}",
					rpcId, header.getType(), header.getFrom(), header.getTo(), header.getSource(), header.getTarget(), costTime, protoSendQueue.size(), rpcStubCache.size());
			
			HawkTaskManager.getInstance().postTask(new HawkTask() {
				@Override
				public Object run() {
					stub.getCallback().onTimeout(stub);
					return null;
				}
			}, stub.getThreadIdx());
		}
		return true;
	}
	
	/**
	 * 做动态开启
	 * @return
	 */
	public boolean isInit() {
		return zmqList != null;
	}
}
