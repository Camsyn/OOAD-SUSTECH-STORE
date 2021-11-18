package top.camsyn.store.auth.service;

import top.camsyn.store.commons.entity.user.User;

public interface IUserService {

    User getOne(int sid);
}
