package com.lamnguyen.profile_service.repository;

import com.lamnguyen.profile_service.model.entity.Address;
import com.lamnguyen.profile_service.model.entity.Customer;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByLockAndCustomer_Id(Boolean lock, Long customerId);

    Address findAddressByIdAndLockAndCustomer_Id(Long id, Boolean lock, Long customerId);

    Address findDistinctFirstByLockAndCustomer_Id(Boolean lock, Long customerId);
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

}
