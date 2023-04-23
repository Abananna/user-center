package com.kaka.mapper;/**
 * @author kkstart
 * @create 2023-04-23 22:16
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kaka.model.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:Kaka
 * @Description:
 * @Date: 2023/4/23 22:16
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
