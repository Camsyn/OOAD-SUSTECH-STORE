package top.camsyn.store.commons.helper;


import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.camsyn.store.commons.constant.AuthConstant;
import top.camsyn.store.commons.entity.auth.Account;
import top.camsyn.store.commons.entity.user.User;
import top.camsyn.store.commons.exception.AuthException;
import top.camsyn.store.commons.exception.NotSelfException;
import top.camsyn.store.commons.model.UserDto;

import javax.servlet.http.HttpServletRequest;

public class UaaHelper {
    public static int getLoginSid() {
        return getCurrentUser().getSid();
    }

    @SneakyThrows
    public static UserDto getCurrentUser() {
        //从Header中获取用户信息
        String userStr = getCurrentUserStr();
        try{
            return JSON.parseObject(userStr, UserDto.class);
        }catch (Exception e){
            throw new AuthException("user 格式不对");
        }
    }

    public static UserDto getUser(String userStr) {
        return JSON.parseObject(userStr, UserDto.class);
    }

    @SneakyThrows
    public static String getCurrentUserStr() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            throw new AuthException("无法得到请求属性");
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();
        final String user = request.getHeader(AuthConstant.UAA_HEADER);
        if (StringUtils.isEmpty(user)) {
            throw new AuthException("无法得到 user 请求头");
        }
        return user;
//        return "ckq";
    }

    public static boolean checkUser(User user) {
        return getLoginSid() == user.getSid();
    }

    public static boolean checkAccount(Account account) {
        return getLoginSid() == account.getSid();
    }

    public static UserDto assertAdmin(Integer sid) {
        final UserDto user = getCurrentUser();
        if (user.getSid().equals(sid)) return user;
        throw new NotSelfException();
    }

}
