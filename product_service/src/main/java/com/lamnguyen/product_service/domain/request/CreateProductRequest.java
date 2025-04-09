/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:41 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lamnguyen.product_service.model.Image;
import com.lamnguyen.product_service.utils.enums.GenderType;
import com.lamnguyen.product_service.utils.enums.ProductTag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateProductRequest(
		@NotBlank
		String title,

		@JsonProperty("seo_alias")
		String seoAlias, // path in url: product/seoAlias

		String vendor,

		List<ProductTag> tags,

		List<Image> images,

		Boolean available,

		@NotNull
		@NotEmpty
		@JsonProperty("collection_ids")
		String collections,

		@JsonProperty("is_combo")
		Boolean isCombo,

		@JsonProperty("display_order")
		Integer displayOrder,

		@JsonProperty("sale_number")
		Integer saleNumber,

		@JsonProperty("is_color_image_option")
		Boolean isColorImageOption,

		@JsonProperty("youtube_video")
		String youtubeVideo,

		@JsonProperty("coming_soon")
		Boolean comingSoon,

		@JsonProperty("display_name_open")
		String displayNameOpen, // Subtitle

		@JsonProperty("gender_type")
		GenderType genderType,

		@JsonProperty("icon_thumbnail")
		Image iconThumbnail,

		@JsonProperty("is_show_variant_color")
		Boolean isShowVariantColor,

		@JsonProperty("apply_allowance_inventory")
		Boolean applyAllowanceInventory
) {
}
