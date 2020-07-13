package com.perfect.lock.componet;

import com.perfect.lock.annotation.PerfectLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class LockTestCase {
    private final RedissonClient redisson;

    public LockTestCase(RedissonClient redisson) {
        this.redisson = redisson;
    }

    public void testLock() {
        RLock testLock = redisson.getLock("test_lock_name");
        RLock testLock2 = redisson.getLock("test_lock_name");
        try {
            System.out.println("lock thread id:" + Thread.currentThread().getId());
            boolean b = testLock.tryLock(1, 10, TimeUnit.MINUTES);
            System.out.println("locked1:" + b);
            boolean b1 = testLock2.tryLock(1, 10, TimeUnit.MINUTES);
            System.out.println("locked2:" + b1);
            boolean b2 = testLock2.tryLock(1, 10, TimeUnit.MINUTES);
            System.out.println("locked22:" + b2);
            Thread.sleep(20000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            testLock2.unlock();
            testLock2.unlock();
            testLock.unlock();
            System.out.println("unlock!");
        }
    }

    @PerfectLock(name = "'mylock:' + #orderNo",maxWait = 5000,maxHold = 30_000)
    public void testLock2(String orderNo) {
        System.out.println("testLock2(String orderNo): " + orderNo);
    }

    @PerfectLock(name = "'mylock:' + #orderNo",maxWait = 5000,maxHold = 30_000)
    public void testLock2(Object orderNo) {
        System.out.println("testLock2(Object orderNo): " + orderNo);
    }

    @PerfectLock(name = "'mylock:' + #orderNo",maxWait = 5000,maxHold = 30_000)
    public void testLock2(long orderNo) {
        System.out.println("testLock2(long orderNo): " + orderNo);
    }

    @PerfectLock(name = "'mylock:' + #orderNo",maxWait = 5000,maxHold = 300)
    public void testLock3(long orderNo) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("testLock2(long orderNo): " + orderNo);
    }
}
