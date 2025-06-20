/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:32 PM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.service.kafka.producer.v1;

import com.lamnguyen.order_service.event.DeleteCartItemsEvent;
import com.lamnguyen.order_service.service.kafka.producer.ICartKafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartKafkaServiceImpl implements ICartKafkaService {
	private final KafkaTemplate<String, Object> kafkaTemplate;
	@Value("${spring.kafka.topic.delete-cart-items}")
	private String topicDeleteCartItems;

	@Override
	public void deleteCartItems(DeleteCartItemsEvent event) {
		kafkaTemplate.send(topicDeleteCartItems, event);
	}
}
