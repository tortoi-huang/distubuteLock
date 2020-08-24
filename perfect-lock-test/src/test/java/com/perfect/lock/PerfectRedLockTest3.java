package com.perfect.lock;

import com.perfect.lock.compoent.PerfectRedLock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PerfectRedLockTest3 {
    @Autowired
    private PerfectRedLock perfectRedLock;

    @Test
    void testInlock() {
        perfectRedLock.inlock("lockName", 0, 500,
                () -> {
                    System.out.println("这里的代码将在锁中执行"); //do business
                });
    }
}
