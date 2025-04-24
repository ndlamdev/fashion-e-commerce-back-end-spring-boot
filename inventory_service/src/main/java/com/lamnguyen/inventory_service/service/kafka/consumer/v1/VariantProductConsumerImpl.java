package com.lamnguyen.inventory_service.service.kafka.consumer.v1;

import com.lamnguyen.inventory_service.message.CreateVariantEvent;
import com.lamnguyen.inventory_service.service.business.IInventoryService;
import com.lamnguyen.inventory_service.service.kafka.consumer.IVariantProductConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class VariantProductConsumerImpl implements IVariantProductConsumer {
	IInventoryService inventoryService;
	@Override
	public void sendCreateVariantEvent(CreateVariantEvent event) {
		log.info("Received CreateVariantEvent: {}", event);
		inventoryService.createVariantProduct(event);
	}
}