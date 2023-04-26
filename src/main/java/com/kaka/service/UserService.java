package com.kaka.service;

import com.kaka.model.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 *
 * @author kaka
 */
public interface UserService extends IService<User> {



    /**
     * 用户注册
     *
     * @param userAccount   用户账号
     * @param userPassword  密码
     * @param checkPassword 确认密码
     * @param planetCode 星球编号
     * @return 注册用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);

    /**
     * 用户登录
     *
     * @param userAccount  用户账号
     * @param userPassword 用户密码
     * @return 返回脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);


    User getSafeUser(User originuser);

    /**
     * 用户注销
     * @param request 用户发送过来的请求对象
     * @return 1
     */
    int userLogout(HttpServletRequest request);
}
