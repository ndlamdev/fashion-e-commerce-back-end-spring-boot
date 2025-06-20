/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:55 PM - 23/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.service.redis;

public interface IGoogleTokenRedisManager {
	void setRegisterTokenIdUsingGoogle(String id);

	boolean existRegisterTokenIdUsingGoogle(String id);
}
