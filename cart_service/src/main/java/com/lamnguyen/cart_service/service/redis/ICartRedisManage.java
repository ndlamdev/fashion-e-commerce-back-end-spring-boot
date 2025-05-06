/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:09 AM-03/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.service.redis;

import com.lamnguyen.cart_service.domain.dto.CartDto;

import java.util.Optional;

public interface ICartRedisManage extends ICacheManage<CartDto> {
	public Optional<CartDto> getCartByUserId(long userId);
}
