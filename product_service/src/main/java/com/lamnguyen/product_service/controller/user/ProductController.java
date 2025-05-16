/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:28 PM - 07/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.controller.user;

import com.lamnguyen.product_service.config.exception.ApplicationException;
import com.lamnguyen.product_service.config.exception.ExceptionEnum;
import com.lamnguyen.product_service.domain.response.ProductResponse;
import com.lamnguyen.product_service.service.business.IGeminiService;
import com.lamnguyen.product_service.service.business.IProductService;
import com.lamnguyen.product_service.utils.annotation.ApiMessageResponse;
import com.lamnguyen.product_service.utils.helper.FileUtil;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@RestController
@RequestMapping("/product/v1")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class ProductController {
	IProductService productService;
	IGeminiService geminiService;

	@GetMapping("/{id}")
	@ApiMessageResponse("Get product success!")
	public ProductResponse getProductDetail(@PathVariable("id") String id) {
		return productService.getProductById(id);
	}

	@PostMapping("/search")
	@ApiMessageResponse("Search with file image success!")
	public Page<ProductResponse> searchProductsUsingFileImage(@RequestParam("file") MultipartFile file) throws IOException {
		if (file.getContentType() == null || !file.getContentType().startsWith("image"))
			throw ApplicationException.createException(ExceptionEnum.ERROR_FILE_TYPE);
		if (file.isEmpty()) throw ApplicationException.createException(ExceptionEnum.EMPTY_FILE);
		return productService.searchByImage(FileUtil.saveFile(file));
	}

	@GetMapping("/voice-search")
	@ApiMessageResponse("Voice search success!")
	public Page<ProductResponse> searchProductsUsingVoice(@RequestParam("query") @Valid @NotBlank String query) throws IOException {
		System.out.println(geminiService.analysisQuery(query));
		return null;
	}

	@GetMapping("/search")
	@ApiMessageResponse("Search title success!")
	public Page<ProductResponse> searchProducts(@RequestParam("query") @Valid @NotBlank String query, @PageableDefault Pageable pageable) throws IOException {
		return productService.search(query, pageable);
	}
}
