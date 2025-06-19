/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:19 PM-23/04/2025
 * User: kimin
 **/

package com.lamnguyen.media_service.service.redis.v1;

import com.lamnguyen.media_service.domain.dto.MediaDto;
import com.lamnguyen.media_service.service.redis.ACacheManage;
import com.lamnguyen.media_service.service.redis.CallbackDB;
import com.lamnguyen.media_service.service.redis.IMediaRedisManage;
import com.lamnguyen.media_service.utils.helper.RedissionClientUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class MediaRedisManageImpl extends ACacheManage<MediaDto> implements IMediaRedisManage {
    public MediaRedisManageImpl(RedisTemplate<String, MediaDto> template, RedissionClientUtil redissonClient) {
        super(template, redissonClient);
    }

    @Override
    public Optional<MediaDto> get(String id) {
        return super.get(id);
    }

    @Override
    public Optional<MediaDto> cache(String keyLock, String keyCache, CallbackDB<MediaDto> callDB) {
        return cache(keyLock, keyCache, callDB, 60, TimeUnit.MINUTES);
    }

    @Override
    public void delete(String key) {
        super.delete(key);
    }

    @Override
    public void save(String key, MediaDto data) {
        save(key, data, 60, TimeUnit.MINUTES);
    }

    private String generateIdKey(String key) {
        return generateHashKey("MEDIA", "ID", key);
    }

    private String generateNameKey(String key) {
        return generateHashKey("MEDIA", "NAME", key);
    }

    @Override
    public Optional<MediaDto> cacheById(String id, CallbackDB<MediaDto> callDB) {
        return cache(generateIdKey(id), generateIdKey(id), callDB);
    }

    @Override
    public void deleteById(String id) {
        delete(generateIdKey(id));
    }

    @Override
    public Optional<MediaDto> getById(String id) {
        return get(generateIdKey(id));
    }

    @Override
    public Optional<MediaDto> cacheByName(String name, CallbackDB<MediaDto> callDB) {
        return cache(generateNameKey(name), generateNameKey(name), callDB);
    }

    @Override
    public void deleteByName(String name) {
        delete(generateNameKey(name));
    }

    @Override
    public Optional<MediaDto> getByName(String name) {
        return get(generateNameKey(name));
    }

    @Override
    public Optional<List<MediaDto>> getAll() {
        var values = this.template.opsForList().range("ALL_MEDIA", 0, -1);
        if (values == null || values.isEmpty()) return Optional.empty();
        this.template.expire("ALL_MEDIA", this.duration, this.timeUnit);
        return Optional.of(values);
    }

    @Override
    public void cleanCacheGetAll() {
        delete("ALL_MEDIA");
    }

    @Override
    public Optional<List<MediaDto>> cacheAll(CallbackDB<List<MediaDto>> callDB) {
        return this.redissonClient
                .synchronize("ALL_MEDIA", () -> {
                    var cached = getAll();
                    if (cached.isPresent()) return cached;

                    var optionalData = callDB.call();
                    if (optionalData.isEmpty() || optionalData.get().isEmpty())
                        return Optional.empty();
                    this.template
                            .opsForList()
                            .leftPushAll("ALL_MEfDIA", optionalData.get());
                    this.template
                            .expire("ALL_MEDIA", this.duration, this.timeUnit);
                    return optionalData;
                }, this::getAll);
    }
}
