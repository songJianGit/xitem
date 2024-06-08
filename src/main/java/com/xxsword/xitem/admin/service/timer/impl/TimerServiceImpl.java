package com.xxsword.xitem.admin.service.timer.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.constant.Device;
import com.xxsword.xitem.admin.constant.TimerType;
import com.xxsword.xitem.admin.domain.timer.entity.Period;
import com.xxsword.xitem.admin.domain.timer.entity.Timer;
import com.xxsword.xitem.admin.domain.timer.entity.Trace;
import com.xxsword.xitem.admin.mapper.timer.TimerMapper;
import com.xxsword.xitem.admin.service.timer.PeriodService;
import com.xxsword.xitem.admin.service.timer.TimerService;
import com.xxsword.xitem.admin.service.timer.TraceService;
import com.xxsword.xitem.admin.utils.DateUtil;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TimerServiceImpl extends ServiceImpl<TimerMapper, Timer> implements TimerService {

    @Autowired
    private PeriodService periodService;

    @Autowired
    private TraceService traceService;

    @Override
    @Transactional
    public Trace trace(String userId, String type, String obId, TimerType timerType, String traceId, Device device) {
        Trace trace = saveTrace(obId, timerType, traceId, type, userId, device);
        if (Trace.T_END.equalsIgnoreCase(type)) {
            this.end(userId, trace, obId, timerType, traceId);
        }
        return trace;
    }

    @Override
    public boolean outLineTime(String userId, String obId, TimerType timerType, Integer time, Integer device) {
        List<Trace> traceList = new ArrayList<>();
        String traceId = Utils.getuuid();
        Trace trace = new Trace();
        trace.setObId(obId);
        trace.setObType(timerType.getCode());
        trace.setTimeStamp(Instant.now().minus(time * 1000).getMillis());
        trace.setTraceId(traceId);
        trace.setTraceType(Trace.T_START);
        trace.setUserId(userId);
        trace.setDevice(device);
        traceList.add(trace);

        Trace traceEnd = new Trace(); // 学习跟踪记录
        traceEnd.setObId(obId);
        traceEnd.setObType(timerType.getCode());
        traceEnd.setTimeStamp(Instant.now().getMillis());
        traceEnd.setTraceId(traceId);
        traceEnd.setTraceType(Trace.T_END);
        traceEnd.setUserId(userId);
        traceEnd.setDevice(device);
        traceList.add(traceEnd);

        traceService.saveBatch(traceList);
        Period x = this.end(userId, traceEnd, obId, timerType, traceId);
        return x != null;
    }

    @Override
    public boolean delTrace(String startDate, String endDate) {
        DateTime dateTime1 = DateTime.parse(startDate, DateUtil.sdfA1);
        DateTime dateTime2 = DateTime.parse(endDate, DateUtil.sdfA1);
        LambdaQueryWrapper<Trace> q = Wrappers.lambdaQuery();
        q.ge(Trace::getTimeStamp, dateTime1.getMillis());
        q.le(Trace::getTimeStamp, dateTime2.getMillis());
        return traceService.remove(q);
    }

    /**
     * 计算进度
     */
    private Period end(String userId, Trace trace, String obId, TimerType timerType, String traceId) {
        /*
         * 1.保存学习段信息
         * 2.更新学习进度
         */
        Period period = this.savePart(userId, trace, obId, timerType, traceId);
        this.saveOrUpdateTimer(userId, obId, timerType, period);
        return period;
    }

    private Trace saveTrace(String obId, TimerType timerType, String traceId, String type, String userId, Device device) {
        if (StringUtils.isBlank(traceId) && Trace.T_START.equalsIgnoreCase(type)) {
            traceId = Utils.getuuid();
        }
        Trace trace = new Trace();// 学习跟踪记录
        trace.setObId(obId);
        trace.setObType(timerType.getCode());
        trace.setTimeStamp(Instant.now().getMillis());
        trace.setTraceId(traceId);
        trace.setUserId(userId);
        trace.setDevice(device.getCode());
        trace.setTraceType(type);
        traceService.save(trace);
        return trace;
    }

    /**
     * 保存学习段信息
     */
    private Period savePart(String userId, Trace trace, String obId, TimerType timerType, String traceId) {
        LambdaQueryWrapper<Trace> q = Wrappers.lambdaQuery();
        q.eq(Trace::getTraceType, Trace.T_START);
        q.eq(Trace::getTraceId, traceId);
        q.eq(Trace::getObId, obId);
        q.eq(Trace::getObType, timerType.getCode());
        q.eq(Trace::getUserId, userId);
        q.orderByDesc(Trace::getTimeStamp);
        Trace sTrace = traceService.getOne(q);
        if (sTrace == null) {
            log.warn("get start trace warn=>userId:{},obId:{},type:{},traceId:{}", userId, obId, timerType.getType(), traceId);
            return null;
        }
        Long start = sTrace.getTimeStamp();// 开始时间戳
        Long end = trace.getTimeStamp();// 结束时间戳
        List<Trace> traceEnd = endList(userId, trace, obId, timerType, traceId);
        if (traceEnd.size() > 0) {// 如果前面有end，则以前面的最后一个end为起点计算
            start = traceEnd.get(0).getTimeStamp();
        }
        Period period = new Period();
        period.setTraceId(traceId);
        period.setObId(obId);
        period.setObType(timerType.getCode());
        period.setUserId(userId);
        period.setStartStamp(start);
        period.setEndStamp(end);
        Long pCost = DateUtil.differSecond(new DateTime(start), new DateTime(end));// 秒
        period.setCost(pCost.intValue());// 时间差（秒）
        periodService.save(period);
        return period;
    }

    /**
     * 查询end
     */
    private List<Trace> endList(String userId, Trace trace, String obId, TimerType timerType, String traceId) {
        LambdaQueryWrapper<Trace> q = Wrappers.lambdaQuery();
        q.eq(Trace::getTraceType, Trace.T_END);
        q.eq(Trace::getObId, obId);
        q.eq(Trace::getObType, timerType.getCode());
        q.eq(Trace::getTraceId, traceId);
        q.eq(Trace::getUserId, userId);
        q.ne(Trace::getId, trace.getId());// 不查这次的end
        q.orderByDesc(Trace::getTimeStamp);
        return traceService.list(q);
    }

    /**
     * 更新进度信息
     */
    private void saveOrUpdateTimer(String userId, String obId, TimerType timerType, Period period) {
        LambdaQueryWrapper<Timer> q = Wrappers.lambdaQuery();
        q.eq(Timer::getObId, obId);
        q.eq(Timer::getObType, timerType.getCode());
        q.eq(Timer::getUserId, userId);
        List<Timer> timerList = list(q);
        Timer timer;
        if (timerList.size() == 1) {// 只有一个，皆大欢喜
            timer = timerList.get(0);
        } else if (timerList.size() == 0) {// 一个没有，就新建
            timer = new Timer();
            timer.setUserId(userId);
            timer.setObId(obId);
            timer.setObType(timerType.getCode());
            timer.setTotalTime(0);
            timer.setStartTime(DateUtil.now());
        } else {
            /*
             * 查出多条就进入修复程序
             * 1.累加所有时间
             * 2.删除多余信息
             */
            timer = timerList.get(0);
            Integer total = 0;
            for (Timer item : timerList) {
                total += item.getTotalTime();
            }
            timer.setTotalTime(total);
            List<String> removeTimerIds = new ArrayList<>();
            for (int i = 1; i < timerList.size(); i++) {
                removeTimerIds.add(timerList.get(i).getId());
            }
            removeBatchByIds(removeTimerIds);
        }
        timer.setLastTime(DateUtil.now());
        Integer time = timer.getTotalTime() + period.getCost(); // 总时长=原时长+本次时长
        timer.setTotalTime(time);
        saveOrUpdate(timer);
    }
}
