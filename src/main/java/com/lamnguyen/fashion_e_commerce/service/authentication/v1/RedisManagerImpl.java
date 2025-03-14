/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:29 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.service.authentication.v1;

import com.lamnguyen.fashion_e_commerce.service.authentication.IRedisManager;
import com.lamnguyen.fashion_e_commerce.util.property.ApplicationProperty;
import com.lamnguyen.fashion_e_commerce.util.property.OtpProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RedisManagerImpl implements IRedisManager {
    RedisTemplate<String, Object> redisTemplate;
    ApplicationProperty applicationProperty;
    OtpProperty.AccountVerification accountVerification;
    OtpProperty.ResetPasswordVerification resetPasswordVerification;

    @Override
    public void addAccessTokenIdInBlackList(long userId, String tokenId) {
        redisTemplate.opsForValue().set(generateKey(applicationProperty.getKeyAccessTokenBlacklist(), userId, tokenId), 1, applicationProperty.getExpireAccessToken() * 1000L);
    }

    @Override
    public void setAccessTokenId(long userId, String tokenId) {
        redisTemplate.opsForValue().set(generateKey(applicationProperty.getKeyAccessToken(), userId, tokenId), 1);
    }

    @Override
    public void removeAccessTokenId(long userId, String tokenId) {
        redisTemplate.delete(generateKey(applicationProperty.getKeyAccessToken(), userId, tokenId));
    }

    @Override
    public boolean existAccessTokenId(long userId, String tokenId) {
        return Objects.equals(redisTemplate.opsForValue().get(generateKey(applicationProperty.getKeyAccessToken(), userId, tokenId)), 1);
    }

    @Override
    public boolean existAccessTokenIdInBlacklist(long userId, String tokenId) {
        return Objects.equals(redisTemplate.opsForValue().get(generateKey(applicationProperty.getKeyAccessTokenBlacklist(), userId, tokenId)), 1);
    }

    @Override
    public void addRefreshTokenIdInBlackList(long userId, String tokenId) {
        redisTemplate.opsForValue().set(generateKey(applicationProperty.getKeyRefreshTokenBlacklist(), userId, tokenId), 1, applicationProperty.getExpireRefreshToken() * 1000L);
    }

    @Override
    public void setRefreshTokenId(long userId, String tokenId) {
        redisTemplate.opsForValue().set(generateKey(applicationProperty.getKeyRefreshToken(), userId, tokenId), 1);
    }

    @Override
    public void removeRefreshTokenId(long userId, String tokenId) {
        redisTemplate.delete(generateKey(applicationProperty.getKeyRefreshToken(), userId, tokenId));
    }

    @Override
    public boolean existRefreshInBlacklist(long userId, String tokenId) {
        return Objects.equals(redisTemplate.opsForValue().get(generateKey(applicationProperty.getKeyRefreshTokenBlacklist(), userId, tokenId)), 1);
    }

    @Override
    public void setVerifyAccountCode(long userId, String code) {
        redisTemplate.opsForValue().set(generateKey(accountVerification.getKey(), userId), code, accountVerification.getExpire(), TimeUnit.MINUTES);
        setVerificationTotalTry(accountVerification.getTotalTryKey(), userId, accountVerification.getExpire());
        setVerificationTotalResend(accountVerification.getTotalResendKey(), userId, accountVerification.getTotalResendExpire());
    }

    @Override
    public Optional<String> getVerifyAccountCode(long userId) {
        return getValue(accountVerification.getKey(), userId);
    }

    @Override
    public int increaseTotalTryVerifyAccount(long userId) {
        var value = redisTemplate.opsForValue().increment(generateKey(accountVerification.getTotalTryKey(), userId));
        return value != null ? value.intValue() : -1;
    }

    @Override
    public void removeVerifyAccountCode(long userId) {
        redisTemplate.delete(generateKey(accountVerification.getKey(), userId));
        redisTemplate.delete(generateKey(accountVerification.getTotalResendKey(), userId));
        redisTemplate.delete(generateKey(accountVerification.getTotalTryKey(), userId));
    }

    @Override
    public void setResetPasswordCode(long userId, String code) {
        redisTemplate.opsForValue().set(generateKey(resetPasswordVerification.getKey(), userId), code, resetPasswordVerification.getExpire(), TimeUnit.MINUTES);
        setVerificationTotalTry(resetPasswordVerification.getTotalTryKey(), userId, resetPasswordVerification.getExpire());
        setVerificationTotalResend(resetPasswordVerification.getTotalResendKey(), userId, resetPasswordVerification.getTotalResendExpire());
    }

    @Override
    public Optional<String> getResetPasswordCode(long userId) {
        return getValue(resetPasswordVerification.getKey(), userId);
    }

    @Override
    public int increaseTotalTryResetPassword(long userId) {
        var value = redisTemplate.opsForValue().increment(generateKey(resetPasswordVerification.getTotalTryKey(), userId));
        return value != null ? value.intValue() : -1;
    }

    @Override
    public void removeResetPasswordCode(long userId) {
        redisTemplate.delete(generateKey(resetPasswordVerification.getKey(), userId));
        redisTemplate.delete(generateKey(resetPasswordVerification.getTotalResendKey(), userId));
        redisTemplate.delete(generateKey(resetPasswordVerification.getTotalTryKey(), userId));
    }

    private void setVerificationTotalTry(String prop, long userId, int expire) {
        redisTemplate.opsForValue().set(generateKey(prop, userId), 0, expire, TimeUnit.MINUTES);
    }

    private void setVerificationTotalResend(String prop, long userId, int expire) {
        var key = generateKey(prop, userId);
        var value = redisTemplate.opsForValue().get(key);
        if (value != null)
            redisTemplate.opsForValue().increment(key);

        redisTemplate.opsForValue().set(key, 0, expire, TimeUnit.MINUTES);
    }

    @Override
    public int getTotalResendVerifyAccount(long userId) {
        var value = redisTemplate.opsForValue().get(generateKey(accountVerification.getTotalResendKey(), userId));
        return value == null ? 0 : (int) value;
    }

    public Optional<String> getValue(String prop, long userId) {
        var value = redisTemplate.opsForValue().get(generateKey(prop, userId));
        return value != null ? Optional.of(value.toString()) : Optional.empty();
    }

    private String generateKey(String prop, long userId, String... others) {
        return prop + "_" + userId + (others.length == 0 ? "" : "_" + String.join("_", others));
    }
}
