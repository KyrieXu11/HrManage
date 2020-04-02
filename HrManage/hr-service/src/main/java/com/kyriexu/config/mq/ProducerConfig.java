package com.kyriexu.config.mq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author KyrieXu
 * @since 2020/3/25 23:13
 **/
@Component
@ConfigurationProperties(prefix = "rocketmq.producer")
@PropertySource("classpath:rocketmq.properties")
@Data
public class ProducerConfig {
    private String nameSrvAddr;
    private String groupName;
    private int retryTimes;
    private String topic;
    private String tag;
    private int sendMsgTimeout;
}
