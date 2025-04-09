/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:05 PM - 05/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.repository;

import com.lamnguyen.product_service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IProductRepository extends MongoRepository<Product, String> {
}
