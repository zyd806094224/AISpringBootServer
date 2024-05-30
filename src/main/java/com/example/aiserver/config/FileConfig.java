package com.example.aiserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 定位各种文件地址
 */
@Configuration
public class FileConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //图片生成地址
        registry.addResourceHandler("/images/ai_generate/**").addResourceLocations(
                "file:" + System.getProperty("user.dir")
                        + System.getProperty("file.separator") + "images"
                        + System.getProperty("file.separator") + "ai_generate"
                        + System.getProperty("file.separator")
        );
    }
}