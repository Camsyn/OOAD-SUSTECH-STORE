package top.camsyn.store.commons.helper;

import top.camsyn.store.commons.entity.chat.ChatRecord;
import top.camsyn.store.commons.entity.chat.CircleMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUrlHelper {
    private static Pattern urlExtractor = Pattern.compile("");
    public static List<String> getUrlLinkInCircleMessage(CircleMessage circleMessage){
        final String content = circleMessage.getContent();
        final Matcher matcher = urlExtractor.matcher(content);
        final ArrayList<String> urls = new ArrayList<>();
        while (matcher.find()) {
            urls.add(matcher.group(0));
        }
        return urls;
    }
    public static String getUrlLinkInChatRecord(ChatRecord chatRecord){
        if (chatRecord.getType()==0) return "";
        return chatRecord.getContent();
    }
}
