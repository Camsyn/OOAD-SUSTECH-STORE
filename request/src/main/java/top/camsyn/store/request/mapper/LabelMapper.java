package top.camsyn.store.request.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.camsyn.store.commons.entity.request.Label;

import java.util.Collection;
import java.util.List;

@Mapper
public interface LabelMapper extends BaseMapper<Label> {

    @Insert("<script>\n" +
            "INSERT INTO label\n" +
            "    (label_name)\n" +
            "   VALUES " +
            "<foreach collection=\"label_list\" item=\"item\" index =\"index\" separator =\",\">\n" +
            "       (\n" +
            "       #{item},\n" +
            "       ) " +
            "</foreach>\n" +
            "   ON DUPLICATE KEY UPDATE\n" +
            "    frequency=frequency+1;\n" +
            "</script>")
    void increaseFreq(@Param("label_list") Collection<String> labels);

    @Insert("<script>\n" +
            "INSERT INTO label\n" +
            "    (label_name)\n" +
            "   VALUES " +
            "<foreach collection=\"label_list\" item=\"item\" index =\"index\" separator =\",\">\n" +
            "       (\n" +
            "       #{item},\n" +
            "       ) " +
            "</foreach>\n" +
            "   ON DUPLICATE KEY UPDATE\n" +
            "    frequency=frequency-1;\n" +
            "</script>")
    void declineFreq(@Param("label_list") Collection<String> labels);

}


