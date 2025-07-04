/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:53 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.business;

import com.lamnguyen.product_service.domain.dto.CollectionSaveRedisDto;
import com.lamnguyen.product_service.domain.response.ProductResponse;
import com.lamnguyen.product_service.utils.enums.CollectionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


public interface ICollectionService {
	CollectionSaveRedisDto findById(String id);

	Page<ProductResponse> getProducts(String id, Pageable pageable);

	Map<CollectionType, List<CollectionSaveRedisDto>> getCollections();

	Page<ProductResponse> getProducts(CollectionType type, Pageable pageable);
}
