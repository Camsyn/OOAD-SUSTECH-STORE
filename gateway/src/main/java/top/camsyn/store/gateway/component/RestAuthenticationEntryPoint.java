package top.camsyn.store.gateway.component;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import top.camsyn.store.gateway.dto.Result;
import top.camsyn.store.gateway.helper.CorsHelper;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 自定义返回结果：没有登录或token过期时
 * Created by macro on 2020/6/18.
 */
@Component
@Slf4j
public class RestAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        final ServerHttpRequest req = exchange.getRequest();
        log.info("未登录. req: {}", req.getPath());

        ServerHttpResponse response = exchange.getResponse();
        response = CorsHelper.resolve(req,response);

        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        String body= JSON.toJSONString(Result.failed(e.getMessage()));

        DataBuffer buffer =  response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
