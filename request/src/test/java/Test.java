import com.alibaba.fastjson.JSON;
import top.camsyn.store.request.dto.SearchDto;

public class Test {
    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(SearchDto.builder().queryStr("132").build(),true));
    }
}
