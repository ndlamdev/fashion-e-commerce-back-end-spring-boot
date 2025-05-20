/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:15 PM-15/05/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lamnguyen.product_service.domain.response.ProductFilterResponse;

public interface IGeminiService {
	ProductFilterResponse analysisQuery(String query) throws JsonProcessingException;
}
