/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:25 PM - 23/04/2025
 * User: kimin
 **/

package com.lamnguyen.media_service.service.grpc;

import com.lamnguyen.media_service.mapper.IMediaMapper;
import com.lamnguyen.media_service.protos.*;
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
public class MediaGrpcServerImpl extends MediaServiceGrpc.MediaServiceImplBase {
	IMediaService mediaService;
	IMediaMapper mediaMapper;

	@Override
	public void checkMediaExists(MediaCodeRequest request, StreamObserver<MediaExistsResponse> responseObserver) {
		log.info("Check media exists with id: {}", request.getMediaId());
		var result = mediaService.existsById(request.getMediaId());
		var response = MediaExistsResponse.newBuilder().setExists(result).build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Override
	public void checkListMediaExists(MediaCodesRequest request, StreamObserver<MediaCodesExistsResponse> responseObserver) {
		log.info("Check list media exists with ids: {}", request.getIdsList());
		var resultData = new HashMap<String, Boolean>();
		request.getIdsList().forEach(id -> {
			resultData.put(id, mediaService.existsById(id));
		});
		var result = MediaCodesExistsResponse.newBuilder()
				.putAllIdExistMap(resultData)
				.build();
		responseObserver.onNext(result);
		responseObserver.onCompleted();
	}

	@Override
	public void getMedias(MediasRequest request, StreamObserver<MediaResponse> responseObserver) {
		log.info("Get medias with ids: {}", request.getIdList());
		var builder = MediaResponse.newBuilder();
		var data = mediaService.getMediaByIds(request.getIdList());
		builder.putAllData(data);
		responseObserver.onNext(builder.build());
		responseObserver.onCompleted();
	}

	@Override
	public void getMediasByFileName(FileNamesRequest request, StreamObserver<MediasByFileNameResponse> responseObserver) {
		log.info("Get medias with names: {}", request.getNamesList());
		var builder = MediasByFileNameResponse.newBuilder();
		var data = mediaService.getMediaByNames(request.getNamesList());
		builder.putAllData(data);
		responseObserver.onNext(builder.build());
		responseObserver.onCompleted();
	}
}
