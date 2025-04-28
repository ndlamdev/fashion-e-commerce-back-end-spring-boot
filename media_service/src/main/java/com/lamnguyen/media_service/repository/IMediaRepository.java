/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:42 AM-23/04/2025
 * User: kimin
 **/

package com.lamnguyen.media_service.repository;

import com.lamnguyen.media_service.model.Media;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMediaRepository extends MongoRepository<Media, String> {
}
