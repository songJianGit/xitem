package com.xxsword.xitem.admin.config;

import com.xxsword.xitem.admin.tag.FunTag;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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
