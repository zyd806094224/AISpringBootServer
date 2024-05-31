package com.example.aiserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.aiserver.entity.AiPic;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AiPicMapper extends BaseMapper<AiPic> {

    AiPic queryByMd5(String md5);
}
