package com.kyriexu.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyriexu.config.mail.MailSenderConfig;
import com.kyriexu.model.Employee;
import com.kyriexu.model.Nation;
import com.kyriexu.redisservice.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.common.message.MessageExt;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * @author KyrieXu
 * @since 2020/3/31 10:28
 **/
//@Aspect
@Component
@Slf4j
@Deprecated
public class MailAspect {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MailSenderConfig config;

    @Autowired
    private RedisService redisService;

    /**
     * 消费者消费的切点方法
     *
     * @param msgs    消息
     * @param context 上下文
     */
    //@Pointcut(value = "execution(public * com.kyriexu.mq.MQListener.consumeMessage(..))&&args(msgs,context)", argNames = "msgs,context")
    public void pc1(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
    }

    /**
     * 消费者的切面方法
     * 因为监听类不让抛出异常，只能try...catch..
     * 又因为要处理全局异常，所以最后选择了aop
     *
     * @param msgs    消息
     * @param context 上下文
     */
    //@Before(value = "pc1(msgs,context)", argNames = "msgs,context")
    public void sendMail(List<MessageExt> msgs, ConsumeConcurrentlyContext context) throws JsonProcessingException, MessagingException {
        if (msgs == null)
            return;
        for (MessageExt msg : msgs) {
            if (msg == null)
                return;
            boolean contains = redisService.contains(msg.getMsgId());
            if (contains) {
                log.info("重复消费");
                return;
            }
            // bean的字符串
            String beanStr = new String(msg.getBody());
            log.info(beanStr);
            // 转换成对象
            Employee employee = objectMapper.readValue(beanStr, Employee.class);
            // 构建消息
            MimeMessage mailMsg = mailSender.createMimeMessage();
            // 邮件帮助类
            MimeMessageHelper helper = new MimeMessageHelper(mailMsg);
            // 设置发送邮件对象
            helper.setTo(employee.getEmail());
            // 发送者
            helper.setFrom(config.getUsername());
            String text = "Hello！" + employee.getName() + ":\n欢迎加入公司！成为我司一员！\n，您的职位是" + employee.getPosition() + "祝您生活愉快";
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
    }
}
