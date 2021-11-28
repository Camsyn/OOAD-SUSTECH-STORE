package top.camsyn.store.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.nimbusds.jose.JWSObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import top.camsyn.store.gateway.props.OAuthProperties;

import java.net.URI;
import java.text.ParseException;

/**
 * 将登录用户的JWT转化成用户信息的全局过滤器
 * Created by macro on 2020/6/17.
 */
@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    OAuthProperties oAuthProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpRequest req = exchange.getRequest();
        final RequestPath path = req.getPath();
        log.info("请求路径: {}, 请求参数: {}", path, req.getQueryParams());
        //获取请求uri的请求参数（GET请求参数通过拼接key=value形式进行传参）
        if (path.toString().startsWith("/auth/oauth")) {
            URI uri = exchange.getRequest().getURI();
            final URI newUri = appendClientInfo(uri);
            ServerHttpRequest request = exchange.getRequest().mutate().uri(newUri)
                    .build();
            exchange = exchange.mutate().request(request).build();
            log.info("mutate for client info: {}", request.getQueryParams());
        }

        String token = req.getHeaders().getFirst("Authorization");
        if (StrUtil.isEmpty(token)) {
            return chain.filter(exchange);
        }
        try {
            //从token中解析用户信息并设置到Header中去
            String realToken = token.replace("Bearer ", "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            String userStr = jwsObject.getPayload().toString();
            log.info("AuthGlobalFilter.filter() user:{}", userStr);
            ServerHttpRequest request = req.mutate().header("user", userStr).build();

            exchange = exchange.mutate().request(request).build();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    public URI appendClientInfo(URI uri) {
        StringBuilder query = new StringBuilder();
        String originalQuery = uri.getRawQuery();
        //判断最后一个字符是否是&，如果不是则拼接一个&，以备后续的参数进行连接
        if (StringUtils.hasText(originalQuery)) {
            query.append(originalQuery);
            if (originalQuery.charAt(originalQuery.length() - 1) != '&') {
                query.append("&");
            }
        }
        //获取config中的key、value，然后拼接到uri请求参数后面
        // TODO urlencode?
        query.append("client_id");
        query.append("=");
        query.append(oAuthProperties.getClientId());
        query.append("&");
        query.append("client_secret");
        query.append("=");
        query.append(oAuthProperties.getClientSecret());
        //把请求参数重新拼接回去，并放入request中传递到过滤链的下一个请求中去

        return UriComponentsBuilder.fromUri(uri)
                .replaceQuery(query.toString()).build(true).toUri();

    }
}
