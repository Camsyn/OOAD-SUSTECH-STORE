package top.camsyn.store.uaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.camsyn.store.commons.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}