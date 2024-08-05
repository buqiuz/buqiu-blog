package com.buqiu.blog.service;

import com.buqiu.blog.domain.dto.EmailDTO;
import com.buqiu.blog.domain.result.Result;

/**
 * <p>
 * 这是用来提供公共接口 服务类
 * </p>
 *
 * @author: 不秋
 * @since: 2024-08-01 11:39:41
 */
public interface IPublicService {
    /**
    * @author: 不秋
    * @since: 2024-08-01 11:42:35
    * @description: 发送注册邮件
    * @params: email:邮箱地址 code:验证码
    * @return:
    */
    Result<Void> sendEmail(EmailDTO emailDTO);
}
