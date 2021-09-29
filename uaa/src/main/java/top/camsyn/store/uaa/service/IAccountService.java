package top.camsyn.store.uaa.service;

import top.camsyn.store.commons.entity.Account;
import top.camsyn.store.uaa.model.AuthUser;

public interface IAccountService {


    Account getLoginUser(AuthUser user);

    Account getLoginUser(String id);

    Account findBySid(int sid);

    Account findByEmail(String email);
}
