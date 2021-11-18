package top.camsyn.store.auth.service.impl;

import org.springframework.stereotype.Service;
import top.camsyn.store.auth.service.IUserService;
import top.camsyn.store.commons.entity.user.User;
import top.camsyn.store.commons.mapper.UserMapper;
import top.camsyn.store.commons.service.impl.SuperServiceImpl;

@Service
public class UserService extends SuperServiceImpl<UserMapper, User> implements IUserService {


    @Override
    public User getOne(int sid) {
        return lambdaQuery().eq(User::getSid, sid).one();
    }
}
