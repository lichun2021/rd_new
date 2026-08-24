package com.hawk.game.crossproxy;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Standalone regression test so it can run without the legacy Gradle test setup.
 */
public final class CrossProxyHealthTest {
	private CrossProxyHealthTest() {
	}

	public static void main(String[] args) throws Exception {
		assertRecoveryRequired(20_000L, 0L, 10_000L);
		assertRecoveryRequired(20_000L, 9_999L, 10_000L);
		assertRecoveryRequired(20_000L, 10_000L, 10_000L);
		assertRecoveryRequired(10_000L, 20_000L, 10_000L);
		assertRecoveryRequired(20_000L, 10_001L, 0L);
		assertHealthy(20_000L, 10_001L, 10_000L);
		assertBusinessGate();
		assertRetryDelay();
		assertRpcCompletionClaimedOnce();
	}

	private static void assertRecoveryRequired(long currentTime, long keepaliveTime, long checkPeriod) throws Exception {
		if (!shouldRecover(currentTime, keepaliveTime, checkPeriod)) {
			throw new AssertionError("expected proxy recovery for keepaliveTime=" + keepaliveTime);
		}
	}

	private static void assertHealthy(long currentTime, long keepaliveTime, long checkPeriod) throws Exception {
		if (shouldRecover(currentTime, keepaliveTime, checkPeriod)) {
			throw new AssertionError("unexpected proxy recovery for keepaliveTime=" + keepaliveTime);
		}
	}

	private static boolean shouldRecover(long currentTime, long keepaliveTime, long checkPeriod) throws Exception {
		try {
			Class<?> healthClass = Class.forName("com.hawk.game.crossproxy.CrossProxyHealth");
			Method method = healthClass.getDeclaredMethod("shouldRecover", long.class, long.class, long.class);
			return ((Boolean) method.invoke(null, currentTime, keepaliveTime, checkPeriod)).booleanValue();
		} catch (ClassNotFoundException e) {
			throw new AssertionError("CrossProxy must expose a testable proxy-health recovery policy", e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void assertBusinessGate() throws Exception {
		Class<?> healthClass = Class.forName("com.hawk.game.crossproxy.CrossProxyHealth");
		Class<? extends Enum> stateClass = (Class<? extends Enum>) Class.forName(
				"com.hawk.game.crossproxy.CrossProxyHealth$State");
		Method method = healthClass.getDeclaredMethod("canSendBusiness", stateClass);
		method.setAccessible(true);
		for (String stateName : new String[] { "DISCONNECTED", "RECOVERING" }) {
			Enum state = Enum.valueOf(stateClass, stateName);
			if (((Boolean) method.invoke(null, state)).booleanValue()) {
				throw new AssertionError("business traffic must be blocked while state=" + stateName);
			}
		}
		Enum healthy = Enum.valueOf(stateClass, "HEALTHY");
		if (!((Boolean) method.invoke(null, healthy)).booleanValue()) {
			throw new AssertionError("business traffic must be allowed after heartbeat validation");
		}
	}

	private static void assertRetryDelay() throws Exception {
		Class<?> healthClass = Class.forName("com.hawk.game.crossproxy.CrossProxyHealth");
		Method method = healthClass.getDeclaredMethod("retryDelayMillis", int.class, long.class);
		method.setAccessible(true);
		long[] expected = { 10_500L, 20_500L, 40_500L, 60_500L, 60_500L };
		for (int attempt = 0; attempt < expected.length; attempt++) {
			long actual = ((Long) method.invoke(null, attempt, 500L)).longValue();
			if (actual != expected[attempt]) {
				throw new AssertionError("unexpected retry delay at attempt=" + attempt
						+ ", expected=" + expected[attempt] + ", actual=" + actual);
			}
		}
	}

	private static void assertRpcCompletionClaimedOnce() throws Exception {
		Class<?> healthClass = Class.forName("com.hawk.game.crossproxy.CrossProxyHealth");
		Method method = healthClass.getDeclaredMethod("tryClaimRpcCompletion", Map.class, String.class);
		method.setAccessible(true);
		for (int round = 0; round < 100; round++) {
			Map<String, Long> pending = new ConcurrentHashMap<String, Long>();
			pending.put("rpc", 1L);
			AtomicInteger winners = new AtomicInteger();
			CountDownLatch start = new CountDownLatch(1);
			Thread first = claimThread(method, pending, winners, start);
			Thread second = claimThread(method, pending, winners, start);
			first.start();
			second.start();
			start.countDown();
			first.join();
			second.join();
			if (winners.get() != 1) {
				throw new AssertionError("RPC completion must have exactly one winner, actual=" + winners.get());
			}
		}
	}

	private static Thread claimThread(Method method, Map<String, Long> pending,
			AtomicInteger winners, CountDownLatch start) {
		return new Thread(() -> {
			try {
				start.await();
				if (((Boolean) method.invoke(null, pending, "rpc")).booleanValue()) {
					winners.incrementAndGet();
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}
}
