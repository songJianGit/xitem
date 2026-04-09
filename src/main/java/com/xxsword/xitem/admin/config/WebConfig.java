package com.xxsword.xitem.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${filepath.path}")
    private String filepathPath;
    @Value("${filepath.type}")
    private Integer filepathType;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        if (filepathType.equals(1)) {
            registry.addResourceHandler("/fileinfo/**").addResourceLocations("file:fileinfo/");
        } else {
            registry.addResourceHandler("/fileinfo/**").addResourceLocations("file:" + filepathPath + "/fileinfo/");
        }
    }

}