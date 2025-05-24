package com.lamnguyen.profile_service.service.business;

import com.lamnguyen.profile_service.domain.request.SaveAddressRequest;
import com.lamnguyen.profile_service.domain.response.AddressResponse;

import java.util.List;

public interface IAddressService {
    AddressResponse saveAddress(SaveAddressRequest request, Long addressId, Long userId);

    AddressResponse saveAddress(SaveAddressRequest request, Long addressId);

    AddressResponse addAddress(SaveAddressRequest request, Long userId);

    AddressResponse addAddress(SaveAddressRequest request);

    List<AddressResponse> getAddresses(Long userId);

    List<AddressResponse> getAddresses();

    AddressResponse getAddressById(Long id, Long userId);

    AddressResponse getAddressById(Long id);

    void deleteAddressById(Long id, Long userId);

    void deleteAddressById(Long id);

    void setCountAddressLimited(Integer limit);

    void setDefaultAddress(Long oldId, Long newId);

    void setDefaultAddress(Long oldId, Long newId, Long userId);

    AddressResponse getDefaultAddress();
}
