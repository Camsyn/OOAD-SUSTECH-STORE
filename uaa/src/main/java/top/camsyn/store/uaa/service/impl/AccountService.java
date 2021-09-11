package top.camsyn.store.uaa.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.camsyn.store.commons.entity.User;
import org.springframework.stereotype.Service;
import top.camsyn.store.commons.service.impl.SuperServiceImpl;
import top.camsyn.store.uaa.mapper.UserMapper;
import top.camsyn.store.uaa.model.AuthUser;
import top.camsyn.store.uaa.service.IAccountService;
import top.camsyn.store.uaa.util.VerifyUtils;


@Service
public class AccountService extends SuperServiceImpl<UserMapper, User> implements IAccountService {

//    public boolean isAccountExist(String username){
//        return userMapper.selectCount()
//    }

    @Autowired
    PasswordEncoder passwordEncoder;


    @Caching(
            put = {
                    @CachePut(value = "uaa", key = "targetClass.toString() + #p0.sid"),
                    @CachePut(value = "uaa", key = "\"findBySid\"+#p0.sid"),
                    @CachePut(value = "uaa", key = "\"findByEmail\"+#p0.email")
            }
    )
    public User createAccount(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            saveIdempotency(user, null, null, null, "以后填充");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User getLoginUser(AuthUser user) {
        final String username = user.getUsername();
        if (VerifyUtils.isSustechSid(username)){
            return findBySid(Integer.parseInt(username));
        }else if (VerifyUtils.isSustechEmail(username)){
            return findByEmail(username);
        }
        return null;
    }

    @Override
    public User getLoginUser(String id) {
        if (VerifyUtils.isSustechSid(id)){
            return findBySid(Integer.parseInt(id));
        }else {
            return findByEmail(id);
        }
//        return null;
    }

    @Cacheable(value = "uaa", key = "getMethodName() + #p0")
    @Override
    public User findBySid(int sid) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("sid", sid));
    }

    @Cacheable(value = "uaa", key = "getMethodName() + #p0")
    @Override
    public User findByEmail(String email) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("email", email));
    }

    public boolean comparePassword(String originalPwd, String encryptedPwd) {
        if (originalPwd == null) return false;
        return passwordEncoder.matches(originalPwd, encryptedPwd);
    }

}