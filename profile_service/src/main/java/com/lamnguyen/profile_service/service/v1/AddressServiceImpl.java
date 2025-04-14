package com.lamnguyen.profile_service.service.v1;

import com.lamnguyen.profile_service.config.exception.ApplicationException;
import com.lamnguyen.profile_service.config.exception.ExceptionEnum;
import com.lamnguyen.profile_service.domain.request.SaveAddressRequest;
import com.lamnguyen.profile_service.domain.response.AddressResponse;
import com.lamnguyen.profile_service.mapper.IAddressMapper;
import com.lamnguyen.profile_service.model.entity.Customer;
import com.lamnguyen.profile_service.repository.IAddressRepository;
import com.lamnguyen.profile_service.service.IAddressService;
import com.lamnguyen.profile_service.service.ICustomerService;
import com.lamnguyen.profile_service.utils.JwtTokenUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AddressServiceImpl implements IAddressService {
    IAddressMapper mapper;
    IAddressRepository repository;
    ICustomerService customerService;
    JwtTokenUtil jwtTokenUtil;

    @Override
    public AddressResponse saveAddress(SaveAddressRequest request, Long id, Long customerId) {
        var address = Optional.of(repository.findAddressByIdAndLockAndCustomer_Id(id, false, customerId)).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
        var addressDTO = mapper.toAddressDto(address);
        address = mapper.toAddress(addressDTO);
        return mapper.toAddressResponse(repository.save(address));
    }

    @Override
    public AddressResponse addAddress(SaveAddressRequest request, Long customerId) {
        log.info("Add address request: {}, {}", request, customerId);
        var customerDto = customerService.getCustomerById(customerId);
        if (customerDto.shippingAddresses().size() > 5)
            throw ApplicationException.createException(ExceptionEnum.ADDRESS_LARGER_LIMITED);
        var address = mapper.toAddress(request);
        if (customerDto.shippingAddresses().isEmpty()) address.setActive(true);
        address.setCustomer(Customer.builder().id(customerId).build());
        return mapper.toAddressResponse(repository.save(address));
    }

    @Override
    public AddressResponse addAddress(SaveAddressRequest request) {
        var authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        var jwtPayload = jwtTokenUtil.getPayloadNotVerify(jwtTokenUtil.decodeTokenNotVerify(authentication.getToken().getTokenValue()));
        return addAddress(request, jwtPayload.getUserId());
    }

    @Override
    public List<AddressResponse> getAddresses(Long customerId) {
        var addresses = repository.findAllByLockAndCustomer_Id(false, customerId);
        return mapper.toAddressResponseList(addresses);
    }

    @Override
    public AddressResponse getAddressById(Long id, Long CustomerId) {
        var address = Optional.of(repository.findAddressByIdAndLockAndCustomer_Id(id, false, CustomerId)).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
        return mapper.toAddressResponse(address);
    }

    @Override
    public void deleteAddressById(Long id, Long customerId) {
        var address = repository.findById(id).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
        address.setLock(true);
        repository.save(address);
    }

}
