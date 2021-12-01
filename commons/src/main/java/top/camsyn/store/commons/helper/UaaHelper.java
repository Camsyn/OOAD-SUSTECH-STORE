package top.camsyn.store.commons.helper;


import com.alibaba.fastjson.JSON;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.camsyn.store.commons.entity.auth.Account;
import top.camsyn.store.commons.entity.user.User;
import top.camsyn.store.commons.exception.NotSelfException;
import top.camsyn.store.commons.model.UserDto;

import javax.servlet.http.HttpServletRequest;

public class UaaHelper {
    public static int getLoginSid() {
        return getCurrentUser().getSid();
    }

    public static UserDto getCurrentUser(){
        //从Header中获取用户信息
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            return new UserDto();
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String userStr = request.getHeader("user");
        return JSON.parseObject(userStr, UserDto.class);
    }

    public static UserDto getUser(String userStr){
        return JSON.parseObject(userStr, UserDto.class);
    }

    public static boolean checkUser(User user){
        return getLoginSid()==user.getSid();
    }
    public static boolean checkAccount(Account account){
        return getLoginSid()==account.getSid();
    }

    public static boolean assertAdmin(Integer sid){
        if (getLoginSid() == sid) return true;
        throw new NotSelfException();
    }

}
