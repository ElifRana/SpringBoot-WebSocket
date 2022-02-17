package com.example.springbootwebsocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WsConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/elifrana").withSockJS()
                .setStreamBytesLimit(15 * 1024)
                .setHttpMessageCacheSize(15 * 1024);
        // registry.addEndpoint("/elifrana").setAllowedOrigins("*").withSockJS();
        // kim gelirse gelsin bağlansın demek için setAllowedOrigins("*") kullanıyoruz
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setSendTimeLimit(60 * 1000)
                .setSendBufferSizeLimit(200 * 1024 * 1024)
                .setMessageSizeLimit(200 * 1024 * 1024);
    }
}
