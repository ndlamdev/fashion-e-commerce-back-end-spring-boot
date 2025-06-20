/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:29 PM - 07/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.business;

import com.lamnguyen.product_service.domain.response.ProductResponse;
import com.lamnguyen.product_service.domain.response.QuickProductResponse;
import com.lamnguyen.product_service.model.ProductFilterAndSort;
import com.lamnguyen.product_service.protos.ProductInCartDto;
import com.lamnguyen.product_service.protos.TitleProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.util.List;

public interface IProductService {
	ProductResponse getProductById(String id);

	com.lamnguyen.product_service.protos.ProductResponseGrpc getProductResponseGrpcById(String id);

	ProductInCartDto getProductInCartById(String id);

	List<ProductResponse> getProductByIds(List<String> ids);

	Page<ProductResponse> searchByImage(File file);

	Page<ProductResponse> search(Pageable pageable, ProductFilterAndSort filterAndSort);

	List<QuickProductResponse> quickSearch(String title);

	TitleProduct getTitleProductById(String productId);
}
