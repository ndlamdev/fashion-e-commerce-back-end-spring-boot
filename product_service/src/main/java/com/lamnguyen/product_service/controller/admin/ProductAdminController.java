/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:28 PM - 07/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.controller.admin;

import com.lamnguyen.product_service.domain.request.DataProductRequest;
import com.lamnguyen.product_service.service.business.IProductManageService;
import com.lamnguyen.product_service.utils.annotation.ApiMessageResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/product/v1")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class ProductAdminController {
	IProductManageService productManageService;

	@PostMapping()
	@ApiMessageResponse("Create success!")
	public void create(@Valid @RequestBody DataProductRequest request) {
		log.info("create product: " + request.getTitle());
		productManageService.create(request);
		log.info("create product: " + request.getTitle() + " success!");
	}

	@PostMapping("/{id}")
	@ApiMessageResponse("Update success!")
	public void update(@PathVariable("id") String id, @Valid @RequestBody DataProductRequest request) {
		productManageService.update(id, request);
	}
}
