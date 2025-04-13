package com.lamnguyen.profile_service.service.v1;

import com.lamnguyen.profile_service.config.exception.ApplicationException;
import com.lamnguyen.profile_service.config.exception.ExceptionEnum;
import com.lamnguyen.profile_service.domain.ApiPaging;
import com.lamnguyen.profile_service.domain.ApiResponseSuccess;
import com.lamnguyen.profile_service.domain.dto.CustomerDto;
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
import org.springframework.http.HttpStatus;
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
    public SaveCustomerResponse saveCustomer(SaveCustomerRequest saveCustomerRequest, Long userId) {
        if(!customerRepository.existsById(userId)) throw ApplicationException.createException(ExceptionEnum.USER_NOT_FOUND);
        return mapper.toSaveCustomerResponse(customerRepository.save(mapper.toCustomer(saveCustomerRequest)));
    }

    @Override
    public ApiResponseSuccess<ApiPaging<CustomerDto>> getCustomers(Integer page, Integer size) {
        var pageable = PageRequest.of(page, size);
        var customers = customerRepository.findAll(pageable);
        var limited = ApiPaging.<CustomerDto>builder()
                .content(mapper.toCustomerDTOs(customers.getContent()))
                .current(customers.getNumber())
                .size(customers.getSize())
                .totalPage(customers.getTotalPages())
                .build();

        return ApiResponseSuccess.<ApiPaging<CustomerDto>>builder()
                .data(limited)
                .message(HttpStatus.OK.name())
                .code(HttpStatus.OK.value())
                .build();
    }

    @Override
    public ApiResponseSuccess<SaveCustomerResponse> getCustomerById(Long id) {
        var customer = customerRepository.findById(id).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.USER_NOT_FOUND));
        return ApiResponseSuccess.<SaveCustomerResponse>builder()
                .data(mapper.toSaveCustomerResponse(customer))
                .message(HttpStatus.OK.name())
                .code(HttpStatus.OK.value())
                .build();
    }
}
