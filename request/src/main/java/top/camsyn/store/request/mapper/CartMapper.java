package top.camsyn.store.request.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import top.camsyn.store.commons.entity.request.CartItem;
import top.camsyn.store.commons.entity.request.CartRequest;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.commons.handler.ListTypeHandler;

import java.util.List;

@Mapper
public interface CartMapper extends BaseMapper<CartItem> {
    @Results(value = {
            @Result(column = "labels", property = "labels", javaType = List.class, jdbcType = JdbcType.VARCHAR, typeHandler = ListTypeHandler.class),
            @Result(column = "images", property = "images", javaType = List.class, jdbcType = JdbcType.VARCHAR, typeHandler = ListTypeHandler.class),
            @Result(column = "video", property = "video", javaType = List.class, jdbcType = JdbcType.VARCHAR, typeHandler = ListTypeHandler.class),
            @Result(column = "desc_", property = "desc_", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(column = "cart_item_owner", property = "owner", javaType = Integer.class, jdbcType = JdbcType.INTEGER)
    })
    @Select("select r.*, c.count as cart_item_count, c.create_time as cart_item_create_time, c.update_time as cart_item_update_time, c.id as cart_item_id, c.owner as cart_item_owner\n" +
            "from cart_item c\n" +
            "         left join request r on c.request_id = r.id " +
            "where c.owner = ${owner_} and c.deleted = 0 and c.state = ${state_};")
    List<CartRequest> getCart(@Param("owner_") Integer sid, @Param("state_") Integer state);


    @Update("update cart_item set state = 2 where owner = ${owner} and state = 0;")
    boolean emptyCart(@Param("owner") Integer sid);
}
