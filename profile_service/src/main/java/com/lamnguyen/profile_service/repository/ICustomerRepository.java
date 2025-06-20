package com.lamnguyen.profile_service.repository;

import com.lamnguyen.profile_service.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByUserId(long id);
}
