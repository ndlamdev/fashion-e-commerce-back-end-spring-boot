package com.lamnguyen.profile_service.service.business.producer;

import com.lamnguyen.profile_service.message.InfoAddressShipping;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@RequiredArgsConstructor
@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerProducer {
    KafkaTemplate<String, InfoAddressShipping> kafkaTemplate;

    public void sendInfoAddressShipping(InfoAddressShipping infoCustomer) {
        log.info("Send info address shipping with json to info-address-shipping-topic {}", infoCustomer);
        Message<InfoAddressShipping> message = MessageBuilder
                .withPayload(infoCustomer)
                .setHeader(TOPIC, "info-address-shipping-topic")
                .build();
        kafkaTemplate.send(message);
    }
}
