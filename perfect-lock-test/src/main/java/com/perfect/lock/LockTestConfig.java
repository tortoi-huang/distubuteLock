package com.perfect.lock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({LockTestProperties.class})
public class LockTestConfig {

    @Bean
    @ConditionalOnProperty("perfect.test.redis.address")
    public RedissonClient redissonClient(LockTestProperties properties) {
        Config config = new Config();
        config.useSingleServer().setAddress(properties.getAddress()).setPassword(properties.getPassword()).setDatabase(properties.getDatabase());
        return Redisson.create(config);
    }
}
