/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:24 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.grpc.v1;

import com.lamnguyen.product_service.mapper.*;
import com.lamnguyen.product_service.protos.ProductInCartDto;
import com.lamnguyen.product_service.protos.ProductRequest;
import com.lamnguyen.product_service.protos.ProductServiceGrpc;
import com.lamnguyen.product_service.service.business.IProductService;
import io.grpc.stub.StreamObserver;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductGrpcServiceImpl extends ProductServiceGrpc.ProductServiceImplBase {
	IProductService productService;

	@Override
	public void getProductById(ProductRequest request, StreamObserver<com.lamnguyen.product_service.protos.ProductDto> responseObserver) {
		String productId = request.getProductId();

		// Get product from the existing service
		var protoProductDto = productService.getProductProtoById(productId);

		// Log the request and response
		log.info("==============================================================================================================================================================================================");
		log.info("gRPC method: {}", "getProductById");
		log.info("gRPC Request: {}", productId);
		log.info("==============================================================================================================================================================================================");

		// Send the response
		responseObserver.onNext(protoProductDto);
		responseObserver.onCompleted();
	}

	@Override
	public void getProductInCartById(ProductRequest request, StreamObserver<ProductInCartDto> responseObserver) {
		String productId = request.getProductId();

		// Get product from the existing service
		var protoProductDto = productService.getProductInCartById(productId);

		// Log the request and response
		log.info("==============================================================================================================================================================================================");
		log.info("gRPC method: {}", "getProductInCartById");
		log.info("gRPC Request: {}", productId);
		log.info("==============================================================================================================================================================================================");

		// Send the response
		responseObserver.onNext(protoProductDto);
		responseObserver.onCompleted();
	}
}
