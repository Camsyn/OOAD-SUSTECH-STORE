package top.camsyn.store.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.camsyn.store.commons.entity.chat.Comment;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
