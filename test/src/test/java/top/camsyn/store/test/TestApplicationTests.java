package top.camsyn.store.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import top.camsyn.store.commons.entity.User;
import top.camsyn.store.test.mapper.UserMapper;
import top.camsyn.store.commons.repository.RedisRepository;
import top.camsyn.store.test.service.UserService;
;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class TestApplicationTests {

    @Resource
    UserMapper userMapper;

    @Autowired
    UserService userService;


    @Autowired
    ApplicationContext context;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    RedisRepository redisRepository;

    @Value("${server.port}")
    String port;

    @Test
    void contextLoads() {
//        System.out.println(context.getBean("redisRepository"));
//        final User camsyn = User.builder().nickname("camsyn").email("11911626@mail.sustech.edu.cn").sid(11911626).pwd("123456").build();
//        userMapper.insert(camsyn);
//        final HashMap<String, Object> sid = new HashMap<>();
//        sid.put("sid",11911626);
//        System.out.println(userMapper.selectByMap(sid));
//        System.out.println(userService.list());
//        redisRepository.setExpire("test","Hello World!",10, TimeUnit.SECONDS);
//        System.out.println(redisRepository.get("test"));
//        redisTemplate.opsForValue().set("hello","hello world!");
        System.out.println(port);
    }

}
