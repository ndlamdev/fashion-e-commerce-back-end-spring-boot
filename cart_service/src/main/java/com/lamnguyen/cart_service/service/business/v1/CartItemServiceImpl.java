/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:16 AM-05/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.service.business.v1;

import com.lamnguyen.cart_service.config.exception.ApplicationException;
import com.lamnguyen.cart_service.config.exception.ExceptionEnum;
import com.lamnguyen.cart_service.model.Cart;
import com.lamnguyen.cart_service.model.CartItem;
import com.lamnguyen.cart_service.repository.ICartItemRepository;
import com.lamnguyen.cart_service.service.business.ICartItemService;
import com.lamnguyen.cart_service.service.grpc.IInventoryGrpcClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartItemServiceImpl implements ICartItemService {
	ICartItemRepository cartItemRepository;
	IInventoryGrpcClient inventoryGrpcClient;

	@Override
	public void addCartItem(long cartId, String variantId) {
		var variantProduct = inventoryGrpcClient.getVariantProductByVariantId(variantId);
		var cartItem = CartItem.builder()
				.cart(Cart.builder().id(cartId).build())
				.variantId(variantId)
				.productId(variantProduct.getProductId())
				.quantity(1)
				.build();

		if (cartItemRepository.existsByCartIdAndVariantId(cartId, variantId)) {
			modifyQuantity(cartId, variantId, variantProduct.getQuantity(), 1);
			return;
		}

		try {
			cartItemRepository.save(cartItem);
		} catch (Exception ignored) {
			modifyQuantity(cartId, variantId, variantProduct.getQuantity(), 1);
		}
	}

	int modifyQuantity(long cartId, String variantId, int inventory, int quantityModify) {
		var item = cartItemRepository.findByCartIdAndVariantId(cartId, variantId)
				.orElseThrow(() -> ApplicationException.createException(ExceptionEnum.CART_ITEM_NOT_FOUND));

		var newQuantity = item.getQuantity() + quantityModify;
		if (newQuantity > inventory || newQuantity < 1)
			throw ApplicationException.createException(ExceptionEnum.NOT_ENOUGH_QUANTITY);
		item.setQuantity(newQuantity);
		cartItemRepository.save(item);
			return newQuantity;
	}

	@Override
	public int updateQuantityCartItem(long cartId, long id, int quantity) {
		var cartItem = cartItemRepository.findByIdAndCartId(id, cartId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.CART_ITEM_NOT_FOUND));
		var variantProduct = inventoryGrpcClient.getVariantProductByVariantId(cartItem.getVariantId());
		return modifyQuantity(cartId, cartItem.getVariantId(), variantProduct.getQuantity(), quantity);
	}

	@Override
	@Transactional
	public void removeCartItem(long cartId, long id) {
		if (cartItemRepository.removeByIdAndCartId(id, cartId) == 0)
			throw ApplicationException.createException(ExceptionEnum.CART_ITEM_NOT_FOUND);
	}
}
