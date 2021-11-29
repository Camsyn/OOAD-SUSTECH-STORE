package top.camsyn.store.gateway.props;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@ToString
@Component
@ConfigurationProperties(prefix="secure.ignore")
public class OAuthProperties {

    List<String> urls;
    @Value("${secure.client-id}")
    String clientId;
    @Value("${secure.client-secret}")
    String clientSecret;
}
