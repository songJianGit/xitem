package com.xxsword.xitem.admin.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.domain.entity.system.Functions;
import com.xxsword.xitem.admin.domain.entity.system.RoleFunctions;
import com.xxsword.xitem.admin.mapper.system.RoleFunctionsMapper;
import com.xxsword.xitem.admin.service.system.FunctionsService;
import com.xxsword.xitem.admin.service.system.RoleFunctionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleFunctionsServiceImpl extends ServiceImpl<RoleFunctionsMapper, RoleFunctions> implements RoleFunctionsService {
    @Autowired
    private FunctionsService functionsService;

    @Override
    public List<Functions> listFunctionsByRoleId(String roleId) {
        LambdaQueryWrapper<RoleFunctions> qRoleFun = Wrappers.lambdaQuery();
        qRoleFun.eq(RoleFunctions::getRoleid, roleId);
        List<RoleFunctions> roleFunctionsList = list(qRoleFun);// 角色的菜单
        List<String> functionIds = roleFunctionsList.stream().map(RoleFunctions::getFunid).collect(Collectors.toList());
        LambdaQueryWrapper<Functions> qFunctions = Wrappers.lambdaQuery();
        qFunctions.in(Functions::getId, functionIds);
        qFunctions.eq(Functions::getStatus, 1);// 菜单会被逻辑删除，需要查询状态为有效的菜单
        return functionsService.list(qFunctions);
    }

    @Override
    public void roleFunctionsSave(String roleId, String funIds) {
        if (StringUtils.isNotBlank(funIds)) {
            List<Functions> functionsList = listFunctionsByRoleId(roleId);
            Set<String> sets = functionsList.stream().map(Functions::getId).collect(Collectors.toSet());
            String[] funs = funIds.split(",");
            List<RoleFunctions> list = new ArrayList<>();
            for (String fId : funs) {
                if (!sets.contains(fId)) {
                    RoleFunctions r = new RoleFunctions();
                    r.setFunid(fId);
                    r.setRoleid(roleId);
                    list.add(r);
                }
            }
            saveBatch(list);
        }
    }
}
