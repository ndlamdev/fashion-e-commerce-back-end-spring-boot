/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:57 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.business.v1;

import com.lamnguyen.product_service.config.exception.ApplicationException;
import com.lamnguyen.product_service.config.exception.ExceptionEnum;
import com.lamnguyen.product_service.domain.request.CreateProductRequest;
import com.lamnguyen.product_service.mapper.IProductMapper;
import com.lamnguyen.product_service.repository.IProductRepository;
import com.lamnguyen.product_service.service.business.ICollectionManageService;
import com.lamnguyen.product_service.service.business.IProductManageService;
import com.lamnguyen.product_service.service.redis.IProductRedisManager;
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
	ICollectionManageService collectionManageService;
	IProductRedisManager redisManager;

	@Override
	public void create(CreateProductRequest request) {
		if (!collectionManageService.existById(request.collection()))
			throw ApplicationException.createException(ExceptionEnum.COLLECTION_NOT_FOUND);

		var product = productMapper.toProduct(request);
		var inserted = productRepository.insert(product);
		collectionManageService.addProductId(request.collection(), inserted.getId());
		redisManager.save(inserted.getId(), productMapper.toProductDto(inserted));
	}

	@Override
	public void update() {

	}

	@Override
	public void lock() {

	}
}
