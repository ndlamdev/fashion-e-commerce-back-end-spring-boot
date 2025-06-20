/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:28 PM - 07/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.controller.admin;

import com.lamnguyen.product_service.domain.request.DataProductRequest;
import com.lamnguyen.product_service.domain.response.AdminSubProductResponse;
import com.lamnguyen.product_service.service.business.IProductManageService;
import com.lamnguyen.product_service.utils.annotation.ApiMessageResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
		productManageService.create(request);
	}

	@PostMapping("/{id}")
	@ApiMessageResponse("Update success!")
	public void update(@PathVariable("id") String id, @Valid @RequestBody DataProductRequest request) {
		productManageService.update(id, request);
	}

	@PutMapping("/{id}")
	@ApiMessageResponse("Lock product success!")
	public void lock(@PathVariable("id") String id, @RequestParam("isLock") boolean isLock) {
		productManageService.lock(id, isLock);
	}

	@GetMapping()
	@ApiMessageResponse("Get all success!")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ADMIN_GET_ALL_PRODUCT')")
	public List<AdminSubProductResponse> getAll(){
		return productManageService.getAll();
	}
}
