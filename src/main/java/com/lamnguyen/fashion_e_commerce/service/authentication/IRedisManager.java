/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:23 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.service.authentication;

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

    void setVerifyAccountCode(long userId, String otp);

    Optional<String> getVerifyAccountCode(long userId);

    int increaseTotalTryVerifyAccount(long userId);

    void removeVerifyAccountCode(long userId);
    void setResetPasswordCode(long userId, String otp);

    Optional<String> getResetPasswordCode(long userId);

    int increaseTotalTryResetPassword(long userId);

    void removeResetPasswordCode(long userId);

    int getTotalResendVerifyAccount(long userId);
}
