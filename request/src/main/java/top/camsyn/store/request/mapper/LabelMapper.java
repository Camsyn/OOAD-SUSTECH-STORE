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
            "       (#{item}) " +
            "</foreach>\n" +
            "   ON DUPLICATE KEY UPDATE\n" +
            "    push_frequency=push_frequency+1;\n" +
            "</script>")
    void increasePushFreq(@Param("label_list") Collection<String> labels);

    @Insert("<script>\n" +
            "INSERT INTO label\n" +
            "    (label_name)\n" +
            "   VALUES " +
            "<foreach collection=\"label_list\" item=\"item\" index =\"index\" separator =\",\">\n" +
            "       (#{item}) " +
            "</foreach>\n" +
            "   ON DUPLICATE KEY UPDATE\n" +
            "    push_frequency=push_frequency-1;\n" +
            "</script>")
    void declinePushFreq(@Param("label_list") Collection<String> labels);
    @Insert("<script>\n" +
            "INSERT INTO label\n" +
            "    (label_name)\n" +
            "   VALUES " +
            "<foreach collection=\"label_list\" item=\"item\" index =\"index\" separator =\",\">\n" +
            "       (#{item}) " +
            "</foreach>\n" +
            "   ON DUPLICATE KEY UPDATE\n" +
            "    pull_frequency=pull_frequency-1;\n" +
            "</script>")
    void increasePullFreq(@Param("label_list") Collection<String> labels);

}


