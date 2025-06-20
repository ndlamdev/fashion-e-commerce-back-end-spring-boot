/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:21 PM-15/05/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.openFeign;

import com.lamnguyen.product_service.domain.request.GeminiRequest;
import com.lamnguyen.product_service.domain.response.GeminiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "geminiAiApi", url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent")
public interface IGeminiOpenFeign {
	@PostMapping()
	GeminiResponse analysisQuery(@RequestBody GeminiRequest query, @RequestParam("key") String key);
}
