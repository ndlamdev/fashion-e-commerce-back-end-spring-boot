/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:22 AM-03/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.service.business.v1;

import com.lamnguyen.cart_service.config.exception.ApplicationException;
import com.lamnguyen.cart_service.config.exception.ExceptionEnum;
import com.lamnguyen.cart_service.domain.dto.CartDto;
import com.lamnguyen.cart_service.domain.response.CartResponse;
import com.lamnguyen.cart_service.mapper.ICartMapper;
import com.lamnguyen.cart_service.model.Cart;
import com.lamnguyen.cart_service.repository.ICartRepository;
import com.lamnguyen.cart_service.service.business.ICartItemService;
import com.lamnguyen.cart_service.service.business.ICartService;
import com.lamnguyen.cart_service.service.grpc.IInventoryGrpcClient;
import com.lamnguyen.cart_service.service.grpc.IProductGrpcClient;
import com.lamnguyen.cart_service.service.redis.ICartRedisManage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Array;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartServiceImpl implements ICartService {
	ICartRedisManage cartRedisManage;
	ICartRepository cartRepository;
	ICartItemService cartItemService;
	ICartMapper cartMapper;
	IProductGrpcClient productGrpcClient;

	@Override
	public CartResponse getCartByUserId(long userId) {
		var result = cartRedisManage.getCartByUserId(userId)
				.or(() -> cartRedisManage.cache(
						String.valueOf(userId),
						String.valueOf(userId),
						() -> cartRepository.findByUserId(userId).map(cartMapper::toCartDto)))
				.or(() -> cartRedisManage.cache(
						String.valueOf(userId),
						String.valueOf(userId),
						() -> Optional.ofNullable(createCart(userId))))
				.map(cartMapper::toCartResponse)
				.orElseThrow(() -> ApplicationException.createException(ExceptionEnum.CART_NOT_FOUND));
		if (result.getCartItems() == null) {
			result.setCartItems(new ArrayList<>());
		} else {
			result.getCartItems().forEach(item -> {
				item.setProduct(productGrpcClient.getProductDto(item.getProduct().getId()));
			});
		}

		return result;
	}

	@Override
	public CartDto createCart(long userId) {
		var cart = Cart.builder().userId(userId).build();
		var saved = cartRepository.save(cart);
		return cartMapper.toCartDto(saved);
	}

	@Override
	public void addVariantToCart(long userId, String variantId) {
		var cart = getCartByUserId(userId);
		cartItemService.addCartItem(cart.getId(), variantId);
		cartRedisManage.delete(String.valueOf(userId));
	}
}
