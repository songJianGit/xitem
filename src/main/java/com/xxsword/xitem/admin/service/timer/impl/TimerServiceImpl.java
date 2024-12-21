package com.xxsword.xitem.admin.service.timer.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.config.ThreadLocalContext;
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
import lombok.extern.slf4j.Slf4j;
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
    public Period trace(String userId, String obId, TimerType timerType, String periodId, Device device) {
        Period period = upPeriod(userId, obId, timerType, periodId);
        if (period == null) {
            log.warn("trace warn period is null");
            return null;
        } else {
            this.saveOrUpdateTimer(userId, obId, timerType, period);
            this.saveTrace(period.getId(), device, userId);
        }
        return period;
    }

    @Override
    public void outLineTime(String userId, String obId, TimerType timerType, Integer time, Integer device) {
        Instant instant = Instant.now();
        Period period = new Period();
        period.setObId(obId);
        period.setObType(timerType.getCode());
        period.setUserId(userId);
        period.setStartStamp(instant.getMillis());
        period.setEndStamp(instant.minus(time * 1000).getMillis());
        Long cost = DateUtil.differSecond(new DateTime(period.getStartStamp()), new DateTime(period.getEndStamp()));
        period.setCost(cost.intValue());
        periodService.save(period);
        this.saveOrUpdateTimer(userId, obId, timerType, period);
    }

    @Override
    public List<Timer> getTimer(String obId, TimerType timerType, String userId) {
        LambdaQueryWrapper<Timer> q = Wrappers.lambdaQuery();
        q.eq(Timer::getObId, obId);
        q.eq(Timer::getObType, timerType.getCode());
        q.eq(Timer::getUserId, userId);
        return list(q);
    }

    /**
     * 计算进度
     */
    private Period upPeriod(String userId, String obId, TimerType timerType, String periodId) {
        long now = Instant.now().getMillis();
        Period period;
        if (StringUtils.isBlank(periodId)) {
            period = new Period();
            period.setObId(obId);
            period.setObType(timerType.getCode());
            period.setUserId(userId);
            period.setStartStamp(now);
            period.setEndStamp(now);
            period.setCost(0);
            period.setCostItem(0);
            periodService.save(period);
        } else {
            period = periodService.getById(periodId);
            if (period == null) {
                log.warn("trace warn periodId:{}", periodId);
                return null;
            }
            Long cost = DateUtil.differSecond(new DateTime(period.getStartStamp()), new DateTime(now));
            long costItem = cost - period.getCost();
            Period periodUp = new Period();
            periodUp.setId(period.getId());
            periodUp.setEndStamp(now);
            periodUp.setCost(cost.intValue());
            periodUp.setCostItem((int) costItem);
            periodService.updateById(periodUp);

            period.setCost(periodUp.getCost());
            period.setCostItem(periodUp.getCostItem());
            period.setEndStamp(now);
        }
        return period;
    }

    /**
     * 流水记录
     *
     * @param periodId 段落id
     * @param device   设备信息
     * @param userId   用户id
     */
    private void saveTrace(String periodId, Device device, String userId) {
        Trace trace = new Trace();// 学习跟踪记录
        trace.setTimeStamp(Instant.now().getMillis());
        trace.setPeriodId(periodId);
        trace.setDevice(device.getCode());
        ThreadLocalContext.setBusinessId(userId);
        traceService.save(trace);
    }

    /**
     * 更新进度信息
     */
    private void saveOrUpdateTimer(String userId, String obId, TimerType timerType, Period period) {

        List<Timer> timerList = getTimer(obId, timerType, userId);
        Timer timer;
        boolean saveFlag = false;
        if (timerList.size() == 1) {// 只有一个，皆大欢喜
            timer = timerList.get(0);
        } else if (timerList.size() == 0) {// 一个没有，就新建
            timer = new Timer();
            timer.setUserId(userId);
            timer.setObId(obId);
            timer.setObType(timerType.getCode());
            timer.setTotalTime(0);
            timer.setStartTime(DateUtil.now());
            saveFlag = true;
        } else {
            /*
             * 查出多条就进入修复程序
             * 1.累加所有时间
             * 2.删除多余信息
             */
            timer = timerList.get(0);
            int total = timerList.stream().mapToInt(Timer::getTotalTime).sum();
            timer.setTotalTime(total);
            List<String> removeTimerIds = new ArrayList<>();
            for (int i = 1; i < timerList.size(); i++) {
                removeTimerIds.add(timerList.get(i).getId());
            }
            removeBatchByIds(removeTimerIds);
        }
        timer.setLastTime(DateUtil.now());
        Integer time = timer.getTotalTime() + period.getCostItem();
        timer.setTotalTime(time);
        if (saveFlag) {
            save(timer);
        } else {
            Timer timerUp = new Timer();
            timerUp.setId(timer.getId());
            timerUp.setLastTime(timer.getLastTime());
            timerUp.setTotalTime(timer.getTotalTime());
            updateById(timerUp);
        }
    }
}
