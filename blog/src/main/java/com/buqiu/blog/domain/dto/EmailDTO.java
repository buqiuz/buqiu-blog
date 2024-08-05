package com.buqiu.blog.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 类描述
 * </p>
 *
 * @author: 不秋
 * @since: 2024-08-05 17:25:06
 */
@Data
public class EmailDTO {
    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "发送类型")
    private String type;
}
