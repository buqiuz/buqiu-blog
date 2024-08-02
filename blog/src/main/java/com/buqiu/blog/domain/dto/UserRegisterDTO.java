package com.buqiu.blog.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * 用户注册DTO
 * </p>
 *
 * @author: 不秋
 * @since: 2024-07-31 16:22:15
 */
@Data
public class UserRegisterDTO {
    @Schema(description = "用户名")
    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$")
    @Length(min = 1, max = 10)
    private String username;

    @Schema(description = "密码")
    @Length(min = 6, max = 20)
    private String password;

    @Schema(description = "邮箱")
    @Email
    @Length(min = 4)
    private String email;

    @Schema(description = "验证码")
    @Length(max = 6, min = 6)
    private String code;

}
