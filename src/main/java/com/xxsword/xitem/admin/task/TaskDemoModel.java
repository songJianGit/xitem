package com.xxsword.xitem.admin.task;

import com.xxsword.xitem.admin.utils.DateUtil;
import com.xxsword.xitem.admin.utils.UpLoadUtil;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

@Slf4j
@Component
@ConditionalOnProperty(name = "scheduling.enabled", havingValue = "true")
public class TaskDemoModel {

    @Value("${filepath.type}")
    private Integer filepathType;
    @Value("${filepath.path}")
    private String filepathPath;

    /**
     * 演示模式下，清理数据
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void demoModel() {
        log.info("demoModel-begin");
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        long startTime = runtimeMxBean.getStartTime();
        DateTime dateTime = new DateTime(startTime);
        String pp = UpLoadUtil.getProjectPath();
        if (filepathType == 2) {
            pp = filepathPath;
        }
        Utils.deleteDirectory(pp + UpLoadUtil.PATH_INFO, dateTime);
        log.info("demoModel deleteDirectory:{},{}", pp + UpLoadUtil.PATH_INFO, dateTime.toString(DateUtil.sdfA1));
        log.info("demoModel-end");
    }

}
