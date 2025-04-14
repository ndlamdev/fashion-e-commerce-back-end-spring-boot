package com.lamnguyen.profile_service.service;

import com.lamnguyen.profile_service.domain.request.SaveAddressRequest;
import com.lamnguyen.profile_service.domain.response.AddressResponse;

import java.util.List;

public interface IAddressService {
    AddressResponse saveAddress(SaveAddressRequest request, Long addressId, Long customerId);

    AddressResponse addAddress(SaveAddressRequest request, Long customerId);

    AddressResponse addAddress(SaveAddressRequest request);

    List<AddressResponse> getAddresses(Long customerId);

    AddressResponse getAddressById(Long id, Long CustomerId);

    void deleteAddressById(Long id, Long customerId);

}
