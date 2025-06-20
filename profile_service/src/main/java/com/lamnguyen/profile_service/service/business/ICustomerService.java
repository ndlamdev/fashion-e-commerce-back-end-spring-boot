package com.lamnguyen.profile_service.service.business;

import com.lamnguyen.profile_service.domain.ApiPaging;
import com.lamnguyen.profile_service.domain.dto.CustomerDto;
import com.lamnguyen.profile_service.domain.request.SaveCustomerRequest;

public interface ICustomerService {
    CustomerDto saveCustomer(SaveCustomerRequest saveCustomerRequest);

    ApiPaging<CustomerDto> getCustomers(Integer page, Integer size);

    CustomerDto getCustomerById(Long id);

    CustomerDto getCustomerByUserId(Long id);

    CustomerDto getCustomer();
}
