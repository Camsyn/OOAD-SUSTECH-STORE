package top.camsyn.store.test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.camsyn.store.commons.entity.User;
import top.camsyn.store.test.mapper.UserMapper;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
}
