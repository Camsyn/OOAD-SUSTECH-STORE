package top.camsyn.store.commons.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UaaHelper {
    public static int getLoginSid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return Integer.parseInt(name);
    }
}
