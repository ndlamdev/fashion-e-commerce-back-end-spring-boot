package com.lamnguyen.profile_service.service.business.v1;

import com.lamnguyen.profile_service.config.exception.ApplicationException;
import com.lamnguyen.profile_service.config.exception.ExceptionEnum;
import com.lamnguyen.profile_service.domain.request.SaveAddressRequest;
import com.lamnguyen.profile_service.domain.response.AddressResponse;
import com.lamnguyen.profile_service.mapper.IAddressMapper;
import com.lamnguyen.profile_service.model.entity.Profile;
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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AddressServiceImpl implements IAddressService {
	IAddressMapper mapper;
	IAddressRepository repository;
	JwtTokenUtil jwtTokenUtil;
	AddressProducerImpl addressProducer;

	@Override
	public AddressResponse saveAddress(SaveAddressRequest request, Long id, Long userId) {
		var address = repository.findAddressByIdAndUserIdAndLockIsFalse(id, userId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
		address = mapper.toAddress(request);
		address.setId(id);
		address.setUserId(userId);
		if (request.getActive()) {
			repository.activeAddress(userId, id);
			addressProducer.sendInfoAddressShipping(mapper.toInfoAddressShipping(address)); // send info-address-topic
		}
		return mapper.toAddressResponse(repository.save(address));
	}

	@Override
	public AddressResponse saveAddress(SaveAddressRequest request, Long addressId) {
		return saveAddress(request, addressId, jwtTokenUtil.getUserId());
	}

	@Override
	public AddressResponse addAddress(SaveAddressRequest request, Long userId) {
		var addresses = getAddresses(userId);
		var addressDefault = addresses.stream()
				.filter(AddressResponse::getActive)
				.findFirst()
				.orElse(null);
		if (addresses.size() >= 5)
			throw ApplicationException.createException(ExceptionEnum.ADDRESS_LARGER_LIMITED);
		var address = mapper.toAddress(request);
		if (addresses.isEmpty()) address.setActive(true);
		if (addressDefault != null && address.getActive()) repository.inactiveAddress(addressDefault.getId(), userId);
		if (address.getActive())
			addressProducer.sendInfoAddressShipping(mapper.toInfoAddressShipping(address)); // send info-address-topic

		address.setUserId(userId);
		return mapper.toAddressResponse(repository.save(address));
	}

	@Override
	public AddressResponse addAddress(SaveAddressRequest request) {
		return addAddress(request, jwtTokenUtil.getUserId());
	}


	@Override
	public List<AddressResponse> getAddresses(Long userId) {
		var addresses = repository.findAllByUserIdAndLockIsFalse(userId);
		return mapper.toAddressResponseList(addresses);
	}

	@Override
	public List<AddressResponse> getAddresses() {
		return getAddresses(jwtTokenUtil.getUserId());
	}

	@Override
	public AddressResponse getAddressById(Long id, Long userId) {
		var address = repository.findAddressByIdAndUserIdAndLockIsFalse(id, userId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
		return mapper.toAddressResponse(address);
	}

	@Override
	public AddressResponse getAddressById(Long id) {
		return getAddressById(id, jwtTokenUtil.getUserId());
	}

	@Override
	public void deleteAddressById(Long id, Long userId) {
		var address = repository.findAddressByIdAndUserIdAndLockIsFalse(id, userId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
		address.setLock(true);
		repository.save(address);

		if (!address.getActive()) return;
		repository.findFirstByUserIdAndLockIsFalse(userId).ifPresent(it -> {
			it.setActive(true);
			repository.save(it);
			addressProducer.sendInfoAddressShipping(mapper.toInfoAddressShipping(address));
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
	public void setDefaultAddress(Long oldId, Long newId, Long userId) {
		repository.findAddressByIdAndUserIdAndLockIsFalse(oldId, userId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
		repository.findAddressByIdAndUserIdAndLockIsFalse(newId, userId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
		repository.setDefaultAddress(oldId, newId, userId);
	}

	@Override
	public AddressResponse getDefaultAddress() {
		var userId = jwtTokenUtil.getUserId();
		var address = repository.findByUserIdAndLockIsFalseAndActiveIsTrue(userId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.ADDRESS_NOT_FOUND));
		return mapper.toAddressResponse(address);
	}
}
