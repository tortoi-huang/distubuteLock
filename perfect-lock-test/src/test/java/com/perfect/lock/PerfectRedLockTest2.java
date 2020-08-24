package com.perfect.lock;

import com.perfect.lock.compoent.PerfectRedLock;
import com.perfect.lock.exception.UnLockFailureException;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PerfectRedLockTest2 {
    @Autowired
    private PerfectRedLock perfectRedLock;

    @Test
    void testGetRedLock() throws InterruptedException {
        RLock redLock = perfectRedLock.getRedLock("lockName");

        boolean isLocked1 = redLock.tryLock(0, 500, TimeUnit.MILLISECONDS);
        System.out.println("加锁状态：" + isLocked1); //do business
        boolean isLocked2 = redLock.tryLock(0, 500, TimeUnit.MILLISECONDS);
        System.out.println("加锁状态：" + isLocked2); //do business
        redLock.unlock();
        redLock.unlock();
        boolean isLocked3 = redLock.tryLock(0, 500, TimeUnit.MILLISECONDS);
        System.out.println("加锁状态：" + isLocked3); //do business
        redLock.unlock();
    }
}
