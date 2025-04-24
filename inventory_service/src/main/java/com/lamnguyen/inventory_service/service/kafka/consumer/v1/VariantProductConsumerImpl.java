package com.lamnguyen.inventory_service.service.kafka.consumer.v1;

import com.lamnguyen.inventory_service.message.CreateVariantEvent;
import com.lamnguyen.inventory_service.service.kafka.consumer.IVariantProductConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class VariantProductConsumerImpl implements IVariantProductConsumer {
	@Override
	@KafkaListener(topics = "${spring.kafka.topic.create-variant}", groupId = "inventory-service-group")
	@RetryableTopic(
			backoff = @Backoff(value = 3000L),
			attempts = "5",
			include = ApiException.class)
	public void sendCreateVariantEvent(CreateVariantEvent event) {
		log.info("Received CreateVariantEvent: {}", event);
	}
}