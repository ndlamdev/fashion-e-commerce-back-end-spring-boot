/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:00 PM - 20/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.service.authentication.v1;

import com.lamnguyen.authentication_service.domain.dto.LoginSuccessDto;
import com.lamnguyen.authentication_service.domain.request.RegisterAccountWithFacebookRequest;
import com.lamnguyen.authentication_service.service.authentication.IFacebookAuthService;
import com.lamnguyen.authentication_service.service.business.facebook.IFacebookGraphClient;
import com.lamnguyen.authentication_service.util.property.ApplicationProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class FacebookAuthServiceImpl implements IFacebookAuthService {
	IFacebookGraphClient facebookGraphClient;
	ApplicationProperty applicationProperty;

	@Override
	public LoginSuccessDto login(String accessToken) {
		System.out.println(facebookGraphClient.debugToken(accessToken));
		System.out.println(facebookGraphClient.exchangeToken(applicationProperty.getAppIdFacebook(), applicationProperty.getAppSecretFacebook(), accessToken));
		return null;
	}

	@Override
	public void register(RegisterAccountWithFacebookRequest request) {

	}
}
