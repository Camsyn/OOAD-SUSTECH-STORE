package top.camsyn.store.commons.helper;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.camsyn.store.commons.entity.auth.Account;
import top.camsyn.store.commons.entity.user.User;
import top.camsyn.store.commons.model.UserDto;

import javax.servlet.http.HttpServletRequest;

public class UaaHelper {
    public static int getLoginSid() {
        return getCurrentUser().getSid();
    }

    public static UserDto getCurrentUser(){
        //从Header中获取用户信息
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String userStr = request.getHeader("user");
        JSONObject userJsonObject = new JSONObject(userStr);
        UserDto userDTO = new UserDto();
        userDTO.setUsername(userJsonObject.getStr("user_name"));
        userDTO.setSid(Convert.toInt(userJsonObject.get("sid")));
        userDTO.setRoles(Convert.toList(String.class,userJsonObject.get("authorities")));
        return userDTO;
    }

    public static boolean checkUser(User user){
        return getLoginSid()==user.getSid();
    }
    public static boolean checkAccount(Account account){
        return getLoginSid()==account.getSid();
    }
}
