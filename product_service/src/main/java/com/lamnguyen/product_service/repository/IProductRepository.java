/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:05 PM - 05/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.repository;

import com.lamnguyen.product_service.model.Collection;
import com.lamnguyen.product_service.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IProductRepository extends MongoRepository<Product, String> {
	List<Product> findByCollection(Collection collection);

	Page<Product> findByAllImageContainsContains(List<String> ids, Pageable pageable);

	Page<Product> findAllByTitleSearchRegex(String title, Pageable pageable);
}
