package top.camsyn.store.auth.handler;

import com.alibaba.nacos.common.model.RestResult;
import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import top.camsyn.store.commons.model.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
//    @Autowired
//    private TokenStore tokenStore;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//        String accessToken = httpServletRequest.getHeader("authorization");
//        if(StringUtils.isNotBlank(accessToken)){
//            OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(accessToken.replace("Bearer ",""));
//            if(oAuth2AccessToken != null){
//                log.info("处理登出请求, 删除redisToken中的值");
//                tokenStore.removeAccessToken(oAuth2AccessToken);
//                OAuth2RefreshToken oAuth2RefreshToken = oAuth2AccessToken.getRefreshToken();
//                tokenStore.removeRefreshToken(oAuth2RefreshToken);
//                tokenStore.removeAccessTokenUsingRefreshToken(oAuth2RefreshToken);
//            }
//        }


        httpServletResponse.setContentType("text/json;charset=utf-8");
        PrintWriter writer = httpServletResponse.getWriter();
        writer.print(JSONObject.toJSONString(Result.succeed("登出成功")));
        writer.close();
        writer.flush();
    }
}
