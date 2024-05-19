package com.example.aiserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.aiserver.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User queryByName(String username);
}
