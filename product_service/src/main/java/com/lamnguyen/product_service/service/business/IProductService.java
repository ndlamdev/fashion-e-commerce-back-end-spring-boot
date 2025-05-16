/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:29 PM - 07/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.business;

import com.lamnguyen.product_service.domain.response.ProductResponse;
import com.lamnguyen.product_service.protos.ProductInCartDto;
import org.springframework.data.domain.Page;

import java.io.File;
import java.util.List;

public interface IProductService {
	ProductResponse getProductById(String id);

	com.lamnguyen.product_service.protos.ProductDto getProductProtoById(String id);

	ProductInCartDto getProductInCartById(String id);

	List<ProductResponse> getProductByIds(List<String> ids);

	Page<ProductResponse> searchByImage(File file);
}
