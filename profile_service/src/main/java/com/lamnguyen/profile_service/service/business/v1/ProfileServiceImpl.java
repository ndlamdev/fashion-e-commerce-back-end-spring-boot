package com.lamnguyen.profile_service.service.business.v1;

import com.lamnguyen.profile_service.config.exception.ApplicationException;
import com.lamnguyen.profile_service.config.exception.ExceptionEnum;
import com.lamnguyen.profile_service.domain.dto.ProfileDto;
import com.lamnguyen.profile_service.domain.request.SaveProfileRequest;
import com.lamnguyen.profile_service.domain.response.ProfileAdminResponse;
import com.lamnguyen.profile_service.mapper.IProfileMapper;
import com.lamnguyen.profile_service.repository.IProfileRepository;
import com.lamnguyen.profile_service.service.business.IProfileService;
import com.lamnguyen.profile_service.service.grpc.IOrderServiceGrpcClient;
import com.lamnguyen.profile_service.service.redis.IProfileRedisManager;
import com.lamnguyen.profile_service.utils.helper.JwtTokenUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileServiceImpl implements IProfileService {
    IProfileRepository profileRepository;
    IProfileMapper mapper;
    IProfileRedisManager profileRedisManager;
    JwtTokenUtil jwtTokenUtil;
    IOrderServiceGrpcClient orderServiceGrpcClient;

    @Override
    public ProfileDto saveProfile(SaveProfileRequest saveProfileRequest) {
        var userId = jwtTokenUtil.getUserId();
        var profile = profileRepository.findByUserId(userId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.USER_NOT_FOUND));
        var dataUpdate = mapper.toProfile(saveProfileRequest);
        dataUpdate.setId(profile.getId());
        dataUpdate.setUserId(userId);
        dataUpdate.setAvatar(profile.getAvatar());
        dataUpdate.setEmail(profile.getEmail());
        dataUpdate.setCreateAt(profile.getCreateAt());
        dataUpdate.setCreateBy(profile.getCreateBy());
        var result = mapper.toProfileDto(profileRepository.save(dataUpdate));
        profileRedisManager.deleteByUserId(userId);
        return result;
    }

	@Override
	public List<ProfileAdminResponse> getProfiles() {
		var userId = jwtTokenUtil.getUserId();
		var profiles = profileRepository.findAllByUserIdNot(userId);
		var result = mapper.toProfileAdminResponses(profiles);
		var generalInfos = orderServiceGrpcClient.getGeneralInfos(result.stream().map(ProfileDto::getUserId).collect(Collectors.toList()));
		result.forEach(data -> {
			var generalInfo = generalInfos.getOrDefault(data.getUserId(), null);
			if(generalInfo == null) return;
			data.setTotalOrders(generalInfo.getTotalOrder());
			data.setTotalSpent(generalInfo.getTotalSpent());
		});
		return result;
	}

    @Override
    @Transactional
    public ProfileDto getProfileByUserId(Long id) {
        return profileRedisManager
                .getByUserId(id) // get in cache
                .or(() -> cacheByUserId(id)) // caching data
                .orElseThrow(() -> ApplicationException.createException(ExceptionEnum.USER_NOT_FOUND)); // throw exception
    }

    private Optional<ProfileDto> cacheByUserId(long id) {
        var data = profileRepository.findByUserId(id).map(mapper::toProfileDto);
        data.orElseThrow(() -> ApplicationException.createException(ExceptionEnum.USER_NOT_FOUND));
        return profileRedisManager.cacheByUserId(id, () -> data, 60, TimeUnit.MINUTES);
    }

    @Override
    @Transactional
    public ProfileDto getProfile() {
        return getProfileByUserId(jwtTokenUtil.getUserId());
    }
}
