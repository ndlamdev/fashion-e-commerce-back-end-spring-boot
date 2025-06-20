/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:57 AM-03/05/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.service.kafka.v1;

import com.lamnguyen.authentication_service.event.CreateCartEvent;
import com.lamnguyen.authentication_service.service.kafka.ICartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CartServiceImpl implements ICartService {
	KafkaTemplate<String, Object> template;
	@NonFinal
	@Value("${spring.kafka.topic.create-cart}")
	String createCartTopic;

	@Override
	public void createCart(long userId) {
		template.send(createCartTopic, new CreateCartEvent(userId));
	}
}
