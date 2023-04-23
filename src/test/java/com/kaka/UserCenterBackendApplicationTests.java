package com.kaka;

import com.kaka.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserCenterBackendApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Test
    void contextLoads() {
        userMapper.selectList(null);
    }

}
