/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:23 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.service.redis;

import java.time.LocalDateTime;

public interface IChangePasswordRedisManager extends IRedisManager {
	Long getDateTimeChangePassword(long userId);

	void setDateTimeChangePassword(long userId, LocalDateTime dateTime);
}
