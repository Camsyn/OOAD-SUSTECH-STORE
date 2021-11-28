package top.camsyn.store.commons.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
@ConditionalOnMissingClass({"top.camsyn.store.gateway.GatewayApplication","top.camsyn.store.auth.AuthApplication"})
public class CommonsProperties {
    @Value("${gateway-ip}")
    String gatewayIp;

    @Value("${spring.application.name}")
    String appName;
}
