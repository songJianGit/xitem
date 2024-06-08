package com.xxsword.xitem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan({"com.gitee.sunchenbin.mybatis.actable.dao.*"})
@ComponentScan({"com.gitee.sunchenbin.mybatis.actable.manager.*", "com.xxsword.xitem.admin.*", "com.xxsword.xitem.pc"})
public class XitemApplication {

    public static void main(String[] args) {
        SpringApplication.run(XitemApplication.class, args);
    }

}
