package com.lamnguyen.profile_service.repository;

import com.lamnguyen.profile_service.model.entity.Address;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByCustomer_IdAndLockIsFalse( Long customerId);

    Optional<Address> findAddressByIdAndCustomer_IdAndLockIsFalse(Long id, Long customerId);

    Optional<Address> findFirstByCustomer_IdAndLockIsFalse(Long customerId);
    @Modifying
    @Transactional
    @Query("""
                UPDATE Address a
                SET a.active = CASE WHEN a.id = :addressId THEN true ELSE false END
                WHERE a.customer.id = :customerId
            """)
    void activeAddress(@Param("customerId") Long customerId, @Param("addressId") Long addressId);

    @Modifying
    @Transactional
    @Query("""
            
            UPDATE Address a
            SET a.active = CASE
                WHEN a.id = :newId THEN true
                WHEN a.id = :oldId THEN false
                ELSE a.active
                END
            WHERE (a.id = :newId OR a.id = :oldId)
                  AND a.customer.id = :customerId
            """)
    void setDefaultAddress(@Param("oldId") Long oldId, @Param("newId") Long  newId, @Param("customerId") Long customerId);

    @Modifying
    @Transactional
    @Query("UPDATE Address a SET a.active = false WHERE a.customer.id = :customerId AND a.id = :id")
    void inactiveAddress(@Param("id") Long id, @Param("customerId") Long customerId);

    Optional<Address> findByCustomer_IdAndLockIsFalseAndActiveIsTrue(long userId);
}
