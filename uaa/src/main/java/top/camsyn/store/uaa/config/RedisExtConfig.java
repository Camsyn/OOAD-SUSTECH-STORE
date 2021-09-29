package top.camsyn.store.uaa.config;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
//import org.springframework.session.FindByIndexNameSessionRepository;
//import org.springframework.session.data.redis.RedisIndexedSessionRepository;

import javax.annotation.Resource;

@Configuration
public class RedisExtConfig {

    @Resource
    private RedisConnectionFactory redisConnectionFactory;


    @Bean
    public RedisTokenStore redisTokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }


}
