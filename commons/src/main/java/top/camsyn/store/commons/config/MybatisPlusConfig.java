package top.camsyn.store.commons.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.camsyn.store.commons.handler.DateMetaObjectHandler;

@Configuration
@MapperScan("top.camsyn.store.commons.mapper")
public class MybatisPlusConfig {
    @Bean
    public MetaObjectHandler metaObjectHandler(){
        return new DateMetaObjectHandler();
    }
}
