/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:27 AM-24/04/2025
 * User: kimin
 **/

package com.lamnguyen.inventory_service.service.kafka.consumer;

import com.lamnguyen.inventory_service.message.DataVariantEvent;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;

public interface IVariantProductConsumer {
	@KafkaListener(topics = "${spring.kafka.topic.create-variant}", groupId = "inventory-service-group")
	@RetryableTopic(
			backoff = @Backoff(value = 3000L),
			attempts = "5",
			include = ApiException.class)
	void createVariantEvent(DataVariantEvent event);

	@KafkaListener(topics = "${spring.kafka.topic.create-variant}", groupId = "inventory-service-group")
	@RetryableTopic(
			backoff = @Backoff(value = 3000L),
			attempts = "5",
			include = ApiException.class)
	void updateVariantEvent(DataVariantEvent event);
}
