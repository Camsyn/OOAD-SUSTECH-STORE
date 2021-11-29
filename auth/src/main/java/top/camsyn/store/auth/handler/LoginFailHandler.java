package top.camsyn.store.auth.handler;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import top.camsyn.store.commons.model.CodeEnum;
import top.camsyn.store.commons.model.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class LoginFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("text/json;charset=utf-8");
        PrintWriter writer = httpServletResponse.getWriter();
        log.warn("登录失败, {}", e.getMessage());
        writer.print(JSONObject.toJSONString(Result.of(e.getMessage(), CodeEnum.LoginFail.getCode(), "用户名或者密码输入错误，请重新输入")));
        writer.close();
        writer.flush();
    }
}
