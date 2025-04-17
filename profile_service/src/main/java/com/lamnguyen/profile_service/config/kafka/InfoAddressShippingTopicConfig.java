package com.lamnguyen.profile_service.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class InfoAddressShippingTopicConfig {

    @Bean
    public NewTopic customerTopic() {
        return TopicBuilder.name("info-address-shipping-topic").build();
    }
}
