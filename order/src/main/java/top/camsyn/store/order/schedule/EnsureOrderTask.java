package top.camsyn.store.order.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import top.camsyn.store.commons.entity.order.TradeRecord;
import top.camsyn.store.order.service.TradeRecordService;

import java.util.List;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class EnsureOrderTask {

    @Autowired
    TradeRecordService recordService;

    //3.添加定时任务(每日0点触发)
    @Scheduled(cron = "0 0 0 * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void autoEnsureOrder() {
        log.info("拉取所有符合要求的未确认订单, 自动确认之");
        List<TradeRecord> records = recordService.getAvailablePullerUnconfirmed();
        records.parallelStream().forEach(
                record -> {
                    record.setPullerConfirm(1);
                    recordService.postHandle(record);
                }
        );
        records = recordService.getAvailablePusherUnconfirmed();
        records.parallelStream().forEach(
                record -> {
                    record.setPusherConfirm(1);
                    recordService.postHandle(record);
                }
        );
    }
}
