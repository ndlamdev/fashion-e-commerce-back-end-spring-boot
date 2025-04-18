package com.lamnguyen.profile_service.service.business;

import com.lamnguyen.profile_service.domain.ApiPaging;
import com.lamnguyen.profile_service.domain.ApiResponseSuccess;
import com.lamnguyen.profile_service.domain.dto.CustomerDto;
import com.lamnguyen.profile_service.domain.request.SaveCustomerRequest;
import com.lamnguyen.profile_service.domain.response.SaveCustomerResponse;

public interface ICustomerService {
    SaveCustomerResponse saveCustomer(SaveCustomerRequest saveCustomerRequest, Long userId);

    ApiResponseSuccess<ApiPaging<CustomerDto>> getCustomers(Integer page, Integer size);

    CustomerDto getCustomerById(Long id);
}
