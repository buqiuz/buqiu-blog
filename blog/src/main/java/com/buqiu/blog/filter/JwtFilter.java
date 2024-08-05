package com.buqiu.blog.filter;

import com.buqiu.blog.config.sercurity.MyUserDetails;
import com.buqiu.blog.service.IRedisService;
import com.buqiu.blog.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

/**
 * <p>
 * 类描述
 * </p>
 *
 * @author: 不秋
 * @since: 2024-08-05 10:00:36
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper;
    private final IRedisService<Object> redisService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt=request.getHeader("Authorization");
        if (jwt!=null) {
            Map<String, Object> loginUser = jwtUtils.parseToken(jwt);
            String jwtRedis =redisService.getValue(new String[]{"user", loginUser.get("username").toString(),"jwt"}).toString();
            // 验证token
            if (jwtUtils.validateToken(jwt)) {
                // 验证redis
                if (jwt.equals(jwtRedis)) {
                    // 获取用户信息
                    MyUserDetails userDetails =  objectMapper.convertValue(redisService.getValue(new String[]{"user", loginUser.get("username").toString(), "userDetails"}), MyUserDetails.class);
                    // 创建认证对象
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUser, null, userDetails.getAuthorities());
                    // 保存认证详细信息
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // 验证通过，设置上下文中
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        // 放行
        filterChain.doFilter(request, response);
    }
}
