/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:33 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.order_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamnguyen.order_service.domain.dto.OrderDto;
import com.lamnguyen.order_service.domain.response.SubOrder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RedisConfig {
	ObjectMapper mapper;
	StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

	@Bean
	RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();

		var genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(mapper);

		template.setKeySerializer(stringRedisSerializer);
		template.setValueSerializer(genericJackson2JsonRedisSerializer);
		template.setHashKeySerializer(stringRedisSerializer);
		template.setHashValueSerializer(genericJackson2JsonRedisSerializer);
		template.setConnectionFactory(connectionFactory);
		return template;
	}

	@Bean
	RedisTemplate<String, SubOrder> subOrderRedisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, SubOrder> template = new RedisTemplate<>();

		var jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(mapper, SubOrder.class);

		template.setKeySerializer(stringRedisSerializer);
		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.setHashKeySerializer(stringRedisSerializer);
		template.setHashValueSerializer(jackson2JsonRedisSerializer);
		template.setConnectionFactory(connectionFactory);
		return template;
	}

	@Bean
	RedisTemplate<String, OrderDto> orderDtoRedisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, OrderDto> template = new RedisTemplate<>();

		var jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(mapper, OrderDto.class);

		template.setKeySerializer(stringRedisSerializer);
		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.setHashKeySerializer(stringRedisSerializer);
		template.setHashValueSerializer(jackson2JsonRedisSerializer);
		template.setConnectionFactory(connectionFactory);
		return template;
	}
}
