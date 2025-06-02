/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:15 PM-01/06/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.repository.v1;

import com.lamnguyen.product_service.model.Product;
import com.lamnguyen.product_service.repository.IProductMongoTemplate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductMongoTemplateImpl implements IProductMongoTemplate {
	MongoTemplate mongoTemplate;

	@Override
	public Product lock(String productId, boolean isLock) {
		return mongoTemplate.findAndModify(
				Query.query(Criteria.where("_id").is(productId)),
				Update.update("lock", isLock),
				Product.class);
	}
}
