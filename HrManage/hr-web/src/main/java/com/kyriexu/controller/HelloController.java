package com.kyriexu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kyriexu.model.MenuRole;
import com.kyriexu.model.Nation;
import com.kyriexu.model.RespBean;
import com.kyriexu.service.TestMailService;
import io.swagger.annotations.ApiOperation;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KyrieXu
 * @since 2020/3/27 10:23
 **/
@RestController
public class HelloController {
    @Autowired
    private TestMailService mailService;

    @ApiOperation("主页的接口")
    @GetMapping("/")
    public String get(){
        return "Hello";
    }

    @ApiOperation("测试接口")
    @GetMapping("/test1")
    public String test1(){
        return "测试接口1";
    }

    @ApiOperation("发送消息的接口，测试用")
    @GetMapping("/msg")
    public RespBean test2(@RequestBody MenuRole menuRole) throws InterruptedException, RemotingException, MQClientException, MQBrokerException, JsonProcessingException {
        int res = mailService.add(menuRole);
        return res==1?RespBean.ok("发送成功"):RespBean.error("发送失败");
    }
}
