package com.buqiu.blog.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.io.Serial;

/**
 * <p>
 * 这是一张不秋设计的blog的user表
 * </p>
 *
 * @author 不秋
 * @since 2024-07-31 15:32:41
 */
@Data
@Builder
@TableName("user")
@Schema(name = "User", description = "这是一张不秋设计的blog的user表")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户名")
    @TableField("username")
    private String username;

    @Schema(description = "昵称")
    @TableField("nickname")
    private String nickname;

    @Schema(description = "密码")
    @TableField("password")
    private String password;

    @Schema(description = "头像")
    @TableField("avatar")
    private String avatar;

    @Schema(description = "邮箱")
    @TableField("email")
    private String email;

    @Schema(description = "年龄")
    @TableField("age")
    private Integer age;

    @Schema(description = "性别(0：未定义；1：男；2：女）")
    @TableField("gender")
    private Byte gender;

    @Schema(description = "生日")
    @TableField("birthday")
    private LocalDateTime birthday;

    @Schema(description = "个人简介")
    @TableField("bio")
    private String bio;

    @Schema(description = "账号状态(-1：已删除；1：正常；0：封禁)")
    @TableField("status")
    private Byte status;

    @Schema(description = "账号创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @Schema(description = "注册时ip")
    @TableField("create_ip")
    private String createIp;

    @Schema(description = "注册地址")
    @TableField("create_address")
    private String createAddress;

    @Schema(description = "账号最近登录时间")
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

    @Schema(description = "最近一次登录ip")
    @TableField("last_login_ip")
    private String lastLoginIp;

    @Schema(description = "最近登录地址")
    @TableField("lsat_login_address")
    private String lsatLoginAddress;

    @Schema(description = "账号最后一次更新时间")
    @TableField("last_update_time")
    private LocalDateTime lastUpdateTime;
}
