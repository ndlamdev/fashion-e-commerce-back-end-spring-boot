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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartItemServiceImpl implements ICartItemService {
	ICartItemRepository cartItemRepository;
	IInventoryGrpcClient inventoryGrpcClient;

	@Override
	public void addCartItem(long cartId, String variantId) {
		if (!inventoryGrpcClient.existInventory(variantId)) return;
		var cartItem = CartItem.builder()
				.cart(Cart.builder().id(cartId).build())
				.variantId(variantId)
				.productId(inventoryGrpcClient.productIdOfVariant(variantId))
				.build();
		try {
			cartItemRepository.save(cartItem);
		} catch (Exception ignored) {
			var item = cartItemRepository.findByCartIdAndVariantId(cartId, variantId)
					.orElseThrow(() -> ApplicationException.createException(ExceptionEnum.CART_ITEM_NOT_FOUND));
			item.setQuantity(item.getQuantity() + 1);
			cartItemRepository.save(item);
		}
	}
}
