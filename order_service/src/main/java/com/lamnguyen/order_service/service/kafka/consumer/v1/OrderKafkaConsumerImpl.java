/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:07 PM-23/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.service.kafka.consumer.v1;

import com.lamnguyen.order_service.service.business.IOrderService;
import com.lamnguyen.order_service.service.kafka.consumer.IOrderKafkaConsumer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderKafkaConsumerImpl implements IOrderKafkaConsumer {
	IOrderService orderService;

	@Override
	public void deleteOrder(long orderId) {
		orderService.deleteOrder(orderId);
	}
}
