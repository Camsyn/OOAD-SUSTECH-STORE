package com.camsyn.top.accountservice.service;

import com.camsyn.top.accountservice.entity.User;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    public void createAccount(User user) {
        final String mail = user.getMail();

    }
}
