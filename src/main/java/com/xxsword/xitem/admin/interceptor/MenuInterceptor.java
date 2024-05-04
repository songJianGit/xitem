package com.xxsword.xitem.admin.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.constant.Constant;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MenuInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String mClick = request.getParameter(Constant.MENUC_LICK_INFO);
        if (StringUtils.isNotBlank(mClick)) {
            request.getSession().setAttribute(Constant.MENUC_LICK_INFO, mClick);// 记住点击的菜单信息
        }
        return true;
    }
}
