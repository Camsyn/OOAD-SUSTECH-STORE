package top.camsyn.store.request.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.camsyn.store.commons.entity.request.Label;

@Mapper
public interface LabelMapper extends BaseMapper<Label> {
}
