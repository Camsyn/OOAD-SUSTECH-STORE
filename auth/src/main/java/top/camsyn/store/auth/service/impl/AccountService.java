package top.camsyn.store.auth.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.camsyn.store.commons.entity.Account;
import top.camsyn.store.commons.mapper.AccountMapper;
import top.camsyn.store.auth.model.AuthUser;
import top.camsyn.store.auth.service.IAccountService;
import top.camsyn.store.auth.util.VerifyUtils;
import org.springframework.stereotype.Service;
import top.camsyn.store.commons.service.impl.SuperServiceImpl;


@Slf4j
@Service
@CacheConfig(cacheNames = "auth")
public class AccountService extends SuperServiceImpl<AccountMapper, Account> implements IAccountService {

//    public boolean isAccountExist(String username){
//        return userMapper.selectCount()
//    }

    @Autowired
    PasswordEncoder passwordEncoder;


    @Caching(
            put = {
                @CachePut(key = "targetClass.toString() + #p0.sid"),
                @CachePut(key = "\"findBySid\"+#p0.sid"),
                @CachePut(key = "\"findByEmail\"+#p0.email")
            },
            evict = {
                @CacheEvict(key = "\"isSidExist\"+#p0.sid")
            }
    )
    public Account createAccount(Account user) {
        try {
            if (isSidExist(user.getSid())) {
                log.info("账户已存在, 后面完善saveIdempotency方法后去除此判断");
                return user;
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            saveIdempotency(user, null, null, null, "以后填充");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Account getLoginAccount(AuthUser user) {
        final String username = user.getUsername();
        if (VerifyUtils.isSustechSid(username)) {
            return findBySid(Integer.parseInt(username));
        } else if (VerifyUtils.isSustechEmail(username)) {
            return findByEmail(username);
        }
        return null;
    }

    @Override
    public Account getLoginAccount(String id) {
        if (VerifyUtils.isSustechSid(id)) {
            return findBySid(Integer.parseInt(id));
        } else {
            return findByEmail(id);
        }
//        return null;
    }

    @Cacheable(key = "getMethodName()+#p0")
    public boolean isSidExist(int sid) {
        return findBySid(sid) != null;
    }

    @Cacheable(key = "getMethodName() + #p0")
    @Override
    public Account findBySid(int sid) {
        return baseMapper.selectOne(new QueryWrapper<Account>().eq("sid", sid));
    }

    @Cacheable(key = "getMethodName() + #p0")
    @Override
    public Account findByEmail(String email) {
        return baseMapper.selectOne(new QueryWrapper<Account>().eq("email", email));
    }


    @Caching(
            put = {
                    @CachePut(key = "targetClass.toString() + #p0.sid"),
                    @CachePut(key = "\"findBySid\"+#p0.sid"),
                    @CachePut(key = "\"findByEmail\"+#p0.email")
            },
            evict = {
                    @CacheEvict(key = "\"isSidExist\"+#p0.sid")
            }
    )
    @Override
    public Account modifyPassword(Account account, String newPwd) {
        account.setPassword(passwordEncoder.encode(newPwd));
        return updateById(account)?account:null;
    }

    public boolean comparePassword(String originalPwd, String encryptedPwd) {
        if (originalPwd == null) return false;
        return passwordEncoder.matches(originalPwd, encryptedPwd);
    }

}