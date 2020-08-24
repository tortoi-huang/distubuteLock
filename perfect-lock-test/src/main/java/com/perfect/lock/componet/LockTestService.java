package com.perfect.lock.componet;

import com.perfect.lock.annotation.PerfectLock;
import org.springframework.stereotype.Service;

@Service
public class LockTestService {

    @PerfectLock(name = "'mylock:' + #orderNo",maxWait = 5000,maxHold = 30_000)
    public void lockByParam(String orderNo) {
        System.out.println("testLock2(String orderNo): " + orderNo);
    }
}
