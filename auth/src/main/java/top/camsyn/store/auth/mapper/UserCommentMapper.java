package top.camsyn.store.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.camsyn.store.auth.model.Role;
import top.camsyn.store.commons.entity.user.UserComment;

@Mapper
public interface UserCommentMapper extends BaseMapper<UserComment> {
}
