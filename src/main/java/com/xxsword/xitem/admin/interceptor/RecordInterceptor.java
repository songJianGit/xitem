package com.xxsword.xitem.admin.interceptor;

import com.xxsword.xitem.admin.domain.entity.system.Record;
import com.xxsword.xitem.admin.domain.entity.system.UserInfo;
import com.xxsword.xitem.admin.service.system.RecordService;
import com.xxsword.xitem.admin.utils.RecordUtils;
import com.xxsword.xitem.admin.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RecordInterceptor implements HandlerInterceptor {

    @Autowired
    private RecordService recordService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {
        UserInfo user = Utils.getUserInfo(request);
        if (user != null) {
            Record record = RecordUtils.httpToRecord(request, user.getId());
            recordService.save(record);
        }
        return true;
    }

}
