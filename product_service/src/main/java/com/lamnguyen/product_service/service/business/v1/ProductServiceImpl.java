/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:24 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.business.v1;

import com.lamnguyen.product_service.config.exception.ApplicationException;
import com.lamnguyen.product_service.config.exception.ExceptionEnum;
import com.lamnguyen.product_service.domain.dto.ProductDto;
import com.lamnguyen.product_service.mapper.IProductMapper;
import com.lamnguyen.product_service.repository.IProductRepository;
import com.lamnguyen.product_service.service.business.IProductService;
import com.lamnguyen.product_service.service.redis.IProductRedisManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProductServiceImpl implements IProductService {
	IProductMapper productMapper;
	IProductRepository productRepository;
	IProductRedisManager redisManager;

	@Override
	public ProductDto getProductDtoById(String id) {
		return redisManager.get(id).orElseGet(() ->
				redisManager.cache(id, id, id, (input) -> productRepository.findById(input).map(productMapper::toProductDto))
						.orElseThrow(() -> ApplicationException.createException(ExceptionEnum.PRODUCT_NOT_FOUND))
		);
	}
}
