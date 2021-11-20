//package top.camsyn.store.commons.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//
//@Configuration
//public class CorsConfig {
//
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("*");//修改为添加而不是设置，* 最好改为实际的需要，我这是非生产配置，所以粗暴了一点
//        configuration.addAllowedMethod("*");//修改为添加而不是设置
//        configuration.addAllowedHeader("*");//这里很重要，起码需要允许 Access-Control-Allow-Origin
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}
