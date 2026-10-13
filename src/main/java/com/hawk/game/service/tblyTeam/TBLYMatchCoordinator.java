package com.hawk.game.service.tblyTeam;

/**
 * Coordinates one idempotent Tiberium team-match attempt under a Redis lease.
 */
public final class TBLYMatchCoordinator {
    private TBLYMatchCoordinator() {
    }

    public enum Result {
        COMPLETED,
        MATCHED,
        BUSY,
        LOST_LEASE
    }

    public interface Store {
        boolean isComplete(String completeKey);

        boolean tryAcquire(String lockKey, String token, int leaseSeconds);

        boolean renew(String lockKey, String token, int leaseSeconds);

        boolean markComplete(String lockKey, String completeKey, String token, String value);

        void release(String lockKey, String token);
    }

    public interface Work {
        void run(Lease lease) throws Exception;
    }

    public interface Lease {
        boolean renew();
    }

    public static Result execute(Store store, String lockKey, String completeKey, String token,
            int leaseSeconds, String completeValue, Work work) throws Exception {
        if (store.isComplete(completeKey)) {
            return Result.COMPLETED;
        }
        if (!store.tryAcquire(lockKey, token, leaseSeconds)) {
            return store.isComplete(completeKey) ? Result.COMPLETED : Result.BUSY;
        }
        try {
            Lease lease = new Lease() {
                @Override
                public boolean renew() {
                    return store.renew(lockKey, token, leaseSeconds);
                }
            };
            work.run(lease);
            if (!store.renew(lockKey, token, leaseSeconds)) {
                return Result.LOST_LEASE;
            }
            if (!store.markComplete(lockKey, completeKey, token, completeValue)) {
                return Result.LOST_LEASE;
            }
            return Result.MATCHED;
        } finally {
            store.release(lockKey, token);
        }
    }

    public static int selectCandidateIndex(int termId, String teamId, int candidateCount) {
        if (candidateCount <= 0) {
            throw new IllegalArgumentException("candidateCount must be positive");
        }
        int stableHash = 31 * termId + teamId.hashCode();
        return Math.floorMod(stableHash, candidateCount);
    }
}
