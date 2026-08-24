package com.hawk.game.crossproxy;

import org.hawk.log.HawkLog;
import org.hawk.os.HawkException;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkTime;

import com.hawk.game.GsConfig;
import com.hawk.game.global.RedisProxy;

import redis.clients.jedis.Jedis;

public class ProxyHelper {
	private static final String VALIDATE_PROXY_CONNECTION_SCRIPT =
			"local master = redis.call('hget', KEYS[1], 'master'); "
			+ "if master == ARGV[1] then redis.call('hset', KEYS[1], 'address', ARGV[2]); return 1; end; "
			+ "if master and master ~= '' and redis.call('hget', KEYS[1], 'address') == ARGV[2] then return 2; end; "
			+ "return 0";
	public static final int PROXY_CONNECTION_REJECTED = 0;
	public static final int PROXY_CONNECTION_MASTER = 1;
	public static final int PROXY_CONNECTION_FOLLOWER = 2;
	/**
	 * 心跳周期
	 */
	public static final int HEART_BEAT_PERIOD = 3000;
	/**
	 * 协议过期时间
	 */
	public static final int PROTOCOL_EXPIRE = 10000;
	/**
	 * 最大空闲周期周期
	 */
	public static final int MASTER_CHECK_PERIOD = 10000;
	/**
	 * 自动解锁时间
	 */
	public static final int AUTO_UNLOCK_PERIOD = 120000;
	
