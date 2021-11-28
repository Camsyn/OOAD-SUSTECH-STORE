package top.camsyn.store.commons;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;


/**
 * @author Chen_Kunqiu
 */
@Configuration
@ComponentScan
@EnableFeignClients
@ConfigurationPropertiesScan("top.camsyn.store.commons.props")
//@Exclude
//@EnableAutoConfiguration
//@ComponentScan(excludeFilters = {
//        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
//        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {AutoConfigurationExcludeFilter.class}),
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Exclude.class)
//        })
//@ConditionalOnMissingBean(value = CommonsAutoConfiguration.class)
//@ConditionalOnProperty(value = "ckq.hello.name")
//@EnableCaching
//@Import(value = {RedisConfiguration.class, RedisRepository.class})
public class CommonsAutoConfiguration {
}

