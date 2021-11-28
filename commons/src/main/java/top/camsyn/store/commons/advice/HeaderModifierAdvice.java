//package top.camsyn.store.commons.advice;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.http.server.ServletServerHttpRequest;
//import org.springframework.http.server.ServletServerHttpResponse;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//import top.camsyn.store.commons.props.CommonsProperties;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Slf4j
//@ControllerAdvice
//@ConditionalOnMissingClass({"top.camsyn.store.gateway.GatewayApplication","top.camsyn.store.auth.AuthApplication"})
//public class HeaderModifierAdvice implements ResponseBodyAdvice<Object> {
//
//
//    @Autowired
//    CommonsProperties commonsProperties;
//
//    static Pattern login = Pattern.compile("^\\s*https?:\\/\\/[^?]*(?=login)");
//    static Pattern authorize = Pattern.compile("(?<=redirect_uri=).*?(?=&)");
//
//    @Override
//    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
//        return true;
//    }
//
//
//
//    @Override
//    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
//                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
//                                  ServerHttpResponse response) {
//        log.info("修改响应头ing");
//        ServletServerHttpRequest ssReq = (ServletServerHttpRequest)request;
//        ServletServerHttpResponse ssResp = (ServletServerHttpResponse)response;
//        if(ssReq == null || ssResp == null
//                || ssReq.getServletRequest() == null
//                || ssResp.getServletResponse() == null) {
//            return body;
//        }
//
//        // 对于未添加跨域消息头的响应进行处理
//        HttpServletRequest req = ssReq.getServletRequest();
//        HttpServletResponse resp = ssResp.getServletResponse();
//
//
//
//        String location = resp.getHeader(HttpHeaders.LOCATION);
//        log.info("origin location: {}",location);
//
//        if (location==null){
//            return body;
//        }
//        String newLocation="";
//        Matcher m1 = login.matcher(location);
//        if (m1.find()) {
//            newLocation = m1.replaceFirst(commonsProperties.getGatewayIp() + "/" + commonsProperties.getAppName() + "/");
//            resp.setHeader(HttpHeaders.LOCATION, newLocation);
//            log.info("new location: {}",newLocation);
//
//        } else {
//            Matcher m2 = authorize.matcher(location);
//            if (m2.find()) {
//                String redirectUri = m2.group(0);
//                Matcher m3 = login.matcher(redirectUri);
//                if (m3.find()) {
//                    String s = m3.replaceFirst(commonsProperties.getGatewayIp() + "/" + commonsProperties.getAppName() + "/");
//                    newLocation = m2.replaceFirst(s);
//                    resp.setHeader(HttpHeaders.LOCATION, newLocation);
//                    log.info("new location: {}",newLocation);
//                }
//            }
//        }
//
//        return body;
//    }
//}