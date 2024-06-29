package com.xxsword.xitem.admin.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * @param key
     * @param value
     * @param timeout 单位分钟
     */
    public void setKeyValue(String key, String value, Integer timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MINUTES);
    }

    public String getValueByKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
