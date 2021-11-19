package top.camsyn.store.commons.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import top.camsyn.store.commons.filter.CsrfFilter;
import top.camsyn.store.commons.filter.MyCorsFilter;

@Configuration
@ConditionalOnMissingClass({"top.camsyn.store.gateway.GatewayApplication","top.camsyn.store.auth.AuthApplication"})
@EnableOAuth2Sso
public class CommonSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MyCorsFilter corsFilter;

    @Autowired
    CsrfFilter csrfFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(corsFilter, ChannelProcessingFilter.class)
//                .addFilterBefore(csrfFilter, ChannelProcessingFilter.class)
                .authorizeRequests().anyRequest().authenticated().and().csrf().disable();
//        http.authorizeRequests().anyRequest().permitAll();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers( "/css/**", "/js/**","/html/**", "{static:\\S+\\..*}");
    }
}
