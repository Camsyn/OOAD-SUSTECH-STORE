package top.camsyn.store.review.Mapper;

import top.camsyn.store.review.domain.ReviewLog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReviewLogMapper {

    @Select("select * from r_log")
    public List<ReviewLog> selectAll();

    @Select("select * from r_log where r_id = #{R_id}")
    public ReviewLog selectByR_id(int R_id);

    @Select("select * from r_log where initiator = #{i_id}")
    public List<ReviewLog> selectReportRecord(int i_id);

    @Select("select * from r_log where state = #{state}")
    public List<ReviewLog> selectByState(int state);

    @Select("select * from r_log where target = #{t_id} and category = #{category}")
    public List<ReviewLog> selectByTC(int t_id, int category);

    @Select("select count(*) from t_sensitive_words where LOCATE(word,#{desc})>0")
    public int review(String desc);

    @Options(useGeneratedKeys = true, keyProperty = "r_id")
    @Insert("insert into r_log(initiator, target, description, time, state, category) values(#{initiator}, #{target}, #{description}, #{time}, #{state}, #{category})")
    public void insert(ReviewLog reviewLog);

    @Update("update r_log set description=#{description}, time=#{time}, state=#{state} where r_id=#{r_id}")
    public void update(ReviewLog reviewLog);
}
