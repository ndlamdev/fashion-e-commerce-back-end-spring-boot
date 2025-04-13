package com.lamnguyen.product_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ConcurrentRequestTest {

	private static final int NUMBER_OF_THREADS = 50;

	@Test
	public void testConcurrentRequests() throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

		CountDownLatch latch = new CountDownLatch(NUMBER_OF_THREADS);

		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			executorService.execute(() -> {
				try {
					HttpClient client = HttpClient.newHttpClient();
					HttpRequest request = HttpRequest.newBuilder()
							.uri(URI.create("http://localhost:8080/api/users/all"))
							.GET()
							.build();

					client.send(request, HttpResponse.BodyHandlers.ofString());
				} catch (Exception e) {
					e.printStackTrace(System.out);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await(); // đợi tất cả request hoàn thành
		executorService.shutdown();
	}
}
