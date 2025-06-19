/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:49 CH-24/04/2025
 * User: Administrator
 **/

package com.lamnguyen.inventory_service.service.grpc;

import com.lamnguyen.inventory_service.mapper.IInventoryMapper;
import com.lamnguyen.inventory_service.model.VariantProduct;
import com.lamnguyen.inventory_service.protos.*;
import com.lamnguyen.inventory_service.service.business.IInventoryService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.stream.Collectors;

@GrpcService
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class InventoryGrpcServerImpl extends InventoryServiceGrpc.InventoryServiceImplBase {
    IInventoryService inventoryService;
    IInventoryMapper inventoryMapper;

    @Override
    public void getVariantProductByProductIdNotDeleteAndProductNotLock(ProductIdRequest request, StreamObserver<VariantProductResponse> responseObserver) {
        log.info("Get variant product by product id: {}", request.getProductId());
        var data = inventoryService.getAllInventoryByProductId(request.getProductId());
        var response = inventoryMapper.toVariantProductInfo(data);
        responseObserver.onNext(VariantProductResponse.newBuilder().addAllVariants(response).build());
        responseObserver.onCompleted();
    }

    @Override
    public void existsVariant(VariantIdsRequest request, StreamObserver<VariantProductExistResponse> responseObserver) {
        log.info("Check variant product exist: {}", request.getVariantIdsList());
        var data = request.getVariantIdsList().stream().collect(Collectors.toMap(s -> s, inventoryService::existsVariantProductId));
        var response = VariantProductExistResponse.newBuilder().putAllExistIds(data).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getProductIdOfVariantProduct(ProductIdOfVariantRequest request, StreamObserver<ProductIdOfVariantResponse> responseObserver) {
        log.info("Get product id of variant product: {}", request.getVariantId());
        var productId = inventoryService.getProductId(request.getVariantId());
        var response = ProductIdOfVariantResponse.newBuilder().setProductId(productId).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getVariantProductByVariantIdsIdsNotDeleteAndProductNotLock(VariantIdsRequest request, StreamObserver<VariantProductByVariantIdsResponse> responseObserver) {
        log.info("Get variant product by variant ids: {}", request.getVariantIdsList());
        var ids = request.getVariantIdsList();
        var map = ids.stream().collect(Collectors.toMap(s -> s, o -> inventoryMapper.toVariantProductInfo(inventoryService.getVariantProductById(o))));
        var result = VariantProductByVariantIdsResponse.newBuilder().putAllVariants(map).build();
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void updateQuantityByVariantIdsIdsNotDelete(UpdateQuantityVariantRequest request, StreamObserver<VariantProductByVariantIdsResponse> responseObserver) {
        log.info("Update quantity variant product by variant ids: {}", request.getQuantitiesMap());
        var listVariant = inventoryService.updateVariantProducts(request.getQuantitiesMap());
        var map = listVariant.stream().collect(Collectors.toMap(VariantProduct::getId, inventoryMapper::toVariantProductInfo));
        var result = VariantProductByVariantIdsResponse.newBuilder().putAllVariants(map).build();
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void getVariantAndInventoryInfos(ProductIdsRequest request, StreamObserver<VariantAndInventoriesResponse> responseObserver) {
        log.info("Get all variant and inventory infos by product ids: {}", request.getProductIdList());
        var map = inventoryService.getVariantAndInventoryInfo(request.getProductIdList());
        var result = VariantAndInventoriesResponse.newBuilder().putAllInfos(map).build();
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }
}
