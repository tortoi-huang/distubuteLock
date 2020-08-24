package com.perfect.lock.controller;

import com.perfect.lock.componet.LockTestCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/test")
public class LockTestController {
    private final LockTestCase lockTestCase;

    public LockTestController(LockTestCase lockTestCase) {
        this.lockTestCase = lockTestCase;
    }

    @GetMapping("/lock")
    public String testLock() {
        try {
            //这里测试超时释放
            lockTestCase.testLock3(31,12);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("lockTestCase.testLock3(31)");
        }
        try {
            lockTestCase.testLock2(21);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("lockTestCase.testLock2(21)");
        }
        try {
            lockTestCase.testLock2("22");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("lockTestCase.testLock2(\"22\")");
        }
        try {
            lockTestCase.testLock2(new Date(23));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("lockTestCase.testLock2(new Date(23))");
        }
        return "test complete";
    }
}
