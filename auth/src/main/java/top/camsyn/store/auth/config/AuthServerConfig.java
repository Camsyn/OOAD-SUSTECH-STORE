package top.camsyn.store.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

//    @Autowired
//    AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataSource dataSource;
//
////    @Autowired
////    private TokenStore redisTokenStore;
//
//    @Autowired
//    private UserDetailsService userDetailService;
//
//    @Autowired
//    private ClientDetailsService clientDetailsService;

//    @Bean
//    AuthorizationServerTokenServices tokenServices() {
//        DefaultTokenServices services = new DefaultTokenServices();
//        services.setClientDetailsService(clientDetailsService);
//        services.setSupportRefreshToken(true);
//        services.setTokenStore(redisTokenStore);
//
//        return services;
//    }

//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//
//        endpoints
////                .authenticationManager(authenticationManager)
//                .tokenServices(tokenServices())
//                /*.userDetailsService(userDetailService)*//*.tokenServices(tokenServices())*/;
//    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("store")
//                .secret(passwordEncoder.encode("123456"))
//                .autoApprove(true)
//                .redirectUris("http://localhost:8002/login", "http://localhost:8003/login","http://localhost:8004/login")
//                .scopes("all")
//                .accessTokenValiditySeconds(7200)
//                .authorizedGrantTypes("authorization_code","password","refresh_token");
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);

    }

//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security.tokenKeyAccess("permitAll()")
//                .allowFormAuthenticationForClients()
//                .checkTokenAccess("permitAll()")
//        ;
//    }
    /*
    @Bean
    ClientDetailsService clientDetailsService() {
        final JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        jdbcClientDetailsService.setPasswordEncoder(passwordEncoder);
        return jdbcClientDetailsService;
    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }*/

//    @Bean
//    AuthorizationServerTokenServices tokenServices() {
//        DefaultTokenServices services = new DefaultTokenServices();
//        services.setClientDetailsService(clientDetailsService());
//        services.setSupportRefreshToken(true);
//        services.setTokenStore(redisTokenStore);
//        return services;
//    }
}