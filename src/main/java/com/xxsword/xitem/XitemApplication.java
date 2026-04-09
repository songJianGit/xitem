package com.xxsword.xitem;

import com.gitee.sunchenbin.mybatis.actable.manager.handler.StartUpHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
@ComponentScan({"com.gitee.sunchenbin.mybatis.actable.manager.*", "com.xxsword.xitem.admin.*"})
public class XitemApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(XitemApplication.class, args);

        // ===acTable===begin
        StartUpHandler bean = run.getBean(StartUpHandler.class, args);// 容器中获取actable的核心处理类
        bean.startHandler();// 手动执行actable的建表方法
        // ===acTable===end
    }

}
