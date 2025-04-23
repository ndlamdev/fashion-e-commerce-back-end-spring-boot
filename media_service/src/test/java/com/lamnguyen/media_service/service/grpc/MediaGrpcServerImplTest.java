/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:25 PM - 23/04/2025
 * User: kimin
 **/

package com.lamnguyen.media_service.service.grpc;

import com.lamnguyen.media_service.service.business.IMediaService;
import com.lamnguyen.media_service.service.protos.ImageCodeRequest;
import com.lamnguyen.media_service.service.protos.ImageExistsResponse;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MediaGrpcServerImplTest {

    @Mock
    private IMediaService mediaService;

    @InjectMocks
    private MediaGrpcServerImpl mediaGrpcServer;

    @Captor
    private ArgumentCaptor<ImageExistsResponse> responseCaptor;

    @Mock
    private StreamObserver<ImageExistsResponse> responseObserver;

    @BeforeEach
    void setUp() {
        // Additional setup if needed
    }

    @Test
    void checkMediaExists_WhenMediaExists_ShouldReturnTrue() {
        // Arrange
        long imageId = 123L;
        ImageCodeRequest request = ImageCodeRequest.newBuilder().setImageId(imageId).build();

        when(mediaService.existsById(imageId)).thenReturn(true);

        // Act
        mediaGrpcServer.checkMediaExists(request, responseObserver);

        // Assert
        verify(responseObserver).onNext(responseCaptor.capture());
        verify(responseObserver).onCompleted();

        ImageExistsResponse response = responseCaptor.getValue();
        assertTrue(response.getExists());

        verify(mediaService, times(1)).existsById(imageId);
        verifyNoMoreInteractions(mediaService);
    }

    @Test
    void checkMediaExists_WhenMediaDoesNotExist_ShouldReturnFalse() {
        // Arrange
        long imageId = 456L;
        ImageCodeRequest request = ImageCodeRequest.newBuilder().setImageId(imageId).build();

        when(mediaService.existsById(imageId)).thenReturn(false);

        // Act
        mediaGrpcServer.checkMediaExists(request, responseObserver);

        // Assert
        verify(responseObserver).onNext(responseCaptor.capture());
        verify(responseObserver).onCompleted();

        ImageExistsResponse response = responseCaptor.getValue();
        assertFalse(response.getExists());

        verify(mediaService, times(1)).existsById(imageId);
        verifyNoMoreInteractions(mediaService);
    }
}
