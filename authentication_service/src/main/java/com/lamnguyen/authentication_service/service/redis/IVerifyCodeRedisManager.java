/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:18 PM - 11/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.service.redis;

import java.util.Optional;

public interface IVerifyCodeRedisManager extends IRedisManager {
	void setCode(long userId, String otp);

	Optional<String> getCode(long userId);

	int increaseTotalTry(long userId);

	void removeCode(long userId);

	int getTotalResendCode(long userId);
}
