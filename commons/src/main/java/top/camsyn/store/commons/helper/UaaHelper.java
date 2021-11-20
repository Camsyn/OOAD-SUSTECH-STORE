package top.camsyn.store.commons.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import top.camsyn.store.commons.entity.auth.Account;
import top.camsyn.store.commons.entity.user.User;

public class UaaHelper {
    public static int getLoginSid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return Integer.parseInt(name);
    }

    public static boolean checkUser(User user){
        return getLoginSid()==user.getSid();
    }
    public static boolean checkAccount(Account account){
        return getLoginSid()==account.getSid();
    }
}
