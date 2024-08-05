package com.buqiu.blog.config.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;

/**
 * <p>
 * 类描述
 * </p>
 *
 * @author: 不秋
 * @since: 2024-08-04 22:57:26
 */
@Configuration
public class JacksonConfig {
//    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new CustomLocalDateTimeSerializer());//添加一个自定义的序列化器
        mapper.registerModule(new JavaTimeModule());//将JavaTimeModule注册到ObjectMapper中
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);//禁止将日期时间转换为时间戳
        return mapper;
    }
}
