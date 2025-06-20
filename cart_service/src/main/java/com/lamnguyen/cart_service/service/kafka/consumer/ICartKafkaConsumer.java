/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:48 AM-03/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.service.kafka.consumer;

import com.lamnguyen.cart_service.message.CreateCartMessage;
import com.lamnguyen.cart_service.message.DeleteCartItemsMessage;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;

public interface ICartKafkaConsumer {
	@KafkaListener(topics = "${spring.kafka.topic.create-cart}", groupId = "cart-service-group")
	@RetryableTopic(
			backoff = @Backoff(value = 3000L),
			attempts = "5",
			include = ApiException.class)
	void createCart(CreateCartMessage message);

	@KafkaListener(topics = "${spring.kafka.topic.delete-cart-items}", groupId = "cart-service-group")
	@RetryableTopic(
			backoff = @Backoff(value = 3000L),
			attempts = "5",
			include = ApiException.class)
	void deleteCartItems(DeleteCartItemsMessage message);
}
