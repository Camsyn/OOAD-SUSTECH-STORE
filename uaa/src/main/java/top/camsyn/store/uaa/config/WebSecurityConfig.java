package top.camsyn.store.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//import org.springframework.session.FindByIndexNameSessionRepository;
//import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import top.camsyn.store.uaa.service.impl.AccountService;
import top.camsyn.store.uaa.service.impl.AuthUserDetailsService;

import javax.annotation.Resource;

/**
 * @author Chen_Kunqiu
 */
//@EnableWebSecurity
@Configuration
@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthUserDetailsService userDetailsService;

    @Resource
    private AccountService accountService;

    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private AuthenticationSuccessHandler authenticationSuccessHandler;
//    @Autowired
//    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;
//    @Autowired
//    private FindByIndexNameSessionRepository sessionRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

        auth.inMemoryAuthentication()
                .withUser("ckq")
                .password(passwordEncoder().encode("123"))
                .roles("admin");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
        .antMatchers("/assets/**", "/css/**", "/images/**", "/account/register", "/custom-login.html");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        if (passwordEncoder == null) {
            passwordEncoder = new BCryptPasswordEncoder(10);
        }
        return passwordEncoder;
    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    /**
     * 允许匿名访问所有接口 主要是 oauth 接口
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/**").permitAll();


//        http.authorizeRequests().anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/custom-login.html")
//                .loginProcessingUrl("/account/login")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .successHandler(authenticationSuccessHandler)
//                .failureHandler(authenticationFailureHandler)
//                .defaultSuccessUrl("/")
////                .defaultSuccessUrl("/defaultSuccess")
////                .failureForwardUrl("/custom-login.html")
//                .permitAll()
//                .and()
//                .rememberMe().rememberMeParameter("rememberme").tokenValiditySeconds(2*24*60*60)
//                .and()
//                .logout().logoutUrl("/uaa/logout")
//                .logoutSuccessHandler(logoutSuccessHandler).deleteCookies("SESSION").permitAll()
//                .and()
//                .httpBasic()
//                .and()
//                .csrf().disable() //关闭跨域防护
//                .headers()
//                .frameOptions()
//                .disable()
////            .and()
////                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint)
//                .and()
//                .sessionManagement().maximumSessions(1).expiredUrl("/uaa/login").sessionRegistry(sessionRegistry());

        http.requestMatchers()
                    .antMatchers("/uaa/login")
                    .antMatchers("/uaa/logout")
                    .antMatchers("/oauth/authorize")
                .and()
                    .authorizeRequests().anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/custom-login.html")
                    .loginProcessingUrl("/uaa/login")
                    .permitAll()
                .and()
                    .logout().logoutUrl("/uaa/logout")
                    .logoutSuccessHandler(logoutSuccessHandler).deleteCookies("SESSION").permitAll()
                .and()
                .csrf().disable();

    }

//    @Bean
//    public SessionRegistry sessionRegistry() {
//        return new SpringSessionBackedSessionRegistry(this.sessionRepository);
//    }
//
//    /**
//     * 全局用户信息
//     */
//    @Autowired
//    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin()
//                .loginPage("/login")
//                .and()
//                .authorizeRequests()
//                .antMatchers("/login").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and().csrf().disable().cors();
//    }
}