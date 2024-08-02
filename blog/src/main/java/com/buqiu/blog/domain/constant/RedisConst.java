package com.buqiu.blog.domain.constant;

/**
 * <p>
 * 类描述
 * </p>
 *
 * @author: 不秋
 * @since: 2024-08-02 02:25:07
 */
public class RedisConst {
    public static final long CODE_EXPIRE_TIME = 5;

    private RedisConst(){
        throw new IllegalStateException("Utility class,cannot be instantiated");
    }
}
