package com.lamnguyen.profile_service.service.business.v1;

import com.lamnguyen.profile_service.config.exception.ApplicationException;
import com.lamnguyen.profile_service.config.exception.ExceptionEnum;
import com.lamnguyen.profile_service.domain.dto.AddressDto;
import com.lamnguyen.profile_service.domain.request.SaveAddressRequest;
import com.lamnguyen.profile_service.mapper.IAddressMapper;
import com.lamnguyen.profile_service.repository.IAddressRepository;
import com.lamnguyen.profile_service.service.business.IAddressService;
import com.lamnguyen.profile_service.service.kafka.producer.IAddressProducer;
import com.lamnguyen.profile_service.service.redis.IProfileRedisManager;
import com.lamnguyen.profile_service.utils.helper.JwtTokenUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AddressServiceImpl implements IAddressService {
    IAddressMapper mapper;
    IAddressRepository repository;
    JwtTokenUtil jwtTokenUtil;
    IAddressProducer addressProducer;

    @Override
    public AddressDto saveAddress(Long userId, Long addressId, SaveAddressRequest request) {
        var address = repository.findAddressByUserIdAndIdAndLockIsFalse(userId, addressId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
        address = mapper.toAddress(request);
        address.setId(addressId);
        address.setUserId(userId);
        var result = mapper.toAddressDto(repository.save(address));
        if (request.getActive())
            setDefaultAddress(userId, result.getId());

        return result;
    }

    @Override
    public AddressDto saveAddress(Long addressId, SaveAddressRequest request) {
        return saveAddress(jwtTokenUtil.getUserId(), addressId, request);
    }

    @Override
    public AddressDto addAddress(Long userId, SaveAddressRequest request) {
        var addresses = getAddresses(userId);
        var addressDefault = addresses.stream()
                .filter(AddressDto::getActive)
                .findFirst()
                .orElse(null);
        if (addresses.size() >= 5)
            throw ApplicationException.createException(ExceptionEnum.ADDRESS_LARGER_LIMITED);
        var address = mapper.toAddress(request);
        if (addresses.isEmpty()) address.setActive(true);
        if (addressDefault != null && address.getActive()) repository.inactiveAddress(addressDefault.getId(), userId);
        if (address.getActive())
            addressProducer.sendInfoAddressShipping(mapper.toInfoAddressShipping(address)); // send info-address-topic

        address.setUserId(userId);
        return mapper.toAddressDto(repository.save(address));
    }

    @Override
    public AddressDto addAddress(SaveAddressRequest request) {
        return addAddress(jwtTokenUtil.getUserId(), request);
    }


    @Override
    public List<AddressDto> getAddresses(Long userId) {
        var addresses = repository.findAllByUserIdAndLockIsFalse(userId);
        return mapper.toAddressDtoList(addresses);
    }

    @Override
    public List<AddressDto> getAddresses() {
        return getAddresses(jwtTokenUtil.getUserId());
    }

    @Override
    public AddressDto getAddressById(Long userId, Long addressId) {
        var address = repository.findAddressByUserIdAndIdAndLockIsFalse(userId, addressId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
        return mapper.toAddressDto(address);
    }

    @Override
    public AddressDto getAddressById(Long addressId) {
        return getAddressById(jwtTokenUtil.getUserId(), addressId);
    }

    @Override
    public void deleteAddressById(Long userId, Long addressId) {
        var address = repository.findAddressByUserIdAndIdAndLockIsFalse(userId, addressId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
        address.setLock(true);
        repository.save(address);

        if (!address.getActive()) return;
        repository.findFirstByUserIdAndLockIsFalse(userId).ifPresent(it -> {
            it.setActive(true);
            repository.save(it);
            addressProducer.sendInfoAddressShipping(mapper.toInfoAddressShipping(address));
        });
    }

    @Override
    public void deleteAddressById(Long addressId) {
        deleteAddressById(jwtTokenUtil.getUserId(), addressId);
    }

    @Override
    public void setDefaultAddress(Long addressId) {
        setDefaultAddress(jwtTokenUtil.getUserId(), addressId);
    }

    @Override
    public void setDefaultAddress(Long userId, Long addressId) {
        repository.findByUserIdAndLockIsFalseAndActiveIsTrue(userId).ifPresent(address -> {
            address.setActive(false);
            repository.save(address);
        });
        var newAddressDefault = repository.findAddressByUserIdAndIdAndLockIsFalse(userId, addressId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
        newAddressDefault.setActive(true);
        repository.save(newAddressDefault);
    }

    @Override
    public AddressDto getDefaultAddress() {
        var userId = jwtTokenUtil.getUserId();
        return getDefaultAddress(userId);
    }

    @Override
    public AddressDto getDefaultAddress(Long userId) {
        var address = repository.findByUserIdAndLockIsFalseAndActiveIsTrue(userId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
        return mapper.toAddressDto(address);
    }
}
