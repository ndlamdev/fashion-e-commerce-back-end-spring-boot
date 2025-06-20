package com.lamnguyen.profile_service.service.business;

import com.lamnguyen.profile_service.domain.request.SaveAddressRequest;
import com.lamnguyen.profile_service.domain.dto.AddressDto;

import java.util.List;

public interface IAddressService {
    AddressDto saveAddress(Long userId, Long addressId, SaveAddressRequest request);

    AddressDto saveAddress(Long addressId, SaveAddressRequest request);

    AddressDto addAddress(Long userId, SaveAddressRequest request);

    AddressDto addAddress(SaveAddressRequest request);

    List<AddressDto> getAddresses(Long userId);

    List<AddressDto> getAddresses();

    AddressDto getAddressById(Long userId, Long addressId);

    AddressDto getAddressById(Long addressId);

    void deleteAddressById(Long userId, Long addressId);

    void deleteAddressById(Long addressId);

    void setDefaultAddress(Long userId, Long addressId);

    void setDefaultAddress(Long addressId);

    AddressDto getDefaultAddress();

    AddressDto getDefaultAddress(Long useId);
}
