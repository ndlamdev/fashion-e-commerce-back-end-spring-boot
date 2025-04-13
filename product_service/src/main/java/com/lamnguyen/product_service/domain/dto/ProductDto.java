/**
 * Author Nguyen Dinh Lam
 * Email kiminonawa1305@gmail.com
 * Phone number +84 855354919
 * Create at 651 PM - 05/04/2025
 * User kimin
 **/

package com.lamnguyen.product_service.domain.dto;

import com.lamnguyen.product_service.model.Discount;
import com.lamnguyen.product_service.model.Image;
import com.lamnguyen.product_service.model.MongoBaseEntity;
import com.lamnguyen.product_service.model.Variant;
import com.lamnguyen.product_service.utils.enums.GenderType;
import com.lamnguyen.product_service.utils.enums.ProductTag;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ProductDto extends MongoBaseEntity {
	String title;

	@Field("seo_alias")
	String seoAlias; // path in url: product/seoAlias

	String vendor; // người bán

	List<ProductTag> tags; // bán chạy hay mới....

	@Field("options_values")
	List<OptionsValueDto> optionsValues; // Các option để tạo ra biến thể

	List<Image> images; // Hình ảnh để show card

	@Builder.Default
	boolean available = true; // Có khả dụng hay không

	@Field("collection_id")
	@DocumentReference(lazy = true)
	CollectionDto collection; // Thuộc danh sách nào

	@Field("display_order")
	Integer displayOrder; // Thứ tự hiển thị

	@Field("is_color_image_option")
	boolean isColorImageOption;

	@Field("youtube_video")
	String youtubeVideo; // Link video trên youtube cho chi tiết sản phẩm

	@Field("coming_soon")
	boolean comingSoon; // Đánh dấu sản phẩm có phải mẫu sẽ xuất hiện sớm không

	@Field("display_name_open")
	String displayNameOpen; // Subtitle, hiển thị ở dưới title trong chi tiết sản phẩm

	@DocumentReference
	List<Variant> variants; // Các biến thể. Phải được tạo ra từ các OptionValue của bản phẩm

	Discount discount; // Khuyến mãi

	@Field("gender_type")
	GenderType genderType; // Dòng sản phẩm của nam hay nữ

	@Field("icon_thumbnail")
	@DocumentReference(lazy = true)
	Image iconThumbnail; // Hình minh họa khuyến mãi

	@Builder.Default
	ReviewDto review = ReviewDto.builder().build();
}
