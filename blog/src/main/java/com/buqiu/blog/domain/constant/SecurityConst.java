package com.buqiu.blog.domain.constant;

/**
 * <p>
 * 类描述
 * </p>
 *
 * @author: 不秋
 * @since: 2024-08-05 15:57:16
 */
public class SecurityConst {
    public static final String [] AUTHORIZED_URL = {
//            "public/**",
            "user/**",
            "/doc.html#/home"
    };
    private SecurityConst(){
        throw new IllegalStateException("Utility class,cannot be instantiated");
    }
}
