//package top.camsyn.store.commons.filter;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Enumeration;
//import java.util.HashSet;
//import java.util.Set;
//
//
//@Slf4j
//@Component
//public class MyCorsFilter implements Filter {
//    static Set<String> allowedOrigins = new HashSet<>();
//
//    static {
//        allowedOrigins.add("http://localhost:8081");
//        allowedOrigins.add("http://camsyn.top");
//        allowedOrigins.add("http://camsyn.cn");
//        allowedOrigins.add("https://localhost:8081");
//        allowedOrigins.add("https://camsyn.top");
//        allowedOrigins.add("https://camsyn.cn");
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException, IOException {
//        HttpServletResponse response = (HttpServletResponse) res;
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        String origin = httpRequest.getHeader("Origin");
//        log.info("收到请求： url: {}, origin: {}", httpRequest.getRequestURI(), httpRequest.getHeader("Origin"));
////        if (allowedOrigins.contains(origin)){
////            response.setHeader("Access-Control-Allow-Origin", origin);
////        }else {
////            response.setHeader("Access-Control-Allow-Origin", "*");
////        }
//
//        response.setHeader("Access-Control-Allow-Origin", origin == null ? "*" : origin);
//
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
//        chain.doFilter(request, response);
//    }
//
//}
