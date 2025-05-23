/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:02 PM-23/05/2025
 * User: kimin
 **/

package com.lamnguyen.payment_service.service.kafka.producer.v1;


import com.lamnguyen.payment_service.service.kafka.producer.IOrderKafkaProducer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderKafkaProducerImpl implements IOrderKafkaProducer {
	@Value("${spring.kafka.topic.delete-order}")
	@NonFinal
	String deleteOrderTopic;

	KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	public void removeOrder(long orderId) {
		kafkaTemplate.send(deleteOrderTopic, orderId);
	}
}
