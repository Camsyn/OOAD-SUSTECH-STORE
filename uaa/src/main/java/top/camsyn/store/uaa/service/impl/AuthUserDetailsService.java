package top.camsyn.store.uaa.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.camsyn.store.commons.entity.User;
import top.camsyn.store.uaa.model.AuthUser;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    AccountService accountService;

//    @Autowired
//    PermissionS

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username:{}",username);
        final User loginUser = accountService.getLoginUser(username);
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        return new AuthUser(loginUser,new ArrayList<>());
    }
}
