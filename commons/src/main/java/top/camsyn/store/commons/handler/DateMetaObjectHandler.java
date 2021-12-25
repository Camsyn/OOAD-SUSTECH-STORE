package top.camsyn.store.commons.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

@Slf4j
public class DateMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("auto fill into time\n");
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "sendTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("auto fill into time\n");

//        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐)
        // 或者
//        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class); // 起始版本 3.3.3(推荐)
//        // 或者
//        this.fillStrategy(metaObject, "updateTime", new Date()); // 也可以使用(3.3.0 该方法有bug)
    }


//    private MybatisPlusAutoFillProperties autoFillProperties;

//    public DateMetaObjectHandler(MybatisPlusAutoFillProperties autoFillProperties) {
//        this.autoFillProperties = autoFillProperties;
//    }

//    /**
//     * 是否开启了插入填充
//     */
//    @Override
//    public boolean openInsertFill() {
//        return autoFillProperties.getEnableInsertFill();
//    }

//    /**
//     * 是否开启了更新填充
//     */
//    @Override
//    public boolean openUpdateFill() {
//        return autoFillProperties.getEnableUpdateFill();
//    }

//    /**
//     * 插入填充，字段为空自动填充
//     */
//    @Override
//    public void insertFill(MetaObject metaObject) {
//        Object createTime = getFieldValByName(autoFillProperties.getCreateTimeField(), metaObject);
//        Object updateTime = getFieldValByName(autoFillProperties.getUpdateTimeField(), metaObject);
//        if (createTime == null || updateTime == null) {
//            Date date = new Date();
//            if (createTime == null) {
//                setFieldValByName(autoFillProperties.getCreateTimeField(), date, metaObject);
//            }
//            if (updateTime == null) {
//                setFieldValByName(autoFillProperties.getUpdateTimeField(), date, metaObject);
//            }
//        }
//    }
//
//    /**
//     * 更新填充
//     */
//    @Override
//    public void updateFill(MetaObject metaObject) {
//        setFieldValByName(autoFillProperties.getUpdateTimeField(), new Date(), metaObject);
//    }
}