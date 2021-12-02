package top.camsyn.store.auth.props;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
public class CommonsProperties {
    @Value("${gateway-ip}")
    String gatewayIp;
}
