/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:41 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;import com.fasterxml.jackson.databind.annotation.JsonNaming;import com.lamnguyen.product_service.domain.dto.OptionDto;
import com.lamnguyen.product_service.model.Discount;
import com.lamnguyen.product_service.utils.enums.GenderType;
import com.lamnguyen.product_service.utils.enums.ProductTag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DataProductRequest {
	@NotBlank
	String title;

	String seoAlias;

	@NotNull
	String vendor; // Người bán

	@NotNull
	List<ProductTag> tags; // Bán chạy hay mới....

	@NotNull
	@NotEmpty
	List<OptionDto> options; // Các option để tạo ra biến thể

	List<CreateImageOptionsValueRequest> optionsValues;// Các giá trị bổ xung thêm cho option. Như detail của option.

	List<String> images; // Hình ảnh để show card

	@JsonProperty("collection_id")
	String collection; // Thuộc danh sách nào

	Integer displayOrder; // Thứ tự hiển thị

	String youtubeVideo; // Link video trên youtube cho chi tiết sản phẩm

	boolean comingSoon; // Đánh dấu sản phẩm có phải mẫu sẽ xuất hiện sớm không

	String displayNameOpen; // Subtitle; hiển thị ở dưới title trong chi tiết sản phẩm

	Discount discount; // Khuyến mãi

	GenderType genderType;// Dòng sản phẩm của nam hay nữ

	String iconThumbnail; // Hình minh họa khuyến mãi

	double comparePrice; // Giá so sánh

	double regularPrice; // Giá thông thường
}
