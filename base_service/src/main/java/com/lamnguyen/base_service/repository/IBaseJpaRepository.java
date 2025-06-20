/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:37 PM-02/05/2025
 * User: kimin
 **/

package com.lamnguyen.base_service.repository;

import com.lamnguyen.base_service.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBaseJpaRepository extends JpaRepository<BaseEntity, Long> {
}
