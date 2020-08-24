package com.perfect.lock.compoent;

import com.perfect.lock.common.LockUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.Map;
import java.util.WeakHashMap;

public class PerfectRedLock {
    private final ThreadLocal<Map<String, RLock>> locks = ThreadLocal.withInitial(WeakHashMap::new);
    private final String prefix;
    private final RedissonClient client;

    public PerfectRedLock(String prefix, RedissonClient client) {
        this.prefix = prefix;
        this.client = client;
    }

    public RLock getRedLock(String lockName) {
        return client.getLock(prefix + lockName);
    }

    public boolean tryLock(String lockName, long waitTime, long leaseTime) {
        RLock rLock = locks.get().computeIfAbsent(lockName, k -> client.getLock(prefix + lockName));
        return LockUtils.tryLock(rLock, waitTime, leaseTime);
    }

    public void unlock(String lockName) {
        RLock rLock = locks.get().computeIfAbsent(lockName, k -> client.getLock(prefix + lockName));
        LockUtils.unlock(rLock);
        if (rLock.getHoldCount() == 0) {
            locks.get().remove(lockName);
        }
    }

    public void inlock(String lockName, long waitTime, long leaseTime,
                       final Runnable runnable) {
        RLock redLock = this.getRedLock(lockName);
        if (LockUtils.tryLockQuiet(redLock, waitTime, leaseTime)) {
            try {
                runnable.run();
            } finally {
                LockUtils.unlock(redLock);
            }
        }
    }
}
