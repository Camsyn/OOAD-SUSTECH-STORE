package top.camsyn.store.test.handler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.model.RestResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        RestResult restResult = new RestResult(0, "登出成功");
        httpServletResponse.setContentType("text/json;charset=utf-8");
        PrintWriter writer = httpServletResponse.getWriter();
        writer.print(JSONObject.toJSONString(restResult));
        writer.close();
        writer.flush();
    }
}
