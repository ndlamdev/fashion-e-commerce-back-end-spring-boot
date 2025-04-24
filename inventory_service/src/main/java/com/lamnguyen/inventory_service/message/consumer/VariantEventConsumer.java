/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:15 AM-24/04/2025
 * User: kimin
 **/
package com.lamnguyen.inventory_service.message.consumer;

import com.lamnguyen.inventory_service.message.CreateVariantEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VariantEventConsumer {

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

            // Process the event
            // In a real implementation, this would:
            // 1. Extract data from the event
            // 2. Create or update inventory records
            // 3. Perform any necessary business logic

            String variantId = event.id();
            log.info("Processing variant with ID: {}", variantId);

            // Log the options
            event.options().forEach(option -> {
                log.info("Option type: {}, values: {}", option.type(), option.values());
            });

            // Here you would implement the actual business logic
            // For example:
            // inventoryService.createInventoryForVariant(variantId, event.options());

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
