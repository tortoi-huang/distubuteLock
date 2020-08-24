package com.perfect.lock;

import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.fail;

public class RlockTest {
    @Test
    void testLock() {
        RedissonClient redisson = client();
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
            fail();
        } finally {
            testLock2.unlock();
            testLock2.unlock();
            testLock.unlock();
            System.out.println("unlock!");
        }
    }


    @Test
    void testLock2() {
        RedissonClient redisson = client();
        final RLock testLock = redisson.getLock("test_lock_2");
        try {
            lock(testLock);
            Thread thread = new Thread(() -> {
                boolean b = lock(testLock);
                if(b) {
                    testLock.unlock();
                    System.out.println("thread unlock");
                }
            });
            thread.start();
            thread.join();
            testLock.unlock();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    private boolean lock(final RLock testLock) {
        try {
            long id = Thread.currentThread().getId();
            System.out.println("lock thread id:" + id);
            boolean b1 = testLock.tryLock(0, 10, TimeUnit.MINUTES);
            System.out.println("thread id lock:" + b1);
            return b1;
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail();
        }
        return false;
    }

    private RedissonClient client() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://42.194.208.5:6379").setPassword("lige@2020");
        return Redisson.create(config);
    }
}
