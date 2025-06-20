/**
 * Author Nguyen Dinh Lam
 * Email kiminonawa1305@gmail.com
 * Phone number +84 855354919
 * Create at 651 PM - 05/04/2025
 * User kimin
 **/

package com.lamnguyen.product_service.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lamnguyen.product_service.domain.dto.*;
import com.lamnguyen.product_service.model.Discount;
import com.lamnguyen.product_service.model.MongoBaseDocument;
import com.lamnguyen.product_service.utils.enums.GenderType;
import com.lamnguyen.product_service.utils.enums.ProductTag;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
@SuperBuilder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductResponse extends MongoBaseDocument implements Serializable {
	String title;

	String seoAlias; // path in url: product/seoAlias

	String vendor; // người bán

	List<ProductTag> tags; // bán chạy hay mới....

	List<OptionDto> options; // Các option để tạo ra biến thể

	List<ImageOptionsValueResponse> optionsValues; // Các option có giá trị riêng thì dùng trường này.

	List<ImageResponse> images; // Hình ảnh để show card

	@Builder.Default
	boolean available = true; // Có khả dụng hay không

	@JsonProperty("collection_id")
	String collection; // Thuộc danh sách nào

	Integer displayOrder; // Thứ tự hiển thị

	String youtubeVideo; // Link video trên youtube cho chi tiết sản phẩm

	boolean comingSoon; // Đánh dấu sản phẩm có phải mẫu sẽ xuất hiện sớm không

	String displayNameOpen; // Subtitle, hiển thị ở dưới title trong chi tiết sản phẩm

	List<VariantResponse> variants; // Các biến thể. Phải được tạo ra từ các OptionValue của bản phẩm

	Discount discount; // Khuyến mãi

	GenderType genderType; // Dòng sản phẩm của nam hay nữ

	ImageResponse iconThumbnail; // Hình minh họa khuyến mãi

	@Builder.Default
	ReviewResponse review = ReviewResponse.builder().build();
}
