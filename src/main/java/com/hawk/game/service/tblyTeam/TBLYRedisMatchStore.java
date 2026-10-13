package com.hawk.game.service.tblyTeam;

import java.util.Arrays;
import java.util.Collections;

import com.hawk.game.global.RedisProxy;

import redis.clients.jedis.Jedis;

/**
 * Redis implementation of the Tiberium team-match lease protocol.
 */
final class TBLYRedisMatchStore implements TBLYMatchCoordinator.Store {
    private static final String ACQUIRE_SCRIPT =
            "local result = redis.call('set', KEYS[1], ARGV[1], 'NX', 'EX', ARGV[2]); "
            + "if result then return 1 end; "
            + "if redis.call('ttl', KEYS[1]) == -1 then redis.call('expire', KEYS[1], ARGV[2]) end; "
            + "return 0";
    private static final String RENEW_SCRIPT =
            "if redis.call('get', KEYS[1]) == ARGV[1] then "
            + "return redis.call('expire', KEYS[1], ARGV[2]) else return 0 end";
    private static final String COMPLETE_SCRIPT =
            "if redis.call('get', KEYS[1]) == ARGV[1] then "
            + "redis.call('set', KEYS[2], ARGV[2]); return 1 else return 0 end";
    private static final String RELEASE_SCRIPT =
            "if redis.call('get', KEYS[1]) == ARGV[1] then "
            + "return redis.call('del', KEYS[1]) else return 0 end";

    @Override
    public boolean isComplete(String completeKey) {
        return RedisProxy.getInstance().getRedisSession().getString(completeKey) != null;
    }

    @Override
    public boolean tryAcquire(String lockKey, String token, int leaseSeconds) {
        try (Jedis jedis = RedisProxy.getInstance().getRedisSession().getJedis()) {
            Object result = jedis.eval(ACQUIRE_SCRIPT, Collections.singletonList(lockKey),
                    Arrays.asList(token, String.valueOf(leaseSeconds)));
            return Long.valueOf(1L).equals(result);
        }
    }

    @Override
    public boolean renew(String lockKey, String token, int leaseSeconds) {
        try (Jedis jedis = RedisProxy.getInstance().getRedisSession().getJedis()) {
            Object result = jedis.eval(RENEW_SCRIPT, Collections.singletonList(lockKey),
                    Arrays.asList(token, String.valueOf(leaseSeconds)));
            return Long.valueOf(1L).equals(result);
        }
    }

    @Override
    public boolean markComplete(String lockKey, String completeKey, String token, String value) {
        try (Jedis jedis = RedisProxy.getInstance().getRedisSession().getJedis()) {
            Object result = jedis.eval(COMPLETE_SCRIPT, Arrays.asList(lockKey, completeKey),
                    Arrays.asList(token, value));
            return Long.valueOf(1L).equals(result);
        }
    }

    @Override
    public void release(String lockKey, String token) {
        try (Jedis jedis = RedisProxy.getInstance().getRedisSession().getJedis()) {
            jedis.eval(RELEASE_SCRIPT, Collections.singletonList(lockKey), Collections.singletonList(token));
        }
    }

    public String getOwner(String lockKey) {
        return RedisProxy.getInstance().getRedisSession().getString(lockKey);
    }

    public long getTtl(String lockKey) {
        try (Jedis jedis = RedisProxy.getInstance().getRedisSession().getJedis()) {
            return jedis.ttl(lockKey);
        }
    }
}
