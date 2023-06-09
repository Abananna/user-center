package com.kaka.model.request;

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
    private String planetCode;

}
