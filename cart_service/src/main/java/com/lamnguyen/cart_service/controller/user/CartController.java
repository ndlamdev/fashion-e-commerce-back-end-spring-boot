/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:14 AM-05/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.controller.user;

import com.lamnguyen.cart_service.domain.request.UpdateCartItemRequest;
import com.lamnguyen.cart_service.domain.response.CartResponse;
import com.lamnguyen.cart_service.service.business.ICartService;
import com.lamnguyen.cart_service.utils.annotation.ApiMessageResponse;
import com.lamnguyen.cart_service.utils.helper.JwtTokenUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/v1/cart")
public class  CartController {
	ICartService cartService;
	JwtTokenUtil jwtTokenUtil;

	@PostMapping("/add/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_BASE', 'ROLE_ADMIN', 'ADD_VARIANT_PRODUCT')")
	@ApiMessageResponse("Add product into cart success!")
	public void addCartItem(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("id") String id) {
		cartService.addVariantToCart(jwtTokenUtil.getUserIdNotVerify(token), id);
	}

	@GetMapping("/me")
	@PreAuthorize("hasAnyAuthority('ROLE_BASE', 'ROLE_ADMIN', 'GET_CART_INFO')")
	@ApiMessageResponse("Get my cart success!")
	public CartResponse getCartInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
		return cartService.getCartByUserId(jwtTokenUtil.getUserIdNotVerify(token));
	}

	@PutMapping
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_BASE', 'CHANGE_QUANTITY_CART_ITEM')")
	@ApiMessageResponse("Update cart items quantity success!")
	public void updateCartItem(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody UpdateCartItemRequest request) {
		cartService.updateCartItem(jwtTokenUtil.getUserIdNotVerify(token), request);
	}
}
