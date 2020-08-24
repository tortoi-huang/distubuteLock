package com.perfect.lock.starter;

import org.redisson.config.SslProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URL;

@ConfigurationProperties(prefix = "perfect.lock.redis")
public class PerfectLockProperties {
    /**
     * 拦截器优先级，默认最高
     */
    private int aopPriority = 0;

    /**
     * redis key 前缀
     */
    private String prefix = "";

    public int getAopPriority() {
        return aopPriority;
    }

    public void setAopPriority(int aopPriority) {
        this.aopPriority = aopPriority;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
