package com.lamnguyen.order_service.service.redis;

import java.util.Optional;

public interface CallbackDB<R> {
	Optional<R> call();
}