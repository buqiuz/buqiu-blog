package com.buqiu.blog.listener;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
@RabbitListener(
        bindings = {
                @QueueBinding(
                        value = @Queue(name = "mail.queue1", durable = "true"),
                        exchange = @Exchange(name = "mail.direct", type = ExchangeTypes.DIRECT),
                        key = {"sendmail.success1", "sendmail.success2"}
                ),
                @QueueBinding(
                        value = @Queue(name = "mail.queue2", durable = "true"),
                        exchange = @Exchange(name = "mail.direct", type = ExchangeTypes.DIRECT),
                        key = {"sendmail.success3"}
                )
        },
        concurrency = "1-3"
)
public class EmailQueueListener {

    @Resource
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;

    @RabbitHandler
    public void handlerMapMessage(Map<String, Object> data) {
        String email = (String) data.get("email");
        String code = (String) data.get("code");
        String type = (String) data.get("type");
        SimpleMailMessage message = switch (type) {
            case "register" ->
                    createMessage("欢迎注册buqiu-Blog", "您的邮件验证码为：" + code + "，有效时间5分钟，为了保障您的安全，请勿向他人泄露验证码信息。", email);
            case "forget" ->
                    createMessage("重置密码", "您的邮件验证码为：" + code + "，有效时间5分钟，为了保障您的安全，请勿向他人泄露验证码信息。", email);
            case "resetEmail" ->
                    createMessage("重置电子邮箱", "您的邮件验证码为：" + code + "，有效时间5分钟，为了保障您的安全，请勿向他人泄露验证码信息。", email);
            case "friendLinkApplication" ->
                    createMessage("友链申请通知", "有新的友链申请，请及时前往博客后台进行审核", email);
            case "friendLinkApplicationPass" ->
                    createMessage("友链审核通知", "您的友链已审核通过", email);
            default -> null;
        };
        if (Objects.isNull(message)) return;
        // 发送邮件
        try {
            javaMailSender.send(message);
            log.info("{}：邮件发送成功", email);//参数化日志记录方式,日志记录框架会自动替换占位符{}为对应的参数值,多个参数就使用多次占位符,按顺序传入
        } catch (MailException e) {
            log.error("{}：邮件发送失败", email, e);//将异常对象e作为最后一个参数传入,日志框架会自动打印堆栈信息
        }
    }

    // 邮件信息
    private SimpleMailMessage createMessage(String title, String content, String email) {
        // 创建一个SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();

        // 设置邮件主题
        message.setSubject(title);

        // 设置邮件内容
        message.setText(content);

        // 设置收件人
        message.setTo(email);

        // 设置发件人
        message.setFrom(username);
        return message;
    }

}
