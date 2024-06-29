package com.xxsword.xitem.admin.task;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xxsword.xitem.admin.domain.system.entity.Dict;
import com.xxsword.xitem.admin.service.system.DictService;
import com.xxsword.xitem.admin.utils.DateUtil;
import com.xxsword.xitem.admin.utils.UpLoadUtil;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

@Slf4j
@Component
@ConditionalOnProperty(name = "scheduling.enabled", havingValue = "true")
public class TaskDemoModel {

    @Autowired
    private DictService dictService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
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
        LambdaQueryWrapper<Dict> query = Wrappers.lambdaQuery();
        query.eq(Dict::getStatus, 0);
        query.eq(Dict::getType, "demoModel");
        query.le(Dict::getCreateDate, "2024-06-29 00:00:00");
        query.le(Dict::getLastUpdate, "2024-06-29 00:00:00");
        List<Dict> dictList = dictService.list(query);
        for (Dict dict : dictList) {
            JSONObject model = JSONObject.parseObject(dict.getVal());
            Integer type = model.getInteger("type");
            String sql = null;
            if (type == 1) {
                String tab = model.getString("table_name");
                String create_date = model.getString("create_date");
                sql = "DELETE FROM " + tab + " WHERE create_date >= '" + create_date + "'";
            }
            if (type == 2) {
                String tab = model.getString("table_name");
                String[] ids = model.getString("ids").split(",");
                sql = "DELETE FROM " + tab + " WHERE id NOT IN ('" + String.join("','", ids) + "')";
            }
            if (type == 3) {
                String tab = model.getString("table_name");
                String timestamp = model.getString("timestamp");
                sql = "DELETE FROM " + tab + " WHERE start_stamp >= '" + timestamp + "'";
            }
            if (type == 4) {
                String tab = model.getString("table_name");
                String start_time = model.getString("start_time");
                sql = "DELETE FROM " + tab + " WHERE start_time >= '" + start_time + "'";
            }
            if (type == 5) {
                String tab = model.getString("table_name");
                String timestamp = model.getString("timestamp");
                sql = "DELETE FROM " + tab + " WHERE time_stamp >= '" + timestamp + "'";
            }
            log.info("demoModel sql:{}", sql);
            jdbcTemplate.update(sql);
        }

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
