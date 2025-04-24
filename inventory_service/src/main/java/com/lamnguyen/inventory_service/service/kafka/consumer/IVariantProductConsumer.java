/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:27 AM-24/04/2025
 * User: kimin
 **/

package com.lamnguyen.inventory_service.service.kafka.consumer;

import com.lamnguyen.inventory_service.message.CreateVariantEvent;

public interface IVariantProductConsumer {
	void sendCreateVariantEvent(CreateVariantEvent event);
}
