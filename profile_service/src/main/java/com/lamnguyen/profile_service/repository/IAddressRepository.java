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
    List<Address> findAllByUserIdAndLockIsFalse(Long userId);

    Optional<Address> findAddressByUserIdAndIdAndLockIsFalse(Long userId, Long addressId);

    Optional<Address> findFirstByUserIdAndLockIsFalse(Long userId);
    @Modifying
    @Transactional
    @Query("""
                UPDATE Address a
                SET a.active = CASE WHEN a.id = :addressId THEN true ELSE false END
                WHERE a.userId = :userId
            """)
    void activeAddress(@Param("userId") Long userId, @Param("addressId") Long addressId);

    @Modifying
    @Transactional
    @Query("UPDATE Address a SET a.active = false WHERE a.userId = :userId AND a.id = :id")
    void inactiveAddress(@Param("id") Long id, @Param("userId") Long userId);

    Optional<Address> findByUserIdAndLockIsFalseAndActiveIsTrue(long userId);
}
