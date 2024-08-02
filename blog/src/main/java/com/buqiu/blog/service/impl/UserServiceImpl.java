package com.buqiu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.buqiu.blog.domain.constant.UserConst;
import com.buqiu.blog.domain.dto.UserRegisterDTO;
import com.buqiu.blog.domain.entity.User;
import com.buqiu.blog.domain.enums.ResultEnum;
import com.buqiu.blog.domain.result.Result;
import com.buqiu.blog.mapper.UserMapper;
import com.buqiu.blog.service.IRedisService;
import com.buqiu.blog.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 这是一张不秋设计的blog的user表 服务实现类
 * </p>
 *
 * @author 不秋
 * @since 2024-07-31 15:30:42
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private final IRedisService<Object> redisService;
    private final PasswordEncoder passwordEncoder;

    /**
    * @author: 不秋
    * @since: 2024-07-31 17:28:34
    * @description: 判断用户名是否已存在
    * @params: username
    * @return: ture: 用户名已存在 ;false: 用户名不存在
    */
    public boolean isUsernameExists(String username) {
        return this.count(new LambdaQueryWrapper<User>().eq(User::getUsername, username)) > 0;
    }

    /**
    * @author: 不秋
    * @since: 2024-07-31 17:29:49
    * @description: 判断邮箱是否已被注册
    * @params: email
    * @return: true: 邮箱已被注册 ;false: 邮箱未被注册
    */
    public boolean isEmailExists(String email) {
        return this.count(new LambdaQueryWrapper<User>().eq(User::getEmail, email)) > 0;
    }
    /**
    * @author: 不秋
    * @since: 2024-07-31 17:34:45
    * @description: 判断验证码是否正确,并且为true时删除验证码
    * @params: code
    * @return: true: 验证码正确 ;false: 验证码错误
    */
    public boolean isCodeCorrect(UserRegisterDTO userRegisterDTO) {
        return redisService.getValue(new String[]{"email", "register", userRegisterDTO.getEmail()}).equals(userRegisterDTO.getCode())
                ? redisService.deleteValue(new String[]{"email","register",userRegisterDTO.getEmail()})
                : false;
    }

    /**
    * @author: 不秋
    * @since: 2024-08-02 16:11:35
    * @description: 用户注册
    * @params: 用户传输对象
    * @return: Result
    */
    @Override
    public Result<Void> userRegister(UserRegisterDTO userRegisterDTO) {
        // 判断用户名是否已存在
        if (isUsernameExists(userRegisterDTO.getUsername())){
            return Result.fail(ResultEnum.USERNAME_EXIST);
        }
        // 判断邮箱是否已被注册
        if (isEmailExists(userRegisterDTO.getEmail())){
            return Result.fail(ResultEnum.EMAIL_EXIST);
        }
        // 判断验证码是否正确
        if (!isCodeCorrect(userRegisterDTO)){
            return Result.fail(ResultEnum.EMAIL_CODE_ERROR);
        }
        // 密码加密
        String encodedPassword = passwordEncoder.encode(userRegisterDTO.getPassword());


        User user = User.builder()
                .id(null)
                .username(userRegisterDTO.getUsername())
                .nickname(UserConst.DEFAULT_NICKNAME)
                .password(encodedPassword)
//                .password(userRegisterDTO.getPassword())
                // TODO 用户注册类型待完善
                .avatar(UserConst.DEFAULT_AVATAR)
                .email(userRegisterDTO.getEmail())
                .age(UserConst.DEFAULT_AGE)
                .gender(UserConst.DEFAULT_GENDER)
                .birthday(UserConst.DEFAULT_BIRTHDAY)
                .bio(UserConst.DEFAULT_BIO)
                .status(UserConst.DEFAULT_STATUS)
                .createTime(LocalDateTime.now())

                .createIp(null)
                .createAddress(null)
                .lastLoginTime(null)
                .lastLoginIp(null)
                .lsatLoginAddress(null)
                .lastUpdateTime(LocalDateTime.now())
                .build();
        if (this.save(user)){
            return Result.success();
        }
        return Result.fail(ResultEnum.CREATE_USER_FAILURE);
    }
}
