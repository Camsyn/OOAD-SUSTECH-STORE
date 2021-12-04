package top.camsyn.store.order.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import top.camsyn.store.commons.entity.order.TradeRecord;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.commons.service.impl.SuperServiceImpl;
import top.camsyn.store.order.mapper.TradeRecordMapper;

import java.util.List;

@Service
public class TradeRecordService extends SuperServiceImpl<TradeRecordMapper, TradeRecord> {

    public List<TradeRecord> pageOfPullOrders(int sid, Integer page, Integer pageSize){
        return page(new Page<>(page, pageSize),
                new LambdaQueryWrapper<TradeRecord>()
                        .eq(TradeRecord::getPuller, sid)
                        .orderByDesc(TradeRecord::getCreateTime))
                .getRecords();
    }

    public List<TradeRecord> pageOfPushOrders(int sid, Integer page, Integer pageSize){
        return page(new Page<>(page, pageSize),
                new LambdaQueryWrapper<TradeRecord>()
                        .eq(TradeRecord::getPusher, sid)
                        .orderByDesc(TradeRecord::getCreateTime))
                .getRecords();
    }


    public void postHandle(TradeRecord order) {

    }
}
