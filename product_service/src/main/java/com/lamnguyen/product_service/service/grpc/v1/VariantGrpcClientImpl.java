/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:20 CH-24/04/2025
 * User: Administrator
 **/

package com.lamnguyen.product_service.service.grpc.v1;

import com.lamnguyen.product_service.domain.response.VariantResponse;
import com.lamnguyen.product_service.mapper.IVariantMapper;
import com.lamnguyen.product_service.protos.InventoryServiceGrpc;
import com.lamnguyen.product_service.protos.ProductIdRequest;
import com.lamnguyen.product_service.protos.ProductIdsRequest;
import com.lamnguyen.product_service.protos.VariantAndInventoryInfo;
import com.lamnguyen.product_service.service.grpc.IVariantGrpcClient;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VariantGrpcClientImpl implements IVariantGrpcClient {
    @GrpcClient("fashion-e-commerce-inventory-service")
    public InventoryServiceGrpc.InventoryServiceBlockingStub inventoryServiceBlockingStub;
    private final IVariantMapper variantMapper;

    @Override
    public List<VariantResponse> getVariantsByProductId(String productId) {
        var request = ProductIdRequest.newBuilder().setProductId(productId).build();
        var result = inventoryServiceBlockingStub.getVariantProductByProductIdNotDeleteAndProductNotLock(request);
        return variantMapper.toVariantResponse(result.getVariantsList());
    }

    @Override
    public Map<String, VariantAndInventoryInfo> getVariantAndInventoryInfo(List<String> productIdList) {
        var request = ProductIdsRequest.newBuilder().addAllProductId(productIdList).build();
        return inventoryServiceBlockingStub.getVariantAndInventoryInfos(request).getInfosMap();
    }
}
