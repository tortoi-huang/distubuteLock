package com.perfect.lock.starter;


import com.perfect.lock.aop.PerfectLockAspect;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ConditionalOnBean(RedissonClient.class)
@EnableConfigurationProperties({PerfectLockProperties.class})
@EnableAspectJAutoProxy
public class PerfectLockAutoConfiguration {

    /*@Bean("lockRedissonClient")
    @ConditionalOnProperty("perfect.lock.redis.host")
    public RedissonClient redissonClient(PerfectLockProperties properties) {
        Config config = new Config();
        config.useSingleServer().setAddress(properties.getAddress())
                .setPassword(properties.getPassword())
                .setDatabase(properties.getDatabase())
                .setConnectionPoolSize(properties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(properties.getConnectionMinimumIdleSize())
                .setIdleConnectionTimeout(properties.getIdleConnectionTimeout())
                .setTimeout(properties.getTimeout())
                .setConnectTimeout(properties.getConnectTimeout());
        return Redisson.create(config);
    }*/

    @ConditionalOnMissingBean(PerfectLockAspect.class)
    @Bean
    public PerfectLockAspect perfectLockAspect(RedissonClient client) {
        return new PerfectLockAspect(client);
    }
}
