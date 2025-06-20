/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:17 AM-15/06/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.service.grpc.v1;

import com.lamnguyen.order_service.protos.GeneralInfosResponse;
import com.lamnguyen.order_service.protos.OrderServiceGrpc;
import com.lamnguyen.order_service.protos.UserIdsRequest;
import com.lamnguyen.order_service.service.business.IOrderService;
import io.grpc.stub.StreamObserver;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderGrpcServerImpl extends OrderServiceGrpc.OrderServiceImplBase {
	IOrderService orderService;

	@Override
	public void getGeneralInfoByUserId(UserIdsRequest request, StreamObserver<GeneralInfosResponse> responseObserver) {
		var response = orderService.getGeneralInfoByUserId(request.getUserIdList());
		responseObserver.onNext(GeneralInfosResponse.newBuilder().putAllGeneralInfos(response).build());
		responseObserver.onCompleted();
	}


}
