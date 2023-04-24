package com.kaka.mapper;/**
 * @author kkstart
 * @create 2023-04-24 16:54
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kaka.model.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

/**
 * @Author:Kaka
 * @Description:
 * @Date: 2023/4/24 16:54
 */
@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    void testLogin() {
        String userAccount = "Abananna";
        String userPassword = "123456789";
        String encryptPassword = DigestUtils.md5DigestAsHex(("kaka" + userPassword).getBytes());
        User user = userMapper.selectByUsernameAndPassword(userAccount, encryptPassword);
        System.out.println(user);
    }
}
