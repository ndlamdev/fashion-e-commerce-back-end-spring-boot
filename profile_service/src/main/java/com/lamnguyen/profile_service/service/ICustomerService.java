package com.lamnguyen.profile_service.service;

import com.lamnguyen.profile_service.domain.request.SaveCustomerRequest;
import com.lamnguyen.profile_service.domain.response.SaveCustomerResponse;
import org.springframework.data.domain.Page;

public interface ICustomerService {
    SaveCustomerResponse saveCustomer(SaveCustomerRequest saveCustomerRequest);
    Page<SaveCustomerResponse> getCustomers(Integer page, Integer size);
    SaveCustomerResponse getCustomerById(Long id);
}
