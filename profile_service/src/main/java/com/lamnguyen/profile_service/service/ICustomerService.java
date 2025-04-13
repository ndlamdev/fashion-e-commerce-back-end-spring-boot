package com.lamnguyen.profile_service.service;

import com.lamnguyen.profile_service.domain.ApiPaging;
import com.lamnguyen.profile_service.domain.ApiResponseSuccess;
import com.lamnguyen.profile_service.domain.dto.CustomerDto;
import com.lamnguyen.profile_service.domain.request.SaveCustomerRequest;
import com.lamnguyen.profile_service.domain.response.SaveCustomerResponse;
import org.springframework.data.domain.Page;

public interface ICustomerService {
    SaveCustomerResponse saveCustomer(SaveCustomerRequest saveCustomerRequest, Long userId);

    ApiResponseSuccess<ApiPaging<CustomerDto>> getCustomers(Integer page, Integer size);

    ApiResponseSuccess<SaveCustomerResponse> getCustomerById(Long id);
}
