package com.perfect.lock;

import com.perfect.lock.compoent.PerfectRedLock;
import com.perfect.lock.exception.UnLockFailureException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PerfectRedLockTest {
    @Autowired
    private PerfectRedLock perfectRedLock;

    @Test
    void test1() throws InterruptedException {
        boolean lock11 = perfectRedLock.tryLock("lockName1", 0, 30_000);
        assertTrue(lock11);
        boolean lock21 = perfectRedLock.tryLock("lockName2", 0, 30_000);
        assertTrue(lock21);
        System.gc();
        boolean lock12 = perfectRedLock.tryLock("lockName1", 0, 30_000);
        assertTrue(lock12);
        Thread lockThread1 = new Thread(() -> {
            assertThrows(UnLockFailureException.class, () -> perfectRedLock.unlock("lockName1"));
        });
        lockThread1.start();
        lockThread1.join();
        perfectRedLock.unlock("lockName1");
        perfectRedLock.unlock("lockName1");
        perfectRedLock.unlock("lockName2");
        assertThrows(UnLockFailureException.class, () -> perfectRedLock.unlock("lockName1"));
    }
}
