package top.camsyn.store.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.camsyn.store.auth.model.AuthUser;
import top.camsyn.store.commons.entity.Role;
import top.camsyn.store.commons.entity.Account;


import java.util.List;


@Slf4j
@Service
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    AccountService accountService;

    @Autowired
    RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username:{}",username);
        final Account loginUser = accountService.getLoginAccount(username);
        final List<Role> roles = roleService.list(new QueryWrapper<Role>().eq("owner", loginUser.getSid()));
        log.info("loginUser:{}",loginUser);

//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        return new AuthUser(loginUser, roles);
    }
}
