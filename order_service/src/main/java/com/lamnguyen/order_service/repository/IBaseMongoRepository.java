/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:38 PM-02/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBaseMongoRepository extends MongoRepository<BaseDocument, String> {
}
