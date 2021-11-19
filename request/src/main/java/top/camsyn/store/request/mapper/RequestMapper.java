package top.camsyn.store.request.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.bouncycastle.cert.ocsp.Req;
import top.camsyn.store.commons.entity.request.Request;

import java.util.List;

@Mapper
public interface RequestMapper extends BaseMapper<Request> {


    /**
     * 联表的分页查询
     */
    @Select("select r.*, l.label_name\n" +
            "from request r\n" +
            "         inner join request_label_relation rlr on r.id = rlr.request_id\n" +
            "         inner join label l on rlr.label_id = l.id"+
            "         ${ew.customSqlSegment}"
    )
    List<Request> pageOfRequestByLabel(@Param("page")IPage<Request> page,
                                        @Param(Constants.WRAPPER) Wrapper<Request> queryWrapper);

  /**
     * 联表的分页查询
     */
    @Select("select r.*, l.label_name\n" +
            "from request r\n" +
            "         inner join request_label_relation rlr on r.id = rlr.request_id\n" +
            "         inner join label l on rlr.label_id = l.id"+
            "         ${ew.customSqlSegment}"
    )
    List<Request> getRequestByLabel(@Param(Constants.WRAPPER) Wrapper<Request> queryWrapper);


}
