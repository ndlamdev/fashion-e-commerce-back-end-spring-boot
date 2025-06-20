/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:04 AM-05/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.service.grpc;

import com.lamnguyen.order_service.domain.response.ProductResponse;
import com.lamnguyen.order_service.protos.TitleProduct;

public interface IProductGrpcClient {
	ProductResponse getProductDto(String id);

	TitleProduct getTitleProduct(String id);
}
