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
import com.lamnguyen.product_service.mapper.IVariantMapper;
import com.lamnguyen.product_service.repository.IProductRepository;
import com.lamnguyen.product_service.service.business.IProductService;
import com.lamnguyen.product_service.service.grpc.IVariantGrpcClient;
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
	IVariantGrpcClient variantService;

	@Override
	public ProductDto getProductDtoById(String id) {
		var start = System.currentTimeMillis();
		var data = redisManager.get(id)
				.or(() -> redisManager.cache(id, () -> productRepository.findById(id).map(productMapper::toProductDto)))
				.orElseThrow(() -> ApplicationException.createException(ExceptionEnum.PRODUCT_NOT_FOUND));
		var end = System.currentTimeMillis();
		System.out.println("Time to get product from redis: " + (end - start) + "ms");
		start = System.currentTimeMillis();
		data.setVariants(variantService.getVariantsByProductId(id));
		end = System.currentTimeMillis();
		System.out.println("Time to get variants from grpc: " + (end - start) + "ms");
		return data;
	}
}
