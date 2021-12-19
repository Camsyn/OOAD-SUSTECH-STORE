package top.camsyn.store.commons.client.callback;

import top.camsyn.store.commons.client.UserClient;
import top.camsyn.store.commons.entity.user.User;
import top.camsyn.store.commons.model.Result;

public class UserHystrix implements UserClient {


    @Override
    public Result<User> getUser(Integer sid) {
        return null;
    }

    @Override
    public Result<User> updateUser(User user) {
        return null;
    }

    @Override
    public Result<User> changeLiyuan(Integer sid, Double delta) {
        return null;
    }

    @Override
    public Result<Boolean> changeLiyuan(Integer adder, Integer subscriber, Double delta) {
        return null;
    }

}
