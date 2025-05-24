package com.lamnguyen.profile_service.service.business;

import com.lamnguyen.profile_service.domain.request.SaveAddressRequest;
import com.lamnguyen.profile_service.domain.response.AddressResponse;

import java.util.List;

public interface IAddressService {
    AddressResponse saveAddress(SaveAddressRequest request, Long addressId, Long customerId);

    AddressResponse saveAddress(SaveAddressRequest request, Long addressId);

    AddressResponse addAddress(SaveAddressRequest request, Long customerId);

    AddressResponse addAddress(SaveAddressRequest request);

    List<AddressResponse> getAddresses(Long customerId);

    List<AddressResponse> getAddresses();

    AddressResponse getAddressById(Long id, Long CustomerId);

    AddressResponse getAddressById(Long id);

    void deleteAddressById(Long id, Long customerId);

    void deleteAddressById(Long id);

    void setCountAddressLimited(Integer limit);

    void setDefaultAddress(Long oldId, Long newId);

    void setDefaultAddress(Long oldId, Long newId, Long customerId);

    AddressResponse getDefaultAddress();
}
