package com.lamnguyen.profile_service.service.business.v1;

import com.lamnguyen.profile_service.config.exception.ApplicationException;
import com.lamnguyen.profile_service.config.exception.ExceptionEnum;
import com.lamnguyen.profile_service.domain.request.SaveAddressRequest;
import com.lamnguyen.profile_service.domain.response.AddressResponse;
import com.lamnguyen.profile_service.mapper.IAddressMapper;
import com.lamnguyen.profile_service.model.entity.Address;
import com.lamnguyen.profile_service.model.entity.Customer;
import com.lamnguyen.profile_service.repository.IAddressRepository;
import com.lamnguyen.profile_service.service.business.IAddressService;
import com.lamnguyen.profile_service.service.business.producer.CustomerProducerImpl;
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
    JwtTokenUtil jwtTokenUtil;
    CustomerProducerImpl customerProducer;

    @Override
    public AddressResponse saveAddress(SaveAddressRequest request, Long id, Long customerId) {
        var address = Optional.ofNullable(repository.findAddressByIdAndLockAndCustomer_Id(id, false, customerId)).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
        address = mapper.toAddress(request);
        address.setId(id);
        address.setCustomer(Customer.builder().id(customerId).build());
        if(request.active()) {
            repository.activeAddress(customerId, id);
            customerProducer.sendInfoAddressShipping(mapper.toInfoAddressShipping(address)); // send info-address-topic
        }
        return mapper.toAddressResponse(repository.save(address));
    }

    @Override
    public AddressResponse saveAddress(SaveAddressRequest request, Long addressId) {
        return saveAddress(request, addressId, getUserIdFromToken());
    }

    @Override
    public AddressResponse addAddress(SaveAddressRequest request, Long customerId) {
        var addresses = getAddresses(customerId);
        var addressDefault = addresses.stream()
                .filter(AddressResponse::active)
                .findFirst()
                .orElse(null);
        if (addresses.size() >= 5)
            throw ApplicationException.createException(ExceptionEnum.ADDRESS_LARGER_LIMITED);
        var address = mapper.toAddress(request);
        if (addresses.isEmpty())  address.setActive(true);
        if(addressDefault != null) repository.inactiveAddress(addressDefault.id(), customerId);
        if(address.getActive()) customerProducer.sendInfoAddressShipping(mapper.toInfoAddressShipping(address)); // send info-address-topic
        address.setCustomer(Customer.builder().id(customerId).build());
        return mapper.toAddressResponse(repository.save(address));
    }

    @Override
    public AddressResponse addAddress(SaveAddressRequest request) {
        return addAddress(request, getUserIdFromToken());
    }


    @Override
    public List<AddressResponse> getAddresses(Long customerId) {
        var addresses = repository.findAllByLockAndCustomer_Id(false, customerId);
        return mapper.toAddressResponseList(addresses);
    }

    @Override
    public List<AddressResponse> getAddresses() {
        return getAddresses(getUserIdFromToken());
    }

    @Override
    public AddressResponse getAddressById(Long id, Long customerId) {
        var address = Optional.ofNullable(repository.findAddressByIdAndLockAndCustomer_Id(id, false, customerId)).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
        return mapper.toAddressResponse(address);
    }

    @Override
    public AddressResponse getAddressById(Long id) {
        return getAddressById(id, getUserIdFromToken());
    }

    @Override
    public void deleteAddressById(Long id, Long customerId) {
        var address = Optional.ofNullable(repository.findAddressByIdAndLockAndCustomer_Id(id, false, customerId)).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
        address.setLock(true);
        repository.save(address);

        Address first = null;
        if (address.getActive()) {
            first = repository.findDistinctFirstByLockAndCustomer_Id(false, customerId);
        }
        if (first != null) {
            first.setActive(true);
            repository.save(first);
            customerProducer.sendInfoAddressShipping(mapper.toInfoAddressShipping(address)); // send info-address-topic
        }
    }

    @Override
    public void deleteAddressById(Long id) {
        deleteAddressById(id, getUserIdFromToken());
    }

    @Override
    public void setCountAddressLimited(Integer limit) {

    }

    @Override
    public void setDefaultAddress(Long oldId, Long newId) {
        setDefaultAddress(oldId, newId, getUserIdFromToken());
    }

    @Override
    public void setDefaultAddress(Long oldId, Long newId, Long customerId) {
        Optional.ofNullable(repository.findAddressByIdAndLockAndCustomer_Id(oldId, false, customerId)).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
        Optional.ofNullable(repository.findAddressByIdAndLockAndCustomer_Id(newId, false, customerId)).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
        repository.setDefaultAddress(oldId, newId, customerId);
    }

    private Long getUserIdFromToken() {
        var authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        var jwtPayload = jwtTokenUtil.getPayloadNotVerify(jwtTokenUtil.decodeTokenNotVerify(authentication.getToken().getTokenValue()));
        return jwtPayload.getUserId();
    }
}
