package com.kyriexu.config.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Map;
import java.util.Properties;

/**
 * @author KyrieXu
 * @since 2020/3/31 13:45
 **/
@Configuration
@Slf4j
public class MailSenderFactory {

    @Autowired
    private MailSenderConfig config;

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(config.getHost());
        sender.setPort(config.getPort());
        sender.setProtocol(config.getProtocol());
        sender.setUsername(config.getUsername());
        sender.setPassword(config.getPassword());
        sender.setDefaultEncoding(config.getDefaultEncoding());
        // 获取map
        Map<String, String> prop = config.getProperties();
        // 获取properties
        Properties properties = new Properties();
        // 将所有的map转换成properties
        properties.putAll(prop);
        sender.setJavaMailProperties(properties);
        return sender;
    }

}
