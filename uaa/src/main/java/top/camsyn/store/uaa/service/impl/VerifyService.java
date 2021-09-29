package top.camsyn.store.uaa.service.impl;

import top.camsyn.store.commons.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.camsyn.store.commons.repository.RedisRepository;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class VerifyService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RedisRepository redisRepository;;


    public String generateVerifyId(Account user){
        final String vId = UUID.randomUUID().toString();
        redisRepository.setExpire(vId, user,6, TimeUnit.HOURS);
        return vId;
    }

    public void terminateVerifying(String vid){
        redisRepository.del(vid);
    }

    public boolean verifyVidExist(String vid){
        return redisRepository.exists(vid);
    }

    public Object getUserByVerifyId(String vid){
        return redisRepository.get(vid);
    }


    public boolean verifyUserExist(int sid) {
        return accountService.isSidExist(sid);
    }

    public boolean verifyUserExist(String email) {
        return accountService.findByEmail(email) != null;
    }
}
