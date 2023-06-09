package com.kaka.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author:Kaka
 * @Description:
 * @Date: 2023/4/24 14:27
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 6430539691123161220L;

    private String userAccount;
    private String userPassword;
}
