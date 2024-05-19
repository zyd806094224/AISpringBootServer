package com.example.aiserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.aiserver.entity.User;
import com.example.aiserver.mapper.UserMapper;
import com.example.aiserver.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User queryByName(String username) {
        return userMapper.queryByName(username);
    }

    @Override
    public boolean save(User user) {
        return userMapper.insert(user) > 0;
    }
}
