/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:25 PM - 23/04/2025
 * User: kimin
 **/

package com.lamnguyen.media_service.service.grpc;

import com.lamnguyen.media_service.protos.ImageCodeRequest;
import com.lamnguyen.media_service.protos.ImageCodesExistsResponse;
import com.lamnguyen.media_service.protos.ImageCodesRequest;
import com.lamnguyen.media_service.protos.ImageExistsResponse;
import com.lamnguyen.media_service.protos.MediaCheckedServiceGrpc.MediaCheckedServiceImplBase;
import com.lamnguyen.media_service.service.business.IMediaService;
import io.grpc.stub.StreamObserver;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.HashMap;

@GrpcService
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MediaGrpcServerImpl extends MediaCheckedServiceImplBase {
	IMediaService mediaService;

	@Override
	public void checkMediaExists(ImageCodeRequest request, StreamObserver<ImageExistsResponse> responseObserver) {
		var result = mediaService.existsById(request.getImageId());
		var response = ImageExistsResponse.newBuilder().setExists(result).build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Override
	public void checkListMediaExists(ImageCodesRequest request, StreamObserver<ImageCodesExistsResponse> responseObserver) {
		var resultData = new HashMap<String, Boolean>();
		request.getIdsList().forEach(id -> {
			resultData.put(id, mediaService.existsById(id));
		});
		var result = ImageCodesExistsResponse.newBuilder()
				.putAllIdExistMap(resultData)
				.build();
		responseObserver.onNext(result);
		responseObserver.onCompleted();
	}
}
