/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:07 AM-05/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.service.grpc.v1;

import com.lamnguyen.cart_service.protos.InventoryServiceGrpc;
import com.lamnguyen.cart_service.protos.ProductIdOfVariantRequest;
import com.lamnguyen.cart_service.protos.VariantIdsRequest;
import com.lamnguyen.cart_service.protos.VariantProductInfo;
import com.lamnguyen.cart_service.service.grpc.IInventoryGrpcClient;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InventoryGrpcClientImpl implements IInventoryGrpcClient {
	@GrpcClient("fashion-e-commerce-inventory-service")
	public InventoryServiceGrpc.InventoryServiceBlockingStub inventoryServiceStub;

	@Override
	public Map<String, Boolean> existVariantProductByVariantIds(List<String> ids) {
		var request = VariantIdsRequest.newBuilder().addAllVariantIds(ids).build();
		return inventoryServiceStub.existsVariant(request).getExistIdsMap();
	}

	@Override
	public boolean existVariantProductByVariantId(String id) {
		return existVariantProductByVariantIds(List.of(id)).getOrDefault(id, false);
	}

	@Override
	public String getProductIdByVariantId(String variantId) {
		var request = ProductIdOfVariantRequest.newBuilder().setVariantId(variantId).build();
		return inventoryServiceStub.getProductIdOfVariantProduct(request).getProductId();
	}

	@Override
	public VariantProductInfo getVariantProductByVariantId(String variantId) {
		var result = getVariantProductByVariantIds(List.of(variantId));
		return result.getOrDefault(variantId, null);
	}

	@Override
	public Map<String, VariantProductInfo> getVariantProductByVariantIds(List<String> variantIds) {
		var request = VariantIdsRequest.newBuilder().addAllVariantIds(variantIds).build();
		return inventoryServiceStub.getVariantProductByVariantIdsIdsNotDeleteAndProductNotLock(request).getVariantsMap();
	}
}
