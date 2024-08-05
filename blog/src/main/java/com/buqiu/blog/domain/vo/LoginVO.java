package com.buqiu.blog.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 类描述
 * </p>
 *
 * @author: 不秋
 * @since: 2024-08-05 16:28:03
 */
@Data
@AllArgsConstructor
public class LoginVO {
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "token")
    private String token;

    @Schema(description = "过期时间")
    private LocalDateTime expireTime;
}
