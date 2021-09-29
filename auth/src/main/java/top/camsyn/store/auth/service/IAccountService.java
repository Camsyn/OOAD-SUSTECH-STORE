package top.camsyn.store.auth.service;

import top.camsyn.store.auth.model.AuthUser;
import top.camsyn.store.commons.entity.Account;

public interface IAccountService {


    Account getLoginAccount(AuthUser user);

    Account getLoginAccount(String id);

    Account findBySid(int sid);

    Account findByEmail(String email);

    Account modifyPassword(Account account, String newPwd);
}
