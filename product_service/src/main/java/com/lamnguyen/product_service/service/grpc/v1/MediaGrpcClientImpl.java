/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:07 PM - 23/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.grpc.v1;

import com.lamnguyen.product_service.domain.response.ImageResponse;
import com.lamnguyen.product_service.mapper.IImageMapper;
import com.lamnguyen.product_service.protos.*;
import com.lamnguyen.product_service.service.grpc.IMediaGrpcClient;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MediaGrpcClientImpl implements IMediaGrpcClient {
	@GrpcClient("fashion-e-commerce-media-service")
	MediaServiceGrpc.MediaServiceBlockingStub mediaServiceBlockingStub;
	private final IImageMapper imageMapper;

	@Override
	public boolean existsById(String id) {
		var request = MediaCodeRequest.newBuilder().setMediaId(id).build();
		return mediaServiceBlockingStub.checkMediaExists(request).getExists();
	}

	@Override
	public Map<String, Boolean> existsByIds(List<String> ids) {
		var request = MediaCodesRequest.newBuilder()
				.addAllIds(ids)
				.build();
		return mediaServiceBlockingStub.checkListMediaExists(request).getIdExistMapMap();
	}

	@Override
	public Map<String, ImageResponse> getImageDto(List<String> ids) {
		var dataMap = mediaServiceBlockingStub.getMedias(MediasRequest.newBuilder().addAllId(ids).build()).getDataMap();
		var result = new HashMap<String, ImageResponse>();
		ids.forEach(id -> {
			var data = dataMap.getOrDefault(id, null);
			if (data == null) result.put(id, null);
			else result.put(id, imageMapper.toImageResponse(data));
		});
		return result;
	}

	@Override
	public Map<String, ImageResponse> findImageByFileName(List<String> fileNames) {
		var dataMap = mediaServiceBlockingStub.getMediasByFileName(FileNamesRequest.newBuilder().addAllNames(fileNames).build()).getDataMap();
		var result = new HashMap<String, ImageResponse>();
		fileNames.forEach(name -> {
			var data = dataMap.getOrDefault(name, null);
			if (data == null) result.put(name, null);
			else result.put(name, imageMapper.toImageResponse(data));
		});
		return result;
	}
}
