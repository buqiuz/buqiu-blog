package com.buqiu.blog.domain.constant;

import java.time.LocalDateTime;

/**
 * <p>
 * 类描述
 * </p>
 *
 * @author: 不秋
 * @since: 2024-08-02 10:14:20
 */
public class UserConst {
    public static final String DEFAULT_NICKNAME="不秋";
    public static final String DEFAULT_AVATAR="";
    public static final Integer DEFAULT_AGE=0;
    public static final Byte DEFAULT_GENDER = 0;
    public static final LocalDateTime DEFAULT_BIRTHDAY = LocalDateTime.of(2000,1,1,0,0);

    public static final String DEFAULT_BIO = "这个人很烂，什么也没留下";
    public static final Byte DEFAULT_STATUS = 1;
    private UserConst(){
        throw new IllegalStateException("Utility class,cannot be instantiated");
    }
}
