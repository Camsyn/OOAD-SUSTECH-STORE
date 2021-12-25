package top.camsyn.store.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.camsyn.store.auth.model.AuthUser;
import top.camsyn.store.auth.model.Role;
import top.camsyn.store.commons.entity.auth.Account;
import top.camsyn.store.commons.exception.AuthException;


import java.util.List;


@Slf4j
@Service
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    AccountService accountService;

    @Autowired
    RoleService roleService;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username:{}",username);
        final Account loginUser = accountService.getLoginAccount(username);
        final List<Role> roles = roleService.list(new QueryWrapper<Role>().eq("owner", loginUser.getSid()));
        log.info("loginUser:{}",loginUser);
        if (loginUser.getState()==1){
            throw new AuthException("账号已封禁");
        }
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        return new AuthUser(loginUser, roles);
    }
}
