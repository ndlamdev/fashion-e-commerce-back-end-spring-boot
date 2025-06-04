/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:07 AM-05/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.service.grpc.v1;

import com.lamnguyen.order_service.protos.InventoryServiceGrpc;
import com.lamnguyen.order_service.protos.UpdateQuantityVariantRequest;
import com.lamnguyen.order_service.protos.VariantProductInfo;
import com.lamnguyen.order_service.service.grpc.IInventoryGrpcClient;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class InventoryGrpcClientImpl implements IInventoryGrpcClient {
	@GrpcClient("fashion-e-commerce-inventory-service")
	public InventoryServiceGrpc.InventoryServiceBlockingStub inventoryServiceStub;

	public Map<String, VariantProductInfo> updateQuantityByVariantIds(Map<String, Integer> quantities) {
		var request = UpdateQuantityVariantRequest.newBuilder().putAllQuantities(quantities).build();
		return inventoryServiceStub.updateQuantityByVariantIdsIdsNotDelete(request).getVariantsMap();
	}
}
