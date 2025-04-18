/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:50 PM - 18/04/2025
 * User: kimin
 **/

package com.lamnguyen.profile_service.service.grpc;

import com.lamnguyen.profile_service.mapper.ICustomerMapper;
import com.lamnguyen.profile_service.protos.ProfileServiceGrpc;
import com.lamnguyen.profile_service.protos.UserRequest;
import com.lamnguyen.profile_service.protos.UserResponse;
import com.lamnguyen.profile_service.service.business.ICustomerService;
import io.grpc.stub.StreamObserver;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileServiceGrpcImpl extends ProfileServiceGrpc.ProfileServiceImplBase {
	ICustomerService customerService;
	ICustomerMapper customerMapper;

	@Override
	public void getUserProfile(UserRequest request, StreamObserver<UserResponse> responseObserver) {
		var data = customerService.getCustomerById(request.getUserId());
		var result = customerMapper.toUserResponse(data);
		responseObserver.onNext(result);
		responseObserver.onCompleted();
	}
}
