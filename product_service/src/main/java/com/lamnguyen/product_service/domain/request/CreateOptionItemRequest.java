package com.lamnguyen.product_service.domain.request;

import java.util.List;

public record CreateOptionItemRequest(
		String title, // Xanh nhạt
		String slug, // xanh-nhat
		String label,
		List<String> images
) {

}