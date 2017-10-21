package com.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import com.util.MessageBrokerUtil;

@Configuration
@EnableWebSocketMessageBroker
public class MessageBrokerConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(MessageBrokerUtil.TOPIC);
        config.setApplicationDestinationPrefixes(MessageBrokerUtil.DST_PREFIX);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(MessageBrokerUtil.ENDPOINT).withSockJS();
    }
    @Override
    public boolean configureMessageConverters(List<MessageConverter> converters) {
      return true;
    }
}