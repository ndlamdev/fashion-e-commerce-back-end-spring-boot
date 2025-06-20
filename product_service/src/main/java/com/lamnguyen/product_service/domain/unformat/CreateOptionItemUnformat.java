package com.lamnguyen.product_service.domain.unformat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateOptionItemUnformat(
		String title, // Xanh nhạt
		String slug, // xanh-nhat
		String label,
		@JsonIgnore
		List<String> images
) implements Serializable {

}