package com.xxsword.xitem.admin.service.timer.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.timer.entity.Trace;
import com.xxsword.xitem.admin.mapper.timer.TraceMapper;
import com.xxsword.xitem.admin.service.timer.TraceService;
import com.xxsword.xitem.admin.utils.DateUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Service
public class TraceServiceImpl extends ServiceImpl<TraceMapper, Trace> implements TraceService {

    @Override
    public void delTrace() {
        Long date30 = DateUtil.getDay(DateTime.now(), -30).getMillis();
        LambdaQueryWrapper<Trace> query = Wrappers.lambdaQuery();
        query.le(Trace::getTimeStamp, date30);
        remove(query);
    }
}
