/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:19 AM - 19/04/2025
 * User: kimin
 **/

package com.lamnguyen.profile_service.service.redis;

import com.lamnguyen.profile_service.domain.dto.CustomerDto;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface ICustomerRedisManager extends IRedisManager<CustomerDto> {
	Optional<CustomerDto> getById(long id);

	void setById(long id, CustomerDto data, long duration, TimeUnit unit);

	void deleteById(long id);

	boolean existById(long id);

	Optional<CustomerDto> cacheByUserId(long id, CacheFunction<CustomerDto> cacheFunction, long duration, TimeUnit unit);

	Optional<CustomerDto> getByUserId(long id);

	void setByUserId(long id, CustomerDto data, long duration, TimeUnit unit);

	void deleteByUserId(long id);

	boolean existByUserId(long id);

	Optional<CustomerDto> cacheById(long id, CacheFunction<CustomerDto> cacheFunction, long duration, TimeUnit unit);
}
