package com.perfect.lock.aop;

import com.perfect.lock.annotation.PerfectLockParam;
import com.perfect.lock.annotation.PerfectLockParamParser;
import com.perfect.lock.exception.LockFailureException;
import com.perfect.lock.exception.UnLockFailureException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Aspect
public class PerfectLockAspect {
    private static final Logger log = LoggerFactory.getLogger(PerfectLockAspect.class);
    private final Map<String, PerfectLockParamParser> lockNameCache = new ConcurrentHashMap<>();
    private final RedissonClient client;

    public PerfectLockAspect(RedissonClient client) {
        this.client = client;
    }

    @Around("@annotation(com.perfect.lock.annotation.PerfectLock)")
    public Object round(ProceedingJoinPoint point) throws Throwable {
        PerfectLockParam lockParam = parseLockName(point);
        log.debug("require lock: {}", lockParam);
        RLock lock = client.getLock(lockParam.getName());
        try {
            boolean b = lock.tryLock(lockParam.getMaxWait(), lockParam.getMaxHold(), TimeUnit.MILLISECONDS);
            if (!b) {
                log.warn("require lock failure: {}",lockParam);
                throw new LockFailureException("lock fail");
            }
            return point.proceed();
        } finally {
            try {
                lock.unlock();
            } catch (Exception e) {
                log.error("unlock failure: {}",lockParam);
                throw new UnLockFailureException(e);
            }
        }
    }

    private PerfectLockParam parseLockName(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        PerfectLockParamParser parser = lockNameCache.computeIfAbsent(signature.toLongString(), k -> new PerfectLockParamParser(signature));

        return parser.parse(point.getArgs());
    }
}
