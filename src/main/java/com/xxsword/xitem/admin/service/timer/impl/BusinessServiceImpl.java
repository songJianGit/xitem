package com.xxsword.xitem.admin.service.timer.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xxsword.xitem.admin.constant.TimerType;
import com.xxsword.xitem.admin.domain.course.entity.Course;
import com.xxsword.xitem.admin.domain.course.entity.CourseUser;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.domain.timer.entity.Period;
import com.xxsword.xitem.admin.domain.timer.entity.Timer;
import com.xxsword.xitem.admin.service.course.CourseService;
import com.xxsword.xitem.admin.service.course.CourseUserService;
import com.xxsword.xitem.admin.service.system.UserInfoService;
import com.xxsword.xitem.admin.service.timer.BusinessService;
import com.xxsword.xitem.admin.service.timer.TimerService;
import com.xxsword.xitem.admin.utils.DateUtil;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private CourseUserService courseUserService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private TimerService timerService;

    @Override
    public void business(Period period) {
        Integer type_code = period.getObType();
        switch (type_code) {
            case 1:
                this.course(period);
                break;
            case 2:
                log.info("其它业务");
                break;
            default:
                log.warn("business code:{},No corresponding business", type_code);
                break;
        }
    }

    // 课程业务处理
    private void course(Period period) {
        Timer tr = this.getTimer(period, TimerType.COURSE_PLAY);
        if (tr == null) {
            log.warn("course Period => Timer null,period:{}", period.getId());
            return;
        }
        Course course = courseService.getById(period.getObId());
        LambdaQueryWrapper<CourseUser> q = Wrappers.lambdaQuery();
        q.eq(CourseUser::getStatus, 1);
        q.eq(CourseUser::getCreateUserId, period.getUserId());
        q.eq(CourseUser::getCourseId, period.getObId());
        CourseUser userCourse = courseUserService.getOne(q);
        boolean saveFlag = false;
        if (userCourse == null) {
            // 一个没有，就新建
            userCourse = new CourseUser();
            LambdaQueryWrapper<UserInfo> queryUser = Wrappers.lambdaQuery();
            queryUser.select(UserInfo::getId, UserInfo::getOrganId);// 只获取id和机构
            queryUser.eq(UserInfo::getId, period.getUserId());
            UserInfo userInfo = userInfoService.getOne(queryUser);
            userCourse.setBaseInfo(userInfo);
            userCourse.setCourseId(period.getObId());
            userCourse.setStatus(1);
            userCourse.setPrecent(0);
            saveFlag = true;
        }
        int learnTime = course.getLearnTime() * 60;
        double pre = Utils.div(tr.getTotalTime(), learnTime, 2, BigDecimal.ROUND_DOWN);
        pre = Utils.mul(pre, 100);
        if (pre > 100) {
            pre = 100d;
        }
        userCourse.setTotal(tr.getTotalTime());
        userCourse.setLastUserId(period.getUserId());
        userCourse.setLastUpdate(DateUtil.now());
        if (userCourse.getPrecent() != 100 && (int) pre == 100) {
            userCourse.setCompleteTime(DateUtil.now());
        }
        userCourse.setPrecent((int) pre);
        if (saveFlag) {
            courseUserService.save(userCourse);
        } else {
            courseUserService.updateById(userCourse);
        }
    }

    /**
     * 获取其对应的Timer
     *
     * @param period
     * @return
     */
    private Timer getTimer(Period period, TimerType timerType) {
        List<Timer> timerList = timerService.getTimer(period.getObId(), timerType, period.getUserId());
        if (timerList.isEmpty()) {
            return null;
        }
        return timerList.get(0);
    }
}
