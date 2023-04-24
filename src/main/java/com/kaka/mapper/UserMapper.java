package com.kaka.mapper;

import com.kaka.model.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author qiaziwei
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2023-04-24 09:35:19
* @Entity com.kaka.model.pojo.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




