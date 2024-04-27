package com.xxsowrd.xitem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.gitee.sunchenbin.mybatis.actable.dao.*")
@ComponentScan(basePackages = {"com.gitee.sunchenbin.mybatis.actable.manager.*"})
public class XitemApplication {

    public static void main(String[] args) {
        SpringApplication.run(XitemApplication.class, args);
    }

}
