package com.xxsword.xitem.admin.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.domain.system.entity.Functions;
import com.xxsword.xitem.admin.domain.system.entity.Role;
import com.xxsword.xitem.admin.domain.system.entity.RoleFunctions;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.service.system.FunctionsService;
import com.xxsword.xitem.admin.service.system.RoleFunctionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private FunctionsService functionsService;
    @Autowired
    private RoleFunctionsService roleFunctionsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 验证用户是否登陆
        Object o = request.getSession().getAttribute(Constant.USER_INFO);
        if (o == null) {
            return back(request, response);
        }
        UserInfo userInfo = (UserInfo) o;
        List<Role> roleSet = userInfo.getRolelist();
        if (roleSet == null) {
            return back(request, response);
        }
        String url = request.getServletPath();
        if (StringUtils.isBlank(url)) {
            return back(request, response);
        }
        Functions functions = checkF(url);
        if (functions == null) {
            return true;// 没有配置该url，默认放行。
        } else {
            List<String> roleIds = roleSet.stream().map(Role::getId).collect(Collectors.toList());
            if (checkRF(roleIds, functions)) {
                return true;
            }
        }
        return back(request, response);
    }

    private boolean back(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 是否有这个链接（是否需要检查权限）
     *
     * @return
     */
    private Functions checkF(String url) {
        List<Functions> haveFun = functionsService.list(new LambdaQueryWrapper<Functions>().select(Functions::getId).eq(Functions::getStatus, 1).eq(Functions::getUrl, url));
        if (haveFun.isEmpty()) {
            return null;
        }
        return haveFun.get(0);
    }

    /**
     * 检查角色是否有本权限
     *
     * @param roleIds
     * @return true-有 false-没有
     */
    private boolean checkRF(List<String> roleIds, Functions functions) {
        long roleFunctionsCount = roleFunctionsService.count(new LambdaQueryWrapper<RoleFunctions>().in(RoleFunctions::getRoleid, roleIds).eq(RoleFunctions::getFunid, functions.getId()));
        return roleFunctionsCount != 0;
    }
}
