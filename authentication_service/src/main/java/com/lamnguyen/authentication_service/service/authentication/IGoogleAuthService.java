/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:33 PM - 17/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.service.authentication;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.lamnguyen.authentication_service.domain.dto.LoginSuccessDto;
import com.lamnguyen.authentication_service.domain.request.RegisterAccountWithGoogleRequest;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface IGoogleAuthService {

	GoogleIdToken.Payload getPayload(String authToken) throws GeneralSecurityException, IOException;

	LoginSuccessDto login(String authCode);

	void register(RegisterAccountWithGoogleRequest request);
}
