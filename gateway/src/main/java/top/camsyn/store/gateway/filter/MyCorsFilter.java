package top.camsyn.store.gateway.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import top.camsyn.store.gateway.helper.CorsHelper;


import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class MyCorsFilter implements GlobalFilter, Ordered {
    static Set<String> allowedOrigins = new HashSet<>();

    static {
        allowedOrigins.add("http://localhost:8081");
        allowedOrigins.add("http://camsyn.top");
        allowedOrigins.add("http://camsyn.cn");
        allowedOrigins.add("https://localhost:8081");
        allowedOrigins.add("https://camsyn.top");
        allowedOrigins.add("https://camsyn.cn");
    }



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpRequest req = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        log.info("请求路径: {}, 请求参数: {}", req.getPath(), req.getQueryParams());

        response = CorsHelper.resolve(req,response);

        exchange = exchange.mutate().response(response).build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
