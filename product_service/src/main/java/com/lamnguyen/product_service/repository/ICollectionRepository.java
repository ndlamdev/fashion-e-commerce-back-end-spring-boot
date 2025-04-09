/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:52 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.repository;

import com.lamnguyen.product_service.model.Collection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ICollectionRepository extends MongoRepository<Collection, String> {
}
