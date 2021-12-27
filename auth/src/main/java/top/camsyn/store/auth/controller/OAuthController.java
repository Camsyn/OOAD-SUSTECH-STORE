package top.camsyn.store.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.camsyn.store.auth.model.Oauth2TokenDto;
import top.camsyn.store.commons.model.Result;

import java.security.Principal;
import java.util.Map;


/**
 * 自定义Oauth2获取令牌接口
 */
@Slf4j
@RestController
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    private TokenEndpoint tokenEndpoint;

    /**
     * Oauth2登录认证
     */
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public Result<Oauth2TokenDto> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        log.info("开始postAccessToken principal: {} parameters: {}",principal,parameters);
        if (parameters.containsKey("password"))
            parameters.put("grant_type","password");
        else if (parameters.containsKey("refresh_token"))
            parameters.put("grant_type","refresh_token");
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
                .token(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .tokenHead("Bearer ").build();
        log.info("登录成功");
        return Result.succeed(oauth2TokenDto, "登录成功");
    }

}
