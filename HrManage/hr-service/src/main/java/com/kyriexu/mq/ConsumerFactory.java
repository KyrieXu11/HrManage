package com.kyriexu.mq;

import com.kyriexu.config.mq.ConsumerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author KyrieXu
 * @since 2020/3/26 13:39
 **/
@Component
@Slf4j
public class ConsumerFactory {

    @Autowired
    @Qualifier("MQListener")
    private MessageListenerConcurrently mqLlistener;

    private DefaultMQPushConsumer consumer;

    @Autowired
    private ConsumerConfig config;

    /**
     * 消费者初始化，只会执行一次，自动执行
     */
    @PostConstruct
    public void init(){
        // 设置消费者群名
        consumer = new DefaultMQPushConsumer(config.getGroupName());
        // 设置namesrv的地址
        consumer.setNamesrvAddr(config.getNameSrvAddr());
        // 设置消费者的消息模式为集群模式
        consumer.setMessageModel(MessageModel.CLUSTERING);
        // 注册监听器
        consumer.registerMessageListener(mqLlistener);
        //设置每次拉取的最大消息数量，默认是1
        consumer.setConsumeMessageBatchMaxSize(2);
        try {
            // 订阅主题
            consumer.subscribe(config.getTopic(),"*");
            consumer.start();
        }
        catch (MQClientException e) {
            log.info("消费者初始化异常");
            e.printStackTrace();
        }
    }
}
