package top.camsyn.store.request.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import top.camsyn.store.commons.entity.request.CartItem;
import top.camsyn.store.commons.entity.request.CartRequest;
import top.camsyn.store.commons.entity.request.Request;

import java.util.List;

@Mapper
public interface CartMapper extends BaseMapper<CartItem> {

    @Select("select r.*, c.count as cart_item_count, c.create_time as cart_item_create_time, c.update_time as cart_item_update_time, c.id as cart_item_id, c.owner as cart_item_owner\n" +
            "from cart c\n" +
            "         left join request r on c.request_id = r.id " +
            "where c.owner = ${owner} and c.deleted = 0 and c.state = ${state};")
    List<CartRequest> getCart(@Param("owner") Integer sid, @Param("state") Integer state);


    @Update("update cart set state = 2 where owner = ${owner} and state = 0;")
    boolean emptyCart(@Param("owner") Integer sid);
}
