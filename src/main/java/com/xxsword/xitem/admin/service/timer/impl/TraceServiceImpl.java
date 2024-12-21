package com.xxsword.xitem.admin.service.timer.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.config.BigDataTableNameHandler;
import com.xxsword.xitem.admin.domain.timer.entity.Trace;
import com.xxsword.xitem.admin.mapper.timer.TraceMapper;
import com.xxsword.xitem.admin.service.timer.TraceService;
import com.xxsword.xitem.admin.utils.DateUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraceServiceImpl extends ServiceImpl<TraceMapper, Trace> implements TraceService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void delTrace() {
        long date90 = DateUtil.getDay(DateTime.now(), -90).getMillis();
        List<String> tables = BigDataTableNameHandler.listTableNames("t_time_trace");
        for (String tabName : tables) {
            jdbcTemplate.update("DELETE FROM " + tabName + " WHERE (time_stamp <= " + date90 + ")");
        }
    }
}
