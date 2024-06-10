package com.xxsword.xitem.pc;

import com.xxsword.xitem.admin.constant.Device;
import com.xxsword.xitem.admin.constant.TimerType;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.domain.timer.entity.Period;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.timer.BusinessService;
import com.xxsword.xitem.admin.service.timer.TimerService;
import com.xxsword.xitem.admin.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("pc/timer")
public class TimerController {

    @Autowired
    private TimerService timerService;
    @Autowired
    private BusinessService businessService;

    /**
     * pc计时
     */
    @RequestMapping("trace")
    @ResponseBody
    public RestResult trace(HttpServletRequest request, String periodId, Integer resourceType, String resourceId) {
        UserInfo userInfo = Utils.getUserInfo(request);
        if (userInfo == null) {
            return RestResult.Fail();
        }
        Period period = this.traceTime_(userInfo.getId(), periodId, resourceType, resourceId, Device.DEVICE_PC);
        if (period == null) {
            return RestResult.Fail();
        }
        RestResult restResult = RestResult.OK();
        restResult.setData(period.getId());
        return restResult;
    }

    private Period traceTime_(String userid, String periodId, Integer resourceType, String resourceId, Device device) {
        Period period = timerService.trace(userid, resourceId, TimerType.getTimerTypeByCode(resourceType), periodId, device);
        businessService.business(period);
        return period;
    }
}
