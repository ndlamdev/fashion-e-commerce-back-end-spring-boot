/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:15 AM-24/04/2025
 * User: kimin
 **/
package com.lamnguyen.product_service.service.kafka.producer.v1;

import com.lamnguyen.product_service.event.CreateVariantEvent;
import com.lamnguyen.product_service.service.kafka.producer.IVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VariantServiceImpl implements IVariantService {
	private final KafkaTemplate<String, Object> kafkaTemplate;
	@Value("${spring.kafka.topic.create-variant}")
	private String topicCreateVariant;

	@Override
	public void saveVariant(String productId, List<CreateVariantEvent.Option> options) {
		var event = new CreateVariantEvent(productId, options);
		kafkaTemplate.send(topicCreateVariant, event);
	}
}