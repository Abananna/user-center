package com.kaka.service;

import com.kaka.model.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户服务
 *
 * @author  kaka
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userAccount 用户账号
     * @param userPassword 密码
     * @param checkPassword 确认密码
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);


}
