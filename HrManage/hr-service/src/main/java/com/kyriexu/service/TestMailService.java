package com.kyriexu.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kyriexu.config.mq.ProducerConfig;
import com.kyriexu.model.MenuRole;
import com.kyriexu.model.Nation;
import com.kyriexu.mq.ProducerFactory;
import com.kyriexu.utils.ObjectMapperUtil;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author KyrieXu
 * @since 2020/3/31 16:24
 **/
@Service
public class TestMailService {

    @Autowired
    private ProducerFactory factory;

    @Autowired
    private ProducerConfig config;

    public int add(MenuRole menuRole) throws JsonProcessingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        DefaultMQProducer producer = factory.getProducer();
        String beanStr = ObjectMapperUtil.BeanToString(menuRole);
        SendResult sendRes = producer.send(new Message(config.getTopic(), beanStr.getBytes()));
        SendStatus sendStatus = sendRes.getSendStatus();
        if (sendStatus==SendStatus.SEND_OK)
            return 1;
        return 0;
    }
}
