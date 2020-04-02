package com.kyriexu.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author KyrieXu
 * @since 2020/3/26 13:39
 **/
@Configuration
public class JedisPoolFactory {

    @Autowired
    private JedisConfig Jconfig;

    @Bean
    public JedisPool jedisPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(Jconfig.getMaxIdle());
        config.setMaxTotal(Jconfig.getMaxTotal());
        return new JedisPool(config,Jconfig.getHost(),Jconfig.getPort(),Jconfig.getTimeout());
    }
}
