package top.camsyn.store.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.camsyn.store.auth.handler.CustomAuthEntryPoint;


/**
 * @author Chen_Kunqiu
 */
@Configuration
//@EnableConfigurationProperties(AuthProperties.class)
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private PasswordEncoder passwordEncoder;

    //
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    CustomAuthEntryPoint authEntryPoint;


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
                .authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .antMatchers("/rsa/publicKey").permitAll()
                .antMatchers("/oauth/**").permitAll()
                .anyRequest().permitAll()
//                .anyRequest().authenticated()
                .and()
                .cors()
                .and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authEntryPoint)
        ;
//                .and()
//                .formLogin()
////            .loginPage(authProperties.loginPage)
//                .loginProcessingUrl(authProperties.loginProcessingUrl)
//                .successHandler(loginSuccessHandler)
//                .failureHandler(loginFailHandler)
//                .permitAll()
//                .and()
//                .logout().logoutUrl(authProperties.logoutProcessingUrl)
//                .logoutRequestMatcher(new AntPathRequestMatcher(authProperties.logoutProcessingUrl, "POST"))
//                .logoutSuccessHandler(logoutSuccessHandler).deleteCookies("JSESSIONID")
//                .clearAuthentication(true).invalidateHttpSession(true).permitAll()
//                .and()
//                .cors()
//                .and()
//                .csrf().disable()
//                .exceptionHandling().authenticationEntryPoint(authEntryPoint);


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
