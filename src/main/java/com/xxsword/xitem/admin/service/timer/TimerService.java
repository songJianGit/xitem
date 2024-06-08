package com.xxsword.xitem.admin.service.timer;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xxsword.xitem.admin.constant.Device;
import com.xxsword.xitem.admin.constant.TimerType;
import com.xxsword.xitem.admin.domain.timer.entity.Timer;
import com.xxsword.xitem.admin.domain.timer.entity.Trace;

public interface TimerService extends IService<Timer> {
    /**
     * @param userId
     * @param type      类型 start trace end
     * @param obId      业务id
     * @param timerType 业务type
     * @param traceId
     * @param device    设备类型
     * @return
     */
    Trace trace(String userId, String type, String obId, TimerType timerType, String traceId, Device device);

    /**
     * 离线上报
     *
     * @param userId
     * @param obId
     * @param timerType
     * @param time      所需增加时长，单位：秒
     * @param device    设备类型
     * @return
     */
    boolean outLineTime(String userId, String obId, TimerType timerType, Integer time, Integer device);

    /**
     * 删除时间段内,类型为trace的跟踪信息
     *
     * @param startDate
     * @param endDate
     * @return
     */
    boolean delTrace(String startDate, String endDate);
}
