package com.xxsword.xitem.admin.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.domain.entity.system.Functions;
import com.xxsword.xitem.admin.domain.entity.system.Role;
import com.xxsword.xitem.admin.domain.entity.system.RoleFunctions;
import com.xxsword.xitem.admin.domain.entity.system.UserInfo;
import com.xxsword.xitem.admin.service.system.FunctionsService;
import com.xxsword.xitem.admin.service.system.RoleFunctionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
        if (userInfo == null) {
            return back(request, response);
        }
        List<Role> roleSet = userInfo.getRolelist();
        if (roleSet == null) {
            return back(request, response);
        }
        for (Role role : roleSet) {
            if (checkF(request, role)) {
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
     * 检查用户是否有本权限    true-有 false-没有
     *
     * @param request
     * @param role
     * @return
     */
    private boolean checkF(HttpServletRequest request, Role role) {
        String url = request.getServletPath();
        if (StringUtils.isBlank(url)) {
            return false;
        }
        // 是否有这个链接（是否需要检查权限）
        List<Functions> haveFun = functionsService.list(new LambdaQueryWrapper<Functions>().select(Functions::getId).eq(Functions::getStatus, 1).likeRight(Functions::getUrl, url));
        if (haveFun.isEmpty()) {
            return true;// 没有配置菜单，默认放行
        }
        // 角色是否拥有本链接
        long roleFunctionsCount = roleFunctionsService.count(new LambdaQueryWrapper<RoleFunctions>().eq(RoleFunctions::getRoleid, role.getId()).eq(RoleFunctions::getFunid, haveFun.get(0).getId()));
        if (roleFunctionsCount == 0) {
            return false;// 该角色没有此权限
        }
        return true;
    }
}
