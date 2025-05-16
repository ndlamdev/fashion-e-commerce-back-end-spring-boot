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
import jakarta.servlet.annotation.MultipartConfig;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
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
	@ApiMessageResponse("Search with file image")
	public Page<ProductResponse> getProductsUsingFileImage(@RequestParam("file") MultipartFile file) throws IOException {
		if (file.getContentType() == null || !file.getContentType().startsWith("image"))
			throw ApplicationException.createException(ExceptionEnum.ERROR_FILE_TYPE);
		if (file.isEmpty()) throw ApplicationException.createException(ExceptionEnum.EMPTY_FILE);
		return productService.searchByImage(saveFile(file));
	}

	@GetMapping("/search")
	@ApiMessageResponse("Search with title")
	public Page<ProductResponse> getProductsUsingFileImage(@RequestParam("query") String query) throws IOException {
		System.out.println(geminiService.analysisQuery(query));
		return null;
	}

	private File saveFile(MultipartFile file) throws IOException {
		var dir = new File("/tmp/product_service");
		if (!dir.exists()) dir.mkdir();
		var ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		var tempFile = new File(dir.getAbsolutePath() + File.separator + UUID.randomUUID().toString() + ext.toLowerCase());
		var in = new BufferedInputStream(file.getInputStream());
		var out = new BufferedOutputStream(new FileOutputStream(tempFile));
		var buffer = new byte[1024 * 100];
		var readed = 0;
		while ((readed = in.read(buffer)) != -1) {
			out.write(buffer, 0, readed);
		}
		out.close();
		in.close();
		return tempFile;
	}
}
