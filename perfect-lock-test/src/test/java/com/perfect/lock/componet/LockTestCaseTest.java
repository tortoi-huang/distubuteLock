package com.perfect.lock.componet;

import com.perfect.lock.exception.UnLockFailureException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LockTestCaseTest {
    @Autowired
    private LockTestCase lockTestCase;

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
            System.out.println(i + " & 7 = " + l);
            try {
                lockTestCase.testLock3(l);
            } catch (UnLockFailureException e) {
                System.out.println("!!!!!!!! UnLockFailureException: " + l);
            }
        });
    }
}