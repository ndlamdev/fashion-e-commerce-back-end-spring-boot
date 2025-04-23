/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:23 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.service.authentication;

import java.time.LocalDateTime;
import java.util.Optional;

public interface IRedisManager {
    void addAccessTokenIdInBlackList(long userId, String tokenId);

    void setAccessTokenId(long userId, String tokenId);

    void removeAccessTokenId(long userId, String tokenId);

    boolean existAccessTokenId(long userId, String tokenId);

    boolean existAccessTokenIdInBlacklist(long userId, String tokenId);

    void addRefreshTokenIdInBlackList(long userId, String tokenId);

    void setRefreshTokenId(long userId, String tokenId);

    void removeRefreshTokenId(long userId, String tokenId);

    boolean existRefreshInBlacklist(long userId, String tokenId);

    void setRegisterCode(long userId, String otp);

    Optional<String> getRegisterCode(long userId);

    int increaseTotalTryVerifyAccount(long userId);

    void removeVerifyRegisterCode(long userId);

    void setResetPasswordCode(long userId, String otp);

    Optional<String> getResetPasswordCode(long userId);

    int increaseTotalTryResetPassword(long userId);

    void removeResetPasswordCode(long userId);

    int getTotalResendResetPasswordCode(long userId);

    boolean existTokenResetPasswordInBlacklist(long userId, String tokenId);

    void addTokenResetPasswordInBlacklist(long userId, String tokenId);

    Long getDateTimeChangePassword(long userId);

    void setDateTimeChangePassword(long userId, LocalDateTime dateTime);

    int getTotalResendRegisterCode(long userId);

    void setRegisterTokenIdUsingGoogle(String id);

    boolean existRegisterTokenIdUsingGoogle(String id);

    void setRegisterTokenIdUsingFacebook(String id);

    boolean existRegisterTokenIdUsingFacebook(String id);

    void setAccessTokenFacebook(String token);

    boolean existAccessTokenFacebook(String token);
}
