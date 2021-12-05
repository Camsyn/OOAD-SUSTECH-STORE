package top.camsyn.store.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.camsyn.store.commons.entity.order.TradeRecord;
import top.camsyn.store.commons.entity.request.Request;

import java.util.List;


@Mapper
public interface TradeRecordMapper extends BaseMapper<TradeRecord> {

    @Select("SELECT *\n" +
            "from trade_record\n" +
            "where type = 1\n" +
            "  and trade_type = 0\n" +
            "  and pusher_confirm = 0\n" +
            "  and DATEDIFF(now(), create_time) >= 14;")
    List<TradeRecord> getAvailablePusherUnconfirmed();

    @Select("SELECT *\n" +
            "from trade_record\n" +
            "where type = 0\n" +
            "  and trade_type = 0\n" +
            "  and puller_confirm = 0\n" +
            "  and DATEDIFF(now(), create_time) >= 14;")
    List<TradeRecord> getAvailablePullerUnconfirmed();
}
