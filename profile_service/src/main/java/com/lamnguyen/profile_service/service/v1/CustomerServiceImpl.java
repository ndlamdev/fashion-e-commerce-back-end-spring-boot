package com.lamnguyen.profile_service.service.v1;

import com.lamnguyen.profile_service.config.exception.ApplicationException;
import com.lamnguyen.profile_service.config.exception.ExceptionEnum;
import com.lamnguyen.profile_service.domain.request.SaveCustomerRequest;
import com.lamnguyen.profile_service.domain.response.SaveCustomerResponse;
import com.lamnguyen.profile_service.mapper.ICustomerMapper;
import com.lamnguyen.profile_service.repository.ICustomerRepository;
import com.lamnguyen.profile_service.service.ICustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerServiceImpl implements ICustomerService {
    ICustomerRepository customerRepository;
    ICustomerMapper mapper;

    @Override
    public SaveCustomerResponse saveCustomer(SaveCustomerRequest saveCustomerRequest) {
        var customer = customerRepository.findById(saveCustomerRequest.id()).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.USER_NOT_FOUND));
        return mapper.toSaveCustomerResponse(customerRepository.save(customer));
    }

    @Override
    public Page<SaveCustomerResponse> getCustomers(Integer page, Integer size) {
        var pageable = PageRequest.of(page, size);
        return customerRepository.findAll(pageable).map(mapper::toSaveCustomerResponse) ;
    }

    @Override
    public SaveCustomerResponse getCustomerById(Long id) {
        var customer = customerRepository.findById(id).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.USER_NOT_FOUND));
        return mapper.toSaveCustomerResponse(customer);
    }
}
