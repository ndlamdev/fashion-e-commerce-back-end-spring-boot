/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:14 AM-05/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.controller.user;

import com.lamnguyen.cart_service.domain.request.QuantityCartItemRequest;
import com.lamnguyen.cart_service.domain.response.CartResponse;
import com.lamnguyen.cart_service.domain.response.UpdateCartItemResponse;
import com.lamnguyen.cart_service.service.business.ICartService;
import com.lamnguyen.cart_service.utils.annotation.ApiMessageResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/cart/v1")
public class CartController {
	ICartService cartService;

	@PostMapping("/add/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_BASE', 'ROLE_ADMIN', 'ADD_VARIANT_PRODUCT')")
	@ApiMessageResponse("Add product into cart success!")
	public void addCartItem(@PathVariable("id") String id, @Valid @RequestBody QuantityCartItemRequest request) {
		cartService.addVariantToCart(id,request.quantity());
	}

	@GetMapping("/me")
	@PreAuthorize("hasAnyAuthority('ROLE_BASE', 'ROLE_ADMIN', 'GET_CART_INFO')")
	@ApiMessageResponse("Get my cart success!")
	public CartResponse getCartInfo() {
		return cartService.getCart();
	}

	@PutMapping("/update/{cartItemId}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_BASE', 'CHANGE_QUANTITY_CART_ITEM')")
	@ApiMessageResponse("Update cart items quantity success!")
	public UpdateCartItemResponse updateCartItem(@PathVariable("cartItemId") long id, @Valid @RequestBody QuantityCartItemRequest request) {
		return cartService.updateCartItem(id, request.quantity());
	}

	@DeleteMapping("/remove/{cartItemId}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_BASE', 'REMOVE_CART_ITEM')")
	@ApiMessageResponse("Remove cart item success!")
	public void deleteCartItem(@PathVariable("cartItemId") long id) {
		cartService.removeCartItem(id);
	}
}
