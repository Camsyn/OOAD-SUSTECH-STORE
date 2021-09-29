package top.camsyn.store.auth.util;

import java.util.regex.Pattern;

public class VerifyUtils {
    public static final Pattern MAIL_PATTERN = Pattern.compile("\\w+@(mail\\.)?sustech\\.edu\\.cn");
    public static boolean isSustechEmail(String email){
        return email.matches("\\w+@(mail\\.)?sustech\\.edu\\.cn");
    }
    public static boolean isSustechSid(String sid){
        return sid.matches("\\d{8}");
    }

}
