package com.buqiu.blog.service;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 接口描述
 * </p>
 *
 * @author: 不秋
 * @since: 2024-08-02 02:30:21
 */
public interface IRedisService<T> {
    void setValue(final String[] key, final T value);
    void setValue(final String[] key, final T value, final long timeout, final TimeUnit unit);

    T getValue(final String[] key);

    Boolean deleteValue(final String[] key);
}
