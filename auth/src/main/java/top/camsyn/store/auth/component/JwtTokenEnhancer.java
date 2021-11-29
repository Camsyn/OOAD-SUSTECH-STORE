package top.camsyn.store.auth.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;
import top.camsyn.store.auth.model.AuthUser;

import java.util.HashMap;
import java.util.Map;

/**
 * JWT内容增强器
 * Created by macro on 2020/6/19.
 */
@Component
@Slf4j
public class JwtTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        Map<String, Object> info = new HashMap<>();
        //把用户SID设置到JWT中
        info.put("sid", authUser.getUsername());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        log.info("enhance jwt token: {}", accessToken);
        return accessToken;
    }
}
