/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:21 PM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.payment_service.service.grpc;

import com.google.protobuf.Empty;
import com.lamnguyen.payment_service.protos.*;
import com.lamnguyen.payment_service.service.business.IPaymentService;
import io.grpc.stub.StreamObserver;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

@GrpcService
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentGrpcServerImpl extends PaymentServiceGrpc.PaymentServiceImplBase {
	IPaymentService paymentService;

	@Override
	public void pay(PaymentRequest request, StreamObserver<PaymentResponse> responseObserver) {
		log.info("Pay request: \n{}", request);
		var result = paymentService.pay(request);
		responseObserver.onNext(result);
		responseObserver.onCompleted();
	}

	@Override
	public void cancelOrder(OrderIdRequest request, StreamObserver<Empty> responseObserver) {
		log.info("Cancel order request: {}", request);
		try {
			paymentService.cancelPayByOrderId(request.getId());
			responseObserver.onNext(Empty.getDefaultInstance());
			responseObserver.onCompleted();
		} catch (Exception e) {
			log.error("Cancel order error: {}", e.getMessage());
			responseObserver.onError(e);
		}
	}

	@Override
	public void getPaymentStatus(OrderIdRequest request, StreamObserver<PaymentResponse> responseObserver) {
		log.info("Get payment status request: {}", request);
		var result = paymentService.getPaymentStatusByOrderId(request.getId());
		responseObserver.onNext(result);
		responseObserver.onCompleted();
	}

	@Override
	public void getPaymentStatuses(OrderIdsRequest request, StreamObserver<PaymentsResponse> responseObserver) {
		log.info("Get payment statuses request: {}", request);
		var map = new HashMap<Long, PaymentResponse>();
		var listTask = new ArrayList<CompletableFuture<Void>>();
		request.getIdList().forEach(orderId -> {
			var task = CompletableFuture.runAsync(() -> {
				var result = paymentService.getPaymentStatusByOrderId(orderId);
				if (result == null) return;
				map.put(orderId, result);
			});
			listTask.add(task);
		});
		CompletableFuture.allOf(listTask.toArray(CompletableFuture[]::new)).join();
		responseObserver.onNext(PaymentsResponse.newBuilder().putAllPayments(map).build());
		responseObserver.onCompleted();
	}
}
