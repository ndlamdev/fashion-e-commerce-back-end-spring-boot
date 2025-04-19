package com.lamnguyen.profile_service.service.business.v1;

import com.lamnguyen.profile_service.config.exception.ApplicationException;
import com.lamnguyen.profile_service.config.exception.ExceptionEnum;
import com.lamnguyen.profile_service.domain.ApiPaging;
import com.lamnguyen.profile_service.domain.ApiResponseSuccess;
import com.lamnguyen.profile_service.domain.dto.CustomerDto;
import com.lamnguyen.profile_service.domain.request.SaveCustomerRequest;
import com.lamnguyen.profile_service.domain.response.SaveCustomerResponse;
import com.lamnguyen.profile_service.mapper.ICustomerMapper;
import com.lamnguyen.profile_service.repository.ICustomerRepository;
import com.lamnguyen.profile_service.service.business.ICustomerService;
import com.lamnguyen.profile_service.service.redis.ICustomerRedisManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerServiceImpl implements ICustomerService {
    ICustomerRepository customerRepository;
    ICustomerMapper mapper;
    ICustomerRedisManager customerRedisManager;

    @Override
    public SaveCustomerResponse saveCustomer(SaveCustomerRequest saveCustomerRequest, Long userId) {
        var customer = customerRepository.findById(userId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.USER_NOT_FOUND));
        customer =  mapper.toCustomer(saveCustomerRequest);
        customer.setId(userId);
        return mapper.toSaveCustomerResponse(customerRepository.save(customer));
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
    @Transactional
    public CustomerDto getCustomerById(Long id) {
        return customerRedisManager
                .getById(id) // get in cache
                .or(() -> cacheById(id)) // caching data
                .orElseThrow(() -> ApplicationException.createException(ExceptionEnum.USER_NOT_FOUND)); // throw exception
    }

    @Override
    @Transactional
    public CustomerDto getCustomerByUserId(Long id) {
        return customerRedisManager
                .getByUserId(id) // get in cache
                .or(() -> cacheByUserId(id)) // caching data
                .orElseThrow(() -> ApplicationException.createException(ExceptionEnum.USER_NOT_FOUND)); // throw exception
    }

    private Optional<CustomerDto> cacheById(long id) {
        var data = customerRepository.findById(id).map(mapper::toCustomerDto);
        return customerRedisManager.cacheById(id, () -> data, 60, TimeUnit.MINUTES);
    }

    private Optional<CustomerDto> cacheByUserId(long id) {
        var data = customerRepository.findByUserId(id).map(mapper::toCustomerDto);
        return customerRedisManager.cacheByUserId(id, () -> data, 60, TimeUnit.MINUTES);
    }
}
