package com.lamnguyen.profile_service.mapper;

import com.lamnguyen.profile_service.domain.response.SaveAddressResponse;
import com.lamnguyen.profile_service.domain.request.SaveAddressRequest;
import com.lamnguyen.profile_service.message.SaveUserDetailMessage;
import com.lamnguyen.profile_service.model.entity.Address;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IAddressMapper {
    Address toAddress(SaveAddressRequest request);


    SaveAddressResponse toAddressResponse(Address address);

    List<SaveAddressResponse> toAddressResponseList(List<Address> addresses);
}
