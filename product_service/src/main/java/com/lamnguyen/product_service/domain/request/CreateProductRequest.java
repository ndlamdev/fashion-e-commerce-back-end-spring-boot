/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:41 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.domain.request;

import com.lamnguyen.product_service.domain.dto.OptionsValueDto;
import com.lamnguyen.product_service.model.Discount;
import com.lamnguyen.product_service.model.Image;
import com.lamnguyen.product_service.model.Variant;
import com.lamnguyen.product_service.utils.enums.GenderType;
import com.lamnguyen.product_service.utils.enums.ProductTag;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

public record CreateProductRequest(
		String title,

		String vendor, // người bán

		List<ProductTag> tags, // bán chạy hay mới....

		@Field("options_values")
		List<OptionsValueDto> optionsValues, // Các option để tạo ra biến thể

		List<Image> images, // Hình ảnh để show card

		boolean available, // Có khả dụng hay không

		@Field("collection_id")
		@DocumentReference(lazy = true)
		String collection, // Thuộc danh sách nào

		@Field("display_order")
		Integer displayOrder, // Thứ tự hiển thị

		@Field("youtube_video")
		String youtubeVideo, // Link video trên youtube cho chi tiết sản phẩm

		@Field("coming_soon")
		boolean comingSoon, // Đánh dấu sản phẩm có phải mẫu sẽ xuất hiện sớm không

		@Field("display_name_open")
		String displayNameOpen, // Subtitle, hiển thị ở dưới title trong chi tiết sản phẩm

		@DocumentReference
		List<Variant> variants, // Các biến thể. Phải được tạo ra từ các OptionValue của bản phẩm

		@NotNull
		Discount discount, // Khuyến mãi

		@Field("gender_type")
		GenderType genderType, // Dòng sản phẩm của nam hay nữ

		@Field("icon_thumbnail")
		@DocumentReference(lazy = true)
		Image iconThumbnail // Hình minh họa khuyến mãi
) {
}
