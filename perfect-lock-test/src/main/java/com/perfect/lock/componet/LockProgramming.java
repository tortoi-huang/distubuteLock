package com.perfect.lock.componet;

import com.perfect.lock.compoent.PerfectRedLock;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class LockProgramming {
    private final PerfectRedLock perfectRedLock;

    public LockProgramming(PerfectRedLock perfectRedLock) {
        this.perfectRedLock = perfectRedLock;
    }

    public String lockLock(String name, long sleepTime) {
        RLock redLock = perfectRedLock.getRedLock(name);
        boolean b = false;
        try {
            b = redLock.tryLock(0, 300, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            if (b) {
                if(sleepTime > 0)
                    Thread.sleep(sleepTime);
                redLock.unlock();
                return "lock success:" + redLock.getName();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "lock fail:" + redLock.getName();
    }

    public String lockThreadLocal(String name, long sleepTime) {
        boolean b = perfectRedLock.tryLock(name, 0, 300);
        if (b) {
            try {
                if (name.endsWith("99"))
                    throw new RuntimeException("end with 99");
                if(sleepTime > 0)
                    Thread.sleep(sleepTime);
                return "lock success:" + name;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                perfectRedLock.unlock(name);
            }
        }
        return "lock fail:" + name;
    }
}
