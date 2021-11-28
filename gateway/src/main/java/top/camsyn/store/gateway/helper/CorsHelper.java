package top.camsyn.store.gateway.helper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

public class CorsHelper {
    public static ServerHttpResponse resolve(ServerHttpRequest req, ServerHttpResponse resp){
        final HttpHeaders reqHeaders = req.getHeaders();
        String origin = reqHeaders.getFirst("Origin");
        if (origin == null || "null".equals(origin) || origin.isEmpty()){
            final String referer = reqHeaders.getFirst("Referer");
            if (referer!=null && referer.startsWith("http")){
                int end = referer.indexOf("/", 8);
                origin = referer.substring(0, end);
            }
            if (origin!=null && origin.isEmpty()) origin = null;
        }
        resp.setStatusCode(HttpStatus.OK);
        resp.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin == null ? "*" : origin);
        resp.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        resp.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, OPTIONS, DELETE, PUT");
        resp.getHeaders().add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
        resp.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "x-requested-with,Authorization,cookie,Origin,Access_Token,token");
        return resp;
    }
}
