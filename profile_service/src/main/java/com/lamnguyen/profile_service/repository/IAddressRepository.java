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
    List<Address> findAllByCustomer_UserIdAndLockIsFalse(Long userId);

    Optional<Address> findAddressByIdAndCustomer_UserIdAndLockIsFalse(Long id, Long userId);

    Optional<Address> findFirstByCustomer_UserIdAndLockIsFalse(Long userId);
    @Modifying
    @Transactional
    @Query("""
                UPDATE Address a
                SET a.active = CASE WHEN a.id = :addressId THEN true ELSE false END
                WHERE a.customer.userId = :userId
            """)
    void activeAddress(@Param("userId") Long userId, @Param("addressId") Long addressId);

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
                  AND a.customer.userId = :userId
            """)
    void setDefaultAddress(@Param("oldId") Long oldId, @Param("newId") Long  newId, @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Address a SET a.active = false WHERE a.customer.id = :userId AND a.id = :id")
    void inactiveAddress(@Param("id") Long id, @Param("userId") Long userId);

    Optional<Address> findByCustomer_UserIdAndLockIsFalseAndActiveIsTrue(long userId);
}
