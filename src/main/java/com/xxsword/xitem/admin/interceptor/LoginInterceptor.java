package com.xxsword.xitem.admin.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.domain.system.entity.Function;
import com.xxsword.xitem.admin.domain.system.entity.Role;
import com.xxsword.xitem.admin.domain.system.entity.RoleFunction;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.service.system.FunctionService;
import com.xxsword.xitem.admin.service.system.RoleFunctionService;
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
    private FunctionService functionService;
    @Autowired
    private RoleFunctionService roleFunctionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 验证用户是否登录
        Object o = request.getSession().getAttribute(Constant.USER_INFO);
        if (o == null) {
            return back(request, response);
        }
        UserInfo userInfo = (UserInfo) o;
        List<Role> roleSet = userInfo.getRoleList();
        if (roleSet == null || roleSet.isEmpty()) {
            return back(request, response);
        }
        String url = request.getServletPath();
        if (StringUtils.isBlank(url)) {
            return back(request, response);
        }
        Function function = checkF(url);
        if (function == null) {
            return true;// 没有配置该url，默认放行。
        } else {
            List<String> roleIds = roleSet.stream().map(Role::getId).collect(Collectors.toList());
            if (checkRF(roleIds, function)) {
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
    private Function checkF(String url) {
        List<Function> haveFun = functionService.list(new LambdaQueryWrapper<Function>().select(Function::getId).eq(Function::getStatus, 1).eq(Function::getUrl, url));
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
    private boolean checkRF(List<String> roleIds, Function function) {
        long roleFunctionsCount = roleFunctionService.count(new LambdaQueryWrapper<RoleFunction>().in(RoleFunction::getRoleId, roleIds).eq(RoleFunction::getFunId, function.getId()));
        return roleFunctionsCount != 0;
    }
}
