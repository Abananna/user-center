package com.kaka.controller;/**
 * @author kkstart
 * @create 2023-04-24 14:08
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kaka.model.pojo.User;
import com.kaka.model.request.UserLoginRequest;
import com.kaka.model.request.UserRegisterRequest;
import com.kaka.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.kaka.constant.UserConstant.ADMIN_ROLE;
import static com.kaka.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author kaka
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAllBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        return userService.userRegister(userAccount, userPassword, checkPassword);
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAllBlank(userAccount, userPassword)) {
            return null;
        }
        return userService.userLogin(userAccount, userPassword, request);
    }

    @GetMapping("/search")
    public List<User> userList(String username, HttpServletRequest request) {
        //仅管理员可查询
        if (!isAdmin(request)) {
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        //脱敏
        return userList.stream().map(user->{
            user.setUserPassword(null);
            return userService.getSafeUser(user);
        }).collect(Collectors.toList());

    }

    @DeleteMapping("{id}")
    public boolean deleteUser(@PathVariable Long id,  HttpServletRequest request) {
        //仅管理员可删除
        if (!isAdmin(request)) {
            return false;
        }
        if (id <= 0) {
            return false;
        }
        return userService.removeById(id);
    }

    /**
     * 判定是否为管理员
     */
    private boolean isAdmin(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute(USER_LOGIN_STATE);
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }
}
