/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:53 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.business;

import com.lamnguyen.product_service.domain.dto.CollectionDto;
import com.lamnguyen.product_service.domain.response.ProductResponse;
import com.lamnguyen.product_service.domain.request.IdCollectionRequest;
import com.lamnguyen.product_service.domain.request.TitleCollectionRequest;
import com.lamnguyen.product_service.domain.request.UpdateCollectionRequest;

import java.util.List;

public interface ICollectionManageService {
	boolean existsById(String id);

	void create(TitleCollectionRequest request);

	List<CollectionDto> getAll();

	void update(UpdateCollectionRequest request);

	void delete(IdCollectionRequest request);

	List<ProductResponse> getAllProductByCollectionId(String id);

	void addProductId(String collectionId, String productId);

	void removeProductId(String collectionId, String productId);
}
