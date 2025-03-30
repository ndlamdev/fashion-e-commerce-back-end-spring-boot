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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
        redisTemplate.opsForValue().set(generateKey(applicationProperty.getKeyAccessTokenBlacklist(), userId, tokenId), 1, applicationProperty.getExpireAccessToken(), TimeUnit.MINUTES);
    }

    @Override
    public void setAccessTokenId(long userId, String tokenId) {
        redisTemplate.opsForValue().set(generateKey(applicationProperty.getKeyAccessToken(), userId, tokenId), 1, applicationProperty.getExpireAccessToken(), TimeUnit.MINUTES);
    }

    @Override
    public void removeAccessTokenId(long userId, String tokenId) {
        redisTemplate.delete(generateKey(applicationProperty.getKeyAccessToken(), userId, tokenId));
    }

    @Override
    public boolean existAccessTokenId(long userId, String tokenId) {
        var key = generateKey(applicationProperty.getKeyAccessToken(), userId, tokenId);
        var token = redisTemplate.opsForValue().get(key);
        return Objects.equals(token, 1);
    }

    @Override
    public boolean existAccessTokenIdInBlacklist(long userId, String tokenId) {
        return Objects.equals(redisTemplate.opsForValue().get(generateKey(applicationProperty.getKeyAccessTokenBlacklist(), userId, tokenId)), 1);
    }

    @Override
    public void addRefreshTokenIdInBlackList(long userId, String tokenId) {
        redisTemplate.opsForValue().set(generateKey(applicationProperty.getKeyRefreshTokenBlacklist(), userId, tokenId), 1, applicationProperty.getExpireRefreshToken(), TimeUnit.MINUTES);
    }

    @Override
    public void setRefreshTokenId(long userId, String tokenId) {
        redisTemplate.opsForValue().set(generateKey(applicationProperty.getKeyRefreshToken(), userId, tokenId), 1, applicationProperty.getExpireRefreshToken(), TimeUnit.MINUTES);
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
    public void setRegisterCode(long userId, String code) {
        setCodeVerify(userId, code, accountVerification.getKey(), accountVerification.getExpire(), accountVerification.getTotalTryKey(), accountVerification.getTotalResendKey(), accountVerification.getTotalResendExpire());
    }

    @Override
    public Optional<String> getRegisterCode(long userId) {
        return getValue(accountVerification.getKey(), userId);
    }

    @Override
    public int increaseTotalTryVerifyAccount(long userId) {
        var value = redisTemplate.opsForValue().increment(generateKey(accountVerification.getTotalTryKey(), userId));
        return value != null ? value.intValue() : -1;
    }

    @Override
    public void removeVerifyRegisterCode(long userId) {
        redisTemplate.delete(generateKey(accountVerification.getKey(), userId));
        redisTemplate.delete(generateKey(accountVerification.getTotalResendKey(), userId));
        redisTemplate.delete(generateKey(accountVerification.getTotalTryKey(), userId));
    }

    @Override
    public int getTotalResendRegisterCode(long userId) {
        var key = generateKey(accountVerification.getTotalResendKey(), userId);
        var value = redisTemplate.opsForValue().get(key);
        return value == null ? 0 : (int) value;
    }

    @Override
    public void setResetPasswordCode(long userId, String code) {
        setCodeVerify(userId, code, resetPasswordVerification.getKey(), resetPasswordVerification.getExpire(), resetPasswordVerification.getTotalTryKey(), resetPasswordVerification.getTotalResendKey(), resetPasswordVerification.getTotalResendExpire());
    }

    @Override
    public Optional<String> getResetPasswordCode(long userId) {
        return getValue(resetPasswordVerification.getKey(), userId);
    }

    @Override
    public int increaseTotalTryResetPassword(long userId) {
        var key = generateKey(resetPasswordVerification.getTotalTryKey(), userId);
        var value = redisTemplate.opsForValue().increment(key);
        return value != null ? value.intValue() : -1;
    }

    @Override
    public void removeResetPasswordCode(long userId) {
        redisTemplate.delete(generateKey(resetPasswordVerification.getKey(), userId));
        redisTemplate.delete(generateKey(resetPasswordVerification.getTotalResendKey(), userId));
        redisTemplate.delete(generateKey(resetPasswordVerification.getTotalTryKey(), userId));
    }

    @Override
    public int getTotalResendResetPasswordCode(long userId) {
        var key = generateKey(resetPasswordVerification.getTotalResendKey(), userId);
        var value = redisTemplate.opsForValue().get(key);
        return value == null ? 0 : (int) value;
    }


    @Override
    public String getApiName(String path, String method) {
        return (String) redisTemplate.opsForValue().get(path + "_" + method);
    }

    public Optional<String> getValue(String prop, long userId, String... others) {
        var value = redisTemplate.opsForValue().get(generateKey(prop, userId, others));
        return value != null ? Optional.of(value.toString()) : Optional.empty();
    }

    private String generateKey(String prop, long userId, String... others) {
        return prop + "_" + userId + (others.length == 0 ? "" : "_" + String.join("_", others));
    }

    @Override
    public boolean existTokenResetPasswordInBlacklist(long userId, String tokenId) {
        return Objects.equals(redisTemplate.opsForValue().get(generateKey(applicationProperty.getKeyResetPasswordTokenBlacklist(), userId, tokenId)), 1);
    }

    @Override
    public void addTokenResetPasswordInBlacklist(long userId, String tokenId) {
        redisTemplate.opsForValue().set(generateKey(applicationProperty.getKeyResetPasswordTokenBlacklist(), userId, tokenId), 1, applicationProperty.getExpireResetPasswordToken(), TimeUnit.MINUTES);
    }

    @Override
    public Long getDateTimeChangePassword(long userId) {
        var number = (Number) redisTemplate.opsForValue().get(generateKey(applicationProperty.getKeyDateTimeChangePassword(), userId));
        if (number == null) return null;
        return number.longValue();
    }

    @Override
    public void setDateTimeChangePassword(long userId, LocalDateTime dateTime) {
        redisTemplate.opsForValue().set(generateKey(applicationProperty.getKeyDateTimeChangePassword(), userId), LocalDateTime.now().toEpochSecond(ZoneOffset.UTC), applicationProperty.getExpireAccessToken(), TimeUnit.MINUTES);
    }

    private void setCodeVerify(long userId, String code, String key, int expire, String totalTryKey, String totalResendKey, int totalResendExpire) {
        redisTemplate.opsForValue().set(generateKey(key, userId), code, expire, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(generateKey(totalTryKey, userId), 0, expire, TimeUnit.MINUTES);
        setTotalResendCode(totalResendKey, userId, totalResendExpire);
    }

    private void setTotalResendCode(String prop, long userId, int expire) {
        var key = generateKey(prop, userId);
        var value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            redisTemplate.opsForValue().increment(key);
            return;
        }

        redisTemplate.opsForValue().set(key, 0, expire, TimeUnit.MINUTES);
    }
}
