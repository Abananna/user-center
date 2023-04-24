package com.kaka.model.request;/**
 * @author kkstart
 * @create 2023-04-24 14:16
 */

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author kaka
 */

@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = 6430539691155161220L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;

}
