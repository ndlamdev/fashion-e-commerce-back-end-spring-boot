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
import com.lamnguyen.product_service.domain.dto.CollectionSaveRedisDto;
import com.lamnguyen.product_service.domain.response.ProductResponse;
import com.lamnguyen.product_service.mapper.ICollectionMapper;
import com.lamnguyen.product_service.repository.ICollectionRepository;
import com.lamnguyen.product_service.repository.IProductRepository;
import com.lamnguyen.product_service.service.business.ICollectionService;
import com.lamnguyen.product_service.service.business.IProductService;
import com.lamnguyen.product_service.service.redis.ICollectionRedisManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CollectionServiceImpl implements ICollectionService {
	ICollectionRepository collectionRepository;
	ICollectionMapper collectionMapper;
	ICollectionRedisManager collectionRedisManager;
	IProductService productService;
	IProductRepository productRepository;

	@Override
	public CollectionSaveRedisDto findById(String id) {
		return collectionRedisManager.get(id)
				.orElseGet(() -> findInDb(id).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.COLLECTION_NOT_FOUND)));
	}

	private Optional<CollectionSaveRedisDto> findInDb(String id) {
		return collectionRedisManager.cache(id, id, () -> {
			var collection = collectionRepository.findById(id);
			var collectionRedis = collection.map(collectionMapper::toCollectionSaveRedisDto);
			return collectionRedis;
		});
	}

	@Override
	public Page<ProductResponse> getProducts(String id, Pageable pageable) {
		var collection = findById(id);
		var ids = collection.getProducts().stream().skip(pageable.getOffset()).limit(pageable.getPageSize()).toList();
		var products = productService.getProductByids(ids);
		var result = new PageImpl<>(products, pageable, collection.getProducts().size());
		return result;
	}
}
