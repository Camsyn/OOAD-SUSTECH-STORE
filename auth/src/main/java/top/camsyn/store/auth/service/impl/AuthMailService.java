package top.camsyn.store.auth.service.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import top.camsyn.store.auth.props.AuthProperties;
import top.camsyn.store.commons.service.MailService;

import java.util.Properties;
import java.util.UUID;


/**
 * @author Chen_Kunqiu
 */
@Setter
@Getter
@Service
@Slf4j
@EnableConfigurationProperties(AuthProperties.class)
public class AuthMailService {

    @Autowired
    MailService mailService;

    private static final String verifyRegisterLinkInfo = "欢迎注册SUSTECH STORE账号, 请点击以下链接完成认证!\n" +
            "http://camsyn.cn:7000/verify/register?id=%s";
    private static final String verifyPassWordModificationCaptcha = "你正在尝试修改SUSTECH STORE账户的密码, 请确认是本人的操作, 复制下面的验证码到修改密码页面完成验证\n" +
            "验证码: %s";
    private static final String verifyRegisterCodeInfo = "欢迎注册SUSTECH STORE账号, 以下是验证码, 请复制后填写到账户注册界面!\n" +
            "验证码: %s";



    public boolean sendVerificationLink(String mailAddr, String verifyId) {
        return mailService.sendMail(mailAddr, "账户注册验证", String.format(verifyRegisterLinkInfo, verifyId));
    }

    public boolean sendVerifyCode(String mailAddr, String VerifyCode) {
        return mailService.sendMail(mailAddr, "账户注册验证", String.format(verifyRegisterCodeInfo, VerifyCode));
    }

    public boolean sendCaptcha(String mailAddr, String captcha) {
        return mailService.sendMail(mailAddr, "账户密码修改", String.format(verifyPassWordModificationCaptcha, captcha));
    }





    public static void main(String[] args) {
//        log.info(UUID.randomUUID());
        final AuthMailService mailService = new AuthMailService();
        mailService.sendVerificationLink("11911626@mail.sustech.edu.cn", UUID.randomUUID().toString());
    }
}



