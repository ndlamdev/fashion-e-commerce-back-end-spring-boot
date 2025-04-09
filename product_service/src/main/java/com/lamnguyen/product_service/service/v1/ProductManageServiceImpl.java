/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:57 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.v1;

import com.lamnguyen.product_service.domain.request.CreateProductRequest;
import com.lamnguyen.product_service.mapper.IProductMapper;
import com.lamnguyen.product_service.repository.IProductRepository;
import com.lamnguyen.product_service.service.IProductManageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductManageServiceImpl implements IProductManageService {
	IProductRepository productRepository;
	IProductMapper productMapper;

	@Override
	public void create(CreateProductRequest request) {
		var product = productMapper.toProduct(request);
		productRepository.insert(product);
	}

	@Override
	public void update() {

	}

	@Override
	public void lock() {

	}
}
