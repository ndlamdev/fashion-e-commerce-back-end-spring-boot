package com.lamnguyen.authentication_service.service.grpc.impl;

import com.lamnguyen.authentication_service.domain.dto.ProfileUserDto;
import com.lamnguyen.authentication_service.mapper.IProfileUserMapper;
import com.lamnguyen.authentication_service.protos.ProfileServiceGrpc;
import com.lamnguyen.authentication_service.protos.ProfileUserRequest;
import com.lamnguyen.authentication_service.service.grpc.IProfileUserGrpcClient;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileUserGrpcClientImpl implements IProfileUserGrpcClient {
	@GrpcClient("fashion-e-commerce-profile-service")
	public ProfileServiceGrpc.ProfileServiceBlockingStub profileServiceBlockingStub;
	private final IProfileUserMapper profileUserMapper;

	@Override
	public ProfileUserDto findById(long id) {
		var request = ProfileUserRequest.newBuilder().setUserId(id).build();
		var data = profileServiceBlockingStub.getUserProfile(request);
		return profileUserMapper.toProfileUserDto(data);
	}
}
