package com.lamnguyen.profile_service.service.business.v1;

import com.lamnguyen.profile_service.config.exception.ApplicationException;
import com.lamnguyen.profile_service.config.exception.ExceptionEnum;
import com.lamnguyen.profile_service.domain.request.SaveAddressRequest;
import com.lamnguyen.profile_service.domain.response.AddressResponse;
import com.lamnguyen.profile_service.mapper.IAddressMapper;
import com.lamnguyen.profile_service.model.entity.Address;
import com.lamnguyen.profile_service.model.entity.Customer;
import com.lamnguyen.profile_service.repository.IAddressRepository;
import com.lamnguyen.profile_service.service.business.IAddressService;
import com.lamnguyen.profile_service.service.kafka.producer.v1.AddressProducerImpl;
import com.lamnguyen.profile_service.utils.helper.JwtTokenUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AddressServiceImpl implements IAddressService {
	IAddressMapper mapper;
	IAddressRepository repository;
	JwtTokenUtil jwtTokenUtil;
	AddressProducerImpl customerProducer;

	@Override
	public AddressResponse saveAddress(SaveAddressRequest request, Long id, Long customerId) {
		var address = repository.findAddressByIdAndCustomer_IdAndLockIsFalse(id, customerId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
		address = mapper.toAddress(request);
		address.setId(id);
		address.setCustomer(Customer.builder().id(customerId).build());
		if (request.active()) {
			repository.activeAddress(customerId, id);
			customerProducer.sendInfoAddressShipping(mapper.toInfoAddressShipping(address)); // send info-address-topic
		}
		return mapper.toAddressResponse(repository.save(address));
	}

	@Override
	public AddressResponse saveAddress(SaveAddressRequest request, Long addressId) {
		return saveAddress(request, addressId, jwtTokenUtil.getUserId());
	}

	@Override
	public AddressResponse addAddress(SaveAddressRequest request, Long customerId) {
		var addresses = getAddresses(customerId);
		var addressDefault = addresses.stream()
				.filter(AddressResponse::active)
				.findFirst()
				.orElse(null);
		if (addresses.size() >= 5)
			throw ApplicationException.createException(ExceptionEnum.ADDRESS_LARGER_LIMITED);
		var address = mapper.toAddress(request);
		if (addresses.isEmpty()) address.setActive(true);
		if (addressDefault != null && address.getActive()) repository.inactiveAddress(addressDefault.id(), customerId);
		if (address.getActive())
			customerProducer.sendInfoAddressShipping(mapper.toInfoAddressShipping(address)); // send info-address-topic
		address.setCustomer(Customer.builder().id(customerId).build());
		return mapper.toAddressResponse(repository.save(address));
	}

	@Override
	public AddressResponse addAddress(SaveAddressRequest request) {
		return addAddress(request, jwtTokenUtil.getUserId());
	}


	@Override
	public List<AddressResponse> getAddresses(Long customerId) {
		var addresses = repository.findAllByCustomer_IdAndLockIsFalse( customerId);
		return mapper.toAddressResponseList(addresses);
	}

	@Override
	public List<AddressResponse> getAddresses() {
		return getAddresses(jwtTokenUtil.getUserId());
	}

	@Override
	public AddressResponse getAddressById(Long id, Long customerId) {
		var address = repository.findAddressByIdAndCustomer_IdAndLockIsFalse(id,  customerId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
		return mapper.toAddressResponse(address);
	}

	@Override
	public AddressResponse getAddressById(Long id) {
		return getAddressById(id, jwtTokenUtil.getUserId());
	}

	@Override
	public void deleteAddressById(Long id, Long customerId) {
		var address = repository.findAddressByIdAndCustomer_IdAndLockIsFalse(id,  customerId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
		address.setLock(true);
		repository.save(address);

		if(!address.getActive()) return;
		repository.findFirstByCustomer_IdAndLockIsFalse(customerId).ifPresent(it -> {
			it.setActive(true);
			repository.save(it);
			customerProducer.sendInfoAddressShipping(mapper.toInfoAddressShipping(address));
		});
	}

	@Override
	public void deleteAddressById(Long id) {
		deleteAddressById(id, jwtTokenUtil.getUserId());
	}

	@Override
	public void setCountAddressLimited(Integer limit) {

	}

	@Override
	public void setDefaultAddress(Long oldId, Long newId) {
		setDefaultAddress(oldId, newId, jwtTokenUtil.getUserId());
	}

	@Override
	public void setDefaultAddress(Long oldId, Long newId, Long customerId) {
		repository.findAddressByIdAndCustomer_IdAndLockIsFalse(oldId,  customerId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
		repository.findAddressByIdAndCustomer_IdAndLockIsFalse(newId,  customerId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
		repository.setDefaultAddress(oldId, newId, customerId);
	}

	@Override
	public AddressResponse getDefaultAddress() {
		var userId = jwtTokenUtil.getUserId();
		var address = repository.findByCustomer_IdAndLockIsFalseAndActiveIsTrue(userId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));;
		return mapper.toAddressResponse(address);
	}
}
