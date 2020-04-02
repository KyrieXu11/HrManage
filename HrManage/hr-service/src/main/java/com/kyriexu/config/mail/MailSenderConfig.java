package com.kyriexu.config.mail;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author KyrieXu
 * @since 2020/3/31 13:40
 **/
@Component
@ConfigurationProperties(prefix = "java.mail")
@PropertySource("classpath:mail.properties")
@Data
public class MailSenderConfig {

    private static final String DEFAULT_CHARSET = "utf-8";

    private String host;

    private Integer port;

    private String username;

    private String password;

    private String protocol = "smtp";

    private String defaultEncoding = DEFAULT_CHARSET;

    private Map<String, String> properties = new HashMap<>();

    private String jndiName;
}
