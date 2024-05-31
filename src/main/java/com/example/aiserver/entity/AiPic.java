package com.example.aiserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiPic {
    private Integer id;
    private String md5;
    private String pic_url;

}
