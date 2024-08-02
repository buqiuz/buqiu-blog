package com.buqiu.blog.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * <p>
 * RabbitMQ配置
 * </p>
 *
 * @author: 不秋
 * @since: 2024-08-01 15:17:01
 */
@Configuration
public class RabbitMQConfig {
    /**
     * 消息JSON序列化
     * 生产者和消费者都能使用这个进行序列化转换
     */
    @Bean
    public MessageConverter jacksonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}