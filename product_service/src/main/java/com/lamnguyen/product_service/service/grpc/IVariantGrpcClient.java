/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:20 CH-24/04/2025
 * User: Administrator
 **/

package com.lamnguyen.product_service.service.grpc;

import com.lamnguyen.product_service.domain.response.VariantResponse;

import java.util.List;

public interface IVariantGrpcClient {
	List<VariantResponse> getVariantsByProductId(String productId);
}
