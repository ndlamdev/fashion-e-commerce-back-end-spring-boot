/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:49 CH-24/04/2025
 * User: Administrator
 **/

package com.lamnguyen.inventory_service.service.grpc;

import com.lamnguyen.inventory_service.mapper.IInventoryMapper;
import com.lamnguyen.inventory_service.protos.InventoryServiceGrpc;
import com.lamnguyen.inventory_service.protos.VariantProductRequest;
import com.lamnguyen.inventory_service.protos.VariantProductResponse;
import com.lamnguyen.inventory_service.service.business.IInventoryService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class InventoryGrpcServerImpl extends InventoryServiceGrpc.InventoryServiceImplBase {
	IInventoryService inventoryService;
	IInventoryMapper inventoryMapper;

	@Override
	public void getVariantProductNotDeleteAndProductNotLock(VariantProductRequest request, StreamObserver<VariantProductResponse> responseObserver) {
		var data = inventoryService.getAllInventory(request.getProductId());
		var response = inventoryMapper.toVariantProductInfo(data);
		responseObserver.onNext(VariantProductResponse.newBuilder().addAllVariants(response).build());
		responseObserver.onCompleted();
	}
}
