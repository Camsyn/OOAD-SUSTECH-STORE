package top.camsyn.store.auth.handler;




import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import top.camsyn.store.commons.model.CodeEnum;
import top.camsyn.store.commons.model.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAccessDenyHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
        httpServletResponse.setContentType("text/json;charset=utf-8");
        PrintWriter writer = httpServletResponse.getWriter();
        writer.print(JSONObject.toJSONString(Result.of(null, CodeEnum.NoPermission.getCode(), "权限不足")));
        writer.close();
        writer.flush();
    }
}

