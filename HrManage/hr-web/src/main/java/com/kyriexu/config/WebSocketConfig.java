package com.kyriexu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author KyrieXu
 * @since 2020/4/2 10:26
 **/
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 客户端连接了`/ws/ep`这个连接点可以进行通信
        registry.addEndpoint("/ws/ep")
                // 允许跨域
                .setAllowedOrigins("*")
                // 能够使用sockjs来通信
                .withSockJS();
    }

    // 配置消息中继器
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 目标中继器的前缀是`/queue`,匹配的url就会到这个中继器
        registry.enableSimpleBroker("/queue");
    }
}
