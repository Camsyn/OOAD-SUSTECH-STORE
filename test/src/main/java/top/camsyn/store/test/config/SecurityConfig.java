package top.camsyn.store.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import top.camsyn.store.test.handler.CustomAuthEntryPoint;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    /**
     * 配置整合redis共享session时使用
     */
    @Autowired
    private FindByIndexNameSessionRepository sessionRepository;

    /**
     * 未登录时发送请求拦截回调
     */
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    /**
     * 登录成功回调
     */
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    /**
     * 登录失败时回调
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 登出成功时回调
     */
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    /**
     * 403时回调
     */
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    private PasswordEncoder passwordEncoder;


    /**
     * 编码工具
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        if (passwordEncoder==null){
            passwordEncoder = new BCryptPasswordEncoder();
        }
        return passwordEncoder;
    }

    /**
     * 解决 无法直接注入 AuthenticationManager
     *
     * @return AuthenticationManager
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().anyRequest()
                .authenticated()
            .and()
                .formLogin()
                .loginPage("/custom-login.html")
                .loginProcessingUrl("/uaa/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .defaultSuccessUrl("/defaultSuccess")
//                .failureForwardUrl("/custom-login.html")
                .permitAll()
            .and()
                .rememberMe().rememberMeParameter("rememberme").tokenValiditySeconds(2*24*60*60)
            .and()
                .logout().logoutUrl("/uaa/logout")
                    .logoutSuccessHandler(logoutSuccessHandler).deleteCookies("SESSION").permitAll()
            .and()
                .httpBasic()
//            .and()
//                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
            .and()
                .csrf().disable() //关闭跨域防护
                .headers()
                .frameOptions()
                .disable();
;
        //认证及权限配置
//        http/*.requestMatchers().antMatchers()
//        .and()*/
//            .formLogin()
            // 登入配置
//            .loginPage("/custom-login.html")
//            .loginProcessingUrl("/uaa/login")
//            .successHandler(authenticationSuccessHandler)
//            .failureHandler(authenticationFailureHandler)
//            .permitAll()
//        .and()
//            .rememberMe().rememberMeParameter("rememberme").tokenValiditySeconds(2*24*60*60)
//        .and()
//         //登出配置
//            .logout()./*logoutUrl("uaa/logout").*/logoutSuccessHandler(logoutSuccessHandler).deleteCookies("SESSION").permitAll()
//        .and()
//            .authorizeRequests()
//            .anyRequest()
//            .authenticated()
//        .and()
//            .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
//        .and()
//            .csrf().disable() //关闭跨域防护
//            .headers()
//            .frameOptions()
//            .disable();
//        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint);
//        // 同一个用户只能有一个session
//        http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());



//        http
//                // CSRF禁用
//                .csrf().disable()
//                // 基于token，所以不需要session
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                // 允许跨域
//                .cors()
//                .and()
//                .headers().addHeaderWriter(new StaticHeadersWriter(Collections.singletonList(new Header("Access-Control-Expose-Headers", "Authorization"))));
//
////        // 匿名访问路径
////        if (ArrayUtil.isNotEmpty(ssoAuthConfig.getIgnoreAccessUrl())) {
////            httpSecurity.authorizeRequests().antMatchers(ssoAuthConfig.getIgnoreAccessUrl()).permitAll();
////        }
//
//        http
//                // 过滤请求
//                .authorizeRequests()
//                // 自定义权限验证规则
//                .anyRequest().access("@ss.hasPermission()")
//                .and()
//                //禁用自带的页面表单登陆，自定义登陆接口
//                .formLogin().disable()
//                .logout()
//                .logoutUrl("/logout")
//                //退出登陆成功
//                .logoutSuccessHandler(logoutSuccessHandler).permitAll()
//                .and()
//                .headers().frameOptions().sameOrigin();
//
//        http.exceptionHandling()
//                // 已认证用户访问无权限资源时的异常
//                .accessDeniedHandler(new AccessDeniedHandlerImpl())
//                // 匿名用户访问无权限资源时的异常
//                .authenticationEntryPoint(new CustomAuthEntryPoint());

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//        auth.authenticationProvider(usernameAuthenticationProvider()).authenticationProvider(mobileCodeAuthenticationProvider());

    }

    /**
     * 配置共享session
     * @return
     */
    @Bean
    public SpringSessionBackedSessionRegistry sessionRegistry(){
        return new SpringSessionBackedSessionRegistry(this.sessionRepository);
    }

//    @Bean
//    public MobileCodeAuthenticationProvider mobileCodeAuthenticationProvider() {
//        return new MobileCodeAuthenticationProvider();
//    }
//
//    @Bean
//    public UsernameAuthenticationProvider usernameAuthenticationProvider() {
//        return new UsernameAuthenticationProvider();
//    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/st/staticRequest");

//        web.ignoring()
//                .antMatchers(HttpMethod.OPTIONS, "/**")
//                .antMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*.css", "/**/*.js")
//                .antMatchers("/swagger-resources")
//                .antMatchers("/swagger-resources/configuration/ui")
//                .antMatchers("/swagger-resources/configuration/security")
//                .antMatchers("/v2/api-docs")
//                .antMatchers("/v2/api-docs-ext");

    }


}