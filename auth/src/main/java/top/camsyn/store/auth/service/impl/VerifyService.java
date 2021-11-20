package top.camsyn.store.auth.service.impl;


import top.camsyn.store.commons.entity.auth.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.camsyn.store.commons.repository.MyRedisRepository;


import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class VerifyService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MyRedisRepository myRedisRepository;;

    private static final String CHAR = "asdfghjkklqwertyuiopzxcvbnm1234567890QWERTYUIOPASDFGHJKLZXCVBNM";
    private static final int CHAR_NUM = 62;
    private static final Random random= new Random();

    public String generateVerifyId(Account user){
        final String vId = UUID.randomUUID().toString();
        myRedisRepository.setExpire(vId, user,6, TimeUnit.HOURS);
        return vId;
    }

    public String generateCaptcha(String key){
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(CHAR.charAt(random.nextInt(CHAR_NUM)));
        }
        myRedisRepository.setExpire(key,sb.toString(),6, TimeUnit.HOURS);
        return sb.toString();
    }

    public void terminateVerifying(String vid){
        myRedisRepository.del(vid);
    }

    public boolean isKeyExist(String key){
        return myRedisRepository.exists(key);
    }

    public Object getValByKey(String key){
        return myRedisRepository.get(key);
    }


    public boolean verifyUserExist(int sid) {
        return accountService.isSidExist(sid);
    }

    public boolean verifyUserExist(String email) {
        return accountService.findByEmail(email) != null;
    }
}
