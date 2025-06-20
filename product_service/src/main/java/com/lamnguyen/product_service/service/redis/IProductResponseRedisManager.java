/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:58 AM - 13/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.redis;

import com.lamnguyen.product_service.domain.dto.ProductDto;
import com.lamnguyen.product_service.domain.response.ProductResponse;

import java.util.Optional;

public interface IProductResponseRedisManager extends ICacheManage<ProductResponse> {
	Optional<ProductResponse> cache(String id, CallbackDB<ProductResponse> callDB);
}
