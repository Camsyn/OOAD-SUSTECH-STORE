//package top.camsyn.store.commons.config;
//
//
//import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
//import org.apache.tomcat.util.http.SameSiteCookies;
//import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class TomcatConfig {
//
//    @Bean
//    public TomcatContextCustomizer sameSiteCookiesConfig() {
//        return context -> {
//            final Rfc6265CookieProcessor cookieProcessor = new Rfc6265CookieProcessor();
//            // 设置Cookie的SameSite
//            cookieProcessor.setSameSiteCookies(SameSiteCookies.NONE.getValue());
//
//            context.setCookieProcessor(cookieProcessor);
//        };
//    }
//}