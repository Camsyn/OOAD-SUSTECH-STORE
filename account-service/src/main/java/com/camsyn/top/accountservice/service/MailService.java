package com.camsyn.top.accountservice.service;

import lombok.Getter;
import lombok.Setter;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.UUID;


/**
 * @author Chen_Kunqiu
 */
@Setter
@Getter
@Service
public class MailService {

    private static JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    private static final String verifyLinkInfo = "欢迎注册SUSTECH STORE账号, 请点击以下链接完成认证!\n" +
            "http://camsyn.top:8001/account/verify?id=%s";
    private static final String verifyCodeInfo = "欢迎注册SUSTECH STORE账号, 以下是验证码, 请复制后填写到账户注册界面!\n" +
            "验证码:  %s";
    private static Properties prop = new Properties();

    static {
//        prop.put("mail.smtp.host", "smtp.qq.com");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.timeout", "25000");

        mailSender.setHost("smtp.qq.com");
        mailSender.setUsername("camsyn@foxmail.com");
        mailSender.setPassword("gietkfokhyjwdeag");
        mailSender.setJavaMailProperties(prop);
    }


    public boolean sendVerificationLink(String mailAddr, String verifyId) {
        return sendMail(mailAddr, "账户注册验证", String.format(verifyLinkInfo, verifyId));
    }
    public boolean sendVerifyCode(String mailAddr, String VerifyCode) {
        return sendMail(mailAddr, "账户注册验证", String.format(verifyCodeInfo, VerifyCode));
    }

    //发送验证码的方法,to是目标邮箱地址，text是发送的验证码（事先生成）
    public boolean sendMail(String to, String subject, String text) {
        System.out.println("sendMail...util...");

        try {
            //设定mail server
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            // 设置收件人，寄件人 用数组发送多个邮件
            // String[] array = new String[]    {"sun111@163.com","sun222@sohu.com"};
            // mailMessage.setTo(array);

            mailMessage.setTo(to);
            mailMessage.setFrom("camsyn@foxmail.com");
            mailMessage.setSubject("SUSTECH STORE " + subject);
            mailMessage.setText(text);

            //发送邮件
            mailSender.send(mailMessage);

            System.out.println("发送邮件成功");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送邮件失败");
            return false;
        }
    }

    public static void main(String[] args) {
//        System.out.println(UUID.randomUUID());
        final MailService mailService = new MailService();
        mailService.sendVerificationLink("11911626@mail.sustech.edu.cn",UUID.randomUUID().toString());
    }
}



