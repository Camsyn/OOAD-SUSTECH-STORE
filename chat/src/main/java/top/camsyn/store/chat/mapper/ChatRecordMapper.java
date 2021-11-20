package top.camsyn.store.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.camsyn.store.commons.entity.chat.ChatRecord;

import java.util.List;

@Mapper
public interface ChatRecordMapper extends BaseMapper<ChatRecord> {
    /**
     * 根据聊天记录的最新发送时间排序的相关用户列表
     */
    @Select("with t as (select *\n" +
            "           from chat_record cr\n" +
            "           where #{sid} in (cr.send_id, cr.recv_id)\n" +
            "             and cr.send_time = (select max(cr1.send_time) st\n" +
            "                                 from chat_record cr1\n" +
            "                                 where cr.send_id = cr1.send_id and cr.recv_id = cr1.recv_id\n" +
            "                                    or cr.send_id = cr1.recv_id and cr.recv_id = cr1.send_id)\n" +
            "           order by send_time)\n" +
            "(select t.send_id as other from t where t.recv_id = #{sid})\n" +
            "union\n" +
            "(select t.recv_id as other from t where t.send_id = #{sid});")
    List<Integer> getRelevantChatUser(@Param("sid") Integer sid);
}
