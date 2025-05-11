/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:04 AM-05/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.service.grpc;

import com.lamnguyen.cart_service.protos.VariantProductInfo;

import java.util.List;
import java.util.Map;

public interface IInventoryGrpcClient {
	Map<String, Boolean> existVariantProductByVariantIds(List<String> ids);

	boolean existVariantProductByVariantId(String id);

	String getProductIdByVariantId(String variantId);

	VariantProductInfo getVariantProductByVariantId(String variantId);

	Map<String, VariantProductInfo> getVariantProductByVariantIds(List<String> variantId);
}
