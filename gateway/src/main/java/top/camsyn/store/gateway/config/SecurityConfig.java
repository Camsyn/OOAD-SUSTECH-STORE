package top.camsyn.store.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginReactiveAuthenticationManager;
import org.springframework.security.web.server.SecurityWebFilterChain;
import top.camsyn.store.gateway.component.CustomReactiveAccessTokenResponseClient;
import top.camsyn.store.gateway.service.CustomReactiveOAuth2UserService;

import javax.annotation.Resource;

@Configuration
public class SecurityConfig {

    @Resource
    private CustomReactiveAccessTokenResponseClient oAuth2AccessTokenResponseClient;

    @Resource
    private CustomReactiveOAuth2UserService oAuth2UserService;

    @Bean
    public SecurityWebFilterChain initSecurityWebFilterChain(ServerHttpSecurity http){

        http.oauth2Login().and().authorizeExchange()
                .pathMatchers("/**").permitAll();

        http.oauth2Login().authenticationManager(new OAuth2LoginReactiveAuthenticationManager(
                oAuth2AccessTokenResponseClient,oAuth2UserService));

        http.cors().disable();
        http.csrf().disable();
        return http.build();
    }
}
