package com.perfect.lock.starter;


import com.perfect.lock.aop.PerfectLockAspect;
import com.perfect.lock.compoent.PerfectRedLock;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
//@ConditionalOnBean(RedissonClient.class)
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties({PerfectLockProperties.class, RedisProperties.class})
@EnableAspectJAutoProxy
public class PerfectLockAutoConfiguration {
    private static final String ADDRESS_PREFIX = "redis://";

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty("spring.redis.host")
    public RedissonClient redissonClient(RedisProperties properties) {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer()
                .setAddress(ADDRESS_PREFIX + properties.getHost() + ":" + properties.getPort())
                .setPassword(properties.getPassword())
                .setDatabase(properties.getDatabase());
        if (properties.getTimeout() != null) {
            singleServerConfig.setTimeout(
                    (int) properties.getTimeout().getSeconds() * 1000 + properties.getTimeout().getNano() / 1000_000);
        }
        return Redisson.create(config);
    }

    @ConditionalOnMissingBean
    @ConditionalOnBean(RedissonClient.class)
    @Bean
    public PerfectLockAspect perfectLockAspect(PerfectRedLock perfectRedLock, PerfectLockProperties properties) {
        return new PerfectLockAspect(perfectRedLock, properties.getAopPriority());
    }

    @ConditionalOnMissingBean
    @ConditionalOnBean(RedissonClient.class)
    @Bean
    public PerfectRedLock perfectRedLock(RedissonClient client, PerfectLockProperties properties) {
        return new PerfectRedLock(properties.getPrefix(), client);
    }
}
