package com.xxsword.xitem.admin.task;

import com.xxsword.xitem.admin.service.timer.TraceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "scheduling.enabled", havingValue = "true")
public class TaskDelTrace {

    @Autowired
    private TraceService traceService;

    @Scheduled(cron = "0 0 5 * * ?")
    public void delTrace() {
        log.info("delTrace-begin");
        traceService.delTrace();
        log.info("delTrace-end");
    }

}
