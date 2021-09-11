package top.camsyn.store.test.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.camsyn.store.commons.entity.User;

import java.util.ArrayList;

@Slf4j
@Service(value = "userDetailsService")
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

//    @Autowired
//    PermissionS

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username:{}",username);
        final User loginUser = userService.getOne(new QueryWrapper<User>().eq("sid",username));
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        log.info("user:{}", loginUser);
        if (loginUser==null){
            throw new UsernameNotFoundException(username+" cannot be found!");
        }
        return new org.springframework.security.core.userdetails.User(loginUser.getSid()+"",loginUser.getPassword(),new ArrayList<>());
    }
}
