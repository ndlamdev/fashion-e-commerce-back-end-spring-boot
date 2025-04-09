/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:57 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.v1;

import com.lamnguyen.product_service.config.exception.ApplicationException;
import com.lamnguyen.product_service.config.exception.ExceptionEnum;
import com.lamnguyen.product_service.domain.dto.CollectionDTO;
import com.lamnguyen.product_service.domain.dto.ProductDTO;
import com.lamnguyen.product_service.domain.request.IdCollectionRequest;
import com.lamnguyen.product_service.domain.request.TitleCollectionRequest;
import com.lamnguyen.product_service.domain.request.UpdateCollectionRequest;
import com.lamnguyen.product_service.mapper.ICollectionMapper;
import com.lamnguyen.product_service.mapper.IProductMapper;
import com.lamnguyen.product_service.repository.ICollectionRepository;
import com.lamnguyen.product_service.service.ICollectionManageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CollectionManageServiceImpl implements ICollectionManageService {
	ICollectionRepository collectionRepository;
	ICollectionMapper collectionMapper;
	IProductMapper productMapper;

	@Override
	public void create(TitleCollectionRequest request) {
		var collection = collectionMapper.toCollection(request);
		collectionRepository.insert(collection);
	}

	@Override
	public List<CollectionDTO> getAll() {
		return List.of();
	}

	@Override
	public void update(UpdateCollectionRequest request) {

	}

	@Override
	public void delete(IdCollectionRequest request) {

	}

	@Override
	public List<ProductDTO> getAllProductByCollectionId(String id) {
		return collectionRepository.findById(id).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.PRODUCT_NOT_FOUND)).getProducts().stream().map(productMapper::toDto).toList();
	}
}
