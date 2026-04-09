package com.xxsword.xitem.admin.config;

import com.xxsword.xitem.admin.tag.FunTag;
import freemarker.template.Configuration;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class FreemarkerConfig {
    @Autowired
    private Configuration configuration;
    @Autowired
    private FunTag funTag;

    @PostConstruct
    public void setSharedVariable() {
        configuration.setSharedVariable("funTag", funTag);
    }
}
