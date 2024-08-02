package com.buqiu.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * <p>
 * 类描述
 * </p>
 *
 * @author: 不秋
 * @since: 2024-08-02 01:02:59
 */
@Configuration
public class RedisConfig{
    @Bean
    @Primary
    public <T>RedisTemplate<String, T> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        //创建RedisTemplate对象
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();

        //设置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //创建Json序列化工具,基于Jackson,专为Redis设计,且jackson依赖由spring mvc 提供,即web依赖
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

        //设置key序列化
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());

        //设置value序列化
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);

        return redisTemplate;
    }
}