	/**
	 * 检测主服务器
	 */
	public static void contendMasterServer(boolean aliveCheck) {
		String proxyKey = String.format("csproxy:%s", GsConfig.getInstance().getAreaId());
		try {
			// 起服时, 不存在主服即注册自己为主服
			if (!aliveCheck) {
				do {
					String masterServer = getMasterServer();
					// 已经有master了
					if (!HawkOSOperator.isEmptyString(masterServer)) {
						break;
					}
					
					// 没有拿到锁
					if (!waitCsProxyLock(proxyKey)) {
						break;
					}
					
					try {
						masterServer = getMasterServer();						
						if (HawkOSOperator.isEmptyString(masterServer)) {
							// 主服务器信息
							RedisProxy.getInstance().getRedisSession().hSet(proxyKey, "master", GsConfig.getInstance().getServerId());
							RedisProxy.getInstance().getRedisSession().hSet(proxyKey, "heartbeat", String.valueOf(HawkTime.getMillisecond()));
							
							HawkLog.logPrintln("csproxy contend master server: {}  old master server is null", GsConfig.getInstance().getServerId());
						}
					} finally {
						freeCsProxyLock(proxyKey);
					}
					
				} while (false);
				
				return;
			}
			
			// 是主服务器, 更新心跳
			if (isMasterServer()) {
				RedisProxy.getInstance().getRedisSession().hSet(proxyKey, "heartbeat", String.valueOf(HawkTime.getMillisecond()));
				return;
			}
			
			// 主服务器存活情况下不处理(后面开始竞争锁, 进行自注册)
			if (isMasterAlive()) {
				return;
			}
			
			// 发现master不存活, 抢锁, 注册自己
			if (!waitCsProxyLock(proxyKey)) {
				return;
			}
			
			try {
				// double check
				if (isMasterAlive()) {
					return;
				}
				
				//老的主服信息.
				String oldMasterServer = RedisProxy.getInstance().getRedisSession().hGet(proxyKey, "master");
				// 注册自己为主服务器
				RedisProxy.getInstance().getRedisSession().hSet(proxyKey, "master", GsConfig.getInstance().getServerId());
				RedisProxy.getInstance().getRedisSession().hSet(proxyKey, "heartbeat", String.valueOf(HawkTime.getMillisecond()));
				
				HawkLog.logPrintln("csproxy contend master server: {} old master server:{}", GsConfig.getInstance().getServerId(), oldMasterServer);
				
			} finally {
				freeCsProxyLock(proxyKey);
			}
			
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}

	/**
	 * 等待跨服锁
	 * 
	 * @param proxyKey
	 * @return
	 */
	private static boolean waitCsProxyLock(String proxyKey) {
		// 等待操作锁
		try {

			long curTime = HawkTime.getMillisecond();
			long lock = RedisProxy.getInstance().getRedisSession().hSetNx(proxyKey, "lock", String.valueOf(curTime));
			if (lock > 0) {
				return true;
			}
			
			String val = RedisProxy.getInstance().getRedisSession().hGet(proxyKey, "lock");
			if (!HawkOSOperator.isEmptyString(val)) {
				long lastTime = Long.valueOf(val);
				if (curTime - lastTime > AUTO_UNLOCK_PERIOD) {
					RedisProxy.getInstance().getRedisSession().hDel(proxyKey, "lock");
				}
			}									
			
		} catch (Exception e) {
			HawkException.catchException(e);
		}
		return false;
	}
	
	/**
	 * 释放跨服锁
	 */
	private static void freeCsProxyLock(String proxyKey) {
		RedisProxy.getInstance().getRedisSession().hDel(proxyKey, "lock");
	}
	
	/**
	 * 获取当前节点地址
	 * 
	 * @return
	 */
	public static String getProxyAddress() {
		String proxyKey = String.format("csproxy:%s", GsConfig.getInstance().getAreaId());
		try {
			return RedisProxy.getInstance().getRedisSession().hGet(proxyKey, "address");
		} catch (Exception e) {
			HawkException.catchException(e);
			return "";
		}
	}
	
	/**
	 * 获取主服务器id
	 * 
	 * @return
	 */
	public static String getMasterServer() {
		String proxyKey = String.format("csproxy:%s", GsConfig.getInstance().getAreaId());
		try {
			return RedisProxy.getInstance().getRedisSession().hGet(proxyKey, "master");
		} catch (Exception e) {
			HawkException.catchException(e);
			return "";
		}
	}

	/**
	 * 原子校验当前连接角色：master 验活后发布地址，follower 仅接受 master 已发布的地址。
	 * 
	 * @param address
	 * @return PROXY_CONNECTION_* 角色
	 */
	public static int validateProxyConnection(String address) {
		String proxyKey = String.format("csproxy:%s", GsConfig.getInstance().getAreaId());
		String serverId = GsConfig.getInstance().getServerId();
		try (Jedis jedis = RedisProxy.getInstance().getRedisSession().getJedis()) {
			Object result = jedis.eval(VALIDATE_PROXY_CONNECTION_SCRIPT, 1, proxyKey, serverId, address);
			return result instanceof Long ? ((Long) result).intValue() : PROXY_CONNECTION_REJECTED;
		} catch (Exception e) {
			HawkException.catchException(e);
			return PROXY_CONNECTION_REJECTED;
		}
	}
	
	/**
	 * 是否为主服务器
	 * 
	 * @return
	 */
	public static boolean isMasterServer() {
		String serverId = getMasterServer();
		if (HawkOSOperator.isEmptyString(serverId)) {
			return false;
		}
		return serverId.equals(GsConfig.getInstance().getServerId());
	}
	
	/**
	 * 主服务器是否存活
	 * 
	 * @return
	 */
	public static boolean isMasterAlive() {
		try {
			String proxyKey = String.format("csproxy:%s", GsConfig.getInstance().getAreaId());
			String value = RedisProxy.getInstance().getRedisSession().hGet(proxyKey, "heartbeat");
			if (HawkOSOperator.isEmptyString(value)) {
				return false;
			}
			
			long heartbeatTime = Long.valueOf(value);
			long timeDiff = HawkTime.getMillisecond() - heartbeatTime;
			if (timeDiff < -MASTER_CHECK_PERIOD || timeDiff > MASTER_CHECK_PERIOD) {
				HawkLog.warnPrintln("csproxy master heartbeat invalid, masterServer: {}, heartbeatTime: {}, timeDiff: {}ms",
						getMasterServer(), heartbeatTime, timeDiff);
				return false;
			}
			return true;
		} catch (Exception e) {
			HawkException.catchException(e);
		}
		return false;		
	}
}
