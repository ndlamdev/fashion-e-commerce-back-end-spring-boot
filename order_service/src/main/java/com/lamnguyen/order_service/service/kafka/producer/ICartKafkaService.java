/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:31 PM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.service.kafka.producer;

import com.lamnguyen.order_service.event.DeleteCartItemsEvent;

public interface ICartKafkaService {
	void deleteCartItems(DeleteCartItemsEvent event);
}
