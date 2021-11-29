package top.camsyn.store.commons.props;


import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rocketmq.consumer")
@Data
public class MqConsumerProperties {
    public static final Logger LOGGER = LoggerFactory.getLogger(MqConsumerProperties.class);

    private String namesrvAddr;

    private String groupName;

    private Integer consumeThreadMin;

    private Integer consumeThreadMax;

    private String topics;

    private Integer consumeMessageBatchMaxSize;

}