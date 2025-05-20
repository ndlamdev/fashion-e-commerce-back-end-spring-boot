/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:20 PM-15/05/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.business.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamnguyen.product_service.domain.request.GeminiRequest;
import com.lamnguyen.product_service.domain.response.ProductFilterResponse;
import com.lamnguyen.product_service.service.business.IGeminiService;
import com.lamnguyen.product_service.service.openFeign.IGeminiOpenFeign;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GeminiServiceImpl implements IGeminiService {
	IGeminiOpenFeign geminiOpenFeign;
	ObjectMapper objectMapper;

	@Override
	public ProductFilterResponse analysisQuery(String query) throws JsonProcessingException {
		var result = geminiOpenFeign.analysisQuery(
				generateGeminiRequest(query), "AIzaSyBmntm48FQKuCGToI8KPEqpnkJ8JuKFn6s");
		String jsonText = result.getCandidates()
				.getFirst()
				.getContent()
				.getParts()
				.getFirst()
				.getText();

		String extractedJson = jsonText.replaceAll("(?s)```json\\s*(\\{.*})\\s*```", "$1");

		return objectMapper.readValue(extractedJson, ProductFilterResponse.class);
	}

	private GeminiRequest generateGeminiRequest(String query) {
		String prefixQuery = """
				Phần tích cấu query sau thành các thần phần (tên sản phẩm, màu sắc, kích thước, giá thấp nhất, giá cao nhất) và trả về với định dạng json ({product: string, color: string, size: string, minPrice: number, maxPrice: number, price: number}), đối với các trường không có thì để bằng null, định dạng tiền luôn trả về dạng nghìn và chỉ trả về dạng json phía trên một cách tuyệt đối. Thực hiện với câu query sau: %s
				""".formatted(query);

		return GeminiRequest.builder()
				.contents(new GeminiRequest.Part[]{
						GeminiRequest.Part.builder()
								.parts(
										new GeminiRequest.Part.Text[]{
												GeminiRequest.Part.Text.builder().text(prefixQuery).build()
										}
								)
								.build(),
				}).build();
	}
}
