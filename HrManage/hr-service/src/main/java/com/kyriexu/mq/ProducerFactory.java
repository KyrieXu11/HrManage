package com.kyriexu.mq;

import com.kyriexu.config.mq.ProducerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author KyrieXu
 * @since 2020/3/26 0:34
 **/
@Component
@Slf4j
public class ProducerFactory {
    @Autowired
    private ProducerConfig config;

    private DefaultMQProducer producer;

    /**
     * 生产者初始化，只会执行一次
     */
    @PostConstruct
    public void init(){
        producer = new DefaultMQProducer(config.getGroupName());
        producer.setNamesrvAddr(config.getNameSrvAddr());
        // 设置发送消息的失败重试次数
        producer.setRetryTimesWhenSendFailed(config.getRetryTimes());
        producer.setSendMsgTimeout(config.getSendMsgTimeout());
        producer.setVipChannelEnabled(false);
        try {
            producer.start();
        }
        catch (MQClientException e) {
            log.info("生产者初始化异常");
            e.printStackTrace();
        }
    }

    public DefaultMQProducer getProducer() {
        return producer;
    }
}
