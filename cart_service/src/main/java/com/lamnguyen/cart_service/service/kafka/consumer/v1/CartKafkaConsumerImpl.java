/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:51 AM-03/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.service.kafka.consumer.v1;

import com.lamnguyen.cart_service.message.CreateCartMessage;
import com.lamnguyen.cart_service.message.DeleteCartItemsMessage;
import com.lamnguyen.cart_service.service.business.ICartService;
import com.lamnguyen.cart_service.service.kafka.consumer.ICartKafkaConsumer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Log4j2
public class CartKafkaConsumerImpl implements ICartKafkaConsumer {
	ICartService cartService;

	@Override
	public void createCart(CreateCartMessage message) {
		log.info("Create cart with user id: {}", message.userId());
		cartService.createCart(message.userId());
	}

	@Override
	public void deleteCartItems(DeleteCartItemsMessage message) {
		log.info("Delete cart items with user id: {} and variant ids: {}", message.getUserId(), message.getVariantIds());
		message.getVariantIds().forEach(it -> {
			cartService.removeCartItem(message.getUserId(), it);
		});
	}
}
