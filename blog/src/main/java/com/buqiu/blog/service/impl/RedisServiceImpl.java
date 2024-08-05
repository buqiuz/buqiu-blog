package com.buqiu.blog.service.impl;

import com.buqiu.blog.service.IRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 类描述
 * </p>
 *
 * @author: 不秋
 * @since: 2024-08-02 02:05:13
 */
@Service
@RequiredArgsConstructor
public class RedisServiceImpl<T> implements IRedisService<T> {
    public static final String SEPARATOR = ":";

//    @Resource
    private final RedisTemplate<String, T> redisTemplate;
    /**
    * @author: 不秋
    * @since: 2024-08-02 02:08:02
    * @description: 将多个key用:拼接
    * @params: keys
    * @return: 最终的Key
    */
    public static String buildKey(String... keys){
        return String.join(SEPARATOR, keys);
    }

    /**
    * @author: 不秋
    * @since: 2024-08-02 02:49:15
    * @description: 保存邮件验证码
    * @params: type可为"register"、"forget"等等
    * @return: Void
    */
    @Override
    public void setValue(final String[] key, final T value) {
        redisTemplate.opsForValue().set(buildKey(key), value);
    }

    @Override
    public void setValue(String[] key, T value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(buildKey(key), value, timeout, unit);
    }

    @Override
    public T getValue(String[] key) {
        return redisTemplate.opsForValue().get(buildKey(key));
    }

    @Override
    public Boolean deleteValue(String[] key) {
        return redisTemplate.delete(buildKey(key));
    }

    @Override
    public void setHash(String[] key,  T value) {
        redisTemplate.opsForHash().put(buildKey(key), value, value);
    }

}
