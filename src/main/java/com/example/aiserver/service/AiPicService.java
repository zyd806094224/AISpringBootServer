package com.example.aiserver.service;

import com.example.aiserver.entity.AiPic;

public interface AiPicService {

    boolean save(AiPic aiPic);

    AiPic queryByMd5(String md5);
}
