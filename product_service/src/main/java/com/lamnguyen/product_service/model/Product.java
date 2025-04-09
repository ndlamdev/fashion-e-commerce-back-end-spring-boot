/**
 * Author Nguyen Dinh Lam
 * Email kiminonawa1305@gmail.com
 * Phone number +84 855354919
 * Create at 651 PM - 05/04/2025
 * User kimin
 **/

package com.lamnguyen.product_service.model;

import com.lamnguyen.product_service.utils.enums.GenderType;
import com.lamnguyen.product_service.utils.enums.ProductTag;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
@Document(collection = "products")
public class Product extends MongoBaseEntity {
	String title;

	@Field("seo_alias")
	String seoAlias; // path in url: product/seoAlias

	String vendor;

	List<ProductTag> tags;

	@Field("options_values")
	List<OptionsValue> optionsValues;

	List<Image> images;

	@Builder.Default
	@Field("on_sale")
	boolean onSale = false;

	@Builder.Default
	boolean available = true;

	@Field("collection_ids")
	@DocumentReference
	Collection collections;

	@Builder.Default
	boolean preorder = false;

	@Builder.Default
	@Field("is_combo")
	boolean isCombo = false;

	@Field("display_order")
	Integer displayOrder;

	@Builder.Default
	@Field("sale_number")
	Integer saleNumber = 0;

	@Field("is_color_image_option")
	boolean isColorImageOption;

	@Field("youtube_video")
	String youtubeVideo;

	@Builder.Default
	Review review = Review.builder().build();

	@Field("coming_soon")
	boolean comingSoon;

	@Field("display_name_open")
	String displayNameOpen; // Subtitle

	List<String> variants;

	Discount discount;

	@Field("gender_type")
	GenderType genderType;

	@Field("icon_thumbnail")
	Image iconThumbnail;

	@Builder.Default
	@Field("is_show_variant_color")
	boolean isShowVariantColor = true;

	@Field("apply_allowance_inventory")
	boolean applyAllowanceInventory;
}

