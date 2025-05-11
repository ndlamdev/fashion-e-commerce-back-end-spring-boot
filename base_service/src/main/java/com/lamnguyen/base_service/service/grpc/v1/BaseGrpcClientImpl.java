/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:44 PM-02/05/2025
 * User: kimin
 **/

package com.lamnguyen.base_service.service.grpc.v1;

import com.lamnguyen.base_service.protos.BaseRequest;
import com.lamnguyen.base_service.protos.BaseServiceGrpc;
import com.lamnguyen.base_service.service.grpc.IBaseGrpcClient;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseGrpcClientImpl implements IBaseGrpcClient {
	@GrpcClient("fashion-e-commerce-base-service")
	BaseServiceGrpc.BaseServiceBlockingStub baseServiceBlockingStub;

	@Override
	public String greeting(String name) {
		var request = BaseRequest.newBuilder().setName(name).build();
		return baseServiceBlockingStub.greeting(request).getResponse();
	}
}
