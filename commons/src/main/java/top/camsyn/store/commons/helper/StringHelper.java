package top.camsyn.store.commons.helper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringHelper {
    public static List<String> parseUrlList(String urls){
        return Arrays.stream(urls.split(",")).map(String::trim).collect(Collectors.toList());
    }
}
