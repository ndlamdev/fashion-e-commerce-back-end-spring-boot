package com.lamnguyen.profile_service.repository;

import com.lamnguyen.profile_service.model.entity.Address;
import com.lamnguyen.profile_service.model.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByLockAndCustomer_Id(Boolean lock, Long customerId);

    Address findAddressByIdAndLockAndCustomer_Id(Long id, Boolean lock, Long customerId);

    Integer countAllByLock(Boolean lock);
}
