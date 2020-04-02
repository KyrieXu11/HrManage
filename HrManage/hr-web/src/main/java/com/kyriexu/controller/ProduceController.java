package com.kyriexu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kyriexu.mqservice.ProduceService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KyrieXu
 * @since 2020/3/27 11:15
 **/
@RestController
public class ProduceController {

    @Autowired
    private ProduceService service;

    //@GetMapping("/msg")
    //public String sendMessage(@RequestParam String msg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException, JsonProcessingException {
    //    boolean b = service.sendMessage(msg);
    //    if (b)
    //        return "发送成功";
    //    return "发送失败";
    //}

}
