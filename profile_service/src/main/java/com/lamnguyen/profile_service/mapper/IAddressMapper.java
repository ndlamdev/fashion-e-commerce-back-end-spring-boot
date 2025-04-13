package com.lamnguyen.profile_service.mapper;

import com.lamnguyen.profile_service.domain.dto.AddressDto;
import com.lamnguyen.profile_service.domain.response.SaveAddressResponse;
import com.lamnguyen.profile_service.domain.request.SaveAddressRequest;
import com.lamnguyen.profile_service.message.SaveUserDetailMessage;
import com.lamnguyen.profile_service.model.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IAddressMapper {
    Address toAddress(SaveAddressRequest request);

    SaveAddressResponse toAddressResponse(Address address);

    List<SaveAddressResponse> toAddressResponseList(List<Address> addresses);

    @Mapping(source = "customer.id", target = "customerId")
    AddressDto toAddressDto(Address address);
}
