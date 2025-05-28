/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:50 AM-07/05/2025
 * User: Administrator
 **/

package com.lamnguyen.order_service.service.grpc.v1;

import com.lamnguyen.order_service.protos.ProductInCartDto;
import com.lamnguyen.order_service.protos.ProductRequest;
import com.lamnguyen.order_service.protos.ProductServiceGrpc;
import com.lamnguyen.order_service.protos.TitleProduct;
import com.lamnguyen.order_service.service.grpc.IProductGrpcClient;
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

	@Override
	public ProductInCartDto getProductDto(String id) {
		var request = ProductRequest.newBuilder().setProductId(id).build();
		return productServiceBlockingStub.getProductInCartById(request);
	}

	@Override
	public TitleProduct getTitleProduct(String id) {
		var request = ProductRequest.newBuilder().setProductId(id).build();
		return productServiceBlockingStub.getTitleProductById(request);
	}
}
