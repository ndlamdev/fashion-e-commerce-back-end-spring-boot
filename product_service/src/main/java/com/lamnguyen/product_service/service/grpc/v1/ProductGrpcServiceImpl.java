/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:24 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.grpc.v1;

import com.lamnguyen.product_service.protos.ProductInCartDto;
import com.lamnguyen.product_service.protos.ProductRequest;
import com.lamnguyen.product_service.protos.ProductServiceGrpc;
import com.lamnguyen.product_service.protos.TitleProduct;
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
	public void getProductById(ProductRequest request, StreamObserver<com.lamnguyen.product_service.protos.ProductResponseGrpc> responseObserver) {
		log.info("Get product by id: {}", request.getProductId());
		String productId = request.getProductId();

		// Get product from the existing service
		var productResponseGrpc = productService.getProductResponseGrpcById(productId);

		// Send the response
		responseObserver.onNext(productResponseGrpc);
		responseObserver.onCompleted();
	}

	@Override
	public void getProductInCartById(ProductRequest request, StreamObserver<ProductInCartDto> responseObserver) {
		log.info("Get product in cart by id: {}", request.getProductId());
		String productId = request.getProductId();

		// Get product from the existing service
		var protoProductDto = productService.getProductInCartById(productId);

		// Send the response
		responseObserver.onNext(protoProductDto);
		responseObserver.onCompleted();
	}

	@Override
	public void getTitleProductById(ProductRequest request, StreamObserver<TitleProduct> responseObserver) {
		log.info("Get product title by id: {}", request.getProductId());
		String productId = request.getProductId();

		// Get product from the existing service
		var protoProductDto = productService.getTitleProductById(productId);

		// Send the response
		responseObserver.onNext(protoProductDto);
		responseObserver.onCompleted();
	}
}
