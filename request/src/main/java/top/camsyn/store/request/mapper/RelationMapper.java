//package top.camsyn.store.request.mapper;
//
//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.annotations.Update;
//import top.camsyn.store.commons.entity.request.LabelRequestRelation;
//
//import java.util.List;
//
//@Mapper
//public interface RelationMapper extends BaseMapper<LabelRequestRelation> {
//    @Update("<script>\n" +
//            "    update request_label_relation rlr set deleted = 1 " +
//            "    <where>\n" +
//            "        request_id = ${rId}" +
//            "        <if test='labels_ids != null and labels_ids.isEmpty() != true'>\n" +
//            "            and\n" +
//            "            label_id in\n" +
//            "            <foreach collection=\"labels_ids\" item=\"label\" open=\"(\" close=\")\" separator=\",\" index=\"i\">\n" +
//            "                ${label}\n" +
//            "            </foreach>\n" +
//            "        </if>\n" +
//            "    </where>\n" +
//            "</script>")
//    void removeBatch(@Param("labels_ids") List<Integer> labelIds, @Param("rId") Integer requestId);
//}
