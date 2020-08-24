package com.perfect.lock;

import com.perfect.lock.compoent.PerfectRedLock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PerfectRedLockTest1 {
    @Autowired
    private PerfectRedLock perfectRedLock;

    @Test
    void testTryLock() {
        boolean isLocked = perfectRedLock.tryLock("lockName", 0, 30_000);
        if (isLocked) {
            try {
                System.out.println("加锁成功"); //do business
            } finally {
                perfectRedLock.unlock("lockName");
            }
        } else {
            System.out.println("加锁失败");
        }
    }
}
