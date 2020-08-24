package com.perfect.lock.aop;

import com.perfect.lock.annotation.PerfectLockParam;
import com.perfect.lock.annotation.PerfectLockParamParser;
import com.perfect.lock.common.LockUtils;
import com.perfect.lock.compoent.PerfectRedLock;
import com.perfect.lock.exception.LockFailureException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
public class PerfectLockAspect implements Ordered {
    private static final Logger log = LoggerFactory.getLogger(PerfectLockAspect.class);
    private final Map<String, PerfectLockParamParser> lockNameCache = new ConcurrentHashMap<>();
    private final PerfectRedLock perfectRedLock;
    private final int priority;

    public PerfectLockAspect(PerfectRedLock perfectRedLock, Integer priority) {
        this.perfectRedLock = perfectRedLock;
        this.priority = priority;
    }

    @Around("@annotation(com.perfect.lock.annotation.PerfectLock)")
    public Object round(ProceedingJoinPoint point) throws Throwable {
        PerfectLockParam lockParam = parseLockName(point);
        log.debug("require lock: {}", lockParam);
        RLock lock = perfectRedLock.getRedLock(lockParam.getName());
        try {
            boolean b = LockUtils.tryLock(lock, lockParam.getMaxWait(), lockParam.getMaxHold());
            if (!b) {
                log.warn("require lock failure: {}",lockParam);
                throw new LockFailureException("lock fail");
            }
            return point.proceed();
        } finally {
            LockUtils.unlock(lock);
        }
    }

    @Override
    public int getOrder() {
        return priority;
    }

    private PerfectLockParam parseLockName(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        PerfectLockParamParser parser = lockNameCache.computeIfAbsent(signature.toLongString(), k -> new PerfectLockParamParser(signature));

        return parser.parse(point.getArgs());
    }
}
