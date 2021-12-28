package top.camsyn.store.review.Mapper;

import top.camsyn.store.commons.entity.review.ReviewLog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReviewLogMapper {

    @Select("select * from r_log order by time desc")
    List<ReviewLog> selectAll();

    @Select("select * from r_log where r_id = #{R_id} order by time desc")
    ReviewLog selectByR_id(int R_id);

    @Select("select * from r_log where initiator = #{i_id} order by time desc")
    List<ReviewLog> selectReportRecord(int i_id);

    @Select("select * from r_log where state = #{state} order by time desc")
    List<ReviewLog> selectByState(int state);

    @Select("select * from r_log where target = #{t_id} and category = #{category} and initiator != -1 order by time desc")
    List<ReviewLog> selectByTC(int t_id, int category);

    @Select("select * from r_log where target = #{t_id} and category = #{category} and initiator = -1 order by time desc")
    List<ReviewLog> selectSendingByTC(int t_id, int category);

    @Select("select count(*) from t_sensitive_words where LOCATE(word,#{desc})>0")
    int review(String desc);

    @Options(useGeneratedKeys = true, keyProperty = "r_id")
    @Insert("insert into r_log(initiator, target, description, time, state, category) values(#{initiator}, #{target}, #{description}, #{time}, #{state}, #{category})")
    void insert(ReviewLog reviewLog);

    @Update("update r_log set description=#{description}, time=#{time}, state=#{state} where r_id=#{r_id}")
    void update(ReviewLog reviewLog);
}
