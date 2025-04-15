package com.lamnguyen.profile_service.service;

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

}
