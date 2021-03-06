package top.camsyn.store.auth.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth")
public class AuthProperties {
    public String loginPage="http://localhost:8001/custom-login.html";
    public String loginProcessingUrl="/uaa/login";
    public String logoutProcessingUrl="/uaa/logout";
    public String verifyMailUrl = "http://camsyn.top:8001/verify/mail";
}
