package top.camsyn.store.commons.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Slf4j
@Service
public class MailService {

    private static JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

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

    //发送验证码的方法,to是目标邮箱地址，text是发送的验证码（事先生成）
    public boolean sendMail(String to, String subject, String text) {
        log.info("sendMail...util...");

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

            log.info("发送邮件成功");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("发送邮件失败");
            return false;
        }
    }
}
