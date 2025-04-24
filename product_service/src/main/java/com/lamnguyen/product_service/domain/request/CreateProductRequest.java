/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:41 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lamnguyen.product_service.domain.dto.OptionDto;
import com.lamnguyen.product_service.model.Discount;
import com.lamnguyen.product_service.utils.enums.GenderType;
import com.lamnguyen.product_service.utils.enums.ProductTag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
	@NotBlank
	String title;

	@NotBlank
	String vendor; // Người bán

	@NotNull
	@NotEmpty
	List<ProductTag> tags; // Bán chạy hay mới....

	@NotNull
	@NotEmpty
	List<OptionDto> options; // Các option để tạo ra biến thể

	@JsonProperty("options_values")
	List<CreateImageOptionsValueRequest> optionsValues;// Các giá trị bổ xung thêm cho option. Như detail của option.

	List<String> images; // Hình ảnh để show card

	@JsonProperty("collection_id")
	String collection; // Thuộc danh sách nào

	@JsonProperty("display_order")
	Integer displayOrder; // Thứ tự hiển thị

	@JsonProperty("youtube_video")
	String youtubeVideo; // Link video trên youtube cho chi tiết sản phẩm

	@JsonProperty("coming_soon")
	boolean comingSoon; // Đánh dấu sản phẩm có phải mẫu sẽ xuất hiện sớm không

	@JsonProperty("display_name_open")
	String displayNameOpen; // Subtitle; hiển thị ở dưới title trong chi tiết sản phẩm

	@NotNull
	Discount discount; // Khuyến mãi

	@JsonProperty("gender_type")
	GenderType genderType;// Dòng sản phẩm của nam hay nữ

	@JsonProperty("icon_thumbnail")
	String iconThumbnail; // Hình minh họa khuyến mãi

	@JsonProperty("compare_price")
	double comparePrice; // Giá so sánh

	@JsonProperty("regular_price")
	double regularPrice; // Giá thông thường
}
