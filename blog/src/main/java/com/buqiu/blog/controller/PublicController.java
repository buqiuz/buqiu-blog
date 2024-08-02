package com.buqiu.blog.controller;

import com.buqiu.blog.domain.result.Result;
import com.buqiu.blog.service.IPublicService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 类描述
 * </p>
 *
 * @author: 不秋
 * @since: 2024-08-01 11:38:06
 */
@RestController
@Tag(name = "公共接口")
@RequiredArgsConstructor
@RequestMapping("/public")
public class PublicController {
    private final IPublicService publicService;

    /**
    * @author: 不秋
    * @since: 2024-08-01 14:15:16
    * @description: 发送不同类型的邮件
    * @params: email:邮箱地址 type:邮件类型
    * @return: 返回成功与否
    */
    @PostMapping("/sendEmail")
    public Result<Void> sendEmail(@RequestParam("email") String email,@RequestParam("type") String type) {
        return publicService.sendEmail(email, type);
    }
}
