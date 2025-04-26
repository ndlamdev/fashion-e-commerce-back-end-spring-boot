package com.lamnguyen.profile_service.mapper;

import com.lamnguyen.profile_service.domain.dto.CustomerDto;
import com.lamnguyen.profile_service.domain.request.SaveCustomerRequest;
import com.lamnguyen.profile_service.domain.response.SaveCustomerResponse;
import com.lamnguyen.profile_service.message.SaveProfileUserMessage;
import com.lamnguyen.profile_service.model.entity.Customer;
import com.lamnguyen.profile_service.protos.ProfileUserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IAddressMapper.class, IGrpcMapper.class})
public interface ICustomerMapper {
	Customer toCustomer(SaveProfileUserMessage message);

	Customer toCustomer(SaveCustomerRequest request);

	SaveCustomerResponse toSaveCustomerResponse(Customer customer);

	List<CustomerDto> toCustomerDTOs(List<Customer> customers);

	CustomerDto toCustomerDto(Customer customer);

	Customer toCustomer(CustomerDto customer);

	@Mapping(source = "birthday", target = "birthday", qualifiedByName = "convertLocalDateTimeToStringValue")
	ProfileUserResponse toUserResponse(CustomerDto customer);
}
