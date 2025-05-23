/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:05 PM-23/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.service.kafka.consumer;

import org.apache.kafka.common.errors.ApiException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;

public interface IOrderKafkaConsumer {

	@KafkaListener(topics = "${spring.kafka.topic.delete-order}", groupId = "base-service-group")
	@RetryableTopic(
			backoff = @Backoff(value = 3000L),
			attempts = "5",
			include = ApiException.class)
	void deleteOrder(long orderId);
}
