package com.buqiu.blog.service.impl;

import com.buqiu.blog.domain.constant.RedisConst;
import com.buqiu.blog.domain.dto.EmailDTO;
import com.buqiu.blog.domain.enums.ResultEnum;
import com.buqiu.blog.domain.result.Result;
import com.buqiu.blog.service.IPublicService;
import com.buqiu.blog.service.IRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 类描述
 * </p>
 *
 * @author: 不秋
 * @since: 2024-08-01 11:45:23
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PublicServiceImpl implements IPublicService {

    private final RabbitTemplate rabbitTemplate;
    private final IRedisService<String> redisService;
    private final String exchange = "mail.direct";
    private final String routingKey = "sendmail.success1";

    /**
    * @author: 不秋
    * @since: 2024-08-01 13:41:23
    * @description: 发送邮件
    * @params: email:邮箱地址 type:发送邮件类型(注册，更改密码，)
    * @return: 返回Result
    */
    @Override
    public Result<Void> sendEmail(EmailDTO emailDTO) {
        boolean success = false;
        String email = emailDTO.getEmail();
        String type = emailDTO.getType();
        // 加锁，防止同一时间被同一人调用多次
        synchronized (email.intern()) {
            try {
                // 生成验证码
                String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
                // 保存到redis，设置过期时间为5分钟
                // redisCache.setCacheObject(RedisConst.VERIFY_CODE + type + RedisConst.SEPARATOR + email, code, RedisConst.VERIFY_CODE_EXPIRATION, TimeUnit.MINUTES);
                redisService.setValue(new String[]{RedisConst.VERIFY_CODE , RedisConst.TYPE_REGISTER , email}, code, RedisConst.CODE_EXPIRE_TIME, TimeUnit.MINUTES);
                // 发送邮件
                Map<String, Object> msg = Map.of("email", email, "code", code, "type", type);
                rabbitTemplate.convertAndSend(exchange, routingKey, msg);

                success = true;
            } catch (Exception e) {
                log.error("发送邮件时出现异常: ", e);
            }
        }

        if (success) {
            return Result.success(ResultEnum.SEND_EMAIL_SUCCESS);
        } else {
            return Result.fail(ResultEnum.SEND_EMAIL_FAILURE);
        }
    }
}
