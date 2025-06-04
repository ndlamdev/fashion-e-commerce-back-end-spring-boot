package com.lamnguyen.profile_service.mapper;

import com.lamnguyen.profile_service.domain.dto.AddressDto;
import com.lamnguyen.profile_service.domain.response.AddressResponse;
import com.lamnguyen.profile_service.domain.request.SaveAddressRequest;
import com.lamnguyen.profile_service.event.InfoAddressShippingEvent;
import com.lamnguyen.profile_service.model.entity.Address;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IAddressMapper {
    Address toAddress(SaveAddressRequest request);

    AddressResponse toAddressResponse(Address address);

    List<AddressResponse> toAddressResponseList(List<Address> addresses);

    AddressDto toAddressDto(Address address);

    AddressDto toAddressDto(SaveAddressRequest request);

    Address toAddress(AddressDto address);

    InfoAddressShippingEvent toInfoAddressShipping(Address address);
}
