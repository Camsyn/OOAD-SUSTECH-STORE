//package top.camsyn.store.auth.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
//
//@Configuration
//public class RedisExtConfig {
//    @Autowired
//    RedisConnectionFactory redisConnectionFactory
//            ;
//    @Bean
//    TokenStore tokenStore() {
//        return new RedisTokenStore(redisConnectionFactory);
//    }
//}
