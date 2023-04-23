package com.kaka.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaka.mapper.UserMapper;
import com.kaka.model.pojo.User;
import com.kaka.service.UserService;
import org.springframework.stereotype.Service;

/**
* @author qiaziwei
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-04-23 22:26:19
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}




