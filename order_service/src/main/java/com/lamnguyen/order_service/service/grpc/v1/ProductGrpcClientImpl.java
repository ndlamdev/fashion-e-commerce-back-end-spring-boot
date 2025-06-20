/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:50 AM-07/05/2025
 * User: Administrator
 **/

package com.lamnguyen.order_service.service.grpc.v1;

import com.lamnguyen.order_service.domain.response.ProductResponse;
import com.lamnguyen.order_service.mapper.IProductMapper;
import org.springframework.stereotype.Service;

import com.lamnguyen.order_service.protos.ProductInCartDto;
import com.lamnguyen.order_service.protos.ProductRequest;
import com.lamnguyen.order_service.protos.ProductServiceGrpc;
import com.lamnguyen.order_service.protos.TitleProduct;
import com.lamnguyen.order_service.service.grpc.IProductGrpcClient;

import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
@RequiredArgsConstructor
public class ProductGrpcClientImpl implements IProductGrpcClient {
	@GrpcClient("fashion-e-commerce-product-service")
	public ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub;
	private final IProductMapper productMapper;

	@Override
	public ProductResponse getProductDto(String id) {
		var request = ProductRequest.newBuilder().setProductId(id).build();
		var response = productServiceBlockingStub.getProductInCartById(request);
		return productMapper.toProductResponse(response);
	}

	@Override
	public TitleProduct getTitleProduct(String id) {
		var request = ProductRequest.newBuilder().setProductId(id).build();
		return productServiceBlockingStub.getTitleProductById(request);
	}
}
