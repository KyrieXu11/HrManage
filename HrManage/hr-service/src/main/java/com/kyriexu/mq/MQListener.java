package com.kyriexu.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyriexu.config.mail.MailSenderConfig;
import com.kyriexu.exception.BaseException;
import com.kyriexu.exception.ResultSatus;
import com.kyriexu.model.Employee;
import com.kyriexu.model.Nation;
import com.kyriexu.redisservice.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;


/**
 * @author KyrieXu
 * @since 2020/3/27 11:48
 **/
@Component
@Slf4j
public class MQListener implements MessageListenerConcurrently {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MailSenderConfig config;

    @Autowired
    private RedisService redisService;

    /**
     * 消费的核心逻辑
     * @param msgs 消息
     * @param context 上下文
     * @return 消费的状态
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        for (MessageExt msg : msgs) {
            if (msg == null){
                log.info("邮件发送异常，消息为空");
                throw new BaseException(ResultSatus.MAIL_MESSAGE_EXCEPTION);
            }
            // 查redis中是否有这个key
            boolean contains = redisService.contains(msg.getMsgId());
            if (contains) {
                log.info("重复消费");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            // bean的字符串
            String beanStr = new String(msg.getBody());
            log.info(beanStr);
            // 转换成对象
            Employee employee;
            //Nation nation;
            try {
                employee = objectMapper.readValue(beanStr, Employee.class);
                log.info(employee.toString());
                //nation = objectMapper.readValue(beanStr, Nation.class);
            }
            catch (JsonProcessingException e) {
                throw new BaseException(ResultSatus.JSON_PROCESS_EXCEPTION);
            }
            // 构建消息
            MimeMessage mailMsg = mailSender.createMimeMessage();
            // 邮件帮助类
            MimeMessageHelper helper = new MimeMessageHelper(mailMsg);
            // 设置发送邮件对象
            try {
                //helper.setTo(employee.getEmail());
                helper.setTo("www1468008857@126.com");
                // 发送者
                helper.setFrom(config.getUsername());
                // 消息
                String text = "Hello！" + employee.getName() + ":\n欢迎加入公司！成为我司一员！\n您的职位是" + employee.getPosition() + "\n祝您生活愉快";
                //标题
                helper.setSubject("欢迎");
                // 消息体
                helper.setText(text);
                // 发送
                mailSender.send(mailMsg);
                // 发送成功就将信息 id 放入redis中避免重复消费,过期时间为10分钟
                redisService.set(msg.getMsgId(), 10 * 60, employee.getName());
                log.info("邮件发送成功");
            }
            catch (MessagingException e) {
                throw new BaseException(ResultSatus.MAIL_MESSAGE_EXCEPTION);
            }catch(JsonProcessingException e) {
                throw new BaseException(ResultSatus.JSON_PROCESS_EXCEPTION);
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
