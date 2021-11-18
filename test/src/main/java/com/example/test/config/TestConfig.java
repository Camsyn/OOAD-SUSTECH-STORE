package com.example.test.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.camsyn.store.commons.entity.auth.Account;

@Configuration
@ConditionalOnMissingClass("com.example.test.TestApplication")
public class TestConfig {
    @Bean
    public Account account(){
        return new Account();
    }
}
