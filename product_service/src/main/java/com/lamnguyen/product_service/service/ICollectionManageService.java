/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:53 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service;

import com.lamnguyen.product_service.domain.dto.CollectionDTO;
import com.lamnguyen.product_service.domain.dto.ProductDTO;
import com.lamnguyen.product_service.domain.request.IdCollectionRequest;
import com.lamnguyen.product_service.domain.request.TitleCollectionRequest;
import com.lamnguyen.product_service.domain.request.UpdateCollectionRequest;

import java.util.List;

public interface ICollectionManageService {
	void create(TitleCollectionRequest request);

	List<CollectionDTO> getAll();

	void update(UpdateCollectionRequest request);

	void delete(IdCollectionRequest request);

	List<ProductDTO> getAllProductByCollectionId(String id);
}
