package com.buqiu.blog.config.sercurity;

import com.buqiu.blog.domain.result.Result;
import com.buqiu.blog.domain.vo.LoginVO;
import com.buqiu.blog.service.IRedisService;
import com.buqiu.blog.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 类描述
 * </p>
 *
 * @author: 不秋
 * @since: 2024-08-04 20:40:19
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Handler {
    private final JwtUtils jwtUtil;
    private final IRedisService<Object> redisService;
    private final ObjectMapper objectMapper;

    @Value("${jwt.expire-time:24}")
    private long expireTime;
    public void JwtAuthenticationSuccessHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String username = authentication.getName();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();

        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("Authorities", userDetails.getAuthorities());
        String token = jwtUtil.createToken(map);

        // 将 token 存入 Redis
        redisService.setValue(new String[]{"user", username,"jwt"}, token,expireTime, TimeUnit.HOURS);

        // 将用户信息存入 Redis
        redisService.setValue(new String[]{"user", username,"userDetails"}, userDetails, expireTime, TimeUnit.HOURS);

        //转换为VO对象
        LoginVO loginVO = new LoginVO(username,token, LocalDateTime.now().plusHours(expireTime));
        System.out.println(loginVO);

        // 返回 token 给客户端
        renderString(response, objectMapper.writeValueAsString(Result.success(loginVO)));
    }
    public static void renderString(HttpServletResponse response, String string) {
        try
        {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            writer.println(string);
            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
            log.error("无法转换",e);
        }
    }
}
