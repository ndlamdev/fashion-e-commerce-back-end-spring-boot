/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:59 AM-24/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.kafka.producer;

import com.lamnguyen.product_service.event.DataVariantEvent;

import java.util.List;

public interface IVariantService {
	void saveVariant(String productId, double comparePrice, double regularPrice, List<DataVariantEvent.Option> options);

	void updateVariant(String productId, double comparePrice, double regularPrice, List<DataVariantEvent.Option> options);
}
