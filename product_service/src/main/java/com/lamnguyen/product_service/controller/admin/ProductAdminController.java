/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:28 PM - 07/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.controller.admin;

import com.lamnguyen.product_service.domain.request.CreateProductRequest;
import com.lamnguyen.product_service.service.business.IProductManageService;
import com.lamnguyen.product_service.utils.annotation.ApiMessageResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/admin/v1")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductAdminController {
	IProductManageService productManageService;

	@PostMapping()
	@ApiMessageResponse("Create success!")
	public void create(@Valid @RequestBody CreateProductRequest request) {
		productManageService.create(request);
	}
}
