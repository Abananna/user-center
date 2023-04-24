package com.kaka.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaka.model.pojo.User;
import com.kaka.service.UserService;
import com.kaka.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kaka.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务实现类
 *
 * @author qiaziwei
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    UserMapper userMapper;

    /**
     * 盐值 混淆密码
     */
    private static final String SALT = "kaka";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        //校验为空
        if (StringUtils.isAllBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }
        //校验账户长度
        if (userAccount.length() < 6) {
            return -1;
        }
        //校验密码长度
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            return -1;
        }
        //账户不能包含特殊字符
        String validPattern = "[`~!@#$^&*()=|{}':;',\\\\[\\\\].<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？ ]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }
        //密码和验证密码相同
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }
        //账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }

        // 2. 对密码进行加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        //向数据库添加数据
        boolean saveRes = this.save(user);
        if (!saveRes) {
            return -1;
        }
        return user.getId();

    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        //校验为空
        if (StringUtils.isAllBlank(userAccount, userPassword)) {
            return null;
        }
        //校验账户长度
        if (userAccount.length() < 6) {
            return null;
        }
        //校验密码长度
        if (userPassword.length() < 8) {
            return null;
        }
        //账户不能包含特殊字符
        String validPattern = "[`~!@#$^&*()=|{}':;',\\\\[\\\\].<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？ ]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return null;
        }

        // 2. 查询用户是否存在
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        User user = userMapper.selectByUsernameAndPassword(userAccount,encryptPassword);
        if (user == null) {
            log.info("user logging failed , userAccount can't match userPassword");
            return null;
        }

        // 3. 用户信息脱敏
        User safeUser = getSafeUser(user);

        // 4. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safeUser);

        return safeUser;
    }

    /**
     * 用户脱敏
     * @param originuser
     * @return
     */
    @Override
    public User getSafeUser(User originuser) {
        User safeUser = new User();
        safeUser.setId(originuser.getId());
        safeUser.setUsername(originuser.getUsername());
        safeUser.setUserAccount(originuser.getUserAccount());
        safeUser.setAvatarUrl(originuser.getAvatarUrl());
        safeUser.setGender(originuser.getGender());
        safeUser.setPhone(originuser.getPhone());
        safeUser.setEmail(originuser.getEmail());
        safeUser.setUserRole(originuser.getUserRole());
        safeUser.setUserStatus(originuser.getUserStatus());
        safeUser.setCreateTime(originuser.getCreateTime());
        safeUser.setUserRole(0);
        return safeUser;
    }
}




