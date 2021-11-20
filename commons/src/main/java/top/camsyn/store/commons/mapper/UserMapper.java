package top.camsyn.store.commons.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.camsyn.store.commons.entity.user.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
