/**
 * Author Nguyen Dinh Lam
 * Email kiminonawa1305@gmail.com
 * Phone number +84 855354919
 * Create at 651 PM - 05/04/2025
 * User kimin
 **/

package com.lamnguyen.product_service.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.*;

@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
@Document(collection = "products")
public class Product {
	@Id
	String id;

	String title;

	@Field("apply_allowance_inventory")
	boolean applyAllowanceInventory;

	@Field("seo_alias")
	String seoAlias;

	String vendor;

	Map<String, String> tags;

	List<Option> options;

	@Field("options_ue")
	List<OptionsValue> optionsValue;

	List<Image> images;

	@Field("on_sale")
	boolean onSale;

	boolean available;

	List<String> collections;

	Integer price;

	@Field("compare_price")
	Integer comparePrice;

	boolean preorder;

	List<String> pricing;

	@Field("is_pricing")
	boolean isPricing;

	@Field("is_combo")
	boolean isCombo;

	@Field("display_order")
	Integer displayOrder;

	@Field("sale_number")
	Integer saleNumber;


	@Field("is_color_image_option")
	boolean isColorImageOption;

	@Field("youtube_video")
	String youtubeVideo;

	@Field("addon_note")
	String addonNote;

	Review review;

	@Field("coming_soon")
	boolean comingSoon;

	@Field("display_name")
	String displayName;

	@Field("display_name_open")
	String displayNameOpen;

	boolean wildcard;

	@Field("size_chart")
	String sizeChart;

	List<Variant> variants;

	Integer percent;

	@Field("regular_price")
	Integer regularPrice;

	@Field("note_collections")
	List<String> noteCollections;

	@Field("gender_type")
	String genderType;

	@Field("pricing_policy")
	List<String> pricingPolicy;

	@Field("collection_pricing_policy")
	List<CollectionPricingPolicy> collectionPricingPolicy;

	@Field("icon_thumbnail")
	Image iconThumbnail;

	Integer indexOption;

	List<String> uesInOption;

	@Field("is_show_variant_color")
	boolean isShowVariantColor;

	List<String> sizes;

	String color;

	@Field("color_slug")
	String colorSlug;

	@Field("is_flattened")
	boolean isFlattened;

	@Field("display_collections_variant")
	Integer displayCollectionsVariant;
}

