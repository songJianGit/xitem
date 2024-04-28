package com.xxsword.xitem.admin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@Component
@Slf4j
public class SystemConfig implements CommandLineRunner {
    private static Properties props;

    @Value("${spring.profiles.active}")
    private String appconfig;

    /**
     * 获取属性
     *
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        return props == null ? null : props.getProperty(key);
    }

    /**
     * 获取属性
     *
     * @param key          属性key
     * @param defaultValue 属性value
     * @return
     */
    public static String getProperty(String key, String defaultValue) {
        return props == null ? null : props.getProperty(key, defaultValue);
    }

    /**
     * 获取properyies属性
     *
     * @return
     */
    public static Properties getProperties() {
        return props;
    }

    @Override
    public void run(String... strings) {
        try {
            log.info("spring.profiles.active {}", appconfig);
            Resource resource = new ClassPathResource("/application-" + appconfig + ".properties");
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
