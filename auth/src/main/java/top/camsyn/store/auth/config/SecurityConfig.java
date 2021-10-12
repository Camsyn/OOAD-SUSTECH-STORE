package top.camsyn.store.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import top.camsyn.store.auth.handler.CustomAuthEntryPoint;
import top.camsyn.store.auth.handler.CustomLogoutSuccessHandler;
import top.camsyn.store.auth.handler.LoginFailHandler;
import top.camsyn.store.auth.handler.LoginSuccessHandler;
import top.camsyn.store.auth.props.AuthProperties;

/**
 * @author Chen_Kunqiu
 */
@Configuration
//@EnableConfigurationProperties(AuthProperties.class)
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthProperties authProperties;
//
    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private LoginFailHandler loginFailHandler;

    @Autowired
    private CustomLogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private CustomAuthEntryPoint authEntryPoint;

//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/custom-login.html", "/css/**", "/js/**", "/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .requestMatchers()
            .antMatchers(authProperties.logoutProcessingUrl)
            .antMatchers(authProperties.loginProcessingUrl)
            .antMatchers("/oauth/authorize")
        .and()
            .authorizeRequests().antMatchers("/oauth/**").permitAll()
        .and()
            .authorizeRequests().anyRequest().authenticated()
        .and()
            .formLogin()
//            .loginPage(authProperties.loginPage)
            .loginProcessingUrl(authProperties.loginProcessingUrl)
            .successHandler(loginSuccessHandler)
            .failureHandler(loginFailHandler)
            .permitAll()
        .and()
            .logout().logoutUrl(authProperties.logoutProcessingUrl)
                .logoutRequestMatcher(new AntPathRequestMatcher(authProperties.logoutProcessingUrl,"POST"))
                .logoutSuccessHandler(logoutSuccessHandler).deleteCookies("JSESSIONID")
                .clearAuthentication(true).invalidateHttpSession(true).permitAll()
        .and()
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(authEntryPoint);


//        http.requestMatchers()
//                .antMatchers("/uaa/login")
//                .antMatchers("/oauth/authorize")
//                .and()
//                .authorizeRequests().anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/custom-login.html")
//                .loginProcessingUrl("/uaa/login")
//                .permitAll()
//                .and()
//                .csrf().disable();

//        http.requestMatchers()
//                .antMatchers("/uaa/login")
//                .antMatchers("/oauth/authorize")
//                .and()
//                .authorizeRequests().anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/custom-login.html")
//                .loginProcessingUrl("/uaa/login")
//                .permitAll()
//                .and()
//                .logout().logoutUrl("/uaa/logout").clearAuthentication(true).deleteCookies("JSESSIONID")
//                .and()
//                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("11911626")
//                .password(passwordEncoder.encode("123456"))
//                .roles("admin");
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);


    }

}
