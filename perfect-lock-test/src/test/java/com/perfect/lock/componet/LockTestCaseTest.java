package com.perfect.lock.componet;

import com.perfect.lock.exception.UnLockFailureException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * junit5 测试用例
 */
@SpringBootTest
class LockTestCaseTest {
    @Autowired
    private LockTestCase lockTestCase;

    /**
     * 测试拦截器正常工作
     */
    @Test
    void testLock300() {
        try {
            lockTestCase.testLock300(123456,250);
        } catch (Exception e) {
            fail();
            e.printStackTrace();
        }
        assertThrows(UnLockFailureException.class,
                () -> lockTestCase.testLock300(123456,350));
    }

    @Test
    void testLock() {
        lockTestCase.testLock2(123456);
    }

    @Test
    void testLockLong() {
        LongStream.range(1,1000).parallel().forEach(lockTestCase::testLock2);
    }

    @Test
    void testLockString() {
        LongStream.range(1,1000).parallel().mapToObj(String::valueOf).forEach(lockTestCase::testLock2);
    }

    @Test
    void testLockObject() {
        LongStream.range(1,1000).parallel().mapToObj(Date::new).forEach(lockTestCase::testLock2);
    }

    @Test
    void testLock3() {
        IntStream.range(1,100).parallel().forEach(i -> {
            long l = i & 7;
            //System.out.println(i + " & 7 = " + l);
            try {
                lockTestCase.testLock3(l,3000);
            } catch (UnLockFailureException e) {
                System.out.println("!!!!!!!! UnLockFailureException: " + l);
            }
        });
    }

    @Test
    void testLock4() {
        IntStream.range(1,10).parallel().forEach(i -> {
            //long l = i & 7;
            try {
                System.out.println("*testLock4 start: " + i);
                lockTestCase.testLock4(i);
                System.out.println("*testLock4 end: " + i);
            } catch (UnLockFailureException e) {
                System.out.println("!!!!!!!! UnLockFailureException: " + i);
            }
        });
    }
}