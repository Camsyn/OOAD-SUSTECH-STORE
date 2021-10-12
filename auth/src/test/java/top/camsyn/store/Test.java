package top.camsyn.store;

import top.camsyn.store.auth.service.impl.MailService;

public class Test {
    public static void main(String[] args) {

        final MailService mailService = new MailService();
        mailService.sendVerificationLink("11910620@mail.sustech.edu.cn", "乱填的");
    }
}
