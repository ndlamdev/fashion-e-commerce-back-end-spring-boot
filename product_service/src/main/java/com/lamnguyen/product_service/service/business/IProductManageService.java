/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:29 PM - 07/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.business;

import com.lamnguyen.product_service.domain.request.DataProductRequest;

public interface IProductManageService {
	void create(DataProductRequest request);

	void update(String id, DataProductRequest request);

	void lock(String id, boolean isLock);
}
