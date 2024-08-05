package com.buqiu.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.buqiu.blog.domain.dto.UserRegisterDTO;
import com.buqiu.blog.domain.entity.User;
import com.buqiu.blog.domain.result.Result;

/**
 * <p>
 * 这是一张不秋设计的blog的user表 服务类
 * </p>
 *
 * @author 不秋
 * @since 2024-07-31 15:30:42
 */
public interface IUserService extends IService<User> {

    Result<Void> Register(UserRegisterDTO userRegisterDTO);

}
