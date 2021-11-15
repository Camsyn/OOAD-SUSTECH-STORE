package top.camsyn.store.commons.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.camsyn.store.commons.entity.Account;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
}
