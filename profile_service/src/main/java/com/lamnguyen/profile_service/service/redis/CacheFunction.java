package com.lamnguyen.profile_service.service.redis;

import java.util.Optional;

public interface CacheFunction<T> {
		Optional<T> invoke();
	}