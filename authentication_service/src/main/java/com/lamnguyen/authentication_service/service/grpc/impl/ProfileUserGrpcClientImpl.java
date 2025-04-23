package com.lamnguyen.authentication_service.service.grpc.impl;

import com.lamnguyen.authentication_service.domain.dto.ProfileUserDto;
import com.lamnguyen.authentication_service.mapper.IProfileUserMapper;
import com.lamnguyen.authentication_service.protos.ProfileServiceGrpc.ProfileServiceBlockingStub;
import com.lamnguyen.authentication_service.protos.ProfileUserRequest;
import com.lamnguyen.authentication_service.service.grpc.IProfileUserGrpcClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProfileUserGrpcClientImpl implements IProfileUserGrpcClient {
	@NonFinal
	@GrpcClient("fashion-e-commerce-profile-service")
	ProfileServiceBlockingStub profileServiceBlockingStub;
	IProfileUserMapper profileUserMapper;

	@Override
	public ProfileUserDto findById(long id) {
		var request = ProfileUserRequest.newBuilder().setUserId(id).build();
		var data = profileServiceBlockingStub.getUserProfile(request);
		return profileUserMapper.toProfileUserDto(data);
	}
}
