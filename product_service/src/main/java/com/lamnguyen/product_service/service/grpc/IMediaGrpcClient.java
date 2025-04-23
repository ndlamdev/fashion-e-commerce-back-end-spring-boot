/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:08 PM - 23/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.grpc;

import java.util.List;
import java.util.Map;

public interface IMediaGrpcClient {
	boolean existsById(String id);

	Map<String, Boolean> existsByIds(List<String> ids);
}
