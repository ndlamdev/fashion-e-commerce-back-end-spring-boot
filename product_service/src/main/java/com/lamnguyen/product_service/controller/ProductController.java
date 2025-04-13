/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:28 PM - 07/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.controller;

import com.lamnguyen.product_service.domain.dto.ProductDto;
import com.lamnguyen.product_service.service.business.IProductService;
import com.lamnguyen.product_service.utils.annotation.ApiMessageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/v1")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductController {
	IProductService productService;

	@GetMapping("/{id}")
	@ApiMessageResponse("Get product success!")
	public ProductDto getProductDetail(@PathVariable("id") String id) {
		return productService.getProductDtoById(id);
	}
}
