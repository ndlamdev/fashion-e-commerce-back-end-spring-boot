/**
 * Author Nguyen Dinh Lam
 * Email kiminonawa1305@gmail.com
 * Phone number +84 855354919
 * Create at 651 PM - 05/04/2025
 * User kimin
 **/

package com.lamnguyen.product_service.model;

import com.lamnguyen.product_service.utils.annotation.CompareAmountImageOptionsValue;
import com.lamnguyen.product_service.utils.enums.GenderType;
import com.lamnguyen.product_service.utils.enums.ProductTag;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Set;

@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
@Document(collection = "products")
@CompareAmountImageOptionsValue
public class Product extends MongoBaseDocument {
	String title;

	@Field("seo_alias")
	String seoAlias; // path in url: product/seoAlias

	String vendor; // người bán

	Set<ProductTag> tags; // bán chạy hay mới....

	Set<Option> options; // Các option để tạo ra biến thể

	@Field("options_values")
	List<ImageOptionsValue> optionsValues; // Nếu các option có các giá trị riêng của nó thì dùng trường này. Ví dụ như các option liên quan đến màu thì cần dùng cái này để lưu hình ảnh sản phẩm theo màu đó.

	List<String> images; // Hình ảnh để show card

	@Builder.Default
	boolean available = true; // Có khả dụng hay không

	@Field("collection_id")
	@DocumentReference(lazy = true)
	Collection collection; // Thuộc danh sách nào

	@Field("display_order")
	Integer displayOrder; // Thứ tự hiển thị

	@Field("youtube_video")
	String youtubeVideo; // Link video trên youtube cho chi tiết sản phẩm

	@Field("coming_soon")
	boolean comingSoon; // Đánh dấu sản phẩm có phải mẫu sẽ xuất hiện sớm không

	@Field("display_name_open")
	String displayNameOpen; // Subtitle, hiển thị ở dưới title trong chi tiết sản phẩm

	Discount discount; // Khuyến mãi

	@Field("gender_type")
	GenderType genderType; // Dòng sản phẩm của nam hay nữ

	@Field("icon_thumbnail")
	String iconThumbnail; // Hình minh họa khuyến mãi

	@Field("all_image_contains")
	List<String> allImageContains;

	@Field("title_search")
	String titleSearch;
}

