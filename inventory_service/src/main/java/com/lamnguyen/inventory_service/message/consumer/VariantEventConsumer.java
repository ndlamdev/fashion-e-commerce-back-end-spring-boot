/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:15 AM-24/04/2025
 * User: kimin
 **/
package com.lamnguyen.inventory_service.message.consumer;

import com.lamnguyen.inventory_service.message.CreateVariantEvent;
import com.lamnguyen.inventory_service.service.business.IInventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VariantEventConsumer {

    private final IInventoryService inventoryService;

    @KafkaListener(topics = "create_variant_product", groupId = "inventory-service-group")
    public void consumeCreateVariantEvent(CreateVariantEvent event) {
        try {
            log.info("Received CreateVariantEvent: {}", event);

            // Validate the event
            if (event == null) {
                log.error("Received null CreateVariantEvent");
                return;
            }

            if (event.id() == null || event.id().isEmpty()) {
                log.error("CreateVariantEvent has null or empty ID");
                return;
            }

            if (event.options() == null || event.options().isEmpty()) {
                log.error("CreateVariantEvent has null or empty options for variant ID: {}", event.id());
                return;
            }

            // Process the event using the InventoryServiceImpl
            String variantId = event.id();
            log.info("Processing variant with ID: {}", variantId);

            // Log the options
            event.options().forEach(option -> {
                log.info("Option type: {}, values: {}", option.type(), option.values());
            });

            // Process the event to create inventory records
            inventoryService.createVariantProduct(event);

            log.info("Successfully processed CreateVariantEvent for variant ID: {}", variantId);
        } catch (Exception e) {
            log.error("Error processing CreateVariantEvent: {}", e.getMessage(), e);
            // In a production environment, you might want to:
            // 1. Send the failed event to a dead letter queue
            // 2. Trigger alerts
            // 3. Implement retry logic
        }
    }
}
