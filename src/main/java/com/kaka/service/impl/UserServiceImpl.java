package com.kaka.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaka.model.pojo.User;
import com.kaka.service.UserService;
import com.kaka.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* 用户服务实现类
 * @author qiaziwei
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    UserMapper userMapper;
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
        final String SALT = "kaka";
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
}




