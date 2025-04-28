/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:33 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamnguyen.authentication_service.domain.dto.RoleDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
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

	@Bean
	RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();

		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer(mapper));
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(mapper));
		template.setConnectionFactory(connectionFactory);
		return template;
	}

	@Bean("customStringRedisTemplate")
	RedisTemplate<String, String> stringRedisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new StringRedisSerializer());
		template.setConnectionFactory(connectionFactory);
		return template;
	}

	@Bean
	RedisTemplate<String, Number> numberRedisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Number> template = new RedisTemplate<>();
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		template.setConnectionFactory(connectionFactory);
		return template;
	}


	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		return RedisCacheManager.builder(redisConnectionFactory).build();
	}

	@Bean
	RedisTemplate<String, RoleDto> roleRedisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, RoleDto> template = new RedisTemplate<>();
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new Jackson2JsonRedisSerializer<>(mapper, RoleDto.class));
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(mapper, RoleDto.class));
		template.setConnectionFactory(connectionFactory);
		return template;
	}
}
