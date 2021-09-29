package com.example.authn.service;

import top.camsyn.store.commons.entity.User;
import top.camsyn.store.uaa.model.AuthUser;

public interface IAccountService {


    User getLoginUser(AuthUser user);

    User getLoginUser(String id);

    User findBySid(int sid);

    User findByEmail(String email);
}
