package com.buqiu.blog.controller;

import com.buqiu.blog.domain.dto.UserRegisterDTO;
import com.buqiu.blog.domain.result.Result;
import com.buqiu.blog.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 不秋
 * @since 2024-07-31 15:27:00
 */
@RestController
@Tag(name = "用户相关接口")
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<Void> register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        return userService.Register(userRegisterDTO);
    }
}
