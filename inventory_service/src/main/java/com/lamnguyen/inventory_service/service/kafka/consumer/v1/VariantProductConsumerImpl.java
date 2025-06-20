package com.lamnguyen.inventory_service.service.kafka.consumer.v1;

import com.lamnguyen.inventory_service.message.DataVariantEvent;
import com.lamnguyen.inventory_service.service.business.IInventoryService;
import com.lamnguyen.inventory_service.service.kafka.consumer.IVariantProductConsumer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class VariantProductConsumerImpl implements IVariantProductConsumer {
	IInventoryService inventoryService;

	@Override
	public void createVariantEvent(DataVariantEvent event) {
		log.info("Create variant event: Received DataVariantEvent: {}", event);
		inventoryService.createVariantProduct(event);
	}

	@Override
	public void updateVariantEvent(DataVariantEvent event) {
		log.info("Update Variant event: Received DataVariantEvent: {}", event);
		inventoryService.updateVariantProduct(event);
	}
}