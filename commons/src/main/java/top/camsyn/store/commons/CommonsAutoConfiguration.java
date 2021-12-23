package top.camsyn.store.commons;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.context.annotation.Configuration;


/**
 * @author Chen_Kunqiu
 */
@Configuration
@ComponentScan
@EnableFeignClients(basePackages = {"top.camsyn.store.commons.client"})
@ConditionalOnMissingClass("com.example.test.TestApplication")
//@EnableBinding({MQProducerSource.class, MQConsumerSource.class})
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

