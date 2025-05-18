/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:46 PM-02/05/2025
 * User: kimin
 **/

package com.lamnguyen.payment_service.service.grpc;

import com.lamnguyen.payment_service.protos.BaseRequest;
import com.lamnguyen.payment_service.protos.BaseResponse;
import com.lamnguyen.payment_service.protos.BaseServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BaseGrpcServerImpl extends BaseServiceGrpc.BaseServiceImplBase {
	@Override
	public void greeting(BaseRequest request, StreamObserver<BaseResponse> responseObserver) {
		var response = BaseResponse.newBuilder().setResponse("Hello " + request.getName()).build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
}
