//package top.camsyn.store.auth.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//
//@Configuration
//@EnableAuthorizationServer
////@AutoConfigureAfter(WebSecurityConfig.class)
//public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("store")
//                .secret(passwordEncoder.encode("123456"))
//                .autoApprove(true)
//                .redirectUris("http://localhost:8002/login", "http://localhost:8003/login","http://localhost:8004/login")
//                .scopes("all")
//                .accessTokenValiditySeconds(7200)
//                .authorizedGrantTypes("authorization_code","password","refresh_token");
//
//    }
//}