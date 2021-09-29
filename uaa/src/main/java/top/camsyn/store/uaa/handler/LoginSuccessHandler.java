package top.camsyn.store.uaa.handler;

import com.alibaba.nacos.common.model.RestResult;
import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        /*LocalUserDetails principal = (LocalUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();*/

        RestResult restResult = new RestResult(0, "登录成功");
        /*Map<String,String> map = new HashMap<>();
        map.put("name", principal.getName());
        map.put("IDCard", principal.getIDCard());
        restResult.setData(map);*/
        httpServletResponse.setContentType("text/json;charset=utf-8");
        PrintWriter writer = httpServletResponse.getWriter();
        writer.print(JSONObject.toJSONString(restResult));
        writer.close();
        writer.flush();
    }
}
