package com.example.aiserver.service;


import com.example.aiserver.entity.User;

public interface UserService{

    boolean save(User user);

    User queryByName(String username);
}
