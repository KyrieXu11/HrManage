package com.kyriexu.exceptionhandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kyriexu.exception.BaseException;
import com.kyriexu.exception.ResultSatus;
import com.kyriexu.model.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author KyrieXu
 * @since 2020/3/27 12:36
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public RespBean io(Exception e){
        if (e instanceof BaseException) {
            // 自定义异常
            BaseException e1 = (BaseException) e;
            ResultSatus res = e1.getResultSatus();
            // 输出错误信息
            log.info(res.getMsg());
            return RespBean.error(res.getMsg());
        }

        if (e instanceof SQLIntegrityConstraintViolationException) {
            return RespBean.error("该数据有关联数据，操作失败!");
        }

        if (e instanceof IOException){
            if (e instanceof JsonProcessingException){
                log.info("json转换失败");
                return RespBean.error("json转换失败");
            }
            log.info("io异常");
            return RespBean.error("io异常");
        }
        if (e instanceof InterruptedException){
            log.info("中断异常");
            return RespBean.error("中断异常");
        }
        if (e instanceof RemotingException){
            log.info("远程异常");
            return RespBean.error("远程异常");
        }
        if (e instanceof MQClientException){
            log.info("mq客户端异常");
            return RespBean.error("mq客户端异常");
        }
        if (e instanceof MQBrokerException){
            log.info("mq broker异常");
            return RespBean.error("mq broker异常");
        }
        if (e instanceof JedisConnectionException){
            log.info("连接超时");
            return RespBean.error("redis连接超时");
        }
        e.printStackTrace();
        return null;
    }
}
