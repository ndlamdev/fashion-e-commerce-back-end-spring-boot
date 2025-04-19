package com.lamnguyen.authentication_service.service.grpc.impl;

import com.lamnguyen.authentication_service.protos.ProfileServiceGrpc.ProfileServiceBlockingStub;
import com.lamnguyen.authentication_service.protos.UserRequest;
import com.lamnguyen.authentication_service.protos.UserResponse;
import com.lamnguyen.authentication_service.service.grpc.IProfileCostumerGrpcClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProfileCostumerGrpcClientImpl implements IProfileCostumerGrpcClient {
	@NonFinal
	@GrpcClient("fashion-e-commerce-profile-service")
	ProfileServiceBlockingStub profileServiceBlockingStub;

	@Override
	public UserResponse findById(long id) {
		var request = UserRequest.newBuilder().setUserId(id).build();
		return profileServiceBlockingStub.getUserProfile(request);
	}
}
