package com.kaka.service;
import java.util.Date;

import com.kaka.mapper.UserMapper;
import com.kaka.model.pojo.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author kkstart
 * @create 2023-04-24 09:42
 */
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void testAddUser() {
        User user = new User();
        user.setUsername("testKaka");
        user.setUserAccount("123");
        user.setAvatarUrl("https://images.zsxq.com/FhehgFrmFN298XCGeSv6OSw6LNGL?e=1685548799&token=kIxbL07-8jAj8w1n4s9zv64FuZZNEATmlU_Vm6zD:FGTkS4xOcwweAHswQIWH5UcCV3w=");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("123");
        user.setUserStatus(0);
        user.setPlanetCode("");
        user.setTags("");
        boolean res = userService.save(user);
        System.out.println("user.getId() = " + user.getId());
        Assertions.assertTrue(res);

    }

    @Test
    void userRegister() {
        String userAccount = "";
        String userPassword = "kaka123456";
        String checkPassword = "kaka123456";
//        校验用户账户和密码是否合法
//        1. 非空
        long res = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,res);
//        2. 账户长度不小于 6 位
        userAccount = "abcd";
        res = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,res);
//        3. 密码长度不小于 8 位
        userPassword = "1234";
        res = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,res);
//        4. 账户不包含特殊字符
        userPassword = "kaka123456";
        userAccount = "ascd adsd";
        res = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,res);
        // 5.两次密码不同
        userAccount = "kakagreat";
        checkPassword = "kaka12345";
        res = userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1,res);
    }

}