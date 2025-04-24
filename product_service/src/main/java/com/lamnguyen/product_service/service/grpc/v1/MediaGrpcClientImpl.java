/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:07 PM - 23/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.grpc.v1;

import com.lamnguyen.product_service.protos.ImageCodeRequest;
import com.lamnguyen.product_service.protos.ImageCodesRequest;
import com.lamnguyen.product_service.protos.MediaServiceGrpc;
import com.lamnguyen.product_service.service.grpc.IMediaGrpcClient;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MediaGrpcClientImpl implements IMediaGrpcClient {
	@GrpcClient("fashion-e-commerce-media-service")
	MediaServiceGrpc.MediaServiceBlockingStub mediaServiceBlockingStub;

	@Override
	public boolean existsById(String id) {
		var request = ImageCodeRequest.newBuilder().setImageId(id).build();
		return mediaServiceBlockingStub.checkMediaExists(request).getExists();
	}

	@Override
	public Map<String, Boolean> existsByIds(List<String> ids) {
		var request = ImageCodesRequest.newBuilder()
				.addAllIds(ids)
				.build();
		return mediaServiceBlockingStub.checkListMediaExists(request).getIdExistMapMap();
	}
}
