package com.seven.service;

import com.seven.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author huo
 * @since 2019-11-14
 */
public interface IUserService extends IService<User> {

    User findByPhone(String phone);


}
