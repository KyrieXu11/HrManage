package com.kyriexu.config.mq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author KyrieXu
 * @since 2020/3/26 14:52
 **/
@Component
@ConfigurationProperties(prefix = "rocketmq.consumer")
@PropertySource("classpath:rocketmq.properties")
@Data
public class ConsumerConfig {
    private String nameSrvAddr;
    private String groupName;
    private int retryTimes;
    private String topic;
    private String tag;
}
