package com.hawk.game.crossproxy;

import java.util.Map;

final class CrossProxyHealth {
	enum State {
		DISCONNECTED,
		RECOVERING,
		HEALTHY
	}

	private static final long RETRY_BASE_MILLIS = 10_000L;
	private static final long RETRY_MAX_MILLIS = 60_000L;

	private CrossProxyHealth() {
	}

	static boolean shouldRecover(long currentTime, long keepaliveTime, long checkPeriod) {
		if (keepaliveTime <= 0L || checkPeriod <= 0L || currentTime < keepaliveTime) {
			return true;
		}
		return currentTime - keepaliveTime >= checkPeriod;
	}

	static boolean canSendBusiness(State state) {
		return state == State.HEALTHY;
	}

	static long retryDelayMillis(int attempt, long jitterMillis) {
		int safeAttempt = Math.max(0, Math.min(attempt, 3));
		long exponentialDelay = RETRY_BASE_MILLIS << safeAttempt;
		return Math.min(exponentialDelay, RETRY_MAX_MILLIS) + Math.max(0L, jitterMillis);
	}

	static boolean tryClaimRpcCompletion(Map<String, Long> pendingRpc, String rpcId) {
		return pendingRpc.remove(rpcId) != null;
	}
}
