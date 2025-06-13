package com.lamnguyen.profile_service.service.business;

import com.lamnguyen.profile_service.domain.request.SaveAddressRequest;
import com.lamnguyen.profile_service.domain.dto.AddressDto;

import java.util.List;

public interface IAddressService {
    AddressDto saveAddress(SaveAddressRequest request, Long addressId, Long userId);

    AddressDto saveAddress(SaveAddressRequest request, Long addressId);

    AddressDto addAddress(SaveAddressRequest request, Long userId);

    AddressDto addAddress(SaveAddressRequest request);

    List<AddressDto> getAddresses(Long userId);

    List<AddressDto> getAddresses();

    AddressDto getAddressById(Long id, Long userId);

    AddressDto getAddressById(Long id);

    void deleteAddressById(Long id, Long userId);

    void deleteAddressById(Long id);

    void setDefaultAddress(Long oldId, Long newId);

    void setDefaultAddress(Long oldId, Long newId, Long userId);

    AddressDto getDefaultAddress();
}
