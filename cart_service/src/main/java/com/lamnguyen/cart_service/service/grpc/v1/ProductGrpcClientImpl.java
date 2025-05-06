/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:50 AM-07/05/2025
 * User: Administrator
 **/

package com.lamnguyen.cart_service.service.grpc.v1;

import com.lamnguyen.cart_service.domain.dto.ProductDto;
import com.lamnguyen.cart_service.mapper.IProductMapper;
import com.lamnguyen.cart_service.protos.InventoryServiceGrpc;
import com.lamnguyen.cart_service.protos.ProductRequest;
import com.lamnguyen.cart_service.protos.ProductServiceGrpc;
import com.lamnguyen.cart_service.service.grpc.IProductGrpcClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProductGrpcClientImpl implements IProductGrpcClient {
	@GrpcClient("fashion-e-commerce-product-service")
	@NonFinal
	public ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub;
	IProductMapper productMapper;

	@Override
	public ProductDto getProductDto(String id) {
		var request = ProductRequest.newBuilder().setProductId(id).build();
		var result = productServiceBlockingStub.getProductInCartById(request);
		return productMapper.toProductDto(result);
	}
}
