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
import com.lamnguyen.cart_service.protos.VariantProductExistRequest;
import com.lamnguyen.cart_service.service.grpc.IInventoryGrpcClient;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class InventoryGrpcClientImpl implements IInventoryGrpcClient {
	@GrpcClient("fashion-e-commerce-inventory-service")
	@NonFinal
	public InventoryServiceGrpc.InventoryServiceBlockingStub inventoryServiceStub;

	@Override
	public Map<String, Boolean> existInventory(List<String> ids) {
		var request = VariantProductExistRequest.newBuilder().addAllIds(ids).build();
		return inventoryServiceStub.existsVariant(request).getExistIdsMap();
	}

	@Override
	public boolean existInventory(String id) {
		return existInventory(List.of(id)).getOrDefault(id, false);
	}

	@Override
	public String productIdOfVariant(String id) {
		var request = ProductIdOfVariantRequest.newBuilder().setVariantId(id).build();
		return inventoryServiceStub.getProductIdOfVariantProduct(request).getProductId();
	}
}
