/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:46 PM-12/05/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.grpc.v1;

import com.google.protobuf.ByteString;
import com.lamnguyen.product_service.protos.FileSearchServiceGrpc;
import com.lamnguyen.product_service.protos.SearchRequest;
import com.lamnguyen.product_service.service.grpc.IFileSearchGrpcClient;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class FileSearchGrpcClientImpl implements IFileSearchGrpcClient {
	@GrpcClient("fashion-e-commerce-file-search-service")
	FileSearchServiceGrpc.FileSearchServiceBlockingStub fileSearchServiceBlockingStub;


	@Override
	public List<String> searchFile(File file) {
		System.out.println(file.getAbsolutePath());
		try (InputStream inputStream = new FileInputStream(file)) {
			SearchRequest.Builder requestBuilder = SearchRequest.newBuilder()
					.setFileName(file.getName());

			byte[] buffer = new byte[32 * 1024];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				requestBuilder.addData(ByteString.copyFrom(buffer, 0, bytesRead));
			}

			SearchRequest request = requestBuilder.build();

			return fileSearchServiceBlockingStub.search(request).getIdsList();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
