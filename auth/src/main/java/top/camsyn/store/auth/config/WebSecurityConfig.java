//package top.camsyn.store.auth.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.PasswordEncoder;
////import org.springframework.session.FindByIndexNameSessionRepository;
////import org.springframework.session.security.SpringSessionBackedSessionRegistry;
//
//
///**
// * @author Chen_Kunqiu
// */
////@EnableWebSecurity
//@Configuration
//@Order(1)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/custom-login.html", "/css/**", "/js/**", "/images/**");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.requestMatchers()
//                .antMatchers("/login")
//                .antMatchers("/oauth/authorize")
//                .and()
//                .authorizeRequests().anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/custom-login.html")
//                .loginProcessingUrl("/login")
//                .permitAll()
//                .and()
//                .logout().logoutUrl("/logout").clearAuthentication(true).deleteCookies("JSESSIONID")
//                .and()
//                .csrf().disable();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("11911626")
//                .password(passwordEncoder.encode("123456"))
//                .roles("admin");
//    }
//}