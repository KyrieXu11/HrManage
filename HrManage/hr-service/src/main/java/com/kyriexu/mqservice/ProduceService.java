package com.kyriexu.mqservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyriexu.mq.ProducerFactory;
import com.kyriexu.config.mq.ProducerConfig;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author KyrieXu
 * @since 2020/3/27 16:01
 **/
@Service
public class ProduceService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProducerFactory producerFactory;

    @Autowired
    private ProducerConfig config;

    /**
     * 发送消息，无回调，有返回值
     * @param o object对象
     * @return 发送是否成功
     * @throws JsonProcessingException
     * @throws InterruptedException
     * @throws RemotingException
     * @throws MQClientException
     * @throws MQBrokerException
     */
    public boolean sendMessage(Object o) throws JsonProcessingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        // 获取生产者
        DefaultMQProducer producer = producerFactory.getProducer();
        // 组装成信息
        Message message = new Message(config.getTopic(), objectMapper.writeValueAsBytes(o));
        // 发送信息的结果
        SendResult res = producer.send(message);
        return res != null;
    }
}
