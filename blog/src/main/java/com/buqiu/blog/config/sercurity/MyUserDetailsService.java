package com.buqiu.blog.config.sercurity;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.buqiu.blog.domain.entity.User;
import com.buqiu.blog.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * <p>
 * 类描述
 * </p>
 *
 * @author: 不秋
 * @since: 2024-08-04 19:46:23
 */
@RequiredArgsConstructor
@Component
public class MyUserDetailsService implements UserDetailsService {
    private final IUserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        //没有查询到就抛出异常
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        //查询权限 TODO

        //封装成UserDetails返回
        return new MyUserDetails(user,null);
    }
}
