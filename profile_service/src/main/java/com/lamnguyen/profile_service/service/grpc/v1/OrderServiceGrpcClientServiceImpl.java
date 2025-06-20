/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:59 AM-15/06/2025
 * User: kimin
 **/

package com.lamnguyen.profile_service.service.grpc.v1;

import com.lamnguyen.order_service.protos.GeneralInfo;
import com.lamnguyen.order_service.protos.OrderServiceGrpc;
import com.lamnguyen.order_service.protos.UserIdsRequest;
import com.lamnguyen.profile_service.service.grpc.IOrderServiceGrpcClient;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceGrpcClientServiceImpl implements IOrderServiceGrpcClient {
	@GrpcClient("fashion-e-commerce-order-service")
	public OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub;

	@Override
	public Map<Long, GeneralInfo> getGeneralInfos(List<Long> ids) {
		return orderServiceBlockingStub.getGeneralInfoByUserId(
				UserIdsRequest.newBuilder()
						.addAllUserId(ids)
						.build()
		).getGeneralInfosMap();
	}
}
