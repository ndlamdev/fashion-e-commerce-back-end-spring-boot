/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:33 PM - 17/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.service.authentication;

import com.lamnguyen.authentication_service.domain.dto.LoginSuccessDto;
import com.lamnguyen.authentication_service.domain.request.RegisterAccountWithFacebookRequest;

public interface IFacebookAuthService {

	LoginSuccessDto login(String accessToken);

	void register(RegisterAccountWithFacebookRequest request);
}
