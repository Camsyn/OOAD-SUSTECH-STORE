package top.camsyn.store.commons.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableDiscoveryClient
public class NacosConfig {

}
