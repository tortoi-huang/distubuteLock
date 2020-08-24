package com.perfect.lock.common;

import com.perfect.lock.exception.LockFailureException;
import com.perfect.lock.exception.UnLockFailureException;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

public class LockUtils {
    public static boolean tryLock(RLock redLock, long waitTime, long leaseTime) {
        try {
            return redLock.tryLock(waitTime, leaseTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new LockFailureException(e);
        }
    }

    public static boolean tryLockQuiet(RLock redLock, long waitTime, long leaseTime) {
        try {
            return redLock.tryLock(waitTime, leaseTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    public static void unlock(RLock redLock) {
        try {
            redLock.unlock();
        } catch (Exception e) {
            throw new UnLockFailureException(e);
        }
    }
}
