package top.camsyn.store;

import top.camsyn.store.auth.service.impl.AuthMailService;

public class Test {
    public static void main(String[] args) {

        final AuthMailService mailService = new AuthMailService();
        mailService.sendVerificationLink("11910620@mail.sustech.edu.cn", "乱填的");
    }
}
