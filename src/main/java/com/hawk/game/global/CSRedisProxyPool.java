package com.hawk.game.global;

import java.util.ArrayList;
import java.util.List;

import org.hawk.log.HawkLog;
import org.hawk.os.HawkOSOperator;
import org.hawk.redis.HawkRedisPoolConfig;
import org.hawk.redis.HawkRedisSession;

import com.google.common.base.Splitter;
import com.hawk.game.GsConfig;

public class CSRedisProxyPool {

	private List<HawkRedisSession> pool ;
	/**
	 * 全局实例对象
	 */
	private static CSRedisProxyPool instance = null;

	/**
	 * 获取实例对象
	 *
	 * @return
	 */
	public static CSRedisProxyPool getInstance() {
		if (instance == null) {
			instance = new CSRedisProxyPool();
		}

		return instance;
	}

	/**一定要放在Redisproxy::init 后面*/
	public boolean init() {
		pool = new ArrayList<>();
		pool.add(RedisProxy.getInstance().getRedisSession());
		// 判断是否配置redis的主机地址
		if (!HawkOSOperator.isEmptyString(GsConfig.getInstance().getCrossReids())) {
			List<String> redis = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(GsConfig.getInstance().getCrossReids());
			List<String> auth = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(GsConfig.getInstance().getCrossRedisAuth());
			for (int i = 0; i < redis.size(); i++) {
				HawkRedisSession redisSession = createCrossRedis(redis.get(i), auth.get(i));
				if(redisSession == null){
					return false;
				}
				pool.add(redisSession);
			}
		}
		return true;
	}

	private HawkRedisSession createCrossRedis(String redisHost, String redisAuth) {
		HawkRedisPoolConfig config = new HawkRedisPoolConfig();
		config.setMaxTotal(GsConfig.getInstance().getRedisMaxActive());
		config.setMaxIdle(GsConfig.getInstance().getRedisMaxIdle());
		config.setMaxWaitMillis(GsConfig.getInstance().getRedisMaxWait());

		int redisPort = 6379;
		int timeout = GsConfig.getInstance().getRedisTimeout();
		if (!HawkOSOperator.isEmptyString(redisHost)) {
			String[] infos = redisHost.split(":");
			redisHost = infos[0];
			redisPort = Integer.valueOf(infos[1]);
		}

		HawkRedisSession redisSession = new HawkRedisSession();
		if (!redisSession.init(redisHost, redisPort, timeout, redisAuth, config)) {
			HawkLog.errPrintln("init csproxy redis failed, ip: {}, port: {}", redisHost, redisPort);
			return null;
		}
		HawkLog.logPrintln("init csproxy redis success, ip: {}, port: {}", redisHost, redisPort);
		return redisSession;
	}

	/**
	 * 获取会话对象, 便于脚本调用
	 * 
	 * @return
	 */
	public HawkRedisSession getRedisSession(String playerId) {
		int index = Math.abs(playerId.hashCode() % pool.size());
		return pool.get(index);
	}

}
