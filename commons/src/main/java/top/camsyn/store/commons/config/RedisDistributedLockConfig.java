package top.camsyn.store.commons.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

@Slf4j
@Configuration
public class RedisDistributedLockConfig {

    private static final String redisLockKeyPrefix = "%s-redis-lock";

    @Value("${spring.application.name}")
    String appName;

    @Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        final String lockPrefix = String.format(redisLockKeyPrefix, appName);
        log.info("注册Redis分布式锁，key前缀："+ lockPrefix);
        return new RedisLockRegistry(redisConnectionFactory, lockPrefix, 300000L);
    }
}

