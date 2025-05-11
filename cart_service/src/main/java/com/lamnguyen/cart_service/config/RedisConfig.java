/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:33 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.cart_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamnguyen.cart_service.domain.dto.CartDto;
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
	RedisTemplate<String, CartDto> cartDtoRedisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, CartDto> template = new RedisTemplate<>();

		var cartDtoJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(mapper, CartDto.class);

		template.setKeySerializer(stringRedisSerializer);
		template.setValueSerializer(cartDtoJackson2JsonRedisSerializer);
		template.setHashKeySerializer(stringRedisSerializer);
		template.setHashValueSerializer(cartDtoJackson2JsonRedisSerializer);
		template.setConnectionFactory(connectionFactory);
		return template;
	}
}
