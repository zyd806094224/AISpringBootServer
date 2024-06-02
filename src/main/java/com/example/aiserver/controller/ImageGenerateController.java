package com.example.aiserver.controller;

import com.example.aiserver.entity.AiPic;
import com.example.aiserver.service.AiPicService;
import com.example.aiserver.utils.GenerateAIImgUtil;
import com.example.aiserver.utils.MD5Util;
import com.example.aiserver.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 图片生成Controller
 */
@Slf4j
@RestController
@RequestMapping("/ImageGenerate")
public class ImageGenerateController {

    @Resource
    private AiPicService aiPicService;

    @RequestMapping(value = "/generateAIImg", method = RequestMethod.POST)
    public Result generateAIImg(@RequestParam String prompt, @RequestParam String username, @RequestParam Long key) {
        if (username == null || key == null) return Result.fail("用户校验异常");
        if (!Objects.equals(UserController.loginUserKey.get(username), key)) {
            return Result.fail("用户在其他地方登录！！！");
        }
        String picPromptMd5 = MD5Util.md5(prompt);
        AiPic aiPic = aiPicService.queryByMd5(picPromptMd5);
        if (aiPic != null) {
            return Result.ok("成功", aiPic.getPicUrl());
        } else {
            String picUrl = GenerateAIImgUtil.generateAIImg(prompt);
            if (!"".equals(picUrl)) {
                AiPic newAiPic = new AiPic();
                newAiPic.setMd5(picPromptMd5);
                newAiPic.setPicUrl(picUrl);
                newAiPic.setCreateTime(LocalDateTime.now());
                aiPicService.save(newAiPic);
                return Result.ok("成功", picUrl);
            }
        }
        return Result.fail("图片生成失败了");
    }
}
