/**
 * Author Nguyen Dinh Lam
 * Email kiminonawa1305@gmail.com
 * Phone number +84 855354919
 * Create at 651 PM - 05/04/2025
 * User kimin
 **/

package com.lamnguyen.product_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lamnguyen.product_service.model.*;
import com.lamnguyen.product_service.utils.enums.ProductTag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
public class ProductDTO {
	String id;

	String title;

	@JsonProperty("apply_allowance_inventory")
	boolean applyAllowanceInventory;

	@JsonProperty("seo_alias")
	String seoAlias;

	String vendor;

	List<ProductTag> tags;

	List<Option> options;

	@JsonProperty("options_ue")
	List<OptionsValue> optionsValue;

	List<Image> images;

	@JsonProperty("on_sale")
	boolean onSale;

	boolean available;

	List<String> collections;

	Integer price;

	@JsonProperty("compare_price")
	Integer comparePrice;

	boolean preorder;

	List<String> pricing;

	@JsonProperty("is_pricing")
	boolean isPricing;

	@JsonProperty("is_combo")
	boolean isCombo;

	@JsonProperty("display_order")
	Integer displayOrder;

	@JsonProperty("sale_number")
	Integer saleNumber;


	@JsonProperty("is_color_image_option")
	boolean isColorImageOption;

	@JsonProperty("youtube_video")
	String youtubeVideo;

	@JsonProperty("addon_note")
	String addonNote;

	@JsonProperty("coming_soon")
	boolean comingSoon;

	@JsonProperty("display_name")
	String displayName;

	@JsonProperty("display_name_open")
	String displayNameOpen;

	boolean wildcard;

	@JsonProperty("size_chart")
	String sizeChart;

	List<Variant> variants;

	Integer percent;

	@JsonProperty("regular_price")
	Integer regularPrice;

	@JsonProperty("note_collections")
	List<String> noteCollections;

	@JsonProperty("gender_type")
	String genderType;

	@JsonProperty("pricing_policy")
	List<String> pricingPolicy;

	@JsonProperty("collection_pricing_policy")
	List<CollectionPricingPolicy> collectionPricingPolicy;

	@JsonProperty("icon_thumbnail")
	Image iconThumbnail;

	Integer indexOption;

	List<String> uesInOption;

	@JsonProperty("is_show_variant_color")
	boolean isShowVariantColor;

	List<String> sizes;

	String color;

	@JsonProperty("color_slug")
	String colorSlug;

	@JsonProperty("is_flattened")
	boolean isFlattened;

	@JsonProperty("display_collections_variant")
	Integer displayCollectionsVariant;
}

