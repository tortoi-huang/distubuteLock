package com.perfect.lock.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PerfectLock {

    /**
     *
     * @return 锁的名称，可以是spring-el表达式
     */
    String name();

    /**
     *
     * @return 锁被其他线程占用时等待，最大等待时间，毫秒
     */
    long maxWait();

    /**
     *
     * @return 获取锁后最大持有锁时间，该时间后锁会自动释放，毫秒
     */
    long maxHold();
}
