package com.buqiu.blog.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * Result结果枚举
 * </p>
 *
 * @author: 不秋
 * @since: 2024-07-31 15:49:45
 */
@AllArgsConstructor
@Getter
public enum ResultEnum {

    SUCCESS(200,"success"),

    FAILURE(500, "failure"),

    USERNAME_EXIST(1000, "用户名已存在"),

    EMAIL_EXIST(1001, "邮箱已被注册"),

    SEND_EMAIL_SUCCESS(200, "邮件发送成功"),

    SEND_EMAIL_FAILURE(1002, "发送邮件失败"),

    EMAIL_CODE_ERROR(1003, "邮箱验证码错误"),

    CREATE_USER_FAILURE(1004, "创建用户失败");

    private final Integer code;

    private final String msg;

}
