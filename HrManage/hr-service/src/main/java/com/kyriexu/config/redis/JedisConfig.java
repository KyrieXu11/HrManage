package com.kyriexu.config.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author KyrieXu
 * @since 2020/3/25 22:45
 **/
@Component
@ConfigurationProperties(prefix = "jedis")
@PropertySource("classpath:jedis.properties")
@Data
public class JedisConfig {
    private int maxIdle;
    private int maxTotal;
    private String host;
    private int port;
    private int timeout;
    private String auth;
}
