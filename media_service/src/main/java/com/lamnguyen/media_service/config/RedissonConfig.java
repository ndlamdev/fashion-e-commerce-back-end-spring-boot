package com.lamnguyen.media_service.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RedissonConfig {
	@Value("${spring.data.redis.host}")
	String host;
	@Value("${spring.data.redis.port}")
	int port;
	@Value("${spring.data.redis.database}")
	int db;
	@Value("${spring.data.redis.username}")
	String username;
	@Value("${spring.data.redis.password}")
	String password;
	@Value("${spring.data.redis.connect-timeout}")
	int connectTimeout;


	@Bean
	public RedissonClient redissonClient() throws IOException {
		Config config = new Config();
		config.useSingleServer()
				.setAddress("redis://" + host + ":" + port)
				.setDatabase(db)
				.setConnectTimeout(connectTimeout);
		System.out.println(config.toYAML());
		return Redisson.create(config);
	}
}