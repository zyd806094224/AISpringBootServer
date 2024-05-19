package com.example.aiserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.aiserver.entity.UserLog;
import com.example.aiserver.mapper.UserLogMapper;
import com.example.aiserver.service.UserLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserLogServiceImpl extends ServiceImpl<UserLogMapper, UserLog> implements UserLogService {
    @Resource
    private UserLogMapper userLogMapper;

    @Override
    public boolean save(UserLog userLog) {
       return userLogMapper.insert(userLog) > 0;
    }
}
