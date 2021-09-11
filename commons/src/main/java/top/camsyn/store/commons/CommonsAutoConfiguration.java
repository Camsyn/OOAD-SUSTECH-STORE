package top.camsyn.store.commons;

import io.lettuce.core.dynamic.codec.RedisCodecResolver;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.TypeExcludeFilter;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import top.camsyn.store.commons.annotation.Exclude;
import top.camsyn.store.commons.config.RedisConfiguration;
import top.camsyn.store.commons.repository.RedisRepository;


/**
 * @author Chen_Kunqiu
 */
//@Configuration
//@Exclude
//@EnableAutoConfiguration
//@ComponentScan(excludeFilters = {
//        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
//        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {AutoConfigurationExcludeFilter.class}),
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Exclude.class)
//        })
//@ConditionalOnProperty(value = "ckq.hello.name")
//@EnableCaching
//@Import(value = {RedisConfiguration.class, RedisRepository.class})
public class CommonsAutoConfiguration {
}

