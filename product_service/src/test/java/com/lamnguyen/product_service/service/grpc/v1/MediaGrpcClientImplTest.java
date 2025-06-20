/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:07 PM - 23/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.grpc.v1;

import com.lamnguyen.product_service.protos.MediaCodeRequest;
import com.lamnguyen.product_service.protos.MediaExistsResponse;
import com.lamnguyen.product_service.protos.MediaServiceGrpc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MediaGrpcClientImplTest {

	@Mock
	private MediaServiceGrpc.MediaServiceBlockingStub mediaServiceBlockingStub;

	@InjectMocks
	private MediaGrpcClientImpl mediaGrpcClient;

	@BeforeEach
	void setUp() {
		// Set the mocked stub on the client
		ReflectionTestUtils.setField(mediaGrpcClient, "mediaServiceBlockingStub", mediaServiceBlockingStub);
	}

	@Test
	void existsById_WhenMediaExists_ShouldReturnTrue() {
		// Arrange
		String imageId = "123L";
		MediaExistsResponse response = MediaExistsResponse.newBuilder().setExists(true).build();

		when(mediaServiceBlockingStub.checkMediaExists(any(MediaCodeRequest.class))).thenReturn(response);

		// Act
		boolean result = mediaGrpcClient.existsById(imageId);

		// Assert
		assertTrue(result);

		// Verify that the stub was called with the correct request
		verify(mediaServiceBlockingStub).checkMediaExists(argThat(request ->
				request.getMediaId().equals(imageId)
		));
	}

	@Test
	void existsById_WhenMediaDoesNotExist_ShouldReturnFalse() {
		// Arrange
		String imageId = "456L";
		MediaExistsResponse response = MediaExistsResponse.newBuilder().setExists(false).build();

		when(mediaServiceBlockingStub.checkMediaExists(any(MediaCodeRequest.class))).thenReturn(response);

		// Act
		boolean result = mediaGrpcClient.existsById(imageId);

		// Assert
		assertFalse(result);

		// Verify that the stub was called with the correct request
		verify(mediaServiceBlockingStub).checkMediaExists(argThat(request ->
				request.getMediaId().equals(imageId)
		));
	}
}