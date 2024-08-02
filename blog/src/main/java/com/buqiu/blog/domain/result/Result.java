package com.buqiu.blog.domain.result;

import com.buqiu.blog.domain.enums.ResultEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * Result统一返回类
 * </p>
 *
 * @author: 不秋
 * @since: 2024-07-31 15:37:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    @Schema(description = "状态码")
    private Integer code;

    @Schema(description = "提示信息")
    private String msg;

    @Schema(description = "响应数据")
    private T data;

    /**
     * 成功响应，不需要返回数据
     */
    public static <T> Result<T> success() {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), null);
    }

    /**
     * 成功响应，需要返回数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), data);
    }

    /**
     * 成功响应，需要返回数据跟信息
     */
    public static <T> Result<T> success(T data, String msg) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), msg, data);
    }

    /**
     * 成功响应，需要返回信息
     */
    public static <T> Result<T> success(ResultEnum resultEnum) {
        return new Result<>(resultEnum.getCode(), resultEnum.getMsg(), null);
    }

    /**
     * 失败响应，不需要返回数据
     */
    public static <T> Result<T> fail() {
        return new Result<>(ResultEnum.FAILURE.getCode(), ResultEnum.FAILURE.getMsg(), null);
    }

    /**
     * 失败响应，不需要返回数据
     */
    public static <T> Result<T> fail(ResultEnum resultEnum) {
        return new Result<>(resultEnum.getCode(), resultEnum.getMsg(), null);
    }
}
