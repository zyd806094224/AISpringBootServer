package com.example.aiserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.aiserver.entity.AiPic;
import com.example.aiserver.mapper.AiPicMapper;
import com.example.aiserver.service.AiPicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AiPicServiceImpl extends ServiceImpl<AiPicMapper,AiPic> implements AiPicService {

    @Resource
    private AiPicMapper aiPicMapper;

    @Override
    public AiPic queryByMd5(String md5) {
        return aiPicMapper.queryByMd5(md5);
    }

    @Override
    public boolean save(AiPic entity) {
        return aiPicMapper.insert(entity) > 0;
    }
}
