package com.perfect.lock.componet;

import com.perfect.lock.exception.UnLockFailureException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class LockProgrammingTest {
    @Autowired
    private LockProgramming lockProgramming;

    @Test
    void lockLock() {
        String hello = lockProgramming.lockLock("hello", 200);
        assertTrue(hello.startsWith("lock success:"));
        /*assertThrows(IllegalMonitorStateException.class,
                () -> lockProgramming.lockLock("hello",310));*/
    }

    @Test
    void lockThreadLocal() {
        String hello = lockProgramming.lockThreadLocal("hello", 200);
        assertTrue(hello.startsWith("lock success:"));
        assertThrows(UnLockFailureException.class,
                () -> lockProgramming.lockThreadLocal("hello", 310));
    }

    //@Test
    void lockThreadLocal0() {
        final int size = 10_000;
        long l1 = System.currentTimeMillis();
        System.out.println("test 1 -------------------" + l1);
        for (int i = 0; i < size; i++) {
            try {
                String hello = lockProgramming.lockThreadLocal("hello" + i % 100, 0);
                assertTrue(hello.startsWith("lock success:"));
            } catch (UnLockFailureException e) {
                System.out.println("unlock fail");
            } catch (Exception e) {
                System.out.println("unlock fail2");
            }
        }
        long l2 = System.currentTimeMillis();
        System.out.println("test 2 ------------------- " + (l2 - l1) + "," + l2);
        IntStream.range(0, size).parallel()
                .forEach((i) -> {
                    try {
                        lockProgramming.lockThreadLocal("hello" + i % 100, 0);
                    } catch (IllegalMonitorStateException e) {
                        System.out.println("unlock fail");
                    } catch (Exception e) {
                        System.out.println("unlock fail2");
                    }
                });

        long l3 = System.currentTimeMillis();
        System.out.println("test 3 ------------------- " + (l3 - l2) + "," + l3);
    }
}