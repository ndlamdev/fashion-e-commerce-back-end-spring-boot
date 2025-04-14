package com.lamnguyen.product_service.domain.request;

public record CreateOptionItemRequest(
		String title, // Xanh nhạt
		String slug, // xanh-nhat
		String label
) {

}